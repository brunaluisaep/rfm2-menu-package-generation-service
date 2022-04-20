package com.rfm.packagegeneration.dto;

import com.rfm.packagegeneration.constants.GeneratorConstant;

public class PackageSmartReminderDTO {
	private Long parentSetId = GeneratorConstant.STR_INVALID_ID_CONST;
	private Long customSetId = GeneratorConstant.STR_INVALID_ID_CONST;
	public Long getParentSetId() {
		return parentSetId;
	}
	public void setParentSetId(Long parentSetId) {
		this.parentSetId = parentSetId;
	}
	public Long getCustomSetId() {
		return customSetId;
	}
	public void setCustomSetId(Long customSetId) {
		this.customSetId = customSetId;
	}
}
