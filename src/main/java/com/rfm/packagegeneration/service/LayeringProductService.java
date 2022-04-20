package com.rfm.packagegeneration.service;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rfm.packagegeneration.cache.CacheService;
import com.rfm.packagegeneration.cache.KeyNameHelper;
import com.rfm.packagegeneration.constants.GeneratorConstant;
import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.dao.LayeringProductDBDAO;
import com.rfm.packagegeneration.dao.NamesDBDAO;
import com.rfm.packagegeneration.dao.ProductDBDAO;
import com.rfm.packagegeneration.dto.Code;
import com.rfm.packagegeneration.dto.Component;
import com.rfm.packagegeneration.dto.Item;
import com.rfm.packagegeneration.dto.KVSRoutes;
import com.rfm.packagegeneration.dto.PPG;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.Parameter;
import com.rfm.packagegeneration.dto.PriceTag;
import com.rfm.packagegeneration.dto.PriceTax;
import com.rfm.packagegeneration.dto.Pricing;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductDBRequest;
import com.rfm.packagegeneration.dto.ProductPosKvs;
import com.rfm.packagegeneration.dto.ProductShortCutSettings;
import com.rfm.packagegeneration.dto.Production;
import com.rfm.packagegeneration.dto.RequestDTO;
import com.rfm.packagegeneration.dto.Restaurant;
import com.rfm.packagegeneration.dto.Route;
import com.rfm.packagegeneration.dto.Set;
import com.rfm.packagegeneration.dto.Size;
import com.rfm.packagegeneration.dto.Tax;

@Service
public class LayeringProductService {
	private String excludeInactiveMI;

	private static final String CHOICE = "CHOICE";
	private String defaultMenuItemStatus;

	private static final Logger LOGGER = LogManager.getLogger("LayeringProductService"); 
	private static final String CACHE_MISS_KEY = "Cache miss key {}";
	private static final String NEVER = "NEVER";
	
	@Autowired
	LayeringProductDBDAO layeringDBDAO;

	@Autowired
	NamesDBDAO namesGeneratorDAO;
	@Autowired
	ProductDBDAO productDBDAO;
	
	@Autowired
	CacheService cacheService;
	
	@Value("${application.redis.cacheTTL}")
	private Long cacheTTLInMinutes;

	/**
	* Returns assigned sets of specific restaurant  
	* @param	request data parameter
	* @return   Restaurant DTO
	*/	
	public Restaurant getRestaurantSets(RequestDTO request) throws Exception{
		Restaurant dto = new Restaurant();		
		dto.setNodeId(request.getNodeId());
		
		dto.setMenuItemSets(layeringDBDAO.getRestaurantSets(request.getMarketId(), request.getNodeId(), request.getEffectiveDate(),GeneratorConstant.TYPE_MENU_ITEM_SET ));
		dto.setPriceSets(layeringDBDAO.getRestaurantSets(request.getMarketId(), request.getNodeId(), request.getEffectiveDate(), GeneratorConstant.TYPE_PRICE_SET));
		 
		return dto;				
	}
	
	public String getDefaultMenuItemStatus() {
		return defaultMenuItemStatus;
	}

	public void setDefaultMenuItemStatus(String defaultMenuItemStatus) {
		this.defaultMenuItemStatus = defaultMenuItemStatus;
	}

	/**
	 * Returns assigned sets of specific restaurant
	 * 
	 * @param request data parameter
	 * @return Restaurant ProductDBRequest
	 */
	public Restaurant getRestaurantSets(ProductDBRequest productDBRequest) throws Exception {
		Restaurant dto = new Restaurant();
		dto.setNodeId(productDBRequest.getNodeId());

		dto.setMenuItemSets(layeringDBDAO.getRestaurantSets(productDBRequest.getMktId(), productDBRequest.getNodeId(),
				productDBRequest.getEffectiveDate(), GeneratorConstant.TYPE_MENU_ITEM_SET));
		dto.setPriceSets(layeringDBDAO.getRestaurantSets(productDBRequest.getMktId(), productDBRequest.getNodeId(),
				productDBRequest.getEffectiveDate(), GeneratorConstant.TYPE_PRICE_SET));

		return dto;
	}

	/**
	* Returns the merged MAP with Product data  
	* @param	request data parameter
	* @return   Hashmap with product merged merged
	*/	
	public Map<Long, Product> getMergedProductsByRest(RequestDTO dto) throws Exception {
		Restaurant restaurant = getRestaurantSets(dto);
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId(dto.getMarketId());
		Map<Long, Product> listOfProductsByMaster = layeringDBDAO.getProductPosKvsPresentationByMaster(dto.getMarketId(), dto.getEffectiveDate(), masterSetId);
		for(Set oSet : restaurant.getMenuItemSets()) {
			layeringDBDAO.getProductPosKvsPresentationBySet(listOfProductsByMaster, dto.getMarketId(), dto.getEffectiveDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());						
		}

		final List< Long > restSetIds = namesGeneratorDAO.retrieveRestSetId(dto.getNodeId(), dto.getMarketId() );
		for(Long restSetId : restSetIds) {
			layeringDBDAO.getProductPosKvsPresentationBySet(listOfProductsByMaster, dto.getMarketId(), dto.getEffectiveDate(), ProductDBConstant.RMI_TYPE, restSetId);						
		}
				
		
		return listOfProductsByMaster;
	}


	/**
	* Returns the merged MAP with Status by Rest  
	* @param	request data parameter
	* @return   Hashmap with status merged
	*/	
	public Map<Long, Product> getProductStatusByRest(PackageGeneratorDTO dto,Long layerLogicTyp) throws Exception {
		Map<Long, Product> listOfProductsByMaster = layeringDBDAO.getProductStatusByMaster(dto.getMarketID(), dto.getDate(), dto.getMasterSetId());
		for(Set oSet : dto.getRestaurant().getMenuItemSets()) {
			layeringDBDAO.getProductStatusBySet(listOfProductsByMaster, dto.getMarketID(), dto.getDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId(),layerLogicTyp);						
		}
		for(Long restSetId : dto.getRestSetIds()) {
			layeringDBDAO.getProductStatusBySet(listOfProductsByMaster, dto.getMarketID(), dto.getDate(), ProductDBConstant.RMI_TYPE, restSetId,layerLogicTyp);						
		}				
		return listOfProductsByMaster;
	}


	/**
	* Returns the merged MAP with Approval Status by Rest  
	* @param	request data parameter
	* @return   Hashmap with approval status merged
	*/		
	public Map<Long, Product> getProductApprovalStatusByRest(PackageGeneratorDTO dto) throws Exception {
		Map<Long, Product> listOfProductsByMaster = layeringDBDAO.getProductApprovalStatusByMaster(dto.getMarketID(), dto.getDate(), dto.getMasterSetId(), getDefaultMenuItemStatus());
		for(Set oSet : dto.getRestaurant().getMenuItemSets()) {
			layeringDBDAO.getProductApprovalStatusBySet(listOfProductsByMaster, dto.getMarketID(), dto.getDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());						
		}

		for(Long restSetId : dto.getRestSetIds()) {
			layeringDBDAO.getProductApprovalStatusBySet(listOfProductsByMaster, dto.getMarketID(), dto.getDate(), ProductDBConstant.RMI_TYPE, restSetId);						
		}				
		
		return listOfProductsByMaster;	
	}

	/**
	* Returns the merged MAP with product Status and Approval Status by Rest  
	* @param	request data parameter
	* @return   Hashmap with approval status merged
	 * @throws Exception 
	*/
	public Map<Long, Product> getProductDualStatusByRest(PackageGeneratorDTO dto) throws Exception {
		return getProductDualStatusByRest(dto, null);
	}

	/**
	* Returns the merged MAP with product Status and Approval Status by Rest  
	* @param	dto the DTO containing the package generation data
	* @param	notApprovedProducts HashMap with all the not approved products
	* @return   Hashmap with approval status merged
	 * @throws Exception 
	*/
	public Map<Long, Product> getProductDualStatusByRest(PackageGeneratorDTO dto, List<Long> notApprovedProducts) throws Exception {
		Map<Long, Product> listOfProductsByMaster;		
		Long layeringLogicTyp = Long.parseLong(productDBDAO.getValuesFromGlobalParam(dto.getMarketID(), ProductDBConstant.MENU_ITEM_LAYERING_LOGIC_TYPE));
		
		if(layeringLogicTyp != null && layeringLogicTyp.equals(1L)) {			
			listOfProductsByMaster = getProductApprovalStatusByRest(dto);			
			for(Long restSetId : dto.getRestSetIds()) {
				layeringDBDAO.getProductStatusBySet(listOfProductsByMaster, dto.getMarketID(), dto.getDate(), ProductDBConstant.RMI_TYPE, restSetId,layeringLogicTyp);						
			}											    
		} else {
			listOfProductsByMaster = getProductStatusByRest(dto,layeringLogicTyp);					
		}			
	
		final List<Long> restaurantList = layeringDBDAO.retrieveRestaurantMIList(dto.getRestSetIds().get(0), dto.getNodeID(), dto.getMarketID(), dto.getDate());
		
		for (Map.Entry<Long, Product> entry : new HashMap<Long, Product>(listOfProductsByMaster).entrySet()) {						        
		        if(layeringLogicTyp==1L && entry!=null && entry.getValue().getApprovalStatus()==0 ) {
					if (notApprovedProducts != null) notApprovedProducts.add(entry.getKey());
		    		listOfProductsByMaster.remove(entry.getKey());
		    	} else {	    		
		    		if(entry != null && entry.getKey() != null && !restaurantList.contains(entry.getKey()) ) {
		    			listOfProductsByMaster.remove(entry.getKey());		    			
		    		}
		    	} 
		        if(getExcludeInactiveMI().toUpperCase().equalsIgnoreCase("YES")
		        		&&entry.getValue().getActive()==0
		        		&&entry.getValue().getAuxiliaryMenuItem() != null
		        		&&!entry.getValue().getAuxiliaryMenuItem().equals(1L)) {
		        	listOfProductsByMaster.remove(entry.getKey());
		        }
		 }		
		return listOfProductsByMaster;		
	}	
	
	
	public Map<Long, Product> getProductParametersByRest(RequestDTO dto) throws Exception {
		Restaurant restaurant = getRestaurantSets(dto);
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId(dto.getMarketId());
		Map<Long, Product> listOfProductsByMaster = layeringDBDAO.getProductParametersByMaster(dto.getMarketId(), dto.getEffectiveDate(), masterSetId);
		for(Set oSet : restaurant.getMenuItemSets()) {
			layeringDBDAO.getProductParametersBynBySet(listOfProductsByMaster, dto.getMarketId(), dto.getEffectiveDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());						
		}

		final List< Long > restSetIds = namesGeneratorDAO.retrieveRestSetId(dto.getNodeId(), dto.getMarketId() );
		for(Long restSetId : restSetIds) {
			layeringDBDAO.getProductParametersBynBySet(listOfProductsByMaster, dto.getMarketId(), dto.getEffectiveDate(), ProductDBConstant.RMI_TYPE, restSetId);						
		}
				
		return listOfProductsByMaster;
	}


	public Map<Long, Product> getProductGeneralSettingByRest(RequestDTO dto) throws Exception {
		Restaurant restaurant = getRestaurantSets(dto);
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId(dto.getMarketId());
		Map<Long, Product> listOfProductsByMaster = new HashMap<>();
		//master
		layeringDBDAO.getProductGeneralSettingMenuItemNames(listOfProductsByMaster,dto.getMarketId(),
				dto.getEffectiveDate(), ProductDBConstant.MMI_SET_TYPE,masterSetId);
		//set
		for (Set oSet : restaurant.getMenuItemSets()) {
			layeringDBDAO.getProductGeneralSettingMenuItemNames(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());
		}
		//rest
		final List<Long> restSetIds = namesGeneratorDAO.retrieveRestSetId(dto.getNodeId(), dto.getMarketId());
		for (Long restSetId : restSetIds) {
			layeringDBDAO.getProductGeneralSettingMenuItemNames(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.RMI_TYPE, restSetId);
		}
		return listOfProductsByMaster;
	}
	
