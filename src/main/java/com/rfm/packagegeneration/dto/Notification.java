package com.rfm.packagegeneration.dto;

public class Notification {
	
	private String message;
	private String code;
	private String name;
	private String parent;
	private Long type;
	private Long dlySetdtl;
	private Long dlySetId;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getDlySetdtl() {
		return dlySetdtl;
	}
	public void setDlySetdtl(Long dlySetdtl) {
		this.dlySetdtl = dlySetdtl;
	}
	public Long getDlySetId() {
		return dlySetId;
	}
	public void setDlySetId(Long dlySetId) {
		this.dlySetId = dlySetId;
	}

	
}
