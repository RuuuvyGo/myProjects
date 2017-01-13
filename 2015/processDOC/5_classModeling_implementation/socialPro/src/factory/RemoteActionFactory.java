package factory;

import remoteAction.DiffFileActionImpl;
import remoteAction.RemoteAction;
import remoteAction.RemoteFileActionImpl;
import remoteAction.RemoteFolderActionImpl;

import com.jcraft.jsch.JSchException;

public class RemoteActionFactory implements Factory{
	
	private static RemoteActionFactory REMOTEACTIONFACTORY_INSTANCE;
	static{
		REMOTEACTIONFACTORY_INSTANCE = new RemoteActionFactory();
	}
	private RemoteActionFactory(){
		//if(ActionImplFactory.ACTIONFACTORY_INSTANCE==null){ActionImplFactory.ACTIONFACTORY_INSTANCE=new ActionImplFactory();}
	}
	public static RemoteActionFactory getREMOTEACTIONFACTORY_INSTANCE(){
		if(REMOTEACTIONFACTORY_INSTANCE==null)REMOTEACTIONFACTORY_INSTANCE = new RemoteActionFactory();
		return RemoteActionFactory.REMOTEACTIONFACTORY_INSTANCE;
	} 
	
	@Override
	public RemoteAction create(String name) {
		
			try {
				if(name.equals("RemoteFileActionImpl")){
					return createRemoteFileActionImpl();
				}else if(name.equals("RemoteFolderActionImpl")){
					return createRemoteFolderActionImpl();
				}else if(name.equals("DiffFileActionImpl")){
					return createDiffFileActionImpl(); 
				}
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}

	private RemoteAction createRemoteFileActionImpl() throws JSchException{
		return new RemoteFileActionImpl();
	}
	
	private RemoteAction createRemoteFolderActionImpl() throws JSchException{
		return new RemoteFolderActionImpl();
	}
	private RemoteAction createDiffFileActionImpl() throws JSchException{
		return new DiffFileActionImpl();
	}
}
