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

import manager.AlarmManager;
import manager.CommitInfoManager;
import manager.FileNodeManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import form.CommitForm;

/**
 * Servlet implementation class CommitServlet
 */
@WebServlet(name="/CommitServlet",urlPatterns={"/searchCommitList","/getCommitContent"})
public class CommitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
        
        if(action.equals("searchCommitList")){
        	//check is ther any new Alarm for member
        	this.searchCommitList(request,response);
        }else if(action.equals("getCommitContent")){
        	this.getCommitContent(request,response);
        }
	}

	private void searchCommitList(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
			
			//insert Data
			try {
				Map<String, List<CommitForm>> resMap;
				if(new StringTokenizer(storageCode, "_").nextToken().equals("team")){
					String branchCode = (String)json.get("branchCode");
					resMap = CommitInfoManager.getINSTANCE().searchTeamProjectCommit(storageCode,branchCode, projectCode);
				}else{
					resMap = CommitInfoManager.getINSTANCE().searchMemberProjectCommit(memberCode, projectCode);
				}
				if(!resMap.isEmpty()){
					List<CommitForm> mergeList = resMap.get("merge");
					List<CommitForm> commitFormList = resMap.get("notmerge");
					
					System.out.println("merged list : "+mergeList.size()+"    ** commitList : "+commitFormList.size());
					
					if(!commitFormList.isEmpty()){
						for(CommitForm form : commitFormList){
							JSONObject valObj = new JSONObject();
							valObj.put("commiter", form.getCommiter());
							valObj.put("commiterNickName", form.getCommiterNickName());
							valObj.put("commitCode", form.getCommitCode());
							valObj.put("title", form.getTitle());
							valObj.put("commitDes", form.getCommitContent());
							valObj.put("date", form.getInsertDate());
							commitJSONList.add(valObj);
						}
					}else System.out.println("commit list is empty :.............");
					if(!mergeList.isEmpty()){
						for(CommitForm form : mergeList){
							JSONObject valObj = new JSONObject();
							valObj.put("commiter", form.getCommiter());
							valObj.put("commiterNickName", form.getCommiterNickName());
							valObj.put("commitCode", form.getCommitCode());
							valObj.put("title", form.getTitle());
							valObj.put("commitDes", form.getCommitContent());
							valObj.put("date", form.getInsertDate());
							System.out.println(valObj);
							mergeJSONList.add(valObj);
						}
					}else System.out.println("merge list is empty :.............");
					
					responseJson.put("answer", "success");
					responseJson.put("commitList", commitJSONList);
					responseJson.put("mergeList", mergeJSONList);
					
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

	private void getCommitContent(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray list = new JSONArray();
		
		if(json!=null){
			System.out.println("getCommitContent  data reach : "+json);
			
			String commitCode = (String)json.get("commitCode");
			
			//insert Data
			try {
				String diffContent= CommitInfoManager.getINSTANCE().searchCommitDescription(commitCode);
				
				if(diffContent!=null){
					responseJson.put("answer", "success");
					responseJson.put("diffContent", diffContent);
					
				}else{
					responseJson.put("answer", "fail");
				}
				System.out.println("getCommitContent  : "+responseJson);
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
