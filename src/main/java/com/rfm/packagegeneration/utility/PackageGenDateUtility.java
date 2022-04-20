package com.rfm.packagegeneration.utility;
import static com.rfm.packagegeneration.constants.GeneratorConstant.INT_TEN_CONSTANT;
import static com.rfm.packagegeneration.constants.GeneratorConstant.INT_THREE_CONSTANT;
import static com.rfm.packagegeneration.constants.GeneratorConstant.INT_TWELVE_CONSTANT;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.rfm.packagegeneration.constants.GeneratorConstant;
public class PackageGenDateUtility {
	/**
	 * This method gets the current timestamp based on the Timezone of the market.
	 * 
	 * @param marketID
	 * @return Current Timestamp
	 * @throws DBException
	 */
	public static String getCurrentTimestampForMarket(final String timeZone) {
		final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(timeZone));
		int iMonth = calendar.get(Calendar.MONTH);
		iMonth++;
		String sMonth = null;
		if (iMonth < INT_TEN_CONSTANT) {
			sMonth = "0" + Integer.toString(iMonth);
		} else {
			sMonth = Integer.toString(iMonth);
		}
		int iDay = calendar.get(Calendar.DATE);
		String sDay = null;
		if (iDay < INT_TEN_CONSTANT) {
			sDay = "0" + Integer.toString(iDay);
		} else {
			sDay = Integer.toString(iDay);
		}
		int iYear = calendar.get(Calendar.YEAR);
		int iHour = calendar.get(Calendar.HOUR);
		int iMinute = calendar.get(Calendar.MINUTE);
		int iSec = calendar.get(Calendar.SECOND);
		int iMiliSec = calendar.get(Calendar.MILLISECOND);
		int iAM_PM = calendar.get(Calendar.AM_PM);
		if (iAM_PM == 1) {
			if (iHour != INT_TWELVE_CONSTANT) {
				iHour = iHour + INT_TWELVE_CONSTANT;
			}
		}
		return String.valueOf(iYear) + sMonth + sDay + fullZero(String.valueOf(iHour), 2) + fullZero(String.valueOf(iMinute), 2)
				+ fullZero(String.valueOf(iSec), 2) + fullZero(String.valueOf(iMiliSec), INT_THREE_CONSTANT);
	}
	/**
	 * This method will pad the string with leading Zerors.
	 * 
	 * @return String
	 * @param inMsg String
	 * @param inLen int
	 */
	public static String fullZero(final String inMsg, final int inLen){
		String inwkMsg = inMsg;
		String outMsg = "";
		if ((inMsg != null) && (!(inMsg.equals("")))) {
			StringBuffer sb = new StringBuffer(inwkMsg);
			int msgLen = inwkMsg.length();
			int zeroNum = inLen - msgLen;
			for (int i = 0; i < zeroNum; i++) {
				sb.insert(0, "0");
			}
			outMsg = sb.toString();
		}
		return String.valueOf(outMsg);
	}
	
	public static String storeDbDate(String str) {
		
		String year = str.substring(0, 4);
		String month = str.substring(4, 6);
		String date = str.substring(6, 8);
		return String.join("-",year,month,date);	
		
		
	}
	
public static String storeDbTime(String str) {
		

		String hour = str.substring(0, 2);
		String minute = str.substring(2, 4);
		String second = str.substring(4, 6);		
		return String.join(":", hour,minute,second);		
		
	}

public static String Dateformat(Date date) {
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy");
	String convertDate = simpleDateFormat.format(date);
	return convertDate;
	
}

	public static String getTimeFormatOfGMT() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(GeneratorConstant.DATEFORMAT);
		sdf.setTimeZone(TimeZone.getTimeZone(GeneratorConstant.TIMEZONE));
		return sdf.format(date);
	}

}
