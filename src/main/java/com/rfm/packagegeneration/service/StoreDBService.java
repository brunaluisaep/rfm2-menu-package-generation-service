package com.rfm.packagegeneration.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rfm.packagegeneration.cache.CacheService;
import com.rfm.packagegeneration.constants.GeneratorConstant;
import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.dao.LayeringProductDBDAO;
import com.rfm.packagegeneration.dao.NamesDBDAO;
import com.rfm.packagegeneration.dao.ProductDBDAO;
import com.rfm.packagegeneration.dao.StoreDBDAO;
import com.rfm.packagegeneration.dto.Adaptor;
import com.rfm.packagegeneration.dto.Adaptors;
import com.rfm.packagegeneration.dto.BunBufferDetails;
import com.rfm.packagegeneration.dto.BusinessLimits;
import com.rfm.packagegeneration.dto.CategoryDetails;
import com.rfm.packagegeneration.dto.CategoryHours;
import com.rfm.packagegeneration.dto.ChargeRules;
import com.rfm.packagegeneration.dto.ColorDb;
import com.rfm.packagegeneration.dto.Configuration;
import com.rfm.packagegeneration.dto.Configurations;
import com.rfm.packagegeneration.dto.CustomDayPart;
import com.rfm.packagegeneration.dto.DayPartSet;
import com.rfm.packagegeneration.dto.DeliveryOrderingHours;
import com.rfm.packagegeneration.dto.DeliverySetDetails;
import com.rfm.packagegeneration.dto.Deposit;
import com.rfm.packagegeneration.dto.DiscountTable;
import com.rfm.packagegeneration.dto.Fee;
import com.rfm.packagegeneration.dto.FlavourSet;
import com.rfm.packagegeneration.dto.HotBusinessLimit;
import com.rfm.packagegeneration.dto.IngredientGroupDetails;
import com.rfm.packagegeneration.dto.KSGroup;
import com.rfm.packagegeneration.dto.KitchenGroup;
import com.rfm.packagegeneration.dto.LanguageDetails;
import com.rfm.packagegeneration.dto.LargeOrderRules;
import com.rfm.packagegeneration.dto.Localization;
import com.rfm.packagegeneration.dto.MinOrderRules;
import com.rfm.packagegeneration.dto.NameTable;
import com.rfm.packagegeneration.dto.Notification;
import com.rfm.packagegeneration.dto.PPGGroup;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;
import com.rfm.packagegeneration.dto.Parameter;
import com.rfm.packagegeneration.dto.PluLargeOrderRules;
import com.rfm.packagegeneration.dto.PopulateDrinkVol;
import com.rfm.packagegeneration.dto.PrdKVSRoute;
import com.rfm.packagegeneration.dto.ProductDetails;
import com.rfm.packagegeneration.dto.ProductGroup;
import com.rfm.packagegeneration.dto.Production;
import com.rfm.packagegeneration.dto.PromotionGroupDetail;
import com.rfm.packagegeneration.dto.PromotionImages;
import com.rfm.packagegeneration.dto.ProviderDetails;
import com.rfm.packagegeneration.dto.ResponseMicroServiceDTO;
import com.rfm.packagegeneration.dto.Section;
import com.rfm.packagegeneration.dto.SetIds;
import com.rfm.packagegeneration.dto.SizeSelection;
import com.rfm.packagegeneration.dto.StoreDB;
import com.rfm.packagegeneration.dto.StoreDBRequest;
import com.rfm.packagegeneration.dto.StoreDetails;
import com.rfm.packagegeneration.dto.StoreHours;
import com.rfm.packagegeneration.dto.StorePromotionDiscounts;
import com.rfm.packagegeneration.dto.TaxChain;
import com.rfm.packagegeneration.dto.TaxDefinition;
import com.rfm.packagegeneration.dto.TaxTable;
import com.rfm.packagegeneration.dto.TaxType;
import com.rfm.packagegeneration.dto.TenderType;
import com.rfm.packagegeneration.dto.TenderTypes;
import com.rfm.packagegeneration.dto.VolumeTable;
import com.rfm.packagegeneration.dto.WeekDays;
import com.rfm.packagegeneration.utility.DateUtility;
import com.rfm.packagegeneration.utility.ObjectUtils;
import com.rfm.packagegeneration.utility.PackageGenDateUtility;
import com.rfm.packagegeneration.utility.PackageGenerationUtility;
import com.rfm.packagegeneration.utility.PackageWriter;
import com.rfm.packagegeneration.utility.StringHelper;


@Service
public class StoreDBService {
	
	@Autowired
	ScreenService screenService;

	@Autowired
	private StoreDBDAO storeDBDAO;
	
	@Autowired
	private NamesDBDAO namesDBDAO;
	
	@Autowired
	private ProductDBDAO productDBDAO;
	
	@Autowired
	CacheService cacheService;
	
	@Value("${application.redis.cacheTTL}")
	private Long cacheTTLInMinutes;
	
	@Autowired
	private LayeringProductDBDAO layeringDBDAO;
	
	@Autowired
	private LayeringProductService layeringProductService;
	
	private static final Logger LOGGER = LogManager.getLogger("StoreDBService");
	
	public StoreDB getStoreDB(StoreDBRequest storeDBRequest) {
		return new StoreDB();
	}

	public StoreDetails getStoreDetails(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getStoreDetails(storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getMktId());
	}
	
	public List<DayPartSet> getDayPartSet(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getDayPartSet(storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate(),storeDBRequest.getNodeId());
	}
	
	public List<FlavourSet> getFlavourSet(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getFlavourSet(storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getMktId());
	}
	
	public List<SizeSelection> getSizeSelection(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getSizeSelection(storeDBRequest.getMktId());
	}
	
	public List<PopulateDrinkVol> getPopulateDrinkVol(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getPopulateDrinkVol(storeDBRequest.getMktId());
	}
	
	public List<PromotionImages> getPromotionImages(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getPromotionImages(storeDBRequest.getMktId());
	}
	
	public List<CategoryHours> getCategoryHours(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getCategoryHours(storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getMktId());
	}
		
	public DeliverySetDetails getDeliverySetDetails(Long restId,Long restInstId,Long mktId ) throws Exception {
		
		DeliverySetDetails set=new DeliverySetDetails();
		
		 Long deliverySetId = storeDBDAO.getDeliverySetId(restId, restInstId, mktId);
		 set.setDeliveryOrderingHours(storeDBDAO.getDeliveryOrderingHours(restId, "4", restInstId));
		 set.setDeliveryAdvanceOrderingHours(storeDBDAO.getDeliveryOrderingHours(restId, "5", restInstId));
		 set.setCustomDayparts(storeDBDAO.getCustomDayPart(mktId,deliverySetId));
		 set.setDeliveryChargeRules(storeDBDAO.getChargeRules(deliverySetId));
		 set.setMinimumOrderValueRules(storeDBDAO.getMinOrderRules(deliverySetId));
		 set.setLargeOrderRules(storeDBDAO.getLargeOrderRules(deliverySetId, mktId));
		 set.setPluLargeOrderRules(storeDBDAO.getPluLargeOrderRules(deliverySetId,mktId));
		 set.setLargeOrderAllowed(storeDBDAO.getLargeOrderAllowed(deliverySetId));
		 set.setDeliverySetId(deliverySetId);
		 return set;
	}
	

	public DeliverySetDetails getDeliverySetDetails(StoreDBRequest storeDBRequest) throws Exception {
		return getDeliverySetDetails(storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getMktId());
	}
	
	public List<ColorDb> getColorDb(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getColorDb(storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate(),storeDBRequest.getMktId());
	}
	
	public List<StorePromotionDiscounts> getStorePromotionDiscounts(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getStorePromotionDiscounts(storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate(),storeDBRequest.getMktId());
	}

	public ResponseMicroServiceDTO generateFileString(PackageGeneratorDTO packageGeneratorDTO) throws Exception {		
		ResponseMicroServiceDTO genDto=new ResponseMicroServiceDTO();
		if(!PackageGenerationUtility.getInstance().shouldFileBeCreated(GeneratorConstant.FILETYPE_STORE_DB_XML, packageGeneratorDTO)) {
			genDto.setGenerated(false);
			return genDto;
		}
		String storeVersion=storeDBDAO.getStoreDBVersion(packageGeneratorDTO.getMarketID());
		PackageWriter bufferWriter = new PackageWriter( GeneratorConstant.STORE_DB_XML_FILENAME, GeneratorConstant.STORE_DB_XML, packageGeneratorDTO, GeneratorConstant.STORE_SCHEMA_TYPE_NAMES );
		
		bufferWriter.append("\n" + "<Document xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " );
		bufferWriter.append( " xsi:noNamespaceSchemaLocation=\"/RFM2/RFM2PackageConf/PackageXSD/2.1/store-db.xsd\" > " );breakline(bufferWriter);
		bufferWriter.append( "<StoreDB type=\"all\"  version=\""+storeVersion+"\">");breakline(bufferWriter);		
		if ( packageGeneratorDTO.isGeneratedSeqNum() && packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() != null && !packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO().isEmpty() ) {
			bufferWriter.append( "<StoreDBSeqNumber>" + packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() + "</StoreDBSeqNumber>" );breakline(bufferWriter);
		}
		
		String timeFormatOfGMT = PackageGenDateUtility.getTimeFormatOfGMT();
		packageGeneratorDTO.getGeneratorDefinedValues().setTimeStamp(timeFormatOfGMT);
		String currentTimestampForMarket = packageGeneratorDTO.getGeneratorDefinedValues().getTimeStamp();
		
		String storeDbDate =currentTimestampForMarket.substring(GeneratorConstant.INT_ZERO_CONSTANT, GeneratorConstant.INT_TEN_CONSTANT);
		String storeDbTime = currentTimestampForMarket.substring(GeneratorConstant.INT_ELEVEN_CONSTANT, GeneratorConstant.INT_NINETEEN_CONSTANT);
		
		bufferWriter.append("<StoreDBDate>"+storeDbDate+"</StoreDBDate>");breakline(bufferWriter);
		bufferWriter.append("<StoreDBTime>"+storeDbTime+"</StoreDBTime>");breakline(bufferWriter);
		bufferWriter.append("<StoreProfile>");breakline(bufferWriter);
		//TaxTable
		final PackageXMLParametersDTO[] params = packageGeneratorDTO.getPackageXmlParameter();
		String scriptManagementFlag ="";
		for (final PackageXMLParametersDTO param : params) {
			if (param != null && (GeneratorConstant.SCRIPT_MANAGEMENT_FLAG.equalsIgnoreCase(param.getParm_na()))) {
				scriptManagementFlag = param.getParm_val();
			}
		}
		
		boolean isHotSelected = storeDBDAO.isHotSelected(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), 
				Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()));
		
		//storeDetails
		StoreDetails storeDetails=storeDBDAO.getStoreDetails(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), 
				Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()),
				packageGeneratorDTO.getMarketID());
		if(storeDetails!=null) {
			bufferWriter.append( "<StoreDetails>");breakline(bufferWriter);
			bufferWriter.append( "<RfmId>"+storeDetails.getRfmId().toString()+"</RfmId>");breakline(bufferWriter);
			bufferWriter.append( "<CompanyId>"+storeDetails.getCompanyId().toString()+"</CompanyId>");breakline(bufferWriter);
			bufferWriter.append( "<StoreId>"+storeDetails.getStoreId() .toString()+"</StoreId>");breakline(bufferWriter);
			bufferWriter.append( "<StoreLegacyId>"+storeDetails.getStoreLegacyId().toString()+"</StoreLegacyId>");breakline(bufferWriter);
			bufferWriter.append( "<StoreAddress>"+ storeDetails.getStoreAddress()+"</StoreAddress>");breakline(bufferWriter);
			bufferWriter.append( "<StoreZipCode>"+ storeDetails.getStoreZipCode()+"</StoreZipCode>");breakline(bufferWriter);
			bufferWriter.append( "<City>"+ storeDetails.getCity()+"</City>");breakline(bufferWriter);
			bufferWriter.append( "<State>"+ storeDetails.getState()+"</State>");breakline(bufferWriter);
			bufferWriter.append( "<Country>"+storeDetails.getCountry()+"</Country>");breakline(bufferWriter);
			bufferWriter.append( "<Email>"+storeDetails.getEmail()+"</Email>");breakline(bufferWriter);
			bufferWriter.append( "<HomePage>"+storeDetails.getHomePage()+"</HomePage>");breakline(bufferWriter);
			
			bufferWriter.append( "<StorePhone>"+storeDetails.getStorePhone()+"</StorePhone>");breakline(bufferWriter);
			bufferWriter.append( "<HelpDeskInfo>"+ storeDetails.getHelpDeskInfo()+"</HelpDeskInfo>" );breakline(bufferWriter);
			bufferWriter.append( "<OwnershipType>"+ storeDetails.getOwnershipType()+"</OwnershipType>");breakline(bufferWriter);
			
			if(storeDetails.getStoreType()!=null && !storeDetails.getStoreType().isEmpty()) {
				bufferWriter.append( "<StoreType>");
				for(String point:storeDetails.getStoreType()) {
					bufferWriter.append("<Point>"+point+"</Point>");breakline(bufferWriter);
				}
				bufferWriter.append( "</StoreType>");breakline(bufferWriter);
			}
			if(storeDetails.getStoreLatitude()!=null && !storeDetails.getStoreLatitude().isBlank()) {
				bufferWriter.append( "<StoreLatitude>"+storeDetails.getStoreLatitude()+"</StoreLatitude>");breakline(bufferWriter);}
			if(storeDetails.getStoreLongitude()!=null && !storeDetails.getStoreLongitude().isBlank()) {
				bufferWriter.append( "<StoreLongitude>"+ storeDetails.getStoreLongitude()+"</StoreLongitude>");breakline(bufferWriter);}
			bufferWriter.append( "</StoreDetails>");breakline(bufferWriter);
			LOGGER.info("StoreDBService::getStoreDetails():: Copied to StoreDB XML"  );
		}
//		BusinessLimits
		List<BusinessLimits> businessLimits = storeDBDAO.getBusinessLimit(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getDate(),packageGeneratorDTO.getMarketID());
		if(null!=businessLimits && businessLimits.size()>0) {
			bufferWriter.append( "<BusinessLimits>");breakline(bufferWriter);
			for(BusinessLimits limits : businessLimits ) {
				bufferWriter.append(getTag("SkimTimeLimit",limits.getSkimTimeLimit()));	
				bufferWriter.append(getTag("SkimAmountLimit",limits.getSkimAmountLimit()));	
				bufferWriter.append(getTag("ManagerModeTimeLimit",limits.getManagerModeTimeLimit()));	
				bufferWriter.append(getTag("HighAmountSaleLimit",limits.getHighAmountSaleLimit()));	
				bufferWriter.append(getTag("HighQuantitySaleLimit",limits.getHighQuantitySaleLimit()));	
				bufferWriter.append(getTag("AutoReceiptPrintAmount",limits.getAutoReceiptPrintAmount()));	
				bufferWriter.append(getTag("FcOrderTakerTimeOver",limits.getFcOrderTakerTimeOver()));	
				bufferWriter.append(getTag("FcCashieringTimeOver",limits.getFcCashieringTimeOver()));
				bufferWriter.append(getTag("FcStoreTimeOver",limits.getFcStoreTimeOver()));
				bufferWriter.append(getTag("DtWinOneTimeOver",limits.getDtWinOneTimeOver()));
				bufferWriter.append(getTag("DtWinTwoTimeOver",limits.getDtWinTwoTimeOver()));
				bufferWriter.append(getTag("DtHeldTimeOver",limits.getDtHeldTimeOver()));
				bufferWriter.append(getTag("ReductionValidationMode",limits.getReductionValidationMode()));
				int defaultPromoValidationValue =0;
				if(null!=limits.getPromoValidationMode()) {
					bufferWriter.append(getTag("PromoValidationMode",limits.getPromoValidationMode()));
				}else {
					bufferWriter.append(getTag("PromoValidationMode",String.valueOf(defaultPromoValidationValue)));
				}
				bufferWriter.append(getTag("TRedBeforeTotal",limits.gettRedBeforeTotal()));
				bufferWriter.append(getTag("TRedAfterTotalAmount",limits.gettRedAfterTotalAmount()));
				bufferWriter.append(getTag("TRedAfterTotalQuantity",limits.gettRedAfterTotalQuantity()));
				bufferWriter.append(getTag("BreakfastStartTimeWeekDay",limits.getBreakfastStartTimeWeekDay()));
				bufferWriter.append(getTag("BreakfastStopTimeWeekDay",limits.getBreakfastStopTimeWeekDay()));
				bufferWriter.append(getTag("BreakfastStartTimeWeekEnd",limits.getBreakfastStartTimeWeekEnd()));
				bufferWriter.append(getTag("BreakfastStopTimeWeekEnd",limits.getBreakfastStopTimeWeekEnd()));
				bufferWriter.append(getTag("BlockTime",limits.getBlockTime()));
				bufferWriter.append(getTag("InitialFloatLimit",limits.getInitialFloatLimit()));
				bufferWriter.append(getTag("FutureDayOpenLimit",limits.getFutureDayOpenLimit()));
				bufferWriter.append(getTag("StoreWidePettyCashAmountLimit",limits.getStoreWidePettyCashAmountLimit()));
				bufferWriter.append(getTag("PromoItemQuantityLimit",limits.getPromoItemQuantityLimit()));					
				bufferWriter.append(getTag("BypassPromoPerItemQuantityLimit",limits.getBypassPromoPerItemQuantityLimit()));
				bufferWriter.append(getTag("BypassIndividualDiscountAmountLimit",limits.getBypassIndividualDiscountAmountLimit()));
				bufferWriter.append(getTag("DiscountQuantityLimit",limits.getDiscountQuantityLimit()));
				bufferWriter.append(getTag("CouponQuantityLimit",limits.getCouponQuantityLimit()));
				bufferWriter.append(getTag("DiscountAmountLimit",limits.getDiscountAmountLimit()));
				bufferWriter.append(getTag("NoTaxSalesAmountLimit",limits.getNoTaxSalesAmountLimit()));
				bufferWriter.append(getTag("TRedAmountBeforeTotal",limits.gettRedAmountBeforeTotal()));
				bufferWriter.append(getTag("CouponAmountLimit",limits.getCouponAmountLimit()));
				bufferWriter.append(getTag("CrewTRedDailyLimit",limits.getCrewTRedDailyLimit()));
				bufferWriter.append(getTag("CrewPromoDailyLimit",limits.getCrewPromoDailyLimit()));
				bufferWriter.append(getTag("DailyLimitExclusionList",limits.getDailyLimitExclusionList()));
				bufferWriter.append(getTag("PromoItemAmountLimit",limits.getPromoItemAmountLimit()));					
			}
			bufferWriter.append( "</BusinessLimits>");breakline(bufferWriter);
			LOGGER.info("StoreDBService::getBusinessLimit():: Copied to StoreDB XML"  );
		}
			