	public Map<Long, Product> getProductComponentSettingByRest(PackageGeneratorDTO dto) throws Exception {
		Map<Long, Product> listOfProductsByMaster = new HashMap<>();
		//master
		layeringDBDAO.getProductComponentsDetails(listOfProductsByMaster, dto.getMarketID(),
				dto.getDate(), ProductDBConstant.MMI_SET_TYPE, dto.getMasterSetId());
		//set
		for (Set oSet : dto.getRestaurant().getMenuItemSets()) {
			layeringDBDAO.getProductComponentsDetails(listOfProductsByMaster, dto.getMarketID(),
					dto.getDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());
		}
		//rest
		for (Long restSetId : dto.getRestSetIds()) {
			layeringDBDAO.getProductComponentsDetails(listOfProductsByMaster, dto.getMarketID(),
					dto.getDate(), ProductDBConstant.RMI_TYPE, restSetId);
		}
		return listOfProductsByMaster;
	}
	

	public Map<Long, Product> getMergedPromotionRangeProductsByRest(RequestDTO dto) throws Exception {
		Restaurant restaurant = getRestaurantSets(dto);
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId(dto.getMarketId());
		Map<Long, Product> listOfProductsByMaster = layeringDBDAO.getProductPromotionRangeByMaster(dto.getMarketId(),
				dto.getEffectiveDate(), masterSetId);
		for (Set oSet : restaurant.getMenuItemSets()) {
			layeringDBDAO.getProductPromotionRangeBySet(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());
		}
		final List<Long> restSetIds = namesGeneratorDAO.retrieveRestSetId(dto.getNodeId(), dto.getMarketId());
		for (Long restSetId : restSetIds) {

			layeringDBDAO.getProductPromotionRangeBySet(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.RMI_TYPE, restSetId);
		}

		return listOfProductsByMaster;
	}

	
	public Map<Long, Product> getProductShortcutSettingsByRest(RequestDTO dto) throws Exception{
		Restaurant restaurant = getRestaurantSets(dto);
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId(dto.getMarketId());
		Map<Long, Product> listOfProductsByMaster = layeringDBDAO.getProductShortcutSettingsByMaster(dto.getMarketId(), dto.getEffectiveDate(), masterSetId);
		for(Set oSet : restaurant.getMenuItemSets()) {
			layeringDBDAO.getProductShortcutSettingsBySet(listOfProductsByMaster, dto.getMarketId(), dto.getEffectiveDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());						
		}
		final List< Long > restSetIds = namesGeneratorDAO.retrieveRestSetId(dto.getNodeId(), dto.getMarketId() );
		for(Long restSetId : restSetIds) {
			layeringDBDAO.getProductShortcutSettingsBySet(listOfProductsByMaster, dto.getMarketId(), dto.getEffectiveDate(), ProductDBConstant.RMI_TYPE, restSetId);						
		}
		return listOfProductsByMaster;
    }
				

	public Map<Long, Product> getProductAssociatedCategoriesByRest(RequestDTO dto) throws Exception {
		Restaurant restaurant = getRestaurantSets(dto);
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId(dto.getMarketId());
		Map<Long, Product> listOfProductsByMaster = layeringDBDAO.getProductAssociatedCategoriesByMaster(dto.getMarketId(),
				dto.getEffectiveDate(), masterSetId);
		for (Set oSet : restaurant.getMenuItemSets()) {
			layeringDBDAO.getProductAssociatedCategoriesBySet(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());
		}
		final List<Long> restSetIds = namesGeneratorDAO.retrieveRestSetId(dto.getNodeId(), dto.getMarketId());
		for (Long restSetId : restSetIds) {

			layeringDBDAO.getProductAssociatedCategoriesBySet(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.RMI_TYPE, restSetId);
		}

		return listOfProductsByMaster;
	}

	public Map<Long, Product> getProductPromotionAssociationByRest(RequestDTO dto) throws Exception {
		Restaurant restaurant = getRestaurantSets(dto);
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId(dto.getMarketId());
		Map<Long, Product> listOfProductsByMaster = layeringDBDAO.getProductPromotionAssociationByMaster(dto.getMarketId(),
				dto.getEffectiveDate(), masterSetId);
		for (Set oSet : restaurant.getMenuItemSets()) {
			layeringDBDAO.getProductPromotionAssociationBySet(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());
		}
		final List<Long> restSetIds = namesGeneratorDAO.retrieveRestSetId(dto.getNodeId(), dto.getMarketId());
		for (Long restSetId : restSetIds) {

			layeringDBDAO.getProductPromotionAssociationBySet(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.RMI_TYPE, restSetId);
		}

		return listOfProductsByMaster;
	}	
	
	public Map<Long, Product> getProductTagsByRest(RequestDTO dto) throws Exception {
		Restaurant restaurant = getRestaurantSets(dto);
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId(dto.getMarketId());
		
		//master
		Map<Long, Product> listOfProductsByMaster = layeringDBDAO.getProductTagsMasterSet(dto.getMarketId(),dto.getEffectiveDate(), ProductDBConstant.MMI_SET_TYPE,masterSetId);
		//set
		for (Set oSet : restaurant.getMenuItemSets()) {
			layeringDBDAO.getProductTagsBySet(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());
		}
		//rest
		final List<Long> restSetIds = namesGeneratorDAO.retrieveRestSetId(dto.getNodeId(), dto.getMarketId());
		for (Long restSetId : restSetIds) {
			layeringDBDAO.getProductTagsBySet(listOfProductsByMaster, dto.getMarketId(),
					dto.getEffectiveDate(), ProductDBConstant.RMI_TYPE, restSetId);
		}
		return listOfProductsByMaster;
	}
	

	/**
	* Returns the merged MAP with Product data  
	* @param	request data parameter
	* @return   Hashmap with product merged merged
	*/	
	public Map<Long, Product> getMergedProductsByRest(PackageGeneratorDTO dto) throws Exception {
		
		String masterDataCacheKey = KeyNameHelper.getProductDataKey(dto.getScheduleRequestID(), dto.getDate(), dto.getMasterSetId());
		Map<Long, Product> restMaster = cacheService.getProductData(masterDataCacheKey);
		if(restMaster == null || (restMaster != null && restMaster.isEmpty())) {
			LOGGER.info(CACHE_MISS_KEY, masterDataCacheKey);
			restMaster = layeringDBDAO.getProductsByMaster(dto.getMarketID(), dto.getDate(), dto.getMasterSetId());
			cacheService.setProductData(masterDataCacheKey, restMaster, Duration.ofMinutes(cacheTTLInMinutes));
		}
		
		
		List<Long> setLayer = dto.getRestaurant().getMenuItemSets().stream().map(Set::getSetId).collect(Collectors.toList());
		
		String setLayerDataCacheKey  = KeyNameHelper.getProductDataKey(dto.getScheduleRequestID(), dto.getDate(), setLayer);
		
		Map<Long, Product> setLayerData =  cacheService.getProductData(setLayerDataCacheKey);
		
		if(setLayerData == null) {
			LOGGER.info(CACHE_MISS_KEY, setLayerDataCacheKey);
			Map<Long, Map<Long, Product>> listOfMapSet = new LinkedHashMap<>();
			for(Set oSet : dto.getRestaurant().getMenuItemSets()) {
				String setDataCacheKey = KeyNameHelper.getProductDataKey(dto.getScheduleRequestID(), dto.getDate(), oSet.getSetId());
				
				Map<Long, Product> setMap = cacheService.getProductData(setDataCacheKey);
				if(setMap == null) {
					LOGGER.info(CACHE_MISS_KEY, setDataCacheKey);
					setMap  = layeringDBDAO.getProductsBySet(dto.getMarketID(), dto.getDate(), ProductDBConstant.MIS_TYPE, oSet.getSetId());
					cacheService.setProductData(setDataCacheKey, setMap, Duration.ofMinutes(cacheTTLInMinutes));
				}
									
				listOfMapSet.put(oSet.getSetId(), setMap); 
			}
			
			setLayerData = new HashMap<>();
			for(Entry<Long, Map<Long, Product>> setEntry : listOfMapSet.entrySet()) {
				for(Entry<Long, Product> prdEntry : setEntry.getValue().entrySet()) {
					Product prdSetLeyer = setLayerData.get(prdEntry.getKey());
					
					if(prdSetLeyer != null) {
						prdSetLeyer = layeringDBDAO.layeringProduct(prdSetLeyer, prdEntry.getValue()); 
					} else {
						prdSetLeyer =  prdEntry.getValue();
						
					}
					setLayerData.put(prdEntry.getKey(),prdSetLeyer);
				}
			}
			
			cacheService.setProductData(setLayerDataCacheKey, setLayerData, Duration.ofMinutes(cacheTTLInMinutes));
			
		}
		
		Map<Long, Map<Long, Product>> listOfMap = new LinkedHashMap<>();
		for(Long restSetId : dto.getRestSetIds()) {
			Map<Long, Product> restMap  =  layeringDBDAO.getProductsBySet(dto.getMarketID(), dto.getDate(), ProductDBConstant.RMI_TYPE, restSetId);
			listOfMap.put(restSetId, restMap);
		}
		
		Map<String,ProductShortCutSettings> productShortCutSettings=layeringDBDAO.getAllShortcuts(dto.getMarketID());
		
		List<Long> notApprovedProducts = new ArrayList<Long>();

		Map<Long, Product> productStatusMap =  getProductDualStatusByRest(dto, notApprovedProducts);
		for(Map.Entry<Long, Product> entry : productStatusMap.entrySet()){
			Product prdDetails = restMaster.get(entry.getKey());
			
			if(prdDetails != null) {
				// merge set layer data
				Product productDTOSetLayer = setLayerData.get(entry.getKey());
				if(productDTOSetLayer != null ) {
					prdDetails = layeringDBDAO.layeringProduct(prdDetails, productDTOSetLayer);
				}
				
				// merge restaurant data
				for(Map.Entry<Long, Map<Long, Product>> setLists : listOfMap.entrySet()){
					Map<Long, Product> setMap = setLists.getValue();
					Product productDTO = setMap.get(entry.getKey());
					if(productDTO != null ) {
						prdDetails = layeringDBDAO.layeringProduct(prdDetails, productDTO);
					}
				}
	
				entry.getValue().setAssociatedCategories(prdDetails.getAssociatedCategories());
				entry.getValue().setAssociatedPromoProducts(prdDetails.getAssociatedPromoProducts());
				entry.getValue().setAssociatedPromoProducts(prdDetails.getAssociatedPromoProducts());
				entry.getValue().setAuxiliaryMenuItem(prdDetails.getAuxiliaryMenuItem());
				entry.getValue().setBreakfast(prdDetails.getBreakfast());
				entry.getValue().setCategories(prdDetails.getCategories());
				entry.getValue().setChoiceGroup(prdDetails.getChoiceGroup());
				entry.getValue().setComponents(prdDetails.getComponents());
				entry.getValue().setDayPartCode(prdDetails.getDayPartCode());
				entry.getValue().setDinner(prdDetails.getDinner());
				entry.getValue().setFamilyGroup(prdDetails.getFamilyGroup());
				entry.getValue().setFeeDeliveryId(prdDetails.getFeeDeliveryId());
				entry.getValue().setFeePercentage(prdDetails.getFeePercentage());
				entry.getValue().setLunch(prdDetails.getLunch());
				entry.getValue().setMaxIngredients(prdDetails.getMaxIngredients());
				entry.getValue().setOvernight(prdDetails.getOvernight());
				entry.getValue().setParameters(prdDetails.getParameters());
				entry.getValue().setParentPromotionItem(prdDetails.getParentPromotionItem());
				entry.getValue().setPriceList(prdDetails.getPriceList());
				entry.getValue().setPrimaryMenuItemCode(prdDetails.getPrimaryMenuItemCode());
				entry.getValue().setPrmoChoice(prdDetails.getPrmoChoice());
				entry.getValue().setPrmoMenuItem(prdDetails.getPrmoMenuItem());
				entry.getValue().setProductAbsSettings(prdDetails.getProductAbsSettings());
				entry.getValue().setProductCategory(prdDetails.getProductCategory());
				entry.getValue().setProductCategoryId(prdDetails.getProductCategoryId());
				entry.getValue().setProductCCMSettings(prdDetails.getProductCCMSettings());
				entry.getValue().setProductClass(prdDetails.getProductClass());
				entry.getValue().setProductClassId(prdDetails.getProductClassId());
				entry.getValue().setProductCode(prdDetails.getProductCode());
				entry.getValue().setPrdInstId(prdDetails.getPrdInstId());
				entry.getValue().setProductGeneralSettingNamesList(prdDetails.getProductGeneralSettingNamesList());
				entry.getValue().setProductGroups(prdDetails.getProductGroups());
				entry.getValue().setProduction(prdDetails.getProduction());
				entry.getValue().setProductPosKvs(prdDetails.getProductPosKvs());
				entry.getValue().setProductPresentation(prdDetails.getProductPresentation());
				entry.getValue().setProductPromotionRange(prdDetails.getProductPromotionRange());
				if(prdDetails.getProductShortCutSettings()!=null && !productShortCutSettings.isEmpty()) {
					entry.getValue().setProductShortCutSettings(getMergedShortCutSettings(prdDetails.getProductShortCutSettings(),dto,productShortCutSettings));
				}
				entry.getValue().setProductSmartRouting(prdDetails.getProductSmartRouting());
				entry.getValue().setProductTagsList(prdDetails.getProductTagsList());
				entry.getValue().setProductType(prdDetails.getProductType());
				entry.getValue().setPromoEndDate(prdDetails.getPromoEndDate());
				entry.getValue().setPromoInstId(prdDetails.getPromoInstId());
				entry.getValue().setPromoStartDate(prdDetails.getPromoStartDate());
				entry.getValue().setPromotionGroups(prdDetails.getPromotionGroups());
				entry.getValue().setSecondaryMenuItem(prdDetails.getSecondaryMenuItem());
				entry.getValue().setSizeSelection(prdDetails.getSizeSelection());
				entry.getValue().setStationGroup(prdDetails.getStationGroup());
				entry.getValue().setSubstitutionList(prdDetails.getSubstitutionList());
				entry.getValue().setProductAbsSettings(prdDetails.getProductAbsSettings());
				entry.getValue().setTimeAvailableForSales(prdDetails.getTimeAvailableForSales());
				entry.getValue().setTimeRestrictions(prdDetails.getTimeRestrictions());
				entry.getValue().setXmlMaxSize(prdDetails.getXmlMaxSize());
				entry.getValue().setXmlVersion(prdDetails.getXmlVersion());
				entry.getValue().setFeeName(prdDetails.getFeeName());
				entry.getValue().setFeeminthreshold(prdDetails.getFeeminthreshold());
				entry.getValue().setFeemaxthreshold(prdDetails.getFeemaxthreshold());
				entry.getValue().setReuseDepositeEating(prdDetails.getReuseDepositeEating());
				entry.getValue().setReuseDepositeTakeout(prdDetails.getReuseDepositeTakeout());
			}
		}
		productStatusMap = getMenuSubstitutionList(productStatusMap, dto.getMarketID());
		
	productStatusMap = getPricingandTaxValues(productStatusMap, 
		dto.getDate() , dto.getMarketID(), dto.getNodeID(), dto.getRestaurant(), notApprovedProducts);
		
			
		productStatusMap = getProductDBDataForDimensionGroup(productStatusMap, 
				dto.getDate() , dto.getMarketID(), dto.getNodeID());
		
		productStatusMap = getCategoryVals(productStatusMap, dto.getDate());
						
		productStatusMap = getProductDBDataForPromotionGroup(productStatusMap, dto.getMarketID(), dto.getDate());	
		
		productStatusMap = getCytGroupDisplayOrder(productStatusMap, dto.getMarketID(),dto.getDate());
		
		productStatusMap = getMenuItemProductGroups(productStatusMap, dto.getMarketID(), dto.getDate());	
															
		return productStatusMap;
	}
	
