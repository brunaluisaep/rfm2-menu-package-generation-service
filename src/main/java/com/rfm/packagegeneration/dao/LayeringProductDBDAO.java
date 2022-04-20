package com.rfm.packagegeneration.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.rfm.packagegeneration.constants.GeneratorConstant;
import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.dto.AssociatedCategories;
import com.rfm.packagegeneration.dto.AssociatedPromoProducts;
import com.rfm.packagegeneration.dto.Category;
import com.rfm.packagegeneration.dto.Component;
import com.rfm.packagegeneration.dto.GenericEntry;
import com.rfm.packagegeneration.dto.Item;
import com.rfm.packagegeneration.dto.Parameter;
import com.rfm.packagegeneration.dto.PriceTax;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductAbsSettings;
import com.rfm.packagegeneration.dto.ProductCCMSettings;
import com.rfm.packagegeneration.dto.ProductGeneralSettingMenuItemNames;
import com.rfm.packagegeneration.dto.ProductList;
import com.rfm.packagegeneration.dto.ProductPosKvs;
import com.rfm.packagegeneration.dto.ProductPresentation;
import com.rfm.packagegeneration.dto.ProductPromotionRange;
import com.rfm.packagegeneration.dto.ProductShortCutSettings;
import com.rfm.packagegeneration.dto.ProductSmartRouting;
import com.rfm.packagegeneration.dto.ProductTags;
import com.rfm.packagegeneration.dto.PromotionGroup;
import com.rfm.packagegeneration.dto.Reduction;
import com.rfm.packagegeneration.dto.Set;
import com.rfm.packagegeneration.dto.ShortCutDetails;
import com.rfm.packagegeneration.dto.Size;
import com.rfm.packagegeneration.dto.SmartRoutingTask;
import com.rfm.packagegeneration.dto.TimeFrames;
import com.rfm.packagegeneration.dto.WeekDays;
import com.rfm.packagegeneration.hikari.Wizard;
import com.rfm.packagegeneration.logging.annotation.TrackedMethod;
import com.rfm.packagegeneration.service.CytGroupDisplayOrder;
import com.rfm.packagegeneration.utility.DateUtility;
import com.rfm.packagegeneration.utility.Pair;



@Repository
public class LayeringProductDBDAO extends CommonDAO {
	private static final Logger LOGGER = LogManager.getLogger("LayeringProductDBDAO");
	private static final String PREN_SET_ID = "PREN_SET_ID";
	private static final String CUSM_SET_ID = "CUSM_SET_ID";
	private static final String SUBS_GRP_ID = "SUBS_GRP_ID";
	private static final String OTH_TAX_ENTR = "OTH_TAX_ENTR";
	private static final String TKUT_TAX_ENTR = "TKUT_TAX_ENTR";
	private static final String EATIN_TAX_ENTR = "EATIN_TAX_ENTR";
	private static final String OTH_TAX_RULE = "OTH_TAX_RULE";
	private static final String TKUT_TAX_RULE = "TKUT_TAX_RULE";
	private static final String EATIN_TAX_RULE = "EATIN_TAX_RULE";
	private static final String OTH_TAX_CD = "OTH_TAX_CD";
	private static final String TKUT_TAX_CD = "TKUT_TAX_CD";
	private static final String EATIN_TAX_CD = "EATIN_TAX_CD";
	private static final String OTH_PRC = "oth_prc";
	private static final String TKUT_PRC = "tkut_prc";
	private static final String EATIN_PRC = "eatin_prc";
	private static final String PRDID = "prdId";
	private static final String EFF_DATE = "effDate";
	private static final String PRMO_PRD_ID = "PRMO_PRD_ID";
	private static final String GET_ASSOCIATED_PROMO_PRODUCTS = "getAssociatedPromoProducts";
	private static final String PRD_CD = "PRD_CD";
	private static final String CAT_CD = "CAT_CD";
	private static final String SET_ID = "set_id";
	private static final String SHRTCUT_KIOSK_ID = "shrtcut_kiosk_id";
	private static final String SUN_TO_TM = "SUN_TO_TM";
	private static final String SUN_FROM_TM = "SUN_FROM_TM";
	private static final String SUN_VLD_FL = "SUN_VLD_FL";
	private static final String SAT_TO_TM = "SAT_TO_TM";
	private static final String SAT_FROM_TM = "SAT_FROM_TM";
	private static final String SAT_VLD_FL = "SAT_VLD_FL";
	private static final String FRI_TO_TM = "FRI_TO_TM";
	private static final String FRI_FROM_TM = "FRI_FROM_TM";
	private static final String FRI_VLD_FL = "FRI_VLD_FL";
	private static final String THU_TO_TM = "THU_TO_TM";
	private static final String THU_FROM_TM = "THU_FROM_TM";
	private static final String THU_VLD_FL = "THU_VLD_FL";
	private static final String WED_TO_TM = "WED_TO_TM";
	private static final String WED_FROM_TM = "WED_FROM_TM";
	private static final String WED_VLD_FL = "WED_VLD_FL";
	private static final String TUE_TO_TM = "TUE_TO_TM";
	private static final String TUE_FROM_TM = "TUE_FROM_TM";
	private static final String TUE_VLD_FL = "TUE_VLD_FL";
	private static final String TIME_SET_23_59 = "23:59";
	private static final String TIME_SET_00_00 = "00:00";
	private static final String MON_TO_TM = "MON_TO_TM";
	private static final String MON_FROM_TM = "MON_FROM_TM";
	private static final String GET_MENU_ITEM_POSKVS_PRESENTATION = "getMenuItemPOSKVSPresentation";
	private static final String MKT_ID= "mktId";
	private static final String MARKETID= "marketId";
	private static final String EFFECTIVE_DATE = "effectiveDate";
	private static final String PRD_ID = "PRD_ID";
	private static final String PRMO_MENU_ITM = "PRMO_MENU_ITM";
	private static final String PRMO_CHCE = "PRMO_CHCE";
	private static final String SMRT_RTNG_TASK_ID = "SMRT_RTNG_TASK_ID";
	private static final String SLS_EATIN = "SLS_EATIN";
	private static final String SLS_TKUT = "SLS_TKUT";
	private static final String SHOW_ON_MAIN = "showOnMain";
	private static final String TRG_DISPLAY_NUMBERS = "TRG_DISPLAY_NUMBERS";
	private static final String SLS_OTH = "SLS_OTH";
	private static final String FEE_EATIN = "FEE_EATIN";
	private static final String FEE_TKUT = "FEE_TKUT";
	private static final String FEE_OTH = "FEE_OTH";
	private static final String KVS_SHOW_ON_MAIN = "KVS_SHOW_ON_MAIN";
	private static final String KVS_DN_DCMP = "KVS_DN_DCMP";
	private static final String DO_NOT_DECOMP_VM = "doNotDecompVM";
	private static final String KVS_SHOW_ON_MFY = "KVS_SHOW_ON_MFY";
	private static final String SHOW_ON_MFY = "showOnMFY";
	private static final String KVS_SHOW_ON_SUMR = "KVS_SHOW_ON_SUMR";
	private static final String SHOW_ON_SUMMARY = "showOnSummary";
	private static final String SMRT_RMDR = "SMRT_RMDR";
	private static final String DRNK_VOL_ID = "DRNK_VOL_ID";
	private static final String AUTO_GRLL_CNFG = "AUTO_GRLL_CNFG";
	private static final String PRD_UNT = "PRD_UNT";
	private static final String TAGS_DATA = "tagsdata";
	private static final String GET_MENU_ITEM_PARAMETER_LIST = "getMenuItemParameterList";
	private static final String LANG_ID = "LANG_ID";
	private static final String PARM_ID = "parm_id";
	private static final String P_VALUE = "pvalue";
	private static final String PRD_DVC_NA = "PRD_DVC_NA";
	private static final String MON_VLD_FL = "MON_VLD_FL";
	private static final String GET_MENU_ITEM_PROMOTION_RANGE = "getMenuItemPromotionRange";

	@Value("${datasource.used.query}")
	private String dataSourceQuery;

