package com.rfm.packagegeneration.dto;

public class ProductGroup {
	
	private String grpName;
	private Long grpId;
	private Long grpCode;
	private Long grpType;
	private String status;
	
	private String grpTypName;
	
	private Long promoGrpCode;
	
	
	
	public Long getPromoGrpCode() {
		return promoGrpCode;
	}
	public void setPromoGrpCode(Long promoGrpCode) {
		this.promoGrpCode = promoGrpCode;
	}
	public String getGrpTypName() {
		return grpTypName;
	}
	public void setGrpTypName(String grpTypName) {
		this.grpTypName = grpTypName;
	}
	public String getGrpName() {
		return grpName;
	}
	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}
	public Long getGrpId() {
		return grpId;
	}
	public void setGrpId(Long grpId) {
		this.grpId = grpId;
	}
	public Long getGrpCode() {
		return grpCode;
	}
	public void setGrpCode(Long grpCode) {
		this.grpCode = grpCode;
	}
	public Long getGrpType() {
		return grpType;
	}
	public void setGrpType(Long grpType) {
		this.grpType = grpType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ProductGroup [grpName=" + grpName + ", grpId=" + grpId + ", grpCode=" + grpCode + ", grpType=" + grpType
				+ ", status=" + status + ", grpTypName=" + grpTypName + "]";
	}
	
	
	
	

}
