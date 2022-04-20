package com.rfm.packagegeneration.dao;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.rfm.packagegeneration.dto.BunBufferDetails;
import com.rfm.packagegeneration.dto.BusinessLimits;
import com.rfm.packagegeneration.dto.CategoryHours;
import com.rfm.packagegeneration.dto.ChargeRules;
import com.rfm.packagegeneration.dto.ColorDb;
import com.rfm.packagegeneration.dto.CustomDayPart;
import com.rfm.packagegeneration.dto.CategoryDetails;
import com.rfm.packagegeneration.dto.DayPartSet;
import com.rfm.packagegeneration.dto.DeliveryOrderingHours;
import com.rfm.packagegeneration.dto.Deposit;
import com.rfm.packagegeneration.dto.DiscountTable;
import com.rfm.packagegeneration.dto.Fee;
import com.rfm.packagegeneration.dto.FlavourSet;
import com.rfm.packagegeneration.dto.HotBusinessLimit;
import com.rfm.packagegeneration.dto.IngredientGroupDetails;
import com.rfm.packagegeneration.dto.KSGroup;
import com.rfm.packagegeneration.dto.KitchenGroup;
import com.rfm.packagegeneration.dto.LargeOrderRules;
import com.rfm.packagegeneration.dto.LanguageDetails;
import com.rfm.packagegeneration.dto.Localization;
import com.rfm.packagegeneration.dto.MinOrderRules;
import com.rfm.packagegeneration.dto.NameTable;
import com.rfm.packagegeneration.dto.Notification;
import com.rfm.packagegeneration.dto.PluLargeOrderRules;
import com.rfm.packagegeneration.dto.PPGGroup;
import com.rfm.packagegeneration.dto.PopulateDrinkVol;
import com.rfm.packagegeneration.dto.PrdKVSRoute;
import com.rfm.packagegeneration.dto.ProductDetails;
import com.rfm.packagegeneration.dto.ProductGroup;
import com.rfm.packagegeneration.dto.Production;
import com.rfm.packagegeneration.dto.PromotionGroupDetail;
import com.rfm.packagegeneration.dto.PromotionImages;
import com.rfm.packagegeneration.dto.ProviderDetails;
import com.rfm.packagegeneration.dto.SetIds;
import com.rfm.packagegeneration.dto.SizeSelection;
import com.rfm.packagegeneration.dto.StoreDetails;
import com.rfm.packagegeneration.dto.StoreHours;
import com.rfm.packagegeneration.dto.StorePromotionDiscounts;
import com.rfm.packagegeneration.dto.TaxChain;
import com.rfm.packagegeneration.dto.TaxDefinition;
import com.rfm.packagegeneration.dto.TaxType;
import com.rfm.packagegeneration.dto.TaxTypeId;
import com.rfm.packagegeneration.dto.TenderType;
import com.rfm.packagegeneration.dto.VolumeTable;
import com.rfm.packagegeneration.hikari.Wizard;
import com.rfm.packagegeneration.logging.annotation.TrackedMethod;
import com.rfm.packagegeneration.utility.DateUtility;
import com.rfm.packagegeneration.utility.PackageGenDateUtility;
import com.rfm.packagegeneration.utility.StringHelper;

@Repository
public class StoreDBDAO extends CommonDAO {
	@Autowired
	private NamesDBDAO namesDBDAO;
	
	private static final Logger LOGGER = LogManager.getLogger("StoreDBDAO");

	@Value("${datasource.used.query}")
	private String dataSourceQuery;

	public List<StoreHours> getStoreHours(Long mktId, Long restId, Long restinstId) throws Exception {
		final String query = getDaoXml("getStoreHours", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		paramMap.put("restId", restId);
		paramMap.put("restinstId", restinstId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("StoreDBDAO::getStoreHours()::size ->" + listOfResults.size());
		List<StoreHours> hoursList = null;
		if (listOfResults != null && listOfResults.size() > 0) {
			hoursList = new ArrayList<>();
			for (final Map<String, Object> data : listOfResults) {
				StoreHours storeHours = new StoreHours();
				if (data.get("restPresentAreaCode") != null) {
					storeHours.setRestpresentareacode(data.get("restPresentAreaCode").toString());
				}
				if (data.get("restPresentArea") != null) {
					storeHours.setRestpresentarea(data.get("restPresentArea").toString());
				}
				if (data.get("pricecode_conv") != null) {
					storeHours.setPricecodeconv(data.get("pricecode_conv").toString());
				}
				if (data.get("sun_strt_tm") != null) {
					storeHours.setSunStartTime(data.get("sun_strt_tm").toString());
				}
				if (data.get("sun_end_tm") != null) {
					storeHours.setSunEndTime(data.get("sun_end_tm").toString());
				}
				if (data.get("mon_strt_tm") != null) {
					storeHours.setMonStartTime(data.get("mon_strt_tm").toString());
				}
				if (data.get("mon_end_tm") != null) {
					storeHours.setMonEndTime(data.get("mon_end_tm").toString());
				}
				if (data.get("tue_strt_tm") != null) {
					storeHours.setTueStartTime(data.get("tue_strt_tm").toString());
				}
				if (data.get("tue_end_tm") != null) {
					storeHours.setTueEndTime(data.get("tue_end_tm").toString());
				}
				if (data.get("wed_strt_tm") != null) {
					storeHours.setWedStartTime(data.get("wed_strt_tm").toString());
				}
				if (data.get("wed_end_tm") != null) {
					storeHours.setWedEndTime(data.get("wed_end_tm").toString());
				}
				if (data.get("thu_strt_tm") != null) {
					storeHours.setThuStartTime(data.get("thu_strt_tm").toString());
				}
				if (data.get("thu_end_tm") != null) {
					storeHours.setThuEndTime(data.get("thu_end_tm").toString());
				}
				if (data.get("fri_strt_tm") != null) {
					storeHours.setFriStartTime(data.get("fri_strt_tm").toString());
				}
				if (data.get("fri_end_tm") != null) {
					storeHours.setFriEndTime(data.get("fri_end_tm").toString());
				}
				if (data.get("sat_strt_tm") != null) {
					storeHours.setSatStartTime(data.get("sat_strt_tm").toString());
				}
				if (data.get("sat_end_tm") != null) {
					storeHours.setSatEndTime(data.get("sat_end_tm").toString());
				}
				hoursList.add(storeHours);
			}
		}
		return hoursList;
	}

	public StoreDetails getStoreDetails(Long restId, Long restInstId, Long marketId) throws Exception {
		final String query = getDaoXml("getStoreDetails", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getStoreDetails()");
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("restId", restId);
		paramMap.put("restInstId", restInstId);
		paramMap.put("marketId", marketId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("StoreDBDAO::getStoreDetails()::results ->" + listOfResults.size());

		StoreDetails storeDetails = null;
		if (listOfResults != null && listOfResults.size() > 0) {
			storeDetails = new StoreDetails();

			for (final Map<String, Object> data : listOfResults) {
				if (data.get("rfmId") != null)
					storeDetails.setRfmId(Long.valueOf(data.get("rfmId").toString()));
				if (data.get("companyId") != null)
					storeDetails.setCompanyId(Long.valueOf(data.get("companyId").toString()));
				if (data.get("storeId") != null)
					storeDetails.setStoreId(Long.valueOf(data.get("storeId").toString()));
				if (data.get("storeLegacyId") != null)
					storeDetails.setStoreLegacyId(Long.valueOf(data.get("storeLegacyId").toString()));
				if (data.get("storeAddress") != null)
					storeDetails.setStoreAddress(
							StringHelper.replaceSpecialCharacters(data.get("storeAddress").toString()));
				if (data.get("storeZipCode") != null)
					storeDetails.setStoreZipCode(data.get("storeZipCode").toString());
				if (data.get("city") != null)
					storeDetails.setCity(StringHelper.replaceSpecialCharacters(data.get("city").toString()));
				if (data.get("state") != null)
					storeDetails.setState(data.get("state").toString());
				if (data.get("country") != null)
					storeDetails.setCountry(data.get("country").toString());
				if (data.get("email") != null)
					storeDetails.setEmail(StringHelper.replaceSpecialCharacters(data.get("email").toString()));
				if (data.get("homePage") != null)
					storeDetails.setHomePage(StringHelper.replaceSpecialCharacters(data.get("homePage").toString()));
				if (data.get("storePhone") != null)
					storeDetails.setStorePhone(data.get("storePhone").toString());
				if (data.get("helpDeskInfo") != null)
					storeDetails.setHelpDeskInfo(
							StringHelper.replaceSpecialCharacters(data.get("helpDeskInfo").toString()));
				if (data.get("sellPoints") != null)
					storeDetails.setStoreType(mapStoreType(data.get("sellPoints").toString()));
				if (data.get("ownerShipType") != null)
					storeDetails.setOwnershipType(
							StringHelper.replaceSpecialCharacters(data.get("ownerShipType").toString()));

				if (data.get("latitude") != null)
					storeDetails.setStoreLatitude(data.get("latitude").toString().trim());
				if (data.get("longitude") != null)
					storeDetails.setStoreLongitude(data.get("longitude").toString().trim());

			}
		}
		return storeDetails;
	}
	
	public List<BunBufferDetails> getBunBufferDetails(Long nodeId,Long marketId) throws Exception {
		final String query = getDaoXml("getBunBufferDetails", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getBunBufferDetails()");
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("nodeId",  nodeId);
		paramMap.put("marketId",  marketId);
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("StoreDBDAO::getBunBufferDetails()::results ->"+listOfResults.size());
		List<BunBufferDetails> bufferDetailsList=null;
		if (listOfResults != null && listOfResults.size() > 0) {
			bufferDetailsList=new ArrayList<BunBufferDetails>();
			for (final Map<String, Object> data : listOfResults) {
				BunBufferDetails bufferDetails=new BunBufferDetails();
				bufferDetails.setBunId(Long.valueOf(data.get("bunId").toString()));
				bufferDetails.setBunName(data.get("bunName").toString());
				bufferDetails.setDisplayPriority(data.get("disp").toString());
				bufferDetails.setQuantityToBuffer(data.get("qtb").toString());
				bufferDetails.setBufferTime(data.get("bufferTime").toString());
				bufferDetails.setStatus(data.get("status").toString());
				bufferDetailsList.add(bufferDetails);
			}
		}
		return bufferDetailsList;
	}

	private List<String> mapStoreType(String string) {
		return new ArrayList<String>(Arrays.stream(string.split(",")).collect(Collectors.toList()));
	}
	
	public List<IngredientGroupDetails> getIngredientGroupDetails(Long marketId,Long nodeId) throws Exception {
		final String query = getDaoXml("getIngriedientGroupDetails", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getIngriedientGroupDetails()");
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId",  marketId);
		paramMap.put("nodeId",  nodeId);
		List<Map<String, Object>> listOfResults  = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("StoreDBDAO::getIngriedientGroupDetails()::results ->"+listOfResults.size());
		List<IngredientGroupDetails> ingredientGroupDetailsList=null;
		if (listOfResults != null && listOfResults.size() > 0) {
			ingredientGroupDetailsList=new ArrayList<IngredientGroupDetails>();
			for (final Map<String, Object> data : listOfResults) {				
				ingredientGroupDetailsList.add(mapIngredientGroupDetails(data));
			}
		}
		return ingredientGroupDetailsList;
		
	}
	
	public IngredientGroupDetails mapIngredientGroupDetails(Map<String, Object> data) {
		IngredientGroupDetails ingredientDetails = new IngredientGroupDetails();
		if (data.get("INGR_GRP_NAME") != null) {
			ingredientDetails.setName(data.get("INGR_GRP_NAME").toString());
		}
		if (data.get("MUTUALLY_EXCLUSIVE") != null) {
			ingredientDetails.setMutuallyExclusive(data.get("MUTUALLY_EXCLUSIVE").toString().toLowerCase());
		}
		if (data.get("MIN_QTY") != null && Long.valueOf(data.get("MIN_QTY").toString()) != 0) {
			ingredientDetails.setMinQuantity(data.get("MIN_QTY").toString());
		}
		if (data.get("MAX_QTY") != null) {
			ingredientDetails.setMaxQuantity(data.get("MAX_QTY").toString());
		}
		if (data.get("CHARGETHRESHOLD") != null && Long.valueOf(data.get("CHARGETHRESHOLD").toString()) != 0) {
			ingredientDetails.setChargeThreshold(data.get("CHARGETHRESHOLD").toString());
		}
		return ingredientDetails;
	}

	public List<DayPartSet> getDayPartSet(Long restId, Long restInstId, String effectiveDate, Long nodeId)
			throws Exception {
		final String query = getDaoXml("getDayPartParentSet", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getDayPartSet()");
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("rest_id", restId);
		paramMap.put("rest_inst_id", restInstId);
		paramMap.put("eff_dt", effectiveDate);
		paramMap.put("node_id", nodeId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		DayPartSet dayPartSet = null;
		String parentSetId = null;
		String customSetId = null;
		List<DayPartSet> daypartList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getDayPartSet()::results ->" + listOfResults.size());
		if (listOfResults != null && listOfResults.size() > 0) {
			dayPartSet = new DayPartSet();

			for (final Map<String, Object> data : listOfResults) {

				if (data.get("pren_set_id") != null) {
					parentSetId = data.get("pren_set_id").toString();
				}
				if (data.get("cusm_set_id") != null) {
					customSetId = data.get("cusm_set_id").toString();
				}

			}

			final String queryDayPart = getDaoXml("getDayPartSet", DAOResources.STORE_DAO);
			LOGGER.info("StoreDBDAO::getDayPartSet()");
			final Map<String, Object> paramMapForDayPart = new HashMap<>();
			LOGGER.info("Values " + parentSetId + "-" + customSetId);
			if (parentSetId != null) {
				paramMapForDayPart.put("pren_set_id", Integer.parseInt(parentSetId));
			}
			if (customSetId != null) {
				paramMapForDayPart.put("cust_set_id", Integer.parseInt(customSetId));
			}

			
			if (paramMapForDayPart.get("pren_set_id") != null && paramMapForDayPart.get("cust_set_id") != null) {
				
				List<Map<String, Object>> listOfResultsObj = Wizard.queryForList(dataSourceQuery, queryDayPart,
						paramMapForDayPart);

				LOGGER.info("StoreDBDAO::getDayPartSet()::results ->" + listOfResultsObj.size());
				if (listOfResultsObj != null && listOfResultsObj.size() > 0) {

					for (final Map<String, Object> data : listOfResultsObj) {
						dayPartSet = new DayPartSet();
						if (data.get("dypt_prd_na") != null)
							dayPartSet.setDayPartName(data.get("dypt_prd_na").toString());

						if (data.get("mon_strt_tm") != null)
							dayPartSet.setMonStartTime(data.get("mon_strt_tm").toString());
						if (data.get("mon_end_tm") != null)
							dayPartSet.setMonEndTime(data.get("mon_end_tm").toString());
						if (data.get("tue_strt_tm") != null)
							dayPartSet.setTueStartTime(data.get("tue_strt_tm").toString());
						if (data.get("tue_end_tm") != null)
							dayPartSet.setTueEndTime(data.get("tue_end_tm").toString());
						if (data.get("wed_strt_tm") != null)
							dayPartSet.setWedStartTime(data.get("wed_strt_tm").toString());
						if (data.get("wed_end_tm") != null)
							dayPartSet.setWedEndTime(data.get("wed_end_tm").toString());
						if (data.get("thu_strt_tm") != null)
							dayPartSet.setThuStartTime(data.get("thu_strt_tm").toString());
						if (data.get("thu_end_tm") != null)
							dayPartSet.setThuEndTime(data.get("thu_end_tm").toString());
						if (data.get("fri_strt_tm") != null)
							dayPartSet.setFriStartTime(data.get("fri_strt_tm").toString());
						if (data.get("fri_end_tm") != null)
							dayPartSet.setFriEndTime(data.get("fri_end_tm").toString());
						if (data.get("sat_strt_tm") != null)
							dayPartSet.setSatStartTime(data.get("sat_strt_tm").toString());
						if (data.get("sat_end_tm") != null)
							dayPartSet.setSatEndTime(data.get("sat_end_tm").toString());
						if (data.get("sun_strt_tm") != null)
							dayPartSet.setSunStartTime(data.get("sun_strt_tm").toString());
						if (data.get("sun_end_tm") != null)
							dayPartSet.setSunEndTime(data.get("sun_end_tm").toString());
						daypartList.add(dayPartSet);

					}

				}

			} else {
				return daypartList;
			}

		} else {
			return daypartList;
		}
		return daypartList;
	}

	public List<Fee> getFeeDetails(Long marketId, String effDate, Long prenSetId, Long cusmSetId) throws Exception {

		final String query = getDaoXml("getFeeDetails", DAOResources.STORE_DAO);

		final Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("mkt_id", marketId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effDate));
		paramMap.put("pren_set_id", prenSetId);
		paramMap.put("chld_set_id", cusmSetId);

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);

		List<Fee> allFeeDetail = new ArrayList<Fee>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				allFeeDetail.add(mapFee(data));
			}
		}
		return allFeeDetail;
	}

	private Fee mapFee(Map<String, Object> data) {
		Fee fee = new Fee();

		if (data.get("feeDescription") != null) {
			fee.setFeeName(data.get("feeDescription").toString());
		}

		if (data.get("feeCode") != null) {
			fee.setFeeId(Long.valueOf(data.get("feeCode").toString()));
		}

		if (data.get("type") != null) {
			fee.setFeeType(data.get("type").toString());
		}

		if (data.get("eatin_value") != null) {
			fee.setEatinVal(Double.valueOf(data.get("eatin_value").toString()));
		}

		if (data.get("tkut_value") != null) {
			fee.setTkutVal(Double.valueOf(data.get("tkut_value").toString()));
		}

		if (data.get("oth_val") != null) {
			fee.setOthVal(Double.valueOf(data.get("oth_val").toString()));
		}

		if (data.get("eatin_tax_cd") != null) {
			fee.setEatinTaxCode(Long.valueOf(data.get("eatin_tax_cd").toString()));
		}

		if (data.get("tkut_tax_cd") != null) {
			fee.setTkutTaxCode(Long.valueOf(data.get("tkut_tax_cd").toString()));
		}

		if (data.get("oth_tax_cd") != null) {
			fee.setOthTaxCode(Long.valueOf(data.get("oth_tax_cd").toString()));
		}

		if (data.get("eating_tax_rule") != null) {
			fee.setEatinTaxRule(Long.valueOf(data.get("eating_tax_rule").toString()));
		}

		if (data.get("tkut_tax_rule") != null) {
			fee.setTkutTaxRule(Long.valueOf(data.get("tkut_tax_rule").toString()));
		}

		if (data.get("oth_tax_rule") != null) {
			fee.setOthTaxRule(Long.valueOf(data.get("oth_tax_rule").toString()));
		}

		if (data.get("eatin_tax_entr") != null) {
			fee.setEatinTaxEntry(Long.valueOf(data.get("eatin_tax_entr").toString()));
		}

		if (data.get("tkut_tax_entr") != null) {
			fee.setTkutTaxEntry(Long.valueOf(data.get("tkut_tax_entr").toString()));
		}

		if (data.get("OTH_TAX_ENTR") != null) {
			fee.setOthTaxEntry(Long.valueOf(data.get("OTH_TAX_ENTR").toString()));
		}

		if (data.get("status") != null) {
			fee.setStatus((data.get("status").toString()));
		}

		if (data.get("eatin_tax_chn_entr") != null) {
			fee.setEatinTaxChainEntry(Long.valueOf(data.get("eatin_tax_chn_entr").toString()));
		}

		if (data.get("tkut_tax_chn_entr") != null) {
			fee.setTakeOutTaxChainEntry(Long.valueOf(data.get("tkut_tax_chn_entr").toString()));
		}

		if (data.get("oth_tax_chn_entr") != null) {
			fee.setOtherTaxChainEntry(Long.valueOf(data.get("oth_tax_chn_entr").toString()));
		}
		return fee;
	}

	public SetIds getFeeSetIds(Long restId, Long restInstId, Long mktId, String effectiveDate, Long nodeId)
			throws Exception {

		SetIds setIds = new SetIds();

		long parentSetId = 0;
		long cusmSetId = 0;
		final String query = getDaoXml("getFeeSetIds", DAOResources.STORE_DAO);

		final Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("mktid", mktId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("rest_id", restId);
		paramMap.put("rest_inst_id", restInstId);
		paramMap.put("node_id", nodeId);

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if (data.get("pren_set_id") != null)
					parentSetId = Long.valueOf(data.get("pren_set_id").toString());

				if (data.get("cusm_set_id") != null)
					cusmSetId = Long.valueOf(data.get("cusm_set_id").toString());
			}
			setIds.setPrenSetId(parentSetId);
			setIds.setCusmSetId(cusmSetId);
		}
		return setIds;
	}
	
	public List<FlavourSet> getFlavourSet(Long restId,Long  restInstId,Long mktId) throws Exception{
				
		final String query = getDaoXml("getFlavourSet", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getFlavourSet()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("mkt_id", mktId);
		paramMap.put("rest_id", restId);
		paramMap.put("rest_inst_id", restInstId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		FlavourSet flavourSet = null;
		List<FlavourSet> flavourSetList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getFlavourSet()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 
				
			 for (final Map<String, Object> data : listOfResults) {
				 flavourSet = new FlavourSet();
					if (data.get("NOZZLE_ID") != null)
						flavourSet.setNozzleId(Long.valueOf(data.get("NOZZLE_ID").toString()));

					if (data.get("FLAV_NA") != null)
						flavourSet.setFlavname(String.valueOf(data.get("FLAV_NA").toString()));
					flavourSetList.add(flavourSet);
				}
			}
		  return flavourSetList;
		}
	
	


	

	public List<ProductGroup> getProductGroupsDetails(Long mktId) throws Exception {
		final String query = getDaoXml("getMIGroupDetails", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mkt_id", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<ProductGroup> allProductGroupsDetail = new ArrayList<ProductGroup>();
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				allProductGroupsDetail.add(mapProductGroups(data));
			}
		}
		return allProductGroupsDetail;
	}

	private ProductGroup mapProductGroups(Map<String, Object> data) {
		ProductGroup miProductGroup=new ProductGroup();
		
		if (data.get("MI_GRP_NA") != null) {
			miProductGroup.setGrpName(data.get("MI_GRP_NA").toString());
		}
		if (data.get("MI_GRP_CD") != null) {
			miProductGroup.setGrpCode(Long.valueOf(data.get("MI_GRP_CD").toString()));
		}
		if (data.get("MI_GRP_ID")!=null) {
			miProductGroup.setGrpId(Long.valueOf(data.get("MI_GRP_ID").toString()));
		}
		if (data.get("MI_GRP_TYP")!=null) {
			miProductGroup.setGrpType(Long.valueOf(data.get("MI_GRP_TYP").toString()));
		}
		if (data.get("STUS")!=null) {
			if (Long.valueOf(data.get("STUS").toString())>0) {
				miProductGroup.setStatus("ACTIVE");
			}else {
				miProductGroup.setStatus("INACTIVE");
			}
		}
		if (data.get("miGrpTypLkpVal")!=null) {
			miProductGroup.setGrpTypName(data.get("miGrpTypLkpVal").toString());
		}
		if (data.get("promo_grp_code")!=null) {
			miProductGroup.setPromoGrpCode(Long.parseLong(data.get("promo_grp_code").toString()));
		}
		return miProductGroup;
	}

	

	

	public SetIds getDepositSetIds(Long restId, Long restInstId, Long mktId, String effectiveDate, Long nodeId) throws Exception {
		SetIds setIds = new SetIds();

		long parentSetId = 0;
		long cusmSetId = 0;
		final String query = getDaoXml("getDepositSetIds", DAOResources.STORE_DAO);

		final Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("mktid", mktId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("rest_id", restId);
		paramMap.put("rest_inst_id", restInstId);
		paramMap.put("node_id", nodeId);

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if (data.get("pren_set_id") != null)
					parentSetId = Long.valueOf(data.get("pren_set_id").toString());

				if (data.get("cusm_set_id") != null)
					cusmSetId = Long.valueOf(data.get("cusm_set_id").toString());
			}
			setIds.setPrenSetId(parentSetId);
			setIds.setCusmSetId(cusmSetId);
		}
		return setIds;
	}

	public List<Deposit> getDepositDetails(Long mktId, String effectiveDate, Long prenSetId, Long cusmSetId) throws Exception {
		final String query = getDaoXml("getDepositDetails", DAOResources.STORE_DAO);

		final Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("mkt_id", mktId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("pren_set_id", prenSetId);
		paramMap.put("chld_set_id", cusmSetId);

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);

		List<Deposit> allDepositDetail = new ArrayList<Deposit>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				allDepositDetail.add(mapDeposit(data));
			}
		}
		return allDepositDetail;
	}

