package com.rfm.packagegeneration.repository;

import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.rfm.packagegeneration.dao.DAOResources;
import com.rfm.packagegeneration.dao.NamesDBDAO;
import com.rfm.packagegeneration.dto.GeneratorDefinedValues;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageSmartReminderDTO;
import com.rfm.packagegeneration.dto.PackageStatusDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;

@RunWith(SpringRunner.class)

@SpringBootTest
class NamesDBDAOTest {
	@Autowired
	NamesDBDAO namesGeneratorDAO;
	
	@Test
	void retrieveMenuItemSetsId() throws Exception {
		final PackageGeneratorDTO dto = dtoObject(false);
		final List<Long> setsId = namesGeneratorDAO.retrieveMenuItemSetsId(dto.getNodeID(), 
				dto.getMarketID(), dto.getDate());
		Assert.notEmpty(setsId, "retrieveMenuItemSetsId is Empty");
	}
	
	@Test
	void retrieveRestSetId() throws Exception {
		final PackageGeneratorDTO dto = dtoObject(false);
		final List<Long> restSetId = namesGeneratorDAO.retrieveRestSetId(dto.getNodeID(), 
				dto.getMarketID());
		Assert.notEmpty(restSetId, "retrieveRestSetId is Empty");
	}
	
	@Test
	void retrieveMasterSetId() throws Exception {
		final PackageGeneratorDTO dto = dtoObject(false);
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId(dto.getMarketID());
		Assert.isTrue(masterSetId > 0, "Does not exist master set id to the required market");
	}
	
	@Test
	void getAllLocales() throws Exception {
		final List<Map<String,String>> allLocales = namesGeneratorDAO.getAllLocales(14L, 94633L, 2L, "03/1/2021");
		Assert.notEmpty(allLocales, "getAllLocales is Empty");
	}
	
	@Test
	void getLocalizationSets() throws Exception {
		final List<Map<String, Object>> localizationSets = namesGeneratorDAO.getLocalizationSets(690152L,43653314L);
		Assert.notEmpty(localizationSets, "getLocalizationSets is Empty");
	}
	
	@Test
	void getCountryCode() throws Exception {
		final String countryCode = namesGeneratorDAO.getCountryCode(14L,14L,"03/1/2021");
		Assert.isTrue(countryCode != null, "Country Code is null");
	}
	
	@Test
	void getLanguageCount() throws Exception {
		final int languageCount = namesGeneratorDAO.getLanguageCount(14L);
		Assert.isTrue(languageCount > 0, "Language settings incorrect");
	}
	
	@Test
	void getLanguageCount_CoverMapCache() throws Exception {
		final int languageCount = namesGeneratorDAO.getLanguageCount(14L);
		Assert.isTrue(languageCount > 0, "Language settings incorrect");
	}
	
	@Test
	void getAllSmartReminders() throws Exception {
		Map<String, String> AllSmartReminders = new HashMap<String, String>();
		AllSmartReminders = namesGeneratorDAO.getAllSmartReminders(2L, "03/1/2021");
		Assert.notEmpty(AllSmartReminders, "getAllSmartReminders is Empty");
	}
	
	@Test
	void getDeviceLex() throws Exception {
		final Long deviceLex = namesGeneratorDAO.getDeviceLex(2L);
		Assert.isTrue(deviceLex > 0, "Device lex id does not exist");
	}
	
	@Test
	void getParamValue() throws Exception {
		final String namesVersion = namesGeneratorDAO.getParamValue("PACKAGE_DATA_NAMES_DB_XML_VERSION", 0L);
		Assert.isTrue(namesVersion != null, "Names xml Version is invalid");
	}
	
	@Test
	void getCustomizedSmartReminders() throws Exception {
		Map<String,String> customSmartReminders= null;
		customSmartReminders = namesGeneratorDAO.getCustomizedSmartReminders(1L, 99999L, 2L, 6L, 56L);
		Assert.notEmpty(customSmartReminders, "getCustomizedSmartReminders is Empty");
	}
	
	@Test
	void getDaoXml_whenExceptionThrown_thenAssertionSucceeds() throws Exception {
	
		Exception exception = assertThrows(Exception.class, () -> {
			DAOResources unitTestException = DAOResources.NAMES_DB_GENERATOR_DAO;
			namesGeneratorDAO.getDaoXml("UnitTest", unitTestException);
	    });
		  	String expectedMessage = "it does not exist";
		    String actualMessage = exception.getMessage();
		Assert.isTrue(actualMessage.contains(expectedMessage), "Query does not exist");
	}
	
	@Test
	void populateNamesDataTempTableTest() throws Exception {
		Map<String,Map<String,String>> allProducts= null;
		final List<Long> setIds = new ArrayList<Long>();
		final List<Long> restIds = new ArrayList<Long>();
		setIds.add(29L);
		setIds.add(2712L);
		setIds.add(1910114L);
		setIds.add(1792919L);
		setIds.add(2035864L);
		restIds.add(1756982L);
		Map<String, String> locale1 = new HashMap<String, String>();
		locale1.put("LANG_CD", "en");
		locale1.put("LCLE_NA", "English");
		locale1.put("LANG_ID", "1");
		locale1.put("DVCE_ID", "99999");
        allProducts = namesGeneratorDAO.populateNamesMap(dtoObject(false), locale1, null);
		Assert.notNull(allProducts, "populateNamesMap is Empty");
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
