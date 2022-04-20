package com.rfm.packagegeneration.dto;

import java.util.List;

public class LayoutSearchDTO extends BaseDTO{

	private String searchCriteria;
	private Integer fullList;
	private Integer status;
	private Long nodeId;
	private List<String> setList;
	private String searchType;
	private String deviceVersion;
	private Long selectedNodeHierarchy;
	private Long setId;
	private Long instanceId;
	private Integer pageNumber;
	private String sortFieldName;
	private String currentSortingCriteria;
	private Long levelId;
	private boolean isRestLvlUser;
	private boolean isNodeFilterChkRequired;	
	private String reportSearch = "N";

	/**
	 * This method returns the value of reportSearch of type String.
	 *
	 * @return the reportSearch
	 */
	public String getReportSearch() {
		return reportSearch;
	}

	/**
	 * This method sets the value of reportSearch of type String.
	 *
	 * @param reportSearch the reportSearch to set
	 */
	public void setReportSearch(final String reportSearch) {
		this.reportSearch = reportSearch;
	}

	/**
	 * @return the isNodeFilterChkRequired
	 */
	public boolean isNodeFilterChkRequired() {
		return isNodeFilterChkRequired;
	}

	/**
	 * @param isNodeFilterChkRequired the isNodeFilterChkRequired to set
	 */
	public void setNodeFilterChkRequired(final boolean isNodeFilterChkRequired) {
		this.isNodeFilterChkRequired = isNodeFilterChkRequired;
	}

	/**
	 * @return the instanceId
	 */
	public Long getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(final Long instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(final Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the searchCriteria
	 */
	public String getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * @param searchCriteria the searchCriteria to set
	 */
	public void setSearchCriteria(final String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(final String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return the selectedNodeHierarchy
	 */
	public Long getSelectedNodeHierarchy() {
		return selectedNodeHierarchy;
	}

	/**
	 * @param selectedNodeHierarchy the selectedNodeHierarchy to set
	 */
	public void setSelectedNodeHierarchy(final Long selectedNodeHierarchy) {
		this.selectedNodeHierarchy = selectedNodeHierarchy;
	}

	/**
	 * @return the setId
	 */
	public Long getSetId() {
		return setId;
	}

	/**
	 * @param setId the setId to set
	 */
	public void setSetId(final Long setId) {
		this.setId = setId;
	}

	/**
	 * @return the setList
	 */
	public List<String> getSetList() {
		return setList;
	}

	/**
	 * @param setList the setList to set
	 */
	public void setSetList(List<String> setList) {
		this.setList = setList;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(final Integer status) {
		this.status = status;
	}

	/**
	 * @return the pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(final Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the currentSortingCriteria
	 */
	public String getCurrentSortingCriteria() {
		return currentSortingCriteria;
	}

	/**
	 * @param currentSortingCriteria the currentSortingCriteria to set
	 */
	public void setCurrentSortingCriteria(final String currentSortingCriteria) {
		this.currentSortingCriteria = currentSortingCriteria;
	}

	/**
	 * @return the fullList
	 */
	public Integer getFullList() {
		return fullList;
	}

	/**
	 * @param fullList the fullList to set
	 */
	public void setFullList(final Integer fullList) {
		this.fullList = fullList;
	}

	/**
	 * @return the sortFieldName
	 */
	public String getSortFieldName() {
		return sortFieldName;
	}

	/**
	 * @param sortFieldName the sortFieldName to set
	 */
	public void setSortFieldName(final String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	/**
	 * @return the levelId
	 */
	public Long getLevelId() {
		return levelId;
	}

	/**
	 * @param levelId the levelId to set
	 */
	public void setLevelId(final Long levelId) {
		this.levelId = levelId;
	}

	/**
	 * @return the isRestLvlUser
	 */
	public boolean isRestLvlUser() {
		return isRestLvlUser;
	}

	/**
	 * @param isRestLvlUser the isRestLvlUser to set
	 */
	public void setRestLvlUser(final boolean isRestLvlUser) {
		this.isRestLvlUser = isRestLvlUser;
	}

	/**
	 * @return the deviceVersion
	 */
	public String getDeviceVersion() {
		return deviceVersion;
	}

	/**
	 * @param deviceVersion the deviceVersion to set
	 */
	public void setDeviceVersion(final String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();

		builder.append("LayoutSearchDTO [");
		if (searchCriteria != null) {
			builder.append("searchCriteria=").append(searchCriteria).append(", ");
		}
		if (fullList != null) {
			builder.append("fullList=").append(fullList).append(", ");
		}
		if (status != null) {
			builder.append("status=").append(status).append(", ");
		}
		if (nodeId != null) {
			builder.append("nodeId=").append(nodeId).append(", ");
		}
		if (setList != null) {
			builder.append("setList=").append(setList).append(", ");
		}
		if (searchType != null) {
			builder.append("searchType=").append(searchType).append(", ");
		}
		if (deviceVersion != null) {
			builder.append("deviceVersion=").append(deviceVersion).append(", ");
		}
		if (selectedNodeHierarchy != null) {
			builder.append("selectedNodeHierarchy=").append(selectedNodeHierarchy).append(", ");
		}
		if (setId != null) {
			builder.append("setId=").append(setId).append(", ");
		}
		if (instanceId != null) {
			builder.append("instanceId=").append(instanceId).append(", ");
		}
		if (pageNumber != null) {
			builder.append("pageNumber=").append(pageNumber).append(", ");
		}
		if (sortFieldName != null) {
			builder.append("sortFieldName=").append(sortFieldName).append(", ");
		}
		if (currentSortingCriteria != null) {
			builder.append("currentSortingCriteria=").append(currentSortingCriteria).append(", ");
		}
		if (levelId != null) {
			builder.append("levelId=").append(levelId).append(", ");
		}
		builder.append("isRestLvlUser=").append(isRestLvlUser);
		builder.append(", isNodeFilterChkRequired=").append(isNodeFilterChkRequired).append(", ");
		if (reportSearch != null) {
			builder.append("reportSearch=").append(reportSearch);
		}
		builder.append("]");

		return builder.toString();
	}

}
