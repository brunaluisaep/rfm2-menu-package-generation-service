package com.rfm.packagegeneration.dto;

public class Category {
	
	private String categoryID;
	private String sequence;
	private String displaySizeSelection;
	public String getDisplaySizeSelection() {
		return displaySizeSelection;
	}
	public void setDisplaySizeSelection(String displaySizeSelection) {
		this.displaySizeSelection = displaySizeSelection;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
