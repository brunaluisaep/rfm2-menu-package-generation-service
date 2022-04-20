package com.rfm.packagegeneration.dto;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
	private long restaurantId;
	private long restaurntCode;
	private long nodeId;
	private String shortName;
	private List<Set> menuItemSets = new ArrayList<>();
	private List<Set> priceSets = new ArrayList<>();
	private Set menuItemTax;
	
	
	public long getRestaurntCode() {
		return restaurntCode;
	}
	public void setRestaurntCode(long restaurntCode) {
		this.restaurntCode = restaurntCode;
	}
	public long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public List<Set> getMenuItemSets() {
		return menuItemSets;
	}
	public void setMenuItemSets(List<Set> menuItemSets) {
		this.menuItemSets = menuItemSets;
	}
	public List<Set> getPriceSets() {
		return priceSets;
	}
	public void setPriceSets(List<Set> priceSets) {
		this.priceSets = priceSets;
	}
	public Set getMenuItemTax() {
		return menuItemTax;
	}
	public void setMenuItemTax(Set menuItemTax) {
		this.menuItemTax = menuItemTax;
	}
	
}
