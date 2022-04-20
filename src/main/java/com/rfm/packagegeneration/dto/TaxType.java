package com.rfm.packagegeneration.dto;

import java.util.List;
import java.util.Map;

public class TaxType {
	private String statusCode;
	private String taxTypeCode;
	private String taxDescription;
	private String taxRate;
	private String taxBasis;
	private String taxCalcType;
	private String rounding;
	private String precision;
	private String rule;
	private List<Map<String, Object>> taxBreakDown;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getTaxTypeCode() {
		return taxTypeCode;
	}
	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}
	public String getTaxDescription() {
		return taxDescription;
	}
	public void setTaxDescription(String taxDescription) {
		this.taxDescription = taxDescription;
	}
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	public String getTaxBasis() {
		return taxBasis;
	}
	public void setTaxBasis(String taxBasis) {
		this.taxBasis = taxBasis;
	}
	public String getTaxCalcType() {
		return taxCalcType;
	}
	public void setTaxCalcType(String taxCalcType) {
		this.taxCalcType = taxCalcType;
	}
	public String getRounding() {
		return rounding;
	}
	public void setRounding(String rounding) {
		this.rounding = rounding;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public List<Map<String, Object>> getTaxBreakDown() {
		return taxBreakDown;
	}
	public void setTaxBreakDown(List<Map<String, Object>> taxBreakDown) {
		this.taxBreakDown = taxBreakDown;
	}
}
