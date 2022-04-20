package com.rfm.packagegeneration.dto;

import java.io.Serializable;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class ScreenDetails implements SQLData, Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SCREEN_TYPE = "PKGSCREENTYPE";
	private String screenId;
	private String scrInstanceId;
	private String effDate;
	private String restNodeID;
	private String deviceType;
	private String mktID;
	private String scheduleType;
	private String scheduleRequestID;
	private String flagCacheOption;
	private String xmlTypData;
	private String masterInstID;
	private String flagDelCache;
	private String restercindicator;

	/** Oracle DATA Type for Screen XML */

	/**
	 * @return String
	 */
	public String getDeviceType(){
		return deviceType;
	}

	/**
	 * @param deviceType String
	 */
	public void setDeviceType(final String deviceType){
		this.deviceType = deviceType;
	}

	/**
	 * @return the screenId
	 */
	public String getScreenId(){
		return screenId;
	}

	/**
	 * @param screenId the screenId to set
	 */
	public void setScreenId(final String screenId){
		this.screenId = screenId;
	}

	/**
	 * @return the scrInstanceId
	 */
	public String getScrInstanceId(){
		return scrInstanceId;
	}

	/**
	 * @param scrInstanceId the scrInstanceId to set
	 */
	public void setScrInstanceId(final String scrInstanceId){
		this.scrInstanceId = scrInstanceId;
	}

	/**
	 * @return Effective Date
	 */
	public String getEffDate(){
		return effDate;
	}

	/**
	 * @param effDate java.sql.Date
	 */
	public void setEffDate(final String effDate){
		this.effDate = effDate;
	}

	/**
	 * @return restNodeID String
	 */
	public String getRestNodeID(){
		return restNodeID;
	}

	/**
	 * @param restNodeID String
	 */
	public void setRestNodeID(final String restNodeID){
		this.restNodeID = restNodeID;
	}

	/**
	 * @param logger
	 */
	/*
	 * UNUSED CODES public void printScreenDetails(final Logger logger){ logger.info(" restNodeID: " + restNodeID + " effDate :" + effDate +
	 * " screenId : " + screenId + " scrInstanceId : " + scrInstanceId + " deviceType " + deviceType + " mktID " + mktID + " flagCacheOption " +
	 * flagCacheOption + " xmlTypData " + xmlTypData); }
	 */
	/*
	 * (non-Javadoc)
	 * @see java.sql.SQLData#getSQLTypeName()
	 */
	public String getSQLTypeName() throws SQLException{
		return SCREEN_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.SQLData#readSQL(java.sql.SQLInput, java.lang.String)
	 */
	/*
	 * UNUSED CODES public void readSQL(final SQLInput stream, final String typeName) throws SQLException{ setScreenId(stream.readString());
	 * setScrInstanceId(stream.readString()); setEffDate(stream.readString()); setRestNodeID(stream.readString()); setDeviceType(stream.readString());
	 * setMktID(stream.readString()); setScheduleType(stream.readString()); setScheduleRequestID(stream.readString());
	 * setFlagCacheOption(stream.readString()); setXmlTypData(stream.readString()); setMasterInstID(stream.readString()); // RFMII00002014 change
	 * starts setRestercindicator(stream.readString()); // RFMII00002014 change ends }
	 */
	/*
	 * (non-Javadoc)
	 * @see java.sql.SQLData#writeSQL(java.sql.SQLOutput)
	 */
	public void writeSQL(final SQLOutput stream) throws SQLException{
		stream.writeString(getScreenId());
		stream.writeString(getScrInstanceId());
		stream.writeString(getEffDate());
		stream.writeString(getRestNodeID());
		stream.writeString(getDeviceType());
		stream.writeString(getMktID());
		stream.writeString(getScheduleType());
		stream.writeString(getScheduleRequestID());
		stream.writeString(getFlagCacheOption());
		stream.writeString(getXmlTypData());
		stream.writeString(getMasterInstID());
		/* RFMII00002014 change starts */
		stream.writeString(getRestercindicator());
		/* RFMII00002014 change ends */
	}

	/**
	 * @return String
	 */
	public String getMktID(){
		return mktID;
	}

	/**
	 * @param mktID String
	 */
	public void setMktID(final String mktID){
		this.mktID = mktID;
	}

	/**
	 * @return String
	 */
	public String getScheduleType(){
		return scheduleType;
	}

	/**
	 * @param scheduleType String
	 */
	public void setScheduleType(final String scheduleType){
		this.scheduleType = scheduleType;
	}

	/**
	 * @return String
	 */
	public String getScheduleRequestID(){
		return scheduleRequestID;
	}

	/**
	 * @param scheduleRequestID String
	 */
	public void setScheduleRequestID(final String scheduleRequestID){
		this.scheduleRequestID = scheduleRequestID;
	}

	/**
	 * @return String
	 */
	public String getFlagCacheOption(){
		return flagCacheOption;
	}

	/**
	 * @param flagCacheOption String
	 */
	public void setFlagCacheOption(final String flagCacheOption){
		this.flagCacheOption = flagCacheOption;
	}

	/**
	 * @return xmlTypData String
	 */
	public String getXmlTypData(){
		return xmlTypData;
	}

	/**
	 * @param xmlTypData the xmlTypData to set
	 */
	public void setXmlTypData(final String xmlTypData){
		this.xmlTypData = xmlTypData;
	}

	/**
	 * @return String
	 */
	public String getMasterInstID(){
		return masterInstID;
	}

	/**
	 * @param masterInstID String
	 */
	public void setMasterInstID(final String masterInstID){
		this.masterInstID = masterInstID;
	}

	/**
	 * @return the flagDelCache
	 */
	public String getFlagDelCache(){
		return flagDelCache;
	}

	/**
	 * @param flagDelCache the flagDelCache to set
	 */
	public void setFlagDelCache(final String flagDelCache){
		this.flagDelCache = flagDelCache;
	}

	/**
	 * @return String
	 */
	public String getRestercindicator(){
		return restercindicator;
	}

	/**
	 * @param restercindicator String
	 */
	public void setRestercindicator(final String restercindicator){
		this.restercindicator = restercindicator;
	}

	/*
	 * (non-Javadoc)
	 * @see java.sql.SQLData#readSQL(java.sql.SQLInput, java.lang.String)
	 */
	public void readSQL(final SQLInput stream, final String typeName) throws SQLException{
		// TODO Auto-generated method stub
	}
}
