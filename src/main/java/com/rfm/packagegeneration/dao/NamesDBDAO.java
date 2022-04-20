package com.rfm.packagegeneration.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductDetails;
import com.rfm.packagegeneration.dto.ProductGeneralSettingMenuItemNames;
import com.rfm.packagegeneration.hikari.Wizard;
import com.rfm.packagegeneration.logging.annotation.TrackedMethod;
import com.rfm.packagegeneration.utility.DateUtility;
import com.rfm.packagegeneration.utility.ObjectUtils;
@Repository

public class NamesDBDAO extends CommonDAO{
	private static final Logger LOGGER = LogManager.getLogger("NamesGeneratorDAO");
	
	@Value("${datasource.used.query}")
	private String dataSourceQuery;
	
	@TrackedMethod
	public List<Map<String, Object>> getLocalizationSets(final Long restaurantId, final Long restaurantInstanceId) throws Exception{
		final String query = getDaoXml("getLocalizationSets", DAOResources.NAMES_DB_GENERATOR_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("restId", restaurantId);
		paramMap.put("restInstId", restaurantInstanceId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getLocalizationSets");
	}
	
	/**
	 *
	 * @param paramName
	 * @param mkt_id
	 * @return
	 * @throws Exception
	 */
	@TrackedMethod
	public String getParamValue(final String paramName, final Long mkt_id) throws Exception{
		final String query = getDaoXml("getGlobalParamValue", DAOResources.NAMES_DB_GENERATOR_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("paramName",paramName);
		paramMap.put("marketId", mkt_id);
		String paramValue = null;
		
		List<Map<String, Object>> paramValueMap = Wizard.queryForList(dataSourceQuery, query, paramMap, "getGlobalParamValue");
		
		for (Map<String, Object> map : paramValueMap) {
			if (map.get("PARAM_VALUE") != null)
				paramValue = map.get("PARAM_VALUE").toString();
			break;
		}
		return paramValue;
	}
	@TrackedMethod
	public String getCountryCode(final Long parentSetId, final Long childSetId,final String effectiveDate) throws Exception {
		final String query = getDaoXml("getCountryCode", DAOResources.NAMES_DB_GENERATOR_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("childSetId", childSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		String countryCode = null;
		List<Map<String, Object>> countryCodeMap = Wizard.queryForList(dataSourceQuery, query, paramMap, "getCountryCode");
		
		for (Map<String, Object> map : countryCodeMap) {
			if (map.get("COUNTRY_ID") != null)
				countryCode = map.get("COUNTRY_ID").toString();
			break;
		}
		return countryCode;
	}
	@TrackedMethod
	public int getLanguageCount(final Long setId) throws Exception {
		final String query = getDaoXml("getLanguageCount", DAOResources.NAMES_DB_GENERATOR_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		int languageCount = 0;
		paramMap.put("setId", setId);
		List<Map<String, Object>> languageMap = Wizard.queryForList(dataSourceQuery, query, paramMap, "getLanguageCount");
		for (Map<String, Object> map : languageMap) {
			if (map.get("count") != null)
				languageCount = Integer.parseInt(map.get("count").toString());
			break;
		}
		return languageCount;
	}
	@TrackedMethod
	public Map<String, String> getAllSmartReminders(Long marketId, String effectiveDate) throws Exception {
		final String query = getDaoXml("getAllSmartReminderGroups", DAOResources.NAMES_DB_GENERATOR_DAO);
		Map<String,String> result = new HashMap<>();
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		List<Map<String, Object>> allSmartRemindersMap = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllSmartReminderGroups");
		for (Map<String, Object> map : allSmartRemindersMap) {
			String key = map.get("SRG_ID")+";"+map.get("lang_id")+";"+map.get("DEVC_ID");
			StringBuffer sb = new StringBuffer();
			if(map.get("QSTN") != null) {
				sb.append(getTag("SR_Question",map.get("QSTN").toString()));
			}
			
			if(map.get("QNA") != null) {
				sb.append(getTag("SR_Name",map.get("QNA").toString()));
			}
			
			result.put(key,sb.toString());
		}
		return result;
	}
	@TrackedMethod
	public Long getDeviceLex(final Long marketId) throws Exception {
		Long lexId = 25439L;
		final String query = getDaoXml("getDeviceLex", DAOResources.NAMES_DB_GENERATOR_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		List<Map<String, Object>> deviceLexMap = Wizard.queryForList(dataSourceQuery, query, paramMap, "getDeviceLex");
		
		for (Map<String, Object> map : deviceLexMap) {
			if (map.get("LEX_ID") != null)
				lexId = Long.parseLong(map.get("LEX_ID").toString());
		}
		return lexId;
	}
	@TrackedMethod
	public List<Map<String,String>> getAllLocales(final Long setId,final Long lexId, final Long marketId,final String effectiveDate) throws Exception{
		final String query = getDaoXml("getAllLocales", DAOResources.NAMES_DB_GENERATOR_DAO);
		final List<Map<String,String>> allLocales = new ArrayList<>();
		Map<String,String> localeInfo;
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("lexId", lexId);
		paramMap.put("marketId", marketId);
		paramMap.put("lexId", lexId);
		paramMap.put("marketId", marketId);
		paramMap.put("marketId", marketId);
		paramMap.put("setId", setId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> allLocalesMap = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllLocales");
		
		for (Map<String, Object> map : allLocalesMap) {
			localeInfo = new HashMap<>();
			if (map.get("LANG_ID") != null)
				localeInfo.put("LANG_ID",map.get("LANG_ID").toString());
			if (map.get("DVCE_ID") != null)
				localeInfo.put("DVCE_ID",map.get("DVCE_ID").toString());
			if (map.get("LANG_CD") != null)
				localeInfo.put("LANG_CD",map.get("LANG_CD").toString());
			if (map.get("DVCE_NA") != null)
				localeInfo.put("DVCE_NA",map.get("DVCE_NA").toString());
			if (map.get("LCLE_NA") != null)
				localeInfo.put("LCLE_NA",map.get("LCLE_NA").toString());
			allLocales.add(localeInfo);
		}
		return allLocales;
	}
	
	public Map<String,Map<String,String>> populateNamesMap(final PackageGeneratorDTO packageGeneratorDTO,
			final Map<String,String>localeInfo, Map<Long, Product> namesLayeringObject) throws Exception {
		final Map<String,Map<String,String>> allProducts = new HashMap<>();
		Map<Long, ProductDetails> allProductsDetails=packageGeneratorDTO.getAllProducts();
		LOGGER.info("Object size:" + allProductsDetails.size());
		Map<String,String> productNames;
		String key;
		for(Long productKey : allProductsDetails.keySet() ) {
			productNames = new HashMap<>();
			ProductDetails productdtls=allProductsDetails.get(productKey);
			productNames = new HashMap<>();
			Product namesProduct = namesLayeringObject.get(productKey);
			if (namesProduct != null) {
				List<ProductGeneralSettingMenuItemNames> generalSetting = namesProduct.getProductGeneralSettingNamesList();
				for(ProductGeneralSettingMenuItemNames names : generalSetting) {
					Long langId = Long.valueOf(names.getLangId());
					if (langId.equals(Long.parseLong(localeInfo.get("LANG_ID")))
							&& localeInfo.get("DVCE_ID").equals(names.getDeviceId())){
						productNames.put("SHRT_NA",names.getShortName());
						productNames.put("LNG_NA",names.getLongName());
						productNames.put("DRV_NA",names.getDtName());
						productNames.put("SUMR_MNIT_NA",names.getSummaryMonitorName());
						productNames.put("SHRT_MNIT_NA",names.getShortMonitorName());
						productNames.put("ALT_NA",names.getAlternativeName());
						productNames.put("CSO_NA",names.getCsoName());
						productNames.put("CSO_SIZE_NA",names.getCsoSizeName());
						productNames.put("CSO_GEN_NA",names.getCsoGenericName());
						if("99999".equals(localeInfo.get("DVCE_ID"))){
							productNames.put("PRMO_TX_LABL",names.getPromoTaxLabel());
						}
						productNames.put("COD_NA",names.getCodName());
						if (productdtls.getProductCode() != null) {
							productNames.put("PRD_CD",productdtls.getProductCode().toString());
						}
						productNames.put("HOME_DELIVERY_NA",names.getHomeDeliveryname());
						productNames.put("SMRT_RMDR",names.getSmartReminder());
						key = productKey +";"+localeInfo.get("LANG_ID")+";"+localeInfo.get("DVCE_ID");
						allProducts.put(key,productNames);
					}

				}
			}

		}
		return allProducts;
	}
	
	
	@TrackedMethod
	public List<Long> retrieveMenuItemSetsId(final Long pNodeId, final Long pMktId, final String effectiveDate) throws Exception{
		final String query = getDaoXml("retrieveMenuItemSetsId", DAOResources.COMMON_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("nodeId", pNodeId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("marketId", pMktId);
		
		List<Map<String, Object>> setsIdsAssignedToRest = Wizard.queryForList(dataSourceQuery, query, paramMap, "retrieveMenuItemSetsId");
		List<Long> listIds = new ArrayList<>();
		for (Map<String, Object> setMap : setsIdsAssignedToRest) {
			if (setMap.get("CHLD_SET_ID") != null)
				listIds.add(Long.parseLong(setMap.get("CHLD_SET_ID").toString()));
		}
		return listIds;
	}
	@TrackedMethod
	public List<Long> retrieveRestSetId(final Long pNodeId, final Long pMktId) throws Exception{
		final String query = getDaoXml("retrieveRestSetId", DAOResources.COMMON_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("nodeId", pNodeId);
		paramMap.put("marketId", pMktId);
		
		List<Map<String, Object>> restSetsIds = Wizard.queryForList(dataSourceQuery, query, paramMap, "retrieveRestSetId");
		List<Long> listIds = new ArrayList<>();
		for (Map<String, Object> setMap : restSetsIds) {
			if (setMap.get("SET_ID") != null)
				listIds.add(Long.parseLong(setMap.get("SET_ID").toString()));
		}
		return listIds;
	}
	@TrackedMethod
	public Long retrieveMasterSetId(final Long pMktId) throws Exception{
		final String query = getDaoXml("retrieveMasterSetId", DAOResources.COMMON_DAO);
		Long masterSetId = 0L;
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", pMktId);
		
		List<Map<String, Object>> masterSetIdMap = Wizard.queryForList(dataSourceQuery, query, paramMap, "retrieveMasterSetId");
		for (Map<String, Object> map : masterSetIdMap) {
			if (map.get("SET_ID") != null)
				masterSetId = Long.parseLong(map.get("SET_ID").toString());
		}
		return masterSetId;
	}
	@TrackedMethod
	public Map<String,String> getCustomizedSmartReminders(final Long localeId, final Long deviceId, final Long marketId, final Long parentSetId,final Long childSetId) throws Exception{
		final String query = getDaoXml("getCustomizedSmartReminders", DAOResources.NAMES_DB_GENERATOR_DAO);
		final Map<String,String> smartReminders = new HashMap<>();
		Map<String,String> localeInfo;
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("childSetId", childSetId);
		paramMap.put("marketId", marketId);
		paramMap.put("deviceId", deviceId);
		paramMap.put("localeId", localeId);
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("childSetId", childSetId);
		List<Map<String, Object>> allSmartRemindersMap = Wizard.queryForList(dataSourceQuery, query, paramMap, "getCustomizedSmartReminders");
		
		StringBuffer sb;
		
		for (Map<String, Object> map : allSmartRemindersMap) {
			localeInfo = new HashMap<>();
			if (map.get("LANG_ID") != null)
				localeInfo.put("LANG_ID",map.get("LANG_ID").toString());
			sb =  new StringBuffer();
			if(!"".equals(map.get("QSTN"))){
				sb.append("<SR_Question>" + ObjectUtils.replaceSpecialCharacters(map.get("QSTN").toString()) + "</SR_Question>");
			}
			if(!"".equals(map.get("QNA"))){
				sb.append("<SR_Name>" + ObjectUtils.replaceSpecialCharacters(map.get("QNA").toString()) + "</SR_Name>");
			}
			smartReminders.put(map.get("SRG_ID").toString(),sb.toString());
		}
		return smartReminders;
	}
	private String getTag(final String tagName, final String value){
		final StringBuffer tag = new StringBuffer("");
		if(ObjectUtils.isFilled(value)){
			tag.append("<"+tagName+">");
			tag.append(ObjectUtils.replaceSpecialCharacters(value));
			tag.append("</"+tagName+">");
		}
		return tag.toString();
	}
	
	@TrackedMethod
	public void closeDataBaseObject(Connection con, PreparedStatement statement, ResultSet resultSet) throws SQLException{
		if (null != resultSet) {
			resultSet.close();
			resultSet = null;
		}
		if (null != statement) {
			statement.close();
			statement = null;
		}
		if (null != con) {
			con.close();
			con = null;
		}
	}
	
}
