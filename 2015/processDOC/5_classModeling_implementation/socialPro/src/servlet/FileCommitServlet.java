package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import form.CommitForm;

/**
 * Servlet implementation class FileCommitServlet
 */
@WebServlet(name="/FileCommitServlet",urlPatterns={"/insertFileCommit","/updateFileContentWithCommit","/mergeNewFile","/checkBeforeMerge"})
public class FileCommitServlet extends HttpServlet {
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
        
        if(action.equals("insertFileCommit")){
        	this.insertFileCommit(request,response);
        }else if(action.equals("updateFileContentWithCommit")){
        	this.updateFileContentWithCommit(request,response);
        }else if(action.equals("mergeNewFile")){
        	this.mergeNewFile(request,response);
        }else if(action.equals("checkBeforeMerge")){
        	this.checkBeforeMerge(request,response);
        }
	}
	
	

	private void insertFileCommit(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
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
			String fileContent = (String)json.get("fileContent");
			String commitTitle = (String)json.get("commitTitle");
			String commitContent = (String)json.get("commitContent");
			
			if(commitTitle==null)commitTitle="create";
			if(commitContent==null)commitContent="create file";
			
			StringTokenizer tokens = new StringTokenizer(storageCode,"_");
			if(tokens.nextToken().equals("team")){
				System.out.println("createTeamFile....");
				String branchCode = (String)json.get("branchCode");
				String storageName = (String)json.get("storageName");
				this.createTeamFile(request,response,storageCode,storageName, branchCode, projectCode, folderCodes, fileName, fileContent, commitTitle, commitContent);
			}else{
			
				//insert Data
				try {
					String fileCode = FileNodeManager.getINSTANCE().createFileCode(memberCode, projectCode, folderCodes, fileName, fileContent, commitTitle, commitContent);
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
					
				} catch(Exception e){
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
	

	private void updateFileContentWithCommit(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		
		if(json!=null){
			System.out.println("updateFileContentWithCommit :>>  data reach : "+json);
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
			String commitTitle = (String)json.get("commitTitle");
			String commitContent = (String)json.get("commitContent");
			
			StringTokenizer tokens = new StringTokenizer(storageCode, "_");
			if(tokens.nextToken().equals("team")){
				String branchCode = (String)json.get("branchCode");
				this.updateTeamFileContentWithCommit(request, response, storageCode, branchCode, projectCode, folderCodes, fileCode, fileContent, commitTitle, commitContent);
			}else{
				//insert Data
				try {
					boolean res = FileNodeManager.getINSTANCE().modifyFileContent(memberCode,storageCode,projectCode,folderCodes,fileCode,fileContent,commitTitle,commitContent);
					if(res){
						//success
						responseJson.put("answer","success");
						System.out.println("성공성공");
					}else{
						//fail
						responseJson.put("answer","fail");
						responseJson.put("failMsg","fileName has already used....");
					}
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
					
				} catch(Exception e){
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


	private void createTeamFile(HttpServletRequest request,HttpServletResponse response,String storageCode,String storageName,String branchCode,String projectCode,List<String> folderCodes,String fileName,String content,String commitTitle,String commitContent) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
		JSONObject responseJson = new JSONObject();
		
		//insert Data
		try {
			String fileCode = TeamFileNodeManager.getINSTANCE().createTeamFile(storageCode,storageName, branchCode, projectCode, folderCodes, fileName, content, commitTitle, commitContent);
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
			
		} catch(Exception e){
			responseJson.put("answer","fail");
			responseJson.put("failMsg",e.getMessage());
			System.out.println(responseJson);
			response.getWriter().print(responseJson);
			e.printStackTrace();
		}	
	}
	
	
	private void updateTeamFileContentWithCommit(HttpServletRequest request,HttpServletResponse response,String storageCode, String branchCode, String projectCode, List<String> folderCodes, String fileCode, String fileContent, String commitTitle,String commitContent) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
		JSONObject responseJson = new JSONObject();
		//insert Data
		try {
			boolean res = TeamFileNodeManager.getINSTANCE().modifyFileContent(storageCode, branchCode, projectCode, folderCodes, fileCode, fileContent, commitTitle, commitContent);
			if(res){
				//success
				responseJson.put("answer","success");
			}else{
				//fail
				responseJson.put("answer","fail");
				responseJson.put("failMsg","fileName has already used....");
			}
			System.out.println("updateTeamFileContentWithCommit    "+responseJson);
			response.getWriter().print(responseJson);
			
		} catch(Exception e){
			responseJson.put("answer","fail");
			responseJson.put("failMsg",e.getMessage());
			System.out.println(responseJson);
			response.getWriter().print(responseJson);
			e.printStackTrace();
		}
	}
	private void mergeNewFile(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray list = new JSONArray();
		JSONArray commitJSONList = new JSONArray();
		JSONArray mergeJSONList = new JSONArray();
		
		if(json!=null){
			System.out.println("searchCommitList  data reach : "+json);
			
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			List<String> folderCodes = (List<String>)json.get("folderCodes");
			String commiter = (String)json.get("commiter");
			String commitCode = (String)json.get("commitCode");
			String msgCode = (String)json.get("msgCode");
			//
			//insert Data
			try {
				boolean res=false;
				if(new StringTokenizer(storageCode, "_").nextToken().equals("team")){
					String branchCode = (String)json.get("branchCode");
					res = TeamFileNodeManager.getINSTANCE().mergeNewObj(storageCode, branchCode, memberCode, projectCode, commiter, commitCode, msgCode, folderCodes);
				}else{
					res = FileNodeManager.getINSTANCE().mergeNewObj(memberCode, projectCode, commiter, commitCode, msgCode, folderCodes);
				}
				if(res){
					responseJson.put("answer", "success");
				}else{
					responseJson.put("answer", "fail");
				}
				System.out.println("searchCommitList"+responseJson);
				response.getWriter().print(responseJson);
				
			} catch(Exception e){
				responseJson.put("answer","fail");
				responseJson.put("failMsg",e.getMessage());
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
				e.printStackTrace();
			}	
		}else {
    		responseJson.put("answer","fail");
    		responseJson.put("failMsg","fail to Join. Try Again Please.");
    		response.getWriter().print(responseJson);
		}		
	}
	
	private void checkBeforeMerge(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray list = new JSONArray();
		JSONArray commitJSONList = new JSONArray();
		JSONArray mergeJSONList = new JSONArray();
		
		if(json!=null){
			System.out.println("checkBeforeMerge  data reach : "+json);
			
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			String commiter = (String)json.get("commiter");
			String commitCode = (String)json.get("commitCode");
			String msgCode = (String)json.get("msgCode");
			//
			//insert Data
			try {
				boolean res=false;
				String checkKey=null;
				if(new StringTokenizer(storageCode, "_").nextToken().equals("team")){
					String branchCode = (String)json.get("branchCode");
					checkKey = TeamFileNodeManager.getINSTANCE().checkFileDiff(storageCode, branchCode, memberCode, projectCode, commiter, commitCode, msgCode);
					if(checkKey.equals("justCopy")){
						res = TeamFileNodeManager.getINSTANCE().mergeNewObj(storageCode, branchCode, memberCode, projectCode, commiter, commitCode, msgCode);
					}else if(checkKey.equals("justCopyContent")){
						res = TeamFileNodeManager.getINSTANCE().mergeFileContent(storageCode, branchCode, memberCode, projectCode, commiter, commitCode, msgCode);						
					} 
				}else{
					checkKey = FileNodeManager.getINSTANCE().checkFileDiff(memberCode, projectCode, commiter,commitCode,msgCode);
					System.out.println("res_String    :   "+checkKey);
					if(checkKey.equals("justCopy")){
						res = FileNodeManager.getINSTANCE().mergeNewObj(memberCode, projectCode, commiter, commitCode, msgCode);
					}else if(checkKey.equals("justCopyContent")){
						res = FileNodeManager.getINSTANCE().mergeFileContent(memberCode, projectCode, commiter, commitCode, msgCode);						
					} 
				}
				if(checkKey!=null){
					if(checkKey.equals("needToSelectpath")){
						responseJson.put("checkKey", "needToSelectpath");
					}else if(checkKey.equals("justCopy")){
						responseJson.put("checkKey", "justCopy");
						if(res){
							responseJson.put("res", "ok");
						}else{
							responseJson.put("res", "fail");
						}
					}else if(checkKey.equals("justCopyContent")){
						responseJson.put("checkKey", "justCopyContent");
						if(res){
							responseJson.put("res", "ok");
						}else{
							responseJson.put("res", "fail");
						}
					}else{
						responseJson.put("checkKey", "noCopy");
					}
					responseJson.put("answer", "success");
				}else{
					responseJson.put("answer", "fail");
				}
				System.out.println("checkBeforeMerge "+responseJson);
				response.getWriter().print(responseJson);
				
			} catch(Exception e){
				responseJson.put("answer","fail");
				responseJson.put("failMsg",e.getMessage());
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
				e.printStackTrace();
			}	
		}else {
    		responseJson.put("answer","fail");
    		responseJson.put("failMsg","fail to Join. Try Again Please.");
    		response.getWriter().print(responseJson);
		}		
	}
}
