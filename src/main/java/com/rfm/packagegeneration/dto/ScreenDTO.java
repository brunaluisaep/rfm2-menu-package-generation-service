package com.rfm.packagegeneration.dto;

import java.util.List;
import java.util.Set;

public class ScreenDTO  extends LayoutBaseDTO  {

	/**
	 *
	 */
	private static final long serialVersionUID = -3960848263063702210L;
	private Long screenId;
	private Long screenNumber;
	private String screenTitle;
	private String screenType;
	private Long marketId;
	private Long maxNoOfColumns;
	private List listButtonDTO;
	// Added for update layout
	private String soundFileName;
	private Long soundFileId;
	private Long screenBackgrImageId;
	private Long screenTitleImageId;
	private String screenBackgrImageName;
	private String screenTitleImageName;
	private String screenBackgrImagePath;
	private String screenTitleImagePath;
	private Integer timeOut;
	private Long onActivateId;
	private Long onDeactivateId;
	private String showBackground;
	private String showEditableBtns;
	private String showDisabledBtns;
	private String screenImagePath;
	// contains dataInstanceId and ButtonId used to delete BLM definition.
	private String[] blmReclaimBttnDltInfo;
	private List<WorkflowDTO> scrWrkflow;
	private String scrRenderingWidth;
	private String scrRenderingHeight;
	private String scrTemplateType;
	private Integer scrCrntExist;
	/* RFMII00002014 changes starts */
	private Long onCompleteId = getOnCompleteId();
	private Long grillGroupId;
	private Long smartReminderId;
	private String grillGroupName;
	private String smartReminderName;
	private Set<Long> grillGroupInUseSet;
	private Set<Long> smartReminderInUseSet;
	private String grillGroupScreenTypes;
	private String smartReminderScreenTypes;
	/* RFMII00002014 changes ends */
	// CQ3601 - Start
	private String scrBackgroundColorCode;
	private String scrBackgroundColorName;
	/* CQ 4540 start : added color id */
	private String scrBackgroundColorId;
	/* CQ 4540 ends : added color id */
	private Long dayPartId;
	private String dayPartName;
	
	//Added to test RFMP-11609
    private List<Long> buttonWorkflowAssignIds;
    private List<Long> screenIds;
    private List<Long> screenInstanceIds;
    private List<Long> langIds;
    private Long screenSetId;
    private String marketLocaleId;

	/**
	 * @return the dayPartId
	 */
	public Long getDayPartId(){
		return dayPartId;
	}

	/**
	 * @param dayPartId the dayPartId to set
	 */
	public void setDayPartId(final Long dayPartId){
		this.dayPartId = dayPartId;
	}

	/**
	 * @return the dayPartName
	 */
	public String getDayPartName(){
		return dayPartName;
	}

	/**
	 * @param dayPartName the dayPartName to set
	 */
	public void setDayPartName(final String dayPartName){
		this.dayPartName = dayPartName;
	}

	/**
	 * @return the scrBackgroundColorCode
	 */
	public String getScrBackgroundColorCode(){
		return scrBackgroundColorCode;
	}

	/**
	 * @param scrBackgroundColorCode the scrBackgroundColorCode to set
	 */
	public void setScrBackgroundColorCode(final String scrBackgroundColorCode){
		this.scrBackgroundColorCode = scrBackgroundColorCode;
	}

	// CQ3601 - End
	/**
	 * @return the scrWrkflow
	 */
	public List<WorkflowDTO> getScrWrkflow(){
		return scrWrkflow;
	}

	/**
	 * @param scrWrkflow the scrWrkflow to set
	 */
	public void setScrWrkflow(final List<WorkflowDTO> scrWrkflow){
		this.scrWrkflow = scrWrkflow;
	}

	/**
	 * @return the blmReclaimBttnDltInfo
	 */
	public String[] getBlmReclaimBttnDltInfo(){
		return blmReclaimBttnDltInfo;
	}

	/**
	 * @param blmReclaimBttnDltInfo the blmReclaimBttnDltInfo to set
	 */
	public void setBlmReclaimBttnDltInfo(final String[] blmReclaimBttnDltInfo){
		this.blmReclaimBttnDltInfo = blmReclaimBttnDltInfo;
	}

	/**
	 * @return the listButtonDTO
	 */
	public List getListButtonDTO(){
		return listButtonDTO;
	}