	private Deposit mapDeposit(Map<String, Object> data) {
		Deposit deposit=new Deposit();
		
		if (data.get("status")!=null) {
			deposit.setStatus(data.get("status").toString());
		}
		
		if (data.get("DepositDescription") != null) {
			deposit.setDepositName(data.get("DepositDescription").toString());
		}

		if (data.get("depositCode") != null) {
			deposit.setDepositId(Long.valueOf(data.get("depositCode").toString()));
		}

		if (data.get("type") != null) {
			deposit.setDepositType(data.get("type").toString());
		}

		if (data.get("eatin_value") != null) {
			deposit.setEatinValue(Double.parseDouble(data.get("eatin_value").toString()));
		}
		if (data.get("tkut_value") != null) {
			deposit.setTakeOutValue(Double.parseDouble(data.get("tkut_value").toString()));
		}
		if (data.get("oth_val") != null) {
			deposit.setOtherValue(Double.parseDouble(data.get("oth_val").toString()));
		}
		
		
		if (data.get("eatin_tax_cd")!=null) {
			deposit.setEatinTaxCode(Long.parseLong(data.get("eatin_tax_cd").toString()));
		}
		if (data.get("tkut_tax_cd")!=null) {
			deposit.setTakeOutTaxCode(Long.parseLong(data.get("tkut_tax_cd").toString()));
		}
		if (data.get("oth_tax_cd")!=null) {
			deposit.setOtherTaxCode(Long.parseLong(data.get("oth_tax_cd").toString()));
		}

		
		
		if (data.get("eating_tax_rule")!=null) {
			deposit.setEatinTaxRule(Long.parseLong(data.get("eating_tax_rule").toString()));
		}
		if (data.get("tkut_tax_rule")!=null) {
			deposit.setTakeOutTaxRule(Long.parseLong(data.get("tkut_tax_rule").toString()));
		}
		if (data.get("oth_tax_rule")!=null) {
			deposit.setOtherTaxRule(Long.parseLong(data.get("oth_tax_rule").toString()));
		}
		
		
		
		if (data.get("eatin_tax_entr")!=null) {
			deposit.setEatinTaxTypeEntry(Long.parseLong(data.get("eatin_tax_entr").toString()));
		}
		if (data.get("tkut_tax_entr")!=null) {
			deposit.setTakeOutTaxTypeEntry(Long.parseLong(data.get("tkut_tax_entr").toString()));
		}
		if (data.get("oth_tax_entr")!=null) {
			deposit.setOtherTaxTypeEntry(Long.parseLong(data.get("oth_tax_entr").toString()));
		}
		
		
		if (data.get("eatin_tax_chn_entr")!=null) {
			deposit.setEatinTaxChainEntry(Long.parseLong(data.get("eatin_tax_chn_entr").toString()));
		}
		if (data.get("tkut_tax_chn_entr")!=null) {
			deposit.setTakeOutTaxChainEntry(Long.parseLong(data.get("tkut_tax_chn_entr").toString()));
		}
		if (data.get("oth_tax_chn_entr")!=null) {
			deposit.setOtherTaxChainEntry(Long.parseLong(data.get("oth_tax_chn_entr").toString()));
		}
		
		
		return deposit;
	}