	/**
	* Returns the MMI map with POS KVS and Presentation data  
	* @param	marketId - market number
	* @param	effectiveDate - effective date
	* @param    masterSetId - set id 
	* @return   Hashmap with MMI POS KVS and Presentation data
	*/	
	public Map<Long, Product>  getProductPosKvsPresentationByMaster(long marketId,  String effetiveDate, long masterSetID) throws Exception {
		Map<String, List<Parameter>> defaultCustomParameters = getMarketCustomParameters(marketId);
		
		final String query = getDaoXml(GET_MENU_ITEM_POSKVS_PRESENTATION, DAOResources.PRODUCT_DB_DAO);
		
		Map<Long, Product> listOfProduct  = new HashMap<>();					 

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  masterSetID);		
		paramMap.put("type", ProductDBConstant.MMI_SET_TYPE);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effetiveDate) );
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemPOSKVSPresentation");
		
		for ( final Map<String, Object> data : listOfResults ) {
			 Product productDTO = new Product();		 
			 productDTO.setProductId(Long.parseLong(data.get(PRD_ID).toString()));
			 
			 populatePromoRangeAttributes(data,productDTO);
			 populateGenralSetting(data, productDTO);
			 populateMenuTypeAvailable(data,productDTO);
			 populateProductTimeAvailableForSale(data,productDTO);
			 populateDeliveryFeeDetails(data,productDTO);
			 productDTO.setProductAbsSettings(mapProductAbsSetting(data));
			 productDTO.setProductPresentation(mapProductPresentation(data));		 			 
			 productDTO.setProductPosKvs(mapProductPosKvs(data, true));	 
			 productDTO.setProductSmartRouting(mapProductSmartRouting(data));
			 productDTO.setProductCCMSettings(mapProductCCMSettings(data));
			 
			 populateDefaultCustomParameters(productDTO, defaultCustomParameters);
			 listOfProduct.put(productDTO.getProductId(), productDTO);
		}		
		createSmartRoutingTask(listOfProduct,paramMap);
		return listOfProduct;
	}
	
	private void populateDefaultCustomParameters(Product productDTO, Map<String, List<Parameter>> defaultCustomParameters) {
		if (productDTO.getProductClassId() != null && 
				defaultCustomParameters.containsKey(productDTO.getProductClassId())) {
			List<Parameter> parameters = defaultCustomParameters.get(productDTO.getProductClassId()).stream().map(Parameter::copy).collect(Collectors.toList());
			productDTO.setParameters(parameters);
		}
	}

	private void populatePromoRangeAttributes(Map<String, Object> data, Product productDTO) {
		if(data.get(PRMO_MENU_ITM)!=null)productDTO.setPrmoMenuItem(Long.parseLong(data.get(PRMO_MENU_ITM).toString()));
		if(data.get(PRMO_CHCE)!=null)productDTO.setPrmoChoice(Long.parseLong(data.get(PRMO_CHCE).toString()));		
		if(data.get("PRD_INST_ID")!=null)productDTO.setPromoInstId(Long.parseLong(data.get("PRD_INST_ID").toString()));		
		
		Timestamp dbStartDate = (Timestamp) data.get("PRMO_STRT_DT");
		if (dbStartDate!=null) {
			productDTO.setPromoStartDate(dbStartDate.toLocalDateTime().toLocalDate());
		}
		
		Timestamp dbEndDate = (Timestamp) data.get("PRMO_END_DT");
		if (dbEndDate!=null) {
			productDTO.setPromoEndDate(dbEndDate.toLocalDateTime().toLocalDate());
		}
		
	}


	private void createSmartRoutingTask(Map<Long, Product> listOfProduct,Map<String, Object> paramMap) throws Exception {
		final String query = getDaoXml("getSmartRoutingTaskDetails", DAOResources.PRODUCT_DB_DAO);
		List<Map<String, Object>> listOfSmartRoutingTask = Wizard.queryForList(dataSourceQuery,query,paramMap, "getSmartRoutingTaskDetails");

		for (final Map<String, Object> data1 : listOfSmartRoutingTask) {

			Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data1.get(PRD_ID).toString()), key -> {
				Product prdDTO = new Product();
				prdDTO.setProductId(Long.parseLong(data1.get(PRD_ID).toString()));
				prdDTO.setProductSmartRouting(new ProductSmartRouting());
				return prdDTO;
			});
			
			if( productDTO.getProductSmartRouting().getSmartRoutingTasks()==null){
				
				productDTO.getProductSmartRouting().setSmartRoutingTasks(new ArrayList<>());
			}
			
			mapSmartRoutingTasks(productDTO.getProductSmartRouting().getSmartRoutingTasks(),data1);

		}

	}

	private void mapSmartRoutingTasks(List<SmartRoutingTask> smartRoutingTasksList, Map<String, Object> data1) {
		long taskId = Long.parseLong(data1.get(SMRT_RTNG_TASK_ID).toString());
		Optional<SmartRoutingTask> routingTaskOp = smartRoutingTasksList.stream().filter(e -> e.getSmartTaskId() == taskId).findAny();
		SmartRoutingTask routingTask = null;
		if (routingTaskOp.isEmpty()) {
			routingTask = new SmartRoutingTask();
			smartRoutingTasksList.add(routingTask);
		} else {
			routingTask = routingTaskOp.get();
		}

		if (data1.get(SMRT_RTNG_TASK_ID) != null) {
			routingTask.setSmartTaskId(Long.valueOf(data1.get(SMRT_RTNG_TASK_ID).toString()));
		}
		if (data1.get("PRD_SMRT_RTNG_TASK_UK") != null) {
			routingTask.setSmartTaskUK(Long.valueOf(data1.get("PRD_SMRT_RTNG_TASK_UK").toString()));
		}
		if (data1.get("MSG_KEY") != null) {
			routingTask.setMsgKey(data1.get("MSG_KEY").toString());
		}
		if (data1.get("TASK_TM") != null) {
			routingTask.setTaskTime(Long.valueOf(data1.get("TASK_TM").toString()));
		}
		if (data1.get("DSPL_TM") != null) {
			routingTask.setDisplayTime(Long.valueOf(data1.get("DSPL_TM").toString()));
		}
		if (data1.get("MNTR") != null) {
			routingTask.setMonitor(data1.get("MNTR").toString());
		}

	}

	/**
	* transform database record to Presentation DTO  
	* @param	date - database record
	* @return   Presentation KVS DTO
	*/

	private ProductPresentation mapProductPresentation(Map<String, Object> data) {
		 ProductPresentation oPresentation = new ProductPresentation();
		 if(data.get("CPTN_LN_1")!=null) oPresentation.setCaptionLine1(data.get("CPTN_LN_1").toString());
		 if(data.get("CPTN_LN_2")!=null) oPresentation.setCaptionLine2(data.get("CPTN_LN_2").toString());
		 if(data.get("CPTN_LN_3")!=null) oPresentation.setCaptionLine3(data.get("CPTN_LN_3").toString());			 			 		 
		 oPresentation.setColorScreenKVSFont(addNewEntry(data,"KVS_COLR"));
		 oPresentation.setColorScreenDisplay(addNewEntry(data,"DSPL_COLR"));
		 oPresentation.setColorButtonBackground(addNewEntry(data,"BG_NRML"));
		 oPresentation.setColorButtonText(addNewEntry(data,"FG_NRML"));
		 oPresentation.setColorButtonBackgroundPressed(addNewEntry(data,"BG_PRSD"));
		 oPresentation.setColorButtonTextPressed(addNewEntry(data,"FG_PRSD"));							 
		 if(data.get("IMG")!=null) {
			if(oPresentation.getImage()==null) oPresentation.setImage(new ArrayList<>());  		 
		 	oPresentation.getImage().add(addNewEntry(data,"IMG"));
		 }
		 oPresentation.setSmallImage(addNewEntry(data,"SML_IMG"));
		 oPresentation.setGrillImageString(addNewEntry(data,"GRL_IMG"));
		 oPresentation.setHandheldImage(addNewEntry(data,"HNDHLD_IMG"));
		 oPresentation.setKioskImage(addNewEntry(data,"KSK_IMG"));
		 oPresentation.setAlternative(addNewEntry(data,"ALT"));
		 oPresentation.setSummaryMonitorImage(addNewEntry(data,"SUMR_MNIT_IMG"));
		 oPresentation.setCsoCircleImage(addNewEntry(data,"CSO_CRCL_IMG"));
		 oPresentation.setCsoLargeImage(addNewEntry(data,"CSO_LRG_IMG"));
		 oPresentation.setCsoSmallImage(addNewEntry(data,"CSO_SMLL_IMG"));
		 oPresentation.setCsoCartImage(addNewEntry(data,"CSO_CRT_IMG"));
		 oPresentation.setCsoGrillImage(addNewEntry(data,"CSO_GRLL_IMG"));
		 oPresentation.setCsoDimensionImage(addNewEntry(data,"CSO_DIMN_IMG"));
		 oPresentation.setCsoValueMealImage(addNewEntry(data,"CSO_VALUE_MEAL_IMG"));
		 oPresentation.setCytPreviewImage(addNewEntry(data,"CYT_PRVW_IMG"));
		 oPresentation.setCytPreviewBottomImage(addNewEntry(data,"CYT_PRVW_BTTN_IMG"));
		 if(data.get("COD_MEDIA_FILES")!=null) {
			if(oPresentation.getCodMediaFiles()==null) oPresentation.setCodMediaFiles(data.get("cod_imges").toString()); 
		 }
		 oPresentation.setSoundFile(addNewEntry(data,"SOUND_FILE"));
		 return oPresentation;
	}
	

	/**
	* transform database record to POS KVS DTO  
	* @param	date - database record
	* @return   Product Pos KVS DTO
	*/
	private ProductPosKvs mapProductPosKvs(Map<String, Object> data, final boolean isMaster) {		 
		 ProductPosKvs oPosKvs = new ProductPosKvs();
		 
		 if(data.get(SLS_EATIN)!=null && data.get(SLS_EATIN).toString().equals(ProductDBConstant.ONE)) { 
			 oPosKvs.getSalesType().add(new GenericEntry(1,"eatin"));
		 } else if (data.get(SLS_EATIN)!=null) {
			 oPosKvs.getSalesType().add(new GenericEntry(0,"eatin"));
		 }
		 
		 if(data.get(SLS_TKUT)!=null && data.get(SLS_TKUT).toString().equals(ProductDBConstant.ONE)) { 
			 oPosKvs.getSalesType().add(new GenericEntry(1,"takeout"));
		 } else if (data.get(SLS_TKUT)!=null) {
			 oPosKvs.getSalesType().add(new GenericEntry(0,"takeout"));
		 }
		 
		 if(data.get(SLS_OTH)!=null && data.get(SLS_OTH).toString().equals(ProductDBConstant.ONE)) { 
			 oPosKvs.getSalesType().add(new GenericEntry(1,"other"));
		 } else if (data.get(SLS_OTH)!=null) {
			 oPosKvs.getSalesType().add(new GenericEntry(0,"other"));
		 }
		 
		 if(data.get(FEE_EATIN)!=null && data.get(FEE_EATIN).toString().equals(ProductDBConstant.ONE)) { 
			 oPosKvs.getFeeExempt().add(new GenericEntry(1,"Eatin"));
		 } else if (data.get(FEE_EATIN)!=null) {
			 oPosKvs.getFeeExempt().add(new GenericEntry(0,"Eatin"));
		 }		 
		 if(data.get(FEE_TKUT)!=null && data.get(FEE_TKUT).toString().equals(ProductDBConstant.ONE)) { 
			 oPosKvs.getFeeExempt().add(new GenericEntry(1,"Takeout"));
		 } else if (data.get(FEE_TKUT)!=null) {
			 oPosKvs.getFeeExempt().add(new GenericEntry(0,"Takeout"));
		 }
		 if(data.get(FEE_OTH)!=null && data.get(FEE_OTH).toString().equals(ProductDBConstant.ONE)) { 
			 oPosKvs.getFeeExempt().add(new GenericEntry(1,"Other"));
		 } else if (data.get(FEE_OTH)!=null) {
			 oPosKvs.getFeeExempt().add(new GenericEntry(0,"Other"));
		 }
		 
		 if(data.get("TRG_DISPLAY_ORB")!=null) { 
			 oPosKvs.setTriggerDisplayonORB(Long.parseLong(data.get("TRG_DISPLAY_ORB").toString()));
		 }
		 if(data.get(TRG_DISPLAY_NUMBERS)!=null && data.get(TRG_DISPLAY_NUMBERS).toString().equals(ProductDBConstant.ONE_STRING)) { 
			 oPosKvs.setDisplayNumbersInsteadOfModifiers(1L);
		} else if(data.get("TRG_DISPLAY_NUMBERS")!=null && data.get("TRG_DISPLAY_NUMBERS").toString().equals(ProductDBConstant.TWO_STRING)) {
			oPosKvs.setDisplayNumbersInsteadOfModifiers(2L);
		 } else if(data.get("TRG_DISPLAY_NUMBERS")!=null && data.get("TRG_DISPLAY_NUMBERS").toString().equals(ProductDBConstant.THREE_STRING)) {
			 oPosKvs.setDisplayNumbersInsteadOfModifiers(3L);
		 }
		 
		 if(data.get(KVS_SHOW_ON_MAIN)!=null && Integer.parseInt(data.get(KVS_SHOW_ON_MAIN).toString())==1) { 
			 oPosKvs.getKvsDisplay().add(new GenericEntry(1L,SHOW_ON_MAIN));
		 } else if (data.get(KVS_SHOW_ON_MAIN)!=null) {
			 oPosKvs.getKvsDisplay().add(new GenericEntry(0L,SHOW_ON_MAIN));
		 }else if (isMaster){
			 oPosKvs.getKvsDisplay().add(new GenericEntry(0L,SHOW_ON_MAIN));
		 }
		 
		 if(data.get(KVS_DN_DCMP)!=null && Integer.parseInt(data.get(KVS_DN_DCMP).toString())==1) { 
			 oPosKvs.getKvsDisplay().add(new GenericEntry(1L,DO_NOT_DECOMP_VM));
		 } else if (data.get(KVS_DN_DCMP)!=null) {
			 oPosKvs.getKvsDisplay().add(new GenericEntry(0L,DO_NOT_DECOMP_VM));
		 }else if (isMaster){
			 oPosKvs.getKvsDisplay().add(new GenericEntry(0L,DO_NOT_DECOMP_VM));
		 }
		 
		 if(data.get(KVS_SHOW_ON_MFY)!=null && Integer.parseInt(data.get(KVS_SHOW_ON_MFY).toString())==1) { 
			 oPosKvs.getKvsDisplay().add(new GenericEntry(1L,SHOW_ON_MFY));
		 } else if (data.get(KVS_SHOW_ON_MFY)!=null) {
			 oPosKvs.getKvsDisplay().add(new GenericEntry(0L,SHOW_ON_MFY));
		 }else if (isMaster){
			 oPosKvs.getKvsDisplay().add(new GenericEntry(0L,SHOW_ON_MFY));
		 }
		 
		 if(data.get(KVS_SHOW_ON_SUMR)!=null && Integer.parseInt(data.get(KVS_SHOW_ON_SUMR).toString())==1) { 
			 oPosKvs.getKvsDisplay().add(new GenericEntry(1L,SHOW_ON_SUMMARY));
		 } else if (data.get(KVS_SHOW_ON_SUMR)!=null) {
			 oPosKvs.getKvsDisplay().add(new GenericEntry(0L,SHOW_ON_SUMMARY));
		 }else if (isMaster){
			 oPosKvs.getKvsDisplay().add(new GenericEntry(0L,SHOW_ON_SUMMARY));
		 }
		 
		 if(data.get("MUTUL_XCLU")!=null) { 
			 oPosKvs.setMutuallyExclusive(data.get("MUTUL_XCLU").toString());
		 }
		 		 
		 if(data.get("BUN_NU")!=null) oPosKvs.setBunPrepTypeID(data.get("BUN_NU").toString());		 
		 
		 oPosKvs.setDisplayAs(addNewEntry(data,"DSPL_PRD_CD"));	
		 oPosKvs.setDisplayAsID(addNewEntry(data,"DSPL_PRD_ID"));	
		 if(data.get("AUTO_GRLL_SLIP")!=null) oPosKvs.setAutoGrill(Long.parseLong(data.get("AUTO_GRLL_SLIP").toString()));		 
		 if(data.get("PRMO_PER_ITM_QTY")!=null) oPosKvs.setPromoPerItemQuantityLimit(Long.parseLong(data.get("PRMO_PER_ITM_QTY").toString()));
		 if(data.get("USE_BFFR_ENG")!=null) oPosKvs.setIncludedInBufferEngine(Long.parseLong(data.get("USE_BFFR_ENG").toString()));
		 if(data.get("PRI_PRD_ID")!=null) oPosKvs.setPriority(data.get("PRI_PRD_ID").toString());
		 if(data.get(SMRT_RMDR)!=null) oPosKvs.setSmartReminderGroup(addNewEntry(data,SMRT_RMDR));
		 if(data.get("PRD_SRC_SUB_ITM_VAL") !=null) oPosKvs.setSourceSubstitutionItems(addNewEntryList(data,"PRD_SRC_SUB_ITM_VAL"));
		 if(data.get("PRTY")!=null) oPosKvs.setSmrtReminderPriority(data.get("PRTY").toString());
		 
		 if(data.get("PRD_BRCD")!=null) oPosKvs.setMenuItemBarcode(data.get("PRD_BRCD").toString());
		 if(data.get("DSPL_WSTE")!=null) oPosKvs.setDisplayWaste(data.get("DSPL_WSTE").toString());	
		 if(data.get("UPSZBL")!=null) oPosKvs.setUpsizable(data.get("UPSZBL").toString());
		 if(data.get("DSPL_ORD")!=null) oPosKvs.setDisplayOrder(Long.parseLong(data.get("DSPL_ORD").toString()));	
		 if(data.get("SLBL")!=null) oPosKvs.setSalable(data.get("SLBL").toString());	
		 if(data.get("GRLBL")!=null) oPosKvs.setGrillable(Long.parseLong(data.get("GRLBL").toString()));
		 
		 if(data.get("GRLBL_PRNT")!=null) oPosKvs.setPrintGrillSlip(Long.parseLong(data.get("GRLBL_PRNT").toString()));
		 
		 if(data.get("NGABS_CD")!=null) oPosKvs.setnGABSCode(data.get("NGABS_CD").toString());
		 if(data.get(DRNK_VOL_ID)!=null) oPosKvs.setDrnkVolId(Long.parseLong(data.get(DRNK_VOL_ID).toString()));
		 if(data.get("GRP_BUNDLE")!=null) oPosKvs.setGrpBundle(Long.parseLong(data.get("GRP_BUNDLE").toString()));
		 if(data.get("GRP_BUNDLE_LIMIT")!=null) oPosKvs.setGrpBundleLimit(Long.parseLong(data.get("GRP_BUNDLE_LIMIT").toString()));
		 if(data.get(AUTO_GRLL_CNFG)!=null) oPosKvs.setAutoGrillConf(Long.parseLong(data.get(AUTO_GRLL_CNFG).toString()));
		 if(data.get("GRL_GRP")!=null) oPosKvs.setGrillGroup(Long.parseLong(data.get("GRL_GRP").toString()));
	
	
		 oPosKvs.setAutoCondiment(addNewEntryList(data,"ACD_VAL"));
		 if(data.get("DIS_GRL_INFO")!=null) { 
			 oPosKvs.setDisplayGrillInstructions(data.get("DIS_GRL_INFO").toString());
		 }
		 oPosKvs.setBunBufferConfiguration(addNewEntry(data,"BUN_BFFR_CNF"));
		 oPosKvs.setProductionMenuItemGroup(addNewEntry(data,"PROD_PRD_GRP"));
		 if (data.get("PROD_PRD_GRP_NA") != null && oPosKvs.getProductionMenuItemGroup() != null){
			oPosKvs.getProductionMenuItemGroup().setName(data.get("PROD_PRD_GRP_NA").toString());
		 }
		 oPosKvs.setTargetSubstitutionItem(addNewEntry(data,"TRG_SUB_PRD_ID"));
		 if(data.get(PRD_UNT)!=null) {
			 oPosKvs.setMenuItemUnit(data.get(PRD_UNT).toString());
		 }
		 oPosKvs.setDynamicGrillConfiguration(addNewEntry(data,AUTO_GRLL_CNFG));		 
		 if (data.get("EQVLNT_PRD_ID") != null) {
		 	oPosKvs.setEquivalent(addNewEntry(data,"EQVLNT_PRD_CD"));
		}
		 oPosKvs.setSellLocation(addNewEntryList(data,"POD_VAL"));
		 oPosKvs.setPromotionComplement(addNewEntryList(data,"PROMO_COMPLI_VAL"));
		 oPosKvs.setMenuTypAssValue(addNewEntryList(data,"MENUTYP_ASS_VAL"));
		 oPosKvs.setSspClassification(addNewEntryList(data,"SSP_VAL")); 	  			 
		 oPosKvs.setDeposit(addNewEntry(data,"DPST_ID"));

		 if(!oPosKvs.getAutoCondiment().isEmpty()) {
			 oPosKvs.setHasAutoCondiment(1L);
		 } else {
			 oPosKvs.setHasAutoCondiment(2L);
		 }		 		 
		 if(data.get("RTM_TYPE")!=null)  oPosKvs.setRtmType(data.get("RTM_TYPE").toString());
		 if(data.get("RTM_TYPE_DESC")!=null) oPosKvs.setRtmTypeDesc(data.get("RTM_TYPE_DESC").toString());
		 if(data.get("RTM_THRESHOlD")!=null) oPosKvs.setRtmThreshold(Long.parseLong(data.get("RTM_THRESHOlD").toString()));	
		 if(data.get("RTM_AUTOBP_TMT")!=null) oPosKvs.setRtmAutoBumpTimeout(Long.parseLong(data.get("RTM_AUTOBP_TMT").toString()));	
		 if(data.get("RTM_IMG")!=null) oPosKvs.setRtmImage(addNewEntry(data,"RTM_IMG"));	
		 if(data.get("RTM_PRIORITY")!=null) oPosKvs.setRtmPriority(Long.parseLong(data.get("RTM_PRIORITY").toString()));	
		 if(data.get("ROUTE_GRILL_ONLY")!=null) oPosKvs.setRouteGrillOnly(Long.parseLong(data.get("ROUTE_GRILL_ONLY").toString()));	
		 if(data.get("DEDICATE_CELL")!=null) oPosKvs.setDedicatedCell(Long.parseLong(data.get("DEDICATE_CELL").toString()));	
		 if(data.get("GRILL_CELL_RANGE_START")!=null) oPosKvs.setGrillCellRangeStart(Long.parseLong(data.get("GRILL_CELL_RANGE_START").toString()));	
		 if(data.get("GRILL_CELL_RANGE_END")!=null) oPosKvs.setGrillCellRangeEnd(Long.parseLong(data.get("GRILL_CELL_RANGE_END").toString()));	
		 
		 if(data.get("auto_cmdt")!=null) oPosKvs.setAutoCondimentDisplay(Long.parseLong(data.get("auto_cmdt").toString()));
		 if(data.get(TAGS_DATA)!=null)  oPosKvs.setSelectedTags(addNewEntryList(data, TAGS_DATA));
		 if(data.get("disply_pos_evnt")!=null) oPosKvs.setDisplyPosEvent(data.get("disply_pos_evnt").toString());
		 if(data.get("disp_dtwt_pos_event")!=null) oPosKvs.setDisplayDTWposEvent(data.get("disp_dtwt_pos_event").toString());
		 if(data.get("sale_typ")!=null)oPosKvs.setApplySalesType(Arrays.asList(data.get("sale_typ").toString().split(",")));
		 
		 return oPosKvs;
	}
	
	private ProductSmartRouting mapProductSmartRouting(Map<String, Object> data) {
		ProductSmartRouting oSmartRouting = new ProductSmartRouting();
		if(data.get("CYT_ITEM")!=null) oSmartRouting.setCytItem(data.get("CYT_ITEM").toString());
		if(data.get("CYT_IGNT_TYP")!=null) oSmartRouting.setCytIngredientTyp(data.get("CYT_IGNT_TYP").toString());
		if(data.get("CYTINGREDIENTGROUP")!=null) oSmartRouting.setCytIngredientGroup(data.get("CYTINGREDIENTGROUP").toString());
		if(data.get("COOK_TM")!=null) oSmartRouting.setCookTime(Long.valueOf(data.get("COOK_TM").toString()));		
		if(data.get("DRES_PREP_TM")!=null) oSmartRouting.setDressPrepTime(Long.valueOf(data.get("DRES_PREP_TM").toString()));
		if(data.get("AVLB_ELY_DEL")!=null && data.get("AVLB_ELY_DEL").toString().equals("1")){
			if (data.get("AVLB_ELY_DEL_DESC") != null ) {
				oSmartRouting.setDeliverEarlyEnabled(data.get("AVLB_ELY_DEL_DESC").toString());
		}

		}
		return oSmartRouting;
	}
	
	private ProductCCMSettings mapProductCCMSettings(Map<String, Object> data) {
		ProductCCMSettings productCCMSettings=new ProductCCMSettings();
		if(data.get("autoBundlingPriority")!=null) productCCMSettings.setAutoBundlingPriority(Long.parseLong(data.get("autoBundlingPriority").toString()));
		if(data.get("reduction")!=null) productCCMSettings.setReduction(getReductionList(data.get("reduction").toString()));
		if(data.get("prc_mtd")!=null) productCCMSettings.setPricingMethod(data.get("prc_mtd").toString());
		if(data.get("ccm_ff_priority")!=null) productCCMSettings.setCcmFFpriority(Long.parseLong(data.get("ccm_ff_priority").toString()));
		return productCCMSettings;
	}
	
	private List<Reduction> getReductionList(String reductionVal){
		List<Reduction> list=new ArrayList<>();
		String []qtyRtList=reductionVal.split(",");
		for(int i=0;i<qtyRtList.length;i++)
		{
			Reduction reduction=new Reduction();
			reduction.setQty(Double.valueOf(qtyRtList[i]).longValue());
			i=i+1;
			reduction.setRate(Double.parseDouble(qtyRtList[i]));
			list.add(reduction);
		}
		return list;
	}
	
	
	

	/**
	* Returns the merged map with Pos KVS / Presentation of specific SET  
	* @param	source - Hashmap with master level source
	* @param	marketId - market number
	* @param	effectiveDate - effective date
	* @param	setType - set type 3001-MIS 3003-RMI  
	* @param    setId - set id 
	* @return   Map<Long, Product>
	*/
	public Map<Long, Product>  getProductPosKvsPresentationBySet(Map<Long, Product> source, long marketId,  String effetiveDate, long setType, long setId) throws Exception {
		final String query = getDaoXml(GET_MENU_ITEM_POSKVS_PRESENTATION, DAOResources.PRODUCT_DB_DAO);
		
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  setId);		
		paramMap.put("type", setType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effetiveDate) );
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemPOSKVSPresentation");
		
		for ( final Map<String, Object> data : listOfResults ) {
			 Product productDTO = new Product();
		 
			 productDTO.setProductId(Long.parseLong(data.get(PRD_ID).toString()));
			 
			 populatePromoRangeAttributes(data,productDTO);
			 	
			 	if (productDTO.getPrmoMenuItem()!=null) {
					BeanUtils.copyProperties(productDTO.getPrmoMenuItem(), source.get(productDTO.getProductId()).getPrmoMenuItem(), getNullPropertyNames(productDTO.getPrmoMenuItem()));
				}
				
				if (productDTO.getPrmoChoice()!=null) {
					BeanUtils.copyProperties(productDTO.getPrmoChoice(), source.get(productDTO.getProductId()).getPrmoChoice(), getNullPropertyNames(productDTO.getPrmoChoice()));
				}
			 
			 
				if (productDTO.getPromoStartDate()!=null) {
					BeanUtils.copyProperties(productDTO.getPromoStartDate(), source.get(productDTO.getProductId()).getPromoStartDate(), getNullPropertyNames(productDTO.getPromoStartDate()));
				}
				
				
				if (productDTO.getPromoStartDate()!=null) {
					BeanUtils.copyProperties(productDTO.getPromoEndDate(), source.get(productDTO.getProductId()).getPromoEndDate(), getNullPropertyNames(productDTO.getPromoEndDate()));
				}
			 
			 ProductPresentation oPresentation = mapProductPresentation(data);			 			 
			 productDTO.setProductPresentation(oPresentation);						 
			 ProductPresentation oSourcePresentation =  source.get(productDTO.getProductId()).getProductPresentation();		 
			 BeanUtils.copyProperties(oPresentation, oSourcePresentation, getNullPropertyNames(oPresentation));
			 
			 ProductPosKvs oPosKvs = mapProductPosKvs(data, false);
			 productDTO.setProductPosKvs(oPosKvs);			 
			 ProductPosKvs oSourceProductPosKvs =  source.get(productDTO.getProductId()).getProductPosKvs();		 

			 BeanUtils.copyProperties(oPosKvs, oSourceProductPosKvs, getNullPropertyNames(oPosKvs));
			 
			 ProductSmartRouting oSmartRouting=mapProductSmartRouting(data);
			 productDTO.setProductSmartRouting(oSmartRouting);	
			 ProductSmartRouting oSourceSmartRouting =  source.get(productDTO.getProductId()).getProductSmartRouting();	
			 BeanUtils.copyProperties(oSmartRouting, oSourceSmartRouting, getNullPropertyNames(oSmartRouting));
			 
			 ProductCCMSettings oProductCCMSettings=mapProductCCMSettings(data);
			 productDTO.setProductCCMSettings(oProductCCMSettings);
			 ProductCCMSettings oSourceproductCCMsettings =  source.get(productDTO.getProductId()).getProductCCMSettings();	
			 BeanUtils.copyProperties(oProductCCMSettings, oSourceproductCCMsettings, getNullPropertyNames(oProductCCMSettings));
		
			 ProductAbsSettings abssettings = mapProductAbsSetting(data);
			 productDTO.setProductAbsSettings(abssettings);			 
			 ProductAbsSettings oSourceProductabssettings =  source.get(productDTO.getProductId()).getProductAbsSettings();		 
			 BeanUtils.copyProperties(abssettings, oSourceProductabssettings, getNullPropertyNames(abssettings));
			 
			 populateGenralSetting(data, productDTO);
			 populateMenuTypeAvailable(data,productDTO);
			 populateProductTimeAvailableForSale(data,productDTO);
			 populateDeliveryFeeDetails( data,productDTO);
			 
			 copyGeneralsettingProperties(productDTO, source.get(productDTO.getProductId()));
			 
		}	
		 createSmartRoutingTask(source,paramMap);

		return source;
	}	


	/**
	* remove entry null names from merge process   
	* @param	source - object
	* @return   retrun a string array
	*/
	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    java.util.Set<String> emptyNames = new HashSet<>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	        if (srcValue instanceof GenericEntry && ((GenericEntry) srcValue).getCode() == 0) emptyNames.add(pd.getName()); 
	        if (srcValue instanceof ArrayList && ((List<?>) srcValue).isEmpty()) emptyNames.add(pd.getName()); 
	    }

	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}

	/**
	* consider only entry null names for merge process
	* @param	source - object
	* @return   retrun a string array
	*/
	public static String[] getNotNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    java.util.Set<String> nonEmptyNames = new HashSet<>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue != null) nonEmptyNames.add(pd.getName());
	    }

	    String[] result = new String[nonEmptyNames.size()];
	    return nonEmptyNames.toArray(result);
	}
	
	/**
	* transform a value in an generic entry   
	* @param	data - record retrieve by database query
	* @param	pField - name of field to be retrieved
	* @return   generic entries object
	*/
	public GenericEntry addNewEntry(Map<String, Object> data, String pField) {
		GenericEntry ge = new GenericEntry();
		if(data.get(pField)!=null) {
			if(pField.equals(PRD_UNT)) {
				ge.setName(data.get(pField).toString());
			}else
			 ge.setCode(Long.parseLong(data.get(pField).toString()));
		}
		return ge;
	}


	/**
	* transform a string with values separate by comma to a list og genericEntry  
	* @param	data - record retrieve by database query
	* @param	pField - name of field to be retrieved
	* @return   list of generic entries
	*/
	public List<GenericEntry> addNewEntryList(Map<String, Object> data, String pField) {
		List<GenericEntry> list = new ArrayList<>();
		if(data.get(pField)!=null) {
			if(data.get(pField).toString().length() > 0 ) {
				String[] items = data.get(pField).toString().split(",");
				for(String item : items) {
					if(pField.equals("POD_VAL") || pField.equals("SSP_VAL") ||  pField.equals("ACD_VAL") ||  pField.equals(TAGS_DATA) ) {
						 list.add(new GenericEntry(1L,item));
					} else 
					 list.add(new GenericEntry(Long.parseLong(item), ""));
				}
			}
		}
		return list;
	}
	
	/**
	* Returns the MI Set and Price SET of specific Node  
	* @param	marketId - market number
	* @param	nodeID - node id, each restaurant has a specific node
	* @param	effectiveDate - effective date  
	* @param    type - type of set 
	* @return   List<Set>
	*/
	public List<Set>  getRestaurantSets(long marketId, long nodeId, String effetiveDate, long type) throws Exception {
		final String query = getDaoXml("retrieveRestaurantSetsId", DAOResources.COMMON_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MARKETID, marketId);
		paramMap.put("nodeId", nodeId);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effetiveDate) );
		paramMap.put("setType", type);
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "retrieveRestaurantSetsId");
		
		List<Set> listOfSets = new ArrayList<>();						 
		for ( final Map<String, Object> data : listOfResults ) {
			 Set setDTO = new Set();
			 setDTO.setSetId(Long.parseLong(data.get("chld_set_id").toString()));			 
			 setDTO.setType(type);		 		 
			 listOfSets.add(setDTO);
		}		
		return listOfSets;
	}
	
	
	/**
	* Returns the source map with status of master level  
	* @param	marketId - market number
	* @param	effectiveDate - effective date 
	* @param    masterSetId - set id 
	* @return   Map<Long, Product>
	*/
	public Map<Long, Product> getProductStatusByMaster(long marketId, String effectiveDate, Long masterSetId) throws Exception {
		final String query = getDaoXml("getProductStatusByMaster", DAOResources.PRODUCT_DB_DAO);
		
		Map<Long, Product> listOfProduct  = new HashMap<>();					 

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  masterSetId);		
		paramMap.put("type", ProductDBConstant.MMI_SET_TYPE);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate) );
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getProductStatusByMaster");
		
		for ( final Map<String, Object> data : listOfResults ) {
			 Product productDTO = new Product();		 
			 productDTO.setProductId(Long.parseLong(data.get(PRD_ID).toString()));			 			 			 
			 if(data.get("STUS")!=null) productDTO.setActive(Integer.parseInt(data.get("STUS").toString()));		 			 			 	 			 			 			 			 
			 listOfProduct.put(productDTO.getProductId(), productDTO); 
		}		
		return listOfProduct;
	}

	/**
	* Returns the source map with approval status of master level  
	* @param	marketId - market number
	* @param	effectiveDate - effective date 
	* @param    masterSetId - set id 
	* @return   Map<Long, Product>
	*/
	public Map<Long, Product> getProductApprovalStatusByMaster(long marketId, String effectiveDate, Long masterSetId, String menuItemDefaultStatus) throws Exception {
		final String query = getDaoXml("getProductApprovalStatusByMaster", DAOResources.PRODUCT_DB_DAO);
			
		Map<Long, Product> listOfProduct  = new HashMap<>();					 

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate) );
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getProductApprovalStatusByMaster");
		
		for ( final Map<String, Object> data : listOfResults ) {
			 Product productDTO = new Product();		 
			 productDTO.setProductId(Long.parseLong(data.get(PRD_ID).toString()));			 			 			 
			 if(data.get("STUS")!=null)  productDTO.setApprovalStatus(Integer.parseInt(data.get("STUS").toString()));
			if (menuItemDefaultStatus != null) productDTO.setActive(Integer.parseInt(menuItemDefaultStatus));
			 listOfProduct.put(productDTO.getProductId(), productDTO); 
		}		
		return listOfProduct;
	}

	/**
	* Returns the merged map with status of specific SET  
	* @param	source - Hashmap with master level source
	* @param	marketId - market number
	* @param	effectiveDate - effective date
	* @param	setType - set type 3001-MIS 3003-RMI  
	* @param    setId - set id 
	* @return   Map<Long, Product>
	*/
	public Map<Long, Product> getProductStatusBySet(Map<Long, Product> source, long marketId, String effectiveDate,
			long setType, long setId,Long layeringTyp) throws Exception {
		final String query = getDaoXml("getProductStatusBySet", DAOResources.PRODUCT_DB_DAO);
		
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  setId);		
		paramMap.put("type", setType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate) );
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getProductStatusBySet");
		
		for ( final Map<String, Object> data : listOfResults ) {			 			 
			 if(data.get("STUS")!=null) {				 		 	
			 	 Product oSource =  source.get(Long.parseLong(data.get(PRD_ID).toString()));
			 	 
			 	 if(oSource != null) {
			 		 //if any menuItem is inactive at master then  can't activate on set,restaurant level.
			 		 if(layeringTyp.equals(2L)&&oSource.getActive()==1&&Integer.parseInt(data.get("STUS").toString())==0)
			 		 oSource.setActive(Integer.parseInt(data.get("STUS").toString()));
			 		else if(layeringTyp.equals(1L))
			 			oSource.setActive(Integer.parseInt(data.get("STUS").toString()));
			 	 } else {
			 		 if(data.get(PRD_ID)!=null) {
				 		 Product oNewProduct = new Product();
				 		 oNewProduct.setProductId(Long.parseLong(data.get(PRD_ID).toString()));
				 		 oNewProduct.setActive(Integer.parseInt(data.get("STUS").toString()));
				 		 source.put(oNewProduct.getProductId(), oNewProduct);
			 		 }
			 	 }
			 }
		}		
		return source;		
	}

	/**
	* Returns the merged map with approval status of specific SET  
	* @param	source - Hashmap with master level source
	* @param	marketId - market number
	* @param	effectiveDate - effective date
	* @param	setType - set type 3001-MIS 3002-MMI 3003-RMI 
	* @param    setId - set id 
	* @return   Map<Long, Product>
	*/
	public  Map<Long, Product>  getProductApprovalStatusBySet(Map<Long, Product> source, long marketId,
			String effectiveDate, long setType, long setId) throws Exception {
		final String query = getDaoXml("getProductApprovalStatusBySet", DAOResources.PRODUCT_DB_DAO);
		
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  setId);		
		paramMap.put("type", setType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate) );
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getProductApprovalStatusBySet");
		
		for ( final Map<String, Object> data : listOfResults ) {
			 if(data.get("STUS")!=null) {


				 Product oSource =  source.get(Long.parseLong(data.get(PRD_ID).toString()));
				 if (oSource!=null) {
					 oSource.setApprovalStatus(Integer.parseInt(data.get("STUS").toString()));	
				}
					 					 	 


			 }
		}		
		return source;		
	}


	
	/**
	* Returns if the market use the dual status STATUS and APPROVAL STATUS  
	* @param  marketId 
	* @return      True - Dual Status, False - Single Status
	*/
	public boolean getMarketMenuItemDualStatus(long marketId) throws Exception {
		boolean dualStatus = false;
		final String query = getDaoXml("retrieveMarketMenuItemDualStatus", DAOResources.COMMON_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);		
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "retrieveMarketMenuItemDualStatus");
										
		for ( final Map<String, Object> data : listOfResults ) {			 
			 if(data.get("PARAM_VALUE").toString().equals("1")) {
				 dualStatus = true;
			 }
		}		
		return dualStatus;
	}
	
	
	
	private ProductAbsSettings mapProductAbsSetting(Map<String, Object> data) {		 
		ProductAbsSettings absSettings = new ProductAbsSettings();
		if(data.get("CUP_SIZE_CD")!=null) absSettings.setCupSize(data.get("CUP_SIZE_CD").toString());
		if(data.get("FLAV_CD")!=null) absSettings.setFlavor(Long.parseLong(data.get("FLAV_CD").toString()));
		if(data.get("LID_OPT_CD")!=null) absSettings.setLidOption(data.get("LID_OPT_CD").toString());
		if(data.get("ICE_SEL_CD")!=null) absSettings.setIceSelection(data.get("ICE_SEL_CD").toString());
		if(data.get("ICE_PRD_DEFN_CD")!=null) absSettings.setIceProductDef(data.get("ICE_PRD_DEFN_CD").toString());
		if(data.get(DRNK_VOL_ID)!=null) absSettings.setNgabsVol(Long.parseLong(data.get(DRNK_VOL_ID).toString()));
		return absSettings;
	}


	public Map<Long, Product> getProductParametersByMaster(long marketId,  String effetiveDate, long masterSetID) throws Exception {
    
		final String query = getDaoXml(GET_MENU_ITEM_PARAMETER_LIST, DAOResources.PRODUCT_DB_DAO);
		Map<Long, Product> listOfProduct  = new HashMap<>();	
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  masterSetID);		
		paramMap.put("type", ProductDBConstant.MMI_SET_TYPE);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effetiveDate));
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemParameterList");
		for ( final Map<String, Object> data : listOfResults ) {
			if(data.get(P_VALUE) == null) {
				continue;
			}
			
			 Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> {
				 Product p=new Product();
				 p.setProductId(key);
				 return p;
			 });
			 List<Parameter> parameters=productDTO.getParameters();
			 if(parameters==null) {
				 parameters=new ArrayList<>();
			 }
			 parameters.add(addParameter(data));
			 productDTO.setParameters(parameters);
			 }
		return listOfProduct;
	}
	
	public Map<String, List<Parameter>> getMarketCustomParameters(long marketId) throws Exception {
		final String query = getDaoXml("getMarketCustomParameters", DAOResources.PRODUCT_DB_DAO);
		Map<String, List<Parameter>> listOfParameters = new HashMap<>();	
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMarketCustomParameters");
		for (final Map<String, Object> data : listOfResults ) {
			if(data.get(P_VALUE) == null) {
				continue;
			}
				 Parameter parameter = new Parameter();
				 parameter = addParameter(data);
				 if (parameter.getValue() != null) {
					 if (!listOfParameters.containsKey(parameter.getParmClass())) {
						 List <Parameter> arrayParam = new ArrayList<>();
						 arrayParam.add(parameter);
						 listOfParameters.put(parameter.getParmClass(), arrayParam);
					 }else {
						 listOfParameters.get(parameter.getParmClass()).add(parameter);
					 }
				 }
			 }
		return listOfParameters;
	}

	private Parameter addParameter(Map<String, Object> data) {
		Parameter parameter=new Parameter();
		if(data.get(PARM_ID)!=null) {
			parameter.setParamId(Long.parseLong(data.get(PARM_ID).toString()));
		}
		if(data.get("pname")!=null) {
			parameter.setName(data.get("pname").toString());
		}
		if(data.get(P_VALUE)!=null) {
			parameter.setValue(data.get(P_VALUE).toString());
		}
		if(data.get("lex_id")!=null) {
			parameter.setLexId(Long.parseLong(data.get("lex_id").toString()));
		}
		if(data.get("data_typ")!=null) {
			parameter.setDataType(Long.parseLong(data.get("data_typ").toString()));
		}
		if(data.get("prd_cls_id")!=null) {
			parameter.setParmClass(data.get("prd_cls_id").toString());
		}
		return parameter;
	}
	
	public boolean containsParm(final List<Parameter> list, final String parmId){
	    return list.stream().anyMatch(o -> o.getName().equals(parmId));
	}
	
	public Map<Long, Product> getProductParametersBynBySet(Map<Long, Product> source, long marketId,
			String effetiveDate, long setType, long setId) throws Exception {
		final String query = getDaoXml(GET_MENU_ITEM_PARAMETER_LIST, DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID, setId);
		paramMap.put("type", setType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effetiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemParameterList");
		for (final Map<String, Object> data : listOfResults) {
			Parameter parameter = addParameter(data);
			List<Parameter> sourceParameters = source.get(Long.parseLong(data.get(PRD_ID).toString())).getParameters();
			boolean isNewattribute = false;
			for (int i = 0; i < sourceParameters.size(); i++) {
				if (sourceParameters.get(i).getParamId().equals(Long.parseLong(data.get(PARM_ID).toString()))) {
					BeanUtils.copyProperties(parameter, sourceParameters.get(i),getNullPropertyNames(parameter));
					isNewattribute = true;
				}
			}
			if (!isNewattribute) {
				sourceParameters.add(parameter);
			}
		}
		return source;
	}


	public Map<Long, Product> getProductGeneralSettingMenuItemNames(Map<Long, Product> listOfProduct,long marketId, String effectiveDate,
			long setType,Long setId) throws Exception {
		final String query = getDaoXml("getGeneralSettingMenuNamesDetails", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  setId);	
		paramMap.put("type", setType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getGeneralSettingMenuNamesDetails");
		for ( final Map<String, Object> data : listOfResults ) {
			 Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> {
				 Product p=new Product();
				 p.setProductId(key);
				 return p;
			 });
			 if(productDTO.getProductGeneralSettingNamesList()==null) {
				 productDTO.setProductGeneralSettingNamesList(new ArrayList<>());
			 }
			 mapProductGeneralsetting(productDTO.getProductGeneralSettingNamesList(),data);
			 listOfProduct.put(productDTO.getProductId(), productDTO);
			 }
		return listOfProduct;
	}

	private void mapProductGeneralsetting(List<ProductGeneralSettingMenuItemNames> productGeneralSettingList, Map<String, Object> data) {
		
		long langId = Long.parseLong(data.get(LANG_ID).toString());
		String deviceNa =data.get(PRD_DVC_NA).toString();
		Optional<ProductGeneralSettingMenuItemNames> generalSettingOp = productGeneralSettingList.stream().filter(e -> e.getLangId() == langId && e.getDeviceId().equals(deviceNa)).findAny();
		ProductGeneralSettingMenuItemNames generalSetting = null;
		if (generalSettingOp.isEmpty()) {
			generalSetting = new ProductGeneralSettingMenuItemNames();
			if(data.get(LANG_ID)!=null) generalSetting.setLangId(Long.parseLong(data.get(LANG_ID).toString()));
			if(data.get(PRD_DVC_NA)!=null) generalSetting.setDeviceId(data.get(PRD_DVC_NA).toString());
			productGeneralSettingList.add(generalSetting);
		} else {
			generalSetting = generalSettingOp.get();
		}
		
		if(data.get("LNG_NA")!=null) generalSetting.setLongName(data.get("LNG_NA").toString());
		if(data.get("SHRT_NA")!=null) generalSetting.setShortName(data.get("SHRT_NA").toString());		
		if(data.get("DRV_NA")!=null) generalSetting.setDtName(data.get("DRV_NA").toString());
		if(data.get("SUMR_MNIT_NA")!=null) generalSetting.setSummaryMonitorName(data.get("SUMR_MNIT_NA").toString());
		if(data.get("SHRT_MNIT_NA")!=null) generalSetting.setShortMonitorName(data.get("SHRT_MNIT_NA").toString());
		if(data.get("CSO_NA")!=null) generalSetting.setCsoName(data.get("CSO_NA").toString());
		if(data.get("CSO_SIZE_NA")!=null) generalSetting.setCsoSizeName(data.get("CSO_SIZE_NA").toString());
		if(data.get("CSO_GEN_NA")!=null) generalSetting.setCsoGenericName(data.get("CSO_GEN_NA").toString());		
		if(data.get("COD_NA")!=null) generalSetting.setCodName(data.get("COD_NA").toString());
		if(data.get("ALT_NA")!=null) generalSetting.setAlternativeName(data.get("ALT_NA").toString());
		if(data.get("HOME_DELIVERY_NA")!=null) generalSetting.setHomeDeliveryname(data.get("HOME_DELIVERY_NA").toString());
		if(data.get(SMRT_RMDR)!=null) generalSetting.setSmartReminder(data.get(SMRT_RMDR).toString());
		if(data.get("PRMO_TX_LABL")!=null) generalSetting.setPromoTaxLabel(data.get("PRMO_TX_LABL").toString());
	}
	
	public Map<Long, Product> getProductComponentsDetails(Map<Long, Product> listOfProduct,long marketId, String effectiveDate,
			long setType,Long setId) throws Exception {		
		String query;
		String queryName;
		if(setType==3002L) {
			queryName = "getProductComponentsDetailsMaster";
		} else {
			queryName = "getProductComponentsDetails";
		}
		query = getDaoXml(queryName, DAOResources.PRODUCT_DB_DAO);
		
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  setId);	
		paramMap.put("type", setType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, queryName);
		final List<Map<String, Object>> defualtChoiceTranslatedValues = getTranslatedValuesForDefaultChoice(ProductDBConstant.DEFAULT_CHOICE_CHANNELS);
		final Map<String,String> translatedValues = new HashMap<>();
		if (!defualtChoiceTranslatedValues.isEmpty()) {
			defualtChoiceTranslatedValues.forEach(e -> 
				translatedValues.put(e.get("entr_cd").toString(), e.get("trnsltd_val").toString())
				
			);
		}
		for ( final Map<String, Object> data : listOfResults ) {
			 Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> {
				 Product p=new Product();
				 p.setProductId(key);
				 return p;
			 });
			 if(productDTO.getComponents()==null) {
				 productDTO.setComponents(new ArrayList<>());
			 }
			 mapProductComponents(productDTO,data,listOfProduct,translatedValues);
			 
			 listOfProduct.put(productDTO.getProductId(), productDTO);
			 }
		return listOfProduct;
	}

	private void mapProductComponents(Product productDTO, Map<String, Object> data,  Map<Long, Product> listOfProduct, final Map<String, String> translatedValues) {
		if (data.get("CHLD_PRD_ID") != null) {
			Long childPrdId = Long.valueOf(data.get("CHLD_PRD_ID").toString());	
				Optional<Component> componentOp = productDTO.getComponents().stream().filter(e -> e.getComponentProductId().longValue()==childPrdId.longValue()).findAny();
				Component component = null;
				if (componentOp==null || componentOp.isEmpty()) {
					component = new Component();
					component.setComponentProductId(childPrdId);
					productDTO.getComponents().add(component);
				} else {
					component = componentOp.get();
				}
				if (data.get("CPSN_TYP") != null)
					component.setCompositionType(Long.valueOf(data.get("CPSN_TYP").toString()));
				if (data.get("SEQ") != null) {
					component.setSequence(Long.valueOf(data.get("SEQ").toString()));	
				}else {
					component.setSequence(-1L);
				}
				
				if (data.get("DEF_QT") != null)
					component.setDefaultQuantity(Long.valueOf(data.get("DEF_QT").toString()));
				if (data.get("MAX_QT") != null)
					component.setMaxQuantity(Long.valueOf(data.get("MAX_QT").toString()));
				if (data.get("MIN_QT") != null)
					component.setMinQuantity(Long.valueOf(data.get("MIN_QT").toString()));
				if (data.get("RFND_THSH") != null)
					component.setRefundThreshold(Long.valueOf(data.get("RFND_THSH").toString()));
				if (data.get("CHRG_THSH") != null)
					component.setChargeThreShold(Long.valueOf(data.get("CHRG_THSH").toString()));
				if (data.get("COST_INCLSV") != null)
					component.setCostInclusive(data.get("COST_INCLSV").toString());
				if (data.get("DISP_CSO") != null)
					component.setDisplayOnCSO(data.get("DISP_CSO").toString());
				if (data.get("FRC_COMP_DSPL") != null)
					component.setForceCompositionDisplay(data.get("FRC_COMP_DSPL").toString());
				if (data.get("PLAIN_GRLL") != null)
					component.setPlainGrill(data.get("PLAIN_GRLL").toString());
				if (data.get("SMRT_GRLL") != null)
					component.setSmartGrill(data.get("SMRT_GRLL").toString());
				if (data.get("RFR_PRD_ID") != null)
					component.setReferenceProductId(Long.valueOf(data.get("RFR_PRD_ID").toString()));
				if (data.get("DEF_PRD_ID") != null)
					component.setDefaultProductId(Long.valueOf(data.get("DEF_PRD_ID").toString()));
				if (data.get("DEF_CHOICE_PRD_ID") != null) 
					component.setDefaultChoice(Long.valueOf(data.get("DEF_CHOICE_PRD_ID").toString()));
				if (data.get("DEF_CHOICE_CHN") != null) {
					String values = data.get("DEF_CHOICE_CHN").toString();
					String[]  arr = values.split("[,]", 0);
					List<String> list = new ArrayList<>();
					for(String val : arr) {
						if(translatedValues.containsKey(val)) {
							list.add(translatedValues.get(val));
						}
					}
					String defaultChoiceChn = list.stream().collect(Collectors.joining("|"));
					component.setDefaultChoiceChn(defaultChoiceChn);					
				}
				if (data.get("FLEX_CHOICE") != null)
					component.setFlexibleChoice(data.get("FLEX_CHOICE").toString());
				if (data.get("PRICE_CALC_MODE") != null) {
					switch(Integer.valueOf(data.get("PRICE_CALC_MODE").toString())) {
						case 1:{
							component.setPriceCalculationMode("regular");
							break;
						}
						case 2:{
							component.setPriceCalculationMode("accumulate");
							break;
						}
						case 3:{
							component.setPriceCalculationMode("reference");
							break;	
						}
						default:{
							LOGGER.debug("Component Calculation Mode undefined");
						}				
					}
				}
				if (data.get("CCM_ANCHOR") != null)
					component.setAnchor(data.get("CCM_ANCHOR").toString());
				if (data.get("PRICE_REDUCT_RATE") != null)
					component.setPricingReductionRate(data.get("PRICE_REDUCT_RATE").toString());								
				if (data.get("NON_REQUIRED_CHOICES") != null)
					component.setNonRequiredChoices(data.get("NON_REQUIRED_CHOICES").toString());
				if (data.get("SET_PARENT_PRODUCT_OUTAGE") != null) {
					component.setSetParentProductOutage(data.get("SET_PARENT_PRODUCT_OUTAGE").toString());	
				}else {
					component.setSetParentProductOutage("0");
				}
					
				if (data.get("IMPACTED_MI_ON_RTM") != null)
					component.setImpactedMenuItemOnRTM(Long.valueOf(data.get("IMPACTED_MI_ON_RTM").toString()));
				if (data.get("SUBSTITUTE_ON_RTM") != null)
					component.setSubstituteOnRTM(Long.valueOf(data.get("SUBSTITUTE_ON_RTM").toString()));	
				component.setAutoCondiment(0L);				
				component.setDeleted(0);
				if (data.get("DLTD_FL") != null)
					component.setDeleted(Integer.valueOf(data.get("DLTD_FL").toString()));
		}
	}
	public List<Map<String, Object>> getTranslatedValuesForDefaultChoice(String defaultChoiceChannels) throws Exception {
		List<Map<String, Object>> getTranslatedValuesForDefaultChoice;
		final String query = getDaoXml("getTranslatedValuesForDefaultChoice", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("defChoiceChn", defaultChoiceChannels);
		return getTranslatedValuesForDefaultChoice = Wizard.queryForList(dataSourceQuery, query, paramMap, "getTranslatedValuesForDefaultChoice");
	}
		
	public Map<Long, Product> getProductPromotionRangeByMaster(long marketId, String effectiveDate, Long masterSetId) throws Exception {
		final String query = getDaoXml(GET_MENU_ITEM_PROMOTION_RANGE, DAOResources.PRODUCT_DB_DAO);

		Map<Long, Product> listOfProduct = new HashMap<>();

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID, masterSetId);
		paramMap.put("type", ProductDBConstant.MMI_SET_TYPE);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemPromotionRange");
		Map<Long, Map<WeekDays, List<Pair<String, String>>>> prdTimeRestrictionMap = new HashMap<>();

		for (final Map<String, Object> data : listOfResults) {
			Map<WeekDays, List<Pair<String, String>>> timeRestrictionByWeekDay = prdTimeRestrictionMap
					.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> new HashMap<>());

			mapProductPromotionRange(data, timeRestrictionByWeekDay);

		}

		prdTimeRestrictionMap.forEach((prdId, timeRestrictionByWeekDay) -> {
			Product p = new Product();
			p.setProductId(prdId);
			List<ProductPromotionRange> timeRestrictions = new ArrayList<>();
			timeRestrictionByWeekDay.forEach((weekDay, allowedTimes) -> 
				timeRestrictions.add(new ProductPromotionRange(weekDay, allowedTimes))
			);
			p.setTimeRestrictions(timeRestrictions);
			listOfProduct.put(prdId, p);
		});

		return listOfProduct;
	}


	private void mapProductPromotionRange(Map<String, Object> data,
			Map<WeekDays, List<Pair<String, String>>> allowedTimesByWeekDay) {
		WeekDays[] weekDays = WeekDays.values();
		for (WeekDays weekDay : weekDays) {

			switch (weekDay) {
			case Monday:
				List<Pair<String, String>> mondayAllowedTimes = allowedTimesByWeekDay.computeIfAbsent(weekDay,
						key -> new ArrayList<>());
				if (data.get(MON_VLD_FL) != null && Long.parseLong(data.get(MON_VLD_FL).toString()) == 2) {
					mondayAllowedTimes.clear();
				}
				if (data.get(MON_FROM_TM) != null && data.get(MON_TO_TM) != null) {
					if (TIME_SET_00_00.equals(data.get(MON_FROM_TM).toString())
							&& TIME_SET_23_59.equals(data.get(MON_TO_TM).toString())
							&& Long.parseLong(data.get(MON_VLD_FL).toString()) == 1) {
						mondayAllowedTimes.clear();
						mondayAllowedTimes.add(new Pair<>(TIME_SET_00_00, TIME_SET_23_59));
					} else {
						mondayAllowedTimes.add(new Pair<>(data.get(MON_FROM_TM).toString(),
								data.get(MON_TO_TM).toString()));
					}
				}
				break;
			case Tuesday:
				List<Pair<String, String>> tuesdayAllowedTimes = allowedTimesByWeekDay.computeIfAbsent(weekDay,
						key -> new ArrayList<>());
				if (data.get(TUE_VLD_FL) != null && Long.parseLong(data.get(TUE_VLD_FL).toString()) == 2) {
					tuesdayAllowedTimes.clear();
				}
				if (data.get(TUE_FROM_TM) != null && data.get(TUE_TO_TM) != null) {
					if (TIME_SET_00_00.equals(data.get(TUE_FROM_TM).toString())
							&& TIME_SET_23_59.equals(data.get(TUE_TO_TM).toString())
							&& Long.parseLong(data.get(TUE_VLD_FL).toString()) == 1) {
						tuesdayAllowedTimes.clear();
						tuesdayAllowedTimes.add(new Pair<>(TIME_SET_00_00, TIME_SET_23_59));
					} else {
						tuesdayAllowedTimes.add(new Pair<>(data.get(TUE_FROM_TM).toString(),
								data.get(TUE_TO_TM).toString()));
					}
				}
				break;
			case Wednesday:
				List<Pair<String, String>> wednesdayAllowedTimes = allowedTimesByWeekDay.computeIfAbsent(weekDay,
						key -> new ArrayList<>());
				if (data.get(WED_VLD_FL) != null && Long.parseLong(data.get(WED_VLD_FL).toString()) == 2) {
					wednesdayAllowedTimes.clear();
				}
				if (data.get(WED_FROM_TM) != null && data.get(WED_TO_TM) != null) {
					if (TIME_SET_00_00.equals(data.get(WED_FROM_TM).toString())
							&& TIME_SET_23_59.equals(data.get(WED_TO_TM).toString())
							&& Long.parseLong(data.get(WED_VLD_FL).toString()) == 1) {
						wednesdayAllowedTimes.clear();
						wednesdayAllowedTimes.add(new Pair<>(TIME_SET_00_00, TIME_SET_23_59));
					} else {
						wednesdayAllowedTimes.add(new Pair<>(data.get(WED_FROM_TM).toString(),
								data.get(WED_TO_TM).toString()));
					}
				}
				break;
			case Thursday:
				List<Pair<String, String>> thursdayAllowedTimes = allowedTimesByWeekDay.computeIfAbsent(weekDay,
						key -> new ArrayList<>());
				if (data.get(THU_VLD_FL) != null && Long.parseLong(data.get(THU_VLD_FL).toString()) == 2) {
					thursdayAllowedTimes.clear();
				}
				if (data.get(THU_FROM_TM) != null && data.get(THU_TO_TM) != null) {
					if (TIME_SET_00_00.equals(data.get(THU_FROM_TM).toString())
							&& TIME_SET_23_59.equals(data.get(THU_TO_TM).toString())
							&& Long.parseLong(data.get(THU_VLD_FL).toString()) == 1) {
						thursdayAllowedTimes.clear();
						thursdayAllowedTimes.add(new Pair<>(TIME_SET_00_00, TIME_SET_23_59));
					} else {
						thursdayAllowedTimes.add(new Pair<>(data.get(THU_FROM_TM).toString(),
								data.get(THU_TO_TM).toString()));
					}
				}
				break;
			case Friday:
				List<Pair<String, String>> fridayAllowedTimes = allowedTimesByWeekDay.computeIfAbsent(weekDay,
						key -> new ArrayList<>());
				if (data.get(FRI_VLD_FL) != null && Long.parseLong(data.get(FRI_VLD_FL).toString()) == 2) {
					fridayAllowedTimes.clear();
				}
				if (data.get(FRI_FROM_TM) != null && data.get(FRI_TO_TM) != null) {
					if (TIME_SET_00_00.equals(data.get(FRI_FROM_TM).toString())
							&& TIME_SET_23_59.equals(data.get(FRI_TO_TM).toString())
							&& Long.parseLong(data.get(FRI_VLD_FL).toString()) == 1) {
						fridayAllowedTimes.clear();
						fridayAllowedTimes.add(new Pair<>(TIME_SET_00_00, TIME_SET_23_59));
					} else {
						fridayAllowedTimes.add(new Pair<>(data.get(FRI_FROM_TM).toString(),
								data.get(FRI_TO_TM).toString()));
					}
				}
				break;
			case Saturday:
				List<Pair<String, String>> satdayAllowedTimes = allowedTimesByWeekDay.computeIfAbsent(weekDay,
						key -> new ArrayList<>());
				if (data.get(SAT_VLD_FL) != null && Long.parseLong(data.get(SAT_VLD_FL).toString()) == 2) {
					satdayAllowedTimes.clear();
				}
				if (data.get(SAT_FROM_TM) != null && data.get(SAT_TO_TM) != null) {
					if (TIME_SET_00_00.equals(data.get(SAT_FROM_TM).toString())
							&& TIME_SET_23_59.equals(data.get(SAT_TO_TM).toString())
							&& Long.parseLong(data.get(SAT_VLD_FL).toString()) == 1) {
						satdayAllowedTimes.clear();
						satdayAllowedTimes.add(new Pair<>(TIME_SET_00_00, TIME_SET_23_59));
					} else {
						satdayAllowedTimes.add(new Pair<>(data.get(SAT_FROM_TM).toString(),
								data.get(SAT_TO_TM).toString()));
					}
				}
				break;
			case Sunday:
				List<Pair<String, String>> sundayAllowedTimes = allowedTimesByWeekDay.computeIfAbsent(weekDay,
						key -> new ArrayList<>());
				if (data.get(SUN_VLD_FL) != null && Long.parseLong(data.get(SUN_VLD_FL).toString()) == 2) {
					sundayAllowedTimes.clear();
				}
				if (data.get(SUN_FROM_TM) != null && data.get(SUN_TO_TM) != null) {
					if (TIME_SET_00_00.equals(data.get(SUN_FROM_TM).toString())
							&& TIME_SET_23_59.equals(data.get(SUN_TO_TM).toString())
							&& Long.parseLong(data.get(SUN_VLD_FL).toString()) == 1) {
						sundayAllowedTimes.clear();
						sundayAllowedTimes.add(new Pair<>(TIME_SET_00_00, TIME_SET_23_59));
					} else {
						sundayAllowedTimes.add(new Pair<>(data.get(SUN_FROM_TM).toString(),
								data.get(SUN_TO_TM).toString()));
					}
				}

				break;
			}
			
		}
		
		
	}


	public Map<Long, Product> getProductPromotionRangeBySet(Map<Long, Product> source, long marketId,
			String effectiveDate, long misType, long setId) throws Exception {
final String query = getDaoXml(GET_MENU_ITEM_PROMOTION_RANGE, DAOResources.PRODUCT_DB_DAO);
		
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  setId);		
		paramMap.put("type", misType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate) );
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemPromotionRange");
		
		Map<Long, Map<WeekDays, List<Pair<String, String>>>> prdTimeRestrictionMap = new HashMap<>();
		
		Map<Long, Product> listOfProductFromSet = new HashMap<>();
		
		for (final Map<String, Object> data : listOfResults) {
			Map<WeekDays, List<Pair<String, String>>> timeRestrictionByWeekDay = prdTimeRestrictionMap
					.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> new HashMap<>());

			mapProductPromotionRange(data, timeRestrictionByWeekDay);

		}

		prdTimeRestrictionMap.forEach((prdId, timeRestrictionByWeekDay) -> {
			Product p = new Product();
			p.setProductId(prdId);
			List<ProductPromotionRange> timeRestrictions = new ArrayList<>();
			timeRestrictionByWeekDay.forEach((weekDay, allowedTimes) -> 
				timeRestrictions.add(new ProductPromotionRange(weekDay, allowedTimes))
			);
			p.setTimeRestrictions(timeRestrictions);
			listOfProductFromSet.put(prdId, p);
		});
		
		for (Map.Entry<Long, Product> dbSetValues : listOfProductFromSet.entrySet()) 
		{	
			  Long key = dbSetValues.getKey();
			  Product setLevelProducts = dbSetValues.getValue();
			  Product masterLevelProducts = source.get(key); 
			
			  if (setLevelProducts.getProductId()!=null && masterLevelProducts.getProductId()==null) 
			  {
				  masterLevelProducts.setProductId(setLevelProducts.getProductId());
				BeanUtils.copyProperties(setLevelProducts.getProductPromotionRange(), masterLevelProducts.getProductPromotionRange(), getNullPropertyNames(setLevelProducts.getProductPromotionRange()));
			  }
		}
		
		return source;
	}

	/**
	* Returns the list of MI of specific restaurant
	* @param	nodeId
	* @param	marketId - market number
	* @param	effectiveDate - effective date
	* @return   List<Long>
	 * @throws Exception 
	*/
	public List<Long> retrieveRestaurantMIList(long restSetId, long nodeId, long marketId, String effectiveDate) throws Exception {				
		List<Long> listOfProduct  = new ArrayList<>();
		String setsId = Long.toString(restSetId);
		List<Set> restSetIDs = getRestaurantSets(marketId, nodeId, effectiveDate,GeneratorConstant.TYPE_MENU_ITEM_SET );		
		for(Set obj : restSetIDs) {
			setsId += "," + obj.getSetId() ;
		}
				
		final String query = getDaoXml("retrieveRestaurantMIList", DAOResources.PRODUCT_DB_DAO).replace(":setsId",setsId);
				
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);			
		paramMap.put("setType", GeneratorConstant.TYPE_MENU_ITEM_SET);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate) );
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "retrieveRestaurantMIList");
		
		for ( final Map<String, Object> data : listOfResults ) {
			 long mi = Long.parseLong(data.get("DATA_ID").toString());			 
			 listOfProduct.add(mi);			  
		}				
		return listOfProduct;
	}
	
	public Map<Long, Product> getProductShortcutSettingsByMaster(long marketId,  String effetiveDate, long masterSetID) throws Exception {
	    
		final String query = getDaoXml("getProductShortcutSettingsByRest", DAOResources.PRODUCT_DB_DAO);
		Map<Long, Product> listOfProduct  = new HashMap<>();	
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  masterSetID);		
		paramMap.put("type", ProductDBConstant.MMI_SET_TYPE);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effetiveDate));
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getProductShortcutSettingsByRest");
		for ( final Map<String, Object> data : listOfResults ) {
			 Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> {
				 Product p=new Product();
				 p.setProductId(key);
				 return p;
			 });
			 List<ProductShortCutSettings> productShortCutSettings=productDTO.getProductShortCutSettings();
			 if(productShortCutSettings==null) {
				 productShortCutSettings=new ArrayList<>();
			 }
			 productShortCutSettings.add(mapShortcutProductSettings(data));
			 productDTO.setProductShortCutSettings(productShortCutSettings);
			 }
		return listOfProduct;
	}
	
	
	public Map<String, ProductShortCutSettings> getAllShortcuts(long marketId) throws Exception {
		final String query = getDaoXml("getProductShortcutsMarketData", DAOResources.PRODUCT_DB_DAO);
		Map<String, ProductShortCutSettings> listOfShortcuts = new HashMap<>();	
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getProductShortcutsMarketData");
		for (final Map<String, Object> data : listOfResults ) {
			if(data.get(SHRTCUT_KIOSK_ID) == null && data.get(SET_ID)==null) {
				continue;
			}
			ProductShortCutSettings parameter = new ProductShortCutSettings();
				 parameter = mapShortCutSettings(data);
				 if (parameter.getKioskId() != null) {
					 if (!listOfShortcuts.containsKey(parameter.getSetId()+"_"+parameter.getKioskId())) {
						 listOfShortcuts.put(parameter.getSetId()+"_"+parameter.getKioskId(), parameter);
				 }
			 }
		}
		return listOfShortcuts;
	}
	
	private ProductShortCutSettings mapShortCutSettings(Map<String, Object> data) {
		ProductShortCutSettings parameter=new ProductShortCutSettings();
		if(data.get(SET_ID)!=null) {
			parameter.setSetId(Long.parseLong(data.get(SET_ID).toString()));
		}
		if(data.get("description")!=null) {
			parameter.setName(data.get("description").toString());
		}
		if(data.get(SHRTCUT_KIOSK_ID)!=null) {
			parameter.setKioskId(Long.parseLong(data.get(SHRTCUT_KIOSK_ID).toString()));
		}
		if(data.get("cfg")!=null) {
			parameter.setItem(getShortCutDetails(data.get("cfg").toString()));
		}
		return parameter;
	}
	
	private ProductShortCutSettings mapShortcutProductSettings(Map<String, Object> data) {
		
		ProductShortCutSettings parameter=new ProductShortCutSettings();
		if(data.get(PRD_ID)!=null) {
			parameter.setProductId(Long.parseLong(data.get(PRD_ID).toString()));
		}
		if(data.get(SHRTCUT_KIOSK_ID)!=null) {
			parameter.setKioskId(Long.parseLong(data.get(SHRTCUT_KIOSK_ID).toString()));
		}
		return parameter;
	}


	private List<ShortCutDetails> getShortCutDetails(String shortcutDetails ) {
		String []records=shortcutDetails.split(",");
		List<ShortCutDetails> details=new ArrayList<>();
		for (int i = 0; i < records.length; i++) {
			details.add(getcfgData(records[i]));
		}
		return details;
	}

	private ShortCutDetails getcfgData(String shctdtl) {
		String []records=shctdtl.split("\\:");
		ShortCutDetails shortCutDetails=new ShortCutDetails();
		shortCutDetails.setProductId(Double.valueOf(records[0]).longValue());
		shortCutDetails.setQuantity(Double.valueOf(records[1]).longValue());
		shortCutDetails.setCpsntype(Double.valueOf(records[2]).longValue());
		shortCutDetails.setLight(Double.valueOf(records[3]).longValue());
		return shortCutDetails;
	}
	
	
	public Map<Long, Product> getProductShortcutSettingsBySet(Map<Long, Product> listOfProduct, long marketId,
			String effetiveDate, long setType, long setId) throws Exception {
		final String query = getDaoXml("getProductShortcutSettingsByRest", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID, setId);
		paramMap.put("type", setType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effetiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getProductShortcutSettingsByRest");
		for (final Map<String, Object> data : listOfResults) {
			Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> {
				 Product p=new Product();
				 p.setProductId(key);
				 return p;
			 });
			 List<ProductShortCutSettings> productShortCutSettings=productDTO.getProductShortCutSettings();
			 if(productShortCutSettings==null) {
				 productShortCutSettings=new ArrayList<>();
			 }
			 productShortCutSettings.add(mapShortcutProductSettings(data));
			 productDTO.setProductShortCutSettings(productShortCutSettings);
			 listOfProduct.put(productDTO.getProductId(), productDTO);
		}
		return listOfProduct;
	}
	
	public Map<Long, Product> getProductTagsMasterSet(long marketId, String effectiveDate,
			long setType, Long setId) throws Exception {
		final String query = getDaoXml("getMenuItemTag", DAOResources.PRODUCT_DB_DAO);

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID, setId);
		paramMap.put("type", setType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemTag");
		Map<Long, Product> listOfProduct = new HashMap<>();
		for (final Map<String, Object> data : listOfResults) {
			Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> {
				Product p = new Product();
				p.setProductId(key);
				return p;
			});

			List<ProductTags> productTags = productDTO.getProductTagsList();
			if (productTags == null) {
				productTags = new ArrayList<>();
			}
			productTags.add(mapProductTags(data));
			productDTO.setProductTagsList(productTags);
		}

		return listOfProduct;
	}

	private ProductTags mapProductTags(Map<String, Object> data) {

		ProductTags productTagsSetting = new ProductTags();

		if (data.get("newpos_name") != null)
			productTagsSetting.setProductTagName(data.get("newpos_name").toString());
		if (data.get("tag_id") != null)
			productTagsSetting.setTagId(Long.valueOf(data.get("tag_id").toString()));
		if (data.get(PRD_ID) != null)
			productTagsSetting.setPrdId(Long.valueOf(data.get(PRD_ID).toString()));
		if (data.get("status") != null)
			productTagsSetting.setStatus(Long.valueOf(data.get("status").toString()));
		return productTagsSetting;
	}

	public Map<Long, Product> getProductTagsBySet(Map<Long, Product> source, long marketId, String effectiveDate,
			long misType, long setId) throws Exception {
		final String query = getDaoXml("getMenuItemTag", DAOResources.PRODUCT_DB_DAO);

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID, setId);
		paramMap.put("type", misType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemTag");
		
		for (final Map<String, Object> data : listOfResults) {
			Product productDTO = source.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()),
					key -> {
						Product p = new Product();
						p.setProductId(key);
						return p;
					});

			List<ProductTags> productTags = productDTO.getProductTagsList();
			if (productTags == null) {
				productTags = new ArrayList<>();
			}
			productTags.add(mapProductTags(data));
			productDTO.setProductTagsList(productTags);
		}

		
		return source;
	}


	public Map<Long, Product> getProductAssociatedCategoriesBySet(Map<Long, Product> source, long marketId,
			String effectiveDate, long rmiType, Long restSetId) throws Exception {
		final String query = getDaoXml("getAssociatedCategories", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  restSetId);		
		paramMap.put("type", rmiType);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
			
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAssociatedCategories");
		
		for (final Map<String, Object> data : listOfResults) {
			AssociatedCategories associatedCategories = addAssociatedCategories(data);
			List<AssociatedCategories> sourceAssociatedCategories = source.get(Long.parseLong(data.get(PRD_ID).toString())).getAssociatedCategories();
			boolean isNewattribute = false;
			for (int i = 0; i < sourceAssociatedCategories.size(); i++) {
				if (sourceAssociatedCategories.get(i).getCategory().equals(data.get(CAT_CD).toString())) {
					BeanUtils.copyProperties(associatedCategories, sourceAssociatedCategories.get(i),getNullPropertyNames(associatedCategories));
					isNewattribute = true;
				}
			}
			if (!isNewattribute) {
				sourceAssociatedCategories.add(associatedCategories);
			}
		}
		return source;
		
	}


	public Map<Long, Product> getProductAssociatedCategoriesByMaster(long marketId, String effectiveDate,
			Long masterSetId) throws Exception {
		final String query = getDaoXml("getAssociatedCategories", DAOResources.PRODUCT_DB_DAO);
		Map<Long, Product> listOfProduct  = new HashMap<>();	
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketId);
		paramMap.put(ProductDBConstant.SETID,  masterSetId);		
		paramMap.put("type", ProductDBConstant.MMI_SET_TYPE);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
		
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAssociatedCategories");
		
		for ( final Map<String, Object> data : listOfResults ) {
				Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> {
				 Product p=new Product();
				 p.setProductId(key);
				 return p;
			 });		
			List<AssociatedCategories> associatedCategories= productDTO.getAssociatedCategories();
			 if(associatedCategories==null) {
				 associatedCategories=new ArrayList<>();
			 }
			 associatedCategories.add(addAssociatedCategories(data));
			 productDTO.setAssociatedCategories(associatedCategories);
			 }

		return listOfProduct;
	}
	
	private AssociatedCategories addAssociatedCategories(Map<String, Object> data) {
		
		AssociatedCategories associatedCategory=new AssociatedCategories();
		
		if(data.get("SIZE_FL")!=null) {
			associatedCategory.setSizeFlag(Long.parseLong(data.get("SIZE_FL").toString()));
		}
		if(data.get(CAT_CD)!=null) {
			associatedCategory.setCategory(data.get(CAT_CD).toString());
		}
		
		return associatedCategory;
	}


	private void populateGenralSetting(Map<String, Object> data, Product productDTO) {

		if(data.get(PRD_CD)!=null)productDTO.setProductCode(Long.parseLong(data.get(PRD_CD).toString()));
		if(data.get("PRD_TYP")!=null)productDTO.setProductType(data.get("PRD_TYP").toString());
		if(data.get("PRD_CLAS")!=null)productDTO.setProductClass(data.get("PRD_CLAS").toString());
		if(data.get("PRD_CLAS_ID")!=null)productDTO.setProductClassId(data.get("PRD_CLAS_ID").toString());
		if(data.get("PRD_CAT")!=null)productDTO.setProductCategory(data.get("PRD_CAT").toString());
		if(data.get("PRD_CAT_ID")!=null)productDTO.setProductCategoryId(data.get("PRD_CAT_ID").toString());
		if(data.get("FMLY_GRP")!=null)productDTO.setFamilyGroup(data.get("FMLY_GRP").toString());
		if(data.get("CHCE_GRP")!=null)productDTO.setChoiceGroup(data.get("CHCE_GRP").toString());
	    if(data.get("prd_inst_id")!=null)productDTO.setPrdInstId(Long.parseLong(data.get("prd_inst_id").toString()));
		if(data.get("DY_PART_CD")!=null)productDTO.setDayPartCode(data.get("DY_PART_CD").toString());
		if(data.get("PRI_MNU_ITM_PRD_CD")!=null)productDTO.setPrimaryMenuItemCode(Long.parseLong(data.get("PRI_MNU_ITM_PRD_CD").toString()));
		if(data.get("SCDY_MENU_ITM")!=null)productDTO.setSecondaryMenuItem(data.get("SCDY_MENU_ITM").toString());
		if(data.get("AUX_MENU_ITM")!=null)productDTO.setAuxiliaryMenuItem(Long.parseLong(data.get("AUX_MENU_ITM").toString()));
		if(data.get(PRMO_MENU_ITM)!=null)productDTO.setPrmoMenuItem(Long.parseLong(data.get(PRMO_MENU_ITM).toString()));
		if(data.get(PRMO_CHCE)!=null)productDTO.setPrmoChoice(Long.parseLong(data.get(PRMO_CHCE).toString()));
		if(data.get("PARENT_PROMO")!=null)productDTO.setParentPromotionItem(Long.parseLong(data.get("PARENT_PROMO").toString()));
		if(data.get("MAX_INGREDIENT")!=null)productDTO.setMaxIngredients(Long.parseLong(data.get("MAX_INGREDIENT").toString()));
		if(data.get("STATION_GROUP")!=null)productDTO.setStationGroup(data.get("STATION_GROUP").toString());
		if(data.get("reuse_deposit_eatin")!=null)productDTO.setReuseDepositeEating(new BigDecimal(data.get("reuse_deposit_eatin").toString()));
		if(data.get("reuse_deposit_takeout")!=null)productDTO.setReuseDepositeTakeout(new BigDecimal(data.get("reuse_deposit_takeout").toString()));
	}

	private void populateMenuTypeAvailable(Map<String, Object> data, Product productDTO) {
		
		if(data.get("BREAKFAST")!=null)productDTO.setBreakfast(Long.parseLong(data.get("BREAKFAST").toString()));
		if(data.get("LUNCH")!=null)productDTO.setLunch(Long.parseLong(data.get("LUNCH").toString()));
		if(data.get("DINNER")!=null)productDTO.setDinner(Long.parseLong(data.get("DINNER").toString()));
		if(data.get("OVERNIGHT")!=null)productDTO.setOvernight(Long.parseLong(data.get("OVERNIGHT").toString()));
		
	}
	
	private void populateProductTimeAvailableForSale(Map<String, Object> data,Product productDTO) {
		List<TimeFrames> productTimeFrames = productDTO.getTimeAvailableForSales();
		if (productTimeFrames == null) {
			productTimeFrames = new ArrayList<>();
			productDTO.setTimeAvailableForSales(productTimeFrames);
		}
		

		if (data.get("FROMTOTIME") != null)
			
			for(String str : getSplitValues(data.get("FROMTOTIME").toString(), ",")){
				String[] values = str.split("-");
				TimeFrames timesAvailableForSales = new TimeFrames();
				timesAvailableForSales.setFrom(values[0]);
				timesAvailableForSales.setTo(values[1]);
				timesAvailableForSales.setPrdInstId(Long.parseLong(values[2]));
				productTimeFrames.add(timesAvailableForSales);
			}
			
	}
	
 private void populateDeliveryFeeDetails(Map<String, Object> data, Product productDTO) {	
	if(data.get("fee_deliveryid")!=null)productDTO.setFeeDeliveryId(Long.parseLong(data.get("fee_deliveryid").toString()));
	if(data.get("fee_percentage")!=null)productDTO.setFeePercentage(Double.parseDouble(data.get("fee_percentage").toString()));
	if(data.get("feename")!=null)productDTO.setFeeName(data.get("feename").toString());
	if(data.get("fee_max_threshold")!=null)productDTO.setFeemaxthreshold(data.get("fee_max_threshold").toString());
	if(data.get("fee_min_threshold")!=null)productDTO.setFeeminthreshold(data.get("fee_min_threshold").toString());
	}
	