	/**
	 * @param listButtonDTO the listButtonDTO to set
	 */
	public void setListButtonDTO(final List listButtonDTO){
		this.listButtonDTO = listButtonDTO;
	}

	/**
	 * @return the marketId
	 */
	@Override
	public Long getMarketId(){
		return marketId;
	}

	/**
	 * @param marketId the marketId to set
	 */
	@Override
	public void setMarketId(final Long marketId){
		this.marketId = marketId;
	}

	/**
	 * @return the maxNoOfColumns
	 */
	public Long getMaxNoOfColumns(){
		return maxNoOfColumns;
	}

	/**
	 * @param maxNoOfColumns the maxNoOfColumns to set
	 */
	public void setMaxNoOfColumns(final Long maxNoOfColumns){
		this.maxNoOfColumns = maxNoOfColumns;
	}

	/**
	 * @return the onActivateId
	 */
	public Long getOnActivateId(){
		return onActivateId;
	}

	/**
	 * @param onActivateId the onActivateId to set
	 */
	public void setOnActivateId(final Long onActivateId){
		this.onActivateId = onActivateId;
	}

	/**
	 * @return the onDeactivateId
	 */
	public Long getOnDeactivateId(){
		return onDeactivateId;
	}

	/**
	 * @param onDeactivateId the onDeactivateId to set
	 */
	public void setOnDeactivateId(final Long onDeactivateId){
		this.onDeactivateId = onDeactivateId;
	}

	/**
	 * @return the screenBackgrImageId
	 */
	public Long getScreenBackgrImageId(){
		return screenBackgrImageId;
	}

	/**
	 * @param screenBackgrImageId the screenBackgrImageId to set
	 */
	public void setScreenBackgrImageId(final Long screenBackgrImageId){
		this.screenBackgrImageId = screenBackgrImageId;
	}

	/**
	 * @return the screenBackgrImageName
	 */
	public String getScreenBackgrImageName(){
		return screenBackgrImageName;
	}

	/**
	 * @param screenBackgrImageName the screenBackgrImageName to set
	 */
	public void setScreenBackgrImageName(final String screenBackgrImageName){
		this.screenBackgrImageName = screenBackgrImageName;
	}

	/**
	 * @return the screenId
	 */
	public Long getScreenId(){
		return screenId;
	}

	/**
	 * @param screenId the screenId to set
	 */
	public void setScreenId(final Long screenId){
		this.screenId = screenId;
	}

	/**
	 * @return the screenNumber
	 */
	public Long getScreenNumber(){
		return screenNumber;
	}

	/**
	 * @param screenNumber the screenNumber to set
	 */
	public void setScreenNumber(final Long screenNumber){
		this.screenNumber = screenNumber;
	}

	/**
	 * @return the screenTitle
	 */
	public String getScreenTitle(){
		return screenTitle;
	}

	/**
	 * @param screenTitle the screenTitle to set
	 */
	public void setScreenTitle(final String screenTitle){
		this.screenTitle = screenTitle;
	}

	/**
	 * @return the screenTitleImageId
	 */
	public Long getScreenTitleImageId(){
		return screenTitleImageId;
	}

	/**
	 * @param screenTitleImageId the screenTitleImageId to set
	 */
	public void setScreenTitleImageId(final Long screenTitleImageId){
		this.screenTitleImageId = screenTitleImageId;
	}

	/**
	 * @return the screenTitleImageName
	 */
	public String getScreenTitleImageName(){
		return screenTitleImageName;
	}

	/**
	 * @param screenTitleImageName the screenTitleImageName to set
	 */
	public void setScreenTitleImageName(final String screenTitleImageName){
		this.screenTitleImageName = screenTitleImageName;
	}

	/**
	 * @return the screenType
	 */
	public String getScreenType(){
		return screenType;
	}

	/**
	 * @param screenType the screenType to set
	 */
	public void setScreenType(final String screenType){
		this.screenType = screenType;
	}

	/**
	 * @return the soundFileName
	 */
	public String getSoundFileName(){
		return soundFileName;
	}

	/**
	 * @param soundFileName the soundFileName to set
	 */
	public void setSoundFileName(final String soundFileName){
		this.soundFileName = soundFileName;
	}

	/**
	 * @return the timeOut
	 */
	public Integer getTimeOut(){
		return timeOut;
	}

