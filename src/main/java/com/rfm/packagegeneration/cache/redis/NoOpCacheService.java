package com.rfm.packagegeneration.cache.redis;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.rfm.packagegeneration.cache.CacheService;
import com.rfm.packagegeneration.dto.BunBufferDetails;
import com.rfm.packagegeneration.dto.IngredientGroupDetails;
import com.rfm.packagegeneration.dto.LanguageDetails;
import com.rfm.packagegeneration.dto.PopulateDrinkVol;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductGroup;
import com.rfm.packagegeneration.dto.PromotionGroupDetail;
import com.rfm.packagegeneration.dto.PromotionImages;
import com.rfm.packagegeneration.dto.SizeSelection;

@Service
@ConditionalOnProperty(name = "application.redis.enabled", havingValue = "false")
public class NoOpCacheService implements CacheService {

	@Override
	public Map<Long, Product> getProductData(String key) {
		// Auto-generated method stub
		return Collections.emptyMap();
	}

	@Override
	public void setProductData(String key, Map<Long, Product> dataMap, Duration ttl) {
		// Auto-generated method stub

	}
	
	@Override
	public List<Map<String, Object>> getColorMediaData(String key) {
		

		return Collections.emptyList();
	}
	
	@Override
	public void setColorMediaData(String key, List<Map<String, Object>> colorList, Duration ttl) {
		// Auto-generated method stub

 }

	@Override
	public String getDefaultLocaleId(String key) {
	

		return null;
	}

	@Override
	public void setDefaultLocaleId(String key, String mktLclMap, Duration ttl) {
		// Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getAllEvents(String key) {
		

		return Collections.emptyMap();
	}

	@Override
	public void setAllEvents(String key, Map<String, String> workflowsMap, Duration ttl) {
		// Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getAllWorkflows(String key) {
		
		return Collections.emptyMap();
	}

	@Override
	public void setAllWorkflows(String key, Map<String, String> workflowsMap, Duration ttl) {
		// Auto-generated method stub

	}

	@Override
	public Map<String, String> getAllWorkflowParams(String key) {

		return Collections.emptyMap();
	}

	@Override
	public void setAllWorkflowParams(String key, Map<String, String> workflowParamsMap, Duration ttl) {
		// Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getAllGrillGroupsData(String key) {

		return Collections.emptyMap();
	}

	@Override
	public void setAllGrillGroupsData(String key, Map<String, String> grillGrpDataMap, Duration ttl) {
		// Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getAllSmtReminderData(String key) {

		return Collections.emptyMap();
	}

	@Override
	public void setAllSmtReminderData(String key, Map<String, String> smrtRemiDataMap, Duration ttl) {
		// Auto-generated method stub
		
	}

	@Override
	public List<String> getHotScreensData(String key) {

		return Collections.emptyList();
	}

	@Override
	public void setHotScreensData(String key, List<String> hotScreenData, Duration ttl) {
		// Auto-generated method stub

		
	}

	@Override
	public List<String> getDynamicWrkflwParamData(String key) {

		return Collections.emptyList();
	}

	@Override
	public void setDynamicWrkflwParamData(String key, List<String> dynamicwrkflwParamData, Duration ttl) {
		// Auto-generated method stub

		
	}

	@Override
	public String getDefaultButtonCaptionData(String key) {

		return null;
	}

	@Override
	public void setDefaultButtonCaptionData(String key, String defaultbtncap, Duration ttl) {
		// Auto-generated method stub

		
	}
	@Override
	public Map<String, Map<String, String>> getMasterScreensInfo(String key) {
	
		return Collections.emptyMap();
	}
	
	@Override
	public void setMasterScreensInfo(String key, Map<String, Map<String, String>> masterScreensInfo, Duration ttl) {
		// Auto-generated method stub

		
	}
	
	@Override
	public Map<String, List<Map<String, String>>> getMasterButtonsInfo(String key) {

		return Collections.emptyMap();
	}
	
	@Override
	public void setMasterButtonsInfo(String key, Map<String, List<Map<String, String>>> masterButtonsInfo,
			Duration ttl) {
		// Auto-generated method stub

		
	}

	@Override
	public List<BunBufferDetails> getBunBufferData(String bunBufferKey) {

		return Collections.emptyList();
	}

	@Override
	public void setBunBufferData(String bunBufferKey, List<BunBufferDetails> bunBufferDetails, Duration ofMinutes) {
		// Auto-generated method stub

		
	}

	@Override
	public Map<Long, List<LanguageDetails>> getLanguageData(String lanuguageKey) {

		return Collections.emptyMap();
	}

	@Override
	public void setLanguageData(String lanuguageKey, Map<Long, List<LanguageDetails>> languageDetails,
			Duration ofMinutes) {
		// Auto-generated method stub

		
	}

	@Override
	public List<PromotionImages> getPromotionImagesData(String promoImgKey) {

		return Collections.emptyList();
	}

	@Override
	public void setPromotionImagesData(String promoImgKey, List<PromotionImages> promoImgDetails, Duration ofMinutes) {
		// Auto-generated method stub

		
	}

	@Override
	public String getAllowExportAddtlRtngFlagData(String allowExportAddtlRtngFlagKey) {

		return null;
	}

	@Override
	public void setAllowExportAddtlRtngFlagData(String allowExportAddtlRtngFlagKey, String allowExportAddtlRtngFlag,
			Duration ofMinutes) {
		// Auto-generated method stub

		
	}

	@Override
	public List<ProductGroup> getMIGroupData(String miGroupDetailsKey) {

		return Collections.emptyList();
	}

	@Override
	public void setMIGroupData(String miGroupDetailsKey, List<ProductGroup> miGroupDetails, Duration ofMinutes) {
		// Auto-generated method stub

		
	}

	@Override
	public List<IngredientGroupDetails> getIngredientDetailsData(String ingredientDetailKey) {

		return Collections.emptyList();
	}

	@Override
	public void setIngredientDetailsData(String ingredientDetailKey, List<IngredientGroupDetails> ingredientDetails,
			Duration ofMinutes) {
		// Auto-generated method stub

		
	}

	@Override
	public List<SizeSelection> getSizeSelectionData(String sizeSelectionKey) {

		return Collections.emptyList();
	}

	@Override
	public void setSizeSelectionData(String sizeSelectionKey, List<SizeSelection> sizeSelectionDetails,
			Duration ofMinutes) {
		// Auto-generated method stub

		
	}

	@Override
	public List<PromotionGroupDetail> getPromotionGroupDetailsData(String promoGrpDtlKey) {

		return Collections.emptyList();
	}

	@Override
	public void setPromotionGroupDetailsData(String promoGrpDtlKey, List<PromotionGroupDetail> promoGrpDetails,
			Duration ofMinutes) {
		// Auto-generated method stub

		
	}

	@Override
	public List<PopulateDrinkVol> getPopulateDrinkVolData(String populateDrinkVolKey) {

		return Collections.emptyList();
	}

	@Override
	public void setPopulateDrinkVolData(String populateDrinkVolKey, List<PopulateDrinkVol> populateDrinkVol,
			Duration ofMinutes) {
		// Auto-generated method stub

		
	}
	
}
