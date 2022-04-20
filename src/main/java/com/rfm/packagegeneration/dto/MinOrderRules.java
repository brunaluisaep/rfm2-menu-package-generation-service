package com.rfm.packagegeneration.dto;

import java.math.BigDecimal;

public class MinOrderRules {

	private String orderChannel;
	private Long ruleId;
	private Long dayPartId;
	private BigDecimal orderValue;
	
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
	public BigDecimal getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(BigDecimal orderValue) {
		this.orderValue = orderValue;
	}
	
}
