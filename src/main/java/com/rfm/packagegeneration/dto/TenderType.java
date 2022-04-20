package com.rfm.packagegeneration.dto;

import java.util.List;

public class TenderType {
	private String statusCode;
	private String tenderId;
	private String tenderFiscalIndex;
	private String tenderName;
	private TenderChange tenderChange = new TenderChange();
	private String tenderCategory;
	private List<String> tenderFlags;
	private String taxOption;
	private String subtotalOption;
	private String defaultSkimLimit;
	private String defaultHaloLimit;
	private String currencyDecimals;
	private LegacyID electronicPayment = new LegacyID();
	private LegacyID giftCoupon = new LegacyID();
	private LegacyID creditSales = new LegacyID();
	private LegacyID otherPayment = new LegacyID();
	private ForeignCurrency foreignCurrency =  new ForeignCurrency();
	private String TenderRoundingRule;
	private String TenderTypeMinCirculatingAmount;
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getTenderId() {
		return tenderId;
	}
	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}
	public String getTenderFiscalIndex() {
		return tenderFiscalIndex;
	}
	public void setTenderFiscalIndex(String tenderFiscalIndex) {
		this.tenderFiscalIndex = tenderFiscalIndex;
	}
	public String getTenderName() {
		return tenderName;
	}
	public void setTenderName(String tenderName) {
		this.tenderName = tenderName;
	}
	public TenderChange getTenderChange() {
		return tenderChange;
	}
	public void setTenderChange(TenderChange tenderChange) {
		this.tenderChange = tenderChange;
	}
	public String getTenderCategory() {
		return tenderCategory;
	}
	public void setTenderCategory(String tenderCategory) {
		this.tenderCategory = tenderCategory;
	}
	public List<String> getTenderFlags() {
		return tenderFlags;
	}
	public void setTenderFlags(List<String> tenderFlags) {
		this.tenderFlags = tenderFlags;
	}
	public String getTaxOption() {
		return taxOption;
	}
	public void setTaxOption(String taxOption) {
		this.taxOption = taxOption;
	}
	public String getSubtotalOption() {
		return subtotalOption;
	}
	public void setSubtotalOption(String subtotalOption) {
		this.subtotalOption = subtotalOption;
	}
	public String getDefaultSkimLimit() {
		return defaultSkimLimit;
	}
	public void setDefaultSkimLimit(String defaultSkimLimit) {
		this.defaultSkimLimit = defaultSkimLimit;
	}
	public String getDefaultHaloLimit() {
		return defaultHaloLimit;
	}
	public void setDefaultHaloLimit(String defaultHaloLimit) {
		this.defaultHaloLimit = defaultHaloLimit;
	}
	public String getCurrencyDecimals() {
		return currencyDecimals;
	}
	public void setCurrencyDecimals(String currencyDecimals) {
		this.currencyDecimals = currencyDecimals;
	}
	public LegacyID getElectronicPayment() {
		return electronicPayment;
	}
	public void setElectronicPayment(LegacyID electronicPayment) {
		this.electronicPayment = electronicPayment;
	}
	public LegacyID getGiftCoupon() {
		return giftCoupon;
	}
	public void setGiftCoupon(LegacyID giftCoupon) {
		this.giftCoupon = giftCoupon;
	}
	public LegacyID getCreditSales() {
		return creditSales;
	}
	public void setCreditSales(LegacyID creditSales) {
		this.creditSales = creditSales;
	}
	public LegacyID getOtherPayment() {
		return otherPayment;
	}
	public void setOtherPayment(LegacyID otherPayment) {
		this.otherPayment = otherPayment;
	}
	public ForeignCurrency getForeignCurrency() {
		return foreignCurrency;
	}
	public void setForeignCurrency(ForeignCurrency foreignCurrency) {
		this.foreignCurrency = foreignCurrency;
	}
	public String getTenderRoundingRule() {
		return TenderRoundingRule;
	}
	public void setTenderRoundingRule(String tenderRoundingRule) {
		TenderRoundingRule = tenderRoundingRule;
	}
	public String getTenderTypeMinCirculatingAmount() {
		return TenderTypeMinCirculatingAmount;
	}
	public void setTenderTypeMinCirculatingAmount(String tenderTypeMinCirculatingAmount) {
		TenderTypeMinCirculatingAmount = tenderTypeMinCirculatingAmount;
	}
	
	
}
