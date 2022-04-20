package com.rfm.packagegeneration.dto;

import java.util.List;

public class TaxDefinition {
	private String legalName;
	private String defaultReceiptHeader;
	private String defaultReceiptFooter;
	private String welcomeMessage;
	private String menuPriceBasis;
	private String calculationType;
	private String displayTaxToCustomer;
	private String displayTaxOnSalePanel;
	private String displayTaxOnReceipt;
	private List<String> grandTotalExclusions;
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getDefaultReceiptHeader() {
		return defaultReceiptHeader;
	}
	public void setDefaultReceiptHeader(String defaultReceiptHeader) {
		this.defaultReceiptHeader = defaultReceiptHeader;
	}
	public String getDefaultReceiptFooter() {
		return defaultReceiptFooter;
	}
	public void setDefaultReceiptFooter(String defaultReceiptFooter) {
		this.defaultReceiptFooter = defaultReceiptFooter;
	}
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	public String getMenuPriceBasis() {
		return menuPriceBasis;
	}
	public void setMenuPriceBasis(String menuPriceBasis) {
		this.menuPriceBasis = menuPriceBasis;
	}
	public String getCalculationType() {
		return calculationType;
	}
	public void setCalculationType(String calculationType) {
		this.calculationType = calculationType;
	}
	public String getDisplayTaxToCustomer() {
		return displayTaxToCustomer;
	}
	public void setDisplayTaxToCustomer(String displayTaxToCustomer) {
		this.displayTaxToCustomer = displayTaxToCustomer;
	}
	public String getDisplayTaxOnSalePanel() {
		return displayTaxOnSalePanel;
	}
	public void setDisplayTaxOnSalePanel(String displayTaxOnSalePanel) {
		this.displayTaxOnSalePanel = displayTaxOnSalePanel;
	}
	public String getDisplayTaxOnReceipt() {
		return displayTaxOnReceipt;
	}
	public void setDisplayTaxOnReceipt(String displayTaxOnReceipt) {
		this.displayTaxOnReceipt = displayTaxOnReceipt;
	}
	public List<String> getGrandTotalExclusions() {
		return grandTotalExclusions;
	}
	public void setGrandTotalExclusions(List<String> grandTotalExclusions) {
		this.grandTotalExclusions = grandTotalExclusions;
	}
}
