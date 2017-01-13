package manager;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import model.Member;
import model.Team;
import model.TeamList;
import action.MemberSearchAction;
import action.TeamCooperVActionImpl;
import action.TeamCooperVSearchAction;
import action.TeamInsertAction;
import action.TeamSearchAction;
import action.TeamUpdateAction;
import socialProExceptions.DAOException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;
import socialProExceptions.TeamException;
import factory.ActionFactory;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : TeamDBManager.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class TeamDBManager {
	
	private static TeamDBManager INSTANCE;
	
	static{
		INSTANCE = new TeamDBManager();
	}
	
	private TeamDBManager(){}	
	
	public static TeamDBManager getINSTANCE() {
		if(INSTANCE==null)INSTANCE=new TeamDBManager();
		return INSTANCE;
	}

	public String insertTeam(String teamName, String description, String manager,  List<String> tagList) throws DAOException, SftpException, JSchException {
	
		/*Member mem = ((MemberSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("MemberActionImpl")).searchMemberNickName(manager);
		if(mem==null){
			return null;
		}*/
		return ((TeamInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).insertTeam(teamName, description, manager,  tagList);
	}
	
	public String insertTeam(String teamName, String manager,  List<String> tagList) throws DAOException, SftpException, JSchException {
		return this.insertTeam(teamName,"", manager, tagList);
	}
	
// =============================================================================================================================
	
	public boolean checkTeamName(String name) throws DAOException {
		Team res=((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTeamName(name);
		if(res==null)return false;
		return true;
	}
	
	public boolean checkTeamManagerNickName(String teamName,String nickName) throws DAOException {
		Team team=((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTeamName(teamName);
		if(team==null)return false;
		
		Member mem=((MemberSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("MemberActionImpl")).searchMemberNickName(nickName);
		if(team.getManager().equals(mem.getCode()))return true;
		return false;
	}
	public boolean checkTeamManagerCode(String teamName,String memberCode) throws DAOException {
		
		Team team=((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTeamName(teamName);
		if(team==null)return false;
		
		if(team.getManager().equals(memberCode))return true;
		return false;
	}
	
	public boolean checkTeamCodeManagerNickName(String teamCode,String nickName) throws DAOException, TeamException {
		
		Team team=((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTeamCode(teamCode);
		if(team==null)return false;
		
		Member mem=((MemberSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("MemberActionImpl")).searchMemberNickName(nickName);
		if(team.getManager().equals(mem.getCode()))return true;
		return false;
	}
	public boolean checkTeamCodeManagerCode(String teamCode,String memberCode) throws DAOException, TeamException {
		Team team=((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTeamCode(teamCode);
		if(team==null)return false;
		
		if(team.getManager().equals(memberCode))return true;
		return false;
	}
	
	public List<String> searchCooperators(String teamName) throws DAOException {
	
		List<String> cooperIdList = new ArrayList<String>();
		Team team=((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTeamName(teamName);
		
		int cnt=team.getCooperatorList().size();
		if(cnt==0)return cooperIdList;
		for(int i=0;i<cnt;i++){
			cooperIdList.add(((MemberSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("MemberActionImpl")).searchMemberCode(team.getCooperatorList().get(i)).getNickName());
		}
		return cooperIdList;
	}
	
	public boolean checkCooperID(String teamName, String cooperID) throws DAOException {
	
		Team team=((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTeamName(teamName);
		if(team==null)return false;
		List<String> cooperList = team.getCooperatorList();
		int cnt=cooperList.size();
		for(int i=0;i<cnt;i++){
			if(cooperList.get(i).equals(cooperID))return true;
		}return false;
	}
	
	public boolean checkCooperNickName(String teamName, String cooperNickName) throws DAOException {
		
		Team team=((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTeamName(teamName);
		if(team==null)return false;
		List<String> cooperList = team.getCooperatorList();
		int cnt=cooperList.size();
		String nickName;
		for(int i=0;i<cnt;i++){
			nickName=((MemberSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("MemberActionImpl")).searchMemberCode(team.getCooperatorList().get(i)).getNickName();
			if(nickName.equals(cooperNickName))return true;
		}return false;
	}
	
	public TeamList searchTeam(String memberCode)throws DAOException{
		
		return ((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchMemberCode(memberCode);
		
	}
	
	public Team searchTeamCode(String teamCode)throws DAOException, TeamException{
		
		return ((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTeamCode(teamCode);
		
	}
	
	public TeamList searchTeamTags(List<String> tagList) throws DAOException {
	
		return ((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTags(tagList);
	}
	
	public TeamList searchTeamTags(List<String> tagList, String name, String description) throws DAOException {
	
		return ((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchTags(tagList, name, description);
	}
	
	public TeamList searchTeamManager(String manager) throws DAOException {
	
		return ((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchManagerCode(manager);
	}
	
	public TeamList searchTeamCooper(String cooperCode)throws DAOException{
		TeamList list=((TeamSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).searchCooper(cooperCode);
		
		return list;
	}
	
// =============================================================================================================================
	
	public boolean addTeamMember(String teamCode, List<String> memberList) throws DAOException, SftpException, JSchException{
		return ((TeamUpdateAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).addTeamCooperators(teamCode, memberList);
	}
	
	public boolean addTeamMember(String teamCode, String cooperatorCode) throws DAOException, SftpException, JSchException, ParseException, FolderException, ProjectException, IOException{
		return ((TeamUpdateAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("teamActionImpl")).addTeamCooperator(teamCode, cooperatorCode);
	}
	
	/*public boolean deleteTeamCode(String nickName, String code) {
	
	}
	
	public boolean modifyTeamName(String nickName, String code, String newName) {
	
	}
	
	public boolean modifyTeamDes(String nickName, String code, String newDescription) {
		
	}
	
	public boolean modifyTeamManager(String nickName, String code, String newManager) {
		
	}
	public boolean modifyTeamCooperators(String nickName,String code, List<String> newCooperators) {
		
	}*/
}
