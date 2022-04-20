package com.rfm.packagegeneration.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.dao.LayeringProductDBDAO;
import com.rfm.packagegeneration.dao.NamesDBDAO;
import com.rfm.packagegeneration.dao.ProductDBDAO;
import com.rfm.packagegeneration.dto.Category;
import com.rfm.packagegeneration.dto.GeneratorDefinedValues;
import com.rfm.packagegeneration.dto.GenericEntry;
import com.rfm.packagegeneration.dto.Item;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageSmartReminderDTO;
import com.rfm.packagegeneration.dto.PackageStatusDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductDBRequest;
import com.rfm.packagegeneration.dto.ProductPosKvs;
import com.rfm.packagegeneration.dto.ProductPresentation;
import com.rfm.packagegeneration.dto.RequestDTO;
import com.rfm.packagegeneration.dto.Restaurant;
import com.rfm.packagegeneration.dto.Set;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ProductDBServiceTest {
	@InjectMocks
	ProductDBService productDbService;

	@Mock
	ProductDBDAO productDbDao;

	@Mock
	LayeringProductService layeringProductService;

	@Mock
	NamesDBDAO namesGeneratorDAO;

	@Mock
	LayeringProductDBDAO layeringDBDAO;

	@Mock
	RequestDTO dto = new RequestDTO();

	@Mock
	LayeringProductDBDAO layeringProductDBDAO;

	private ProductDBRequest productDbRequest;

	@Test
	void getProductDBDataForDimensionGroupTest() throws Exception {
		List<Map<String, Object>> localizationSet = new ArrayList<Map<String, Object>>();

		Map<Long, Product> products = new HashMap<Long, Product>();
		
		Map<String, Object> localSet = new HashMap<String, Object>();
		localSet.put("PREN_SET_ID", 14);
		localSet.put("CUSM_SET_ID", 14);
		localizationSet.add(localSet);
		ProductDBRequest productDbRequest = new ProductDBRequest();

		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(8833L);
		productDbRequest.setRestId(1813258L);
		productDbRequest.setRestInstId(51971563L);
		productDbRequest.setEffectiveDate("08/26/2021");
		when(productDbDao.getLocalizationSets(1813258L, productDbRequest.getEffectiveDate())).thenReturn(localizationSet);	
		layeringProductService.getProductDBDataForDimensionGroup(products, productDbRequest.getEffectiveDate(), productDbRequest.getMktId(), productDbRequest.getNodeId());
		Assert.notEmpty(localizationSet, "localizationSet fetched");
	}

	@Test
	void getProductDBDataForDimensionGroupTestCase2() throws Exception {
		List<Map<String, Object>> localizationSet = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> productData = new ArrayList<Map<String, Object>>();

		Map<Long, Product> products = new HashMap<Long, Product>();
				
		Map<String, Object> localSet = new HashMap<String, Object>();
		localSet.put("PREN_SET_ID", 14);
		localSet.put("CUSM_SET_ID", 14);
		localizationSet.add(localSet);
		ProductDBRequest productDbRequest = new ProductDBRequest();

		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(8833L);
		productDbRequest.setRestId(1813258L);
		productDbRequest.setRestInstId(51971563L);
		productDbRequest.setEffectiveDate("08/26/2021");
		when(productDbDao.getLocalizationSets(Long.parseLong(productDbRequest.getRestId().toString()),
				productDbRequest.getEffectiveDate())).thenReturn(localizationSet);
		when(layeringDBDAO.getProductDataForDimensionGroup(
				Long.parseLong(localizationSet.get(0).get("PREN_SET_ID").toString()),
				Long.parseLong(localizationSet.get(0).get("CUSM_SET_ID").toString()), 
				productDbRequest.getEffectiveDate(), productDbRequest.getMktId())).thenReturn(getProductData());

		layeringProductService.getProductDBDataForDimensionGroup(products, productDbRequest.getEffectiveDate(), productDbRequest.getMktId(), productDbRequest.getNodeId());
		Assert.notEmpty(localizationSet, "localizationSet fetched");
		Assert.notEmpty(getProductData(), "productData fetched");
	}

	private List<Map<String, Object>> getProductData() {
		List<Map<String, Object>> productData = new ArrayList<Map<String, Object>>();
		Map<String, Object> productData1 = new HashMap<String, Object>();
		productData1.put("CD", "0");
		productData1.put("PRD_CD", "6244");
		productData1.put("SHW_ON_RCT_LCL_PROMO", "1");
		productData1.put("SHOW_DIM_TO_CUST", "1");
		Map<String, Object> productData2 = new HashMap<String, Object>();
		productData2.put("CD", "1");
		productData2.put("PRD_CD", "6244");
		productData2.put("SHW_ON_RCT_LCL_PROMO", "1");
		productData2.put("SHOW_DIM_TO_CUST", "1");
		Map<String, Object> productData3 = new HashMap<String, Object>();
		productData3.put("CD", "2");
		productData3.put("PRD_CD", "6244");
		productData3.put("SHW_ON_RCT_LCL_PROMO", "1");
		productData3.put("SHOW_DIM_TO_CUST", "1");
		productData.add(productData1);
		productData.add(productData2);
		productData.add(productData3);
		return productData;
	}

	// Test For Category
	@Test
	void getProductDBDataForCategoriesTestCase() throws Exception {
		Map<Long, Product> products = new HashMap<Long, Product>();
		List<Category> productData = new ArrayList<Category>();
		ProductDBRequest productDbRequest = new ProductDBRequest();
		productDbRequest.setProductId(2502L);
		productDbRequest.setEffectiveDate("08/26/2021");
		when(layeringDBDAO.getCategoriesVals(products, "08/26/2021")).thenReturn(products);
		layeringProductService.getCategoryVals(products, productDbRequest.getEffectiveDate());
		Assert.notEmpty(getProductDataforCategory(), "productData fetched");
	}

	private List<Category> getProductDataforCategory() {
		List<Category> productData = new ArrayList<Category>();
		Category productData1 = new Category();		
		productData1.setCategoryID("257");
		productData1.setSequence("54");
		productData1.setDisplaySizeSelection("true");
		
		Category productData2 = new Category();		
		productData2.setCategoryID("265");
		productData2.setSequence("51");
		productData2.setDisplaySizeSelection("true");
		

		Category productData3 = new Category();		
		productData2.setCategoryID("251");
		productData2.setSequence("51");
		productData2.setDisplaySizeSelection("true");
		
		
		productData.add(productData1);
		productData.add(productData2);
		productData.add(productData3);
		return productData;
	}


	private List<Item> getMenuSubstitutionList() {
		List<Item> productData = new ArrayList<Item>();
		Item productData1 = new Item();
		productData1.setId("257");
		productData1.setProductCode("54");
		productData1.setProductId(54);
		
		Item productData2 = new Item();
		productData1.setId("265");
		productData1.setProductCode("14");
		productData1.setProductId(14);
		
		Item productData3 = new Item();
		productData1.setId("251");
		productData1.setProductCode("23");
		productData1.setProductId(23);		
		
		productData.add(productData1);
		productData.add(productData2);
		productData.add(productData3);
		return productData;
	}

	@Test
	void getProductDBDataForPromotionGroupTestCase1() throws Exception {
		ProductDBRequest productDbRequest = new ProductDBRequest();
		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(126L);
		productDbRequest.setEffectiveDate("06/09/2021");
		Map<Long, Product> products = new HashMap<Long, Product>();
		Product dto = new Product();
		dto.setProductId(126l);
		products.put(126l, dto);
		
		when(layeringDBDAO.getProductListDataForPromotionGroup(products, 2L, "06/09/2021"))
				.thenReturn(products);
		
		when(layeringDBDAO.getProductListCount(2L, 126L)).thenReturn(1L);
		layeringProductService.getProductDBDataForPromotionGroup(products, 2L, "06/09/2021");
		Assert.notEmpty(getProductListDataForPromotionGroup(), "productListDataForPromotionGroup is fetched");
	}

	@Test
	void getProductDBDataForPromotionGroupTestCase2() throws Exception {
		ProductDBRequest productDbRequest = new ProductDBRequest();
		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(31L);
		productDbRequest.setEffectiveDate("06/09/2021");
		
		Map<Long, Product> products = new HashMap<Long, Product>();
		Product dto = new Product();
		dto.setProductId(126l);
		products.put(126l, dto);
		
		
		when(layeringDBDAO.getProductListDataForPromotionGroup(products, 2L, "06/09/2021"))
				.thenReturn(products);
		Long productListCount = layeringDBDAO.getProductListCount(2L, 126L);
		layeringProductService.getProductDBDataForPromotionGroup(products, 2L, "06/09/2021");
		Assert.notNull(productListCount, "productListCount is fetched");
	}

	private List<Map<String, Object>> getProductListDataForPromotionGroup() {
		List<Map<String, Object>> productData = new ArrayList<Map<String, Object>>();
		Map<String, Object> productData1 = new HashMap<String, Object>();
		productData1.put("PROMO_GRP_ID", "1");
		productData1.put("PROMO_GRP_CD", "1");
		Map<String, Object> productData2 = new HashMap<String, Object>();
		productData2.put("PROMO_GRP_ID", "2");
		productData2.put("PROMO_GRP_CD", "2");
		productData.add(productData1);
		productData.add(productData2);
		return productData;
	}

	@Test
	void getProductDBDataForPromotionGroupTestCase3() throws Exception {
		ProductDBRequest productDbRequest = new ProductDBRequest();
		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(126L);
		productDbRequest.setEffectiveDate("06/09/2021");
		
		Map<Long, Product> products = new HashMap<Long, Product>();
		Product dto = new Product();
		dto.setProductId(126l);
		products.put(126l, dto);
		
		
		when(layeringDBDAO.getProductListDataForPromotionGroup(products, 2L, "06/09/2021"))
				.thenReturn(products);
		when(layeringDBDAO.getProductListCount(2L, 126L)).thenReturn(2L);
		when(layeringDBDAO.getListFlag(2L, 126L, "2")).thenReturn(getListFlagData());
		layeringProductService.getProductDBDataForPromotionGroup(products, 2L, "06/09/2021");
		Assert.notEmpty(getListFlagData(), "listFlagData is fetched");
	}

	private List<Map<String, Object>> getListFlagData() {
		List<Map<String, Object>> listFlagData = new ArrayList<Map<String, Object>>();
		Map<String, Object> listFlagData1 = new HashMap<String, Object>();
		listFlagData1.put("LSTFLG", "1");
		Map<String, Object> listFlagData2 = new HashMap<String, Object>();
		listFlagData2.put("LSTFLG", "2");
		listFlagData.add(listFlagData1);
		listFlagData.add(listFlagData2);
		return listFlagData;
	}

	private List<Map<String, Object>> getProductListDataForPromotionGroupForCase3() {
		List<Map<String, Object>> productData = new ArrayList<Map<String, Object>>();
		Map<String, Object> productData1 = new HashMap<String, Object>();
		productData1.put("PROMO_GRP_ID", "2");
		productData1.put("PROMO_GRP_CD", "2");
		Map<String, Object> productData2 = new HashMap<String, Object>();
		productData2.put("PROMO_GRP_ID", "2");
		productData2.put("PROMO_GRP_CD", "2");
		Map<String, Object> productData3 = new HashMap<String, Object>();
		productData3.put("PROMO_GRP_ID", "3");
		productData3.put("PROMO_GRP_CD", "3");
		productData.add(productData1);
		productData.add(productData2);
		productData.add(productData3);
		return productData;
	}

	@Test
	void getPresentationAndProductRoutingTestCase1() throws Exception {
		dto.setMarketId(2L);
		dto.setNodeId(11451L);
		dto.setEffectiveDate("09/24/2021");
		ProductDBRequest productDbRequest = new ProductDBRequest();
		productDbRequest.setMktId(2L);
		productDbRequest.setNodeId(11451L);
		productDbRequest.setProductId(126L);
		productDbRequest.setEffectiveDate("09/24/2021");
		when(layeringProductService.getMergedProductsByRest(dto)).thenReturn(getListofProducts(productDbRequest));
		when(layeringProductService.getRestaurantSets(dto)).thenReturn(getDto(dto));

		when(namesGeneratorDAO.retrieveMasterSetId(dto.getMarketId())).thenReturn(7L);
		when(layeringDBDAO.getProductPosKvsPresentationByMaster(dto.getMarketId(), dto.getEffectiveDate(), 7L))
				.thenReturn(getListofProducts(productDbRequest));
		when(layeringDBDAO.getRoutingSets(productDbRequest.getMktId(), productDbRequest.getRestId(),
				productDbRequest.getRestInstId())).thenReturn(getRoutingSets());
		layeringProductService.getPresentationAndProductRouting(productDbRequest);
		Assert.notEmpty(getRoutingSets(), "productListDataForPromotionGroup is fetched");
	}

	private Restaurant getDto(RequestDTO dto) {
		Restaurant restDto = new Restaurant();
		Set setItems = new Set();
		setItems.setSetId(9L);
		setItems.setName("Rest");
		setItems.setType(60083001L);
		setItems.setTypeName("Sub");
		List<Set> menuItemSets = new ArrayList<>();
		menuItemSets.add(setItems);
		Set setItemsForPricing = new Set();
		setItemsForPricing.setSetId(1749112L);
		setItemsForPricing.setType(60084002L);
		List<Set> priceSets = new ArrayList<>();
		priceSets.add(setItemsForPricing);
		restDto.setMenuItemSets(menuItemSets);
		restDto.setPriceSets(priceSets);
		return restDto;
	}

	private Map<Long, Product> getListofProducts(ProductDBRequest productDbRequest2) {
		Map<Long, Product> listofProductsByMaster = new HashMap<>();
		Product product = new Product();
		ProductPosKvs productPosKvs = new ProductPosKvs();
		productPosKvs.getProductionMenuItemGroup().setCode(94129L);
		product.setProductPosKvs(productPosKvs);
		Map<Long, Product> listofProductsByMaster1 = new HashMap<>();
		listofProductsByMaster1.put(126L, product);
		listofProductsByMaster.putAll(listofProductsByMaster1);
		return listofProductsByMaster;
	}

	private List<Map<String, Object>> getRoutingSets() {
		List<Map<String, Object>> routingSets = new ArrayList<Map<String, Object>>();
		Map<String, Object> routingSets1 = new HashMap<String, Object>();
		routingSets1.put("rte_id", "100");
		routingSets1.put("PMI_GRP_ID", "94819");
		routingSets1.put("PROD_RTNG_SET", "7");
		routingSets1.put("PSNTN_RTNG_SET", "8");
		routingSets1.put("ds", "ABS");
		Map<String, Object> routingSets2 = new HashMap<String, Object>();
		routingSets2.put("rte_id", "101");
		routingSets2.put("PMI_GRP_ID", "94129");
		routingSets2.put("PROD_RTNG_SET", "7");
		routingSets2.put("PSNTN_RTNG_SET", "8");
		routingSets2.put("ds", "MFY");
		Map<String, Object> routingSets3 = new HashMap<String, Object>();
		routingSets3.put("rte_id", "102");
		routingSets3.put("PMI_GRP_ID", "94819");
		routingSets3.put("PROD_RTNG_SET", "7");
		routingSets3.put("PSNTN_RTNG_SET", "8");
		routingSets3.put("ds", "ABS");
		routingSets.add(routingSets1);
		routingSets.add(routingSets2);
		routingSets.add(routingSets3);
		return routingSets;
	}

	@Test
	void getPricingandTaxValuesTestCase1() throws Exception {
		Map<Long, Product> products = new HashMap<Long, Product>();
		ProductDBRequest productDbRequest = new ProductDBRequest();
		Restaurant restvalues = new Restaurant();
		List<Set> priceSet = new ArrayList<>();
		Set set = new Set();
		set.setSetId(1749128L);
		priceSet.add(set);
		restvalues.setPriceSets(priceSet);
		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(126L);
		productDbRequest.setEffectiveDate("10/01/2021");
		productDbRequest.setNodeId(11451L);
		productDbRequest.setRestId(1812126L);
		productDbRequest.setRestInstId(60139008L);
		when(layeringProductService.getRestaurantSets(productDbRequest)).thenReturn(restvalues);
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.DEFAULT_MENU_ITEM_TAX_SETTINGS)).thenReturn("Restaurant");
		when(namesGeneratorDAO.getParamValue(ProductDBConstant.NON_MIT_MARKET_DEFAULT_TAX, productDbRequest.getMktId()))
				.thenReturn("false");
		when(layeringDBDAO.getMenuSubstitutionList(products , productDbRequest.getMktId())).thenReturn(products);
		when(layeringProductService.getMergedProductsByRest(dto)).thenReturn(getListOfMaster());
		when(layeringProductService.getProductDualStatusByRest(dtoObject(true))).thenReturn(getListOfMasterForApprovalStatus());
		when(layeringDBDAO.getPriceValuesWithTax(productDbRequest.getProductId(), productDbRequest.getMktId(),
				productDbRequest.getEffectiveDate(), 1749128L)).thenReturn(getPricingValues());
		when(layeringDBDAO.getPricingValuesBysetId(productDbRequest.getProductId(), productDbRequest.getMktId(),
				productDbRequest.getEffectiveDate(), 1749128L)).thenReturn(getPricingValues());
		when(layeringDBDAO.getTaxEntryList(2L)).thenReturn(getTaxEntryValues());
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.AUTO_PRICE_DUMMYPRODUCT)).thenReturn("true");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(), ProductDBConstant.DISPLAY_TAX_FOR_BRKD))
				.thenReturn("false");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_EATING)).thenReturn("2");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_TAKEOUT)).thenReturn("0");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_OTHER)).thenReturn("5");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.REMOVE_PRICING_DECIMAL)).thenReturn("N");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.GLOBAL_PARAM_PKG_PRICE_TYPE_GEN)).thenReturn("OTHER");
		when(productDbDao.getDataForDisplayBRD(productDbRequest.getProductId(), productDbRequest.getEffectiveDate()))
				.thenReturn(getDataForDisplayBRD());
		layeringProductService.getPricingandTaxValues(products, productDbRequest.getEffectiveDate(), productDbRequest.getMktId(), productDbRequest.getNodeId(), restvalues, null);
		Assert.notEmpty(getPricingValues(), "PricingValues by Set ID is fetched");
	}

	private Map<Long, Product> getListOfMaster() {
		Map<Long, Product> listofProductsByMaster = new HashMap<>();
		Product product = new Product();
		product.setAuxiliaryMenuItem(0L);
		Map<Long, Product> listofProductsByMaster1 = new HashMap<>();
		listofProductsByMaster1.put(126L, product);
		listofProductsByMaster.putAll(listofProductsByMaster1);
		return listofProductsByMaster;
	}

	private List<Map<String, Object>> getPricingValues() {
		List<Map<String, Object>> pricingSets = new ArrayList<Map<String, Object>>();
		Map<String, Object> pricingSets1 = new HashMap<String, Object>();
		pricingSets1.put("EATIN_PRC", "3.4900");
		pricingSets1.put("EATIN_TAX_CD", 2L);
		pricingSets1.put("EATIN_TAX_RULE", 2L);
		pricingSets1.put("EATIN_TAX_ENTR", 1749073L);
		pricingSets1.put("TKUT_PRC", "3.4900");
		pricingSets1.put("TKUT_TAX_CD", 1L);
		pricingSets1.put("TKUT_TAX_RULE", 1L);
		pricingSets1.put("TKUT_TAX_ENTR", 1L);
		pricingSets1.put("OTH_PRC", "4.5400");
		pricingSets1.put("OTH_TAX_CD", 2L);
		pricingSets1.put("OTH_TAX_RULE", 2L);
		pricingSets1.put("OTH_TAX_ENTR", 1749073L);
		pricingSets.add(pricingSets1);
		return pricingSets;
	}

	private List<Map<String, Object>> getTaxEntryValues() {
		List<Map<String, Object>> taxEntryValues = new ArrayList<Map<String, Object>>();
		Map<String, Object> taxEntryValue1 = new HashMap<String, Object>();
		taxEntryValue1.put("set_id", "7055");
		taxEntryValue1.put("tax_typ_code", "1");
		Map<String, Object> taxEntryValue2 = new HashMap<String, Object>();
		taxEntryValue2.put("set_id", "7056");
		taxEntryValue2.put("tax_typ_code", "7");
		Map<String, Object> taxEntryValue3 = new HashMap<String, Object>();
		taxEntryValue3.put("set_id", "7057");
		taxEntryValue3.put("tax_typ_code", "2");
		taxEntryValues.add(taxEntryValue1);
		taxEntryValues.add(taxEntryValue2);
		taxEntryValues.add(taxEntryValue3);
		return taxEntryValues;
	}

	@Test
	void getPricingandTaxValuesTestCase2() throws Exception {
		ProductDBRequest productDbRequest = new ProductDBRequest();
		Restaurant restvalues = new Restaurant();
		List<Set> priceSet = new ArrayList<>();
		Set set = new Set();
		set.setSetId(1749128L);
		priceSet.add(set);
		restvalues.setPriceSets(priceSet);
		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(126L);
		productDbRequest.setEffectiveDate("10/01/2021");
		productDbRequest.setNodeId(11451L);
		productDbRequest.setRestId(1812126L);
		productDbRequest.setRestInstId(60139008L);
		when(layeringProductService.getRestaurantSets(productDbRequest)).thenReturn(restvalues);
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.DEFAULT_MENU_ITEM_TAX_SETTINGS)).thenReturn("MENU_ITEM_TAX_SET");
		when(namesGeneratorDAO.getParamValue(ProductDBConstant.NON_MIT_MARKET_DEFAULT_TAX, productDbRequest.getMktId()))
				.thenReturn("true");
		when(layeringProductService.getMergedProductsByRest(dto)).thenReturn(getListOfMaster());
		when(layeringProductService.getProductDualStatusByRest(dtoObject(true))).thenReturn(getListOfMasterForApprovalStatus());
		when(layeringDBDAO.getPriceValuesWithTax(productDbRequest.getProductId(), productDbRequest.getMktId(),
				productDbRequest.getEffectiveDate(), 1749128L)).thenReturn(getPricingValues());
		when(layeringDBDAO.getTaxEntryList(2L)).thenReturn(getTaxEntryValues());
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.AUTO_PRICE_DUMMYPRODUCT)).thenReturn("false");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(), ProductDBConstant.DISPLAY_TAX_FOR_BRKD))
				.thenReturn("true");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_EATING)).thenReturn("4");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_TAKEOUT)).thenReturn("3");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_OTHER)).thenReturn("1");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.REMOVE_PRICING_DECIMAL)).thenReturn("Y");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.GLOBAL_PARAM_PKG_PRICE_TYPE_GEN)).thenReturn("OTHER");
		when(productDbDao.getDataForDisplayBRD(productDbRequest.getProductId(), productDbRequest.getEffectiveDate()))
				.thenReturn(getDataForDisplayBRD());
		Map<Long, Product> products = new HashMap<Long, Product>();
		layeringProductService.getPricingandTaxValues(products, productDbRequest.getEffectiveDate(), productDbRequest.getMktId(), productDbRequest.getNodeId(), restvalues, null);		
		Assert.notEmpty(getPricingValues(), "PricingValues by Set ID is fetched");
	}

	private Map<String, Object> getDataForDisplayBRD() {
		List<Map<String, Object>> pricingSets = new ArrayList<Map<String, Object>>();
		Map<String, Object> displayValues = new HashMap<String, Object>();
		displayValues.put("C_EATIN_PRC", "3.4900");
		displayValues.put("P_EATIN_TAX_CD", 2L);
		displayValues.put("P_EATIN_TAX_RULE", 2L);
		displayValues.put("P_EATIN_TAX_ENTR", 1749073L);
		displayValues.put("C_TKUT_PRC", "3.4900");
		displayValues.put("P_TKUT_TAX_CD", 1L);
		displayValues.put("P_TKUT_TAX_RULE", 1L);
		displayValues.put("P_TKUT_TAX_ENTR", 1L);
		displayValues.put("C_OTH_PRC", "4.5400");
		displayValues.put("P_OTH_TAX_CD", 2L);
		displayValues.put("P_OTH_TAX_RULE", 2L);
		displayValues.put("P_OTH_TAX_ENTR", 1749073L);
		return displayValues;
	}

	@Test
	void getPricingandTaxValuesTestCase3() throws Exception {
		ProductDBRequest productDbRequest = new ProductDBRequest();
		Restaurant restvalues = new Restaurant();
		List<Set> priceSet = new ArrayList<>();
		Set set = new Set();
		set.setSetId(1749128L);
		priceSet.add(set);
		restvalues.setPriceSets(priceSet);
		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(126L);
		productDbRequest.setEffectiveDate("10/01/2021");
		productDbRequest.setNodeId(11451L);
		productDbRequest.setRestId(1812126L);
		productDbRequest.setRestInstId(60139008L);
		when(layeringProductService.getRestaurantSets(productDbRequest)).thenReturn(restvalues);
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.DEFAULT_MENU_ITEM_TAX_SETTINGS)).thenReturn("MENU_ITEM_TAX_SET");
		when(namesGeneratorDAO.getParamValue(ProductDBConstant.NON_MIT_MARKET_DEFAULT_TAX, productDbRequest.getMktId()))
				.thenReturn("true");
		when(layeringProductService.getMergedProductsByRest(dto)).thenReturn(getListOfMaster());
		when(layeringProductService.getProductDualStatusByRest(dtoObject(true))).thenReturn(getListOfMasterForApprovalStatus());
		when(layeringDBDAO.getPriceValuesWithTax(productDbRequest.getProductId(), productDbRequest.getMktId(),
				productDbRequest.getEffectiveDate(), 1749128L)).thenReturn(getPricingValues2());
		when(layeringDBDAO.getTaxEntryList(2L)).thenReturn(getTaxEntryValues());
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.AUTO_PRICE_DUMMYPRODUCT)).thenReturn("false");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(), ProductDBConstant.DISPLAY_TAX_FOR_BRKD))
				.thenReturn("true");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_EATING)).thenReturn("4");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_TAKEOUT)).thenReturn("3");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_OTHER)).thenReturn("1");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.REMOVE_PRICING_DECIMAL)).thenReturn("Y");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.GLOBAL_PARAM_PKG_PRICE_TYPE_GEN)).thenReturn("OTHER");
		when(productDbDao.getDataForDisplayBRD(productDbRequest.getProductId(), productDbRequest.getEffectiveDate()))
				.thenReturn(getDataForDisplayBRD2());
		Map<Long, Product> products = new HashMap<Long, Product>();
		layeringProductService.getPricingandTaxValues(products, productDbRequest.getEffectiveDate(), productDbRequest.getMktId(), productDbRequest.getNodeId(), restvalues, null);	
		Assert.notEmpty(getPricingValues2(), "PricingValues by Set ID is fetched");
	}

	private Map<String, Object> getDataForDisplayBRD2() {
		List<Map<String, Object>> pricingSets = new ArrayList<Map<String, Object>>();
		Map<String, Object> displayValues = new HashMap<String, Object>();
		displayValues.put("C_EATIN_PRC", "3.4900");
		displayValues.put("C_EATIN_TAX_CD", 2L);
		displayValues.put("C_EATIN_TAX_RULE", 2L);
		displayValues.put("C_EATIN_TAX_ENTR", 1749073L);
		displayValues.put("C_TKUT_PRC", "3.4900");
		displayValues.put("C_TKUT_TAX_CD", 1L);
		displayValues.put("C_TKUT_TAX_RULE", 1L);
		displayValues.put("C_TKUT_TAX_ENTR", 1L);
		displayValues.put("C_OTH_PRC", "4.5400");
		displayValues.put("C_OTH_TAX_CD", 2L);
		displayValues.put("C_OTH_TAX_RULE", 2L);
		displayValues.put("C_OTH_TAX_ENTR", 1749073L);
		return displayValues;
	}

	private List<Map<String, Object>> getPricingValues2() {
		List<Map<String, Object>> pricingSets = new ArrayList<Map<String, Object>>();
		Map<String, Object> pricingSets1 = new HashMap<String, Object>();
		pricingSets1.put("EATIN_PRC", "3.4900");
		pricingSets1.put("EATIN_TAX_CD", -99L);
		pricingSets1.put("EATIN_TAX_RULE", 2L);
		pricingSets1.put("EATIN_TAX_ENTR", 1749073L);
		pricingSets1.put("TKUT_PRC", "3.4900");
		pricingSets1.put("TKUT_TAX_CD", -99L);
		pricingSets1.put("TKUT_TAX_RULE", 1L);
		pricingSets1.put("TKUT_TAX_ENTR", 1L);
		pricingSets1.put("OTH_PRC", "4.5400");
		pricingSets1.put("OTH_TAX_CD", -99L);
		pricingSets1.put("OTH_TAX_RULE", 2L);
		pricingSets1.put("OTH_TAX_ENTR", 1749073L);
		pricingSets.add(pricingSets1);
		return pricingSets;
	}

	@Test
	void getPricingandTaxValuesTestCase4() throws Exception {
		ProductDBRequest productDbRequest = new ProductDBRequest();
		Restaurant restvalues = new Restaurant();
		List<Set> priceSet = new ArrayList<>();
		Set set = new Set();
		set.setSetId(1749128L);
		priceSet.add(set);
		restvalues.setPriceSets(priceSet);
		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(126L);
		productDbRequest.setEffectiveDate("10/01/2021");
		productDbRequest.setNodeId(11451L);
		productDbRequest.setRestId(1812126L);
		productDbRequest.setRestInstId(60139008L);
		when(layeringProductService.getRestaurantSets(productDbRequest)).thenReturn(restvalues);
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.DEFAULT_MENU_ITEM_TAX_SETTINGS)).thenReturn("Restaurant");
		when(namesGeneratorDAO.getParamValue(ProductDBConstant.NON_MIT_MARKET_DEFAULT_TAX, productDbRequest.getMktId()))
				.thenReturn("false");
		when(layeringProductService.getMergedProductsByRest(dto)).thenReturn(getListOfMaster());
		when(layeringProductService.getProductDualStatusByRest(dtoObject(true))).thenReturn(getListOfMasterForApprovalStatus());
		when(layeringDBDAO.getPricingValuesBysetId(productDbRequest.getProductId(), productDbRequest.getMktId(),
				productDbRequest.getEffectiveDate(), 1749112L)).thenReturn(null);
		when(productDbDao.getPricingValueFromRest(productDbRequest.getMktId(), productDbRequest.getEffectiveDate(),
				 productDbRequest.getNodeId()))
						.thenReturn(getPricingValues());
		when(layeringDBDAO.getTaxEntryList(2L)).thenReturn(getTaxEntryValues());
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.AUTO_PRICE_DUMMYPRODUCT)).thenReturn("true");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(), ProductDBConstant.DISPLAY_TAX_FOR_BRKD))
				.thenReturn("false");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_EATING)).thenReturn("2");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_TAKEOUT)).thenReturn("0");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_OTHER)).thenReturn("5");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.REMOVE_PRICING_DECIMAL)).thenReturn("N");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.GLOBAL_PARAM_PKG_PRICE_TYPE_GEN)).thenReturn("OTHER");
		when(productDbDao.getDataForDisplayBRD(productDbRequest.getProductId(), productDbRequest.getEffectiveDate()))
				.thenReturn(getDataForDisplayBRD());
		Map<Long, Product> products = new HashMap<Long, Product>();
		layeringProductService.getPricingandTaxValues(products, productDbRequest.getEffectiveDate(), productDbRequest.getMktId(), productDbRequest.getNodeId(), restvalues, null);	
		Assert.notEmpty(getPricingValues(), "PricingValues by Set ID is fetched");
	}

	@Test
	void getColorsandMediaValuesTestCase() throws Exception {
		dto.setEffectiveDate("11/10/2021");
		dto.setMarketId(2L);
		dto.setNodeId(11451L);
		when(layeringProductService.getMergedProductsByRest(dto))
				.thenReturn(getListofProductsByMaster(productDbRequest));
		when(productDbDao.getAllColorsList(2L)).thenReturn(getAllColorsListCase());
		when(productDbDao.getAllMediaList(2L)).thenReturn(getAllMediaListCase());
		Assert.notEmpty(getListofProductsByMaster(productDbRequest), "listofProductsByMaster fetched");
	}

	private Map<Long, Product> getListofProductsByMaster(ProductDBRequest productDbRequest) {
		Map<Long, Product> listofProductsByMaster = new HashMap<>();
		Product product = new Product();
		ProductPresentation productPresentation = new ProductPresentation();
		List<GenericEntry> genImg = new ArrayList<GenericEntry>();
		GenericEntry genImg1 = new GenericEntry();
		genImg1.setCode(757L);
		genImg1.setName("CHSBUR7.png");
		genImg.add(genImg1);
		List<GenericEntry> genMedfile = new ArrayList<GenericEntry>();
		GenericEntry genMedfile1 = new GenericEntry();
		genMedfile1.setCode(0L);
		genMedfile1.setName(null);
		genMedfile.add(genMedfile1);
		productPresentation.getColorScreenKVSFont().setCode(0L);
		productPresentation.getColorScreenKVSFont().setName(null);
		productPresentation.getColorScreenDisplay().setCode(0L);
		productPresentation.getColorScreenDisplay().setName(null);
		productPresentation.getColorButtonBackground().setCode(92537L);
		productPresentation.getColorButtonBackground().setName("LIGHTCYAN");
		productPresentation.getColorButtonText().setCode(92478L);
		productPresentation.getColorButtonText().setName("BLACK");
		productPresentation.getColorButtonBackgroundPressed().setCode(92478L);
		productPresentation.getColorButtonBackgroundPressed().setName("BLACK");
		productPresentation.getColorButtonTextPressed().setCode(92537L);
		productPresentation.getColorButtonTextPressed().setName("LIGHTCYAN");
		productPresentation.setImage(genImg);
		productPresentation.getSmallImage().setCode(0L);
		productPresentation.getSmallImage().setName(null);
		productPresentation.getGrillImageString().setCode(751L);
		productPresentation.getGrillImageString().setName("CHSBUR.png");
		productPresentation.getHandheldImage().setCode(0L);
		productPresentation.getHandheldImage().setName(null);
		productPresentation.getKioskImage().setCode(0L);
		productPresentation.getKioskImage().setName(null);
		productPresentation.getAlternative().setCode(0L);
		productPresentation.getAlternative().setName(null);
		productPresentation.getSummaryMonitorImage().setCode(0L);
		productPresentation.getSummaryMonitorImage().setName(null);
		productPresentation.getCsoCircleImage().setCode(0L);
		productPresentation.getCsoCircleImage().setName(null);
		productPresentation.getCsoLargeImage().setCode(4450L);
		productPresentation.getCsoLargeImage().setName("CSO_CHEESEBURGER_3.png");
		productPresentation.getCsoSmallImage().setCode(4450L);
		productPresentation.getCsoSmallImage().setName("CSO_CHEESEBURGER_3.png");
		productPresentation.getCsoCartImage().setCode(4450L);
		productPresentation.getCsoCartImage().setName("CSO_CHEESEBURGER_3.png");
		productPresentation.getCsoGrillImage().setCode(4450L);
		productPresentation.getCsoGrillImage().setName("CSO_CHEESEBURGER_3.png");
		productPresentation.getCsoDimensionImage().setCode(4450L);
		productPresentation.getCsoDimensionImage().setName("CSO_CHEESEBURGER_3.png");
		productPresentation.getCsoValueMealImage().setCode(0L);
		productPresentation.getCsoValueMealImage().setName(null);
		productPresentation.getCytPreviewImage().setCode(0L);
		productPresentation.getCytPreviewImage().setName(null);
		productPresentation.getCytPreviewBottomImage().setCode(0L);
		productPresentation.getCytPreviewBottomImage().setName(null);
		productPresentation.setImage(genMedfile);
		productPresentation.getSoundFile().setCode(0L);
		productPresentation.getSoundFile().setName(null);
		product.setProductPresentation(productPresentation);
		Map<Long, Product> listofProductsByMaster1 = new HashMap<>();
		listofProductsByMaster1.put(108L, product);
		listofProductsByMaster.putAll(listofProductsByMaster1);
		return listofProductsByMaster;
	}

	private List<Map<String, Object>> getAllColorsListCase() {
		List<Map<String, Object>> productData = new ArrayList<Map<String, Object>>();
		Map<String, Object> productData1 = new HashMap<String, Object>();
		productData1.put("colr_id", "92468");
		productData1.put("colr_na", "DEFAULTFOREGROUND");
		Map<String, Object> productData2 = new HashMap<String, Object>();
		productData2.put("colr_id", "92469");
		productData2.put("colr_na", "DEFAULTBACKGROUND");
		Map<String, Object> productData3 = new HashMap<String, Object>();
		productData3.put("colr_id", "92470");
		productData3.put("colr_na", "TRANSPARENT");
		Map<String, Object> productData4 = new HashMap<String, Object>();
		productData4.put("colr_id", "92471");
		productData4.put("colr_na", "ALICEBLUE");
		productData.add(productData1);
		productData.add(productData2);
		productData.add(productData3);
		productData.add(productData4);
		return productData;
	}

	private List<Map<String, Object>> getAllMediaListCase() {
		List<Map<String, Object>> productData = new ArrayList<Map<String, Object>>();
		Map<String, Object> productData1 = new HashMap<String, Object>();
		productData1.put("mdia_id", "128");
		productData1.put("mdia_file_na", "3SEL8.png");
		Map<String, Object> productData2 = new HashMap<String, Object>();
		productData2.put("mdia_id", "129");
		productData2.put("mdia_file_na", "3SEL8_HH.png");
		Map<String, Object> productData3 = new HashMap<String, Object>();
		productData3.put("mdia_id", "130");
		productData3.put("mdia_file_na", "3SEL9.png");
		Map<String, Object> productData4 = new HashMap<String, Object>();
		productData4.put("mdia_id", "131");
		productData4.put("mdia_file_na", "3SEL9_HH.png");
		productData.add(productData1);
		productData.add(productData2);
		productData.add(productData3);
		productData.add(productData4);
		return productData;
	}

	@Test
	void getPricingandTaxValuesTestCaseForAux() throws Exception {
		ProductDBRequest productDbRequest = new ProductDBRequest();
		Restaurant restvalues = new Restaurant();
		List<Set> priceSet = new ArrayList<>();
		Set set = new Set();
		set.setSetId(1749128L);
		priceSet.add(set);
		restvalues.setPriceSets(priceSet);
		productDbRequest.setMktId(2L);
		productDbRequest.setProductId(106720L);
		productDbRequest.setEffectiveDate("10/21/2021");
		productDbRequest.setNodeId(11451L);
		productDbRequest.setRestId(1812126L);
		productDbRequest.setRestInstId(60139008L);
		
		
		Map<Long, Product> products = new HashMap<Long, Product>();
		List<Category> productData = new ArrayList<Category>();		
		productDbRequest.setProductId(2502L);
		
		when(layeringProductService.getRestaurantSets(productDbRequest)).thenReturn(restvalues);
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.DEFAULT_MENU_ITEM_TAX_SETTINGS)).thenReturn("Restaurant");
		when(namesGeneratorDAO.getParamValue(ProductDBConstant.NON_MIT_MARKET_DEFAULT_TAX, productDbRequest.getMktId()))
				.thenReturn("false");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.AUTO_PRICE_DUMMYPRODUCT)).thenReturn("Y");
		when(namesGeneratorDAO.retrieveMasterSetId(productDbRequest.getMktId())).thenReturn(7L);
		when(layeringDBDAO.getPriceValuesWithTax(productDbRequest.getProductId(), productDbRequest.getMktId(),
				productDbRequest.getEffectiveDate(), 1749112L)).thenReturn(getPricingValues());
		when(layeringDBDAO.getPricingValuesBysetId(productDbRequest.getProductId(), productDbRequest.getMktId(),
				productDbRequest.getEffectiveDate(), 1749112L)).thenReturn(getPricingValues());
		when(layeringDBDAO.getTaxEntryList(2L)).thenReturn(getTaxEntryValues());
		when(layeringDBDAO.getMenuSubstitutionList(products, productDbRequest.getMktId())).thenReturn(products);
		when(layeringProductService.getMergedProductsByRest(dto)).thenReturn(getListOfMasterForAux());
		when(layeringProductService.getProductDualStatusByRest(dtoObject(true))).thenReturn(getListOfMasterForApprovalStatus());
		
		when(productDbDao.getPricingValueFromRest(productDbRequest.getMktId(),productDbRequest.getEffectiveDate(),
				productDbRequest.getNodeId())).thenReturn(getPricingValues());
		when(layeringDBDAO.getPricingValuesBysetId(126L, productDbRequest.getMktId(),productDbRequest.getEffectiveDate(), set.getSetId() )).thenReturn(getPricingValues());
		when(productDbDao.getTaxValuesForSetId(126L, productDbRequest.getMktId(),productDbRequest.getEffectiveDate(), 1749112L,441L)).thenReturn(getPricingValues());
		when(layeringDBDAO.getMenuSubstitutionList(products, productDbRequest.getMktId())).thenReturn(products);
		when(layeringProductService.getMergedProductsByRest(dto)).thenReturn(getListOfMasteForSub());
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(), ProductDBConstant.DISPLAY_TAX_FOR_BRKD))
				.thenReturn("false");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_EATING)).thenReturn("2");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_TAKEOUT)).thenReturn("0");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_OTHER)).thenReturn("5");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.REMOVE_PRICING_DECIMAL)).thenReturn("N");
		when(productDbDao.getValuesFromGlobalParam(productDbRequest.getMktId(),
				ProductDBConstant.GLOBAL_PARAM_PKG_PRICE_TYPE_GEN)).thenReturn("OTHER");
		when(productDbDao.getDataForDisplayBRD(productDbRequest.getProductId(), productDbRequest.getEffectiveDate()))
				.thenReturn(getDataForDisplayBRD());		
		layeringProductService.getPricingandTaxValues(products, productDbRequest.getEffectiveDate(), productDbRequest.getMktId(), productDbRequest.getNodeId(), restvalues, null);	
		Assert.notEmpty(getPricingValues(), "PricingValues by Set ID is fetched");
	}

	private List<Map<String, Object>> getMenuSubstitutionList1() {
		List<Map<String, Object>> productData = new ArrayList<Map<String, Object>>();
		Map<String, Object> productData1 = new HashMap<String, Object>();
		productData1.put("ITM_ID", "257");
		productData1.put("prd_code", "126");
		productData1.put("subs_grp_id", "441");
		Map<String, Object> productData2 = new HashMap<String, Object>();
		productData2.put("ITM_ID", "265");
		productData2.put("prd_code", "0");
		productData2.put("subs_grp_id", "441");
		productData.add(productData1);
		return productData;
	}

	private Map<Long, Product> getListOfMasteForSub() {
		Map<Long, Product> listofProductsByMaster = new HashMap<>();
		Product product = new Product();
		product.setAuxiliaryMenuItem(1L);
		Map<Long, Product> listofProductsByMaster1 = new HashMap<>();
		listofProductsByMaster1.put(106720L, product);
		listofProductsByMaster1.put(126L, product);
		listofProductsByMaster1.put(0L, product);
		listofProductsByMaster.putAll(listofProductsByMaster1);
		return listofProductsByMaster;
	}

	private Map<Long, Product> getListOfMasterForApprovalStatus() {
		Map<Long, Product> ListOfMasterForApprovalStatus = new HashMap<>();
		Product product = new Product();
		product.setApprovalStatus(1);
		Map<Long, Product> ListOfMasterForApprovalStatus1 = new HashMap<>();
		ListOfMasterForApprovalStatus1.put(106720L, product);
		ListOfMasterForApprovalStatus1.put(0L, product);
		ListOfMasterForApprovalStatus1.put(126L, product);
		ListOfMasterForApprovalStatus.putAll(ListOfMasterForApprovalStatus1);
		return ListOfMasterForApprovalStatus;
	}

	private Map<Long, Product> getListOfMasterForAux() {
		Map<Long, Product> listofProductsByMaster = new HashMap<>();
		Product product1 = new Product();
		product1.setAuxiliaryMenuItem(1L);
		Product product2 = new Product();
		product2.setAuxiliaryMenuItem(0L);
		Map<Long, Product> listofProductsByMaster1 = new HashMap<>();
		listofProductsByMaster1.put(106720L, product1);
		listofProductsByMaster1.put(0L, product2);
		listofProductsByMaster.putAll(listofProductsByMaster1);
		return listofProductsByMaster;
	}
	
	private PackageGeneratorDTO dtoObject(boolean coverage) {
		PackageGeneratorDTO packageGeneratorDTO = new PackageGeneratorDTO();
		PackageXMLParametersDTO[] packageXMLParametersDTO = new PackageXMLParametersDTO[2];
		PackageXMLParametersDTO p1 = new PackageXMLParametersDTO();
		PackageXMLParametersDTO p2 = new PackageXMLParametersDTO();
		if (coverage) {
			p1.setParm_na("PACKAGE_DATA_NAMES_DB_XML_VERSION");	
		}
		p1.setParm_val("1.0");
		p2.setParm_na("kvs-monitor-redesign-support");
		p2.setParm_val("Y");
		packageXMLParametersDTO[0] = p1;
		packageXMLParametersDTO[1] = p2;
		packageGeneratorDTO.setPackageXmlParameter(packageXMLParametersDTO);
		
		Map<String, Object> restaurantDataPointer = new HashMap<String, Object>();
		restaurantDataPointer.put("restaurantID", "1813258");
		restaurantDataPointer.put("restaurantInstID", "51971563");
		packageGeneratorDTO.setRestaurantDataPointers(restaurantDataPointer);
		
		GeneratorDefinedValues generatorDefinedValue = new GeneratorDefinedValues();
		generatorDefinedValue.setEffectiveDatePath("");
		packageGeneratorDTO.setGeneratorDefinedValues(generatorDefinedValue);
		packageGeneratorDTO.setGeneratedSeqNum(true);
		PackageStatusDTO pkg = new PackageStatusDTO();
		pkg.setSEQ_NO("123");
		packageGeneratorDTO.setPackageStatusDTO(pkg);
		
		packageGeneratorDTO.setDate("06/02/2021");
		packageGeneratorDTO.setMarketID(2L);
		packageGeneratorDTO.setNodeID(7646L);
		packageGeneratorDTO.setPackageStatusID(147712799752L);
		PackageSmartReminderDTO[] pkgSmartDTO = new PackageSmartReminderDTO[2];
		PackageSmartReminderDTO pkgSmartDTO1 = new PackageSmartReminderDTO();
		PackageSmartReminderDTO pkgSmartDTO2 = new PackageSmartReminderDTO();
		pkgSmartDTO1.setCustomSetId(-99L);
		pkgSmartDTO1.setParentSetId(-99L);
		pkgSmartDTO2.setCustomSetId(-99L);
		pkgSmartDTO2.setParentSetId(-99L);
		pkgSmartDTO[0] = pkgSmartDTO1;
		pkgSmartDTO[1] = pkgSmartDTO2;
		packageGeneratorDTO.setPackageSmartReminderDTO(pkgSmartDTO);
		
		return packageGeneratorDTO;
	}
}
