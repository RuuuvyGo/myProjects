package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.CommitMessageManager;
import manager.FileNodeManager;
import manager.MemberAlarmManager;
import manager.MemberManager;
import model.Member;

import org.json.simple.*;

import manager.CooperatorMessageManager;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import socialProExceptions.DAOException;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet(name="/MemberServlet",urlPatterns={"/signOut","/signIn","/signOn"})
public class MemberServlet extends HttpServlet {
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
		System.out.println("doPSost");
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
        
        if(action.equals("signOn")){
        	//
        	signOn(request,response);
        }
        else if(action.equals("signIn")){
        	//
        	signIn(request,response);
        }
        else if(action.equals("signOut")){
        	//
        	signOut(request,response);
        }
	}
 
	private void signOn(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
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
				String email = (String)json.get("email");
				String nickName = (String)json.get("nickName");
				String pw = (String)json.get("pw");
				String school = (String)json.get("school");
				int entranceYear = Integer.parseInt((String)json.get("entranceYear"));
				
				//get Data
				String code;
				try {
					code = MemberManager.getINSTANCE().joinMember(nickName, email, pw, school, entranceYear);
					if(code!=null){
						//success
						responseJson.put("answer","success");
						responseJson.put("successMsg","successfully registered!!!");
						System.out.println(responseJson);
						response.getWriter().print(responseJson);
					}
					else{
						//fail
						responseJson.put("answer","fail");
						responseJson.put("failMsg","This is failMsg/......");
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
	private void signIn(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		 
		 	// TODO Auto-generated method stub
		 	response.setContentType("application/json;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        String returnAction=new String();
	    
	    
	    	BufferedReader db = request.getReader();
			JSONObject json = (JSONObject)JSONValue.parse(db);
			JSONObject responseJson = new JSONObject();
			System.out.println("data reach : "+json);
			if(json!=null){
				Iterator<String> kIt=json.keySet().iterator();
				String strKey=null;
				while(kIt.hasNext()){
					strKey=kIt.next();
					System.out.println("JSON Key : "+strKey+" JSON val : "+json.get(strKey));
				}
				String email = (String)json.get("email");
				String pw = (String)json.get("pw");
				
				//get Data
				try {
					if(MemberManager.getINSTANCE().loginMember(email, pw)){
						//success
						responseJson.put("answer","success");
												
						//get memberCode and nickName
						Member model=MemberManager.getINSTANCE().searchMemberEmail(email);
						if(model!=null){
							responseJson.put("nickName",model.getNickName());
							responseJson.put("memberCode",model.getCode());
							responseJson.put("email",model.getEmail());
							
							//load Message
							MemberAlarmManager.getINSTANCE().loadNewAlarm(model.getCode());
						}
						System.out.println("HHH");
						System.out.println(responseJson);
					}else{
						//fail
						responseJson.put("answer","fail");
						responseJson.put("failMsg","error");
					}
					
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
	    		responseJson.put("failMsg","fail to login. Try Again Please.");
	    		response.getWriter().print(responseJson);
			}
		}
	 private void signOut(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		 
		 	Cookie ck1=new Cookie("nickName","");  
	        ck1.setMaxAge(0);
	        Cookie ck2=new Cookie("memberCode","");  
	        ck2.setMaxAge(0);
	        response.addCookie(ck1);
	        response.addCookie(ck2);
	 }

}
