package com.rfm.packagegeneration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PackageStatusDTO {
	@JsonProperty("SEQ_NO")
	private String SEQ_NO;
	private String PKG_STUS_DTL_ID;
	private String PKG_STUS_ID;
	private String EFF_DT;

	public String getSEQ_NO() {
		return SEQ_NO;
	}

	public void setSEQ_NO(String sEQ_NO) {
		SEQ_NO = sEQ_NO;
	}

	public String getPKG_STUS_DTL_ID() {
		return PKG_STUS_DTL_ID;
	}

	public void setPKG_STUS_DTL_ID(String pKG_STUS_DTL_ID) {
		PKG_STUS_DTL_ID = pKG_STUS_DTL_ID;
	}

	public String getPKG_STUS_ID() {
		return PKG_STUS_ID;
	}

	public void setPKG_STUS_ID(String pKG_STUS_ID) {
		PKG_STUS_ID = pKG_STUS_ID;
	}

	public String getEFF_DT() {
		return EFF_DT;
	}

	public void setEFF_DT(String eFF_DT) {
		EFF_DT = eFF_DT;
	}
}
