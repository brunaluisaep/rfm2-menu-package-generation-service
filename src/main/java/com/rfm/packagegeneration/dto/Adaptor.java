package com.rfm.packagegeneration.dto;

import java.util.List;

public class Adaptor {
	private List<Section> section;
	private String type;
	private String startonload;
	
	public List<Section> getSection() {
		return section;
	}
	public void setSection(List<Section> section) {
		this.section = section;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartonload() {
		return startonload;
	}
	public void setStartonload(String startonload) {
		this.startonload = startonload;
	}
	
	
}
