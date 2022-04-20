package com.rfm.packagegeneration.dto;

public class LogStatus {
	private String effectiveDate;
	private Long infoType;
	private String message;
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Long getInfoType() {
		return infoType;
	}
	public void setInfoType(Long infoType) {
		this.infoType = infoType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
