package com.rfm.packagegeneration.cache.redis;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

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

public class RedisCacheService implements CacheService {

	private RedisTemplate<String, Object> redisClient;

	public RedisCacheService(RedisTemplate<String, Object> redissonClien) {
		super();
		this.redisClient = redissonClien;
	}

	@Override
	public Map<Long, Product> getProductData(String key) {
		return (Map<Long, Product>) redisClient.boundValueOps(key).get();

	}

	@Override
	public void setProductData(String key, Map<Long, Product> dataMap, Duration ttl) {
		redisClient.boundValueOps(key).set(dataMap, ttl);
	}

	@Override
	public List<Map<String, Object>> getColorMediaData(String key) {
		return (List<Map<String, Object>>) redisClient.boundValueOps(key).get();

	}

	@Override
	public void setColorMediaData(String key, List<Map<String, Object>> colorMediaList, Duration ttl) {
		redisClient.boundValueOps(key).set(colorMediaList, ttl);
	}

	@Override
	public String getDefaultLocaleId(String key) {
		return (String) redisClient.boundValueOps(key).get();
	}

	public Map<String, String> getAllGrillGroupsData(String key) {
		return (Map<String, String>) redisClient.boundValueOps(key).get();
	}

	@Override
	public void setDefaultLocaleId(String key, String MktLclMap, Duration ttl) {
		redisClient.boundValueOps(key).set(MktLclMap, ttl);

	}

	@Override
	public Map<String, String> getAllEvents(String key) {
		return (Map<String, String>) redisClient.boundValueOps(key).get();
	}

	public void setAllGrillGroupsData(String key, Map<String, String> grillGrpMap, Duration ttl) {
		redisClient.boundValueOps(key).set(grillGrpMap, ttl);
	}

	@Override
	public Map<String, String> getAllSmtReminderData(String key) {
		return (Map<String, String>) redisClient.boundValueOps(key).get();
	}

	@Override
	public void setAllEvents(String key, Map<String, String> EventsMap, Duration ttl) {
		redisClient.boundValueOps(key).set(EventsMap, ttl);

	}

	@Override
	public Map<String, String> getAllWorkflows(String key) {
		return (Map<String, String>) redisClient.boundValueOps(key).get();
	}

	public void setAllSmtReminderData(String key, Map<String, String> smrtReminderMap, Duration ttl) {
		redisClient.boundValueOps(key).set(smrtReminderMap, ttl);
	}

	@Override
	public List<String> getHotScreensData(String key) {
		return (List<String>) redisClient.boundValueOps(key).get();
	}

	@Override
	public void setAllWorkflows(String key, Map<String, String> WorkflowsMap, Duration ttl) {
		redisClient.boundValueOps(key).set(WorkflowsMap, ttl);

	}

	@Override
	public Map<String, String> getAllWorkflowParams(String key) {
		return (Map<String, String>) redisClient.boundValueOps(key).get();
	}

	public void setHotScreensData(String key, List<String> hotScreenData, Duration ttl) {
		redisClient.boundValueOps(key).set(hotScreenData, ttl);
	}

	@Override
	public List<String> getDynamicWrkflwParamData(String key) {
		return (List<String>) redisClient.boundValueOps(key).get();
	}

	@Override
	public void setAllWorkflowParams(String key, Map<String, String> WorkflowParamsMap, Duration ttl) {
		redisClient.boundValueOps(key).set(WorkflowParamsMap, ttl);
	}

	public void setDynamicWrkflwParamData(String key, List<String> wrkflwParamData, Duration ttl) {
		redisClient.boundValueOps(key).set(wrkflwParamData, ttl);
	}

	@Override
	public String getDefaultButtonCaptionData(String key) {
		return (String) redisClient.boundValueOps(key).get();
	}

	@Override
	public void setDefaultButtonCaptionData(String key, String defaultbtncap, Duration ttl) {
		redisClient.boundValueOps(key).set(defaultbtncap, ttl);
	}

	@Override
	public Map<String, Map<String, String>> getMasterScreensInfo(String Key) {
		return (Map<String, Map<String, String>>) redisClient.boundValueOps(Key).get();
	}

	@Override
	public void setMasterScreensInfo(String key, Map<String, Map<String, String>> masterScreensInfo, Duration ttl) {
		redisClient.boundValueOps(key).set(masterScreensInfo, ttl);

	}

	@Override
	public Map<String, List<Map<String, String>>> getMasterButtonsInfo(String Key) {
		return (Map<String, List<Map<String, String>>>) redisClient.boundValueOps(Key).get();
	}

