package remoteAction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import factory.ChannelFactory;

public class DiffFileActionImpl extends BaseRemoteAction implements InsertDiffFileAction,SearchDiffFileAction,DeleteDiffFileAction{

	private String remoteRootPath;
	private String remoteDiffRootPath;
	
	public DiffFileActionImpl() throws JSchException {
		super();
		// TODO Auto-generated constructor stub
		this.setChannel((ChannelShell)ChannelFactory.getCHANNELFACTORY_INSTANCE().create("shell"));
		this.remoteRootPath="/home/socialPro";
		this.remoteDiffRootPath="/home/socialPro/tmp";
	}

	@Override
	public void setChannel(Channel channel) {
		// TODO Auto-generated method stub
		
		this.channel = (ChannelShell)channel;
		
	}
	
	@Override
	public synchronized boolean insertDiffFile(String originPath, String diffFilePath) throws IOException, JSchException {
		// TODO Auto-generated method stub
		System.out.println("originPath : "+originPath+"    diffFilePath : "+diffFilePath);
		
		originPath = originPath.replaceAll("/home/socialPro/", "");
		System.out.println("real originPath : "+originPath);
		
		diffFilePath = diffFilePath.replaceAll("/home/socialPro/", "");
		System.out.println("real diffFilePath : "+diffFilePath);
		
		String tempPath=originPath+"_cp";
		System.out.println("insertDiffFile  :  DiffFileActionImpl....????");
		System.out.println(originPath+"\n"+tempPath+"\n"+diffFilePath);
		ChannelShell chanShell= (ChannelShell)this.channel;
		if(!chanShell.isConnected())chanShell.connect();
		
		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		//InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		//BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		System.out.println("diff -ub "+originPath+" "+tempPath+" > "+diffFilePath);
		commander.println("diff -ub "+originPath+" "+tempPath+" > "+diffFilePath);
		commander.println("exit");
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("hhhh");
		} while(!chanShell.isEOF());
		
		inputstream_for_the_channel.close();
		
