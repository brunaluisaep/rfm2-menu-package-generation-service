package com.rfm.packagegeneration.dto;

import java.math.BigDecimal;
import java.util.List;

public class LargeOrderRules {
	
	private String orderChannel;
	private Long ruleId;
	private BigDecimal totMinThreshold;
	private Long estDeliveryLimit;
	private String isConfirmNeed;
	private String isAdvOrder;
	private String multiRiderAllowed;
	private Long minRiderCount;
	private String tenderType;
	private String isMaxThreshold;
	private List<Notification> notifications;
	
	
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
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
	public BigDecimal getTotMinThreshold() {
		return totMinThreshold;
	}
	public void setTotMinThreshold(BigDecimal totMinThreshold) {
		this.totMinThreshold = totMinThreshold;
	}
	public Long getEstDeliveryLimit() {
		return estDeliveryLimit;
	}
	public void setEstDeliveryLimit(Long estDeliveryLimit) {
		this.estDeliveryLimit = estDeliveryLimit;
	}
	public String getIsConfirmNeed() {
		return isConfirmNeed;
	}
	public void setIsConfirmNeed(String isConfirmNeed) {
		this.isConfirmNeed = isConfirmNeed;
	}
	public String getIsAdvOrder() {
		return isAdvOrder;
	}
	public void setIsAdvOrder(String isAdvOrder) {
		this.isAdvOrder = isAdvOrder;
	}
	public String getMultiRiderAllowed() {
		return multiRiderAllowed;
	}
	public void setMultiRiderAllowed(String multiRiderAllowed) {
		this.multiRiderAllowed = multiRiderAllowed;
	}
	public Long getMinRiderCount() {
		return minRiderCount;
	}
	public void setMinRiderCount(Long minRiderCount) {
		this.minRiderCount = minRiderCount;
	}
	public String getTenderType() {
		return tenderType;
	}
	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}
	public String getIsMaxThreshold() {
		return isMaxThreshold;
	}
	public void setIsMaxThreshold(String isMaxThreshold) {
		this.isMaxThreshold = isMaxThreshold;
	}

}