	public List<ProductShortCutSettings> getMergedShortCutSettings(List<ProductShortCutSettings> sourceData,PackageGeneratorDTO dto,Map<String,ProductShortCutSettings> map){
		List<ProductShortCutSettings> master = new ArrayList<>();
		sourceData.stream().forEach(sl -> {
			// Add master Shortcut Settings
			if (map.containsKey(dto.getMasterSetId() + "_" + sl.getKioskId())) {
				map.get(dto.getMasterSetId() + "_" + sl.getKioskId()).setProductId(sl.getProductId());
				master.add(map.get(dto.getMasterSetId() + "_" + sl.getKioskId()));
			}
			// Add or Merge set Shortcut Settings
			dto.getRestaurant().getMenuItemSets().stream().forEach(set -> {
				if (map.containsKey(set.getSetId() + "_" + sl.getKioskId())) {
					ProductShortCutSettings target = map.get(set.getSetId() + "_" + sl.getKioskId());
					Optional<ProductShortCutSettings> sParameter = master.stream()
							.filter(mp -> mp.getKioskId().equals(target.getKioskId())).findAny();
					if (sParameter.isPresent()) {
						sParameter.get().setItem(target.getItem());
						sParameter.get().setKioskId(target.getKioskId());
						sParameter.get().setName(target.getName());
						sParameter.get().setProductId(sl.getProductId());
					} else {
						map.get(set.getSetId() + "_" + sl.getKioskId()).setProductId(sl.getProductId());
						master.add(map.get(set.getSetId() + "_" + sl.getKioskId()));
					}
				}
			});
			// Add or Merge rest Shortcut Settings
			dto.getRestSetIds().stream().forEach(rest -> {
				if (map.containsKey(rest + "_" + sl.getKioskId())) {
					ProductShortCutSettings target = map.get(rest + "_" + sl.getKioskId());
					Optional<ProductShortCutSettings> sParameter = master.stream()
							.filter(mp -> mp.getKioskId().equals(target.getKioskId())).findAny();
					if (sParameter.isPresent()) {
						sParameter.get().setItem(target.getItem());
						sParameter.get().setKioskId(target.getKioskId());
						sParameter.get().setName(target.getName());
						sParameter.get().setProductId(sl.getProductId());
					} else {
						map.get(rest + "_" + sl.getKioskId()).setProductId(sl.getProductId());
						master.add(map.get(rest + "_" + sl.getKioskId()));
					}
				}
			});
		});
		return master;
	}
	
	public Map<Long, Product> getMenuItemProductGroups(Map<Long, Product> products, Long marketID,
			String date) throws Exception {
			// Finding the GroupCount
			final int groupCount = layeringDBDAO.getgroupCount(marketID);
			// based upon Group Count, fetching Code
			if (groupCount != 0) {
				final List<Map<String, Object>> menuItemGroupVals = layeringDBDAO.getMenuItemGroupValues(
						marketID,date);
				for (final Map<String, Object> productData : menuItemGroupVals) {
					long prdId = Long.parseLong(productData.get("PRD_ID").toString());
					Product product = products.get(prdId);
					if(null!=product) {
					Code code = new Code();
					code.setCode(productData.get("mi_grp_cd").toString());
					product.getProductGroups().add(code);
					products.put(prdId, product);
					}	
				}
			}
			return products;		
	}
	
	private Map<Long, Product> getCytGroupDisplayOrder(Map<Long, Product> productStatusMap, Long marketID, String effDate) throws Exception {
		return layeringDBDAO.getCytGroupDisplayOrder(productStatusMap, marketID, effDate);
	}
	
	public Map<Long, Product> removeInactiveMIDetails(Map<Long, Product> productStatusMap, boolean excludeInactiveMI, Long mktId) throws Exception {		
		HashMap<Long, Product> map = new HashMap<>(productStatusMap);
		final Long layering = Long.parseLong(productDBDAO.getValuesFromGlobalParam(mktId, ProductDBConstant.MENU_ITEM_LAYERING_LOGIC_TYPE));
		
		for(Map.Entry<Long, Product> entry : map.entrySet()){			
			Product dto = entry.getValue();
			
			if(dto.getAuxiliaryMenuItem() == null) 
				dto.setAuxiliaryMenuItem(0L);
			
			if (dto.getComponents() != null) {
				List<Component> filtered = dto.getComponents().stream()
                        .filter(b ->(productStatusMap.get(b.getComponentProductId())!=null 
                        		&& (!excludeInactiveMI || (excludeInactiveMI && productStatusMap.get(b.getComponentProductId()).getActive() == 1))
						))		
                        .collect(Collectors.toList());
				dto.setComponents(null);
				dto.setComponents(filtered);
				
			}
			
			if (dto.getProductShortCutSettings() != null) {
				List<ProductShortCutSettings> filtered = dto.getProductShortCutSettings().stream()
                        .filter(b ->(productStatusMap.get(b.getProductId())!=null
                        		&& (!excludeInactiveMI || (excludeInactiveMI && productStatusMap.get(b.getProductId()).getActive() == 1))
						))
                        .collect(Collectors.toList());
				dto.setProductShortCutSettings(null);
				dto.setProductShortCutSettings(filtered);
				
			}
			
			if (dto.getSizeSelection() != null) {
				List<Size> filtered = dto.getSizeSelection().stream()
                        .filter(b ->(productStatusMap.get(b.getProductId())!=null
                        		&& ((!excludeInactiveMI && (productStatusMap.get(b.getProductId()).getApprovalStatus() == 1 || layering == 2L)) ||
									(excludeInactiveMI &&
										(productStatusMap.get(b.getProductId()).getActive() == 1) ||
										(productStatusMap.get(b.getProductId()).getAuxiliaryMenuItem() != null
											&& (productStatusMap.get(b.getProductId()).getAuxiliaryMenuItem().equals(1L))))
						)))
                        .collect(Collectors.toList());
				dto.setSizeSelection(null);
				dto.setSizeSelection(filtered);

			}
		}
		
		return map;
	}

	public Map<Long, Product> getProductDBDataForDimensionGroup(Map<Long, Product> products, String effDate , long marketID, long nodeId) throws Exception {
		final List<Map<String, Object>> localizationSets = productDBDAO.getLocalizationSets(nodeId, effDate);
		return layeringDBDAO.getProductDBDataForDimensionGroup(localizationSets, products, effDate, marketID);		
	}

	/*
	 * This method returns the collection of product for Category
	 * 
	 * @param productDBRequest
	 * 
	 * @return products
	 */
	public Map<Long, Product> getCategoryVals(Map<Long, Product> products,String effectiveDate) throws Exception {		
		return layeringDBDAO.getCategoriesVals(products, effectiveDate) ;				
	}

	/*
	 * This method returns the collection of product for Promotion Group
	 * 
	 * @param productDBRequest
	 * 
	 * @return products
	 */
	public Map<Long, Product> getProductDBDataForPromotionGroup(Map<Long, Product> productStatusMap,Long marketId, String effectiveDate) throws Exception {			
		return layeringDBDAO.getProductListDataForPromotionGroup(productStatusMap, marketId, effectiveDate);				
	}

