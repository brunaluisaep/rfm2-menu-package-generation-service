package com.rfm.packagegeneration.dto;

import java.math.BigDecimal;

public class ChargeRules {
	
	private String orderChannel;
	private Long ruleId;
	private Long dayPartId;
	private BigDecimal totMinThreshold;
	private String binNumber;
	private Long minLimitMinutes;
	private Long maxLimitMinutes;
	private String payType;
	public String getOrderChannel() {
		return orderChannel;
	}
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public Long getDayPartId() {
		return dayPartId;
	}
	public void setDayPartId(Long dayPartId) {
		this.dayPartId = dayPartId;
	}
	public BigDecimal getTotMinThreshold() {
		return totMinThreshold;
	}
	public void setTotMinThreshold(BigDecimal totMinThreshold) {
		this.totMinThreshold = totMinThreshold;
	}
	public String getBinNumber() {
		return binNumber;
	}
	public void setBinNumber(String binNumber) {
		this.binNumber = binNumber;
	}
	public Long getMinLimitMinutes() {
		return minLimitMinutes;
	}
	public void setMinLimitMinutes(Long minLimitMinutes) {
		this.minLimitMinutes = minLimitMinutes;
	}
	public Long getMaxLimitMinutes() {
		return maxLimitMinutes;
	}
	public void setMaxLimitMinutes(Long maxLimitMinutes) {
		this.maxLimitMinutes = maxLimitMinutes;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}

}
