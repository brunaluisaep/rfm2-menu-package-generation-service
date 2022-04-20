package com.rfm.packagegeneration.dto;

public class SuggestivePromotion {
	
	private Long promoId;
	private Long sugstvId;
	private Long sugstvTyp;
	
	public Long getPromoId() {
		return promoId;
	}
	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}
	public Long getSugstvId() {
		return sugstvId;
	}
	public void setSugstvId(Long sugstvId) {
		this.sugstvId = sugstvId;
	}
	public Long getSugstvTyp() {
		return sugstvTyp;
	}
	public void setSugstvTyp(Long sugstvTyp) {
		this.sugstvTyp = sugstvTyp;
	}
	@Override
	public String toString() {
		return "SuggestivePromotion [promoId=" + promoId + ", sugstvId=" + sugstvId + ", sugstvTyp=" + sugstvTyp + "]";
	}
	
	
	

}
