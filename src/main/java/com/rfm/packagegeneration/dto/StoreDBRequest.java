package com.rfm.packagegeneration.dto;

public class StoreDBRequest {
	private Long mktId;
	private Long nodeId;//restaurant node id
	private String effectiveDate;
 
	private Long  restId;
    private Long  restInstId;
    
    private Long  bunBufrMktInstId;
    private Long  bunBufrRestInstId;

	private String scheduleRequestID;
	private String scriptManagementFlag;

	private Long packageStatusID;
	

	public Long getPackageStatusID() {
		return packageStatusID;
	}



	public void setPackageStatusID(Long packageStatusID) {
		this.packageStatusID = packageStatusID;
	}



	public StoreDBRequest() {
		super();
	}

	

	public Long getMktId() {
		return mktId;
	}

	public void setMktId(Long mktId) {
		this.mktId = mktId;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Long getRestId() {
		return restId;
	}

	public void setRestId(Long restId) {
		this.restId = restId;
	}

	public Long getRestInstId() {
		return restInstId;
	}

	public void setRestInstId(Long restInstId) {
		this.restInstId = restInstId;
	}

	public Long getBunBufrMktInstId() {
		return bunBufrMktInstId;
	}

	public void setBunBufrMktInstId(Long bunBufrMktInstId) {
		this.bunBufrMktInstId = bunBufrMktInstId;
	}

	public Long getBunBufrRestInstId() {
		return bunBufrRestInstId;
	}

	public void setBunBufrRestInstId(Long bunBufrRestInstId) {
		this.bunBufrRestInstId = bunBufrRestInstId;
	}

	public String getScheduleRequestID() {
		return scheduleRequestID;
	}

	public void setScheduleRequestID(String scheduleRequestID) {
		this.scheduleRequestID = scheduleRequestID;
	}

	public String getScriptManagementFlag() {
		return scriptManagementFlag;
	}
	
	public void setScriptManagementFlag(String scriptManagementFlag) {
		this.scriptManagementFlag = scriptManagementFlag;
	}
    
	
	
}
