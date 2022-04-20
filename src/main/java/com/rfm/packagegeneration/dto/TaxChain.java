package com.rfm.packagegeneration.dto;

import java.util.List;

public class TaxChain {
	private String statusCode;
	private String taxChainId;
	private String rule;
	private TaxTypeId taxTypeId;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getTaxChainId() {
		return taxChainId;
	}
	public void setTaxChainId(String taxChainId) {
		this.taxChainId = taxChainId;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public TaxTypeId getTaxTypeId() {
		return taxTypeId;
	}
	public void setTaxTypeId(TaxTypeId taxTypeId) {
		this.taxTypeId = taxTypeId;
	}
}