	// fetching details for MenuItem SubstitutionList
	public Map<Long, Product> getMenuSubstitutionList(Map<Long, Product> products, long marketId) throws Exception {				
		return layeringDBDAO.getMenuSubstitutionList(products, marketId);				
	}


	/*
	 * This method returns the collection of product for Pricing and Tax Values
	 * 
	 * @param productDBRequest
	 * 
	 * @return products
	 */
	public Map<Long, Product> getPricingandTaxValues(Map<Long, Product> products, 
			String effectiveDate,  long marketId, long nodeId, Restaurant restaurant,
			List<Long> notApprovedProducts) throws Exception {
		String mitFlag = "";
		mitFlag = productDBDAO.getValuesFromGlobalParam(marketId,
				ProductDBConstant.DEFAULT_MENU_ITEM_TAX_SETTINGS);
		String autoPriceDummyProduct = productDBDAO.getValuesFromGlobalParam(marketId,
				ProductDBConstant.AUTO_PRICE_DUMMYPRODUCT);
		
		Map<Long, Long> taxEntryMap = new HashMap<>();
		List<Map<String, Object>> taxEntryList = layeringDBDAO.getTaxEntryList(marketId);
		for (final Map<String, Object> productData : taxEntryList) {
			taxEntryMap.put(Long.parseLong(productData.get("set_id").toString()),
					Long.parseLong(productData.get("tax_typ_code").toString()));
		}
		
		String useConfiguredTax = productDBDAO.getValuesFromGlobalParam(marketId,
				ProductDBConstant.DISPLAY_TAX_FOR_BRKD);
		String precisionValueForEating = getPrecisionValue(marketId,
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_EATING);
		String precisionValueForTakeout = getPrecisionValue(marketId,
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_TAKEOUT);
		String precisionValueForOther = getPrecisionValue(marketId,
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_OTHER);
		String truncateDecimalParam = productDBDAO.getValuesFromGlobalParam(marketId,
				ProductDBConstant.REMOVE_PRICING_DECIMAL);
//		String priceTypeSelect = productDBDAO.getValuesFromGlobalParam(marketId,
		Boolean removeInactive = getExcludeInactiveMI().toUpperCase().equalsIgnoreCase("YES");
		int taxEmpty = 1;
		if(mitFlag.equals(ProductDBConstant.MENU_ITEM_TAX_SET)) {
			String paramValue = productDBDAO.getValuesFromGlobalParam(marketId,
					ProductDBConstant.NON_MIT_MARKET_DEFAULT_TAX);
			if(paramValue.equalsIgnoreCase("true")) {
				taxEmpty = -88;
			}
		}
		
		PriceTax taxSetValues = new PriceTax();
		if (!(mitFlag.equals(ProductDBConstant.MENU_ITEM_TAX_SET))) {
			taxSetValues = layeringDBDAO.getPricingValueFromRest(marketId,
					effectiveDate,	nodeId);
		}
		
		Map<Long, PriceTax> setMap = new HashMap<>();
		Map<Long, PriceTax>  dataForDisplayBRD = new HashMap<>(); 
		
		if(restaurant.getPriceSets().isEmpty()) {
			restaurant.setPriceSets(layeringDBDAO.getRestaurantSets(marketId, nodeId, effectiveDate, GeneratorConstant.TYPE_PRICE_SET));
		}

		for (Set retrievedsetId : restaurant.getPriceSets()) {
			setMap  = layeringDBDAO.getPricingValuesBysetId(setMap, marketId, effectiveDate, retrievedsetId.getSetId());	
			dataForDisplayBRD = layeringDBDAO.getDataForDisplayBRD(dataForDisplayBRD, retrievedsetId.getSetId(),  effectiveDate) ;
		}
		// Retrieve the Menu Item Tax Set values
		if (mitFlag.equals(ProductDBConstant.MENU_ITEM_TAX_SET)) {
			List<Set> menuItemTaxSet = layeringDBDAO.getRestaurantSets(marketId, nodeId, effectiveDate, GeneratorConstant.TYPE_MENU_ITEM_TAX_SET);
			if (!menuItemTaxSet.isEmpty()) {
				restaurant.setMenuItemTax(menuItemTaxSet.get(0));
				setMap  = layeringDBDAO.getMenuItemTaxSetValues(setMap, marketId, effectiveDate, restaurant.getMenuItemTax().getSetId());
			}
		}
				
		for(Map.Entry<Long, Product> entry : products.entrySet()){
			Product product = entry.getValue();
			List<PriceTax> finalPrice = new ArrayList<>();
			Long auxMenuItem = product.getAuxiliaryMenuItem();
						
			if ((null!=auxMenuItem && auxMenuItem.equals(1L) && autoPriceDummyProduct.equals("Y"))) {
				if (notApprovedProducts == null) notApprovedProducts = new ArrayList<Long>();
				finalPrice = calculatePriceAndTaxForAux(products,mitFlag, taxSetValues, setMap, product, finalPrice, notApprovedProducts, removeInactive);
				
				PriceTax taxForAux = setMap.get(product.getProductId());
				if (null != taxForAux) product.setCounTaxSubtitution(product.getCounTaxSubtitution()+1);
			} else {				
				PriceTax dtoPrice = setMap.get(product.getProductId());				
				if (mitFlag.equals(ProductDBConstant.MENU_ITEM_TAX_SET)) {
					if (null == dtoPrice) dtoPrice = new PriceTax();
					finalPrice.add(dtoPrice);
				} else {						
					if (null == dtoPrice) {
						finalPrice.add(taxSetValues);
					} else {
						product.setCountPriceSubtitution(product.getCountPriceSubtitution()+1);
						Long eatingTaxCode = getEatingTaxCode(taxEmpty, taxSetValues, dtoPrice);
						Long eatingTaxRule = getEatingTaxRule(taxSetValues, dtoPrice);
						Long eatingTaxEntry = getEatingTaxEntry(taxSetValues, dtoPrice);
						Long tkutTaxCode = getTkutTaxCode(taxEmpty, taxSetValues, dtoPrice);
						Long tkutTaxRule = getTkutTaxRule(taxSetValues, dtoPrice);
						Long tkutTaxEntry = getTkutTaxEntry(taxSetValues, dtoPrice);
						Long othTaxCode = getOthTaxCode(taxEmpty, taxSetValues, dtoPrice);
						Long othTaxRule = getOthTaxRule(taxSetValues, dtoPrice);
						Long othTaxEntry = getOthTaxEntry(taxSetValues, dtoPrice);
						if(null!=eatingTaxCode)dtoPrice.setEatin_tax_cd(eatingTaxCode);
						if(null!=eatingTaxRule)dtoPrice.setEatin_tax_rule(eatingTaxRule);
						if(null!=eatingTaxEntry) {
							dtoPrice.setEatin_tax_entr(eatingTaxEntry);
						}else if(eatingTaxCode!=4L && null==eatingTaxEntry) {
							dtoPrice.setEatin_tax_entr(taxSetValues.getEatin_tax_entr());
						}
						if(null!=tkutTaxCode)dtoPrice.setTkut_tax_cd(tkutTaxCode);
						if(null!=tkutTaxRule)dtoPrice.setTkut_tax_rule(tkutTaxRule);
						if(null!=tkutTaxEntry) {
							dtoPrice.setTkut_tax_entr(tkutTaxEntry);
						}else if(tkutTaxCode!=4L && null==tkutTaxEntry) {
							dtoPrice.setTkut_tax_entr(taxSetValues.getTkut_tax_entr());
						}
						if(null!=othTaxCode)dtoPrice.setOth_tax_cd(othTaxCode);
						if(null!=othTaxRule)dtoPrice.setOth_tax_rule(othTaxRule);
						if(null!=othTaxEntry) {
							dtoPrice.setOth_tax_entr(othTaxEntry);
						}else if(othTaxCode!=4L && null == othTaxEntry) {
							dtoPrice.setOth_tax_entr(taxSetValues.getOth_tax_entr());
						}
						finalPrice.add(dtoPrice); 
					}
				}				
			}				
			Long productID = product.getProductId();		
			List<PriceTag> priceList = new ArrayList<>();
			PriceTag priceTag = new PriceTag();
			List<Pricing> pricetag = new ArrayList<>();
			Pricing priceEatin = new Pricing();
			Pricing priceTakeout = new Pricing();
			Pricing priceOthers = new Pricing();
			List<Tax> pricingEatin = new ArrayList<>();
			List<Tax> pricingTakeout = new ArrayList<>();
			List<Tax> pricingOthers = new ArrayList<>();
			Map<Long, String> taxCodeMap = getTaxCodeList();
			Map<Long, String> taxRuleMap = getTaxRuleList();
			String trimmedPriceEatin = "";
			String trimmedPriceOther = "";
			String trimmedPriceTakeOut= "";
	
			
			for(PriceTax productData : finalPrice) {
				Tax taxEatin = new Tax();
				Tax taxTakeout = new Tax();
				Tax taxOthers = new Tax();
				Long eatinTaxCode;
				Long eatInTaxRule;
				Long eatinTaxEntry;
				Long takeOutTaxCode;
				Long takeoutTaxRule;
				Long takeoutTaxEntry;
				Long othTaxCode;
				Long othTaxRule;
				Long othTaxEntry;
				// To get price and tax values when "DISPLAY_TAX_FOR_BRKD" param
				if (useConfiguredTax != null && useConfiguredTax.equalsIgnoreCase("true")) {
					
					if (!dataForDisplayBRD.isEmpty()) {
						PriceTax dtoPriceSet = setMap.get(product.getProductId());
						PriceTax dtoPriceBrk = dataForDisplayBRD.get(product.getProductId());
												
						if (useConfiguredTax.equalsIgnoreCase("false")) {
								trimmedPriceEatin = Double.toString( dtoPriceSet.getEatin_prc());
								eatinTaxCode = dtoPriceBrk.getEatin_tax_cd();
								eatInTaxRule =  dtoPriceBrk.getEatin_tax_rule();
								eatinTaxEntry = dtoPriceBrk.getEatin_tax_entr();
								
								trimmedPriceTakeOut = Double.toString(dtoPriceSet.getTkut_prc());
								takeOutTaxCode = dtoPriceBrk.getTkut_tax_cd();
								takeoutTaxRule = dtoPriceBrk.getTkut_tax_cd();
								takeoutTaxEntry = dtoPriceBrk.getTkut_tax_entr();
								
								trimmedPriceOther = Double.toString(dtoPriceSet.getOth_prc());
								othTaxCode = dtoPriceBrk.getOth_tax_cd();
								othTaxRule = dtoPriceBrk.getOth_tax_cd();
								othTaxEntry = dtoPriceBrk.getOth_tax_entr();				
						} else {							
								trimmedPriceEatin = Double.toString(dtoPriceSet.getEatin_prc());
								if(dtoPriceSet==null || dtoPriceSet.getEatin_tax_cd()==1) {
									eatinTaxCode = dtoPriceBrk.getEatin_tax_cd();
								} else {
									eatinTaxCode = dtoPriceSet.getEatin_tax_cd();
								}
								if (dtoPriceSet==null || dtoPriceSet.getEatin_tax_rule() ==1) {
									eatInTaxRule = dtoPriceBrk.getEatin_tax_rule();
								} else {
									eatInTaxRule = dtoPriceSet.getEatin_tax_rule();
								}								
								if (dtoPriceSet==null || dtoPriceSet.getEatin_tax_entr()==1) {
									eatinTaxEntry = dtoPriceBrk.getEatin_tax_entr();
								} else {
									eatinTaxEntry = dtoPriceSet.getEatin_tax_entr();
								}								
															
								trimmedPriceTakeOut = Double.toString(dtoPriceSet.getTkut_prc());							
								if(dtoPriceSet==null || dtoPriceSet.getTkut_tax_cd()==1) {
									takeOutTaxCode = dtoPriceBrk.getTkut_tax_cd();
								} else {
									takeOutTaxCode = dtoPriceSet.getTkut_tax_cd();
								}
								if (dtoPriceSet==null || dtoPriceSet.getTkut_tax_rule() ==1) {
									takeoutTaxRule = dtoPriceBrk.getTkut_tax_rule();
								} else {
									takeoutTaxRule = dtoPriceSet.getTkut_tax_rule();
								}								
								if (dtoPriceSet==null || dtoPriceSet.getTkut_tax_entr()==1) {
									takeoutTaxEntry = dtoPriceBrk.getTkut_tax_entr();
								} else {
									takeoutTaxEntry = dtoPriceSet.getTkut_tax_entr();
								}
															
								trimmedPriceOther =  Double.toString(dtoPriceSet.getOth_prc());																
								if(dtoPriceSet==null || dtoPriceSet.getOth_tax_cd()==1) {
									othTaxCode = dtoPriceBrk.getOth_tax_cd();
								} else {
									othTaxCode = dtoPriceSet.getOth_tax_cd();
								}
								if (dtoPriceSet==null || dtoPriceSet.getOth_tax_rule() ==1) {
									othTaxRule = dtoPriceBrk.getOth_tax_rule();
								} else {
									othTaxRule = dtoPriceSet.getOth_tax_rule();
								}								
								if (dtoPriceSet==null || dtoPriceSet.getOth_tax_entr()==1) {
									othTaxEntry = dtoPriceBrk.getOth_tax_entr();
								} else {
									othTaxEntry = dtoPriceSet.getOth_tax_entr();
								}								
						}
					}
				}
				
				boolean notPriced = true;
				if (productData != null) {
					if (null!=productData.getEatin_prc() || null!=productData.getTkut_prc() || null!=productData.getOth_prc()) {
						notPriced = false;
					}
					if (truncateDecimalParam.equals("Y")) {
						trimmedPriceEatin =  Double.toString(productData.getEatin_prc());
						trimmedPriceOther =  Double.toString(productData.getOth_prc());
						trimmedPriceTakeOut =  Double.toString(productData.getTkut_prc());
					} else {
						if (null!=productData.getEatin_prc()) {
							DecimalFormat decimalFormat = new DecimalFormat(precisionValueForEating);
							double price = productData.getEatin_prc();
							trimmedPriceEatin = decimalFormat.format(price);
						}
						
						if (null!=productData.getTkut_prc()) {
							DecimalFormat decimalFormat = new DecimalFormat(precisionValueForTakeout);
							double price = productData.getTkut_prc();
							trimmedPriceTakeOut = decimalFormat.format(price);
						}
						
						if (null!=productData.getOth_prc()) {
							DecimalFormat decimalFormat = new DecimalFormat(precisionValueForOther);
							double price = productData.getOth_prc();
							trimmedPriceOther = decimalFormat.format(price);
						}
					}

					eatinTaxCode = productData.getEatin_tax_cd();				
					eatInTaxRule = productData.getEatin_tax_rule();				
					eatinTaxEntry = productData.getEatin_tax_entr();
				
				
					if (notPriced) {
						taxEatin.setTaxCode(taxCodeMap.containsKey(taxSetValues.getEatin_tax_cd()) ? taxCodeMap.get(taxSetValues.getEatin_tax_cd()) : "NEVER");
						taxEatin.setRule(taxRuleMap.containsKey(taxSetValues.getEatin_tax_rule()) ? taxRuleMap.get(taxSetValues.getEatin_tax_rule()) : "FLAT");
						taxEatin.setEntry(taxEntryMap.containsKey(taxSetValues.getEatin_tax_entr()) ? taxEntryMap.get(taxSetValues.getEatin_tax_entr()).toString() : null);
					} else if (eatinTaxCode == null || eatinTaxCode == -99L) {
						taxEatin.setTaxCode("NEVER");
						taxEatin.setRule("FLAT");
					} else {
						if (taxCodeMap.containsKey(eatinTaxCode)) {
							taxEatin.setTaxCode(taxCodeMap.get(eatinTaxCode));
						} else {
							taxEatin.setTaxCode(NEVER);
						}
						if (eatinTaxCode == 4L) {
							taxEatin.setRule("FLAT");
						} else if (taxRuleMap.containsKey(eatInTaxRule)) {
							taxEatin.setRule(taxRuleMap.get(eatInTaxRule));
						} else {
							taxEatin.setRule("FLAT");
						}
						if (taxEntryMap.containsKey(eatinTaxEntry)) {
							taxEatin.setEntry(taxEntryMap.get(eatinTaxEntry).toString());
						} else if (eatinTaxCode == 4L && eatinTaxEntry == null) {
							taxEatin.setEntry(null);
						}
					}
					
					pricingEatin.add(taxEatin);
					priceEatin.setPrice(trimmedPriceEatin);
					priceEatin.setTax(pricingEatin);
					priceEatin.setPriceCode("EATIN");

					takeOutTaxCode =  productData.getTkut_tax_cd();
					takeoutTaxRule =  productData.getTkut_tax_rule();					
					takeoutTaxEntry = productData.getTkut_tax_entr();
										
					if (notPriced) {
						taxTakeout.setTaxCode(taxCodeMap.containsKey(taxSetValues.getTkut_tax_cd()) ? taxCodeMap.get(taxSetValues.getTkut_tax_cd()) : "NEVER");						
						taxTakeout.setRule(taxRuleMap.containsKey(taxSetValues.getTkut_tax_rule()) ? taxRuleMap.get(taxSetValues.getTkut_tax_rule()) : "FLAT");
						taxTakeout.setEntry(taxEntryMap.containsKey(taxSetValues.getTkut_tax_entr()) ? taxEntryMap.get(taxSetValues.getTkut_tax_entr()).toString() : null);
					} else if (takeOutTaxCode == null || takeOutTaxCode == -99L) {
						taxTakeout.setTaxCode(NEVER);
						taxTakeout.setRule("FLAT");
					} else {
						if (taxCodeMap.containsKey(takeOutTaxCode)) {
							taxTakeout.setTaxCode(taxCodeMap.get(takeOutTaxCode));
						} else {
							taxTakeout.setTaxCode(NEVER);
						}
						if (takeOutTaxCode == 4L) {
							taxTakeout.setRule("FLAT");
						} else if (taxRuleMap.containsKey(takeoutTaxRule)) {
							taxTakeout.setRule(taxRuleMap.get(takeoutTaxRule));
						} else {
							taxTakeout.setRule("FLAT");
						}

						if (taxEntryMap.containsKey(takeoutTaxEntry)) {
							taxTakeout.setEntry(taxEntryMap.get(takeoutTaxEntry).toString());
						} else {
							taxTakeout.setEntry(null);
						}
					}
					pricingTakeout.add(taxTakeout);
					priceTakeout.setPrice(trimmedPriceTakeOut);
					priceTakeout.setTax(pricingTakeout);
					priceTakeout.setPriceCode("TAKEOUT");				

					othTaxCode = productData.getOth_tax_cd();
					othTaxRule = productData.getOth_tax_rule();
					othTaxEntry = productData.getOth_tax_entr();
				
					if (notPriced) {
						taxOthers.setTaxCode(taxCodeMap.containsKey(taxSetValues.getOth_tax_cd()) ? taxCodeMap.get(taxSetValues.getOth_tax_cd()) : "NEVER");
						taxOthers.setRule(taxRuleMap.containsKey(taxSetValues.getOth_tax_rule()) ? taxRuleMap.get(taxSetValues.getOth_tax_rule()) : "FLAT");
						taxOthers.setEntry(taxEntryMap.containsKey(taxSetValues.getOth_tax_entr()) ? taxEntryMap.get(taxSetValues.getOth_tax_entr()).toString() : null);
					} else if (othTaxCode == null || othTaxCode == -99L) {
						taxOthers.setTaxCode(NEVER);
						taxOthers.setRule("FLAT");
					} else {
						if (taxCodeMap.containsKey(othTaxCode)) {
							taxOthers.setTaxCode(taxCodeMap.get(othTaxCode));
						} else {
							taxOthers.setTaxCode(NEVER);
						}
						if (othTaxCode == 4L) {
							taxOthers.setRule("FLAT");
						} else if (taxRuleMap.containsKey(othTaxRule)) {
							taxOthers.setRule(taxRuleMap.get(othTaxRule));
						} else {
							taxOthers.setRule("FLAT");
						}

						if (taxEntryMap.containsKey(othTaxEntry)) {
							taxOthers.setEntry(taxEntryMap.get(othTaxEntry).toString());
						} else {
							taxOthers.setEntry(null);
						}
					}
					pricingOthers.add(taxOthers);
					priceOthers.setPrice(trimmedPriceOther);
					priceOthers.setTax(pricingOthers);
					priceOthers.setPriceCode("OTHER");
				}
		
				pricetag.add(priceEatin);
				pricetag.add(priceTakeout);
				pricetag.add(priceOthers);
				priceTag.setPricing(pricetag);
				priceList.add(priceTag);
				product.setPriceList(priceList);
				product.setProductId(productID);			
			}
			products = calculateValuesForAllParameters(products, precisionValueForEating, precisionValueForTakeout,
					precisionValueForOther, product, productID, trimmedPriceEatin, trimmedPriceOther,
					trimmedPriceTakeOut);
		}
		return products;
	}
	private Long getEatingTaxEntry(PriceTax taxSetValues, PriceTax dtoPrice) {
		Long value = null;
		if(null!= dtoPrice.getEatin_tax_rule() && null!=dtoPrice.getEatin_tax_cd()) {
			if(dtoPrice.getEatin_tax_cd().equals(4L)) {
				value = null;
			}else if(dtoPrice.getEatin_tax_entr() == null || dtoPrice.getEatin_tax_entr().equals(1L)) {
				value = taxSetValues.getEatin_tax_entr();
			}else {
				value = dtoPrice.getEatin_tax_entr();
			}
		}
		return value;
	}

