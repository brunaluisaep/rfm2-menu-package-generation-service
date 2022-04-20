package com.rfm.packagegeneration.utility;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
public final class ObjectUtils {
	private static final XPathFactory factory = XPathFactory.newInstance();
	private static final XPath xpath = factory.newXPath();

	private ObjectUtils() {
	}

	/**
	 * Method to check if String object is filled.
	 *
	 * @param str String
	 * @return boolean
	 */
	public static boolean isFilled(final String str){
		return (str != null) && !"".equals(str.trim()) && !"null".equals(str.trim());
	}

	public static String replaceSpecialCharacters(final String str){
		if (str == null) {
			return null;
		}
		String returnStr = str;

		returnStr = returnStr.replace("&amp;","ampersandString"); 
		returnStr =  returnStr.replace("&quot;","quotString"); 
		returnStr = returnStr.replace("&lt;","leftTagString"); 
		returnStr = returnStr.replace("&gt;","rightTagString");  
		returnStr = returnStr.replace("&apos;","apostrophe");

		returnStr = returnStr.replace("&","&amp;"); 
		returnStr = returnStr.replace("\"","&quot;"); 
		returnStr = returnStr.replace("'","&apos;"); 
		returnStr = returnStr.replace("<","&lt;"); 
		returnStr = returnStr.replace(">","&gt;"); 

		returnStr = returnStr.replace("ampersandString","&amp;"); 
		returnStr = returnStr.replace("quotString","&quot;"); 
		returnStr = returnStr.replace("leftTagString","&lt;"); 
		returnStr =  returnStr.replace("rightTagString","&gt;"); 
		returnStr = returnStr.replace("apostrophe","&apos;");

		return returnStr;
	}

	public static XPath getXPath(){
		return xpath;
	}
}