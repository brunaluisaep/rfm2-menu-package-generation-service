package com.rfm.packagegeneration.dto;

public class ProductDetails {
	private Long productId;
	private Long productCode;
	private String bgPressedColor;
	private String fgPressedColor;
	private String bgNoramlColor;
	private String captionName;
	private int active;
	private int approvalStatus; 
	private Long auxiliaryMenuItem;
	private Long prodPRGGroup;
	
	public Long getProdPRGGroup() {
		return prodPRGGroup;
	}
	public void setProdPRGGroup(Long prodPRGGroup) {
		this.prodPRGGroup = prodPRGGroup;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public Long getAuxiliaryMenuItem() {
		return auxiliaryMenuItem;
	}
	public void setAuxiliaryMenuItem(Long auxiliaryMenuItem) {
		this.auxiliaryMenuItem = auxiliaryMenuItem;
	}
	
	public String getCaptionName() {
		return captionName;
	}
	public void setCaptionName(String captionName) {
		this.captionName = captionName;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductCode() {
		return productCode;
	}
	public void setProductCode(Long productCode) {
		this.productCode = productCode;
	}
	public String getBgPressedColor() {
		return bgPressedColor;
	}
	public void setBgPressedColor(String bgPressedColor) {
		this.bgPressedColor = bgPressedColor;
	}
	public String getFgPressedColor() {
		return fgPressedColor;
	}
	public void setFgPressedColor(String fgPressedColor) {
		this.fgPressedColor = fgPressedColor;
	}
	public String getBgNoramlColor() {
		return bgNoramlColor;
	}
	public void setBgNoramlColor(String bgNoramlColor) {
		this.bgNoramlColor = bgNoramlColor;
	}
	public String getFgNoramlColor() {
		return fgNoramlColor;
	}
	public void setFgNoramlColor(String fgNoramlColor) {
		this.fgNoramlColor = fgNoramlColor;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	private String fgNoramlColor;
	private String imageName;
}
