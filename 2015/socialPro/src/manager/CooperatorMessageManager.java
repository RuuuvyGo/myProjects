package manager;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Alarm;
import model.CooperatorMessage;
import model.Notice;
import model.OriginProject;
import socialProExceptions.DAOException;
import socialProExceptions.FileException;
import socialProExceptions.FolderException;
import socialProExceptions.MemberException;
import socialProExceptions.ProjectException;
import socialProExceptions.TeamException;
import sun.util.locale.StringTokenIterator;
import form.MessageForm;

public class CooperatorMessageManager {

	private static CooperatorMessageManager INSTANCE;
	private Map<String,Map<String,Notice>> cooperProMsgMap;//memberCode, pro or team
	
	//(memberCode,origin projectCode or teamCode or copiedProjectCode)
	
	static{
		INSTANCE = new CooperatorMessageManager();
	}
	
	private CooperatorMessageManager(){
		this.cooperProMsgMap = new HashMap<String,Map<String,Notice>>();
	}
	
	public static CooperatorMessageManager getINSTANCE() {
		return INSTANCE;
	}
	

	public void loadUnReadCooperMessage(String memberCode) throws DAOException, ParseException, FolderException, ProjectException{
		
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap1 = MessageDBManager.getINSTANCE().loadUnReadTeamCooperMsgManagerSend(memberCode);
		if(!teamCooperMsgMap1.isEmpty()){
			for(String teamCode : teamCooperMsgMap1.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> cooperMsgMap = teamCooperMsgMap1.get(teamCode);
				for(String cooperCode : cooperMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = cooperMsgMap.get(cooperCode);
					for(Alarm alarm : msgMap.keySet()){
						
						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(cooperCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(cooperCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(cooperCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(cooperCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(cooperCode).get(teamCode).addUnReadNoticeCode(msgCode);
							}
						}
						
						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(teamCode).addUnReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}
		
		//teamCode, managerCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap2 = MessageDBManager.getINSTANCE().loadUnReadTeamCooperMsgMemSend(memberCode);
		if(!teamCooperMsgMap2.isEmpty()){
			for(String teamCode : teamCooperMsgMap2.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> ownerMsgMap = teamCooperMsgMap2.get(teamCode);
				for(String managerCode : ownerMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = ownerMsgMap.get(managerCode);
					for(Alarm alarm : msgMap.keySet()){
						
						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(managerCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(managerCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(managerCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(managerCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(managerCode).get(teamCode).addUnReadNoticeCode(msgCode);
							}
						}

						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(teamCode).addUnReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}

		//teamCode, memberCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap3 = MessageDBManager.getINSTANCE().loadUnReadTeamCooperMsgManagerRec(memberCode);
		if(!teamCooperMsgMap3.isEmpty()){
			for(String teamCode : teamCooperMsgMap3.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> cooperMsgMap = teamCooperMsgMap3.get(teamCode);
				for(String cooperCode : cooperMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = cooperMsgMap.get(cooperCode);
					for(Alarm alarm : msgMap.keySet()){
						
						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();

						if(!this.cooperProMsgMap.containsKey(cooperCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(cooperCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(cooperCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(cooperCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(cooperCode).get(teamCode).addUnReadNoticeCode(msgCode);
							}
						}
						
						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(teamCode).addUnReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}
		
		//teamCode, ownerCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap4 = MessageDBManager.getINSTANCE().loadUnReadTeamCooperMsgMemRec(memberCode);
		if(!teamCooperMsgMap4.isEmpty()){
			for(String teamCode : teamCooperMsgMap4.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> ownerMsgMap = teamCooperMsgMap4.get(teamCode);
				for(String ownerCode : ownerMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = ownerMsgMap.get(ownerCode);
					for(Alarm alarm : msgMap.keySet()){

						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(ownerCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(ownerCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(ownerCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(ownerCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(ownerCode).get(teamCode).addUnReadNoticeCode(msgCode);
							}
						}

						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(teamCode).addUnReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}

		//OwnerCode,  projectCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap5 = MessageDBManager.getINSTANCE().loadUnReadProCooperMsgSend(memberCode);
		if(!teamCooperMsgMap5.isEmpty()){
			for(String ownerCode : teamCooperMsgMap5.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> proMsgMap = teamCooperMsgMap5.get(ownerCode);
				for(String oriProCode : proMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = proMsgMap.get(oriProCode);
					for(Alarm alarm : msgMap.keySet()){

						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(ownerCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(oriProCode, note1);
							
							this.cooperProMsgMap.put(ownerCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(ownerCode).containsKey(oriProCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(ownerCode).put(oriProCode, note1);
							}else{
								this.cooperProMsgMap.get(ownerCode).get(oriProCode).addUnReadNoticeCode(msgCode);
							}
						}

						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(oriProCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(oriProCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(oriProCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(oriProCode).addUnReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}
		
		//originProjectCode, cooperatorCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap6 = MessageDBManager.getINSTANCE().loadUnReadProCooperMsgRec(memberCode);
		if(!teamCooperMsgMap6.isEmpty()){
			for(String oriProCode : teamCooperMsgMap6.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> cooperMsgMap = teamCooperMsgMap6.get(oriProCode);
				for(String cooperCode : cooperMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = cooperMsgMap.get(cooperCode);
					for(Alarm alarm : msgMap.keySet()){

						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(cooperCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(oriProCode, note1);
							
							this.cooperProMsgMap.put(cooperCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(cooperCode).containsKey(oriProCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(cooperCode).put(oriProCode, note1);
							}else{
								this.cooperProMsgMap.get(cooperCode).get(oriProCode).addUnReadNoticeCode(msgCode);
							}
						}

						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addUnReadNoticeCode(msgCode);
							val1.put(oriProCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(oriProCode)){
								Notice note1 = new Notice();
								note1.addUnReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(oriProCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(oriProCode).addUnReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}
	}
	
	public void loadReadCooperMessage(String memberCode) throws DAOException, ParseException, FolderException, ProjectException{
		
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap1 = MessageDBManager.getINSTANCE().loadReadTeamCooperMsgManagerSend(memberCode);
		if(!teamCooperMsgMap1.isEmpty()){
			for(String teamCode : teamCooperMsgMap1.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> cooperMsgMap = teamCooperMsgMap1.get(teamCode);
				for(String cooperCode : cooperMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = cooperMsgMap.get(cooperCode);
					for(Alarm alarm : msgMap.keySet()){
						
						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(cooperCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(cooperCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(cooperCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(cooperCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(cooperCode).get(teamCode).addReadNoticeCode(msgCode);
							}
						}
						
						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(teamCode).addReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}
		
		//teamCode, managerCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap2 = MessageDBManager.getINSTANCE().loadReadTeamCooperMsgMemSend(memberCode);
		if(!teamCooperMsgMap2.isEmpty()){
			for(String teamCode : teamCooperMsgMap2.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> ownerMsgMap = teamCooperMsgMap2.get(teamCode);
				for(String managerCode : ownerMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = ownerMsgMap.get(managerCode);
					for(Alarm alarm : msgMap.keySet()){
						
						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(managerCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(managerCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(managerCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(managerCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(managerCode).get(teamCode).addReadNoticeCode(msgCode);
							}
						}

						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(teamCode).addReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}

		//teamCode, memberCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap3 = MessageDBManager.getINSTANCE().loadReadTeamCooperMsgManagerRec(memberCode);
		if(!teamCooperMsgMap3.isEmpty()){
			for(String teamCode : teamCooperMsgMap3.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> cooperMsgMap = teamCooperMsgMap3.get(teamCode);
				for(String cooperCode : cooperMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = cooperMsgMap.get(cooperCode);
					for(Alarm alarm : msgMap.keySet()){
						
						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();

						if(!this.cooperProMsgMap.containsKey(cooperCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(cooperCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(cooperCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(cooperCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(cooperCode).get(teamCode).addReadNoticeCode(msgCode);
							}
						}
						
						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(teamCode).addReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}
		
		//teamCode, ownerCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap4 = MessageDBManager.getINSTANCE().loadReadTeamCooperMsgMemRec(memberCode);
		if(!teamCooperMsgMap4.isEmpty()){
			for(String teamCode : teamCooperMsgMap4.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> ownerMsgMap = teamCooperMsgMap4.get(teamCode);
				for(String ownerCode : ownerMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = ownerMsgMap.get(ownerCode);
					for(Alarm alarm : msgMap.keySet()){

						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(ownerCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(ownerCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(ownerCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(ownerCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(ownerCode).get(teamCode).addReadNoticeCode(msgCode);
							}
						}

						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(teamCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(teamCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(teamCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(teamCode).addReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}

		//OwnerCode,  projectCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap5 = MessageDBManager.getINSTANCE().loadReadProCooperMsgSend(memberCode);
		if(!teamCooperMsgMap5.isEmpty()){
			for(String ownerCode : teamCooperMsgMap5.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> proMsgMap = teamCooperMsgMap5.get(ownerCode);
				for(String oriProCode : proMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = proMsgMap.get(oriProCode);
					for(Alarm alarm : msgMap.keySet()){

						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(ownerCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(oriProCode, note1);
							
							this.cooperProMsgMap.put(ownerCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(ownerCode).containsKey(oriProCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(ownerCode).put(oriProCode, note1);
							}else{
								this.cooperProMsgMap.get(ownerCode).get(oriProCode).addReadNoticeCode(msgCode);
							}
						}

						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(oriProCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(oriProCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(oriProCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(oriProCode).addReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}
		
		//originProjectCode, cooperatorCode
		Map<String, Map<String, Map<Alarm, CooperatorMessage>>> teamCooperMsgMap6 = MessageDBManager.getINSTANCE().loadReadProCooperMsgRec(memberCode);
		if(!teamCooperMsgMap6.isEmpty()){
			for(String oriProCode : teamCooperMsgMap6.keySet()){
				
				Map<String,Map<Alarm,CooperatorMessage>> cooperMsgMap = teamCooperMsgMap6.get(oriProCode);
				for(String cooperCode : cooperMsgMap.keySet()){
					
					Map<Alarm,CooperatorMessage> msgMap = cooperMsgMap.get(cooperCode);
					for(Alarm alarm : msgMap.keySet()){

						CooperatorMessage cooperMsg = msgMap.get(alarm);
						AlarmManager.getINSTANCE().putAlarm(alarm);
						MessageManager.getINSTANCE().putMessage(cooperMsg);
						
						String msgCode = cooperMsg.getMessageCode();
						
						if(!this.cooperProMsgMap.containsKey(cooperCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(oriProCode, note1);
							
							this.cooperProMsgMap.put(cooperCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(cooperCode).containsKey(oriProCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(cooperCode).put(oriProCode, note1);
							}else{
								this.cooperProMsgMap.get(cooperCode).get(oriProCode).addReadNoticeCode(msgCode);
							}
						}

						if(!this.cooperProMsgMap.containsKey(memberCode)){
							Map<String,Notice> val1 = new HashMap<String, Notice>();
							Notice note1 = new Notice();
							note1.addReadNoticeCode(msgCode);
							val1.put(oriProCode, note1);
							
							this.cooperProMsgMap.put(memberCode, val1);
						}else{
							if(!this.cooperProMsgMap.get(memberCode).containsKey(oriProCode)){
								Notice note1 = new Notice();
								note1.addReadNoticeCode(msgCode);
								
								this.cooperProMsgMap.get(memberCode).put(oriProCode, note1);
							}else{
								this.cooperProMsgMap.get(memberCode).get(oriProCode).addReadNoticeCode(msgCode);
							}
						}
					}
				}
			}
		}
	}
	
	public void initMemberCooperatorMessage(String memberCode) throws DAOException, ParseException, FolderException, ProjectException{
		this.loadReadCooperMessage(memberCode);
		this.loadUnReadCooperMessage(memberCode);
	}
	

	public List<MessageForm> searchMemberUnReadMsgFormList(String memberCode) throws DAOException, ParseException, FolderException, ProjectException, MemberException{
		
		List<MessageForm> resList = new ArrayList<MessageForm>();
		
		if(!this.cooperProMsgMap.containsKey(memberCode))this.loadUnReadCooperMessage(memberCode);
		if(this.cooperProMsgMap.containsKey(memberCode)){
			if(!this.cooperProMsgMap.get(memberCode).isEmpty()){
				for(Notice note : this.cooperProMsgMap.get(memberCode).values()){
					for(String msgCode : note.getUnreadNotice()){
						CooperatorMessage msg = MessageManager.getINSTANCE().getCooperatorMessage(msgCode);
						if(msg!=null){
							String nickName = MemberManager.getINSTANCE().searchMemberCode(msg.getSenderCode()).getNickName();
							String groupCode = msg.getGroupCode();
							System.out.println("groupCode : "+groupCode);
							if(new StringTokenIterator(groupCode, "_").next().equals("team")){
								 MessageForm val = new MessageForm(msgCode, msg.getSenderCode(), nickName, msg.getTitle(), msg.getGroupCode(),msg.getContent());
								 resList.add(val);
							}else{
								OriginProject oriPro = ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectCode(groupCode);
								MessageForm val = new MessageForm(msgCode, msg.getSenderCode(), nickName, msg.getTitle(), oriPro.getOwner(), oriPro.getCode(),msg.getContent());
								resList.add(val);
							}
						}
					}
				}
			}
		}
		return resList;
	}

	public List<MessageForm> searchMemberReadMsgFormList(String memberCode) throws DAOException, ParseException, FolderException, ProjectException, MemberException{
		
		List<MessageForm> resList = new ArrayList<MessageForm>();
		
		if(!this.cooperProMsgMap.containsKey(memberCode))this.loadUnReadCooperMessage(memberCode);
		if(this.cooperProMsgMap.containsKey(memberCode)){
			if(!this.cooperProMsgMap.get(memberCode).isEmpty()){
				for(Notice note : this.cooperProMsgMap.get(memberCode).values()){
					for(String msgCode : note.getReadNotice()){
						CooperatorMessage msg = MessageManager.getINSTANCE().getCooperatorMessage(msgCode);
						if(msg!=null){
							String nickName = MemberManager.getINSTANCE().searchMemberCode(msg.getSenderCode()).getNickName();
							String groupCode = msg.getGroupCode();
							String realGroupCode;
							if(new StringTokenIterator(groupCode, "_").next().equals("team")){
								 MessageForm val = new MessageForm(msgCode, msg.getSenderCode(), nickName, msg.getTitle(), msg.getGroupCode(),msg.getContent());
								 resList.add(val);
							}else{
								OriginProject oriPro = ProjectManager.getINSTANCE().searchOnlyOriginProjectByProjectCode(groupCode);
								MessageForm val = new MessageForm(msgCode, msg.getSenderCode(), nickName, msg.getTitle(), oriPro.getOwner(), oriPro.getCode(),msg.getContent());
								resList.add(val);
							}
						}
					}
				}
			}
		}
		return resList;
	}
	
	public List<String> searchMemberUnReadList(String memberCode) throws DAOException, ParseException, FolderException, ProjectException{
		
		List<String> resList = new ArrayList<String>();
		
		if(!this.cooperProMsgMap.containsKey(memberCode))this.loadUnReadCooperMessage(memberCode);
		if(this.cooperProMsgMap.containsKey(memberCode)){
			if(!this.cooperProMsgMap.get(memberCode).isEmpty()){
				for(Notice note : this.cooperProMsgMap.get(memberCode).values()){
					
					List<String> unReadList = note.getUnreadNotice();
					if(!unReadList.isEmpty())resList.addAll(unReadList);
				}
			}
		}
		return resList;
	}
	
	public List<String> searchMemberReadList(String memberCode) throws DAOException, ParseException, FolderException, ProjectException{
		
		List<String> resList = new ArrayList<String>();
		
		if(!this.cooperProMsgMap.containsKey(memberCode))this.loadUnReadCooperMessage(memberCode);
		
		for(Notice note : this.cooperProMsgMap.get(memberCode).values()){
			
			List<String> readList = note.getReadNotice();
			if(!readList.isEmpty())resList.addAll(readList);
		}
		
		return resList;
	}
	
	public boolean createTeamCooperatorMessage(String teamCode, List<String> cooperNickNameList) 
			throws DAOException, TeamException, MemberException, ParseException, FolderException, ProjectException{
		
		List<String> cooperList = new ArrayList<String>();
		for(String cooperNickName : cooperNickNameList){
			String coopercode = MemberManager.getINSTANCE().searchMemberNickName(cooperNickName).getCode();
			if(coopercode!=null)cooperList.add(coopercode);
		}
		
		Map<Alarm,CooperatorMessage> resMap = MessageDBManager.getINSTANCE().insertTeamCooperatorInviteMsg(teamCode, cooperList);
		if(!resMap.isEmpty()){
			
			for(Alarm alarm : resMap.keySet()){
				CooperatorMessage cooperMsg = resMap.get(alarm);
				String sender = cooperMsg.getSenderCode();
				String receiver = cooperMsg.getReceiverCode();
				String msgCode = cooperMsg.getMessageCode();
				
				if(!this.cooperProMsgMap.containsKey(sender))this.loadUnReadCooperMessage(sender);
				if(!this.cooperProMsgMap.containsKey(receiver))this.loadUnReadCooperMessage(receiver);
				
				if(this.cooperProMsgMap.get(sender).containsKey(teamCode)){
					this.cooperProMsgMap.get(sender).get(teamCode).addUnReadNoticeCode(msgCode);
				}else{
					Notice note = new Notice();
					note.addUnReadNoticeCode(msgCode);
					this.cooperProMsgMap.get(sender).put(teamCode, note);
				}

				if(this.cooperProMsgMap.get(receiver).containsKey(teamCode)){
					this.cooperProMsgMap.get(receiver).get(teamCode).addUnReadNoticeCode(msgCode);
				}else{
					Notice note = new Notice();
					note.addUnReadNoticeCode(msgCode);
					this.cooperProMsgMap.get(receiver).put(teamCode, note);
				}
			}
			return true;
		}else return false;
	}

	public boolean createProCooperatorMessage(String oriProOwnerCode, String oriProCode, String cooperCode) 
			throws DAOException, TeamException, MemberException, ParseException, FolderException, ProjectException, IOException, 
			FileException{
		
		Map<Alarm,CooperatorMessage> resMap = MessageDBManager.getINSTANCE().insertProjectCooperatorMsg(oriProOwnerCode, oriProCode, cooperCode);
		if(!resMap.isEmpty()){
			Alarm alarm = resMap.keySet().iterator().next();
			CooperatorMessage cooperMsg = resMap.get(alarm);
			String sender = cooperMsg.getSenderCode();
			String receiver = cooperMsg.getReceiverCode();
			String msgCode = cooperMsg.getMessageCode();

			System.out.println("sender : "+sender);
			System.out.println("receiver : "+receiver);
			if(!this.cooperProMsgMap.containsKey(sender))this.loadUnReadCooperMessage(sender);
			if(!this.cooperProMsgMap.containsKey(receiver))this.loadUnReadCooperMessage(receiver);
			
			for(String mem : this.cooperProMsgMap.keySet()){
				System.out.print("* memberCode : "+mem +"//  ");
				for(String pro : this.cooperProMsgMap.get(mem).keySet()){
					System.out.print(pro+" , ");
				}
				System.out.println();
			}
			if(this.cooperProMsgMap.get(sender).containsKey(oriProCode)){
				this.cooperProMsgMap.get(sender).get(oriProCode).addUnReadNoticeCode(msgCode);
			}else{
				Notice note = new Notice();
				note.addUnReadNoticeCode(msgCode);
				this.cooperProMsgMap.get(sender).put(oriProCode, note);
			}

			
			if(this.cooperProMsgMap.get(receiver).containsKey(oriProCode)){
				this.cooperProMsgMap.get(receiver).get(oriProCode).addUnReadNoticeCode(msgCode);
			}else{
				Notice note = new Notice();
				note.addUnReadNoticeCode(msgCode);
				this.cooperProMsgMap.get(receiver).put(oriProCode, note);
			}
			return true;
		}else return false;
	}
}
