package com.rfm.packagegeneration.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.ResponseMicroServiceDTO;
import com.rfm.packagegeneration.service.PackageGenerationService;

@RestController
@RequestMapping("/package")
public class PackageGenerationController {
	private static final Logger logger = LogManager.getLogger("PackageGenerationController");
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	PackageGenerationService packageGenerationService;

	@PostMapping("/generationByDTO")
	public ResponseMicroServiceDTO generationByDTO(@RequestBody PackageGeneratorDTO packageFile) throws Exception {
		if(logger.isInfoEnabled()) {
			logger.info("generationByDTO: "+objectMapper.writeValueAsString(packageFile));
		}
		
		
		boolean productGenerated =false;
		boolean namesGenerated =false;
		boolean screenGenerated =false;
		boolean storeGenerated =false;
		Boolean promotionGenerated=null;
		ResponseMicroServiceDTO generatorDTO = new ResponseMicroServiceDTO();
		long productTime = 0L;
		long namesTime = 0L;
		long screenTime = 0L;
		long storeTime = 0L;
		long promotionTime = 0L;
		long packageTime = System.currentTimeMillis();
		
		if(packageFile.isGenerateProductXml()) {
			long startTime = System.currentTimeMillis();
			generatorDTO = packageGenerationService.generateProductDBXML(packageFile);
			productTime = System.currentTimeMillis() - startTime;
			packageFile.setAllProducts(generatorDTO.getAllProductsClone());
			startTime = System.currentTimeMillis();
			productGenerated = generatorDTO.isGenerated();
			namesGenerated = packageGenerationService.generateNamesXML(packageFile);
			namesTime = System.currentTimeMillis() - startTime;
		} 
		
		if(packageFile.isGenerateScreenXml()) {
			long startTime = System.currentTimeMillis();
			screenGenerated = packageGenerationService.generateScreenDBXML(packageFile);
			screenTime = System.currentTimeMillis() - startTime;
		}
		
		if(packageFile.isGenerateStoreXml()) {
			long startTime = System.currentTimeMillis();
			storeGenerated = packageGenerationService.generateStoreDBXML(packageFile).isGenerated();
			storeTime = System.currentTimeMillis() - startTime;
		}
		
		if(packageFile.isGeneratePromotionXml()) {
			long startTime = System.currentTimeMillis();
			promotionGenerated = packageGenerationService.generatePromotionDBXML(packageFile).isGenerated();
			promotionTime = System.currentTimeMillis() - startTime;
		}
		
		logger.info(" ***** PERFORMANCE product-db.xml:" + productTime + "ms");
		logger.info(" ***** PERFORMANCE names-db.xml:" + namesTime + "ms");
		logger.info(" ***** PERFORMANCE screen.xml:" + screenTime + "ms");
		logger.info(" ***** PERFORMANCE store-db.xml:" + storeTime + "ms");
		logger.info(" ***** PERFORMANCE promotion-db.xml:" + promotionTime + "ms");
		logger.info(" ***** PERFORMANCE Package:" + (System.currentTimeMillis() - packageTime) + "ms");
		
		generatorDTO.setNamesDBGenerated(namesGenerated);
		generatorDTO.setProductDBGenerated(productGenerated);
		generatorDTO.setStoreDBGenerated(storeGenerated);
		generatorDTO.setPromotionDBGenerated(promotionGenerated);
		generatorDTO.setScreenPOSGenerated(packageFile.getGeneratorDefinedValues().isScreenPOSGenerated());
		generatorDTO.setScreenHOTGenerated(packageFile.getGeneratorDefinedValues().isScreenHOTGenerated());
		
		generatorDTO.setGenerated(namesGenerated || productGenerated || screenGenerated || storeGenerated || promotionGenerated);
		
		return generatorDTO;
}

	@PostMapping("/generationProductDB")
	public ResponseEntity<Object> generationProductDB(@RequestBody PackageGeneratorDTO packageFile) throws Throwable{
		ResponseMicroServiceDTO generatorDTO=packageGenerationService.generateProductDBXML(packageFile);
			if (generatorDTO.isGenerated())
				return ResponseEntity.ok().build();
			throw new Exception("No generated");
	}
	
	@PostMapping("/generationStoreDB")
	public ResponseEntity<Object> generationStoreDB(@RequestBody PackageGeneratorDTO packageFile) throws Throwable{
		ResponseMicroServiceDTO generatorDTO=packageGenerationService.generateStoreDBXML(packageFile);
			if (generatorDTO.isGenerated())
				return ResponseEntity.ok().build();
			throw new Exception("No generated");
	}

	@PostMapping("/generationScreenDB")
	public ResponseEntity<Object> generationScreenDB(@RequestBody PackageGeneratorDTO packageFile) throws Throwable{
			if (packageGenerationService.generateScreenDBXML(packageFile))
				return ResponseEntity.ok().build();
			throw new Exception("No generated");
	}

	@PostMapping("/generationPromotionDB")
	public ResponseEntity<Object> generationPromotionDB(@RequestBody PackageGeneratorDTO packageFile) throws Throwable{
		ResponseMicroServiceDTO generatorDTO=packageGenerationService.generatePromotionDBXML(packageFile);
			if (generatorDTO.isGenerated())
				return ResponseEntity.ok().build();
			throw new Exception("No generated");
	}
}
