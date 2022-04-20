package com.rfm.packagegeneration.dto;

public class Size {
	private Long productId;
	
	private String entry;

	private String code;

	private String showDimensionToCustomer;

	private String showDimensionOnRCTLocalPromotion;


	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShowDimensionToCustomer() {
		return showDimensionToCustomer;
	}

	public void setShowDimensionToCustomer(String showDimensionToCustomer) {
		this.showDimensionToCustomer = showDimensionToCustomer;
	}

	public String getShowDimensionOnRCTLocalPromotion() {
		return showDimensionOnRCTLocalPromotion;
	}

	public void setShowDimensionOnRCTLocalPromotion(String showDimensionOnRCTLocalPromotion) {
		this.showDimensionOnRCTLocalPromotion = showDimensionOnRCTLocalPromotion;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}


}
