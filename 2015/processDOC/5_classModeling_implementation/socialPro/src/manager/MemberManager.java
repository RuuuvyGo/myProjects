package manager;

import java.util.HashMap;
import java.util.Map;

import model.Member;
import socialProExceptions.DAOException;
import socialProExceptions.MemberException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : MemberManager.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class MemberManager implements MemberJoinable, MemberLoginable, MemberSearchable, MemberDropable, MemberModifiable{

	private static MemberManager INSTANCE;
	private Map<String,Member> memberMap;
	
	static{
		INSTANCE = new MemberManager();
	}
	
	private MemberManager(){
		this.memberMap = new HashMap<String, Member>();
	}
	
	public static MemberManager getINSTANCE() {
		if(INSTANCE==null)INSTANCE = new MemberManager();
		return INSTANCE;
	}

	@Override
	public boolean modifyMember(String code,String nickName, String email, String pw,String school, int entranceYear) throws DAOException{
		// TODO Auto-generated method stub

		return MemberDBManager.getINSTANCE().modifyMember(code, nickName);
	}

	@Override
	public boolean dropMember(String email, String pw) throws DAOException{
		// TODO Auto-generated method stub
		for(Member m : this.memberMap.values()){
			if(m.getEmail().equals(email)){
				if(m.getPw().equals(pw)){
					this.memberMap.remove(m.getCode());
				}
			}
		}
		return MemberDBManager.getINSTANCE().deleteMember(email, pw);
	}
	
	@Override
	public boolean dropMember(String code) throws DAOException{
		// TODO Auto-generated method stub
		if(this.memberMap.containsKey(code)){
			this.memberMap.remove(code);
		}
		return MemberDBManager.getINSTANCE().dropMember(code);
	}

	@Override
	public Member searchMemberNickName(String nickName) throws DAOException, MemberException{
		// TODO Auto-generated method stub
		
		for(Member m : this.memberMap.values()){
			if(m.getNickName().equals(nickName)){
				return m;
			}
		}
		Member mem=MemberDBManager.getINSTANCE().searchMemberNickName(nickName);
		if(mem==null)throw new MemberException("Error search Member");
		this.memberMap.put(mem.getCode(), mem);
		return mem;
	}

	@Override
	public Member searchMemberEmail(String email) throws DAOException, MemberException{
		// TODO Auto-generated method stub
		if(!this.memberMap.isEmpty()){
			for(Member m : this.memberMap.values()){
				System.out.println(m.getCode());
				System.out.println(m.getEmail());
				if(m.getEmail().equals(email)){
					return m;
				}
			}
		}
		
		Member mem=MemberDBManager.getINSTANCE().searchMemberEmail(email);
		if(mem==null)throw new MemberException("Error search Member");
		this.memberMap.put(mem.getCode(), mem);
		return mem;
	}
	
	
	@Override
	public Member searchMemberCode(String code) throws DAOException, MemberException{
		// TODO Auto-generated method stub
		if(!this.memberMap.containsKey(code)){
			Member mem=MemberDBManager.getINSTANCE().searchMemberCode(code);
			if(mem==null)throw new MemberException("Error search Member");
			this.memberMap.put(mem.getCode(), mem);
			return mem;
		}
		return this.memberMap.get(code);
	}

	@Override
	public Member searchMemberEmail(String email, String pw) throws DAOException, MemberException{
		// TODO Auto-generated method stub

		for(Member m : this.memberMap.values()){
			if(m.getEmail().equals(email)){
				if(m.getPw().equals(pw)){
					return m;
				}
			}
		}
		Member mem=MemberDBManager.getINSTANCE().searchMemberEmail(email, pw);
		if(mem==null)throw new MemberException("Error search Member");
		this.memberMap.put(mem.getCode(), mem);
		return mem;
	}

	@Override
	public boolean loginMember(String email, String pw) throws DAOException, MemberException{
		// TODO Auto-generated method stub
		Member mem = MemberDBManager.getINSTANCE().checkLoginInfo(email, pw);
		if(mem!=null){
			this.memberMap.put(mem.getCode(), mem);
			return true;
		}
		return false;
	}

	@Override
	public String joinMember(String nickName, String email, String pw,
			String school, int entranceYear) throws DAOException, SftpException, JSchException, MemberException {
		// TODO Auto-generated method stub

		for(Member mem : this.memberMap.values()){
			if(mem.getEmail().equals(email))throw new MemberException("This Email has already used");
			if(mem.getNickName().equals(nickName))throw new MemberException("This Nick Name has already used");
		}
		
		Member model=MemberDBManager.getINSTANCE().joinMember(nickName, email, pw, school, entranceYear);
		if(model!=null){
			this.memberMap.put(model.getCode(), model);
			return model.getCode();
		}
		return null;
	}
	
	
	
}