public Map<Long, Product> getProductPromotionAssociationByMaster(long marketId, String effectiveDate,
		Long masterSetId) throws Exception {
	
	final String query = getDaoXml(GET_ASSOCIATED_PROMO_PRODUCTS, DAOResources.PRODUCT_DB_DAO);
	Map<Long, Product> listOfProduct  = new HashMap<>();	
	final Map<String, Object> paramMap = new HashMap<>();
	paramMap.put(MKT_ID, marketId);
	paramMap.put(ProductDBConstant.SETID,  masterSetId);		
	paramMap.put("type", ProductDBConstant.MMI_SET_TYPE);
	paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
	
	List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAssociatedPromoProducts");
	for ( final Map<String, Object> data : listOfResults ) {
		 Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> {
			 Product p=new Product();
			 p.setProductId(key);
			 return p;
		 });

		 List<AssociatedPromoProducts> assocaitedPromoProducts=productDTO.getAssociatedPromoProducts();
		 
		 if(assocaitedPromoProducts==null) {
			 assocaitedPromoProducts=new ArrayList<>();
		 }
		 assocaitedPromoProducts.add(addAssocaitedProducts(data));
		 productDTO.setAssociatedPromoProducts(assocaitedPromoProducts);
		 }
	return listOfProduct;
	
}



