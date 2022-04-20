package com.rfm.packagegeneration.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rfm.packagegeneration.dto.Adaptors;
import com.rfm.packagegeneration.dto.BunBufferDetails;
import com.rfm.packagegeneration.dto.BusinessLimits; 
import com.rfm.packagegeneration.dto.CategoryHours;

import com.rfm.packagegeneration.dto.Configurations;

import com.rfm.packagegeneration.dto.ColorDb;

import com.rfm.packagegeneration.dto.CategoryDetails;
import com.rfm.packagegeneration.dto.DayPartSet;
import com.rfm.packagegeneration.dto.DeliverySetDetails;
import com.rfm.packagegeneration.dto.Deposit;
import com.rfm.packagegeneration.dto.DiscountTable;
import com.rfm.packagegeneration.dto.Fee;
import com.rfm.packagegeneration.dto.FlavourSet;
import com.rfm.packagegeneration.dto.HotBusinessLimit;
import com.rfm.packagegeneration.dto.IngredientGroupDetails;
import com.rfm.packagegeneration.dto.Localization;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PopulateDrinkVol;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductDBRequest;
import com.rfm.packagegeneration.dto.ProductGroup;
import com.rfm.packagegeneration.dto.Production;
import com.rfm.packagegeneration.dto.PromotionData;
import com.rfm.packagegeneration.dto.PromotionGroupDetail;
import com.rfm.packagegeneration.dto.PromotionImages;
import com.rfm.packagegeneration.dto.ProviderDetails;
import com.rfm.packagegeneration.dto.RequestDTO;
import com.rfm.packagegeneration.dto.Restaurant;
import com.rfm.packagegeneration.dto.ScreenRequest;
import com.rfm.packagegeneration.dto.SizeSelection;
import com.rfm.packagegeneration.dto.StoreDB;
import com.rfm.packagegeneration.dto.StoreDBRequest;
import com.rfm.packagegeneration.dto.StoreDetails;
import com.rfm.packagegeneration.dto.StoreHours;
import com.rfm.packagegeneration.dto.StorePromotionDiscounts;
import com.rfm.packagegeneration.dto.TaxDefinition;
import com.rfm.packagegeneration.dto.TaxTable;
import com.rfm.packagegeneration.dto.TenderTypes;
import com.rfm.packagegeneration.service.LayeringProductService;
import com.rfm.packagegeneration.service.LogDetailsService;
import com.rfm.packagegeneration.service.ProductDBService;
import com.rfm.packagegeneration.service.PromotionDBService;
import com.rfm.packagegeneration.service.ScreenService;
import com.rfm.packagegeneration.service.StoreDBService;


@RestController
//@Profile(value={"dev", "qa", "test"})
@RequestMapping()
public class TestController {
	@Autowired
	ProductDBService productDbService;

	@Autowired
	LayeringProductService layeringProductService;
	
	@Autowired
	LogDetailsService logDetailsService;
	
	@Autowired
	ScreenService screenService;

	@Autowired
	StoreDBService storeDBService;
	

	@PostMapping("/generateProductDBForDimensionGroup")
	public Map<Long, Product> generateEvents(@RequestBody ProductDBRequest productDBRequest) throws Exception {
		Map<Long, Product> collectionProduct = new HashMap<>();
		// Calls the service class to fetch the productDBDataforDimensionGroup		
		collectionProduct = layeringProductService.getProductDBDataForDimensionGroup(collectionProduct, productDBRequest.getEffectiveDate(),  
				productDBRequest.getMktId(), productDBRequest.getNodeId());
		return collectionProduct;
	}

	@PostMapping("/generateProductCategoryDB")
	public Map<Long, Product> generateEventsForCategory(@RequestBody ProductDBRequest productDBRequest)
			throws Exception {
		Map<Long, Product> collectionProduct = new HashMap<>();
		// Calls the service class to fetch the productDBDataforCategory			
		collectionProduct = layeringProductService.getCategoryVals(collectionProduct, productDBRequest.getEffectiveDate());
		return collectionProduct;
	}
	@PostMapping("/generateProductDBForPromotionGroup")
	public Map<Long, Product>  generateEventsForPromotionGroup(@RequestBody ProductDBRequest productDBRequest)
			throws Exception {
		Map<Long, Product>  collectionProduct = new HashMap<>();
		// Calls the service class to fetch the productDBDataforPromotionGroup
		collectionProduct = layeringProductService.getProductDBDataForPromotionGroup(collectionProduct, productDBRequest.getMktId(), productDBRequest.getEffectiveDate());
		return collectionProduct;
	}

