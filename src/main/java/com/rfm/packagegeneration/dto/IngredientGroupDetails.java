package com.rfm.packagegeneration.dto;

public class IngredientGroupDetails {
	
	private String name;
	private String mutuallyExclusive;
	private String minQuantity;
	private String maxQuantity;
	private String chargeThreshold;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMutuallyExclusive() {
		return mutuallyExclusive;
	}
	public void setMutuallyExclusive(String mutuallyExclusive) {
		this.mutuallyExclusive = mutuallyExclusive;
	}
	public String getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(String minQuantity) {
		this.minQuantity = minQuantity;
	}
	public String getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(String maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public String getChargeThreshold() {
		return chargeThreshold;
	}
	public void setChargeThreshold(String chargeThreshold) {
		this.chargeThreshold = chargeThreshold;
	}
	
	
}
