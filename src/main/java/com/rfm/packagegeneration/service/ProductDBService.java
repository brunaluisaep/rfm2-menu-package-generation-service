package com.rfm.packagegeneration.service;

import static com.rfm.packagegeneration.constants.GeneratorConstant.FINAL_TAG_SYMBOL;
import static com.rfm.packagegeneration.constants.GeneratorConstant.PRODUCTS_DB_XML_FILENAME;
import static com.rfm.packagegeneration.constants.GeneratorConstant.PRODUCT_DB_XML;
import static com.rfm.packagegeneration.constants.GeneratorConstant.SCHEMA_TYPE_NAMES;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rfm.packagegeneration.cache.CacheService;
import com.rfm.packagegeneration.cache.KeyNameHelper;
import com.rfm.packagegeneration.constants.GeneratorConstant;
import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.dao.LayeringProductDBDAO;
import com.rfm.packagegeneration.dao.NamesDBDAO;
import com.rfm.packagegeneration.dao.ProductDBDAO;
import com.rfm.packagegeneration.dto.AssociatedPromoProducts;
import com.rfm.packagegeneration.dto.Category;
import com.rfm.packagegeneration.dto.Code;
import com.rfm.packagegeneration.dto.Component;
import com.rfm.packagegeneration.dto.GenericEntry;
import com.rfm.packagegeneration.dto.Item;
import com.rfm.packagegeneration.dto.LogStatus;
import com.rfm.packagegeneration.dto.PPG;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;
import com.rfm.packagegeneration.dto.Parameter;
import com.rfm.packagegeneration.dto.PriceTag;
import com.rfm.packagegeneration.dto.Pricing;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductDBRequest;
import com.rfm.packagegeneration.dto.ProductDetails;
import com.rfm.packagegeneration.dto.ProductList;
import com.rfm.packagegeneration.dto.ProductPresentation;
import com.rfm.packagegeneration.dto.ProductShortCutSettings;
import com.rfm.packagegeneration.dto.ProductTags;
import com.rfm.packagegeneration.dto.PromotionGroup;
import com.rfm.packagegeneration.dto.Reduction;
import com.rfm.packagegeneration.dto.RequestDTO;
import com.rfm.packagegeneration.dto.ResponseMicroServiceDTO;
import com.rfm.packagegeneration.dto.Route;
import com.rfm.packagegeneration.dto.ShortCutDetails;
import com.rfm.packagegeneration.dto.Size;
import com.rfm.packagegeneration.dto.SmartRoutingTask;
import com.rfm.packagegeneration.dto.Tax;
import com.rfm.packagegeneration.dto.TimeFrames;
import com.rfm.packagegeneration.dto.WeekDays;
import com.rfm.packagegeneration.utility.MenuItemComponentsComparator;
import com.rfm.packagegeneration.utility.ObjectUtils;
import com.rfm.packagegeneration.utility.PackageWriter;
import com.rfm.packagegeneration.utility.Pair;

@Service
public class ProductDBService {
	@Autowired
	ProductDBDAO productDBDAO;

	@Autowired
	LayeringProductService layeringProductService;

	@Autowired
	NamesDBDAO namesDBDAO;
	@Autowired
	LogDetailsService logDetailsService;
	
	@Autowired
	CacheService cacheService;
	
	@Autowired
	LayeringProductDBDAO layeringDBDAO;

	
	@Value("${application.redis.cacheTTL}")
	private Long cacheTTLInMinutes;
	
	private static final Logger LOGGER = LogManager.getLogger("ProductDBService"); 
	
	/*
	 * This method returns the collection of product for Dimension Group
	 * 
	 * @param productDBRequest
	 * 
	 * @return products
	 */


	public ResponseMicroServiceDTO generateFileString(PackageGeneratorDTO packageGeneratorDTO) throws Exception {
		Map<Long, Product> allProducts = null;
		Map<Long, ProductDetails> allProductsClone = new HashMap<>();
		ResponseMicroServiceDTO genDto=new ResponseMicroServiceDTO();
		if (packageGeneratorDTO.getRestaurant() == null) {
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setMarketId(packageGeneratorDTO.getMarketID());
			requestDTO.setEffectiveDate(packageGeneratorDTO.getDate());
			requestDTO.setNodeId(packageGeneratorDTO.getNodeID());
			packageGeneratorDTO.setRestSetIds(namesDBDAO.retrieveRestSetId(packageGeneratorDTO.getNodeID(), packageGeneratorDTO.getMarketID()));
			packageGeneratorDTO.setRestaurant(layeringProductService.getRestaurantSets(requestDTO));
			packageGeneratorDTO.setMasterSetId(namesDBDAO.retrieveMasterSetId(packageGeneratorDTO.getMarketID()));
		}

		PackageWriter bufferWriter = new PackageWriter( PRODUCTS_DB_XML_FILENAME, PRODUCT_DB_XML, packageGeneratorDTO, SCHEMA_TYPE_NAMES );
		
		bufferWriter.append( "<ProductDb version=\"1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " );
		bufferWriter.append( " xsi:noNamespaceSchemaLocation=\"/RFM2/RFM2PackageConf/PackageXSD/2.1/product-db.xsd\" > " );
		String sequenceNumber = productDBDAO.getValuesFromGlobalParam(packageGeneratorDTO.getMarketID(), ProductDBConstant.CONSTANT_ENABLE_SEAMLESS );
		if(sequenceNumber.equals("true")) {
		if ( packageGeneratorDTO.isGeneratedSeqNum() && packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() != null && !packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO().isEmpty() ) {
			bufferWriter.append( "<ProductDBSeqNumber>" + packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() + "</ProductDBSeqNumber>" );
			}
		}
		
		final List<Map<String, Object>> allDrinkVolList = productDBDAO.getDrinkVolList(packageGeneratorDTO.getMarketID());

		Map<String, Object> allDrinkVolListMap = new HashMap<>();
		for (final Map<String, Object> drinkVolData : allDrinkVolList) {
			allDrinkVolListMap.put(String.valueOf(drinkVolData.get("drnk_vol_id")),
					(drinkVolData.get("drnk_vol_code")));
		}
		final String defualtChoiceValues = productDBDAO.getValuesFromGlobalParam(packageGeneratorDTO.getMarketID(),ProductDBConstant.TURN_ON_DEFAULT_CHOICE_TO_THE_CHANNELS);
		final List<Map<String, Object>> routingSetsForPresentationAndProductIon = layeringDBDAO.getRoutingSets(
				packageGeneratorDTO.getMarketID(), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()));
		layeringProductService.setExcludeInactiveMI(productDBDAO.getValuesFromGlobalParam(packageGeneratorDTO.getMarketID(), ProductDBConstant.EXCLUDE_INACTIVE_MENU_ITEMS));
		layeringProductService.setDefaultMenuItemStatus(productDBDAO.getValuesFromGlobalParam(packageGeneratorDTO.getMarketID(), ProductDBConstant.MENU_ITEM_DEFAULT_MENU_ITEM_STATUS));
		allProducts = layeringProductService.getMergedProductsByRest(packageGeneratorDTO);
		boolean kvsflagformarketDetails = getkvsflagformarketDetails(packageGeneratorDTO.getMarketID());
		List<LogStatus>logSatusList=logDetailsService.getLogDetailsProductDB(allProducts, packageGeneratorDTO);
		genDto.setLogStatusList(logSatusList);
		Map<Long, String> allColorsMap = new HashMap<>();
		
		allProducts = layeringProductService.removeInactiveMIDetails(allProducts, layeringProductService.getExcludeInactiveMI().toUpperCase().equalsIgnoreCase("YES"), packageGeneratorDTO.getMarketID());
		final List<Map<String, Object>> allColorsList = getProductColorData(packageGeneratorDTO.getScheduleRequestID(), packageGeneratorDTO.getMarketID());
		for (final Map<String, Object> colorsProductData : allColorsList) {
			allColorsMap.put((Long.parseLong(colorsProductData.get("colr_id").toString())),
					(colorsProductData.get("colr_na").toString()));
		}
		Map<Long, String> allMediaMap = new HashMap<>();
		final List<Map<String, Object>> allMediaList = getProductMediaData(packageGeneratorDTO.getScheduleRequestID(), packageGeneratorDTO.getMarketID());
		for (final Map<String, Object> mediaProductData : allMediaList) {
			allMediaMap.put((Long.parseLong(mediaProductData.get("lgl_id").toString())),
					(mediaProductData.get("mdia_file_na").toString()));
		}
		Map<Long, Long> productCodeIdRelationMap= new HashMap<>();
		for (Map.Entry<Long, Product> entry : allProducts.entrySet()) 
		{
			productCodeIdRelationMap.put(entry.getValue().getProductCode(), entry.getKey());
			
			
			// all product clone is needed for Names
			ProductDetails productDetails=getpresentationDetails(entry.getValue(), allColorsMap, allMediaMap);
			
			if(layeringProductService.getExcludeInactiveMI().toUpperCase().equalsIgnoreCase("YES")) {
				boolean active = (entry.getValue().getActive()==1||(entry.getValue().getActive()==0&&null!=entry.getValue().getAuxiliaryMenuItem()&&entry.getValue().getAuxiliaryMenuItem().equals(1L)));
				if(active) {
					//only add if active
					allProductsClone.put( entry.getKey(), productDetails);
				}
			
			} else {
				// don't exclude based on status
				allProductsClone.put( entry.getKey(), productDetails);
			}
			
		}
		
		layeringProductService.bzirule(allProducts, packageGeneratorDTO);
		layeringProductService.defaultProductbziRule(allProducts);
		if(LOGGER.isInfoEnabled()) LOGGER.info("start write Product DB XML");
		
		Long CSOHasLimitedTimeDiscount = getLimitedTimeDiscountVal(packageGeneratorDTO.getDate(), packageGeneratorDTO.getNodeID());
		String mktName=productDBDAO.getMarketName(packageGeneratorDTO.getMarketID());
		final PackageXMLParametersDTO[] params = packageGeneratorDTO.getPackageXmlParameter();
		final boolean excludeInactiveMI = layeringProductService.getExcludeInactiveMI().toUpperCase().equalsIgnoreCase("YES");

