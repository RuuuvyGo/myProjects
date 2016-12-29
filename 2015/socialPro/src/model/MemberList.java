package model;

import java.util.ArrayList;
import java.util.List;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : MemberList.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class MemberList {
	private List<Member> list;
	
	public MemberList() {
		this.list=  new ArrayList<Member>();
	}
	public MemberList(String nickName, String email, String pw, String school,
			int entranceYear) {
		this.list=  new ArrayList<Member>();
		this.list.add(new Member(nickName, email, pw, school, entranceYear));
	}
	
	public MemberList(String code,String nickName, String email, String pw, String school,
			int entranceYear) {
		this.list=  new ArrayList<Member>();
		this.list.add(new Member(code,nickName, email, pw, school, entranceYear));
	}
	public MemberList(Member member) {
		this.list=  new ArrayList<Member>();
		this.list.add(member);
	}
	
	public boolean addMember(Member member) {
		return this.list.add(member);	
	}
	
	public boolean modifyMemberNickName(String nickName, String newNickName) {
	
		Member mem = this.searchMemberNickName(nickName);
		if(mem==null)return false;
		mem.setNickName(newNickName);
		return true;
	}
	
	public boolean modifyMemberPW(String nickName, String newPW) {
	
		Member mem = this.searchMemberNickName(nickName);
		if(mem==null)return false;
		mem.setPw(newPW);
		return true;
	}
	
	public boolean modifyMemberSchool(String nickName, String newSchool) {
	
		Member mem = this.searchMemberNickName(nickName);
		if(mem==null)return false;
		mem.setSchool(newSchool);;
		return true;
	}
	
	public boolean modifyMemberEmail(String nickName, String newEmail) {
	
		Member mem = this.searchMemberNickName(nickName);
		if(mem==null)return false;
		mem.setEmail(newEmail);
		return true;
	}
	
	public boolean modifyMemberEntranceYear(String nickName, int newEntranceYear) {
		Member mem = this.searchMemberNickName(nickName);
		if(mem==null)return false;
		mem.setEntranceYear(newEntranceYear);
		return true;
	}
	
	public boolean deleteMemberNickName(String nickName) {
	
		Member mem = this.searchMemberNickName(nickName);
		if(mem==null)return false;
		return this.list.remove(mem);
	}
		
	public Member searchMemberNickName(String nickName) {
	
		for(Member mem:this.list){
			if(mem.getNickName().equals(nickName))return mem;
		}return null;
	}
	
	public Member searchMemberNickName(String nickName,String pw) {
		
		for(Member mem:this.list){
			if(mem.getNickName().equals(nickName))
				if(mem.getPw().equals(pw))return mem;
		}return null;
	}
	
	public Member searchMemberEmail(String email) {
	
		for(Member mem:this.list){
			if(mem.getEmail().equals(email))return mem;
		}return null;
	}
	
	public MemberList searchMemberSchool(String school) {
	
		MemberList list = new MemberList();
		for(Member mem:this.list){
			if(mem.getSchool().equals(school))list.addMember(mem);
		}return list;
	}
	
	public MemberList searchMemberSchool(String school, int entranceYear) {
	
		MemberList list = new MemberList();
		for(Member mem:this.list){
			if(mem.getSchool().equals(school))
				if(mem.getEntranceYear()==entranceYear)list.addMember(mem);
		}return list;
	}
	
	public MemberList searchMemberEntranceYear(int entranceYear) {
	
		MemberList list = new MemberList();
		for(Member mem:this.list){
				if(mem.getEntranceYear()==entranceYear)list.addMember(mem);
		}return list;
	}
	public List<Member> getList() {
		return list;
	}
	public void setList(List<Member> list) {
		this.list = list;
	}
	
}