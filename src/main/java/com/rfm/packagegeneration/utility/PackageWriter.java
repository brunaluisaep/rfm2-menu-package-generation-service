package com.rfm.packagegeneration.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.rfm.packagegeneration.constants.GeneratorConstant;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;

public class PackageWriter {
	
	private BufferedWriter out = null;
	private FileOutputStream fileOutputStream;
	private OutputStreamWriter outputStream;
	private File outFile;

	/**
	 * @param strFilename
	 * @param screenPos
	 * @param objPackageGeneratorDTO
	 * @param sCHEMA_TYPE_SCREEN
	 * @throws IOException 
	 * @throws GeneratorExternalInterfaceException
	 */
	public PackageWriter(String strFilename, String screenPos, PackageGeneratorDTO objPackageGeneratorDTO, String sCHEMA_TYPE_SCREEN) throws IOException{
		if (objPackageGeneratorDTO == null) {
			//missing objPackageGenerator
			return;
		}
		
		outFile = new File(objPackageGeneratorDTO.getGeneratorDefinedValues().getEffectiveDatePath() + strFilename);

		fileOutputStream = new FileOutputStream(outFile);
		outputStream = new OutputStreamWriter(fileOutputStream, GeneratorConstant.UTF_ENCODING);
		out = new BufferedWriter(outputStream);
		if (strFilename.equals(GeneratorConstant.NAMES_DB_XML_FILENAME) || strFilename.equals(GeneratorConstant.STORE_DB_XML_FILENAME) 
				||strFilename.equals(GeneratorConstant.PRODUCTS_DB_XML_FILENAME) || strFilename.equals(GeneratorConstant.PROMOTION_DB_XML_FILENAME)) {
			append("<?xml version= \"1.0\" encoding=\"UTF-8\"?>");
		}
	}



	/**
	 * @param text
	 * @throws IOException 
	 */
	public void append(StringBuilder text) throws IOException {
		out.append(text);
		
	}

	/**
	 * @param text
	 * @throws IOException 
	 */
	public void append(String text) throws IOException {
		out.append(text);
	}
	
	/**
	 * @throws GeneratorExternalInterfaceException
	 */
	public void remove() throws IOException {
		close();
		if (outFile != null) {
			outFile.delete();
		}
	}
	/**
	 * Write the buffer and close the file
	 * @throws IOException 
	 */
	public void close() throws IOException {
		try {
			
			if (out != null) {
				out.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}

		} catch (final IOException ioexception) {
			throw new IOException();
		}

	}
}