		return true;
	}
	
	@Override
	public boolean insertDiffFile(String originPath, String compareFilePath,String diffFilePath) throws IOException, JSchException{
		// TODO Auto-generated method stub
		System.out.println("insertDiffFile  :  DiffFileActionImpl....????");
		System.out.println(originPath+"\n"+compareFilePath+"\n"+diffFilePath);
		
		originPath = originPath.replaceAll("/home/socialPro/", "");
		System.out.println("real originPath : "+originPath);
		
		compareFilePath = compareFilePath.replaceAll("/home/socialPro/", "");
		System.out.println("real compareFilePath : "+compareFilePath);

		diffFilePath = diffFilePath.replaceAll("/home/socialPro/", "");
		System.out.println("real diffFilePath : "+diffFilePath);
		
		ChannelShell chanShell= (ChannelShell)this.channel;
		if(!chanShell.isConnected())chanShell.connect();
		
		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		//InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		//BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		System.out.println("diff -ub "+originPath+" "+compareFilePath+" > "+diffFilePath);
		commander.println("diff -ub "+originPath+" "+compareFilePath+" > "+diffFilePath);
		commander.println("exit");
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("hhhh");
		} while(!chanShell.isEOF());
		
		inputstream_for_the_channel.close();
		
		return true;
	}

	@Override
	public synchronized boolean patchFile(String pathPath, String oriPath) throws IOException, JSchException {
		// TODO Auto-generated method stub
		System.out.println("patchPath : "+pathPath+"    oriPath : "+oriPath);
		
		pathPath = pathPath.replaceAll("/home/socialPro/", "");
		System.out.println("real pathPath : "+pathPath);
		
		oriPath = oriPath.replaceAll("/home/socialPro/", "");
		System.out.println("real oriPath : "+oriPath);
		
		ChannelShell chanShell= (ChannelShell)this.channel;
		if(!chanShell.isConnected())chanShell.connect();
		
		OutputStream inputstream_for_the_channel = ((ChannelShell)this.channel).getOutputStream();
		InputStream outputstream_from_the_channel = ((ChannelShell)this.channel).getInputStream();
		
		/*PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));

		commander.println("patch -l -p0 < "+pathPath+"\n");
		System.out.println("\npatch -l -p0 < "+pathPath+"\n");
		//commander.println("cat "+oriPath);
		commander.println("exit\n");
		commander.close();*/
		
		PrintWriter commander = new PrintWriter(inputstream_for_the_channel);
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));

		commander.println("patch -l -p0 < "+pathPath+"\n");
		System.out.println("\npatch -l -p0 < "+pathPath+"\n");
		commander.flush();
		//commander.println("cat "+oriPath);
		commander.println("exit\n");
		commander.flush();
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("hhhh");
		} while(!chanShell.isEOF());
		
		inputstream_for_the_channel.close();
		return true;
	}

	@Override
	public synchronized boolean copyFile(String remotePath) throws FileNotFoundException,SftpException, IOException, JSchException {
		
		System.out.println("copyFile : DiffFileActionImpl  ..... ");
		// TODO Auto-generated method stub
		String tmpPath = remotePath+"_cp";
		System.out.println(remotePath+"\n"+tmpPath);
		

		ChannelShell chanShell=(ChannelShell) this.channel;
		
		if(chanShell==null)System.out.println("chan is null");
		else System.out.println("chan is not null");
		if(!chanShell.isConnected()){
			chanShell.connect();
		}
		if(chanShell.isConnected()){System.out.println("chan Connected");}else System.out.println("chan connect not!!!");

		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		String commandLine = "cp "+remotePath+" "+tmpPath;
		System.out.println(commandLine);
		commander.println(commandLine);
		commander.println("exit");
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("hhhh");
		} while(!chanShell.isEOF());
		
		String tmp=null;
		while((tmp=br.readLine())!=null){
			System.out.println(tmp);
		}
		
		br.close();
		inputstream_for_the_channel.close();
		outputstream_from_the_channel.close();
		
		return true;
		
	}
	
	@Override
	public boolean copyFileToTmp(String originPath,String fileCode) throws FileNotFoundException,SftpException, IOException, JSchException {
		// TODO Auto-generated method stub
		String tmpPath = this.remoteDiffRootPath+"/"+fileCode;
		System.out.println(originPath+"\n"+tmpPath);
		

		ChannelShell chanShell=(ChannelShell) this.channel;
		
		if(chanShell==null)System.out.println("chan is null");
		else System.out.println("chan is not null");
		if(!chanShell.isConnected()){
			chanShell.connect();
		}
		if(chanShell.isConnected()){System.out.println("chan Connected");}else System.out.println("chan connect not!!!");

		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		String commandLine = "cp "+originPath+" "+tmpPath;
		System.out.println(commandLine);
		commander.println(commandLine);
		commander.println("exit");
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("hhhh");
		} while(!chanShell.isEOF());
		
		String tmp=null;
		while((tmp=br.readLine())!=null){
			System.out.println(tmp);
		}
		
		br.close();
		inputstream_for_the_channel.close();
		outputstream_from_the_channel.close();
		
		return true;
	}
	

	@Override
	public boolean copyTmpFileToPath(String originPath, String fileCode)throws FileNotFoundException, SftpException, IOException,JSchException {
		// TODO Auto-generated method stub
		
		String tmpPath = this.remoteDiffRootPath+"/"+fileCode;
		System.out.println("  copyTmpFileToPath  "+originPath+" \n"+tmpPath);
		

		ChannelShell chanShell=(ChannelShell) this.channel;
		
		if(chanShell==null)System.out.println("chan is null");
		else System.out.println("chan is not null");
		if(!chanShell.isConnected()){
			chanShell.connect();
		}
		if(chanShell.isConnected()){System.out.println("chan Connected");}else System.out.println("chan connect not!!!");

		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		String commandLine1 = "cp "+tmpPath+" "+originPath;
		String commandLine2 = "rm "+tmpPath;
		System.out.println(commandLine1);
		commander.println(commandLine1);
		System.out.println(commandLine2);
		commander.println(commandLine2);
		commander.println("exit");
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("hhhh");
		} while(!chanShell.isEOF());
		
		String tmp=null;
		while((tmp=br.readLine())!=null){
			System.out.println("br.readLind()  : "+tmp);
		}
		
		br.close();
		inputstream_for_the_channel.close();
		outputstream_from_the_channel.close();
		
		return true;
	}
	@Override
	public synchronized String searchDiffFile(String remotePath,String remoteDiffPath) throws JSchException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println(remotePath+"\n"+remoteDiffPath);
		remotePath = remotePath.replaceAll("/home/socialPro/", "");
		System.out.println("real remotePath : "+remotePath);
		
		remoteDiffPath = remoteDiffPath.replaceAll("/home/socialPro/", "");
		System.out.println("real remoteDiffPath : "+remoteDiffPath);
		
		ChannelShell chanShell= (ChannelShell)this.channel;
		if(!chanShell.isConnected())chanShell.connect();
		
		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		String originRemotePath = remotePath+"_ori";
		List<String> commandList  = new ArrayList<String>();
		commandList.add("cp "+remotePath+" "+originRemotePath);
		commandList.add("patch -l -p0 < "+remoteDiffPath);
		commandList.add("diff -ub "+originRemotePath+" "+remotePath);
		
		//copy origin file
		System.out.println(commandList.get(0));
		commander.println(commandList.get(0));

		//patch file
		System.out.println(commandList.get(1));
		commander.println(commandList.get(1));
		
		//diff -y
		System.out.println(commandList.get(2));
		commander.println(commandList.get(2));
		commander.println("exit");
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("=");
			}
		} while(!chanShell.isEOF());
		commander.close();
		
		String res = "";
		String tmp=null;
		
		int cnt=8+commandList.size()*2+1;
		
		for(int i=0;i<cnt;i++){
			br.readLine();
		}
		tmp=br.readLine();
		if(tmp!=null){
			System.out.print("--------");
			System.out.println(tmp);
			res=(String)tmp.subSequence(4,tmp.length())+"\n";
			while((tmp=br.readLine())!=null){
				res+=tmp+"\n";
			}
		}
				
		if(res==null)System.out.println("res is null");
		else System.out.println("res is not null\n"+res+"\n"+cnt);
		
		br.close();
		inputstream_for_the_channel.close();
		outputstream_from_the_channel.close();
		
		return res;
	}

	/*@Override
	public String searchFileContent(String remotePath) throws JSchException,IOException {
		// TODO Auto-generated method stub
		
		ChannelShell chanShell= (ChannelShell)this.channel;
		if(!chanShell.isConnected())chanShell.connect();
		
		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		List<String> commandList  = new ArrayList<String>();
		commander.println("cat "+remotePath);
		commander.println("exit");
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("=");
			}
		} while(!chanShell.isEOF());
		commander.close();
		
		String res = null;
		String tmp=null;
		
		while((tmp=br.readLine())!=null){
			res+=tmp+"\n";
		}		
		
		br.close();
		inputstream_for_the_channel.close();
		outputstream_from_the_channel.close();
		
		return res;
	}*/
	
	@Override
	public boolean copyFile(String remoteFromPath,String remoteToPath) throws FileNotFoundException,SftpException, IOException, JSchException{
		// TODO Auto-generated method stub
		
		System.out.println("from : "+remoteFromPath+"\nto : "+remoteToPath);
		

		ChannelShell chanShell=(ChannelShell) this.channel;
		
		if(chanShell==null)System.out.println("chan is null");
		else System.out.println("chan is not null");
		if(!chanShell.isConnected()){
			chanShell.connect();
		}
		if(chanShell.isConnected()){System.out.println("chan Connected");}else System.out.println("chan connect not!!!");

		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		String commandLine = "cp "+remoteFromPath+" "+remoteToPath;
		System.out.println(commandLine);
		commander.println(commandLine);
		commander.println("exit");
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("hhhh");
		} while(!chanShell.isEOF());
		
		String tmp=null;
		while((tmp=br.readLine())!=null){
			System.out.println(tmp);
		}
		
		br.close();
		inputstream_for_the_channel.close();
		outputstream_from_the_channel.close();
		
		return true;
				
	}

	@Override
	public boolean removeCpFile(String remoteOriFilePath) throws JSchException, IOException {
		// TODO Auto-generated method stub
		String delPath=remoteOriFilePath+"_cp";
		System.out.println("removeCpFile    :::::::   DiffFileActionImpl");
		System.out.println(remoteOriFilePath+"\n"+delPath);
		
		ChannelShell chanShell= (ChannelShell)this.channel;
		if(!chanShell.isConnected())chanShell.connect();
		
		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		//InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		//BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		System.out.println("rm "+delPath);
		commander.println("rm "+delPath);
		commander.println("exit");
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("??");
			}
		} while(!chanShell.isEOF());
		inputstream_for_the_channel.close();
		
		
		return true;
	}

	@Override
	public boolean removeTmpFile(String remoteOriFilePath)throws JSchException, IOException {
		// TODO Auto-generated method stub
		String tmpPath = remoteOriFilePath+"_ori";
		System.out.println(remoteOriFilePath+"\n"+tmpPath);
		
		List<String> commandList  = new ArrayList<String>();
		commandList.add("rm "+remoteOriFilePath);
		commandList.add("mv "+tmpPath+" "+remoteOriFilePath);

		ChannelShell chanShell=(ChannelShell) this.channel;
		if(!chanShell.isConnected())chanShell.connect();
		
		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		//InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		//BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		//rm changed file
		System.out.println(commandList.get(0));
		commander.println(commandList.get(0));
		
		//rename origin file
		System.out.println(commandList.get(1));
		commander.println(commandList.get(1));
		commander.println("exit");
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("??");
			}
		} while(!chanShell.isEOF());

		inputstream_for_the_channel.close();	
		return true;
	}
	public boolean justRemoveTmpFile(String remoteFileCode) throws JSchException, IOException{
		// TODO Auto-generated method stub
		System.out.println(remoteFileCode);
		
		List<String> commandList  = new ArrayList<String>();
		commandList.add("rm "+remoteFileCode);

		ChannelShell chanShell=(ChannelShell) this.channel;
		if(!chanShell.isConnected())chanShell.connect();
		
		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		//InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		//BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		//rm changed file
		System.out.println(commandList.get(0));
		commander.println(commandList.get(0));
		
		//rename origin file
		commander.println("exit");
		commander.close();
		
		do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("??");
			}
		} while(!chanShell.isEOF());

		inputstream_for_the_channel.close();	
		return true;
	}
}