	private Long getEatingTaxRule(PriceTax taxSetValues, PriceTax dtoPrice) {
		Long value = null!=dtoPrice.getEatin_tax_rule()?dtoPrice.getEatin_tax_rule():1L;
		if(value==1L) {
			value = taxSetValues.getEatin_tax_rule();
		}else {
			value = dtoPrice.getEatin_tax_rule();
		}
		return value;
	}

	private Long getEatingTaxCode(int taxEmpty, PriceTax taxSetValues, PriceTax dtoPrice) {
		Long value = null!=dtoPrice.getEatin_tax_cd()?dtoPrice.getEatin_tax_cd():taxEmpty;
		if(value==1L) {
			 value = taxEmpty==-88?-88:taxSetValues.getEatin_tax_cd();
		}else if(value==-88) {
			value = taxSetValues.getEatin_tax_cd();
		}else {
			value = dtoPrice.getEatin_tax_cd();
		}
		return value;
	}
	private Long getTkutTaxEntry(PriceTax taxSetValues, PriceTax dtoPrice) {
		Long value = null;
		if(null!=dtoPrice.getTkut_tax_rule() && null!= dtoPrice.getTkut_tax_cd()) {
			if(dtoPrice.getTkut_tax_cd().equals(4L)) {
				value = null;
			}else if(dtoPrice.getTkut_tax_entr() == null || dtoPrice.getTkut_tax_entr().equals(1L)) {
				value = taxSetValues.getTkut_tax_entr();
			}else {
				value = dtoPrice.getTkut_tax_entr();
			}
		}
		return value;
	}

	private Long getTkutTaxRule(PriceTax taxSetValues, PriceTax dtoPrice) {
		Long value = null!=dtoPrice.getTkut_tax_rule()?dtoPrice.getTkut_tax_rule():1L;
		if(value==1L) {
			value = taxSetValues.getTkut_tax_rule();
		}else {
			value = dtoPrice.getTkut_tax_rule();
		}
		return value;
	}