private AssociatedPromoProducts addAssocaitedProducts(Map<String, Object> data) {
	
	AssociatedPromoProducts assocaitedPromoProductDetails=new AssociatedPromoProducts();
	
	if(data.get(PRMO_PRD_ID)!=null) {
		
		assocaitedPromoProductDetails.setPromoPrdId(Long.parseLong(data.get(PRMO_PRD_ID).toString()));
	}
	if(data.get("PRMO_TYP")!=null) {
		assocaitedPromoProductDetails.setPromoType(Long.parseLong(data.get("PRMO_TYP").toString()));
		if(assocaitedPromoProductDetails.getPromoType()==1L) {
			assocaitedPromoProductDetails.setPkgGenCd("Automatic");
		} else {
			assocaitedPromoProductDetails.setPkgGenCd("Manual");
		}
	}
	
	if(data.get("PRRT")!=null) {
		assocaitedPromoProductDetails.setPriority(Long.parseLong(data.get("PRRT").toString()));
	}
	
	if(data.get("PRMO_ASSC_ID")!=null) {
		assocaitedPromoProductDetails.setPromoAsscId(Long.parseLong(data.get("PRMO_ASSC_ID").toString()));
	}
	return assocaitedPromoProductDetails;
}


public Map<Long, Product> getProductPromotionAssociationBySet(Map<Long, Product> source, long marketId,
		String effectiveDate, long misType, long setId) throws Exception {
	
	final String query = getDaoXml(GET_ASSOCIATED_PROMO_PRODUCTS, DAOResources.PRODUCT_DB_DAO);
	final Map<String, Object> paramMap = new HashMap<>();
	paramMap.put(MKT_ID, marketId);
	paramMap.put(ProductDBConstant.SETID,  setId);		
	paramMap.put("type", misType);
	paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
		
	List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAssociatedPromoProducts");
	
	for (final Map<String, Object> data : listOfResults) 
	{
		AssociatedPromoProducts assocaitedPrds=addAssocaitedProducts(data);
		
		List<AssociatedPromoProducts> sourceAssociatedProducts=source.get(Long.parseLong(data.get(PRD_ID).toString())).getAssociatedPromoProducts();
		
		boolean isNewattribute = false;
		for (int i = 0; i < sourceAssociatedProducts.size(); i++) {
			if (sourceAssociatedProducts.get(i).getPromoPrdId().equals(Long.parseLong(data.get(PRMO_PRD_ID).toString()))) 
			{
				BeanUtils.copyProperties(assocaitedPrds, sourceAssociatedProducts.get(i),getNullPropertyNames(assocaitedPrds));
				isNewattribute = true;
			}
		}
		if (!isNewattribute) {
			sourceAssociatedProducts.add(assocaitedPrds);
		}
	}
	return source;
	
}
	


