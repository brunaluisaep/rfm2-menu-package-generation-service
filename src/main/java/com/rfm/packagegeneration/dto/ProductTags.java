package com.rfm.packagegeneration.dto;

public class ProductTags {

	private String productTagName;
	private Long status;
	private Long prdId;
	private Long tagId;
	
	public String getProductTagName() {
		return productTagName;
	}
	public void setProductTagName(String productTagName) {
		this.productTagName = productTagName;
	}
	
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getPrdId() {
		return prdId;
	}
	public void setPrdId(Long prdId) {
		this.prdId = prdId;
	}
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	
	
}
