package action;

import java.util.ArrayList;
import java.util.List;

import socialProExceptions.DAOException;
import model.GroupAlarm;
import dao.DAO;
import dao.FileDAO;
import dao.GroupAlarmDAO;
import dto.GroupAlarmDTO;
import factory.ActionFactory;
import factory.DAOFactory;

public class GroupAlarmActionImpl extends BaseAction implements GroupAlarmSearchAction,GroupAlarmInsertAction,GroupAlarmDropAction,GroupAlarmUpdateAction{

	public GroupAlarmActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((GroupAlarmDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("GroupAlarmDAO"));
	}
	
	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		this.dao=(GroupAlarmDAO)dao;
	}

	@Override
	public GroupAlarm insertGroupAlarm(String targetCode,List<String> groupMemberCodeList) throws DAOException {
		// TODO Auto-generated method stub
		
		//insert Alarm_tb
		GroupAlarm res = new GroupAlarm();
		List<GroupAlarmDTO> dtoList = new ArrayList<GroupAlarmDTO>();
		for(String cooperCode : groupMemberCodeList){
			dtoList.add(new GroupAlarmDTO(targetCode, cooperCode));
			res.putMemberCheckDate(cooperCode, null);
		}
		String gAlarmCode = ((GroupAlarmDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("GroupAlarmDAO")).insertGroupAlarm(dtoList);
		if(gAlarmCode!=null){
			res.setAlarmCode(gAlarmCode);
			res.setTargetCode(targetCode);
		}
		return res;
	}
}
