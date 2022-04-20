package com.rfm.packagegeneration.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.ProductDetails;
import com.rfm.packagegeneration.dto.RequestDTO;
import com.rfm.packagegeneration.dto.ScreenDetails;
import com.rfm.packagegeneration.hikari.Wizard;
import com.rfm.packagegeneration.utility.DateUtility;
import com.rfm.packagegeneration.utility.ObjectUtils;

@Repository
public class ScreenDAO extends CommonDAO{

	private static final Logger LOGGER = LogManager.getLogger("ScreenDBDAO");
	@Autowired
	LayeringProductDBDAO layeringDBDAO;
	@Value("${datasource.used.query}")
	private String dataSourceQuery;

	  public String stringOrEmpty(Object value) {
		return value != null? value.toString() : "";

		}
	/**
	* @param screenId
	* @param screenSetName
	* @param restaurantNodeId
	* @param effectiveDate
	* @return
	 * @throws Exception 
	* @throws GeneratorDBException
	*/
	public Map<String,Map<String, Map<String, String>>> getBLMButtonDetails(final ArrayList<Long> screenIds, final String screenSetName,
			final String restaurantNodeId, final String effectiveDate) throws Exception{
	
		Map<String,Map<String,Map<String,String>>> result = new HashMap<String,Map<String,Map<String,String>>>();
		Map<String,String> buttonDetail = null;
		
		String ids="0";
		for(Long id : screenIds)  ids = id.toString() + "," + ids;	
		
		String query = getDaoXml("getBLMButtonDetails", DAOResources.SCREEN_DAO);
		query = query.replace(":screenIds",ids);
		
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("nodeId",  Long.parseLong(restaurantNodeId));
		paramMap.put("effectiveDate",  DateUtility.convertStringToDate(effectiveDate));			
		paramMap.put("screenName", screenSetName);
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getBLMButtonDetails");
		
		for ( final Map<String, Object> data : listOfResults ) {
			buttonDetail = new HashMap<String,String>();

			buttonDetail.put("BTTN_NU",data.get("BTTN_NU").toString());
			if(data.get("CAT") != null) buttonDetail.put("CAT",data.get("CAT").toString());
			if(data.get("FRGND_CPTN_COLR_ID") != null) buttonDetail.put("FRGND_CPTN_COLR_ID",data.get("FRGND_CPTN_COLR_ID").toString());
			if(data.get("FRGND_CPTN_COLR_PRSD_ID") != null) buttonDetail.put("FRGND_CPTN_COLR_PRSD_ID",data.get("FRGND_CPTN_COLR_PRSD_ID").toString());
			if(data.get("BKGD_CPTN_COLR_ID") != null) buttonDetail.put("BKGD_CPTN_COLR_ID",data.get("BKGD_CPTN_COLR_ID").toString());
			if(data.get("BKGD_CPTN_COLR_PRSD_ID") != null) buttonDetail.put("BKGD_CPTN_COLR_PRSD_ID",data.get("BKGD_CPTN_COLR_PRSD_ID").toString());
			if(data.get("KEY_SCAN") != null) buttonDetail.put("KEY_SCAN",data.get("KEY_SCAN").toString());
			if(data.get("KEY_SHFT") != null) buttonDetail.put("KEY_SHFT",data.get("KEY_SHFT").toString());
			if(data.get("WDTH") != null) buttonDetail.put("WDTH",data.get("WDTH").toString());
			if(data.get("HGHT") != null) buttonDetail.put("HGHT",data.get("HGHT").toString());
			if(data.get("BTTN_ID") != null) buttonDetail.put("BTTN_ID",data.get("BTTN_ID").toString());
			if(data.get("PRD_CD") != null) buttonDetail.put("PRD_CD",data.get("PRD_CD").toString());
			if(data.get("sound_file_id") != null) buttonDetail.put("sound_file_id",data.get("sound_file_id").toString());
			if(data.get("data_inst_id") != null) buttonDetail.put("SCR_INST_ID",data.get("data_inst_id").toString());
			if(data.get("OUTAGE_MODE") != null) buttonDetail.put("OUTAGE_MODE",data.get("OUTAGE_MODE").toString());

			String key = data.get("id").toString();
			Map<String,Map<String,String>> buttonDetails = result.get(key);
			if (buttonDetails == null) {
				buttonDetails = new HashMap<String, Map<String,String>>();
				result.put(key, buttonDetails);
			}
			buttonDetails.put(stringOrEmpty(data.get("BTTN_ID")),buttonDetail);
		}
		return result;
	}
	
	
	public Map<String,List<Map<String, String>>> getButtonWorkflowParameters(final List<Long> buttonWorkflowAssignIds) throws Exception {
		Map<String,List<Map<String,String>>> result = new HashMap<String,List<Map<String,String>>>();
		if (buttonWorkflowAssignIds == null || buttonWorkflowAssignIds.isEmpty()) {
			return result;
		}
		final String query = getDaoXml("getButtonWorkflowParameters", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("buttonWorkflowAssignIds", buttonWorkflowAssignIds);
		
		List<Map<String, Object>> listOfResults =Wizard.queryForList(dataSourceQuery, query, paramMap, "getButtonWorkflowParameters");
		LOGGER.info("ScreenServiceDAO :: getButtonWorkflowParameters()");
			for(Map<String, Object> data: listOfResults) {
				Map<String,String> workflowParameter = new HashMap<String,String>();
				workflowParameter.put("PARM_ID", stringOrEmpty(data.get("PARM_ID")));
				workflowParameter.put("PARM_TYP", stringOrEmpty(data.get("PARM_TYP")));
				workflowParameter.put("VAL", stringOrEmpty(data.get("VAL")));
				String key =  stringOrEmpty(data.get("id"));
				/*List<Map<String, String>> workflowParameters = result.get(key);
				if (workflowParameters == null) {
					workflowParameters = new ArrayList<Map<String, String>>();
					result.put(key, workflowParameters);
				}*/
				List<Map<String, String>> workflowParameters = result.computeIfAbsent(key, k -> new ArrayList<Map<String, String>>());
				workflowParameters.add(workflowParameter);
			}
		return result;
	}
	public 	Map<String,Map<String,Map<String,String>>> getMasterButtonDetails(final List<Long> screenIds, final List<Long> screenInstanceIds) throws Exception{
		Map<String,Map<String,Map<String,String>>> result = new HashMap<String,Map<String,Map<String,String>>>();
		final String query = getDaoXml("getMasterButtonDetails", DAOResources.SCREEN_DAO);
		List<Object[]> valuesMap = new ArrayList<>();
		for(int i =0 ; i < screenIds.size(); i++) {
			Object[] entry = {screenIds.get(i), screenInstanceIds.get(i)};
			valuesMap.add(entry);
		}
		
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("valuesMap", valuesMap);
		
		List<Map<String, Object>> listOfResults =Wizard.queryForList(dataSourceQuery, query, paramMap, "getMasterButtonDetails");
		LOGGER.info("ScreenServiceDAO :: getAssignedSets()");
		
		for (Map<String, Object> data : listOfResults) {
			
				Map<String,String> buttonDetail = new HashMap<String,String>();
				buttonDetail.put("BTTN_NU",stringOrEmpty( data.get("BTTN_NU")));

				buttonDetail.put("CAT", stringOrEmpty( data.get("CAT")));

				buttonDetail.put("FRGND_CPTN_COLR_ID", stringOrEmpty( data.get("FRGND_CPTN_COLR_ID")));

				buttonDetail.put("FRGND_CPTN_COLR_PRSD_ID",stringOrEmpty(data.get("FRGND_CPTN_COLR_PRSD_ID")));

				buttonDetail.put("BKGD_CPTN_COLR_ID",stringOrEmpty(data.get("BKGD_CPTN_COLR_ID")));

				buttonDetail.put("BKGD_CPTN_COLR_PRSD_ID", stringOrEmpty(data.get("BKGD_CPTN_COLR_PRSD_ID")));
				
				buttonDetail.put("KEY_SCAN", stringOrEmpty(data.get("KEY_SCAN")));
			
				buttonDetail.put("KEY_SHFT",stringOrEmpty(data.get("KEY_SHFT")));
			
				buttonDetail.put("WDTH", stringOrEmpty(data.get("WDTH")));
		
				buttonDetail.put("HGHT", stringOrEmpty(data.get("HGHT")));
				buttonDetail.put("BTTN_ID", stringOrEmpty(data.get("BTTN_ID")));

				buttonDetail.put("PRD_CD", stringOrEmpty(data.get("PRD_CD")));

				buttonDetail.put("sound_file_id", stringOrEmpty(data.get("sound_file_id")));
			
				buttonDetail.put("IS_BLM", stringOrEmpty(data.get("IS_BLM")));
				buttonDetail.put("IS_DYNAMIC",stringOrEmpty(data.get("IS_DYNAMIC")));
			
				buttonDetail.put("OUTAGE_MODE", stringOrEmpty(data.get("OUTAGE_MODE")));

			String key = stringOrEmpty(data.get("id")) + "," + stringOrEmpty(data.get("instId"));
			/*Map<String, Map<String, String>> buttonsDetail = result.get(key);
			if (buttonsDetail == null) {
				buttonsDetail = new LinkedHashMap<String, Map<String, String>>();
				result.put(key, buttonsDetail);
			}*/
			Map<String, Map<String, String>> buttonsDetail = result.computeIfAbsent(key, k -> new LinkedHashMap<String, Map<String, String>>());
			buttonsDetail.put(stringOrEmpty(data.get("BTTN_ID")), buttonDetail);
		}
		return result;
	}

	public Map<String,Map<String,Map<String,String>>> getButtonLangDetails(List<Long> screenIds, List<Long> screenInstanceIds, List<Long> langIds, String restaurantDefLang) throws Exception{
		Map<String,Map<String,Map<String,String>>> buttonDetails = new HashMap<String,Map<String,Map<String,String>>>();

		final String query = getDaoXml("getButtonLangDetails", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		List<Object[]> valuesMap = new ArrayList<>();
		for(int i =0 ; i < screenIds.size(); i++) {
			Object[] entry = {screenIds.get(i), screenInstanceIds.get(i)};
			valuesMap.add(entry);
		}
		paramMap.put("valuesMap", valuesMap);
		paramMap.put("defaultLang", Long.parseLong(restaurantDefLang));
		paramMap.put("langIds",langIds);
		
		List<Map<String, Object>> listOfResults =Wizard.queryForList(dataSourceQuery, query, paramMap, "getButtonLangDetails");
		LOGGER.info("ScreenDAO :: getButtonLangDetails()");
		
		for (Map<String, Object> data : listOfResults) {
				Map<String,String> buttonDetail = new HashMap<String,String>();
				buttonDetail.put("CPTN", stringOrEmpty(data.get("CPTN")));
				buttonDetail.put("BMP", stringOrEmpty(data.get("BMP")));
				buttonDetail.put("BMP_PRSD", stringOrEmpty(data.get("BMP_PRSD")));
			String key = stringOrEmpty(data.get("id")) + "," + stringOrEmpty(data.get("instId"));
			String key1 = stringOrEmpty(data.get("BTTN_ID")) + "," + stringOrEmpty(data.get("LANG_ID"));
			/*Map<String, Map<String, String>> buttonsDetail = buttonDetails.get(key);
			if (buttonsDetail == null) {
				buttonsDetail = new HashMap<String, Map<String,String>>();
				buttonDetails.put(key, buttonsDetail);
			}*/
			Map<String, Map<String, String>> buttonsDetail = buttonDetails.computeIfAbsent(key, k -> new LinkedHashMap<String, Map<String, String>>());
			buttonsDetail.put(key1,buttonDetail);
		}
		return buttonDetails;
	}

public List<Map<String,String>> getAllLocales(final Long marketId,final String marketLocaleId,final String effectiveDate) throws Exception{
		
		final List<Map<String,String>> allLocales = new ArrayList<Map<String,String>>();
		Map<String,String> localeInfo;
		final String query = getDaoXml("getAllScreenLocales", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketLocaleId",  marketLocaleId);
		paramMap.put("marketId", marketId);
		paramMap.put("effectiveDate",  DateUtility.convertStringToDate(effectiveDate));
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllScreenLocales");
		
		LOGGER.info("ScreenServiceDAO :: getAllLocales()");
		for ( final Map<String, Object> data : listOfResults ) {
			localeInfo = new HashMap<String,String>();
			localeInfo.put("LANG_ID",stringOrEmpty(data.get("LANG_ID")));			
			localeInfo.put("LANG_CD",stringOrEmpty(data.get("LANG_CD")));			
			localeInfo.put("LCLE_NA",stringOrEmpty(data.get("LCLE_NA")));
			allLocales.add(localeInfo);
		}
		return allLocales;
	}
	
	
	
	public Map<String, List<Map<String, String>>> getScreenWorkflows(final List<Long> screenIds, final List<Long> screenInstanceIds) throws Exception{		
		Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String, String>>>();
		Map<String,String> workflowDetail = null;
			final String query = getDaoXml("getScreenWorkflows", DAOResources.SCREEN_DAO);
			final Map<String, Object> paramMap = new HashMap<>();
			List<Object[]> valuesMap = new ArrayList<>();
			for(int i =0 ; i < screenIds.size(); i++) {
				Object[] entry = {screenIds.get(i), screenInstanceIds.get(i)};
				valuesMap.add(entry);
			}
			paramMap.put("valuesMap", valuesMap);
			List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getScreenWorkflows");
			for ( final Map<String, Object> data : listOfResults ) {
				workflowDetail = new HashMap<String,String>();
				workflowDetail.put("SCR_WRKFL_ASGN_ID",data.get("SCR_WRKFL_ASGN_ID").toString());
				workflowDetail.put("WRKFL_ID",data.get("WRKFL_ID").toString());
				workflowDetail.put("EVNT_ID",data.get("EVNT_ID").toString());
				String key = data.get("SCR_ID").toString() + "," + data.get("SCR_INST_ID").toString();
				List<Map<String,String>> workflowDetails = result.get(key);
				if (workflowDetails == null) {
					workflowDetails = new ArrayList<Map<String,String>>();
					result.put(key, workflowDetails);
				}
				workflowDetails.add(workflowDetail);
			}
		return result;
	}
	
	
	public Map<String,List<Map<String, String>>> getScreenWorkflowParameters(List<Long> screenWorkflowAssignId) throws Exception {
		Map<String,List<Map<String, String>>> result = new  HashMap<String,List<Map<String, String>>>();
		Map<String,String> workflowParameter = null;
		if(screenWorkflowAssignId==null||screenWorkflowAssignId.isEmpty())
			return result;
			final String query = getDaoXml("getScreenWorkflowParameters", DAOResources.SCREEN_DAO);
			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("screenWrkflIds",  screenWorkflowAssignId);
			List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getScreenWorkflowParameters");
			for ( final Map<String, Object> data : listOfResults ) {
				workflowParameter = new HashMap<String,String>();
				if(data.get("PARM_ID")!=null) workflowParameter.put("PARM_ID",data.get("PARM_ID").toString());
				if(data.get("PARM_TYP")!=null) workflowParameter.put("PARM_TYP",data.get("PARM_TYP").toString());
				if(data.get("VAL")!=null) workflowParameter.put("VAL",data.get("VAL").toString());
				String key = data.get("scr_wrkfl_asgn_id").toString() + "";
				List<Map<String,String>> workflowParameters = result.get(key);
				if (workflowParameters == null) {
					workflowParameters = new ArrayList<Map<String,String>>();
					result.put(key, workflowParameters);
				}
				workflowParameters.add(workflowParameter);
			}
		return result;
	}
	
	
	public Map<String, String> getScreenLookupParameter(List<Long> liskWorkflowParams, String marketlocaleId) throws Exception{
		Map<String, String> result = new HashMap<String, String>();
		if(null==liskWorkflowParams||liskWorkflowParams.isEmpty())
			return result;
			final String query = getDaoXml("getScreenLookupParameter", DAOResources.SCREEN_DAO);
			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("listWorkflwparam",  liskWorkflowParams);
			paramMap.put("mktlocaleId", Long.parseLong(marketlocaleId) );
			List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getScreenLookupParameter");
			for ( final Map<String, Object> data : listOfResults ) {
				result.put(data.get("entr_val").toString() + "", data.get("parm_val").toString());
			}
		return result;
	}
	/**
	 * @param mapScreenWkfParam
	 * @return
	 */
	private ArrayList<Long> getWkfParamScrVal(final Map<String, List<Map<String, String>>> mapScreenWkfParam) {
		final ArrayList<Long> result = new ArrayList<>();
		for (final String wkf : mapScreenWkfParam.keySet()) {
			final List<Map<String, String>> listwkfs = mapScreenWkfParam.get(wkf);
			for (final Map<String, String> workflowParameter : listwkfs) {
				if ("3".equals(workflowParameter.get("PARM_TYP"))) {
					if (ObjectUtils.isFilled(workflowParameter.get("VAL"))) {
						result.add(new Long(workflowParameter.get("VAL")));
					}
				}
			}
		}

		return result;
	}
	
	public List<String> getRestaurantMenuItems(final Long uniqueId, final String effectiveDate) throws Exception{
 		final List<String> restaurantMenuItems = new ArrayList<String>();
 		final String query = getDaoXml("getRestaurantMenuItems", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("uniqueId", uniqueId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getRestaurantMenuItems");
		for ( final Map<String, Object> data : listOfResults ) {
				if(data.get("PRD_CD")!=null) restaurantMenuItems.add(data.get("PRD_CD").toString());
			}
 		return restaurantMenuItems;
 	}
	
	public Map<String, Map<String, Map<String, String>>> getDynamicButtonDetails(List<Long> screenIds, List<Long> screenInstanceIds,Map<Long, ProductDetails> productCodeDtlsRelationMap, final String effectiveDate) throws Exception{
		Map<String, Map<String, Map<String, String>>> result = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String,String> buttonDetail = null;
		final String query = getDaoXml("getDynamicButtonDetails", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		List<Object[]> valuesMap = new ArrayList<>();
		for(int i =0 ; i < screenIds.size(); i++) {
			Object[] entry = {screenIds.get(i), screenInstanceIds.get(i)};
			valuesMap.add(entry);
		}
		paramMap.put("valuesMap", valuesMap);
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getDynamicButtonDetails");
		for ( final Map<String, Object> data : listOfResults ) {
			if(productCodeDtlsRelationMap.containsKey(Long.parseLong(data.get("PRD_CD").toString()))) {
				buttonDetail = new HashMap<String,String>();
				if(data.get("BTTN_NU")!=null) buttonDetail.put("BTTN_NU",data.get("BTTN_NU").toString());
				if(data.get("CAT")!=null) buttonDetail.put("CAT",data.get("CAT").toString());
				if(data.get("PRD_CD")!=null) {
				buttonDetail.put("PRD_CD",data.get("PRD_CD").toString());
				if(productCodeDtlsRelationMap.containsKey(Long.parseLong(data.get("PRD_CD").toString()))) {
					ProductDetails productDetails=productCodeDtlsRelationMap.get(Long.parseLong(data.get("PRD_CD").toString()));
					if(productDetails.getFgNoramlColor()!=null)	
						buttonDetail.put("FG_NRML_NA",productDetails.getFgNoramlColor());
					if(productDetails.getFgPressedColor()!=null)	
						buttonDetail.put("FG_PRSD_NA",productDetails.getFgPressedColor());
					if(productDetails.getBgNoramlColor()!=null)buttonDetail.put("BG_NRML_NA",productDetails.getBgNoramlColor());
					if(productDetails.getBgPressedColor()!=null)
							buttonDetail.put("BG_PRSD_NA",productDetails.getBgPressedColor());
					if(productDetails.getCaptionName()!=null)	buttonDetail.put("CPTN",productDetails.getCaptionName());
					if(productDetails.getImageName()!=null)	buttonDetail.put("IMG_NA",productDetails.getImageName());
				}
				}
				if(data.get("KEY_SCAN")!=null)	buttonDetail.put("KEY_SCAN",data.get("KEY_SCAN").toString());
				if(data.get("KEY_SHFT")!=null)buttonDetail.put("KEY_SHFT",data.get("KEY_SHFT").toString());
				if(data.get("IMG_NA")!=null)	buttonDetail.put("IMG_NA",data.get("IMG_NA").toString());
				if(data.get("WDTH")!=null)buttonDetail.put("WDTH",data.get("WDTH").toString());
				if(data.get("HGHT")!=null)	buttonDetail.put("HGHT",data.get("HGHT").toString());
				if(data.get("BTTN_ID")!=null)	buttonDetail.put("BTTN_ID",data.get("BTTN_ID").toString());
				if(data.get("PRD_CD")!=null)buttonDetail.put("PRD_CD",data.get("PRD_CD").toString());
				if(data.get("sound_file_id")!=null)buttonDetail.put("sound_file_id",data.get("sound_file_id").toString());
				if(data.get("OUTAGE_MODE")!=null)buttonDetail.put("OUTAGE_MODE",data.get("OUTAGE_MODE").toString());
				String key = data.get("scr_id").toString()  + "," + data.get("DATA_INST_ID").toString();
				Map<String, Map<String, String>> buttonsDetail =  result.get(key);
				if (buttonsDetail == null) {
					buttonsDetail = new HashMap<String, Map<String, String>>();
					result.put(key, buttonsDetail);
				}
				if(data.get("BTTN_ID")!=null&&!buttonsDetail.containsKey(data.get("BTTN_ID").toString())) 
					buttonsDetail.put(data.get("BTTN_ID").toString(),buttonDetail);
		} 
		}
		return result;
	}
	
	
	
	
	public Map<String,String> getAssignedScreenSet(Long marketId,
			String restaurantId, String restaurantInstanceId, 
			String effectiveDate) throws Exception {
		final Map<String, String> assignedScreenSets = new HashMap<String, String>();
		final String query = getDaoXml("getAssignedScreenSet", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId",marketId);
		paramMap.put("restId", Long.parseLong(restaurantId));
		paramMap.put("restInstId", Long.parseLong(restaurantInstanceId));
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults =Wizard.queryForList(dataSourceQuery, query, paramMap, "getAssignedScreenSet");
		LOGGER.info("ScreenServiceDAO :: getAssignedSets()");
		for(Map<String, Object> data: listOfResults) {
			assignedScreenSets.put("SET_ID", data.get("chld_set_id").toString());
			assignedScreenSets.put("SET_NA", data.get("na").toString());
			assignedScreenSets.put("SET_INST", data.get("data_inst_id").toString());
		}
		LOGGER.info("results for getAssignedSets():"+assignedScreenSets.size());
		return assignedScreenSets;

	}
	
	public Map<String, Long> getScheduleSize(String scheduleId,Long marketId) throws Exception{	
		Map<String,Long> scheduleSize=new HashMap<>();
		final String query = getDaoXml("getScheduleSize", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		paramMap.put("requestId", Long.valueOf(scheduleId));

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getScheduleSize");
		LOGGER.info("ScreenDAO :: getScheduleSize()");
		for (final Map<String, Object> screenData : listOfResults) {
			scheduleSize.put("scheduleSize",Long.parseLong(screenData.get("count").toString()));
		}
		LOGGER.info("results for getScheduleSize():"+scheduleSize);
		return scheduleSize;
	}
	
	public String getDefaultLocaleId(Long marketId) throws Exception {

		final String query = getDaoXml("getDefaultLocaleId", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		String marketLocaleId = null;

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getDefaultLocaleId");
		LOGGER.info("ScreenDAO :: getDefaultLocaleId()");
		for (final Map<String, Object> screenData : listOfResults) {
			marketLocaleId = screenData.get("lcle_id").toString();
		}
		LOGGER.info("results for getDefaultLocaleId():"+marketLocaleId);
		return marketLocaleId;
	}
	
	public List<String> getHotScreens() throws Exception{
		
		List<String> allHotScreens=new ArrayList<String>();
		
		final String query = getDaoXml("getHotScreens", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
	
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query,paramMap, "getHotScreens");
		
		for ( final Map<String, Object> data : listOfResults ) 
		{
			allHotScreens.add(data.get("SCR_TYP").toString());
		}
		
		return allHotScreens;
	}
	
	
	public String getDefaultButtonCaption(Long mktId) throws Exception 
	{
		String defaultButtonCaption=null;
		
			final String query = getDaoXml("getDefaultButtonCaption", DAOResources.SCREEN_DAO);

			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("mktId",  mktId);
			
			List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getDefaultButtonCaption");
			
			for ( final Map<String, Object> data : listOfResults ) 
			{
				
				defaultButtonCaption=data.get("prop_val").toString();
			}
		
		return defaultButtonCaption;
	}
	
	public List<String> getDynamicWorkflowParameters(Long mktId, String workflowName) throws Exception {
		List<String> workflowParams=new ArrayList<String>();
		if (mktId!=null && workflowName!=null) 
		{
			final String query = getDaoXml("getDynamicWorkflowParameters", DAOResources.SCREEN_DAO);

			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("mktId",  mktId);
			paramMap.put("wrkflname",  workflowName);
			
			List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getDynamicWorkflowParameters");
			
			for ( final Map<String, Object> data : listOfResults ) 
			{
				workflowParams.add(data.get("PARM_ID").toString());
			}
		}
		return workflowParams;
	}
	
	
	
	public Map<String, String> getLocalizationSets(String restId, String restInstId, Long marketId) throws Exception {
		Map<String, String> localizationSets=new HashMap<String, String>();
		final String query = getDaoXml("getLocalizationSets", DAOResources.SCREEN_DAO);

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("rstId", Long.parseLong(restId) );
		paramMap.put("rstInstId",   Long.parseLong(restInstId));
		paramMap.put("marketId",   marketId);
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getLocalizationSets");
		
		for ( final Map<String, Object> data : listOfResults ) 
		{	
			localizationSets.put("PREN_SET_ID",data.get("PREN_SET_ID").toString());
			localizationSets.put("CUSM_SET_ID", data.get("CUSM_SET_ID").toString());
		}
		
		return localizationSets;
	}
	public Map<String, String> getLocalizedFields(Long parentSetId, Long childSetId, String effectiveDate) throws Exception {
		Map<String, String> localizationFields=new HashMap<String, String>();
		final String query = getDaoXml("getLocalizedField", DAOResources.SCREEN_DAO);

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("prtStId",  parentSetId);
		paramMap.put("chdStId",  childSetId);
		paramMap.put("effectiveDate",  effectiveDate);
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getLocalizedField");
		
		for ( final Map<String, Object> data : listOfResults ) 
		{	
			localizationFields.put("CTRY_ID",data.get("CTRY_ID").toString());
			localizationFields.put("LANG_ID", data.get("LANG_ID").toString());
		}
		
		return localizationFields;
	}
	
	

	/**
	 * @param screenSetId
	 * @param effectiveDate
	 * @return
	 * @throws Exception 
	 * @throws GeneratorDBException
	 */
	public Map<String,Map<String,String>> getScreenDetails(Long screenSetId, String effectiveDate) throws Exception{
		Map<String,Map<String,String>> screenDetails = new LinkedHashMap<String,Map<String,String>>();
		Map<String,String> screenDetail = null;

		final String query = getDaoXml("getScreenDetails", DAOResources.SCREEN_DAO);

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("setId",  screenSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));

		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getScreenDetails");

		for ( final Map<String, Object> data : listOfResults ) {			
				screenDetail = new HashMap<String,String>();
				screenDetail.put("SCR_INST_ID",data.get("DATA_INST_ID").toString());
				screenDetail.put("TYP",data.get("TYP").toString());
				screenDetail.put("SCR_NU",data.get("SCR_NU").toString());
				screenDetail.put("TOUT",data.get("TOUT").toString());
				screenDetail.put("TITL",data.get("TITL").toString());
				if(data.get("SCR_BKGD_IMG") !=null) screenDetail.put("SCR_BKGD_IMG",data.get("SCR_BKGD_IMG").toString());
				if(data.get("DYPT_NA") !=null) screenDetail.put("DYPT_NA",data.get("DYPT_NA").toString());
				if(data.get("SCR_BKGD_COLR_ID") !=null) screenDetail.put("SCR_BKGD_COLR_ID",data.get("SCR_BKGD_COLR_ID").toString());
				if(data.get("GRLL_GRP_ID") !=null) screenDetail.put("GRLL_GRP_ID",data.get("GRLL_GRP_ID").toString());
				if(data.get("SRG_ID") !=null) screenDetail.put("SRG_ID",data.get("SRG_ID").toString());
				if(data.get("SOUND_FILE_ID") !=null) screenDetail.put("SOUND_FILE_ID",data.get("SOUND_FILE_ID").toString());
				screenDetails.put(data.get("SCR_ID").toString(),screenDetail);
		}	
		return screenDetails;				
	}
	
	public Map<String, String> getAllWorkflows(Long marketId) throws Exception {
		final Map<String, String> allWorkflows = new HashMap<String, String>();
		final String query = getDaoXml("getAllWorkflows", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllWorkflows");
		for (Map<String, Object> data : listOfResults) {
			allWorkflows.put(data.get("WRKFL_ID").toString(), data.get("WRKFL_NA").toString());
		}

		return allWorkflows;
	}
	
	public Map<String,String> getAllEvents(Long marketId) throws Exception{
		
		final Map<String,String> allEvents = new HashMap<String,String>();
		final String query = getDaoXml("getAllEvents", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllEvents");
		for (Map<String, Object> data : listOfResults) {
				allEvents.put(data.get("evnt_id").toString(), data.get("event_name").toString());
		}
		
		return allEvents;
	}
	
	public Map<String, String> getAllWorkflowParams(Long marketId) throws Exception {

		final Map<String, String> allWorkflows = new HashMap<String, String>();
		final String query = getDaoXml("getAllWorkflowParams", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllWorkflowParams");
		for (Map<String, Object> data : listOfResults) {
			allWorkflows.put(data.get("PARM_ID").toString(), data.get("PARM_NA").toString());
		}

		return allWorkflows;
	}
	
	public Map<String, String> getAllGrillGroups(Long marketId) throws Exception {

		final Map<String, String> allGrillGroups = new HashMap<String, String>();
		final String query = getDaoXml("getAllGrillGroups", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllGrillGroups");
		for (Map<String, Object> data : listOfResults) {
			allGrillGroups.put(data.get("GRLL_GRP_ID").toString(), data.get("GRLL_GRP_NA").toString());
		}

		return allGrillGroups;
	}
	
	public Map<String, String> getAllSmartReminders(Long marketId) throws Exception {
		final Map<String, String> allSmartReminders = new HashMap<String, String>();
		final String query = getDaoXml("getAllSmartReminders", DAOResources.SCREEN_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAllSmartReminders");
		for (Map<String, Object> data : listOfResults) {
			allSmartReminders.put(data.get("SRG_ID").toString(), data.get("SRG_NA").toString());
		}

		return allSmartReminders;
	}


	public Map<String, List<Map<String, String>>> getButtonWorkflows(List<Long> buttonIds, List<Long> screenInstanceIds) throws Exception{
		Map<String, List<Map<String,String>>> result = new HashMap<String,List<Map<String,String>>>();
		Map<String,String> workflowDetail = null;
	
		if (buttonIds == null || buttonIds.isEmpty()) {
			return result;
		}
		
		HashSet<Long> hsButtonIds = new HashSet<Long>(buttonIds);
		String ids = hsButtonIds.toString().replace("[","").replace("]","");
		
		HashSet<Long> hsInstanceIds = new HashSet<Long>(screenInstanceIds);
		String instanceIds = hsInstanceIds.toString().replace("[","").replace("]","");
		
		String query = getDaoXml("getButtonWorkflows", DAOResources.SCREEN_DAO);
		query = query.replace(":arrayBttnIds",ids);
		query = query.replace(":arrayScreenInstIds",instanceIds);
			
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, "getButtonWorkflows");
		
		for ( final Map<String, Object> data : listOfResults ) {
			workflowDetail = new HashMap<String,String>();
			if(data.get("BTTN_WRKFL_ASGN_ID") != null) workflowDetail.put("BTTN_WRKFL_ASGN_ID",data.get("BTTN_WRKFL_ASGN_ID").toString());
			if(data.get("WRKFL_ID") != null) workflowDetail.put("WRKFL_ID",data.get("WRKFL_ID").toString());
			if(data.get("EVNT_ID") != null) workflowDetail.put("EVNT_ID",data.get("EVNT_ID").toString());				
			String key = data.get("BTTN_ID").toString() + "," + data.get("BTTN_INST_ID").toString();
			List<Map<String, String>> workflowDetails = result.get(key);
			if (workflowDetails == null) {
				workflowDetails = new ArrayList<Map<String, String>>();
				result.put(key, workflowDetails);
			}
			workflowDetails.add(workflowDetail);
		}
		return result;
	}

	
	public Map<String, String>  getButtonProductCode(ArrayList<Long> buttonIds, ArrayList<Long> screenInstanceIds) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
	
		if (buttonIds == null || buttonIds.isEmpty()) {
			return result;
		}

		HashSet<Long> hsButtonIds = new HashSet<Long>(buttonIds);
		String ids = hsButtonIds.toString().replace("[","").replace("]","");
		
		HashSet<Long> hsInstanceIds = new HashSet<Long>(screenInstanceIds);
		String instanceIds = hsInstanceIds.toString().replace("[","").replace("]","");
		
		String query = getDaoXml("getButtonProductCode", DAOResources.SCREEN_DAO);
		query = query.replace(":arrayBttnIds",ids);
		query = query.replace(":arrayScreenInstIds",instanceIds);			
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, "getButtonProductCode");		
		for ( final Map<String, Object> data : listOfResults ) {							
				String key = data.get("ID").toString() + "," +   data.get("INSTID").toString();
				if (!result.containsKey(key)) {
					result.put(key,   stringOrEmpty(data.get("VAL")));			
				}
		}
	
		return result;
	}

	public Map<String, String> createScreenXMLinDB(List<ScreenDetails> listScreenDetailsPos, String screenPos,
			Element elError, String uniqueId, PackageGeneratorDTO packageGeneratorDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	public Map<String, Map<String, Map<String, String>>> getBLMLangDetails(ArrayList<Long> buttonIds, ArrayList<Long> screenInstanceIds) throws Exception {
		Map<String,Map<String,Map<String,String>>> result = new HashMap<String,Map<String,Map<String,String>>>();
		Map<String,String> buttonDetail = null;
		
		if (buttonIds == null || buttonIds.isEmpty()) {
			return result;
		}


		String ids="0";
		for(Long id : buttonIds)  ids = id.toString() + "," + ids;	
		
		String instanceIds="0";
		for(Long id : screenInstanceIds)  instanceIds = id.toString() + "," + instanceIds;
		
		String query = getDaoXml("getBLMLangDetails", DAOResources.SCREEN_DAO);
		query = query.replace(":arrayBttnIds",ids);
		query = query.replace(":arrayScreenInstIds",instanceIds);
						
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, "getBLMLangDetails");
		
		for ( final Map<String, Object> data : listOfResults ) {
				buttonDetail = new HashMap<String,String>();
				if(data.get("CPTN") != null) buttonDetail.put("CPTN",data.get("CPTN").toString());
				if(data.get("BMP") != null) buttonDetail.put("BMP",data.get("BMP").toString());
				if(data.get("BMP_PRSD") != null) buttonDetail.put("BMP_PRSD",data.get("BMP_PRSD").toString());	
				String key = data.get("id").toString() + "," + data.get("instId").toString();
				Map<String, Map<String, String>> buttonDetails = result.get(key);
				if (buttonDetails == null) {
					buttonDetails = new HashMap<String, Map<String,String>>();
					result.put(key, buttonDetails);
				}
				buttonDetails.put(data.get("BTTN_ID").toString()+","+data.get("LANG_ID").toString(),buttonDetail);
			}		
	
		return result;
	}
	
	/**
	* Returns the MMI map with POS KVS and Presentation data  
	* @param	marketId - market number
	* @param	effectiveDate - effective date
	* @param    masterSetId - set id 
	* @return   Hashmap with MMI POS KVS and Presentation data
	*/	
	public Map<Long, ProductDetails>  getProductPosKvsPresentationByMaster( RequestDTO requestDTO, long masterSetID,Map<String, String> allColors,Map<String, 
			String> allImages,List<Long>prdList,String restaurantDefLang) throws Exception {
		
		final String query = getDaoXml("getMenuItemPOSKVSPresentation", DAOResources.SCREEN_DAO);
		
		Map<Long, ProductDetails> listOfProduct  = new HashMap<>();	
		if(prdList.isEmpty())
			return listOfProduct;

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", requestDTO.getMarketId());
		paramMap.put(ProductDBConstant.SETID,  masterSetID);		
		paramMap.put("type", ProductDBConstant.MMI_SET_TYPE);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(requestDTO.getEffectiveDate()) );
		paramMap.put("prdIDs", prdList);
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap);
		
		for ( final Map<String, Object> data : listOfResults ) {
			ProductDetails productDTO = new ProductDetails();	
			if(data.get("prd_id")!=null) {
			 productDTO.setProductId(Long.parseLong(data.get("prd_id").toString()));
			 productDTO.setProductCode(Long.parseLong(data.get("prd_cd").toString()));
			 if(data.get("AUX_MENU_ITM")!=null) 
				 productDTO.setAuxiliaryMenuItem(Long.parseLong(data.get("AUX_MENU_ITM").toString()));
			 if(data.get("bg_nrml")!=null) 
				 productDTO.setBgNoramlColor(allColors.get(data.get("bg_nrml").toString() + "," + restaurantDefLang));	 
			 if(data.get("fg_nrml")!=null) 
				 productDTO.setFgNoramlColor(allColors.get(data.get("fg_nrml").toString() + "," + restaurantDefLang));
			 if(data.get("bg_prsd")!=null) 
				 productDTO.setBgPressedColor(allColors.get(data.get("bg_prsd").toString() + "," + restaurantDefLang));
			 if(data.get("fg_prsd")!=null) 
				 productDTO.setFgPressedColor(allColors.get(data.get("fg_prsd").toString() + "," + restaurantDefLang));
			 if(data.get("img")!=null)		 
			 productDTO.setImageName(allImages.get(data.get("img").toString()));
			 productDTO.setCaptionName(stringOrEmpty(data.get("CPTN_LN_1"))+" "+stringOrEmpty(data.get("CPTN_LN_2"))+" "+stringOrEmpty(data.get("CPTN_LN_3")));
			// BeanUtils.copyProperties(productDTO, productDTOMaster, layeringDBDAO.getNullPropertyNames(productDTO));
			 if(data.get("prod_prd_grp")!=null) {
				 productDTO.setProdPRGGroup(Long.valueOf(data.get("prod_prd_grp").toString()));
			 }
			listOfProduct.put(productDTO.getProductId(), productDTO);
		}
		}
		return listOfProduct;
	}	
	public Map<Long, ProductDetails>  getProductPosKvsPresentationBySet(Map<Long, ProductDetails>  listOfProductsByMaster,RequestDTO requestDTO,long typ, long masterSetID,Map<String, String> allColors,
			Map<String, String> allImages,List<Long>prdList,String restaurantDefLang) throws Exception {
			
			final String query = getDaoXml("getMenuItemPOSKVSPresentation", DAOResources.SCREEN_DAO);
			if(prdList.isEmpty())
				return listOfProductsByMaster;
			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("mktId", requestDTO.getMarketId());
			paramMap.put(ProductDBConstant.SETID,  masterSetID);		
			paramMap.put("type", typ);
			paramMap.put("effectiveDate", DateUtility.convertStringToDate(requestDTO.getEffectiveDate()) );
			paramMap.put("prdIDs", prdList);
			List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap);
			
			for ( final Map<String, Object> data : listOfResults ) {
				ProductDetails productDTO = new ProductDetails();		 
				 productDTO.setProductId(Long.parseLong(data.get("PRD_ID").toString()));
				 if(data.get("prd_cd")!=null) 
				 productDTO.setProductCode(Long.parseLong(data.get("prd_cd").toString()));
				 ProductDetails productDTOMaster=  listOfProductsByMaster.get(productDTO.getProductId());
				 if(data.get("AUX_MENU_ITM")!=null) 
					 productDTO.setAuxiliaryMenuItem(Long.parseLong(data.get("AUX_MENU_ITM").toString()));
				 if(data.get("bg_nrml")!=null) 
					 productDTO.setBgNoramlColor(allColors.get(data.get("bg_nrml").toString() + "," + restaurantDefLang));
				 if(data.get("fg_nrml")!=null) 
					 productDTO.setFgNoramlColor(allColors.get(data.get("fg_nrml").toString() + "," + restaurantDefLang));
				 if(data.get("bg_prsd")!=null) 
					 productDTO.setBgPressedColor(allColors.get(data.get("bg_prsd").toString() + "," + restaurantDefLang));
				 if(data.get("fg_prsd")!=null) 
					 productDTO.setFgPressedColor(allColors.get(data.get("fg_prsd").toString() + "," + restaurantDefLang));
				 if(data.get("img")!=null)		 
				 productDTO.setImageName(allImages.get(data.get("img").toString()));
				 if(null==data.get("CPTN_LN_1")&&null==data.get("CPTN_LN_2")&&null==data.get("CPTN_LN_3")) {
					 productDTO.setCaptionName(null);
				 }else {
				 productDTO.setCaptionName(stringOrEmpty(data.get("CPTN_LN_1"))+" "+stringOrEmpty(data.get("CPTN_LN_2"))+" "+stringOrEmpty(data.get("CPTN_LN_3")));
				 }
				 if(data.get("prod_prd_grp")!=null) {
					 productDTO.setProdPRGGroup(Long.valueOf(data.get("prod_prd_grp").toString()));
				 }
				 BeanUtils.copyProperties(productDTO, productDTOMaster, layeringDBDAO.getNullPropertyNames(productDTO));
				
			}
			return listOfProductsByMaster;
		}

}
