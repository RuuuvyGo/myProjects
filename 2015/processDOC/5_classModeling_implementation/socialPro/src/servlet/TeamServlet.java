package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.TeamFileNodeManager;
import manager.TeamManager;
import model.Team;
import model.TeamList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import form.SimpleMemberForm;



/**
 * Servlet implementation class TeamServlet
 */
@WebServlet(name="/TeamServlet",urlPatterns={"/createTeam","/teamInfo","/updateTeam","/dropTeam","/searchTeamMember","/searchTeam","/loadTeam","/addTeamMember","/searchTeamManager"})
public class TeamServlet extends HttpServlet {
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
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        
        String action=processKind(request,response);
        System.out.println("action : " + action);
        
        if(action.equals("createTeam")){
        	//
        	createTeam(request,response);
        }
        else if(action.equals("searchTeam")){
        	//
        	searchTeam(request,response);
        }
        else if(action.equals("teamInfo")){
        	//
        	//signOut(request,response);
        }
        else if(action.equals("dropTeam")){
        	//
        	//signOut(request,response);
        }
        else if(action.equals("searchTeamMember")){
        	//
        	searchTeamMember(request,response);
        }
        else if(action.equals("loadTeam")){
        	//
        	loadTeam(request,response);
        }else if(action.equals("addTeamMember")){
        	
        	addTeamMember(request,response);
        }else if(action.equals("searchTeamManager")){
        	searchTeamManager(request,response);
        }
	}
	
	private void searchTeamMember(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		if(json!=null){
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			System.out.println("searchTeamMember  data reach : "+json);
			String storageCode = (String)json.get("storageCode");
			String memberCode = (String)json.get("memberCode");
			
			//get Data
			try {
				List<SimpleMemberForm> teamMember=TeamManager.getINSTANCE().searchTeamCodeForMember(storageCode, memberCode);
				System.out.println("servlet : "+storageCode);
				if(!teamMember.isEmpty()){
					//success
					responseJson.put("answer","success");
					JSONArray ary = new JSONArray();
					for(SimpleMemberForm form : teamMember){
						JSONObject obj = new JSONObject();
						obj.put("id", form.getMemberCode());
						obj.put("name", form.getMemberNickName());
						ary.add(obj);
					}
					responseJson.put("list",ary);
					
				}else{
					//fail
					responseJson.put("answer","fail");
					responseJson.put("failMsg","team name has already used....");
					
				}
				System.out.println("searchTeamMember result : "+responseJson);
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

	protected void createTeam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		System.out.println("createTeam data reach : "+json);
		if(json!=null){
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			
			String teamName = (String)json.get("teamName");
			String teamManagerName = (String)json.get("teamManager");
			String tagValues = (String)json.get("tagValues");
			StringTokenizer tokens = new StringTokenizer(tagValues,",");
			List<String> tags  = new ArrayList<String>();
			while(tokens.hasMoreTokens()){
				tags.add(tokens.nextToken());
			}
			System.out.println(tags.size());
			String teamDes = (String)json.get("teamDes");
			
			//get Data
			try {
				String teamCode=TeamManager.getINSTANCE().createTeam(teamName, teamDes, teamManagerName, tags);
				System.out.println("servlet : "+teamCode);
				if(teamCode!=null){
					//success
					responseJson.put("answer","success");
					responseJson.put("teamCode",teamCode);
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
				}else{
					//fail
					responseJson.put("answer","fail");
					responseJson.put("failMsg","team name has already used....");
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
	
	
	protected void searchTeam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		System.out.println("searchTeam data reach : "+json);
		if(json!=null){
			Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}
			
			String teamName = (String)json.get("teamName");
			String teamManager = (String)json.get("teamManager");
			String tagValues = (String)json.get("tagValues");
			StringTokenizer tokens = new StringTokenizer(tagValues,",");
			List<String> tags  = new ArrayList<String>();
			while(tokens.hasMoreTokens()){
				tags.add(tokens.nextToken());
			}
			System.out.println(tags.size());
			String teamDes = (String)json.get("teamDes");
			
			//get Data
			try {
				String teamCode=TeamManager.getINSTANCE().createTeam(teamName, teamDes, teamManager, tags);
				System.out.println("servlet : "+teamCode);
				if(teamCode!=null){
					//success
					responseJson.put("answer","success");
					System.out.println(responseJson);
					response.getWriter().print(responseJson);
				}else{
					//fail
					responseJson.put("answer","fail");
					responseJson.put("failMsg","team name has already used....");
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
	
	protected void loadTeam(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("loadTeam data reach : "+json);
		if(json!=null){
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			
			String memberCode = (String)json.get("memberCode");
			System.out.println(memberCode);
	        
	        //get teamList from db
	        try {
				TeamList teamList=TeamManager.getINSTANCE().searchTeamMCode(memberCode);
				int cnt = teamList.getList().size();
				
				//make json array data
				for(int i=0;i<cnt;i++){
					JSONObject jobj = new JSONObject();
					jobj.put("id",teamList.getList().get(i).getCode());
					jobj.put("name",teamList.getList().get(i).getName());
					System.out.println(jobj);
					jList.add(jobj);
					//for test
					System.out.println("servlet teamName"+teamList.getList().get(i).getName());
				}
				System.out.println(jList);
				//responseJson.put("teamList",jList);
				response.getWriter().print(jList.toString());
				
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
	
	
	protected void addTeamMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("addTeamMember data reach : "+json);
		if(json!=null){
			/*Iterator<String> kIt=json.keySet().iterator();
			String strKey=null;
			while(kIt.hasNext()){
				strKey=kIt.next();
				System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
			}*/
			
			List<String> memList = new ArrayList<String>();
			JSONArray jsonMemList = (JSONArray)json.get("memList");
			for(int i=0;i<jsonMemList.size();i++){
				memList.add((String)((JSONObject)jsonMemList.get(i)).get("name"));
				System.out.println(memList.get(i));
			}
			String teamCode = (String)json.get("teamCode"); 
	        
	        //get teamList from db
	        try {
				boolean res=TeamManager.getINSTANCE().addTeamMember(teamCode, memList);

				if(res){
					responseJson.put("answer", "success");
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
		else {
    		responseJson.put("answer","fail");
    		responseJson.put("failMsg","fail to Join. Try Again Please.");
    		response.getWriter().print(responseJson);
		}
	
	}
	

	private void searchTeamManager(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("searchTeamManager data reach : "+json);
		if(json!=null){
			String teamCode = (String)json.get("storageCode");
			String memberCode = (String)json.get("memberCode");
	        
	        //get teamList from db
	        try {
				Team teamInfo = TeamManager.getINSTANCE().searchTeamCode(teamCode);
				if(teamInfo.getManager().equals(memberCode)){
					responseJson.put("answer", "success");
				}else{
					responseJson.put("answer","fail");
					responseJson.put("failMsg","You can't create project at here");
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

}
