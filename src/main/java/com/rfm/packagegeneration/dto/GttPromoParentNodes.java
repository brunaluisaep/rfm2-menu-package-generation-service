package com.rfm.packagegeneration.dto;

public class GttPromoParentNodes {
	private Long nodeId;
	private Long inhrOrd;
	private String nodeType;
	
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public Long getInhrOrd() {
		return inhrOrd;
	}
	public void setInhrOrd(Long inhrOrd) {
		this.inhrOrd = inhrOrd;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	@Override
	public String toString() {
		return "GttPromoParentNodes [nodeId=" + nodeId + ", inhrOrd=" + inhrOrd + ", nodeType=" + nodeType + "]";
	}
	
	
}
