package com.rfm.packagegeneration.dto;

public class GttPriority {
	
	private Long promoId;
	private Long promoPriority;
	private Long pkgPrty;
	private Long promoTyp;
	
	
	
	public Long getPromoId() {
		return promoId;
	}
	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}
	public Long getPromoPriority() {
		return promoPriority;
	}
	public void setPromoPriority(Long promoPriority) {
		this.promoPriority = promoPriority;
	}
	public Long getPkgPrty() {
		return pkgPrty;
	}
	public void setPkgPrty(Long pkgPrty) {
		this.pkgPrty = pkgPrty;
	}
	public Long getPromoTyp() {
		return promoTyp;
	}
	public void setPromoTyp(Long promoTyp) {
		this.promoTyp = promoTyp;
	}
	
	public GttPriority() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GttPriority(Long promoId, Long promoPriority, Long pkgPrty, Long promoTyp) {
		super();
		this.promoId = promoId;
		this.promoPriority = promoPriority;
		this.pkgPrty = pkgPrty;
		this.promoTyp = promoTyp;
	}
	@Override
	public String toString() {
		return "GttPriority [promoId=" + promoId + ", promoPriority=" + promoPriority + ", pkgPrty=" + pkgPrty
				+ ", promoTyp=" + promoTyp + "]";
	}
	
	
}
