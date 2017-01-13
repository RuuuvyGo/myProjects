package action;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import model.Member;
import socialProExceptions.DAOException;
import socialProExceptions.MemberException;

public interface MemberInsertAction {

	public Member insertMember(Member model) throws DAOException, SftpException, JSchException, MemberException;
}
