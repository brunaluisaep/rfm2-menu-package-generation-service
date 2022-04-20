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

import com.rfm.packagegeneration.dto.GeneratorDefinedValues;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageSmartReminderDTO;
import com.rfm.packagegeneration.dto.PackageStatusDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;
import com.rfm.packagegeneration.dto.ScreenDetails;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class PackageGenerationServiceTest {
	@InjectMocks
	PackageGenerationService packageGenerationService;
	
	@Mock
	NamesDBService namesDbGeneratorService;
	
	@Mock
	ScreenService screenDbGeneratorService;
	
	@Test
	void generateFileStringTest() throws Exception {
		final PackageGeneratorDTO dto = dtoObject(false);
		when(namesDbGeneratorService.generateFileString(dto)).thenReturn(true);
		Assert.isTrue(packageGenerationService.generateNamesXML(dto), "File did not generate");
	}
	
	@Test
	void generateFileStringTest2() throws Exception {
		final PackageGeneratorDTO dto = dtoObject(false);
		dto.getGeneratorDefinedValues().setEffectiveDatePath(null);
		dto.setMarketts("CST6CDT");
		packageGenerationService.setDirectoryXmls("");
		when(namesDbGeneratorService.generateFileString(dto)).thenReturn(true);
		packageGenerationService.generateNamesXML(dto);
		Assert.isTrue(packageGenerationService.generateNamesXML(dto), "File did not generate");
	}
	
	@Test
	void generateFileStringTestForScreen() throws Exception {
		final PackageGeneratorDTO dto = dtoObject(false);
		boolean[] arrResult = new boolean[2];
		arrResult[0] = true;
		arrResult[1] = true;
		when(screenDbGeneratorService.generateFile(dto)).thenReturn(arrResult);
		Assert.isTrue(packageGenerationService.generateScreenDBXML(dto), "File did not generate");
	}
	@Test
	void generateFileStringTestForScreen2() throws Exception {
		final PackageGeneratorDTO dto = dtoObject(false);
		boolean[] arrResult = new boolean[2];
		arrResult[0] = true;
		arrResult[1] = true;
		dto.getGeneratorDefinedValues().setEffectiveDatePath(null);
		dto.setMarketts("CST6CDT");
		packageGenerationService.setDirectoryXmls("");
		when(screenDbGeneratorService.generateFile(dto)).thenReturn(arrResult);
		packageGenerationService.generateScreenDBXML(dto);
		Assert.isTrue(packageGenerationService.generateScreenDBXML(dto), "File did not generate");
	}
	@Test
	void generateFileStringTestForScreen3() throws Exception {
		final PackageGeneratorDTO dto = dtoObject(true);
		boolean[] arrResult = new boolean[2];
		arrResult[0] = false;
		arrResult[1] = false;
		dto.getGeneratorDefinedValues().setEffectiveDatePath(null);
		dto.setMarketts("CST6CDT");
		packageGenerationService.setDirectoryXmls("");
		when(screenDbGeneratorService.generateFile(dto)).thenReturn(arrResult);
		packageGenerationService.generateScreenDBXML(dto);
		Assert.isTrue(packageGenerationService.generateScreenDBXML(dto), "File did not generate");
	}
	
	private PackageGeneratorDTO dtoObject(boolean coverage) {
		PackageGeneratorDTO packageGeneratorDTO = new PackageGeneratorDTO();
		PackageXMLParametersDTO[] packageXMLParametersDTO = new PackageXMLParametersDTO[4];
		PackageXMLParametersDTO p1 = new PackageXMLParametersDTO();
		PackageXMLParametersDTO p2 = new PackageXMLParametersDTO();
		PackageXMLParametersDTO p3 = new PackageXMLParametersDTO();
		PackageXMLParametersDTO p4 = new PackageXMLParametersDTO();
		if (coverage) {
			p1.setParm_na("PACKAGE_DATA_NAMES_DB_XML_VERSION");	
		}
		p1.setParm_val("1.0");
		p2.setParm_na("kvs-monitor-redesign-support");
		p2.setParm_val("Y");
		if(coverage) {
		p3.setParm_na("PACKAGE_DATA_SCREEN_DB_XML_VERSION");}
		p3.setParm_val("Y");
		p4.setParm_na("POS_LAYOUT_WORKFLOW");
		p4.setParm_val("Y");
		packageXMLParametersDTO[0] = p1;
		packageXMLParametersDTO[1] = p2;
		packageXMLParametersDTO[2] = p3;
		packageXMLParametersDTO[3] = p4;
		
		packageGeneratorDTO.setPackageXmlParameter(packageXMLParametersDTO);
		packageGeneratorDTO.setScheduleRequestID("564567");
		packageGeneratorDTO.setPackageStatusID(24693062092L);
		Map<String, Object> restaurantDataPointer = new HashMap<String, Object>();
		restaurantDataPointer.put("restaurantID", "1812819");
		restaurantDataPointer.put("restaurantInstID", "60003290");
		restaurantDataPointer.put("restaurantNumber", 7);
		List<String> files = new ArrayList<String>();
		files.add("store-db.xml");
		files.add("product-db.xml");
		files.add("screen.xml");
		restaurantDataPointer.put("xmlFilesList", files);
		packageGeneratorDTO.setRestaurantDataPointers(restaurantDataPointer);
		Map<String, List<ScreenDetails>> screens = new HashMap<>();
		packageGeneratorDTO.setScreens(screens);
		GeneratorDefinedValues generatorDefinedValue = new GeneratorDefinedValues();
		generatorDefinedValue.setEffectiveDatePath("12/14/2021");
		packageGeneratorDTO.setGeneratorDefinedValues(generatorDefinedValue);
		packageGeneratorDTO.setGeneratedSeqNum(true);
		PackageStatusDTO pkg = new PackageStatusDTO();
		pkg.setSEQ_NO("23456");
		packageGeneratorDTO.setPackageStatusDTO(pkg);
		
		packageGeneratorDTO.setDate("12/14/2021");
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
		packageGeneratorDTO.setScheduleType("1");
		
		return packageGeneratorDTO;
	}
}