	/**
	 * @param timeOut the timeOut to set
	 */
	public void setTimeOut(final Integer timeOut){
		this.timeOut = timeOut;
	}

	/**
	 * @return the screenImagePath
	 */
	public String getScreenImagePath(){
		return screenImagePath;
	}

	/**
	 * @param screenImagePath the screenImagePath to set
	 */
	public void setScreenImagePath(final String screenImagePath){
		this.screenImagePath = screenImagePath;
	}

	/**
	 * @return the scrRenderingWidth
	 */
	public String getScrRenderingWidth(){
		return scrRenderingWidth;
	}

	/**
	 * @param scrRenderingWidth the scrRenderingWidth to set
	 */
	public void setScrRenderingWidth(final String scrRenderingWidth){
		this.scrRenderingWidth = scrRenderingWidth;
	}

	/**
	 * @return the scrRenderingHeight
	 */
	public String getScrRenderingHeight(){
		return scrRenderingHeight;
	}

	/**
	 * @param scrRenderingHeight the scrRenderingHeight to set
	 */
	public void setScrRenderingHeight(final String scrRenderingHeight){
		this.scrRenderingHeight = scrRenderingHeight;
	}

	/**
	 * @return the screenBackgrImagePath
	 */
	public String getScreenBackgrImagePath(){
		return screenBackgrImagePath;
	}

	/**
	 * @param screenBackgrImagePath the screenBackgrImagePath to set
	 */
	public void setScreenBackgrImagePath(final String screenBackgrImagePath){
		this.screenBackgrImagePath = screenBackgrImagePath;
	}

	/**
	 * @return the screenTitleImagePath
	 */
	public String getScreenTitleImagePath(){
		return screenTitleImagePath;
	}

	/**
	 * @param screenTitleImagePath the screenTitleImagePath to set
	 */
	public void setScreenTitleImagePath(final String screenTitleImagePath){
		this.screenTitleImagePath = screenTitleImagePath;
	}

	/**
	 * @return the soundFileId
	 */
	public Long getSoundFileId(){
		return soundFileId;
	}

	/**
	 * @param soundFileId the soundFileId to set
	 */
	public void setSoundFileId(final Long soundFileId){
		this.soundFileId = soundFileId;
	}

	/**
	 * @return the showBackground
	 */
	public String getShowBackground(){
		return showBackground;
	}

	/**
	 * @param showBackground the showBackground to set
	 */
	public void setShowBackground(final String showBackground){
		this.showBackground = showBackground;
	}

	/**
	 * @return the showDisabledBtns
	 */
	public String getShowDisabledBtns(){
		return showDisabledBtns;
	}

	/**
	 * @param showDisabledBtns the showDisabledBtns to set
	 */
	public void setShowDisabledBtns(final String showDisabledBtns){
		this.showDisabledBtns = showDisabledBtns;
	}

	/**
	 * @return the showEditableBtns
	 */
	public String getShowEditableBtns(){
		return null == showEditableBtns ? "false" : showEditableBtns;
	}

	/**
	 * @param showEditableBtns the showEditableBtns to set
	 */
	public void setShowEditableBtns(final String showEditableBtns){
		this.showEditableBtns = showEditableBtns;
	}

	/**
	 * @return the scrCrntExist
	 */
	public Integer getScrCrntExist(){
		return scrCrntExist;
	}

	/**
	 * @param scrCrntExist the scrCrntExist to set
	 */
	public void setScrCrntExist(final Integer scrCrntExist){
		this.scrCrntExist = scrCrntExist;
	}

	/**
	 * @return the grillGroupId
	 */
	public Long getGrillGroupId(){
		return grillGroupId;
	}

	/**
	 * @param grillGroupId the grillGroupId to set
	 */
	public void setGrillGroupId(final Long grillGroupId){
		this.grillGroupId = grillGroupId;
	}

	/**
	 * @return the onCompleteId
	 */
	public Long getOnCompleteId(){
		return onCompleteId;
	}

	/**
	 * @param onCompleteId the onCompleteId to set
	 */
	public void setOnCompleteId(final Long onCompleteId){
		this.onCompleteId = onCompleteId;
	}

	/**
	 * @return the smartReminderId
	 */
	public Long getSmartReminderId(){
		return smartReminderId;
	}

	/**
	 * @param smartReminderId the smartReminderId to set
	 */
	public void setSmartReminderId(final Long smartReminderId){
		this.smartReminderId = smartReminderId;
	}

