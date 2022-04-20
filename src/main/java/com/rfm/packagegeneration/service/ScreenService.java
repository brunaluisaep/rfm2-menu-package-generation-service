package com.rfm.packagegeneration.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rfm.packagegeneration.cache.CacheService;
import com.rfm.packagegeneration.cache.KeyNameHelper;
import com.rfm.packagegeneration.constants.GeneratorConstant;
import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.constants.ScreenDBConstant;
import com.rfm.packagegeneration.dao.LayeringProductDBDAO;
import com.rfm.packagegeneration.dao.NamesDBDAO;
import com.rfm.packagegeneration.dao.ProductDBDAO;
import com.rfm.packagegeneration.dao.ScreenDAO;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductDetails;
import com.rfm.packagegeneration.dto.RequestDTO;
import com.rfm.packagegeneration.dto.Restaurant;
import com.rfm.packagegeneration.dto.ScreenDetails;
import com.rfm.packagegeneration.utility.ObjectUtils;
import com.rfm.packagegeneration.utility.PackageWriter;

@Service
public class ScreenService {
	private static final int INT_ONE_CONSTANT=1;
	private static final int INT_TWO_CONSTANT=2;
	private static final int INT_ZERO_CONSTANT=0;
	//private static final String KEY_RESTAURANT_ID="restaurantId";
	//private static final String KEY_RESTAURANT_INST_ID="restaurantInstId";
	private static final String TRUE_UPPER_CASE = "TRUE";
	public static final String SCREEN_POS = "POS";
	public static final String SCREEN_CSO = "CSO";
	public static final int SCREEN_POS_DATA_TYP = 14;
	public static final int SCREEN_HOT_DATA_TYP = 15;
	public static final String SCREEN_HOT = "HOT";
	public static final String SCREEN_POS_FILENAME = "/screen.xml";
	public static final String SCREEN_HOT_FILENAME = "/screenHot.xml";
	public static  final String SCHEMA_TYPE_SCREEN = "screen";
	public static final  String SCHEMA_TYPE_SCREENHOT = "screenHOT";
	public static final  String SCHEMA_TYPE_WORKFLOW = "workflow";
	public static final  String EMPTY_STRING = "";
	private static String IGNORE_DEFAULT_FORCEWORKFLOW = "ignore_force_defaultWorkflow";
	
	@Autowired
	ScreenDAO screenDAO;
	@Autowired
	NamesDBDAO objNames;
	@Autowired
	LogDetailsService logDetailsService;
	
	@Autowired
	ProductDBService productDBService;
	
	@Autowired
	CacheService cacheService;
	@Autowired
	LayeringProductService layeringService;
	@Autowired
	LayeringProductDBDAO layeringDBDAO;
	@Autowired
	ProductDBDAO productDBDAO;
	@Value("${application.redis.cacheTTL}")
	private Long cacheTTLInMinutes;
	
	private final Object object=new Object();
	
	private static final Logger LOGGER = LogManager.getLogger("ScreenService"); 

	public Map<String,List<Map<String, String>>> getButtonWorkflowParameters(List<Long> buttonWorkflowAssignIds) throws Exception{	
		return screenDAO.getButtonWorkflowParameters(buttonWorkflowAssignIds);
	}
	
	public 	Map<String,Map<String,Map<String,String>>> getMasterButtonDetails(List<Long> screenIds, List<Long> screenInstIds) throws Exception{	
		return screenDAO.getMasterButtonDetails(screenIds,screenInstIds);
	}
	
	public Map<String,Map<String,Map<String,String>>> getButtonLangDetails(List<Long> screenIds, List<Long> screenInstIds, List<Long> langIds, String restaurantDefLang) throws Exception{	
		return screenDAO.getButtonLangDetails(screenIds,screenInstIds,langIds, restaurantDefLang);
	}
	
	public List<Map<String, String>> getAllLocales(Long mktId,String marketLocaleId, String effectiveDate  ) throws Exception {
		return screenDAO.getAllLocales(mktId,marketLocaleId,effectiveDate);
	}
	
   public Map<String, List<Map<String, String>>> getScreenWorkflows(List<Long> screenIds, List<Long> screenInstIds) throws Exception{
		return screenDAO.getScreenWorkflows(screenIds, screenInstIds);
   }
   
   public  Map<String,List<Map<String, String>>> getScreenWorkflowParameters(List<Long> screenWorkflowAssignId) throws Exception{
	   return screenDAO.getScreenWorkflowParameters(screenWorkflowAssignId);
   }
   
   public Map<String, String> getScreenLookupParameter(List<Long> liskWorkflowParams, String marketLocaleId) throws Exception{
	   return screenDAO.getScreenLookupParameter(liskWorkflowParams,marketLocaleId);
   }
   
   public List<String> getRestaurantMenuItems(Long uniqueId,String effectiveDate) throws Exception{
	   return screenDAO.getRestaurantMenuItems(uniqueId, effectiveDate);
   }
   
   public Map<String, Map<String, Map<String, String>>> getDynamicButtonDetails(List<Long> screenIds, List<Long> screenInstIds,Map<Long, ProductDetails> productCodeDtlsRelationMap,String effectiveDate) throws Exception{
	   return screenDAO.getDynamicButtonDetails(screenIds, screenInstIds, productCodeDtlsRelationMap, effectiveDate);
   }
   
