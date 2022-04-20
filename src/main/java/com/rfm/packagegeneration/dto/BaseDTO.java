package com.rfm.packagegeneration.dto;

import java.sql.Timestamp;

public class BaseDTO {

	private Timestamp createTimeStamp;
	private String createUserId;
	private Timestamp updateTimeStamp;
	private String updateUserId;
	private Timestamp deleteTimeStamp;
	private String deleteUserId;
	private Integer deleteFlag;
	// Added for updating timestamp in sets creating duplicate-- Swapnesh
	private Long dupSetId;

	/**
	 * Getter method for getting the Create Time Stamp.
	 *
	 * @return createTimeStamp
	 */
	public Timestamp getCreateTimeStamp(){
		return createTimeStamp;
	}

	/**
	 * Setter method for setting the Create Time Stamp..
	 *
	 * @param createTimeStamp Timestamp
	 */
	public void setCreateTimeStamp(final Timestamp createTimeStamp){
		this.createTimeStamp = createTimeStamp;
	}

	/**
	 * Getter method for getting the Create User Id.
	 *
	 * @return createUserId
	 */
	public String getCreateUserId(){
		return createUserId;
	}

	/**
	 * Setter method for setting the Create User Id.
	 *
	 * @param createUserId String
	 */
	public void setCreateUserId(final String createUserId){
		this.createUserId = createUserId;
	}

	/**
	 * Getter method for getting the Delete Time Stamp.
	 *
	 * @return deleteTimeStamp
	 */
	public Timestamp getDeleteTimeStamp(){
		return deleteTimeStamp;
	}

	/**
	 * Setter method for setting the Delete Time Stamp.
	 *
	 * @param deleteTimeStamp Timestamp
	 */
	public void setDeleteTimeStamp(final Timestamp deleteTimeStamp){
		this.deleteTimeStamp = deleteTimeStamp;
	}

	/**
	 * Getter method for getting the Delete User Id.
	 *
	 * @return deleteUserId
	 */
	public String getDeleteUserId(){
		return deleteUserId;
	}

	/**
	 * Setter method for setting the Delete User Id.
	 *
	 * @param deleteUserId String
	 */
	public void setDeleteUserId(final String deleteUserId){
		this.deleteUserId = deleteUserId;
	}

	/**
	 * Getter method for getting the Update Time Stamp.
	 *
	 * @return updateTimeStamp
	 */
	public Timestamp getUpdateTimeStamp(){
		return updateTimeStamp;
	}

	/**
	 * Setter method for setting the Update Time Stamp.
	 *
	 * @param updateTimeStamp Timestamp
	 */
	public void setUpdateTimeStamp(final Timestamp updateTimeStamp){
		this.updateTimeStamp = updateTimeStamp;
	}

	/**
	 * Getter method for getting the Update User Id.
	 *
	 * @return updateUserId
	 */
	public String getUpdateUserId(){
		return updateUserId;
	}

	/**
	 * Setter method for setting the Update User Id.
	 *
	 * @param updateUserId String
	 */
	public void setUpdateUserId(final String updateUserId){
		this.updateUserId = updateUserId;
	}

	/**
	 * Getter method for getting the Delete Flag.
	 *
	 * @return deleteFlag
	 */
	public Integer getDeleteFlag(){
		return deleteFlag;
	}

	/**
	 * Setter method for setting the Delete Flag.
	 *
	 * @param deleteFlag Integer
	 */
	public void setDeleteFlag(final Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return the duplicate setId
	 */
	public Long getDupSetId(){
		return dupSetId;
	}

	/**
	 * @param dupSetId Long
	 */
	public void setDupSetId(final Long dupSetId){
		this.dupSetId = dupSetId;
	}

	
	public BaseDTO clone(BaseDTO clone){
		if(clone == null){
			clone = new BaseDTO(); 
		}
		clone.createTimeStamp = createTimeStamp;
		clone.createUserId = createUserId;
		clone.updateTimeStamp = updateTimeStamp;
		clone.updateUserId = updateUserId;
		clone.deleteTimeStamp = deleteTimeStamp;
		clone.deleteUserId = deleteUserId;
		clone.deleteFlag = deleteFlag;
		clone.dupSetId = dupSetId;
		return clone;
	}
}