	@PostMapping("/getRestaurantSetsDB")
	public Restaurant getRestaurantSets(@RequestBody RequestDTO dto) throws Throwable {
		return layeringProductService.getRestaurantSets(dto);
	}

	@PostMapping("/getMergedProductsByRest")
	public Map<Long, Product> getMergedProductsByRest(@RequestBody RequestDTO dto) throws Throwable {
		return layeringProductService.getMergedProductsByRest(dto);
	}

	@PostMapping("/getProductParametersByRest")
	public Map<Long, Product> getProductParametersByRest(@RequestBody RequestDTO dto) throws Throwable {
		return layeringProductService.getProductParametersByRest(dto);
	}

	@PostMapping("/generatePresentationAndProductRouting")
	public Collection<Product> generatePresentationAndProductRouting(@RequestBody ProductDBRequest productDBRequest)
			throws Exception {
		Collection<Product> collectionProduct = new ArrayList<>();
		// Calls the service class to fetch the productDBDataForRouting
		collectionProduct = layeringProductService.getPresentationAndProductRouting(productDBRequest);
		return collectionProduct;
	}

	@PostMapping("/getMergedPromotionProductsByRest")
	public Map<Long, Product> getMergedPromotionProductsByRest(@RequestBody RequestDTO dto) throws Throwable {
		return layeringProductService.getMergedPromotionRangeProductsByRest(dto);
	}
	
	@PostMapping("/getProductShortcutSettingsByRest")
	public Map<Long, Product> getProductShortcutSettingsByRest(@RequestBody RequestDTO dto) throws Throwable {
		return layeringProductService.getProductShortcutSettingsByRest(dto);
	}
	
	@PostMapping("/getProductAssociatedCategoriesByRest")
	public Map<Long,Product> getProductPromotionAssociationByRest(@RequestBody RequestDTO dto) throws Throwable{
			return layeringProductService.getProductAssociatedCategoriesByRest(dto);	
	}
	
	@PostMapping("/getProductPromotionAssociationByRest")
	public Map<Long,Product> getProductsPromotionAssociationByRest(@RequestBody RequestDTO dto) throws Throwable{
			return layeringProductService.getProductPromotionAssociationByRest(dto);	
	}

	@PostMapping("/getProductTagsByRest")
	public Map<Long,Product> getProductTagsByRest(@RequestBody RequestDTO dto) throws Throwable{
			return layeringProductService.getProductTagsByRest(dto);
	}
	

	@PostMapping("/getMergedProductsByRestRedis")
	public   Map<Long, Product> getMergedProductsByRestRedis(@RequestBody PackageGeneratorDTO dto) throws Throwable{
		return layeringProductService.getMergedProductsByRest(dto);
	}
	@PostMapping("/getXMLAttribute")
	public Collection<Product> getXMLSeqNumber(@RequestBody ProductDBRequest productDBRequest) throws Exception {
		Collection<Product> collectionProduct = new ArrayList<>();
		collectionProduct = productDbService.getXMLAttribute(productDBRequest);
		return collectionProduct;
	}
	/*@PostMapping("/getDiscountNotAllowed")
	public Collection<Product> discountNotAllowed(@RequestBody ProductDBRequest productDBRequest) throws Exception {
		Collection<Product> collectionProduct = new ArrayList<>();
		collectionProduct = productDbService.discountNotAllowed(productDBRequest);
		return collectionProduct;
	}*/
	
	@PostMapping("/getColorsandMediaValues")
	public Product getColorsandMediaValues(@RequestBody ProductDBRequest productDBRequest) throws Throwable {
		return productDbService.getColorsandMediaValues(productDBRequest);
	}
	
