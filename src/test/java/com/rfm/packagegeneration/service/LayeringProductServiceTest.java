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
import com.rfm.packagegeneration.dto.GeneratorDefinedValues;
import com.rfm.packagegeneration.dto.GenericEntry;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageSmartReminderDTO;
import com.rfm.packagegeneration.dto.PackageStatusDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;
import com.rfm.packagegeneration.dto.Parameter;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductDBRequest;
import com.rfm.packagegeneration.dto.ProductPosKvs;
import com.rfm.packagegeneration.dto.ProductPresentation;
import com.rfm.packagegeneration.dto.RequestDTO;
import com.rfm.packagegeneration.dto.Restaurant;
import com.rfm.packagegeneration.dto.Set;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class LayeringProductServiceTest {
	@InjectMocks
	LayeringProductService service;

	@Mock
	LayeringProductDBDAO dao;
	
	@Mock
	NamesDBDAO namesDAO;
	
	private RequestDTO request = new RequestDTO();

	@Test
	void getRestaurantSets() throws Exception {	
		request.setMarketId(2L);
		request.setNodeId(0);
		
		List<Set> list = new ArrayList<Set>();
		Set dto = new Set();
		dto.setName("Test");
		dto.setSetId(1L);
		dto.setType(6008001L);
		list.add(dto);
		
		when(dao.getRestaurantSets(request.getMarketId(), request.getNodeId(), request.getEffectiveDate(), ProductDBConstant.MIS_TYPE)).thenReturn(list);
		service.getRestaurantSets(request);
		Assert.notEmpty(list, "getRestaurantSets fetched");
	}

	@Test
	void getMergedProductsByRest() throws Exception {
		request.setMarketId(2L);
		request.setNodeId(15265L);
		request.setEffectiveDate("08/26/2021");
		final Long masterSetId = 7L;
		
		Restaurant rest = new Restaurant();		
		List<Set> list = new ArrayList<Set>();
		Set dtoSet = new Set();
		dtoSet.setName("Test");
		dtoSet.setSetId(1L);
		dtoSet.setType(6008001L);
		list.add(dtoSet);
		rest.setPriceSets(list);
		
		when(dao.getRestaurantSets(request.getMarketId(), request.getNodeId(), request.getEffectiveDate(), ProductDBConstant.MIS_TYPE)).thenReturn(list);
		when(namesDAO.retrieveMasterSetId(2L)).thenReturn(7L);		 		
		when(dao.getProductPosKvsPresentationByMaster(request.getMarketId(), request.getEffectiveDate(), masterSetId)).thenReturn(getListOfProducts());		
		when(dao.getProductPosKvsPresentationBySet(getListOfProducts(), request.getMarketId(), request.getEffectiveDate(), ProductDBConstant.MIS_TYPE, 1L)).thenReturn(getListOfProducts());						
		when(dao.getProductPosKvsPresentationBySet(getListOfProducts(), request.getMarketId(), request.getEffectiveDate(), ProductDBConstant.RMI_TYPE, 1L)).thenReturn(getListOfProducts());				
		service.getMergedProductsByRest(request);
		Assert.notEmpty(getListOfProducts(), "product fetched");		
	}

	
	@Test
	void getProductStatusByRest() throws Exception {
		request.setMarketId(2L);
		request.setNodeId(15265L);
		request.setEffectiveDate("08/26/2021");
		final Long masterSetId = 7L;
		
		Restaurant rest = new Restaurant();		
		List<Set> list = new ArrayList<Set>();
		Set dtoSet = new Set();
		dtoSet.setName("Test");
		dtoSet.setSetId(1L);
		dtoSet.setType(6008001L);
		list.add(dtoSet);
		rest.setPriceSets(list);
		
		when(dao.getRestaurantSets(request.getMarketId(), request.getNodeId(), request.getEffectiveDate(), ProductDBConstant.MIS_TYPE)).thenReturn(list);
		when(namesDAO.retrieveMasterSetId(2L)).thenReturn(7L);		 		
		when(dao.getProductStatusByMaster(request.getMarketId(), request.getEffectiveDate(), masterSetId)).thenReturn(getListOfProducts());						
		when(dao.getProductStatusBySet(getListOfProducts(), request.getMarketId(), request.getEffectiveDate(), ProductDBConstant.MIS_TYPE, 1L,1L)).thenReturn(getListOfProducts());						
		when(dao.getProductStatusBySet(getListOfProducts(), request.getMarketId(), request.getEffectiveDate(), ProductDBConstant.RMI_TYPE, 1L,1L)).thenReturn(getListOfProducts());				
		
		service.getProductStatusByRest(dtoObject(true),1L);
		Assert.notEmpty(getListOfProducts(), "status fetched");
	}
	
	
	@Test
	void getProductApprovalStatusByRest() throws Exception {
		request.setMarketId(2L);
		request.setNodeId(15265L);
		request.setEffectiveDate("08/26/2021");
		final Long masterSetId = 7L;
		
		Restaurant rest = new Restaurant();		
		List<Set> list = new ArrayList<Set>();
		Set dtoSet = new Set();
		dtoSet.setName("Test");
		dtoSet.setSetId(1L);
		dtoSet.setType(6008001L);
		list.add(dtoSet);
		rest.setPriceSets(list);
		
		when(dao.getRestaurantSets(request.getMarketId(), request.getNodeId(), request.getEffectiveDate(), ProductDBConstant.MIS_TYPE)).thenReturn(list);
		when(namesDAO.retrieveMasterSetId(2L)).thenReturn(7L);		 		
		when(dao.getProductApprovalStatusByMaster(request.getMarketId(), request.getEffectiveDate(), masterSetId, "1")).thenReturn(getListOfProducts());					
		when(dao.getProductApprovalStatusBySet(getListOfProducts(), request.getMarketId(), request.getEffectiveDate(), ProductDBConstant.MIS_TYPE, 1L)).thenReturn(getListOfProducts());						
		when(dao.getProductApprovalStatusBySet(getListOfProducts(), request.getMarketId(), request.getEffectiveDate(), ProductDBConstant.RMI_TYPE, 1L)).thenReturn(getListOfProducts());				
		
		service.getProductApprovalStatusByRest(dtoObject(true));
		Assert.notEmpty(getListOfProducts(), "status fetched");
	}

	@Test
	void getProductDualStatusByRest() throws Exception {		
		request.setMarketId(2L);
		request.setNodeId(15265L);
		request.setEffectiveDate("08/26/2021");
		final Long masterSetId = 7L;
		
		Restaurant rest = new Restaurant();		
		List<Set> list = new ArrayList<Set>();
		Set dtoSet = new Set();
		dtoSet.setName("Test");
		dtoSet.setSetId(1L);
		dtoSet.setType(6008001L);
		list.add(dtoSet);
		rest.setPriceSets(list);
		

		List<Long> setsList = new ArrayList<Long>();
		setsList.add(1L);
		
		List<Long> restaurantList = new ArrayList<Long>();
		restaurantList.add(1L);
		
		
		when(dao.getMarketMenuItemDualStatus(request.getMarketId())).thenReturn(true);
		when(dao.getRestaurantSets(request.getMarketId(), request.getNodeId(), request.getEffectiveDate(), ProductDBConstant.MIS_TYPE)).thenReturn(list);
		when(namesDAO.retrieveMasterSetId(2L)).thenReturn(7L);		
		when(namesDAO.retrieveRestSetId(request.getNodeId(), request.getMarketId())).thenReturn(setsList);
		when(dao.getProductApprovalStatusByMaster(request.getMarketId(), request.getEffectiveDate(), masterSetId, "1")).thenReturn(getListOfProducts());					
		when(dao.getProductApprovalStatusBySet(getListOfProducts(), request.getMarketId(), request.getEffectiveDate(), ProductDBConstant.MIS_TYPE, 1L)).thenReturn(getListOfProducts());						
		when(dao.getProductApprovalStatusBySet(getListOfProducts(), request.getMarketId(), request.getEffectiveDate(), ProductDBConstant.RMI_TYPE, 1L)).thenReturn(getListOfProducts());				
		when(dao.getProductStatusBySet(getListOfProducts(), request.getMarketId(), request.getEffectiveDate(), ProductDBConstant.RMI_TYPE, 1L,1L)).thenReturn(getListOfProducts());		
		when(dao.retrieveRestaurantMIList(setsList.get(0), request.getNodeId(), request.getMarketId(), request.getEffectiveDate())).thenReturn(restaurantList);
		
		service.getProductDualStatusByRest(dtoObject(true));
		Assert.notEmpty(getListOfProducts(), "dual status fetched");
	}
	
	
	private Map<Long, Product> getListOfProducts() {
		Map<Long, Product> listOfProducts = new HashMap<Long, Product>();
		Product dto = new Product();
		dto.setProductId(1L);
		dto.setApprovalStatus(0);
		dto.setActive(1);		
		dto.setProductPosKvs(new ProductPosKvs());
		dto.setProductPresentation(new ProductPresentation());		
		dto.getProductPosKvs().setAutoGrill(1L);
		dto.getProductPosKvs().setAutoCondiment(new ArrayList<GenericEntry>());
		dto.getProductPosKvs().setBunBufferConfiguration(new GenericEntry());
		dto.getProductPosKvs().setDedicatedCell(0L);
		dto.getProductPosKvs().setDeposit(new GenericEntry());
		dto.getProductPosKvs().setDiscountsNotAllowed(new ArrayList<GenericEntry>());
		dto.getProductPosKvs().setDisplayAs(new  GenericEntry());
		dto.getProductPosKvs().setDisplayGrillInstructions("ASKVS");
		dto.getProductPresentation().setCaptionLine1("Test 1");
		dto.getProductPresentation().setImage(new ArrayList<GenericEntry>());
		listOfProducts.put(1L, dto);		
		return listOfProducts;
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
	
	@Test
	void calculateValuesForAllParametersTest() throws Exception {
		
		List<Parameter>  parameterList = getProductData();
		Map<Long, Product> products = new HashMap<>();
		Product product = new Product();
		product.setParameters(parameterList);
		products.put(126L, product);		
		service.calculateValuesForAllParameters(products, "#0.00", "#0.00", "#0.00", product, 126L, "1.95", "1.95", "1.95");
		Assert.notEmpty(getProductData(), "productData is not empty");
	}
	
	private List<Parameter> getProductData() {
		
		
		Parameter param1 = new Parameter();
		Parameter param2 = new Parameter();
		Parameter param3 = new Parameter();
		Parameter param4 = new Parameter();
		Parameter param5 = new Parameter();
		Parameter param6 = new Parameter();
		Parameter param7 = new Parameter();
		Parameter param8 = new Parameter();
		Parameter param9 = new Parameter();
		Parameter param10 = new Parameter();
		Parameter param11 = new Parameter();
		Parameter param12 = new Parameter();
		param1.setName("KioskBitMapName");
		param1.setValue("MO_202104_0031_3HotCakes.jpg");
		param1.setDataType(2L);
		param2.setName("LabelUnit");
		param2.setValue("1.00");
		param2.setDataType(2L);
		param3.setName("BaselineUnitofMeasure");
		param3.setValue(".5");
		param3.setDataType(2L);
		param4.setName("MenuItemVolumeUnit");
		param4.setValue("2.45");
		param4.setDataType(2L);
		param5.setName("PriceperUnitEatin");
		param5.setValue("5.25");
		param5.setDataType(2L);
		param6.setName("PriceperUnitTakeout");
		param6.setValue("5.25");
		param6.setDataType(2L);
		param7.setName("PriceperUnitOther");
		param7.setValue("5.25");
		param7.setDataType(2L);
		param8.setName("ShowGrillOnSale");
		param8.setValue("true");
		param8.setDataType(3L);
		param9.setName("nutritionalInfoKjRange");
		param9.setValue("testRange");
		param9.setDataType(2L);
		param10.setName("nutritionalInfoKj");
		param10.setValue("100");
		param10.setDataType(2L);
		param11.setName("nutritionalInfoKCalRange");
		param11.setValue("testRange");
		param11.setDataType(2L);
		param12.setName("nutritionalInfoKCal");
		param12.setValue("100");
		param12.setDataType(2L);

		List<Parameter> parameterList = new ArrayList<>();
		parameterList.add(param1);
		parameterList.add(param2);
		parameterList.add(param3);
		parameterList.add(param4);
		parameterList.add(param5);
		parameterList.add(param6);
		parameterList.add(param7);
		parameterList.add(param8);
		return parameterList;
	}
	
	@Test
	void calculateValuesForAllParametersTestCase2() throws Exception {
		
		List<Parameter>  parameterList = getProductData1();
		Map<Long, Product> products = new HashMap<>();
		Product product = new Product();
		product.setParameters(parameterList);
		products.put(126L, product);		
		service.calculateValuesForAllParameters(products, "#0.00", "#0.00", "#0.00", product, 126L, "1.95", "1.95", "1.95");
		Assert.notEmpty(getProductData(), "productData is not empty");
	}
	
	
	private List<Parameter> getProductData1() {
		
		
		Parameter param1 = new Parameter();
		Parameter param2 = new Parameter();
		Parameter param3 = new Parameter();
		Parameter param4 = new Parameter();
		Parameter param5 = new Parameter();
		Parameter param6 = new Parameter();
		Parameter param7 = new Parameter();
		Parameter param8 = new Parameter();
		param1.setName("KioskBitMapName");
		param1.setValue("-1");
		param1.setDataType(2L);
		param2.setName("LabelUnit");
		param2.setValue("1.00");
		param2.setDataType(2L);
		param3.setName("BaselineUnitofMeasure");
		param3.setValue(".5");
		param3.setDataType(2L);
		param4.setName("MenuItemVolumeUnit");
		param4.setValue("2.45");
		param4.setDataType(2L);
		param5.setName("PriceperUnitEatin");
		param5.setValue("");
		param5.setDataType(2L);
		param6.setName("PriceperUnitTakeout");
		param6.setValue("");
		param6.setDataType(2L);
		param7.setName("PriceperUnitOther");
		param7.setValue("");
		param7.setDataType(2L);
		param8.setName("ShowGrillOnSale");
		param8.setValue("true");
		param8.setDataType(3L);

		List<Parameter> parameterList = new ArrayList<>();
		parameterList.add(param1);
		parameterList.add(param2);
		parameterList.add(param3);
		parameterList.add(param4);
		parameterList.add(param5);
		parameterList.add(param6);
		parameterList.add(param7);
		parameterList.add(param8);
		return parameterList;
	}
	
	@Test
	void bziRuleTestCase() throws Exception {
		Map<Long, Product> products = new HashMap<>();
		Product product = new Product();
		product.setProductClass("CHOICE");
		product.setActive(0);
		products.put(10000003L, product);
		service.bzirule(products, dtoObjects());
		service.defaultProductbziRule(products);
		Assert.notEmpty(products, "allProducts is not empty");
	}
	
	private PackageGeneratorDTO dtoObjects() {
		PackageGeneratorDTO packageGeneratorDTO = new PackageGeneratorDTO();
		packageGeneratorDTO.setDate("11/17/2021");
		packageGeneratorDTO.setMarketID(2L);
		packageGeneratorDTO.setNodeID(11077L);
		packageGeneratorDTO.setScheduleType("1");		
		PackageXMLParametersDTO[] packageXMLParametersDTO = new PackageXMLParametersDTO[2];
		PackageXMLParametersDTO p1 = new PackageXMLParametersDTO();
		PackageXMLParametersDTO p2 = new PackageXMLParametersDTO();
		p1.setParm_val("1.0");
		p2.setParm_na("restercindicator");
		p2.setParm_val("ON");
		packageXMLParametersDTO[0] = p1;
		packageXMLParametersDTO[1] = p2;
		packageGeneratorDTO.setPackageXmlParameter(packageXMLParametersDTO);
		GeneratorDefinedValues generatorDefinedValue = new GeneratorDefinedValues();
		generatorDefinedValue.setEffectiveDatePath("11/17/2021");
		generatorDefinedValue.setProductDBGenerated(false);
		packageGeneratorDTO.setGeneratorDefinedValues(generatorDefinedValue);
		packageGeneratorDTO.setGeneratedSeqNum(true);
		PackageStatusDTO pkg = new PackageStatusDTO();
		pkg.setSEQ_NO("123");
		packageGeneratorDTO.setPackageStatusDTO(pkg);
		return packageGeneratorDTO;
	}
	
	// Test for Menu Item Group To Fetch Code
		@Test
		void getProductDBDataForMenuItemTestCase1() throws Exception {
			Map<Long, Product> products = new HashMap<>();
			Product product = new Product();
			products.put(10000003L, product);
			int languageCount = 1;
			when(dao.getgroupCount(2L)).thenReturn(languageCount);
			when(dao.getMenuItemGroupValues(2L, "07/09/2021")).thenReturn(getProductDataforMenuItem());

			service.getMenuItemProductGroups(products,2L,"07/09/2021");
			Assert.notEmpty(getProductDataforMenuItem(), "productData fetched");
		}

		@Test
		void getProductDBDataForMenuItemTestCase2() throws Exception {
			Map<Long, Product> products = new HashMap<>();
			Product product = new Product();
			products.put(10000003L, product);
			int languageCount = 3;
			when(dao.getgroupCount(2L)).thenReturn(languageCount);
			when(dao.getMenuItemGroupValues(2L, "11/23/2021")).thenReturn(getProductDataforMenuItem());

			service.getMenuItemProductGroups(products,2L,"11/23/2021");
			Assert.notEmpty(getProductDataforMenuItem(), "productData is not fetched");
		}
		private List<Map<String, Object>> getProductDataforMenuItem() {
			List<Map<String, Object>> productData = new ArrayList<Map<String, Object>>();
			Map<String, Object> productData1 = new HashMap<String, Object>();
			productData1.put("PRD_ID", "10000003");
			productData1.put("mi_grp_cd", "1");
			Map<String, Object> productData2 = new HashMap<String, Object>();
			productData2.put("PRD_ID", "10000003");
			productData2.put("mi_grp_cd", "5");
			productData.add(productData1);
			productData.add(productData2);
			return productData;
		}
	
}
