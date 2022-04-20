package com.rfm.packagegeneration.dto;

import java.util.List;

public class Configuration {
	private List<Section> section;
	private String type;

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
}