	public List<BusinessLimits> getBusinessLimit(Long restId, Long restInstId, String effDate,Long marketId) throws Exception {
		Long asnSetType = 6011L;
		final List< Map< String, Object > > localizationSets = getLocalizationSets(restId,restInstId,asnSetType,marketId);
		Long parentSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != localizationSets.get(0).get("PREN_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		// Fetching custSetId from the localizationsets
		Long childSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("CUSM_SET_ID") != null) {
			childSetId = null != localizationSets.get(0).get("CUSM_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		if(parentSetId==childSetId) {
			childSetId = -1L;
		}
		final String query = getDaoXml("getBusinessLimit", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("childSetId", childSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<BusinessLimits> businessLimits = new ArrayList<BusinessLimits>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				businessLimits.add(mapBusinessLimits(data));
			}
		}
		return businessLimits;
	}
	private BusinessLimits mapBusinessLimits(Map<String, Object> data) {
		BusinessLimits limits = new BusinessLimits();
		if (data.get("auto_rcpt_prnt_am") != null)limits.setAutoReceiptPrintAmount(data.get("auto_rcpt_prnt_am").toString());
		if (data.get("Blok_Tm") != null)limits.setBlockTime(data.get("Blok_Tm").toString());
		if (data.get("WKDY_BRKF_STRT_TM") != null)limits.setBreakfastStartTimeWeekDay(data.get("WKDY_BRKF_STRT_TM").toString());
		if (data.get("wknd_brkf_strt_tm") != null)limits.setBreakfastStartTimeWeekEnd(data.get("wknd_brkf_strt_tm").toString());
		if (data.get("wkdy_brkf_stop_tm") != null)limits.setBreakfastStopTimeWeekDay(data.get("wkdy_brkf_stop_tm").toString());
		if (data.get("wknd_brkf_stop_tm") != null)limits.setBreakfastStopTimeWeekEnd(data.get("wknd_brkf_stop_tm").toString());
		if (data.get("BP_INDV_DISC_AM_LMT") != null)limits.setBypassIndividualDiscountAmountLimit(data.get("BP_INDV_DISC_AM_LMT").toString());
		if (data.get("BP_PRM_PI_QA_LMT") != null)limits.setBypassPromoPerItemQuantityLimit(data.get("BP_PRM_PI_QA_LMT").toString());
		if (data.get("CPN_AM_LMT") != null)limits.setCouponAmountLimit(data.get("CPN_AM_LMT").toString());
		if (data.get("CPN_QT_LMT") != null)limits.setCouponQuantityLimit(data.get("CPN_QT_LMT").toString());
		if (data.get("CREW_PROMO_DAILY_LIMIT") != null)limits.setCrewPromoDailyLimit(data.get("CREW_PROMO_DAILY_LIMIT").toString());
		if (data.get("CREW_TRED_DAILY_LIMIT") != null)limits.setCrewTRedDailyLimit(data.get("CREW_TRED_DAILY_LIMIT").toString());
		if (data.get("DAILY_LIMIT_EXCLUSION_LIST") != null)limits.setDailyLimitExclusionList(data.get("DAILY_LIMIT_EXCLUSION_LIST").toString());
		if (data.get("DISC_AM_LMT") != null)limits.setDiscountAmountLimit(data.get("DISC_AM_LMT").toString());
		if (data.get("DISC_QT_LMT") != null)limits.setDiscountQuantityLimit(data.get("DISC_QT_LMT").toString());
		if (data.get("DT_HELD_TM_OVER") != null)limits.setDtHeldTimeOver(data.get("DT_HELD_TM_OVER").toString());
		if (data.get("dt_win_1_tm_over") != null)limits.setDtWinOneTimeOver(data.get("dt_win_1_tm_over").toString());
		if (data.get("dt_win_2_tm_over") != null)limits.setDtWinTwoTimeOver(data.get("dt_win_2_tm_over").toString());
		if (data.get("fc_cshrng_tm_over") != null)limits.setFcCashieringTimeOver(data.get("fc_cshrng_tm_over").toString());
		if (data.get("fc_otd_tm_over") != null)limits.setFcOrderTakerTimeOver(data.get("fc_otd_tm_over").toString());
		if (data.get("FC_STR_TM_OVER") != null)limits.setFcStoreTimeOver(data.get("FC_STR_TM_OVER").toString());
		if (data.get("Futr_Dy_Open_Lmt") != null)limits.setFutureDayOpenLimit(data.get("Futr_Dy_Open_Lmt").toString());
		if (data.get("high_am_sale_lmt") != null)limits.setHighAmountSaleLimit(data.get("high_am_sale_lmt").toString());
		if (data.get("High_Qt_Sale_Lmt") != null)limits.setHighQuantitySaleLimit(data.get("High_Qt_Sale_Lmt").toString());
		if (data.get("init_flt_lmt") != null)limits.setInitialFloatLimit(data.get("init_flt_lmt").toString());
		if (data.get("MGR_MODE_TM_LMT") != null)limits.setManagerModeTimeLimit(data.get("MGR_MODE_TM_LMT").toString());
		if (data.get("init_flt_amt") != null)limits.setN_initialFloatAmount(Double.parseDouble(data.get("init_flt_amt").toString()));
		if (data.get("NO_TAX_SALES_AM_LMT") != null)limits.setNoTaxSalesAmountLimit(data.get("NO_TAX_SALES_AM_LMT").toString());
		if (data.get("PRM_ITM_AMT_LMT") != null)limits.setPromoItemAmountLimit(data.get("PRM_ITM_AMT_LMT").toString());
		if (data.get("promo_vldn_mode") != null)limits.setPromoValidationMode(data.get("promo_vldn_mode").toString());
		if (data.get("PRM_ITM_QTY_LMT") != null)limits.setPromoItemQuantityLimit(data.get("PRM_ITM_QTY_LMT").toString());
		if (data.get("rduc_vldn_mode") != null)limits.setReductionValidationMode(data.get("rduc_vldn_mode").toString());
		if (data.get("SKIM_AM_LMT") != null)limits.setSkimAmountLimit(data.get("SKIM_AM_LMT").toString());
		if (data.get("SKIM_TM_LMT") != null)limits.setSkimTimeLimit(data.get("SKIM_TM_LMT").toString());
		if (data.get("pety_cash_am_lmt") != null)limits.setStoreWidePettyCashAmountLimit(data.get("pety_cash_am_lmt").toString());
		if (data.get("am_aft_tot") != null)limits.settRedAfterTotalAmount(data.get("am_aft_tot").toString());
		if (data.get("qt_aft_tot") != null)limits.settRedAfterTotalQuantity(data.get("qt_aft_tot").toString());
		if (data.get("AM_BEF_TOT") != null)limits.settRedAmountBeforeTotal(data.get("AM_BEF_TOT").toString());
		if (data.get("bfr_tot_amunt") != null)limits.settRedBeforeTotal(data.get("bfr_tot_amunt").toString());

		return limits;
	}

	@TrackedMethod
	public List<Map<String, Object>> getLocalizationSets(final Long restaurantId, final Long restaurantInstanceId, Long asnSetType, Long marketId) throws Exception{
		final String query = getDaoXml("getLocalizationSets", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("restId", restaurantId);
		paramMap.put("restInstId", restaurantInstanceId);
		paramMap.put("asnSetType", asnSetType);
		paramMap.put("marketId", marketId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap);
	}
	
	@SuppressWarnings("finally")
	public boolean isHotSelected(final Long restaurantId, final Long restaurantInstanceId ) {
		boolean seletected = false;
		try {
			final String query = getDaoXml("isHotSelected", DAOResources.STORE_DAO);
			final Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("restId", restaurantId);
			paramMap.put("restInstId", restaurantInstanceId);
			List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
			
			
			if (listOfResults != null && listOfResults.size() > 0) {
				for (final Map<String, Object> data : listOfResults) {
					if(data.get("isHotSelected") != null) {
						if(data.get("isHotSelected").toString().equals("1")) seletected = true;
					}
				}
			}
		}
		catch(Exception err) {
			LOGGER.error("STOREDBDAO.isHotSelected::"+ err.getMessage());
		}
		finally {
			return seletected;
		}
	}
	
	
	public List<HotBusinessLimit> getHotBusinessLimit(Long restId, Long restInstId, String effDate,Long marketId) throws Exception {
		Long asnSetType = 6011L;
		final List< Map< String, Object > > localizationSets = getLocalizationSets(restId,restInstId,asnSetType,marketId);
		Long parentSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != localizationSets.get(0).get("PREN_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		// Fetching custSetId from the localizationsets
		Long customSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("CUSM_SET_ID") != null) {
			customSetId = null != localizationSets.get(0).get("CUSM_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		if(parentSetId==customSetId) {
			customSetId = -1L;
		}
		final String query = getDaoXml("getHotBusinessLimit", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("customSetId", customSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<HotBusinessLimit> hotBusinessLimit = new ArrayList<HotBusinessLimit>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				hotBusinessLimit.add(mapHotBusinessLimits(data));
			}
		}
		return hotBusinessLimit;
	}
	private HotBusinessLimit mapHotBusinessLimits(Map<String, Object> data) {
		HotBusinessLimit hotBusiLimits = new HotBusinessLimit();
		if (data.get("MGR_MODE_TM_LMT") != null)hotBusiLimits.setManagerModeTimeLimit(data.get("MGR_MODE_TM_LMT").toString());
		if (data.get("high_am_sale_lmt") != null)hotBusiLimits.setHighAmountSaleLimit(data.get("high_am_sale_lmt").toString());
		if (data.get("high_qt_sale_lmt") != null)hotBusiLimits.setHighQuantitySaleLimit(data.get("high_qt_sale_lmt").toString());
		if (data.get("rduc_vldn_mode") != null)hotBusiLimits.setReductionValidationMode(data.get("rduc_vldn_mode").toString());
		if (data.get("bfr_tot_amunt") != null)hotBusiLimits.settRedBeforeTotal(data.get("bfr_tot_amunt").toString());
		if (data.get("am_aft_tot") != null)hotBusiLimits.settRedAfterTotalAmount(data.get("am_aft_tot").toString());
		if (data.get("qt_aft_tot") != null)hotBusiLimits.settRedAfterTotalQuantity(data.get("qt_aft_tot").toString());
		if (data.get("Prm_Itm_Qty_Lmt") != null)hotBusiLimits.setPromoItemQuantityLimit(data.get("Prm_Itm_Qty_Lmt").toString());
		if (data.get("BP_PRM_PI_QA_LMT") != null)hotBusiLimits.setBypassPromoPerItemQuantityLimit(data.get("BP_PRM_PI_QA_LMT").toString());
		if (data.get("BP_INDV_DISC_AM_LMT") != null)hotBusiLimits.setBypassIndividualDiscountAmountLimit(data.get("BP_INDV_DISC_AM_LMT").toString());
		if (data.get("DISC_QT_LMT") != null)hotBusiLimits.setDiscountQuantityLimit(data.get("DISC_QT_LMT").toString());
		if (data.get("CPN_QT_LMT") != null)hotBusiLimits.setCouponQuantityLimit(data.get("CPN_QT_LMT").toString());
		if (data.get("DISC_AM_LMT") != null)hotBusiLimits.setDiscountAmountLimit(data.get("DISC_AM_LMT").toString());
		if (data.get("NO_TAX_SALES_AM_LMT") != null)hotBusiLimits.setNoTaxSalesAmountLimit(data.get("NO_TAX_SALES_AM_LMT").toString());
		if (data.get("AM_BEF_TOT") != null)hotBusiLimits.settRedAmountBeforeTotal(data.get("AM_BEF_TOT").toString());
		if (data.get("CPN_AM_LMT") != null)hotBusiLimits.setCouponAmountLimit(data.get("CPN_AM_LMT").toString());
		if (data.get("PRM_ITM_AMT_LMT") != null)hotBusiLimits.setPromoItemAmountLimit(data.get("PRM_ITM_AMT_LMT").toString());
		

		return hotBusiLimits;
	}
	
	public List<SizeSelection> getSizeSelection(Long mktId) throws Exception{
		final String query = getDaoXml("getSizeSelection", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getSizeSelection()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("mkt_id", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		SizeSelection sizeSelection = null;
		List<SizeSelection> sizeSelectionList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getSizeSelection()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 
				
			 for (final Map<String, Object> data : listOfResults) {
				 sizeSelection = new SizeSelection();
					if (data.get("cd") != null)
						sizeSelection.setCode(Long.valueOf(data.get("cd").toString()));

					if (data.get("na") != null)
						sizeSelection.setDescrption(String.valueOf(data.get("na").toString()));
					sizeSelectionList.add(sizeSelection);
				}
			}
		  return sizeSelectionList;

	}
	
	public List<PopulateDrinkVol> getPopulateDrinkVol(Long mktId) throws Exception{
		final String query = getDaoXml("getPopulateDrinkVol", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getPopulateDrinkVol()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("mkt_id", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		PopulateDrinkVol populateDrinkVol = null;
		List<PopulateDrinkVol> populateDrinkVolList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getPopulateDrinkVol()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 
				
			 for (final Map<String, Object> data : listOfResults) {
				 populateDrinkVol = new PopulateDrinkVol();
					if (data.get("drnk_vol_na") != null)
						populateDrinkVol.setName(String.valueOf(data.get("drnk_vol_na")));
					
					if (data.get("drnk_vol_code") != null)
						populateDrinkVol.setCode(String.valueOf(data.get("drnk_vol_code")));
						populateDrinkVolList.add(populateDrinkVol);
				}
			}
		  return populateDrinkVolList;

	}

	public List<PromotionGroupDetail> getPromotionGroupsDetails(Long mktId) throws Exception {
		final String query = getDaoXml("getPromotionGroupDetails", DAOResources.STORE_DAO);

		final Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("PARAM_MKT_ID", mktId);

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);

		List<PromotionGroupDetail> promotionGroupDetails = new ArrayList<PromotionGroupDetail>();
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				promotionGroupDetails.add(mapPromotionGroupDetails(data));
			}
		}
		return promotionGroupDetails;
	}

	private PromotionGroupDetail mapPromotionGroupDetails(Map<String, Object> data) {
		PromotionGroupDetail promotionGroup=new PromotionGroupDetail();

		if (data.get("STUS")!=null) {
			promotionGroup.setStatus(data.get("STUS").toString());
		}
		if (data.get("PROMO_GRP_ID") != null) {
			promotionGroup.setPromoGrpId(Long.valueOf(data.get("PROMO_GRP_ID").toString()));
		}
		if (data.get("PROMO_GRP_CD") != null) {
			promotionGroup.setPromoGrpCode(Long.valueOf(data.get("PROMO_GRP_CD").toString()));
		}
		if (data.get("PROMO_GRP_NA")!=null) {
			promotionGroup.setPromotionGroupName(data.get("PROMO_GRP_NA").toString());
		}

		if (data.get("v_promoGrp_model")!=null) {
			promotionGroup.setPromoGrpModel(data.get("v_promoGrp_model").toString());
		}

		
		if (data.get("v_promogrp_types")!=null) {
			promotionGroup.setTypes(Arrays.asList(data.get("v_promogrp_types").toString().split(",")));
		}
	
	
		
		return promotionGroup;
	}
	
	
	public Map<String, Long> getRoutingSetIdsforProduction(Long restId, Long restInstId, Long marketId) throws Exception {
		final String query = getDaoXml("getRoutingSetIdsforProduction", DAOResources.STORE_DAO);

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("restId", restId);
		paramMap.put("restInstId", restInstId);
		paramMap.put("marketId", marketId);
		LOGGER.info("storeDBDAO :: getRoutingSetIdsforProduction()");
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("storeDBDAO :: getRoutingSetIdsforProduction() :: results :"+listOfResults.size());
		Map<String,Long>  rtngSetIds=null;
		
		if (listOfResults != null && listOfResults.size() > 0) {
			rtngSetIds=new HashMap<>();
			for (final Map<String, Object> data : listOfResults) {
				if(data.get("PROD_RTNG_SET")!=null) {
					rtngSetIds.put("PROD_RTNG_SET",Long.valueOf(data.get("PROD_RTNG_SET").toString()));}
				if(data.get("PSNTN_RTNG_SET")!=null) {
					rtngSetIds.put("PSNTN_RTNG_SET",Long.valueOf(data.get("PSNTN_RTNG_SET").toString()));}
				if(data.get("RTM_PROD_RTNG_SET")!=null) {
					rtngSetIds.put("RTM_PROD_RTNG_SET",Long.valueOf(data.get("RTM_PROD_RTNG_SET").toString()));}
			}
		}
		LOGGER.info("storeDBDAO :: getRoutingSetIdsforProduction() :: rtngSetIds :"+rtngSetIds);
		return rtngSetIds;
	}
	
	public Production getNameKVSVolumeProductionDetails(String defaultMktLocale, Long defaultRestLocale, 
			List<Long> rtngSetIdsList, Long marketId,String addtlRtngFlg) throws Exception {
		final String query = getDaoXml("getNameKVSVolumeProductionDetails", DAOResources.STORE_DAO);

		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("defaultMktLocale", Long.valueOf(defaultMktLocale));
		paramMap.put("defaultRestLocale", Long.valueOf(defaultRestLocale));
		paramMap.put("rtngSetIds", rtngSetIdsList);
		paramMap.put("marketId", marketId);
		LOGGER.info("storeDBDAO :: getNameKVSVolumeProductionDetails()");
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("storeDBDAO :: getNameKVSVolumeProductionDetails() :: results :"+listOfResults.size());
		Production production=null;
		if (listOfResults != null && listOfResults.size() > 0) {
			production=new Production();
			for (final Map<String, Object> data : listOfResults) {
				mapNameKVSVolumeProductionDetails(production,data,"N");
			}
		}
		return production;
	}

	public String getAllowExportAddtlRtngFlag(Long marketId) throws Exception {

		final String query = getDaoXml("getAllowExportAddtlRtngFlag", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		String addtlRtngFlg = null;

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("storeDBDAO :: getAllowExportAddtlRtngFlag()");
		for (final Map<String, Object> data : listOfResults) {
			addtlRtngFlg = data.get("ALLOWEXPORTADDITIONALROUTING").toString();
		}
		LOGGER.info("results for getAllowExportAddtlRtngFlag():" + addtlRtngFlg);
		return addtlRtngFlg;
	}

	private void mapNameKVSVolumeProductionDetails(Production production, Map<String, Object> data,String addtlRtngFlg) {
		if (production.getNameTables() == null) {
			production.setNameTables(new ArrayList<>());
		}
		if (production.getPrdKvsRoutes() == null) {
			production.setPrdKvsRoutes(new ArrayList<>());
		}
		if (production.getVolumeTables() == null) {
			production.setVolumeTables(new ArrayList<>());
		}
		if (data.get("rtng_pkg_table_typ") != null) {
			switch (data.get("rtng_pkg_table_typ").toString().toUpperCase()) {// VOLUME_TABLE_ROUTES
				case "KVS_ROUTE": {
					PrdKVSRoute kvsRoute = new PrdKVSRoute();
					if (data.get("rte_id") != null) {
						kvsRoute.setId(data.get("rte_id").toString());
					}
					if (data.get("path") != null) {
						kvsRoute.setPath(data.get("path").toString());
					}
					production.getPrdKvsRoutes().add(kvsRoute);
					break;
				}
				case "VOLUME_TABLE": {
					VolumeTable volumeTable = new VolumeTable();
					if (data.get("vol_id") != null) {
						volumeTable.setVolId(data.get("vol_id").toString());
					}
					if (data.get("na") != null) {
						volumeTable.setName(data.get("na").toString());
					}
					if (data.get("path") != null) {
						volumeTable.setPath(data.get("path").toString());
					}
					production.getVolumeTables().add(volumeTable);
					
					break;
				}
				case "VOLUME_TABLE_ROUTES": {
					if (addtlRtngFlg.equals("Y")) {
						Optional<VolumeTable> optVolTbl = production.getVolumeTables().stream().filter(e -> {
							return e.getVolId().equals(data.get("vol_id").toString());
						}).findAny();
						VolumeTable volumeTable = null;
						if (optVolTbl==null || optVolTbl.isEmpty()) {
							volumeTable = new VolumeTable();
							volumeTable.setRoutes(new ArrayList<VolumeTable.Route>());
						} else {
							volumeTable = optVolTbl.get();
							if (volumeTable.getRoutes() == null) {
								volumeTable.setRoutes(new ArrayList<VolumeTable.Route>());
							}
						}
						VolumeTable.Route route = volumeTable.new Route();
						route.setId(data.get("typ").toString());
						route.setSource(data.get("typ").toString());
						route.setRoutes(data.get("path").toString());
						volumeTable.getRoutes().add(route);
						production.getVolumeTables().add(volumeTable);
					}
					break;
				}	
				case "NAME_TABLE": {
					NameTable nameTable = new NameTable();
					if (data.get("que_id") != null) {
						nameTable.setId(data.get("que_id").toString());
					}
					if (data.get("na") != null) {
						nameTable.setName(data.get("na").toString());
					}
					if (data.get("mirr_que_id") != null) {
						nameTable.setMirror(data.get("mirr_que_id").toString());
					}
					if (data.get("shrt_na") != null) {
						nameTable.setShortName(data.get("shrt_na").toString());
					}
					if (data.get("typ") != null) {
						nameTable.setType(data.get("typ").toString());
					}
					production.getNameTables().add(nameTable);
					break;
				}
			}
		}
	}
	
	public List<KSGroup> getProductionKSGroups(Long restId, Long restInstId, Long marketId,Long psntnRtngSet,String defaultMktLocale) throws Exception {
		final String query = getDaoXml("getProductionKSGroups", DAOResources.STORE_DAO);
		LOGGER.info("storeDBDAO :: getProductionKSGroups()");
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("restId", restId);
		paramMap.put("restInstId", restInstId);
		paramMap.put("marketId", marketId);
		paramMap.put("defaultMktLocale", Long.valueOf(defaultMktLocale));
		paramMap.put("psntnRtngSet", psntnRtngSet);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("storeDBDAO :: getProductionKSGroups()::results : "+listOfResults.size());
		List<KSGroup> ksgGroupList=null;
		if(listOfResults!=null && listOfResults.size()>0) {
			ksgGroupList=new ArrayList<KSGroup>();
			for (final Map<String, Object> data : listOfResults) {
				KSGroup group=new KSGroup();
				if(data.get("xmlid")!=null) {
					group.setId(data.get("xmlid").toString());}
				if(data.get("xmlpath")!=null) {
					group.setPath(data.get("xmlpath").toString());}
				ksgGroupList.add(group);
			}
		}
		return ksgGroupList;
	}
	
	
	public List<KitchenGroup> getProductionKitchenGroups(Long restId, Long restInstId, Long marketId,String defaultMktLocale) throws Exception {
		final String query = getDaoXml("getProductionKitchenGroups", DAOResources.STORE_DAO);
		LOGGER.info("storeDBDAO :: getProductionKitchenGroups()");
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("restId", restId);
		paramMap.put("restInstId", restInstId);
		paramMap.put("marketId", marketId);
		paramMap.put("defaultMktLocale", Long.valueOf(defaultMktLocale));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("storeDBDAO :: getProductionKitchenGroups()::results : "+listOfResults.size());
		List<KitchenGroup> kitchenGroupList=null;
		if(listOfResults!=null && listOfResults.size()>0) {
			kitchenGroupList=new ArrayList<>();
			for (final Map<String, Object> data : listOfResults) {
				mapKitchenGroupData(kitchenGroupList,data);
			}
		}
		return kitchenGroupList;
	}
	
	private void mapKitchenGroupData(List<KitchenGroup> kitchenGroupList, Map<String, Object> data) {
		Optional<KitchenGroup> optKitGRP = kitchenGroupList.stream().filter(e -> {
			return e.getId().equals(data.get("grpname1").toString());
		}).findAny();
		KitchenGroup kitchenGroup = null;
		if (optKitGRP==null || optKitGRP.isEmpty()) {
			kitchenGroup = new KitchenGroup();
			kitchenGroup.setId(data.get("grpname1").toString());
			kitchenGroup.setPriorities(new ArrayList<>());
			kitchenGroupList.add(kitchenGroup);
		} else {
			kitchenGroup = optKitGRP.get();
			if (kitchenGroup.getPriorities() == null) {
				kitchenGroup.setPriorities(new ArrayList<>());
			}
		}
		if (data.get("trnsltd_val") != null) {
			kitchenGroup.setAlias(data.get("trnsltd_val").toString());
		}

		Optional<KitchenGroup.Priority> optPrtGrp = kitchenGroup.getPriorities().stream().filter(e -> {
			return e.getId().equals(data.get("priorityLvl").toString());

		}).findAny();
		KitchenGroup.Priority prority = null;
		if (optPrtGrp==null || optPrtGrp.isEmpty()) {
			prority = kitchenGroup.new Priority();
			if (data.get("priorityLvl") != null) {
				prority.setId(data.get("priorityLvl").toString());
			}
			kitchenGroup.getPriorities().add(prority);
		} else {
			prority = optPrtGrp.get();
		}
		if (prority.getNames() == null) {
			prority.setNames(new ArrayList<>());
		}
		if (data.get("ShorTypeQueue") != null) {
			prority.getNames().add(data.get("ShorTypeQueue").toString());
		}
		

	}

	public Long getDefaultRestLocaleId(Long parentSetId, Long childSetId, Long mktId) throws Exception {
		final String query = getDaoXml("getDefaultRestLocaleId", DAOResources.STORE_DAO);
		LOGGER.info("storeDBDAO:: getDefaultRestLocaleId()");
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("prenSetId", parentSetId);
		paramMap.put("chldSetId", childSetId);
		paramMap.put("marketId", mktId);
		Long defaultRestLocleId=0L;
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if(data.get("lang_id")!=null) {
					defaultRestLocleId=Long.valueOf(data.get("lang_id").toString());
				}
			}
		}
		LOGGER.info("results for getDefaultRestLocaleId():: results : "+defaultRestLocleId);
		return defaultRestLocleId;
	}

	public List<TaxDefinition> getTaxDefinition(Long restId, Long restInstId, String effectiveDate, Long mktId) throws Exception {
		Long asnSetType = 6016L;
		final List< Map< String, Object > > localizationSets = getLocalizationSets(restId,restInstId,asnSetType,mktId);
		Long parentSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != localizationSets.get(0).get("PREN_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		// Fetching custSetId from the localizationsets
		Long childSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("CUSM_SET_ID") != null) {
			childSetId = null != localizationSets.get(0).get("CUSM_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		if(parentSetId==childSetId) {
			childSetId = -1L;
		}
		final String query = getDaoXml("getTaxDefinition", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		paramMap.put("restId", restId);
		paramMap.put("restInstId", restInstId);
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("childSetId", childSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<TaxDefinition> taxDefinition = new ArrayList<TaxDefinition>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				taxDefinition.add(mapTaxDefinition(data));
			}
		}
		return taxDefinition;
	}

	private TaxDefinition mapTaxDefinition(Map<String, Object> data) {
		TaxDefinition list = new TaxDefinition();
		List<String> grandOptList = new ArrayList<>();
		if(data.get("LGL_NA")!=null)list.setLegalName(data.get("LGL_NA").toString());
		if(data.get("RCPT_HDR")!=null)list.setDefaultReceiptHeader(data.get("RCPT_HDR").toString());
		if(data.get("RCPT_FOTR")!=null)list.setDefaultReceiptFooter(data.get("RCPT_FOTR").toString());
		if(data.get("WLCM_MSG")!=null)list.setWelcomeMessage(data.get("WLCM_MSG").toString());
		if(data.get("MENU_PRC_BSIS")!=null)list.setMenuPriceBasis(data.get("MENU_PRC_BSIS").toString());
		if(data.get("CALC_TYP")!=null)list.setCalculationType(data.get("CALC_TYP").toString());
		if(data.get("DSPL_TAX_TO_CUST")!=null)list.setDisplayTaxToCustomer(data.get("DSPL_TAX_TO_CUST").toString());
		if(data.get("DSPL_TAX_ON_SLS_PNL")!=null)list.setDisplayTaxOnSalePanel(data.get("DSPL_TAX_ON_SLS_PNL").toString());
		if(data.get("DSPL_TAX_ON_RCPT")!=null)list.setDisplayTaxOnReceipt(data.get("DSPL_TAX_ON_RCPT").toString());
		if(data.get("GTO_PROMO")!=null && data.get("GTO_PROMO").equals("GrandTotalOption"))grandOptList.add("GT_PROMO");
		if(data.get("GTO_WASTE")!=null && data.get("GTO_WASTE").equals("GrandTotalOption"))grandOptList.add("GT_WASTE");
		if(data.get("GTO_EMP_MEAL")!=null && data.get("GTO_EMP_MEAL").equals("GrandTotalOption"))grandOptList.add("GT_EMPMEAL");
		if(data.get("GTO_MAN_MEAL")!=null && data.get("GTO_MAN_MEAL").equals("GrandTotalOption"))grandOptList.add("GT_MGRMEAL");
		if(data.get("GTO_REFUND")!=null && data.get("GTO_REFUND").equals("GrandTotalOption"))grandOptList.add("GT_REFUND");
		if(data.get("GTO_VOID")!=null && data.get("GTO_VOID").equals("GrandTotalOption"))grandOptList.add("GT_VOID");
		if(data.get("GTO_GT")!=null && data.get("GTO_GT").equals("GrandTotalOption"))grandOptList.add("GT_GT");
		if(data.get("GTO_VOID_ITEM")!=null && data.get("GTO_VOID_ITEM").equals("GrandTotalOption"))grandOptList.add("GT_VOIDITEM");
		if(data.get("GTO_VOID_SALE")!=null && data.get("GTO_VOID_SALE").equals("GrandTotalOption"))grandOptList.add("GT_VOIDSALE");
		if(data.get("GTO_VOID_DISC")!=null && data.get("GTO_VOID_DISC").equals("GrandTotalOption"))grandOptList.add("GT_DISCOUNT");
		if(data.get("GTO_VOID_GIFT")!=null && data.get("GTO_VOID_GIFT").equals("GrandTotalOption"))grandOptList.add("GT_GIFT");
		if(data.get("GTO_OTH_RCPT")!=null && data.get("GTO_OTH_RCPT").equals("GrandTotalOption"))grandOptList.add("GT_OTHERRECEIPTS");
		if(data.get("GTO_COUPONS")!=null && data.get("GTO_COUPONS").equals("GrandTotalOption"))grandOptList.add("GT_COUPON");
		if(null!=grandOptList && !grandOptList.isEmpty() && grandOptList.size()>0) {
				list.setGrandTotalExclusions(grandOptList);
		}
		return list;
	}
	
	public List<PromotionImages> getPromotionImages(Long mktId) throws Exception{
		final String query = getDaoXml("getPromotionImages", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getPromotionImages()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("mkt_id", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<PromotionImages> promotionImagesList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getPromotionImages()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 
				
			 for (final Map<String, Object> data : listOfResults) {
			PromotionImages promotionImages = new PromotionImages();
				 
					if (data.get("mdia_file_na") != null)
						promotionImages.setMediaFileName(String.valueOf(data.get("mdia_file_na").toString()));
					promotionImagesList.add(promotionImages);
				}
			}
		  return promotionImagesList;
	}



	public List<CategoryDetails> getCategoryDetails(Long restId, Long restInstId, String effectiveDate, Long mktId,
			 Map<Long, List<LanguageDetails>> languageDetails) throws Exception {
		
		final String query = getDaoXml("getCategoryDetails", DAOResources.STORE_DAO);

		final Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("marketId", mktId);
		paramMap.put("restId", restId);
		paramMap.put("restInstId", restInstId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);

		List<CategoryDetails> allCategoryDetails = new ArrayList<CategoryDetails>();
		List<CategoryDetails> uniqueCategoryDetails = new ArrayList<CategoryDetails>();
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				allCategoryDetails.add(mapCategoryDetails(data));
			}
		}
		for (CategoryDetails categoryDetails2 : allCategoryDetails) {

			if (languageDetails.containsKey(categoryDetails2.getId())) {
				categoryDetails2.setLanguageDetails(languageDetails.get(categoryDetails2.getId()));
			}
		}
		
		 for (int i = 0; i < allCategoryDetails.size(); i++) {
		        boolean flag = true;
		        for (int j = i + 1; j < allCategoryDetails.size(); j++) {
		            if (allCategoryDetails.get(i).getCode().equals(allCategoryDetails.get(j).getCode()) && 
		            		allCategoryDetails.get(i).getParentCode().equals(allCategoryDetails.get(j).getParentCode())) {
		                flag = false;                
		            }
		        }
		        if (flag) {
		        	uniqueCategoryDetails.add(allCategoryDetails.get(i));
		        }
		    }
		
		return uniqueCategoryDetails;
	}

	private CategoryDetails mapCategoryDetails(Map<String, Object> data) {
	
		CategoryDetails categoryDetail=new CategoryDetails();
		
		if (data.get("catcode")!=null) {
			categoryDetail.setCode(Long.valueOf(data.get("catcode").toString()));
		}
		
		if (data.get("cat_id")!=null) {
			categoryDetail.setId(Long.valueOf(data.get("cat_id").toString()));
		}
		
		if (data.get("catdesc")!= null) {
			categoryDetail.setCategoryDescription(data.get("catdesc").toString());
		}
		if (data.get("prn_catcode")!=null) {
			categoryDetail.setParentCode(Long.valueOf(data.get("prn_catcode").toString()));
		}
		
		if (data.get("pren_cat_id")!=null) {
			categoryDetail.setParentId(Long.valueOf(data.get("pren_cat_id").toString()));
		}
		
		if (data.get("cat_img") != null) {
			categoryDetail.setCategoryImageName((data.get("cat_img").toString()));
		}
		if (data.get("daypart") != null) {
			categoryDetail.setDayPart(data.get("daypart").toString());
		}
		
		if (data.get("colorVal") != null) {
			categoryDetail.setColorValue(data.get("colorVal").toString());
		}
		if (data.get("enabled") != null) {
			categoryDetail.setEnabled(data.get("enabled").toString());
		}
		if (data.get("cat_sequence")!=null) {
			categoryDetail.setCategorySequence((Long.valueOf(data.get("cat_sequence").toString())));
		}
		return categoryDetail;
	}
	public Map<Long, List<LanguageDetails>> getLanguageDetails(Long marketID) throws Exception {
		final String query = getDaoXml("getLanguageDetails", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketID);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		Map<Long,List<LanguageDetails>> allLangDtls=new HashMap<Long, List<LanguageDetails>>();
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				Long catId=null;
				if (data.get("cat_id")!=null) {
					catId=Long.valueOf(data.get("cat_id").toString());
				}
				List<LanguageDetails> catLangDtl=new ArrayList<LanguageDetails>();
				String []countries=null;
				String []codes=null;
				String []names=null;
				String []descriptions=null;
				String []imageNames=null;
				
				if (data.get("country") != null) {
					countries=data.get("country").toString().split(";");//en_US ;es_US 
				}
				if (data.get("langcode") != null) {
					codes=data.get("langcode").toString().split(";"); //en;es
				}
				if (data.get("lang_name") != null) {
					names=data.get("lang_name").toString().split(";");//English;Spanish
				}
				if (data.get("cat_lang_description") != null) {
					descriptions=data.get("cat_lang_description").toString().split(";"); //Lunch;Almuerzo
				}
				if (data.get("imageName") != null) {
					imageNames=data.get("imageName").toString().split(";"); //Lunch;Almuerzo
				}
					
				if (codes != null) {
					for (int i = 0; i <codes.length; i++) 
					{
						LanguageDetails langDetail=new LanguageDetails();
						if (countries.length>i) {
							langDetail.setCountry(countries[i]);
						}
						if (codes.length>i) {
							langDetail.setLang_code(codes[i]);
						}		
						if (names.length>i) {
							langDetail.setName(names[i]);
						}
						if (descriptions.length>i) {
							langDetail.setCat_lang_desc(descriptions[i]);
						}
						if (imageNames.length>i) {
							langDetail.setImgName(imageNames[i]);
						}
						
						catLangDtl.add(langDetail);	
					}	
				}
					allLangDtls.put(catId, catLangDtl);
			}
		}
		return allLangDtls;
	}
	


	public List<Localization> getFuncLocalizationSet(Long mktId, Long restId, Long restInstId, String effDate) throws Exception {
		final List< Map< String, Object > > localizationPrntCustmSet = getLocalizationPrntCustmSet(restId,restInstId);
		Long parentSetId = null;
		if (localizationPrntCustmSet != null && localizationPrntCustmSet.size() > 0 && localizationPrntCustmSet.get(0) != null
				&& localizationPrntCustmSet.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != localizationPrntCustmSet.get(0).get("PREN_SET_ID")
					? Long.valueOf(localizationPrntCustmSet.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		// Fetching custSetId from the localizationsets
		Long childSetId = null;
		if (localizationPrntCustmSet != null && localizationPrntCustmSet.size() > 0 && localizationPrntCustmSet.get(0) != null
				&& localizationPrntCustmSet.get(0).get("CUSM_SET_ID") != null) {
			childSetId = null != localizationPrntCustmSet.get(0).get("CUSM_SET_ID")
					? Long.valueOf(localizationPrntCustmSet.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		if(parentSetId==childSetId) {
			childSetId = -1L;
		}
		final String query = getDaoXml("getFuncLocalizationSet", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("childSetId", childSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effDate));
		paramMap.put("mktId", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<Localization> localizations = new ArrayList<Localization>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				localizations.add(funcLocalization(data));
			}
		}
		return localizations;
	}
	
	@TrackedMethod
	public List<Map<String, Object>> getLocalizationPrntCustmSet(final Long restaurantId, final Long restaurantInstanceId) throws Exception{
		final String query = getDaoXml("getLocalizationPrntCustmSet", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("restId", restaurantId);
		paramMap.put("restInstId", restaurantInstanceId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap);
	}
	
	private Localization funcLocalization(Map<String, Object> data) {
		Localization localization = new Localization();
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		BigDecimal minCltngAmtBig = new BigDecimal(data.get("MIN_CLTNG_AM").toString());
		BigDecimal minLglAmtBig = new BigDecimal(data.get("MIN_LGL_AM").toString());
		
		double minCltngAmtValue = minCltngAmtBig.doubleValue();
		double minLglAmtValue = minLglAmtBig.doubleValue();
		
		int minCltngAmtintPart = (int) minCltngAmtValue;
		int minLglAmtintPart = (int) minLglAmtValue;
		
		String minCltngAmt;
		String minLglAmt;
		
		if(data.get("MIN_CLTNG_AM") != null) {
			if((minCltngAmtValue - minCltngAmtintPart) == 0) { //whole no.
				minCltngAmt = decimalFormat.format(minCltngAmtBig.doubleValue());
			}else {
				minCltngAmt =  Double.toString((double) minCltngAmtBig.doubleValue());
			}
		}else {
			minCltngAmt = (String) data.get("MIN_CLTNG_AM");
		}
		
		if(data.get("MIN_LGL_AM") != null) {
			if((minLglAmtValue - minLglAmtintPart) == 0) { //whole no.
				minLglAmt = decimalFormat.format(minLglAmtBig.doubleValue());
			}else {
				minLglAmt =  Double.toString((double) minLglAmtBig.doubleValue());
			}
		}else {
			minLglAmt = (String) data.get("MIN_LGL_AM");
		}
		
		if (data.get("CTRY_ID") != null)localization.setCountryId(data.get("CTRY_ID").toString());
		if (data.get("LANG_CD") != null)localization.setLanguage(data.get("LANG_CD").toString());
		if (data.get("CTRY_VRNT_ID") != null)localization.setVariant(data.get("CTRY_VRNT_ID").toString());
		if (data.get("DATE_FRMT") != null)localization.setDateFormat(data.get("DATE_FRMT").toString());
		if (data.get("TM_FRMT") != null)localization.setTimeFormat(data.get("TM_FRMT").toString());
		if (data.get("DCML_SEPR") != null)localization.setDecimalSeparator(data.get("DCML_SEPR").toString());
		if (data.get("THOU_SEPR") != null)localization.setThousandSeparator(data.get("THOU_SEPR").toString());
		if (data.get("CURN_NA") != null)localization.setCurrencyName(data.get("CURN_NA").toString());
		if (data.get("CURN_SYMB") != null)localization.setCurrencySymbol(data.get("CURN_SYMB").toString());
		if (data.get("CURN_DCMLS") != null)localization.setCurrencyDecimals(data.get("CURN_DCMLS").toString());
		if (data.get("MIN_CLTNG_AM") != null)localization.setMinCirculatingAmt(minCltngAmt);
		if (data.get("MIN_LGL_AM") != null)localization.setMinLegalAmt(minLglAmt);
		if (data.get("CSO_GRLL_SCR_LOUT") != null)localization.setCsoGrillScreenLayout(data.get("CSO_GRLL_SCR_LOUT").toString());
		if (data.get("PSTV_CURN_FRMT") != null)localization.setPositiveCurrencyFormat(data.get("PSTV_CURN_FRMT").toString());
		if (data.get("NGTV_CRNY_FRMT") != null)localization.setNegativeCurrencyFormat(data.get("NGTV_CRNY_FRMT").toString());
		if (data.get("ORD_TOT_RNDG_RULE") != null)localization.setOrderTotalRoundingRule(data.get("ORD_TOT_RNDG_RULE").toString());
		if (data.get("TOT_DUE_RNDG_RULE") != null)localization.setTotalDueRoundingRule(data.get("TOT_DUE_RNDG_RULE").toString());
		if (data.get("DISC_RND_RULE") != null)localization.setDiscountRoundingRule(data.get("DISC_RND_RULE").toString());
		if (data.get("Def_Rndg_Rule") != null)localization.setDefaultRoundingRule(data.get("Def_Rndg_Rule").toString());
		if (data.get("RD_DISP_MODE") != null)localization.setRoundingDisplayMode(data.get("RD_DISP_MODE").toString());
		
   return localization;
	}
	
	public List<CategoryHours> getCategoryHours(Long restId,Long  restInstId,Long mktId) throws Exception{
		final String query = getDaoXml("getCategoryHours", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getCategoryHours()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("rest_id", restId);
		paramMap.put("rest_inst_id", restInstId);
		paramMap.put("mkt_id", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<CategoryHours> categoryHoursList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getCategoryHours()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 
				
			 for (final Map<String, Object> data : listOfResults) {
				 CategoryHours categoryHours = new CategoryHours();
				 
					if (data.get("cat_cd") != null)
						categoryHours.setCategoryCode(String.valueOf(data.get("cat_cd")));
					if (data.get("sun_strt_tm") != null)
						categoryHours.setSunStartTime(String.valueOf(data.get("sun_strt_tm")));
					if (data.get("sun_end_tm") != null)
						categoryHours.setSunEndTime(String.valueOf(data.get("sun_end_tm")));
					if (data.get("mon_strt_tm") != null)
						categoryHours.setMonStartTime(String.valueOf(data.get("mon_strt_tm")));
					if (data.get("mon_end_tm") != null)
						categoryHours.setMonEndTime(String.valueOf(data.get("mon_end_tm")));
					if (data.get("tue_strt_tm") != null)
						categoryHours.setTueStartTime(String.valueOf(data.get("tue_strt_tm")));
					if (data.get("tue_end_tm") != null)
						categoryHours.setTueEndTime(String.valueOf(data.get("tue_end_tm")));
					if (data.get("wed_strt_tm") != null)
						categoryHours.setWedStartTime(String.valueOf(data.get("wed_strt_tm")));
					if (data.get("wed_end_tm") != null)
						categoryHours.setWedEndTime(String.valueOf(data.get("wed_end_tm")));
					if (data.get("thu_strt_tm") != null)
						categoryHours.setThuStartTime(String.valueOf(data.get("thu_strt_tm")));
					if (data.get("thu_end_tm") != null)
						categoryHours.setThuEndTime(String.valueOf(data.get("thu_end_tm")));
					if (data.get("fri_strt_tm") != null)
						categoryHours.setFriStartTime(String.valueOf(data.get("fri_strt_tm")));
					if (data.get("fri_end_tm") != null)
						categoryHours.setFriEndTime(String.valueOf(data.get("fri_end_tm")));
					if (data.get("sat_strt_tm") != null)
						categoryHours.setSatStartTime(String.valueOf(data.get("sat_strt_tm")));
					if (data.get("sat_end_tm") != null)
						categoryHours.setSatEndTime(String.valueOf(data.get("sat_end_tm")));
					categoryHoursList.add(categoryHours);
				}
			}
		  return categoryHoursList;
	}


	public List<Map<String, Object>> getTaxSearchSets(Long marketID, long nodeID, String effectiveDate) throws Exception {
		final String query = getDaoXml("getTaxSearchSets", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", marketID);
		paramMap.put("nodeID", nodeID);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));		
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		return listOfResults;
	}

	public List<TaxType> getTaxType(Long marketID, List<Map<String, Object>> taxSearchSets, String scptManagmentFlag)
			throws Exception {
		List<Object[]> valuesMap = new ArrayList<>();
		for (int i = 0; i < taxSearchSets.size(); i++) {
			Object[] entry = { taxSearchSets.get(i).get("set_id"), taxSearchSets.get(i).get("tax_typ_code"),
					DateUtility.convertStringToDate(taxSearchSets.get(i).get("strt_dt").toString()) };
			valuesMap.add(entry);
		}
		String query = getDaoXml("getTaxType", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", marketID);
		if(null!=valuesMap && !valuesMap.isEmpty() && valuesMap.size()>0) {
			paramMap.put("valuesMap", valuesMap);
			}else {
				query = query.replace(":valuesMap","null,null,null");
			}
		paramMap.put("scriptManagementFlag", scptManagmentFlag);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		
		List<TaxType> listOfTaxType = new ArrayList<>();
		List<Object[]> taxBreakdownMap = new ArrayList<>();
		if(listOfResults!=null && listOfResults.size()>0) {
			for (int i = 0; i < listOfResults.size(); i++) {
				Object[] entry = { Long.parseLong(listOfResults.get(i).get("TAX_TYP_ID").toString()), DateUtility.convertStringToDate(listOfResults.get(i).get("STRT_DT").toString()),
						Long.parseLong(listOfResults.get(i).get("TAX_ID").toString()) };
				taxBreakdownMap.add(entry);
			}
		}
		List<Map<String, Object>> taxBreakdownList = getTaxBreakDown(taxBreakdownMap);
		if (listOfResults != null && listOfResults.size() > 0 ) {
			for (Map<String, Object> val : listOfResults) {
				List<Map<String,Object>> newList = new ArrayList<>();
				if(null!=taxBreakdownList && taxBreakdownList.size()>0 && !taxBreakdownList.isEmpty()) {
				for(Map<String, Object> brkdMap:taxBreakdownList) {
					if(brkdMap.get("TAX_TYP_ID").equals(val.get("TAX_TYP_ID"))){
						newList.add(brkdMap);
					}					
				}}
				listOfTaxType.add(mapTaxType(val, newList));
			}}
		return listOfTaxType;
	}

	private TaxType mapTaxType(Map<String, Object> val, List<Map<String, Object>> newList) {
		TaxType taxType = new TaxType();
		if(val.get("stus")!=null) taxType.setStatusCode(val.get("stus").toString());
		if(val.get("TAX_TYP_CODE")!=null) taxType.setTaxTypeCode(val.get("TAX_TYP_CODE").toString());
		if(val.get("TAX_TYP_NA")!=null) taxType.setTaxDescription(val.get("TAX_TYP_NA").toString());
		if(val.get("TAX_RATE")!=null) {
			BigDecimal bd = new BigDecimal(val.get("TAX_RATE").toString());
			taxType.setTaxRate(bd.stripTrailingZeros().toPlainString());
		}
		if(val.get("TAX_BSIS")!=null) taxType.setTaxBasis(val.get("TAX_BSIS").toString());
		if(val.get("TAX_CALC_TYP")!=null) taxType.setTaxCalcType(val.get("TAX_CALC_TYP").toString());
		if(val.get("RNDG")!=null) taxType.setRounding(val.get("RNDG").toString());
		if(val.get("PRCN")!=null) taxType.setPrecision(val.get("PRCN").toString());
		if(val.get("scpt_na")!=null) taxType.setRule(val.get("scpt_na").toString());
		if(null!=newList && !newList.isEmpty() && newList.size()>0) {
			taxType.setTaxBreakDown(newList);
		}
		return taxType;
	}

	public final List<Map<String, Object>> getTaxBreakDown(List<Object[]> taxBreakdownMap) throws Exception {
		String query = getDaoXml("getTaxBreakDown", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		if(null!=taxBreakdownMap && !taxBreakdownMap.isEmpty() && taxBreakdownMap.size()>0) {
			paramMap.put("taxBreakdownMap", taxBreakdownMap);
			}else {
				query = query.replace(":taxBreakdownMap","null,null,null");
			}
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		return listOfResults;
	}

	public List<String> getFacilities(Long restId, Long restInstId,  Long mktId) throws Exception {
		final String query = getDaoXml("getFacilities", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		paramMap.put("restId", restId);
		paramMap.put("restInstId", restInstId);		
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<String> list = new ArrayList<String>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if (data.get("FCIL_VAL") != null) list.add(data.get("FCIL_VAL").toString());
			}
		}
		return list;
	}
	
	public List<TenderType> getTenderType(Long mktId, Long restId, Long restInstId, String effDate) throws Exception {
		final List< Map< String, Object > > tenderTypesSet = getTenderTypesSet(restId,restInstId);
		Long parentSetId = null;
		if (tenderTypesSet != null && tenderTypesSet.size() > 0 && tenderTypesSet.get(0) != null
				&& tenderTypesSet.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != tenderTypesSet.get(0).get("PREN_SET_ID")
					? Long.valueOf(tenderTypesSet.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		// Fetching custSetId from the localizationsets
		Long childSetId = null;
		if (tenderTypesSet != null && tenderTypesSet.size() > 0 && tenderTypesSet.get(0) != null
				&& tenderTypesSet.get(0).get("CUSM_SET_ID") != null) {
			childSetId = null != tenderTypesSet.get(0).get("CUSM_SET_ID")
					? Long.valueOf(tenderTypesSet.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		//fetching Coupon setIDs
		final List< Map< String, Object > > couponTenderTypesSet = getCouponTenderTypesSet(restId,restInstId);
		Long couponParentSetId = null;
		if (couponTenderTypesSet != null && couponTenderTypesSet.size() > 0 && couponTenderTypesSet.get(0) != null
				&& couponTenderTypesSet.get(0).get("PREN_SET_ID") != null) {
			couponParentSetId = null != couponTenderTypesSet.get(0).get("PREN_SET_ID")
					? Long.valueOf(couponTenderTypesSet.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		// Fetching custSetId from the localizationsets
		Long couponChildSetId = null;
		if (couponTenderTypesSet != null && couponTenderTypesSet.size() > 0 && couponTenderTypesSet.get(0) != null
				&& tenderTypesSet.get(0).get("CUSM_SET_ID") != null) {
			couponChildSetId = null != couponTenderTypesSet.get(0).get("CUSM_SET_ID")
					? Long.valueOf(couponTenderTypesSet.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		
		final List< Map< String, Object > > tenderTypesMarketId = getTenderTypesMarketId(mktId);
		Long masterSetID = null;
		if (tenderTypesMarketId != null && tenderTypesMarketId.size() > 0 && tenderTypesMarketId.get(0) != null
				&& tenderTypesMarketId.get(0).get("SET_ID") != null) {
			masterSetID = null != tenderTypesMarketId.get(0).get("SET_ID")
					? Long.valueOf(tenderTypesMarketId.get(0).get("SET_ID").toString())
					: null;
		}
		
		final List< Map< String, Object > > tenderTypeList = getTenderTypeList(parentSetId, childSetId,effDate, masterSetID, mktId);
		final List< Map< String, Object > > couponTenderTypeList = getCouponTenderTypeList(couponParentSetId, couponChildSetId,effDate, masterSetID, mktId);
		
		List<Map<String, Object>> listOfResultsTendType = tenderTypeList;
		List<Map<String, Object>> listOfResultsCouponType = couponTenderTypeList;
		List<TenderType> tenderTypesDatas = new ArrayList<TenderType>();

		if (listOfResultsTendType != null && listOfResultsTendType.size() > 0) {
			for (final Map<String, Object> tendTypeData: listOfResultsTendType) {
				tenderTypesDatas.add(tenderTypesVals(tendTypeData, listOfResultsTendType));
			}
		}
		if (listOfResultsCouponType != null && listOfResultsCouponType.size() > 0) {
			for (final Map<String, Object> couponTypeData : listOfResultsCouponType) {
				tenderTypesDatas.add(couponTypesVals(couponTypeData, listOfResultsTendType));
			}
		}
		return tenderTypesDatas;
	}
	
	private List<Map<String, Object>> getTenderTypeList(Long parentSetId, Long childSetId, String effDate,
			Long masterSetID, Long mktId) throws Exception {
		final String query = getDaoXml("getTenderTypeList", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("childSetId", childSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effDate));
		paramMap.put("masterSetID", masterSetID);
		paramMap.put("mktId", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		
		return listOfResults;
	}
	
	private List<Map<String, Object>> getCouponTenderTypeList(Long couponParentSetId, Long couponChildSetId,
			String effDate, Long masterSetID, Long mktId) throws Exception {
		final String query = getDaoXml("getCouponTenderTypeList", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("couponParentSetId", couponParentSetId);
		paramMap.put("couponChildSetId", couponChildSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effDate));
		paramMap.put("masterSetID", masterSetID);
		paramMap.put("mktId", mktId);
		List<Map<String, Object>> couponList = Wizard.queryForList(dataSourceQuery, query, paramMap);
		
		return couponList;
	}
	
	@TrackedMethod
	public List<Map<String, Object>> getCouponTenderTypesSet(final Long restaurantId, final Long restaurantInstanceId) throws Exception{
		final String query = getDaoXml("getCouponTenderTypesSet", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("restId", restaurantId);
		paramMap.put("restInstId", restaurantInstanceId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap);
	}
	@TrackedMethod
	public List<Map<String, Object>> getTenderTypesSet(final Long restaurantId, final Long restaurantInstanceId) throws Exception{
		final String query = getDaoXml("getTenderTypesSet", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("restId", restaurantId);
		paramMap.put("restInstId", restaurantInstanceId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap);
	}
	@TrackedMethod
	public List<Map<String, Object>> getTenderTypesMarketId(final Long mktId) throws Exception{
		final String query = getDaoXml("getTenderTypesMarketId", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap);
	}
	
	private TenderType tenderTypesVals(Map<String, Object> data, List<Map<String, Object>> listOfResults){
		TenderType tenderTypeVal = new TenderType();		
		String tendId = null; 
		if(data.get("CHG_TEND_TYP_ID") != null) {
			String chandId = data.get("CHG_TEND_TYP_ID").toString();
			for(Map<String, Object> data1: listOfResults) {
				String tendTypeID = data1.get("TEND_TYP_ID").toString();
				if(tendTypeID == chandId) {
					tendId = data1.get("TEND_ID").toString();
				}
				break ;
			}
			
		}		
		List<String> tenderflag = new ArrayList<>();
		BigDecimal tendCatId = new BigDecimal(data.get("TEND_CATID").toString());
		long tendCatIdValue = tendCatId.longValue();		
		DecimalFormat decimalFormat = new DecimalFormat("#0");
		String maxchngeallw = null;
		String defSkimLimit = null;
		String defHaloLimit = null;
		String exchnRate = null;
		if(data.get("MAX_CHG_ALLW") != null) {
			BigDecimal maxchngeallwBig = new BigDecimal(data.get("MAX_CHG_ALLW").toString());
			maxchngeallw = decimalFormat.format(maxchngeallwBig.doubleValue());
			}
		if(data.get("SKIM_LMT") != null) {
			BigDecimal skimLimBig = new BigDecimal(data.get("SKIM_LMT").toString());
			defSkimLimit = decimalFormat.format(skimLimBig.doubleValue());
			}
		if(data.get("HALO_LMT") != null) {
			BigDecimal haloLimBig = new BigDecimal(data.get("HALO_LMT").toString());
			defHaloLimit = decimalFormat.format(haloLimBig.doubleValue());
			}
		if(data.get("XCNG_RATE") != null) {
			BigDecimal xchngRateBig = new BigDecimal(data.get("XCNG_RATE").toString());
			exchnRate = decimalFormat.format(xchngRateBig.doubleValue());
			}
		
		if (data.get("STUS") != null)tenderTypeVal.setStatusCode(data.get("STUS").toString());
		if (data.get("TEND_ID") != null)tenderTypeVal.setTenderId(data.get("TEND_ID").toString());
		if (data.get("FISC_INDX") != null)tenderTypeVal.setTenderFiscalIndex(data.get("FISC_INDX").toString());
		if (data.get("TEND_NA") != null)tenderTypeVal.setTenderName(data.get("TEND_NA").toString());
		tenderTypeVal.getTenderChange().setTenderChangeID(tendId);
		if (data.get("TEND_CHG_TYP") != null)tenderTypeVal.getTenderChange().setTenderChangeType(data.get("TEND_CHG_TYP").toString());
		if (data.get("RND_TO_MIN_AM") != null)tenderTypeVal.getTenderChange().setTenderChangeRoundToMinAmount(data.get("RND_TO_MIN_AM").toString());
		if (data.get("MAX_CHG_ALLW") != null)tenderTypeVal.getTenderChange().setTenderChangeMaxAllowed(maxchngeallw);
		if (data.get("MIN_CLTNG_AM") != null)tenderTypeVal.getTenderChange().setTenderChangeminCirculatingAmount(data.get("MIN_CLTNG_AM").toString());
		if (data.get("TEND_CAT_NA") != null)tenderTypeVal.setTenderCategory(data.get("TEND_CAT_NA").toString());
		if(data.get("NOT_OPEN_DRWR")!=null && data.get("NOT_OPEN_DRWR").equals(1))tenderflag.add("NOT_OPEN_DRAWER");
		if(data.get("IS_BANK_CHECK")!=null && data.get("IS_BANK_CHECK").equals(1))tenderflag.add("IS_BANK_CHECK");
		if(data.get("INCLD_IN_DPST")!=null && data.get("INCLD_IN_DPST").equals(1))tenderflag.add("INCLUDE_IN_DEPOSIT");
		if(data.get("NO_SKIM")!=null && data.get("NO_SKIM").equals(1))tenderflag.add("NO_SKIM");
		if (data.get("TAX_OPT") != null)tenderTypeVal.setTaxOption(data.get("TAX_OPT").toString());
		if (data.get("TAX_STOT_OPT") != null)tenderTypeVal.setSubtotalOption(data.get("TAX_STOT_OPT").toString());
		if (data.get("SKIM_LMT") != null)tenderTypeVal.setDefaultSkimLimit(defSkimLimit);
		if (data.get("HALO_LMT") != null)tenderTypeVal.setDefaultHaloLimit(defHaloLimit);
		if (data.get("CURN_DCMLS") != null)tenderTypeVal.setCurrencyDecimals(data.get("CURN_DCMLS").toString());
		if(data.get("TEND_CATID")!=null && tendCatIdValue == 2) {
			tenderTypeVal.getElectronicPayment().setLegacyID(data.get("LGCY_ID").toString());
		}
		else if(data.get("TEND_CATID")!=null && tendCatIdValue == 3) {
			tenderTypeVal.getGiftCoupon().setLegacyID(data.get("LGCY_ID").toString());
		}
		else if(data.get("TEND_CATID")!=null && tendCatIdValue == 6) {
			tenderTypeVal.getCreditSales().setLegacyID(data.get("LGCY_ID").toString());
		}
		else if(data.get("TEND_CATID")!=null && tendCatIdValue == 4) {
			tenderTypeVal.getOtherPayment().setLegacyID(data.get("LGCY_ID").toString());
		}
		else if(data.get("TEND_CATID")!=null && tendCatIdValue == 5) {
			tenderTypeVal.getForeignCurrency().setLegacyID(data.get("LGCY_ID").toString());
			if (data.get("XCNG_RATE") != null)tenderTypeVal.getForeignCurrency().setExchangeRate(exchnRate);
			if (data.get("OREN") != null)tenderTypeVal.getForeignCurrency().setOrientation(data.get("OREN").toString());
			if (data.get("PRCN") != null)tenderTypeVal.getForeignCurrency().setPrecision(data.get("PRCN").toString());
			if (data.get("RNDG") != null)tenderTypeVal.getForeignCurrency().setRounding(data.get("RNDG").toString());
			if (data.get("XCNG_MODE") != null)tenderTypeVal.getForeignCurrency().setExchangeMode(data.get("XCNG_MODE").toString());
			if (data.get("SYMBOL") != null)tenderTypeVal.getForeignCurrency().setSymbol(data.get("SYMBOL").toString());			
		}
		if (data.get("TEND_RNDG_RULE") != null)tenderTypeVal.setTenderRoundingRule(data.get("TEND_RNDG_RULE").toString());
		if (data.get("SYMBOL") != null)tenderTypeVal.setTenderTypeMinCirculatingAmount(data.get("MIN_CLTNG_AM").toString());		
		if(null!=tenderflag && !tenderflag.isEmpty() && tenderflag.size()>0) {
			tenderTypeVal.setTenderFlags(tenderflag);
	}
	return tenderTypeVal;
	}
	

	public List<DeliveryOrderingHours> getDeliveryOrderingHours(Long restId,String psntAreaCode, Long restInstId) throws Exception{
		final String query = getDaoXml("getDeliveryOrderingHours", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getDeliveryOrderingHours()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("rest_id", restId);
		paramMap.put("psnt_area_cd", psntAreaCode);
		paramMap.put("rest_inst_id", restInstId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<DeliveryOrderingHours> deliveryOrderingHoursList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getDeliveryOrderingHours()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 
				
			 for (final Map<String, Object> data : listOfResults) {
				 DeliveryOrderingHours deliveryOrderingHours = new DeliveryOrderingHours();
				 
				 	if (data.get("pricecode_conv") != null)
					 deliveryOrderingHours.setPriceCodeConv(String.valueOf(data.get("pricecode_conv")));
					if (data.get("mon_strt_tm") != null)
						deliveryOrderingHours.setMonStartTime(String.valueOf(data.get("mon_strt_tm")));
					if (data.get("mon_end_tm") != null)
						deliveryOrderingHours.setMonEndTime(String.valueOf(data.get("mon_end_tm")));
					if (data.get("tue_strt_tm") != null)
						deliveryOrderingHours.setTueStartTime(String.valueOf(data.get("tue_strt_tm")));
					if (data.get("tue_end_tm") != null)
						deliveryOrderingHours.setTueEndTime(String.valueOf(data.get("tue_end_tm")));
					if (data.get("wed_strt_tm") != null)
						deliveryOrderingHours.setWedStartTime(String.valueOf(data.get("wed_strt_tm")));
					if (data.get("wed_end_tm") != null)
						deliveryOrderingHours.setWedEndTime(String.valueOf(data.get("wed_end_tm")));
					if (data.get("thu_strt_tm") != null)
						deliveryOrderingHours.setThuStartTime(String.valueOf(data.get("thu_strt_tm")));
					if (data.get("thu_end_tm") != null)
						deliveryOrderingHours.setThuEndTime(String.valueOf(data.get("thu_end_tm")));
					if (data.get("fri_strt_tm") != null)
						deliveryOrderingHours.setFriStartTime(String.valueOf(data.get("fri_strt_tm")));
					if (data.get("fri_end_tm") != null)
						deliveryOrderingHours.setFriEndTime(String.valueOf(data.get("fri_end_tm")));
					if (data.get("sat_strt_tm") != null)
						deliveryOrderingHours.setSatStartTime(String.valueOf(data.get("sat_strt_tm")));
					if (data.get("sat_end_tm") != null)
						deliveryOrderingHours.setSatEndTime(String.valueOf(data.get("sat_end_tm")));
					if (data.get("sun_strt_tm") != null)
						 deliveryOrderingHours.setSunStartTime(String.valueOf(data.get("sun_strt_tm")));
					if (data.get("sun_end_tm") != null)
						 deliveryOrderingHours.setSunEndTime(String.valueOf(data.get("sun_end_tm")));
					if (data.get("psnt_area_cd") != null)
						 deliveryOrderingHours.setPsntAreaCode(String.valueOf(data.get("psnt_area_cd")));
					deliveryOrderingHoursList.add(deliveryOrderingHours);
				}
			}
		  return deliveryOrderingHoursList;
	}
	
	public List<CustomDayPart> getCustomDayPart(Long mktId, Long deliverySetId) throws Exception{
		final String query = getDaoXml("getCustomDayPart", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getCustomDayPart()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("mkt_id", mktId);
		paramMap.put("dly_set_id", deliverySetId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<CustomDayPart> customDayPartList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getCustomDayPart()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 
				
			 for (final Map<String, Object> data : listOfResults) {
				 CustomDayPart customDayPart = new CustomDayPart();
				 
				 if (data.get("data_id") != null)
					 customDayPart.setDataId(Long.valueOf(data.get("data_id").toString()));
					if (data.get("dypt_prd_na") != null)
						customDayPart.setDyptPrdName(String.valueOf(data.get("dypt_prd_na")));
					if (data.get("mon_strt_tm") != null)
						customDayPart.setMonStartTime(String.valueOf(data.get("mon_strt_tm")));
					if (data.get("mon_end_tm") != null)
						customDayPart.setMonEndTime(String.valueOf(data.get("mon_end_tm")));
					if (data.get("tue_strt_tm") != null)
						customDayPart.setTueStartTime(String.valueOf(data.get("tue_strt_tm")));
					if (data.get("tue_end_tm") != null)
						customDayPart.setTueEndTime(String.valueOf(data.get("tue_end_tm")));
					if (data.get("wed_strt_tm") != null)
						customDayPart.setWedStartTime(String.valueOf(data.get("wed_strt_tm")));
					if (data.get("wed_end_tm") != null)
						customDayPart.setWedEndTime(String.valueOf(data.get("wed_end_tm")));
					if (data.get("thu_strt_tm") != null)
						customDayPart.setThuStartTime(String.valueOf(data.get("thu_strt_tm")));
					if (data.get("thu_end_tm") != null)
						customDayPart.setThuEndTime(String.valueOf(data.get("thu_end_tm")));
					if (data.get("fri_strt_tm") != null)
						customDayPart.setFriStartTime(String.valueOf(data.get("fri_strt_tm")));
					if (data.get("fri_end_tm") != null)
						customDayPart.setFriEndTime(String.valueOf(data.get("fri_end_tm")));
					if (data.get("sat_strt_tm") != null)
						customDayPart.setSatStartTime(String.valueOf(data.get("sat_strt_tm")));
					if (data.get("sat_end_tm") != null)
						customDayPart.setSatEndTime(String.valueOf(data.get("sat_end_tm")));
					if (data.get("sun_strt_tm") != null)
						customDayPart.setSunStartTime(String.valueOf(data.get("sun_strt_tm")));
					if (data.get("sun_end_tm") != null)
						customDayPart.setSunEndTime(String.valueOf(data.get("sun_end_tm")));
					customDayPartList.add(customDayPart);
				}
			}
		  return customDayPartList;
	}
	
	public List<ChargeRules> getChargeRules(Long deliverySetId) throws Exception{
		final String query = getDaoXml("getChargeRules", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getChargeRules()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("dly_set_id", deliverySetId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<ChargeRules> chargeRulesList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getChargeRules()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 	
			 for (final Map<String, Object> data : listOfResults) {
				 ChargeRules chargeRules = new ChargeRules();
				 
				 if (data.get("order_chanel") != null)
					 chargeRules.setOrderChannel(String.valueOf(data.get("order_chanel")));
				 if (data.get("rule_id") != null)
					 chargeRules.setRuleId(Long.valueOf(data.get("rule_id").toString()));
				 if (data.get("day_part_id") != null)
					 chargeRules.setDayPartId(Long.valueOf(data.get("day_part_id").toString()));
				 if (data.get("tot_min_threeshold") != null) {				
						String longValue = getDecimalOutput(data.get("tot_min_threeshold").toString());
						BigDecimal bigDecimal = new BigDecimal (longValue);
						chargeRules.setTotMinThreshold(bigDecimal);
					 }
				 if (data.get("bin_number") != null)
					 chargeRules.setBinNumber(String.valueOf(data.get("bin_number")));
				 if (data.get("minimum_limit_minutes") != null)
					 chargeRules.setMinLimitMinutes(Long.valueOf(data.get("minimum_limit_minutes").toString()));
				 if (data.get("maximum_limit_minutes") != null)
					 chargeRules.setMaxLimitMinutes(Long.valueOf(data.get("maximum_limit_minutes").toString()));
				 if (data.get("pay_type") != null)
					 chargeRules.setPayType(String.valueOf(data.get("pay_type")));
				 chargeRulesList.add(chargeRules);
				}
			}
		  return chargeRulesList;
	}
	
	public List<MinOrderRules> getMinOrderRules(Long deliverySetId) throws Exception{
		final String query = getDaoXml("getMinOrderRules", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getMinOrderRules()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("dly_set_id", deliverySetId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<MinOrderRules> minOrderRulesList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getMinOrderRules()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 	
			 for (final Map<String, Object> data : listOfResults) {
				 MinOrderRules minOrderRules = new MinOrderRules();
				 
				 if (data.get("order_chanel") != null)
					 minOrderRules.setOrderChannel(String.valueOf(data.get("order_chanel")));
				 if (data.get("rule_id") != null)
					 minOrderRules.setRuleId(Long.valueOf(data.get("rule_id").toString()));
				 if (data.get("day_part_id") != null)
					 minOrderRules.setDayPartId(Long.valueOf(data.get("day_part_id").toString()));
				 if (data.get("ord_val") != null) { 					
					String longValue = getDecimalOutput(data.get("ord_val").toString());
					BigDecimal bigDecimal = new BigDecimal (longValue);
					 minOrderRules.setOrderValue(bigDecimal);
				 }
				 minOrderRulesList.add(minOrderRules);
				
			}
		 }
		  return minOrderRulesList;
	}
	
	
	
	
	

	private static boolean isIntegerValue(BigDecimal bd) {
		return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
	}
	
	public static boolean toCheckLastDigits(String input) {
		boolean result=false;
		if ( input.substring(input.length()-4).equals("0000")) {			
			return result;
		}else if(input.substring(input.length()-3).equals("000")) {		
			return result;
		}else if(input.substring(input.length()-2).equals("00")) {	
			return result;
		}
		else if(input.substring(input.length()-1).equals("0")) {	
			return result;
		}
		else {
			return result;
		}
	}
	
	public static BigDecimal[] convertStringTOBigDecimalArray(String input) {
		return new BigDecimal[] {new BigDecimal(input)};
		
		
	}
	
	private static String getDecimalOutput(String input) {
		DecimalFormat decimalFormatter = null;
		
		String result=null;
		boolean checkLastDigits = toCheckLastDigits(input);
		if(checkLastDigits) {	
			result= input;
			return result;
			
		}else {
			BigDecimal[] convertStringTOBigDecimal = convertStringTOBigDecimalArray(input);
			for (BigDecimal covertedValue : convertStringTOBigDecimal) {
				if (isIntegerValue(covertedValue)) {
					decimalFormatter = new DecimalFormat("##.0000");
					result= String.valueOf(covertedValue.longValue());
					return result;
				} else if (!isIntegerValue(covertedValue)) {
					decimalFormatter = new DecimalFormat("##.####");
					result=String.valueOf(decimalFormatter.format(covertedValue));
					return result;
				}
			}

		}
		return result;
	}

	
	public List<LargeOrderRules> getLargeOrderRules(Long deliverySetId,Long mktId ) throws Exception{
		final String query = getDaoXml("getLargeOrderRules", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getLargeOrderRules()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("dly_set_id", deliverySetId);
		paramMap.put("mkt_id", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<LargeOrderRules> largeOrderRulesList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getLargeOrderRules()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 	
			 for (final Map<String, Object> data : listOfResults) {
				 LargeOrderRules largeOrderRules = new LargeOrderRules();
				 
				 if (data.get("rule_id") != null)
					 largeOrderRules.setRuleId(Long.valueOf(data.get("rule_id").toString()));
				 if (data.get("order_chanel") != null)
					 largeOrderRules.setOrderChannel(String.valueOf(data.get("order_chanel")));
				 if (data.get("est_dly_min") != null)
					 largeOrderRules.setEstDeliveryLimit(Long.valueOf(data.get("est_dly_min").toString()));
				 if (data.get("tender") != null)
					 largeOrderRules.setTenderType(String.valueOf(data.get("tender")));
				 if (data.get("is_max_threeshold") != null)
					 largeOrderRules.setIsMaxThreshold(String.valueOf(data.get("is_max_threeshold")));
				 if (data.get("tot_min_threeshold") != null) {				
					String longValue = getDecimalOutput(data.get("tot_min_threeshold").toString());
					BigDecimal bigDecimal = new BigDecimal (longValue);
					largeOrderRules.setTotMinThreshold(bigDecimal);
				 }
				 if (data.get("is_conf_nee") != null)
					 largeOrderRules.setIsConfirmNeed(String.valueOf(data.get("is_conf_nee")));
				 if (data.get("mul_rid_alw") != null)
					 largeOrderRules.setMultiRiderAllowed(String.valueOf(data.get("mul_rid_alw")));
				 if (data.get("is_adv_ord") != null)
					 largeOrderRules.setIsAdvOrder(String.valueOf(data.get("is_adv_ord")));
				 if (data.get("min_rid_cou") != null)
					 largeOrderRules.setMinRiderCount(Long.valueOf(data.get("min_rid_cou").toString()));
				 if(largeOrderRules.getNotifications()==null) {
					 largeOrderRules.setNotifications(new ArrayList<>());
				 }
				 List<Notification> notificationlist = getNotification(deliverySetId);
				 if(notificationlist!= null && notificationlist.size()>0) {
					 for(Notification notification:notificationlist) {
						 if(notification.getType()==2
								 && notification.getDlySetdtl()==largeOrderRules.getRuleId() 
								 && notification.getDlySetId()==deliverySetId ) {
							 largeOrderRules.getNotifications().add(notification);
						 }
					 } 
				 }
				 largeOrderRulesList.add(largeOrderRules);
				}
			}
		  return largeOrderRulesList;
	}
	
	public List<PluLargeOrderRules> getPluLargeOrderRules(Long deliverySetId,Long mktId) throws Exception{
		final String query = getDaoXml("getPluLargeOrderRules", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getPluLargeOrderRules()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("dly_set_id", deliverySetId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<PluLargeOrderRules> pluLargeOrderRulesList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getPluLargeOrderRules()::results ->" + listOfResults.size());
	
		 if (listOfResults != null && listOfResults.size() > 0) {
			 	
			 for (final Map<String, Object> data : listOfResults) {
				 PluLargeOrderRules pluLargeOrderRules = new PluLargeOrderRules();
				 
				 if (data.get("rule_id") != null)
					 pluLargeOrderRules.setRuleId(Long.valueOf(data.get("rule_id").toString()));
				 if (data.get("order_chanel") != null)
					 pluLargeOrderRules.setOrderChannel(String.valueOf(data.get("order_chanel")));
				 if (data.get("est_dly_min") != null)
					 pluLargeOrderRules.setEstDeliveryLimit(Long.valueOf(data.get("est_dly_min").toString()));
				 if (data.get("tender") != null)
					 pluLargeOrderRules.setTenderType(String.valueOf(data.get("tender")));
				 if (data.get("is_max_threeshold") != null)
					 pluLargeOrderRules.setIsMaxThreshold(String.valueOf(data.get("is_max_threeshold")));
				 if (data.get("is_conf_nee") != null)
					 pluLargeOrderRules.setIsConfirmNeed(String.valueOf(data.get("is_conf_nee")));
				 if (data.get("mul_rid_alw") != null)
					 pluLargeOrderRules.setMultiRiderAllowed(String.valueOf(data.get("mul_rid_alw")));
				 if (data.get("is_adv_ord") != null)
					 pluLargeOrderRules.setIsAdvOrder(String.valueOf(data.get("is_adv_ord")));
				 if (data.get("mul_rid_cou") != null)
					 pluLargeOrderRules.setMulRiderCount(Long.valueOf(data.get("mul_rid_cou").toString()));
				 if (data.get("quantity") != null)
					 pluLargeOrderRules.setQuantity(Long.valueOf(data.get("quantity").toString()));
				 if(pluLargeOrderRules.getNotifications()==null) {
					 pluLargeOrderRules.setNotifications(new ArrayList<>());
				 }
				 List<Notification> notificationlist = getNotification(deliverySetId);
				 if(notificationlist!= null && notificationlist.size()>0) {
					 for(Notification notification:notificationlist) {
						 if(notification.getType()==3
								 && notification.getDlySetdtl()==pluLargeOrderRules.getRuleId() 
								 && notification.getDlySetId()==deliverySetId ) {
							 pluLargeOrderRules.getNotifications().add(notification);
						 }
					 } 
				 }
				 pluLargeOrderRulesList.add(pluLargeOrderRules);
				}
			}
		  return pluLargeOrderRulesList;
	}
		
	public List<Notification> getNotification(Long deliverySetId) throws Exception{
		final String query = getDaoXml("getNotification", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getNotification()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("dly_set_id", deliverySetId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<Notification> notificationRulesList = new ArrayList<>();
		LOGGER.info("StoreDBDAO::getNotification()::results ->" + listOfResults.size());
		List<Notification> asListOfObject=null;
		 if (listOfResults != null && listOfResults.size() > 0) {
			 	
			 asListOfObject = mapNotificationList(listOfResults, notificationRulesList);
			}
		  return asListOfObject;
	}

	private List<Notification> mapNotificationList(List<Map<String, Object>> listOfResults, List<Notification> notificationRulesList) {
		for (final Map<String, Object> data : listOfResults) {
			Notification notification=  new Notification();
			 
			 if (data.get("note") != null)
				 notification.setMessage(String.valueOf(data.get("note")));
			 if (data.get("lang_cd") != null)
				 notification.setParent(String.valueOf(data.get("lang_cd")));
			 if (data.get("na") != null)
				 notification.setName(String.valueOf(data.get("na")));
			 if (data.get("code") != null)
				 notification.setCode(String.valueOf(data.get("code")));
			 if (data.get("typ") != null)
				 notification.setType(Long.valueOf(data.get("typ").toString()));
			 if (data.get("dly_set_dtl") != null)
				 notification.setDlySetdtl(Long.valueOf(data.get("dly_set_dtl").toString()));
			 if (data.get("dly_set_id") != null)
				 notification.setDlySetId(Long.valueOf(data.get("dly_set_id").toString()));
			 notificationRulesList.add(notification);
			}
		return notificationRulesList;
	}
	
	public Long getDeliverySetId(Long restId,Long restInstId,Long mktId) throws Exception{
		final String query = getDaoXml("getDeliverySetId", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getDeliverySetId()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("rest_id", restId);
		paramMap.put("rest_inst_id", restInstId);
		paramMap.put("mkt_id", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		Long deliverySetId = null;
		for (final Map<String, Object> data : listOfResults) {
		
				if (data.get("max") != null)
					deliverySetId=Long.valueOf(data.get("max").toString());
			}
		
		 return deliverySetId;
		}
	
	public String getLargeOrderAllowed(Long deliverySetId) throws Exception{
		final String query = getDaoXml("getLargeOrderAllowed", DAOResources.STORE_DAO);
		LOGGER.info("StoreDBDAO::getLargeOrderAllowed()");
		final Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("dly_set_id", deliverySetId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		String largeOrderAllowed = null;
		for (final Map<String, Object> data : listOfResults) {
			
				if (data.get("lrg_ord_all") != null)
					largeOrderAllowed=String.valueOf(data.get("lrg_ord_all"));
			}
		
		 return largeOrderAllowed;
		}
					 

	private TenderType couponTypesVals(Map<String, Object> data, List<Map<String, Object>> listOfResults){
		TenderType couponTypeVal = new TenderType();		
		String tendId = null; 
		if(data.get("CHG_TEND_TYP_ID") != null) {
			String chandId = data.get("CHG_TEND_TYP_ID").toString();
			for(Map<String, Object> data1: listOfResults) {
				String tendTypeID = data1.get("TEND_TYP_ID").toString();
				if(tendTypeID == chandId) {
					tendId = data1.get("TEND_ID").toString();
				}
				break ;
			}
		}		
		List<String> tenderflag = new ArrayList<>();
		BigDecimal tendCatId = new BigDecimal(data.get("TEND_CATID").toString());
		long tendCatIdValue = tendCatId.longValue();
		DecimalFormat decimalFormat = new DecimalFormat("#0");
		DecimalFormat afterpoint = new DecimalFormat("##.00");
		String maxchngeallw = null;
		String defSkimLimit = null;
		String defHaloLimit = null;
		String amt = null; 
		if(data.get("MAX_CHG_ALLW") != null) {
			BigDecimal maxchngeallwBig = new BigDecimal(data.get("MAX_CHG_ALLW").toString());
			maxchngeallw = decimalFormat.format(maxchngeallwBig.doubleValue());
			}
		if(data.get("SKIM_LMT") != null) {
			BigDecimal skimLimBig = new BigDecimal(data.get("SKIM_LMT").toString());
			defSkimLimit = decimalFormat.format(skimLimBig.doubleValue());
			}
		if(data.get("HALO_LMT") != null) {
			BigDecimal haloLimBig = new BigDecimal(data.get("HALO_LMT").toString());
			defHaloLimit = decimalFormat.format(haloLimBig.doubleValue());
			}
		
		BigDecimal amtBig = new BigDecimal(data.get("AM").toString());
		double amtValue = amtBig.doubleValue();
		int amtintPart = (int) amtValue;
		if (data.get("AM") != null) {
			if ((amtValue - amtintPart) == 0) { // whole no.
				amt = decimalFormat.format(amtBig.doubleValue());
			} else {
				amt = afterpoint.format(amtBig.doubleValue());
			}
		}
		
		if (data.get("STUS") != null)couponTypeVal.setStatusCode(data.get("STUS").toString());
		if (data.get("TEND_ID") != null)couponTypeVal.setTenderId(data.get("TEND_ID").toString());
		if (data.get("FISC_INDX") != null)couponTypeVal.setTenderFiscalIndex(data.get("FISC_INDX").toString());
		if (data.get("TEND_NA") != null)couponTypeVal.setTenderName(data.get("TEND_NA").toString());
		couponTypeVal.getTenderChange().setTenderChangeID(tendId);
		if (data.get("TEND_CHG_TYP") != null)couponTypeVal.getTenderChange().setTenderChangeType(data.get("TEND_CHG_TYP").toString());
		if (data.get("RND_TO_MIN_AM") != null)couponTypeVal.getTenderChange().setTenderChangeRoundToMinAmount(data.get("RND_TO_MIN_AM").toString());
		if (data.get("MAX_CHG_ALLW") != null)couponTypeVal.getTenderChange().setTenderChangeMaxAllowed(maxchngeallw);
		if (data.get("MIN_CLTNG_AM") != null)couponTypeVal.getTenderChange().setTenderChangeminCirculatingAmount(data.get("MIN_CLTNG_AM").toString());
		if (data.get("TEND_CAT") != null)couponTypeVal.setTenderCategory(data.get("TEND_CAT").toString());
		if(data.get("NOT_OPEN_DRWR")!=null && data.get("NOT_OPEN_DRWR").equals(1))tenderflag.add("NOT_OPEN_DRAWER");
		if(data.get("IS_BANK_CHECK")!=null && data.get("IS_BANK_CHECK").equals(1))tenderflag.add("IS_BANK_CHECK");
		if(data.get("INCLD_IN_DPST")!=null && data.get("INCLD_IN_DPST").equals(1))tenderflag.add("INCLUDE_IN_DEPOSIT");
		if(data.get("NO_SKIM")!=null && data.get("NO_SKIM").equals(1))tenderflag.add("NO_SKIM");
		if (data.get("TAX_OPT") != null)couponTypeVal.setTaxOption(data.get("TAX_OPT").toString());
		if (data.get("TAX_STOT_OPT") != null)couponTypeVal.setSubtotalOption(data.get("TAX_STOT_OPT").toString());
		if (data.get("SKIM_LMT") != null)couponTypeVal.setDefaultSkimLimit(defSkimLimit);
		if (data.get("HALO_LMT") != null)couponTypeVal.setDefaultHaloLimit(defHaloLimit);
		if (data.get("CURN_DCMLS") != null)couponTypeVal.setCurrencyDecimals(data.get("CURN_DCMLS").toString());
		if(data.get("TEND_CATID")!=null && tendCatIdValue == 2) {
			couponTypeVal.getElectronicPayment().setLegacyID(data.get("LGCY_ID").toString());
		}
		else if(data.get("TEND_CATID")!=null && tendCatIdValue == 3) {
			couponTypeVal.getGiftCoupon().setLegacyID(data.get("LGCY_ID").toString());
			couponTypeVal.getGiftCoupon().setAmt(amt);
		}
		else if(data.get("TEND_CATID")!=null && tendCatIdValue == 6) {
			couponTypeVal.getCreditSales().setLegacyID(data.get("LGCY_ID").toString());
		}
		if (data.get("TEND_RNDG_RULE") != null)couponTypeVal.setTenderRoundingRule(data.get("TEND_RNDG_RULE").toString());
		if (data.get("SYMBOL") != null)couponTypeVal.setTenderTypeMinCirculatingAmount(data.get("MIN_CLTNG_AM").toString());		
		if(null!=tenderflag && !tenderflag.isEmpty() && tenderflag.size()>0) {
			couponTypeVal.setTenderFlags(tenderflag);
	}
	return couponTypeVal;
	}

	public List<ProviderDetails> getPaymentProvidersDetails(Long mktId, Long restId, Long restInstId, String effectiveDate) throws Exception {
		final String query = getDaoXml("getProvidersDetails", DAOResources.STORE_DAO);

		final Map<String, Object> paramMap = new HashMap<>();

		paramMap.put("marketId", mktId);
		paramMap.put("restId", restId);
		paramMap.put("restInstId", restInstId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);

		List<ProviderDetails> allPaymentProvidersDetails = new ArrayList<ProviderDetails>();
	
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				allPaymentProvidersDetails.add(mapPaymentProviderDetails(data));
			}
		}
		return allPaymentProvidersDetails;
	}

	private ProviderDetails mapPaymentProviderDetails(Map<String, Object> data) {
		ProviderDetails providerDetails=new ProviderDetails();
		
		if (data.get("provider_alias") != null) {
			providerDetails.setProviderAlias(data.get("provider_alias").toString());
		}
		if (data.get("provider_lex_id") != null) {
			providerDetails.setProviderLexId(new BigDecimal(data.get("provider_lex_id").toString()).longValue());
		}
		if (data.get("provider_terminal") != null) {
			providerDetails.setProviderTerminal(data.get("provider_terminal").toString());
		}
		if (data.get("provider_mail") != null) {
			providerDetails.setProviderMail(data.get("provider_mail").toString());
		}
		if (data.get("srce_wd") != null) {
			providerDetails.setSrceWd(data.get("srce_wd").toString());
		}
		if (data.get("provider_id") != null) {
			providerDetails.setProviderId(new BigDecimal(data.get("provider_id").toString()).longValue());
		} 
		
		return providerDetails;
	}

	public List<PPGGroup> getProductionPPGGroups(Long restId, Long restInstId, String effectiveDate, Long marketId,
			Long packageStatusId, Long defaultRestLocale, String defaultMktLocale, Long prdRtngSet, Long pstsnRtngSet,
				String scheduleRequestId,List<Long> rtngSetIdsList, Map<Long, ProductDetails> allProducts) throws Exception {
			final List< Map< String, Object > > localizationSets = getLocalizationSets(restId,restInstId,5009L,marketId);
	Long parentSetId = null;
	if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
			&& localizationSets.get(0).get("PREN_SET_ID") != null) {
		parentSetId = null != localizationSets.get(0).get("PREN_SET_ID")
				? Long.valueOf(localizationSets.get(0).get("PREN_SET_ID").toString())
				: null;
	}
	Long childSetId = null;
	if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
			&& localizationSets.get(0).get("CUSM_SET_ID") != null) {
		childSetId = null != localizationSets.get(0).get("CUSM_SET_ID")
				? Long.valueOf(localizationSets.get(0).get("CUSM_SET_ID").toString())
				: null;
	}
	if(parentSetId==childSetId) {
		childSetId = -1L;
	}
	
	final String query = getDaoXml("getProductionPPGGroups", DAOResources.STORE_DAO);
	LOGGER.info("storeDBDAO :: getProductionPPGGroups()");
	final Map<String, Object> paramMap = new HashMap<>();
	paramMap.put("marketId", marketId);
	paramMap.put("prdRtngSet", prdRtngSet);
	paramMap.put("psntnRtngSet", pstsnRtngSet);
	paramMap.put("defaultMktLocale", Long.valueOf(defaultMktLocale));
	paramMap.put("defaultRestLocale", defaultRestLocale);
//	paramMap.put("effectiveDate", effectiveDate);
	paramMap.put("customizedSetId", childSetId);
	paramMap.put("parentSetId", parentSetId);
//	paramMap.put("pkgStatusId", packageStatusId);
	paramMap.put("rtngSetIds", rtngSetIdsList);
	List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
	LOGGER.info("storeDBDAO :: getProductionPPGGroups()::results:"+listOfResults.size());
	List<PPGGroup> ppgGroupsList=null;
	
	List<Long> allApprovedProducts=allProducts.values().stream().map(ProductDetails::getProdPRGGroup).collect(Collectors.toList());
	LOGGER.info("allApprovedProducts : "+allApprovedProducts.size());

	if(listOfResults!=null && listOfResults.size()>0) {
		ppgGroupsList=new ArrayList<PPGGroup>();
		for(final Map<String, Object> data : listOfResults) {
			if(data.get("pmigGroupId")!=null && allApprovedProducts.contains(Long.valueOf(data.get("pmigGroupId").toString()))) {
			PPGGroup ppgGroup=new PPGGroup();
			if(data.get("PMIG_NA")!=null) {
				ppgGroup.setId(data.get("PMIG_NA").toString());
			}
			if(data.get("IMG_NA")!=null) {
				ppgGroup.setImage(data.get("IMG_NA").toString());
			}
			if(data.get("ROUTES")!=null) {
				ppgGroup.setRoutes(data.get("ROUTES").toString());
			}
			ppgGroupsList.add(ppgGroup);
			}
		}
	}	
	return ppgGroupsList;
}
	public List<DiscountTable> getDiscountTableTrue(Long restId, Long restInstId, String effectiveDate, Long mktId,String defaultMktLocale) throws Exception {
		Long asnSetType = 6013L;
		final List< Map< String, Object > > localizationSets = getLocalizationSets(restId,restInstId,asnSetType,mktId);
		Long parentSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != localizationSets.get(0).get("PREN_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		// Fetching custSetId from the localizationsets
		Long childSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("CUSM_SET_ID") != null) {
			childSetId = null != localizationSets.get(0).get("CUSM_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		if(parentSetId==childSetId) {
			childSetId = -1L;
		}
		Long masterSetId=getDiscntMasterSetId(mktId);
		final String query = getDaoXml("discountTableTrue", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		//paramMap.put("restId", restId);
		paramMap.put("defaultMktLocale", Long.parseLong(defaultMktLocale));
		paramMap.put("v_parentSetId", parentSetId);
		paramMap.put("v_customizeSetId", childSetId);
		paramMap.put("mstrSetId", masterSetId);
		paramMap.put("v_eff_date", DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<DiscountTable> discountTableList = new ArrayList<>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				discountTableList.add(mapDiscountTable(data,mktId));
			}
		}
		return discountTableList;
	}
	public List<DiscountTable> getDiscountTableFalse(Long restId, Long restInstId, String effectiveDate, Long mktId,String defaultMktLocale) throws Exception {
		Long asnSetType = 6013L;
		final List< Map< String, Object > > localizationSets = getLocalizationSets(restId,restInstId,asnSetType,mktId);
		Long parentSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != localizationSets.get(0).get("PREN_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		// Fetching custSetId from the localizationsets
		Long childSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("CUSM_SET_ID") != null) {
			childSetId = null != localizationSets.get(0).get("CUSM_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		if(parentSetId==childSetId) {
			childSetId = -1L;
		}
		final String query = getDaoXml("discountTableFalse", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		//paramMap.put("restId", restId);
		paramMap.put("defaultMktLocale", Long.parseLong(defaultMktLocale));
		paramMap.put("v_parentSetId", parentSetId);
		paramMap.put("v_customizeSetId", childSetId);
	//paramMap.put("mstrSetId", masterSetId);
		paramMap.put("v_eff_date", DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<DiscountTable> discountTableList = new ArrayList<>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				discountTableList.add(mapDiscountTable(data,mktId));
			}
		}
		return discountTableList;
	}
	public String getDiscountCellGroupBndlFlag(Long marketId) throws Exception {

		final String query = getDaoXml("getDiscountCellGroupBndlFlag", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		String discountCellBundl = null;

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("storeDBDAO :: getDiscountCellGroupBndlFlag()");
		for (final Map<String, Object> data : listOfResults) {
			discountCellBundl = data.get("DISPLAY_DISC_SALE_GROUP_BUNDLE").toString();
		}
		LOGGER.info("results for getAllowExportAddtlRtngFlag():" + discountCellBundl);
		return discountCellBundl;
	}
	public String getDiscountBreakDnFlag(Long marketId) throws Exception {

		final String query = getDaoXml("discountBreakdnFlag", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		String discountBreakdnFlag = null;

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("storeDBDAO :: getDiscountBreakDnFlag()");
		for (final Map<String, Object> data : listOfResults) {
			discountBreakdnFlag = data.get("DISCOUNT_BREAKDOWN_ENABLED").toString();
		}
		LOGGER.info("results for getDiscountBreakDnFlag():" + discountBreakdnFlag);
		return discountBreakdnFlag;
	}
	public Long getDiscntMasterSetId(Long marketId) throws Exception {

		final String query = getDaoXml("discountmstrSetId", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		Long discountMasterSetId = 0L;

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("storeDBDAO :: discountmstrSetId()");
		if(null!=listOfResults&&listOfResults.size()>0) {
		for (final Map<String, Object> data : listOfResults) {
			discountMasterSetId =Long.parseLong( data.get("set_id").toString());
		}
	 }
		LOGGER.info("results for discountmstrSetId():" + discountMasterSetId);
		return discountMasterSetId;
	}
	private DiscountTable mapDiscountTable(Map<String, Object> data,Long marketId) throws Exception {
		DiscountTable list = new DiscountTable();
		
		if(data.get("DISC_ID")!=null)list.setDiscountId(Long.parseLong(data.get("DISC_ID").toString()));
		if(data.get("disc_typ_id")!=null)list.setDiscountDescription(data.get("disc_typ_id").toString());
		if(data.get("pc_disc_rate")!=null) {
			BigDecimal bd = new BigDecimal(data.get("pc_disc_rate").toString());
			list.setDiscountRate(bd.stripTrailingZeros().toPlainString());
		}
		if(data.get("DISC_ALLOWED")!=null)list.setDiscountAllowed((data.get("DISC_ALLOWED").toString()));
		if(data.get("DISC_AM_LMT")!=null)list.setDiscountAmount(data.get("DISC_AM_LMT").toString());
		if(data.get("taxoption")!=null)list.setTaxOption(data.get("taxoption").toString());
		if(getDiscountCellGroupBndlFlag(marketId).equalsIgnoreCase("true")) {
		if(data.get("MEMC")!=null)list.setMemc(data.get("MEMC").toString());   
		if(data.get("MEMCSaleType")!=null)list.setSalesTyp(data.get("MEMCSaleType").toString());
		}
		return list;
	}
	public List<TaxChain> getTaxChainValues(Long mktId,  List<Object[]> taxTypeList, List<Object[]> taxChainList) throws Exception {
		List<TaxChain> listOfTaxChain = new ArrayList<>();
		String query = getDaoXml("getTaxChainValues", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		if(!taxTypeList.isEmpty() && null!=taxTypeList && taxTypeList.size()>0) {
		paramMap.put("taxTypeList", taxTypeList);
		}else {
			query = query.replace(":taxTypeList","null,null,null");
		}
		if(!taxChainList.isEmpty() && null!=taxChainList && taxChainList.size()>0) {
		paramMap.put("taxChainList", taxChainList);
		}else {
			query = query.replace(":taxChainList","null,null,null");
		}
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		if(null!= listOfResults && !listOfResults.isEmpty() && listOfResults.size()>0) {
			listOfResults.stream().forEach(e->{
				listOfTaxChain.add(mapTaxChain(e));
			});
		}

	
		return listOfTaxChain;
	}

	private TaxChain mapTaxChain(Map<String, Object> val) {
		TaxChain taxChain = new TaxChain();
		TaxTypeId taxTypeId = new TaxTypeId();
		List<String> ids = new ArrayList<>();
		if(null!=val.get("stus")) taxChain.setStatusCode(val.get("stus").toString());
		if(null!=val.get("tax_chn_cd"))taxChain.setTaxChainId(val.get("tax_chn_cd").toString());
		if(null!=val.get("rule")) taxChain.setRule(val.get("rule").toString());
		if(null!=val.get("tax_typ_code")) {
			if(val.get("tax_typ_code").toString().contains(";")) {
			String arrTaxTypeId[] = val.get("tax_typ_code").toString().split(";");
			for(String arr:arrTaxTypeId) {
				ids.add(arr);
			}
			}else {
				ids.add(val.get("tax_typ_code").toString());
			}
			taxTypeId.setTaxId(ids);
			taxChain.setTaxTypeId(taxTypeId);
		}
		return taxChain;

	}	
	public List<ColorDb> getColorDb(Long restId, Long restInstId, String effDate,Long marketId) throws Exception {
		Long asnSetType = 6015L;
		final List< Map< String, Object > > localizationSets = getLocalizationSets(restId,restInstId,asnSetType,marketId);
		Long parentSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != localizationSets.get(0).get("PREN_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		
		Long childSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("CUSM_SET_ID") != null) {
			childSetId = null != localizationSets.get(0).get("CUSM_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		if(parentSetId==childSetId) {
			childSetId = -1L;
		}
		
		final String query = getDaoXml("getColorDb", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("childSetId", childSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<ColorDb> colorDbList = new ArrayList<ColorDb>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				colorDbList.add(mapColorDb(data));
			}
		}
		return colorDbList;
	}
	
	private ColorDb mapColorDb(Map<String, Object> data) {
		ColorDb colorDb = new ColorDb();
		if (data.get("TRNSLTD_VAL") != null)
			colorDb.setName(String.valueOf(data.get("TRNSLTD_VAL")));
		if (data.get("HXCD") != null)
			colorDb.setValue(String.valueOf(data.get("HXCD")));		

		return colorDb;
	}
	public String getStoreDBVersion(Long marketId) throws Exception {

		final String query = getDaoXml("getStoreDBVersion", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("marketId", marketId);
		String storeDbVersion = null;

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		LOGGER.info("storeDBDAO :: getStoreDBVersion()");
		for (final Map<String, Object> data : listOfResults) {
			storeDbVersion = data.get("PACKAGE_DATA_STORE_DB_XML_VERSION").toString();
		}
		LOGGER.info("results for getStoreDBVersion():" + storeDbVersion);
		return storeDbVersion;
	}
	public List<Map<String, Object>> getPkgConfigurationAdapt(Long mktId) throws Exception {

		final String query = getDaoXml("getPkgConfigurationAdapt", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		return listOfResults;
	}

	public List<Map<String, Object>> getPkgParameters(Long mktId, Long restId, Long restInstId, String effectiveDate, String paramValue, String countryCodeFlag, String countryId, String initialFloatAmount) throws Exception {
		final String query = getDaoXml("getPkgParameters", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		paramMap.put("restId", restId);
		paramMap.put("restInstId", restInstId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("paramValue", paramValue);
		paramMap.put("countryCodeFlag", countryCodeFlag);
		paramMap.put("countryId", countryId);
		paramMap.put("initialFloatAmount", initialFloatAmount);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		return listOfResults;
	}

	public String getCountryId(Long restId, Long restInstId, String effectiveDate, Long mktId) throws Exception {
		Long asnSetType = 6015L;
		final List< Map< String, Object > > localizationSets = getLocalizationSets(restId,restInstId,asnSetType,mktId);
		Long parentSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != localizationSets.get(0).get("PREN_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		// Fetching custSetId from the localizationsets
		Long childSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("CUSM_SET_ID") != null) {
			childSetId = null != localizationSets.get(0).get("CUSM_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		if(parentSetId==childSetId) {
			childSetId = -1L;
		}
		String countryId = namesDBDAO.getCountryCode(parentSetId, childSetId, effectiveDate);
		
		return countryId;
	}

	public List<Map<String, Object>> getPkgSection(Long mktId, String countryCodeFlag, String countryId) throws Exception {
		final String query = getDaoXml("getPkgSection", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		paramMap.put("countryCodeFlag", countryCodeFlag);
		paramMap.put("countryId", countryId);

		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		return listOfResults;
	}	
	
	public List<Map<String, Object>> getAdaptorDataList(final Long mktId) throws Exception{
		final String query = getDaoXml("getAdaptorDataList", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", mktId);
		return Wizard.queryForList(dataSourceQuery, query, paramMap);
	}
	
	public List<Map<String, Object>> getsectionDataList() throws Exception{
		final String query = getDaoXml("getsectionDataList", DAOResources.STORE_DAO);
		return Wizard.queryForList(dataSourceQuery, query);
	}
	
	public List<Map<String, Object>> getParamsDataList(final Long restId, final Long restInstId, final Long mkt_id, final String effectiveDate) throws Exception{
		final String query = getDaoXml("getParamsDataList", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("rest_id", restId);
		paramMap.put("rest_inst_id", restInstId);
		paramMap.put("mkt_id", mkt_id);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		return Wizard.queryForList(dataSourceQuery, query, paramMap);
	}
	
	public List<StorePromotionDiscounts> getStorePromotionDiscounts(Long restId, Long restInstId, String effDate,Long marketId) throws Exception {
		Long asnSetType = 6033L;
		final List< Map< String, Object > > localizationSets = getLocalizationSets(restId,restInstId,asnSetType,marketId);
		Long parentSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("PREN_SET_ID") != null) {
			parentSetId = null != localizationSets.get(0).get("PREN_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("PREN_SET_ID").toString())
					: null;
		}
		
		Long customSetId = null;
		if (localizationSets != null && localizationSets.size() > 0 && localizationSets.get(0) != null
				&& localizationSets.get(0).get("CUSM_SET_ID") != null) {
			customSetId = null != localizationSets.get(0).get("CUSM_SET_ID")
					? Long.valueOf(localizationSets.get(0).get("CUSM_SET_ID").toString())
					: null;
		}
		if(parentSetId==customSetId) {
			customSetId = -1L;
		}
		
		final String query = getDaoXml("getStorePromotionDiscounts", DAOResources.STORE_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("parentSetId", parentSetId);
		paramMap.put("customSetId", customSetId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<StorePromotionDiscounts> storePromotionDiscountsList = new ArrayList<StorePromotionDiscounts>();

		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				storePromotionDiscountsList.add(mapStorePromotionDiscounts(data));
			}
		}
		return storePromotionDiscountsList;
	}
	
	private StorePromotionDiscounts mapStorePromotionDiscounts(Map<String, Object> data) {
		StorePromotionDiscounts storePromotionDiscounts = new StorePromotionDiscounts();
		if (data.get("name") != null)
			storePromotionDiscounts.setName(String.valueOf(data.get("name")));
		if (data.get("sequence") != null)
			storePromotionDiscounts.setSequence(Long.valueOf(data.get("sequence").toString()));	
		if (data.get("status") != null)
			storePromotionDiscounts.setStatus(String.valueOf(data.get("status")));
		if (data.get("initialdate") != null) {
			Date initialdate = (Date) (data.get("initialdate"));
			storePromotionDiscounts.setInitialDate(PackageGenDateUtility.Dateformat(initialdate).toUpperCase());			
		}
		if (data.get("finaldate") != null) {
			Date finaldate = (Date) (data.get("finaldate"));
			storePromotionDiscounts.setFinalDate(PackageGenDateUtility.Dateformat(finaldate).toUpperCase());
		}
		if (data.get("initialordertotalvalue") != null) {				
			String longValue = getDecimalOutput(data.get("initialordertotalvalue").toString());
			BigDecimal bigDecimal = new BigDecimal (longValue);
			storePromotionDiscounts.setInitialOrdTotVal(bigDecimal);
		 }
		if (data.get("finalordertotalvalue") != null) {				
			String longValue = getDecimalOutput(data.get("finalordertotalvalue").toString());
			BigDecimal bigDecimal = new BigDecimal (longValue);
			storePromotionDiscounts.setFinalOrdTotVal(bigDecimal);
		 }
		if (data.get("discounttype") != null)
			storePromotionDiscounts.setDiscountType(String.valueOf(data.get("discounttype")));
		if (data.get("discountvalue") != null)
			storePromotionDiscounts.setDiscountValue(String.valueOf(data.get("discountvalue")));
		
		return storePromotionDiscounts;
	}
	
}
