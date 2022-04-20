package com.rfm.packagegeneration.dto;

import java.util.ArrayList;
import java.util.List;

public class PromotionGroups {
	private List<PromotionGroup> promotionGroup = new ArrayList<>();
	private List<ProductList> list = new ArrayList<>();
	public List<ProductList> getList() {
		return list;
	}
	public void setList(List<ProductList> list) {
		this.list = list;
	}
	public List<PromotionGroup> getPromotionGroup() {
		return promotionGroup;
	}
	public void setPromotionGroup(List<PromotionGroup> promotionGroup) {
		this.promotionGroup = promotionGroup;
	}


}
