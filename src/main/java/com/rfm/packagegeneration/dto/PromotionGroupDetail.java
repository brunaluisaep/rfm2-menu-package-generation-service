package com.rfm.packagegeneration.dto;

import java.util.List;

public class PromotionGroupDetail {
	private String status;
	private String promotionGroupName;
	private Long promoGrpId;
	private Long promoGrpCode;

	private String promoGrpModel;

	
	private List<String> types;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPromotionGroupName() {
		return promotionGroupName;
	}

	public void setPromotionGroupName(String promotionGroupName) {
		this.promotionGroupName = promotionGroupName;
	}

	public Long getPromoGrpId() {
		return promoGrpId;
	}

	public void setPromoGrpId(Long promoGrpId) {
		this.promoGrpId = promoGrpId;
	}

	public Long getPromoGrpCode() {
		return promoGrpCode;
	}

	public void setPromoGrpCode(Long promoGrpCode) {
		this.promoGrpCode = promoGrpCode;
	}


	public String getPromoGrpModel() {
		return promoGrpModel;
	}

	public void setPromoGrpModel(String promoGrpModel) {
		this.promoGrpModel = promoGrpModel;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	
	
	

}
