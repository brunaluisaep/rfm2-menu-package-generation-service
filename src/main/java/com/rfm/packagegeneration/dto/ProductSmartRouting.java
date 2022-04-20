package com.rfm.packagegeneration.dto;

import java.util.List;

import com.rfm.packagegeneration.service.CytGroupDisplayOrder;

public class ProductSmartRouting {
	
	
	private String cytItem;
	private String cytIngredientTyp;
	private String cytIngredientGroup;
	private Long cookTime;
	private Long dressPrepTime;
	private String deliverEarlyEnabled;
	private List<SmartRoutingTask> smartRoutingTasks;
	private List<CytGroupDisplayOrder> cytGroupDisplayOrder;
	
	public String getCytItem() {
		return cytItem;
	}
	public void setCytItem(String cytItem) {
		this.cytItem = cytItem;
	}
	public String getCytIngredientTyp() {
		return cytIngredientTyp;
	}
	public void setCytIngredientTyp(String cytIngredientTyp) {
		this.cytIngredientTyp = cytIngredientTyp;
	}
	public Long getCookTime() {
		return cookTime;
	}
	public void setCookTime(Long cookTime) {
		this.cookTime = cookTime;
	}
	public Long getDressPrepTime() {
		return dressPrepTime;
	}
	public void setDressPrepTime(Long dressPrepTime) {
		this.dressPrepTime = dressPrepTime;
	}
	public String getDeliverEarlyEnabled() {
		return deliverEarlyEnabled;
	}
	public void setDeliverEarlyEnabled(String deliverEarlyEnabled) {
		this.deliverEarlyEnabled = deliverEarlyEnabled;
	}
	public List<SmartRoutingTask> getSmartRoutingTasks() {
		return smartRoutingTasks;
	}
	public void setSmartRoutingTasks(List<SmartRoutingTask> smartRoutingTasks) {
		this.smartRoutingTasks = smartRoutingTasks;
	}
	public List<CytGroupDisplayOrder> getCytGroupDisplayOrder() {
		return cytGroupDisplayOrder;
	}
	public void setCytGroupDisplayOrder(List<CytGroupDisplayOrder> cytGroupDisplayOrder) {
		this.cytGroupDisplayOrder = cytGroupDisplayOrder;
	}
	public String getCytIngredientGroup() {
		return cytIngredientGroup;
	}
	public void setCytIngredientGroup(String cytIngredientGroup) {
		this.cytIngredientGroup = cytIngredientGroup;
	}
	
	
}
