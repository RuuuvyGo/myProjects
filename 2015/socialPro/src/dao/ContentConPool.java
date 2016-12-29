package dao;

import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.proxy.annotation.GetCreator;

import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;

import com.jcraft.jsch.JSchException;

public class ContentConPool {

	private static ContentConPool CONTENTCONPOOL;
	private  List<ContentCon> conList;
	private static int PNT;
	
	static {
		try {
			CONTENTCONPOOL=new ContentConPool();
			PNT=0;
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ContentConPool() throws JSchException{
		this.conList = new ArrayList<ContentCon>();
		ContentCon c1,c2,c3,c4,c5;
		try {
			c1 = (ContentCon)new ContentCon().getConnection();
			conList.add(0,c1);
			c2=(ContentCon)new ContentCon().getConnection();
			conList.add(1,c2);
			c3=(ContentCon)new ContentCon().getConnection();
			conList.add(2,c3);
			c4=(ContentCon)new ContentCon().getConnection();
			conList.add(3,c4);
			c5=(ContentCon)new ContentCon().getConnection();
			conList.add(4,c5);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSchException("error ContnetConPool Constructor..\n"+e.getMessage());
		}
	}
	
	public static ContentConPool getCONTENTCONPOOL() throws JSchException{
		
		if(CONTENTCONPOOL==null){
			CONTENTCONPOOL=new ContentConPool();
		}
		return CONTENTCONPOOL;		
	}
	
	public ContentCon getContentCon(){
		return conList.get(PNT);
	}
	
	public int getPNT(){
		return PNT;
	}
	
	
	
}
