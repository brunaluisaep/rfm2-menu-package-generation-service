package com.rfm.packagegeneration.dto;

import java.util.ArrayList;
import java.util.List;

public class PriceTag {
	private List<Pricing> Pricing = new ArrayList<>();

	public List<Pricing> getPricing() {
		return Pricing;
	}

	public void setPricing(List<Pricing> pricing) {
		Pricing = pricing;
	}

}
