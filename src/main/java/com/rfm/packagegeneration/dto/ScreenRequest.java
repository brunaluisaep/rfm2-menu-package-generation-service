package com.rfm.packagegeneration.dto;

import java.util.ArrayList;
import java.util.List;

public class ScreenRequest {
	private Long mktId;
	private Long nodeId;// restaurant node id
	private Long productId;
	// Added for RFMP-9296
	private String effectiveDate;

	private Long restId;
	private Long restInstId;
	private Long masterInstId;

	// Added to test RFMP-11608 getDynamicWorkflowParameters method
	private String workflowName;

	// Added to test RFMP-11286
	private String scheduleRequestID;
 
	// Added to test RFMP-11610
	private List<Long> screenIds;
	private List<Long> screenInstIds;
	private List<Long> screenWorkflowAssignId;
	private List<Long> liskWorkflowParams;
	private Long uniqueId;

	// Added to test RFMP-4666
	private Long globalLocaleId;
	//Added to test RFMP-11609
    private List<Long> langIds;
    private Long screenSetId; 	
    
    private Long setId;
	private Long lexId;
	private String marketLocaleId;
	private List<Long> buttonWorkflowAssignIds;
	
	private ArrayList<Long> buttonsIds;
	private  ArrayList<Long>  buttonsInstIds;


	public List<Long> getButtonWorkflowAssignIds() {
		return buttonWorkflowAssignIds;
	}

	public void setButtonWorkflowAssignIds(List<Long> buttonWorkflowAssignIds) {
		this.buttonWorkflowAssignIds = buttonWorkflowAssignIds;
	}

	public Long getGlobalLocaleId() {
		return globalLocaleId;
	}

	public void setGlobalLocaleId(Long globalLocaleId) {
		this.globalLocaleId = globalLocaleId;
	}

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

	public List<Long> getScreenIds() {
		return screenIds;
	}

	public void setScreenIds(List<Long> screenIds) {
		this.screenIds = screenIds;
	}

	public List<Long> getScreenInstIds() {
		return screenInstIds;
	}

	public void setScreenInstIds(List<Long> screenInstIds) {
		this.screenInstIds = screenInstIds;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public List<Long> getScreenWorkflowAssignId() {
		return screenWorkflowAssignId;
	}

	public void setScreenWorkflowAssignId(List<Long> screenWorkflowAssignId) {
		this.screenWorkflowAssignId = screenWorkflowAssignId;
	}

	public List<Long> getLiskWorkflowParams() {
		return liskWorkflowParams;
	}

	public void setLiskWorkflowParams(List<Long> liskWorkflowParams) {
		this.liskWorkflowParams = liskWorkflowParams;
	}

	public Long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(Long uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void setMasterInstId(Long masterInstId) {
		this.masterInstId = masterInstId;
	}

	public List<Long> getLangIds() {
		return langIds;
	}

	public void setLangIds(List<Long> langIds) {
		this.langIds = langIds;
	}

	public Long getScreenSetId() {
		return screenSetId;
	}

	public void setScreenSetId(Long screenSetId) {
		this.screenSetId = screenSetId;
	}

	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	public Long getLexId() {
		return lexId;
	}

	public void setLexId(Long lexId) {
		this.lexId = lexId;
	}

	public String getMarketLocaleId() {
		return marketLocaleId;
	}

	public void setMarketLocaleId(String marketLocaleId) {
		this.marketLocaleId = marketLocaleId;
	}

	public ArrayList<Long> getButtonsIds() {
		return buttonsIds;
	}

	public void setButtonsIds(ArrayList<Long> buttonsIds) {
		this.buttonsIds = buttonsIds;
	}

	public ArrayList<Long> getButtonsInstIds() {
		return buttonsInstIds;
	}

	public void setButtonsInstIds(ArrayList<Long> buttonsInstIds) {
		this.buttonsInstIds = buttonsInstIds;
	}

}
