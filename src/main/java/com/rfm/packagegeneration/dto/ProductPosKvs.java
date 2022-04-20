package com.rfm.packagegeneration.dto;

import java.util.ArrayList;
import java.util.List;


public class ProductPosKvs {
	private List<GenericEntry> sellLocation = new ArrayList<>();
	private List<GenericEntry> salesType =new ArrayList<>();
	private List<GenericEntry> feeExempt =new ArrayList<>();
	private GenericEntry displayAs = new GenericEntry();	
	private GenericEntry equivalent = new GenericEntry();	
	private List<GenericEntry> promotionComplement =new ArrayList<>();
	private List<GenericEntry> sspClassification =new ArrayList<>(); 	  	
	private String displayWaste;	
	private String upsizable;	
	private List<GenericEntry> discountsNotAllowed  =new ArrayList<>();
	private List<GenericEntry> sourceSubstitutionItems =new ArrayList<>();
	private GenericEntry targetSubstitutionItem =new GenericEntry();
	private Long displayOrder;
	private String salable;	 
	private Long includedInBufferEngine;
	private GenericEntry bunBufferConfiguration = new GenericEntry(); 	
	private String bunPrepTypeID;
	private String mutuallyExclusive; 	
	private String menuItemUnit; 	
	private String menuItemBarcode; 	
	private GenericEntry smartReminderGroup = new GenericEntry(); 	
	private String priority;  
	private Long grillable;	 
	private String displayGrillInstructions;	
	private Long autoGrill;
	private Long printGrillSlip;	 
	private GenericEntry dynamicGrillConfiguration = new GenericEntry(); 	
	private Long promoPerItemQuantityLimit;	
	private List<GenericEntry> kvsDisplay =new ArrayList<>();
	
	private GenericEntry productionMenuItemGroup = new GenericEntry(); 	
	private GenericEntry deposit = new GenericEntry();  	
	private String nGABSCode;
	private Long drnkVolId;
	private Long triggerDisplayonORB;	 
	private Long displayNumbersInsteadOfModifiers;
	private Long hasAutoCondiment;  
	private List<GenericEntry> autoCondiment  =new ArrayList<>();	
	private String rtmType;
	private String rtmTypeDesc;
	private Long rtmThreshold; 	
	private Long rtmAutoBumpTimeout; 
	private GenericEntry rtmImage; 	 						   
	private Long rtmPriority; 	
	private Long routeGrillOnly;
	private Long dedicatedCell; 	
	private Long grillCellRangeStart;
	private Long grillCellRangeEnd;
	private GenericEntry displayAsID = new GenericEntry(); 
	private boolean modified;
	private Long grpBundle;
	private Long grpBundleLimit;
	private Long autoGrillConf;
	private Long grillGroup;
	private GenericEntry equivalentAsID = new GenericEntry(); 
	private GenericEntry targetSubstitutionItemCD =new GenericEntry();
	private List<GenericEntry> menuTypAssValue =new ArrayList<>();
	private String smrtReminderPriority;
	private Long autoCondimentDisplay;
	private List<GenericEntry> selectedTags=new ArrayList<>();
	private String displyPosEvent;
	private List<String> applySalesType =new ArrayList<>();
	private String displayDTWposEvent;
	
	public List<GenericEntry> getKvsDisplay() {
		return kvsDisplay;
	}
	public void setKvsDisplay(List<GenericEntry> kvsDisplay) {
		this.kvsDisplay = kvsDisplay;
	}
	
