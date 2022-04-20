package com.rfm.packagegeneration.dto;

import java.util.List;

public class CategoryDetails {
	private Long code;
	private Long id;
	private String categoryDescription;
	private Long parentCode;
	private Long parentId;
	private String categoryImageName;
	private String dayPart;
	private String colorValue;
	private String enabled;
	private Long categorySequence;
	private List<LanguageDetails> languageDetails;
	
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getCategoryImageName() {
		return categoryImageName;
	}
	public void setCategoryImageName(String categoryImageName) {
		this.categoryImageName = categoryImageName;
	}
	public Long getCategorySequence() {
		return categorySequence;
	}
	public void setCategorySequence(Long categorySequence) {
		this.categorySequence = categorySequence;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public List<LanguageDetails> getLanguageDetails() {
		return languageDetails;
	}
	public void setLanguageDetails(List<LanguageDetails> languageDetails) {
		this.languageDetails = languageDetails;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Long getParentCode() {
		return parentCode;
	}
	public void setParentCode(Long parentCode) {
		this.parentCode = parentCode;
	}
	
	public String getDayPart() {
		return dayPart;
	}
	public void setDayPart(String dayPart) {
		this.dayPart = dayPart;
	}
	public String getColorValue() {
		return colorValue;
	}
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	

}
