package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.AlarmManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import form.AlarmForm;
import form.LoadProjectForm;

/**
 * Servlet implementation class AlarmServlet
 */
@WebServlet(name="/AlarmServlet",urlPatterns={"/lookupAlarm","/addTeamJoinAlarm","/loadProjectAlarm","/loadUnreadAlarm","/loadNewAlarm","/saveChange","/checkTeamAlarm","/checkProjectAlarm"})
public class AlarmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        
        String action=processKind(request,response);
        System.out.println("action : " + action);
        
        if(action.equals("lookupAlarm")){
        	//check is ther any new Alarm for member
        	this.lookupAlarm(request,response);
        }else if(action.equals("addTeamJoinAlarm")){
        	//from teamManager to memberList
        	this.addTeamJoinAlarm(request,response);
        }else if (action.equals("loadNewAlarm")){
        	//load New Alarm for member
        	this.loadNewAlarm(request,response);
        }else if(action.equals("loadUnreadAlarm")){
        	//for alarmList page
        	this.loadUnreadAlarm(request,response);
        }else if(action.equals("loadProjectAlarm")){
        	//for alarmList page
        	this.loadProjectAlarm(request,response);
        }else if(action.equals("saveChange")){
        	
        	this.saveChange(request,response);
        }else if(action.equals("checkTeamAlarm")){
        	this.checkTeamAlarm(request,response);
        }else if(action.equals("checkProjectAlarm")){
        	this.checkProjectAlarm(request,response);
        }      
	}



	private void lookupAlarm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray list = new JSONArray();
		
		if(json!=null){
			System.out.println("lookupAlarm  data reach : "+json);
			
			String memberCode = (String)json.get("memberCode");
			
			//insert Data
			try {
				int res = AlarmManager.getINSTANCE().searchLookUp(memberCode);
				responseJson.put("answer", res);
				System.out.println(responseJson);
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
 

	private void addTeamJoinAlarm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("addTeamMember data reach : "+json);
		if(json!=null){	
			List<String> memList = new ArrayList<String>();
			JSONArray jsonMemList = (JSONArray)json.get("memList");
			for(int i=0;i<jsonMemList.size();i++){
				memList.add((String)((JSONObject)jsonMemList.get(i)).get("name"));
				System.out.println(memList.get(i));
			}
			String teamCode = (String)json.get("teamCode"); 
	        
	        //get teamList from db
	        try {
	        	boolean res=AlarmManager.getINSTANCE().insertTeamInviteAlarm(teamCode, memList, teamCode);

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
	
	
	private void loadNewAlarm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
		
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		
		System.out.println("addTeamMember data reach : "+json);
		if(json!=null){	
			String memberCode = (String)json.get("memberCode"); 
	        
	        //get teamList from db
	        try {
	        	Map<String,List<AlarmForm>> resMap= AlarmManager.getINSTANCE().searchNewAlarmByMCode(memberCode);
	        	List<AlarmForm> joinList = resMap.get("join");
	        	List<AlarmForm> proList = resMap.get("project");
	        	int cnt=0;
	        	if(joinList.isEmpty()){
	        		responseJson.put("joinAnswer","nothing");
	        	}else{
	        		JSONArray jList = new JSONArray();
	        		for(AlarmForm form : joinList){
	        			JSONObject tmpObj = new JSONObject();
		        		tmpObj.put("code", form.getCode());
		        		tmpObj.put("title", form.getTitle());
		        		tmpObj.put("content", form.getContent());
		        		tmpObj.put("date", form.getSendDate());
		        		jList.add(tmpObj);
	        		}
	        		responseJson.put("joinAnswer", "list");
	        		responseJson.put("join", jList);
	        		cnt=joinList.size();
	        	}
	        	
				if(proList.isEmpty()){
					responseJson.put("projectAnswer", "nothing");
				}else {
					JSONArray jList = new JSONArray();
					for(AlarmForm form : proList){
	        			JSONObject tmpObj = new JSONObject();
		        		tmpObj.put("code", form.getCode());
		        		tmpObj.put("title", form.getTitle());
		        		tmpObj.put("content", form.getContent());
		        		tmpObj.put("date", form.getSendDate());
		        		jList.add(tmpObj);
	        		}
					responseJson.put("projectAnswer", "list");
	        		responseJson.put("project", jList);
	        		cnt+=proList.size();
	        		
				}
				responseJson.put("size", cnt);
				System.out.println(responseJson);
				//responseJson.put("teamList",jList);
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
	
	
	private void loadUnreadAlarm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		

		response.setContentType("application/json;charset=UTF-8");
		
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("addTeamMember data reach : "+json);
		if(json!=null){	
			String memberCode = (String)json.get("memberCode"); 
	        
	        //get teamList from db
	        try {
	        	Map<String,List<AlarmForm>> resMap= AlarmManager.getINSTANCE().searchNewAlarmByMCode(memberCode);
	        	List<AlarmForm> joinList = resMap.get("join");
	        	List<AlarmForm> proList = resMap.get("project");
	        	int cnt=0;
	        	if(!joinList.isEmpty()){
	        		for(AlarmForm form : joinList){
	        			JSONObject tmpObj = new JSONObject();
		        		tmpObj.put("code", form.getCode());
		        		tmpObj.put("title", form.getTitle());
		        		tmpObj.put("content", form.getContent());
		        		tmpObj.put("date", form.getSendDate());
		        		jList.add(tmpObj);
	        		}
	        		cnt=joinList.size();
	        	}
	        	
				if(!proList.isEmpty()){
					for(AlarmForm form : proList){
	        			JSONObject tmpObj = new JSONObject();
		        		tmpObj.put("code", form.getCode());
		        		tmpObj.put("title", form.getTitle());
		        		tmpObj.put("content", form.getContent());
		        		tmpObj.put("date", form.getSendDate());
		        		jList.add(tmpObj);
	        		}
	        		cnt+=proList.size();
				}
				if(cnt==0)responseJson.put("result", "fail");
				else responseJson.put("list", jList);
				System.out.println(responseJson);
				//responseJson.put("teamList",jList);
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
	

	private void loadProjectAlarm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
		
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("addTeamMember data reach : "+json);
		if(json!=null){	
			String memberCode = (String)json.get("memberCode"); 
	        
	        //get teamList from db
	        try {
	        	Map<String,List<AlarmForm>> resMap= AlarmManager.getINSTANCE().searchNewAlarmByMCode(memberCode);
	        	List<AlarmForm> joinList = resMap.get("join");
	        	List<AlarmForm> proList = resMap.get("project");
	        	int cnt=0;
	        	if(!joinList.isEmpty()){
	        		for(AlarmForm form : joinList){
	        			JSONObject tmpObj = new JSONObject();
		        		tmpObj.put("code", form.getCode());
		        		tmpObj.put("title", form.getTitle());
		        		tmpObj.put("content", form.getContent());
		        		tmpObj.put("date", form.getSendDate());
		        		jList.add(tmpObj);
	        		}
	        		cnt=joinList.size();
	        	}
	        	
				if(!proList.isEmpty()){
					for(AlarmForm form : proList){
	        			JSONObject tmpObj = new JSONObject();
		        		tmpObj.put("code", form.getCode());
		        		tmpObj.put("title", form.getTitle());
		        		tmpObj.put("content", form.getContent());
		        		tmpObj.put("date", form.getSendDate());
		        		jList.add(tmpObj);
	        		}
	        		cnt+=proList.size();
				}
				if(cnt==0)responseJson.put("result", "fail");
				else responseJson.put("list", jList);
				System.out.println(responseJson);
				//responseJson.put("teamList",jList);
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

	

	private void saveChange(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		
		System.out.println("addTeamMember data reach : "+json);
		if(json!=null){	
			String memberCode = (String)json.get("memberCode"); 
			String alarmCode = (String)json.get("alarmCode"); 
	        
	        //get teamList from db
	        try {
	        	String msg= AlarmManager.getINSTANCE().modifyAlarm(memberCode, alarmCode);
	        	
	        	if(msg==null){
	        		responseJson.put("answer","fail");
	        		msg="ERROR";
	        		responseJson.put("msg",msg);
	        	}else{
	        		responseJson.put("answer", msg);
	        	}
	        	
				System.out.println(responseJson);
				//responseJson.put("teamList",jList);
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


	private void checkProjectAlarm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		
		System.out.println("addTeamMember data reach : "+json);
		if(json!=null){	
			String memberCode = (String)json.get("memberCode"); 
			String alarmCode = (String)json.get("alarmCode"); 
	        
	        //get teamList from db
	        try {
	        	String msg= AlarmManager.getINSTANCE().modifyAlarm(memberCode, alarmCode);
	        	
	        	if(msg==null){
	        		responseJson.put("answer","fail");
	        		msg="ERROR";
	        		responseJson.put("msg",msg);
	        	}else{
	        		responseJson.put("answer", msg);
	        	}
	        	
				System.out.println(responseJson);
				//responseJson.put("teamList",jList);
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

	private void checkTeamAlarm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		
		System.out.println("addTeamMember data reach : "+json);
		if(json!=null){	
			String memberCode = (String)json.get("memberCode"); 
			String alarmCode = (String)json.get("alarmCode"); 
	        
	        //get teamList from db
	        try {
	        	List<LoadProjectForm> resList= AlarmManager.getINSTANCE().joinTeamAlarm(memberCode, alarmCode);
	        	
	        	if(!resList.isEmpty()){
	        		responseJson.put("answer","success");
	        		JSONArray jList = new JSONArray();
	        		//success make json
					int cnt = resList.size();
					//make json array data
					LoadProjectForm oriPro=null;
					for(int i=0;i<cnt;i++){
						JSONObject jobj = new JSONObject();
						
						oriPro=resList.get(i);
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
					responseJson.put("list",jList.toString());
	        	}else{
	        		responseJson.put("answer", "fail");
	        	}
	        	
				System.out.println(responseJson);
				//responseJson.put("teamList",jList);
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
