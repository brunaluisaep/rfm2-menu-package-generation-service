package com.rfm.packagegeneration.service;

import static com.rfm.packagegeneration.constants.GeneratorConstant.KEY_RESTAURANT_ID;
import static com.rfm.packagegeneration.constants.GeneratorConstant.KEY_RESTAURANT_INST_ID;
import static com.rfm.packagegeneration.constants.GeneratorConstant.NAMES_DB_XML;
import static com.rfm.packagegeneration.constants.GeneratorConstant.NAMES_DB_XML_FILENAME;
import static com.rfm.packagegeneration.constants.GeneratorConstant.SCHEMA_TYPE_NAMES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.dao.LayeringProductDBDAO;
import com.rfm.packagegeneration.dao.NamesDBDAO;
import com.rfm.packagegeneration.dao.ProductDBDAO;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.utility.ObjectUtils;
import com.rfm.packagegeneration.utility.PackageWriter;

@Service
public class NamesDBService {

	private static final String FINAL_TAG_SYMBOL = ">";
	private static final String DVCE_ID = "DVCE_ID";
	private static final String LANG_ID = "LANG_ID";
	private static final String DVC_99999 = "99999";
	private static final String PACKAGE_DATA_NAMES_DB_XML_VERSION = "PACKAGE_DATA_NAMES_DB_XML_VERSION";
	private static final String KVS_MONITOR_REDESIGN_SUPPORT = "kvs-monitor-redesign-support";
	private static final String SMRT_RMDR = "SMRT_RMDR";
	private static final String PREN_SET_ID = "PREN_SET_ID";
	private static final String CUSM_SET_ID = "CUSM_SET_ID";
	private static final String LANG_CD = "LANG_CD";

	@Autowired
	NamesDBDAO namesGeneratorDAO;
	
	@Autowired
	LayeringProductDBDAO layeringDBDAO;
	
	@Autowired
	ProductDBDAO productDBDAO;

	public boolean generateFileString ( final PackageGeneratorDTO packageGeneratorDTO ) throws Exception {

		final PackageXMLParametersDTO[] params = packageGeneratorDTO.getPackageXmlParameter();
		String kvsFlag = null;
		String namesVersion = null;

		for ( final PackageXMLParametersDTO param : params ) {
			if ( null != param && ( null == kvsFlag || null == namesVersion ) ) {
				if ( KVS_MONITOR_REDESIGN_SUPPORT.equalsIgnoreCase( param.getParm_na() ) ) {
					kvsFlag = param.getParm_val();
				}
				else if ( PACKAGE_DATA_NAMES_DB_XML_VERSION.equalsIgnoreCase( param.getParm_na() ) ) {
					namesVersion = param.getParm_val();
				}
			}
		}

		final List< Map< String, Object > > localizationSets = namesGeneratorDAO.getLocalizationSets(Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get( KEY_RESTAURANT_ID ).toString()),
			Long.parseLong(packageGeneratorDTO.getRestaurantDataPointers().get( KEY_RESTAURANT_INST_ID ).toString()) );
		Long parentSetId = null;

		if ( localizationSets != null && !localizationSets.isEmpty() && localizationSets.get( 0 ) != null && localizationSets.get( 0 ).get( PREN_SET_ID ) != null ) {
			parentSetId = null != localizationSets.get( 0 ).get( PREN_SET_ID )
					? Long.valueOf( localizationSets.get( 0 ).get( PREN_SET_ID ).toString() )
							: null;
		}
		Long childSetId = null;

		if ( localizationSets != null && !localizationSets.isEmpty() && localizationSets.get( 0 ) != null && localizationSets.get( 0 ).get( CUSM_SET_ID ) != null ) {
			childSetId = null != localizationSets.get( 0 ).get( CUSM_SET_ID )
					? Long.valueOf( localizationSets.get( 0 ).get( CUSM_SET_ID ).toString() )
							: null;
		}

		Long langSetId = parentSetId;
		final String countryCode = namesGeneratorDAO.getCountryCode( parentSetId, childSetId, packageGeneratorDTO.getDate() );
		final int languageCount = namesGeneratorDAO.getLanguageCount( childSetId );

		if ( languageCount > 0 ) {
			langSetId = childSetId;
		}

		Map< String, Map < String, String >> allProducts = null;
		Map< String, String > customSmartReminders = null;
		Map< String, String > allSmartReminders = new HashMap<>();

