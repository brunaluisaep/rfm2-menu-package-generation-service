package com.rfm.packagegeneration.dao;

public enum DAOResources {

	NAMES_DB_GENERATOR_DAO("DAOQueriesNamesDbGenerator.xml"),
	PRODUCT_DB_DAO("DAOQueriesProductDB.xml"),
	COMMON_DAO("DAOQueriesCommon.xml"),
	SCREEN_DAO("DAOQueriesScreen.xml"),
	STORE_DAO("DAOQueriesStoreDB.xml"),
	PROMOTION_DAO("DAOQueriesPromotionDB.xml");
	
	private String fileName;

	DAOResources(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	void setFileName(String fileName) {
		this.fileName = fileName;
	}

}