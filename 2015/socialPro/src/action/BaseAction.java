package action;

import dao.DAO;

public abstract class BaseAction implements Action{


	protected DAO dao;
	
	@Override
	public abstract void setDAO(DAO dao);
	protected DAO getDAO(){
		return dao;
	}
}
