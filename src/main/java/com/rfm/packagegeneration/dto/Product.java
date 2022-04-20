package com.rfm.packagegeneration.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Product {
	
	private Long productId;

	private List <Size> sizeSelection = new ArrayList<>() ;
	private List <Category> categories = new ArrayList<>();
	private List <Code> ProductGroups = new ArrayList<>(); //Added for RFMP-9823
	private List <PromotionGroup> promotionGroups= new ArrayList<>();
	private List<Item>substitutionList=new ArrayList<>();
	private ProductPresentation productPresentation;
	private ProductPosKvs productPosKvs;
	private ProductSmartRouting productSmartRouting;
	private List<Parameter> parameters;
	private ProductAbsSettings productAbsSettings;
	private int active;
	private int approvalStatus;
	private Production production;
	private ProductCCMSettings productCCMSettings;
	private List <PriceTag> priceList = new ArrayList<>();
	private List<ProductPromotionRange> timeRestrictions;
	private Long prmoMenuItem;
	private Long prmoChoice; 
	private LocalDate promoStartDate;
	private LocalDate promoEndDate;
	private Long promoInstId;
	private ProductPromotionRange productPromotionRange;
	private List<ProductGeneralSettingMenuItemNames> productGeneralSettingNamesList;
	private List<ProductShortCutSettings> productShortCutSettings;
	private List<ProductTags> productTagsList;
	private List<AssociatedPromoProducts> associatedPromoProducts;
	private List<AssociatedCategories> associatedCategories;
	

	//general settings
	private Long productCode;
	private Long prdInstId;
	private String productType;
	private String productClass;
	private String productClassId;
	private String ProductCategory;
	private String productCategoryId;
	private String familyGroup;
	private String choiceGroup;
	private String dayPartCode;
	private Long primaryMenuItemCode;
	private String secondaryMenuItem;
	private Long auxiliaryMenuItem;
	private Long parentPromotionItem;
	private Long maxIngredients;
	private String stationGroup;
	
	private List<TimeFrames> timeAvailableForSales;
	//menuTypeAssociation
	private Long breakfast;
	private Long lunch;
	private Long dinner;
	private Long overnight;

	//deliveryFeeDetails
	private Long feeDeliveryId;
	private Double feePercentage;
	private String feeName;
	private String feemaxthreshold;
	private String feeminthreshold;
	
	private List<Component> components;
	private String xmlVersion;
	private Long xmlMaxSize;
	
	
	private int countPriceSubtitution=0;
	private int counTaxSubtitution=0;
	
    private BigDecimal reuseDepositeEating;
    private BigDecimal reuseDepositeTakeout;
	
	public Long getXmlMaxSize() {
		return xmlMaxSize;
	}
	public void setXmlMaxSize(Long xmlMaxSize) {
		this.xmlMaxSize = xmlMaxSize;
	}
	public String getXmlVersion() {
		return xmlVersion;
	}
	public void setXmlVersion(String xmlVersion) {
		this.xmlVersion = xmlVersion;
	}

	public List<AssociatedPromoProducts> getAssociatedPromoProducts() {
		return associatedPromoProducts;
	}

	public void setAssociatedPromoProducts(List<AssociatedPromoProducts> associatedPromoProducts) {
		this.associatedPromoProducts = associatedPromoProducts;
	}
	
	public List<AssociatedCategories> getAssociatedCategories() {
		return associatedCategories;
	}
	public void setAssociatedCategories(List<AssociatedCategories> associatedCategories) {
		this.associatedCategories = associatedCategories;
	}
	public List<ProductGeneralSettingMenuItemNames> getProductGeneralSettingNamesList() {
		return productGeneralSettingNamesList;
	}
	public void setProductGeneralSettingNamesList(List<ProductGeneralSettingMenuItemNames> productGeneralSettingNamesList) {
		this.productGeneralSettingNamesList = productGeneralSettingNamesList;
	}
	public ProductPromotionRange getProductPromotionRange() {
		return productPromotionRange;
	}
	public void setProductPromotionRange(ProductPromotionRange productPromotionRange) {
		this.productPromotionRange = productPromotionRange;
	}
	public List<ProductPromotionRange> getTimeRestrictions() {
		return timeRestrictions;
	}
	public void setTimeRestrictions(List<ProductPromotionRange> timeRestrictions) {
		this.timeRestrictions = timeRestrictions;
	}
	public Long getPrmoMenuItem() {
		return prmoMenuItem;
	}
	public void setPrmoMenuItem(Long prmoMenuItem) {
		this.prmoMenuItem = prmoMenuItem;
	}
	public Long getPrmoChoice() {
		return prmoChoice;
	}
	public void setPrmoChoice(Long prmoChoice) {
		this.prmoChoice = prmoChoice;
	}
	public LocalDate getPromoStartDate() {
		return promoStartDate;
	}
	public void setPromoStartDate(LocalDate promoStartDate) {
		this.promoStartDate = promoStartDate;
	}
	public LocalDate getPromoEndDate() {
		return promoEndDate;
	}
	public void setPromoEndDate(LocalDate promoEndDate) {
		this.promoEndDate = promoEndDate;
	}
	public ProductSmartRouting getProductSmartRouting() {
		return productSmartRouting;
	}
	public void setProductSmartRouting(ProductSmartRouting productSmartRouting) {
		this.productSmartRouting = productSmartRouting;
	}
	public ProductAbsSettings getProductAbsSettings() {
		return productAbsSettings;
	}
	public void setProductAbsSettings(ProductAbsSettings productAbsSettings) {
		this.productAbsSettings = productAbsSettings;
	}

	public ProductPresentation getProductPresentation() {
		return productPresentation;
	}
	public void setProductPresentation(ProductPresentation productPresentation) {
		this.productPresentation = productPresentation;
	}
	public ProductPosKvs getProductPosKvs() {
		return productPosKvs;
	}
	public void setProductPosKvs(ProductPosKvs productPosKvs) {
		this.productPosKvs = productPosKvs;
	}
	public List<Item> getSubstitutionList() {
		return substitutionList;
	}
	public void setSubstitutionList(List<Item> substitutionList) {
		this.substitutionList = substitutionList;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public List <Category> getCategories() {
		return categories;
	}
	public void setCategories(List <Category> categories) {
		this.categories = categories;
	}

	public List <Size> getSizeSelection() {
		return sizeSelection;
	}

	public void setSizeSelection(List <Size> sizeSelection) {
		this.sizeSelection = sizeSelection;
	}
	public List <Code> getProductGroups() {
		return ProductGroups;
	}
	public void setProductGroups(List <Code> productGroups) {
		ProductGroups = productGroups;
	}
	public List <PromotionGroup> getPromotionGroups() {
		return promotionGroups;
	}
	public void setPromotionGroups(List <PromotionGroup> promotionGroups) {
		this.promotionGroups = promotionGroups;
	}
	public List<Parameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public Production getProduction() {
		return production;
	}
	public void setProduction(Production production) {
		this.production = production;
	}
	public ProductCCMSettings getProductCCMSettings() {
		return productCCMSettings;
	}
	public void setProductCCMSettings(ProductCCMSettings productCCMSettings) {
		this.productCCMSettings = productCCMSettings;
	}
	public List <PriceTag> getPriceList() {
		return priceList;
	}
	public void setPriceList(List <PriceTag> priceList) {
		this.priceList = priceList;
	}
	public List<ProductShortCutSettings> getProductShortCutSettings() {
		return productShortCutSettings;
	}
	public void setProductShortCutSettings(List<ProductShortCutSettings> productShortCutSettings) {
		this.productShortCutSettings = productShortCutSettings;
	}

	public List<ProductTags> getProductTagsList() {
		return productTagsList;
	}
	public void setProductTagsList(List<ProductTags> productTagsList) {
		this.productTagsList = productTagsList;
	}
	public Long getProductCode() {
		return productCode;
	}
	public void setProductCode(Long productCode) {
		this.productCode = productCode;
	}
	public Long getPrdInstId() {
		return prdInstId;
	}
	public void setPrdInstId(Long prdInstId) {
		this.prdInstId = prdInstId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductClass() {
		return productClass;
	}
	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}
	public String getProductCategory() {
		return ProductCategory;
	}
	public void setProductCategory(String productCategory) {
		ProductCategory = productCategory;
	}
	public String getFamilyGroup() {
		return familyGroup;
	}
	public void setFamilyGroup(String familyGroup) {
		this.familyGroup = familyGroup;
	}
	public String getChoiceGroup() {
		return choiceGroup;
	}
	public void setChoiceGroup(String choiceGroup) {
		this.choiceGroup = choiceGroup;
	}
	public String getDayPartCode() {
		return dayPartCode;
	}
	public void setDayPartCode(String dayPartCode) {
		this.dayPartCode = dayPartCode;
	}
	public Long getPrimaryMenuItemCode() {
		return primaryMenuItemCode;
	}
	public void setPrimaryMenuItemCode(Long primaryMenuItemCode) {
		this.primaryMenuItemCode = primaryMenuItemCode;
	}
	public String getSecondaryMenuItem() {
		return secondaryMenuItem;
	}
	public void setSecondaryMenuItem(String secondaryMenuItem) {
		this.secondaryMenuItem = secondaryMenuItem;
	}
	public Long getAuxiliaryMenuItem() {
		return auxiliaryMenuItem;
	}
	public void setAuxiliaryMenuItem(Long auxiliaryMenuItem) {
		this.auxiliaryMenuItem = auxiliaryMenuItem;
	}
	public Long getParentPromotionItem() {
		return parentPromotionItem;
	}
	public void setParentPromotionItem(Long parentPromotionItem) {
		this.parentPromotionItem = parentPromotionItem;
	}
	public Long getMaxIngredients() {
		return maxIngredients;
	}
	public void setMaxIngredients(Long maxIngredients) {
		this.maxIngredients = maxIngredients;
	}
	public String getStationGroup() {
		return stationGroup;
	}
	public void setStationGroup(String stationGroup) {
		this.stationGroup = stationGroup;
	}
	public List<TimeFrames> getTimeAvailableForSales() {
		return timeAvailableForSales;
	}
	public void setTimeAvailableForSales(List<TimeFrames> timeAvailableForSales) {
		this.timeAvailableForSales = timeAvailableForSales;
	}
	public Long getBreakfast() {
		return breakfast;
	}
	public void setBreakfast(Long breakfast) {
		this.breakfast = breakfast;
	}
	public Long getLunch() {
		return lunch;
	}
	public void setLunch(Long lunch) {
		this.lunch = lunch;
	}
	public Long getDinner() {
		return dinner;
	}
	public void setDinner(Long dinner) {
		this.dinner = dinner;
	}
	public Long getOvernight() {
		return overnight;
	}
	public void setOvernight(Long overnight) {
		this.overnight = overnight;
	}
	public List<Component> getComponents() {
		return components;
	}
	public void setComponents(List<Component> components) {
		this.components = components;
	}
	public Long getFeeDeliveryId() {
		return feeDeliveryId;
	}
	public void setFeeDeliveryId(Long feeDeliveryId) {
		this.feeDeliveryId = feeDeliveryId;
	}
	public Double getFeePercentage() {
		return feePercentage;
	}
	public void setFeePercentage(Double feePercentage) {
		this.feePercentage = feePercentage;
	}
	public Long getPromoInstId() {
		return promoInstId;
	}
	public void setPromoInstId(Long promoInstId) {
		this.promoInstId = promoInstId;
	}
	public String getProductClassId() {
		return productClassId;
	}
	public void setProductClassId(String productClassId) {
		this.productClassId = productClassId;
	}
	public int getCountPriceSubtitution() {
		return countPriceSubtitution;
	}
	public void setCountPriceSubtitution(int countPriceSubtitution) {
		this.countPriceSubtitution = countPriceSubtitution;
	}
	public int getCounTaxSubtitution() {
		return counTaxSubtitution;
	}
	public void setCounTaxSubtitution(int counTaxSubtitution) {
		this.counTaxSubtitution = counTaxSubtitution;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public String getFeemaxthreshold() {
		return feemaxthreshold;
	}
	public void setFeemaxthreshold(String feemaxthreshold) {
		this.feemaxthreshold = feemaxthreshold;
	}
	public String getFeeminthreshold() {
		return feeminthreshold;
	}
	public void setFeeminthreshold(String feeminthreshold) {
		this.feeminthreshold = feeminthreshold;
	}
	public String getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public BigDecimal getReuseDepositeEating() {
		return reuseDepositeEating;
	}
	public void setReuseDepositeEating(BigDecimal reuseDepositeEating) {
		this.reuseDepositeEating = reuseDepositeEating;
	}
	public BigDecimal getReuseDepositeTakeout() {
		return reuseDepositeTakeout;
	}
	public void setReuseDepositeTakeout(BigDecimal reuseDepositeTakeout) {
		this.reuseDepositeTakeout = reuseDepositeTakeout;
	}
}
