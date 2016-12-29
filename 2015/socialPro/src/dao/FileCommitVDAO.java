package dao;

public class FileCommitVDAO extends BaseDAO{
	
	private static final String UPDATE_COMMIT_SQL2481 = "update commit_tb set TITLE=?, DESCRIPTION=?, MERGE=? where COMMITCODE=?";
	private static final String UPDATE_COMMIT_SQL71 = "update commit_tb set MERGE=? where commitCode=?";
	private static final String UPDATE_COMMIT_SQL7 = "update commit_tb set MERGE=? where ";
	
	private static final String SELECT_COMMIT_SQL0 = "select COMMITCODE, TITLE, ownerCode, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, setcode, CONTENT, MERGE,TYPE FROM commit_tb";
	private static final String SELECT_COMMIT_SQL1 = "select COMMITCODE, TITLE, ownerCode, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, setcode, CONTENT, MERGE,TYPE FROM commit_tb where COMMITCODE=?";
	private static final String SELECT_COMMIT_SQL3 = "select COMMITCODE, TITLE, ownerCode, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, setcode, CONTENT, MERGE,TYPE FROM commit_tb where ownerCode=? order by insertDate desc";
	private static final String SELECT_COMMIT_SQL5 = "select COMMITCODE, TITLE, ownerCode, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, setcode, CONTENT, MERGE,TYPE FROM commit_tb where setcode=? order by insertDate desc";
	private static final String SELECT_COMMIT_SQL57 = "select COMMITCODE, TITLE, ownerCode, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, setcode, CONTENT, MERGE,TYPE FROM commit_tb where setcode=? and merge=? order by insertDate desc";
	private static final String SELECT_COMMIT_SQL574 = "select COMMITCODE, TITLE, ownerCode, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, setcode, CONTENT, MERGE,TYPE FROM commit_tb where setcode=? and merge=? and insertdate<=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff3') order by insertDate desc";
	private static final String SELECT_COMMIT_SQL57_ = "select COMMITCODE, TITLE, ownerCode, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, setcode, CONTENT, MERGE,TYPE FROM commit_tb where setcode=? and insertdate>=to_timestamp(?,'yyyy-mm-dd hh24:mi:ss.ff3') order by insertDate desc";
	private static final String SELECT_COMMIT_SQL58 = "select COMMITCODE, TITLE, ownerCode, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, setcode, CONTENT, MERGE,TYPE FROM commit_tb where setcode=? and type=?";
	private static final String SELECT_COMMIT_SQL7 = "select COMMITCODE, TITLE, ownerCode, to_char(INSERTDATE,'yyyy-mm-dd hh24:mi:ss.ff3') as INSERTDATE, setcode, CONTENT, MERGE,TYPE FROM commit_tb where MERGE=? order by insertDate desc";
	
	
}
