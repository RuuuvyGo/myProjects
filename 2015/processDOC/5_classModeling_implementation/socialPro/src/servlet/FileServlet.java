package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.CommitInfoManager;
import manager.FileManager;
import manager.FileNodeManager;
import manager.TeamFileNodeManager;
import model.OriginFile;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import socialProExceptions.DAOException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;
import socialProExceptions.TeamException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import form.CommitForm;

/**
 * Servlet implementation class FileServlet
 */
@WebServlet(name="/FileServlet",urlPatterns={"/createFile","/updateFile","/dropFile","/searchFile","/loadFile","/loadFileContent","/updateFileContent","/searchFileDiff"})
public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost");
		processRequest(request,response);
	}
	
	private String processKind(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf("/");
		int nameIndex = uri.lastIndexOf("?");
		String action = null;
		if(nameIndex == -1)
			action = uri.substring(lastIndex+1);
		else
			action = uri.substring(lastIndex+1, nameIndex);
		System.out.println("action : " + action);
		return action;
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        
        String action=processKind(request,response);
        System.out.println("action : " + action);
        
        if(action.equals("createFile")){
        	//
        	createFile(request,response);
        }
        else if(action.equals("updateFile")){
        	//
        }else if(action.equals("dropFile")){
        	//
        	
        }else if(action.equals("searchFile")){
        	//loadProject(request,response);
        	
        }else if(action.equals("loadFile")){
        	//loadParentFolders(request,response);
        	
        }else if(action.equals("loadFileContent")){
        	
        	loadFileContent(request,response);        	
        }else if(action.equals("updateFileContent")){
        	
        	updateFileContent(request,response);
        }else if(action.equals("searchFileDiff")){
        	searchFileDiff(request,response);
        }
	}
	

	private void createFile(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		System.out.println("data reach : "+json);
		if(json!=null){
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			List<String> folderCodes = (List<String>)json.get("folderCodes");
			String fileName = (String)json.get("fileName");
			String content = (String)json.get("content");
			
			//insert Data
			try {
				String fileCode = FileNodeManager.getINSTANCE().createFileCode(memberCode, projectCode, folderCodes, fileName, content);
				if(fileCode==null){
					//fail
					responseJson.put("answer","fail");
					responseJson.put("failMsg","fileName has already used....");
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
				}else{
					//success
					responseJson.put("answer","success");
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				responseJson.put("answer","fail");
				responseJson.put("failMsg",e.getMessage());
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
				e.printStackTrace();
			} 
			
		}
		else {
    		responseJson.put("answer","fail");
    		responseJson.put("failMsg","fail to Join. Try Again Please.");
    		response.getWriter().print(responseJson);
		}
		
	}
	
	private void loadFileContent(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		
		if(json!=null){
			System.out.println("loadFileContent data reach : "+json);
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			List<String> folderCodes = (List<String>)json.get("folderCodes");
			String fileCode = (String)json.get("fileCode");
			
			StringTokenizer tokens = new StringTokenizer(storageCode, "_");
			if(tokens.nextToken().equals("team")){
				String branchCode = (String)json.get("branchCode");
				this.searchTeamFileContent(request,response,storageCode,branchCode,projectCode,folderCodes,fileCode);
			}
			else{
				//getFile content Data
				try {
					
					String oriFile = FileNodeManager.getINSTANCE().searchFileContent(memberCode, projectCode, folderCodes,fileCode);
					if(oriFile==null){
						//fail
						responseJson.put("answer","fail");
						responseJson.put("failMsg","fileName has already used....");
						System.out.println(responseJson);
						response.getWriter().print(responseJson);
					}else{
						//success
						responseJson.put("answer","success");
						responseJson.put("fileContent",oriFile);
						response.getWriter().print(responseJson);
					}
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					responseJson.put("answer","fail");
					responseJson.put("failMsg",e.getMessage());
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
					e.printStackTrace();
				}
			}
		}
		else {
    		responseJson.put("answer","fail");
    		responseJson.put("failMsg","fail to Join. Try Again Please.");
    		response.getWriter().print(responseJson);
		}
		
	}


	private void updateFileContent(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		System.out.println("data reach : "+json);
		if(json!=null){
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			String memberCode = (String)json.get("memberCode");
			String ownerCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			List<String> folderCodes = (List<String>)json.get("folderCodes");
			String fileCode = (String)json.get("fileCode");
			String fileContent = (String)json.get("fileContent");
			
			//insert Data
			try {
				boolean resBoolean = FileNodeManager.getINSTANCE().modifyFileContent(memberCode,ownerCode,projectCode,folderCodes,fileCode,fileContent); 
				//String fileCode = manager.createFileCode(ownerCode, fileName, folderCode, fileContent);
				if(resBoolean){
					//success
					responseJson.put("answer","success");
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
				}else{
					//fail
					responseJson.put("answer","fail");
					responseJson.put("failMsg","fileName has already used....");
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				responseJson.put("answer","fail");
				responseJson.put("failMsg",e.getMessage());
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
				e.printStackTrace();
			} 
			
		}
		else {
    		responseJson.put("answer","fail");
    		responseJson.put("failMsg","fail to Join. Try Again Please.");
    		response.getWriter().print(responseJson);
		}
		
	}
	

	private void searchFileDiff(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		if(json!=null){
			System.out.println("searchFileDiff  data reach : "+json);
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			List<String> folderCodes = (List<String>)json.get("folderCodes");
			String fileCode = (String)json.get("fileCode");
			String fileContent = (String)json.get("fileContent");
			
			StringTokenizer tokens = new StringTokenizer(storageCode,"_");
			if(tokens.nextToken().equals("team")){
				String branchCode = (String)json.get("branchCode");
				this.searchTeamFileDiff(request,response,storageCode,branchCode,projectCode,folderCodes,fileCode,fileContent);
			}else{
				//insert Data
				try {
					String res = FileNodeManager.getINSTANCE().searchDiffFile(memberCode,storageCode,projectCode,folderCodes,fileCode,fileContent); 
					if(res!=null){
						//success
						responseJson.put("answer","success");
						responseJson.put("diffRes",res);
						System.out.println(responseJson);
						response.getWriter().print(responseJson);
					}else{
						//fail
						responseJson.put("answer","fail");
						responseJson.put("failMsg","fileName has already used....");
						System.out.println(responseJson);
						response.getWriter().print(responseJson);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					responseJson.put("answer","fail");
					responseJson.put("failMsg",e.getMessage());
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
					e.printStackTrace();
				} 
			}
		}
		else {
    		responseJson.put("answer","fail");
    		responseJson.put("failMsg","fail to Join. Try Again Please.");
    		response.getWriter().print(responseJson);
		}
		
	}

	private void searchTeamFileContent(HttpServletRequest request,HttpServletResponse response, String storageCode,String branchCode,String projectCode, List<String> folderCodes, String fileCode) throws IOException{
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
		JSONObject responseJson = new JSONObject();
		
		String oriFile;
		try {
			oriFile = TeamFileNodeManager.getINSTANCE().searchFileContent(storageCode, branchCode, projectCode, folderCodes, fileCode);
			if(oriFile==null){
				//fail
				responseJson.put("answer","fail");
				responseJson.put("failMsg","fileName has already used....");
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
			}else{
				//success
				responseJson.put("answer","success");
				responseJson.put("fileContent",oriFile);
				response.getWriter().print(responseJson);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseJson.put("answer","fail");
			responseJson.put("failMsg",e.getMessage());
			System.out.println(responseJson);
			response.getWriter().print(responseJson);
			e.printStackTrace();
		}	
	}
	
	private void searchTeamFileDiff(HttpServletRequest request,HttpServletResponse response, String storageCode,String branchCode, 
			String projectCode, List<String> folderCodes,String fileCode, String fileContent) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    

		JSONObject responseJson = new JSONObject();
		//insert Data
		try {
			String res = TeamFileNodeManager.getINSTANCE().searchDiffFile(storageCode, branchCode, projectCode, folderCodes, fileCode, fileContent);
			if(res!=null){
				//success
				responseJson.put("answer","success");
				responseJson.put("diffRes",res);
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
			}else{
				//fail
				responseJson.put("answer","success");
				responseJson.put("diffRes",fileContent);
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseJson.put("answer","fail");
			responseJson.put("failMsg",e.getMessage());
			System.out.println(responseJson);
			response.getWriter().print(responseJson);
			e.printStackTrace();
		} 
	}

}
