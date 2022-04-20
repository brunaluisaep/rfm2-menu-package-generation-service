package com.rfm.packagegeneration.dto;

import java.sql.Timestamp;

public class LayoutBaseDTO extends BaseDTO {
	/**
	 *
	 */
	private String deviceVersion;
	private Long setId;
	private Long dSetId;
	private String setName;
	private String setTypeName;
	private Long instanceId;
	private Long futureInstanceId;
	private Integer status; // Changed for update status functionality
	private boolean isFutureSettingExists;
	private String futureEffectiveDate;
	private boolean isNewSet;
	private boolean isNewBLMSet;
	private boolean isRemoveFutureSetting;
	private boolean isChangeFutureDate;
	private boolean isCopySet;
	private boolean isCreateFutureSetting;
	private boolean isChangeSetStatus;
	private boolean isAllLocalizationsDeleted;
	private LayoutSearchDTO layoutSearchDTO;
	private Long nodeId;
	private String nodeName;
	private Long dataId;
	private Long futureDataId;
	private Long marketId; 
	private Long levelId;
	private Long userId; 
	private Long effectiveDateId;
	private Long blmSetId; // Require for getting BLM set details
	private Long blmInstanceId;// Require for getting BLM set details
	private String blmSetName;// Require for getting BLM set details
	private Long futureEffectiveDateId;
	private Long futureDsetId;
	private String lockingScreenStatusInBlm;
	private Long functionType;
	// Added for CED (will be passed for create, update timestamp)
	private Timestamp currentGMTTimeStamp;
	// Added for CED the object will convert the Date from DB to currentGMTDateAtNode to be shown at UI
	private EffectiveDate effectiveDate;
	private String oldSetName;
	private boolean isChangeSetName;

	/**
	 * @return the isChangeSetName
	 */
	public boolean isChangeSetName() {
		return isChangeSetName;
	}

	/**
	 * @param isChangeSetName the isChangeSetName to set
	 */
	public void setChangeSetName(final boolean isChangeSetName) {
		this.isChangeSetName = isChangeSetName;
	}

	/**
	 * @return the oldSetName
	 */
	public final String getOldSetName() {
		return oldSetName;
	}