	@Override
	public void setMasterButtonsInfo(String key, Map<String, List<Map<String, String>>> masterButtonsInfo,
			Duration ttl) {
		redisClient.boundValueOps(key).set(masterButtonsInfo, ttl);

	}
	
	@Override
	public List<BunBufferDetails> getBunBufferData(String bunBufferKey) {
		return (List<BunBufferDetails>) redisClient.boundValueOps(bunBufferKey).get();
	}

	@Override
	public void setBunBufferData(String bunBufferKey, List<BunBufferDetails> bunBufferDetails, Duration ofMinutes) {
		redisClient.boundValueOps(bunBufferKey).set(bunBufferDetails, ofMinutes);
		
	}

	@Override
	public Map<Long, List<LanguageDetails>> getLanguageData(String lanuguageKey) {
		return (Map<Long, List<LanguageDetails>>) redisClient.boundValueOps(lanuguageKey).get();
	}

	@Override
	public void setLanguageData(String lanuguageKey, Map<Long, List<LanguageDetails>> languageDetails,
			Duration ofMinutes) {
		redisClient.boundValueOps(lanuguageKey).set(languageDetails, ofMinutes);
		
	}

	@Override
	public List<PromotionImages> getPromotionImagesData(String promoImgKey) {
		return (List<PromotionImages>) redisClient.boundValueOps(promoImgKey).get();
	}

	@Override
	public void setPromotionImagesData(String promoImgKey, List<PromotionImages> promoImgDetails, Duration ofMinutes) {
		redisClient.boundValueOps(promoImgKey).set(promoImgDetails, ofMinutes);
		
	}

	@Override
	public String getAllowExportAddtlRtngFlagData(String allowExportAddtlRtngFlagKey) {
		return (String) redisClient.boundValueOps(allowExportAddtlRtngFlagKey).get();
	}

	@Override
	public void setAllowExportAddtlRtngFlagData(String allowExportAddtlRtngFlagKey, String allowExportAddtlRtngFlag,
			Duration ofMinutes) {
		redisClient.boundValueOps(allowExportAddtlRtngFlagKey).set(allowExportAddtlRtngFlag, ofMinutes);
		
	}

	@Override
	public List<ProductGroup> getMIGroupData(String miGroupDetailsKey) {
		return (List<ProductGroup>) redisClient.boundValueOps(miGroupDetailsKey).get();
	}

	@Override
	public void setMIGroupData(String miGroupDetailsKey, List<ProductGroup> miGroupDetails, Duration ofMinutes) {
		redisClient.boundValueOps(miGroupDetailsKey).set(miGroupDetails, ofMinutes);
		
	}

	@Override
	public List<IngredientGroupDetails> getIngredientDetailsData(String ingredientDetailKey) {
		return (List<IngredientGroupDetails>) redisClient.boundValueOps(ingredientDetailKey).get();
	}

	@Override
	public void setIngredientDetailsData(String ingredientDetailKey, List<IngredientGroupDetails> ingredientDetails,
			Duration ofMinutes) {
		redisClient.boundValueOps(ingredientDetailKey).set(ingredientDetails, ofMinutes);
		
	}

	
	@Override
	public List<SizeSelection> getSizeSelectionData(String sizeSelectionKey) {
		return (List<SizeSelection>) redisClient.boundValueOps(sizeSelectionKey).get();
		
	}

	@Override
	public void setSizeSelectionData(String sizeSelectionKey, List<SizeSelection> sizeSelectionDetails,
			Duration ofMinutes) {
		redisClient.boundValueOps(sizeSelectionKey).set(sizeSelectionDetails, ofMinutes);
		
	}

	@Override
	public List<PromotionGroupDetail> getPromotionGroupDetailsData(String promoGrpDtlKey) {
		return (List<PromotionGroupDetail>) redisClient.boundValueOps(promoGrpDtlKey).get();
	}

	@Override
	public void setPromotionGroupDetailsData(String promoGrpDtlKey, List<PromotionGroupDetail> promoGrpDetails,
			Duration ofMinutes) {
		redisClient.boundValueOps(promoGrpDtlKey).set(promoGrpDetails, ofMinutes);
		
	}

	@Override
	public List<PopulateDrinkVol> getPopulateDrinkVolData(String populateDrinkVolKey) {
		return (List<PopulateDrinkVol>) redisClient.boundValueOps(populateDrinkVolKey).get();
	}

	@Override
	public void setPopulateDrinkVolData(String populateDrinkVolKey, List<PopulateDrinkVol> populateDrinkVol,
			Duration ofMinutes) {
		redisClient.boundValueOps(populateDrinkVolKey).set(populateDrinkVol, ofMinutes);
		
	}

}
