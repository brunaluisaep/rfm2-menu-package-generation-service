package com.rfm.packagegeneration.dto;

import java.util.List;

public class WorkflowDTO extends BaseDTO {

	/**
     *
     */
	private static final long serialVersionUID = 39974135740626323L;
	private Long wrkflowAssignId;
	private Long wrkflowId;
	private String wrkflowName;
	private Long eventId;
	private String eventName;
	private List<WorkflowParam> wrkflowParamList;
	private boolean isNewWrkflow;

	/**
	 * @return the isNewWrkflow
	 */
	public boolean isNewWrkflow(){
		return isNewWrkflow;
	}

	/**
	 * @param isNewWrkflow the isNewWrkflow to set
	 */
	public void setNewWrkflow(final boolean isNewWrkflow){
		this.isNewWrkflow = isNewWrkflow;
	}

	/**
	 * @return the eventId
	 */
	public Long getEventId(){
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(final Long eventId){
		this.eventId = eventId;
	}

	/**
	 * @return the eventName
	 */
	public String getEventName(){
		return eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(final String eventName){
		this.eventName = eventName;
	}

	/**
	 * @return the wrkflowAssignId
	 */
	public Long getWrkflowAssignId(){
		return wrkflowAssignId;
	}

	/**
	 * @param wrkflowAssignId the wrkflowAssignId to set
	 */
	public void setWrkflowAssignId(final Long wrkflowAssignId){
		this.wrkflowAssignId = wrkflowAssignId;
	}

	/**
	 * @return the wrkflowId
	 */
	public Long getWrkflowId(){
		return wrkflowId;
	}

	/**
	 * @param wrkflowId the wrkflowId to set
	 */
	public void setWrkflowId(final Long wrkflowId){
		this.wrkflowId = wrkflowId;
	}

	/**
	 * @return the wrkflowName
	 */
	public String getWrkflowName(){
		return wrkflowName;
	}

	/**
	 * @param wrkflowName the wrkflowName to set
	 */
	public void setWrkflowName(final String wrkflowName){
		this.wrkflowName = wrkflowName;
	}

	/**
	 * @return the wrkflowParamList
	 */
	public List<WorkflowParam> getWrkflowParamList(){
		return wrkflowParamList;
	}

	/**
	 * @param wrkflowParamList the wrkflowParamList to set
	 */
	public void setWrkflowParamList(final List<WorkflowParam> wrkflowParamList){
		this.wrkflowParamList = wrkflowParamList;
	}
}