public Map<Long, Product> getProductPromotionAssociationBySet( long marketId,
		String effectiveDate, long misType, long setId) throws Exception {

	Map<Long, Product> source = new HashMap<>();
	final String query = getDaoXml(GET_ASSOCIATED_PROMO_PRODUCTS, DAOResources.PRODUCT_DB_DAO);
	final Map<String, Object> paramMap = new HashMap<>();
	paramMap.put(MKT_ID, marketId);
	paramMap.put(ProductDBConstant.SETID,  setId);		
	paramMap.put("type", misType);
	paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));

	List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap, "getAssociatedPromoProducts");

	for (final Map<String, Object> data : listOfResults) 
	{
		AssociatedPromoProducts assocaitedPrds=addAssocaitedProducts(data);

		List<AssociatedPromoProducts> sourceAssociatedProducts=source.get(Long.parseLong(data.get(PRD_ID).toString())).getAssociatedPromoProducts();

		boolean isNewattribute = false;
		for (int i = 0; i < sourceAssociatedProducts.size(); i++) {
			if (sourceAssociatedProducts.get(i).getPromoPrdId().equals(Long.parseLong(data.get(PRMO_PRD_ID).toString()))) 
			{
				BeanUtils.copyProperties(assocaitedPrds, sourceAssociatedProducts.get(i),getNullPropertyNames(assocaitedPrds));
				isNewattribute = true;
			}
		}
		if (!isNewattribute) {
			sourceAssociatedProducts.add(assocaitedPrds);
		}
	}
	return source;

}





