package action;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import manager.TeamFileNodeManager;
import model.FileNode;
import model.Member;
import model.Team;
import model.TeamList;
import remoteAction.RemoteFolderInsertAction;
import socialProExceptions.DAOException;
import socialProExceptions.FolderException;
import socialProExceptions.ProjectException;
import socialProExceptions.TeamException;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import dao.DAO;
import dao.TeamCooperVDAO;
import dao.TeamDAO;
import dao.TeamTagsVDAO;
import dto.TeamCooperVDTO;
import dto.TeamDTO;
import dto.TeamTagsVDTO;
import factory.ActionFactory;
import factory.DAOFactory;
import factory.RemoteActionFactory;

public class TeamActionImpl extends BaseAction implements TeamSearchAction,TeamInsertAction,TeamDropAction,TeamUpdateAction{

	private String remoteRootPath = "/home/socialPro";

	public TeamActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO"));
	}

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		super.dao=(TeamDAO)dao;
	}

	@Override
	public boolean updateTeamName( String code, String newName)
			throws DAOException {
		// TODO Auto-generated method stub
		
		return ((TeamDAO)this.getDAO()).updateTeamName(code, newName);
	}

	@Override
	public boolean updateTeamDes(String code,String newDescription) throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamDAO)this.getDAO()).updateTeamDes(code, newDescription);
	}

	@Override
	public boolean updateTeamManager(String code,
			String newManager) throws DAOException {
		// TODO Auto-generated method stub
		return ((TeamDAO)this.getDAO()).updateTeamManager(code, newManager);
	}

	@Override
	public boolean updateTeamCooperators(String code,List<String> newCooperators) throws DAOException {
		// TODO Auto-generated method stub
		return new CooperatorActionImpl().updateCooperator(newCooperators, code);
	}

	@Override
	public boolean updateTeamTags(String code,
			List<String> newTags) throws DAOException {
		// TODO Auto-generated method stub
		TeamTagsVActionImpl action=new TeamTagsVActionImpl();
		List<TeamTagsVDTO> teamTagCodeList=action.searchTeamCode(code);
		int cnt=teamTagCodeList.size();
		int cntNew = newTags.size();
		List<String> realNew = new ArrayList<String>();
		int k=0;
		if(cnt==0)return false;
		for(int i=0;i<cnt;i++){
			for(int j=0;j<cntNew;j++){
				if(teamTagCodeList.get(i).getTagName().equals(newTags.get(j))){k++;}
			}
			if(k!=0){realNew.add(teamTagCodeList.get(i).getTagName());}
			k=0;
		}
		
		return new TagActionImpl().updateTag(code, realNew);
	}

	@Override
	public boolean dropTeamCode( String code)
			throws DAOException {
		// TODO Auto-generated method stub
		
		//folder, project, task, board, code delete!!!
		if(new TagActionImpl().dropTagSCode(code)<=0)return false;
		if(new CooperatorActionImpl().dropCooperSCode(code)<=0)return false;
		return ((TeamDAO)this.getDAO()).deleteTeamCode(code);
	}

	@Override
	public String insertTeam(String teamName, String manager,List<String> tagList)throws DAOException {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public String insertTeam(String teamName, String description,String manager, List<String> tagList)throws DAOException, SftpException, JSchException {
		// TODO Auto-generated method stub
		System.out.println("managerNick : "+manager);
		//0.make Team
		TeamDTO dto = new TeamDTO();
		dto.setDescription(description);
		Member mem1=((MemberSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("MemberActionImpl")).searchMemberNickName(manager);
		if(mem1==null) return null;
 		System.out.println(mem1.getCode());
		dto.setManager(mem1.getCode());
		dto.setName(teamName);
		String teamCode=((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).insertTeam(dto);
		if(teamCode==null)return null;
		synchronized (teamCode) {
			((RemoteFolderInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFolderActionImpl")).insertOriginFolder(remoteRootPath+"/"+teamCode);
			((RemoteFolderInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFolderActionImpl")).insertOriginFolder(remoteRootPath+"/"+teamCode+"/"+mem1.getCode());
		}
		
		//2. tags insert
		if(tagList.size()!=0){
			Map<String,List<String>> tagMap=((TagSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("TagActionImpl")).searchTagsExist(tagList);
			List<String> existTags = tagMap.get("exist");
			List<String> nonexistTags = tagMap.get("nonexist");
			
			//insert existed Tag in tagDetailsTB
			if(!existTags.isEmpty()){
				System.out.println("This is Exist");
				int res=((TagInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("TagActionImpl")).insertTagDetails(existTags, teamCode);
				if(res!=existTags.size())return null;
			}
			
			//insert nonExisted Tag in tagtb and tagDetails
			if(!nonexistTags.isEmpty()){
				System.out.println("This is nonExist");
				int res=((TagInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("TagActionImpl")).insertTag(nonexistTags, teamCode);
				if(res!=nonexistTags.size())return null;
			}
		}
		
		return teamCode;
		
	}
	
	public Team makeResult(TeamDTO dto, List<TeamCooperVDTO> cooperList, List<TeamTagsVDTO> teamTagList,String teamCode) throws DAOException{
		
		if(dto==null){
			dto =((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).searchTeamCode(teamCode);
		}
		if(cooperList.isEmpty()){
			cooperList=((TeamCooperVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamCooperVDAO")).searchTeamCode(teamCode);
		}
		if(teamTagList.isEmpty()){
			teamTagList=((TeamTagsVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamTagsVDAO")).searchTeamCode(teamCode);
		}
		
		//make model
		Team team = new Team(dto.getCode(), dto.getName(), dto.getDescription(), dto.getManager());
		int cnt1=cooperList.size();
		int cnt2=teamTagList.size();
		List<String> coopers = new ArrayList<String>();
		List<String> tags = new ArrayList<String>();
		if(cnt1>=cnt2){
			for(int i=0;i<cnt2;i++){
				coopers.add(cooperList.get(i).getMemberCode());
				tags.add(teamTagList.get(i).getTagName());
			}
			for(int i=cnt2;i<cnt1;i++){coopers.add(cooperList.get(i).getMemberCode());}
		}else{
			for(int i=0;i<cnt1;i++){
				coopers.add(cooperList.get(i).getMemberCode());
				tags.add(teamTagList.get(i).getTagName());
			}
			for(int i=cnt1;i<cnt2;i++){tags.add(teamTagList.get(i).getTagName());}
		}
		team.setCooperatorList(coopers);
		team.setTagList(tags);
		return team;
		
	}

	@Override
	public Team searchTeamCode(String teamCode) throws DAOException, TeamException {
		// TODO Auto-generated method stub
		
		//team_tb
		TeamDTO dto =((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).searchTeamCode(teamCode);
		if(dto==null)throw new TeamException("There is no such team");
		//teamCooper_tb
		List<TeamCooperVDTO> cooperList=((TeamCooperVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamCooperVDAO")).searchTeamCode(teamCode);
		//teamTags_view
		TeamTagsVDAO tDAO = ((TeamTagsVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamTagsVDAO"));
		if(tDAO==null)System.out.println("thsis  dDAO is null  - = -"+dto.getCode());
		List<TeamTagsVDTO> teamTagList=tDAO.searchTeamCode(dto.getCode());
		
		return makeResult(dto, cooperList, teamTagList,dto.getCode());
	}

	@Override
	public TeamList searchManagerCode(String managerCode) throws DAOException {
		// TODO Auto-generated method stub
		
		System.out.println("TeamActionImpl manager");
		TeamList teamList=new TeamList();
		
		//team_tb
		List<TeamDTO> teamDTOList=((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).searchTeamManager(managerCode);
		if(teamDTOList.isEmpty())return teamList;
		
		System.out.println(teamDTOList.get(0).getName());
		int cnt=teamDTOList.size();
		for(int i=0;i<cnt;i++){
			teamList.addTeam(this.makeResult(teamDTOList.get(i), new ArrayList<TeamCooperVDTO>(),new ArrayList<TeamTagsVDTO>(),teamDTOList.get(i).getCode()));
		}
		
		return teamList;
	}

	@Override
	public TeamList searchCooper(String cooperCode) throws DAOException {
		// TODO Auto-generated method stub
		System.out.println("TeamActionImpl searchCooper start");
		TeamList teamList=new TeamList();
		
		//teamCooper_tb
		List<TeamCooperVDTO> teamCooperDTOList=((TeamCooperVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamCooperVDAO")).searchMCode(cooperCode);
		if(teamCooperDTOList.isEmpty())return teamList;
		
		System.out.println(teamCooperDTOList.get(0).getMemberCode());
		int cnt=teamCooperDTOList.size();
		for(int i=0;i<cnt;i++){
			teamList.addTeam(this.makeResult(null,new ArrayList<TeamCooperVDTO>(),new ArrayList<TeamTagsVDTO>(), teamCooperDTOList.get(i).getTeamCode()));
		}
		
		System.out.println("TeamAction Impl : "+teamList.getList().get(0).getName());
		
		return teamList;
	}

	@Override
	public TeamList searchCooper(List<String> cooperCode) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team searchTeamName(String teamName) throws DAOException {
		// TODO Auto-generated method stub
		//team_tb
		TeamDTO dto =((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).searchTeamName(teamName);
		if(dto==null)return null;
		//teamCooper_tb
		List<TeamCooperVDTO> cooperList=((TeamCooperVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamCooperVDAO")).searchTeamCode(dto.getCode());
		//teamTags_view
		List<TeamTagsVDTO> teamTagList=((TeamTagsVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamTagsVDAO")).searchTeamCode(dto.getCode());
		
		//make model
		Team team = new Team(dto.getCode(), dto.getName(), dto.getDescription(), dto.getManager());
		int cnt1=cooperList.size();
		int cnt2=teamTagList.size();
		List<String> coopers = new ArrayList<String>();
		List<String> tags = new ArrayList<String>();
		if(cnt1>=cnt2){
			for(int i=0;i<cnt2;i++){
				coopers.add(cooperList.get(i).getMemberCode());
				tags.add(teamTagList.get(i).getTagName());
			}
			for(int i=cnt2;i<cnt1;i++){coopers.add(cooperList.get(i).getMemberCode());}
		}else{
			for(int i=0;i<cnt1;i++){
				coopers.add(cooperList.get(i).getMemberCode());
				tags.add(teamTagList.get(i).getTagName());
			}
			for(int i=cnt1;i<cnt2;i++){tags.add(teamTagList.get(i).getTagName());}
		}
		team.setCooperatorList(coopers);
		team.setTagList(tags);
		return team;
	}

	@Override
	public TeamList searchTeamDes(String description) throws DAOException {
		// TODO Auto-generated method stub
		
		TeamList teamList=new TeamList();
		//team_tb
		List<TeamDTO> dtoList =((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).searchTeamDes(description);
		if(dtoList.isEmpty())return teamList;
		
		int cnt=dtoList.size();
		for(int i=0;i<cnt;i++){
			teamList.addTeam(this.makeResult(dtoList.get(i), new ArrayList<TeamCooperVDTO>(),new ArrayList<TeamTagsVDTO>(), dtoList.get(i).getCode()));
		}
		
		return teamList;
	}

	@Override
	public TeamList searchTags(String tags) throws DAOException {
		// TODO Auto-generated method stub
		TeamList teamList=new TeamList();
		
		//teamTags_view
		List<TeamTagsVDTO> teamTagsList=((TeamTagsVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamTagsVDAO")).searchTagName(tags);
		if(teamTagsList.isEmpty())return teamList;
		
		int cnt=teamTagsList.size();
		for(int i=0;i<cnt;i++){
			teamList.addTeam(this.makeResult(null, new ArrayList<TeamCooperVDTO>(),new ArrayList<TeamTagsVDTO>(), teamTagsList.get(i).getTeamCode()));
		}
		
		return teamList;
	}

	@Override
	public TeamList searchTags(List<String> tags) throws DAOException {
		// TODO Auto-generated method stub
		TeamList teamList=new TeamList();
		
		//teamTags_view
		List<TeamTagsVDTO> teamTagsList=((TeamTagsVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamTagsVDAO")).searchTagNames(tags);
		if(teamTagsList.isEmpty())return teamList;
		
		int cnt=teamTagsList.size();
		for(int i=0;i<cnt;i++){
			teamList.addTeam(this.makeResult(null, new ArrayList<TeamCooperVDTO>(),new ArrayList<TeamTagsVDTO>(), teamTagsList.get(i).getTeamCode()));
		}
		
		return teamList;
	}

	@Override
	public TeamList searchTags(List<String> tags, String name)
			throws DAOException {
		// TODO Auto-generated method stub
		TeamList teamList=new TeamList();
		
		//team_tb
		List<TeamDTO> dtoList =((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).searchTeamNameAll(name);
		//teaTags_view
		List<TeamTagsVDTO> tagsVList=((TeamTagsVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamTagsVDAO")).searchTagNames(tags);
		
		String teamCode=null;
		int tagCnt = tagsVList.size();
		int dtoCnt = dtoList.size();
		for(int i=0;i<tagCnt;i++){
			teamCode = tagsVList.get(i).getTeamCode();
			for(int j=0;j<dtoCnt;j++){
				if(dtoList.get(j).getCode().equals(teamCode)){
					teamList.addTeam(makeResult(null, new ArrayList<TeamCooperVDTO>(),new ArrayList<TeamTagsVDTO>(), teamCode));
				}
			}
		}
		
		return teamList;
	}

	@Override
	public TeamList searchTags(List<String> tags, String name,String description) throws DAOException {
		// TODO Auto-generated method stub
		
		TeamList teamList=new TeamList();
		
		//team_tb
		List<TeamDTO> dtoList =((TeamDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamDAO")).searchTeamName(name, description);
		//teaTags_view
		List<TeamTagsVDTO> tagsVList=((TeamTagsVDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("teamTagsVDAO")).searchTagNames(tags);
		
		String teamCode=null;
		int tagCnt = tagsVList.size();
		int dtoCnt = dtoList.size();
		for(int i=0;i<tagCnt;i++){
			teamCode = tagsVList.get(i).getTeamCode();
			for(int j=0;j<dtoCnt;j++){
				if(dtoList.get(j).getCode().equals(teamCode)){
					teamList.addTeam(makeResult(null, new ArrayList<TeamCooperVDTO>(),new ArrayList<TeamTagsVDTO>(), teamCode));
				}
			}
		}
		
		return teamList;
	}

	@Override
	public TeamList searchMemberCode(String memberCode) throws DAOException {
		// TODO Auto-generated method stub
		System.out.println("TeamAction Impl Start");
		TeamList teamList=this.searchManagerCode(memberCode);
		
		teamList.addTeam(this.searchCooper(memberCode).getList());
		
		return teamList;
	}
	

	@Override
	public boolean addTeamCooperators(String teamCode, List<String> cooperatorList)throws DAOException, SftpException, JSchException {
		// TODO Auto-generated method stub
		
		//1. cooper insert
		int coopCnt = cooperatorList.size();
		System.out.println(coopCnt);
		List<String> cooperCodes = new ArrayList<String>();
		if(coopCnt!=0){
			for(int i=0;i<coopCnt;i++){
				Member mem=((MemberSearchAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("MemberActionImpl")).searchMemberNickName(cooperatorList.get(i));
				if(mem==null)return false;
				cooperCodes.add(mem.getCode());
				synchronized (cooperCodes) {
					((RemoteFolderInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFolderActionImpl")).insertOriginFolder(remoteRootPath+"/"+teamCode+"/"+mem.getCode());
				}
			}
			if(cooperCodes.isEmpty()){
				System.out.println("cooperCodes has nothing");
			}
			if(!((CooperatorInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("cooperatorActionImpl")).insertCooper(cooperCodes, teamCode)){
				System.out.println("설마여기냐...TeamAcitonImpl addTeamCooperators");
				return false;
				}
		}
		return true;
	}

	@Override
	public boolean addTeamCooperator(String teamCode, String cooperatorCode) throws DAOException, SftpException, JSchException, ParseException, FolderException, ProjectException, IOException{
		
		//1. cooper insert

		if(!((CooperatorInsertAction)ActionFactory.getACTIONFACTORY_INSTANCE().create("cooperatorActionImpl")).insertCooper(cooperatorCode, teamCode)){
			System.out.println("설마여기냐...TeamAcitonImpl addTeamCooperators");
			return false;
		}
		
		synchronized (cooperatorCode) {
			((RemoteFolderInsertAction)RemoteActionFactory.getREMOTEACTIONFACTORY_INSTANCE().create("RemoteFolderActionImpl")).insertOriginFolder(remoteRootPath+"/"+teamCode+"/"+cooperatorCode);
		}
		
		
		
		
		
		return true;
	}
	
}