	//Added to test RFMP-11609
	@PostMapping("/getButtonWorkflowParameters")
	public  Map<String,List<Map<String, String>>> getButtonWorkflowParameters(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getButtonWorkflowParameters(screenRequest.getButtonWorkflowAssignIds());
	}
	@PostMapping("/getMasterButtonDetails")
	public 	Map<String,Map<String,Map<String,String>>> getMasterButtonDetails(@RequestBody ScreenRequest screenRequest) throws Throwable {	
		return screenService.getMasterButtonDetails(screenRequest.getScreenIds(), screenRequest.getScreenInstIds());
	}
	@PostMapping("/getButtonLangDetails")
	public Map<String,Map<String,Map<String,String>>> getButtonLangDetails(@RequestBody ScreenRequest screenRequest) throws Throwable {	
		return screenService.getButtonLangDetails(screenRequest.getScreenIds(), screenRequest.getScreenInstIds(), screenRequest.getLangIds(), screenRequest.getMarketLocaleId());
	}
	@PostMapping("/getScreenDetails")
	public Map<String,Map<String,String>> getScreenDetails(@RequestBody ScreenRequest screenRequest) throws Throwable{
		return screenService.getScreenDetails(screenRequest.getScreenSetId(), screenRequest.getEffectiveDate());
	}
	
	@PostMapping("/getAllLocales")
	public List<Map<String, String>> getAllLocales(@RequestBody ScreenRequest screenRequest) throws Throwable{
		return screenService.getAllLocales(screenRequest.getMktId(), screenRequest.getMarketLocaleId(), screenRequest.getEffectiveDate());
	}
	
	@PostMapping("/getAllHotScreens")
	public List<String> getHotScreens(@RequestBody ScreenRequest request) throws Throwable {
		return screenService.getHotScreens(request.getScheduleRequestID(), request.getNodeId(), request.getEffectiveDate());
	}
	
	@PostMapping("/getDefaultButtonCaption")
	public String getDefaultButtonCaption(@RequestBody ScreenRequest request) throws Throwable {
		return screenService.getDefaultButtonCaption(request.getScheduleRequestID(), request.getMktId());
	}
	
	@PostMapping("/getLocalizationSets")
	public Map<String,String> getLocalizationSets(@RequestBody ScreenRequest request) throws Throwable {
		return screenService.getLocalizationSets(request.getRestId(), request.getRestInstId(),request.getMktId());
	}
	
	@PostMapping("/getLocalizedFields")
	public Map<String,String> getLocalizedFields(@RequestBody ScreenRequest request) throws Throwable {
		return screenService.getLocalizedFields(request.getRestId(), request.getRestInstId(), request.getEffectiveDate(),request.getMktId());
	}
	
	@PostMapping("/getDynamicWorkflowParameters")
	public List<String> getDynamicWorkflowParameters(@RequestBody ScreenRequest request) throws Throwable {
		return screenService.getDynamicWorkflowParameters(request.getScheduleRequestID(), request.getMktId(), request.getWorkflowName());
	}

	@PostMapping("/getAssignedScreenSet")
	public Map<String, String> getAssignedScreenSet(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getAssignedScreenSet(screenRequest.getMktId(), screenRequest.getRestId(), screenRequest.getRestInstId(), screenRequest.getEffectiveDate());
	}
	
	@PostMapping("/getScheduleSize")
	public  Map<String, Long> getScheduleSize(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getScheduleSize(screenRequest.getScheduleRequestID(), screenRequest.getMktId());
	}
	@PostMapping("/getAllMedia")
	public Map<String, String> getAllMedia(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getAllMedia(screenRequest.getScheduleRequestID(), screenRequest.getMktId());
	}
	@PostMapping("/getDefaultLocaleId")
	public String getDefaultLocaleId(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getDefaultLocaleId(screenRequest.getScheduleRequestID(),screenRequest.getMktId());
	}
	@PostMapping("/getAllColors")
	public Map<String, String> getAllColors(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getAllColors(screenRequest.getScheduleRequestID(), screenRequest.getMktId());
	}
	
	
	@PostMapping("/getBLMButtonDetail")
	public Map<String,Map<String, Map<String, String>>> getBLMButtonDetail(@RequestBody ScreenRequest request) throws Throwable {
		final Map<String, String> screenSet = screenService.getAssignedScreenSet(request.getMktId(), request.getRestId(), request.getRestInstId(), request.getEffectiveDate());			
		final Long screenSetId = Long.valueOf(screenSet.get("SET_ID"));
		final String screenSetName = screenSet.get("SET_NA");
				
		Map<String, Map<String, String>> masterScreensInfo =  screenService. getScreenDetails(screenSetId, request.getEffectiveDate());				
		Map<String,Map<String, Map<String, String>>> blmButtons = screenService.getBLMButtonDetails(masterScreensInfo,  screenSetName,  request.getNodeId().toString(),
				request.getEffectiveDate()); 
		 
		return blmButtons;
	}
	
