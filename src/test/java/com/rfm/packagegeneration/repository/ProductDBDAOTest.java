package com.rfm.packagegeneration.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.rfm.packagegeneration.dao.LayeringProductDBDAO;
import com.rfm.packagegeneration.dao.ProductDBDAO;
import com.rfm.packagegeneration.dto.Item;
import com.rfm.packagegeneration.dto.Product;

@RunWith(SpringRunner.class)
@SpringBootTest

class ProductDBDAOTest {
	
	@Autowired
	ProductDBDAO productDbDao;
	
	@Autowired
	LayeringProductDBDAO layeringDBDAO;
	@Test
	void getLocalizationSetsTest() throws Exception {
		final List<Map<String, Object>> localizationSets = productDbDao.getLocalizationSets(690152L,"20/10/2021");
		Assert.notEmpty(localizationSets, "getLocalizationSets is not Empty");
	}
	
	@Test
	void getProductDataForDimensionGroupTest() throws Exception {
		final List<Map<String, Object>> productData = layeringDBDAO.getProductDataForDimensionGroup(14L, 14L, "06/09/2021" , 2L);
		Assert.notEmpty(productData, "productData is not Empty");
	}
	
	@Test
	void getProductDataForPromotionGroupTestCase1() throws Exception {
		Map<Long, Product> map = new HashMap<Long, Product>(); 
		Product dto = new Product();
		dto.setProductId(126l);
		map.put(126l, dto);
		
		final Map<Long, Product> productData = layeringDBDAO.getProductListDataForPromotionGroup(map, 2L,  "10/01/2021");
		Assert.notNull(productData, "productData is not Empty");
	}
	
	@Test
	void getProductDataForPromotionGroupTestCase2() throws Exception {
		final Long productData = layeringDBDAO.getProductListCount(2L, 126L);
		Assert.notNull(productData, "productData is not null");
	}
	
	@Test
	void getProductDataForPromotionGroupTestCase3() throws Exception {
		final List<Map<String, Object>> productData = layeringDBDAO.getListFlag(2L, 126L, "2");
		Assert.notNull(productData, "productData is not empty");
	}


	@Test
	void getgroupMenuCountCase2() throws Exception {
		int groupCount = layeringDBDAO.getgroupCount(2L);
		Assert.notNull(groupCount, "productData is not NULL");
	}

	@Test
	void getMenuItemGroupValuesTestCases() throws Exception {
		final List<Map<String, Object>> MenuItemGroupVals = layeringDBDAO.getMenuItemGroupValues(2L,
				"06/09/2021");
		Assert.notEmpty(MenuItemGroupVals, "productData is not Empty");
	}
	
	@Test
	void getRoutingSetsTest() throws Exception {
		final List<Map<String, Object>> productData = layeringDBDAO.getRoutingSets(2L, 1812126L, 53713848L);
		Assert.notEmpty(productData, "productData is not Empty");
	}
	
	@Test
	void getTaxEntryListTestCases() throws Exception {
		final List<Map<String, Object>> TaxEntryList = layeringDBDAO.getTaxEntryList(2L);
		Assert.notEmpty(TaxEntryList, "productData is not Empty");
	}
	
	@Test
	void getPriceValuesWithTax() throws Exception {
		final List<Map<String, Object>> pricingValuesWithTax = layeringDBDAO.getPriceValuesWithTax(126L, 2L, "10/01/2021", 1749128L);
		Assert.notNull(pricingValuesWithTax, "pricingValuesWithTax is not Empty");
	}
	
	@Test
	void getDataForDisplayBRD() throws Exception {
		final Map<String, Object> dataForDisplayBRD = productDbDao.getDataForDisplayBRD(126L, "10/01/2021");
		Assert.notNull(dataForDisplayBRD, "dataForDisplayBRD is not Empty");
	}
	
	@Test
	void getValuesFromGlobalParamTestCase1() throws Exception {
		final String valuesFromGlobalParam = productDbDao.getValuesFromGlobalParam(2L, "DEFAULT_MENU_ITEM_TAX_SETTINGS");
		Assert.hasText(valuesFromGlobalParam,  "valuesFromGlobalParam is not Empty");	
	}
	@Test
	void getValuesFromGlobalParamTestCase2() throws Exception {
		final String valuesFromGlobalParam = productDbDao.getValuesFromGlobalParam(2L, "REMOVE_PRICING_DECIMAL");
		Assert.hasText(valuesFromGlobalParam,  "valuesFromGlobalParam is not Empty");	
	}
	@Test
	void getValuesFromGlobalParamTestCase3() throws Exception {
		final String valuesFromGlobalParam = productDbDao.getValuesFromGlobalParam(2L, "GLOBAL_PARAM_PKG_PRICE_TYPE_GEN");
		Assert.hasText(valuesFromGlobalParam,  "valuesFromGlobalParam is not Empty");	
	}
	@Test
	void getValuesFromGlobalParamTestCase4() throws Exception {
		final String valuesFromGlobalParam = productDbDao.getValuesFromGlobalParam(2L, "MAX_DECIMAL_PLACES_ALLOWED_FOR_PRICE_EATING");
		Assert.hasText(valuesFromGlobalParam,  "valuesFromGlobalParam is not Empty");	
	}
	@Test
	void getAllColorsList() throws Exception {
		final List<Map<String, Object>> AllColorsList = productDbDao.getAllColorsList(2L);
		Assert.notEmpty(AllColorsList, "productData is not Empty");
	}
	@Test
	void getAllMediaList() throws Exception {
		final List<Map<String, Object>> AllMediaList = productDbDao.getAllMediaList(2L);
		Assert.notEmpty(AllMediaList, "productData is not Empty");
	}
	@Test
	void getTaxValuesForSetId() throws Exception {
		final List<Map<String, Object>> taxValues = productDbDao.getTaxValuesForSetId(126L,2L,"10/26/2021",1749128L,441L);
		Assert.notNull(taxValues, "taxValues is not Empty");
	}

}