		if ( ( null == allSmartReminders ) || ( ( null != allSmartReminders ) && allSmartReminders.isEmpty() ) ) {
			allSmartReminders = namesGeneratorDAO.getAllSmartReminders( packageGeneratorDTO.getMarketID(), packageGeneratorDTO.getDate() );
		}

		Long deviceLex = null;

		if ( null == deviceLex ) {
			deviceLex = namesGeneratorDAO.getDeviceLex( packageGeneratorDTO.getMarketID() );
		}

		final List< Map< String, String > > allLocales = namesGeneratorDAO.getAllLocales( langSetId, deviceLex, packageGeneratorDTO.getMarketID(), packageGeneratorDTO.getDate() );
		PackageWriter bufferWriter = new PackageWriter( NAMES_DB_XML_FILENAME, NAMES_DB_XML, packageGeneratorDTO, SCHEMA_TYPE_NAMES );

		if ( namesVersion == null ) {
			namesVersion = namesGeneratorDAO.getParamValue( PACKAGE_DATA_NAMES_DB_XML_VERSION, packageGeneratorDTO.getMarketID() );
		}
		bufferWriter.append( "<NamesDb version=\"" + namesVersion + "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " );
		bufferWriter.append( " xsi:noNamespaceSchemaLocation=\"/RFM2/RFM2PackageConf/PackageXSD/2.1/names-db.xsd\" > " );
		
		String sequenceNumber = productDBDAO.getValuesFromGlobalParam(packageGeneratorDTO.getMarketID(), ProductDBConstant.CONSTANT_ENABLE_SEAMLESS );
		if(sequenceNumber.equals("true")) {
		if ( packageGeneratorDTO.isGeneratedSeqNum() && packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() != null && !packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO().isEmpty() ) {
			bufferWriter.append( "<NamesDBSeqNumber>" + packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() + "</NamesDBSeqNumber>" );
			}
		}

		final List< Long > setIds = namesGeneratorDAO.retrieveMenuItemSetsId( packageGeneratorDTO.getNodeID(), packageGeneratorDTO.getMarketID(), packageGeneratorDTO.getDate() );
		final Long masterSetId = namesGeneratorDAO.retrieveMasterSetId( packageGeneratorDTO.getMarketID() );
		final List< Long > restSetIds = namesGeneratorDAO.retrieveRestSetId( packageGeneratorDTO.getNodeID(), packageGeneratorDTO.getMarketID() );
		
