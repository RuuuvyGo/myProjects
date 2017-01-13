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

import manager.FileNodeManager;
import manager.TeamFileNodeManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import socialProExceptions.CommitExcetion;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;
import socialProExceptions.TeamException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import form.FolderForm;
import form.FolderPathForm;

/**
 * Servlet implementation class FolderServlet
 */
@WebServlet(name="/FolderServlet",urlPatterns={"/createFolder","/updateFolder","/dropFolder","/searchFolder","/loadFolder","/loadParentFolders","/loadSiblingFolders","/loadOnlyOriginFolders"})
public class FolderServlet extends HttpServlet {
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
        
        if(action.equals("loadFolder")){
        	//
        	loadFolder(request,response);
        }
        else if(action.equals("createFolder")){
        	//
        	createFolder(request,response);
        }
        else if(action.equals("updateProject")){
        	//
        	
        }
        else if(action.equals("searchProject")){
        	//
        	
        }else if(action.equals("loadProject")){
        	//loadProject(request,response);
        	
        }else if(action.equals("loadParentFolders")){
        	loadParentFolders(request,response);
        }else if(action.equals("loadSiblingFolders")){
        	loadSiblingFolders(request,response);
        }else if(action.equals("loadOnlyOriginFolders")){
        	loadOnlyOriginFolders(request,response);
        }
	}
	
	

	private void createFolder(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		if(json!=null){
			
			System.out.println("createFolder data reach : "+json);
			
			String folderName = (String)json.get("folderName");
			String folderDes = (String)json.get("folderDes");
			String storageCode = (String)json.get("storageCode");
			String storageName = (String)json.get("storageName");
			String memberCode = (String)json.get("memberCode");
			String nickName = (String)json.get("nickName");
			List<String> parentFolderCode = (List<String>)json.get("folderCodes");
			String projectCode = (String)json.get("projectCode");
			String commitTitle = (String)json.get("commitTitle");
			String commitContent = (String)json.get("commitContent");
			String branchCode = (String)json.get("branchCode");
			
			if(commitTitle==null){commitTitle="create";}
			if(commitContent==null){commitContent="create folder.";}
			
			
			StringTokenizer t = new StringTokenizer(storageCode, "_");
			if(t.nextToken().equals("team")){
				this.createFoldersForTeam(request, response, storageCode, storageName, branchCode, memberCode, projectCode, folderName, folderDes, parentFolderCode, commitTitle, commitContent);
			}
			else{
			
				//insert Data
				String folderCode=null;
				try {
					folderCode = FileNodeManager.getINSTANCE().createOriginFolderByPFCode(memberCode, nickName,projectCode, folderName, folderDes, parentFolderCode,commitTitle,commitContent);
					if(folderCode==null){
						//fail
						responseJson.put("answer","fail");
						responseJson.put("failMsg","projectName has already used....");
						System.out.println(responseJson);
						response.getWriter().print(responseJson);
					}else{
						//success
						responseJson.put("answer","success");
						responseJson.put("folderCode",folderCode);
						responseJson.put("folderName",folderName);
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
	
	private void loadFolder(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		
		/*    'id' // 'name' // 'description' // 'date' // 'share' // 'status'   */
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONObject responseJsonFolder = new JSONObject();
		JSONObject responseJsonFile = new JSONObject();

		System.out.println("loadFolder reach : "+json);
		if(json!=null){
			
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			Object obj=json.get("folderCodes");
			List<String> folderCodes=null;
			if(obj!=null){folderCodes = (List<String>)obj;}
			 
			
			StringTokenizer t = new StringTokenizer(storageCode, "_");
			if(t.nextToken().equals("team")){
				System.out.println("go Team....");
				String branchCode = (String)json.get("branchCode");
				String branchName = (String)json.get("branchName");
				System.out.println("branchCode   :  "+branchCode);
				this.loadFoldersForTeam(request, response,memberCode, storageCode, projectCode, folderCodes, branchCode);
			}else{
				//load Data
				String failMsg = "NO Project. Make it right Now!!";
				try {
					//search FolderList
					Map<String, List<FolderForm>> oriFolderMap=FileNodeManager.getINSTANCE().searchFoldersFilesMap(memberCode, projectCode, folderCodes);
					if(oriFolderMap.isEmpty()){
						//fail
						responseJson.put("answer","fail");
						//responseJsonFolder.put("failMsg","NO Project. Make it right Now!!");
					}else{
						List<FolderForm> folderList = oriFolderMap.get("folderList");
						List<FolderForm> fileList = oriFolderMap.get("fileList");
						if(!folderList.isEmpty()){
							JSONArray jList = new JSONArray();
							for(FolderForm oriFolder : folderList){
								JSONObject jobj = new JSONObject();
								jobj.put("id",oriFolder.getId());
								jobj.put("name",oriFolder.getName());
								jobj.put("type",oriFolder.getType());
								
								if(oriFolder.getAlterDate()==null) jobj.put("date",oriFolder.getMakeDate());
								else jobj.put("date",oriFolder.getAlterDate());
								if(oriFolder.getDescription()!=null) jobj.put("description",oriFolder.getDescription());
								jList.add(jobj);
								//for test
							}
							responseJson.put("folderList", jList);
						}
						if(!fileList.isEmpty()){
							JSONArray jList = new JSONArray();
							for(FolderForm oriFolder : fileList){
								JSONObject jobj = new JSONObject();
								jobj.put("id",oriFolder.getId());
								jobj.put("name",oriFolder.getName());
								jobj.put("type",oriFolder.getType());
								
								if(oriFolder.getAlterDate()==null) jobj.put("date",oriFolder.getMakeDate());
								else jobj.put("date",oriFolder.getAlterDate());
								if(oriFolder.getDescription()!=null) jobj.put("description",oriFolder.getDescription());
								jList.add(jobj);
								//for test
							}
							responseJson.put("fileList", jList);
						}
						
						responseJson.put("answer","success");
					}
					System.out.println(" searchFoldersFilesMap  "+responseJson);
					response.getWriter().print(responseJson);
					
					
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
	private void loadOnlyOriginFolders(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		/*    'id' // 'name' // 'description' // 'date' // 'share' // 'status'   */
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONObject responseJsonFolder = new JSONObject();
		JSONObject responseJsonFile = new JSONObject();

		System.out.println("loaOnlyFolder reach : "+json);
		if(json!=null){
			
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			Object obj=json.get("folderCodes");
			List<String> folderCodes=null;
			if(obj!=null){folderCodes = (List<String>)obj;}
			 
			
			StringTokenizer t = new StringTokenizer(storageCode, "_");
			if(t.nextToken().equals("team")){
				System.out.println("go Team....");
				String branchCode = (String)json.get("branchCode");
				String branchName = (String)json.get("branchName");
				System.out.println("branchCode   :  "+branchCode);
				this.loadOnlyOriginFoldersForTeam(request, response,memberCode, storageCode, projectCode, folderCodes, branchCode);
			}else{
				//load Data
				String failMsg = "NO Project. Make it right Now!!";
				try {
					//search FolderList
					List<FolderPathForm> folderList = FileNodeManager.getINSTANCE().searchOnlyOriginFoldersMap(memberCode, projectCode, folderCodes);
					if(folderList.isEmpty()){
						//fail
						responseJson.put("answer","fail");
						//responseJsonFolder.put("failMsg","NO Project. Make it right Now!!");
					}else{
						JSONArray jList = new JSONArray();
						for(FolderPathForm oriFolder : folderList){
							JSONObject jobj = new JSONObject();
							jobj.put("id",oriFolder.getId());
							jobj.put("name",oriFolder.getName());
							jobj.put("type",oriFolder.getType());
							jobj.put("path",oriFolder.getPath());
							
							jList.add(jobj);
							//for test
						}
						responseJson.put("folderList", jList);
						responseJson.put("answer","success");
					}
					System.out.println(" searchFoldersFilesMap  "+responseJson);
					response.getWriter().print(responseJson);
					
					
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
	
	
	private void loadParentFolders(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		
		/*    'id' // 'name' // 'description' // 'date' // 'share' // 'status'   */
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONObject responseJsonFolder = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("loadParentFolders data reach : "+json);
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

			
			StringTokenizer t = new StringTokenizer(storageCode, "_");
			if(t.nextToken().equals("team")){
				String branchCode = (String)json.get("branchCode");
				this.loadParentFoldersForTeam(request, response, memberCode, storageCode, branchCode, projectCode, folderCodes);
			}
			else{
				//load Data
				String failMsg = "NO Project. Make it right Now!!";
				try {
					//search FolderList
					List<FolderForm> oriFolderList=FileNodeManager.getINSTANCE().searchParentFolders(memberCode, projectCode, folderCodes);
					if(oriFolderList.isEmpty()){
						//fail
						responseJsonFolder.put("folderAnswer","fail");
						//responseJsonFolder.put("folderFailMsg","NO Project. Make it right Now!!");
					}else{
						//success make json
						int cnt = oriFolderList.size();
						//make json array data
						FolderForm oriFolder=null;
						for(int i=cnt-1;i>=0;i--){
							JSONObject jobj = new JSONObject();
							oriFolder=oriFolderList.get(i);
							jobj.put("id",oriFolder.getId());
							jobj.put("name",oriFolder.getName());
							jobj.put("type",oriFolder.getType());
							
							jList.add(jobj);
							//for test
						}
						System.out.println(jList);
						System.out.println("설마....");
						//responseJson.put("teamList",jList);
					}
					JSONObject jRes = new JSONObject();
					jRes.put("folderRes", responseJsonFolder);
					jRes.put("list", jList);
					System.out.println("jRes  :   "+jRes);
					response.getWriter().print(jRes);
					
					
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
	
	private void loadSiblingFolders(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		
		/*    'id' // 'name' // 'description' // 'date' // 'share' // 'status'   */
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONObject responseJsonFolder = new JSONObject();
		System.out.println("loadSiblingFolders reach : "+json);
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
			
			//load Data
			String failMsg = "NO Project. Make it right Now!!";
			String parentFolderCode=null;
			try {
				StringTokenizer t = new StringTokenizer(storageCode, "_");
				if(t.nextToken().equals("team")){
					System.out.println("go Team....");
					String branchCode = (String)json.get("branchCode");
					String branchName = (String)json.get("branchName");
					this.searchSiblingFoldersMapForTeam(request, response,memberCode, storageCode, projectCode, folderCodes, branchCode);
				}else{

					//search siblings
					Map<String, List<FolderForm>> oriFolderList = FileNodeManager.getINSTANCE().searchSiblingFoldersMap(memberCode, projectCode, folderCodes);
					
					if(oriFolderList.isEmpty()){
						//fail
						responseJsonFolder.put("answer","fail");
						responseJsonFolder.put("failMsg","NO Folder. Make it right Now!!");
					}else{
						List<FolderForm> folderList = oriFolderList.get("folderList");
						List<FolderForm> fileList = oriFolderList.get("fileList");
						if(!folderList.isEmpty()){
							JSONArray jList = new JSONArray();
							for(FolderForm oriFolder : folderList){
								JSONObject jobj = new JSONObject();
								jobj.put("id",oriFolder.getId());
								jobj.put("name",oriFolder.getName());
								jobj.put("type",oriFolder.getType());
								
								if(oriFolder.getAlterDate()==null) jobj.put("date",oriFolder.getMakeDate());
								else jobj.put("date",oriFolder.getAlterDate());
								if(oriFolder.getDescription()!=null) jobj.put("description",oriFolder.getDescription());
								jList.add(jobj);
								//for test
							}
							responseJson.put("folderList", jList);
						}
						if(!fileList.isEmpty()){
							JSONArray jList = new JSONArray();
							for(FolderForm oriFolder : fileList){
								JSONObject jobj = new JSONObject();
								jobj.put("id",oriFolder.getId());
								jobj.put("name",oriFolder.getName());
								jobj.put("type",oriFolder.getType());
								
								if(oriFolder.getAlterDate()==null) jobj.put("date",oriFolder.getMakeDate());
								else jobj.put("date",oriFolder.getAlterDate());
								if(oriFolder.getDescription()!=null) jobj.put("description",oriFolder.getDescription());
								jList.add(jobj);
								//for test
							}
							responseJson.put("fileList", jList);
						}
						
						responseJson.put("answer","success");	
					}
					System.out.println("loadSiblingFolders  :   "+responseJson);
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
	
	private void loadFoldersForTeam(HttpServletRequest request,HttpServletResponse response, String memberCode, String storageCode, String projectCode, List<String> folderCodes, String branchCode) throws IOException{
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONObject responseJsonFolder = new JSONObject();
		JSONObject responseJsonFile = new JSONObject();
		
		//load Data
		String failMsg = "NO Project. Make it right Now!!";
		try {
			//search FolderList
			Map<String, List<FolderForm>> oriFolderMap=TeamFileNodeManager.getINSTANCE().searchFolders(storageCode, branchCode, projectCode, folderCodes);
			if(oriFolderMap.isEmpty()){
				//fail
				responseJson.put("answer","fail");
				//responseJsonFolder.put("failMsg","NO Project. Make it right Now!!");
			}else{
				List<FolderForm> folderList = oriFolderMap.get("folderList");
				List<FolderForm> fileList = oriFolderMap.get("fileList");
				if(!folderList.isEmpty()){
					JSONArray jList = new JSONArray();
					for(FolderForm oriFolder : folderList){
						JSONObject jobj = new JSONObject();
						jobj.put("id",oriFolder.getId());
						jobj.put("name",oriFolder.getName());
						jobj.put("type",oriFolder.getType());
						
						if(oriFolder.getAlterDate()==null) jobj.put("date",oriFolder.getMakeDate());
						else jobj.put("date",oriFolder.getAlterDate());
						if(oriFolder.getDescription()!=null) jobj.put("description",oriFolder.getDescription());
						jList.add(jobj);
						//for test
					}
					responseJson.put("folderList", jList);
				}
				if(!fileList.isEmpty()){
					JSONArray jList = new JSONArray();
					for(FolderForm oriFolder : fileList){
						JSONObject jobj = new JSONObject();
						jobj.put("id",oriFolder.getId());
						jobj.put("name",oriFolder.getName());
						jobj.put("type",oriFolder.getType());
						
						if(oriFolder.getAlterDate()==null) jobj.put("date",oriFolder.getMakeDate());
						else jobj.put("date",oriFolder.getAlterDate());
						if(oriFolder.getDescription()!=null) jobj.put("description",oriFolder.getDescription());
						jList.add(jobj);
						//for test
					}
					responseJson.put("fileList", jList);
				}
				
				responseJson.put("answer","success");
			}
			System.out.println(" loadFoldersForTeam  res:  "+responseJson);
			response.getWriter().print(responseJson);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseJson.put("answer","fail");
			responseJson.put("failMsg",e.getMessage());
			System.out.println(responseJson);
			response.getWriter().print(responseJson);
			e.printStackTrace();
		} 
	}
	private void loadOnlyOriginFoldersForTeam(HttpServletRequest request,HttpServletResponse response, String memberCode, String storageCode, String projectCode, List<String> folderCodes, String branchCode) throws IOException{
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONObject responseJsonFolder = new JSONObject();
		JSONObject responseJsonFile = new JSONObject();
		
		//load Data
		String failMsg = "NO Project. Make it right Now!!";
		try {
			//search FolderList
			List<FolderPathForm> folderList=TeamFileNodeManager.getINSTANCE().searchOnlyTeamSharedFolders(storageCode, branchCode, projectCode, folderCodes);
			if(folderList.isEmpty()){
				//fail
				responseJson.put("answer","fail");
				//responseJsonFolder.put("failMsg","NO Project. Make it right Now!!");
			}else{
				JSONArray jList = new JSONArray();
				for(FolderPathForm oriFolder : folderList){
					JSONObject jobj = new JSONObject();
					jobj.put("id",oriFolder.getId());
					jobj.put("name",oriFolder.getName());
					jobj.put("type",oriFolder.getType());
					jobj.put("path",oriFolder.getPath());
					
					jList.add(jobj);
					//for test
				}
				responseJson.put("folderList", jList);
				responseJson.put("answer","success");
			}
			System.out.println(" loadFoldersForTeam  res:  "+responseJson);
			response.getWriter().print(responseJson);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseJson.put("answer","fail");
			responseJson.put("failMsg",e.getMessage());
			System.out.println(responseJson);
			response.getWriter().print(responseJson);
			e.printStackTrace();
		} 
	}
	
	private void loadParentFoldersForTeam(HttpServletRequest request,HttpServletResponse response,String memberCode, String storageCode, String branchCode,String projectCode, List<String> folderCodes) throws IOException{
		System.out.println("FolderServlet    loadParentFoldersForTeam.......");
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONObject responseJsonFolder = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("loadParentFolders data reach : "+json);

		
			//load Data
			String failMsg = "NO Project. Make it right Now!!";
			try {
				
				//search FolderList
				if(storageCode.equals(branchCode))branchCode=null;
				List<FolderForm> oriFolderList = TeamFileNodeManager.getINSTANCE().searchParentFolders(storageCode, branchCode, projectCode, folderCodes);

				if(oriFolderList.isEmpty()){
					//fail
					responseJsonFolder.put("folderAnswer","fail");
					//responseJsonFolder.put("folderFailMsg","NO Project. Make it right Now!!");
				}else{
					//success make json
					int cnt = oriFolderList.size();
					//make json array data
					FolderForm oriFolder=null;
					for(int i=0;i<cnt;i++){
						JSONObject jobj = new JSONObject();
						oriFolder=oriFolderList.get(i);
						System.out.println("didi   :    "+oriFolder.getId());
						jobj.put("id",oriFolder.getId());
						jobj.put("name",oriFolder.getName());
						jobj.put("type",oriFolder.getType());
						
						jList.add(jobj);
						//for test
					}
					System.out.println(jList);
					System.out.println("설마....");
					//responseJson.put("teamList",jList);
				}
				JSONObject jRes = new JSONObject();
				jRes.put("folderRes", responseJsonFolder);
				jRes.put("list", jList);
				System.out.println("jRes  :   "+jRes);
				response.getWriter().print(jRes);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				responseJson.put("answer","fail");
				responseJson.put("failMsg",e.getMessage());
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
				e.printStackTrace();
			} 
		
		
	}

	private void createFoldersForTeam(HttpServletRequest request,HttpServletResponse response, String storageCode,String storageName,String branchCode,String memberCode,
			String projectCode, String folderName, String folderDes, List<String > folderCodes, String commitTitle,String commitContent) throws IOException{
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
		JSONObject responseJson = new JSONObject();
	
		//insert Data
		String folderCode=null;
		try {
			folderCode = TeamFileNodeManager.getINSTANCE().createTeamFolder(storageCode, storageName, branchCode,memberCode, projectCode, folderCodes, folderName, folderDes, commitTitle, commitContent);
			if(folderCode==null){
				//fail
				responseJson.put("answer","fail");
				responseJson.put("failMsg","projectName has already used....");
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
			}else{
				//success
				responseJson.put("answer","success");
				responseJson.put("folderCode",folderCode);
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

	private void searchSiblingFoldersMapForTeam(HttpServletRequest request,HttpServletResponse response, 
			String memberCode,String storageCode, String projectCode, List<String> folderCodes,String branchCode) throws IOException, DAOException, ParseException, FolderException, FileException, ProjectException, SftpException, JSchException, TeamException, CommitExcetion {
		// TODO Auto-generated method stub

		JSONObject responseJson = new JSONObject();
		JSONObject responseJsonFolder = new JSONObject();
		//search siblings
		Map<String, List<FolderForm>> oriFolderList = TeamFileNodeManager.getINSTANCE().searchTeamFolderSiblings(storageCode, branchCode, projectCode, folderCodes);
		
		if(oriFolderList.isEmpty()){
			//fail
			responseJsonFolder.put("answer","fail");
			responseJsonFolder.put("failMsg","NO Folder. Make it right Now!!");
		}else{
			List<FolderForm> folderList = oriFolderList.get("folderList");
			List<FolderForm> fileList = oriFolderList.get("fileList");
			if(!folderList.isEmpty()){
				JSONArray jList = new JSONArray();
				for(FolderForm oriFolder : folderList){
					JSONObject jobj = new JSONObject();
					jobj.put("id",oriFolder.getId());
					jobj.put("name",oriFolder.getName());
					jobj.put("type",oriFolder.getType());
					
					if(oriFolder.getAlterDate()==null) jobj.put("date",oriFolder.getMakeDate());
					else jobj.put("date",oriFolder.getAlterDate());
					if(oriFolder.getDescription()!=null) jobj.put("description",oriFolder.getDescription());
					jList.add(jobj);
					//for test
				}
				responseJson.put("folderList", jList);
			}
			if(!fileList.isEmpty()){
				JSONArray jList = new JSONArray();
				for(FolderForm oriFolder : fileList){
					JSONObject jobj = new JSONObject();
					jobj.put("id",oriFolder.getId());
					jobj.put("name",oriFolder.getName());
					jobj.put("type",oriFolder.getType());
					
					if(oriFolder.getAlterDate()==null) jobj.put("date",oriFolder.getMakeDate());
					else jobj.put("date",oriFolder.getAlterDate());
					if(oriFolder.getDescription()!=null) jobj.put("description",oriFolder.getDescription());
					jList.add(jobj);
					//for test
				}
				responseJson.put("fileList", jList);
			}
			
			responseJson.put("answer","success");	
		}
		System.out.println("loadSiblingFolders  :   "+responseJson);
		response.getWriter().print(responseJson);
	}

}
