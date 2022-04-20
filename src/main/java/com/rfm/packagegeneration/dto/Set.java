package com.rfm.packagegeneration.dto;

public class Set {
	private long setId;
	private String name;
	private long type;
	private String typeName;
	
	public long getSetId() {
		return this.setId;
	}
	public void setSetId(long p) {
		this.setId = p;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
