package com.rfm.packagegeneration.dto;

import java.util.List;

public class PluLargeOrderRules {
	
	private String orderChannel;
	private Long ruleId;
	private Long estDeliveryLimit;
	private String isAdvOrder;
	private String multiRiderAllowed;
	private Long mulRiderCount;
	private String tenderType;
	private String isMaxThreshold;
	private Long quantity;
	private String isConfirmNeed;
	private List<Notification> notifications;
	
	
	public Long getMulRiderCount() {
		return mulRiderCount;
	}
	public void setMulRiderCount(Long mulRiderCount) {
		this.mulRiderCount = mulRiderCount;
	}
	public String getIsConfirmNeed() {
		return isConfirmNeed;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	public void setIsConfirmNeed(String isConfirmNeed) {
		this.isConfirmNeed = isConfirmNeed;
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
	public Long getEstDeliveryLimit() {
		return estDeliveryLimit;
	}
	public void setEstDeliveryLimit(Long estDeliveryLimit) {
		this.estDeliveryLimit = estDeliveryLimit;
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
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
