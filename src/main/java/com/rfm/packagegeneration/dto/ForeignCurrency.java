package com.rfm.packagegeneration.dto;

public class ForeignCurrency {
	private String ExchangeRate;
	private String Orientation;
	private String Precision;
	private String Rounding;
	private String ExchangeMode;
	private String Symbol;
	private String legacyID;
	public String getExchangeRate() {
		return ExchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		ExchangeRate = exchangeRate;
	}
	public String getOrientation() {
		return Orientation;
	}
	public void setOrientation(String orientation) {
		Orientation = orientation;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	public String getRounding() {
		return Rounding;
	}
	public void setRounding(String rounding) {
		Rounding = rounding;
	}
	public String getExchangeMode() {
		return ExchangeMode;
	}
	public void setExchangeMode(String exchangeMode) {
		ExchangeMode = exchangeMode;
	}
	public String getSymbol() {
		return Symbol;
	}
	public void setSymbol(String symbol) {
		Symbol = symbol;
	}
	public String getLegacyID() {
		return legacyID;
	}
	public void setLegacyID(String legacyID) {
		this.legacyID = legacyID;
	}
	
	

}