	/**
	 * @return the grillGroupName
	 */
	public String getGrillGroupName(){
		return grillGroupName;
	}

	/**
	 * @param grillGroupName the grillGroupName to set
	 */
	public void setGrillGroupName(final String grillGroupName){
		this.grillGroupName = grillGroupName;
	}

	/**
	 * @return the smartReminderName
	 */
	public String getSmartReminderName(){
		return smartReminderName;
	}

	/**
	 * @param smartReminderName the smartReminderName to set
	 */
	public void setSmartReminderName(final String smartReminderName){
		this.smartReminderName = smartReminderName;
	}

	/**
	 * @return the grillGroupInUseSet
	 */
	public Set<Long> getGrillGroupInUseSet(){
		return grillGroupInUseSet;
	}

	/**
	 * @param grillGroupInUseSet the grillGroupInUseSet to set
	 */
	public void setGrillGroupInUseSet(final Set<Long> grillGroupInUseSet){
		this.grillGroupInUseSet = grillGroupInUseSet;
	}

	/**
	 * @return the smartReminderInUseSet
	 */
	public Set<Long> getSmartReminderInUseSet(){
		return smartReminderInUseSet;
	}

	/**
	 * @param smartReminderInUseSet the smartReminderInUseSet to set
	 */
	public void setSmartReminderInUseSet(final Set<Long> smartReminderInUseSet){
		this.smartReminderInUseSet = smartReminderInUseSet;
	}

	/**
	 * @return String
	 */
	public String getGrillGroupScreenTypes(){
		return grillGroupScreenTypes;
	}

	/**
	 * @param grillGroupScreenTypes String
	 */
	public void setGrillGroupScreenTypes(final String grillGroupScreenTypes){
		this.grillGroupScreenTypes = grillGroupScreenTypes;
	}

	/**
	 * @return String
	 */
	public String getSmartReminderScreenTypes(){
		return smartReminderScreenTypes;
	}

	/**
	 * @param smartReminderScreenTypes String
	 */
	public void setSmartReminderScreenTypes(final String smartReminderScreenTypes){
		this.smartReminderScreenTypes = smartReminderScreenTypes;
	}

	/**
	 * @return the scrTemplateType
	 */
	public String getScrTemplateType(){
		return scrTemplateType;
	}

	/**
	 * @param scrTemplateType the scrTemplateType to set
	 */
	public void setScrTemplateType(final String scrTemplateType){
		this.scrTemplateType = scrTemplateType;
	}

	/* CQ 4540 start : added color id */
	/**
	 * @return the scrBackgroundColorId
	 */
	public String getScrBackgroundColorId(){
		return scrBackgroundColorId;
	}

	/**
	 * @param scrBackgroundColorId the scrBackgroundColorId to set
	 */
	public void setScrBackgroundColorId(final String scrBackgroundColorId){
		this.scrBackgroundColorId = scrBackgroundColorId;
	}

	/* CQ 4540 ends : added color id */

	/**
	 * @return the scrBackgroundColorName
	 */
	public String getScrBackgroundColorName(){
		return scrBackgroundColorName;
	}

	/**
	 * @param scrBackgroundColorName the scrBackgroundColorName to set
	 */
	public void setScrBackgroundColorName(final String scrBackgroundColorName){
		this.scrBackgroundColorName = scrBackgroundColorName;
	}

	public List<Long> getButtonWorkflowAssignIds() {
		return buttonWorkflowAssignIds;
	}

	public void setButtonWorkflowAssignIds(List<Long> buttonWorkflowAssignIds) {
		this.buttonWorkflowAssignIds = buttonWorkflowAssignIds;
	}
	
	public List<Long> getScreenIds() {
		return screenIds;
	}
	public void setScreenIds(List<Long> screenIds) {
		this.screenIds = screenIds;
	}
	public List<Long> getScreenInstanceIds() {
		return screenInstanceIds;
	}
	public void setScreenInstanceIds(List<Long> screenInstanceIds) {
		this.screenInstanceIds = screenInstanceIds;
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

	public String getMarketLocaleId() {
		return marketLocaleId;
	}
	public void setMarketLocaleId(String marketLocaleId) {
		this.marketLocaleId = marketLocaleId;
	}
	public ScreenDTO() {
	}
	
}
