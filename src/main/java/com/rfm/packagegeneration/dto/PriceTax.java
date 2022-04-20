package com.rfm.packagegeneration.dto;

public class PriceTax {
	private long prd_id=0;	
	private Double eatin_prc=null;
	private Double tkut_prc=null;
	private Double oth_prc=null;
	private Long eatin_tax_cd;
	private Long eatin_tax_rule;
	private Long eatin_tax_entr;
	private Long tkut_tax_cd;
	private Long tkut_tax_rule;
	private Long tkut_tax_entr;
	private Long oth_tax_cd;
	private Long oth_tax_rule;
	private Long oth_tax_entr;
	private long subs_grp_id;
	
	private boolean hasValue=false;
	
	public Double getEatin_prc() {
		return eatin_prc;
	}
	public void setEatin_prc(Double eatin_prc) {
		this.eatin_prc = eatin_prc;
	}
	public Double getTkut_prc() {
		return tkut_prc;
	}
	public void setTkut_prc(Double tkut_prc) {
		this.tkut_prc = tkut_prc;
	}
	public Double getOth_prc() {
		return oth_prc;
	}
	public void setOth_prc(Double oth_prc) {
		this.oth_prc = oth_prc;
	}
	public Long getEatin_tax_cd() {
		return eatin_tax_cd;
	}
	public void setEatin_tax_cd(Long eatin_tax_cd) {
		this.eatin_tax_cd = eatin_tax_cd;
	}
	public Long getEatin_tax_rule() {
		return eatin_tax_rule;
	}
	public void setEatin_tax_rule(Long eatin_tax_rule) {
		this.eatin_tax_rule = eatin_tax_rule;
	}
	public Long getEatin_tax_entr() {
		return eatin_tax_entr;
	}
	public void setEatin_tax_entr(Long eatin_tax_entr) {
		this.eatin_tax_entr = eatin_tax_entr;
	}
	public Long getTkut_tax_cd() {
		return tkut_tax_cd;
	}
	public void setTkut_tax_cd(Long tkut_tax_cd) {
		this.tkut_tax_cd = tkut_tax_cd;
	}
	public Long getTkut_tax_rule() {
		return tkut_tax_rule;
	}
	public void setTkut_tax_rule(Long tkut_tax_rule) {
		this.tkut_tax_rule = tkut_tax_rule;
	}
	public Long getTkut_tax_entr() {
		return tkut_tax_entr;
	}
	public void setTkut_tax_entr(Long tkut_tax_entr) {
		this.tkut_tax_entr = tkut_tax_entr;
	}
	public Long getOth_tax_cd() {
		return oth_tax_cd;
	}
	public void setOth_tax_cd(Long oth_tax_cd) {
		this.oth_tax_cd = oth_tax_cd;
	}
	public Long getOth_tax_rule() {
		return oth_tax_rule;
	}
	public void setOth_tax_rule(Long oth_tax_rule) {
		this.oth_tax_rule = oth_tax_rule;
	}
	public Long getOth_tax_entr() {
		return oth_tax_entr;
	}
	public void setOth_tax_entr(Long oth_tax_entr) {
		this.oth_tax_entr = oth_tax_entr;
	}
	public long getPrd_id() {
		return prd_id;
	}
	public void setPrd_id(long prd_id) {
		this.prd_id = prd_id;
	}
	public long getSubs_grp_id() {
		return subs_grp_id;
	}
	public void setSubs_grp_id(long subs_grp_id) {
		this.subs_grp_id = subs_grp_id;
	}
	public boolean isHasValue() {
		return hasValue;
	}
	public void setHasValue(boolean hasValue) {
		this.hasValue = hasValue;
	}
}
