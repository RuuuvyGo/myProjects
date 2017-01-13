package action;

import java.util.ArrayList;
import java.util.List;

import dao.CooperatorDAO;
import dao.DAO;
import socialProExceptions.DAOException;
import dao.MemberDAO;
import dto.CooperatorDTO;
import dto.TeamCooperVDTO;
import factory.DAOFactory;

public class CooperatorActionImpl extends BaseAction implements CooperatorUpdateAction,CooperatorSearchAction,CooperatorInsertAction,CooperatorDropAction{

	public CooperatorActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((CooperatorDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("cooperatorDAO"));
	}

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		super.dao = (CooperatorDAO)dao;
	}

	@Override
	public boolean dropCooper(String setCode, String memberCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((CooperatorDAO)this.getDAO()).dropCooper(memberCode, setCode);
	}

	@Override
	public int dropCooperCode(String memberCode) throws DAOException {
		// TODO Auto-generated method stub
		return ((CooperatorDAO)this.getDAO()).dropCooperMCode(memberCode);
	}

	@Override
	public int dropCooperSCode(String setCode) throws DAOException {
		// TODO Auto-generated method stub
		return ((CooperatorDAO)this.getDAO()).dropCooperSCode(setCode);
	}

	@Override
	public boolean insertCooper(String memberCode, String setCode)
			throws DAOException {
		// TODO Auto-generated method stub
		return ((CooperatorDAO)this.getDAO()).insertCooperator(new CooperatorDTO(memberCode, setCode));
	}

	@Override
	public List<String> searchCooper(String setCode) throws DAOException {
		// TODO Auto-generated method stub
		List<CooperatorDTO> dtoList=((CooperatorDAO)this.getDAO()).searchCooperSCode(setCode);
		int length = dtoList.size();
		if(length==0)return null;
		List<String> resList = new ArrayList<String>();
		for(int i=0;i<length;i++){
			resList.add(dtoList.get(i).getMemberCode());
		}
		return resList;
	}

	@Override
	public List<String> searchSCode(String memberCode) throws DAOException {
		// TODO Auto-generated method stub
		
		List<CooperatorDTO> dtoList=((CooperatorDAO)this.getDAO()).searchCooperMCode(memberCode);
		int length = dtoList.size();
		if(length==0)return null;
		List<String> resList = new ArrayList<String>();
		for(int i=0;i<length;i++){
			resList.add(dtoList.get(i).getSetCode());
		}
		return resList;
	}

	@Override
	public List<String> searchSCode(String memberCode, String front)
			throws DAOException {
		// TODO Auto-generated method stub
		if(front.equals("project")){
			List<TeamCooperVDTO> dtoList=new TeamCooperVActionImpl().searchMCode(memberCode);
			List<String> codeList = new ArrayList<String>();
			int cnt=dtoList.size();
			if(cnt==0)return codeList;
			for(int i=0;i<cnt;i++){
				codeList.add(dtoList.get(i).getTeamCode());
			}
			return codeList;
		}
		/*List<CooperatorDTO> dtoList=((CooperatorDAO)this.getDAO()).searchCooper(memberCode, front);
		int length = dtoList.size();
		if(length==0)return null;
		List<String> resList = new ArrayList<String>();
		for(int i=0;i<length;i++){
			resList.add(dtoList.get(i).getSetCode());
		}*/
		return null;
	}

	@Override
	public boolean updateCooperator(List<String> memberCodes, String setCode)
			throws DAOException {
		// TODO Auto-generated method stub
		List<CooperatorDTO> dtoList=((CooperatorDAO)this.getDAO()).searchCooperSCode(setCode);
		if(dtoList.size()==0)return false;
		int newMemsize = memberCodes.size();
		for(int i=0;i<dtoList.size();i++){
			for(int j=0;j<newMemsize;j++){
				if(memberCodes.get(j).equals(dtoList.get(i).getMemberCode())){memberCodes.remove(j);}
			}
		}
		if(newMemsize==memberCodes.size())return true;
		return ((CooperatorDAO)this.getDAO()).insertCooperators(memberCodes, setCode);
	}

	@Override
	public boolean insertCooper(List<String> memberCode, String setCode)
			throws DAOException {
		// TODO Auto-generated method stub
		if(memberCode.isEmpty()){
			System.out.println("THis is Emty....");
		}
		return ((CooperatorDAO)this.getDAO()).insertCooperators(memberCode, setCode);
	}

	
}