package com.rfm.packagegeneration.dto;

import java.util.List;

public class ProductCCMSettings {
	private Long autoBundlingPriority;
	private Long ccmFFpriority;
	private String pricingMethod;
	List<Reduction> reduction;

	public Long getAutoBundlingPriority() {
		return autoBundlingPriority;
	}

	public void setAutoBundlingPriority(Long autoBundlingPriority) {
		this.autoBundlingPriority = autoBundlingPriority;
	}

	public Long getCcmFFpriority() {
		return ccmFFpriority;
	}

	public void setCcmFFpriority(Long ccmFFpriority) {
		this.ccmFFpriority = ccmFFpriority;
	}

	public String getPricingMethod() {
		return pricingMethod;
	}

	public void setPricingMethod(String pricingMethod) {
		this.pricingMethod = pricingMethod;
	}

	public List<Reduction> getReduction() {
		return reduction;
	}

	public void setReduction(List<Reduction> reduction) {
		this.reduction = reduction;
	}
}
