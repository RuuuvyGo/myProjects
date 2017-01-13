package factory;

import dao.AlarmDAO;
import dao.BoardDAO;
import dao.BoardTagsVDAO;
import dao.ChattingTeamVDAO;
import dao.CommentAlarmVDAO;
import dao.CommentDAO;
import dao.CommentGroupAlarmVDAO;
import dao.CommitDAO;
import dao.CommitMessageDAO;
import dao.CommitMessageVDAO;
import dao.CooperatorDAO;
import dao.CooperatorMessageAlarmVDAO;
import dao.CooperatorMessageDAO;
import dao.CopiedFileCommitAlarmVDAO;
import dao.CopiedFileCommitVDAO;
import dao.CopiedFileVDAO;
import dao.CopiedFolderCommitAlarmVDAO;
import dao.CopiedFolderCommitVDAO;
import dao.CopiedFolderVDAO;
import dao.CopiedInfoDAO;
import dao.CopiedProjectVDAO;
import dao.DAO;
import dao.FileCommitGroupAlarmVDAO;
import dao.FileDAO;
import dao.FolderCommitGroupAlarmVDAO;
import dao.FolderDAO;
import dao.GroupAlarmDAO;
import dao.LogDAO;
import dao.LogDetailsDAO;
import dao.MemberDAO;
import dao.MergeDAO;
import dao.MessageDAO;
import dao.OriginFileCommitAlarmVDAO;
import dao.OriginFileCommitVDAO;
import dao.OriginFolderCommitAlarmVDAO;
import dao.OriginFolderCommitVDAO;
import dao.ProjectDAO;
import dao.ProjectFolderVDAO;
import dao.ProjectSharedMemVDAO;
import dao.ProjectTagsVDAO;
import dao.TagDAO;
import dao.TagDetailsDAO;
import dao.TeamCooperVDAO;
import dao.TeamDAO;
import dao.TeamTagsVDAO;

public class DAOFactory implements Factory{

