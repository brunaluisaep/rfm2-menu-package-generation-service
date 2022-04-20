package com.rfm.packagegeneration.service;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.rfm.packagegeneration.dao.NamesDBDAO;
import com.rfm.packagegeneration.dto.GeneratorDefinedValues;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageSmartReminderDTO;
import com.rfm.packagegeneration.dto.PackageStatusDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class NamesDbGeneratorServiceTest {
	@InjectMocks
	NamesDBService namesDbGeneratorService;
	
	@Mock
	NamesDBDAO namesGeneratorDAO;
	
	@Test
	void generateFileStringTest() throws Exception {
		List<Map<String, Object>> localizationSet = new ArrayList<Map<String, Object>>();
		
		Map<String,String> customSmartReminders = new HashMap<String, String>();
		customSmartReminders.put("88", "<SR_Question>Would you like crispy chicken for your"
				+ " Guacamole Sandwich?</SR_Question><SR_Name>Guacamole</SR_Name>");
		
		Map<String, Object> localSet = new HashMap<String, Object>();
		String countryCode = "US";
		int languageCount = 1;
		localSet.put("PREN_SET_ID", 14);
		localSet.put("CUSM_SET_ID", 14);
		localizationSet.add(localSet);
		PackageGeneratorDTO dto = dtoObject(true);
		List<Long> emptLst = new ArrayList<Long>();
		when(namesGeneratorDAO.getLocalizationSets(1813258L, 51971563L)).thenReturn(localizationSet);
		when(namesGeneratorDAO.getCountryCode(14L,14L,"03/1/2021")).thenReturn(countryCode);
		when(namesGeneratorDAO.getLanguageCount(14L)).thenReturn(languageCount);
		when(namesGeneratorDAO.getAllLocales(14L, 0L, 2L, "03/1/2021")).thenReturn(getAllLocales());
		when(namesGeneratorDAO.getCustomizedSmartReminders(1L, 99999L, 2L, -99L, -99L)).thenReturn(customSmartReminders);
		//when(namesGeneratorDAO.populateNamesDataTempTable(0L, emptLst, emptLst, dto, getAllLocales().get(0))).thenReturn(customNames(), UUID.randomUUID());
		namesDbGeneratorService.generateFileString(dto);
		Assert.isTrue(namesDbGeneratorService.generateFileString(dto), "names-db.xml did not generate")  ;  
	}
	
	@Test
	void generateFileStringTest2() throws Exception {
		List<Map<String, Object>> localizationSet = new ArrayList<Map<String, Object>>();
		Map<String,String> customSmartReminders = new HashMap<String, String>();
		customSmartReminders.put("88", "<SR_Question>Would you like crispy chicken for your"
				+ " Guacamole Sandwich?</SR_Question><SR_Name>Guacamole</SR_Name>");
		
		Map<String, Object> localSet = new HashMap<String, Object>();
		String countryCode = "US";
		int languageCount = 1;
		localSet.put("PREN_SET_ID", 14);
		localSet.put("CUSM_SET_ID", 14);
		localizationSet.add(localSet);
		PackageGeneratorDTO dto = dtoObject(false);
		List<Long> emptLst = new ArrayList<Long>();
		when(namesGeneratorDAO.getLocalizationSets(1813258L, 51971563L)).thenReturn(localizationSet);
		when(namesGeneratorDAO.getCountryCode(14L,14L,"03/1/2021")).thenReturn(countryCode);
		when(namesGeneratorDAO.getLanguageCount(14L)).thenReturn(languageCount);
		when(namesGeneratorDAO.getAllLocales(14L, 0L, 2L, "03/1/2021")).thenReturn(getAllLocales());
		when(namesGeneratorDAO.getCustomizedSmartReminders(1L, 99999L, 2L, -99L, -99L)).thenReturn(customSmartReminders);
		//when(namesGeneratorDAO.populateNamesDataTempTable(0L, emptLst, emptLst, dto, getAllLocales().get(0))).thenReturn(customNames(),  UUID.randomUUID());
		namesDbGeneratorService.generateFileString(dto);
		Assert.isTrue(namesDbGeneratorService.generateFileString(dto), "names-db.xml did not generate")  ;  
	}
	
	private List<Map<String,String>> getAllLocales(){
		List<Map<String,String>> allLocales = new ArrayList<Map<String,String>>();
		Map<String, String> locale1 = new HashMap<String, String>();
		locale1.put("LANG_CD", "en");
		locale1.put("LCLE_NA", "English");
		locale1.put("LANG_ID", "1");
		locale1.put("DVCE_ID", "99999");
		Map<String, String> locale2 = new HashMap<String, String>();
		locale2.put("LANG_CD", "es");
		locale2.put("LCLE_NA", "SpanishUS");
		locale2.put("LANG_ID", "2");
		locale2.put("DVCE_ID", "99999");
		Map<String, String> locale3 = new HashMap<String, String>();
		locale3.put("LANG_CD", "ess");
		locale3.put("LCLE_NA", "SpanishUSs");
		locale3.put("LANG_ID", "3");
		locale3.put("DVCE_ID", "1235");
		allLocales.add(locale1);
		allLocales.add(locale2);
		allLocales.add(locale3);
		return allLocales;
	}
	
	private Map<String,Map<String,String>> customNames(){
		final Map<String,Map<String,String>> allProducts = new HashMap<String,Map<String,String>>();
		Map<String,String> productNames = new HashMap<String,String>();
		
		productNames = new HashMap<String,String>();
		productNames.put("SHRT_NA","SHRT_NA");
		productNames.put("LNG_NA","LNG_NA");
		productNames.put("DRV_NA","DRV_NA");
		productNames.put("SUMR_MNIT_NA","SUMR_MNIT_NA");
		productNames.put("SHRT_MNIT_NA","SHRT_MNIT_NA");
		productNames.put("ALT_NA","ALT_NA");
		productNames.put("CSO_NA","CSO_NA");
		productNames.put("CSO_SIZE_NA","CSO_SIZE_NA");
		productNames.put("CSO_GEN_NA","CSO_GEN_NA");
		productNames.put("COD_NA","COD_NA");
		productNames.put("SET_INST","SET_INST");
		productNames.put("REST_INST","REST_INST");
		productNames.put("PRD_CD","PRD_CD");
		productNames.put("HOME_DELIVERY_NA","HOME_DELIVERY_NA");
		productNames.put("SMRT_RMDR","SMRT_RMDR");
		String key = "PRD_ID"+";"+"PRD_MSTR_INST"+";1;"+"99999";
		allProducts.put(key,productNames);
		return allProducts;
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
		
		packageGeneratorDTO.setDate("03/1/2021");
		packageGeneratorDTO.setMarketID(2L);
		packageGeneratorDTO.setNodeID(2L);
		packageGeneratorDTO.setPackageStatusID(119272358832L);
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
