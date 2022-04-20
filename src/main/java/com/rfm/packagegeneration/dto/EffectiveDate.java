package com.rfm.packagegeneration.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EffectiveDate implements Serializable {

	/**
     *
     */
	private static final long serialVersionUID = 388449180799789121L;
	private int date;
	private int hour;
	private int minutes;
	private int month;
	private int seconds;
	private int year;
	private static final String DATE_TMSTMP_FRMT = "MM/dd/yyyy HH:mm:ss";
	private static final String DATE_FORMAT = "MM/dd/yyyy";	


	/**
	 * Default Constructor.
	 */
	public EffectiveDate() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param year int
	 * @param month int
	 * @param date int
	 * @param hour int
	 * @param minutes int
	 */
	public EffectiveDate(final int year, final int month, final int date, final int hour, final int minutes) {
		super();
		this.year = year;
		this.month = month;
		this.date = date;
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = 0;
	}

	/**
	 * Constructor.
	 *
	 * @param year int
	 * @param month int
	 * @param date int
	 * @param hour int
	 * @param minutes int
	 * @param seconds int
	 */
	public EffectiveDate(final int year, final int month, final int date, final int hour, final int minutes, final int seconds) {
		super();
		this.year = year;
		this.month = month;
		this.date = date;
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public EffectiveDate(Date effectiveDate) {
		super();
		
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(effectiveDate);
		
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		this.date = calendar.get(Calendar.DATE);
		this.hour = calendar.get(Calendar.HOUR);
		this.minutes = calendar.get(Calendar.MINUTE);
		this.seconds = calendar.get(Calendar.SECOND);
	}

	/**
	 * @param effectiveDate effectiveDate
	 * @return int
	 */
	public int compareTo(final EffectiveDate effectiveDate){
		if (this.equals(effectiveDate)) {
			return 0;
		} else {
			if (Long.parseLong(String.format("%04d%02d%02d%02d%02d", this.year, this.month, this.date, this.hour, this.minutes)) > 
		        Long.parseLong(String.format("%04d%02d%02d%02d%02d", effectiveDate.year, effectiveDate.month, effectiveDate.date, effectiveDate.hour, effectiveDate.minutes))) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
	/**
	 * 
	 * @param effectiveDate
	 * @param status
	 * @param active
	 */
	


	/**
	 * equals override.
	 *
	 * @param obj Object
	 * @return boolean boolean
	 **/
	@Override
	public boolean equals(final Object obj){
		final EffectiveDate other = (EffectiveDate) obj;
		if (obj == null || (getClass() != obj.getClass()) || (date != other.date) || (hour != other.hour) || (minutes != other.minutes)
			|| (month != other.month) || (year != other.year)) {
			return false;
		}else if(this.getClass() != obj.getClass()){
			return true;
		}
		return true;
	}

	/**
	 * This method returns the date.
	 *
	 * @return the date
	 */
	public int getDate(){
		return date;
	}

	/**
	 * This method returns the date string on the basis of a particular format. Following parameters are required:
	 * <ul>
	 * <li>format</li>
	 * </ul>
	 *
	 * @param format String
	 * @return String
	 */
	public String getFormattedDate(final String format){
		final Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, getYear());
		calendar.set(Calendar.MONTH, getMonth());
		calendar.set(Calendar.DATE, getDate());
		calendar.set(Calendar.HOUR_OF_DAY, getHour());
		calendar.set(Calendar.MINUTE, getMinutes());
		calendar.set(Calendar.SECOND, getSeconds());
		final Date dateObj = calendar.getTime();
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(dateObj);
		
	}

	/**
	 * This method returns the hour.
	 *
	 * @return the hour
	 */
	public int getHour(){
		return hour;
	}

	/**
	 * This method returns the minutes.
	 *
	 * @return the minutes
	 */
	public int getMinutes(){
		return minutes;
	}

	/**
	 * This method returns the month.
	 *
	 * @return the month
	 */
	public int getMonth(){
		return month;
	}

	/**
	 * This method returns instance of seconds of type int.
	 *
	 * @return the seconds
	 */
	public int getSeconds(){
		return seconds;
	}

	/**
	 * This method returns the year.
	 *
	 * @return the year
	 */
	public int getYear(){
		return year;
	}

	/**
	 * Hash code override.
	 *
	 * @param Integer
	 * @return Integer Integer
	 **/
	@Override
	public int hashCode(){
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + date;
		result = PRIME * result + hour;
		result = PRIME * result + minutes;
		result = PRIME * result + month;
		result = PRIME * result + year;
		return result;
	}

	/**
	 * This method sets the date.
	 *
	 * @param date the date to set
	 */
	public void setDate(final int date){
		this.date = date;
	}

	/**
	 * This method sets the hour.
	 *
	 * @param hour the hour to set
	 */
	public void setHour(final int hour){
		this.hour = hour;
	}

	/**
	 * This method sets the minutes.
	 *
	 * @param minutes the minutes to set
	 */
	public void setMinutes(final int minutes){
		this.minutes = minutes;
	}

	/**
	 * This method sets the month.
	 *
	 * @param month the month to set
	 */
	public void setMonth(final int month){
		this.month = month;
	}

	/**
	 * This method sets the value of seconds.
	 *
	 * @param seconds the seconds to set
	 */
	public void setSeconds(final int seconds){
		this.seconds = seconds;
	}

	/**
	 * This method sets the year.
	 *
	 * @param year the year to set
	 */
	public void setYear(final int year){
		this.year = year;
	}

	/**
	 * Override toString method.
	 *
	 * @return String
	 **/
	@Override
	public String toString(){
		final StringBuilder sbEffDate = new StringBuilder();
		sbEffDate.append(this.month).append("/").append(this.date).append("/").append(this.year).append(" ").append(this.hour).append(":").append(
			this.minutes);
		return sbEffDate.toString();
	}

	/**
	 * This method returns the date string on the basis of a particular format. Following parameters are required:
	 * <ul>
	 * <li>format</li>
	 * </ul>
	 *
	 * @param format String
	 * @return String
	 */
	public String getFormatDate(final String format){
		final Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, getYear());
		calendar.set(Calendar.MONTH, getMonth());
		calendar.set(Calendar.DATE, getDate());
		final Date dateObj = calendar.getTime();
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(dateObj);
		
	}

	public String toStringDt() {
		final Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, getYear());
		calendar.set(Calendar.MONTH, getMonth());
		calendar.set(Calendar.DATE, getDate());
		final java.sql.Date dateObj = new java.sql.Date(calendar.getTime().getTime());
		
		return convertDateToString(dateObj);
	}

	
	/**
	 * @param paramDate Date
	 * @return String
	 */
	public static String convertDateToString(final Date paramDate){
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(paramDate);
	}
	
	/**
	 * Format using RFM2Constant.DATE_TMSTMP_FRMT
	 * @return
	 */
	public String getTimeStamp() {
		final Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, getYear());
		calendar.set(Calendar.MONTH, getMonth());
		calendar.set(Calendar.DATE, getDate());
		calendar.set(Calendar.HOUR_OF_DAY, getHour());
		calendar.set(Calendar.MINUTE, getMinutes());
		calendar.set(Calendar.SECOND, getSeconds());
		
		final Date dateObj = calendar.getTime();
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TMSTMP_FRMT);
		return dateFormat.format(dateObj);
	}
	
	/**
	 * @return
	 */
	public java.sql.Date getDateDB() {
		final Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, getYear());
		calendar.set(Calendar.MONTH, getMonth());
		calendar.set(Calendar.DATE, getDate());
		calendar.set(Calendar.HOUR_OF_DAY, getHour());
		calendar.set(Calendar.MINUTE, getMinutes());
		calendar.set(Calendar.SECOND, getSeconds());
		
		final Date dateObj = calendar.getTime();
		
		return new java.sql.Date(dateObj.getTime());
	}

	/**
	 * @param string
	 * @return
	 */
	public String formatDateTime(String format) {
		final Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, getYear());
		calendar.set(Calendar.MONTH, getMonth());
		calendar.set(Calendar.DATE, getDate());
		calendar.set(Calendar.HOUR, getHour());
		calendar.set(Calendar.MINUTE, getMinutes());
		calendar.set(Calendar.SECOND, getSeconds());
		calendar.set(Calendar.AM_PM, getHour() > 12 ? Calendar.PM : Calendar.AM);
		final Date dateObj = calendar.getTime();
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(dateObj);
		
	}
}



