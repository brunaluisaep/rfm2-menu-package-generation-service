package com.rfm.packagegeneration.utility;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class DateUtility {

	/**
	 * convert current time in string to date.
	 * 
	 * @param date : It accepts date of type String.
	 * @return Date : This method returns date converted from String type to Date type.
	 * @throws ParseException : throws ParseException
	 */
	public static Date convertStringToDate(final String date) throws ParseException{
		java.sql.Date convertedDate = null;
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		convertedDate = new java.sql.Date(dateFormat.parse(date).getTime());
		return convertedDate;
	}
}
