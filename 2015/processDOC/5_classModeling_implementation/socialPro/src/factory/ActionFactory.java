package factory;

import action.Action;
import action.AlarmActionImpl;
import action.CommentActionImpl;
import action.CommitActionImpl;
import action.CommitMessageActionImpl;
import action.CooperatorActionImpl;
import action.CooperatorMessageActionImpl;
import action.CopiedFileVActionImpl;
import action.CopiedFolderVActionImpl;
import action.CopiedInfoActionImpl;
import action.CopiedProjectVActionImpl;
import action.FileActionImpl;
import action.FolderActionImpl;
import action.GroupAlarmActionImpl;
import action.MemberActionImpl;
import action.MessageActionImpl;
import action.ProjectActionImpl;
import action.ProjectSharedMemVActionImpl;
import action.ProjectTagsVActionImpl;
import action.TagActionImpl;
import action.TeamActionImpl;
import action.TeamCooperVActionImpl;
import action.TeamTagsVActionImpl;

public class ActionFactory implements Factory{

	private static ActionFactory ACTIONFACTORY_INSTANCE;
	static{
		ACTIONFACTORY_INSTANCE = new ActionFactory();
	}
	private ActionFactory(){
		//if(ActionImplFactory.ACTIONFACTORY_INSTANCE==null){ActionImplFactory.ACTIONFACTORY_INSTANCE=new ActionImplFactory();}
	}
	public static ActionFactory getACTIONFACTORY_INSTANCE(){
		return ActionFactory.ACTIONFACTORY_INSTANCE;
	} 
	
	@Override
	public Object create(String name) {
		// TODO Auto-generated method stub
		if(name.equals("MemberActionImpl")){return createMemberActionImpl();}
		else if(name.equals("TeamActionImpl")){return createTeamActionImpl();}
		
		else if(name.equals("TagActionImpl")) {return createTagActionImpl();}
		else if(name.equals("alarmActionImpl")) {return createAlarmActionImpl();}
		else if(name.equals("GroupAlarmActionImpl")) {return createGroupAlarmActionImpl();}
		
		else if(name.equals("commentInfoActionImpl")){return createCommentInfoActionImpl();}
		else if(name.equals("commitActionImpl")){return createCommitActionImpl();}
		else if(name.equals("commitMessageActionImpl")){return createCommitMessageActionImpl();}
		
		else if(name.equals("CooperActionImpl")){return createCooperActionImpl();}
		else if(name.equals("cooperatorActionImpl")){return createCooperatorActionImpl();}
		else if(name.equals("cooperatorMessageActionImpl")){return createCooperatorMessageActionImpl();}
		
		else if(name.equals("copiedFileVActionImpl")){return createCopiedFileVActionImpl();}
		else if(name.equals("copiedFolderVActionImpl")){return createCopiedFolderVActionImpl();}
		else if(name.equals("copiedInfoActionImpl")){return createCopiedInfoActionImpl();}
		else if(name.equals("copiedProjectVActionImpl")){return createCopiedProjectVActionImpl();}
		else if(name.equals("fileActionImpl")){return createFileActionImpl();}
		else if(name.equals("folderActionImpl")){return createFolderActionImpl();}
		else if(name.equals("memberActionImpl")){return createMemberActionImpl();}
		else if(name.equals("messageActionImpl")){return createMessageActionImpl();}
		else if(name.equals("projectActionImpl")){return createProjectActionImpl();}
		else if(name.equals("projectSharedMemVActionImpl")){return createProjectSharedMemVActionImpl();}
		else if(name.equals("projectTagsVActionImpl")){return createProjectTagsVActionImpl();}
		else if(name.equals("teamCooperVActionImpl")){return createTeamCooperVActionImpl();}
		else if(name.equals("teamActionImpl")){return createTeamActionImpl();}
		else if(name.equals("teamTagsVActionImpl")){return createTeamTagsVActionImpl();}
		return null;
	}

	private Action createMemberActionImpl(){
		return (Action)new MemberActionImpl();
	}
	private Action createTeamActionImpl(){
		return (Action)new TeamActionImpl();
	}
	private Action createCooperActionImpl(){
		return (Action)new CooperatorActionImpl();
	}
	private Action createTagActionImpl(){
		return (Action)new TagActionImpl();
	}
	
	private Action createAlarmActionImpl(){
		return (Action)new AlarmActionImpl();
	}

	private Action createGroupAlarmActionImpl(){
		return (Action)new GroupAlarmActionImpl();
	}
	private Object createCooperatorMessageActionImpl() {
		// TODO Auto-generated method stub
		return (Action)new CooperatorMessageActionImpl();
	}
	private Object createCommitMessageActionImpl() {
		// TODO Auto-generated method stub
		return (Action)new CommitMessageActionImpl();
	}
	private Action createCommentInfoActionImpl(){
		return (Action)new CommentActionImpl();
	}
	private Action createCommitActionImpl(){
		return (Action)new CommitActionImpl();
	}
	private Action createCooperatorActionImpl(){
		return (Action)new CooperatorActionImpl();
	}
	private Action createCopiedFileVActionImpl(){
		return (Action)new CopiedFileVActionImpl();
	}
	private Action createCopiedFolderVActionImpl(){
		return (Action)new CopiedFolderVActionImpl();
	}
	private Action createCopiedInfoActionImpl(){
		return (Action)new CopiedInfoActionImpl();
	}
	private Action createCopiedProjectVActionImpl(){
		return (Action)new CopiedProjectVActionImpl();
	}
	private Action createFileActionImpl(){
		return (Action)new FileActionImpl();
	}
	private Action createFolderActionImpl(){
		return (Action)new FolderActionImpl();
	}
	private Action createMessageActionImpl(){
		return (Action)new MessageActionImpl();
	}
	private Action createProjectActionImpl(){
		return (Action)new ProjectActionImpl();
	}
	private Action createProjectSharedMemVActionImpl(){
		return (Action)new ProjectSharedMemVActionImpl();
	}
	private Action createProjectTagsVActionImpl(){
		return (Action)new ProjectTagsVActionImpl();
	}
	private Action createTeamCooperVActionImpl(){
		return (Action)new TeamCooperVActionImpl();
	}
	private Action createTeamTagsVActionImpl(){
		return (Action)new TeamTagsVActionImpl();
	}
	
}
