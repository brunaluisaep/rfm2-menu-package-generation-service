package com.rfm.packagegeneration.dto;

public class DiscountTable {
	private Long discountId;
	private String discountDescription;
	private String discountRate;
	private String discountAllowed;
	private  String discountAmount;
	private String memc;
	private String salesTyp;
	private String taxOption;
	public Long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}
	public String getDiscountDescription() {
		return discountDescription;
	}
	public void setDiscountDescription(String discountDescription) {
		this.discountDescription = discountDescription;
	}
	public String getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}
	public String getDiscountAllowed() {
		return discountAllowed;
	}
	public void setDiscountAllowed(String discountAllowed) {
		this.discountAllowed = discountAllowed;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getMemc() {
		return memc;
	}
	public void setMemc(String memc) {
		this.memc = memc;
	}
	public String getSalesTyp() {
		return salesTyp;
	}
	public void setSalesTyp(String salesTyp) {
		this.salesTyp = salesTyp;
	}
	public String getTaxOption() {
		return taxOption;
	}
	public void setTaxOption(String taxOption) {
		this.taxOption = taxOption;
	}
}
