package com.rfm.packagegeneration.dto;

import org.dom4j.Element;

public class GeneratorDefinedValues {
	private String timeStamp;
	private String effectiveDatePath;
	private boolean doProductsExist;
	private boolean namesDBGenerated;
	private boolean productDBGenerated;
	private boolean screenPOSGenerated;
	private boolean screenHOTGenerated; 
	private boolean storeDBGenerated;
	

	public boolean isStoreDBGenerated() {
		return storeDBGenerated;
	}

	public void setStoreDBGenerated(boolean storeDBGenerated) {
		this.storeDBGenerated = storeDBGenerated;
	}

	public boolean isScreenPOSGenerated() {
		return screenPOSGenerated;
	}

	public void setScreenPOSGenerated(boolean screenPOSGenerated) {
		this.screenPOSGenerated = screenPOSGenerated;
	}

	public boolean isScreenHOTGenerated() {
		return screenHOTGenerated;
	}

	public void setScreenHOTGenerated(boolean screenHOTGenerated) {
		this.screenHOTGenerated = screenHOTGenerated;
	}

	public boolean isNamesDBGenerated() {
		return namesDBGenerated;
	}

	public void setNamesDBGenerated(final boolean namesDBGenerated) {
		this.namesDBGenerated = namesDBGenerated;
	}

	
	public String getEffectiveDatePath() {
		return effectiveDatePath;
	}

	public void setEffectiveDatePath(final String effectiveDatePath) {
		this.effectiveDatePath = effectiveDatePath;
	}
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(final String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean isDoProductsExist() {
		return doProductsExist;
	}
	public void setDoProductsExist(final boolean doProductsExist) {
		this.doProductsExist = doProductsExist;
	}

	public boolean isProductDBGenerated() {
		return productDBGenerated;
	}

	public void setProductDBGenerated(boolean productDBGenerated) {
		this.productDBGenerated = productDBGenerated;
	}

	public Element getErrorElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
