package com.rfm.packagegeneration.dto;

import java.util.List;

public class Section {
	private List<Parameter> parameter;
	private String name;

	public List<Parameter> getParameter() {
		return parameter;
	}

	public void setParameter(List<Parameter> parameter) {
		this.parameter = parameter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