//			DeliverySet
			
			DeliverySetDetails deliverySetDetails=getDeliverySetDetails(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()),packageGeneratorDTO.getMarketID());
			List<Notification> notificationlist = storeDBDAO.getNotification(deliverySetDetails.getDeliverySetId());
			if(deliverySetDetails!=null && deliverySetDetails.getDeliverySetId()!=null && deliverySetDetails.getDeliverySetId()>0) {
				bufferWriter.append("<DeliveryDefinitions>");breakline(bufferWriter);
				bufferWriter.append("<DeliveryOrderingHours>");breakline(bufferWriter);
				if( deliverySetDetails.getDeliveryOrderingHours()!=null && deliverySetDetails.getDeliveryOrderingHours().size() > 0) {
					for(DeliveryOrderingHours deliveryOrderingHours : deliverySetDetails.getDeliveryOrderingHours()) {
					
						String salesTyp=null;
						if(deliveryOrderingHours.getPriceCodeConv().equals("1"))
							salesTyp="saleType=\"Eatin";
						if(deliveryOrderingHours.getPriceCodeConv().equals("2"))
							salesTyp="saleType=\"Takeout";
						getDayOfWeek(bufferWriter,WeekDays.Monday.name(),deliveryOrderingHours.getMonStartTime(),deliveryOrderingHours.getMonEndTime(),salesTyp);
						getDayOfWeek(bufferWriter,WeekDays.Tuesday.name(),deliveryOrderingHours.getTueStartTime(),deliveryOrderingHours.getTueEndTime(),salesTyp);
						getDayOfWeek(bufferWriter,WeekDays.Wednesday.name(),deliveryOrderingHours.getWedStartTime(),deliveryOrderingHours.getWedEndTime(),salesTyp);
						getDayOfWeek(bufferWriter,WeekDays.Thursday.name(),deliveryOrderingHours.getThuStartTime(),deliveryOrderingHours.getThuEndTime(),salesTyp);
						getDayOfWeek(bufferWriter,WeekDays.Friday.name(),deliveryOrderingHours.getFriStartTime(),deliveryOrderingHours.getFriEndTime(),salesTyp);
						getDayOfWeek(bufferWriter,WeekDays.Saturday.name(),deliveryOrderingHours.getSatStartTime(),deliveryOrderingHours.getSatEndTime(),salesTyp);
						getDayOfWeek(bufferWriter,WeekDays.Sunday.name(),deliveryOrderingHours.getSunStartTime(),deliveryOrderingHours.getSunEndTime(),salesTyp);					
					}
				}
				
				
				bufferWriter.append("</DeliveryOrderingHours>");breakline(bufferWriter);
				
				bufferWriter.append("<DeliveryAdvanceOrderingHours>");breakline(bufferWriter);
				if( deliverySetDetails.getDeliveryAdvanceOrderingHours()!=null && deliverySetDetails.getDeliveryAdvanceOrderingHours().size() > 0) {
					for(DeliveryOrderingHours deliveryOrderingHours : deliverySetDetails.getDeliveryAdvanceOrderingHours()) {
					
						String salesType=null;
						if(deliveryOrderingHours.getPriceCodeConv().equals("1"))
							salesType="saleType=\"Eatin";
						if(deliveryOrderingHours.getPriceCodeConv().equals("2"))
							salesType="saleType=\"Takeout";
						getDayOfWeek(bufferWriter,WeekDays.Monday.name(),deliveryOrderingHours.getMonStartTime(),deliveryOrderingHours.getMonEndTime(),salesType);
						getDayOfWeek(bufferWriter,WeekDays.Tuesday.name(),deliveryOrderingHours.getTueStartTime(),deliveryOrderingHours.getTueEndTime(),salesType);
						getDayOfWeek(bufferWriter,WeekDays.Wednesday.name(),deliveryOrderingHours.getWedStartTime(),deliveryOrderingHours.getWedEndTime(),salesType);
						getDayOfWeek(bufferWriter,WeekDays.Thursday.name(),deliveryOrderingHours.getThuStartTime(),deliveryOrderingHours.getThuEndTime(),salesType);
						getDayOfWeek(bufferWriter,WeekDays.Friday.name(),deliveryOrderingHours.getFriStartTime(),deliveryOrderingHours.getFriEndTime(),salesType);
						getDayOfWeek(bufferWriter,WeekDays.Saturday.name(),deliveryOrderingHours.getSatStartTime(),deliveryOrderingHours.getSatEndTime(),salesType);
						getDayOfWeek(bufferWriter,WeekDays.Sunday.name(),deliveryOrderingHours.getSunStartTime(),deliveryOrderingHours.getSunEndTime(),salesType);
					}
				}
				
				bufferWriter.append("</DeliveryAdvanceOrderingHours>");breakline(bufferWriter);
				
				bufferWriter.append("<CustomDayparts>");breakline(bufferWriter);
			if( deliverySetDetails.getCustomDayparts()!=null && deliverySetDetails.getCustomDayparts().size() > 0) {
				for(CustomDayPart customDayPart : deliverySetDetails.getCustomDayparts()) {
					
					bufferWriter.append( "<CustomDaypart id = " + "\"" + customDayPart.getDataId() + "\"" + " name=" + "\"" + customDayPart.getDyptPrdName() + "\"" + ">");breakline(bufferWriter);
					bufferWriter.append( "<DayOfWeek name=" + "\"" + WeekDays.Sunday.name() + "\"" + "    fromTime=" + "\"" + customDayPart.getSunStartTime() + "\"" + " toTime=" + "\"" + customDayPart.getSunEndTime() + "\"" + "/>");breakline(bufferWriter);
					bufferWriter.append( "<DayOfWeek name=" + "\"" + WeekDays.Monday.name() + "\"" + "    fromTime=" + "\"" + customDayPart.getMonStartTime() + "\"" + " toTime=" + "\"" + customDayPart.getMonEndTime() + "\"" + "/>");breakline(bufferWriter);
					bufferWriter.append( "<DayOfWeek name=" + "\"" + WeekDays.Tuesday.name() + "\"" + "   fromTime=" + "\"" + customDayPart.getTueStartTime() + "\"" + " toTime=" + "\"" + customDayPart.getTueEndTime() + "\"" + "/>");breakline(bufferWriter);
					bufferWriter.append( "<DayOfWeek name=" + "\"" + WeekDays.Wednesday.name() + "\"" + " fromTime=" + "\"" + customDayPart.getWedStartTime() + "\"" + " toTime=" + "\"" + customDayPart.getWedEndTime() + "\"" + "/>");breakline(bufferWriter);
					bufferWriter.append( "<DayOfWeek name=" + "\"" + WeekDays.Thursday.name() + "\"" + "  fromTime=" + "\"" + customDayPart.getThuStartTime() + "\"" + " toTime=" + "\"" + customDayPart.getThuEndTime() + "\"" + "/>");breakline(bufferWriter);
					bufferWriter.append( "<DayOfWeek name=" + "\"" + WeekDays.Friday.name() + "\"" + "    fromTime=" + "\"" + customDayPart.getFriStartTime() + "\"" + " toTime=" + "\"" + customDayPart.getFriEndTime() + "\"" + "/>");breakline(bufferWriter);
					bufferWriter.append( "<DayOfWeek name=" + "\"" + WeekDays.Saturday.name() + "\"" + "  fromTime=" + "\"" + customDayPart.getSatStartTime() + "\"" + " toTime=" + "\"" + customDayPart.getSatEndTime() + "\"" + "/>");breakline(bufferWriter);
					bufferWriter.append("</CustomDaypart>");breakline(bufferWriter);
				}
			}
				bufferWriter.append("</CustomDayparts>");breakline(bufferWriter);
			
				bufferWriter.append("<DeliveryChargeRules>");breakline(bufferWriter);
			if( deliverySetDetails.getDeliveryChargeRules()!=null && deliverySetDetails.getDeliveryChargeRules().size() > 0) {
				for(ChargeRules chargeRules : deliverySetDetails.getDeliveryChargeRules()) {

					bufferWriter.append( "<OrderChannel name=" +  "\"" + chargeRules.getOrderChannel() +  "\"" + ">");breakline(bufferWriter);
					bufferWriter.append( "<DCRule dcRuleId="  +  "\"" + chargeRules.getRuleId() +  "\"" + " customDayPartId =" +  "\"" + chargeRules.getDayPartId() + "\"" );breakline(bufferWriter);
					if(chargeRules.getTotMinThreshold()!=null) {
					bufferWriter.append( "orderTotalMinimumThreshold=" +  "\"" + chargeRules.getTotMinThreshold() + "\"" );breakline(bufferWriter);
					}else {
						bufferWriter.append( "orderTotalMinimumThreshold=" +  "\"" +  "\"" );breakline(bufferWriter);
					}
					if (chargeRules.getPayType() != null) {
						bufferWriter.append( "deliveryPaymentType=" +  "\"" + chargeRules.getPayType() + "\"" );breakline(bufferWriter);
					} else {
						bufferWriter.append( "deliveryPaymentType=" +  "\""  + "\"" );breakline(bufferWriter);
					}
					if (chargeRules.getBinNumber() != null) {
						bufferWriter.append( "binNumber=" +  "\"" + chargeRules.getBinNumber() + "\"" );breakline(bufferWriter);
					} else {
						bufferWriter.append( "binNumber=" +  "\""  + "\"" );breakline(bufferWriter);
					}
					if (chargeRules.getMinLimitMinutes() != null) {
						bufferWriter.append( "advancedOrderMinimumTimeLimitMinutes=" +  "\"" + chargeRules.getMinLimitMinutes() + "\"" );breakline(bufferWriter);
					} else {
						bufferWriter.append( "advancedOrderMinimumTimeLimitMinutes=" +  "\"" +  "\"" );breakline(bufferWriter);
					}
					if (chargeRules.getMaxLimitMinutes() != null) {
						bufferWriter.append( "advancedMaximumTimeLimitMinutes=" +  "\"" + chargeRules.getMaxLimitMinutes() + "\""  + " />");breakline(bufferWriter);	
					} else {
						bufferWriter.append( "advancedMaximumTimeLimitMinutes=" +  "\"" +  "\""  + "/>");breakline(bufferWriter);
					}
				}
				bufferWriter.append("</OrderChannel>");breakline(bufferWriter);
			}
				bufferWriter.append("</DeliveryChargeRules>");breakline(bufferWriter);
			
				bufferWriter.append("<MinimumOrderValueRules>");breakline(bufferWriter);
			if( deliverySetDetails.getMinimumOrderValueRules()!=null && deliverySetDetails.getMinimumOrderValueRules().size() > 0) {
				for(MinOrderRules minOrderRules : deliverySetDetails.getMinimumOrderValueRules()) {

					bufferWriter.append( "<OrderChannel name=" + "\"" + minOrderRules.getOrderChannel() + "\"" + ">");breakline(bufferWriter);
					bufferWriter.append( "<MOVRule movRuleId="  + "\"" + minOrderRules.getRuleId() + "\"" + "  customDayPartId =" + "\"" + minOrderRules.getDayPartId() + "\"");breakline(bufferWriter);
					if (minOrderRules.getOrderValue() != null) {
						bufferWriter.append( "minimumOrderValue=" + "\"" + minOrderRules.getOrderValue() + "\"" + "/>");breakline(bufferWriter);
					} else {
						bufferWriter.append( "minimumOrderValue=" + "\"" + "\"" + "/>");breakline(bufferWriter);
					}
				}				
				bufferWriter.append("</OrderChannel>");breakline(bufferWriter);
			}
			bufferWriter.append("</MinimumOrderValueRules>");breakline(bufferWriter);
			

			bufferWriter.append("<LargeOrderRules>");breakline(bufferWriter);
			if( deliverySetDetails.getLargeOrderAllowed()!=null && deliverySetDetails.getLargeOrderRules().size() > 0) {
				for(LargeOrderRules largeOrderRules : deliverySetDetails.getLargeOrderRules()) {

					bufferWriter.append( "<OrderChannel name=" + "\"" + largeOrderRules.getOrderChannel() + "\"" +  ">");breakline(bufferWriter);
					bufferWriter.append( "<TotalBasedLORule id = "  + "\"" + largeOrderRules.getRuleId() + "\"" );
					if (largeOrderRules.getTenderType() != null) {
						bufferWriter.append( " cashlessPaymentType=" + "\"" + largeOrderRules.getTenderType() + "\"" + ">");breakline(bufferWriter);
					} else {
						bufferWriter.append( " cashlessPaymentType=" + "\"" + "\"" + ">");breakline(bufferWriter);
					}
					bufferWriter.append( "<OrderTotalMinimumThreshold>" + largeOrderRules.getTotMinThreshold() + "</OrderTotalMinimumThreshold>");breakline(bufferWriter);
					bufferWriter.append( "<IsLargeOrderMaximumThreshold>" + largeOrderRules.getIsMaxThreshold() + "</IsLargeOrderMaximumThreshold>");breakline(bufferWriter);
					bufferWriter.append( "<EstimatedDeliveryMinutes>" + largeOrderRules.getEstDeliveryLimit() + "</EstimatedDeliveryMinutes>");breakline(bufferWriter);
					bufferWriter.append( "<IsConfirmationNeeded>" + largeOrderRules.getIsConfirmNeed() + "</IsConfirmationNeeded>");breakline(bufferWriter);
					bufferWriter.append( "<IsAdvanceOrder>" + largeOrderRules.getIsAdvOrder() + "</IsAdvanceOrder>");breakline(bufferWriter);
					bufferWriter.append( "<IsMultiRiderAllowed>" + largeOrderRules.getMultiRiderAllowed() + "</IsMultiRiderAllowed>");breakline(bufferWriter);
					if(largeOrderRules.getMultiRiderAllowed().equals("true")) {
						bufferWriter.append( "<MinimumRiderCount>" + largeOrderRules.getMinRiderCount() + "</MinimumRiderCount>");breakline(bufferWriter);
					}else {
						bufferWriter.append( "<MinimumRiderCount>" + "1" + "</MinimumRiderCount>");breakline(bufferWriter);
					}
					bufferWriter.append( "<NotificationMessages>");breakline(bufferWriter);
					if(notificationlist!= null && notificationlist.size()>0) {
						for(Notification notification :notificationlist) {
							if(notification.getType()==2
									 && notification.getDlySetdtl()==largeOrderRules.getRuleId() 
									 && notification.getDlySetId()==deliverySetDetails.getDeliverySetId() ) {
								
								bufferWriter.append( "<Language code=" + "\"" + notification.getCode() + "\"" + " " + "name=" + "\"" + notification.getName() + "\"" + " " + "parent=" + "\"" + notification.getParent() + "\"" + ">");breakline(bufferWriter);
								bufferWriter.append( "<NotificationMessage>" + notification.getMessage() + "</NotificationMessage>");breakline(bufferWriter);
								bufferWriter.append("</Language>");breakline(bufferWriter);
							}
						}	
					}
					bufferWriter.append("</NotificationMessages>");breakline(bufferWriter);
					bufferWriter.append("</TotalBasedLORule>");
				}				
				bufferWriter.append("</OrderChannel>");breakline(bufferWriter);
			}
					
			if( deliverySetDetails.getPluLargeOrderRules()!=null && deliverySetDetails.getPluLargeOrderRules().size() > 0) {
				for(PluLargeOrderRules pluLargeOrderRules : deliverySetDetails.getPluLargeOrderRules()) {

					bufferWriter.append( "<OrderChannel name=" + "\"" + pluLargeOrderRules.getOrderChannel() + "\"" +  ">");breakline(bufferWriter);
					bufferWriter.append( "<ProductCodeBasedLORule id= "  + "\"" + pluLargeOrderRules.getRuleId() + "\"" );
					if (pluLargeOrderRules.getTenderType() != null) {
						bufferWriter.append( " cashlessPaymentType=" + "\"" + pluLargeOrderRules.getTenderType() + "\"" + ">");breakline(bufferWriter);
					} else {
						bufferWriter.append( " cashlessPaymentType=" + "\"" + "\"" + ">");breakline(bufferWriter);
					}
					bufferWriter.append( "<IsLargeOrderMaximumThreshold>" + pluLargeOrderRules.getIsMaxThreshold() + "</IsLargeOrderMaximumThreshold>");breakline(bufferWriter);
					bufferWriter.append( "<EstimatedDeliveryMinutes>" + pluLargeOrderRules.getEstDeliveryLimit() + "</EstimatedDeliveryMinutes>");breakline(bufferWriter);
					bufferWriter.append( "<IsConfirmationNeeded>" + pluLargeOrderRules.getIsConfirmNeed() + "</IsConfirmationNeeded>");breakline(bufferWriter);
					bufferWriter.append( "<IsAdvanceOrder>" + pluLargeOrderRules.getIsAdvOrder() + "</IsAdvanceOrder>");breakline(bufferWriter);
					bufferWriter.append( "<IsMultiRiderAllowed>" + pluLargeOrderRules.getMultiRiderAllowed() + "</IsMultiRiderAllowed>");breakline(bufferWriter);
					if(pluLargeOrderRules.getMultiRiderAllowed().equals("true")) {
						bufferWriter.append( "<MinimumRiderCount>" + pluLargeOrderRules.getMulRiderCount() + "</MinimumRiderCount>");breakline(bufferWriter);
					}else {
						bufferWriter.append( "<MinimumRiderCount>" + "1" + "</MinimumRiderCount>");breakline(bufferWriter);
					}
					bufferWriter.append( "<NotificationMessages>");breakline(bufferWriter);
					if(notificationlist!= null && notificationlist.size()>0) {
						for(Notification notification :notificationlist) {
							if(notification.getType()==3
									 && notification.getDlySetdtl()==pluLargeOrderRules.getRuleId() 
									 && notification.getDlySetId()==deliverySetDetails.getDeliverySetId() ) {
								
								bufferWriter.append( "<Language code=" + "\"" + notification.getCode() + "\"" + " " + "name=" + "\"" + notification.getName() + "\"" + " " + "parent=" + "\"" + notification.getParent() + "\"" + ">");breakline(bufferWriter);
								bufferWriter.append( "<NotificationMessage>" + notification.getMessage() + "</NotificationMessage>");breakline(bufferWriter);
								bufferWriter.append("</Language>");breakline(bufferWriter);
							}
						}	
					}
					bufferWriter.append("</NotificationMessages>");breakline(bufferWriter);
					if (pluLargeOrderRules.getQuantity() != null) {
						bufferWriter.append( "<Quantity>" + pluLargeOrderRules.getQuantity() + "</Quantity>");breakline(bufferWriter);
					} else {
						bufferWriter.append( "<Quantity>" + "</Quantity>");breakline(bufferWriter);
					}
					
					bufferWriter.append("</ProductCodeBasedLORule>");breakline(bufferWriter);
				}				
				bufferWriter.append("</OrderChannel>");breakline(bufferWriter);
				
			}
			bufferWriter.append("</LargeOrderRules>");breakline(bufferWriter);
			
			bufferWriter.append("<LargeOrderAllowed>" + deliverySetDetails.getLargeOrderAllowed() + "</LargeOrderAllowed>");breakline(bufferWriter);
			
			bufferWriter.append("</DeliveryDefinitions>");
		}
			
		//		HotBusinessLimits
		List<HotBusinessLimit> hotBusinessLimit = storeDBDAO.getHotBusinessLimit(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getDate(),packageGeneratorDTO.getMarketID());
	
		if(isHotSelected && null!=hotBusinessLimit && hotBusinessLimit.size()>0) {
			bufferWriter.append( "<HHOTBusinessLimits>");breakline(bufferWriter);
			for(HotBusinessLimit hotBusilimits : hotBusinessLimit ) {
				bufferWriter.append(getTag("ManagerModeTimeLimit",hotBusilimits.getManagerModeTimeLimit()));	
				bufferWriter.append(getTag("HighAmountSaleLimit",hotBusilimits.getHighAmountSaleLimit()));	
				bufferWriter.append(getTag("HighQuantitySaleLimit",hotBusilimits.getHighQuantitySaleLimit()));	
				bufferWriter.append(getTag("ReductionValidationMode",hotBusilimits.getReductionValidationMode()));	
				bufferWriter.append(getTag("TRedBeforeTotal",hotBusilimits.gettRedBeforeTotal()));	
				bufferWriter.append(getTag("TRedAfterTotalAmount",hotBusilimits.gettRedAfterTotalAmount()));	
				bufferWriter.append(getTag("TRedAfterTotalQuantity",hotBusilimits.gettRedAfterTotalQuantity()));	
				bufferWriter.append(getTag("PromoItemQuantityLimit",hotBusilimits.getPromoItemQuantityLimit()));
				bufferWriter.append(getTag("BypassPromoPerItemQuantityLimit",hotBusilimits.getBypassPromoPerItemQuantityLimit()));
				bufferWriter.append(getTag("BypassIndividualDiscountAmountLimit",hotBusilimits.getBypassIndividualDiscountAmountLimit()));
				bufferWriter.append(getTag("DiscountQuantityLimit",hotBusilimits.getDiscountQuantityLimit()));
				bufferWriter.append(getTag("CouponQuantityLimit",hotBusilimits.getCouponQuantityLimit()));
				bufferWriter.append(getTag("DiscountAmountLimit",hotBusilimits.getDiscountAmountLimit()));
				bufferWriter.append(getTag("NoTaxSalesAmountLimit",hotBusilimits.getNoTaxSalesAmountLimit()));
				bufferWriter.append(getTag("TRedAmountBeforeTotal",hotBusilimits.gettRedAmountBeforeTotal()));
				bufferWriter.append(getTag("CouponAmountLimit",hotBusilimits.getCouponAmountLimit()));
				bufferWriter.append(getTag("PromoItemAmountLimit",hotBusilimits.getPromoItemAmountLimit()));					

			}
			bufferWriter.append( "</HHOTBusinessLimits>");breakline(bufferWriter);
			LOGGER.info("StoreDBService::getHotBusinessLimit():: Copied to StoreDB XML"  );
		}
