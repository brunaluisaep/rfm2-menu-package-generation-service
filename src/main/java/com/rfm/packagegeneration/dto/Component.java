package com.rfm.packagegeneration.dto;

import java.util.Objects;

public class Component {
	private Long componentProductId;
	private Long productCode;
	private Long compositionType;
	private Long sequence;
	
	private Long defaultQuantity;
	private Long minQuantity;
	private Long maxQuantity;
	private Long refundThreshold;
	private Long chargeThreShold;
	private String costInclusive;
	private String displayOnCSO;
	
	private String plainGrill;
	private String smartGrill;
	private String forceCompositionDisplay;
	private Long referenceProductId;
	private Long defaultProductId;	
	private Long defaultChoice;
	private String defaultChoiceChn;
	
	private String flexibleChoice;
	private String priceCalculationMode;
	private String anchor;
	private String pricingReductionRate;
	private String nonRequiredChoices;
	private String setParentProductOutage;
	private Long impactedMenuItemOnRTM;
	private Long substituteOnRTM;
	private String productClass; 
	private Long autoCondiment; 
	private Long productCodeImpactedOnRTM;	
	private Long servingFactor;
	private int deleted;
	
	public Long getComponentProductId() {
		return componentProductId;
	}
	public void setComponentProductId(Long componentProductId) {
		this.componentProductId = componentProductId;
	}
	public Long getProductCode() {
		return productCode;
	}
	public void setProductCode(Long productCode) {
		this.productCode = productCode;
	}
	public Long getCompositionType() {
		return compositionType;
	}
	public void setCompositionType(Long compositionType) {
		this.compositionType = compositionType;
	}
	public Long getSequence() {
		return sequence;
	}
	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}
	public Long getDefaultQuantity() {
		return defaultQuantity;
	}
	public void setDefaultQuantity(Long defaultQuantity) {
		this.defaultQuantity = defaultQuantity;
	}
	public Long getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(Long minQuantity) {
		this.minQuantity = minQuantity;
	}
	public Long getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(Long maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public Long getRefundThreshold() {
		return refundThreshold;
	}
	public void setRefundThreshold(Long refundThreshold) {
		this.refundThreshold = refundThreshold;
	}
	public Long getChargeThreShold() {
		return chargeThreShold;
	}
	public void setChargeThreShold(Long chargeThreShold) {
		this.chargeThreShold = chargeThreShold;
	}
	public String getCostInclusive() {
		return costInclusive;
	}
	public void setCostInclusive(String costInclusive) {
		this.costInclusive = costInclusive;
	}
	public String getDisplayOnCSO() {
		return displayOnCSO;
	}
	public void setDisplayOnCSO(String displayOnCSO) {
		this.displayOnCSO = displayOnCSO;
	}
	public String getPlainGrill() {
		return plainGrill;
	}
	public void setPlainGrill(String plainGrill) {
		this.plainGrill = plainGrill;
	}
	public String getSmartGrill() {
		return smartGrill;
	}
	public void setSmartGrill(String smartGrill) {
		this.smartGrill = smartGrill;
	}
	public String getForceCompositionDisplay() {
		return forceCompositionDisplay;
	}
	public void setForceCompositionDisplay(String forceCompositionDisplay) {
		this.forceCompositionDisplay = forceCompositionDisplay;
	}
	public Long getReferenceProductId() {
		return referenceProductId;
	}
	public void setReferenceProductId(Long referenceProductId) {
		this.referenceProductId = referenceProductId;
	}
	public Long getDefaultProductId() {
		return defaultProductId;
	}
	public void setDefaultProductId(Long defaultProductId) {
		this.defaultProductId = defaultProductId;
	}
	public String getFlexibleChoice() {
		return flexibleChoice;
	}
	public void setFlexibleChoice(String flexibleChoice) {
		this.flexibleChoice = flexibleChoice;
	}
	public String getPriceCalculationMode() {
		return priceCalculationMode;
	}
	public void setPriceCalculationMode(String priceCalculationMode) {
		this.priceCalculationMode = priceCalculationMode;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public String getPricingReductionRate() {
		return pricingReductionRate;
	}
	public void setPricingReductionRate(String pricingReductionRate) {
		this.pricingReductionRate = pricingReductionRate;
	}
	public String getNonRequiredChoices() {
		return nonRequiredChoices;
	}
	public void setNonRequiredChoices(String nonRequiredChoices) {
		this.nonRequiredChoices = nonRequiredChoices;
	}
	public String getSetParentProductOutage() {
		return setParentProductOutage;
	}
	public void setSetParentProductOutage(String setParentProductOutage) {
		this.setParentProductOutage = setParentProductOutage;
	}
	public Long getImpactedMenuItemOnRTM() {
		return impactedMenuItemOnRTM;
	}
	public void setImpactedMenuItemOnRTM(Long impactedMenuItemOnRTM) {
		this.impactedMenuItemOnRTM = impactedMenuItemOnRTM;
	}
	public Long getSubstituteOnRTM() {
		return substituteOnRTM;
	}
	public void setSubstituteOnRTM(Long substituteOnRTM) {
		this.substituteOnRTM = substituteOnRTM;
	}
	public String getProductClass() {
		return productClass;
	}
	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}
	public Long getAutoCondiment() {
		return autoCondiment;
	}
	public void setAutoCondiment(Long autoCondiment) {
		this.autoCondiment = autoCondiment;
	}
	public Long getProductCodeImpactedOnRTM() {
		return productCodeImpactedOnRTM;
	}
	public void setProductCodeImpactedOnRTM(Long productCodeImpactedOnRTM) {
		this.productCodeImpactedOnRTM = productCodeImpactedOnRTM;
	}
	public Long getServingFactor() {
		return servingFactor;
	}
	public void setServingFactor(Long servingFactor) {
		this.servingFactor = servingFactor;
	}
	public Long getDefaultChoice() {
		return defaultChoice;
	}
	public void setDefaultChoice(Long defaultChoice) {
		this.defaultChoice = defaultChoice;
	}
	public String getDefaultChoiceChn() {
		return defaultChoiceChn;
	}
	public void setDefaultChoiceChn(String defaultChoiceChn) {
		this.defaultChoiceChn = defaultChoiceChn;
	}
	@Override
	public int hashCode() {
		return Objects.hash(componentProductId, compositionType);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Component other = (Component) obj;
		return Objects.equals(componentProductId, other.componentProductId)
				&& Objects.equals(compositionType, other.compositionType);
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	
	}