	public Long getDrnkVolId() {
		return drnkVolId;
	}
	public void setDrnkVolId(Long drnkVolId) {
		this.drnkVolId = drnkVolId;
	}
	public boolean isModified() {
		return modified;
	}
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	public Long getGrpBundle() {
		return grpBundle;
	}
	public void setGrpBundle(Long grpBundle) {
		this.grpBundle = grpBundle;
	}
	public Long getAutoGrillConf() {
		return autoGrillConf;
	}
	public void setAutoGrillConf(Long autoGrillConf) {
		this.autoGrillConf = autoGrillConf;
	}
	public GenericEntry getDisplayAsID() {
		return displayAsID;
	}
	public void setDisplayAsID(GenericEntry displayAsID) {
		this.displayAsID = displayAsID;
	}
	public List<GenericEntry> getSellLocation() {
		return sellLocation;
	}
	public void setSellLocation(List<GenericEntry> sellLocation) {
		this.sellLocation = sellLocation;
	}
	public List<GenericEntry> getSalesType() {
		return salesType;
	}
	public void setSalesType(List<GenericEntry> salesType) {
		this.salesType = salesType;
	}
	public List<GenericEntry> getFeeExempt() {
		return feeExempt;
	}
	public void setFeeExempt(List<GenericEntry> feeExempt) {
		this.feeExempt = feeExempt;
	}
	public GenericEntry getDisplayAs() {
		return displayAs;
	}
	public void setDisplayAs(GenericEntry displayAs) {
		this.displayAs = displayAs;
	}
	public GenericEntry getEquivalent() {
		return equivalent;
	}
	public void setEquivalent(GenericEntry equivalent) {
		this.equivalent = equivalent;
	}
	public List<GenericEntry> getPromotionComplement() {
		return promotionComplement;
	}
	public void setPromotionComplement(List<GenericEntry> promotionComplement) {
		this.promotionComplement = promotionComplement;
	}
	public List<GenericEntry> getSspClassification() {
		return sspClassification;
	}
	public void setSspClassification(List<GenericEntry> sspClassification) {
		this.sspClassification = sspClassification;
	}
	public String getDisplayWaste() {
		return displayWaste;
	}
	public void setDisplayWaste(String displayWaste) {
		this.displayWaste = displayWaste;
	}
	public String getUpsizable() {
		return upsizable;
	}
	public void setUpsizable(String upsizable) {
		this.upsizable = upsizable;
	}
	public List<GenericEntry> getDiscountsNotAllowed() {
		return discountsNotAllowed;
	}
	public void setDiscountsNotAllowed(List<GenericEntry> discountsNotAllowed) {
		this.discountsNotAllowed = discountsNotAllowed;
	}
	public List<GenericEntry> getSourceSubstitutionItems() {
		return sourceSubstitutionItems;
	}
	public void setSourceSubstitutionItems(List<GenericEntry> sourceSubstitutionItems) {
		this.sourceSubstitutionItems = sourceSubstitutionItems;
	}
	public GenericEntry getTargetSubstitutionItem() {
		return targetSubstitutionItem;
	}
	public void setTargetSubstitutionItem(GenericEntry genericEntry) {
		this.targetSubstitutionItem = genericEntry;
	}
	public Long getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getSalable() {
		return salable;
	}
	public void setSalable(String salable) {
		this.salable = salable;
	}
	public Long getIncludedInBufferEngine() {
		return includedInBufferEngine;
	}
	public void setIncludedInBufferEngine(Long includedInBufferEngine) {
		this.includedInBufferEngine = includedInBufferEngine;
	}
	public GenericEntry getBunBufferConfiguration() {
		return bunBufferConfiguration;
	}
	public void setBunBufferConfiguration(GenericEntry bunBufferConfiguration) {
		this.bunBufferConfiguration = bunBufferConfiguration;
	}
	public String getMutuallyExclusive() {
		return mutuallyExclusive;
	}
	public void setMutuallyExclusive(String mutuallyExclusive) {
		this.mutuallyExclusive = mutuallyExclusive;
	}
	public String getMenuItemUnit() {
		return menuItemUnit;
	}
	public void setMenuItemUnit(String menuItemUnit) {
		this.menuItemUnit = menuItemUnit;
	}
	public String getMenuItemBarcode() {
		return menuItemBarcode;
	}
	public void setMenuItemBarcode(String menuItemBarcode) {
		this.menuItemBarcode = menuItemBarcode;
	}
	public GenericEntry getSmartReminderGroup() {
		return smartReminderGroup;
	}
	public void setSmartReminderGroup(GenericEntry smartReminderGroup) {
		this.smartReminderGroup = smartReminderGroup;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Long getGrillable() {
		return grillable;
	}
	public void setGrillable(Long grillable) {
		this.grillable = grillable;
	}
	public String getDisplayGrillInstructions() {
		return displayGrillInstructions;
	}
	public void setDisplayGrillInstructions(String displayGrillInstructions) {
		this.displayGrillInstructions = displayGrillInstructions;
	}
	public Long getAutoGrill() {
		return autoGrill;
	}
	public void setAutoGrill(Long autoGrill) {
		this.autoGrill = autoGrill;
	}
	public Long getPrintGrillSlip() {
		return printGrillSlip;
	}
	public void setPrintGrillSlip(Long printGrillSlip) {
		this.printGrillSlip = printGrillSlip;
	}
	public GenericEntry getDynamicGrillConfiguration() {
		return dynamicGrillConfiguration;
	}
	public void setDynamicGrillConfiguration(GenericEntry dynamicGrillConfiguration) {
		this.dynamicGrillConfiguration = dynamicGrillConfiguration;
	}
	public Long getPromoPerItemQuantityLimit() {
		return promoPerItemQuantityLimit;
	}
	public void setPromoPerItemQuantityLimit(Long promoPerItemQuantityLimit) {
		this.promoPerItemQuantityLimit = promoPerItemQuantityLimit;
	}

	public GenericEntry getProductionMenuItemGroup() {
		return productionMenuItemGroup;
	}
	public void setProductionMenuItemGroup(GenericEntry productionMenuItemGroup) {
		this.productionMenuItemGroup = productionMenuItemGroup;
	}
	public GenericEntry getDeposit() {
		return deposit;
	}
	public void setDeposit(GenericEntry deposit) {
		this.deposit = deposit;
	}
	public String getnGABSCode() {
		return nGABSCode;
	}
	public void setnGABSCode(String nGABSCode) {
		this.nGABSCode = nGABSCode;
	}
	public Long getTriggerDisplayonORB() {
		return triggerDisplayonORB;
	}
	public void setTriggerDisplayonORB(Long l) {
		this.triggerDisplayonORB = l;
	}
	public Long getDisplayNumbersInsteadOfModifiers() {
		return displayNumbersInsteadOfModifiers;
	}
	public void setDisplayNumbersInsteadOfModifiers(Long displayNumbersInsteadOfModifiers) {
		this.displayNumbersInsteadOfModifiers = displayNumbersInsteadOfModifiers;
	}
	public Long getHasAutoCondiment() {
		return hasAutoCondiment;
	}
	public void setHasAutoCondiment(Long hasAutoCondiment) {
		this.hasAutoCondiment = hasAutoCondiment;
	}
	public List<GenericEntry> getAutoCondiment() {
		return autoCondiment;
	}
	public void setAutoCondiment(List<GenericEntry> autoCondiment) {
		this.autoCondiment = autoCondiment;
	}
	public String getRtmType() {
		return rtmType;
	}
	public void setRtmType(String rtmType) {
		this.rtmType = rtmType;
	}
	public Long getRtmThreshold() {
		return rtmThreshold;
	}
	public void setRtmThreshold(Long rtmThreshold) {
		this.rtmThreshold = rtmThreshold;
	}
	public Long getRtmAutoBumpTimeout() {
		return rtmAutoBumpTimeout;
	}
	public void setRtmAutoBumpTimeout(Long rtmAutoBumpTimeout) {
		this.rtmAutoBumpTimeout = rtmAutoBumpTimeout;
	}
	public Long getRtmPriority() {
		return rtmPriority;
	}
	public void setRtmPriority(Long rtmPriority) {
		this.rtmPriority = rtmPriority;
	}
	public Long getRouteGrillOnly() {
		return routeGrillOnly;
	}
	public void setRouteGrillOnly(Long routeGrillOnly) {
		this.routeGrillOnly = routeGrillOnly;
	}
	public Long getDedicatedCell() {
		return dedicatedCell;
	}
	public void setDedicatedCell(Long dedicatedCell) {
		this.dedicatedCell = dedicatedCell;
	}
	public Long getGrillCellRangeStart() {
		return grillCellRangeStart;
	}
	public void setGrillCellRangeStart(Long grillCellRangeStart) {
		this.grillCellRangeStart = grillCellRangeStart;
	}
	public Long getGrillCellRangeEnd() {
		return grillCellRangeEnd;
	}
	public void setGrillCellRangeEnd(Long grillCellRangeEnd) {
		this.grillCellRangeEnd = grillCellRangeEnd;
	}
	public String getBunPrepTypeID() {
		return bunPrepTypeID;
	}
	public void setBunPrepTypeID(String bunPrepTypeID) {
		this.bunPrepTypeID = bunPrepTypeID;
	}
	public Long getGrillGroup() {
		return grillGroup;
	}
	public void setGrillGroup(Long grillGroup) {
		this.grillGroup = grillGroup;
	}
	public Long getGrpBundleLimit() {
		return grpBundleLimit;
	}
	public void setGrpBundleLimit(Long grpBundleLimit) {
		this.grpBundleLimit = grpBundleLimit;
	}
	public GenericEntry getEquivalentAsID() {
		return equivalentAsID;
	}
	public void setEquivalentAsID(GenericEntry equivalentAsID) {
		this.equivalentAsID = equivalentAsID;
	}
	public GenericEntry getTargetSubstitutionItemCD() {
		return targetSubstitutionItemCD;
	}
	public void setTargetSubstitutionItemCD(GenericEntry targetSubstitutionItemCD) {
		this.targetSubstitutionItemCD = targetSubstitutionItemCD;
	}
	public List<GenericEntry> getMenuTypAssValue() {
		return menuTypAssValue;
	}
	public void setMenuTypAssValue(List<GenericEntry> menuTypAssValue) {
		this.menuTypAssValue = menuTypAssValue;
	}
	public GenericEntry getRtmImage() {
		return rtmImage;
	}
	public void setRtmImage(GenericEntry rtmImage) {
		this.rtmImage = rtmImage;
	}
	public String getSmrtReminderPriority() {
		return smrtReminderPriority;
	}
	public void setSmrtReminderPriority(String smrtReminderPriority) {
		this.smrtReminderPriority = smrtReminderPriority;
	}
	public Long getAutoCondimentDisplay() {
		return autoCondimentDisplay;
	}
	public void setAutoCondimentDisplay(Long autoCondimentDisplay) {
		this.autoCondimentDisplay = autoCondimentDisplay;
	}
	public List<GenericEntry> getSelectedTags() {
		return selectedTags;
	}
	public void setSelectedTags(List<GenericEntry> selectedTags) {
		this.selectedTags = selectedTags;
	}
	public String getDisplyPosEvent() {
		return displyPosEvent;
	}
	public void setDisplyPosEvent(String displyPosEvent) {
		this.displyPosEvent = displyPosEvent;
	}
	public List<String> getApplySalesType() {
		return applySalesType;
	}
	public void setApplySalesType(List<String> applySalesType) {
		this.applySalesType = applySalesType;
	}
	public String getDisplayDTWposEvent() {
		return displayDTWposEvent;
	}
	public void setDisplayDTWposEvent(String displayDTWposEvent) {
		this.displayDTWposEvent = displayDTWposEvent;
	}
	public String getRtmTypeDesc() {
		return rtmTypeDesc;
	}
	public void setRtmTypeDesc(String rtmTypeDesc) {
		this.rtmTypeDesc = rtmTypeDesc;
	}
}
