package com.rfm.packagegeneration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.hikari.Wizard;
import com.rfm.packagegeneration.logging.annotation.TrackedMethod;
import com.rfm.packagegeneration.utility.DateUtility;

@Repository
public class ProductDBDAO extends CommonDAO{

	private static final Logger LOGGER = LogManager.getLogger("ProductDBDAO");

	@Value("${datasource.used.query}")
	private String dataSourceQuery;

	@TrackedMethod
	public List<Map<String, Object>> getLocalizationSets(final long nodeId, final String effectiveDate )
			throws Exception {
		final String query = getDaoXml("getLocalizationSets", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("nodeId", nodeId);		
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getLocalizationSets");
	}

	/*
	 * This method returns the Display Tax for Breakdown values
	 * 
	 * @param prdID,effectiveDate
	 * 
	 * @return dataForDisplayBRD
	 */
	public Map<String, Object> getDataForDisplayBRD(Long prdID, String effectiveDate) throws Exception {
		Map<String, Object> dataForDisplayBRD = new HashMap<>();
		try {
			final String query = getDaoXml("getDataForDisplayBRD", DAOResources.PRODUCT_DB_DAO);
			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("prdId", prdID);
			paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
			dataForDisplayBRD = Wizard.queryForMap(dataSourceQuery, query, paramMap, "getDataForDisplayBRD");
		} catch (Exception e) {
			dataForDisplayBRD.isEmpty();
		}
		return dataForDisplayBRD;
	}

	/*
	 * This method returns the param value
	 * 
	 * @param mktId, paramName
	 * 
	 * @return paramNameValue
	 */
	public String getValuesFromGlobalParam(Long mktId, String paramName) throws Exception {
		String paramNameValue = "";
			final String query = getDaoXml("getValuesFromGlobalParam", DAOResources.PRODUCT_DB_DAO);
			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("mktId", mktId);
			paramMap.put("paramName", paramName);
			List<Map<String, Object>> mapParamNameValue = Wizard.queryForList(dataSourceQuery, query, paramMap, "getValuesFromGlobalParam");
			
			if (mapParamNameValue != null && mapParamNameValue.size() > 0
					&& mapParamNameValue.get(0).get("PARAM_VALUE") != null) {
					paramNameValue = mapParamNameValue.get(0).get("PARAM_VALUE").toString();
			}else {
				if (paramName.equals("REMOVE_PRICING_DECIMAL")) {
					paramNameValue = "N";
				} else if (paramName.equals("GLOBAL_PARAM_PKG_PRICE_TYPE_GEN")) {
					paramNameValue = "C_TAKEOUT";
				} else if (paramName.equals("MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_EATING")
						|| paramName.equals("MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_TAKEOUT")
						|| paramName.equals("MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_OTHER")) {
					paramNameValue = "2";
				} else if (paramName.equals("EXCLUDE_INACTIVE_MENU_ITEMS")) {
					paramNameValue = "NO";
				} else if (paramName.equals("KVS_MONITOR_REDESIGN_SUPPORT")) {
					paramNameValue = "NO";
				}
			}
	
		return paramNameValue;
	}

	public List<Map<String, Object>> populateDiscountForTrue(final Long mktId, Long restId, Long restInstId) throws Exception {
		final String query = getDaoXml(ProductDBConstant.GETDISCOUNTBREAKT, DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(ProductDBConstant.MKTID, mktId);
		paramMap.put(ProductDBConstant.REST_ID, restId);
		paramMap.put(ProductDBConstant.REST_INST_ID, restInstId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, ProductDBConstant.GETDISCOUNTBREAKT);
	}

	public List<Map<String, Object>> populateDiscountForFalse(final Long mktId, Long restId, Long restInstId) throws Exception {
		final String query = getDaoXml(ProductDBConstant.GETDISCOUNTBREAKF, DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(ProductDBConstant.MKTID, mktId);
		paramMap.put(ProductDBConstant.REST_ID, restId);
		paramMap.put(ProductDBConstant.REST_INST_ID, restInstId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, ProductDBConstant.GETDISCOUNTBREAKF);
	}

	public List<Map<String, Object>> getAllColorsList(final Long mktId) throws Exception {
		final String query = getDaoXml("getAllColorsList", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketid", mktId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllColorsList");
	}

	public List<Map<String, Object>> getAllMediaList(final Long mktId) throws Exception {
		final String query = getDaoXml("getAllMediaList", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketid", mktId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllMediaList");
	}


	/*
	 * This method returns the tax values
	 * 
	 * @param productId,mktId,effectiveDate,setId,subsGrpId
	 * 
	 * @return taxValues
	 */
	public List<Map<String, Object>> getTaxValuesForSetId(Long productId, Long mktId, String effectiveDate, long setId,
			long subsGrpId) throws Exception {
		final String query = getDaoXml("getTaxValuesForSetId", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		paramMap.put("prdId", productId);
		paramMap.put("effDate", DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("setId", setId);
		paramMap.put("subsGrpId", subsGrpId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getTaxValuesForSetId");
	}
	
	public Map<Long, String> getGrillGroupMap() throws Exception {
		Map<Long, String> grillGroupMap = new HashMap<Long, String>();
		final String query = getDaoXml("getgrillGroupName", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		List<Map<String, Object>> mapParamNameValue = Wizard.queryForList(dataSourceQuery, query, paramMap, "getgrillGroupName");
		
		for (Map<String,Object> result : mapParamNameValue) {
			if (result.get("GRLL_GRP_ID") != null && result.get("GRLL_GRP_NA") != null) {
				Long grillGroupId = Long.parseLong(result.get("GRLL_GRP_ID").toString());
				String grillGroupName = result.get("GRLL_GRP_NA").toString();
				grillGroupMap.put(grillGroupId, grillGroupName);
			}
		}
		return grillGroupMap;
	}

	public Long getLimitedTimeDiscountVal(final String date, final Long nodeID) throws Exception {
		Long limitedTimeDiscountVal = 0L;
		final String query = getDaoXml("getLimitedTimeDiscountVal", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("date", DateUtility.convertStringToDate(date));
		paramMap.put("nodeId", nodeID);
		List<Map<String, Object>> mapParamNameValue = Wizard.queryForList(dataSourceQuery, query, paramMap, "getLimitedTimeDiscountVal");
		
		if (mapParamNameValue != null && mapParamNameValue.size() > 0
				&& mapParamNameValue.get(0).get("coalesce") != null) {
			limitedTimeDiscountVal = Long.parseLong(mapParamNameValue.get(0).get("coalesce").toString());
		}
		return limitedTimeDiscountVal;
	}

	public List<Map<String, Object>> getDrinkVolList(Long mktId) throws Exception {
		final String query = getDaoXml("getDrinkVolList", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getDrinkVolList");
	}

	public List<Map<String, Object>> getPricingValueFromRest(Long mktId, String effectiveDate,  Long nodeId) throws Exception {
		final String query = getDaoXml("getPricingValueFromRest", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);		
		paramMap.put("nodeId", nodeId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getPricingValueFromRest");
	}
	
	public String getSmrtRemindrGroupName(Long smrtrmdr) throws Exception {
		String smrtrmdrGroupName = "";
		final String query = getDaoXml("getSmrtRemindrGroupName", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("smrtrmdr", smrtrmdr);
		List<Map<String, Object>> mapParamNameValue = Wizard.queryForList(dataSourceQuery, query, paramMap, "getSmrtRemindrGroupName");
		
		if (mapParamNameValue != null && mapParamNameValue.size() > 0
				&& mapParamNameValue.get(0).get("SRG_NA") != null) {
			smrtrmdrGroupName = mapParamNameValue.get(0).get("SRG_NA").toString();
		}
		return smrtrmdrGroupName;
	}
	
	
	public String getMarketName(Long marketId) throws Exception {
		String marketName= "";
		final String query = getDaoXml("getMarketName", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		List<Map<String, Object>> mapParamNameValue = Wizard.queryForList(dataSourceQuery, query, paramMap);
		
		if (mapParamNameValue != null && mapParamNameValue.size() > 0
				&& mapParamNameValue.get(0).get("MKT_NA") != null) {
			marketName = mapParamNameValue.get(0).get("MKT_NA").toString();
		}
		return marketName;
	}
	
	
}
