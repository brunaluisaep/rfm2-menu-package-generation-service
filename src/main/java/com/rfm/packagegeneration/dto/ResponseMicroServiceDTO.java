package com.rfm.packagegeneration.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
public class ResponseMicroServiceDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9079709983340767876L;
	private List<LogStatus> logStatusList;
	
	private boolean storeDBGenerated;
	private boolean productDBGenerated;
	private boolean namesDBGenerated;
	private boolean screenHOTGenerated;
	private boolean screenPOSGenerated; 
    private boolean isGenerated;
    
    @JsonInclude(Include.NON_NULL)
    private Boolean promotionDBGenerated;
    
    @JsonIgnore
    private Map<Long, ProductDetails> allProductsClone = new HashMap<>();
    public Map<Long, ProductDetails> getAllProductsClone() {
		return allProductsClone;
	}

	public void setAllProductsClone(Map<Long, ProductDetails> allProductsClone) {
		this.allProductsClone = allProductsClone;
	}


    public boolean isGenerated() {
		return isGenerated;
	}

	public void setGenerated(boolean isGenerated) {
		this.isGenerated = isGenerated;
	}

	public List<LogStatus> getLogStatusList() {
		return logStatusList;
	}

	public void setLogStatusList(List<LogStatus> logStatusList) {
		this.logStatusList = logStatusList;
	}

	public boolean isStoreDBGenerated() {
		return storeDBGenerated;
	}

	public void setStoreDBGenerated(boolean storeDBGenerated) {
		this.storeDBGenerated = storeDBGenerated;
	}

	public boolean isProductDBGenerated() {
		return productDBGenerated;
	}

	public void setProductDBGenerated(boolean productDBGenerated) {
		this.productDBGenerated = productDBGenerated;
	}

	public boolean isNamesDBGenerated() {
		return namesDBGenerated;
	}

	public void setNamesDBGenerated(boolean namesDBGenerated) {
		this.namesDBGenerated = namesDBGenerated;
	}

	public boolean isScreenHOTGenerated() {
		return screenHOTGenerated;
	}

	public void setScreenHOTGenerated(boolean screenHOTGenerated) {
		this.screenHOTGenerated = screenHOTGenerated;
	}

	public boolean isScreenPOSGenerated() {
		return screenPOSGenerated;
	}

	public void setScreenPOSGenerated(boolean screenPOSGenerated) {
		this.screenPOSGenerated = screenPOSGenerated;
	}

	public Boolean isPromotionDBGenerated() {
		return promotionDBGenerated;
	}

	public void setPromotionDBGenerated(Boolean promotionDBGenerated) {
		this.promotionDBGenerated = promotionDBGenerated;
	}
	
	
}
