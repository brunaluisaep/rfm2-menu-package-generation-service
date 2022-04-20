package com.rfm.packagegeneration.dto;

public class GenericEntry {
	private long code;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public GenericEntry(){		
	}
	
	public GenericEntry(long pCode, String pName){
		this.setCode(pCode);
		this.name = pName;
	}
	
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
}