	public List<String> getHotScreens(String scheduleRequestID ,Long nodeId,String effectiveDate) throws Exception
	{
		String hotscrnCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ScreenDBConstant.REDIS_HOT_SCREEN);
		List<String> hotscrdataMap = cacheService.getHotScreensData(hotscrnCacheKey);
		if(hotscrdataMap == null || (hotscrdataMap != null && hotscrdataMap.isEmpty())) {
			LOGGER.info("Cache miss key {}", hotscrnCacheKey);
			hotscrdataMap = screenDAO.getHotScreens();
			cacheService.setHotScreensData(hotscrnCacheKey, hotscrdataMap, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return hotscrdataMap;
	}
	
	
	public String getDefaultButtonCaption(String scheduleRequestID,Long mktId) throws Exception
	{
		String defaultButtoncapCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ScreenDBConstant.REDIS_BUTTON_CAPTION);
		String defaultButtoncapMap = cacheService.getDefaultButtonCaptionData(defaultButtoncapCacheKey);
		if(defaultButtoncapMap == null) {
			LOGGER.info("Cache miss key {}", defaultButtoncapCacheKey);
			defaultButtoncapMap = screenDAO.getDefaultButtonCaption(mktId);
			cacheService.setDefaultButtonCaptionData(defaultButtoncapCacheKey, defaultButtoncapMap, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return defaultButtoncapMap;
	}
	
	public List<String> getDynamicWorkflowParameters(String scheduleRequestID,Long mktId,String workflowName) throws Exception
	{	 
		String dynwrkfParmCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ScreenDBConstant.REDIS_DYNAMIC_WORKFLOW_PARAM);
		List<String> dynwrkfParmdataMap = cacheService.getDynamicWrkflwParamData(dynwrkfParmCacheKey);
		if(dynwrkfParmdataMap == null || (dynwrkfParmdataMap != null && dynwrkfParmdataMap.isEmpty())) {
			LOGGER.info("Cache miss key {}", dynwrkfParmCacheKey);
			dynwrkfParmdataMap = screenDAO.getDynamicWorkflowParameters(mktId,workflowName);
			cacheService.setDynamicWrkflwParamData(dynwrkfParmCacheKey, dynwrkfParmdataMap, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return dynwrkfParmdataMap;
	}
	
	public Map<String,String> getLocalizationSets(Long restId,Long restInstId,Long marketId) throws Exception
	{	
		return screenDAO.getLocalizationSets(restId.toString(), restInstId.toString(), marketId);
	}
	
	public Map<String, String> getLocalizedFields(Long restId,Long restInstId, String effectiveDate, Long marketId) throws Exception
	{	Map<String, String> allLocalizedFields=new HashMap<String, String>();
		Map<String, String> localizedFields=new HashMap<String, String>();
		Long parentSetId=null;
		Long childSetId=null;
		Map<String, String> localizationSets=getLocalizationSets(restId, restInstId,marketId);
		
		for (Map.Entry<String,String> localizationSet : localizationSets.entrySet()) 
		{
			if (localizationSet.getKey()!=null && localizationSet.getKey().equals("PREN_SET_ID")) 
			{
				parentSetId=Long.parseLong(localizationSet.getValue());
			}
			if (localizationSet.getKey()!=null && localizationSet.getKey().equals("CUSM_SET_ID")) 
			{
				childSetId=Long.parseLong(localizationSet.getValue());
			}
			
			localizedFields=screenDAO.getLocalizedFields(parentSetId, childSetId, effectiveDate);
		}
            
		allLocalizedFields.putAll(localizedFields);
		return allLocalizedFields;
	}

	public Map<String, Long> getScheduleSize(String scheduleRequestID ,Long mktId) throws Exception {
		LOGGER.info("ScreenService :: getScheduleSize()");
		return screenDAO.getScheduleSize(scheduleRequestID,mktId);
	}

	public Map<String,String> getAllMedia(String scheduleRequestID, Long marketID ) throws Exception {
		LOGGER.info("ScreenService :: getAllMedia()");
		Map<String,String> allMediaMap=new HashMap<String,String>();
		final List<Map<String, Object>> allMediaList= productDBService.getProductMediaData(scheduleRequestID, marketID);
		for (final Map<String, Object> mediaProductData : allMediaList) {
			allMediaMap.put((mediaProductData.get("mdia_id").toString()),
					(mediaProductData.get("mdia_file_na").toString()));
		}
		return allMediaMap;
	}

	public String getDefaultLocaleId(String scheduleRequestID,Long mktId) throws Exception {
		String LocaleIdCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ScreenDBConstant.REDIS_MKT_LCL);
		String LocaleIdMap = cacheService.getDefaultLocaleId(LocaleIdCacheKey);
		if(LocaleIdMap == null) {
			LOGGER.info("Cache miss key {}", LocaleIdCacheKey);
			LocaleIdMap = screenDAO.getDefaultLocaleId(mktId);
			cacheService.setDefaultLocaleId(LocaleIdCacheKey, LocaleIdMap, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return LocaleIdMap;		
	}

	public Map<String,String> getAllColors(String scheduleRequestID, Long marketID ) throws Exception {
		LOGGER.info("ScreenService :: getAllColors()");
		Map<String,String> allColors=new HashMap<String,String>();
		final List<Map<String, Object>> listOfResults =productDBService.getProductColorData(scheduleRequestID, marketID);
		for (final Map<String, Object> data : listOfResults) {
			allColors.put((data.get("colr_id") + "," + data.get("lcle_id")), data.get("colr_na").toString());
		}
		return allColors;
	}
	
	public Map<String,Map<String, Map<String, String>>> getBLMButtonDetails(Map<String, Map<String, String>> masterScreensInfo, String screenSetName, String nodeId,
			String effectiveDate) throws Exception{		
		final ArrayList<Long> screenIds = new ArrayList<>();
		final ArrayList<Long> screenInstanceIds = new ArrayList<>();
		getScreenIds(masterScreensInfo, screenIds, screenInstanceIds);
				
		Map<String,Map<String, Map<String, String>>>  blmButtons = screenDAO.getBLMButtonDetails(screenIds, screenSetName, nodeId, effectiveDate);		
		return 	blmButtons;	
	}
	
	public Map<String, Map<String, String>>  getScreenDetails(Long screenSetId, String effectiveDate) throws Exception {
		final Map<String, Map<String, String>> masterScreensDetails =  screenDAO.getScreenDetails(screenSetId,  effectiveDate);
		return  masterScreensDetails ;	
	}
	
	@SuppressWarnings("deprecation")
	private void getScreenIds(final Map<String, Map<String, String>> masterScreensInfo, final ArrayList<Long> screenIds, final ArrayList<Long> screenInstanceIds) {

		for (final String screenId : masterScreensInfo.keySet()) {
			final Map<String, String> masterScreenInfo = masterScreensInfo.get(screenId);
			screenIds.add(new Long(screenId));
           screenInstanceIds.add(new Long(masterScreenInfo.get("INST")));
		}
	}
	
	public Map<String, String> getAssignedScreenSet(Long marketId, Long restId, Long restInstId, String effectiveDate) throws Exception {
		LOGGER.info("ScreenService :: getAssignedSets()");
		return screenDAO.getAssignedScreenSet(marketId,restId.toString(),restInstId.toString(), effectiveDate);
	}
	
	public Map<String, String> getAllWorkflows(String scheduleRequestID,Long mktId) throws Exception {
		String WorkflowsCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ScreenDBConstant.REDIS_ALL_WORKFLOWS);
		 Map<String, String> WorkflowsMap = cacheService.getAllWorkflows(WorkflowsCacheKey);
		if(WorkflowsMap == null || (WorkflowsMap != null && WorkflowsMap.isEmpty())) {
			LOGGER.info("Cache miss key {}", WorkflowsCacheKey);
			WorkflowsMap = screenDAO.getAllWorkflows(mktId);
			cacheService.setAllWorkflows(WorkflowsCacheKey, WorkflowsMap, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return WorkflowsMap;
	}

	public Map<String, String> getAllEvents(String scheduleRequestID,Long mktId) throws Exception {
		String EventsCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ScreenDBConstant.REDIS_EVENTS);
		 Map<String, String> EventsMap = cacheService.getAllEvents(EventsCacheKey);
		if(EventsMap == null || (EventsMap != null && EventsMap.isEmpty())) {
			LOGGER.info("Cache miss key {}", EventsCacheKey);
			EventsMap = screenDAO.getAllEvents(mktId);
			cacheService.setAllEvents(EventsCacheKey, EventsMap, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return EventsMap;	
	}


	public Map<String, String> getAllWorkflowParams(String scheduleRequestID,Long mktId) throws Exception {
		String WorkflowParamsCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ScreenDBConstant.REDIS_ALL_WORKFLOW_PARAMS);
		 Map<String, String> WorkflowParamsMap = cacheService.getAllWorkflowParams(WorkflowParamsCacheKey);
		if(WorkflowParamsMap == null || (WorkflowParamsMap != null && WorkflowParamsMap.isEmpty())) {
			LOGGER.info("Cache miss key {}", WorkflowParamsCacheKey);
			WorkflowParamsMap = screenDAO.getAllWorkflowParams(mktId);
			cacheService.setAllWorkflowParams(WorkflowParamsCacheKey, WorkflowParamsMap, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return WorkflowParamsMap;
	}

	public Map<String, String> getAllGrillGroups(String scheduleRequestID,Long mktId) throws Exception {
		String grillGrpCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ScreenDBConstant.REDIS_GRILL_GROUPS);
		 Map<String, String> allgrillGrpMap = cacheService.getAllGrillGroupsData(grillGrpCacheKey);
		if(allgrillGrpMap == null || (allgrillGrpMap != null && allgrillGrpMap.isEmpty())) {
			LOGGER.info("Cache miss key {}", grillGrpCacheKey);
			allgrillGrpMap = screenDAO.getAllGrillGroups(mktId);
			cacheService.setAllGrillGroupsData(grillGrpCacheKey, allgrillGrpMap, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return allgrillGrpMap;
	}

	public Map<String, String> getAllSmartReminders(String scheduleRequestID,Long mktId) throws Exception {
		String smartReminderCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ScreenDBConstant.REDIS_SMART_REMINDERS);
		 Map<String, String> allsmartReminderMap = cacheService.getAllSmtReminderData(smartReminderCacheKey);
		if(allsmartReminderMap == null || (allsmartReminderMap != null && allsmartReminderMap.isEmpty())) {
			LOGGER.info("Cache miss key {}", smartReminderCacheKey);
			allsmartReminderMap = screenDAO.getAllSmartReminders(mktId);
			cacheService.setAllSmtReminderData(smartReminderCacheKey, allsmartReminderMap, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return allsmartReminderMap;
	}
	
	
	
	/**
	 * Used to generate, validate and write XML file for a particular effective date.
	 * 
	 * @param objPackageGeneratorDTO DTO
	 * @return boolean[]
	 * @throws GeneratorBusinessException Exception
	 * @throws GeneratorDBException Exception
	 * @throws GeneratorExternalInterfaceException GeneratorExternalInterfaceException
	 */
	public boolean[] generateFile(final PackageGeneratorDTO objPackageGeneratorDTO)  {
		boolean[] arrResult = new boolean[INT_TWO_CONSTANT];

		/** *Get Screen Data Pointers from Restraunt Manifest** */
		final Map<String, List<ScreenDetails>> hmScreenDetails = objPackageGeneratorDTO.getScreens();
		final List<ScreenDetails> listScreenDetailsPos = hmScreenDetails.get(SCREEN_POS);
		// added for CQ 3601
		if (null != hmScreenDetails.get(SCREEN_CSO)) {
			for (final ScreenDetails scr : hmScreenDetails.get(SCREEN_CSO)) {
				listScreenDetailsPos.add(scr);
			}
		}
		
		final List<ScreenDetails> listScreenDetailsHot =hmScreenDetails.get(SCREEN_HOT);
		
		arrResult = generateScreenXML(listScreenDetailsPos, listScreenDetailsHot, objPackageGeneratorDTO);
		return arrResult;
	}
	
	/**
	 * @param listScreenDetailsPos List<ScreenDetails>
	 * @param listScreenDetailsHot List<ScreenDetails>
	 * @param objPackageGeneratorDTO PackageGeneratorDTO
	 * @return boolean[]
	 * @throws GeneratorDBException GeneratorDBException
	 * @throws GeneratorExternalInterfaceException GeneratorExternalInterfaceException
	 * @throws GeneratorBusinessException GeneratorBusinessException
	 */
	private boolean[] generateScreenXML(final List<ScreenDetails> listScreenDetailsPos, final List<ScreenDetails> listScreenDetailsHot, final PackageGeneratorDTO packageGeneratorDTO)  {
		final boolean[] result = new boolean[2];
		try {
			final Long startTs = System.currentTimeMillis();
			final org.dom4j.Element elError = packageGeneratorDTO.getGeneratorDefinedValues().getErrorElement();

			final String uniqueId = packageGeneratorDTO.getPackageStatusID().toString();
			if (packageGeneratorDTO.getScheduleType().compareTo("5") == 0) {
				if ((listScreenDetailsPos.size() > INT_ZERO_CONSTANT) || (listScreenDetailsHot.size() > INT_ZERO_CONSTANT)) {
					listScreenDetailsPos.addAll(listScreenDetailsHot);

					final Map<String, String> screenMap = 
						screenDAO.createScreenXMLinDB(listScreenDetailsPos, SCREEN_POS, 
												elError, uniqueId, packageGeneratorDTO);

					if (TRUE_UPPER_CASE.equals(screenMap.get("SCREEN_GENERATED"))) {
						result[INT_ZERO_CONSTANT] = true;
					}

					if (TRUE_UPPER_CASE.equals(screenMap.get("HOT_GENERATED"))) {
						result[INT_ONE_CONSTANT] = true;
					}

				}
			} else {
				//final NamesDBDAO objNames = new NamesDBDAO();
				final PackageWriter screenBuffer = new PackageWriter(SCREEN_POS_FILENAME, SCREEN_POS, packageGeneratorDTO, SCHEMA_TYPE_SCREEN);
				final PackageWriter hotBuffer = new PackageWriter(SCREEN_HOT_FILENAME, SCREEN_HOT, packageGeneratorDTO, SCHEMA_TYPE_SCREENHOT);

				boolean generateHOT = false;
				boolean generateScreen = false;
				final Long marketId = packageGeneratorDTO.getMarketID();
				final String effectiveDate = packageGeneratorDTO.getDate();
				final Map<String, String> screenSet = screenDAO.getAssignedScreenSet(marketId, packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString(), 
						packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString(), effectiveDate);
				final Long screenSetId = Long.valueOf(screenSet.get("SET_ID"));
				final String screenSetName = screenSet.get("SET_NA");
				final Long currentInstanceId = Long.valueOf(screenSet.get("SET_INST"));

				final String scheduleDataID = packageGeneratorDTO.getScheduleRequestID();
				
				final String restaurantId = packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString();
				final String restaurantInstanceId = packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString();
//				cacheHandler = (RFM2DynaCacheHandler) CacheFactory.getCacheHandler(CacheFactory.DEFAULT);
				Map<String, String> allMedia = new HashMap<String, String>(); // cacheHandler.get(scheduleDataID + "Media", SYSTEM_CACHE);
				Map<String, String> allColors= new HashMap<String, String>(); // = (HashMap<String, String>) cacheHandler.get(scheduleDataID + "Color", SYSTEM_CACHE);
				Map<String, String> allEvents= cacheService.getAllEvents(scheduleDataID + "Events"); // = (HashMap<String, String>) cacheHandler.get(scheduleDataID + "Events", SYSTEM_CACHE);
				Map<String, String> allWorkflows= cacheService.getAllWorkflows(scheduleDataID + "Workflows"); // = (HashMap<String, String>) cacheHandler.get(scheduleDataID + "Workflows", SYSTEM_CACHE);
				Map<String, String> allWorkflowParams=cacheService.getAllWorkflowParams(scheduleDataID + "WorkflowParams");// = (HashMap<String, String>) cacheHandler.get(scheduleDataID + "WorkflowParams", SYSTEM_CACHE);
				Map<String, String> allGrillGroups=  cacheService.getAllGrillGroupsData(scheduleDataID + "GrillGroups");  // = (HashMap<String, String>) cacheHandler.get(scheduleDataID + "GrillGroups", SYSTEM_CACHE);
				Map<String, String> allSmartReminders=  cacheService.getAllSmtReminderData(scheduleDataID + "SmartReminders");  // = (HashMap<String, String>) cacheHandler.get(scheduleDataID + "SmartReminders", SYSTEM_CACHE);
				List<String> hotScreens= cacheService.getHotScreensData(scheduleDataID + "hotScreens"); // = (ArrayList<String>) cacheHandler.get(scheduleDataID + "hotScreens", SYSTEM_CACHE);
				String marketLocaleId=cacheService.getDefaultLocaleId(scheduleDataID + "MktLcl");// = (String) cacheHandler.get(scheduleDataID + "MktLcl", SYSTEM_CACHE);
				String globalLocaleId=cacheService.getDefaultLocaleId(scheduleDataID + "GblLcl");// = (String) cacheHandler.get(scheduleDataID + "GblLcl", SYSTEM_CACHE);
				List<String> dynamicWorkflowParams = cacheService.getDynamicWrkflwParamData(scheduleDataID + "DynmcWrkflParams");// cacheHandler.get(scheduleDataID + "DynmcWrkflParams", SYSTEM_CACHE);
				String defaultButtonCaption=cacheService.getDefaultButtonCaptionData(scheduleDataID + "ButtonCaption");// = (String) cacheHandler.get(scheduleDataID + "ButtonCaption", SYSTEM_CACHE);
				final List<String> kioskScreens = Arrays.asList("2800", "2801", "2802", "2803", "2804", "2805", "2806", "2850", "2851", "2852", "2853", "2854", "2950", "2951", "2952", "2953", "2954", "2955", "2957", "2958", "2959", "2960");
				int cacheTime = 1800;
				Map<String, Long> scheduleSizeMap =screenDAO.getScheduleSize(scheduleDataID,marketId );
	               final Long scheduleSize=2L;//scheduleSizeMap.get("scheduleSize");

				//fetch and put in cache if not found
				if (scheduleSize >= 5000) {
					cacheTime = 18000;
				} else if ((scheduleSize >= 2000) && (scheduleSize < 5000)) {
					cacheTime = 18000;
				} else if ((scheduleSize >= 500) && (scheduleSize < 2000)) {
					cacheTime = 12000;
				} else {
					cacheTime = 12000;
				}

				if (LOGGER.isDebugEnabled()) {
					final StringBuilder strBuilder = new StringBuilder();
					strBuilder.append("Thread").append(Thread.currentThread().getId());
					strBuilder.append(" @NodeId:").append(packageGeneratorDTO.getNodeID());
					strBuilder.append(" @UniqueId:").append(packageGeneratorDTO.getPackageStatusID());
					strBuilder.append(" @scheduleDataID:").append(scheduleDataID);
					strBuilder.append(" @marketId:").append(marketId);
					strBuilder.append(" @restaurantId:").append(restaurantId);
					strBuilder.append(" @screenSetName:").append(screenSetName);
					strBuilder.append(" @effectiveDate:").append(effectiveDate);
					strBuilder.append(" @cacheTime:").append(cacheTime);
					strBuilder.append(" @scheduleSize:").append(scheduleSize);
					LOGGER.debug(strBuilder.toString());
				}

				final PackageXMLParametersDTO[] params = packageGeneratorDTO.getPackageXmlParameter();
				String screenVersion = null;
				String posWorkflow = null;
				for (final PackageXMLParametersDTO param : params) {
					if ((null != param) && ((null == screenVersion) || (null == posWorkflow))) {
						if ("PACKAGE_DATA_SCREEN_DB_XML_VERSION".equalsIgnoreCase(param.getParm_na())) {
							screenVersion = param.getParm_val();
						} else if ("POS_LAYOUT_WORKFLOW".equalsIgnoreCase(param.getParm_na())) {
							posWorkflow = param.getParm_val();
						}
					}
				}

				if ((null == allMedia) || ((null != allMedia) && allMedia.isEmpty()) || (scheduleSize == 1)) {
					allMedia = getAllMedia(scheduleDataID,marketId);
					if (scheduleSize > 1) {
					//cacheHandler.put(Thread.currentThread().getId(), scheduleDataID+"Media",allMedia,cacheTime, SYSTEM_CACHE);
					}
				}

				if ((null == marketLocaleId) || ((null != marketLocaleId) && !isFilled(marketLocaleId)) || (scheduleSize == 1)) {
					marketLocaleId = getDefaultLocaleId(scheduleDataID,marketId);
					if (scheduleSize > 1) {
						cacheService.setDefaultLocaleId(scheduleDataID + "MktLcl", marketLocaleId, Duration.ofSeconds(cacheTime));
				//	cacheHandler.put(Thread.currentThread().getId(), scheduleDataID+"MktLcl",marketLocaleId,cacheTime, SYSTEM_CACHE);
					}
				}

				if ((null == globalLocaleId) || ((null != globalLocaleId) && !isFilled(globalLocaleId)) || (scheduleSize == 1)) {
					globalLocaleId = screenDAO.getDefaultLocaleId(0L);
					if (scheduleSize > 1) {
						cacheService.setDefaultLocaleId(scheduleDataID + "GblLcl", globalLocaleId, Duration.ofSeconds(cacheTime));
				//	cacheHandler.put(Thread.currentThread().getId(), scheduleDataID+"GblLcl",globalLocaleId,cacheTime, SYSTEM_CACHE);
					}
				}

				if ((null == allColors) || ((null != allColors) && allColors.isEmpty()) || (scheduleSize == 1)) {
					allColors = getAllColors(scheduleDataID,marketId);
					if (scheduleSize > 1) {
				//	cacheHandler.put(Thread.currentThread().getId(), scheduleDataID+"Color",allColors,cacheTime, SYSTEM_CACHE);
					}
				}

				if ((null == allWorkflows) || ((null != allWorkflows) && allWorkflows.isEmpty()) || (scheduleSize == 1)) {
					allWorkflows = screenDAO.getAllWorkflows(marketId);
					if ((allWorkflows != null) && LOGGER.isDebugEnabled()) {
						LOGGER.debug("allWorkflows size: " + allWorkflows.size());
					}
					if (scheduleSize > 1) {
						cacheService.setAllWorkflows(scheduleDataID + "Workflows", allWorkflows, Duration.ofSeconds(cacheTime));
					}
				}

				if ((null == allEvents) || ((null != allEvents) && allEvents.isEmpty()) || (scheduleSize == 1)) {
					allEvents = screenDAO.getAllEvents(marketId);
					if (scheduleSize > 1) {
						cacheService.setAllWorkflows(scheduleDataID + "Events", allEvents, Duration.ofSeconds(cacheTime));
					}
				}

				if ((null == allWorkflowParams) || ((null != allWorkflowParams) && allWorkflowParams.isEmpty()) || (scheduleSize == 1)) {
					allWorkflowParams = screenDAO.getAllWorkflowParams(marketId);
					if ((allWorkflowParams != null) && LOGGER.isInfoEnabled()) {
						LOGGER.info("allWorkflowParams size: " + allWorkflowParams.size());
					}
					if (scheduleSize > 1) {
					cacheService.setAllWorkflowParams(scheduleDataID + "WorkflowParams", allWorkflowParams, Duration.ofSeconds(cacheTime));
					}
				}

				if ((null == allGrillGroups) || ((null != allGrillGroups) && allGrillGroups.isEmpty()) || (scheduleSize == 1)) {
					allGrillGroups = screenDAO.getAllGrillGroups(marketId);
					if (scheduleSize > 1) {
						cacheService.setAllGrillGroupsData(scheduleDataID + "GrillGroups", allGrillGroups, Duration.ofSeconds(cacheTime));
					}
				}

				if ((null == allSmartReminders) || ((null != allSmartReminders) && allSmartReminders.isEmpty()) || (scheduleSize == 1)) {
					allSmartReminders = screenDAO.getAllSmartReminders(marketId);
					if (scheduleSize > 1) {
						cacheService.setAllSmtReminderData(scheduleDataID + "SmartReminders", allSmartReminders, Duration.ofSeconds(cacheTime));
					}
				}

				if ((null == hotScreens) || ((null != hotScreens) && hotScreens.isEmpty()) || (scheduleSize == 1)) {
					hotScreens = screenDAO.getHotScreens();
					if (scheduleSize > 1) {
						cacheService.setHotScreensData(scheduleDataID + "hotScreens", hotScreens, Duration.ofSeconds(cacheTime));
					}
				}

				if ((null == defaultButtonCaption) || ((null != defaultButtonCaption) && !isFilled(defaultButtonCaption)) || (scheduleSize == 1)) {
					defaultButtonCaption = screenDAO.getDefaultButtonCaption(marketId);
					if (scheduleSize > 1) {
						cacheService.setDefaultButtonCaptionData(scheduleDataID + "ButtonCaption", defaultButtonCaption, Duration.ofSeconds(cacheTime));
					}
				}

				if ((null == dynamicWorkflowParams) || ((null != dynamicWorkflowParams) && dynamicWorkflowParams.isEmpty()) || (scheduleSize == 1)) {
					if (!isFilled(posWorkflow)) {
						posWorkflow = "WF_DoSale";
					}
					dynamicWorkflowParams = screenDAO.getDynamicWorkflowParameters(marketId,posWorkflow);
					if (scheduleSize > 1) {
						cacheService.setDynamicWrkflwParamData(scheduleDataID + "DynmcWrkflParams", dynamicWorkflowParams, Duration.ofSeconds(cacheTime));				
					}
				}

				//Using these methods from NamesDBGenerator. getLocalizedFields is created in screen
				final Map<String, String> localizationSets = screenDAO.getLocalizationSets(restaurantId.toString(), restaurantInstanceId.toString(),packageGeneratorDTO.getMarketID());
				final Long parentSetId = null != localizationSets.get("PREN_SET_ID") ? Long.valueOf(localizationSets.get("PREN_SET_ID")) : null;
				final Long childSetId = null != localizationSets.get("CUSM_SET_ID") ? Long.valueOf(localizationSets.get("CUSM_SET_ID")) : null;
				Long langSetId = parentSetId;
				final Map<String, String> localizedFields = screenDAO.getLocalizedFields(parentSetId, childSetId, effectiveDate);
			LOGGER.warn("T"+Thread.currentThread().getId()+ "  " + packageGeneratorDTO.getNodeID() +" "+ restaurantId + "localizedFields pr" + parentSetId + "  ch" + childSetId);
			for (String lcK : localizedFields.keySet()) {
				LOGGER.warn(packageGeneratorDTO.getNodeID() +" "+ restaurantId + "localizedFields  k"+ lcK+ "  v:"+localizedFields.get(lcK));
			}
			final String restaurantDefLang = localizedFields.get("LANG_ID");
				final int languageCount = objNames.getLanguageCount(childSetId);
				if (languageCount > 0) {
				LOGGER.info("T"+Thread.currentThread().getId()+ "  vlanguageCount > 0     from " + parentSetId + "    to " + childSetId + "   " + restaurantDefLang);
					langSetId = childSetId;
				}

			final List<Map<String,String>> allLocales = screenDAO.getAllLocales(Long.valueOf(marketId),marketLocaleId,effectiveDate);

				final ArrayList<Long> langIds = new ArrayList<Long>();
				for (final Map<String, String> localeInfo : allLocales) {
					try {
						final Long langId = Long.parseLong(localeInfo.get("LANG_ID"));
						langIds.add(langId);
					} catch (final Exception e) {
					LOGGER.warn("T"+Thread.currentThread().getId()+ " " + packageGeneratorDTO.getNodeID() +" "+ restaurantId + " invalid LANG_ID for set_id=" + langSetId);
						if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("T"+Thread.currentThread().getId()+ "     INC9175677 - invalid LANG_ID for set_id=" + langSetId);
						}
						continue;
					}
				}

				screenBuffer.append(header(screenVersion));
				
				String sequenceNumber = productDBDAO.getValuesFromGlobalParam(packageGeneratorDTO.getMarketID(), ProductDBConstant.CONSTANT_ENABLE_SEAMLESS );
				if(sequenceNumber.equals("true")) {
				if (packageGeneratorDTO.isGeneratedSeqNum() && (packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() != null) && !packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO().isEmpty()) {
					screenBuffer.append("<ScreenDBSeqNumber>" + packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() + "</ScreenDBSeqNumber>");
					}
				}

				hotBuffer.append(header(screenVersion));
				if (packageGeneratorDTO.isGeneratedSeqNum() && (packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() != null) && !packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO().isEmpty()) {
					hotBuffer.append("<ScreenSeqNumber>" + packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() + "</ScreenSeqNumber>");
				}

				
				Map<String, Map<String, Map<String, String>>> masterButtonsLangDetails = null;
				Map<String, Map<String, String>> masterScreensInfo = null;
				Map<String, List<Map<String, String>>> masterButtonsInfo = null;
				synchronized (object) {
					//masterScreensInfo = (LinkedHashMap<String, Map<String, String>>) cacheHandler.get(scheduleDataID + "Screens" + screenSetId + "Inst" + currentInstanceId, SYSTEM_CACHE);
					//masterButtonsInfo = (LinkedHashMap<String, List<Map<String, String>>>) cacheHandler.get(scheduleDataID + "Buttons" + screenSetId + "Inst" + currentInstanceId, SYSTEM_CACHE);
				    masterScreensInfo=cacheService.getMasterScreensInfo(packageGeneratorDTO.getScheduleRequestID()+"Screens"+screenSetId+"Inst"+currentInstanceId);
				    masterButtonsInfo=cacheService.getMasterButtonsInfo(packageGeneratorDTO.getScheduleRequestID()+"Buttons"+screenSetId+"Inst"+currentInstanceId);
				     if ((null == masterScreensInfo) || ((masterScreensInfo != null) && masterScreensInfo.isEmpty()) || (scheduleSize == 1)) {
						masterScreensInfo = new LinkedHashMap<String, Map<String, String>>();

						final Map<String, Map<String, String>> masterScreensDetails = screenDAO.getScreenDetails(screenSetId, effectiveDate);
						final ArrayList<Long> screenIds = new ArrayList<>();
						final ArrayList<Long> screenInstanceIds = new ArrayList<>();
						getMasterScreenIds(masterScreensDetails, screenIds, screenInstanceIds);

						masterButtonsLangDetails = screenDAO.getButtonLangDetails(screenIds, screenInstanceIds, langIds, restaurantDefLang);
						if (LOGGER.isDebugEnabled()) {
							StringBuilder sbLangId= new StringBuilder();
							for (Long langIdv : langIds) {
								sbLangId.append(langIdv).append(", ");
							}
							LOGGER.debug("T"+Thread.currentThread().getId()+ "  getButtonLangDetails   screenIds:"+screenIds+",screenInstanceIds:"+screenInstanceIds+", langsId:" + sbLangId.toString());
						}
						final Map<String, Map<String, Map<String, String>>> mapMasterButtonsDetails = screenDAO.getMasterButtonDetails(screenIds, screenInstanceIds);

						final Map<String, List<Map<String, String>>> mapButtonWorkflows = getBtnIds(mapMasterButtonsDetails);
						final ArrayList<Long> liskWorkflows = getWkfIds(mapButtonWorkflows);
						final Map<String, List<Map<String, String>>> mapWorkFlowParam = screenDAO.getButtonWorkflowParameters(liskWorkflows);

						final ArrayList<Long> liskWorkflowParams = getWkfParams(mapWorkFlowParam);
						final Map<String, String> mapButtonWorkflowParams = screenDAO.getScreenLookupParameter(liskWorkflowParams, marketLocaleId);

						// retrieve only for common buttons
						final Map<String, String> mapProduct = getBtnPrdIds(mapMasterButtonsDetails);

						final Map<String, List<Map<String, String>>> mapScreenWkf = screenDAO.getScreenWorkflows(screenIds, screenInstanceIds);

						final ArrayList<Long> liskWorkflowScreen = getWkfScreenIds(mapScreenWkf);
						final Map<String, List<Map<String, String>>> mapScreenWkfParam = screenDAO.getScreenWorkflowParameters(liskWorkflowScreen);

						final ArrayList<Long> listWkfParamVals = getWkfParamScrVal(mapScreenWkfParam);
						final Map<String, String> mapScreenWkLkp = screenDAO.getScreenLookupParameter(listWkfParamVals, marketLocaleId);
						for (final String screenId : masterScreensDetails.keySet()) {
							final Map<String, String> masterScreenInfo = new HashMap<String, String>();
							final Map<String, String> masterScreenDetail = masterScreensDetails.get(screenId);
							final String screenInstanceId = masterScreenDetail.get("SCR_INST_ID");
							masterScreenInfo.put("screenXML", toScreenXML(masterScreenDetail, allMedia, 
																		allColors, allGrillGroups, allSmartReminders, 
																		allEvents, marketLocaleId));
							final String key = screenId + "," + screenInstanceId;
							masterScreenInfo.put("screenWorkflows", getWrkScreen(mapScreenWkf.get(key), allEvents, allWorkflows, allWorkflowParams, mapScreenWkfParam, mapScreenWkLkp));
							masterScreenInfo.put("SCR_BKGD_COLR_ID", masterScreenDetail.get("SCR_BKGD_COLR_ID"));

							masterScreenInfo.put("INST", screenInstanceId);
							masterScreenInfo.put("TYP", masterScreenDetail.get("TYP"));

							masterScreensInfo.put(screenId, masterScreenInfo);

							if (((null == masterButtonsInfo) || (null == masterButtonsInfo.get(screenId))) || ((masterButtonsInfo != null) && masterButtonsInfo.isEmpty()) || (scheduleSize == 1)) {
								if (null == masterButtonsInfo || masterButtonsInfo.isEmpty()) {
									masterButtonsInfo = new LinkedHashMap<String, List<Map<String, String>>>();
								}
								final Map<String, Map<String, String>> masterButtonsDetails = mapMasterButtonsDetails.get(screenId + "," + screenInstanceId);
								if (masterButtonsDetails != null) {
									final List<Map<String, String>> masterButtons = getMasterButtons(masterButtonsDetails, 
											masterButtonsLangDetails,
											screenId, 
											screenInstanceId, 
											marketLocaleId, 
											allMedia, 
											allColors, 
											mapProduct,
											mapButtonWorkflows, 
											allEvents, 
											allWorkflows, 
											mapWorkFlowParam, 
											allWorkflowParams, 
											mapButtonWorkflowParams, 
                                            restaurantDefLang,
											restaurantId, 
											packageGeneratorDTO,
											masterScreenDetail.get("TYP"));


									masterButtonsInfo.put(screenId, masterButtons);
								}
							}
						}
						if (scheduleSize > 1) {
					//cacheHandler.put(Thread.currentThread().getId(), scheduleDataID+"Screens"+screenSetId+"Inst"+currentInstanceId,masterScreensInfo,cacheTime, SYSTEM_CACHE);
					//cacheHandler.put(Thread.currentThread().getId(), scheduleDataID+"Buttons"+screenSetId+"Inst"+currentInstanceId,masterButtonsInfo,cacheTime, SYSTEM_CACHE);
							cacheService.setMasterScreensInfo(packageGeneratorDTO.getScheduleRequestID()+"Screens"+screenSetId+"Inst"+currentInstanceId, masterScreensInfo, Duration.ofSeconds(cacheTime));
							cacheService.setMasterButtonsInfo(packageGeneratorDTO.getScheduleRequestID()+"Buttons"+screenSetId+"Inst"+currentInstanceId, masterButtonsInfo, Duration.ofSeconds(cacheTime));		
						}
					}
				}

				//logTimer(packageGeneratorDTO.getNodeID() +" "+ restaurantId + "generateScreenDBXML cache end", startTs, packageGeneratorDTO);
				Map<Long, ProductDetails> allllProducts= new HashMap<>();
				Map<Long, ProductDetails> productCodeDtlsRelationMap= new HashMap<>();
			     List<String> restaurantMenuItems =new ArrayList<>();
				if(null!=packageGeneratorDTO.getAllProducts()&&packageGeneratorDTO.getAllProducts().size()>0)
					allllProducts=packageGeneratorDTO.getAllProducts();
				else
					allllProducts=getRestaurantProducts(packageGeneratorDTO,restaurantDefLang);
			
				for(Long productKey : allllProducts.keySet() ) {
					restaurantMenuItems.add(allllProducts.get(productKey).getProductCode().toString());
					if(allllProducts.get(productKey).getActive()==1)
					productCodeDtlsRelationMap.put(allllProducts.get(productKey).getProductCode(),allllProducts.get(productKey));
				}
				// Retrieve the buttons used in this screen set
				final ArrayList<Long> screenIds = new ArrayList<>();
				final ArrayList<Long> screenInstanceIds = new ArrayList<>();
				getScreenIds(masterScreensInfo, screenIds, screenInstanceIds);
				final Map<String, Map<String, Map<String, String>>> dynamicButtonsAll = screenDAO.getDynamicButtonDetails(screenIds, screenInstanceIds,productCodeDtlsRelationMap, effectiveDate);

				final Map<String, Map<String, Map<String, String>>> buttonLangsDetails = (masterButtonsLangDetails != null) && masterButtonsLangDetails.isEmpty() ? masterButtonsLangDetails : screenDAO.getButtonLangDetails(screenIds, screenInstanceIds, langIds, restaurantDefLang);
				if (LOGGER.isDebugEnabled()) {
					StringBuilder sbLangId= new StringBuilder();
					for (Long langIdv : langIds) {
						sbLangId.append(langIdv).append(", ");
					}
					LOGGER.debug("T"+Thread.currentThread().getId()+ "  getButtonLangDetails   screenIds:"+screenIds+",screenInstanceIds:"+screenInstanceIds+", langsId:" + sbLangId.toString());
				}

				final Map<String, Map<String, Map<String, String>>> mapBlmButtons = screenDAO.getBLMButtonDetails(screenIds, screenSetName, packageGeneratorDTO.getNodeID().toString(), effectiveDate);

				final ArrayList<Long> buttonBlmIds = new ArrayList<>();
				final ArrayList<Long> blmInstanceIds = new ArrayList<>();
				getBlmIds(masterScreensInfo, masterButtonsInfo, mapBlmButtons, buttonBlmIds, blmInstanceIds, restaurantMenuItems);
				final Map<String, List<Map<String, String>>> mapButtonWorkflows = screenDAO.getButtonWorkflows(buttonBlmIds, blmInstanceIds);
				final Map<String, String> mapProductBlm = screenDAO.getButtonProductCode(buttonBlmIds, blmInstanceIds);
				final Map<String, Map<String, Map<String, String>>> mapBlmButtonLang = screenDAO.getBLMLangDetails(buttonBlmIds, blmInstanceIds);

				final ArrayList<Long> liskWorkflows =  getWkfIds(mapButtonWorkflows);
				final Map<String, List<Map<String, String>>> mapWorkFlowParams = screenDAO.getButtonWorkflowParameters(liskWorkflows);

				final ArrayList<Long> liskWorkflowParams = getWkfParams(mapWorkFlowParams);
				final Map<String, String> mapButtonWorkflowParams = screenDAO.getScreenLookupParameter(liskWorkflowParams, marketLocaleId);

		
				for (final String screenId : masterScreensInfo.keySet()) {
				if(LOGGER.isTraceEnabled()){
					LOGGER.trace("uniqueId:"+ uniqueId + " screenId:" + screenId);
					}
					final Map<String, String> masterScreenInfo = masterScreensInfo.get(screenId);
					PackageWriter bufferScr;
					if (hotScreens.contains(masterScreenInfo.get("TYP"))) {
						bufferScr = hotBuffer;
					} else {
						bufferScr = screenBuffer;
					}
					bufferScr.append(masterScreenInfo.get("screenXML"));
					final String colorScr = masterScreenInfo.get("SCR_BKGD_COLR_ID");
					bufferScr.append(getAttribute("bgcolor", allColors.get(NVL(colorScr, "") + "," + restaurantDefLang), false));
					bufferScr.append(" >");
					if (masterScreenInfo.containsKey("screenWorkflows")) {
						bufferScr.append(masterScreenInfo.get("screenWorkflows"));
					}

					final List<Map<String, String>> nonBLMButtons = masterButtonsInfo.get(screenId);
					if (nonBLMButtons != null) {
						for (final Map<String, String> nonBLMButton : nonBLMButtons) {
							final String buttonId = nonBLMButton.get("BTTN_ID");
							final Map<String, Map<String, String>> buttonLangDetails = buttonLangsDetails.get(screenId + "," + masterScreenInfo.get("INST"));
						
							if ("N".equalsIgnoreCase(nonBLMButton.get("IS_BLM")) && "N".equalsIgnoreCase(nonBLMButton.get("IS_DYNAMIC"))) { //generate common buttons
								if (!ObjectUtils.isFilled(nonBLMButton.get("PRD_CD")) || restaurantMenuItems.contains(nonBLMButton.get("PRD_CD"))) {
								bufferScr.append(commonButton(screenId, masterScreenInfo.get("INST"), 
														nonBLMButton, 
														buttonLangDetails, kioskScreens, 
														allLocales, allMedia, 
														restaurantDefLang,
										                buttonId, masterScreenInfo, localizedFields,
													   restaurantId, packageGeneratorDTO));
								}
							} else if ("Y".equalsIgnoreCase(nonBLMButton.get("IS_DYNAMIC"))) {
								final Map<String, Map<String, String>> dynamicButtons = dynamicButtonsAll.get(screenId + "," + masterScreenInfo.get("INST"));
								if (dynamicButtons != null) {
									final Map<String, String> dynamicButtonDetail = dynamicButtons.get(buttonId);
									if (dynamicButtonDetail != null) {
    									String dynamicButton = getDynamicButton(screenId, dynamicButtonDetail, 
    	             		                   dynamicWorkflowParams, 
    	             		                   kioskScreens, restaurantDefLang, marketLocaleId, defaultButtonCaption, localizedFields, 
    	             		                   allMedia,
    	             		                   buttonLangDetails, allWorkflowParams, allLocales,
    	             		                   masterScreenInfo,
    	             		                   posWorkflow);
    									bufferScr.append(dynamicButton);
									}
								}
							} else if ("Y".equalsIgnoreCase(nonBLMButton.get("IS_BLM"))) {
								final Map<String, Map<String, String>> blmButtons = mapBlmButtons.get(screenId);
								if (blmButtons != null) {
									Map<String,String> blmButton =  blmButtons.get(buttonId);
									if (blmButton != null) {
    									String bmlButton = getBMLButton(blmButton, restaurantMenuItems, restaurantDefLang, marketLocaleId, 
    		             		               allMedia, allColors,
    		             		               allEvents, allWorkflows, allWorkflowParams,
    		             		               buttonId,
    		             		               kioskScreens, allLocales, masterScreenInfo, localizedFields, mapButtonWorkflows, mapProductBlm, mapBlmButtonLang, 
    		             		               mapWorkFlowParams,
    		             		               mapButtonWorkflowParams);
    									bufferScr.append(bmlButton);
									}
								}
							}
						}
					}

					bufferScr.append("</Screen>");
					if (hotScreens.contains(masterScreenInfo.get("TYP"))) {
						generateHOT = true;
					} else {
						generateScreen = true;
					}
				}

				if (generateScreen) {
					screenBuffer.append("</Screens>");
					screenBuffer.close();
					result[INT_ZERO_CONSTANT] = true;
				} else {
					screenBuffer.remove();
				}

				if (generateHOT) {
					hotBuffer.append("</Screens>");
					hotBuffer.close();
					result[INT_ONE_CONSTANT] = true;
				} else {
					hotBuffer.remove();
				}			
			}		
		} catch (final Exception exception) {
			LOGGER.error(exception.getMessage(), exception);		
		}
		
		return result;
	}

	private StringBuilder header(final String screenVersion) {
		final StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Screens version=\"");
		sb.append(screenVersion);
		sb.append("\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"/RFM2/RFM2PackageConf/PackageXSD/2.1/screen-db.xsd\">");

		return sb;
	}
	
	public static boolean isFilled(final String str) {
		return (str != null) && !EMPTY_STRING.equals(str.trim());
	}

	/**
	 * @param prdCode
	 * @param screenType
	 * @return
	 */
	private String getWorkFlowDefault(final String prdCode, final String screenType) {
		final StringBuilder result = new StringBuilder();
		final String defWorkFlow = ""; //TODO ApplicationPropertyRead.getProperty(IGNORE_DEFAULT_FORCEWORKFLOW);
		if ((prdCode != null) && ((defWorkFlow == null) || !defWorkFlow.equals("true"))) {
			result.append("<Action");
			result.append(getAttribute("type", "onclick", false));
			final String wkrDefaultType = getWkfType(screenType);
			result.append(getAttribute("workflow", wkrDefaultType, false));
			result.append(" >");

			result.append("<Parameter");
			result.append(getAttribute("name", "ProductCode", false));
			result.append(getAttribute("value", prdCode, false));
			result.append(" />");

			result.append("</Action>");
		}

		return result.toString();
	}

	/**
	 * @param screenType
	 * @return
	 */
	private String getWkfType(final String screenType) {
		String result = "WF_DoSale";

		if (isIn(screenType, new String[]{ "2800", "2801", "2802", "2803", "2804", "2805", "2806", "2850", "2851", "2852", "2853", "2854" })) {
			result = "WF_NGK_DoSale";
		} else if (isIn(screenType, new String[]{ "2950", "2951", "2952", "2953", "2954", "2955", "2957", "2958", "2959", "2960" })) {
			result = "WF_CHF_DoSale";
		}

		return result;
	}
	/**
	 * @param masterButtonsDetails
	 * @param masterButtonsLangDetails 
	 * @param mapProduct 
	 * @param allColors 
	 * @param allMedia 
	 * @param marketLocaleId 
	 * @param screenInstanceId 
	 * @param screenId 
	 * @param allWorkflowParams 
	 * @param mapWorkFlowParam 
	 * @param allWorkflows 
	 * @param allEvents 
	 * @param mapButtonWorkflows 
	 * @param mapButtonWorkflowParams 
	 * @return
	 */
	private List<Map<String, String>> getMasterButtons(final Map<String, Map<String, String>> masterButtonsDetails, 
			                                           final Map<String, Map<String, Map<String, String>>> masterButtonsLangDetails, 
			                                           final String screenId, final String screenInstanceId, final String marketLocaleId, 
													   final Map<String, String> allMedia, final Map<String, String> allColors, final Map<String, String> mapProduct,
			                                           final Map<String, List<Map<String, String>>> mapButtonWorkflows, final Map<String, String> allEvents, 
													   final Map<String, String> allWorkflows, final Map<String, List<Map<String, String>>> mapWorkFlowParam, 
													   final Map<String, String> allWorkflowParams, final Map<String, String> mapButtonWorkflowParams, 
			                                           final String restaurantDefLang,
													   final String restaurantId,  final PackageGeneratorDTO packageGeneratorDTO,
													   final String screenType) {
		final List<Map<String, String>> masterButtons = new ArrayList<Map<String, String>>();
		Map<String, String> masterButton;

		for (final String buttonId : masterButtonsDetails.keySet()) {
			final Map<String, String> masterButtonDetail = masterButtonsDetails.get(buttonId);

			masterButton = new HashMap<String, String>();

			String prdCode = null;
			if ("N".equalsIgnoreCase(masterButtonDetail.get("IS_BLM")) && "N".equalsIgnoreCase(masterButtonDetail.get("IS_DYNAMIC"))) {
				final Map<String, Map<String, String>> masterButtonLangDetails = masterButtonsLangDetails.get(screenId + "," + screenInstanceId);
				if (masterButtonLangDetails != null) {
					final Set<String> bttns = masterButtonLangDetails.keySet();
					if (!ObjectUtils.isFilled(masterButtonDetail.get("PRD_CD"))) {
						prdCode = mapProduct.get(buttonId + "," + screenInstanceId);
					} else {
						prdCode = masterButtonDetail.get("PRD_CD");
					}
					for (final String id_land : bttns) {
						final String[] keys = id_land.split("[,]");
						final String bttnIdLang = keys[0];
						final String restLang = keys[1];
						if (bttnIdLang.equals(buttonId)) {
							final StringBuilder sb = new StringBuilder();
							final Map<String, String> defaultButtonLang = masterButtonLangDetails.get(masterButtonDetail.get("BTTN_ID") + "," + restLang);
							final Map<String, String> mktButtonLang = masterButtonLangDetails.get(masterButtonDetail.get("BTTN_ID") + "," + marketLocaleId);
							sb.append("<Button");
							sb.append(getAttribute("number", masterButtonDetail.get("BTTN_NU"), false));
							sb.append(getAttribute("category", masterButtonDetail.get("CAT"), false));
							if (null != defaultButtonLang) {
								sb.append(getAttribute("title", defaultButtonLang.get("CPTN"), false));
							}
							sb.append(getAttribute("keyscan", masterButtonDetail.get("KEY_SCAN"), false));
							sb.append(getAttribute("keyshift", masterButtonDetail.get("KEY_SHFT"), false));
							sb.append(getAttribute("bitmap", allMedia.get(NVL(null != defaultButtonLang ? defaultButtonLang.get("BMP") : null, null != mktButtonLang ? mktButtonLang.get("BMP") : null)), true));
							sb.append(getAttribute("bitmapdn", allMedia.get(NVL(null != defaultButtonLang ? defaultButtonLang.get("BMP_PRSD") : null, null != mktButtonLang ? mktButtonLang.get("BMP_PRSD") : null)), true));
							sb.append(getAttribute("textup", allColors.get(NVL(masterButtonDetail.get("FRGND_CPTN_COLR_ID"), "") + "," + restLang), false));
							sb.append(getAttribute("textdn", allColors.get(NVL(masterButtonDetail.get("FRGND_CPTN_COLR_PRSD_ID"), "") + "," + restLang), false));
							sb.append(getAttribute("bgup", allColors.get(NVL(masterButtonDetail.get("BKGD_CPTN_COLR_ID"), "") + "," + restLang), false));
							sb.append(getAttribute("bgdn", allColors.get(NVL(masterButtonDetail.get("BKGD_CPTN_COLR_PRSD_ID"), "") + "," + restLang), false));
							sb.append(getAttribute("v", masterButtonDetail.get("WDTH"), false));
							sb.append(getAttribute("h", masterButtonDetail.get("HGHT"), false));
							sb.append(getAttribute("productCode", prdCode, false));
							sb.append(getAttribute("sound", allMedia.get(masterButtonDetail.get("sound_file_id")), false));

							String attributeValueOutageMode = masterButtonDetail.get("OUTAGE_MODE");
							if ("1".equals(attributeValueOutageMode)) {
								attributeValueOutageMode = "true";
							} else if ("0".equals(attributeValueOutageMode)) {
								attributeValueOutageMode = "false";
							}
							sb.append(getAttribute("outageModeButtonDisabled", attributeValueOutageMode, false));

							sb.append(" >");
							masterButton.put("ButtonXML_" + restLang, sb.toString());
						}
					}
				}

				final List<Map<String, String>> buttonWorkflows = mapButtonWorkflows.get(buttonId + "," + screenInstanceId);
				final StringBuilder sb = new StringBuilder();
				if (buttonWorkflows != null) {
					for (final Map<String, String> buttonWorkflow : buttonWorkflows) {
						sb.append("<Action");
						sb.append(getAttribute("type", allEvents.get(buttonWorkflow.get("EVNT_ID")), false));
						sb.append(getAttribute("workflow", allWorkflows.get(buttonWorkflow.get("WRKFL_ID")), false));
						sb.append(" >");

						final List<Map<String, String>> workflowParameters = mapWorkFlowParam.get(buttonWorkflow.get("BTTN_WRKFL_ASGN_ID"));
						if (workflowParameters != null) {
							for (final Map<String, String> workflowParameter : workflowParameters) {
								sb.append("<Parameter");
								sb.append(getAttribute("name", allWorkflowParams.get(workflowParameter.get("PARM_ID")), false));
								if ("3".equals(workflowParameter.get("PARM_TYP"))) {
									if (ObjectUtils.isFilled(workflowParameter.get("VAL"))) {
										sb.append(getAttribute("value", mapButtonWorkflowParams.get(workflowParameter.get("VAL")), false));
									} else {
										sb.append(getAttribute("value", "", false));
									}
								} else {
									final String parameterName = allWorkflowParams.get(workflowParameter.get("PARM_ID"));
									if (parameterName == null) {
										LOGGER.error("buttonId " + buttonId + " screenInstanceId " + screenInstanceId + " PARM_ID: " + workflowParameter.get("PARM_ID") + "   parameterName is null ");
									}
									if ((parameterName != null) && parameterName.equalsIgnoreCase("PRODUCTCODE") && (workflowParameter.get("VAL") == null)) {
										LOGGER.error("buttonId " + buttonId + " screenInstanceId " + screenInstanceId + "  ProductCode is null");
									}
									sb.append(getAttribute("value", workflowParameter.get("VAL"), false));
								}
								sb.append(" />");
							}
						}
						sb.append("</Action>");
					}
				}
				if ((sb.length() == 0) && (prdCode != null)) {
					sb.append(getWorkFlowDefault(prdCode, screenType));
				}
				masterButton.put("ButtonActionXML", sb.toString());

				masterButton.put("BTTN_ID", buttonId);
				masterButton.put("PRD_CD", masterButtonDetail.get("PRD_CD"));
				masterButton.put("IS_BLM", masterButtonDetail.get("IS_BLM"));
				masterButton.put("IS_DYNAMIC", masterButtonDetail.get("IS_DYNAMIC"));
			} else {
				masterButton.put("BTTN_ID", buttonId);
				masterButton.put("PRD_CD", masterButtonDetail.get("PRD_CD"));
				masterButton.put("IS_BLM", masterButtonDetail.get("IS_BLM"));
				masterButton.put("IS_DYNAMIC", masterButtonDetail.get("IS_DYNAMIC"));
			}
			masterButtons.add(masterButton);
		}

		return masterButtons;
	}

	/**
	 * @param mapButtonWorkflows
	 * @param buttonWorkflows
	 */
	private ArrayList<Long> getWkfIds(final Map<String, List<Map<String, String>>> mapButtonWorkflows) {
		final ArrayList<Long> result = new ArrayList<>();
		if (mapButtonWorkflows != null) {
			for (final String bttnBlm : mapButtonWorkflows.keySet()) {
				final List<Map<String, String>> blm = mapButtonWorkflows.get(bttnBlm);
				if (blm != null) {
					for (final Map<String, String> buttonWorkflow : blm) {
						result.add(new Long(buttonWorkflow.get("BTTN_WRKFL_ASGN_ID")));
					}
				}
			}
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
	/**
	 * @param masterScreenDetail
	 * @param allMedia
	 * @param allColors
	 * @param allGrillGroups
	 * @param allSmartReminders
	 * @param restaurantDefLang
	 * @param allWorkflows
	 * @param screenWorkflows
	 * @param allEvents
	 * @param allWorkflowParams
	 * @param marketLocaleId
	 * @return
	 * @throws GeneratorDBException 
	 */
	private String toScreenXML(final Map<String, String> masterScreenDetail, final Map<String, String> allMedia, final Map<String, String> allColors, 
			                   final Map<String, String> allGrillGroups, final Map<String, String> allSmartReminders,  
			                   final Map<String, String> allEvents,
			                   final String marketLocaleId)  {
		final StringBuilder sb = new StringBuilder();
		sb.append("<Screen");
		sb.append(getAttribute("number", masterScreenDetail.get("SCR_NU"), false));
		sb.append(getAttribute("timeout", masterScreenDetail.get("TOUT"), false));
		sb.append(getAttribute("type", masterScreenDetail.get("TYP"), false));
		sb.append(getAttribute("title", masterScreenDetail.get("TITL"), false));
		sb.append(getAttribute("bgimage", allMedia.get(masterScreenDetail.get("SCR_BKGD_IMG")), false));
		sb.append(getAttribute("daypart", masterScreenDetail.get("DYPT_NA"), false));
		sb.append(getAttribute("productGroup", allGrillGroups.get(masterScreenDetail.get("GRLL_GRP_ID")), false));
		sb.append(getAttribute("reminderGroup", allSmartReminders.get(masterScreenDetail.get("SRG_ID")), false));
		sb.append(getAttribute("sound", allMedia.get(masterScreenDetail.get("SOUND_FILE_ID")), false));

		return sb.toString();
	}
	/**
	 * @param screenId
	 * @param screenInstanceId
	 * @param marketLocaleId
	 * @param allEvents
	 * @param allWorkflows
	 * @param allWorkflowParams 
	 * @param mapScreenWkfParam 
	 * @param mapScreenWkLkp 
	 * @return
	 * @throws GeneratorDBException
	 */
	private String getWrkScreen(final List<Map<String, String>> screenWorkflows, final Map<String, String> allEvents, final Map<String, String> allWorkflows, 
								final Map<String, String> allWorkflowParams, 
								final Map<String, List<Map<String, String>>> mapScreenWkfParam, final Map<String, String> mapScreenWkLkp)  {
		final StringBuilder sb = new StringBuilder();
		if (screenWorkflows == null) {
			return "";
		}

		for (final Map<String, String> screenWorkflow : screenWorkflows) {
			sb.append("<Action");
			sb.append(getAttribute("type", allEvents.get(screenWorkflow.get("EVNT_ID")), false));
			sb.append(getAttribute("workflow", allWorkflows.get(screenWorkflow.get("WRKFL_ID")), false));
			sb.append(" >");
			final List<Map<String, String>> workflowParameters = mapScreenWkfParam.get(screenWorkflow.get("SCR_WRKFL_ASGN_ID"));
			if (workflowParameters != null) {
				for (final Map<String, String> workflowParameter : workflowParameters) {
					sb.append("<Parameter");
					sb.append(getAttribute("name", allWorkflowParams.get(workflowParameter.get("PARM_ID")), false));
					if ("3".equals(workflowParameter.get("PARM_TYP"))) {
						if (ObjectUtils.isFilled(workflowParameter.get("VAL"))) {
							sb.append(getAttribute("value", mapScreenWkLkp.get(workflowParameter.get("VAL")), false));
						} else {
							sb.append(getAttribute("value", "", false));
						}
					} else {
						sb.append(getAttribute("value", workflowParameter.get("VAL"), false));
					}
					sb.append(" />");
				}
			}
			sb.append("</Action>");
		}
		return sb.toString();
	}

	/**
	 * @param mapMasterButtonsDetails
	 * @param buttonIds
	 * @param btnScreenInstanceIds
	 * @return 
	 * @throws Exception 
	 * @throws GeneratorDBException 
	 */
	private Map<String, String> getBtnPrdIds(final Map<String, Map<String, Map<String, String>>> mapMasterButtonsDetails) throws Exception {
		final ArrayList<Long> buttonPrdIds = new ArrayList<>();
		final ArrayList<Long> screenInstancePrdIds = new ArrayList<>();
		if (mapMasterButtonsDetails != null) {
			for (final String screenId : mapMasterButtonsDetails.keySet()) {
				final String[] scrInstId = screenId.split("[,]"); // screenid,screeninstanceid
				final Map<String, Map<String, String>> masterButtonsDetails = mapMasterButtonsDetails.get(screenId);
				if (masterButtonsDetails != null) {
					for (final String buttonId : masterButtonsDetails.keySet()) {
						final Map<String, String> masterButtonDetail = masterButtonsDetails.get(buttonId);
						if (masterButtonsDetails != null) {
							if ("N".equalsIgnoreCase(masterButtonDetail.get("IS_BLM")) && "N".equalsIgnoreCase(masterButtonDetail.get("IS_DYNAMIC"))) {
								if (!ObjectUtils.isFilled(masterButtonDetail.get("PRD_CD"))) {
									buttonPrdIds.add(new Long(buttonId));
									screenInstancePrdIds.add(new Long(scrInstId[1]));
								}
							}
						}
					}
				}

			}
		}
		return screenDAO.getButtonProductCode(buttonPrdIds, screenInstancePrdIds);
	}

	/**
	 * @param masterScreensInfo 
	 * @param masterButtonsInfo 
	 * @param mapBlmButtons
	 * @param blmInstanceIds 
	 * @param buttonBlmIds 
	 * @param restaurantMenuItems 
	 * @param screenIds
	 * @param screenInstanceIds
	 * @return 
	 * @throws GeneratorDBException 
	 */
	public void getBlmIds(Map<String, Map<String, String>> masterScreensInfo, Map<String,List<Map<String,String>>> masterButtonsInfo, Map<String, Map<String, Map<String, String>>> mapBlmButtons, ArrayList<Long> buttonBlmIds, ArrayList<Long> blmInstanceIds, List<String> restaurantMenuItems) {
		 for(String screenId : masterScreensInfo.keySet()){
			final List<Map<String, String>> nonBLMButtons = masterButtonsInfo.get(screenId);
			if (nonBLMButtons != null) {
				for (final Map<String, String> nonBLMButton : nonBLMButtons) {
					final String buttonId = nonBLMButton.get("BTTN_ID");
					if ("Y".equalsIgnoreCase(nonBLMButton.get("IS_BLM"))) {
						final Map<String, Map<String, String>> blm = mapBlmButtons.get(screenId);
						if (blm != null) {
							final Map<String, String> blmButton = blm.get(buttonId);
							if ((null != blmButton) && !blmButton.isEmpty()) {
								if (!ObjectUtils.isFilled(blmButton.get("PRD_CD")) || restaurantMenuItems.contains(blmButton.get("PRD_CD"))) {
									final String blmInstanceId = blmButton.get("SCR_INST_ID");
									buttonBlmIds.add(new Long(buttonId));
									blmInstanceIds.add(new Long(blmInstanceId));
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * @param mapScreenWkf
	 * @return
	 */
	private ArrayList<Long> getWkfScreenIds(final Map<String, List<Map<String, String>>> mapScreenWkf) {
		final ArrayList<Long> result = new ArrayList<>();
		for (final String wkf : mapScreenWkf.keySet()) {
			final List<Map<String, String>> listwkfs = mapScreenWkf.get(wkf);
			for (final Map<String, String> screenWorkflow : listwkfs) {
				result.add(new Long(screenWorkflow.get("SCR_WRKFL_ASGN_ID")));
			}
		}
		return result;
	}
	/**
	 * @param mapMasterButtonsDetails
	 * @param buttonIds
	 * @param btnScreenInstanceIds
	 * @return 
	 * @throws Exception 
	 * @throws GeneratorDBException 
	 */
	private Map<String, List<Map<String, String>>> getBtnIds(final Map<String, Map<String, Map<String, String>>> mapMasterButtonsDetails) throws Exception  {
		final ArrayList<Long> buttonIds = new ArrayList<>();
		final ArrayList<Long> btnScreenInstanceIds = new ArrayList<>();
		for (final String screenId : mapMasterButtonsDetails.keySet()) {
			final String[] scrInstId = screenId.split("[,]"); // screenid,screeninstanceid
			final Map<String, Map<String, String>> masterButtonsDetails = mapMasterButtonsDetails.get(screenId);
			if (masterButtonsDetails != null) {
				for (final String buttonId : masterButtonsDetails.keySet()) {
					buttonIds.add(new Long(buttonId));
					btnScreenInstanceIds.add(new Long(scrInstId[1]));
				}
			}
		}
		return screenDAO.getButtonWorkflows(buttonIds, btnScreenInstanceIds);
	}


	/**
	 * @param masterScreensInfo
	 * @param screenIds
	 * @param screenInstanceIds
	 */
	private void getMasterScreenIds(final Map<String, Map<String, String>> masterScreensInfo, final ArrayList<Long> screenIds, final ArrayList<Long> screenInstanceIds) {

		for (final String screenId : masterScreensInfo.keySet()) {
			final Map<String, String> masterScreenInfo = masterScreensInfo.get(screenId);
			screenIds.add(new Long(screenId));
			screenInstanceIds.add(new Long(masterScreenInfo.get("SCR_INST_ID")));
		}
	}
	


	/**
	 * @param str1
	 * @param str2
	 * @return
	 */
	private String NVL(final String str1, final String str2) {
		if (isFilled(str1)) {
			return str1;
		} else {
			return str2;
		}
	}

	/**
	 * @param attributeName
	 * @param attributeValue
	 * @param mandatory
	 * @return
	 */
	private String getAttribute(final String attributeName, final String attributeValue, final boolean mandatory) {
		final StringBuilder attribute = new StringBuilder();
		if ((mandatory) || (!mandatory && isFilled(attributeValue))) {
			attribute.append(" ");
			attribute.append(attributeName);
			attribute.append(" =\"");
			attribute.append(replaceSpecialCharacters(NVL(attributeValue, "")));
			attribute.append("\"");
		}
		return attribute.toString();
	}	


	/**
	 * @param screenId 
	 * @param allMedia 
	 * @param localizedFields 
	 * @param defaultButtonCaption 
	 * @param marketLocaleId 
	 * @param restaurantDefLang 
	 * @param kioskScreens 
	 * @param dynamicWorkflowParams 
	 * @param dynamicButtonDetail 
	 * @param allLocales 
	 * @param allWorkflowParams 
	 * @param buttonLangDetails 
	 * @param masterScreenInfo 
	 * @param defaultPosWorkflow 
	 * @param buttonId 
	 * @return
	 * 
	 */
	private String getDynamicButton(final String screenId, final Map<String, String> dynamicButtonDetail, 
			                        final List<String> dynamicWorkflowParams, 
			                        final List<String> kioskScreens, 
			                        final String restaurantDefLang, final String marketLocaleId, final String defaultButtonCaption, 
			                        final Map<String, String> localizedFields, 
			                        final Map<String, String> allMedia, 
			                        final Map<String, Map<String, String>> buttonLangDetails,
			                        final Map<String, String> allWorkflowParams, 
			                        final List<Map<String, String>> allLocales, 
			                        final Map<String, String> masterScreenInfo, 
			                        final String defaultPosWorkflow) {
		String posWorkflow = defaultPosWorkflow;
		final StringBuilder sb = new StringBuilder();
		if ((null != dynamicButtonDetail) && !dynamicButtonDetail.isEmpty()) {
			final Map<String, String> defaultButtonLang = buttonLangDetails == null ? null : buttonLangDetails.get(dynamicButtonDetail.get("BTTN_ID") + "," + restaurantDefLang);
			final Map<String, String> mktButtonLang = buttonLangDetails == null ? null : buttonLangDetails.get(dynamicButtonDetail.get("BTTN_ID") + "," + marketLocaleId);
			sb.append("<Button");
			sb.append(getAttribute("number", dynamicButtonDetail.get("BTTN_NU"), false));
			sb.append(getAttribute("category", dynamicButtonDetail.get("CAT"), false));
			sb.append(getAttribute("title", NVL(dynamicButtonDetail.get("CPTN"), defaultButtonCaption), false)); //delete with dynamic caption - why?
			sb.append(getAttribute("keyscan", dynamicButtonDetail.get("KEY_SCAN"), false));
			sb.append(getAttribute("keyshift", dynamicButtonDetail.get("KEY_SHFT"), false));
			sb.append(getAttribute("bitmap", dynamicButtonDetail.get("IMG_NA"), true));
			sb.append(getAttribute("bitmapdn", allMedia.get(NVL(null != defaultButtonLang ? defaultButtonLang.get("BMP_PRSD") : null, null != mktButtonLang ? mktButtonLang.get("BMP_PRSD") : null)), true));
			sb.append(getAttribute("textup", dynamicButtonDetail.get("FG_NRML_NA"), false));
			sb.append(getAttribute("textdn", dynamicButtonDetail.get("FG_PRSD_NA"), false));
			sb.append(getAttribute("bgup", dynamicButtonDetail.get("BG_NRML_NA"), false));
			sb.append(getAttribute("bgdn", dynamicButtonDetail.get("BG_PRSD_NA"), false));
			sb.append(getAttribute("v", dynamicButtonDetail.get("WDTH"), false));
			sb.append(getAttribute("h", dynamicButtonDetail.get("HGHT"), false));
			sb.append(getAttribute("productCode", dynamicButtonDetail.get("PRD_CD"), false)); //workflow rule to be done
			sb.append(getAttribute("sound", allMedia.get(dynamicButtonDetail.get("sound_file_id")), false));

			String attributeValueOutageMode = dynamicButtonDetail.get("OUTAGE_MODE");
			if ("1".equals(attributeValueOutageMode)) {
				attributeValueOutageMode = "true";
			} else if ("0".equals(attributeValueOutageMode)) {
				attributeValueOutageMode = "false";
			}
			sb.append(getAttribute("outageModeButtonDisabled", attributeValueOutageMode, false));

			sb.append(" >");
			sb.append("<Action");
			sb.append(getAttribute("type", "onclick", false));

			if (!ObjectUtils.isFilled(posWorkflow)) {
				posWorkflow = "WF_DoSale";
			}

			if (kioskScreens.contains(masterScreenInfo.get("TYP"))) {
				posWorkflow = "WF_NGK_DoSale";
			}

			sb.append(getAttribute("workflow", posWorkflow, false));
			sb.append(" >");
			for (final String paramId : dynamicWorkflowParams) {
				sb.append("<Parameter");
				sb.append(getAttribute("name", allWorkflowParams.get(paramId), false));
				sb.append(getAttribute("value", dynamicButtonDetail.get("PRD_CD"), false));
				sb.append(" />");
			}
			sb.append("</Action>");

			if (null != buttonLangDetails) {
				Map<String, String> buttonLang = null;
				if(allLocales == null || allLocales.isEmpty()){
					LOGGER.warn("screenId " + screenId + " btnNu "+ dynamicButtonDetail.get("BTTN_NU")+"  allLocales is empty");
				}
				for(Map<String,String>localeInfo : allLocales){
					if (kioskScreens.contains(masterScreenInfo.get("TYP")) || (!kioskScreens.contains(masterScreenInfo.get("TYP")) && localeInfo.get("LANG_ID").equals(restaurantDefLang))) {
						buttonLang = buttonLangDetails.get(dynamicButtonDetail.get("BTTN_ID") + "," + localeInfo.get("LANG_ID"));
						if (null != buttonLang) {
							sb.append("<Language");
							sb.append(getAttribute("code", localeInfo.get("LANG_CD") + "_" + localizedFields.get("CTRY_ID"), false));
							sb.append(getAttribute("name", localeInfo.get("LCLE_NA"), false));
							sb.append(getAttribute("parent", localeInfo.get("LANG_CD"), false));
							sb.append(" >");
							sb.append("<title>");
							sb.append(replaceSpecialCharacters(NVL(dynamicButtonDetail.get("CPTN"), defaultButtonCaption)));
							sb.append("</title>");
							if (ObjectUtils.isFilled(dynamicButtonDetail.get("IMG_NA"))) {
								sb.append("<bitmap>");
								sb.append(replaceSpecialCharacters(dynamicButtonDetail.get("IMG_NA")));
								sb.append("</bitmap>");
							}
							if ((null == mktButtonLang) && ObjectUtils.isFilled(buttonLang.get("BMP_PRSD"))) {
								sb.append("<bitmapdn>");
								sb.append(replaceSpecialCharacters(allMedia.get(buttonLang.get("BMP_PRSD"))));
								sb.append("</bitmapdn>");
							} else if (ObjectUtils.isFilled(NVL(mktButtonLang.get("BMP_PRSD"), buttonLang.get("BMP_PRSD")))) {
								sb.append("<bitmapdn>");
								sb.append(replaceSpecialCharacters(allMedia.get(NVL(mktButtonLang.get("BMP_PRSD"), buttonLang.get("BMP_PRSD")))));
								sb.append("</bitmapdn>");
							}
							sb.append("</Language>");
						}
					}
				}
			}
			sb.append("</Button>");
		}

		return sb.toString();
	}
	

	public static String replaceSpecialCharacters(final String str){
		if (str == null) {
			return null;
		}
		String returnStr = str;

		returnStr = returnStr.replace("&amp;","ampersandString"); 
		returnStr =  returnStr.replace("&quot;","quotString"); 
		returnStr = returnStr.replace("&lt;","leftTagString"); 
		returnStr = returnStr.replace("&gt;","rightTagString");  
		returnStr = returnStr.replace("&apos;","apostrophe");

		returnStr = returnStr.replace("&","&amp;"); 
		returnStr = returnStr.replace("\"","&quot;"); 
		returnStr = returnStr.replace("'","&apos;"); 
		returnStr = returnStr.replace("<","&lt;"); 
		returnStr = returnStr.replace(">","&gt;"); 

		returnStr = returnStr.replace("ampersandString","&amp;"); 
		returnStr = returnStr.replace("quotString","&quot;"); 
		returnStr = returnStr.replace("leftTagString","&lt;"); 
		returnStr =  returnStr.replace("rightTagString","&gt;"); 
		returnStr = returnStr.replace("apostrophe","&apos;");

		return returnStr;
	}
	
	/**
	 * @param productClass
	 * @param integers
	 * @return
	 */
	public static boolean isIn(final String value, final String[] strings) {
		boolean result = false;
		if (value == null) {
			result = false;
		} else {
			for (final String item : strings) {
				if (value.equals(item)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * @param lookupFlag
	 * @param strings
	 * @return
	 */
	public static boolean isIn(final Character lookupFlag, final Character[] value) {
		boolean result = false;
		if (value == null) {
			result = false;
		} else {
			for (final Character item : value) {
				if (item.equals(lookupFlag)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * @param mapWorkFlowParam
	 * @return
	 */
	private ArrayList<Long> getWkfParams(final Map<String, List<Map<String, String>>> mapWorkFlowParam) {
		final ArrayList<Long> result = new ArrayList<>();
		for (final String wkf : mapWorkFlowParam.keySet()) {
			final List<Map<String, String>> listwkfs = mapWorkFlowParam.get(wkf);
			if (listwkfs != null) {
				for (final Map<String, String> workflowParameter : listwkfs) {
					if ("3".equals(workflowParameter.get("PARM_TYP"))) {
						if (ObjectUtils.isFilled(workflowParameter.get("VAL"))) {
							result.add(new Long(workflowParameter.get("VAL")));
						}
					}
				}
			}
		}
		return result;
	}


	/**
	 * @param screenInst 
	 * @param nonBLMButtons 
	 * @param nonBLMButton
	 * @param buttonLangDetails
	 * @param kioskScreens
	 * @param allLocales
	 * @param allMedia
	 * @param restaurantDefLang
	 * @param buttonId
	 * @param masterScreenInfo
	 * @param localizedFields
	 * @return
	 */
	private String commonButton(String screenId, String screenInstanceId, 
								final Map<String, String> nonBLMButton,
								final Map<String, Map<String, String>> buttonLangDetails, 
								final List<String> kioskScreens,
								final List<Map<String, String>> allLocales, 
								final Map<String, String> allMedia, 
								final String restaurantDefLang,  
								final String buttonId, 
								final Map<String, String> masterScreenInfo, 
								final Map<String, String> localizedFields, 
								String restaurantId, PackageGeneratorDTO packageGeneratorDTO) {
		
		boolean foundBtnXMLLang = false;
		final StringBuilder sb = new StringBuilder();
		if (nonBLMButton.containsKey("ButtonXML_" + restaurantDefLang)) {
			foundBtnXMLLang = true;
			sb.append(nonBLMButton.get("ButtonXML_" + restaurantDefLang));
			if (nonBLMButton.containsKey("ButtonActionXML")) {
				sb.append(nonBLMButton.get("ButtonActionXML"));
			}
		}

		if(!foundBtnXMLLang){
			final StringBuilder sbk = new StringBuilder();
			for (String nonBLMButtonkey : nonBLMButton.keySet()) {
				sbk.append(nonBLMButtonkey).append(", ");
			}
			LOGGER.warn("missing  ButtonXML_ "+ restaurantDefLang+"   "+ sbk.toString());
        }
		
		//Button language
		if (buttonLangDetails != null) {
			for (final Map<String, String> localeInfo : allLocales) {
				if (kioskScreens.contains(masterScreenInfo.get("TYP")) || (!kioskScreens.contains(masterScreenInfo.get("TYP")) && localeInfo.get("LANG_ID").equals(restaurantDefLang))) {
					final Map<String, String> buttonLang = buttonLangDetails.get(buttonId + "," + localeInfo.get("LANG_ID"));
					if (null != buttonLang) {
						sb.append("<Language");
						sb.append(getAttribute("code", localeInfo.get("LANG_CD") + "_" + localizedFields.get("CTRY_ID"), false));
						sb.append(getAttribute("name", localeInfo.get("LCLE_NA"), false));
						sb.append(getAttribute("parent", localeInfo.get("LANG_CD"), false));
						sb.append(" >");
						sb.append("<title>");
						sb.append(replaceSpecialCharacters(NVL(buttonLang.get("CPTN"), "")));
						sb.append("</title>");
						if (ObjectUtils.isFilled(allMedia.get(buttonLang.get("BMP")))) {
							sb.append("<bitmap>");
							sb.append(replaceSpecialCharacters(allMedia.get(buttonLang.get("BMP"))));
							sb.append("</bitmap>");
						}
						if (ObjectUtils.isFilled(allMedia.get(buttonLang.get("BMP_PRSD")))) {
							sb.append("<bitmapdn>");
							sb.append(replaceSpecialCharacters(allMedia.get(buttonLang.get("BMP_PRSD"))));
							sb.append("</bitmapdn>");
						}
						sb.append("</Language>");
					}
				}
			}
		}
		sb.append("</Button>");

		return sb.toString();
	}
	

	/**
	 * @param blmButton
	 * @param restaurantMenuItems
	 * @param restaurantDefLang
	 * @param marketLocaleId
	 * @param allColors 
	 * @param allMedia 
	 * @param allWorkflowParams 
	 * @param allWorkflows 
	 * @param allEvents 
	 * @param localizedFields 
	 * @param masterScreenInfo 
	 * @param allLocales 
	 * @param kioskScreens 
	 * @param mapButtonWorkflows 
	 * @param mapButtonProductCode 
	 * @param mapButtonBlmLang 
	 * @param mapButtonWorkflowParams 
	 * @return
	 * @throws GeneratorDBException 
	 */
	private String getBMLButton(final Map<String, String> blmButton,
								final List<String> restaurantMenuItems, String restaurantDefLang,
								final String marketLocaleId, 
								final Map<String, String> allMedia, 
								final Map<String, String> allColors,
								final Map<String, String> allEvents, 
								final Map<String, String> allWorkflows, 
								final Map<String, String> allWorkflowParams,
		                        final String buttonId, List<String> kioskScreens,
		                        final List<Map<String, String>> allLocales, 
		                        final Map<String, String> masterScreenInfo, 
		                        final Map<String, String> localizedFields, Map<String, List<Map<String, String>>> mapButtonWorkflows, 
		                        final Map<String, String> mapButtonProductCode, 
		                        final Map<String, Map<String, Map<String,String>>> mapButtonBlmLang,
		                        final Map<String,List<Map<String,String>>> mapWorkFlowParams, Map<String, String> mapButtonWorkflowParams) {
	    StringBuilder sb = new StringBuilder();
        if( null !=blmButton && !blmButton.isEmpty()){
			if (!ObjectUtils.isFilled(blmButton.get("PRD_CD")) || restaurantMenuItems.contains(blmButton.get("PRD_CD"))) {

				final String blmInstanceId = blmButton.get("SCR_INST_ID");
				String prdCode = null;
				final Map<String, Map<String, String>> buttonBLMLangDetails = mapButtonBlmLang.get(buttonId + "," + blmInstanceId);
				if (buttonBLMLangDetails != null) {
					final Map<String, String> defaultButtonLang = buttonBLMLangDetails.get(blmButton.get("BTTN_ID") + "," + restaurantDefLang);
					final Map<String, String> mktButtonLang = buttonBLMLangDetails.get(blmButton.get("BTTN_ID") + "," + marketLocaleId);
					sb.append("<Button");
					sb.append(getAttribute("number", blmButton.get("BTTN_NU"), false));
					sb.append(getAttribute("category", blmButton.get("CAT"), false));
					if (null != defaultButtonLang) {
						sb.append(getAttribute("title", defaultButtonLang.get("CPTN"), false));
					}
					sb.append(getAttribute("keyscan", blmButton.get("KEY_SCAN"), false));
					sb.append(getAttribute("keyshift", blmButton.get("KEY_SHFT"), false));
					sb.append(getAttribute("bitmap", allMedia.get(NVL(null != defaultButtonLang ? defaultButtonLang.get("BMP") : null, null != mktButtonLang ? mktButtonLang.get("BMP") : null)), true));
					sb.append(getAttribute("bitmapdn", allMedia.get(NVL(null != defaultButtonLang ? defaultButtonLang.get("BMP_PRSD") : null, null != mktButtonLang ? mktButtonLang.get("BMP_PRSD") : null)), true));
					sb.append(getAttribute("textup", allColors.get(NVL(blmButton.get("FRGND_CPTN_COLR_ID"), "") + "," + restaurantDefLang), false));
					sb.append(getAttribute("textdn", allColors.get(NVL(blmButton.get("FRGND_CPTN_COLR_PRSD_ID"), "") + "," + restaurantDefLang), false));
					sb.append(getAttribute("bgup", allColors.get(NVL(blmButton.get("BKGD_CPTN_COLR_ID"), "") + "," + restaurantDefLang), false));
					sb.append(getAttribute("bgdn", allColors.get(NVL(blmButton.get("BKGD_CPTN_COLR_PRSD_ID"), "") + "," + restaurantDefLang), false));
					sb.append(getAttribute("v", blmButton.get("WDTH"), false));
					sb.append(getAttribute("h", blmButton.get("HGHT"), false));
					if (!ObjectUtils.isFilled(blmButton.get("PRD_CD"))) {
						prdCode = mapButtonProductCode.get(buttonId + "," + blmInstanceId);
					} else {
						prdCode = blmButton.get("PRD_CD");
					}
					sb.append(getAttribute("productCode", prdCode, false));
					sb.append(getAttribute("sound", allMedia.get(blmButton.get("sound_file_id")), false));

					String attributeValueOutageMode = blmButton.get("OUTAGE_MODE");
					if ("1".equals(attributeValueOutageMode)) {
						attributeValueOutageMode = "true";
					} else if ("0".equals(attributeValueOutageMode)) {
						attributeValueOutageMode = "false";
					}
					sb.append(getAttribute("outageModeButtonDisabled", attributeValueOutageMode, false));

					sb.append(" >");
				}

				final List<Map<String, String>> buttonWorkflows = mapButtonWorkflows.get(buttonId + "," + blmInstanceId);
				boolean hasAction = false;
				if (buttonWorkflows != null) {
					for (final Map<String, String> buttonWorkflow : buttonWorkflows) {
						hasAction = true;
						sb.append("<Action");
						sb.append(getAttribute("type", allEvents.get(buttonWorkflow.get("EVNT_ID")), false));
						sb.append(getAttribute("workflow", allWorkflows.get(buttonWorkflow.get("WRKFL_ID")), false));
						sb.append(" >");

						mapWorkFlowParams.get(buttonWorkflow.get("BTTN_WRKFL_ASGN_ID"));
						final List<Map<String, String>> workflowParameters = mapWorkFlowParams.get(buttonWorkflow.get("BTTN_WRKFL_ASGN_ID"));
						if (workflowParameters != null) {
							for (final Map<String, String> workflowParameter : workflowParameters) {
								sb.append("<Parameter");
								sb.append(getAttribute("name", allWorkflowParams.get(workflowParameter.get("PARM_ID")), false));
								if ("3".equals(workflowParameter.get("PARM_TYP"))) {
									if (ObjectUtils.isFilled(workflowParameter.get("VAL"))) {
										sb.append(getAttribute("value", mapButtonWorkflowParams.get(workflowParameter.get("VAL")), false));
									} else {
										sb.append(getAttribute("value", null, false));
									}
								} else {
									sb.append(getAttribute("value", workflowParameter.get("VAL"), false));
								}
								sb.append(" />");
							}
						}
						sb.append("</Action>");
					}
				}
				if (!hasAction && (prdCode != null)) {
					sb.append(getWorkFlowDefault(prdCode, masterScreenInfo.get("TYP")));
				}

				Map<String, String> buttonLang = null;
				for (final Map<String, String> localeInfo : allLocales) {
					if (kioskScreens.contains(masterScreenInfo.get("TYP")) || (!kioskScreens.contains(masterScreenInfo.get("TYP")) && localeInfo.get("LANG_ID").equals(restaurantDefLang))) {
						if (null != buttonBLMLangDetails) {
							buttonLang = buttonBLMLangDetails.get(blmButton.get("BTTN_ID") + "," + localeInfo.get("LANG_ID"));
						}
						if (null != buttonLang) {
							sb.append("<Language");
							sb.append(getAttribute("code", localeInfo.get("LANG_CD") + "_" + localizedFields.get("CTRY_ID"), false));
							sb.append(getAttribute("name", localeInfo.get("LCLE_NA"), false));
							sb.append(getAttribute("parent", localeInfo.get("LANG_CD"), false));
							sb.append(" >");
							sb.append("<title>");
							sb.append(replaceSpecialCharacters(NVL(buttonLang.get("CPTN"), "")));
							sb.append("</title>");
							if (ObjectUtils.isFilled(allMedia.get(buttonLang.get("BMP")))) {
								sb.append("<bitmap>");
								sb.append(replaceSpecialCharacters(allMedia.get(buttonLang.get("BMP"))));
								sb.append("</bitmap>");
							}
							if (ObjectUtils.isFilled(allMedia.get(buttonLang.get("BMP_PRSD")))) {
								sb.append("<bitmapdn>");
								sb.append(replaceSpecialCharacters(allMedia.get(buttonLang.get("BMP_PRSD"))));
								sb.append("</bitmapdn>");
							}
							sb.append("</Language>");
						}

					}

				}
				sb.append("</Button>");
			}
		}
		return sb.toString();
	}
	
	
	public Map<String, List<Map<String, String>>>  getButtonWorkflows(ArrayList<Long>  buttonBlmIds, ArrayList<Long>   blmInstanceIds) throws Exception{		
		Map<String, List<Map<String, String>>> workflows = screenDAO.getButtonWorkflows( buttonBlmIds, blmInstanceIds);		
		return 	workflows ;	
	}
	
	
	public  Map<String, String> getButtonProductCode(ArrayList<Long> buttonIds, ArrayList<Long>  blmInstanceIds) throws Exception{					
		 Map<String, String> result = screenDAO.getButtonProductCode(buttonIds, blmInstanceIds);		
		return  result;	
	}
	
	public Map<String,Map<String, Map<String, String>>> getBLMLangDetails(ArrayList<Long> buttonBlmIds, ArrayList<Long>  blmInstanceIds) throws Exception{						
		Map<String,Map<String, Map<String, String>>>   result = screenDAO.getBLMLangDetails( buttonBlmIds,  blmInstanceIds);		
		return 	 result;	
	}
	public Map<Long,ProductDetails> getRestaurantProducts( PackageGeneratorDTO packageGeneratorDTO,String restaurantDefLang) throws Exception{
		RequestDTO requestDTO = new RequestDTO();
		layeringService.setExcludeInactiveMI(productDBDAO.getValuesFromGlobalParam(packageGeneratorDTO.getMarketID(), ProductDBConstant.EXCLUDE_INACTIVE_MENU_ITEMS));	
		requestDTO.setMarketId(packageGeneratorDTO.getMarketID());
		requestDTO.setEffectiveDate(packageGeneratorDTO.getDate());
		requestDTO.setNodeId(packageGeneratorDTO.getNodeID());
      if (packageGeneratorDTO.getRestaurant() == null) {
			packageGeneratorDTO.setRestSetIds(objNames.retrieveRestSetId(packageGeneratorDTO.getNodeID(), packageGeneratorDTO.getMarketID()));
			packageGeneratorDTO.setRestaurant(layeringService.getRestaurantSets(requestDTO));
			packageGeneratorDTO.setMasterSetId(objNames.retrieveMasterSetId(packageGeneratorDTO.getMarketID()));
		}
		Map<Long, Product> productStatusMap =  layeringService.getProductDualStatusByRest(packageGeneratorDTO);
		Map<String, String> allColors = getAllColors(packageGeneratorDTO.getScheduleRequestID(),packageGeneratorDTO.getMarketID());
		Map<String, String> allImage =new HashMap<>();
		final List<Map<String, Object>> allMediaList= productDBService.getProductMediaData(packageGeneratorDTO.getScheduleRequestID(), packageGeneratorDTO.getMarketID());
		for (final Map<String, Object> mediaProductData : allMediaList) {
			allImage.put((mediaProductData.get("lgl_id").toString()),
					(mediaProductData.get("mdia_file_na").toString()));
		}
		List<Long> prdList=new ArrayList<>();
		for(Long key : productStatusMap.keySet() ) 
		{		
			prdList.add(key);
		}
		Restaurant restaurant = packageGeneratorDTO.getRestaurant();
		final Long masterSetId = packageGeneratorDTO.getMasterSetId();
		Map<Long, ProductDetails> listOfProductsByMaster = screenDAO.getProductPosKvsPresentationByMaster(requestDTO, masterSetId,allColors,allImage,prdList,restaurantDefLang);
		Map<Long, ProductDetails> listOfProductsByRestaurant=new HashMap<>();
		
		
		for(com.rfm.packagegeneration.dto.Set oSet : restaurant.getMenuItemSets()) {
			screenDAO.getProductPosKvsPresentationBySet(listOfProductsByMaster,requestDTO,ProductDBConstant.MIS_TYPE, oSet.getSetId() ,allColors,allImage,prdList,restaurantDefLang); 
		}
		final List< Long > restSetIds = packageGeneratorDTO.getRestSetIds();
		for(Long restSetId : restSetIds) {
			screenDAO.getProductPosKvsPresentationBySet(listOfProductsByMaster, requestDTO,ProductDBConstant.RMI_TYPE,  restSetId, allColors,allImage,prdList,restaurantDefLang);						
		}
		boolean excludeInactiveMI = layeringService.getExcludeInactiveMI().toUpperCase().equalsIgnoreCase("YES");
		for(Long key : listOfProductsByMaster.keySet() ) {	
		Product product=productStatusMap.get(key);
		ProductDetails productdtls=listOfProductsByMaster.get(key);
		productdtls.setActive(product.getActive());
		productdtls.setApprovalStatus(product.getApprovalStatus());
		if(!excludeInactiveMI
			|| (excludeInactiveMI && (productdtls.getActive()==1 
									|| (productdtls.getActive()==0 
										&& null!=productdtls.getAuxiliaryMenuItem()
										&& productdtls.getAuxiliaryMenuItem().equals(1L))))){
			listOfProductsByRestaurant.put(key, productdtls);	
		}
	}		
		return listOfProductsByRestaurant;
	}


}
