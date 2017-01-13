package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.CommitMessageManager;
import manager.CooperatorMessageManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import form.CommitMessageInfoForm;
import form.CommitMsgDetails;
import form.FileForm;
import form.MessageForm;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet(name="/MessageServlet",urlPatterns={"/sendTeamCooperatorMsg","/sendProCooperatorMsg","/loadUnReadRecMsg","/loadCommitMessage","/searchCommitMessage","/sendCommitMsg"})
public class MessageServlet extends HttpServlet {
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
        
        if(action.equals("sendTeamCooperatorMsg")){
        	sendTeamCooperatorMsg(request,response);
        }else if(action.equals("sendProCooperatorMsg")){
        	sendProCooperatorMsg(request,response);
        }else if(action.equals("loadUnReadRecMsg")){
        	loadUnReadRecMsg(request,response);
        }else if(action.equals("loadCommitMessage")){
        	this.loadCommitMessage(request,response);
        }else if(action.equals("searchCommitMessage")){
        	searchCommitMessage(request,response);
        }else if(action.equals("sendCommitMsg")){
        	sendCommitMsg(request,response);
        }
	}


	private void sendTeamCooperatorMsg(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
	        	boolean res=CooperatorMessageManager.getINSTANCE().createTeamCooperatorMessage(teamCode, memList);
				//TeamManager.getINSTANCE().addTeamMember(teamCode, memList);

				if(res){
					responseJson.put("answer", "success");
				}else {
					responseJson.put("answer","fail");
				}
				System.out.println("sendTeamCooperatorMsg"+responseJson);
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

	private void sendProCooperatorMsg(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
			String oriProCode = (String)json.get("oriProCode");
			String oriProOwnerCode = (String)json.get("oriProOwnerCode");
			String memberCode = (String)json.get("memberCode");
	        
	        //get teamList from db
	        try {
	        	boolean res=CooperatorMessageManager.getINSTANCE().createProCooperatorMessage(oriProOwnerCode, oriProCode, memberCode);
	        	
	        	if(res){
					responseJson.put("answer", "success");
				}else {
					responseJson.put("answer","fail");
				}
				System.out.println("sendProCooperatorMsg"+responseJson);
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

	private void loadUnReadRecMsg(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
			String memberCode = (String)json.get("memberCode");
	        
	        //get teamList from db
	        try {
	        	int resInt=0;
	        	//get cooper Msg
	        	List<MessageForm> res=CooperatorMessageManager.getINSTANCE().searchMemberUnReadMsgFormList(memberCode);
	        	if(!res.isEmpty())resInt++;
	        	
	        	//get commit Msg
	        	List<CommitMessageInfoForm> resMap = CommitMessageManager.getINSTANCE().searchMemberCommitMessage(memberCode,false);
	        	if(!resMap.isEmpty())resInt++;
	        	
	        	if(resInt>0){
					responseJson.put("answer", "success");
				}else {
					responseJson.put("answer","fail");
				}
				System.out.println("sendProCooperatorMsg"+responseJson);
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


	private void loadCommitMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		JSONArray jList = new JSONArray();
		System.out.println("loadCommitMessage data reach : "+json);
		if(json!=null){
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
	        
	        //get teamList from db
	        try {
	        	Map<String,List<CommitMessageInfoForm>> messageMap = new HashMap<String, List<CommitMessageInfoForm>>();
	        	if(new StringTokenizer(storageCode, "_").nextToken().equals("team")){
	        		messageMap = CommitMessageManager.getINSTANCE().searchTeamProjectCommitMessage(memberCode, storageCode, projectCode);
	        	}else{
	        		messageMap = CommitMessageManager.getINSTANCE().searchMemberProjectCommitMessage(memberCode, projectCode);
	        	}
	        	
	        	if(!messageMap.isEmpty()){
	        		List<CommitMessageInfoForm> readList = messageMap.get("read");
	        		List<CommitMessageInfoForm> unreadList = messageMap.get("unread");
	        		if(!readList.isEmpty()){
	        			JSONArray ary = new JSONArray();
	        			for(CommitMessageInfoForm form : readList){
	        				JSONObject obj = new JSONObject();
	        				obj.put("msgCode", form.getMessageCode());
	        				obj.put("msgReceiver", form.getReceiverCode());
	        				obj.put("msgReceiverNickName", form.getReceiverNickName());
	        				obj.put("msgSender", form.getSenderCode());
	        				obj.put("msgSenderNickName", form.getSenderNickName());
	        				obj.put("msgSendDate", form.getSendDate());
	        				obj.put("msgTitle", form.getTitle());
	        				ary.add(obj);
		        		}
	        			responseJson.put("commitReadMsgList", ary);
	        		}

	        		if(!unreadList.isEmpty()){
	        			JSONArray ary = new JSONArray();
	        			for(CommitMessageInfoForm form : unreadList){
	        				JSONObject obj = new JSONObject();
	        				obj.put("msgCode", form.getMessageCode());
	        				obj.put("msgReceiver", form.getReceiverCode());
	        				obj.put("msgReceiverNickName", form.getReceiverNickName());
	        				obj.put("msgSender", form.getSenderCode());
	        				obj.put("msgSenderNickName", form.getSenderNickName());
	        				obj.put("msgSendDate", form.getSendDate());
	        				obj.put("msgTitle", form.getTitle());
	        				ary.add(obj);
		        		}
	        			responseJson.put("commitMsgList", ary);
	        		}
	        		responseJson.put("answer", "success");
	        	}else {
					responseJson.put("answer","fail");
				}
				System.out.println("loadCommitMessage  :  "+responseJson);
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
	
	private void searchCommitMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String returnAction=new String();
    
    	BufferedReader db = request.getReader();
		JSONObject json = (JSONObject)JSONValue.parse(db);
		JSONObject responseJson = new JSONObject();
		System.out.println("searchCommitMessage data reach : "+json);
		if(json!=null){
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			String commitMsgCode = (String)json.get("msgCode");
	        
	        //get teamList from db
	        try {
	        	CommitMsgDetails resCommitMsgDtls = null;
	        	if(new StringTokenizer(storageCode, "_").nextToken().equals("team")){
	        		resCommitMsgDtls = CommitMessageManager.getINSTANCE().searchTeamProjectCommitMsgDetails(storageCode, memberCode, projectCode,commitMsgCode);
	        	}else{
	        		resCommitMsgDtls = CommitMessageManager.getINSTANCE().searchMemberProjectCommitMsgDetails(memberCode, projectCode,commitMsgCode);
	        	}
	        	
	        	if(resCommitMsgDtls!=null){
	        		List<FileForm> fileFormList = resCommitMsgDtls.getFileList();
	        		if(!fileFormList.isEmpty()){
	        			JSONArray ary = new JSONArray();
	        			for(FileForm form : fileFormList){
	        				JSONObject obj = new JSONObject();
	        				obj.put("commitCode", form.getCommitCode());
	        				obj.put("fileName", form.getFileName());
	        				obj.put("fileContent", form.getFileContent());
	        				obj.put("date", form.getCommitDate());
	        				obj.put("fileDiffContent", form.getFileDiffContent());
	        				obj.put("commitTitle", form.getFileCommitTitle());
	        				obj.put("commitContent", form.getFileCommitContent());
	        				ary.add(obj);
		        		}
	        			responseJson.put("commitFileList", ary);
	        		}
	        		responseJson.put("msgContent", resCommitMsgDtls.getMsgContent());
	        		responseJson.put("answer", "success");
	        		
	        	}else {
					responseJson.put("answer","fail");
				}
				System.out.println("searchCommitMessage    //  "+responseJson);
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

	private void sendCommitMsg(HttpServletRequest request,HttpServletResponse response) throws IOException {
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
			String memberCode = (String)json.get("memberCode");
			String storageCode = (String)json.get("storageCode");
			String projectCode = (String)json.get("projectCode");
			List<String> commitCodeList = (List<String>)json.get("commitCodeList");
			String commitTitle = (String)json.get("commitTitle");
			String commitContent = (String)json.get("commitContent");
	        
	        //get teamList from db
	        try {
	        	//get cooper Msg
	        	boolean res;
	        	StringTokenizer tokens = new StringTokenizer(storageCode, "_");
	        	if(tokens.nextToken().equals("team")) {
	        		String branchCode = (String)json.get("branchCode");
	        		res = CommitMessageManager.getINSTANCE().insertTeamProCommitMsg(storageCode, branchCode,memberCode, projectCode, commitTitle, commitContent, commitCodeList);
	        	}
	        	else res = CommitMessageManager.getINSTANCE().insertSharedProjectCommitMessage(memberCode, projectCode, commitTitle, commitContent, commitCodeList);
	        	
	        	if(res){
					responseJson.put("answer", "success");
				}else {
					responseJson.put("answer","fail");
				}
				System.out.println("sendProCooperatorMsg"+responseJson);
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

}
