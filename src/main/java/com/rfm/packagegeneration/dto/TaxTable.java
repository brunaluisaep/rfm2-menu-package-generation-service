package com.rfm.packagegeneration.dto;

import java.util.List;

public class TaxTable {
	private List<TaxType> taxType;
	private List<TaxChain> taxChain;

	public List<TaxType> getTaxType() {
		return taxType;
	}

	public void setTaxType(List<TaxType> taxType) {
		this.taxType = taxType;
	}

	public List<TaxChain> getTaxChain() {
		return taxChain;
	}

	public void setTaxChain(List<TaxChain> taxChain) {
		this.taxChain = taxChain;
	}
}