	private static DAOFactory DAOFACTORY_INSTANCE;
	static{
		DAOFACTORY_INSTANCE = new DAOFactory();
	}
	private DAOFactory(){
		//if(DAOFactory.DAOFACTORY_INSTANCE==null){DAOFactory.DAOFACTORY_INSTANCE=new DAOFactory();}
	}
	public static DAOFactory getDAOFACTORY_INSTANCE(){
		return DAOFactory.DAOFACTORY_INSTANCE;
	} 
	@Override
	public DAO create(String name) {
		// TODO Auto-generated method stub
		if(name.equals("memberDAO")) {return createMemberDAO();}
		else if(name.equals("teamDAO")) {return createTeamDAO();}
		else if(name.equals("CooperDAO")) {return createCooperDAO();}
		else if(name.equals("TagDAO")) {return createTagDAO();}
		else if(name.equals("TagDetailsDAO")) {return createTagDetailsDAO();}
		else if(name.equals("alarmDAO")) {return createAlarmDAO();}
		else if(name.equals("GroupAlarmDAO")) {return createGroupAlarmDAO();}
		else if(name.equals("boardDAO")) {return createBoardDAO();}
		else if(name.equals("chattingTeamVDAO")) {return createChattingTeamVDAO();}
		else if(name.equals("CommentDAO")){return createCommentDAO();}
		else if(name.equals("CommentAlarmVDAO")){return createCommentAlarmVDAO();}
		else if(name.equals("CommentAlarmGroupVDAO")){return createCommentGroupAlarmVDAO();}
		
		else if(name.equals("commitDAO")){return createCommitDAO();}
		else if(name.equals("CommitMessageDAO")){return createCommitMessageDAO();}
		else if(name.equals("CommitMessageVDAO")){return createCommitMessageVDAO();}
		
		else if(name.equals("cooperatorDAO")){return createCooperatorDAO();}
		else if(name.equals("CooperatorMessageDAO")){return createCooperatorMessageDAO();}
		else if(name.equals("CooperatorMessageAlarmVDAO")){return createCooperatorMessageAlarmVDAO();}
		
		else if(name.equals("copiedFileVDAO")){return createCopiedFileVDAO();}
		else if(name.equals("CopiedFileCommitVDAO")){return createCopiedFileCommitVDAO();}
		else if(name.equals("OriginFileCommitVDAO")){return createOriginFileCommitVDAO();}
		
		else if(name.equals("copiedFolderVDAO")){return createCopiedFolderVDAO();}
		else if(name.equals("CopiedFolderCommitVDAO")){return createCopiedFolderCommitVDAO();}
		else if(name.equals("OriginFolderCommitVDAO")){return createOriginFolderCommitVDAO();}
		
		else if(name.equals("copiedInfoDAO")){return createCopiedInfoDAO();}
		
		else if(name.equals("copiedProjectVDAO")){return createCopiedProjectVDAO();}
		
		else if(name.equals("fileDAO")){return createFileDAO();}
		else if(name.equals("FileCommitGroupAlarmVDAO")){return createFileCommitGroupAlarmVDAO();}
		else if(name.equals("OriginFileCommitAlarmVDAO")){return createOriginFileCommitAlarmVDAO();}
		else if(name.equals("CopiedFileCommitAlarmVDAO")){return createCopiedFileCommitAlarmVDAO();}
		
		else if(name.equals("folderDAO")){return createFolderDAO();}
		else if(name.equals("FolderCommitGroupAlarmVDAO")){return createFolderCommitGroupAlarmVDAO();}
		else if(name.equals("OriginFolderCommitAlarmVDAO")){return createOriginFolderCommitAlarmVDAO();}
		else if(name.equals("CopiedFolderCommitAlarmVDAO")){return createCopiedFolderCommitAlarmVDAO();}
		
		else if(name.equals("logDetailsDAO")){return createLogDetailsDAO();}
		else if(name.equals("logDAO")){return createLogDAO();}
		else if(name.equals("MergeDAO")){return createMergeDAO();}
		else if(name.equals("memberDAO")){return createMemberDAO();}
		else if(name.equals("MessageDAO")){return createMessageDAO();}
		else if(name.equals("projectDAO")){return createProjectDAO();}
		else if(name.equals("projectFolderVDAO")){return createProjectFolderVDAO();}
		else if(name.equals("projectSharedMemVDAO")){return createProjectSharedMemVDAO();}
		else if(name.equals("projectTagsVDAO")){return createProjectTagsVDAO();}
		else if(name.equals("teamCooperVDAO")){return createTeamCooperVDAO();}
		else if(name.equals("teamDAO")){return createTeamDAO();}
		else if(name.equals("teamTagsVDAO")){return createTeamTagsVDAO();}
		else if(name.equals("boardTagsVDAO")){return createBoardTagsVDAO();}
		return null;
	}
	
