package com.rfm.packagegeneration.service;

import static com.rfm.packagegeneration.constants.GeneratorConstant.DIRECTORY_SEPERATOR;
import static com.rfm.packagegeneration.constants.GeneratorConstant.ELEMENT_RESTAURANT_NUMBER;
import static com.rfm.packagegeneration.constants.GeneratorConstant.FILETYPE_PRODUCT_DB_XML;
import static com.rfm.packagegeneration.constants.GeneratorConstant.INT_ONE_CONSTANT;
import static com.rfm.packagegeneration.constants.GeneratorConstant.INT_ZERO_CONSTANT;
import static com.rfm.packagegeneration.constants.GeneratorConstant.STRING_FIVE_CONSTANT;
import static com.rfm.packagegeneration.constants.GeneratorConstant.XML_DIRECTORY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rfm.packagegeneration.dto.GeneratorDefinedValues;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.ResponseMicroServiceDTO;
import com.rfm.packagegeneration.utility.PackageGenDateUtility;
import com.rfm.packagegeneration.utility.PackageGenerationUtility;

@Service
public class PackageGenerationService {

	@Autowired
	NamesDBService namesDbGeneratorService;
	
	@Autowired
	ScreenService screenDbGeneratorService;
	
	@Autowired
	ProductDBService productDbGeneratorService;
	
	@Autowired
	StoreDBService storeDbGeneratorService;
	
	@Autowired
	PromotionDBService promotionDbGeneratorService;

	@Value( "${package.generation.directory}" )
	private String directoryXmls;
	
	@Value("${package.generation.dev.env}")
	private boolean isDev;

	/**
	 * A helper method, for creating the names.xml
	 * @param packageGeneratorDTO PackageGeneratorDTO
	 * @throws GeneratorBusinessException          generatorBusinessException
	 * @throws GeneratorExternalInterfaceException generatorExternalInterfaceException
	 */
	public boolean generateNamesXML ( final PackageGeneratorDTO packageGeneratorDTO ) throws Exception {
		/**  names-db.xml. */
		/** Decide whether or not product-db.xml need to be created. */

		if ( packageGeneratorDTO != null && packageGeneratorDTO.getGeneratorDefinedValues() != null && packageGeneratorDTO.getGeneratorDefinedValues().getEffectiveDatePath() == null ) {
			packageGeneratorDTO.setGeneratorDefinedValues( populateGeneratorDefinedValues( packageGeneratorDTO, directoryXmls ) );
			PackageGenerationUtility.getInstance().makeDir( packageGeneratorDTO.getGeneratorDefinedValues().getEffectiveDatePath() );
		}
		boolean generateProduct = true;
		if ( packageGeneratorDTO.getScheduleType().equals( STRING_FIVE_CONSTANT ) ) {
			generateProduct = true;
		}
		else {
			generateProduct = PackageGenerationUtility.getInstance().shouldFileBeCreated( FILETYPE_PRODUCT_DB_XML, packageGeneratorDTO );
		}
		if ( generateProduct ) {
			if ( !( packageGeneratorDTO.getScheduleType().equals( STRING_FIVE_CONSTANT ) ) ) {
				packageGeneratorDTO.getGeneratorDefinedValues().setNamesDBGenerated( namesDbGeneratorService.generateFileString( packageGeneratorDTO ) );
			}
		}
		return packageGeneratorDTO.getGeneratorDefinedValues().isNamesDBGenerated();

	}