		Map<Long, Product> namesLayeringObject = getProductGeneralSettingByRest(packageGeneratorDTO.getMarketID(), masterSetId, packageGeneratorDTO.getDate(), setIds, restSetIds);
		for ( final Map< String, String > localeInfo : allLocales ) {

			bufferWriter.append( "<Language code=\"" + localeInfo.get( LANG_CD ) + "_" + countryCode );
			if ( !DVC_99999.equals( localeInfo.get( DVCE_ID ) ) ) {
				bufferWriter.append( "_" + localeInfo.get( "DVCE_NA" ) );
			}

			bufferWriter.append( "\" name=\"" + localeInfo.get( "LCLE_NA" ) );
			if ( !DVC_99999.equals( localeInfo.get( DVCE_ID ) ) ) {
				bufferWriter.append( "(" + localeInfo.get( "DVCE_NA" ) + ")" );
			}

			bufferWriter.append( "\" parent=\"" + localeInfo.get( LANG_CD ) + "\" >" );
			
			allProducts = namesGeneratorDAO.populateNamesMap(packageGeneratorDTO, localeInfo, namesLayeringObject);

			customSmartReminders = namesGeneratorDAO.getCustomizedSmartReminders(Long.parseLong(localeInfo.get( LANG_ID) ),Long.parseLong(localeInfo.get( DVCE_ID)), packageGeneratorDTO.getMarketID(),
					packageGeneratorDTO.getPackageSmartReminderDTO()[0].getParentSetId(),
					packageGeneratorDTO.getPackageSmartReminderDTO()[0].getCustomSetId() );
			Map< String, String > customizedProductNames;
			
			//if no data is returned, an exception must be thrown 
			if (allProducts != null && allProducts.isEmpty()) {
				bufferWriter.close();
				throw new Exception("error language code:" +  localeInfo.get( LANG_CD ));
			}
			
			for (Map.Entry <String, Map<String, String>> entry : allProducts.entrySet()) {
				customizedProductNames = entry.getValue();

				bufferWriter.append( "<ProductName>" );
				bufferWriter.append( "<ProductCode>" + customizedProductNames.get( "PRD_CD" ) + "</ProductCode>" );
				bufferWriter.append( getTag( "ShortName", customizedProductNames.get( "SHRT_NA" ) ) );
				bufferWriter.append( getTag( "LongName", customizedProductNames.get( "LNG_NA" ) ) );
				bufferWriter.append( getTag( "DTName", customizedProductNames.get( "DRV_NA" ) ) );
				bufferWriter.append( getTag( "SummaryMonitorName", customizedProductNames.get( "SUMR_MNIT_NA" ) ) );

				if ( "Y".equals( kvsFlag ) ) {
					bufferWriter.append( getTag( "ShortMonitorName", customizedProductNames.get( "SHRT_MNIT_NA" ) ) );
					bufferWriter.append( getTag( "AlternativeName", customizedProductNames.get( "ALT_NA" ) ) );
				}

				bufferWriter.append( getTag( "CSOName", customizedProductNames.get( "CSO_NA" ) ) );
				bufferWriter.append( getTag( "CSOSizeName", customizedProductNames.get( "CSO_SIZE_NA" ) ) );
				bufferWriter.append( getTag( "CSOGenericName", customizedProductNames.get( "CSO_GEN_NA" ) ) );

				if ( DVC_99999.equals( localeInfo.get( DVCE_ID ) ) ) {
					bufferWriter.append( getTag( "PromotionLabel", customizedProductNames.get( "PRMO_TX_LABL" ) ) );
				}

				bufferWriter.append( getTag( "CODName", customizedProductNames.get( "COD_NA" ) ) );


				if ( ObjectUtils.isFilled( customizedProductNames.get( SMRT_RMDR ) ) ) {
					String keySMRT = customizedProductNames.get( SMRT_RMDR ) + ";" + localeInfo.get( LANG_ID ) + ";" + localeInfo.get( DVCE_ID );
					String smrtXml = customSmartReminders.get( customizedProductNames.get( SMRT_RMDR ) );
					if ( !ObjectUtils.isFilled( smrtXml ) ) {
						smrtXml = allSmartReminders.get( keySMRT );
					}
					if ( ObjectUtils.isFilled( smrtXml ) ) {
						bufferWriter.append( smrtXml );
					}
				}
				bufferWriter.append( getTag( "HomeDeliveryName", customizedProductNames.get( "HOME_DELIVERY_NA" ) ) );

				bufferWriter.append( "</ProductName>" );
			}
			bufferWriter.append( "</Language>" );
		}
		bufferWriter.append( "</NamesDb>" );
		bufferWriter.close();

		return true; 
	}
			

			
	
	public String getTag ( final String tagName, final String customizedValue ) {

		final StringBuilder tag = new StringBuilder( "" );
		if ( ObjectUtils.isFilled( customizedValue ) ) {
			tag.append( "<" + tagName + FINAL_TAG_SYMBOL );
			tag.append( ObjectUtils.replaceSpecialCharacters( customizedValue ) );
			tag.append( "</" + tagName + FINAL_TAG_SYMBOL );
		}
		return tag.toString();
	}

	public Map<Long, Product> getProductGeneralSettingByRest(Long marketId, Long masterSetId, 
			String effectiveDate, List<Long> lstSetIds, List<Long> restSetIds) throws Exception {
		Map<Long, Product> listOfProductsByMaster = new HashMap<>();
		//master
		layeringDBDAO.getProductGeneralSettingMenuItemNames(listOfProductsByMaster, marketId,
				effectiveDate, ProductDBConstant.MMI_SET_TYPE, masterSetId);
		//set
		for (Long setId : lstSetIds) {
			layeringDBDAO.getProductGeneralSettingMenuItemNames(listOfProductsByMaster, marketId,
					effectiveDate, ProductDBConstant.MIS_TYPE, setId);
		}
		//rest
		for (Long restSetId : restSetIds) {
			layeringDBDAO.getProductGeneralSettingMenuItemNames(listOfProductsByMaster, marketId,
					effectiveDate, ProductDBConstant.RMI_TYPE, restSetId);
		}
		return listOfProductsByMaster;
	}

}
