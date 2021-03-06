package model;

import java.util.List;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Team.java
//  @ Date : 2015-07-28
//  @ Author : 
//
//




public class Team {
	private String code;
	private String name;
	private String description;
	private String manager;
	private List<String> cooperatorList;
	private List<String> tagList;
	
	
	public Team(String code, String name, String description, String manager,
			List<String> cooperatorList, List<String> tagList) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.manager = manager;
		this.cooperatorList = cooperatorList;
		this.tagList = tagList;
	}
	public Team(String code, String name, String description, String manager) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.manager = manager;
	}
	public Team(String name, String description, String manager,
			List<String> cooperatorList, List<String> tagList) {
		this.name = name;
		this.description = description;
		this.manager = manager;
		this.cooperatorList = cooperatorList;
		this.tagList = tagList;
	}
	public Team(String name, String description, String manager,
			List<String> tagList) {
		this.name = name;
		this.description = description;
		this.manager = manager;
		this.tagList = tagList;
	}
	public Team(String code, String name, String description, String manager,
			List<String> tagList) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.manager = manager;
		this.tagList = tagList;
	}
	public Team(List<String> tagList,String name, String description, String manager,
			List<String> cooperatorList) {
		this.name = name;
		this.description = description;
		this.manager = manager;
		this.cooperatorList = cooperatorList;
		this.tagList = tagList;
	}
	public Team() {
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public List<String> getCooperatorList() {
		return cooperatorList;
	}
	public void setCooperatorList(List<String> cooperatorList) {
		this.cooperatorList = cooperatorList;
	}
	public List<String> getTagList() {
		return tagList;
	}
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
	public boolean addCooperatorList(List<String> coopers){
		for(String cooper : coopers){
			if(!this.cooperatorList.contains(cooper)){
				if(!this.cooperatorList.add(cooper))return false;
			}
		}
		return true;
	}
	
	public boolean addCooperator(String cooper){
		if(!this.cooperatorList.contains(cooper)){
			if(!this.cooperatorList.add(cooper))return false;
		}
		return true;
	}
}