public Map<Long, Product> getProductsByMaster(long marketId,  String effectiveDate , long masterSetID) throws Exception {
	Map<Long, Product> listOfProduct  = getProductPosKvsPresentationByMaster(marketId, effectiveDate , masterSetID);					 
	Map<Long, Product> listOfParameters = getProductParametersByMaster(marketId, effectiveDate , masterSetID); 
	Map<Long, Product> listOfPromotionRange = getProductPromotionRangeByMaster(marketId,effectiveDate , masterSetID);
	Map<Long, Product> listOfProductsByMaster = getProductTagsMasterSet(marketId,effectiveDate, ProductDBConstant.MMI_SET_TYPE, masterSetID);
	Map<Long, Product> listOfAssociatedPromotions =getProductPromotionAssociationByMaster(marketId, effectiveDate,masterSetID);
	Map<Long, Product> listOfShortCuts= getProductShortcutSettingsByMaster(marketId, effectiveDate, masterSetID);
	Map<Long, Product> listOfComponents=getProductComponentsDetails(listOfProduct, marketId, effectiveDate, ProductDBConstant.MMI_SET_TYPE, masterSetID);
	for(Map.Entry<Long, Product> entry : listOfProduct.entrySet()){
		Product productParameters = listOfParameters.get(entry.getKey());
		Product productPromotion = listOfPromotionRange.get(entry.getKey());		
		Product associatedPromotion =  listOfAssociatedPromotions.get(entry.getKey());
		Product productShortCuts = listOfShortCuts.get(entry.getKey());
		Product productComponents = listOfComponents.get(entry.getKey());
		Product productTags = listOfProductsByMaster.get(entry.getKey());
		if(productParameters !=null) entry.getValue().setParameters(addMarketDefaultParameters(productParameters.getParameters(), entry.getValue().getParameters()));
		if(productPromotion !=null) entry.getValue().setProductPromotionRange(productPromotion.getProductPromotionRange());
		if (productShortCuts!=null) {
			entry.getValue().setProductShortCutSettings(productShortCuts.getProductShortCutSettings());
		}
		if (productComponents!=null) {
			entry.getValue().setComponents(productComponents.getComponents());
		}
		if(productPromotion !=null) entry.getValue().setProductPromotionRange(productPromotion.getProductPromotionRange());	
		if(productPromotion !=null) entry.getValue().setTimeRestrictions(productPromotion.getTimeRestrictions());
		if(associatedPromotion !=null) entry.getValue().setAssociatedPromoProducts(associatedPromotion.getAssociatedPromoProducts());
		if(productTags !=null) entry.getValue().setProductTagsList(productTags.getProductTagsList());
	}
	return listOfProduct;
}

private List<Parameter> addMarketDefaultParameters(List<Parameter> menuItemParameters, List<Parameter> marketParameters) {
	List<Parameter> parameters = menuItemParameters;
	if (marketParameters != null && menuItemParameters != null) {
		for (Parameter param : marketParameters) {
			if (!containsParm(menuItemParameters, param.getName())) {
				parameters.add(param);
			}
		}
	}
	return parameters;
}


public Map<Long, Product> getProductsBySet(Long marketId, String effectiveDate, long setType,
		long setId) throws Exception  {
	 	 
		Map<Long, Product> listOfProduct  = getProductPosKvsPresentationBySet( marketId, effectiveDate, setType,  setId); 					 		
		Map<Long, Product> listOfParameters =  getProductParametersBynBySet(marketId, effectiveDate, setType, setId); 
		Map<Long, Product> listOfPromotionRange = getProductPromotionRangeBySet(marketId,effectiveDate , setType, setId);
		Map<Long, Product> listOfAssociatedPromotions =getProductPromotionAssociationByMaster(marketId, effectiveDate,setId);
		Map<Long, Product> listOfShortCuts=getProductShortcutSettingsBySet(listOfProduct, marketId, effectiveDate, setType, setId);
		Map<Long, Product> listOfComponents=getProductComponentsDetails(listOfProduct, marketId, effectiveDate, setType, setId);
		getProductTagsBySet(listOfProduct,marketId,effectiveDate, setType, setId);

		for(Map.Entry<Long, Product> entry : listOfProduct.entrySet()){
			Product productParameters = listOfParameters.get(entry.getKey());
			Product productPromotion = listOfPromotionRange.get(entry.getKey());			
			Product associatedPromotion =  listOfAssociatedPromotions.get(entry.getKey());	
			Product productShortCuts = listOfShortCuts.get(entry.getKey());
			Product productComponents = listOfComponents.get(entry.getKey());
			if(productParameters !=null) entry.getValue().setParameters(productParameters.getParameters());
			if(productPromotion !=null) entry.getValue().setProductPromotionRange(productPromotion.getProductPromotionRange());
			if(productShortCuts!=null) entry.getValue().setProductShortCutSettings(productShortCuts.getProductShortCutSettings());
			if (productComponents!=null) {
				entry.getValue().setComponents(productComponents.getComponents());
			}
			if(productPromotion !=null) entry.getValue().setProductPromotionRange(productPromotion.getProductPromotionRange());	
			if(productPromotion !=null) entry.getValue().setTimeRestrictions(productPromotion.getTimeRestrictions());
			if(associatedPromotion !=null) entry.getValue().setAssociatedPromoProducts(associatedPromotion.getAssociatedPromoProducts());
			
		}			
		return listOfProduct;
}

/**
* Returns the merged map with Product SET  
* @param	source - source product
* @param	productDTO - product customized by set
* @return   Product - merged product
*/
public Product layeringProduct(Product source, Product productDTO) throws Exception { 	
	if (productDTO.getDayPartCode() != null) {
		source.setDayPartCode(productDTO.getDayPartCode());
	}
	if (productDTO.getSecondaryMenuItem() != null) {
		source.setSecondaryMenuItem(productDTO.getSecondaryMenuItem());
	}
 	if (productDTO.getPrmoMenuItem()!=null && productDTO.getPrmoMenuItem()==0) {
		BeanUtils.copyProperties(productDTO.getPrmoMenuItem(), source.getPrmoMenuItem(), getNullPropertyNames(productDTO.getPrmoMenuItem()));
	}
	
	if (productDTO.getPrmoChoice()!=null && productDTO.getPrmoChoice()==0) {
		BeanUtils.copyProperties(productDTO.getPrmoChoice(), source.getPrmoChoice(), getNullPropertyNames(productDTO.getPrmoChoice()));
	}

	if (productDTO.getPromoStartDate()!=null) 	BeanUtils.copyProperties(productDTO.getPromoStartDate(), source.getPromoStartDate(), getNullPropertyNames(productDTO.getPromoStartDate()));
	if (productDTO.getPromoStartDate()!=null)  BeanUtils.copyProperties(productDTO.getPromoEndDate(), source.getPromoEndDate(), getNullPropertyNames(productDTO.getPromoEndDate()));
	if(productDTO.getProductPresentation() != null) {
		if (source.getProductPresentation() == null) source.setProductPresentation(new ProductPresentation());
		BeanUtils.copyProperties(productDTO.getProductPresentation(), source.getProductPresentation(), getNullPropertyNames(productDTO.getProductPresentation()));		 
	}
	layringProductPOSKVS(source, productDTO);
	layeringSmartRoutingProperties(source, productDTO);
	if(productDTO.getProductCCMSettings() != null && source.getProductCCMSettings() != null)	BeanUtils.copyProperties(productDTO.getProductCCMSettings(), source.getProductCCMSettings(), getNullPropertyNames(productDTO.getProductCCMSettings()));
	if(productDTO.getProductAbsSettings()!=null && source.getProductAbsSettings() != null) BeanUtils.copyProperties(productDTO.getProductAbsSettings(), source.getProductAbsSettings(), getNullPropertyNames(productDTO.getProductAbsSettings()));
	layringParameterProperties(source, productDTO);
	if(productDTO.getProductPromotionRange()!=null && source.getProductPromotionRange() != null)	BeanUtils.copyProperties(productDTO.getProductPromotionRange(), source.getProductPromotionRange(), getNullPropertyNames(productDTO.getProductPromotionRange()));
	if(productDTO.getProductGeneralSettingNamesList()!=null && source.getProductGeneralSettingNamesList() != null)			BeanUtils.copyProperties(productDTO.getProductGeneralSettingNamesList(), source.getProductGeneralSettingNamesList(), getNullPropertyNames(productDTO.getProductGeneralSettingNamesList()));
	mergeComponentProperties(source, productDTO);
	if(productDTO.getAssociatedPromoProducts()!=null && source.getAssociatedPromoProducts() != null) 	BeanUtils.copyProperties(productDTO.getAssociatedPromoProducts(), source.getAssociatedPromoProducts(), getNullPropertyNames(productDTO.getAssociatedPromoProducts()));
	if(productDTO.getPromotionGroups()!=null && source.getPromotionGroups() != null) 	BeanUtils.copyProperties(productDTO.getPromotionGroups(), source.getPromotionGroups(), getNullPropertyNames(productDTO.getPromotionGroups()));
	layringShrotcutSettings(source, productDTO);
	if(productDTO.getSizeSelection()!=null && source.getSizeSelection() != null) 	BeanUtils.copyProperties(productDTO.getSizeSelection(), source.getSizeSelection(), getNullPropertyNames(productDTO.getSizeSelection()));
	if(productDTO.getSubstitutionList()!=null && source.getSubstitutionList() != null) 	BeanUtils.copyProperties(productDTO.getSubstitutionList(), source.getSubstitutionList(), getNullPropertyNames(productDTO.getSubstitutionList()));
	if(productDTO.getTimeRestrictions()!=null && source.getSubstitutionList() != null) 	BeanUtils.copyProperties(productDTO.getTimeRestrictions(), source.getTimeRestrictions(), getNullPropertyNames(productDTO.getTimeRestrictions()));
	
		if (productDTO.getProductTagsList() != null) {
			if (source.getProductTagsList() == null) {
				source.setProductTagsList(new ArrayList<>());
			} else {
				source.getProductTagsList().clear();
			}
			for (ProductTags productTag : productDTO.getProductTagsList()) {
				ProductTags srcPrdTag = new ProductTags();
				srcPrdTag.setTagId(productTag.getTagId());
				source.getProductTagsList().add(srcPrdTag);
				
				BeanUtils.copyProperties(productTag, srcPrdTag, getNullPropertyNames(productTag));
			}
		}
	if(productDTO.getAssociatedCategories()!=null && source.getAssociatedCategories() != null) 	BeanUtils.copyProperties(productDTO.getAssociatedCategories(), source.getAssociatedCategories(), getNullPropertyNames(productDTO.getAssociatedCategories()));
	layringTimesAvailbleForSales(source, productDTO);
	if(productDTO.getPriceList()!=null && source.getPriceList() != null) 	BeanUtils.copyProperties(productDTO.getPriceList(), source.getPriceList(), getNullPropertyNames(productDTO.getPriceList()));
	copyGeneralsettingProperties(productDTO, source);
	
	return source;
}

private void mergeComponentProperties(Product source, Product productDTO) {
	if (productDTO.getComponents() != null && source.getComponents() != null) {
		List<Component> sourceList = source.getComponents();
		List<Component> targetList = productDTO.getComponents();
		boolean modified = false;
		if(sourceList.size()>targetList.size()
			|| (sourceList.size() == targetList.size() && !sourceList.containsAll(targetList))) {
			modified=true;
			List<Component> removed = sourceList.stream()
					.filter(o1 -> targetList.stream()
							.anyMatch(o2 -> o2.getComponentProductId().equals(o1.getComponentProductId())))
					.collect(Collectors.toList());
			List<Component> different = new ArrayList<>();
			different.addAll(sourceList);
			different.removeAll(removed);	

			for (Component tgtComponent : targetList) {
				Component srcComponent = sourceList.contains(tgtComponent) ? sourceList.get(sourceList.indexOf(tgtComponent)) : null;
				if (srcComponent == null) continue;
				if (tgtComponent.getDeleted()==0)
					BeanUtils.copyProperties(srcComponent, tgtComponent, getNotNullPropertyNames(tgtComponent));
			}

			different.addAll(targetList);
			source.setComponents(different);
			
		} else if (sourceList.containsAll(targetList)) { 
			for (Component srcComponent : sourceList) {
				Component tgtComponent = targetList.get(targetList.indexOf(srcComponent));
				if (tgtComponent == null) continue;
				if(tgtComponent.getDeleted()==0)
					BeanUtils.copyProperties(tgtComponent, srcComponent, getNullPropertyNames(tgtComponent));
			}
		} else if (targetList.stream().anyMatch(c -> c.getDeleted() == 0)) {
			modified=true;
			source.setComponents(targetList);
		}
		if(Boolean.FALSE.equals(modified))source.setComponents(sourceList);
	}
}

private void layringParameterProperties(Product source, Product productDTO) {
	if(productDTO.getParameters()!=null && !productDTO.getParameters().isEmpty()) {
		if(source.getParameters() == null || source.getParameters().isEmpty()) {
			source.setParameters(productDTO.getParameters());
		} else {
			for(Parameter parameter: productDTO.getParameters()) {
				Optional<Parameter> sParameter = source.getParameters().stream().filter(sp -> sp.getParamId().equals(parameter.getParamId())).findAny();
				if(sParameter.isPresent()) {
					if(parameter.getValue()!=null) sParameter.get().setValue(parameter.getValue());
				} else {
					source.getParameters().add(parameter);
				}
			}
		}
	}
}

	private void layringShrotcutSettings(Product source, Product productDTO) {
		if (productDTO.getProductShortCutSettings() != null && !productDTO.getProductShortCutSettings().isEmpty()) {
			if (source.getProductShortCutSettings() == null || source.getProductShortCutSettings().isEmpty()) {
				source.setProductShortCutSettings(productDTO.getProductShortCutSettings());
			} else {
				for (ProductShortCutSettings prdshtsettings : productDTO.getProductShortCutSettings()) {
					Optional<ProductShortCutSettings> sParameter = source.getProductShortCutSettings().stream()
							.filter(sp -> sp.getKioskId().equals(prdshtsettings.getKioskId())).findAny();
					if (sParameter.isPresent()) {
						sParameter.get().setKioskId(prdshtsettings.getKioskId());
						sParameter.get().setProductId(prdshtsettings.getProductId());
						
					} else {
						source.getProductShortCutSettings().add(prdshtsettings);
					}
				}
				source.getProductShortCutSettings().retainAll(productDTO.getProductShortCutSettings());
			}
		}
	}
	
	private void layringTimesAvailbleForSales(Product source, Product productDTO) {
		if(productDTO.getTimeAvailableForSales()!=null && !productDTO.getTimeAvailableForSales().isEmpty()) {
			if (source.getTimeAvailableForSales() == null || source.getTimeAvailableForSales().isEmpty()) {
				source.setTimeAvailableForSales(productDTO.getTimeAvailableForSales());
			} else {
				source.getTimeAvailableForSales().addAll(productDTO.getTimeAvailableForSales());
				source.getTimeAvailableForSales().retainAll(productDTO.getTimeAvailableForSales());
			}
		}
	}


private void layeringSmartRoutingProperties(Product source, Product productDTO) {
	if(productDTO.getProductSmartRouting()!=null) {
		if (source.getProductSmartRouting()==null) {
			source.setProductSmartRouting(new ProductSmartRouting());
		}
		if (source.getProductSmartRouting().getSmartRoutingTasks()==null) {
			source.getProductSmartRouting().setSmartRoutingTasks(new ArrayList<>());
		}
		List<SmartRoutingTask> tempList=source.getProductSmartRouting().getSmartRoutingTasks();
		BeanUtils.copyProperties(productDTO.getProductSmartRouting(),source.getProductSmartRouting(),getNullPropertyNames(productDTO.getProductSmartRouting()));
		source.getProductSmartRouting().setSmartRoutingTasks(tempList);
		if(productDTO.getProductSmartRouting().getSmartRoutingTasks() != null) {
			for(SmartRoutingTask task:productDTO.getProductSmartRouting().getSmartRoutingTasks()) {
				Optional<SmartRoutingTask> optSmrttask=source.getProductSmartRouting().getSmartRoutingTasks().stream().filter(e-> e.getSmartTaskId().equals(task.getSmartTaskId())).findAny();
				SmartRoutingTask routingTask=null;
				if(optSmrttask==null ||optSmrttask.isEmpty()) {
					routingTask=new SmartRoutingTask();
					routingTask.setSmartTaskId(task.getSmartTaskId());
					source.getProductSmartRouting().getSmartRoutingTasks().add(routingTask);
				}else {
					routingTask=optSmrttask.get();
				}
				if(task.getMonitor()!=null) {		routingTask.setMonitor(task.getMonitor());	 	}
				if(task.getMsgKey()!=null) {		routingTask.setMsgKey(task.getMsgKey());		}
				if(task.getDisplayTime()!=null) {	routingTask.setDisplayTime(task.getDisplayTime());	}
				if(task.getTaskTime()!=null) {		routingTask.setTaskTime(task.getTaskTime());		}
				if(task.getSmartTaskUK()!=null) {	routingTask.setSmartTaskUK(task.getSmartTaskUK());	}
				
			}
		}
	}
}

private void layringProductPOSKVS(Product source, Product productDTO) {
	if(productDTO.getProductPosKvs() != null && source.getProductPosKvs() != null) {	
		List<GenericEntry> tempSellList=source.getProductPosKvs().getSellLocation();
		List<GenericEntry> sourceKvsDisplay =  source.getProductPosKvs().getKvsDisplay();
		BeanUtils.copyProperties(productDTO.getProductPosKvs(), source.getProductPosKvs(), getNullPropertyNames(productDTO.getProductPosKvs()));	
		if(productDTO.getProductPosKvs().getSellLocation()!=null && !productDTO.getProductPosKvs().getSellLocation().isEmpty()) {
			source.getProductPosKvs().setSellLocation(productDTO.getProductPosKvs().getSellLocation());
		}else {
			source.getProductPosKvs().setSellLocation(tempSellList);
		}
		if(productDTO.getProductPosKvs().getAutoCondimentDisplay()!=null) source.getProductPosKvs().setAutoCondimentDisplay(productDTO.getProductPosKvs().getAutoCondimentDisplay());
		if(productDTO.getProductPosKvs().getDisplyPosEvent()!=null)	source.getProductPosKvs().setDisplyPosEvent(productDTO.getProductPosKvs().getDisplyPosEvent());
		if(productDTO.getProductPosKvs().getDisplayDTWposEvent()!=null) source.getProductPosKvs().setDisplayDTWposEvent(productDTO.getProductPosKvs().getDisplayDTWposEvent());
		if(productDTO.getProductPosKvs().getSelectedTags()!=null) BeanUtils.copyProperties(productDTO.getProductPosKvs().getSelectedTags(), source.getProductPosKvs().getSelectedTags(), getNullPropertyNames(productDTO.getProductPosKvs().getSelectedTags()));
		if(productDTO.getProductPosKvs().getApplySalesType()!=null && !productDTO.getProductPosKvs().getApplySalesType().isEmpty() ) {
			if(source.getProductPosKvs().getApplySalesType()==null && source.getProductPosKvs().getApplySalesType().isEmpty()) {
				source.getProductPosKvs().setApplySalesType(productDTO.getProductPosKvs().getApplySalesType());
			}else {
				for(String saletype: productDTO.getProductPosKvs().getApplySalesType()) {
					Optional<String> sParameter = source.getProductPosKvs().getApplySalesType().stream().filter(sp -> sp.equals(saletype)).findAny();
					if(!sParameter.isPresent()) {
						source.getProductPosKvs().getApplySalesType().add(saletype);
				} 
			}
		  }
		}
		
		long showOnMain = 0l;
		long doNotDecompVM = 0l;
		long showOnMFY = 0l;
		long showOnSummary = 0l;
	
		for(GenericEntry dto : sourceKvsDisplay) { 
			if(dto.getName().equals(SHOW_ON_MAIN) ) showOnMain = dto.getCode();
			if(dto.getName().equals(DO_NOT_DECOMP_VM) ) doNotDecompVM = dto.getCode();
			if(dto.getName().equals(SHOW_ON_MFY) ) showOnMFY  = dto.getCode();
			if(dto.getName().equals(SHOW_ON_SUMMARY) )  showOnSummary  = dto.getCode();
		}
		for(GenericEntry dto : productDTO.getProductPosKvs().getKvsDisplay()) { 
			if(dto.getName().equals(SHOW_ON_MAIN) ) showOnMain = dto.getCode();
			if(dto.getName().equals(DO_NOT_DECOMP_VM) ) doNotDecompVM = dto.getCode();
			if(dto.getName().equals(SHOW_ON_MFY) ) showOnMFY  = dto.getCode();
			if(dto.getName().equals(SHOW_ON_SUMMARY) )  showOnSummary  = dto.getCode();
		}
			
		
		
		source.getProductPosKvs().getKvsDisplay().clear();
		source.getProductPosKvs().getKvsDisplay().add(new GenericEntry(showOnMain,SHOW_ON_MAIN));
		source.getProductPosKvs().getKvsDisplay().add(new GenericEntry(doNotDecompVM,DO_NOT_DECOMP_VM));
		source.getProductPosKvs().getKvsDisplay().add(new GenericEntry(showOnMFY,SHOW_ON_MFY));
		source.getProductPosKvs().getKvsDisplay().add(new GenericEntry(showOnSummary,SHOW_ON_SUMMARY));
		
		
		
	}
	
}