	private Long getTkutTaxCode(int taxEmpty, PriceTax taxSetValues, PriceTax dtoPrice) {
		Long value = null!=dtoPrice.getTkut_tax_cd()?dtoPrice.getTkut_tax_cd():taxEmpty;
		if(value==1L) {
			 value = taxEmpty==-88?-88:taxSetValues.getTkut_tax_cd();
		}else if(value==-88) {
			value = taxSetValues.getTkut_tax_cd();
		}else {
			value = dtoPrice.getTkut_tax_cd();
		}
		return value;
	}
	private Long getOthTaxEntry(PriceTax taxSetValues, PriceTax dtoPrice) {
		Long value = null;
		if(null!=dtoPrice.getOth_tax_rule() && null!=dtoPrice.getOth_tax_cd()) {
			if(dtoPrice.getOth_tax_cd().equals(4L)) {
				value = null;
			}else if(dtoPrice.getOth_tax_entr() == null || dtoPrice.getOth_tax_entr().equals(1L)) {
				value = taxSetValues.getOth_tax_entr();
			}else {
				value = dtoPrice.getOth_tax_entr();
			}
		}
		return value;
	}

	private Long getOthTaxRule(PriceTax taxSetValues, PriceTax dtoPrice) {
		Long value = null!=dtoPrice.getOth_tax_rule()?dtoPrice.getOth_tax_rule():1L;
		if(value==1L) {
			value = taxSetValues.getOth_tax_rule();
		}else {
			value = dtoPrice.getOth_tax_rule();
		}
		return value;
	}

	private Long getOthTaxCode(int taxEmpty, PriceTax taxSetValues, PriceTax dtoPrice) {
		Long value = null!=dtoPrice.getOth_tax_cd()?dtoPrice.getOth_tax_cd():taxEmpty;
		if(value==1L) {
			 value = taxEmpty==-88?-88:taxSetValues.getOth_tax_cd();
		}else if(value==-88) {
			value = taxSetValues.getOth_tax_cd();
		}else {
			value = dtoPrice.getOth_tax_cd();
		}
		return value;
	}
	private List<PriceTax> calculatePriceAndTaxForAux(Map<Long, Product> products, String mitFlag, PriceTax taxSetValues, Map<Long, PriceTax> setMap,
			Product auxProduct, List<PriceTax> finalPrice, List<Long> notApprovedProducts, Boolean removeInactive) {
		PriceTax auxTaxSetValues = new PriceTax();
		if (!(mitFlag.equals(ProductDBConstant.MENU_ITEM_TAX_SET))) {
			auxTaxSetValues = taxSetValues;
		} else {
			auxTaxSetValues.setEatin_tax_cd(4L);
			auxTaxSetValues.setEatin_tax_rule(0L);
			auxTaxSetValues.setEatin_tax_entr(0L);
			auxTaxSetValues.setOth_tax_cd(4L);
			auxTaxSetValues.setOth_tax_rule(0L);
			auxTaxSetValues.setOth_tax_entr(0L);
			auxTaxSetValues.setTkut_tax_cd(4L);
			auxTaxSetValues.setTkut_tax_rule(0L);
			auxTaxSetValues.setTkut_tax_rule(0L);
		}
		List<String> priceEating = new ArrayList<>();
		List<String> priceTkut = new ArrayList<>();
		List<String> priceOth = new ArrayList<>();
		PriceTax taxFirstSubsItm = new PriceTax();
		PriceTax finalPriceAndTaxForAux = new PriceTax();
		for (Item dtoSubsItem : auxProduct.getSubstitutionList()) {
			if (!dtoSubsItem.getGroupId().equals("-1") && dtoSubsItem.getProductId()!=auxProduct.getProductId()) {
				PriceTax priceValuesForAux = setMap.get(dtoSubsItem.getProductId());
				Product productItem = products.get(dtoSubsItem.getProductId());
				if (notApprovedProducts != null && productItem != null
						&& !notApprovedProducts.contains(dtoSubsItem.getProductId())
						&& ((removeInactive && productItem.getActive() == 1) || !removeInactive)) {
					if (priceValuesForAux == null) {
						priceEating.add(null);
						priceTkut.add(null);
						priceOth.add(null);
						continue;
					}
					if(null!=priceValuesForAux.getEatin_prc()) {
						priceEating.add(priceValuesForAux.getEatin_prc().toString());
					} else {
						priceEating.add(null);
					}
					if(null!=priceValuesForAux.getTkut_prc()) {
						priceTkut.add(priceValuesForAux.getTkut_prc().toString());
					} else {
						priceTkut.add(null);
					}
					if(null!=priceValuesForAux.getOth_prc()) {
						priceOth.add(priceValuesForAux.getOth_prc().toString());
					} else {
						priceOth.add(null);
					}
				}
			}
		}

		// Sort the substitution list by subtitution name code
		if (auxProduct.getSubstitutionList() != null) {
			Collections.sort(auxProduct.getSubstitutionList(), new Comparator<Item>() {
				@Override
				public int compare(Item i1, Item i2) {
					return i1.getId().compareTo(i2.getId());
				}
			});
			if (!auxProduct.getSubstitutionList().isEmpty()) {
				Item firstSubsItm = null;
				for (Item item : auxProduct.getSubstitutionList()) {
					Product productItem = products.get(item.getProductId());
					// Set the tax of the first active menu item for auxiliary menu item
					if (productItem != null && productItem.getActive() == 1 && !productItem.getProductId().equals(auxProduct.getProductId())) {
						firstSubsItm = item;
						break;
					}
				}
				if (firstSubsItm != null) taxFirstSubsItm = setMap.get(firstSubsItm.getProductId());
			}
		}
		
		// Calculate Price Values for Aux Menu Item
		finalPriceAndTaxForAux = getAuxPriceValues(priceEating, priceTkut, priceOth, finalPriceAndTaxForAux);

		// Calculate Tax Values for Aux Menu Item
		finalPriceAndTaxForAux = getTaxValuesForAux(auxTaxSetValues, taxFirstSubsItm, finalPriceAndTaxForAux);

		finalPrice.add(finalPriceAndTaxForAux);

		return finalPrice;
	}

	private PriceTax getAuxPriceValues(List<String> priceEatin, List<String> priceTkut, List<String> priceOth,
			 PriceTax finalPriceAndTaxForAux) {	
		if((priceEatin.stream().distinct().count()>1 || priceTkut.stream().distinct().count()>1 || priceOth.stream().distinct().count()>1)
			|| (priceEatin.isEmpty()||priceTkut.isEmpty()||priceOth.isEmpty())){
			finalPriceAndTaxForAux.setEatin_prc(0.0);
			finalPriceAndTaxForAux.setOth_prc(0.0);
			finalPriceAndTaxForAux.setTkut_prc(0.0);
		}else {
			if (priceEatin.get(0) == null) {
				finalPriceAndTaxForAux.setEatin_prc(null);
			} else {
				finalPriceAndTaxForAux.setEatin_prc(Double.parseDouble(priceEatin.get(0)));
			}
			if (priceTkut.get(0) == null) {
				finalPriceAndTaxForAux.setTkut_prc(null);
			} else {
				finalPriceAndTaxForAux.setTkut_prc(Double.parseDouble(priceTkut.get(0)));
			}
			if (priceOth.get(0) == null) {
				finalPriceAndTaxForAux.setOth_prc(null);
			} else {
				finalPriceAndTaxForAux.setOth_prc(Double.parseDouble(priceOth.get(0)));
			}
			}
		return finalPriceAndTaxForAux;
	}

	private PriceTax getTaxValuesForAux(PriceTax auxTaxSetValues, PriceTax tax,
			PriceTax finalPriceAndTaxForAux) {
		if (tax == null || tax.getEatin_tax_cd() == null || tax.getEatin_tax_cd().equals(1L)) {
			finalPriceAndTaxForAux.setEatin_tax_cd(auxTaxSetValues.getEatin_tax_cd());
			finalPriceAndTaxForAux.setEatin_tax_entr(auxTaxSetValues.getEatin_tax_entr());
			finalPriceAndTaxForAux.setEatin_tax_rule(auxTaxSetValues.getEatin_tax_rule());
			finalPriceAndTaxForAux.setOth_tax_cd(auxTaxSetValues.getOth_tax_cd());
			finalPriceAndTaxForAux.setOth_tax_entr(auxTaxSetValues.getOth_tax_entr());
			finalPriceAndTaxForAux.setOth_tax_rule(auxTaxSetValues.getOth_tax_rule());
			finalPriceAndTaxForAux.setTkut_tax_cd(auxTaxSetValues.getTkut_tax_cd());
			finalPriceAndTaxForAux.setTkut_tax_entr(auxTaxSetValues.getTkut_tax_entr());
			finalPriceAndTaxForAux.setTkut_tax_rule(auxTaxSetValues.getTkut_tax_rule());
 		} else {
			finalPriceAndTaxForAux.setEatin_tax_cd(tax.getEatin_tax_cd());
			finalPriceAndTaxForAux.setEatin_tax_entr(tax.getEatin_tax_entr());
			finalPriceAndTaxForAux.setEatin_tax_rule(tax.getEatin_tax_rule());
			finalPriceAndTaxForAux.setOth_tax_cd(tax.getOth_tax_cd());
			finalPriceAndTaxForAux.setOth_tax_entr(tax.getOth_tax_entr());
			finalPriceAndTaxForAux.setOth_tax_rule(tax.getOth_tax_rule());
			finalPriceAndTaxForAux.setTkut_tax_cd(tax.getTkut_tax_cd());
			finalPriceAndTaxForAux.setTkut_tax_entr(tax.getTkut_tax_entr());
			finalPriceAndTaxForAux.setTkut_tax_rule(tax.getTkut_tax_rule());
		}
		return finalPriceAndTaxForAux;
	}

