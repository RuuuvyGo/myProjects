package remoteAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import factory.ChannelFactory;

public class RemoteFileActionImpl extends BaseRemoteAction implements RemoteFileInsertAction,RemoteFileSearchAction,RemoteFileUpdateAction,RemoteFileDeleteAction{

	private String localRootPath;
	public RemoteFileActionImpl() throws JSchException {
		this.channel=(ChannelSftp)ChannelFactory.getCHANNELFACTORY_INSTANCE().create("sftp");
		this.localRootPath = "C:\\socialPro";
	}

	@Override
	public synchronized void setChannel(Channel channel) {
		// TODO Auto-generated method stub
		
		this.channel  = (ChannelSftp)channel; 	
	}
		

	@Override
	public synchronized boolean deleteFileConent(String remotePath) throws SftpException {
		// TODO Auto-generated method stub
		
		ChannelSftp channelSftp=((ChannelSftp) this.channel);
		channelSftp.rm(remotePath);
		
		
		return false;
	}

	@Override
	public synchronized  boolean updateFileContent(String localPath, String remotePath)  throws SftpException, IOException {
		// TODO Auto-generated method stub
		System.out.println(localPath+"\n"+remotePath);
		
		InputStream fis = null;
		ChannelSftp chan=(ChannelSftp) this.channel;
	
		try {
			if(chan==null){System.out.println("chan is null");}else{System.out.println("chan is not null...");}
			if(!chan.isConnected()){System.out.println("chan is not connected");chan.connect();}else{System.out.println("chan is connected...");}
			fis = new FileInputStream(localPath);
			chan.put(fis, remotePath,ChannelSftp.OVERWRITE);
			 
	        fis.close();
	        return true;
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SftpException(e.hashCode(),"Error .. remoteFileInsertACtion SftpEx");
		}
	}

	@Override
	public synchronized  File searchFile(String remotePath, String remoteRootPath,String localRootPath) throws SftpException, IOException, JSchException {
		// TODO Auto-generated method stub
		
		InputStream in = null;
	    FileOutputStream out = null;
	    File resFile = new File(localRootPath+remotePath);
	    
	    ChannelSftp channelSftp=(ChannelSftp) this.channel;
	    if(!channelSftp.isConnected())channelSftp.connect();
        in = channelSftp.get(remoteRootPath+remotePath);
        
        out = new FileOutputStream(resFile);
        int i;

        while ((i = in.read()) != -1) {
            out.write((char)i);
            
        }
        
        in.close();
        out.close();
        //channelSftp.exit();
	    
		
		return resFile;
	}
	
	@Override
	public File searchDiffFile(String remoteDiffPath,String diffCode) throws SftpException, FileNotFoundException, IOException, JSchException{
		
		String localpath = this.localRootPath+"\\"+diffCode;
		System.out.println(localpath+"    ::    "+remoteDiffPath);
		InputStream in = null;
	    FileOutputStream out = null;
	    File resFile = new File(localpath);
	    
	    ChannelSftp channelSftp=(ChannelSftp) this.channel;
	    if(!channelSftp.isConnected())channelSftp.connect();
        in = channelSftp.get(remoteDiffPath);
        
        out = new FileOutputStream(resFile);
        int i;

        while ((i = in.read()) != -1) {
            out.write((char)i);
            
        }
        
        in.close();
        out.close();
        //channelSftp.exit();
	    
		
		return resFile;
	}
	
	@Override
	public synchronized  File searchFile(String remotePath, String localPath) throws SftpException, IOException, JSchException {
		// TODO Auto-generated method stub
		
		System.out.println("..  여기는 오니...제발  --> "+remotePath+"  ******   "+localPath);
		InputStream in = null;
	    FileOutputStream out = null;
	    File resFile = new File(localPath);
	    
	    ChannelSftp channelSftp=(ChannelSftp) this.channel;
	    if(!channelSftp.isConnected())channelSftp.connect();
        in = channelSftp.get(remotePath);
        
        out = new FileOutputStream(resFile);
        int i;

        while ((i = in.read()) != -1) {
            out.write((char)i);
            
        }
        if(i==-1)System.out.println("down load end...");
        
        in.close();
        out.close();
        //channelSftp.exit();
	    
		
		return resFile;
	}

	@Override
	public String searchFileContent(String remotePath) throws SftpException,IOException, JSchException{

		System.out.println(remotePath);
		
		ChannelShell chanShell=(ChannelShell) ChannelFactory.getCHANNELFACTORY_INSTANCE().create("shell");
		if(!chanShell.isConnected())chanShell.connect();
		
		OutputStream inputstream_for_the_channel = chanShell.getOutputStream();
		InputStream outputstream_from_the_channel = chanShell.getInputStream();
		
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		
		List<String> commandList  = new ArrayList<String>();
		commandList.add("cat "+remotePath);
		
		//copy origin file
		System.out.println(commandList.get(0));
		commander.println(commandList.get(0));
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
				
		if(res==null)System.out.println("res is null");
		else System.out.println("res is not null\n");
		
		br.close();
		inputstream_for_the_channel.close();
		outputstream_from_the_channel.close();
		
		return res;
	}
	
	@Override
	public synchronized boolean insertOriginFileContent(String localPath, String remotePath) throws SftpException, IOException, JSchException {
		//upload File
		// TODO Auto-generated method stub

		System.out.println("!!!!!!!!!!!!!!!!!!insert originFileContent  : RemoteFileActionImpl  [[[[[");
		System.out.println(localPath);
		System.out.println(remotePath);
		InputStream fis = null;
		
		try {
			ChannelSftp chan=(ChannelSftp) this.channel;
			
			if(chan==null)System.out.println("chan is null");
			else System.out.println("chan is not null");
			if(!chan.isConnected()){
				chan.connect();
			}
			if(chan.isConnected()){System.out.println("chan Connected");}else System.out.println("chan connect not!!!");

			fis = new FileInputStream(localPath);
			/*if(fis==null){
				System.out.println("fis is null");
			}else System.out.println("fis is not null");*/
			chan.put(fis, remotePath);
			chan.disconnect();
			
	        return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException("error...remoteFileInsertACtion readlocal..");
		}catch (SftpException e){
			e.printStackTrace();
			throw new SftpException(e.hashCode(),"Error .. remoteFileInsertACtion SftpEx");
			
		}finally{
			System.out.println("설마");
			if(fis!=null){fis.close();}
		}
		
	}

	@Override
	public synchronized  boolean insertCopiedFileContent(String remotrOriPath, String remotePath) throws IOException, JSchException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		System.out.println(remotrOriPath+"\n"+remotePath);
		
		ChannelShell chanShell=(ChannelShell) ChannelFactory.getCHANNELFACTORY_INSTANCE().create("shell");
		
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
		
		String commandLine = "cp "+remotrOriPath+" "+remotePath;
		System.out.println(commandLine);
		commander.println(commandLine);
		commander.println("cat "+remotePath);
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
	public synchronized  Map<String, InputStream> searchFileByFolder(String remoteFolderPath) throws SftpException {
		// TODO Auto-generated method stub

		Map<String,InputStream> resMap  = new HashMap<String, InputStream>();
		Vector<ChannelSftp.LsEntry> filelist = ((ChannelSftp) this.channel).ls(remoteFolderPath);
        for (ChannelSftp.LsEntry file : filelist) {
            if (file.getAttrs().isDir()) {
                continue;
            }
            //do here only for file
            resMap.put(file.getFilename(), ((ChannelSftp) this.channel).get(remoteFolderPath));
        }
		
		return resMap;
	}
}
