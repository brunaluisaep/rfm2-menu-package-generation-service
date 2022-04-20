package com.rfm.packagegeneration.dto;

public class Parameter {
	
	private Long paramId;

	private String name;

	private String value;
	
	private String parmClass;

	private Long lexId;

	private Long dataType;

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getLexId() {
		return lexId;
	}

	public void setLexId(Long lexId) {
		this.lexId = lexId;
	}

	public Long getDataType() {
		return dataType;
	}

	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}

	public String getParmClass() {
		return parmClass;
	}

	public void setParmClass(String parmClass) {
		this.parmClass = parmClass;
	}
	
	public Parameter copy() {
		Parameter newP = new Parameter();
		newP.paramId = paramId;
		newP.dataType = dataType;
		newP.lexId = lexId;
		newP.name = name;
		newP.parmClass = parmClass;
		newP.value = value;
		return newP;
	}
}
