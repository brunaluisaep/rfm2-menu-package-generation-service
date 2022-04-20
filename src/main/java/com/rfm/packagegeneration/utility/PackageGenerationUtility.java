package com.rfm.packagegeneration.utility;
import static com.rfm.packagegeneration.constants.GeneratorConstant.ELEMENT_XML_FILELIST;
import static com.rfm.packagegeneration.constants.GeneratorConstant.FILETYPE_PRODUCT_DB_XML;
import static com.rfm.packagegeneration.constants.GeneratorConstant.FILETYPE_PROMOTION_DB_XML;
import static com.rfm.packagegeneration.constants.GeneratorConstant.FILETYPE_STORE_DB_XML;
import static com.rfm.packagegeneration.constants.GeneratorConstant.INT_ONE_CONSTANT;
import static com.rfm.packagegeneration.constants.GeneratorConstant.INT_ZERO_CONSTANT;
import static com.rfm.packagegeneration.constants.GeneratorConstant.PRODUCT_DB_XML;
import static com.rfm.packagegeneration.constants.GeneratorConstant.PROMOTION_DB_XML;
import static com.rfm.packagegeneration.constants.GeneratorConstant.STORE_DB_XML;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rfm.packagegeneration.constants.GeneratorConstant;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.logging.annotation.TrackedMethod;

public final class PackageGenerationUtility {
	private static PackageGenerationUtility PACKAGE_GENERATION_UTILITY = new PackageGenerationUtility();
	private static final Logger LOGGER = LogManager.getLogger("PackageGenerationUtility");
	
	public static PackageGenerationUtility getInstance() {
		return PACKAGE_GENERATION_UTILITY;
	}
	/**
	 * Method for creating the directory for XML Files.
	 * 
	 * @param path String, path at which directory needs to be created
	 * @throws GeneratorExternalInterfaceException Exception if directory could not be created
	 */
	@TrackedMethod
	public void makeDir(final String path) {
		if (path == null) {
			LOGGER.error("missing path");
		}
		try {
			final File file = new File(path);
			file.mkdirs();
		} catch (final Exception exception) {
			logException(exception);
		}
	}
	
	/**
	 * Logs the first two lines of Exception Stack Trace.
	 * 
	 * @param exception Exception
	 */
	public static void logException(final Exception exception) {
		try {
			LOGGER.error("\n************** Exception begins ***************");
			final StackTraceElement[] steArray = exception.getStackTrace();
			for (int i = INT_ZERO_CONSTANT; i <= INT_ONE_CONSTANT; i++) {
				LOGGER.error(steArray[i]);
			}
			LOGGER.error("************** Exception ends ***************\n");
		} catch (final Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	/**
	 * Given the restaurant manifest, decided whether or not a particular type of XML file (Store, Product, Names, Screen) be created for a particular
	 * effective date.
	 * 
	 * @param fileType int
	 * @param packageGeneratorDTONew PackageGeneratorDTO
	 * @return boolean
	 * @throws GeneratorBusinessException Exception
	 */
	@TrackedMethod
	public boolean shouldFileBeCreated(final int fileType, final PackageGeneratorDTO packageGeneratorDTONew) {

		if (packageGeneratorDTONew == null) {
			return false;
		}

		final boolean shouldFileBeCreated = false;
		String strFileName = "";
		switch (fileType) {
		case FILETYPE_PRODUCT_DB_XML:
			strFileName = PRODUCT_DB_XML;
			break;
		case FILETYPE_STORE_DB_XML:
			strFileName = STORE_DB_XML;
			break;
		case FILETYPE_PROMOTION_DB_XML:
			strFileName = PROMOTION_DB_XML;
			break;
		default:
			break;
		}
		final Map<String, Object> restaurantDataPointers = packageGeneratorDTONew.getRestaurantDataPointers();
		final List<String> xmlFileList = (List<String>) restaurantDataPointers.get(ELEMENT_XML_FILELIST);
		final int nIndex = xmlFileList.size();
		for (int i = INT_ZERO_CONSTANT; i < nIndex; i++) {
			if (strFileName.equalsIgnoreCase(xmlFileList.get(i))) {
				return Boolean.TRUE;
			}
		}
		return shouldFileBeCreated;
	}
	
	
	

	public String getAttrValuePair(String attr, String val, char c, boolean trimVar) {
		StringBuilder combinedValue = new StringBuilder();
		if (c == 'N') {
			if (val!=null && val.length() > 0) {
				combinedValue.append(' ').append(attr).append('=').append('"').append(StringHelper.replaceSpecialCharacters(val,trimVar)).append('"');
			}
		} else {
			combinedValue.append(' ').append(attr).append('=').append('"').append(StringHelper.replaceSpecialCharacters(val,trimVar)).append('"');
		}

		return combinedValue.toString();
	}
	
	public String getAttrValuePair(String attr, String val, char c) {
		boolean trimVar = true;
		if(attr.equals(GeneratorConstant.CAT_DESCRIPTION)) {
			trimVar=false;
		}
		return getAttrValuePair(attr, val,  c,  trimVar);
	}
	
}
