package com.rfm.packagegeneration.cache;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import com.rfm.packagegeneration.dto.BunBufferDetails;
import com.rfm.packagegeneration.dto.IngredientGroupDetails;
import com.rfm.packagegeneration.dto.LanguageDetails;
import com.rfm.packagegeneration.dto.PopulateDrinkVol;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductGroup;
import com.rfm.packagegeneration.dto.PromotionGroupDetail;
import com.rfm.packagegeneration.dto.PromotionImages;
import com.rfm.packagegeneration.dto.SizeSelection;

public interface CacheService {

	Map<Long, Product> getProductData(String key);
	void setProductData(String key, Map<Long, Product> dataMap, Duration ttl);
	List<Map<String, Object>> getColorMediaData(String key);
	void setColorMediaData(String key,List<Map<String, Object>> colorList,Duration ttl );
	String getDefaultLocaleId(String key);
	void setDefaultLocaleId(String key,String MktLclMap,Duration ttl);
	Map<String, String> getAllEvents(String key);
	void setAllEvents(String key,Map<String, String> WorkflowsMap,Duration ttl);
	Map<String, String> getAllWorkflows(String key);
	void setAllWorkflows(String key,Map<String, String> WorkflowsMap,Duration ttl);
	Map<String, String> getAllWorkflowParams(String key);
	void setAllWorkflowParams(String key,Map<String, String> WorkflowParamsMap,Duration ttl);
	Map<String, String> getAllGrillGroupsData(String key);
	void setAllGrillGroupsData(String key,Map<String, String> grillGrpDataMap,Duration ttl);
	Map<String, String> getAllSmtReminderData(String key);
	void setAllSmtReminderData(String key,Map<String, String> smrtRemiDataMap,Duration ttl);
	List<String> getHotScreensData(String key);
	void setHotScreensData(String key,List<String> hotScreenData,Duration ttl);
	List<String> getDynamicWrkflwParamData(String key);
	void setDynamicWrkflwParamData(String key,List<String> dynamicwrkflwParamData,Duration ttl);
	String getDefaultButtonCaptionData(String key);
	void  setDefaultButtonCaptionData(String key,String defaultbtncap,Duration ttl);
	Map<String, Map<String, String>> getMasterScreensInfo(String Key);
	void setMasterScreensInfo(String key,Map<String, Map<String, String>> masterScreensInfo,Duration ttl);
	Map<String, List<Map<String, String>>> getMasterButtonsInfo(String Key);
	void setMasterButtonsInfo(String key,Map<String, List<Map<String, String>>> masterButtonsInfo,Duration ttl);
	List<BunBufferDetails> getBunBufferData(String bunBufferKey);
	void setBunBufferData(String bunBufferKey, List<BunBufferDetails> bunBufferDetails, Duration ofMinutes);
	Map<Long, List<LanguageDetails>> getLanguageData(String lanuguageKey);
	void setLanguageData(String lanuguageKey, Map<Long, List<LanguageDetails>> languageDetails, Duration ofMinutes);
	List<PromotionImages> getPromotionImagesData(String promoImgKey);
	void setPromotionImagesData(String promoImgKey, List<PromotionImages> promoImgDetails, Duration ofMinutes);
	String getAllowExportAddtlRtngFlagData(String allowExportAddtlRtngFlagKey);
	void setAllowExportAddtlRtngFlagData(String allowExportAddtlRtngFlagKey, String allowExportAddtlRtngFlag,
			Duration ofMinutes);
	List<ProductGroup> getMIGroupData(String miGroupDetailsKey);
	void setMIGroupData(String miGroupDetailsKey, List<ProductGroup> miGroupDetails, Duration ofMinutes);
	List<IngredientGroupDetails> getIngredientDetailsData(String ingredientDetailKey);
	void setIngredientDetailsData(String ingredientDetailKey, List<IngredientGroupDetails> ingredientDetails,
			Duration ofMinutes);
	List<SizeSelection> getSizeSelectionData(String sizeSelectionKey);
	void setSizeSelectionData(String sizeSelectionKey, List<SizeSelection> sizeSelectionDetails, Duration ofMinutes);
	List<PromotionGroupDetail> getPromotionGroupDetailsData(String promoGrpDtlKey);
	void setPromotionGroupDetailsData(String promoGrpDtlKey, List<PromotionGroupDetail> promoGrpDetails,
			Duration ofMinutes);
	List<PopulateDrinkVol> getPopulateDrinkVolData(String populateDrinkVolKey);
	void setPopulateDrinkVolData(String populateDrinkVolKey, List<PopulateDrinkVol> populateDrinkVol,
			Duration ofMinutes);
}