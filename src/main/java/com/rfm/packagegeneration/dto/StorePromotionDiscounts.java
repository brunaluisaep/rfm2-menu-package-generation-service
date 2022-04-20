package com.rfm.packagegeneration.dto;

import java.math.BigDecimal;

public class StorePromotionDiscounts {
	
	private String name;
	private Long sequence;
	private String status;
	private String initialDate;
	private String finalDate;
	private BigDecimal initialOrdTotVal;
	private BigDecimal finalOrdTotVal;
	private String discountType;
	private String discountValue;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSequence() {
		return sequence;
	}
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInitialDate() {
		return initialDate;
	}
	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}
	public String getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}
	public BigDecimal getInitialOrdTotVal() {
		return initialOrdTotVal;
	}
	public void setInitialOrdTotVal(BigDecimal initialOrdTotVal) {
		this.initialOrdTotVal = initialOrdTotVal;
	}
	public BigDecimal getFinalOrdTotVal() {
		return finalOrdTotVal;
	}
	public void setFinalOrdTotVal(BigDecimal finalOrdTotVal) {
		this.finalOrdTotVal = finalOrdTotVal;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}
	
	
}