	public Map<Long, Product> calculateValuesForAllParameters(Map<Long, Product> products, String precisionValueForEating,
			String precisionValueForTakeout, String precisionValueForOther, Product product, Long productID,
			String trimmedPriceEating, String trimmedPriceOther, String trimmedPriceTakeOut) {
		if(product!=null && product.getParameters()!=null && !product.getParameters().isEmpty()) {
			List<Parameter> parametersList = product.getParameters();
			String calorieVal = "";
			String jouleVal = "";
			String miVolUnitValue = "";
			String baselineUnitMeasureValue = "";
			int paramCount = 0;
			String labelUnitValue = "";
			String priceUnitEatinValue = "";
			String priceUnitTakeOutValue = "";
			String priceUnitOtherValue = "";
			int isActiveTakeout = 0;
			int isActiveEatin = 0;
			int isActiveOther = 0;
			Parameter paramForLabelUnit = new Parameter();
			Parameter paramForbaselineUnitMeasure = new Parameter();
			Parameter paramFormiVolUnit = new Parameter();
			Parameter paramForEatinPPU = new Parameter();
			Parameter paramForTakeOutPPU = new Parameter();
			Parameter paramForOtherPPU = new Parameter();
			List<Parameter> finalListOfParam = new ArrayList<>();
			Map<String, Parameter> paramMap = new HashMap<>();
			for (Parameter list : parametersList) {
				if (list.getValue() != null) {
					if (paramCount == 0) {
						paramCount = paramCount + 1;
					}
				}
				
				if(list.getValue()!=null  && list.getValue().equals("-1")) {
					list.setValue("");
				}
				paramMap = getParams(list,paramMap);
				calorieVal = getCalorieValues(calorieVal, list);
				jouleVal = getJouleValues(jouleVal, list);
				if (!(list.getName().equals(ProductDBConstant.MENU_ITEM_VOLUME_UNIT))
						&& !(list.getName().equals(ProductDBConstant.BASELINE_UNIT_OF_MEASURE))
						&& !(list.getName().equals(ProductDBConstant.LABEL_UNIT))) {
					continue;
				}

				if(list.getName().equals(ProductDBConstant.MENU_ITEM_VOLUME_UNIT) && list.getValue()!=null) {
					miVolUnitValue = list.getValue();
				}
				if(list.getName().equals(ProductDBConstant.BASELINE_UNIT_OF_MEASURE) && list.getValue()!=null) {
					baselineUnitMeasureValue = list.getValue();
				}
				if(list.getName().equals(ProductDBConstant.LABEL_UNIT) && list.getValue()!=null) {
					labelUnitValue = list.getValue().toString();
				}
			}
			if (miVolUnitValue != null && !miVolUnitValue.isEmpty() && Double.parseDouble(miVolUnitValue) > 0.0
					&& baselineUnitMeasureValue != null && !baselineUnitMeasureValue.isEmpty() && Double.parseDouble(baselineUnitMeasureValue) > 0.0
					&& labelUnitValue != null) {
				isActiveEatin = 1;
				isActiveTakeout = 1;
				isActiveOther = 1;
			}
			for(Entry<String, Parameter> e: paramMap.entrySet()) {
				finalListOfParam.add(e.getValue());
			}
			//To calculate PPU
			finalListOfParam = calculatePricePerUnitValue(precisionValueForEating, precisionValueForTakeout, precisionValueForOther,
					trimmedPriceEating, trimmedPriceOther, trimmedPriceTakeOut, miVolUnitValue,
					baselineUnitMeasureValue, labelUnitValue, priceUnitEatinValue, priceUnitTakeOutValue,
					priceUnitOtherValue, isActiveTakeout, isActiveEatin, isActiveOther, paramForLabelUnit,
					paramForbaselineUnitMeasure, paramFormiVolUnit, paramForEatinPPU, paramForTakeOutPPU,
					paramForOtherPPU,finalListOfParam);
			
			if(finalListOfParam!=null && !finalListOfParam.isEmpty()) {
				product.setParameters(finalListOfParam);
			}
			products.put(productID, product);
		}
		return products;
	}
	private List<Parameter> calculatePricePerUnitValue(String precisionValueForEating, String precisionValueForTakeout,
			String precisionValueForOther, String trimmedPriceEating, String trimmedPriceOther,
			String trimmedPriceTakeOut, String miVolUnitValue, String baselineUnitMeasureValue, String labelUnitValue,
			String priceUnitEatinValue, String priceUnitTakeOutValue, String priceUnitOtherValue, int isActiveTakeout,
			int isActiveEatin, int isActiveOther, Parameter paramForLabelUnit, Parameter paramForbaselineUnitMeasure,
			Parameter paramFormiVolUnit, Parameter paramForEatinPPU, Parameter paramForTakeOutPPU,
			Parameter paramForOtherPPU, List<Parameter> finalListOfParam) {
		if (isActiveEatin == 1 || isActiveTakeout == 1 || isActiveOther == 1) {
			if(trimmedPriceEating!=null || trimmedPriceOther!=null ||trimmedPriceTakeOut!=null || (priceUnitEatinValue!=null && isActiveEatin==1) || (priceUnitTakeOutValue!=null && isActiveTakeout==1) || (priceUnitOtherValue!=null && isActiveOther ==1)) {
				paramForLabelUnit.setName(ProductDBConstant.LABEL_UNIT);
				paramForLabelUnit.setValue(labelUnitValue);
				paramForbaselineUnitMeasure.setName(ProductDBConstant.BASELINE_UNIT_OF_MEASURE);
				paramForbaselineUnitMeasure.setValue(baselineUnitMeasureValue);
				paramFormiVolUnit.setName(ProductDBConstant.MENU_ITEM_VOLUME_UNIT);
				paramFormiVolUnit.setValue(miVolUnitValue);
				paramForEatinPPU = getPricePerUnitEatinParam(precisionValueForEating, trimmedPriceEating, Double.parseDouble(miVolUnitValue),
					Double.parseDouble(baselineUnitMeasureValue), priceUnitEatinValue, isActiveEatin, paramForEatinPPU);
				paramForTakeOutPPU = getPricePerUnitTakeOutParam(precisionValueForTakeout, trimmedPriceTakeOut, Double.parseDouble(miVolUnitValue),
					Double.parseDouble(baselineUnitMeasureValue), priceUnitTakeOutValue, isActiveTakeout, paramForTakeOutPPU);
				paramForOtherPPU = getPricePerUnitOtherParam(precisionValueForOther, trimmedPriceOther, Double.parseDouble(miVolUnitValue),
					Double.parseDouble(baselineUnitMeasureValue), priceUnitOtherValue, isActiveOther, paramForOtherPPU);
			}
		}
		if(paramForLabelUnit.getName()!=null)finalListOfParam.add(paramForLabelUnit);
		if(paramForbaselineUnitMeasure.getName()!=null)finalListOfParam.add(paramForbaselineUnitMeasure);
		if(paramFormiVolUnit.getName()!=null)finalListOfParam.add(paramFormiVolUnit);
		if(paramForEatinPPU.getName()!=null)finalListOfParam.add(paramForEatinPPU);
		if(paramForTakeOutPPU.getName()!=null)finalListOfParam.add(paramForTakeOutPPU);
		if(paramForOtherPPU.getName()!=null)finalListOfParam.add(paramForOtherPPU);
		return finalListOfParam;
	}

	private Parameter getPricePerUnitOtherParam(String precisionValueForOther, String trimmedPriceOther,
			double miVolUnitValue, double baselineUnitMeasureValue, String priceUnitOtherValue, int isActiveOther,
			Parameter paramForOtherPPU) {
		double totalPerUnitForOthers;
		if(isActiveOther==1) {
			if(priceUnitOtherValue.isEmpty()) {
				totalPerUnitForOthers = getPricePerUnitForOther(trimmedPriceOther,miVolUnitValue,baselineUnitMeasureValue);
				paramForOtherPPU.setName(ProductDBConstant.PRICE_PER_UNIT_OTHER);
			String	totalPerUnitForOthersValue = getPPUValueForOther(String.valueOf(totalPerUnitForOthers),precisionValueForOther);
			paramForOtherPPU.setValue(totalPerUnitForOthersValue);
			}else {
				paramForOtherPPU.setName(ProductDBConstant.PRICE_PER_UNIT_OTHER);
				priceUnitOtherValue = getPPUValueForOther(priceUnitOtherValue,precisionValueForOther);
				paramForOtherPPU.setValue(priceUnitOtherValue);
			}
		}
		return paramForOtherPPU;
	}

	private Parameter getPricePerUnitTakeOutParam(String precisionValueForTakeout, String trimmedPriceTakeOut,
			double miVolUnitValue, double baselineUnitMeasureValue, String priceUnitTakeOutValue, int isActiveTakeout,
			Parameter paramForTakeOutPPU) {
		double totalPerUnitForTakeOut;
		if(isActiveTakeout==1) {
			if(priceUnitTakeOutValue.isEmpty()) {
				totalPerUnitForTakeOut = getPricePerUnitForTakeOut(trimmedPriceTakeOut,miVolUnitValue,baselineUnitMeasureValue);
				paramForTakeOutPPU.setName(ProductDBConstant.PRICE_PER_UNIT_TAKEOUT);
				String totalPerUnitForTakeOutValue = getPPUValueForTakeOut(String.valueOf(totalPerUnitForTakeOut),precisionValueForTakeout);
				paramForTakeOutPPU.setValue(totalPerUnitForTakeOutValue);
			}else {
				paramForTakeOutPPU.setName(ProductDBConstant.PRICE_PER_UNIT_TAKEOUT);
				priceUnitTakeOutValue = getPPUValueForTakeOut(priceUnitTakeOutValue,precisionValueForTakeout);
				paramForTakeOutPPU.setValue(priceUnitTakeOutValue);
			}
		}
		return paramForTakeOutPPU;
	}

	private Parameter getPricePerUnitEatinParam(String precisionValueForEating, String trimmedPriceEating,
			double miVolUnitValue, double baselineUnitMeasureValue, String priceUnitEatinValue, int isActiveEatin,
			Parameter paramForEatinPPU) {
		double totalPerUnitForEating;
		if(isActiveEatin==1) {
			if(priceUnitEatinValue.isEmpty()) {
				totalPerUnitForEating = getPricePerUnitForEating(trimmedPriceEating,miVolUnitValue,baselineUnitMeasureValue);
				paramForEatinPPU.setName(ProductDBConstant.PRICE_PER_UNIT_EATIN);
				String totalPerUnitForEatingValue = getPPUValueForEatin(String.valueOf(totalPerUnitForEating),precisionValueForEating);
				paramForEatinPPU.setValue(totalPerUnitForEatingValue);
			}else {
				paramForEatinPPU.setName(ProductDBConstant.PRICE_PER_UNIT_EATIN);
				priceUnitEatinValue = getPPUValueForEatin(priceUnitEatinValue,precisionValueForEating);
				paramForEatinPPU.setValue(priceUnitEatinValue);
			}
		}
		return paramForEatinPPU;
	}

	private String getJouleValues(String jouleVal, Parameter list) {
		if(list.getName().equals(ProductDBConstant.NUTRITIONAL_INFO_KJRANGE) && list.getValue()!=null) {
			jouleVal = (list.getValue()).trim();
		}else if(list.getName().equals(ProductDBConstant.NUTRITIONAL_INFO_KJ) && (jouleVal.isEmpty())) {
			 if(list.getValue()!=null)jouleVal = list.getValue();
		}
		return jouleVal;
	}

	private String getCalorieValues(String calorieVal, Parameter list) {
		if(list.getName().equals(ProductDBConstant.NUTRITIONAL_INFO_KCALRANGE) && list.getValue()!=null) {
			calorieVal = (list.getValue()).trim();
		}else if(list.getName().equals(ProductDBConstant.NUTRITIONAL_INFO_KCAL) && (calorieVal.isEmpty())) {
			 if(list.getValue()!=null)calorieVal = list.getValue();
		}
		return calorieVal;
	}

	private Map<String, Parameter> getParams(Parameter list, Map<String, Parameter> paramMap) {
		Parameter param = new Parameter();
		if(!(list.getName().equals(ProductDBConstant.PRICE_PER_UNIT_EATIN)) && !(list.getName().equals(ProductDBConstant.PRICE_PER_UNIT_TAKEOUT)) && !(list.getName().equals(ProductDBConstant.PRICE_PER_UNIT_OTHER)) && !(list.getName().equals(ProductDBConstant.MENU_ITEM_VOLUME_UNIT)) && !(list.getName().equals(ProductDBConstant.BASELINE_UNIT_OF_MEASURE)) && !(list.getName().equals(ProductDBConstant.LABEL_UNIT)) && (list.getValue()!=null)) {
			param.setName(list.getName());
			if(list.getDataType()==3L && list.getValue()!=null) {
				param.setValue(list.getValue());
			}else {
				param.setValue(list.getValue());
			}
			paramMap.put(param.getName(), param);
		}
		
		return paramMap;
	}
	private String getPPUValueForOther(String priceUnitOthers, String precisionValueForOther) {
		String trimmedPricePerUnitForOther = "";
		DecimalFormat decimalFormat = new DecimalFormat(precisionValueForOther);
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		double price = Double.parseDouble(priceUnitOthers);
		trimmedPricePerUnitForOther = decimalFormat.format(price);		
	return trimmedPricePerUnitForOther;	
	}

	private double getPricePerUnitForOther(String trimmedPriceOther, double miVolUnitValue, double baselineUnitMeasureValue) {
		double totalPerUnitForOther=0;
		if(trimmedPriceOther!=null && !trimmedPriceOther.isBlank()) {
			totalPerUnitForOther = ((Double.parseDouble(trimmedPriceOther))/miVolUnitValue)*baselineUnitMeasureValue;
		}
		return totalPerUnitForOther;
	}

