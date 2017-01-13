package action;

import java.util.List;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import remoteAction.RemoteFolderInsertAction;
import model.Member;
import model.MemberList;
import dao.DAO;
import socialProExceptions.DAOException;
import socialProExceptions.MemberException;
import dao.MemberDAO;
import dto.EnrollMemberDTO;
import dto.MemberDTO;
import factory.DAOFactory;
import factory.RemoteActionFactory;

public class MemberActionImpl extends BaseAction implements MemberSearchAction,MemberInsertAction,MemberDropAction,MemberUpdateAction{

	private String remoteRootPath="/home/socialPro/";
	public MemberActionImpl() {
		// TODO Auto-generated constructor stub
		System.out.println("getDAO");
		this.setDAO((MemberDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("memberDAO"));
	}
	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		super.dao=(MemberDAO)dao;
	}
	@Override
	public boolean modifyMemberNickName(String code, String newNickName,
			String newPw) throws DAOException {
		// TODO Auto-generated method stub
		return ((MemberDAO)super.getDAO()).modifyMemberNickName(code, newNickName, newPw);
	}
	@Override
	public boolean modifyMemberNickName(String code, String newNickName)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((MemberDAO)super.getDAO()).modifyMemberNickName(code, newNickName);
	}
	@Override
	public boolean deleteMember(String code) throws DAOException {
		// TODO Auto-generated method stub
		return ((MemberDAO)super.getDAO()).deleteMember(code);
	}
	@Override
	public boolean deleteMember(String email, String pw) throws DAOException {
		// TODO Auto-generated method stub
		return ((MemberDAO)super.getDAO()).deleteMember(email, pw);
	}
	@Override
	public Member insertMember(Member model) throws DAOException, SftpException, JSchException, MemberException {
		// TODO Auto-generated method stub
		EnrollMemberDTO dto=new EnrollMemberDTO(model.getNickName(), model.getEmail(), model.getPw(),model.getSchool(),model.getEntranceYear());
		String memberCode = ((MemberDAO)super.getDAO()).insertMember(dto);
		if(memberCode!=null){
			synchronized (dto) {
				System.out.println(remoteRootPath+memberCode);
				((RemoteFolderInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFolderActionImpl")).insertOriginFolder(remoteRootPath+memberCode);
			}
			model.setCode(memberCode);
			return model;
		}
		else throw new MemberException("Error Insert MemberInfo");
	}
	
	@Override
	public Member searchMemberCode(String code) throws DAOException {
		// TODO Auto-generated method stub
		MemberDTO dto=((MemberDAO)super.getDAO()).searchMemberCode(code);
		return new Member(dto.getCode(),dto.getNickName(), dto.getEmail(), dto.getPw(), dto.getSchool(),dto.getEntranceYear());
	}
	@Override
	public Member searchMemberNickName(String nickName)
			throws DAOException {
		// TODO Auto-generated method stub
		MemberDTO dto=((MemberDAO)super.getDAO()).searchMemberNickName(nickName);
		if(dto==null)return null;		
		return new Member(dto.getCode(), dto.getNickName(), dto.getEmail(), dto.getPw(), dto.getSchool(), dto.getEntranceYear());
	}
	@Override
	public Member searchMemberEmail(String email) throws DAOException {
		// TODO Auto-generated method stub
		System.out.println("This is MemberActionImpl searchMemberEmail");
		MemberDTO dto=((MemberDAO)super.getDAO()).searchMemberEmail(email);
		if(dto==null)System.out.println("actionImpl Search Email null");
		if(dto==null)return null;
		return new Member(dto.getCode(),dto.getNickName(), dto.getEmail(), dto.getPw(), dto.getSchool(),dto.getEntranceYear());
	}
	@Override
	public Member searchMemberLoginInfo(String email, String pw)
			throws DAOException {
		// TODO Auto-generated method stub
		MemberDTO dto=((MemberDAO)super.getDAO()).searchMemberLoginInfo(email, pw);
		System.out.println(dto.getCode());
		return new Member(dto.getCode(),dto.getNickName(), dto.getEmail(), dto.getPw(), dto.getSchool(),dto.getEntranceYear());
	}
	@Override
	public MemberList searchMemberSchool(String school)
			throws DAOException {
		// TODO Auto-generated method stub
		List<MemberDTO> dtoList=((MemberDAO)super.getDAO()).searchMemberSchool(school);
		int i=0; 
		int cnt=dtoList.size();
		MemberList list = new MemberList();
		MemberDTO dto = null;
		for(i=0;i<cnt;i++){
			dto=dtoList.get(i);
			list.addMember(new Member(dto.getCode(),dto.getNickName(), dto.getEmail(), dto.getPw(), dto.getSchool(),dto.getEntranceYear()));
		}
		return list;
	}
	@Override
	public MemberList searchMemberSchool(String school, int entranceYear)
			throws DAOException {
		// TODO Auto-generated method stub
		List<MemberDTO> dtoList=((MemberDAO)super.getDAO()).searchMemberSchool(school, entranceYear);
		int i=0; 
		int cnt=dtoList.size();
		MemberList list = new MemberList();
		MemberDTO dto = null;
		for(i=0;i<cnt;i++){
			dto=dtoList.get(i);
			list.addMember(new Member(dto.getCode(),dto.getNickName(), dto.getEmail(), dto.getPw(), dto.getSchool(),dto.getEntranceYear()));
		}
		return list;
	}
	@Override
	public MemberList searchMemberEntranceYear(int entranceYear)
			throws DAOException {
		// TODO Auto-generated method stub
		List<MemberDTO> dtoList=((MemberDAO)super.getDAO()).searchMemberEntranceYear(entranceYear);
		int i=0; 
		int cnt=dtoList.size();
		MemberList list = new MemberList();
		MemberDTO dto = null;
		for(i=0;i<cnt;i++){
			dto=dtoList.get(i);
			list.addMember(new Member(dto.getCode(),dto.getNickName(), dto.getEmail(), dto.getPw(), dto.getSchool(),dto.getEntranceYear()));
		}
		return list;
	}

	
	
}
