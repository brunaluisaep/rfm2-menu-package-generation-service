package com.rfm.packagegeneration.hikari.domain;

public enum CredentialType {

	ORACLE   ( "oracle.jdbc.driver.OracleDriver", "SELECT 1 FROM DUAL" ),
	 AURORA ( "org.postgresql.Driver", "SELECT 1" ); 


	private String driverName;
	private String defaultQuery;

	CredentialType(String driverName, String defaultQuery) {
		this.driverName = driverName;
		this.defaultQuery = defaultQuery;
	}	

	public String getDriverName() {
		return driverName;
	}

	public String getDefaultQuery() {
		return defaultQuery;
	}

	public static CredentialType getConnectionTypeByDriverName( final String driverName ) {
		for (CredentialType t : values()) {
			if(t.getDriverName().equals(driverName)) {
				return t;
			}   
		}
		return null; 
	}

}
