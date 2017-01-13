package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.FileNodeManager;
import manager.MemberManager;
import manager.ProjectManager;
import manager.TeamFileNodeManager;
import manager.TeamManager;
import model.Member;
import model.OriginProject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import form.CommitForm;
import form.LoadProjectForm;
import form.ProjectSearchedForm;
import form.ShareProjectInfo;

/**
 * Servlet implementation class ProjectServlet
 */
@WebServlet(name="/ProjectServlet",urlPatterns={"/createProject","/dropProject","/updateProject","/searchProject","/loadProject","/shareProject","/searchProjectCooperators"})
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Class<? extends OriginProject> OriginProject = null;

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
        
        if(action.equals("createProject")){
        	//
        	createProject(request,response);
        }
        else if(action.equals("dropProject")){
        	this.dropProject(request,response);
        }
        else if(action.equals("updateProject")){
        	//
        	
        }
        else if(action.equals("searchProject")){
        	searchProject(request,response);
        }else if(action.equals("loadProject")){
        	loadProject(request,response);
        }else if(action.equals("shareProject")){
        	shareProject(request,response);
        }else if(action.equals("searchProjectCooperators")){
        	searchProjectCooperators(request,response);
        }
	}

	private void createProject(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		System.out.println("data reach : "+json);
		if(json!=null){
			//Iterator<String> kIt=json.keySet().iterator();
			
			//project Info
			String projectName = (String)json.get("projectName");
			String commitTitle = (String)json.get("commitTitle");
			String commitContent = (String)json.get("commitContent");
			String tagValues = (String)json.get("tagValues");
			String projectDes = (String)json.get("projectDes");
			StringTokenizer tokens = new StringTokenizer(tagValues,",");
			List<String> tags  = new ArrayList<String>();
			while(tokens.hasMoreTokens()){
				tags.add(tokens.nextToken());
			}
			if(commitTitle==null)commitTitle="create";
			if(commitContent==null)commitContent="create project";
			
			//owner info
			String memberCode = (String)json.get("memberCode");
			String nickName = (String)json.get("nickName");
			String storageCode = (String)json.get("storageCode");
			
			StringTokenizer token = new StringTokenizer(storageCode, "_");
			if((token.nextToken()).equals("team")){	
				String storageName = (String)json.get("storageName");
				String branchCode = (String)json.get("branchCode");
				this.createTeamProject(request,response,memberCode,branchCode,projectName,storageCode,storageName,tags,projectDes,commitTitle,commitContent);
			}
			else {
				//insert Data
				String projectCode;
				try {
					String path ="C:\\socialPro\\"+nickName+"\\"+projectName;
					projectCode = FileNodeManager.getINSTANCE().createProject(memberCode, nickName, projectName, projectDes, path, tags,commitTitle,commitContent);
					if(projectCode==null){
						//fail
						responseJson.put("answer","fail");
						responseJson.put("failMsg","projectName has already used....");
						System.out.println(responseJson);
						response.getWriter().print(responseJson);
					}else{
						//success
						responseJson.put("answer","success");
						responseJson.put("projectCode",projectCode);
						responseJson.put("projectName",projectName);
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

	private void searchProject(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList1 = new JSONArray();
		JSONArray jList2 = new JSONArray();
		System.out.println("data reach : "+json);
		if(json!=null){
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			List<String> tagList = new ArrayList<String>();
			String tags = (String)json.get("tagList");
			System.out.println(tags);
			StringTokenizer tokens = new StringTokenizer(tags, ",");
			while(tokens.hasMoreTokens()){
				tagList.add(tokens.nextToken());
			}
			
			try {
				Map<String,List<ProjectSearchedForm>> resMap = ProjectManager.getINSTANCE().searchProjectTagList(tagList);

				if(resMap.isEmpty()){
					//fail
					responseJson.put("answer","fail");
					responseJson.put("failMsg","NO Project. Make it right Now!!");
				}else{
					List<ProjectSearchedForm> personalList = resMap.get("personal");
					List<ProjectSearchedForm> teamList = resMap.get("team");
					
					if(!personalList.isEmpty()){
						for(ProjectSearchedForm oriPro : personalList){
							JSONObject jobj = new JSONObject();
							jobj.put("id",oriPro.getId());
							jobj.put("name",oriPro.getName());
							jobj.put("storageCode",oriPro.getProjectOwnerCode());
							jobj.put("storageName",oriPro.getProjectOwnerName());
							jobj.put("description",oriPro.getDes());
							jobj.put("date",oriPro.getMakeDate());
							jobj.put("share",oriPro.getSharedList()); 
							jobj.put("status",oriPro.getStatus());
							
							jList1.add(jobj);
						}
					}

					if(!teamList.isEmpty()){
						for(ProjectSearchedForm oriPro : teamList){
							JSONObject jobj = new JSONObject();
							jobj.put("id",oriPro.getId());
							jobj.put("name",oriPro.getName());
							jobj.put("storageCode",oriPro.getProjectOwnerCode());
							jobj.put("storageName",oriPro.getProjectOwnerName());
							jobj.put("description",oriPro.getDes());
							jobj.put("date",oriPro.getMakeDate());
							jobj.put("share",oriPro.getSharedList()); 
							jobj.put("status",oriPro.getStatus());
							
							jList2.add(jobj);
						}
					}
					responseJson.put("answer","success");
					responseJson.put("personal",jList1);
					responseJson.put("team",jList2);
					//responseJson.put("teamList",jList);
				}
				System.out.println(responseJson);
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
		else {
    		responseJson.put("answer","fail");
    		responseJson.put("failMsg","fail to Join. Try Again Please.");
    		response.getWriter().print(responseJson);
		}
	}

	
	private void loadProject(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		
		/*    'id' // 'name' // 'description' // 'date' // 'share' // 'status'   */
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("loadProject data reach : "+json);
		if(json!=null){
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			
			StringTokenizer tokens = new StringTokenizer(storageCode, "_");
			if(tokens.nextToken().equals("team")){
				String branchCode = (String)json.get("branchCode");
				System.out.println(branchCode);
				this.loadTeamProject(request,response,memberCode,storageCode,branchCode);
			}else{
				//load Data
				try {
					List<LoadProjectForm> oriProList=FileNodeManager.getINSTANCE().loadProjectMCode(memberCode);
					if(oriProList.isEmpty()){
						//fail
						responseJson.put("answer","fail");
						responseJson.put("failMsg","NO Project. Make it right Now!!");
						System.out.println(responseJson);
						response.getWriter().print(responseJson);
					}else{
						//success make json
						int cnt = oriProList.size();
						//make json array data
						LoadProjectForm oriPro=null;
						for(int i=0;i<cnt;i++){
							JSONObject jobj = new JSONObject();
							
							oriPro=oriProList.get(i);
							jobj.put("id",oriPro.getId());
							jobj.put("name",oriPro.getName());
							jobj.put("description",oriPro.getDes());
							jobj.put("date",oriPro.getMakeDate());
							jobj.put("share",oriPro.getSharedList()); 
							jobj.put("status",oriPro.getStatus());
							
							jList.add(jobj);
						}
						
						System.out.println(jList);
						//responseJson.put("teamList",jList);
						response.getWriter().print(jList.toString());
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
	
	
	public void createTeamProject(HttpServletRequest request,HttpServletResponse response,String managerCode,String branchCode,String projectName,String teamCode,String teamName,List<String> tags,String projectDes,String commitTitle, String commitContent) throws IOException{

		System.out.println("createTeamProject");
		
		JSONObject responseJson = new JSONObject();
		String projectCode;
		try {
			String path ="C:\\socialPro\\"+teamName;
			System.out.println(path);
			projectCode = TeamFileNodeManager.getINSTANCE().createTeamProject(managerCode,branchCode,teamCode, teamName, projectName, projectDes, path, tags,commitTitle,commitContent);
			if(projectCode==null){
				//fail
				responseJson.put("answer","fail");
				responseJson.put("failMsg","projectName has already used....");
				System.out.println(responseJson);
				response.getWriter().print(responseJson);
			}else{
				//success
				responseJson.put("answer","success");
				responseJson.put("projectCode",projectCode);
				responseJson.put("projectName",projectName);
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
	
	private void dropProject(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
		String returnAction=new String();
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		
        try {
			PrintWriter out = response.getWriter();
			
			BufferedReader db = request.getReader();
			JSONObject json = (JSONObject)JSONValue.parse(db);
			
			
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
				
				//delete Data
				/*ProjectManager manager = new ProjectManager();
				OriginProjectList oriProList=manager.searchProjectMCode(memberCode);
				if(oriProList.getList().isEmpty()){
					//fail
					responseJson.put("answer","fail");
					responseJson.put("failMsg","NO Project. Make it right Now!!");
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
				}else{
					//success make json
					int cnt = oriProList.getList().size();
					//make json array data
					OriginProject oriPro=null;
					for(int i=0;i<cnt;i++){
						JSONObject jobj = new JSONObject();
						oriPro=oriProList.getList().get(i);
						jobj.put("id",oriPro.getCode());
						jobj.put("name",oriPro.getName());
						jobj.put("description",oriPro.getDescription());
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.SSS");
						dateFormat.setTimeZone(oriPro.getMakeDate().getTimeZone());
						jobj.put("date",dateFormat.format(oriPro.getMakeDate().getTime()));
						
						if(oriPro.getSharedMemberList()==null)jobj.put("share","0");
						else jobj.put("share",oriPro.getSharedMemberList().size());
						
						if(oriPro.getClass() == OriginProject)jobj.put("status","OriginProject");
						else jobj.put("status","SharedProject");
						jList.add(jobj);
						//for test
						
					}
					System.out.println(jList);
					//responseJson.put("teamList",jList);
					response.getWriter().print(jList.toString());
				}*/
			}
			else {
	    		responseJson.put("answer","fail");
	    		responseJson.put("failMsg","fail to Join. Try Again Please.");
	    		response.getWriter().print(responseJson);
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			responseJson.put("answer","fail");
			responseJson.put("failMsg",e1.getMessage());
			System.out.println(responseJson);
			response.getWriter().print(responseJson);
			e1.printStackTrace();
		}	
		
	}
	
	private void loadTeamProject(HttpServletRequest request,HttpServletResponse response,String memberCode, String storageCode,String branchCode) throws IOException {

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		
		//load Data
		try {
			List<LoadProjectForm> oriProList=TeamFileNodeManager.getINSTANCE().loadProjectMCode(branchCode, storageCode);
			if(oriProList.isEmpty()){
				//fail
				responseJson.put("answer","fail");
				responseJson.put("failMsg","NO Project. Make it right Now!!");
			}else{
				//success make json
				int cnt = oriProList.size();
				//make json array data
				LoadProjectForm oriPro=null;
				for(int i=0;i<cnt;i++){
					JSONObject jobj = new JSONObject();
					
					oriPro=oriProList.get(i);
					jobj.put("id",oriPro.getId());
					jobj.put("name",oriPro.getName());
					jobj.put("description",oriPro.getDes());
					jobj.put("date",oriPro.getMakeDate());
					jobj.put("share",oriPro.getSharedList()); 
					jobj.put("status",oriPro.getStatus());
					
					jList.add(jobj);
				}
				responseJson.put("answer","success");
				responseJson.put("list",jList);
			}
			System.out.println(responseJson);
			//responseJson.put("teamList",jList);
			response.getWriter().print(responseJson.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseJson.put("answer","fail");
			responseJson.put("failMsg",e.getMessage());
			System.out.println(responseJson);
			response.getWriter().print(responseJson);
			e.printStackTrace();
		}
	}
	
	private void shareProject(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("send team cooperator msg data reach : "+json);
		if(json!=null){
			String oriProCode = (String)json.get("originProCode");
			String oriProOwnerCode = (String)json.get("originProOwnerCode");
			String memberCode = (String)json.get("memberCode");
	        
			if(new StringTokenizer(oriProOwnerCode, "_").nextToken().equals("team")){
				//if become team member
			}else{

		        //get teamList from db
		        try {
		        	ShareProjectInfo proCode= FileNodeManager.getINSTANCE().createCopiedProject(memberCode, oriProOwnerCode, oriProCode);
		        	
		        	if(proCode!=null){
						responseJson.put("answer", "success");
						responseJson.put("proCode", proCode.getProjectCode());
						responseJson.put("proName", proCode.getProjectName());
					}else {
						responseJson.put("answer","fail");
					}
					System.out.println(responseJson);
					//responseJson.put("teamList",jList);
					response.getWriter().print(responseJson.toString());
					
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

	private void searchProjectCooperators(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray list = new JSONArray();
		
		if(json!=null){
			System.out.println("searchProjectCooperators  data reach : "+json);
			
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			
			//insert Data
			try {
				List<String> cooperatorList;
				if(new StringTokenizer(storageCode, "_").nextToken().equals("team")){
					cooperatorList = TeamManager.getINSTANCE().searchTeamCode(storageCode).getCooperatorList();
				}else{
					OriginProject oriPro = FileNodeManager.getINSTANCE().searchMemberProject(memberCode, projectCode);
					cooperatorList =  oriPro.getSharedMemberList();
				}
				if(!cooperatorList.isEmpty()){
					JSONArray cooperJSONList = new JSONArray();
					for(String cooperCode : cooperatorList){
						Member mem= MemberManager.getINSTANCE().searchMemberCode(cooperCode);
						JSONObject valObj = new JSONObject();
						valObj.put("cooperatorCode", mem.getCode());
						valObj.put("cooperatorNickName", mem.getNickName());
						valObj.put("cooperatorEmail", mem.getEmail());
						valObj.put("cooperatorSchool", mem.getSchool());
						cooperJSONList.add(valObj);
					}
					responseJson.put("answer", "success");
					responseJson.put("cooperList", cooperJSONList);
					
				}else{
					responseJson.put("answer", "fail");
				}
				System.out.println("searchProjectCooperators"+responseJson);
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