/**
* Returns the merged map with Pos KVS / Presentation of specific SET  
* @param	marketId - market number
* @param	effectiveDate - effective date
* @param	setType - set type 3001-MIS 3003-RMI  
* @param    setId - set id 
* @return   Map<Long, Product>
*/
public Map<Long, Product>  getProductPosKvsPresentationBySet(long marketId,  String effetiveDate, long setType, long setId) throws Exception {
	final String query = getDaoXml(GET_MENU_ITEM_POSKVS_PRESENTATION, DAOResources.PRODUCT_DB_DAO);
	
	final Map<String, Object> paramMap = new HashMap<>();
	paramMap.put(MKT_ID, marketId);
	paramMap.put(ProductDBConstant.SETID,  setId);		
	paramMap.put("type", setType);
	paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effetiveDate) );
	Map<Long, Product>  listOfSet = new HashMap<>();
	
	List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemPOSKVSPresentation");
	
	for ( final Map<String, Object> data : listOfResults ) {
		 Product productDTO = new Product();		 
		 productDTO.setProductId(Long.parseLong(data.get(PRD_ID).toString()));			 
		 populatePromoRangeAttributes(data,productDTO);			 	 
		 ProductPresentation oPresentation = mapProductPresentation(data);			 			 
		 productDTO.setProductPresentation(oPresentation);						 			 
		 ProductPosKvs oPosKvs = mapProductPosKvs(data, false);
		 productDTO.setProductPosKvs(oPosKvs);			 			 			 
		 ProductSmartRouting oSmartRouting=mapProductSmartRouting(data);
		 productDTO.setProductSmartRouting(oSmartRouting);				 
		 ProductCCMSettings oProductCCMSettings=mapProductCCMSettings(data);
		 productDTO.setProductCCMSettings(oProductCCMSettings);			 
		 ProductAbsSettings abssettings = mapProductAbsSetting(data);
		 productDTO.setProductAbsSettings(abssettings);	
		 populateGenralSetting(data, productDTO);
		 populateMenuTypeAvailable(data,productDTO);
		 populateProductTimeAvailableForSale(data,productDTO);
		 populateDeliveryFeeDetails( data,productDTO);
		 listOfSet.put(productDTO.getProductId(), productDTO);
	}	
	createSmartRoutingTask(listOfSet,paramMap);
	return listOfSet;
}	



public Map<Long, Product> getProductParametersBynBySet(long marketId,
		String effetiveDate, long setType, long setId) throws Exception {
	
	Map<Long, Product> listOfProduct = new HashMap<>();
	final String query = getDaoXml(GET_MENU_ITEM_PARAMETER_LIST, DAOResources.PRODUCT_DB_DAO);
	final Map<String, Object> paramMap = new HashMap<>();
	paramMap.put(MKT_ID, marketId);
	paramMap.put(ProductDBConstant.SETID, setId);
	paramMap.put("type", setType);
	paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effetiveDate));
	List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemParameterList");
	for ( final Map<String, Object> data : listOfResults ) {
		 
		Product productDTO = listOfProduct.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> {
			 Product p=new Product();
			 p.setProductId(key);
			 return p;
		 });
		 List<Parameter> parameters=productDTO.getParameters();
		 if(parameters==null) {
			 parameters=new ArrayList<>();
		 }
		 parameters.add(addParameter(data));
		 productDTO.setParameters(parameters);
		 listOfProduct.put(productDTO.getProductId(), productDTO);
		 }
	return listOfProduct;
}



