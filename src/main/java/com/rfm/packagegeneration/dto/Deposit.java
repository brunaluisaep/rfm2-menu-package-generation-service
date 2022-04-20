package com.rfm.packagegeneration.dto;

public class Deposit {
	
	private Long depositId;
	private String depositName;
	private String depositType;

	private Double eatinValue;
	private Double takeOutValue;
	private Double otherValue;

	private Long eatinTaxCode;
	private Long takeOutTaxCode;
	private Long otherTaxCode;

	private Long eatinTaxRule;
	private Long takeOutTaxRule;
	private Long otherTaxRule;

	private Long eatinTaxTypeEntry;
	private Long takeOutTaxTypeEntry;
	private Long otherTaxTypeEntry;

	private Long eatinTaxChainEntry;
	private Long takeOutTaxChainEntry;
	private Long otherTaxChainEntry;
	
	private String status;

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public String getDepositName() {
		return depositName;
	}

	public void setDepositName(String depositName) {
		this.depositName = depositName;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	
	public Double getEatinValue() {
		return eatinValue;
	}

	public void setEatinValue(Double eatinValue) {
		this.eatinValue = eatinValue;
	}

	public Double getTakeOutValue() {
		return takeOutValue;
	}

	public void setTakeOutValue(Double takeOutValue) {
		this.takeOutValue = takeOutValue;
	}

	public Double getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(Double otherValue) {
		this.otherValue = otherValue;
	}

	public Long getEatinTaxCode() {
		return eatinTaxCode;
	}

	public void setEatinTaxCode(Long eatinTaxCode) {
		this.eatinTaxCode = eatinTaxCode;
	}

	public Long getTakeOutTaxCode() {
		return takeOutTaxCode;
	}

	public void setTakeOutTaxCode(Long takeOutTaxCode) {
		this.takeOutTaxCode = takeOutTaxCode;
	}

	public Long getOtherTaxCode() {
		return otherTaxCode;
	}

	public void setOtherTaxCode(Long otherTaxCode) {
		this.otherTaxCode = otherTaxCode;
	}

	public Long getEatinTaxRule() {
		return eatinTaxRule;
	}

	public void setEatinTaxRule(Long eatinTaxRule) {
		this.eatinTaxRule = eatinTaxRule;
	}

	public Long getTakeOutTaxRule() {
		return takeOutTaxRule;
	}

	public void setTakeOutTaxRule(Long takeOutTaxRule) {
		this.takeOutTaxRule = takeOutTaxRule;
	}

	public Long getOtherTaxRule() {
		return otherTaxRule;
	}

	public void setOtherTaxRule(Long otherTaxRule) {
		this.otherTaxRule = otherTaxRule;
	}

	public Long getEatinTaxTypeEntry() {
		return eatinTaxTypeEntry;
	}

	public void setEatinTaxTypeEntry(Long eatinTaxTypeEntry) {
		this.eatinTaxTypeEntry = eatinTaxTypeEntry;
	}

	public Long getTakeOutTaxTypeEntry() {
		return takeOutTaxTypeEntry;
	}

	public void setTakeOutTaxTypeEntry(Long takeOutTaxTypeEntry) {
		this.takeOutTaxTypeEntry = takeOutTaxTypeEntry;
	}

	public Long getOtherTaxTypeEntry() {
		return otherTaxTypeEntry;
	}

	public void setOtherTaxTypeEntry(Long otherTaxTypeEntry) {
		this.otherTaxTypeEntry = otherTaxTypeEntry;
	}

	public Long getEatinTaxChainEntry() {
		return eatinTaxChainEntry;
	}

	public void setEatinTaxChainEntry(Long eatinTaxChainEntry) {
		this.eatinTaxChainEntry = eatinTaxChainEntry;
	}

	public Long getTakeOutTaxChainEntry() {
		return takeOutTaxChainEntry;
	}

	public void setTakeOutTaxChainEntry(Long takeOutTaxChainEntry) {
		this.takeOutTaxChainEntry = takeOutTaxChainEntry;
	}

	public Long getOtherTaxChainEntry() {
		return otherTaxChainEntry;
	}

	public void setOtherTaxChainEntry(Long otherTaxChainEntry) {
		this.otherTaxChainEntry = otherTaxChainEntry;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Deposit [depositId=" + depositId + ", depositName=" + depositName + ", depositType=" + depositType
				+ ", eatinValue=" + eatinValue + ", takeOutValue=" + takeOutValue + ", otherValue=" + otherValue
				+ ", eatinTaxCode=" + eatinTaxCode + ", takeOutTaxCode=" + takeOutTaxCode + ", otherTaxCode="
				+ otherTaxCode + ", eatinTaxRule=" + eatinTaxRule + ", takeOutTaxRule=" + takeOutTaxRule
				+ ", otherTaxRule=" + otherTaxRule + ", eatinTaxTypeEntry=" + eatinTaxTypeEntry
				+ ", takeOutTaxTypeEntry=" + takeOutTaxTypeEntry + ", otherTaxTypeEntry=" + otherTaxTypeEntry
				+ ", eatinTaxChainEntry=" + eatinTaxChainEntry + ", takeOutTaxChainEntry=" + takeOutTaxChainEntry
				+ ", otherTaxChainEntry=" + otherTaxChainEntry + ", status=" + status + "]";
	}
	
	
	

}