	/**
	 * @param oldSetName the oldSetName to set
	 */
	public final void setOldSetName(final String oldSetName) {
		this.oldSetName = oldSetName;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(final Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(final String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the deviceVersion
	 */
	public String getDeviceVersion() {
		return deviceVersion;
	}

	/**
	 * @param deviceVersion the deviceVersion to set
	 */
	public void setDeviceVersion(final String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	/**
	 * @return the futureEffectiveDate
	 */
	public String getFutureEffectiveDate() {
		return futureEffectiveDate;
	}

	/**
	 * @param futureEffectiveDate the futureEffectiveDate to set
	 */
	public void setFutureEffectiveDate(final String futureEffectiveDate) {
		this.futureEffectiveDate = futureEffectiveDate;
	}

	/**
	 * @return the instanceId
	 */
	public Long getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(final Long instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return the isChangeFutureDate
	 */
	public boolean isChangeFutureDate() {
		return isChangeFutureDate;
	}

	/**
	 * @param isChangeFutureDate the isChangeFutureDate to set
	 */
	public void setChangeFutureDate(final boolean isChangeFutureDate) {
		this.isChangeFutureDate = isChangeFutureDate;
	}

	/**
	 * @return the isCopySet
	 */
	public boolean isCopySet() {
		return isCopySet;
	}

	/**
	 * @param isCopySet the isCopySet to set
	 */
	public void setCopySet(final boolean isCopySet) {
		this.isCopySet = isCopySet;
	}

	/**
	 * @return the isCreateFutureSetting
	 */
	public boolean isCreateFutureSetting() {
		return isCreateFutureSetting;
	}

	/**
	 * @param isCreateFutureSetting the isCreateFutureSetting to set
	 */
	public void setCreateFutureSetting(final boolean isCreateFutureSetting) {
		this.isCreateFutureSetting = isCreateFutureSetting;
	}

	/**
	 * @return the isFutureSettingExists
	 */
	public boolean isFutureSettingExists() {
		return isFutureSettingExists;
	}

	/**
	 * @param isFutureSettingExists the isFutureSettingExists to set
	 */
	public void setFutureSettingExists(final boolean isFutureSettingExists) {
		this.isFutureSettingExists = isFutureSettingExists;
	}

	/**
	 * @return the isNewSet
	 */
	public boolean isNewSet() {
		return isNewSet;
	}

	/**
	 * @param isNewSet the isNewSet to set
	 */
	public void setNewSet(final boolean isNewSet) {
		this.isNewSet = isNewSet;
	}

	/**
	 * @return the isRemoveFutureSetting
	 */
	public boolean isRemoveFutureSetting() {
		return isRemoveFutureSetting;
	}

	/**
	 * @param isRemoveFutureSetting the isRemoveFutureSetting to set
	 */
	public void setRemoveFutureSetting(final boolean isRemoveFutureSetting) {
		this.isRemoveFutureSetting = isRemoveFutureSetting;
	}

	/**
	 * @return the setId
	 */
	public Long getSetId() {
		return setId;
	}

	/**
	 * @param setId the setId to set
	 */
	public void setSetId(final Long setId) {
		this.setId = setId;
	}

	/**
	 * @return the setName
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * @param setName the setName to set
	 */
	public void setSetName(final String setName) {
		this.setName = setName;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(final Integer status) {
		this.status = status;
	}

	/**
	 * @return the layoutSearchDTO
	 */
	public LayoutSearchDTO getLayoutSearchDTO() {
		return layoutSearchDTO;
	}

	/**
	 * @param layoutSearchDTO the layoutSearchDTO to set
	 */
	public void setLayoutSearchDTO(final LayoutSearchDTO layoutSearchDTO) {
		this.layoutSearchDTO = layoutSearchDTO;
	}

	/**
	 * @return the dataId
	 */
	public Long getDataId() {
		return dataId;
	}

	/**
	 * @param dataId the dataId to set
	 */
	public void setDataId(final Long dataId) {
		this.dataId = dataId;
	}

	/**
	 * @return the isNewBLMSet
	 */
	public boolean isNewBLMSet() {
		return isNewBLMSet;
	}

	/**
	 * @param isNewBLMSet the isNewBLMSet to set
	 */
	public void setNewBLMSet(final boolean isNewBLMSet) {
		this.isNewBLMSet = isNewBLMSet;
	}

	/**
	 * @return the isChangeSetStatus
	 */
	public boolean isChangeSetStatus() {
		return isChangeSetStatus;
	}

	/**
	 * @param isChangeSetStatus the isChangeSetStatus to set
	 */
	public void setChangeSetStatus(final boolean isChangeSetStatus) {
		this.isChangeSetStatus = isChangeSetStatus;
	}

	/**
	 * @return the levelId
	 */
	public Long getLevelId() {
		return levelId;
	}

	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(final Long levelId) {
		this.levelId = levelId;
	}

	/**
	 * @return the marketId
	 */
	public Long getMarketId() {
		return marketId;
	}

	/**
	 * @param marketId the marketId to set
	 */
	public void setMarketId(final Long marketId) {
		this.marketId = marketId;
	}

	/**
	 * @return the effectiveDateId
	 */
	public Long getEffectiveDateId() {
		return effectiveDateId;
	}

	/**
	 * @param effectiveDateId the effectiveDateId to set
	 */
	public void setEffectiveDateId(final Long effectiveDateId) {
		this.effectiveDateId = effectiveDateId;
	}

	/**
	 * @return the blmInstanceId
	 */
	public Long getBlmInstanceId() {
		return blmInstanceId;
	}

	/**
	 * @param blmInstanceId the blmInstanceId to set
	 */
	public void setBlmInstanceId(final Long blmInstanceId) {
		this.blmInstanceId = blmInstanceId;
	}

	/**
	 * @return the blmSetId
	 */
	public Long getBlmSetId() {
		return blmSetId;
	}

	/**
	 * @param blmSetId the blmSetId to set
	 */
	public void setBlmSetId(final Long blmSetId) {
		this.blmSetId = blmSetId;
	}

	/**
	 * @return the blmSetName
	 */
	public String getBlmSetName() {
		return blmSetName;
	}

	/**
	 * @param blmSetName the blmSetName to set
	 */
	public void setBlmSetName(final String blmSetName) {
		this.blmSetName = blmSetName;
	}

	/**
	 * @return the futureDataId
	 */
	public Long getFutureDataId() {
		return futureDataId;
	}

	/**
	 * @param futureDataId the futureDataId to set
	 */
	public void setFutureDataId(final Long futureDataId) {
		this.futureDataId = futureDataId;
	}

	/**
	 * @return the futureEffectiveDateId
	 */
	public Long getFutureEffectiveDateId() {
		return futureEffectiveDateId;
	}

	/**
	 * @param futureEffectiveDateId the futureEffectiveDateId to set
	 */
	public void setFutureEffectiveDateId(final Long futureEffectiveDateId) {
		this.futureEffectiveDateId = futureEffectiveDateId;
	}

	/**
	 * @return the futureDsetId
	 */
	public Long getFutureDsetId() {
		return futureDsetId;
	}

	/**
	 * @param futureDsetId the futureDsetId to set
	 */
	public void setFutureDsetId(final Long futureDsetId) {
		this.futureDsetId = futureDsetId;
	}

	/**
	 * @return the dSetId
	 */
	public Long getDSetId() {
		return dSetId;
	}

	/**
	 * @param setId the dSetId to set
	 */
	public void setDSetId(final Long setId) {
		dSetId = setId;
	}

	/**
	 * @return the isAllLocalizationsDeleted
	 */
	public boolean isAllLocalizationsDeleted() {
		return isAllLocalizationsDeleted;
	}

	/**
	 * @param isAllLocalizationsDeleted the isAllLocalizationsDeleted to set
	 */
	public void setAllLocalizationsDeleted(final boolean isAllLocalizationsDeleted) {
		this.isAllLocalizationsDeleted = isAllLocalizationsDeleted;
	}

	/**
	 * @return the futureInstanceId
	 */
	public Long getFutureInstanceId() {
		return futureInstanceId;
	}

	/**
	 * @param futureInstanceId the futureInstanceId to set
	 */
	public void setFutureInstanceId(final Long futureInstanceId) {
		this.futureInstanceId = futureInstanceId;
	}

	/**
	 * @return the lockingScreenStatusInBlm
	 */
	public String getLockingScreenStatusInBlm() {
		return lockingScreenStatusInBlm;
	}

	/**
	 * @param lockingScreenStatusInBlm the lockingScreenStatusInBlm to set
	 */
	public void setLockingScreenStatusInBlm(final String lockingScreenStatusInBlm) {
		this.lockingScreenStatusInBlm = lockingScreenStatusInBlm;
	}

	/**
	 * @return the functionType
	 */
	public Long getFunctionType() {
		return functionType;
	}

	/**
	 * @param functionType the functionType to set
	 */
	public void setFunctionType(final Long functionType) {
		this.functionType = functionType;
	}

	/**
	 * @return the setTypeName
	 */
	public String getSetTypeName() {
		return setTypeName;
	}

	/**
	 * @param setTypeName the setTypeName to set
	 */
	public void setSetTypeName(final String setTypeName) {
		this.setTypeName = setTypeName;
	}

	/**
	 * @return the currentGMTTimeStamp
	 */
	public Timestamp getCurrentGMTTimeStamp() {
		return currentGMTTimeStamp;
	}

	/**
	 * @param currentGMTTimeStamp the currentGMTTimeStamp to set
	 */
	public void setCurrentGMTTimeStamp(final Timestamp currentGMTTimeStamp) {
		this.currentGMTTimeStamp = currentGMTTimeStamp;
	}

	/**
	 * @return the effectiveDate
	 */
	public EffectiveDate getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(final EffectiveDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("LayoutBaseDTO [");
		if (deviceVersion != null) {
			builder.append("deviceVersion=").append(deviceVersion).append(", ");
		}
		if (setId != null) {
			builder.append("setId=").append(setId).append(", ");
		}
		if (dSetId != null) {
			builder.append("dSetId=").append(dSetId).append(", ");
		}
		if (setName != null) {
			builder.append("setName=").append(setName).append(", ");
		}
		if (setTypeName != null) {
			builder.append("setTypeName=").append(setTypeName).append(", ");
		}
		if (instanceId != null) {
			builder.append("instanceId=").append(instanceId).append(", ");
		}
		if (futureInstanceId != null) {
			builder.append("futureInstanceId=").append(futureInstanceId).append(", ");
		}
		if (status != null) {
			builder.append("status=").append(status).append(", ");
		}
		builder.append("isFutureSettingExists=").append(isFutureSettingExists).append(", ");
		if (futureEffectiveDate != null) {
			builder.append("futureEffectiveDate=").append(futureEffectiveDate).append(", ");
		}
		builder.append("isNewSet=").append(isNewSet).append(", isNewBLMSet=").append(isNewBLMSet).append(", isRemoveFutureSetting=").append(isRemoveFutureSetting).append(", isChangeFutureDate=").append(isChangeFutureDate).append(", isCopySet=").append(isCopySet).append(", isCreateFutureSetting=").append(isCreateFutureSetting).append(", isChangeSetStatus=").append(isChangeSetStatus)
				.append(", isAllLocalizationsDeleted=").append(isAllLocalizationsDeleted).append(", ");
		if (layoutSearchDTO != null) {
			builder.append("layoutSearchDTO=").append(layoutSearchDTO).append(", ");
		}
		if (nodeId != null) {
			builder.append("nodeId=").append(nodeId).append(", ");
		}
		if (nodeName != null) {
			builder.append("nodeName=").append(nodeName).append(", ");
		}
		if (dataId != null) {
			builder.append("dataId=").append(dataId).append(", ");
		}
		if (futureDataId != null) {
			builder.append("futureDataId=").append(futureDataId).append(", ");
		}
		if (marketId != null) {
			builder.append("marketId=").append(marketId).append(", ");
		}
		if (levelId != null) {
			builder.append("levelId=").append(levelId).append(", ");
		}
		if (userId != null) {
			builder.append("userId=").append(userId).append(", ");
		}
		if (effectiveDateId != null) {
			builder.append("effectiveDateId=").append(effectiveDateId).append(", ");
		}
		if (blmSetId != null) {
			builder.append("blmSetId=").append(blmSetId).append(", ");
		}
		if (blmInstanceId != null) {
			builder.append("blmInstanceId=").append(blmInstanceId).append(", ");
		}
		if (blmSetName != null) {
			builder.append("blmSetName=").append(blmSetName).append(", ");
		}
		if (futureEffectiveDateId != null) {
			builder.append("futureEffectiveDateId=").append(futureEffectiveDateId).append(", ");
		}
		if (futureDsetId != null) {
			builder.append("futureDsetId=").append(futureDsetId).append(", ");
		}
		if (lockingScreenStatusInBlm != null) {
			builder.append("lockingScreenStatusInBlm=").append(lockingScreenStatusInBlm).append(", ");
		}
		if (functionType != null) {
			builder.append("functionType=").append(functionType).append(", ");
		}
		if (currentGMTTimeStamp != null) {
			builder.append("currentGMTTimeStamp=").append(currentGMTTimeStamp).append(", ");
		}
		if (effectiveDate != null) {
			builder.append("effectiveDate=").append(effectiveDate).append(", ");
		}
		if (oldSetName != null) {
			builder.append("oldSetName=").append(oldSetName).append(", ");
		}

		builder.append("isChangeSetName=").append(isChangeSetName).append("]");
		return builder.toString();
}
}