public Map<Long, Product> getProductPromotionRangeBySet(long marketId,
		String effectiveDate, long misType, long setId) throws Exception {
	final String query = getDaoXml(GET_MENU_ITEM_PROMOTION_RANGE, DAOResources.PRODUCT_DB_DAO);
	final Map<String, Object> paramMap = new HashMap<>();
	paramMap.put(MKT_ID, marketId);
	paramMap.put(ProductDBConstant.SETID,  setId);		
	paramMap.put("type", misType);
	paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate) );
	
	List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemPromotionRange");
	
	Map<Long, Map<WeekDays, List<Pair<String, String>>>> prdTimeRestrictionMap = new HashMap<>();
	
	Map<Long, Product> listOfProduct= new HashMap<>();
	
	for (final Map<String, Object> data : listOfResults) {
		Map<WeekDays, List<Pair<String, String>>> timeRestrictionByWeekDay = prdTimeRestrictionMap
				.computeIfAbsent(Long.parseLong(data.get(PRD_ID).toString()), key -> new HashMap<>());

		mapProductPromotionRange(data, timeRestrictionByWeekDay);

	}

	prdTimeRestrictionMap.forEach((prdId, timeRestrictionByWeekDay) -> {
		Product p = new Product();
		p.setProductId(prdId);
		List<ProductPromotionRange> timeRestrictions = new ArrayList<>();
		timeRestrictionByWeekDay.forEach((weekDay, allowedTimes) -> 
			timeRestrictions.add(new ProductPromotionRange(weekDay, allowedTimes))
		);
		p.setTimeRestrictions(timeRestrictions);
		listOfProduct.put(prdId, p);
	});

	return listOfProduct;
}


	private void copyGeneralsettingProperties(Product setProduct, Product  sourceProduct)
	{
		
		if(setProduct.getProductCode() != null) {
			sourceProduct.setProductCode(setProduct.getProductCode());}
		if(setProduct.getProductType() != null) {
			sourceProduct.setProductType(setProduct.getProductType());}
		if(setProduct.getProductClass() != null) {
			sourceProduct.setProductClass(setProduct.getProductClass());}
		if(setProduct.getProductClassId() != null) {
			sourceProduct.setProductClassId(setProduct.getProductClassId());}
		if(setProduct.getProductCategory() != null) {
			sourceProduct.setProductCategory(setProduct.getProductCategory());}
		if(setProduct.getFamilyGroup() != null) {
			sourceProduct.setFamilyGroup(setProduct.getFamilyGroup());}
		if(setProduct.getChoiceGroup() != null) {
			sourceProduct.setChoiceGroup(setProduct.getChoiceGroup());}
		if(setProduct.getDayPartCode() != null) {
			sourceProduct.setDayPartCode(setProduct.getDayPartCode());}
		if(setProduct.getPrimaryMenuItemCode() != null) {
			sourceProduct.setPrimaryMenuItemCode(setProduct.getPrimaryMenuItemCode());}
		if(setProduct.getSecondaryMenuItem() != null) {
			sourceProduct.setSecondaryMenuItem(setProduct.getSecondaryMenuItem());}
		if(setProduct.getAuxiliaryMenuItem() != null) {
			sourceProduct.setAuxiliaryMenuItem(setProduct.getAuxiliaryMenuItem());}
		if(setProduct.getPrmoMenuItem() != null) {
			sourceProduct.setPrmoMenuItem(setProduct.getPrmoMenuItem());}
		if(setProduct.getPrmoChoice() != null) {
			sourceProduct.setPrmoChoice(setProduct.getPrmoChoice());}
		if(setProduct.getParentPromotionItem() != null) {
			sourceProduct.setParentPromotionItem(setProduct.getParentPromotionItem());}
		if(setProduct.getMaxIngredients() != null) {
			sourceProduct.setMaxIngredients(setProduct.getMaxIngredients());}
		if(setProduct.getStationGroup() != null) {
			sourceProduct.setStationGroup(setProduct.getStationGroup());}
		if(setProduct.getBreakfast() != null) {
			sourceProduct.setBreakfast(setProduct.getBreakfast());}
		if(setProduct.getLunch() != null) {
			sourceProduct.setLunch(setProduct.getLunch());}
		if(setProduct.getDinner()!= null) {
			sourceProduct.setDinner(setProduct.getDinner());}
		if(setProduct.getOvernight() != null) {
			sourceProduct.setOvernight(setProduct.getOvernight());}
		if (setProduct.getTimeAvailableForSales() != null && !setProduct.getTimeAvailableForSales().isEmpty()) {
			sourceProduct.setTimeAvailableForSales(setProduct.getTimeAvailableForSales());}
		if(setProduct.getFeeDeliveryId() != null) {
			sourceProduct.setFeeDeliveryId(setProduct.getFeeDeliveryId());}
		if(setProduct.getFeePercentage() != null) {
			sourceProduct.setFeePercentage(Double.parseDouble(setProduct.getFeePercentage().toString()));}
		if(setProduct.getFeeminthreshold()!=null) {sourceProduct.setFeeminthreshold(setProduct.getFeeminthreshold());}
	    if(setProduct.getFeemaxthreshold()!=null) {sourceProduct.setFeemaxthreshold(setProduct.getFeemaxthreshold());}
	    if(setProduct.getReuseDepositeEating()!=null) {sourceProduct.setReuseDepositeEating(setProduct.getReuseDepositeEating());}
	    if(setProduct.getReuseDepositeTakeout()!=null) {sourceProduct.setReuseDepositeTakeout(setProduct.getReuseDepositeTakeout());}
	}
	

	/**
	 * 
	 * @param valueToBeSplited
	 * @return
	 */
	private ArrayList<String> getSplitValues(String valueToBeSplited, String strToSplit) {
		if (valueToBeSplited == null)
			return null;
		
		ArrayList<String> lstValues = new ArrayList<>();
		String[] result = valueToBeSplited.split(strToSplit);

		for (String dist : result) {
			lstValues.add(dist.trim());
		}
		return lstValues;
	}
	
	/*
	 * This method is used to fetch the product data for Dimension group from DB
	 * 
	 * @param parentSetId, childSetId, productId, effDate,marketID
	 * 
	 * @return productDataForDimension
	 */
	public List<Map<String, Object>> getProductDataForDimensionGroup(Long parentSetId, Long childSetId, 
			String effDate, Long marketID) throws Exception {
		final String query = getDaoXml("getProductDataForDimensionGroup", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("custSetId", childSetId);
		paramMap.put("p_eff_dt", DateUtility.convertStringToDate(effDate));
		paramMap.put(MKT_ID, marketID);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getProductDataForDimensionGroup");
	}

	/*
	 * This method is used to fetch the product data for Category from DB
	 * 
	 * @param prodID, categoryEffectiveDate
	 * 
	 * @return CategoriesVals
	 */
	public Map<Long, Product> getCategoriesVals(Map<Long, Product> products, final String categoryEffectiveDate)
			throws Exception {
		final String query = getDaoXml("getCategoriesVals", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();		
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(categoryEffectiveDate));
		List<Map<String, Object>> categoriesVals = Wizard.queryForList(dataSourceQuery, query, paramMap, "getCategoriesVals");
		
		for (final Map<String, Object> productData : categoriesVals) {
			Category category = new Category();
			category.setCategoryID(productData.get("cat_cd").toString());
			category.setSequence(productData.get("prd_seq").toString());
			category.setDisplaySizeSelection(productData.get("sizeFlag").toString());			
			Long prodID = Long.parseLong(productData.get(PRD_ID).toString());
			
			Product product = products.get(prodID);
			if(product!=null) {
				product.getCategories().add(category);
			}
		}

		return products;
	}

	// MenuItemGroup- To find the Group Count
	@TrackedMethod
	public int getgroupCount(final Long marketID) throws Exception {
		final String query = getDaoXml("getGroupCount", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		int groupCount = 0;
		paramMap.put(MKT_ID, marketID);
		List<Map<String, Object>> groupMap = Wizard.queryForList(dataSourceQuery, query, paramMap, "getGroupCount");
		for (Map<String, Object> map : groupMap) {
			if (map.get("GPCOUNT") != null)
				groupCount = Integer.parseInt(map.get("GPCOUNT").toString());
			
		}
		return groupCount;
	}

		// Exporting Menu Item Group Code
		public List<Map<String, Object>> getMenuItemGroupValues(final Long marketID, final String groupItemEffectiveDate) throws Exception {
			final String query = getDaoXml("getMenuItemGroupValues", DAOResources.PRODUCT_DB_DAO);
			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put(MKT_ID, marketID);
			paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(groupItemEffectiveDate));
		 
			return Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemGroupValues");
		}

	/*
	 * This method is used to fetch the product list for promotion group from DB
	 * 
	 * @param marketID, productID, effectiveDate
	 * 
	 * @return productListData
	 */
	public Map<Long, Product> getProductListDataForPromotionGroup( Map<Long, Product> products, Long marketID, String effectiveDate) throws Exception {
		final String query = getDaoXml("getProductListDataForPromotionGroup", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketID);
		paramMap.put(EFF_DATE, DateUtility.convertStringToDate(effectiveDate));// need date conversion or not
		List<Map<String, Object>> promotionGroupData = Wizard.queryForList(dataSourceQuery, query, paramMap, "getProductListDataForPromotionGroup");
		
		for (final Map<String, Object> productList : promotionGroupData) {					
			Long productId = Long.parseLong(productList.get(PRD_ID).toString());
			Product product =  products.get(productId);
			if (product == null) continue;
			if (product.getPromotionGroups() == null) product.setPromotionGroups(new ArrayList<PromotionGroup>());
			
			String promotionGroupCode = productList.get("PROMO_GRP_CD").toString();
			PromotionGroup aux = new PromotionGroup();
			aux.setCode(promotionGroupCode);
			PromotionGroup promotionGroup;
			if (product.getPromotionGroups().contains(aux)) {
				promotionGroup = product.getPromotionGroups().get(product.getPromotionGroups().indexOf(aux));
			} else {
				promotionGroup = aux;
				product.getPromotionGroups().add(promotionGroup);
			}

			String flag = productList.get("FLAG").toString();
			
			List<ProductList> list;
			if (promotionGroup.getList() != null) {
				list = promotionGroup.getList();
			} else {
				list = new ArrayList<ProductList>();
				promotionGroup.setList(list);
			}
			if(! flag.equals("0")) {
				ProductList productLists = new ProductList();
				productLists.setList(flag);
				list.add(productLists);				
			}		
		}
		return products;	
	}
	
	
	

	/*
	 * This method is used to fetch the product list count from DB
	 * 
	 * @param marketID,productID
	 * 
	 * @return productListCount
	 */
	public Long getProductListCount(Long marketID, Long productID) throws Exception {
		final String query = getDaoXml("getProductListCount", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, marketID);
		paramMap.put(PRDID, productID);
		return Wizard.queryForObject(dataSourceQuery, query, paramMap, Long.class, "getProductListCount");
	}

	/*
	 * This method is used to fetch the list flag from DB
	 * 
	 * @param marketID,productID,promoGroupID
	 * 
	 * @return listFlagData
	 */
	public List<Map<String, Object>> getListFlag(Long marketID, Long productID, String promoGroupID) throws Exception {
		final String query = getDaoXml("getListFlag", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		Long promoGrpId = Long.parseLong(promoGroupID);
		paramMap.put(MKT_ID, marketID);
		paramMap.put(PRDID, productID);
		paramMap.put("promoGrpId", promoGrpId);		
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getListFlag");
	}

	/*
	 * This method is used to fetch the routing sets for both presentation and
	 * production routing from DB
	 * 
	 * @param marketID,restId,restInstId
	 * 
	 * @return routingSets
	 */
	public List<Map<String, Object>> getRoutingSets(Long mktId, Long restId, Long restInstId) throws Exception {
		final String query = getDaoXml(ProductDBConstant.GET_ROUTINGSETS, DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(ProductDBConstant.MKT_ID, mktId);
		paramMap.put(ProductDBConstant.REST_ID, restId);
		paramMap.put(ProductDBConstant.REST_INST_ID, restInstId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, ProductDBConstant.GET_ROUTINGSETS);
	}


	public Map<Long, PriceTax> getPricingValuesBysetId(Map<Long, PriceTax> priceMap, final Long mktId, String effectiveDate, long retrievedsetId) throws Exception {
		final String query = getDaoXml("getPricingValuesBysetId", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();		
		paramMap.put(MKT_ID, mktId);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("retrievedsetId", retrievedsetId);
		List<Map<String, Object>>  priceValues =  Wizard.queryForList(dataSourceQuery, query, paramMap, "getPricingValuesBysetId");
		
		for (final Map<String, Object> price : priceValues) {											
			Long prdId = Long.parseLong(price.get(PRD_ID).toString());
			PriceTax dto = new PriceTax();
			
			if(priceMap.get(prdId)!=null) {
				dto = priceMap.get(prdId);
			} else {		
				priceMap.put(Long.parseLong(price.get(PRD_ID).toString()), dto);
			}
			
			if(price.get(EATIN_PRC) != null) dto.setEatin_prc(Double.parseDouble(price.get(EATIN_PRC).toString()));
			if(price.get(TKUT_PRC) != null) dto.setTkut_prc(Double.parseDouble(price.get(TKUT_PRC).toString()));
			if(price.get(OTH_PRC) != null) dto.setOth_prc(Double.parseDouble(price.get(OTH_PRC).toString()));			
			if(price.get(EATIN_TAX_CD) !=null) dto.setEatin_tax_cd(Long.parseLong(price.get(EATIN_TAX_CD).toString()));
			if(price.get(TKUT_TAX_CD) != null) dto.setTkut_tax_cd(Long.parseLong(price.get(TKUT_TAX_CD).toString()));
			if(price.get(OTH_TAX_CD) != null) dto.setOth_tax_cd(Long.parseLong(price.get(OTH_TAX_CD).toString()));
			if(price.get(EATIN_TAX_RULE) != null) dto.setEatin_tax_rule(Long.parseLong(price.get(EATIN_TAX_RULE).toString()));
			if(price.get(TKUT_TAX_RULE) != null) dto.setTkut_tax_rule(Long.parseLong(price.get(TKUT_TAX_RULE).toString()));
			if(price.get(OTH_TAX_RULE) != null) dto.setOth_tax_rule(Long.parseLong(price.get(OTH_TAX_RULE).toString()));
			if(price.get(EATIN_TAX_ENTR) != null) dto.setEatin_tax_entr(Long.parseLong(price.get(EATIN_TAX_ENTR).toString()));
			if(price.get(TKUT_TAX_ENTR) != null) dto.setTkut_tax_entr(Long.parseLong(price.get(TKUT_TAX_ENTR).toString()));
			if(price.get(OTH_TAX_ENTR) != null) dto.setOth_tax_entr(Long.parseLong(price.get(OTH_TAX_ENTR).toString()));			
			dto.setPrd_id(prdId);
		}
		return priceMap;
	}
	

	public List<Map<String, Object>> getTaxEntryList(final Long mktId) throws Exception {
		final String query = getDaoXml("getTaxEntry", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("vmarketid", mktId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getTaxEntry");
	}

	/*
	 * This method returns the pricing values from prd_prc and prd_tax table
	 * 
	 * @param productId,mktId,effectiveDate,setId
	 * 
	 * @return priceValues
	 */
	public List<Map<String, Object>> getPriceValuesWithTax(Long productId, Long mktId, String effectiveDate, long setId)
			throws Exception {
		final String query = getDaoXml("getPriceValuesWithTax", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(PRDID, productId);
		paramMap.put(MKT_ID, mktId);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
		paramMap.put(ProductDBConstant.SETID, setId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap, "getPriceValuesWithTax");
	}

	/*
	 * This method returns the pricing values from rest table
	 * 
	 * @param mktId,effectiveDate,restId,restInstId,nodeId
	 * 
	 * @return priceValues
	 */
	public PriceTax getPricingValueFromRest(Long mktId, String effectiveDate,  Long nodeId) throws Exception {
		final String query = getDaoXml("getPricingValueFromRest", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, mktId);		
		paramMap.put("nodeId", nodeId);
		List<Map<String, Object>> priceValues = Wizard.queryForList(dataSourceQuery, query, paramMap, "getPricingValueFromRest");
		PriceTax dto = new PriceTax();		
		for (final Map<String, Object> price : priceValues) {											
			if(price.get(EATIN_PRC) !=null) dto.setEatin_prc(Double.parseDouble(price.get(EATIN_PRC).toString()));
			if(price.get(TKUT_PRC) !=null) dto.setTkut_prc(Double.parseDouble(price.get(TKUT_PRC).toString()));
			if(price.get(OTH_PRC) !=null) dto.setOth_prc(Double.parseDouble(price.get(OTH_PRC).toString()));			
			if(price.get(EATIN_TAX_CD) !=null) dto.setEatin_tax_cd(Long.parseLong(price.get(EATIN_TAX_CD).toString()));
			if(price.get(TKUT_TAX_CD) !=null) dto.setTkut_tax_cd(Long.parseLong(price.get(TKUT_TAX_CD).toString()));
			if(price.get(OTH_TAX_CD) !=null) dto.setOth_tax_cd(Long.parseLong(price.get(OTH_TAX_CD).toString()));
			if(price.get(EATIN_TAX_RULE) !=null) dto.setEatin_tax_rule(Long.parseLong(price.get(EATIN_TAX_RULE).toString()));
			if(price.get(TKUT_TAX_RULE) !=null) dto.setTkut_tax_rule(Long.parseLong(price.get(TKUT_TAX_RULE).toString()));
			if(price.get(OTH_TAX_RULE) !=null) dto.setOth_tax_rule(Long.parseLong(price.get(OTH_TAX_RULE).toString()));
			if(price.get(EATIN_TAX_ENTR) !=null) dto.setEatin_tax_entr(Long.parseLong(price.get(EATIN_TAX_ENTR).toString()));
			if(price.get(TKUT_TAX_ENTR) !=null) dto.setTkut_tax_entr(Long.parseLong(price.get(TKUT_TAX_ENTR).toString()));
			if(price.get(OTH_TAX_ENTR) !=null) dto.setOth_tax_entr(Long.parseLong(price.get(OTH_TAX_ENTR).toString()));									
		}
		return dto;		
	}
	
	/*
	 * This method returns the tax values
	 * 
	 * @param productId,mktId,effectiveDate,setId,subsGrpId
	 * 
	 * @return taxValues
	 */
	public Map<Long, PriceTax> getMenuItemTaxSetValues(Map<Long, PriceTax> taxMap, long mktId, String effectiveDate, long setId) throws Exception {
		final String query = getDaoXml("getMenuItemTaxSetValues", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);		
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("setId", setId);	
		List<Map<String,Object>> resultTaxValues = Wizard.queryForList(dataSourceQuery, query, paramMap, "getMenuItemTaxSetValues");
		
		
		for (final Map<String, Object> tax : resultTaxValues) {						
			Long prdId = Long.parseLong(tax.get("prd_id").toString());
			PriceTax dto;
			
			if(taxMap.get(prdId)!=null) {
				dto =taxMap.get(prdId);			
			} else {					
				dto = new PriceTax();
				dto.setPrd_id(prdId);
				taxMap.put(prdId, dto);
			}

			if(tax.get("prd_tax_id") !=null) dto.setHasValue(true);			
			if(tax.get("EATIN_TAX_CD") !=null) dto.setEatin_tax_cd(Long.parseLong(tax.get("EATIN_TAX_CD").toString()));
			if(tax.get("TKUT_TAX_CD") !=null) dto.setTkut_tax_cd(Long.parseLong(tax.get("TKUT_TAX_CD").toString()));
			if(tax.get("OTH_TAX_CD") !=null) dto.setOth_tax_cd(Long.parseLong(tax.get("OTH_TAX_CD").toString()));
			if(tax.get("EATIN_TAX_RULE") !=null) dto.setEatin_tax_rule(Long.parseLong(tax.get("EATIN_TAX_RULE").toString()));
			if(tax.get("TKUT_TAX_RULE") !=null) dto.setTkut_tax_rule(Long.parseLong(tax.get("TKUT_TAX_RULE").toString()));
			if(tax.get("OTH_TAX_RULE") !=null) dto.setOth_tax_rule(Long.parseLong(tax.get("OTH_TAX_RULE").toString()));
			if(tax.get("EATIN_TAX_ENTR") !=null) dto.setEatin_tax_entr(Long.parseLong(tax.get("EATIN_TAX_ENTR").toString()));
			if(tax.get("TKUT_TAX_ENTR") !=null) dto.setTkut_tax_entr(Long.parseLong(tax.get("TKUT_TAX_ENTR").toString()));
			if(tax.get("OTH_TAX_ENTR") !=null) dto.setOth_tax_entr(Long.parseLong(tax.get("OTH_TAX_ENTR").toString()));			

		}
		return taxMap;
	}
	
	/*
	 * This method returns the tax values
	 * 
	 * @param productId,mktId,effectiveDate,setId,subsGrpId
	 * 
	 * @return taxValues
	 */
	public Map<Long, List<PriceTax>> getTaxValuesForSetId(Map<Long, Product> products, Map<Long, List<PriceTax>> taxMap, Long mktId, String effectiveDate, long setId) throws Exception {
		final String query = getDaoXml("getTaxValuesForSetId", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(MKT_ID, mktId);		
		paramMap.put(EFF_DATE, DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("setId", setId);	
		List<Map<String,Object>> taxValues = Wizard.queryForList(dataSourceQuery, query, paramMap, "getTaxValuesForSetId");
		
		
		for (final Map<String, Object> price : taxValues) {						
			Long subsGrpId = Long.parseLong(price.get(SUBS_GRP_ID).toString());
			Long prdId = Long.parseLong(price.get(PRD_ID).toString());
			Product product = products.get(prdId);
			
			if(product!=null && product.getActive()==1) {
				List<PriceTax> subsGrpList = new ArrayList<>();				
				PriceTax dto = new PriceTax();
				dto.setPrd_id(prdId);
				dto.setSubs_grp_id(subsGrpId);
				
				if(taxMap.get(subsGrpId)!=null) {
					subsGrpList =taxMap.get(subsGrpId);				
				} else {					
					taxMap.put(subsGrpId, subsGrpList);
				}
				
				if(price.get("prd_tax_id") !=null) dto.setHasValue(true);				
				if(price.get(EATIN_PRC) !=null) dto.setEatin_prc(Double.parseDouble(price.get(EATIN_PRC).toString()));
				if(price.get(TKUT_PRC) !=null) dto.setTkut_prc(Double.parseDouble(price.get(TKUT_PRC).toString()));
				if(price.get(OTH_PRC) !=null) dto.setOth_prc(Double.parseDouble(price.get(OTH_PRC).toString()));			
				if(price.get(EATIN_TAX_CD) !=null) dto.setEatin_tax_cd(Long.parseLong(price.get(EATIN_TAX_CD).toString()));
				if(price.get(TKUT_TAX_CD) !=null) dto.setTkut_tax_cd(Long.parseLong(price.get(TKUT_TAX_CD).toString()));
				if(price.get(OTH_TAX_CD) !=null) dto.setOth_tax_cd(Long.parseLong(price.get(OTH_TAX_CD).toString()));
				if(price.get(EATIN_TAX_RULE) !=null) dto.setEatin_tax_rule(Long.parseLong(price.get(EATIN_TAX_RULE).toString()));
				if(price.get(TKUT_TAX_RULE) !=null) dto.setTkut_tax_rule(Long.parseLong(price.get(TKUT_TAX_RULE).toString()));
				if(price.get(OTH_TAX_RULE) !=null) dto.setOth_tax_rule(Long.parseLong(price.get(OTH_TAX_RULE).toString()));
				if(price.get(EATIN_TAX_ENTR) !=null) dto.setEatin_tax_entr(Long.parseLong(price.get(EATIN_TAX_ENTR).toString()));
				if(price.get(TKUT_TAX_ENTR) !=null) dto.setTkut_tax_entr(Long.parseLong(price.get(TKUT_TAX_ENTR).toString()));
				if(price.get(OTH_TAX_ENTR) !=null) dto.setOth_tax_entr(Long.parseLong(price.get(OTH_TAX_ENTR).toString()));			
				
				subsGrpList.add(dto);
			}
		}
		return taxMap;
	}
	
	/*
	 * This method returns the Display Tax for Breakdown values
	 * 
	 * @param prdID,effectiveDate
	 * 
	 * @return dataForDisplayBRD
	 */
	public Map<Long, PriceTax> getDataForDisplayBRD(Map<Long, PriceTax> dataForDisplayBRD,Long setId, String effectiveDate) throws Exception {
		final String query = getDaoXml("getDataForDisplayBRD", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("setId", setId);
		paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
		
		List<Map<String,Object>> values = Wizard.queryForList(dataSourceQuery, query, paramMap, "getDataForDisplayBRD");
		
		
		for (final Map<String, Object> price : values) {
			Long prdId = Long.parseLong(price.get(PRD_ID).toString());
			
			PriceTax dto = new PriceTax();				
			if(dataForDisplayBRD.get(prdId)!=null) {
				dto = dataForDisplayBRD.get(prdId); 
			} else {
				dataForDisplayBRD.put(prdId, dto);			
			}
			if(price.get(EATIN_PRC) !=null) dto.setEatin_prc(Double.parseDouble(price.get(EATIN_PRC).toString()));
			if(price.get(TKUT_PRC) !=null) dto.setTkut_prc(Double.parseDouble(price.get(TKUT_PRC).toString()));
			if(price.get(OTH_PRC) !=null) dto.setOth_prc(Double.parseDouble(price.get(OTH_PRC).toString()));			
			if(price.get(EATIN_TAX_CD) !=null) dto.setEatin_tax_cd(Long.parseLong(price.get(EATIN_TAX_CD).toString()));
			if(price.get(TKUT_TAX_CD) !=null) dto.setTkut_tax_cd(Long.parseLong(price.get(TKUT_TAX_CD).toString()));
			if(price.get(OTH_TAX_CD) !=null) dto.setOth_tax_cd(Long.parseLong(price.get(OTH_TAX_CD).toString()));
			if(price.get(EATIN_TAX_RULE) !=null) dto.setEatin_tax_rule(Long.parseLong(price.get(EATIN_TAX_RULE).toString()));
			if(price.get(TKUT_TAX_RULE) !=null) dto.setTkut_tax_rule(Long.parseLong(price.get(TKUT_TAX_RULE).toString()));
			if(price.get(OTH_TAX_RULE) !=null) dto.setOth_tax_rule(Long.parseLong(price.get(OTH_TAX_RULE).toString()));
			if(price.get(EATIN_TAX_ENTR) !=null) dto.setEatin_tax_entr(Long.parseLong(price.get(EATIN_TAX_ENTR).toString()));
			if(price.get(TKUT_TAX_ENTR) !=null) dto.setTkut_tax_entr(Long.parseLong(price.get(TKUT_TAX_ENTR).toString()));
			if(price.get(OTH_TAX_ENTR) !=null) dto.setOth_tax_entr(Long.parseLong(price.get(OTH_TAX_ENTR).toString()));			
			if(price.get(SUBS_GRP_ID) !=null)dto.setSubs_grp_id(Long.parseLong(price.get(SUBS_GRP_ID).toString()));
			dto.setPrd_id(prdId);
		}

		return dataForDisplayBRD;
	}
	
	
	public Map<Long, Product> getProductDBDataForDimensionGroup(List<Map<String, Object>> localizationSets , Map<Long, Product> products ,String effDate,long marketID) throws Exception {

				Long parentSetId = null;
				if (localizationSets != null && !localizationSets.isEmpty() && localizationSets.get(0) != null
						&& localizationSets.get(0).get(PREN_SET_ID) != null) {
					parentSetId = null != localizationSets.get(0).get(PREN_SET_ID)
							? Long.valueOf(localizationSets.get(0).get(PREN_SET_ID).toString())
							: null;
				}
				// Fetching custSetId from the localizationsets
				Long childSetId = null;
				if (localizationSets != null && !localizationSets.isEmpty() && localizationSets.get(0) != null
						&& localizationSets.get(0).get(CUSM_SET_ID) != null) {
					childSetId = null != localizationSets.get(0).get(CUSM_SET_ID)
							? Long.valueOf(localizationSets.get(0).get(CUSM_SET_ID).toString())
							: null;
				}
				
				// Fetching DimensionGroup data
				final List<Map<String, Object>> productDataForDimensionGroup = getProductDataForDimensionGroup(parentSetId,
						childSetId, effDate, marketID);
				Map<Long, List<Size>> groupByDimensionID = new HashMap<>();
				for (final Map<String, Object> productData : productDataForDimensionGroup) {
					Size size = new Size();
					Long productID = Long.parseLong(productData.get(PRD_ID).toString());
					Long dimnId = null;
					size.setProductId(productID);
					if (productData.get(PRD_CD) != null)
						size.setCode(productData.get(PRD_CD).toString());

					if (productData.get("CD") != null)
						size.setEntry(productData.get("CD").toString());

					if (productData.get("SHW_ON_RCT_LCL_PROMO") != null)
						size.setShowDimensionOnRCTLocalPromotion(productData.get("SHW_ON_RCT_LCL_PROMO").toString());
					else 
						size.setShowDimensionOnRCTLocalPromotion("false");

					if (productData.get("SHOW_DIM_TO_CUST") != null)
						size.setShowDimensionToCustomer(productData.get("SHOW_DIM_TO_CUST").toString());
					
					if (productData.get("DIMN_ID") != null) {
						dimnId = Long.parseLong(productData.get("DIMN_ID").toString());
					}
					
					if (groupByDimensionID.containsKey(dimnId)) {
						groupByDimensionID.get(dimnId).add(size);
					}else {
						List<Size> sizeList = new ArrayList<>();
						sizeList.add(size);
						groupByDimensionID.put(dimnId, sizeList);
					}
					
					Product product = products.get(productID);
					if (product != null)
						product.setSizeSelection(groupByDimensionID.get(dimnId));

				}
				return products;			
	}

	public Map<Long, Product> getMenuSubstitutionList(Map<Long, Product> products, final long mkt_id) throws Exception {
		final String query = getDaoXml(ProductDBConstant.GET_SUBSTITUTIONLIST, DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(ProductDBConstant.MKT_ID, mkt_id);			
		List<Map<String, Object>> substitutionVal = Wizard.queryForList(dataSourceQuery, query, paramMap, ProductDBConstant.GET_SUBSTITUTIONLIST);

		for (final Map<String, Object> productData : substitutionVal) {
			Long prdId = Long.parseLong(productData.get(PRD_ID).toString());

			Product product = products.get(prdId);

			Item item = new Item();
			item.setId(productData.get("ITM_ID").toString());
			item.setProductCode(productData.get("PRD_CODE").toString());
			item.setName(productData.get("SUBS_GRP_NA").toString());
			item.setGroupName(productData.get("SUBS_GRP_NA").toString());
			item.setGroupId(productData.get(SUBS_GRP_ID).toString());
			item.setProductId(Long.parseLong(productData.get("sub_PRD_ID").toString()));
			if(null!=product) {
				product.getSubstitutionList().add(item);
			}
		}			
		return products;
	}
	
	public Map<Long, Product> getCytGroupDisplayOrder(Map<Long, Product> products, Long marketID, String effDate) throws Exception {
		final String query = getDaoXml("getCytGroupDisplayOrder", DAOResources.PRODUCT_DB_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(ProductDBConstant.MKT_ID, marketID);
		paramMap.put(EFF_DATE, DateUtility.convertStringToDate(effDate));
		List<Map<String, Object>> cytGroupDisplayOrderValues = Wizard.queryForList(dataSourceQuery, query, paramMap, "getCytGroupDisplayOrder");
		Product product = new Product();
		for (final Map<String, Object> productData : cytGroupDisplayOrderValues) {
			Long prdId = Long.parseLong(productData.get(PRD_ID).toString());
			product = products.get(prdId);
			
			if(product!=null) {
			CytGroupDisplayOrder cytGroupDisplayOrder = new CytGroupDisplayOrder();
			cytGroupDisplayOrder.setGroup(productData.get("ingr_grp_na").toString());
			if(product.getProductSmartRouting().getCytGroupDisplayOrder()==null) {
				product.getProductSmartRouting().setCytGroupDisplayOrder(new ArrayList<>());
				product.getProductSmartRouting().getCytGroupDisplayOrder().add(cytGroupDisplayOrder);
			}else if(product.getProductSmartRouting().getCytGroupDisplayOrder()!=null) {
				product.getProductSmartRouting().getCytGroupDisplayOrder().add(cytGroupDisplayOrder);
			}
			products.put(prdId, product);
			}
		}	
		return products;
	}


	public List<Map<String, Object>> getPricingValuesBysetId(final Long prodID, final Long mktId, String effectiveDate,
				long retrievedsetId) throws Exception {
	final String query = getDaoXml("getPricingValuesBysetId", DAOResources.PRODUCT_DB_DAO);
			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put(PRDID, prodID);
	paramMap.put(MKT_ID, mktId);
			paramMap.put(EFFECTIVE_DATE, DateUtility.convertStringToDate(effectiveDate));
			paramMap.put("retrievedsetId", retrievedsetId);
			return Wizard.queryForList(dataSourceQuery, query, paramMap, "getPricingValuesBysetId");
	}

}
