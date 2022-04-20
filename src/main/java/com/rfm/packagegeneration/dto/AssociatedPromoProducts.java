package com.rfm.packagegeneration.dto;

public class AssociatedPromoProducts {
	
	private Long promoPrdId;
	private Long promoType;
	private Long priority;	
	private Long promoAsscId;      	   
	private String pkgGenCd;
	      
	
	public Long getPromoPrdId() {
		return promoPrdId;
	}
	public void setPromoPrdId(Long promoPrdId) {
		this.promoPrdId = promoPrdId;
	}
	public Long getPromoType() {
		return promoType;
	}
	public void setPromoType(Long promoType) {
		this.promoType = promoType;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public Long getPromoAsscId() {
		return promoAsscId;
	}
	public void setPromoAsscId(Long promoAsscId) {
		this.promoAsscId = promoAsscId;
	}
	public String getPkgGenCd() {
		return pkgGenCd;
	}
	public void setPkgGenCd(String pkgGenCd) {
		this.pkgGenCd = pkgGenCd;
	}
	

}
