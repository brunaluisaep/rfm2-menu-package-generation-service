package com.rfm.packagegeneration.dto;

public class SmartRoutingTask {
	private Long smartTaskId;
	private Long smartTaskUK;
	private String msgKey;
	private Long taskTime;
	private Long displayTime;
	private String monitor;
	
	public Long getSmartTaskId() {
		return smartTaskId;
	}
	public void setSmartTaskId(Long smartTaskId) {
		this.smartTaskId = smartTaskId;
	}
	public Long getSmartTaskUK() {
		return smartTaskUK;
	}
	public void setSmartTaskUK(Long smartTaskUK) {
		this.smartTaskUK = smartTaskUK;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public Long getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Long taskTime) {
		this.taskTime = taskTime;
	}
	public Long getDisplayTime() {
		return displayTime;
	}
	public void setDisplayTime(Long displayTime) {
		this.displayTime = displayTime;
	}
	public String getMonitor() {
		return monitor;
	}
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}	
}
