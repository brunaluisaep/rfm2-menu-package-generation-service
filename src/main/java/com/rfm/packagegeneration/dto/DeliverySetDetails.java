package com.rfm.packagegeneration.dto;

import java.util.List;

public class DeliverySetDetails {
	private Long deliverySetId;
	private List<DeliveryOrderingHours> deliveryOrderingHours;
	private List<DeliveryOrderingHours> deliveryAdvanceOrderingHours;
	private List<CustomDayPart> customDayparts;
	private List<ChargeRules> deliveryChargeRules;
	private List<MinOrderRules> minimumOrderValueRules;
	private List<LargeOrderRules> largeOrderRules;
	private List<PluLargeOrderRules> pluLargeOrderRules;
	private String largeOrderAllowed;
	
	

	public List<PluLargeOrderRules> getPluLargeOrderRules() {
		return pluLargeOrderRules;
	}

	public void setPluLargeOrderRules(List<PluLargeOrderRules> pluLargeOrderRules) {
		this.pluLargeOrderRules = pluLargeOrderRules;
	}

	public String getLargeOrderAllowed() {
		return largeOrderAllowed;
	}

	public void setLargeOrderAllowed(String largeOrderAllowed) {
		this.largeOrderAllowed = largeOrderAllowed;
	}

	public Long getDeliverySetId() {
		return deliverySetId;
	}

	public void setDeliverySetId(Long deliverySetId) {
		this.deliverySetId = deliverySetId;
	}

	public List<DeliveryOrderingHours> getDeliveryOrderingHours() {
		return deliveryOrderingHours;
	}

	public void setDeliveryOrderingHours(List<DeliveryOrderingHours> deliveryOrderingHours) {
		this.deliveryOrderingHours = deliveryOrderingHours;
	}

	public List<DeliveryOrderingHours> getDeliveryAdvanceOrderingHours() {
		return deliveryAdvanceOrderingHours;
	}

	public void setDeliveryAdvanceOrderingHours(List<DeliveryOrderingHours> deliveryAdvanceOrderingHours) {
		this.deliveryAdvanceOrderingHours = deliveryAdvanceOrderingHours;
	}

	public List<CustomDayPart> getCustomDayparts() {
		return customDayparts;
	}

	public void setCustomDayparts(List<CustomDayPart> customDayparts) {
		this.customDayparts = customDayparts;
	}

	public List<ChargeRules> getDeliveryChargeRules() {
		return deliveryChargeRules;
	}

	public void setDeliveryChargeRules(List<ChargeRules> deliveryChargeRules) {
		this.deliveryChargeRules = deliveryChargeRules;
	}

	public List<MinOrderRules> getMinimumOrderValueRules() {
		return minimumOrderValueRules;
	}

	public void setMinimumOrderValueRules(List<MinOrderRules> minimumOrderValueRules) {
		this.minimumOrderValueRules = minimumOrderValueRules;
	}

	public List<LargeOrderRules> getLargeOrderRules() {
		return largeOrderRules;
	}

	public void setLargeOrderRules(List<LargeOrderRules> largeOrderRules) {
		this.largeOrderRules = largeOrderRules;
	}


}