//		TaxDefinintion
		List<TaxDefinition> taxDefinition = storeDBDAO.getTaxDefinition(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getDate(),packageGeneratorDTO.getMarketID());
		if(null!=taxDefinition && taxDefinition.size()>0) {
			bufferWriter.append("<TaxDefinition>");breakline(bufferWriter);
			for(TaxDefinition taxdef : taxDefinition ) {
				bufferWriter.append(getTag("LegalName",taxdef.getLegalName()));	
				if(taxdef.getDefaultReceiptHeader()!=null && !taxdef.getDefaultReceiptHeader().isEmpty()) {bufferWriter.append("<DefaultReceiptHeader>"+taxdef.getDefaultReceiptHeader()+"</DefaultReceiptHeader>");	breakline(bufferWriter);	}
				if(taxdef.getDefaultReceiptFooter()!=null && !taxdef.getDefaultReceiptHeader().isEmpty()) {bufferWriter.append("<DefaultReceiptFooter>"+taxdef.getDefaultReceiptFooter()+"</DefaultReceiptFooter>");	breakline(bufferWriter);}
				if(taxdef.getWelcomeMessage()!=null && !taxdef.getWelcomeMessage().isEmpty()) {bufferWriter.append("<WelcomeMessage>"+taxdef.getWelcomeMessage()+"</WelcomeMessage>");	breakline(bufferWriter);}
				bufferWriter.append(getTag("MenuPriceBasis",taxdef.getMenuPriceBasis()));	
				bufferWriter.append(getTag("CalculationType",taxdef.getCalculationType()));	
				bufferWriter.append(getTag("DisplayTaxToCustomer",taxdef.getDisplayTaxToCustomer()));	
				bufferWriter.append(getTag("DisplayTaxOnSalePanel",taxdef.getDisplayTaxOnSalePanel()));	
				bufferWriter.append(getTag("DisplayTaxOnReceipt",taxdef.getDisplayTaxOnReceipt()));	
				if(null!=taxdef.getGrandTotalExclusions() && !taxdef.getGrandTotalExclusions().isEmpty() && taxdef.getGrandTotalExclusions().size()>0) {
					bufferWriter.append("<GrandTotalExclusions>");breakline(bufferWriter);	
					taxdef.getGrandTotalExclusions().stream().forEach(val -> {
						try {
							bufferWriter.append(getTag("GrandTotalOption",val));
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
					bufferWriter.append("</GrandTotalExclusions>");	breakline(bufferWriter);
				}				
			}
			bufferWriter.append( "</TaxDefinition>");breakline(bufferWriter);
		}
			//LocalizationSet
		List<Localization> localization = storeDBDAO.getFuncLocalizationSet(packageGeneratorDTO.getMarketID(), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getDate());
		if(null!=localization && localization.size()>0) {
			bufferWriter.append( "<Localization>");breakline(bufferWriter);
			for(Localization localizationsets : localization ) {
				bufferWriter.append(getTag("CountryId",localizationsets.getCountryId()));	
				bufferWriter.append(getTag("Language",localizationsets.getLanguage()));	
				bufferWriter.append(getTag("Variant",localizationsets.getVariant()));	
				bufferWriter.append(getTag("DateFormat",localizationsets.getDateFormat()));	
				bufferWriter.append(getTag("TimeFormat",localizationsets.getTimeFormat()));	
				bufferWriter.append(getTag("DecimalSeparator",localizationsets.getDecimalSeparator()));	
				bufferWriter.append(getTag("ThousandSeparator",localizationsets.getThousandSeparator()));	
				bufferWriter.append(getTag("CurrencyName",localizationsets.getCurrencyName()));
				bufferWriter.append(getTag("CurrencySymbol",localizationsets.getCurrencySymbol()));
				bufferWriter.append(getTag("CurrencyDecimals",localizationsets.getCurrencyDecimals()));
				bufferWriter.append(getTag("MinCirculatingAmount",localizationsets.getMinCirculatingAmt()));
				bufferWriter.append(getTag("MinLegalAmount",localizationsets.getMinLegalAmt()));
				bufferWriter.append(getTag("CSOGrillScreenLayout",localizationsets.getCsoGrillScreenLayout()));
				bufferWriter.append(getTag("PositiveCurrencyFormat",localizationsets.getPositiveCurrencyFormat()));
				bufferWriter.append(getTag("NegativeCurrencyFormat",localizationsets.getNegativeCurrencyFormat()));
				bufferWriter.append(getTag("OrderTotalRoundingRule",localizationsets.getOrderTotalRoundingRule()));
				bufferWriter.append(getTag("TotalDueRoundingRule",localizationsets.getTotalDueRoundingRule()));
				bufferWriter.append(getTag("DiscountRoundingRule",localizationsets.getDiscountRoundingRule()));
				bufferWriter.append(getTag("DefaultRoundingRule",localizationsets.getDefaultRoundingRule()));
				bufferWriter.append(getTag("RoundingDisplayMode",localizationsets.getRoundingDisplayMode()));
			}
			bufferWriter.append( "</Localization>");breakline(bufferWriter);
			LOGGER.info("StoreDBService::getFuncLocalizationSet():: Copied to StoreDB XML"  );
		}
		List<String> facilities = storeDBDAO.getFacilities(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), 
				Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getMarketID());
		if( facilities!=null &&  facilities.size() > 0) {
			bufferWriter.append("<StoreFacilities>");breakline(bufferWriter);
			for(String itm :  facilities) {
				bufferWriter.append( "<Facility>"+ itm + "</Facility>");breakline(bufferWriter);					
			}
			bufferWriter.append("</StoreFacilities>");breakline(bufferWriter);
		}
//		getStoreHours
		generateStoreHourXMLtagData(bufferWriter,packageGeneratorDTO.getMarketID(),Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()));
		bufferWriter.append( "</StoreProfile>");breakline(bufferWriter);
//			TaxTable
		TaxTable taxTableDetails = getTaxTableDetails(packageGeneratorDTO.getMarketID(),packageGeneratorDTO.getNodeID(),packageGeneratorDTO.getDate(),scriptManagementFlag);
		if((null!=taxTableDetails.getTaxType() && taxTableDetails.getTaxType().size()>0) || (null!=taxTableDetails.getTaxChain() && taxTableDetails.getTaxChain().size()>0)) {
			bufferWriter.append("<TaxTable>");breakline(bufferWriter);
			if(null!=taxTableDetails.getTaxType() && taxTableDetails.getTaxType().size()>0 && !taxTableDetails.getTaxType().isEmpty()) {
				for(TaxType val:taxTableDetails.getTaxType()) {
					bufferWriter.append( "<TaxType statusCode=\""+ val.getStatusCode().toString() +"\">" );breakline(bufferWriter);
					bufferWriter.append(getTag("TaxId",val.getTaxTypeCode()));
					bufferWriter.append(getTag("TaxDescription",val.getTaxDescription()));
					bufferWriter.append(getTag("TaxRate",val.getTaxRate()));
					bufferWriter.append(getTag("TaxBasis",val.getTaxBasis()));
					bufferWriter.append(getTag("TaxCalcType",val.getTaxCalcType()));
					bufferWriter.append(getTag("Rounding",val.getRounding()));
					bufferWriter.append(getTag("Precision",val.getPrecision()));
					bufferWriter.append(getTag("Rule",val.getRule()));
					if(null!=val.getTaxBreakDown() && !val.getTaxBreakDown().isEmpty() && val.getTaxBreakDown().size()>0) {
						for(Map<String, Object> data: val.getTaxBreakDown()) {
							bufferWriter.append("<TaxBreakDown>");breakline(bufferWriter);
							bufferWriter.append(getTag("Start",data.get("TAX_BRKD_BEG").toString()));
							bufferWriter.append(getTag("End",data.get("TAX_BRKD_END").toString()));
							bufferWriter.append(getTag("Amount",data.get("TAX_BRKD_AM").toString()));
							bufferWriter.append("</TaxBreakDown>");breakline(bufferWriter);
						}
					}		
					bufferWriter.append("</TaxType>");breakline(bufferWriter);
				}
			}
			if(null!=taxTableDetails.getTaxChain() && taxTableDetails.getTaxChain().size()>0 && !taxTableDetails.getTaxChain().isEmpty()) {
				for(TaxChain val:taxTableDetails.getTaxChain()) {
					bufferWriter.append( "<TaxChain statusCode=\""+ val.getStatusCode().toString() +"\">" );breakline(bufferWriter);
					bufferWriter.append(getTag("TaxChainId",val.getTaxChainId()));
					bufferWriter.append(getTag("Rule",val.getRule()));
					if(null!=val.getTaxTypeId() && null!=val.getTaxTypeId().getTaxId() && val.getTaxTypeId().getTaxId().size()>0 && !val.getTaxTypeId().getTaxId().isEmpty()) {
						for( String ids:val.getTaxTypeId().getTaxId()) {
							bufferWriter.append( "<TaxTypeId taxId=\""+ ids +"\"/>" );breakline(bufferWriter);
						}
					}
					bufferWriter.append("</TaxChain>");breakline(bufferWriter);
				}
			}
			bufferWriter.append("</TaxTable>");breakline(bufferWriter);
		}

//		TenderType
		List<TenderType> tenderType = getTenderType(packageGeneratorDTO.getMarketID(),Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()),packageGeneratorDTO.getDate());
		if(null!=tenderType && tenderType.size()>0 && !tenderType.isEmpty()) {
			bufferWriter.append("<TenderTypes>");breakline(bufferWriter);
			for(TenderType tenderVal:tenderType) {
				bufferWriter.append( "<TenderType statusCode=\""+ tenderVal.getStatusCode().toString() +"\">" );breakline(bufferWriter);
				bufferWriter.append(getTag("TenderId",tenderVal.getTenderId()));
				bufferWriter.append(getTag("TenderFiscalIndex",tenderVal.getTenderFiscalIndex()));
				bufferWriter.append(getTag("TenderName",tenderVal.getTenderName()));
				bufferWriter.append("<TenderChange"
						+ PackageGenerationUtility.getInstance().getAttrValuePair("id", tenderVal.getTenderChange().getTenderChangeID(), 'N')
						+ PackageGenerationUtility.getInstance().getAttrValuePair("type", tenderVal.getTenderChange().getTenderChangeType(),'N')
						+ PackageGenerationUtility.getInstance().getAttrValuePair("roundToMinAmount", tenderVal.getTenderChange().getTenderChangeRoundToMinAmount(), 'N')
						+ PackageGenerationUtility.getInstance().getAttrValuePair("maxAllowed",tenderVal.getTenderChange().getTenderChangeMaxAllowed(), 'N')
						+ PackageGenerationUtility.getInstance().getAttrValuePair("minCirculatingAmount", tenderVal.getTenderChange().getTenderChangeminCirculatingAmount(),'N')+ "/>");
				breakline(bufferWriter);
				bufferWriter.append(getTag("TenderCategory",tenderVal.getTenderCategory()));
				
				if(null!=tenderVal.getTenderFlags() && tenderVal.getTenderFlags().size()>0 && !tenderVal.getTenderFlags().isEmpty()) {
					bufferWriter.append("<TenderFlags>");breakline(bufferWriter);
					tenderVal.getTenderFlags().stream().forEach(val -> {
					try {
						bufferWriter.append(getTag("TenderFlag",val));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
					bufferWriter.append("</TenderFlags>");	breakline(bufferWriter);
				}
				bufferWriter.append(getTag("TaxOption",tenderVal.getTaxOption()));
				bufferWriter.append(getTag("SubtotalOption",tenderVal.getSubtotalOption()));
				bufferWriter.append(getTag("DefaultSkimLimit",tenderVal.getDefaultSkimLimit()));
				bufferWriter.append(getTag("DefaultHaloLimit",tenderVal.getDefaultHaloLimit()));
				bufferWriter.append(getTag("CurrencyDecimals",tenderVal.getCurrencyDecimals()));
				if(tenderVal.getElectronicPayment().getLegacyID() != null) {
					bufferWriter.append("<ElectronicPayment>");breakline(bufferWriter);
					bufferWriter.append(getTag("LegacyId",tenderVal.getElectronicPayment().getLegacyID()));
					bufferWriter.append("</ElectronicPayment>");breakline(bufferWriter);					
				} else if(tenderVal.getGiftCoupon().getLegacyID() != null) {
					bufferWriter.append("<GiftCoupon>");breakline(bufferWriter);
					bufferWriter.append(getTag("LegacyId",tenderVal.getGiftCoupon().getLegacyID()));
					if(tenderVal.getGiftCoupon().getAmt() != null) {
						bufferWriter.append(getTag("Amount",tenderVal.getGiftCoupon().getAmt()));	
					}
					bufferWriter.append("</GiftCoupon>");breakline(bufferWriter);
				} else if(tenderVal.getCreditSales().getLegacyID() != null) {
					bufferWriter.append("<CreditSales>");breakline(bufferWriter);
					bufferWriter.append(getTag("LegacyId",tenderVal.getCreditSales().getLegacyID()));
					bufferWriter.append("</CreditSales>");breakline(bufferWriter);
				}else if(tenderVal.getOtherPayment().getLegacyID() != null) {
					bufferWriter.append("<OtherPayment>");breakline(bufferWriter);
					bufferWriter.append(getTag("LegacyId",tenderVal.getOtherPayment().getLegacyID()));
					bufferWriter.append("</OtherPayment>");breakline(bufferWriter);
				}else if(tenderVal.getForeignCurrency().getLegacyID() != null) {
					bufferWriter.append("<ForeignCurrency>");breakline(bufferWriter);
					bufferWriter.append(getTag("LegacyId",tenderVal.getForeignCurrency().getLegacyID()));
					bufferWriter.append(getTag("ExchangeRate",tenderVal.getForeignCurrency().getExchangeRate()));
					bufferWriter.append(getTag("Orientation",tenderVal.getForeignCurrency().getOrientation()));
					bufferWriter.append(getTag("Precision",tenderVal.getForeignCurrency().getPrecision()));
					bufferWriter.append(getTag("Rounding",tenderVal.getForeignCurrency().getRounding()));
					bufferWriter.append(getTag("ExchangeMode",tenderVal.getForeignCurrency().getExchangeMode()));
					bufferWriter.append(getTag("Symbol",tenderVal.getForeignCurrency().getSymbol()));
					bufferWriter.append("</ForeignCurrency>");breakline(bufferWriter);
				}
				bufferWriter.append(getTag("TenderRoundingRule",tenderVal.getTenderRoundingRule()));
				bufferWriter.append(getTag("TenderTypeMinCirculatingAmount",tenderVal.getTenderTypeMinCirculatingAmount()));
				bufferWriter.append("</TenderType>");breakline(bufferWriter);
			}
			bufferWriter.append("</TenderTypes>");breakline(bufferWriter);
		}
		//DiscountTables
		List<DiscountTable> discountTableList = getDiscountTables(packageGeneratorDTO.getScheduleRequestID(), packageGeneratorDTO.getMarketID(),Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), 
				Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getDate());
		if(null!=discountTableList && !discountTableList.isEmpty() && discountTableList.size()>0) {
		bufferWriter.append("<DiscountTables>");breakline(bufferWriter);
		for(DiscountTable val:discountTableList) {
			bufferWriter.append("<DiscountTable>");breakline(bufferWriter);
			bufferWriter.append(getTag("DiscountId",val.getDiscountId().toString()));
			bufferWriter.append(getTag("DiscountDescription",val.getDiscountDescription()));
			bufferWriter.append(getTag("DiscountRate",val.getDiscountRate()));
			bufferWriter.append(getTag("TaxOption",val.getTaxOption()));
			bufferWriter.append(getTag("MEMC",val.getMemc()));
			bufferWriter.append(getTag("MEMCSaleType",val.getSalesTyp()));
			if(null!=val.getDiscountAllowed())bufferWriter.append(val.getDiscountAllowed());
			bufferWriter.append(getTag("AmountLimit",val.getDiscountAmount()));
			bufferWriter.append("</DiscountTable>");breakline(bufferWriter);
		}
		bufferWriter.append("</DiscountTables>");breakline(bufferWriter);
		}

//		StorePromotionDiscounts
		List<StorePromotionDiscounts> storePromotionDiscountsList = storeDBDAO.getStorePromotionDiscounts(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getDate(),packageGeneratorDTO.getMarketID());
		if( storePromotionDiscountsList!=null && storePromotionDiscountsList.size() > 0) {
			bufferWriter.append("<StorePromotionDiscounts>");breakline(bufferWriter);
			for(StorePromotionDiscounts storePromotionDiscounts : storePromotionDiscountsList) {
				bufferWriter.append("<Discount" 
									+ PackageGenerationUtility.getInstance().getAttrValuePair("name", storePromotionDiscounts.getName(), 'Y')
								    + PackageGenerationUtility.getInstance().getAttrValuePair("sequence", storePromotionDiscounts.getSequence().toString(), 'Y')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("status", storePromotionDiscounts.getStatus(), 'Y')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("initialDate", storePromotionDiscounts.getInitialDate(), 'Y')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("finalDate", storePromotionDiscounts.getFinalDate(), 'Y')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("initialOrderTotalValue", storePromotionDiscounts.getInitialOrdTotVal().toString(), 'Y')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("finalOrderTotalValue", storePromotionDiscounts.getFinalOrdTotVal().toString(), 'Y')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("discountID", "0", 'Y')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("discountType", storePromotionDiscounts.getDiscountType(), 'Y')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("discountValue", storePromotionDiscounts.getDiscountValue(), 'Y') + "/>");
				breakline(bufferWriter);
				
			}
			bufferWriter.append("</StorePromotionDiscounts>");breakline(bufferWriter);
		}
		
//		SizeSelections	
		List<SizeSelection> sizeSelectionList = getSizeSelectionData(packageGeneratorDTO.getMarketID());
		if( sizeSelectionList!=null && sizeSelectionList.size() > 0) {
			bufferWriter.append("<SizeSelections>");breakline(bufferWriter);
			for(SizeSelection sizeSelection : sizeSelectionList) {
				bufferWriter.append( "<SizeSelectionId>"+sizeSelection.getCode() + "</SizeSelectionId>");breakline(bufferWriter);
				bufferWriter.append( "<SizeSelectionName>"+sizeSelection.getDescrption() + "</SizeSelectionName>");breakline(bufferWriter);
			}
			bufferWriter.append("</SizeSelections>");breakline(bufferWriter);
		}
//		Production Details Start
		if(packageGeneratorDTO.getAllProducts()==null || packageGeneratorDTO.getAllProducts().size()==0) {
			packageGeneratorDTO.setAllProducts(screenService.getRestaurantProducts(packageGeneratorDTO	,null ));
		}
		Production production=getNameKVSVolumeProductionDetails(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), 
				Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getScheduleRequestID(),
				packageGeneratorDTO.getMarketID(),packageGeneratorDTO.getPackageStatusID(), packageGeneratorDTO.getDate(),packageGeneratorDTO.getAllProducts());
		if (production != null) {
			bufferWriter.append("<Production>");breakline(bufferWriter);
			if (production.getNameTables() != null && !production.getNameTables().isEmpty()) {
				bufferWriter.append("<NameTable>");breakline(bufferWriter);
				for (NameTable nametable : production.getNameTables()) {
					bufferWriter.append("<Queue"
							+ PackageGenerationUtility.getInstance().getAttrValuePair("id", nametable.getId(), 'N')
							+ PackageGenerationUtility.getInstance().getAttrValuePair("name", nametable.getName(),'N')
							+ PackageGenerationUtility.getInstance().getAttrValuePair("mirror",	nametable.getMirror(), 'N')
							+ PackageGenerationUtility.getInstance().getAttrValuePair("shortName",nametable.getShortName(), 'N')
							+ PackageGenerationUtility.getInstance().getAttrValuePair("type", nametable.getType(),'N')+ "/>");
					breakline(bufferWriter);
				}
				bufferWriter.append("</NameTable>");breakline(bufferWriter);
			}
			if (production.getPrdKvsRoutes() != null && !production.getPrdKvsRoutes().isEmpty()) {
				bufferWriter.append("<KVSRoutes>");breakline(bufferWriter);
				for (PrdKVSRoute prdKVSRoute : production.getPrdKvsRoutes()) {
					bufferWriter.append("<Route"
							+ PackageGenerationUtility.getInstance().getAttrValuePair("id", prdKVSRoute.getId(),'N')
							+ PackageGenerationUtility.getInstance().getAttrValuePair("path", prdKVSRoute.getPath(),'N')+"/>");
					breakline(bufferWriter);
				}
				bufferWriter.append("</KVSRoutes>");
				breakline(bufferWriter);
			}
			if (production.getVolumeTables() != null && !production.getVolumeTables().isEmpty()) {
				bufferWriter.append("<VolumeTable>");breakline(bufferWriter);
				for (VolumeTable volumeTable : production.getVolumeTables()) {
					bufferWriter.append("<Volume"
							+ PackageGenerationUtility.getInstance().getAttrValuePair("name", volumeTable.getName(),'N')
							+ PackageGenerationUtility.getInstance().getAttrValuePair("path", volumeTable.getPath(),'N')+">");
					breakline(bufferWriter);
					if (volumeTable.getRoutes() != null && !volumeTable.getRoutes().isEmpty()) {
						bufferWriter.append("<Routers>");breakline(bufferWriter);
						for (VolumeTable.Route route : volumeTable.getRoutes()) {
							bufferWriter.append("<Route"
									+ PackageGenerationUtility.getInstance().getAttrValuePair("id", route.getId(),'N')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("source",route.getSource(), 'N')
									+ PackageGenerationUtility.getInstance().getAttrValuePair("routes",route.getRoutes(), 'N')+"/>");
							breakline(bufferWriter);
						}
						bufferWriter.append("</Routers>");
						breakline(bufferWriter);
					}
					bufferWriter.append("</Volume>");
					breakline(bufferWriter);
				}
				bufferWriter.append("</VolumeTable>");breakline(bufferWriter);
			}
			
			LOGGER.info("StoreDBService::getNameKVSVolumeProductionDetails():: Copied to StoreDB XML"  );
			
			if (production.getKsGroups() != null && !production.getKsGroups().isEmpty()) {
				bufferWriter.append("<KSGroups>");breakline(bufferWriter);
				for (KSGroup ksgGroup : production.getKsGroups()) {
					bufferWriter.append("<Group"
							+ PackageGenerationUtility.getInstance().getAttrValuePair("id", ksgGroup.getId(),'N')
							+ PackageGenerationUtility.getInstance().getAttrValuePair("path", ksgGroup.getPath(),'N')+"/>");
					breakline(bufferWriter);
					
				}
				bufferWriter.append("</KSGroups>");breakline(bufferWriter);		
				LOGGER.info("StoreDBService::getKsGroups():: Copied to StoreDB XML"  );
			}
			
			if (production.getKitechenGroups() != null && !production.getKitechenGroups().isEmpty()) {
				bufferWriter.append("<KitchenGroups>");
				breakline(bufferWriter);
				for (KitchenGroup kitchenGroup : production.getKitechenGroups()) {
					bufferWriter.append("<KitchenGroup"+ PackageGenerationUtility.getInstance().getAttrValuePair("id", kitchenGroup.getId(),'N')
							+ PackageGenerationUtility.getInstance().getAttrValuePair("alias",kitchenGroup.getAlias(), 'N')+">");
					breakline(bufferWriter);
					for (KitchenGroup.Priority priority : kitchenGroup.getPriorities()) {
						bufferWriter.append("<Priority" + PackageGenerationUtility.getInstance().getAttrValuePair("id", priority.getId(), 'N') +">");
						breakline(bufferWriter);
						for (String queue : priority.getNames()) {
							bufferWriter.append("<ProductionQueue"+ PackageGenerationUtility.getInstance().getAttrValuePair("name", queue, 'N')+ "/>");
							breakline(bufferWriter);
						}
						bufferWriter.append("</Priority>");
						breakline(bufferWriter);
					}
					bufferWriter.append("</KitchenGroup>");breakline(bufferWriter);
				}
				bufferWriter.append("</KitchenGroups>");breakline(bufferWriter);
				LOGGER.info("StoreDBService::getKitechenGroups():: Copied to StoreDB XML"  );
			}
			if(production.getPpgGroups()!=null && !production.getPpgGroups().isEmpty()) {
				bufferWriter.append("<PPGs>");breakline(bufferWriter);
				for(PPGGroup group:production.getPpgGroups()) {
					bufferWriter.append("<PPG"+PackageGenerationUtility.getInstance().getAttrValuePair("id",group.getId(), 'N')
						+PackageGenerationUtility.getInstance().getAttrValuePair("image",group.getImage(),'N')
						+PackageGenerationUtility.getInstance().getAttrValuePair("routes",group.getRoutes(),'N')+"/>");breakline(bufferWriter);
				}
				bufferWriter.append("</PPGs>");breakline(bufferWriter);
			}
			bufferWriter.append("</Production>");breakline(bufferWriter);
		}
//		Production Details End
		
//		BunBufferDetails start
		List<BunBufferDetails> bunBufferDetailsList=getBunBufferData(packageGeneratorDTO.getNodeID(), packageGeneratorDTO.getMarketID());
		if(bunBufferDetailsList!=null && bunBufferDetailsList.size()>0) {
			bufferWriter.append( "<BunBuffer>");breakline(bufferWriter);
			for(BunBufferDetails bufferDetails:bunBufferDetailsList) {
				bufferWriter.append( "<Bun"+ PackageGenerationUtility.getInstance().getAttrValuePair("id", bufferDetails.getBunId().toString(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("name", bufferDetails.getBunName(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("displayPriority", bufferDetails.getDisplayPriority(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("quantityToBuffer", bufferDetails.getQuantityToBuffer(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("bufferTime", bufferDetails.getBufferTime(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("status", bufferDetails.getStatus(), 'N')+"/>");
				breakline(bufferWriter);
			}
			bufferWriter.append( "</BunBuffer>");breakline(bufferWriter);
			LOGGER.info("StoreDBService::getBunBufferDetails():: Copied to StoreDB XML"  );
		}
//		BunBufferDetails end
		
//		IngredientGroups Start			
		List<IngredientGroupDetails> ingredientDetailsList=getIngredientDetailsData(packageGeneratorDTO.getNodeID(), packageGeneratorDTO.getMarketID());
		if(ingredientDetailsList!=null && ingredientDetailsList.size()>0) {
			bufferWriter.append("<CytIngredientGroups>");breakline(bufferWriter);
			for(IngredientGroupDetails groupDetails:ingredientDetailsList) {
				bufferWriter.append("<Group"+PackageGenerationUtility.getInstance().getAttrValuePair("name", groupDetails.getName(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("mutuallyExclusive", groupDetails.getMutuallyExclusive(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("minQuantity",groupDetails.getMinQuantity(),'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("maxQuantity",groupDetails.getMaxQuantity(),'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("chargeThreshold",groupDetails.getChargeThreshold(),'N')+"/>");	
				breakline(bufferWriter);
			}
			bufferWriter.append("</CytIngredientGroups>");breakline(bufferWriter);
			LOGGER.info("StoreDBService::getIngredientGroupDetails():: Copied to StoreDB XML"  );
		}
//		IngredientGroups End
			
//		PromotionImages
		List<PromotionImages> promotionImagesList = getPromotionImagesData(packageGeneratorDTO.getMarketID());
		if( promotionImagesList!=null && promotionImagesList.size() > 0) {
			bufferWriter.append("<PromotionImages>");breakline(bufferWriter);
			bufferWriter.append("<PromotionImage name=\"BARCODE_REDEMPTION\">");breakline(bufferWriter);
			for(PromotionImages promotionImages : promotionImagesList) {
				
				bufferWriter.append( "<Image>"+promotionImages.getMediaFileName() + "</Image>");breakline(bufferWriter);breakline(bufferWriter);
			}
			bufferWriter.append("</PromotionImage>");breakline(bufferWriter);
			bufferWriter.append("</PromotionImages>");
		}
		
//		PromotionGroups
		List<PromotionGroupDetail> promotionGroupsDetails=getPromotionGroupDetailsData(packageGeneratorDTO.getMarketID());
		if (promotionGroupsDetails != null && promotionGroupsDetails.size() > 0) {
			bufferWriter.append("<PromotionGroups>");breakline(bufferWriter);
			for (PromotionGroupDetail promotionGroupDetail : promotionGroupsDetails) {
				bufferWriter.append("<PromotionGroup"+ PackageGenerationUtility.getInstance().getAttrValuePair("code",promotionGroupDetail.getPromoGrpCode().toString(), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("status",promotionGroupDetail.getStatus(), 'N')+ ">");breakline(bufferWriter);
				bufferWriter.append("<Name>"+promotionGroupDetail.getPromotionGroupName()+"</Name>");breakline(bufferWriter);
				bufferWriter.append("<Model>"+promotionGroupDetail.getPromoGrpModel()+"</Model>");breakline(bufferWriter);

				if (promotionGroupDetail.getTypes() != null && !promotionGroupDetail.getTypes().isEmpty()) {
					bufferWriter.append("<Types>");
					for (String type : promotionGroupDetail.getTypes()){
						bufferWriter.append("<Type>"+type+"</Type>");breakline(bufferWriter);
					}
					bufferWriter.append("</Types>");breakline(bufferWriter);
				}
				bufferWriter.append("</PromotionGroup>");breakline(bufferWriter);
			}
			bufferWriter.append("</PromotionGroups>");breakline(bufferWriter);
		}
		
		
//		ProductGroups
		List<ProductGroup> allProductGroups=getMIGroupData(packageGeneratorDTO.getMarketID());
		if (allProductGroups != null && allProductGroups.size() > 0) {
			bufferWriter.append("<ProductGroups>");breakline(bufferWriter);
			for (ProductGroup productGroup : allProductGroups){
				if (productGroup.getGrpType()!=3){
					bufferWriter.append("<ProductGroup"+ PackageGenerationUtility.getInstance().getAttrValuePair("code",productGroup.getGrpCode().toString(), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("status",productGroup.getStatus(), 'N')+ ">");
					bufferWriter.append("<Name>" + productGroup.getGrpName() + "</Name>");breakline(bufferWriter);
					bufferWriter.append("<Type>" + productGroup.getGrpTypName() + "</Type>");breakline(bufferWriter);
					if (productGroup.getPromoGrpCode()!=null && productGroup.getPromoGrpCode()>=0) {
						bufferWriter.append("<PromotionGroups>");breakline(bufferWriter);
						bufferWriter.append("<Code>" + productGroup.getPromoGrpCode() + "</Code>");
						bufferWriter.append("</PromotionGroups>");breakline(bufferWriter);
				  }
				  bufferWriter.append("</ProductGroup>");breakline(bufferWriter);
				}
			}
			bufferWriter.append("</ProductGroups>");breakline(bufferWriter);
		}
			
//		DayParts
		List<DayPartSet> dayPartSetList = storeDBDAO.getDayPartSet(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getDate(), packageGeneratorDTO.getNodeID());
		if( dayPartSetList!=null && dayPartSetList.size() > 0) {
			bufferWriter.append("<DayParts>");breakline(bufferWriter);
			for(DayPartSet dayPartSet : dayPartSetList) {
				bufferWriter.append("<DayPart" + PackageGenerationUtility.getInstance().getAttrValuePair("name", dayPartSet.getDayPartName(), 'N') +">");breakline(bufferWriter);
				bufferWriter.append("<DayPartMonday" + PackageGenerationUtility.getInstance().getAttrValuePair("start", dayPartSet.getMonStartTime(), 'N')  + PackageGenerationUtility.getInstance().getAttrValuePair("end", dayPartSet.getMonEndTime(), 'N') + "/>");breakline(bufferWriter);
				bufferWriter.append("<DayPartTuesday"  + PackageGenerationUtility.getInstance().getAttrValuePair("start", dayPartSet.getTueStartTime(), 'N')  + PackageGenerationUtility.getInstance().getAttrValuePair("end", dayPartSet.getTueEndTime(), 'N') + "/>");breakline(bufferWriter);
				bufferWriter.append("<DayPartWednesday"  + PackageGenerationUtility.getInstance().getAttrValuePair("start", dayPartSet.getWedStartTime(), 'N')  + PackageGenerationUtility.getInstance().getAttrValuePair("end", dayPartSet.getWedEndTime(), 'N') + "/>");breakline(bufferWriter);
				bufferWriter.append("<DayPartThursday"  + PackageGenerationUtility.getInstance().getAttrValuePair("start", dayPartSet.getThuStartTime(), 'N')  + PackageGenerationUtility.getInstance().getAttrValuePair("end", dayPartSet.getThuEndTime(), 'N') + "/>");breakline(bufferWriter);
				bufferWriter.append("<DayPartFriday"  + PackageGenerationUtility.getInstance().getAttrValuePair("start", dayPartSet.getFriStartTime(), 'N')  + PackageGenerationUtility.getInstance().getAttrValuePair("end", dayPartSet.getFriEndTime(), 'N') + "/>");breakline(bufferWriter);
				bufferWriter.append("<DayPartSaturday"  + PackageGenerationUtility.getInstance().getAttrValuePair("start", dayPartSet.getSatStartTime(), 'N')  + PackageGenerationUtility.getInstance().getAttrValuePair("end", dayPartSet.getSatEndTime(), 'N') + "/>");breakline(bufferWriter);
				bufferWriter.append("<DayPartSunday"  + PackageGenerationUtility.getInstance().getAttrValuePair("start", dayPartSet.getSunStartTime(), 'N')  + PackageGenerationUtility.getInstance().getAttrValuePair("end", dayPartSet.getSunEndTime(), 'N') + "/>");breakline(bufferWriter);
				bufferWriter.append("</DayPart>");breakline(bufferWriter);
			}
			bufferWriter.append("</DayParts>");breakline(bufferWriter);
		}
		
//		FeeTypes
		List<Fee> feeTypes=storeDBDAO.getFeeDetails(packageGeneratorDTO.getMarketID(), packageGeneratorDTO.getDate(), storeDBDAO.getFeeSetIds(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()),packageGeneratorDTO.getMarketID(), packageGeneratorDTO.getDate(), packageGeneratorDTO.getNodeID()).getPrenSetId(), storeDBDAO.getFeeSetIds(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()),packageGeneratorDTO.getMarketID(), packageGeneratorDTO.getDate(), packageGeneratorDTO.getNodeID()).getCusmSetId());
		String precisionValueForEating = layeringProductService.getPrecisionValue(packageGeneratorDTO.getMarketID(),ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_EATING);
		String precisionValueForTakeout = layeringProductService.getPrecisionValue(packageGeneratorDTO.getMarketID(),ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_TAKEOUT);
		String precisionValueForOther = layeringProductService.getPrecisionValue(packageGeneratorDTO.getMarketID(),ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_OTHER);
			
		Map<Long, Long> taxEntryMap =getTaxEntryMap(packageGeneratorDTO.getMarketID());
		if (feeTypes != null && feeTypes.size() > 0) {
			bufferWriter.append("<FeeTypes>");breakline(bufferWriter);
			for (Fee feeDetail : feeTypes) {
				bufferWriter.append("<FeeType"+ PackageGenerationUtility.getInstance().getAttrValuePair("feeCode",feeDetail.getFeeId().toString(), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("status",feeDetail.getStatus().toString(), 'N')+ ">");breakline(bufferWriter);
				bufferWriter.append("<FeeDescription>" + feeDetail.getFeeName() + "</FeeDescription>");breakline(bufferWriter);
				
				bufferWriter.append("<Pricing"+ PackageGenerationUtility.getInstance().getAttrValuePair("priceCode", "EATIN", 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("type", feeDetail.getFeeType(),'N')+ ">");breakline(bufferWriter);
				bufferWriter.append("<Value>"+ getNvlTrimPrecisionValue(feeDetail.getEatinVal().toString(), precisionValueForEating)+ "</Value>");breakline(bufferWriter);
				bufferWriter.append("<Tax"+ PackageGenerationUtility.getInstance().getAttrValuePair("taxCode",getTextTaxCode(feeDetail.getEatinTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("rule",getTextTaxRule(feeDetail.getEatinTaxRule(), feeDetail.getEatinTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("entry", getTaxEntry(feeDetail.getEatinTaxRule(),feeDetail.getEatinTaxChainEntry(), feeDetail.getEatinTaxEntry(),taxEntryMap),'N')+ "/>");breakline(bufferWriter);
				bufferWriter.append("</Pricing>");breakline(bufferWriter);

				bufferWriter.append("<Pricing"+ PackageGenerationUtility.getInstance().getAttrValuePair("priceCode", "TAKEOUT", 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("type", feeDetail.getFeeType(),'N')+ ">");
				bufferWriter.append("<Value>"+ getNvlTrimPrecisionValue(feeDetail.getTkutVal().toString(), precisionValueForTakeout)+ "</Value>");breakline(bufferWriter);
				bufferWriter.append("<Tax"+ PackageGenerationUtility.getInstance().getAttrValuePair("taxCode",getTextTaxCode(feeDetail.getTkutTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("rule",getTextTaxRule(feeDetail.getTkutTaxRule(), feeDetail.getTkutTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("entry", getTaxEntry(feeDetail.getTkutTaxRule(),feeDetail.getTakeOutTaxChainEntry(), feeDetail.getTkutTaxEntry(),taxEntryMap),'N')+ "/>");breakline(bufferWriter);
				bufferWriter.append("</Pricing>");breakline(bufferWriter);

				bufferWriter.append("<Pricing"+ PackageGenerationUtility.getInstance().getAttrValuePair("priceCode", "OTHER", 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("type", feeDetail.getFeeType(),'N')+ ">");
				bufferWriter.append("<Value>"+ getNvlTrimPrecisionValue(feeDetail.getOthVal().toString(), precisionValueForOther)+ "</Value>");breakline(bufferWriter);
				bufferWriter.append("<Tax"+ PackageGenerationUtility.getInstance().getAttrValuePair("taxCode",getTextTaxCode(feeDetail.getOthTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("rule",getTextTaxRule(feeDetail.getOthTaxRule(), feeDetail.getOthTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("entry", getTaxEntry(feeDetail.getOthTaxRule(),feeDetail.getOtherTaxChainEntry(), feeDetail.getOthTaxEntry(),taxEntryMap), 'N')+ "/>");breakline(bufferWriter);
				bufferWriter.append("</Pricing>");breakline(bufferWriter);

				bufferWriter.append("</FeeType>");breakline(bufferWriter);
			}
			bufferWriter.append("</FeeTypes>");breakline(bufferWriter);
		}
		
//		DepositTypes
		List<Deposit> depositTypes=storeDBDAO.getDepositDetails(packageGeneratorDTO.getMarketID(), packageGeneratorDTO.getDate(), storeDBDAO.getDepositSetIds(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getMarketID(), packageGeneratorDTO.getDate(), packageGeneratorDTO.getNodeID()).getPrenSetId(), storeDBDAO.getDepositSetIds(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getMarketID(), packageGeneratorDTO.getDate(), packageGeneratorDTO.getNodeID()).getCusmSetId());
		if (depositTypes != null && depositTypes.size() > 0) {
			bufferWriter.append("<DepositTypes>");breakline(bufferWriter);
			for (Deposit depositDetail : depositTypes) {
				bufferWriter.append("<DepositType"+ PackageGenerationUtility.getInstance().getAttrValuePair("depositCode",depositDetail.getDepositId().toString(), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("status",depositDetail.getStatus(), 'N')+ ">");breakline(bufferWriter);
				bufferWriter.append("<DepositDescription>" + depositDetail.getDepositName() + "</DepositDescription>");breakline(bufferWriter);
				
				bufferWriter.append("<Pricing"+ PackageGenerationUtility.getInstance().getAttrValuePair("priceCode", "EATIN", 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("type", depositDetail.getDepositType(),'N')+ ">");breakline(bufferWriter);
				if (depositDetail.getEatinValue() != null) {
					bufferWriter.append("<Value>"+ getNvlTrimPrecisionValue(depositDetail.getEatinValue().toString(), precisionValueForEating)+ "</Value>");breakline(bufferWriter);	
				}
				
				bufferWriter.append("<Tax"+ PackageGenerationUtility.getInstance().getAttrValuePair("taxCode",getTextTaxCode(depositDetail.getEatinTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("rule",getTextTaxRule(depositDetail.getEatinTaxRule(), depositDetail.getEatinTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("entry", getTaxEntry(depositDetail.getEatinTaxRule(),depositDetail.getEatinTaxChainEntry(), depositDetail.getEatinTaxTypeEntry(),taxEntryMap),'N')+ "/>");breakline(bufferWriter);
				bufferWriter.append("</Pricing>");breakline(bufferWriter);

				bufferWriter.append("<Pricing"+ PackageGenerationUtility.getInstance().getAttrValuePair("priceCode", "TAKEOUT", 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("type", depositDetail.getDepositType(),'N')+ ">");breakline(bufferWriter);
				if (depositDetail.getTakeOutValue() != null) {
					bufferWriter.append("<Value>"+ getNvlTrimPrecisionValue(depositDetail.getTakeOutValue().toString(), precisionValueForTakeout)+ "</Value>");breakline(bufferWriter);	
				}
				
				bufferWriter.append("<Tax"+ PackageGenerationUtility.getInstance().getAttrValuePair("taxCode",getTextTaxCode(depositDetail.getTakeOutTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("rule",getTextTaxRule(depositDetail.getTakeOutTaxRule(), depositDetail.getTakeOutTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("entry", getTaxEntry(depositDetail.getTakeOutTaxRule(),depositDetail.getTakeOutTaxChainEntry(), depositDetail.getTakeOutTaxTypeEntry(),taxEntryMap),'N')+ "/>");breakline(bufferWriter);
				bufferWriter.append("</Pricing>");breakline(bufferWriter);

				bufferWriter.append("<Pricing"+ PackageGenerationUtility.getInstance().getAttrValuePair("priceCode", "OTHER", 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("type", depositDetail.getDepositType(),'N')+ ">");breakline(bufferWriter);
				if (depositDetail.getOtherValue() != null) {
					bufferWriter.append("<Value>"+ getNvlTrimPrecisionValue(depositDetail.getOtherValue().toString(), precisionValueForOther)+ "</Value>");breakline(bufferWriter);	
				}
				
				bufferWriter.append("<Tax"+ PackageGenerationUtility.getInstance().getAttrValuePair("taxCode",getTextTaxCode(depositDetail.getOtherTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("rule",getTextTaxRule(depositDetail.getOtherTaxRule(), depositDetail.getOtherTaxCode()), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("entry", getTaxEntry(depositDetail.getOtherTaxRule(),depositDetail.getOtherTaxChainEntry(), depositDetail.getOtherTaxTypeEntry(),taxEntryMap), 'N')+ "/>");breakline(bufferWriter);
				bufferWriter.append("</Pricing>");breakline(bufferWriter);

				bufferWriter.append("</DepositType>");breakline(bufferWriter);
			}
			bufferWriter.append("</DepositTypes>");breakline(bufferWriter);
		}
		
//		FlavorTable
		List<FlavourSet> flavourSetList=storeDBDAO.getFlavourSet(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()),packageGeneratorDTO.getMarketID());
		if(flavourSetList!=null && flavourSetList.size() > 0) {
			bufferWriter.append("<FlavorTable>");breakline(bufferWriter);
			for(FlavourSet flavourSet : flavourSetList) {
				bufferWriter.append("<Flavor" + PackageGenerationUtility.getInstance().getAttrValuePair("code", flavourSet.getNozzleId().toString(), 'N')  + PackageGenerationUtility.getInstance().getAttrValuePair("name", flavourSet.getFlavname(), 'N') + "/>");breakline(bufferWriter);
			}
			bufferWriter.append("</FlavorTable>");breakline(bufferWriter);
		}
		
//		Categories
		String param_value=namesDBDAO.getParamValue("ENABLE_IMAGE_PER_LANGUAGE_CATEGORY",packageGeneratorDTO.getMarketID());
		Map<Long, List<LanguageDetails>> languageDetails=getLanguageData(packageGeneratorDTO.getMarketID());
		List<CategoryDetails> categoryDetails=storeDBDAO.getCategoryDetails(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()),packageGeneratorDTO.getDate(), packageGeneratorDTO.getMarketID(),languageDetails);
		if (categoryDetails != null && categoryDetails.size() > 0) {
			bufferWriter.append("<Categories>");breakline(bufferWriter);
			Map<Long,Long> sequenceMap=new HashMap<>();
			for (CategoryDetails categoryDetail : categoryDetails) {
				bufferWriter.append("<Category"+ PackageGenerationUtility.getInstance().getAttrValuePair("id",categoryDetail.getCode().toString(), 'N')+ 
						PackageGenerationUtility.getInstance().getAttrValuePair("parentCategoryId",categoryDetail.getParentCode().toString(), 'N'));
						if (categoryDetail.getParentCode()==0) {
							bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled",categoryDetail.getEnabled(), 'N'));
						}
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("sequence",getSequenceNumber(sequenceMap, categoryDetail.getParentCode()).toString(), 'N')+ 
						PackageGenerationUtility.getInstance().getAttrValuePair("categoryDescription",categoryDetail.getCategoryDescription(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("daypart",categoryDetail.getDayPart(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("categoryImage",categoryDetail.getCategoryImageName(), 'N')+
						PackageGenerationUtility.getInstance().getAttrValuePair("colorValue",categoryDetail.getColorValue(), 'N')
						+">");
						List<LanguageDetails> catlangDetails=categoryDetail.getLanguageDetails();
								if (catlangDetails != null && catlangDetails.size() > 0) {
									for (LanguageDetails langDetail : catlangDetails) 
									{	
										if (!"NA".equals(langDetail.getCat_lang_desc())) {
											bufferWriter.append("<Language"+ 
													PackageGenerationUtility.getInstance().getAttrValuePair("code",langDetail.getLang_code().concat("_").concat(langDetail.getCountry().toString().trim()), 'N')+ 
													PackageGenerationUtility.getInstance().getAttrValuePair("name",langDetail.getName(), 'N')+
													PackageGenerationUtility.getInstance().getAttrValuePair("parent",langDetail.getLang_code(), 'N')+
													PackageGenerationUtility.getInstance().getAttrValuePair("categoryDescription",langDetail.getCat_lang_desc(), 'N'));
													if (param_value!=null && "true".equals(param_value)) {
														if (!"NA".equals(langDetail.getImgName())) {
															bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("categoryImage",langDetail.getImgName(), 'N'));	
														}
													}								
													bufferWriter.append("/>");
										}
																
									}
								}
				bufferWriter.append("</Category>");breakline(bufferWriter);
			}
			
			bufferWriter.append("</Categories>");breakline(bufferWriter);
		}
			
			List<CategoryHours> categoryHoursList = storeDBDAO.getCategoryHours(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getMarketID());
			if( categoryHoursList!=null && categoryHoursList.size() > 0) {
				bufferWriter.append("<CategoryHours>");breakline(bufferWriter);
				for(CategoryHours categoryHours : categoryHoursList) {
					
					bufferWriter.append("<CategoryHour" + PackageGenerationUtility.getInstance().getAttrValuePair("categoryId", categoryHours.getCategoryCode(), 'Y') +">");
					bufferWriter.append( "<Weekday" + PackageGenerationUtility.getInstance().getAttrValuePair("name", WeekDays.Sunday.name(),'Y'));
					if (categoryHours.getSunStartTime() != null && categoryHours.getSunEndTime() != null) { 
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "true", 'Y') + PackageGenerationUtility.getInstance().getAttrValuePair("startTime", categoryHours.getSunStartTime(), 'Y')  + PackageGenerationUtility.getInstance().getAttrValuePair("endTime", categoryHours.getSunEndTime(), 'Y') + "/>");breakline(bufferWriter);
					}
					else {
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "false", 'Y') + "/>");breakline(bufferWriter);
					}
					bufferWriter.append( "<Weekday" + PackageGenerationUtility.getInstance().getAttrValuePair("name", WeekDays.Monday.name(),'Y'));
					if (categoryHours.getMonStartTime() != null && categoryHours.getMonEndTime() != null) { 
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "true", 'Y') + PackageGenerationUtility.getInstance().getAttrValuePair("startTime", categoryHours.getMonStartTime(), 'Y')  + PackageGenerationUtility.getInstance().getAttrValuePair("endTime", categoryHours.getMonEndTime(), 'Y') + "/>");breakline(bufferWriter);
					}
					else {
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "false", 'Y') + "/>");breakline(bufferWriter);
					}
					bufferWriter.append( "<Weekday" + PackageGenerationUtility.getInstance().getAttrValuePair("name", WeekDays.Tuesday.name(),'Y'));
					if (categoryHours.getTueStartTime() != null && categoryHours.getTueEndTime() != null) { 
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "true", 'Y') + PackageGenerationUtility.getInstance().getAttrValuePair("startTime", categoryHours.getTueStartTime(), 'Y')  + PackageGenerationUtility.getInstance().getAttrValuePair("endTime", categoryHours.getTueEndTime(), 'Y') + "/>");breakline(bufferWriter);
					}
					else {
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "false", 'Y') + "/>");breakline(bufferWriter);

					}
					bufferWriter.append( "<Weekday" + PackageGenerationUtility.getInstance().getAttrValuePair("name", WeekDays.Wednesday.name(),'Y'));
					if (categoryHours.getWedStartTime() != null && categoryHours.getWedEndTime() != null) { 
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "true", 'Y') + PackageGenerationUtility.getInstance().getAttrValuePair("startTime", categoryHours.getWedStartTime(), 'Y')  + PackageGenerationUtility.getInstance().getAttrValuePair("endTime", categoryHours.getWedEndTime(), 'Y') + "/>");breakline(bufferWriter);
					}
					else {
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "false" , 'Y') + "/>");breakline(bufferWriter);

					}
					bufferWriter.append( "<Weekday" + PackageGenerationUtility.getInstance().getAttrValuePair("name", WeekDays.Thursday.name(),'Y'));
					if (categoryHours.getThuStartTime() != null && categoryHours.getThuEndTime() != null) { 
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "true", 'Y') + PackageGenerationUtility.getInstance().getAttrValuePair("startTime", categoryHours.getThuStartTime(), 'Y')  + PackageGenerationUtility.getInstance().getAttrValuePair("endTime", categoryHours.getThuEndTime(), 'Y') + "/>");breakline(bufferWriter);
					}
					else {
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "false", 'Y') + "/>");breakline(bufferWriter);

					}
					bufferWriter.append( "<Weekday" + PackageGenerationUtility.getInstance().getAttrValuePair("name", WeekDays.Friday.name(),'Y'));
					if (categoryHours.getFriStartTime() != null && categoryHours.getFriEndTime() != null) { 
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "true", 'Y') + PackageGenerationUtility.getInstance().getAttrValuePair("startTime", categoryHours.getFriStartTime(), 'Y')  + PackageGenerationUtility.getInstance().getAttrValuePair("endTime", categoryHours.getFriEndTime(), 'Y') + "/>");breakline(bufferWriter);
					}
					else {
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "false", 'Y') + "/>");breakline(bufferWriter);

					}
					bufferWriter.append( "<Weekday" + PackageGenerationUtility.getInstance().getAttrValuePair("name", WeekDays.Saturday.name(),'Y'));
					if (categoryHours.getSatStartTime() != null && categoryHours.getSatEndTime() != null) { 
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "true", 'Y') + PackageGenerationUtility.getInstance().getAttrValuePair("startTime", categoryHours.getSatStartTime(), 'Y')  + PackageGenerationUtility.getInstance().getAttrValuePair("endTime", categoryHours.getSatEndTime(), 'Y') + "/>");breakline(bufferWriter);
					}
					else {
						
						bufferWriter.append(PackageGenerationUtility.getInstance().getAttrValuePair("enabled", "false", 'Y') + "/>");breakline(bufferWriter);

					}
					
					bufferWriter.append("</CategoryHour>");
				}
				
				bufferWriter.append("</CategoryHours>");breakline(bufferWriter);
			}
			
			List<PopulateDrinkVol> populateDrinkVolList=getPopulateDrinkVolData(packageGeneratorDTO.getMarketID());
			if(populateDrinkVolList!=null && populateDrinkVolList.size() > 0) {
				
				bufferWriter.append("<DrinkVolumeTable>");breakline(bufferWriter);
				for(PopulateDrinkVol populateDrinkVol : populateDrinkVolList) {
					
					bufferWriter.append("<DrinkVolume" + PackageGenerationUtility.getInstance().getAttrValuePair("code", populateDrinkVol.getCode(), 'N')  + PackageGenerationUtility.getInstance().getAttrValuePair("name", populateDrinkVol.getName(), 'N') + "/>");breakline(bufferWriter);
					
				}
				bufferWriter.append("</DrinkVolumeTable>");breakline(bufferWriter);
			}		
			//PaymentProvidersTag
			List<ProviderDetails> paymentProvidersDetails=storeDBDAO.getPaymentProvidersDetails(packageGeneratorDTO.getMarketID(), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getDate());
			if (paymentProvidersDetails != null && paymentProvidersDetails.size() > 0) {
				bufferWriter.append("<PaymentProviders>");breakline(bufferWriter);
				for (ProviderDetails providerDetails : paymentProvidersDetails) {
					bufferWriter.append("<PaymentProvider"+ PackageGenerationUtility.getInstance().getAttrValuePair("type",providerDetails.getSrceWd(), 'N')+ ">");
					breakline(bufferWriter);
					bufferWriter.append("<Parameter"+ PackageGenerationUtility.getInstance().getAttrValuePair("name","MerchantId", 'N')+
							PackageGenerationUtility.getInstance().getAttrValuePair("value",providerDetails.getProviderAlias(), 'N')
					+"/>");
					breakline(bufferWriter);
					if (providerDetails.getProviderTerminal()!=null && !providerDetails.getProviderTerminal().isEmpty()) {
						bufferWriter.append("<Parameter"+ PackageGenerationUtility.getInstance().getAttrValuePair("name","TerminalNumber", 'N')+
								PackageGenerationUtility.getInstance().getAttrValuePair("value",StringHelper.replaceSpecialCharacters(providerDetails.getProviderTerminal()), 'N')
						+"/>");breakline(bufferWriter);
					}
					if (providerDetails.getProviderMail()!=null && !providerDetails.getProviderMail().isEmpty()) {
						bufferWriter.append("<Parameter"+ PackageGenerationUtility.getInstance().getAttrValuePair("name","MerchantEmail", 'N')+
								PackageGenerationUtility.getInstance().getAttrValuePair("value",providerDetails.getProviderMail(), 'N')
						+"/>");breakline(bufferWriter);
					}
					bufferWriter.append("</PaymentProvider>");
				}
				bufferWriter.append("</PaymentProviders>");breakline(bufferWriter);
			}
			
			bufferWriter.append( "</StoreDB>");breakline(bufferWriter);
			
//			ColorDB
			List<ColorDb> colorDbList = storeDBDAO.getColorDb(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()), packageGeneratorDTO.getDate(),packageGeneratorDTO.getMarketID());
			if( colorDbList!=null && colorDbList.size() > 0) {
				bufferWriter.append("<ColorDB type=" + "\"" + "default" + "\"" + ">");breakline(bufferWriter);
				for(ColorDb colorDb : colorDbList) {
					bufferWriter.append( "<ColorName" + " name=" + "\"" + colorDb.getName() +  "\"" + " value=" +  "\"" + colorDb.getValue() +  "\"" + ">");breakline(bufferWriter);
					bufferWriter.append( "</ColorName>");breakline(bufferWriter);
				}
				bufferWriter.append("</ColorDB>");breakline(bufferWriter);
			}
			//Configurations
			Configurations configurationDetails  = getConfigurationDetails(packageGeneratorDTO.getMarketID(),Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()),packageGeneratorDTO.getDate());
			bufferWriter.append("<Configurations>");breakline(bufferWriter);
			List<Configuration>  configurationList = configurationDetails.getConfiguration();
			if(null!=configurationList && !configurationList.isEmpty() && configurationList.size()>0) {
				for(Configuration configVal:configurationList) {
					if(null!=configVal.getType())bufferWriter.append("<Configuration"+ PackageGenerationUtility.getInstance().getAttrValuePair("type",configVal.getType(), 'N')+">");breakline(bufferWriter);
					if(configVal.getSection().size()>0 && !configVal.getSection().isEmpty() && null!=configVal.getSection()) {
						for(Section sectionVal:configVal.getSection()) {
							if(null!=sectionVal.getName())bufferWriter.append("<Section"+ PackageGenerationUtility.getInstance().getAttrValuePair("name",sectionVal.getName(), 'N')+">");breakline(bufferWriter);
							if(sectionVal.getParameter().size()>0 && !sectionVal.getParameter().isEmpty() && null!=sectionVal.getParameter()) {
								for(Parameter paramVal:sectionVal.getParameter()) {
									bufferWriter.append("<Parameter"+ PackageGenerationUtility.getInstance().getAttrValuePair("name",paramVal.getName(), 'N')+
											PackageGenerationUtility.getInstance().getAttrValuePair("value",paramVal.getValue(), 'N',false)
									+"/>");breakline(bufferWriter);
								}
							}
							bufferWriter.append("</Section>");breakline(bufferWriter);
						}
					}
					bufferWriter.append("</Configuration>");breakline(bufferWriter);
				}
			}
			bufferWriter.append("</Configurations>");breakline(bufferWriter);
			
	        // Adaptors
			Adaptors adaptors = getAdaptor(packageGeneratorDTO.getMarketID(),Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()),Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()),packageGeneratorDTO.getDate());
			if (adaptors != null) {
				bufferWriter.append("<Adaptors>");breakline(bufferWriter);
				if (adaptors.getAdaptor() != null && !adaptors.getAdaptor().isEmpty()) {
					for (Adaptor adaptor : adaptors.getAdaptor()) {
						bufferWriter.append("<Adaptor"+ PackageGenerationUtility.getInstance().getAttrValuePair("type", adaptor.getType(), 'N')+ PackageGenerationUtility.getInstance().getAttrValuePair("startonload",adaptor.getStartonload(), 'N')+ ">");breakline(bufferWriter);
						if (adaptor.getSection() != null && !adaptor.getSection().isEmpty()) {
							for (Section section : adaptor.getSection()) {
								bufferWriter.append("<Section" + PackageGenerationUtility.getInstance()
										.getAttrValuePair("name", section.getName(), 'N') + ">");breakline(bufferWriter);
								if (section.getParameter() != null && !section.getParameter().isEmpty()) {
									for (Parameter parameter : section.getParameter()) {
										bufferWriter.append("<Parameter"
												+ PackageGenerationUtility.getInstance().getAttrValuePair("name",
														parameter.getName(), 'N')
												+ PackageGenerationUtility.getInstance().getAttrValuePair("value",
														parameter.getValue(), 'N')
												+ "/>");breakline(bufferWriter);
									}
									bufferWriter.append("</Section>");breakline(bufferWriter);
								}else if(section != null && section.getName() != null && (section.getParameter() == null || section.getParameter().isEmpty())) {
									bufferWriter.append("</Section>");breakline(bufferWriter);
								}
							}
							bufferWriter.append("</Adaptor>");breakline(bufferWriter);
						}else if(adaptor != null && adaptor.getType() != null) {
							bufferWriter.append("</Adaptor>");breakline(bufferWriter);	
						}
						
					}
				}
				bufferWriter.append("</Adaptors>");breakline(bufferWriter);
			}
			bufferWriter.append("</Document>");
			bufferWriter.close();
			LOGGER.info("End of StoreDB XML writing");
			genDto.setGenerated(true);
		
		return genDto;	
			}
	


	public List<BunBufferDetails> getBunBufferDetails(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getBunBufferDetails(storeDBRequest.getNodeId(), storeDBRequest.getMktId());
	}
	
	
	private Map<Long, Long> getTaxEntryMap(Long mktid) throws Exception{
	Map<Long, Long> taxEntryMap = new HashMap<>();
	List<Map<String, Object>> taxEntryList = layeringDBDAO.getTaxEntryList(mktid);
	for (final Map<String, Object> taxEntry : taxEntryList) {
		taxEntryMap.put(Long.parseLong(taxEntry.get("set_id").toString()),
				Long.parseLong(taxEntry.get("tax_typ_code").toString()));
	}
	return taxEntryMap;
	}

	private String getTaxEntry(long taxRule, long taxChainEntry, long taxEntry, Map<Long, Long> taxEntryMap) {
		String finalEntryValue = null;

		if (!taxEntryMap.isEmpty()) {
			for (Map.Entry<Long, Long> entry : taxEntryMap.entrySet()) {
				if (entry.getKey().equals(taxChainEntry)) {
					taxChainEntry = taxEntryMap.get(entry.getKey());
				} else {
					if (entry.getKey().equals(taxEntry)) {
						taxEntry = taxEntryMap.get(entry.getKey());
					}
				}
			}
		}
		if (taxRule == 9 && taxChainEntry > 0) {
			finalEntryValue = String.valueOf(taxChainEntry);
		} else {
			if (taxRule != 9 && taxEntry > 0) {
				finalEntryValue = String.valueOf(taxEntry);
			} else {
				finalEntryValue = "";
			}
		}
		return finalEntryValue;
	}

	private String getTextTaxRule(long taxRuleValue, long taxCodeValue) {
		String rule = null;
		if (taxCodeValue == 4 && taxRuleValue == 0) {
			rule = "FLAT";
		} else {
			switch ((int) taxRuleValue) {
			case 2:
				rule = "FLAT";
				break;
			case 3:
				rule = "EXCISE";
				break;
			case 4:
				rule = "GST";
				break;
			case 5:
				rule = "PST";
				break;
			case 6:
				rule = "VAT";
				break;
			case 7:
				rule = "BREAK_TABLE";
				break;
			case 8:
				rule = "FISCAL_PRINTER";
				break;
			case 9:
				rule = "TAX_CHAIN";
				break;
			}
		}
		return rule;
	}

	private String getNvlTrimPrecisionValue(String priceVal, String precisionValue) throws Exception {
		String finalPriceValue = null;
		if (priceVal.length() > 0 && !precisionValue.equals(null)) {
			DecimalFormat decimalFormat = new DecimalFormat(precisionValue);
			finalPriceValue = decimalFormat.format(Double.parseDouble(priceVal)).trim();
		}
		return finalPriceValue;
	}

	private String getTextTaxCode(long taxCode) {
		String code = null;
		switch ((int) taxCode) {
		case 1:
			code = "DEFAULT";
			break;
		case 2:
			code = "ALWAYS";
			break;
		case 3:
			code = "OPTIONAL";
			break;
		case 4:
			code = "NEVER";
			break;
		}
		return code;
	}

	public List<Fee> getFeeDetails(StoreDBRequest storeDBRequest) throws Exception {
		SetIds setIds = storeDBDAO.getFeeSetIds(storeDBRequest.getRestId(), storeDBRequest.getRestInstId(),storeDBRequest.getMktId(), storeDBRequest.getEffectiveDate(), storeDBRequest.getNodeId());
		List<Fee> feeDetails = storeDBDAO.getFeeDetails(storeDBRequest.getMktId(), storeDBRequest.getEffectiveDate(),setIds.getPrenSetId(), setIds.getCusmSetId());
		return feeDetails;
	}
		
	public List<StoreHours> getStoreHours(Long mktId,Long restId,Long restinstId) throws Exception {
		return storeDBDAO.getStoreHours(mktId, restId, restinstId);
	}

	public List<ProductGroup> getProductGroupsDetails(StoreDBRequest storeDBRequest) throws Exception {
		List<ProductGroup> allProductGroupDetail=storeDBDAO.getProductGroupsDetails(storeDBRequest.getMktId());
		return allProductGroupDetail;}

	public List<Deposit> getDepositDetails(StoreDBRequest storeDBRequest) throws Exception {
		SetIds setIds = storeDBDAO.getDepositSetIds(storeDBRequest.getRestId(), storeDBRequest.getRestInstId(),storeDBRequest.getMktId(), storeDBRequest.getEffectiveDate(), storeDBRequest.getNodeId());
		List<Deposit> depositDetails = storeDBDAO.getDepositDetails(storeDBRequest.getMktId(), storeDBRequest.getEffectiveDate(),setIds.getPrenSetId(), setIds.getCusmSetId());
		return depositDetails;
	}
	
	public void generateStoreHourXMLtagData(PackageWriter bufferWriter, Long mktId, Long restId, Long restinstId) throws Exception {
		List<StoreHours> storeHoursList= storeDBDAO.getStoreHours(mktId, restId, restinstId);
		if(storeHoursList!=null && !storeHoursList.isEmpty()) {
			bufferWriter.append("<StoreHours>");breakline(bufferWriter);
			for(StoreHours storeHours : storeHoursList) {
				if(storeHours.getRestpresentarea()!=null) {
					String salesTyp=null;
					if(storeHours.getPricecodeconv().equals("1"))
						salesTyp="saleType=\"Eatin";
					if(storeHours.getPricecodeconv().equals("2"))
						salesTyp="saleType=\"Takeout";
					bufferWriter.append( "<Hours" + ' ' + "areaName=" + "\"" + storeHours.getRestpresentarea() +"\"" +">");
					getWeekDayHrsData(bufferWriter,WeekDays.Sunday.name(),storeHours.getSunStartTime(),storeHours.getSunEndTime(),salesTyp);
					getWeekDayHrsData(bufferWriter,WeekDays.Monday.name(),storeHours.getMonStartTime(),storeHours.getMonEndTime(),salesTyp);
					getWeekDayHrsData(bufferWriter,WeekDays.Tuesday.name(),storeHours.getTueStartTime(),storeHours.getTueEndTime(),salesTyp);
					getWeekDayHrsData(bufferWriter,WeekDays.Wednesday.name(),storeHours.getWedStartTime(),storeHours.getWedEndTime(),salesTyp);
					getWeekDayHrsData(bufferWriter,WeekDays.Thursday.name(),storeHours.getThuStartTime(),storeHours.getThuEndTime(),salesTyp);
					getWeekDayHrsData(bufferWriter,WeekDays.Friday.name(),storeHours.getFriStartTime(),storeHours.getFriEndTime(),salesTyp);
					getWeekDayHrsData(bufferWriter,WeekDays.Saturday.name(),storeHours.getSatStartTime(),storeHours.getSatEndTime(),salesTyp);
					bufferWriter.append( "</Hours>");breakline(bufferWriter);breakline(bufferWriter);
				}
			}
			bufferWriter.append("</StoreHours>");breakline(bufferWriter);
		}
	}
	
	public void getWeekDayHrsData(PackageWriter bufferWriter,String day, String startTime,String endTime, String salesType) throws Exception{
		bufferWriter.append( "<Weekday" + ' ' + "name=" + "\"" + day +"\""+' ');
		if(startTime.equals("C")) {
		bufferWriter.append( "closed="+"\""+true+"\"");
		}
		if(startTime.equals("H")) {
			bufferWriter.append( "open24hrs="+"\""+true+"\"");
		}
		if(!startTime.equals("C") && !startTime.equals("H")) {
			bufferWriter.append( "openTime=" + "\"" + startTime +"\""+' ');
			bufferWriter.append( "closeTime=" + "\"" + endTime +"\"");
		}
		if(salesType!=null) {
			bufferWriter.append(' '+ salesType +"\""+"/>");
		} else {
			bufferWriter.append("/>");
		}
		breakline(bufferWriter);
	}
		
	
	public void getDayOfWeek(PackageWriter bufferWriter,String day, String fromTime,String toTime,String salesType) throws Exception{
		bufferWriter.append( "<DayOfWeek name=" + "\"" + day +"\""+' ');

		if(fromTime!=null && toTime!=null) {
		if(fromTime.equals("C")) {
		bufferWriter.append( "closed="+"\""+true+"\"");
		}
		if(fromTime.equals("H")) {
			bufferWriter.append( "open24hrs="+"\""+true+"\"");
		}
		if(!fromTime.equals("C") && !fromTime.equals("H")) {
			bufferWriter.append( "fromTime=" + "\"" + fromTime +"\""+' ');
			bufferWriter.append( "toTime=" + "\"" + toTime +"\"");
		}
		if(salesType!=null) {
			bufferWriter.append(' '+"\"" + salesType +"\""+"/>");
		}
		 else {
			bufferWriter.append("/>");
		}
		breakline(bufferWriter);
	}else {
		bufferWriter.append( "fromTime=" + "\"" + "\""+' ');
		bufferWriter.append( "toTime=" + "\"" + "\"");
		bufferWriter.append("/>");
		breakline(bufferWriter);
	}
		
	}
	
	private void breakline(PackageWriter bufferWriter) throws IOException {
		bufferWriter.append(System.lineSeparator());
	}
	
	public List<BusinessLimits> getBusinessLimit(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getBusinessLimit(storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate(),storeDBRequest.getMktId());
	}

	public List<IngredientGroupDetails> getIngredientGroupDetails(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getIngredientGroupDetails(storeDBRequest.getMktId(),storeDBRequest.getNodeId());
	}
	
	public List<HotBusinessLimit> getHotBusinessLimit(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getHotBusinessLimit(storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate(),
				storeDBRequest.getMktId());
	}
	
	private String getTag(final String tagName, final String customizedValue) {
		final StringBuffer tag = new StringBuffer("");
		if (customizedValue != null && !customizedValue.isEmpty()) {
			if (ObjectUtils.isFilled(customizedValue)) {
				tag.append("<" + tagName + GeneratorConstant.FINAL_TAG_SYMBOL);
				tag.append(ObjectUtils.replaceSpecialCharacters(customizedValue));
				tag.append("</" + tagName + GeneratorConstant.FINAL_TAG_SYMBOL);
				tag.append(System.lineSeparator());
			}
		}
		return tag.toString();
	}

	public List<PromotionGroupDetail> getPromotionGroupsDetails(StoreDBRequest storeDBRequest) throws Exception {
		List<PromotionGroupDetail> promotionGroupDetail = storeDBDAO.getPromotionGroupsDetails(storeDBRequest.getMktId());
		return promotionGroupDetail;
	}

	public List<TaxDefinition> getTaxDefinition(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getTaxDefinition(storeDBRequest.getRestId(), storeDBRequest.getRestInstId(),
				storeDBRequest.getEffectiveDate(), storeDBRequest.getMktId());
	}

	
	public Production getProductionDetails(StoreDBRequest storeDBRequest) throws Exception {
		return getNameKVSVolumeProductionDetails(storeDBRequest.getRestId(), storeDBRequest.getRestInstId(),
				storeDBRequest.getScheduleRequestID(), storeDBRequest.getMktId(),storeDBRequest.getPackageStatusID(),storeDBRequest.getEffectiveDate(),new HashMap<Long, ProductDetails>());
	}

	public Production getNameKVSVolumeProductionDetails(Long restId, Long restInstId, String scheduleRequestID,
			Long marketId,Long packageStatusId, String effectiveDate, Map<Long, ProductDetails> allProducts) throws Exception {
		Map<String, String> localizationSets = screenService.getLocalizationSets(restId,
				restInstId,marketId);
		
		String defaultMktLocale = screenService.getDefaultLocaleId(scheduleRequestID, marketId);
		
		
		Map<String, Long> rtngSetIds = storeDBDAO.getRoutingSetIdsforProduction(restId, restInstId, marketId);
		String addtlRtngFlg=getAllowExportAddtlRtngFlagData(marketId);

		Long parentSetId = null;
		Long childSetId = null;
		List<Long> rtngSetIdsList=new ArrayList<Long>();

		if (localizationSets.get("PREN_SET_ID") != null) {
			parentSetId = Long.parseLong(localizationSets.get("PREN_SET_ID").toString());
		}
		if (localizationSets.get("CUSM_SET_ID") != null) {
			childSetId = Long.parseLong(localizationSets.get("CUSM_SET_ID").toString());
		}
		
		if(rtngSetIds.containsKey("PROD_RTNG_SET") && rtngSetIds.get("PROD_RTNG_SET")!=null) {
			rtngSetIdsList.add(Long.valueOf(rtngSetIds.get("PROD_RTNG_SET").toString()));}
		if(rtngSetIds.containsKey("PSNTN_RTNG_SET") &&rtngSetIds.get("PSNTN_RTNG_SET")!=null) {
			rtngSetIdsList.add(Long.valueOf(rtngSetIds.get("PSNTN_RTNG_SET").toString()));}
		if(rtngSetIds.containsKey("RTM_PROD_RTNG_SET") && rtngSetIds.get("RTM_PROD_RTNG_SET")!=null) {
			rtngSetIdsList.add(Long.valueOf(rtngSetIds.get("RTM_PROD_RTNG_SET").toString()));}
		
		Long defaultRestLocale = storeDBDAO.getDefaultRestLocaleId(parentSetId, childSetId, marketId);
		LOGGER.info("localizationSets :"+localizationSets+" defaultMktLocale:"+defaultMktLocale+" defaultRestLocale:"+defaultRestLocale+" rtngSetIdsList:"+rtngSetIdsList);
		Production production= storeDBDAO.getNameKVSVolumeProductionDetails(defaultMktLocale, defaultRestLocale, rtngSetIdsList,
				marketId,addtlRtngFlg);
		production.setKsGroups( storeDBDAO.getProductionKSGroups(restId, restInstId, marketId, Long.valueOf(rtngSetIds.get("PSNTN_RTNG_SET").toString()), defaultMktLocale));
		production.setKitechenGroups( storeDBDAO.getProductionKitchenGroups(restId, restInstId, marketId, defaultMktLocale));
		production.setPpgGroups(storeDBDAO.getProductionPPGGroups(restId,restInstId,effectiveDate,marketId,packageStatusId,defaultRestLocale,defaultMktLocale,
				Long.valueOf(rtngSetIds.get("PROD_RTNG_SET").toString()),Long.valueOf(rtngSetIds.get("PSNTN_RTNG_SET").toString()),scheduleRequestID,rtngSetIdsList,allProducts));
		return production;
	}
	
	public List<Localization> getFuncLocalizationSet(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getFuncLocalizationSet(storeDBRequest.getMktId(), storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate());
	}

	public List<CategoryDetails> getCategoryDetails(StoreDBRequest storeDBRequest) throws Exception {
		Map<Long, List<LanguageDetails>> languageDetails=storeDBDAO.getLanguageDetails(storeDBRequest.getMktId());
		return storeDBDAO.getCategoryDetails(storeDBRequest.getRestId(), storeDBRequest.getRestInstId(),
				storeDBRequest.getEffectiveDate(), storeDBRequest.getMktId(),languageDetails);
		
	}

	public TaxTable getTaxTable(StoreDBRequest storeDBRequest) throws NumberFormatException, Exception {
		TaxTable taxTableDetails = new TaxTable();
		taxTableDetails = getTaxTableDetails(storeDBRequest.getMktId(),storeDBRequest.getNodeId(),storeDBRequest.getEffectiveDate(), storeDBRequest.getScriptManagementFlag());
		return taxTableDetails;
	}

	private List<TaxType> getTaxTypeDetails(Long marketID, List<Map<String, Object>> taxSearchSets, String scriptManagementFlag) throws Exception {	
		List<TaxType> listOfTaxType= storeDBDAO.getTaxType(marketID,taxSearchSets,scriptManagementFlag);			
		return listOfTaxType;
	}

	private List<TaxChain> getTaxChainDetails(Long mktId, Long nodeId, String effectiveDate, List<Map<String, Object>> taxMap, String scriptManagementFlag) throws Exception {	
		List<Object[]> taxTypeList = new ArrayList<>();
		List<Object[]> taxChainList = new ArrayList<>();
		if(null!=taxMap && !taxMap.isEmpty() && taxMap.size()>0) {
				for (int i = 0; i < taxMap.size(); i++) {
					if(Long.parseLong(taxMap.get(i).get("typ").toString())==4005L) {
					Object[] entry = { Long.parseLong(taxMap.get(i).get("set_id").toString()),Long.parseLong(taxMap.get(i).get("data_id").toString()), DateUtility.convertStringToDate(taxMap.get(i).get("STRT_DT").toString()) };
					taxTypeList.add(entry);
					}else {
						Object[] entry = { Long.parseLong(taxMap.get(i).get("set_id").toString()),Long.parseLong(taxMap.get(i).get("data_id").toString()), DateUtility.convertStringToDate(taxMap.get(i).get("STRT_DT").toString())};
						taxChainList.add(entry);
					}
				}	
		}	
		List<TaxChain> listOfTaxChain = storeDBDAO.getTaxChainValues(mktId,taxTypeList,taxChainList);	
		return listOfTaxChain;
	}
	private TaxTable getTaxTableDetails(Long marketID, Long nodeId, String date,
			String scriptManagementFlag) throws Exception {
		TaxTable taxTableDetails = new TaxTable();
		List<TaxType> taxTypeList = new ArrayList<>();
		List<TaxChain> taxChainList = new ArrayList<>();
		List<Map<String, Object>> taxSearchSets = storeDBDAO.getTaxSearchSets(marketID,nodeId,date);	
		taxTypeList = getTaxTypeDetails(marketID,taxSearchSets,scriptManagementFlag);
		taxChainList = getTaxChainDetails(marketID,nodeId,date,taxSearchSets,scriptManagementFlag);
		taxTableDetails.setTaxType(taxTypeList);
		taxTableDetails.setTaxChain(taxChainList);
		return taxTableDetails;
	}
	
	public TenderTypes getTenderTypes(StoreDBRequest storeDBRequest) throws Exception {
		TenderTypes tenderTypeDatas = new TenderTypes();
		List<TenderType> tenderTypeList = new ArrayList<>();
		tenderTypeList = getTenderType(storeDBRequest.getMktId(),storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate() );
		tenderTypeDatas.setTendertype(tenderTypeList);
		return tenderTypeDatas;
	}

	private List<TenderType> getTenderType(Long marketID, long restId, long restInstId, String effDate) throws Exception {	
		List<TenderType> tenderTypeDataList= storeDBDAO.getTenderType(marketID, restId,restInstId, effDate);			
		return tenderTypeDataList;
	}

	public List<ProviderDetails> getPaymentProvidersDetails(StoreDBRequest storeDBRequest) throws Exception {
		return storeDBDAO.getPaymentProvidersDetails(storeDBRequest.getMktId(),storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate());
	}
	public List<DiscountTable> getDisplayTable(StoreDBRequest storeDBRequest) throws Exception {
		List<DiscountTable> discountTableList = getDiscountTables(storeDBRequest.getScheduleRequestID(),storeDBRequest.getMktId(),storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate());
		return discountTableList;
	}
	private List<DiscountTable> getDiscountTables(String scheduleRequestID, Long mktId, Long restId, Long restInstId, String effectiveDate ) throws Exception {
		List<DiscountTable> discountTableList=new ArrayList<>();
		String defaultMktLocale = screenService.getDefaultLocaleId(scheduleRequestID, mktId);
		String discountBrkFlag= storeDBDAO.getDiscountBreakDnFlag(mktId);
		if(null!=discountBrkFlag&&discountBrkFlag.equalsIgnoreCase("true")) {
			discountTableList=storeDBDAO.getDiscountTableTrue(restId, restInstId,effectiveDate,
					mktId,defaultMktLocale);
		}else {
			discountTableList=storeDBDAO.getDiscountTableFalse(restId, restInstId,effectiveDate,
					mktId,defaultMktLocale);
		}
		return discountTableList;
	}
	
	
	public List<BunBufferDetails> getBunBufferData(Long nodeID,Long marketID) throws Exception{
		String keyName="BunBufferDetail";
		String bunBufferKey = keyName.concat(nodeID.toString()).concat("Mkt").concat(marketID.toString());
		List<BunBufferDetails> bunBufferDetails = cacheService.getBunBufferData(bunBufferKey);
		if(bunBufferDetails == null || (bunBufferDetails != null && bunBufferDetails.isEmpty())) {
			LOGGER.info("Cache miss key {}", bunBufferKey);
			bunBufferDetails = storeDBDAO.getBunBufferDetails(nodeID, marketID);
			cacheService.setBunBufferData(bunBufferKey, bunBufferDetails, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return bunBufferDetails;
	}

	public Map<Long, List<LanguageDetails>> getLanguageData(Long marketID) throws Exception{
		String keyName="LanguageDetails";
		String lanuguageKey = keyName.concat("Mkt").concat(marketID.toString());
		Map<Long, List<LanguageDetails>> languageDetails = cacheService.getLanguageData(lanuguageKey);
		if(languageDetails == null || (languageDetails != null && languageDetails.isEmpty())) {
			LOGGER.info("Cache miss key {}", lanuguageKey);
			languageDetails = storeDBDAO.getLanguageDetails(marketID);
			cacheService.setLanguageData(lanuguageKey, languageDetails, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return languageDetails;
	}
	
	public List<PromotionImages> getPromotionImagesData(Long marketID) throws Exception{
		String keyName="PromotionImages";
		String promoImgKey = keyName.concat("Mkt").concat(marketID.toString());
		List<PromotionImages> promoImgDetails = cacheService.getPromotionImagesData(promoImgKey);
		if(promoImgDetails == null || (promoImgDetails != null && promoImgDetails.isEmpty())) {
			LOGGER.info("Cache miss key {}", promoImgKey);
			promoImgDetails = storeDBDAO.getPromotionImages(marketID);
			cacheService.setPromotionImagesData(promoImgKey, promoImgDetails, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return promoImgDetails;
	}
	
	public String getAllowExportAddtlRtngFlagData(Long marketID) throws Exception{
		String keyName="AllowExportAddtlRtngFlag";
		String allowExportAddtlRtngFlagKey = keyName.concat("Mkt").concat(marketID.toString());
		String allowExportAddtlRtngFlag = cacheService.getAllowExportAddtlRtngFlagData(allowExportAddtlRtngFlagKey);
		if(allowExportAddtlRtngFlag == null) {
			LOGGER.info("Cache miss key {}", allowExportAddtlRtngFlagKey);
			allowExportAddtlRtngFlag = storeDBDAO.getAllowExportAddtlRtngFlag(marketID);
			cacheService.setAllowExportAddtlRtngFlagData(allowExportAddtlRtngFlagKey, allowExportAddtlRtngFlag, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return allowExportAddtlRtngFlag;
	}
	
	public List<ProductGroup> getMIGroupData(Long marketID) throws Exception{
		String keyName="MIGroupDetails";
		String miGroupDetailsKey = keyName.concat("Mkt").concat(marketID.toString());
		List<ProductGroup> miGroupDetails = cacheService.getMIGroupData(miGroupDetailsKey);
		if(miGroupDetails == null || (miGroupDetails != null && miGroupDetails.isEmpty())) {
			LOGGER.info("Cache miss key {}", miGroupDetailsKey);
			miGroupDetails = storeDBDAO.getProductGroupsDetails(marketID);
			cacheService.setMIGroupData(miGroupDetailsKey, miGroupDetails, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return miGroupDetails;
	}
	
	
		public List<IngredientGroupDetails> getIngredientDetailsData(Long nodeID,Long marketID) throws Exception{
		String keyName="IngriedientGroupDetails";
		String ingredientDetailKey = keyName.concat(nodeID.toString()).concat("Mkt").concat(marketID.toString());
		List<IngredientGroupDetails> ingredientDetails = cacheService.getIngredientDetailsData(ingredientDetailKey);
		if(ingredientDetails == null || (ingredientDetails != null && ingredientDetails.isEmpty())) {
			LOGGER.info("Cache miss key {}", ingredientDetailKey);
			ingredientDetails = storeDBDAO.getIngredientGroupDetails(marketID, nodeID);
			cacheService.setIngredientDetailsData(ingredientDetailKey, ingredientDetails, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return ingredientDetails;
	}
	
		public List<SizeSelection> getSizeSelectionData(Long marketID) throws Exception{
			String keyName="SizeSelection";
			String sizeSelectionKey = keyName.concat("Mkt").concat(marketID.toString());
			List<SizeSelection> sizeSelectionDetails = cacheService.getSizeSelectionData(sizeSelectionKey);
			if(sizeSelectionDetails == null || (sizeSelectionDetails != null && sizeSelectionDetails.isEmpty())) {
				LOGGER.info("Cache miss key {}", sizeSelectionKey);
				sizeSelectionDetails = storeDBDAO.getSizeSelection(marketID);
				cacheService.setSizeSelectionData(sizeSelectionKey, sizeSelectionDetails, Duration.ofMinutes(cacheTTLInMinutes));
			}
			return sizeSelectionDetails;
		}
		
		public List<PromotionGroupDetail> getPromotionGroupDetailsData(Long marketID) throws Exception{
			String keyName="PromotionGroupDetails";
			String promoGrpDtlKey = keyName.concat("Mkt").concat(marketID.toString());
			List<PromotionGroupDetail> promoGrpDetails = cacheService.getPromotionGroupDetailsData(promoGrpDtlKey);
			if(promoGrpDetails == null || (promoGrpDetails != null && promoGrpDetails.isEmpty())) {
				LOGGER.info("Cache miss key {}", promoGrpDtlKey);
				promoGrpDetails = storeDBDAO.getPromotionGroupsDetails(marketID);
				cacheService.setPromotionGroupDetailsData(promoGrpDtlKey, promoGrpDetails, Duration.ofMinutes(cacheTTLInMinutes));
			}
			return promoGrpDetails;
		}
		
		public List<PopulateDrinkVol> getPopulateDrinkVolData(Long marketID) throws Exception{
			String keyName="PopulateDrinkVol";
			String populateDrinkVolKey = keyName.concat("Mkt").concat(marketID.toString());
			List<PopulateDrinkVol> populateDrinkVol = cacheService.getPopulateDrinkVolData(populateDrinkVolKey);
			if(populateDrinkVol == null || (populateDrinkVol != null && populateDrinkVol.isEmpty())) {
				LOGGER.info("Cache miss key {}", populateDrinkVolKey);
				populateDrinkVol = storeDBDAO.getPopulateDrinkVol(marketID);
				cacheService.setPopulateDrinkVolData(populateDrinkVolKey, populateDrinkVol, Duration.ofMinutes(cacheTTLInMinutes));
			}
			return populateDrinkVol;
		}

		public Configurations getConfigurationDetails(StoreDBRequest storeDBRequest) throws Exception {
			Configurations configurationList = getConfigurationDetails(storeDBRequest.getMktId(),storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate());
			return configurationList;
		}

		private Configurations getConfigurationDetails(Long mktId, Long restId, Long restInstId, String effectiveDate) throws Exception {
			List<BusinessLimits> businessLimits = storeDBDAO.getBusinessLimit(restId,restInstId,effectiveDate, mktId);
			String intialFloatAmount = "" ;
			if(null!=businessLimits && businessLimits.size()>0 && !businessLimits.isEmpty()) {
			for(BusinessLimits val:businessLimits) {
				if(null!=val.getN_initialFloatAmount()) {
					 intialFloatAmount = String.format("%.0f", val.getN_initialFloatAmount()); 
				}
			}}
			String paramValue ="";
			try {
			 paramValue = productDBDAO.getValuesFromGlobalParam(mktId, GeneratorConstant.MARKET_INITIAL_FLOAT_AMOUNT);
			}catch (Exception e) {
			 paramValue = "N";
			}		
			List<Map<String, Object>> pkgConfigurationAdapt  = storeDBDAO.getPkgConfigurationAdapt(mktId);
			String countryCodeFlag = "";
			try {
			 countryCodeFlag = productDBDAO.getValuesFromGlobalParam(mktId, GeneratorConstant.ADD_COUNTRY_CODE_To_IMAGE_DB_SECTION);
			}catch(Exception e) {
			 countryCodeFlag = "false";
			}
			String countryId = storeDBDAO.getCountryId(restId,restInstId,effectiveDate,mktId);
			List<Map<String, Object>> pkgSection  = storeDBDAO.getPkgSection(mktId,countryCodeFlag,countryId);
			List<Map<String, Object>> pkgParameters  = storeDBDAO.getPkgParameters(mktId,restId,restInstId,effectiveDate,paramValue,countryCodeFlag,countryId,String.valueOf(intialFloatAmount));
			Configurations configurationList = getConfigurationList(pkgConfigurationAdapt,pkgSection,pkgParameters);
			return configurationList;
		}

		private Configurations getConfigurationList(List<Map<String, Object>> pkgConfigurationAdapt,
				List<Map<String, Object>> pkgSection, List<Map<String, Object>> pkgParameters) {
			Configurations  configurations = new Configurations();
			List<Configuration> configList = new ArrayList<>();
			if(!pkgConfigurationAdapt.isEmpty() && null!= pkgConfigurationAdapt && pkgConfigurationAdapt.size()>0) {
			pkgConfigurationAdapt.stream().forEach(a->{
				Configuration config = new Configuration();
				List<Section> sectionList = new ArrayList<>();
				if(null!=pkgSection && !pkgSection.isEmpty() && pkgSection.size()>0) {
				pkgSection.stream().forEach(b->{
					if(b.get("mkt_cnfg_typ_id").toString().equals(a.get("mkt_cnfg_typ_id").toString())) {
						Section section = new Section();
						List<Parameter> paramList = new ArrayList<>();
						if(null!= pkgParameters && !pkgParameters.isEmpty() && pkgParameters.size()>0) {
						pkgParameters.stream().forEach(c->{
							if(b.get("mkt_cnfg_sect_id").toString().equals(c.get("mkt_cnfg_sect_id").toString())) {
								Parameter parameter = new Parameter();
								if(null!=c.get("name"))parameter.setName(c.get("name").toString());
								if(null!=c.get("value"))parameter.setValue(c.get("value").toString());
								paramList.add(parameter);
							}		
						});
						}
						section.setName(b.get("CNFG_SECT_NA").toString());
						section.setParameter(paramList);
						sectionList.add(section);
					}
				});
				}
				config.setSection(sectionList);
				config.setType(a.get("XML_NEWPOS_TAG").toString());
				configList.add(config);
			});
			}
			configurations.setConfiguration(configList);
			return configurations;		
		}

		
		public Adaptors getAdaptors(StoreDBRequest storeDBRequest) throws Exception {
			Adaptors adaptorList = getAdaptor(storeDBRequest.getMktId(),storeDBRequest.getRestId(),storeDBRequest.getRestInstId(),storeDBRequest.getEffectiveDate());
			return adaptorList;
		}

		private Adaptors getAdaptor(Long mktId, Long restId, Long restInstId, String effectiveDate) throws Exception {
					
			List<Map<String, Object>> pkgAdapt  = storeDBDAO.getAdaptorDataList(mktId);
			List<Map<String, Object>> pkgSection  = storeDBDAO.getsectionDataList();
			List<Map<String, Object>> pkgParameters  = storeDBDAO.getParamsDataList(restId,restInstId,mktId,effectiveDate);
			Adaptors adaptorsList = getAdaptorsList(pkgAdapt,pkgSection,pkgParameters);
			return adaptorsList;
		}

	private Adaptors getAdaptorsList(List<Map<String, Object>> pkgAdapt, List<Map<String, Object>> pkgSection,
			List<Map<String, Object>> pkgParameters) {
		Adaptors adaptors = new Adaptors();
		List<Adaptor> adaptList = new ArrayList<>();
		if (!pkgAdapt.isEmpty() && null != pkgAdapt && pkgAdapt.size() > 0) {
			pkgAdapt.stream().forEach(a -> {
				Adaptor adaptor = new Adaptor();
				List<Section> sectionList = new ArrayList<>();
				if (null != pkgSection && !pkgSection.isEmpty() && pkgSection.size() > 0) {
					pkgSection.stream().forEach(b -> {
						if (b.get("MKT_ADPTR_TYP_ID").toString().equals(a.get("MKT_ADPTR_TYP_ID").toString())) {
							Section section = new Section();
							List<Parameter> paramList = new ArrayList<>();
							if (null != pkgParameters && !pkgParameters.isEmpty() && pkgParameters.size() > 0) {
								pkgParameters.stream().forEach(c -> {
									if (b.get("MKT_ADPTR_SECT_ID").toString()
											.equals(c.get("MKT_ADPTR_SECT_ID").toString())) {
										Parameter parameter = new Parameter();
										if (null != c.get("name"))
											parameter.setName(c.get("name").toString());
										if (null != c.get("value"))
											parameter.setValue(c.get("value").toString());
										paramList.add(parameter);
									}
								});
							}
							section.setName(b.get("ADPTR_SECT_NA").toString());
							section.setParameter(paramList);
							sectionList.add(section);
						}
					});
				}
				adaptor.setSection(sectionList);
				adaptor.setType(a.get("XML_NEWPOS_TAG").toString());
				adaptor.setStartonload(a.get("STRT_ON_LOAD").toString());
				adaptList.add(adaptor);
			});
		}
		adaptors.setAdaptor(adaptList);
		return adaptors;
	}
	
	private Long getSequenceNumber(Map<Long,Long> sequenceMap, Long catCode) {
		Long seq=sequenceMap.get(catCode);
		if(seq==null){
			seq=1L;
			sequenceMap.put(catCode, seq);
		}else {
		seq=seq+1;
		sequenceMap.put(catCode, seq);
		}
		return seq;
	}
	
}