	@PostMapping("/getScreenWorkflows")
	public  Map<String, List<Map<String, String>>> getScreenWorkflows(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getScreenWorkflows(screenRequest.getScreenIds(), screenRequest.getScreenInstIds());
	}
	
	@PostMapping("/getScreenWorkflowParameters")
	 public  Map<String,List<Map<String, String>>> getScreenWorkflowParameters(@RequestBody ScreenRequest screenRequest) throws Throwable{
		   return screenService.getScreenWorkflowParameters(screenRequest.getScreenWorkflowAssignId());
	   }
	   
	@PostMapping("/getScreenLookupParameter") 
	public Map<String, String> getScreenLookupParameter(@RequestBody ScreenRequest screenRequest) throws Throwable{
		   return screenService.getScreenLookupParameter(screenRequest.getLiskWorkflowParams(), screenRequest.getMarketLocaleId());
	   }
	   
	@PostMapping("/getRestaurantMenuItems")   
	public List<String> getRestaurantMenuItems(@RequestBody ScreenRequest screenRequest) throws Throwable{
		   return screenService.getRestaurantMenuItems(screenRequest.getUniqueId(), screenRequest.getEffectiveDate());
	   }
	   
	@PostMapping("/getDynamicButtonDetails")  
	public Map<String, Map<String, Map<String, String>>> getDynamicButtonDetails(@RequestBody ScreenRequest screenRequest) throws Throwable{
		   return screenService.getDynamicButtonDetails(screenRequest.getScreenIds(), screenRequest.getScreenInstIds(), new HashMap<>(), screenRequest.getEffectiveDate());
	   }
	
	@PostMapping("/getAllWorkflows")
	public Map<String, String> getAllWorkflows(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getAllWorkflows(screenRequest.getScheduleRequestID(),screenRequest.getMktId());
	}
	@PostMapping("/getAllEvents")
	public Map<String, String> getAllEvents(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getAllEvents(screenRequest.getScheduleRequestID(),screenRequest.getMktId());
	}
	@PostMapping("/getAllWorkflowParams")
	public Map<String, String> getAllWorkflowParams(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getAllWorkflowParams(screenRequest.getScheduleRequestID(),screenRequest.getMktId());
	}
	@PostMapping("/getAllGrillGroups")
	public Map<String, String> getAllGrillGroups(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getAllGrillGroups(screenRequest.getScheduleRequestID(),screenRequest.getMktId());
	}
	@PostMapping("/getAllSmartReminders")
	public Map<String, String> getAllSmartReminders(@RequestBody ScreenRequest screenRequest) throws Throwable {
		return screenService.getAllSmartReminders(screenRequest.getScheduleRequestID(),screenRequest.getMktId());

	}
	
	@PostMapping("/getButtonWorkflows")
	public Map<String, List<Map<String, String>>> getButtonWorkflows(@RequestBody ScreenRequest request) throws Throwable {		 
		
		Map<String, List<Map<String, String>>> workflows = screenService.getButtonWorkflows(request.getButtonsIds(), request.getButtonsInstIds()); 		 
		return workflows;
	}
	

	@PostMapping("/getButtonProductCode")
	public Map<String, String> getButtonProductCode(@RequestBody ScreenRequest request) throws Throwable {
		
		Map<String, String> buttonProductCode = screenService.getButtonProductCode(request.getButtonsIds(), request.getButtonsInstIds()); 
		 
		return  buttonProductCode;
	}
	
	

	@PostMapping("/getBLMLangDetails")
	public Map<String,Map<String, Map<String, String>>> getBLMLangDetails(@RequestBody ScreenRequest request) throws Throwable {
			
		Map<String,Map<String, Map<String, String>>> blmLangDetails = screenService.getBLMLangDetails(request.getButtonsIds(), request.getButtonsInstIds()); 
		 
		return  blmLangDetails;
	}
	
	@PostMapping("/getStoreDB")
	public StoreDB getStoreDB(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getStoreDB(storeDBRequest);
	}
	

	@PostMapping("/getStoreHours")
	public List<StoreHours> getStoreHours(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getStoreHours(storeDBRequest.getMktId(), storeDBRequest.getRestId(), storeDBRequest.getRestInstId());
	}
	

