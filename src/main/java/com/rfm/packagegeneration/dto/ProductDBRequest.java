package com.rfm.packagegeneration.dto;

public class ProductDBRequest {
	private Long mktId;
	private Long nodeId;//restaurant node id
	private Long productId;
	//Added for RFMP-9296
	private String effectiveDate;
 
	private Long  restId;
    private Long  restInstId;
    private Long masterInstId;
    
    //Added to test RFMP-11286
    private String scheduleRequestID;
    
    public Long getMasterInstId() {
		return masterInstId;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getScheduleRequestID() {
		return scheduleRequestID;
	}
	public void setScheduleRequestID(String scheduleRequestID) {
		this.scheduleRequestID = scheduleRequestID;
	}
}