	private String getPPUValueForTakeOut(String priceUnitTakeout, String precisionValueForTakeout) {
		String trimmedPricePerUnitForTakeOut = "";
		DecimalFormat decimalFormat = new DecimalFormat(precisionValueForTakeout);
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		double price = Double.parseDouble(priceUnitTakeout);
		trimmedPricePerUnitForTakeOut = decimalFormat.format(price);		
	return trimmedPricePerUnitForTakeOut;	
	}

	private double getPricePerUnitForTakeOut(String trimmedPriceTakeOut, double miVolUnitValue,
			double baselineUnitMeasureValue) {
		double totalPerUnitForTakeOut=0;
		if(trimmedPriceTakeOut!=null && !trimmedPriceTakeOut.isBlank()) {
			 totalPerUnitForTakeOut = ((Double.parseDouble(trimmedPriceTakeOut))/miVolUnitValue)*baselineUnitMeasureValue;
		}
		return totalPerUnitForTakeOut;
	}

	private String getPPUValueForEatin(String priceUnitEating, String precisionValueForEating) {
			String trimmedPricePerUnitForEatin = "";
				DecimalFormat decimalFormat = new DecimalFormat(precisionValueForEating);
				decimalFormat.setRoundingMode(RoundingMode.UP);
				double price = Double.parseDouble(priceUnitEating);
				trimmedPricePerUnitForEatin = decimalFormat.format(price);		
			return trimmedPricePerUnitForEatin;	
	}

	private double getPricePerUnitForEating(String trimmedPriceEating, double miVolUnitValue,
			double baselineUnitMeasureValue) {
		double totalPerUnitForEating=0;
		if(trimmedPriceEating!=null && !trimmedPriceEating.isBlank()) {
			 totalPerUnitForEating = ((Double.parseDouble(trimmedPriceEating))/miVolUnitValue)*baselineUnitMeasureValue;
		}
		return totalPerUnitForEating;
		
	}
	/*
	 * This method returns the precision value for Eating, Takeout and other
	 * 
	 * @param mktId, priceType
	 * 
	 * @return precisionValue
	 */
	public String getPrecisionValue(Long mktId, String priceType) throws Exception {
		String precisionValue = "";
		String globalParamPrecision = productDBDAO.getValuesFromGlobalParam(mktId, priceType);
		if (globalParamPrecision.equals("4")) {
			precisionValue = "#0.0000";
		} else if (globalParamPrecision.equals("3")) {
			precisionValue = "#0.000";
		} else if (globalParamPrecision.equals("2")) {
			precisionValue = "#0.00";
		} else if (globalParamPrecision.equals("1")) {
			precisionValue = "#0.0";
		} else if (globalParamPrecision.equals("0")) {
			precisionValue = "#0";
		} else {
			precisionValue = "#0.00";
		}
		return precisionValue;
	}

	private Map<Long, String> getTaxCodeList() {
		Map<Long, String> taxMap = new HashMap<>();
		taxMap.put(1L, "DEFAULT");
		taxMap.put(2L, "ALWAYS");
		taxMap.put(3L, "OPTIONAL");
		taxMap.put(4L, NEVER);
		return taxMap;
	}

	private Map<Long, String> getTaxRuleList() {
		Map<Long, String> taxRule = new HashMap<>();
		taxRule.put(2L, "FLAT");
		taxRule.put(3L, "EXCISE");
		taxRule.put(4L, "GST");
		taxRule.put(5L, "PST");
		taxRule.put(6L, "VAT");
		taxRule.put(7L, "BREAK_TABLE");
		taxRule.put(8L, "FISCAL_PRINTER");
		taxRule.put(9L, "TAX_CHAIN");
		return taxRule;
	}

	/*
	 * This method returns the collection of product for both Presentation and
	 * Production Routing
	 * 
	 * @param productDBRequest
	 * 
	 * @return products
	 */
	public Collection<Product> getPresentationAndProductRouting(ProductDBRequest productDBRequest) throws Exception {
		RequestDTO dto = new RequestDTO();
		Collection<Product> products = new ArrayList<>();
		dto.setEffectiveDate(productDBRequest.getEffectiveDate());
		dto.setMarketId(productDBRequest.getMktId());
		dto.setNodeId(productDBRequest.getNodeId());
		Map<Long, Product> listofProductsByMaster = getMergedProductsByRest(dto);
		ProductPosKvs productPosKvs = listofProductsByMaster.get(productDBRequest.getProductId()).getProductPosKvs();
		Long prdGrpID = productPosKvs.getProductionMenuItemGroup().getCode();
		final List<Map<String, Object>> routingSetsForPresentationAndProductIon = layeringDBDAO.getRoutingSets(
				productDBRequest.getMktId(), productDBRequest.getRestId(), productDBRequest.getRestInstId());
		Long productID = productDBRequest.getProductId();
		Product product = new Product();
		Production production = new Production();
		List<KVSRoutes> kVSRoutesList = new ArrayList<>();
		KVSRoutes kVSRoutes = new KVSRoutes();
		PPG ppg = new PPG();
		String ppgValue = null;
		List<Route> routesList = new ArrayList<>();
		for (final Map<String, Object> routingList : routingSetsForPresentationAndProductIon) {
			if ((routingList.get(ProductDBConstant.PMI_GRP_ID).toString()).equals(prdGrpID.toString())) {
				Route route = new Route();
				route.setId(routingList.get(ProductDBConstant.RTE_ID).toString());
				routesList.add(route);
				ppg.setPpg(routingList.get(ProductDBConstant.PMI_GRP_DS).toString());
				ppgValue = ppg.getPpg();
			}
		}
		kVSRoutes.setRoute(routesList);
		kVSRoutesList.add(kVSRoutes);
		production.setkVSRoutes(kVSRoutesList);
		production.setPpg(ppgValue);
		product.setProduction(production);
		product.setProductId(productID);
		products.add(product);
		return products;
	}
	
	public void bzirule(Map<Long, Product> allProducts,PackageGeneratorDTO dto) throws Exception {
		if(LOGGER.isInfoEnabled()) LOGGER.info("bzirule");
		Long layering = Long.parseLong(productDBDAO.getValuesFromGlobalParam(dto.getMarketID(), ProductDBConstant.MENU_ITEM_LAYERING_LOGIC_TYPE));
		for (Map.Entry<Long, Product> entry : new HashMap<Long, Product>(allProducts).entrySet()) {
			Product product=allProducts.get(entry.getKey());
			if(layering==1L && entry!=null && entry.getValue().getActive()==1 ) {
				if(null!=product) {
					if(product.getProductClass()!=null && (product.getProductClass().equals(CHOICE) || product.getProductClass().equals("CHECK CATEGORY") || product.getProductClass().equals("CHOICE_EVM") ) ) {
						List<Component> componentList=product.getComponents();
						int countChoice=0;
						if(componentList!=null) {
						for(Component component:componentList) {
							Product productCompoStatus=allProducts.get(component.getComponentProductId());
							if(null!=productCompoStatus&&productCompoStatus.getActive()==1) {
								countChoice++;
							}
						}}
						if(countChoice==0) {
							product.setActive(0);
							LOGGER.info("Status of menu item " + product.getProductCode().toString() + " set to Inactive");
						}
		             }
					if(product.getProductClass().equals("VALUE_MEAL") || product.getProductClass().equals("CONTAINER_VALUE_MEAL") ) {
						List<Component> componentList=product.getComponents();
						int countChoice=0;
						if(componentList!=null) {
						for(Component component:componentList) {
							Product productCompoStatus=allProducts.get(component.getComponentProductId());
							if(null!=productCompoStatus&&productCompoStatus.getActive()==0) {
								countChoice++;
							}
						}}
						if(countChoice > 0) {
							product.setActive(0);
							LOGGER.info("Status of menu item " + product.getProductCode().toString() + " set to Inactive");
						}
						
					}
				}
             }else if(layering==2L && entry!=null && entry.getValue().getActive()==1 ) {
            	if(null!=product) {
            		 if(product.getProductClass().equals(CHOICE) || product.getProductClass().equals("CHECK CATEGORY") || product.getProductClass().equals("CHOICE_EVM")  ) {
 						List<Component> componentList=product.getComponents();
 						int countChoice=0;
 						if(componentList!=null) {
 						for(Component component:componentList) {
 							Product productCompoStatus=allProducts.get(component.getComponentProductId());
 							if(null!=productCompoStatus&&productCompoStatus.getActive()==1) {
 								countChoice++;
 							}
 						}}
 						if(countChoice==0) {
 							product.setActive(1);
 							if(componentList!=null) {
 							for(Component component:componentList) {
								Product productCompoStatus=allProducts.get(component.getComponentProductId());
								if(productCompoStatus != null) {
								productCompoStatus.setActive(1);
								}
							}
 						}}
 		             }
            		 if(product.getProductClass().equals("VALUE_MEAL") || product.getProductClass().equals("CONTAINER_VALUE_MEAL") ) {
 						List<Component> componentList=product.getComponents();
 						int countChoice=0;
 						if(componentList!=null) {
 						for(Component component:componentList) {
 							Product productCompoStatus=allProducts.get(component.getComponentProductId());
 							if(null!=productCompoStatus&&productCompoStatus.getActive()==0) {
 								countChoice++;
 							}
 						}}
 						if(countChoice > 0) {
 							product.setActive(1);
 							if(componentList!=null) {
 							for(Component component:componentList) {
								Product productCompoStatus=allProducts.get(component.getComponentProductId());
								if(productCompoStatus != null) {
								productCompoStatus.setActive(1);
								}
							}}
 							
 						}
 						
 					}
 				}
            	 
            }
		}
	}

	public void defaultProductbziRule(Map<Long, Product> allProducts) {
		if(LOGGER.isInfoEnabled()) LOGGER.info("defaultProductbziRule");
		for (Map.Entry<Long, Product> entry : new HashMap<Long, Product>(allProducts).entrySet()) {
			if (entry != null && entry.getValue().getApprovalStatus() == 1) {
				Product product = allProducts.get(entry.getKey());
				if (null != product) {
					List<Component> componentList = product.getComponents();
					if (null != componentList)
						for (Component component : componentList) {
							Product productCompoStatus = allProducts.get(component.getComponentProductId());
							if (null != productCompoStatus && productCompoStatus.getApprovalStatus() == 1
									&& component.getDefaultProductId() != null) {
								List<Long> choiceComponentList = checkChoiceHerarichyComponent(
										component.getComponentProductId(), allProducts);
								if (choiceComponentList.contains(component.getDefaultProductId())) {
									component.setDefaultProductId(null);
								}
							}

						}
				}

			}
		}

	}

	public List<Long> checkChoiceHerarichyComponent(Long productId, Map<Long, Product> allProducts) {
		List<Long> choiceComponentList = new ArrayList<>();
		Product product = allProducts.get(productId);
		if (null != product && product.getProductClass().equals(CHOICE) && product.getApprovalStatus() == 1) {
			choiceComponentList.add(productId);
			if (null != product.getComponents() && !product.getComponents().isEmpty()) {
				for (Component component : product.getComponents()) {
					Product productCompoStatus = allProducts.get(component.getComponentProductId());
					if (null != productCompoStatus && productCompoStatus.getApprovalStatus() == 1
							&& component.getDefaultProductId() != null) {
						checkChoiceHerarichyComponent(productCompoStatus.getProductId(), allProducts);
					}
				}
			} else {
				return choiceComponentList;
			}

		}
		return choiceComponentList;
	}

	public String getExcludeInactiveMI() {
		return excludeInactiveMI;
	}

	public void setExcludeInactiveMI(String excludeInactiveMI) {
		this.excludeInactiveMI = excludeInactiveMI;
	}

}