	@PostMapping("/getStoreDetails")
	public StoreDetails getStoreDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getStoreDetails(storeDBRequest);
		
	}
	@PostMapping("/getBunBufferDetails")
	public List<BunBufferDetails> getBunBufferDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getBunBufferDetails(storeDBRequest);
		
	}
	@PostMapping("/getIngredientGroupDetails")
	public List<IngredientGroupDetails> getIngredientGroupDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getIngredientGroupDetails(storeDBRequest);
		
	}

	@PostMapping("/getDayPartSet")
	public List<DayPartSet> getDayPartSet(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getDayPartSet(storeDBRequest);
	}
	
	@PostMapping("/getFeeDetails")
	public List<Fee> getFeeDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getFeeDetails(storeDBRequest);

	}
	
	@PostMapping("/getFlavourSet")
	public List<FlavourSet> getFlavourSet(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getFlavourSet(storeDBRequest);
		
	}
	 

	@PostMapping("/getProductGroupsDetails")
	public List<ProductGroup> getProductGroupsDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getProductGroupsDetails(storeDBRequest);

	}
	 

	@PostMapping("/getDepositDetails")
	public List<Deposit> getDepositDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getDepositDetails(storeDBRequest);

	}
	@PostMapping("/getBusinessLimit")
	public List<BusinessLimits> getBusinessLimit(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getBusinessLimit(storeDBRequest);

	}

	@PostMapping("/getHotBusinessLimit")
	public List<HotBusinessLimit> getHotBusinessLimit(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getHotBusinessLimit(storeDBRequest);

	}
	
	@PostMapping("/getSizeSelection")
	public List<SizeSelection> getSizeSelection(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getSizeSelection(storeDBRequest);
		
	}
	
	@PostMapping("/getPopulateDrinkVol")
	public List<PopulateDrinkVol> getPopulateDrinkVol(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getPopulateDrinkVol(storeDBRequest);
		
	}
	
	@PostMapping("/getPromotionGroupsDetails")
	public List<PromotionGroupDetail> getPromotionGroupsDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getPromotionGroupsDetails(storeDBRequest);

	}
	
	@PostMapping("/getTaxDefinition")
	public List<TaxDefinition> getTaxDefinition(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getTaxDefinition(storeDBRequest);
	}
	
	@PostMapping("/getProductionDetails")
	public Production getproductDetais(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getProductionDetails(storeDBRequest);
	}
	
	@PostMapping("/getPromotionImages")
	public List<PromotionImages> getPromotionImages(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getPromotionImages(storeDBRequest);
	}

	@PostMapping("/getLocalizationSet")
	public List<Localization> getFuncLocalizationSet(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getFuncLocalizationSet(storeDBRequest);
	}
	
	@PostMapping("/getCategoryHours")
	public List<CategoryHours> getCategoryHours(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getCategoryHours(storeDBRequest);
	}

	
	@PostMapping("/getCategoryDetails")
	public List<CategoryDetails> getCategoryDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getCategoryDetails(storeDBRequest);

	}
	@PostMapping("/getTaxTable")
	public TaxTable getTaxTable(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getTaxTable(storeDBRequest);
	}	
	@PostMapping("/getTenderType")
	public TenderTypes getTenderType(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getTenderTypes(storeDBRequest);
	}
	

	@PostMapping("/getDeliverySetDetails")
	public DeliverySetDetails getDeliverySetDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getDeliverySetDetails(storeDBRequest);
	}
	

	@PostMapping("/getPaymentProvidersDetails")
	public List<ProviderDetails> getPaymentProvidersDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getPaymentProvidersDetails(storeDBRequest);

	}
	@PostMapping("/getDiscountTables")
	public List<DiscountTable> getDiscountTables(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getDisplayTable(storeDBRequest);

	}

	@PostMapping("/getConfigurationDetails")
	public Configurations getConfigurationDetails(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getConfigurationDetails(storeDBRequest);

	}

	
	@PostMapping("/getColorDb")
	public List<ColorDb> getColorDb(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getColorDb(storeDBRequest);
	}

	@PostMapping("/getAdaptors")
	public Adaptors getAdaptors(@RequestBody StoreDBRequest storeDBRequest) throws Throwable {
		return storeDBService.getAdaptors(storeDBRequest);
	}
	
	@PostMapping("/getStorePromotionDiscounts")
	public List<StorePromotionDiscounts> getStorePromotionDiscounts(@RequestBody StoreDBRequest storeDBRequest) throws Throwable{
		return storeDBService.getStorePromotionDiscounts(storeDBRequest);
	}

}
