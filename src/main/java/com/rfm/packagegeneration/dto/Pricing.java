package com.rfm.packagegeneration.dto;

import java.util.ArrayList;
import java.util.List;

public class Pricing {

	private String priceCode;
	private String price;
	private List<Tax> tax = new ArrayList<>();

	public String getPriceCode() {
		return priceCode;
	}

	public void setPriceCode(String priceCode) {
		this.priceCode = priceCode;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<Tax> getTax() {
		return tax;
	}

	public void setTax(List<Tax> tax) {
		this.tax = tax;
	}

}
