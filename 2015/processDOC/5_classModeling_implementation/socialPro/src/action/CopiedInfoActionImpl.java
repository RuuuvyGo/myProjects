package action;

import java.util.List;

import socialProExceptions.DAOException;
import dao.CopiedInfoDAO;
import dao.DAO;
import dto.CopiedInfoDTO;
import factory.DAOFactory;

public class CopiedInfoActionImpl extends BaseAction implements CopiedInfoSearchAction,CopiedInfoInsertAction,CopiedInfoDropAction,CopiedInfoUpdateAction{

	public CopiedInfoActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((CopiedInfoDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("copiedInfoDAO"));
	}

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		this.dao = (CopiedInfoDAO)dao;
	}

	@Override
	public boolean insertCopiedCode(String copiedCode,String originCode) throws DAOException {
		// TODO Auto-generated method stub
		if(((CopiedInfoDAO)this.dao).insertCopiedInfo(new CopiedInfoDTO(copiedCode, originCode))!=1)return false;
		return true;
	}

	@Override
	public int insertOriginCode(String originCode,List<String> copiedCodeList) throws DAOException {
		// TODO Auto-generated method stub
		
		int cnt = copiedCodeList.size();
		for(int i=0;i<cnt;i++){
			if(((CopiedInfoDAO)this.dao).insertCopiedInfo(new CopiedInfoDTO(copiedCodeList.get(i), originCode))!=1)return i;
		}
		return cnt;
	}
	

}