	private DAO createMergeDAO(){
		return (DAO)new MergeDAO();
	}
	private DAO createMemberDAO(){
		return (DAO)new MemberDAO();
	}
	private DAO createTeamDAO(){
		return (DAO)new TeamDAO();
	}
	private DAO createCooperDAO(){
		return (DAO)new CooperatorDAO();
	}
	private DAO createTagDAO(){
		return (DAO)new TagDAO();
	}
	private DAO createTagDetailsDAO(){
		return (DAO)new TagDetailsDAO();
	}
	private DAO createAlarmDAO(){
		return (DAO)new AlarmDAO();
	}
	private DAO createGroupAlarmDAO(){
		return (DAO)new GroupAlarmDAO();
	}
	private DAO createBoardDAO(){
		return (DAO)new BoardDAO();
	}
	private DAO createBoardTagsVDAO(){
		return (DAO)new BoardTagsVDAO();
	}
	private DAO createChattingTeamVDAO(){
		return (DAO)new ChattingTeamVDAO();
	}
	private DAO createCommentDAO(){
		return (DAO)new CommentDAO();
	}
	private DAO createCommentAlarmVDAO(){
		return (DAO)new CommentAlarmVDAO();
	}
	private DAO createCommentGroupAlarmVDAO(){
		return (DAO)new CommentGroupAlarmVDAO();
	}
	private DAO createCommitDAO(){
		return (DAO)new CommitDAO();
	}
	private DAO createCommitMessageDAO(){
		return (DAO)new CommitMessageDAO();
	}
	private DAO createCommitMessageVDAO(){
		return (DAO)new CommitMessageVDAO();
	}
	private DAO createCooperatorDAO(){
		return (DAO)new CooperatorDAO();
	}
	private DAO createCooperatorMessageDAO(){
		return (DAO)new CooperatorMessageDAO();
	}
	private DAO createCooperatorMessageAlarmVDAO(){
		return (DAO)new CooperatorMessageAlarmVDAO();
	}
	private DAO createCopiedFileVDAO(){
		return (DAO)new CopiedFileVDAO();
	}
	private DAO createCopiedFileCommitVDAO(){
		return (DAO)new CopiedFileCommitVDAO();
	}
	private DAO createOriginFileCommitVDAO(){
		return (DAO)new OriginFileCommitVDAO();
	}
	private DAO createCopiedFolderVDAO(){
		return (DAO)new CopiedFolderVDAO();
	}
	private DAO createCopiedFolderCommitVDAO(){
		return (DAO)new CopiedFolderCommitVDAO();
	}
	private DAO createOriginFolderCommitVDAO(){
		return (DAO)new OriginFolderCommitVDAO();
	}
	private DAO createCopiedInfoDAO(){
		return (DAO)new CopiedInfoDAO();
	}
	private DAO createCopiedProjectVDAO(){
		return (DAO)new CopiedProjectVDAO();
	}
	private DAO createFileDAO(){
		return (DAO)new FileDAO();
	}
	private DAO createFileCommitGroupAlarmVDAO(){
		return (DAO)new FileCommitGroupAlarmVDAO();
	}
	private DAO createOriginFileCommitAlarmVDAO(){
		return (DAO)new OriginFileCommitAlarmVDAO();
	}
	private DAO createCopiedFileCommitAlarmVDAO(){
		return (DAO)new CopiedFileCommitAlarmVDAO();
	}
	private DAO createFolderDAO(){
		return (DAO)new FolderDAO();
	}
	private DAO createFolderCommitGroupAlarmVDAO(){
		return (DAO)new FolderCommitGroupAlarmVDAO();
	}
	private DAO createOriginFolderCommitAlarmVDAO(){
		return (DAO)new OriginFolderCommitAlarmVDAO();
	}
	private DAO createCopiedFolderCommitAlarmVDAO(){
		return (DAO)new CopiedFolderCommitAlarmVDAO();
	}
	private DAO createLogDetailsDAO(){
		return (DAO)new LogDetailsDAO();
	}
	private DAO createLogDAO(){
		return (DAO)new LogDAO();
	}
	private DAO createMessageDAO(){
		return (DAO)new MessageDAO();
	}
	private DAO createProjectDAO(){
		return (DAO)new ProjectDAO();
	}
	private DAO createProjectFolderVDAO(){
		return (DAO)new ProjectFolderVDAO();
	}
	private DAO createProjectSharedMemVDAO(){
		return (DAO)new ProjectSharedMemVDAO();
	}
	private DAO createProjectTagsVDAO(){
		return (DAO)new ProjectTagsVDAO();
	}
	private DAO createTeamCooperVDAO(){
		return (DAO)new TeamCooperVDAO();
	}
	private DAO createTeamTagsVDAO(){
		return (DAO)new TeamTagsVDAO();
	}
	
}
