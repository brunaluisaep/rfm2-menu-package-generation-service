package com.rfm.packagegeneration.dto;

import java.util.ArrayList;
import java.util.List;

public class PriceList {
	
	private List<PriceTag> pricetag = new ArrayList<>();

	public List<PriceTag> getPricetag() {
		return pricetag;
	}

	public void setPricetag(List<PriceTag> pricetag) {
		this.pricetag = pricetag;
	}

}