		Map<String, List<Long>> mapDiscountNotAllowed = discountNotAllowed(packageGeneratorDTO.getMarketID(), Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_ID).toString()), 
			Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get(GeneratorConstant.KEY_RESTAURANT_INST_ID).toString()));
		Map<Long, String> mapGrillGroups = productDBDAO.getGrillGroupMap();

		for(Long key : allProducts.keySet() ) 
		{
			
		
				Product dto  = allProducts.get(key);
				if(excludeInactiveMI && dto.getActive()==0) continue;
				
				if(!(dto.getProductClass().equals("CHOICE") && ( dto.getComponents()==null || dto.getComponents().size() <=0))) {
						
				bufferWriter.append( "<Product " );				
				
				if (dto.getActive()==1) {
					bufferWriter.append("statusCode=\"ACTIVE\"");					
				} else {
					bufferWriter.append("statusCode=\"INACTIVE\"");
				}
				if(dto.getProductClass() != null) bufferWriter.append(" productClass=\""+  dto.getProductClass() +"\"");	
//				if(dto.getProductCCMSettings()!=null && dto.getProductCCMSettings().getAutoBundlingPriority() != null) bufferWriter.append(" autoBundlingPriority=\""+  dto.getProductCCMSettings().getAutoBundlingPriority() +"\"");				
				if(dto.getProductCategory() != null) bufferWriter.append(" productCategory=\""+  dto.getProductCategory() +"\"");
				if (dto.getProductPosKvs() != null 
						&& dto.getProductPosKvs().getSalable() == null) {
					dto.getProductPosKvs().setSalable("false");
				}
				if(dto.getProductPosKvs() != null) bufferWriter.append(" salable=\""+  dto.getProductPosKvs().getSalable() +"\"");
				
				dto.getProductPosKvs().setModified(false);
				if (dto.getProductPosKvs() != null) {
					bufferWriter.append(" modified=\"" + dto.getProductPosKvs().isModified() + "\"");
				}
								
				Long grillGroup = 0L;
				if (dto.getProductPosKvs() != null) {
					grillGroup = dto.getProductPosKvs().getGrillGroup();
				}
				if(grillGroup != null && grillGroup != 0L && params != null ) {
					String grillGroupName =  mapGrillGroups.get(grillGroup);
					for ( final PackageXMLParametersDTO param : params ) {
							if ( param != null && ("restercindicator".equalsIgnoreCase( param.getParm_na() )) && "ON".equalsIgnoreCase( param.getParm_val() ) ) {
								bufferWriter.append(" grillGroup=\""+ grillGroupName +"\"");
							}
	
					}
				}			
				
				bufferWriter.append("><ProductCode>"+ dto.getProductCode() +"</ProductCode>");breakline(bufferWriter);
				if (dto.getProductPosKvs() != null && dto.getProductPosKvs().getMenuTypAssValue() != null &&
						!dto.getProductPosKvs().getMenuTypAssValue().isEmpty()) {
					bufferWriter.append( "<CategoryMenus>");
	                for(GenericEntry dtoCategory : dto.getProductPosKvs().getMenuTypAssValue()) {
	                	bufferWriter.append( "<CategoryMenu categoryID=\""+ dtoCategory.getCode() + "\"/>");                
	                }
	                bufferWriter.append( "</CategoryMenus>");breakline(bufferWriter);
				}
								
				if(dto.getProductPosKvs() != null && dto.getProductPosKvs().getEquivalent() != null && dto.getProductPosKvs().getEquivalent().getCode()!=(-999)) { 
					Product equivalent = allProducts.get(productCodeIdRelationMap.get(dto.getProductPosKvs().getEquivalent().getCode()));
					if (equivalent != null &&
							validMenuItemCode(equivalent, layeringProductService.getExcludeInactiveMI())) {
						bufferWriter.append( getTag("Equivalent", dto.getProductPosKvs().getEquivalent()));
					}
				}
				if(dto.getProductPosKvs() != null && dto.getProductPosKvs().getDisplayAs() != null) { 
					Product displayAs = allProducts.get(productCodeIdRelationMap.get(dto.getProductPosKvs().getDisplayAs().getCode()));
					if (displayAs != null &&
							validMenuItemCode(displayAs, layeringProductService.getExcludeInactiveMI())) {
						bufferWriter.append( getTag("DisplayAs", dto.getProductPosKvs().getDisplayAs()));
					}
				}
				if ( dto.getProductPosKvs()!=null) {
					bufferWriter.append(getTag("PromoPerItemQuantityLimit", String.valueOf(dto.getProductPosKvs().getPromoPerItemQuantityLimit())));
					bufferWriter.append(getTag("DepositCode", dto.getProductPosKvs().getDeposit()));	
					bufferWriter.append(getTag("Barcode", dto.getProductPosKvs().getMenuItemBarcode() ));	
				}
				
                if(dto.getSecondaryMenuItem() != null) bufferWriter.append( getTag("Secondary", dto.getSecondaryMenuItem().toString()) );
                
               if(dto.getPrimaryMenuItemCode() != null) {
            	   Product primaryMenuItem = allProducts.get(productCodeIdRelationMap.get(dto.getPrimaryMenuItemCode()));
            	   if (primaryMenuItem != null &&
            			   validMenuItemCode(primaryMenuItem, layeringProductService.getExcludeInactiveMI())) {
            		   bufferWriter.append( getTag("PrimaryProductCode", dto.getPrimaryMenuItemCode().toString()) ); 
            	   }
               }
				
                if(dto.getFamilyGroup() != null) bufferWriter.append( getTag("FamilyGroup", dto.getFamilyGroup()) );						
				if(dto.getChoiceGroup() != null) bufferWriter.append( getTag("ChoiceGroup", dto.getChoiceGroup()));				
				if(dto.getDayPartCode() != null) bufferWriter.append( getTag("DayPartCode", dto.getDayPartCode()) );
				if(dto.getAuxiliaryMenuItem() != null) bufferWriter.append( getTagBoolean("DummyProduct", dto.getAuxiliaryMenuItem()) );	

				if(dto.getProductPosKvs()!=null && dto.getProductPosKvs().getPromotionComplement().size() > 0 ) {
					for(GenericEntry dtoPromoComplement : dto.getProductPosKvs().getPromotionComplement())
					{

						if (allProducts.containsKey(dtoPromoComplement.getCode()))
						{
							bufferWriter.append( "<DoubledPromotion>"+ allProducts.get(dtoPromoComplement.getCode()).getProductCode());
							bufferWriter.append( "</DoubledPromotion>");breakline(bufferWriter);
						}
					}
				}
				
				if(dto.getProductClassId() != null && dto.getProductClassId().equals("11")) {
					bufferWriter.append("<ContainerVM>true</ContainerVM>");					
				} else {
					bufferWriter.append("<ContainerVM>false</ContainerVM>");					
				}
				breakline(bufferWriter);
						
				if (dto.getProductPosKvs()!=null && dto.getProductPosKvs().getMenuItemUnit()!=null) {
					bufferWriter.append(getTag("ProductUnit",dto.getProductPosKvs().getMenuItemUnit()));
				}
				
				if (CSOHasLimitedTimeDiscount != null) {
					bufferWriter.append(getTagBoolean("CSOHasLimitedTimeDiscount", CSOHasLimitedTimeDiscount));
				}
	        
				
			if(dto.getProductPosKvs()!= null) {
				if (dto.getProductPosKvs().getDisplayOrder() == null) dto.getProductPosKvs().setDisplayOrder(0L);
				bufferWriter.append("<DisplayOrder>"+ dto.getProductPosKvs().getDisplayOrder() +"</DisplayOrder>");breakline(bufferWriter);
			}

			
			bufferWriter.append("<SalesType"); 
			if(dto.getProductPosKvs()!= null) {
				for(GenericEntry dtoSalesType : dto.getProductPosKvs().getSalesType()) {			
					String salestyp = getAttributeBoolean(dtoSalesType.getName(), dtoSalesType.getCode());	
					bufferWriter.append(salestyp);				        	        
	        }
			bufferWriter.append("/>");
			if(dto.getProductPosKvs().getDisplayWaste()!= null && dto.getProductPosKvs().getDisplayWaste().equals("YES")) {
				String displayWaste = getTagBoolean("DisplayWaste", 1L);
				bufferWriter.append(displayWaste);	
			} else {
				String displayWaste = getTagBoolean("DisplayWaste", 0L);
				bufferWriter.append(displayWaste);
			}
			if(dto.getProductPosKvs().getUpsizable()!= null && dto.getProductPosKvs().getUpsizable().equals("YES")) {
				String upsizable = getTagBoolean("Upsizable", 1L);
				bufferWriter.append(upsizable);	
			} else {
				String upsizable = getTagBoolean("Upsizable", 0L);
				bufferWriter.append(upsizable);
			}
			if (dto.getProductClassId() != null && 
					(dto.getProductClassId().equals("4") ||
							dto.getProductClassId().equals("9") ||
							dto.getProductClassId().equals("12"))) {
				if(dto.getProductPosKvs().getMutuallyExclusive() != null 
						&& dto.getProductPosKvs().getMutuallyExclusive().equalsIgnoreCase("YES")) {
						String isMutuallyExclusive = getTagBoolean("IsMutuallyExclusive", 1L);
						bufferWriter.append(isMutuallyExclusive);	
				} else {
					String isMutuallyExclusive = getTagBoolean("IsMutuallyExclusive", 0L);
					bufferWriter.append(isMutuallyExclusive);
				}
			}
			
			}
			if(dto.getTimeAvailableForSales()!=null && !dto.getTimeAvailableForSales().isEmpty()) {
				bufferWriter.append("<TimeRestrictions>");breakline(bufferWriter);
				for (TimeFrames tf : dto.getTimeAvailableForSales()) {
					bufferWriter.append("<AllowedTime startTime="+"\""+tf.getFrom()+"\""+' '+"endTime="+"\""+tf.getTo()+"\""+"/>");
					breakline(bufferWriter);
				}
				bufferWriter.append("</TimeRestrictions>");breakline(bufferWriter);
			}
			
				if((dto.getPrmoMenuItem() !=null && dto.getPrmoMenuItem() !=2) || (dto.getPrmoChoice() !=null && dto.getPrmoChoice() !=2)) {
				bufferWriter.append("<Promotion");
				bufferWriter.append(getAttributeBoolean("isPromotional", dto.getPrmoMenuItem())); 
				bufferWriter.append(getAttributeBoolean("isPromotionalChoice", dto.getPrmoChoice()));
				if(dto.getPromoStartDate()!=null) {
					bufferWriter.append(" startDate=\""+ dto.getPromoStartDate() + "\"");
					}else {
					bufferWriter.append(" startDate="+"\""+"\"" );
					}
					if(dto.getPromoEndDate()!=null) {
					bufferWriter.append(" endDate=\""+ dto.getPromoEndDate() +"\">");
					}else {
					bufferWriter.append(" endDate="+"\"" +"\">");
					}
				if(dto.getPromoInstId() != null) {			
					String timeRestrictionsTagText = getTimeRestrictionsTagText(dto);
					if (!timeRestrictionsTagText.isBlank()) {
						bufferWriter.append(timeRestrictionsTagText);breakline(bufferWriter);
					}
					bufferWriter.append("</Promotion>");breakline(bufferWriter);
					}
				}
				
				if(dto.getAssociatedPromoProducts()!=null) {
					 int autoFlag   = 0;
					 int manualFlag = 0;
					 int promoFlg   = 0;
					for(AssociatedPromoProducts dtoAssocaitedPromo :dto.getAssociatedPromoProducts()) {
						if(dtoAssocaitedPromo.getPkgGenCd()!=null && dtoAssocaitedPromo.getPkgGenCd().equals("Automatic")) {
							if(promoFlg== 0) { 
								bufferWriter.append("<PromotionAssociation>"); 
					        	promoFlg = 1;
							}
							if(autoFlag==0) 	bufferWriter.append("<AutoPromotions>"); 
							autoFlag = 1;
							bufferWriter.append("<Promotion ");
							bufferWriter.append("promoItemID=\""+ dtoAssocaitedPromo.getPromoPrdId() +"\" ");
							bufferWriter.append("sequence=\""+ dtoAssocaitedPromo.getPriority() +"\" />") ;							
						}					
					}
					if(autoFlag==1) bufferWriter.append("</AutoPromotions>");breakline(bufferWriter);
					
					for(AssociatedPromoProducts dtoAssocaitedPromo :dto.getAssociatedPromoProducts()) {
						if(dtoAssocaitedPromo.getPkgGenCd()!=null && dtoAssocaitedPromo.getPkgGenCd().equals("Manual")) {
							if(promoFlg== 0) { 
								bufferWriter.append("<PromotionAssociation>"); 
					        	promoFlg = 1;
							}
							if(manualFlag==0) 	bufferWriter.append("<ManualPromotions>"); 
							manualFlag = 1;
							bufferWriter.append("<Promotion ");
							bufferWriter.append("promoItemID=\""+ dtoAssocaitedPromo.getPromoPrdId() +"\" ");
							bufferWriter.append("sequence=\""+ dtoAssocaitedPromo.getPriority() +"\" />") ;							
						}					
					}
					if(manualFlag==1) bufferWriter.append("</ManualPromotions>");breakline(bufferWriter);
					if(promoFlg == 1) bufferWriter.append("</PromotionAssociation>");breakline(bufferWriter); 
				}
							
				if(dto.getPromotionGroups()!=null && dto.getPromotionGroups().size() > 0 ) {
					bufferWriter.append( "<PromotionGroups>");
						for(PromotionGroup dtoPromo : dto.getPromotionGroups()) {
							bufferWriter.append( "<PromotionGroup code=\""+ dtoPromo.getCode() +"\">"); 
							for(ProductList dtoPromoList :  dtoPromo.getList()) {
								bufferWriter.append( getTag("List", dtoPromoList.getList() ) );							
							}
							bufferWriter.append( "</PromotionGroup>");breakline(bufferWriter);
						}
					bufferWriter.append( "</PromotionGroups>");breakline(bufferWriter);
				}
					
				if(dto.getProductGroups()!=null && dto.getProductGroups().size() > 0 ) {				
					bufferWriter.append( "<ProductGroups>"); breakline(bufferWriter);	
					for(Code dtoCode :  dto.getProductGroups()) {
						bufferWriter.append("<Code>"+ dtoCode.getCode() +"</Code>");breakline(bufferWriter);							
					}
					bufferWriter.append( "</ProductGroups>");breakline(bufferWriter);		    
				} 
				
				String keyDiscount = dto.getProductId() + "_" + dto.getPrdInstId();
			    List<Long> discounts=  mapDiscountNotAllowed.get(keyDiscount);
			    if(discounts!=null && discounts.size()>0) {
			    	bufferWriter.append( "<DiscountsNotAllowed>");breakline(bufferWriter);
			    	for(Long dc:discounts) {
			    		bufferWriter.append("<Id>"+dc+"</Id>");breakline(bufferWriter);
			    	}
			    	bufferWriter.append( "</DiscountsNotAllowed>");breakline(bufferWriter);
			    }
			  
			    if(dto.getProductAbsSettings()!=null) {
			   if(dto.getProductAbsSettings().getIceProductDef()!=null) bufferWriter.append("<IceDefinition>"+dto.getProductAbsSettings().getIceProductDef()+"</IceDefinition>");breakline(bufferWriter);
			    }

			    if(dto.getProductPosKvs()!=null && dto.getProductPosKvs().getSellLocation().size() > 0 ) {
				bufferWriter.append( "<Distribution>");				
				for(GenericEntry dtoSell : dto.getProductPosKvs().getSellLocation()) {
					bufferWriter.append( getTag("Point", dtoSell.getName()) );								
				}
				bufferWriter.append( "</Distribution>");breakline(bufferWriter);
			}
			
			if (dto.getProductPosKvs().getGrpBundle() != null && dto.getProductPosKvs().getGrpBundle().equals(1L)) {
				bufferWriter.append(getTagBoolean("GroupBundle", dto.getProductPosKvs().getGrpBundle()));
				String grpLimit = "";
				if (dto.getProductPosKvs().getGrpBundleLimit() != null) {
					grpLimit = dto.getProductPosKvs().getGrpBundleLimit().toString();
				}
				
				bufferWriter.append("<GroupBundleQty>"+ grpLimit +"</GroupBundleQty>");breakline(bufferWriter);
			}
			if (dto.getProductCCMSettings() != null && 
					dto.getProductCCMSettings().getCcmFFpriority() != null) {
				bufferWriter.append("<FulfillmentPriority>"+ dto.getProductCCMSettings().getCcmFFpriority() +"</FulfillmentPriority>");breakline(bufferWriter);	
			}
			
			if (dto.getProductPosKvs() != null && dto.getProductPosKvs().getDiscountsNotAllowed() != null
					&& !dto.getProductPosKvs().getDiscountsNotAllowed().isEmpty()) {
				bufferWriter.append("<DiscountsNotAllowed>");
				for (GenericEntry entry : dto.getProductPosKvs().getDiscountsNotAllowed()) {
					bufferWriter.append("<Id>"+ entry.getCode() +"</Id>");breakline(bufferWriter);
				}
				bufferWriter.append("</DiscountsNotAllowed>");breakline(bufferWriter);
			}
			if(dto.getMaxIngredients() != null) bufferWriter.append( getTag("MaxExtraIngredientsQuantity", dto.getMaxIngredients().toString()) );
			if (dto.getStationGroup() != null) {
				bufferWriter.append(getTag("StationGroup", dto.getStationGroup()));
			}
				
				
			bufferWriter.append( "<Production>");				
			if (dto.getProductPosKvs()!=null) {
				if(dto.getProductPosKvs().getAutoGrill()!=null) {
					bufferWriter.append( getTagBoolean("AutoGrill", dto.getProductPosKvs().getAutoGrill()) );
				} else {
					bufferWriter.append( getTagBoolean("AutoGrill", 0L) );
				}

				
				
				bufferWriter.append( "<KVSdisplay");
				for(GenericEntry dtoGenericEntry : dto.getProductPosKvs().getKvsDisplay()) {
					bufferWriter.append( getAttributeBoolean(dtoGenericEntry.getName(), dtoGenericEntry.getCode()) );					 
				}
				
				if(dto.getProductPosKvs().getDisplayGrillInstructions()!=null) {
					bufferWriter.append( " displayGrillInstructions=\""+ dto.getProductPosKvs().getDisplayGrillInstructions() +"\"");
				}
				else {
					bufferWriter.append( " displayGrillInstructions=\"ASKVS\"");
				}
				bufferWriter.append( "/>");
			
                 
                bufferWriter.append( "<Grillable ");
				if(dto.getProductPosKvs().getPrintGrillSlip()!=null && dto.getProductPosKvs().getPrintGrillSlip() == 2L) {
					bufferWriter.append( getAttributeBoolean("doNotPrint",  1L));
				}else {
					bufferWriter.append( getAttributeBoolean("doNotPrint", 0L));
				}
				if(dto.getProductPosKvs().getGrillable()!=null) {
					bufferWriter.append( getAttributeBoolean("status",   dto.getProductPosKvs().getGrillable()) );
				} else {
					bufferWriter.append( getAttributeBoolean("status",   0L) );
				}			
                bufferWriter.append( "/>");
                
				bufferWriter.append( "<UseBufferEngine ");
                if(dto.getProductPosKvs().getIncludedInBufferEngine()!=null && dto.getProductPosKvs().getIncludedInBufferEngine().equals(1L)) {    	
					bufferWriter.append( getAttributeBoolean("status",  dto.getProductPosKvs().getIncludedInBufferEngine() ) );	
					bufferWriter.append( getAttributeBoolean("isBunBuffer",  dto.getProductPosKvs().getIncludedInBufferEngine() ) );				
				} else {
					bufferWriter.append( getAttributeBoolean("status",  0 ) );	
					bufferWriter.append( getAttributeBoolean("isBunBuffer",  0 ) );
				}
                bufferWriter.append( "/>");     
				

                if(dto.getProductPosKvs().getProductionMenuItemGroup()!=null && routingSetsForPresentationAndProductIon.size()>0) {
                    
                    String kvsRoutesText = getKVSRoutesText(dto,routingSetsForPresentationAndProductIon);
                    if (!kvsRoutesText.isBlank()) {
    					bufferWriter.append(kvsRoutesText);
    				}
                    }
				
				bufferWriter.append( getTag("BunPrepTypeID", dto.getProductPosKvs().getBunPrepTypeID() ) );							
                
				Long triggerDisplayOnORB = dto.getProductPosKvs().getTriggerDisplayonORB() == null ? 1L : dto.getProductPosKvs().getTriggerDisplayonORB();
				bufferWriter.append(getTagBoolean("TriggerDisplayOnOrb",triggerDisplayOnORB));

				if (dto.getProductPosKvs().getDisplayNumbersInsteadOfModifiers() != null) {
					if (dto.getProductPosKvs().getDisplayNumbersInsteadOfModifiers().equals(1L)) {
						bufferWriter.append(getTag("displayNumbersInsteadofModifiers", "true"));
					} else if (dto.getProductPosKvs().getDisplayNumbersInsteadOfModifiers().equals(2L)) {
						bufferWriter.append(getTag("displayNumbersInsteadofModifiers", "false"));
					} else if (dto.getProductPosKvs().getDisplayNumbersInsteadOfModifiers().equals(3L)) {
						bufferWriter.append(getTag("displayNumbersInsteadofModifiers", "both"));
					}
				}
				
				if (allDrinkVolListMap.containsKey(String.valueOf(dto.getProductPosKvs().getDrnkVolId()))) {
					String drinkCode = allDrinkVolListMap.get(String.valueOf(dto.getProductPosKvs().getDrnkVolId()))
							.toString();
					bufferWriter.append("<NGABSVolume>" + drinkCode + "</NGABSVolume>");breakline(bufferWriter);
				}
				if (dto.getProductPosKvs().getnGABSCode() != null) {
					bufferWriter.append("<NGABSCode>" + dto.getProductPosKvs().getnGABSCode() + "</NGABSCode>");breakline(bufferWriter);	
				}
}

			if (dto.getProductPosKvs().getRtmType() != null
					&& Long.parseLong(dto.getProductPosKvs().getRtmType()) > 0L) {
				bufferWriter.append("<RapidTurnoverMonitor");
				if (dto.getProductPosKvs().getRtmTypeDesc() != null) {
					bufferWriter.append(" type=\"" + dto.getProductPosKvs().getRtmTypeDesc() +"\"");	
				}
				if (dto.getProductPosKvs().getRtmType() != null
						&& Long.parseLong(dto.getProductPosKvs().getRtmType()) == 1L) {
					if (dto.getProductPosKvs().getRtmThreshold() != null &&
							dto.getProductPosKvs().getRtmThreshold() > 0L) {
						bufferWriter.append(" threshold=\"" + dto.getProductPosKvs().getRtmThreshold() +"\"");
					}
					if (dto.getProductPosKvs().getRtmAutoBumpTimeout()!= null &&
							dto.getProductPosKvs().getRtmAutoBumpTimeout() > 0L) {
						bufferWriter.append(" autoBumpTimeout=\"" + dto.getProductPosKvs().getRtmAutoBumpTimeout() +"\"");
					}
					if (dto.getProductPosKvs().getRtmImage() != null) {
						bufferWriter.append(" image=\"" + allMediaMap.get(dto.getProductPosKvs().getRtmImage().getCode()) +"\"");
					}
					if (dto.getProductPosKvs().getRtmPriority()!= null &&
							dto.getProductPosKvs().getRtmPriority() > 0L) {
						bufferWriter.append(" priority=\"" + dto.getProductPosKvs().getRtmPriority() +"\"");
					}
					
					if (dto.getProductPosKvs().getRouteGrillOnly() != null) {
						bufferWriter.append( getAttributeBoolean("routeGrillOnly", dto.getProductPosKvs().getRouteGrillOnly()));
					}
					if (dto.getProductPosKvs().getDedicatedCell() != null && dto.getProductPosKvs().getDedicatedCell()>0L
							&& dto.getProductPosKvs().getRouteGrillOnly() != null 
							&& !dto.getProductPosKvs().getRouteGrillOnly().equals(1L)) {
						bufferWriter.append(" dedicatedCell=\"" + dto.getProductPosKvs().getDedicatedCell() +"\"");
					}
					if (dto.getProductPosKvs().getGrillCellRangeStart() != null && 
							dto.getProductPosKvs().getGrillCellRangeStart()>0) {
						bufferWriter.append(" grillCellRangeStart=\"" + dto.getProductPosKvs().getGrillCellRangeStart() +"\"");
					}
					if (dto.getProductPosKvs().getGrillCellRangeEnd() != null &&
							dto.getProductPosKvs().getGrillCellRangeEnd()>0) {
						bufferWriter.append(" grillCellRangeEnd=\"" + dto.getProductPosKvs().getGrillCellRangeEnd() +"\"");
					}
					
				}
				bufferWriter.append("/>");
			}
			
				String substitutionGrillText = getSubstitutionGrillText(dto, allProducts);	
					bufferWriter.append( "<SubstitutionGrill>");
					bufferWriter.append(substitutionGrillText);
					bufferWriter.append( "</SubstitutionGrill>");breakline(bufferWriter);
				
				String nonRequiredChoicesText = getNonRequiredChoicesText(dto, allProducts);	
				if(!nonRequiredChoicesText.isBlank()) {
					bufferWriter.append( "<NonRequiredChoices>");breakline(bufferWriter);
					bufferWriter.append(nonRequiredChoicesText.substring(0, nonRequiredChoicesText.length() - 1));
					breakline(bufferWriter);
					bufferWriter.append( "</NonRequiredChoices>");breakline(bufferWriter);
				}
				bufferWriter.append( "</Production>");breakline(bufferWriter);
				bufferWriter.append( "<PriceList>");								
				for(PriceTag priceTag : dto.getPriceList()) {
					bufferWriter.append( "<PriceTag>");
					for(Pricing price : priceTag.getPricing()) {
						bufferWriter.append( "<Pricing priceCode=\""+ price.getPriceCode() +"\">");	
						if(dto.getProductPosKvs()!= null && dto.getProductPosKvs().getFeeExempt()!=null) {
							for(GenericEntry dtoFeeExempt : dto.getProductPosKvs().getFeeExempt()) {	
								if(dtoFeeExempt.getName().equalsIgnoreCase(price.getPriceCode())) {
									if(dtoFeeExempt.getCode()==1L) {
										bufferWriter.append(getTag("FeeExempt","true"));
									}
								}
				        }
						}
						String taxPrice= price.getPrice();
						bufferWriter.append( "<Price>"+  taxPrice +"</Price>");breakline(bufferWriter);				
						for(Tax tax : price.getTax()) {
							bufferWriter.append("<Tax ");
							if(null!=tax.getTaxCode())bufferWriter.append(" taxCode=\""+ tax.getTaxCode());
							if(null!=tax.getRule())bufferWriter.append("\" rule=\""+ tax.getRule());
							if(null!=tax.getEntry())bufferWriter.append("\" entry=\""+ tax.getEntry());
							bufferWriter.append("\"/>");
						}
						bufferWriter.append( "</Pricing>");breakline(bufferWriter);				
					}
					bufferWriter.append( "</PriceTag>");breakline(bufferWriter);
				}				
			bufferWriter.append( "</PriceList>");breakline(bufferWriter);
				if(dto.getPriceList()!=null &&  dto.getPriceList().size() > 0) {				 					
					bufferWriter.append(getTag("PricingMethod", dto.getProductCCMSettings().getPricingMethod() ));
					if (dto.getProductCCMSettings().getReduction()!=null && dto.getProductCCMSettings().getReduction().size()>0) 
					{
						bufferWriter.append( "<EscalatingPriceReductions>");
						for (Reduction reduction : dto.getProductCCMSettings().getReduction())
						{
							bufferWriter.append( "<Reduction Qty=\""+  reduction.getQty() +"\" Rate=\""+ reduction.getRate().intValue() +"\"/>");
							
						}
						bufferWriter.append( "</EscalatingPriceReductions>");breakline(bufferWriter);
					}
					if(dto.getProductPosKvs().getAutoGrillConf()!=null) {
					if (dto.getProductPosKvs().getAutoGrillConf() == 1L) {
						bufferWriter.append("<Department>");
						bufferWriter.append("<Id>QL</Id><ClassDepartment><Id>A1</Id><SubClassDepartment><Id>A1</Id></SubClassDepartment></ClassDepartment>");
	                    bufferWriter.append("</Department>");breakline(bufferWriter);
	                    }
					else if (dto.getProductPosKvs().getAutoGrillConf() == 2L) {
						bufferWriter.append("<Department>");
						bufferWriter.append("<Id>QL</Id><ClassDepartment><Id>A2</Id><SubClassDepartment><Id>A2</Id></SubClassDepartment></ClassDepartment>");
						bufferWriter.append("</Department>");breakline(bufferWriter);
					}
					else if (dto.getProductPosKvs().getAutoGrillConf() == 4L) {
						bufferWriter.append("<Department>");
						bufferWriter.append("<Id>QL</Id><ClassDepartment><Id>A0</Id><SubClassDepartment><Id>A0</Id></SubClassDepartment></ClassDepartment>");
						bufferWriter.append("</Department>");breakline(bufferWriter);
					}	
					}
					
					sortComponents(dto.getComponents());
				if(dto.getComponents()!=null) {
					boolean writeComponentMainTag = false;
					for(Component dtoComponents : dto.getComponents()) {	
						if(dtoComponents.getComponentProductId() != null 
								&& allProducts.get(dtoComponents.getComponentProductId())!= null
								&& null!=allProducts.get(dtoComponents.getComponentProductId()).getProductCode()) {
							dtoComponents.setProductCode(allProducts.get(dtoComponents.getComponentProductId()).getProductCode());
						}
						if(allProducts.get(dtoComponents.getComponentProductId())!= null && 
								null!=allProducts.get(dtoComponents.getComponentProductId()).getProductClass()) {
							dtoComponents.setProductClass(allProducts.get(dtoComponents.getComponentProductId()).getProductClass().toString());
						}
							
						if (allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs() && allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs().getAutoCondiment()
								.size() > 0)
							dtoComponents.setAutoCondiment(allProducts.get(dtoComponents.getComponentProductId())
									.getProductPosKvs().getHasAutoCondiment());
						if(null!=dtoComponents.getProductClass() && !(dtoComponents.getProductClass().equals("CHOICE") ||dtoComponents.getProductClass().equals("CHECK CATEGORY")
								||dtoComponents.getProductClass().equals("CHOICE_EVM"))&& 
								dtoComponents.getAutoCondiment() != null &&
									!dtoComponents.getAutoCondiment().equals(1L) && 
											dtoComponents.getCompositionType() != null &&
												dtoComponents.getCompositionType().equals(1L) && dtoComponents.getDeleted()==0) {
							
							if (!writeComponentMainTag) {
								writeComponentMainTag = true;
								bufferWriter.append( "<Composition>");
							}
							bufferWriter.append( "<Component>");
							bufferWriter.append( "<ProductCode>"+ dtoComponents.getProductCode() +"</ProductCode>");breakline(bufferWriter);
							if(null!=dtoComponents.getDefaultQuantity())bufferWriter.append( "<DefaultQuantity>"+ dtoComponents.getDefaultQuantity() +"</DefaultQuantity>");breakline(bufferWriter);
							if(null!=dtoComponents.getMinQuantity())bufferWriter.append( "<MinQuantity>"+ dtoComponents.getMinQuantity() +"</MinQuantity>");breakline(bufferWriter);
							if(null!=dtoComponents.getMaxQuantity())bufferWriter.append( "<MaxQuantity>"+ dtoComponents.getMaxQuantity() +"</MaxQuantity>");breakline(bufferWriter);
							if(null!=dtoComponents.getRefundThreshold())bufferWriter.append( "<RefundThreshold>"+ dtoComponents.getRefundThreshold() +"</RefundThreshold>");breakline(bufferWriter);
							bufferWriter.append( "<ChargeThreshold>"+ dtoComponents.getChargeThreShold() +"</ChargeThreshold>");breakline(bufferWriter);
							bufferWriter.append( getTag("CostInclusive", dtoComponents.getCostInclusive()));

							if(dtoComponents.getDisplayOnCSO() != null)	bufferWriter.append( getTag("DisplayOnCSO", dtoComponents.getDisplayOnCSO()));
														
							if(!(dtoComponents.getProductClass().equals("CHOICE") ||dtoComponents.getProductClass().equals("CHECK CATEGORY")
									||dtoComponents.getProductClass().equals("CHOICE_EVM")) && kvsflagformarketDetails ) {
								bufferWriter.append( "<PlainGrill>"+ dtoComponents.getPlainGrill() +"</PlainGrill>");breakline(bufferWriter);
								bufferWriter.append( "<SmartGrill>"+ dtoComponents.getSmartGrill() +"</SmartGrill>");breakline(bufferWriter);
								bufferWriter.append( "<ForceCompDisplay>"+ dtoComponents.getForceCompositionDisplay() +"</ForceCompDisplay>");breakline(bufferWriter);
							}

							if(dtoComponents.getDefaultProductId() != null && allProducts.containsKey(dtoComponents.getDefaultProductId())) {
								Product auxComponent = allProducts.get(dtoComponents.getDefaultProductId());
								if(validMenuItemCode(auxComponent, layeringProductService.getExcludeInactiveMI()))
									bufferWriter.append( "<DefaultProduct>"+ auxComponent.getProductCode() +"</DefaultProduct>");breakline(bufferWriter);
							}
				

							if(dtoComponents.getReferenceProductId() != null && allProducts.containsKey(dtoComponents.getReferenceProductId())) {
								Product auxComponent = allProducts.get(dtoComponents.getReferenceProductId());
								if(validMenuItemCode(auxComponent, layeringProductService.getExcludeInactiveMI()))
									bufferWriter.append( "<ReferenceProduct>"+ auxComponent.getProductCode() +"</ReferenceProduct>");breakline(bufferWriter);
							}
							
							if(null!=dtoComponents.getFlexibleChoice() && !(dtoComponents.getFlexibleChoice().equals("-1")))bufferWriter.append(getTagBoolean("FlexibleChoice",Long.valueOf(dtoComponents.getFlexibleChoice())));
							bufferWriter.append( getTag("PriceCalculationMode", dtoComponents.getPriceCalculationMode()));
							if(null!=dtoComponents.getAnchor())bufferWriter.append( "<Anchor>"+ dtoComponents.getAnchor() +"</Anchor>");breakline(bufferWriter);							bufferWriter.append( getTag("PriceReductionRate", dtoComponents.getPricingReductionRate()));
							
							bufferWriter.append(getTag("setParentProductOutage",dtoComponents.getSetParentProductOutage()));	
							bufferWriter.append(getTag("ServingFactor",String.valueOf(dtoComponents.getServingFactor()))); 
							
							if(dtoComponents.getImpactedMenuItemOnRTM() != null && allProducts.containsKey(dtoComponents.getImpactedMenuItemOnRTM())) {
								bufferWriter.append( "<ProductCodeImpactedOnRTM>"+ allProducts.get(dtoComponents.getImpactedMenuItemOnRTM()).getProductCode() +"</ProductCodeImpactedOnRTM>");breakline(bufferWriter);
							}
							
							if(dtoComponents.getSubstituteOnRTM() != null && allProducts.containsKey(dtoComponents.getSubstituteOnRTM())) {
								bufferWriter.append( "<SubstituteOnRTM>"+ allProducts.get(dtoComponents.getSubstituteOnRTM()).getProductCode() +"</SubstituteOnRTM>");breakline(bufferWriter);
							}
							
							bufferWriter.append( "</Component>");breakline(bufferWriter);																
						}					
					}	

					if (writeComponentMainTag) {
						writeComponentMainTag = false;
						bufferWriter.append( "</Composition>");breakline(bufferWriter);
					}										 		
					
					for(Component dtoComponents : dto.getComponents()) {
						if(allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductCode())dtoComponents.setProductCode(
								allProducts.get(dtoComponents.getComponentProductId()).getProductCode());
						if(allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductClass())dtoComponents.setProductClass(
								allProducts.get(dtoComponents.getComponentProductId()).getProductClass().toString());
						if (allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs() && allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs().getAutoCondiment()
								.size() > 0)
							dtoComponents.setAutoCondiment(allProducts.get(dtoComponents.getComponentProductId())
									.getProductPosKvs().getAutoCondiment().get(0).getCode());
						if(dtoComponents.getProductClass()!=null && !dtoComponents.getProductClass().equals("CHOICE") && 
								dtoComponents.getAutoCondiment() != null &&
									dtoComponents.getAutoCondiment() != 1L && 
									dtoComponents.getCompositionType() != null &&
										dtoComponents.getCompositionType()==2L  && dtoComponents.getDeleted()==0) {

							if (!writeComponentMainTag) {
								writeComponentMainTag = true;
								bufferWriter.append( "<CanAdds>");
							}
							
							bufferWriter.append( "<Component>");
							bufferWriter.append( "<ProductCode>"+ dtoComponents.getProductCode() +"</ProductCode>");breakline(bufferWriter);
							if(null!=dtoComponents.getDefaultQuantity()) {
								bufferWriter.append( "<DefaultQuantity>"+ dtoComponents.getDefaultQuantity() +"</DefaultQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getMinQuantity()) {
								bufferWriter.append( "<MinQuantity>"+ dtoComponents.getMinQuantity() +"</MinQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getMaxQuantity()) {
								bufferWriter.append( "<MaxQuantity>"+ dtoComponents.getMaxQuantity() +"</MaxQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getRefundThreshold()) {
								bufferWriter.append( "<RefundThreshold>"+ dtoComponents.getRefundThreshold() +"</RefundThreshold>");breakline(bufferWriter);
							}
							bufferWriter.append( "<ChargeThreshold>"+ dtoComponents.getChargeThreShold() +"</ChargeThreshold>");breakline(bufferWriter);
							bufferWriter.append( getTag("CostInclusive", dtoComponents.getCostInclusive()));
							if(dtoComponents.getDisplayOnCSO() != null) bufferWriter.append( getTag("DisplayOnCSO", dtoComponents.getDisplayOnCSO()));
							
							bufferWriter.append(getTag("setParentProductOutage",dtoComponents.getSetParentProductOutage()));
							bufferWriter.append( "</Component>");breakline(bufferWriter);																
						}
					}
					if (writeComponentMainTag) {
						writeComponentMainTag = false;
						bufferWriter.append( "</CanAdds>");breakline(bufferWriter);	
					}
					
					for(Component dtoComponents : dto.getComponents()) {
						if(allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductCode())dtoComponents.setProductCode(
								allProducts.get(dtoComponents.getComponentProductId()).getProductCode());
						if(allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductClass())dtoComponents.setProductClass(
								allProducts.get(dtoComponents.getComponentProductId()).getProductClass().toString());
						if (allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs() && allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs().getAutoCondiment()
								.size() > 0)
							dtoComponents.setAutoCondiment(allProducts.get(dtoComponents.getComponentProductId())
									.getProductPosKvs().getAutoCondiment().get(0).getCode());
						if(dtoComponents.getProductClass()!=null && !dtoComponents.getProductClass().equals("CHOICE") && 
								dtoComponents.getAutoCondiment() != null &&
									dtoComponents.getAutoCondiment() != 1L && 
											dtoComponents.getCompositionType() != null &&
												dtoComponents.getCompositionType()==3L   && dtoComponents.getDeleted()==0) {

							if (!writeComponentMainTag) {
								writeComponentMainTag = true;
								bufferWriter.append( "<Comments>");
							}
							
							bufferWriter.append( "<Component>");
							bufferWriter.append( "<ProductCode>"+ dtoComponents.getProductCode() +"</ProductCode>");breakline(bufferWriter);
							if(null!=dtoComponents.getDefaultQuantity()) {
								bufferWriter.append( "<DefaultQuantity>"+ dtoComponents.getDefaultQuantity() +"</DefaultQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getMinQuantity()) {
								bufferWriter.append( "<MinQuantity>"+ dtoComponents.getMinQuantity() +"</MinQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getMaxQuantity()) {
								bufferWriter.append( "<MaxQuantity>"+ dtoComponents.getMaxQuantity() +"</MaxQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getRefundThreshold()) {
								bufferWriter.append( "<RefundThreshold>"+ dtoComponents.getRefundThreshold() +"</RefundThreshold>");breakline(bufferWriter);
							}
							bufferWriter.append( "<ChargeThreshold>"+ dtoComponents.getChargeThreShold() +"</ChargeThreshold>");breakline(bufferWriter);
							if (dtoComponents.getCostInclusive() != null) {
								bufferWriter.append( getTag("CostInclusive", dtoComponents.getCostInclusive()));
							}
							if(dtoComponents.getDisplayOnCSO() != null) bufferWriter.append( getTag("DisplayOnCSO", dtoComponents.getDisplayOnCSO()));
							bufferWriter.append(getTag("setParentProductOutage", dtoComponents.getSetParentProductOutage()));
							bufferWriter.append( "</Component>");breakline(bufferWriter);																
						}
					}	

					if (writeComponentMainTag) {
						writeComponentMainTag = false;
						bufferWriter.append( "</Comments>");breakline(bufferWriter);
					}
						
					

					for(Component dtoComponents : dto.getComponents())
					{
						if(allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductCode())dtoComponents.setProductCode(
								allProducts.get(dtoComponents.getComponentProductId()).getProductCode());
						if(allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductClass())dtoComponents.setProductClass(
								allProducts.get(dtoComponents.getComponentProductId()).getProductClass().toString());
						if (allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs() && allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs().getAutoCondiment()
								.size() > 0)
							dtoComponents.setAutoCondiment(allProducts.get(dtoComponents.getComponentProductId())
									.getProductPosKvs().getAutoCondiment().get(0).getCode());
						double v_price_ref_prod = 0; 
						if(dtoComponents.getProductClass()!=null && 
								(dtoComponents.getProductClass().equals("CHOICE")) &&  
								( dtoComponents.getCompositionType() != null && dtoComponents.getCompositionType()==1L )  && 
								dtoComponents.getDeleted()==0)
						{
							if(dto.getActive()==1 &&  dtoComponents.getReferenceProductId() != null && 
									dtoComponents.getCostInclusive() != null && dtoComponents.getCostInclusive().equals("false") ) 
							{
								if(dtoComponents.getProductClass().equals("VALUE_MEAL")) {
									if (v_price_ref_prod < 0)  v_price_ref_prod= 0;						
								}							
							}

							if (!writeComponentMainTag) {
								writeComponentMainTag = true;
								bufferWriter.append( "<Choices>");
							}
							
							bufferWriter.append( "<Component>");
							bufferWriter.append( "<ProductCode>"+ dtoComponents.getProductCode() +"</ProductCode>");breakline(bufferWriter);
							if(null!=dtoComponents.getDefaultQuantity()) {
								bufferWriter.append( "<DefaultQuantity>"+ dtoComponents.getDefaultQuantity() +"</DefaultQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getMinQuantity()) {
								bufferWriter.append( "<MinQuantity>"+ dtoComponents.getMinQuantity() +"</MinQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getMaxQuantity()) {
								bufferWriter.append( "<MaxQuantity>"+ dtoComponents.getMaxQuantity() +"</MaxQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getRefundThreshold()) {
								bufferWriter.append( "<RefundThreshold>"+ dtoComponents.getRefundThreshold() +"</RefundThreshold>");breakline(bufferWriter);
							}
							bufferWriter.append( "<ChargeThreshold>"+ dtoComponents.getChargeThreShold() +"</ChargeThreshold>");breakline(bufferWriter);
							bufferWriter.append( getTag("CostInclusive", dtoComponents.getCostInclusive()));
							if(dtoComponents.getDefaultProductId() != null && allProducts.containsKey(dtoComponents.getDefaultProductId())) {
								Product auxComponent = allProducts.get(dtoComponents.getDefaultProductId());
								if(validMenuItemCode(auxComponent, layeringProductService.getExcludeInactiveMI()))
									bufferWriter.append( "<DefaultProduct>"+ auxComponent.getProductCode() +"</DefaultProduct>");breakline(bufferWriter);
							}
				

							if(null!=dtoComponents.getDefaultChoice() &&  null!=allProducts.get(dtoComponents.getDefaultChoice())) {
								bufferWriter.append( getTag("DefaultChoice", allProducts.get(dtoComponents.getDefaultChoice()).getProductCode().toString()));
							if(null==dtoComponents.getDefaultChoiceChn() || dtoComponents.getDefaultChoiceChn().isEmpty() || dtoComponents.getDefaultChoiceChn().equals("null")) {
								bufferWriter.append( getTag("DefaultChoiceChannels", defualtChoiceValues));
							}else {
							bufferWriter.append( getTag("DefaultChoiceChannels", dtoComponents.getDefaultChoiceChn().toString()));}}
							
							if(dtoComponents.getReferenceProductId() != null && allProducts.containsKey(dtoComponents.getReferenceProductId())) {
								Product auxComponent = allProducts.get(dtoComponents.getReferenceProductId());
								if(validMenuItemCode(auxComponent, layeringProductService.getExcludeInactiveMI()))
									bufferWriter.append( "<ReferenceProduct>"+ auxComponent.getProductCode() +"</ReferenceProduct>");breakline(bufferWriter);
							}
							
							
							if(null!=dtoComponents.getFlexibleChoice() && !(dtoComponents.getFlexibleChoice().equals("-1"))) {
								bufferWriter.append(getTagBoolean("FlexibleChoice",Long.valueOf(dtoComponents.getFlexibleChoice())));
							}
							bufferWriter.append( getTag("PriceCalculationMode", dtoComponents.getPriceCalculationMode()));
							if(null!=dtoComponents.getAnchor()) {
								bufferWriter.append( "<Anchor>"+ dtoComponents.getAnchor() +"</Anchor>");breakline(bufferWriter);
							}
							bufferWriter.append( getTag("PriceReductionRate", dtoComponents.getPricingReductionRate()));

							bufferWriter.append(getTag("setParentProductOutage", dtoComponents.getSetParentProductOutage()));
							
							if(dtoComponents.getImpactedMenuItemOnRTM() != null && allProducts.containsKey(dtoComponents.getImpactedMenuItemOnRTM())) {
								bufferWriter.append( "<ProductCodeImpactedOnRTM>"+ allProducts.get(dtoComponents.getImpactedMenuItemOnRTM()).getProductCode() +"</ProductCodeImpactedOnRTM>");breakline(bufferWriter);
							}
							
							if(dtoComponents.getSubstituteOnRTM() != null && allProducts.containsKey(dtoComponents.getSubstituteOnRTM())) {
								bufferWriter.append( "<SubstituteOnRTM>"+ allProducts.get(dtoComponents.getSubstituteOnRTM()).getProductCode() +"</SubstituteOnRTM>");breakline(bufferWriter);
							}
						
						bufferWriter.append( "</Component>");breakline(bufferWriter);
						}	
					}	

					if (writeComponentMainTag) {
						writeComponentMainTag = false;
						bufferWriter.append( "</Choices>");breakline(bufferWriter);
					}

				 //AutoCondimentDisplay 
					if(dto.getProductPosKvs()!=null) {
					if (dto.getProductPosKvs().getAutoCondimentDisplay()!=null && dto.getProductPosKvs().getAutoCondimentDisplay().equals(ProductDBConstant.ONE_LONG)) {
						bufferWriter.append("<AutoCondimentDisplay>");
						List<GenericEntry> tags = dto.getProductPosKvs().getSelectedTags();
						if(tags!=null && !tags.isEmpty() ) {
						for (GenericEntry tag : tags) {
							bufferWriter.append(tag.getName());
						  }
						}
						List<GenericEntry> list=dto.getProductPosKvs().getAutoCondiment();
						if(list!=null && list.size()>0) {
						if(dto.getProductPosKvs().getHasAutoCondiment()!=null && dto.getProductPosKvs().getHasAutoCondiment().equals(ProductDBConstant.ONE_LONG)) {
						for(GenericEntry genericEntry :list) {
						 bufferWriter.append("<Display"+" "+"queue="+"\""+genericEntry.getName()+"\""+"/>");
						 }
						}
						}
						if(dto.getProductPosKvs().getDisplyPosEvent()!=null)bufferWriter.append("<DisplayPOSEvent>"+dto.getProductPosKvs().getDisplyPosEvent()+"</DisplayPOSEvent>");breakline(bufferWriter);
						if(dto.getProductPosKvs().getApplySalesType()!=null && !dto.getProductPosKvs().getApplySalesType().isEmpty()) {
							bufferWriter.append("<ApplyBySaleType>"+String.join("|",dto.getProductPosKvs().getApplySalesType())+"</ApplyBySaleType>");breakline(bufferWriter);
						}
						if(dto.getProductPosKvs().getDisplayDTWposEvent()!=null)bufferWriter.append("<DisplayDTWTPOSEvent>"+dto.getProductPosKvs().getDisplayDTWposEvent()+"</DisplayDTWTPOSEvent>");breakline(bufferWriter);
						bufferWriter.append("</AutoCondimentDisplay>");breakline(bufferWriter);
					  } 
					}
					
					for(Component dtoComponents : dto.getComponents()) 
					{		
						if(allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductCode())dtoComponents.setProductCode(
								allProducts.get(dtoComponents.getComponentProductId()).getProductCode());
						if(allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductClass())dtoComponents.setProductClass(
								allProducts.get(dtoComponents.getComponentProductId()).getProductClass().toString());
						if (allProducts.get(dtoComponents.getComponentProductId())!= null && null!=allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs() && allProducts.get(dtoComponents.getComponentProductId()).getProductPosKvs().getAutoCondiment()
								.size() > 0)
							dtoComponents.setAutoCondiment(allProducts.get(dtoComponents.getComponentProductId())
									.getProductPosKvs().getAutoCondiment().get(0).getCode());
						if((dtoComponents.getProductClass() != null && !dtoComponents.getProductClass().equals("CHOICE")) &&  dtoComponents.getCompositionType() != null && dtoComponents.getCompositionType()==1  
								&&  dtoComponents.getAutoCondiment() != null && dtoComponents.getAutoCondiment()==1L  && dtoComponents.getDeleted()==0) 
						{	
							bufferWriter.append( "<AutoCondiments>");					
							bufferWriter.append( "<Component>");
							bufferWriter.append( "<ProductCode>"+ dtoComponents.getProductCode() +"</ProductCode>");breakline(bufferWriter);
							if(null!=dtoComponents.getDefaultQuantity()) {
								bufferWriter.append( "<DefaultQuantity>"+ dtoComponents.getDefaultQuantity() +"</DefaultQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getMinQuantity()) {
								bufferWriter.append( "<MinQuantity>"+ dtoComponents.getMinQuantity() +"</MinQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getMaxQuantity()) {
								bufferWriter.append( "<MaxQuantity>"+ dtoComponents.getMaxQuantity() +"</MaxQuantity>");breakline(bufferWriter);
							}
							if(null!=dtoComponents.getRefundThreshold()) {
								bufferWriter.append( "<RefundThreshold>"+ dtoComponents.getRefundThreshold() +"</RefundThreshold>");breakline(bufferWriter);
							}
							bufferWriter.append( "<ChargeThreshold>"+ dtoComponents.getChargeThreShold() +"</ChargeThreshold>");breakline(bufferWriter);
							bufferWriter.append( getTag("CostInclusive", dtoComponents.getCostInclusive()));
							if(dtoComponents.getDefaultProductId() != null && allProducts.containsKey(dtoComponents.getDefaultProductId())) {
								Product auxComponent = allProducts.get(dtoComponents.getDefaultProductId());
								if(validMenuItemCode(auxComponent, layeringProductService.getExcludeInactiveMI()))
									bufferWriter.append( "<DefaultProduct>"+ auxComponent.getProductCode() +"</DefaultProduct>");breakline(bufferWriter);
							}
				

							if(dtoComponents.getReferenceProductId() != null && allProducts.containsKey(dtoComponents.getReferenceProductId())) {
								Product auxComponent = allProducts.get(dtoComponents.getReferenceProductId());
								if(validMenuItemCode(auxComponent, layeringProductService.getExcludeInactiveMI()))
									bufferWriter.append( "<ReferenceProduct>"+ auxComponent.getProductCode() +"</ReferenceProduct>");breakline(bufferWriter);
							}
							
							if(null!=dtoComponents.getFlexibleChoice() && !(dtoComponents.getFlexibleChoice().equals("-1"))) {
								bufferWriter.append(getTagBoolean("FlexibleChoice",Long.valueOf(dtoComponents.getFlexibleChoice())));
							}
							bufferWriter.append( getTag("PriceCalculationMode", dtoComponents.getPriceCalculationMode()));
							if(null!=dtoComponents.getAnchor()) {
								bufferWriter.append( "<Anchor>"+ dtoComponents.getAnchor() +"</Anchor>");breakline(bufferWriter);
							}
							bufferWriter.append(getTag("setParentProductOutage",dtoComponents.getSetParentProductOutage()));
							if(dtoComponents.getPricingReductionRate()!=null) {
								bufferWriter.append( getTag("PriceReductionRate", dtoComponents.getPricingReductionRate()));
							} 
										
							bufferWriter.append( "</Component>");breakline(bufferWriter);	
							bufferWriter.append( "</AutoCondiments>");breakline(bufferWriter);
						}
					}	
					
				}
				if (dto.getProductClass().equals("PRODUCT") && dto.getProductShortCutSettings()!=null && dto.getProductShortCutSettings().size()>0)
				{	bufferWriter.append( "<GrillShortcuts>");
					for(ProductShortCutSettings dtoShortCut : dto.getProductShortCutSettings()) 
					{
						boolean allMatched = true;
						
						for(ShortCutDetails dtoShortCutDetails :dtoShortCut.getItem()) {
							if(!allProducts.containsKey(dtoShortCutDetails.getProductId()) ) {
								allMatched = false;
								break;
							}															
						}
						
						if(allMatched) {
							bufferWriter.append("<GrillShortcut name=\"" + dtoShortCut.getName()+ "\">");
							
							for(ShortCutDetails dtoShortCutDetails :dtoShortCut.getItem()) {
								bufferWriter.append( "<Item productCode=\""+ allProducts.get(dtoShortCutDetails.getProductId()).getProductCode() +"\" quantity=\""+ dtoShortCutDetails.getQuantity() + "\"");
								if(dtoShortCutDetails.getLight()!=0) bufferWriter.append(" modifier=\"Light\" ");
								bufferWriter.append("/>");																
							}
							
							bufferWriter.append( "</GrillShortcut>");breakline(bufferWriter);
						}
						
					}
					bufferWriter.append( "</GrillShortcuts>");breakline(bufferWriter);
				}
				if(dto.getSizeSelection().size() > 0 ) {
					bufferWriter.append( "<SizeSelection>");breakline(bufferWriter);
					for(Size dtoSize :  dto.getSizeSelection()) {										
						bufferWriter.append( "<Size entry=\""+ dtoSize.getEntry() +"\" code=\""+ dtoSize.getCode() +"\" showDimensionToCustomer=\""+ dtoSize.getShowDimensionToCustomer() +"\" showDimensionOnRCTLocalPromotion=\""+ dtoSize.getShowDimensionOnRCTLocalPromotion() +"\" />");
					}
					bufferWriter.append( "</SizeSelection>");breakline(bufferWriter);														
				}
				
				if(dto.getSubstitutionList().size() > 0 ) {
					bufferWriter.append( "<SubstitutionList>");
					for(Item dtoSubstitutionList :  dto.getSubstitutionList()) {										
						bufferWriter.append( "<Item id=\""+ dtoSubstitutionList.getId() +"\" productCode=\""+  dtoSubstitutionList.getProductCode() +"\"/>");
					}
					bufferWriter.append( "</SubstitutionList>");breakline(bufferWriter);
				}
				
				final PackageXMLParametersDTO[] paramss = packageGeneratorDTO.getPackageXmlParameter();
				long smrtrmdr = dto.getProductPosKvs().getSmartReminderGroup().getCode();
				if(smrtrmdr != 0L && paramss != null ) {
					String smrtRemndrGroupName =  smrtrmndrGroupName(smrtrmdr);
					for ( final PackageXMLParametersDTO param : paramss ) {
							if ( param != null && ("restercindicator".equalsIgnoreCase( param.getParm_na() )) && "ON".equalsIgnoreCase( param.getParm_val() ) ) {
								bufferWriter.append("<SmartReminder group=\""+ smrtRemndrGroupName +"\">");
								final String smrtReminderPriority = dto.getProductPosKvs().getSmrtReminderPriority().toString();
								final String tagName = "Priority";
								final StringBuffer tag = new StringBuffer("");
								if (ObjectUtils.isFilled(smrtReminderPriority)) {
									tag.append("<" + tagName + FINAL_TAG_SYMBOL);
									tag.append(ObjectUtils.replaceSpecialCharacters(smrtReminderPriority));
									tag.append("</" + tagName + FINAL_TAG_SYMBOL);
									tag.append(System.lineSeparator());
									bufferWriter.append(tag.toString());
								}
								bufferWriter.append( "</SmartReminder>");breakline(bufferWriter);
							}
						}
				}
			
				bufferWriter.append( "<Presentation>");				
				bufferWriter.append( getTag("KVSColor", allColorsMap.get(dto.getProductPresentation().getColorScreenKVSFont().getCode()))) ; 
				bufferWriter.append( getTag("DisplayColor", allColorsMap.get(dto.getProductPresentation().getColorScreenDisplay().getCode()) ));				
				bufferWriter.append( getTag("BGNormal", allColorsMap.get(dto.getProductPresentation().getColorButtonBackground().getCode()) ));								
				bufferWriter.append( getTag("BGPressed", allColorsMap.get(dto.getProductPresentation().getColorButtonBackgroundPressed().getCode())));								
				bufferWriter.append( getTag("FGNormal", allColorsMap.get(dto.getProductPresentation().getColorButtonText().getCode())));
				bufferWriter.append( getTag("FGPressed", allColorsMap.get(dto.getProductPresentation().getColorButtonTextPressed().getCode())));
				if (dto.getProductPresentation().getImage() != null &&
						!dto.getProductPresentation().getImage().isEmpty()) {
					bufferWriter.append( getTag("BitmapName", allMediaMap.get(dto.getProductPresentation().getImage().get(0).getCode())));	
				}
				bufferWriter.append( getTag("SummaryMonitorIcon",allMediaMap.get( dto.getProductPresentation().getSummaryMonitorImage().getCode()) ));
				bufferWriter.append( getTag("SmallBitmapName", allMediaMap.get(dto.getProductPresentation().getSmallImage().getCode())) );
				bufferWriter.append( getTag("GrillBitmapName", allMediaMap.get(dto.getProductPresentation().getGrillImageString().getCode() )));
				bufferWriter.append( getTag("HandheldImageName",allMediaMap.get( dto.getProductPresentation().getHandheldImage().getCode() ) ));
				bufferWriter.append( getTag("CSOCircleImageName", allMediaMap.get(dto.getProductPresentation().getCsoCircleImage().getCode() )) );
				bufferWriter.append( getTag("CSOLargeImageName", allMediaMap.get(dto.getProductPresentation().getCsoLargeImage().getCode())  ));
				bufferWriter.append( getTag("CSOSmallImageName", allMediaMap.get(dto.getProductPresentation().getCsoSmallImage().getCode()) ));
				bufferWriter.append( getTag("CSOGrillImageName", allMediaMap.get(dto.getProductPresentation().getCsoGrillImage().getCode()) ));
				bufferWriter.append( getTag("CSODimensionImageName",allMediaMap.get( dto.getProductPresentation().getCsoDimensionImage().getCode()) ));				
				bufferWriter.append( getTag("CSOValueMealImage",allMediaMap.get( dto.getProductPresentation().getCsoValueMealImage().getCode()) ) );
				bufferWriter.append( getTag("CSOCartImageName", allMediaMap.get(dto.getProductPresentation().getCsoCartImage().getCode()) ));
				bufferWriter.append( getTag("CytPreviewImage",allMediaMap.get( dto.getProductPresentation().getCytPreviewImage().getCode())  ) );
				bufferWriter.append( getTag("CytPreviewImageBottom", allMediaMap.get(dto.getProductPresentation().getCytPreviewBottomImage().getCode()) ) );
				if(dto.getProductPresentation().getCodMediaFiles()!= null) 
					bufferWriter.append( getTag("CODMediaFiles", (dto.getProductPresentation().getCodMediaFiles()))) ;						
				bufferWriter.append( "</Presentation>");
				
				if(dto.getParameters()!=null && dto.getParameters().size() > 0 ) {
					bufferWriter.append( "<CustomParameters>");
					for(Parameter dtoParameter : dto.getParameters()) {
						bufferWriter.append( "<Parameter name=\""+ dtoParameter.getName() +"\" value=\""+  dtoParameter.getValue() +"\"/>"); 				
					}				
					bufferWriter.append( "</CustomParameters>");breakline(bufferWriter);
				}

				
				if(dto.getProductTagsList()!=null && dto.getProductTagsList().size() > 0) {
					bufferWriter.append( "<Tags>");
					for(ProductTags dtoTags : dto.getProductTagsList()) {											
						bufferWriter.append("<Tag>"+  dtoTags.getProductTagName() +"</Tag>");breakline(bufferWriter);											
					}  
				    bufferWriter.append( "</Tags>");breakline(bufferWriter);
				}
				
				if(dto.getCategories()!=null && dto.getCategories().size() > 0 ) {				
					bufferWriter.append( "<Categories>");
					for(Category dtoCategory : dto.getCategories()) {
						bufferWriter.append( "<Category categoryID=\""+ dtoCategory.getCategoryID() +"\" sequence=\""+  dtoCategory.getSequence() +"\" displaySizeSelection=\""+ dtoCategory.getDisplaySizeSelection() +"\"/>"); 				
					}
					bufferWriter.append( "</Categories>");breakline(bufferWriter);				
				}
				
				writeSmartRoutingTags(bufferWriter, dto);
					
		}
				if(dto.getProductCategoryId().equals(ProductDBConstant.NINE) && dto.getProductClassId().equals(ProductDBConstant.SIX)) {
					if(dto.getFeeDeliveryId().equals(1l)) {
					if(dto.getFeeminthreshold()!=null) {
						bufferWriter.append("<Delivery id=\""+dto.getFeeName()+"\">"+"<Rules><Threshold><Minimum>"+dto.getFeeminthreshold()+"</Minimum></Threshold></Rules></Delivery>");breakline(bufferWriter);
					}else {
						bufferWriter.append("<Delivery id=\""+dto.getFeeName()+"\""+"/>");breakline(bufferWriter);
					}
					}else if (dto.getFeeDeliveryId().equals(4l)) {
						DecimalFormat decfmt = new DecimalFormat("#0.00");
						if(dto.getFeePercentage()!=null) {
						bufferWriter.append("<Delivery id=\""+dto.getFeeName()+"\">"+"<Rules><Percentage>"+decfmt.format(dto.getFeePercentage())+"</Percentage></Rules></Delivery>");breakline(bufferWriter);
						}else {
						bufferWriter.append("<Delivery id=\""+dto.getFeeName()+"\""+"/>");breakline(bufferWriter);
						}
					}else if(dto.getFeeDeliveryId().equals(2l) || dto.getFeeDeliveryId().equals(3l)||dto.getFeeDeliveryId().equals(5l)
							||dto.getFeeDeliveryId().equals(6l)) {
						bufferWriter.append("<Delivery id=\""+dto.getFeeName()+"\""+"/>");breakline(bufferWriter);
					}
				}
				
				if(mktName.toUpperCase().equals(ProductDBConstant.MKTNAME_FRANCE)) {
				bufferWriter.append("<FrenchReuseDeposit>");breakline(bufferWriter);
				DecimalFormat f = new DecimalFormat("#0.00");
				bufferWriter.append("<Amount type=\"EATIN\">");breakline(bufferWriter);
				if(dto.getReuseDepositeEating()!=null) {
					bufferWriter.append("<Value>"+f.format(dto.getReuseDepositeEating())+"</Value>");breakline(bufferWriter);
				}else {
					bufferWriter.append("<Value>"+"0.00"+"</Value>");breakline(bufferWriter);
				}
				bufferWriter.append("</Amount>");breakline(bufferWriter);
				
				bufferWriter.append("<Amount type=\"TAKEOUT\">");breakline(bufferWriter);
				if(dto.getReuseDepositeTakeout()!=null) {
					bufferWriter.append("<Value>"+f.format(dto.getReuseDepositeTakeout())+"</Value>");breakline(bufferWriter);
				}else {
					bufferWriter.append("<Value>"+"0.00"+"</Value>");breakline(bufferWriter);
				}
				bufferWriter.append("</Amount>");breakline(bufferWriter);
				bufferWriter.append("</FrenchReuseDeposit>");breakline(bufferWriter);
			}
				bufferWriter.append( "</Product>");breakline(bufferWriter);
			}
		}
		bufferWriter.append( "</ProductDb>");
		bufferWriter.close();
		if(LOGGER.isInfoEnabled()) LOGGER.info("end write Product DB XML");
		genDto.setGenerated(true);
		genDto.setAllProductsClone(allProductsClone);
		return genDto;	
	}
	
	private boolean validMenuItemCode(Product menuItem, String removeInactiveMI) {
		boolean isValid = true;
		if (menuItem != null) {
			if (removeInactiveMI.toUpperCase().equalsIgnoreCase("YES")){
				if (menuItem.getActive() == 0) {
					isValid = false;
				}
			}
		}
		return isValid;
	}
	
	private void writeSmartRoutingTags(PackageWriter bufferWriter, Product dto) throws IOException {
		 boolean writeSmartRoutingMainTag = checkSmartRoutingEmpty(dto);
		if (writeSmartRoutingMainTag) {
			bufferWriter.append( "<SmartRouting>");
			if(dto.getProductSmartRouting().getCytIngredientTyp()!=null) {
				bufferWriter.append( getTag("CytIngredient", dto.getProductSmartRouting().getCytIngredientTyp()));
			}
			if(dto.getProductSmartRouting().getCookTime()!=null) {
				bufferWriter.append( getTag("CookTime", dto.getProductSmartRouting().getCookTime().toString()) );
			}
			if(dto.getProductSmartRouting().getCytIngredientGroup()!=null) {
				bufferWriter.append( getTag("CytIngredientGroup", dto.getProductSmartRouting().getCytIngredientGroup().toString()));
			}
			if(dto.getProductSmartRouting().getDressPrepTime()!=null) {
				bufferWriter.append( getTag("DressPrepTime", dto.getProductSmartRouting().getDressPrepTime().toString()) );							
			}
			if(dto.getProductSmartRouting().getDeliverEarlyEnabled()!=null) {
				bufferWriter.append( getTag("DeliverEarlyEnabled", dto.getProductSmartRouting().getDeliverEarlyEnabled().toString()) );
			}
			if(dto.getProductSmartRouting().getCytItem()!=null) {
				bufferWriter.append( getTag("CytProduct", dto.getProductSmartRouting().getCytItem()));
			}
			if(dto.getProductSmartRouting().getCytGroupDisplayOrder()!=null && dto.getProductSmartRouting().getCytGroupDisplayOrder().size()>0 ) {
				bufferWriter.append( "<CytGroupDisplayOrder>");
				for(CytGroupDisplayOrder cytGroupDisplayOrder : dto.getProductSmartRouting().getCytGroupDisplayOrder()) {
					bufferWriter.append(getTag("Group",cytGroupDisplayOrder.getGroup().toString()));
				}
				bufferWriter.append( "</CytGroupDisplayOrder>");breakline(bufferWriter);
			}
			
			if(dto.getProductSmartRouting().getSmartRoutingTasks()!=null && dto.getProductSmartRouting().getSmartRoutingTasks().size()>0) {
				bufferWriter.append( "<Alerts>");
				for(SmartRoutingTask dtoSmartRoutingTask : dto.getProductSmartRouting().getSmartRoutingTasks()) {
					breakline(bufferWriter);
					String msgKey = "";
					if (dtoSmartRoutingTask.getMsgKey() != null) {
						msgKey = dtoSmartRoutingTask.getMsgKey();
					}
					bufferWriter.append( "<Alert key =\""+ msgKey +"\"" );
					String taskTime = "";
					if (dtoSmartRoutingTask.getTaskTime() != null) {
						taskTime = dtoSmartRoutingTask.getTaskTime().toString(); 
					}
					bufferWriter.append(" time = \""+ taskTime +"\"");
					String displayTime = "";
					if (dtoSmartRoutingTask.getDisplayTime() != null) {
						displayTime = dtoSmartRoutingTask.getDisplayTime().toString();						
					}
					bufferWriter.append(" displayTime = \""+ displayTime +"\"");
					String monitor = "";
					if (dtoSmartRoutingTask.getMonitor() != null) {
						monitor = dtoSmartRoutingTask.getMonitor();
					}
					bufferWriter.append(" monitor = \""+ monitor +"\"");
					bufferWriter.append(" />");
				}
				breakline(bufferWriter);
				bufferWriter.append( "</Alerts>");breakline(bufferWriter);
			}					
			bufferWriter.append( "</SmartRouting>");breakline(bufferWriter);
		}
	}
	

	private boolean checkSmartRoutingEmpty(Product dto) {
		if (dto.getProductSmartRouting() != null &&
				(dto.getProductSmartRouting().getCookTime()!=null || 
				dto.getProductSmartRouting().getDressPrepTime()!=null || 
				dto.getProductSmartRouting().getDeliverEarlyEnabled()!=null ||
				dto.getProductSmartRouting().getCytItem()!=null || 
				dto.getProductSmartRouting().getCytIngredientTyp()!=null ||
				dto.getProductSmartRouting().getCytIngredientGroup()!=null ||
				(dto.getProductSmartRouting().getSmartRoutingTasks() != null && 
				!dto.getProductSmartRouting().getSmartRoutingTasks().isEmpty()) ||
				(dto.getProductSmartRouting().getCytGroupDisplayOrder() != null && 
				!dto.getProductSmartRouting().getCytGroupDisplayOrder().isEmpty())
				)) {
			return true;
		}
		return false;
	}

	private void sortComponents(List<Component> components) {
		if (components != null) {
			java.util.Collections.sort(components,
					new MenuItemComponentsComparator());	
		}
				
	}


	private String getNonRequiredChoicesText(Product dto, Map<Long, Product> allProducts) {
		
		StringBuilder sb = new StringBuilder();
		if (dto.getComponents()!=null) 
		  { 
			  for (Component component :dto.getComponents()) 
			  { 
				  
			  	if (component.getNonRequiredChoices()!=null && Long.parseLong(component.getNonRequiredChoices())==1) 
			  	{
			  		
			  			sb.append(allProducts.get(component.getComponentProductId()).getProductCode()+"|");
					
				}
			  
			  } 
		  }
		
		return sb.toString();
		
	}


	private String getSubstitutionGrillText(Product dto, Map<Long, Product> allProducts) {
		StringBuilder sb = new StringBuilder();
		if (dto.getProductPosKvs() != null && dto.getProductPosKvs().getSourceSubstitutionItems().size() > 0) {
			List<GenericEntry> onlyActiveApprovedItems = new ArrayList<>();

			for (GenericEntry sourceSubstitutionItem : dto.getProductPosKvs().getSourceSubstitutionItems()) {

				GenericEntry temp = new GenericEntry();

				if (allProducts.containsKey(sourceSubstitutionItem.getCode())) {
					temp.setCode(allProducts.get(sourceSubstitutionItem.getCode()).getProductCode());
					onlyActiveApprovedItems.add(temp);
				}
			}
			
			sb.append(getTagList("SourceSubstitutionItems", onlyActiveApprovedItems, "|"));
		}
		
		if (dto.getProductPosKvs() != null && allProducts.containsKey(dto.getProductPosKvs().getTargetSubstitutionItem().getCode())) {
				GenericEntry temp = new GenericEntry();
				temp.setCode(allProducts.get(dto.getProductPosKvs().getTargetSubstitutionItem().getCode())
						.getProductCode());
				sb.append(getTag("TargetSubstitutionItems", temp));
		}
		
		return sb.toString();
	}

	private Long getLimitedTimeDiscountVal(String date, Long nodeID) throws Exception {
		Long limitedTimeDiscountVal = productDBDAO.getLimitedTimeDiscountVal(date, nodeID);
		return limitedTimeDiscountVal;
	}
	
	private String getAllowedTimeTags(List<Pair<String,String>> startEndPairs) {
		StringBuilder sb = new StringBuilder();
		startEndPairs.forEach(p -> {
			sb.append("<AllowedTime").append(" startTime=\"").append(p.getElement0()).append("\" endTime=\"").append(p.getElement1()).append("\"/>");
		});
		
		return sb.toString();
	}
	
	private String getTimeRestrictionsTagText(Product dto) throws IOException {
		
		TreeMap<WeekDays, List<Pair<String,String>>> weekWiseTimeRestrictions = new TreeMap<WeekDays, List<Pair<String,String>>>();
		if (dto.getTimeRestrictions() != null) {
			dto.getTimeRestrictions().forEach(e -> {
				weekWiseTimeRestrictions.put(e.getWeekDay(), e.getAllowedTimes());
				
			});
		}
		
		final StringBuilder sb = new StringBuilder();
		if (weekWiseTimeRestrictions != null && !weekWiseTimeRestrictions.isEmpty()) {
			weekWiseTimeRestrictions.forEach((k, v) -> {
				if(v != null && !v.isEmpty()) {
					sb.append("<TimeRestriction weekday=\"").append(k.toString()).append("\">").append(getAllowedTimeTags(v)).append("</TimeRestriction>");
					sb.append(System.lineSeparator());
				} else {
					sb.append("<TimeRestriction weekday=\"").append(k.toString()).append("\"/>");
					sb.append(System.lineSeparator());
				}
				
			});
		}
		
		if(sb.toString().isEmpty()) {
			return sb.toString();
		}
		
		return new StringBuilder().append("<TimeRestrictions>").append(sb).append("</TimeRestrictions>").toString();
	}
	
	private String getTag(final String tagName, final String customizedValue) {
		final StringBuffer tag = new StringBuffer("");
		if (customizedValue != null && !customizedValue.equals("0") ) {
			if (ObjectUtils.isFilled(customizedValue)) {
				tag.append("<" + tagName + FINAL_TAG_SYMBOL);
				tag.append(ObjectUtils.replaceSpecialCharacters(customizedValue));
				tag.append("</" + tagName + FINAL_TAG_SYMBOL);
				tag.append(System.lineSeparator());
			}
		}
		return tag.toString();
	}

	private String getTag(final String tagName, final GenericEntry customizedValue) {
		final StringBuffer tag = new StringBuffer("");
		if (customizedValue != null && customizedValue.getCode()!=0L) {
			tag.append("<" + tagName + FINAL_TAG_SYMBOL);
			if (customizedValue.getName() == null) {
				tag.append(customizedValue.getCode());
			} else {
				tag.append(ObjectUtils.replaceSpecialCharacters(customizedValue.getName()));
			}
			tag.append("</" + tagName + FINAL_TAG_SYMBOL);
			tag.append(System.lineSeparator());
		}
		return tag.toString();
	}

	private String getTagBoolean(final String tagName, long customizedValue) {
		final StringBuffer tag = new StringBuffer("");

		tag.append("<" + tagName + FINAL_TAG_SYMBOL);
		if (customizedValue == 1L) {
			tag.append("true");
		} else if (customizedValue == 3L){
			tag.append("both");
		}else {
			tag.append("false");
		}
		tag.append("</" + tagName + FINAL_TAG_SYMBOL);
		tag.append(System.lineSeparator());
		return tag.toString();
	}

	private String getTagList(final String tagName, final List<GenericEntry> list, String separator) {
		final StringBuffer tag = new StringBuffer("");
		if (list.size() > 0) {
			tag.append("<" + tagName + FINAL_TAG_SYMBOL);
			
			String tagValue = list.stream().map(e -> {
				return e.getName() !=null ? ObjectUtils.replaceSpecialCharacters(e.getName()) : String.valueOf(e.getCode());
			}).collect(Collectors.joining(separator));

			tag.append(tagValue);
			tag.append("</" + tagName + FINAL_TAG_SYMBOL);
			tag.append(System.lineSeparator());
		}
		return tag.toString();
	}

	private String getAttributeBoolean(final String attributeName, long customizedValue) {
		final StringBuffer tag = new StringBuffer("");

		tag.append(" "+ attributeName + "=");
		if (customizedValue == 1) {
			tag.append("\"true\"");
		} else {
			tag.append("\"false\"");
		}
		return tag.toString();
	}
	
	public Collection<Product> getXMLAttribute(final ProductDBRequest productDBRequest) throws Exception {
		// boolean enableSeamless=false;
		// Long xmlSeq_NUM=0L;
		Collection<Product> products = new ArrayList<>();
		Product product = new Product();
		// paramNames.append(ProductDBConstant.ENABLE_SEAMLESS).append(ProductDBConstant.SINGLE_QUOTE_CONSTANT).
		// append(ProductDBConstant.COMMA_CONSTANT).append(ProductDBConstant.SINGLE_QUOTE_CONSTANT);
		String xmlVersion = productDBDAO.getValuesFromGlobalParam(productDBRequest.getMktId(),
				ProductDBConstant.PACKAGE_DATA_PRODUCT_DB_XML_VERSION);
		String xmlSize = productDBDAO.getValuesFromGlobalParam(productDBRequest.getMktId(),
				ProductDBConstant.PRODUCT_DB_XML_SIZE);
		product.setXmlMaxSize(Long.parseLong(xmlSize));
		product.setXmlVersion(xmlVersion);

		products.add(product);
		return products;

	}

	public Map<String, List<Long>> discountNotAllowed(Long mktId,Long restId,Long restInstId) throws Exception {
		String discountBreak = productDBDAO.getValuesFromGlobalParam(mktId,ProductDBConstant.DISCOUNT_BREAKDOWN_ENABLED);
		List<Map<String, Object>> discountAllowList = null;
		if (discountBreak.toUpperCase().equalsIgnoreCase("TRUE")) {
			discountAllowList = productDBDAO.populateDiscountForTrue(mktId,restId,restInstId);
		}
		else if (discountBreak.toUpperCase().equalsIgnoreCase("FALSE")) {
			discountAllowList = productDBDAO.populateDiscountForFalse(mktId,restId,restInstId);
		}
		Map<String, List<Long>> discountMap = new HashMap<String, List<Long>>();
		if (null == discountAllowList || discountAllowList.size() > 0) {
			return discountMap;
		}

		List<Long> discountList;
		for (final Map<String, Object> discount : discountAllowList) {
			if (discount.get("PRD_ID") == null || discount.get("PRD_INST_ID") == null) {
				continue;
			}

			String key = discount.get("PRD_ID").toString() + discount.get("PRD_INST_ID");
			discountList = discountMap.get(key);
			if (discountList == null) {
				discountList = new ArrayList<Long>();
				discountMap.put(key, discountList);
			}

			if (discountBreak.toUpperCase().equalsIgnoreCase("TRUE")) {
				discountList.add(Long.parseLong(discount.get("DISC_ENABLED").toString()));
			} else if (discountBreak.toUpperCase().equalsIgnoreCase("FALSE")) {
				discountList.add(Long.parseLong(discount.get("DISC_DISABLED").toString()));
			}
		}
		return discountMap;
	}

	public boolean getkvsflagformarketDetails(final Long marketId) throws Exception {
		String kvsMonitorDesign = productDBDAO.getValuesFromGlobalParam(marketId,
				ProductDBConstant.KVS_MONITOR_REDESIGN_SUPPORT);

		return kvsMonitorDesign.toUpperCase().equalsIgnoreCase(ProductDBConstant.Y);
	}

	public Product getColorsandMediaValues(ProductDBRequest productDBRequest) throws Exception {
		Product product = new Product();
		RequestDTO dto = new RequestDTO();
		dto.setEffectiveDate(productDBRequest.getEffectiveDate());
		dto.setMarketId(productDBRequest.getMktId());
		dto.setNodeId(productDBRequest.getNodeId());
		Map<Long, Product> listofProductsByMaster = layeringProductService.getMergedProductsByRest(dto);
		if (listofProductsByMaster.containsKey(productDBRequest.getProductId())) {
			ProductPresentation productPresentation = listofProductsByMaster.get(productDBRequest.getProductId())
					.getProductPresentation();
			Long colorScreenKVSFont = productPresentation.getColorScreenKVSFont().getCode();
			Long colorScreenDisplay = productPresentation.getColorScreenDisplay().getCode();
			Long colorButtonBackground = productPresentation.getColorButtonBackground().getCode();
			Long colorButtonText = productPresentation.getColorButtonText().getCode();
			Long colorButtonBackgroundPressed = productPresentation.getColorButtonBackgroundPressed().getCode();
			Long colorButtonTextPressed = productPresentation.getColorButtonTextPressed().getCode();
			Long mediaImageCode = productPresentation.getImage().get(0).getCode();
			Long mediaSmallImageCode = productPresentation.getSmallImage().getCode();
			Long mediaGrillImageStringCode = productPresentation.getGrillImageString().getCode();
			Long mediaHandheldImageCode = productPresentation.getHandheldImage().getCode();
			Long mediaKioskImageCode = productPresentation.getKioskImage().getCode();
			Long mediaAlternativeCode = productPresentation.getAlternative().getCode();
			Long mediaSummaryMonitorImageCode = productPresentation.getSummaryMonitorImage().getCode();
			Long mediaCsoCircleImageCode = productPresentation.getCsoCircleImage().getCode();
			Long mediaCsoLargeImageCode = productPresentation.getCsoLargeImage().getCode();
			Long mediaCsoSmallImageCode = productPresentation.getCsoSmallImage().getCode();
			Long mediaCsoCartImageCode = productPresentation.getCsoCartImage().getCode();
			Long mediaCsoGrillImageCode = productPresentation.getCsoGrillImage().getCode();
			Long mediaCsoDimensionImageCode = productPresentation.getCsoDimensionImage().getCode();
			Long mediaCsoValueMealImageode = productPresentation.getCsoValueMealImage().getCode();
			Long mediaCytPreviewImageCode = productPresentation.getCytPreviewImage().getCode();
			Long mediaCytPreviewBottomImageCode = productPresentation.getCytPreviewBottomImage().getCode();
			String mediacodMediaFilesCode = productPresentation.getCodMediaFiles();
			Long mediaSoundFileCode = productPresentation.getSoundFile().getCode();

			Map<String, String> allColorsMap = new HashMap<>();
			final List<Map<String, Object>> allColorsList = getProductColorData(productDBRequest.getScheduleRequestID(), productDBRequest.getMktId());
			for (final Map<String, Object> colorsProductData : allColorsList) {
				allColorsMap.put((colorsProductData.get("colr_id").toString()),
						(colorsProductData.get("colr_na").toString()));
			}
			Map<String, String> allMediaMap = new HashMap<>();
			final List<Map<String, Object>> allMediaList = getProductMediaData(productDBRequest.getScheduleRequestID(), productDBRequest.getMktId());
			for (final Map<String, Object> mediaProductData : allMediaList) {
				allMediaMap.put((mediaProductData.get("mdia_id").toString()),
						(mediaProductData.get("mdia_file_na").toString()));
			}
			if (allColorsMap.containsKey(colorScreenKVSFont.toString())) {
				String colorName = allColorsMap.get(colorScreenKVSFont.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(colorScreenKVSFont);
				gen.setName(colorName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getColorScreenKVSFont(), getNullPropertyNames(gen));
			}
			if (allColorsMap.containsKey(colorScreenDisplay.toString())) {
				String colorName = allColorsMap.get(colorScreenDisplay.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(colorScreenDisplay);
				gen.setName(colorName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getColorScreenDisplay(), getNullPropertyNames(gen));
			}
			if (allColorsMap.containsKey(colorButtonBackground.toString())) {
				String colorName = allColorsMap.get(colorButtonBackground.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(colorButtonBackground);
				gen.setName(colorName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getColorButtonBackground(), getNullPropertyNames(gen));
			}
			if (allColorsMap.containsKey(colorButtonText.toString())) {
				String colorName = allColorsMap.get(colorButtonText.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(colorButtonText);
				gen.setName(colorName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getColorButtonText(), getNullPropertyNames(gen));
			}
			if (allColorsMap.containsKey(colorButtonBackgroundPressed.toString())) {
				String colorName = allColorsMap.get(colorButtonBackgroundPressed.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(colorButtonBackgroundPressed);
				gen.setName(colorName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getColorButtonBackgroundPressed(), getNullPropertyNames(gen));
			}
			if (allColorsMap.containsKey(colorButtonTextPressed.toString())) {
				String colorName = allColorsMap.get(colorButtonTextPressed.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(colorButtonTextPressed);
				gen.setName(colorName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getColorButtonTextPressed(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaImageCode.toString());
				List<GenericEntry> gen = new ArrayList<>();
				GenericEntry gen1 = new GenericEntry();
				gen1.setCode(mediaImageCode);
				gen1.setName(mediaName);
				gen.add(gen1);
				productPresentation.setImage(gen);
				BeanUtils.copyProperties(gen,
						listofProductsByMaster.get(productDBRequest.getProductId()).getProductPresentation().getImage(),
						getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaSmallImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaSmallImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaSmallImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getSmallImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaGrillImageStringCode.toString())) {
				String mediaName = allMediaMap.get(mediaGrillImageStringCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaGrillImageStringCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getGrillImageString(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaHandheldImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaHandheldImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaHandheldImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getHandheldImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaKioskImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaKioskImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaKioskImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getKioskImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaAlternativeCode.toString())) {
				String mediaName = allMediaMap.get(mediaAlternativeCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaAlternativeCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getAlternative(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaSummaryMonitorImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaSummaryMonitorImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaSummaryMonitorImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getSummaryMonitorImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaCsoCircleImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaCsoCircleImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaCsoCircleImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCsoCircleImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaCsoLargeImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaCsoLargeImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaCsoLargeImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCsoLargeImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaCsoSmallImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaCsoSmallImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaCsoSmallImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCsoSmallImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaCsoCartImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaCsoCartImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaCsoCartImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCsoCartImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaCsoGrillImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaCsoGrillImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaCsoGrillImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCsoGrillImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaCsoDimensionImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaCsoDimensionImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaCsoDimensionImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCsoDimensionImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaCsoValueMealImageode.toString())) {
				String mediaName = allMediaMap.get(mediaCsoValueMealImageode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaCsoValueMealImageode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCsoValueMealImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaCytPreviewImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaCytPreviewImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaCytPreviewImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCytPreviewImage(), getNullPropertyNames(gen));
			}
			if (allMediaMap.containsKey(mediaCytPreviewBottomImageCode.toString())) {
				String mediaName = allMediaMap.get(mediaCytPreviewBottomImageCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaCytPreviewBottomImageCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCytPreviewBottomImage(), getNullPropertyNames(gen));
			}
			
				BeanUtils.copyProperties(mediacodMediaFilesCode, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getCodMediaFiles(), getNullPropertyNames(mediacodMediaFilesCode));
			
			if (allMediaMap.containsKey(mediaSoundFileCode.toString())) {
				String mediaName = allMediaMap.get(mediaSoundFileCode.toString());
				GenericEntry gen = new GenericEntry();
				gen.setCode(mediaSoundFileCode);
				gen.setName(mediaName);
				BeanUtils.copyProperties(gen, listofProductsByMaster.get(productDBRequest.getProductId())
						.getProductPresentation().getSoundFile(), getNullPropertyNames(gen));
			}
			return listofProductsByMaster.get(productDBRequest.getProductId());
		} else {
			return product;
		}
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		java.util.Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
	
	
	public List<Map<String, Object>> getProductColorData(String scheduleRequestID,Long marketID) throws Exception{
		String colorCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ProductDBConstant.COLORS);
		List<Map<String, Object>> allColorsList = cacheService.getColorMediaData(colorCacheKey);
		if(allColorsList == null || (allColorsList !=null && allColorsList.isEmpty())) {
			LOGGER.info("Cache miss key {}", colorCacheKey);
			allColorsList = productDBDAO.getAllColorsList(marketID);
			cacheService.setColorMediaData(colorCacheKey, allColorsList, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return allColorsList;
	}
	
	
	public List<Map<String, Object>> getProductMediaData(String scheduleRequestID,Long marketID) throws Exception{
		String mediaCacheKey = KeyNameHelper.getRedisDataKey(scheduleRequestID,ProductDBConstant.MEDIA);
		List<Map<String, Object>> allMediaList = cacheService.getColorMediaData(mediaCacheKey);
		if(allMediaList == null || (allMediaList !=null && allMediaList.isEmpty())) {
			LOGGER.info("Cache miss key {}", mediaCacheKey);
			allMediaList = productDBDAO.getAllMediaList(marketID);
			cacheService.setColorMediaData(mediaCacheKey, allMediaList, Duration.ofMinutes(cacheTTLInMinutes));
		}
		return allMediaList;
	}
	private String getKVSRoutesText(Product dto, List<Map<String, Object>> routingSetsForPresentationAndProductIon) {
		List<Route> routesList = new ArrayList<>();
		PPG ppg = new PPG();
		String ppgValue = dto.getProductPosKvs().getProductionMenuItemGroup().getName();
		Long prdGrpID = dto.getProductPosKvs().getProductionMenuItemGroup().getCode();
		for (final Map<String, Object> routingList : routingSetsForPresentationAndProductIon) {
			if ((routingList.get(ProductDBConstant.PMI_GRP_ID).toString()).equals(prdGrpID.toString())) {
				Route route = new Route();
				route.setId(routingList.get(ProductDBConstant.RTE_ID).toString());
				routesList.add(route);
			}			
		}
		final StringBuilder sb = new StringBuilder();
		if(routesList.size()>0) {
		sb.append( "<KVSRoutes>");
		routesList.forEach(e -> {
			sb.append( "<Route id=\""+ e.getId().toString() + "\" />");      
			
		});
		sb.append( "</KVSRoutes>");
		}
		sb.append(getTag("PPG",ppgValue));
		if(sb.toString().isEmpty()) {
			return sb.toString();
		}
		return sb.toString();
	}
	private ProductDetails getpresentationDetails(Product dto,Map<Long, String> allColorsMap,Map<Long, String> allMediaMap) {
		ProductDetails productDetails=new ProductDetails();
		productDetails.setProductCode(dto.getProductCode());
		productDetails.setBgPressedColor( allColorsMap.get(dto.getProductPresentation().getColorButtonBackgroundPressed().getCode()));
		productDetails.setFgPressedColor( allColorsMap.get(dto.getProductPresentation().getColorButtonTextPressed().getCode()));
		productDetails.setFgNoramlColor( allColorsMap.get(dto.getProductPresentation().getColorButtonText().getCode()));
		productDetails.setBgNoramlColor( allColorsMap.get(dto.getProductPresentation().getColorButtonBackground().getCode()));
		productDetails.setCaptionName(stringOrEmpty(dto.getProductPresentation().getCaptionLine1())+" "+stringOrEmpty(dto.getProductPresentation().getCaptionLine2())
		 +" "+stringOrEmpty(dto.getProductPresentation().getCaptionLine3()));
		if (dto.getProductPresentation().getImage() != null &&
				!dto.getProductPresentation().getImage().isEmpty()) {
			productDetails.setImageName( allMediaMap.get(dto.getProductPresentation().getImage().get(0).getCode()));	
		}
		productDetails.setActive(dto.getActive());
		productDetails.setApprovalStatus(dto.getApprovalStatus());
		productDetails.setAuxiliaryMenuItem(dto.getAuxiliaryMenuItem());
		productDetails.setProdPRGGroup(dto.getProductPosKvs().getProductionMenuItemGroup().getCode());
		return productDetails;
	}
	 private String stringOrEmpty(Object value) {
			return value != null? value.toString() : "";

			}
	private void breakline(PackageWriter bufferWriter) throws IOException {
		bufferWriter.append(System.lineSeparator());
	}
	private String smrtrmndrGroupName(Long smrtrmdr) throws Exception {
		String smrtrmndrGroupName = productDBDAO.getSmrtRemindrGroupName(smrtrmdr);
		return ObjectUtils.replaceSpecialCharacters(smrtrmndrGroupName);
		
	}
}