	/**
	 * This method populates Generator Defined Values in DTO.
	 * @param objPackageGeneratorDTONew PackageGeneratorDTO
	 * @return PackageGeneratorDTO.GeneratorDefinedValues
	 * @throws GeneratorExternalInterfaceException Exception
	 */
	public GeneratorDefinedValues populateGeneratorDefinedValues ( final PackageGeneratorDTO objPackageGeneratorDTONew, final String directoryXmls ) throws Exception {

		final GeneratorDefinedValues generatorDefinedValues = new GeneratorDefinedValues();
		final String                 timeStamp              = PackageGenDateUtility.getCurrentTimestampForMarket( objPackageGeneratorDTONew.getMarketts() );
		generatorDefinedValues.setTimeStamp( timeStamp );
		String directoryForXMLFiles = createDirectoryForXMLFiles( objPackageGeneratorDTONew.getMarketID(),
				objPackageGeneratorDTONew.getRestaurantDataPointers().get( ELEMENT_RESTAURANT_NUMBER ).toString(),
				timeStamp.concat( objPackageGeneratorDTONew.getDate().replace( "/", "" ) ), directoryXmls );
		generatorDefinedValues.setEffectiveDatePath( directoryForXMLFiles );

		//		generatorDefinedValues.setErrorElement(createErrorElement());
		if ( ( objPackageGeneratorDTONew.getProducts() == null ) || ( objPackageGeneratorDTONew.getProducts() != null && objPackageGeneratorDTONew.getProducts().isEmpty() ) ) {
			generatorDefinedValues.setDoProductsExist( Boolean.FALSE );
		}
		else {
			generatorDefinedValues.setDoProductsExist( Boolean.TRUE );
		}
		return generatorDefinedValues;
	}

	/**
	 * A helper method for creating the path wherein the files will placed.
	 * @param marketID         String
	 * @param restaurantNumber String
	 * @param timeStamp        String
	 * @return String, directoryPath.
	 */
	private String createDirectoryForXMLFiles ( final Long marketID, final String restaurantNumber, final String timeStamp, final String directoryXmls ) {

		final StringBuilder sbPath = new StringBuilder( directoryXmls );
		sbPath.append( DIRECTORY_SEPERATOR );
		sbPath.append( marketID ).append( DIRECTORY_SEPERATOR );
		sbPath.append( restaurantNumber ).append( DIRECTORY_SEPERATOR );
		sbPath.append( timeStamp ).append( DIRECTORY_SEPERATOR );
		sbPath.append( XML_DIRECTORY );

		return sbPath.toString();
	}

	public void setDirectoryXmls ( String directoryXmls ) {

		this.directoryXmls = directoryXmls;
	}

	public ResponseMicroServiceDTO generateProductDBXML( final PackageGeneratorDTO packageGeneratorDTO ) throws Exception {
		ResponseMicroServiceDTO generatorDTO=new ResponseMicroServiceDTO();
			PackageGenerationUtility.getInstance().makeDir( packageGeneratorDTO.getGeneratorDefinedValues().getEffectiveDatePath() );
			//packageGeneratorDTO.getGeneratorDefinedValues().setProductDBGenerated(productDbGeneratorService.generateFileString( packageGeneratorDTO ) );
			generatorDTO=productDbGeneratorService.generateFileString( packageGeneratorDTO );
			return generatorDTO;
	}

	public ResponseMicroServiceDTO generateStoreDBXML(PackageGeneratorDTO packageGeneratorDTO) throws Exception {
		ResponseMicroServiceDTO generatorDTO=new ResponseMicroServiceDTO();
		PackageGenerationUtility.getInstance().makeDir( packageGeneratorDTO.getGeneratorDefinedValues().getEffectiveDatePath() );
		generatorDTO=storeDbGeneratorService.generateFileString( packageGeneratorDTO );
		return generatorDTO;
	}
	
	public boolean generateScreenDBXML(PackageGeneratorDTO packageFile){
		PackageGenerationUtility.getInstance().makeDir( packageFile.getGeneratorDefinedValues().getEffectiveDatePath() );
		boolean[] result=screenDbGeneratorService.generateFile(packageFile);
		
		packageFile.getGeneratorDefinedValues().setScreenPOSGenerated(result[INT_ZERO_CONSTANT]);
		packageFile.getGeneratorDefinedValues().setScreenHOTGenerated(result[INT_ONE_CONSTANT]);
		
		return packageFile.getGeneratorDefinedValues().isScreenHOTGenerated() || packageFile.getGeneratorDefinedValues().isScreenPOSGenerated();
	}
	
	public ResponseMicroServiceDTO generatePromotionDBXML(PackageGeneratorDTO packageGeneratorDTO) throws Exception {
		ResponseMicroServiceDTO generatorDTO=new ResponseMicroServiceDTO();
		PackageGenerationUtility.getInstance().makeDir( packageGeneratorDTO.getGeneratorDefinedValues().getEffectiveDatePath());
		generatorDTO=promotionDbGeneratorService.generateFileString( packageGeneratorDTO );
		return generatorDTO;
	}

}
