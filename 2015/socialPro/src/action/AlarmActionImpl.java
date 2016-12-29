package action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import model.Alarm;
import socialProExceptions.DAOException;
import dao.AlarmDAO;
import dao.DAO;
import dto.AlarmDTO;
import factory.DAOFactory;

public class AlarmActionImpl extends BaseAction implements AlarmInsertAction,AlarmSearchAction,AlarmUpdateAction,AlarmDropAction{

	public AlarmActionImpl() {
		// TODO Auto-generated constructor stub
		this.setDAO((AlarmDAO)DAOFactory.getDAOFACTORY_INSTANCE().create("alarmDAO"));
	}

	@Override
	public void setDAO(DAO dao) {
		// TODO Auto-generated method stub
		this.dao = (AlarmDAO)dao;
	}

	@Override
	public boolean updateCheckDate(String alarmCode) throws DAOException {
		// TODO Auto-generated method stub
		GregorianCalendar cal = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		df.setTimeZone(cal.getTimeZone());
		
		return ((AlarmDAO)this.getDAO()).updateChecks(alarmCode, df.format(cal.getTime()));
	}

	@Override
	public Alarm updateCommitMsgCheckDate(String commitMsgCode) throws DAOException{
		System.out.println(commitMsgCode);
		List<AlarmDTO> alarmDTOList = ((AlarmDAO)this.getDAO()).searchTargetCode(commitMsgCode);
		if(!alarmDTOList.isEmpty()){
			AlarmDTO dto = alarmDTOList.get(0);
			String alarmCode = dto.getAlarmCode();
			System.out.println("alarmCode  ::::   "+alarmCode+" resDTOList size   :::   "+alarmDTOList.size());
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			GregorianCalendar cal = new GregorianCalendar();
			df.setTimeZone(cal.getTimeZone());
			if(((AlarmDAO)this.getDAO()).updateChecks(alarmCode, df.format(cal.getTime()))){
				return new Alarm(alarmCode, commitMsgCode, cal);
			}else return null;
		}else {
			System.out.println(" there is no alarm................tt");
			return null;
		}
	}
	
//////////////////////////////////////////////////////////////   insert
	
	public Alarm insertAlarm(String targetCode) throws DAOException{
		
		//insert Alarm_tb
		String alarmCode = ((AlarmDAO)this.getDAO()).insertAlarm(new AlarmDTO(targetCode));
		if(alarmCode==null)return null;
		Alarm alarm = new Alarm(alarmCode, targetCode, null);
		System.out.println("alarmCode : "+alarmCode);
		return alarm;
	}
}
