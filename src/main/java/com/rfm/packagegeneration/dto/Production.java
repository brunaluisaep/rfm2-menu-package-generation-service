package com.rfm.packagegeneration.dto;

import java.util.ArrayList;
import java.util.List;

public class Production {
	private List<KVSRoutes> kVSRoutes = new ArrayList<>();
	private String ppg;

	public List<KVSRoutes> getkVSRoutes() {
		return kVSRoutes;
	}

	public void setkVSRoutes(List<KVSRoutes> kVSRoutes) {
		this.kVSRoutes = kVSRoutes;
	}

	public String getPpg() {
		return ppg;
	}

	public void setPpg(String ppg) {
		this.ppg = ppg;
	}
	
	private List<NameTable> nameTables;
	private List<PrdKVSRoute> prdKvsRoutes;
	private List<VolumeTable> volumeTables;
	private List<KSGroup> ksGroups;
	private List<KitchenGroup> kitechenGroups;
	private List<PPGGroup> ppgGroups;

	public List<NameTable> getNameTables() {
		return nameTables;
	}

	public void setNameTables(List<NameTable> nameTables) {
		this.nameTables = nameTables;
	}

	public List<PrdKVSRoute> getPrdKvsRoutes() {
		return prdKvsRoutes;
	}

	public void setPrdKvsRoutes(List<PrdKVSRoute> prdKvsRoutes) {
		this.prdKvsRoutes = prdKvsRoutes;
	}

	public List<VolumeTable> getVolumeTables() {
		return volumeTables;
	}

	public void setVolumeTables(List<VolumeTable> volumeTables) {
		this.volumeTables = volumeTables;
	}

	public List<KSGroup> getKsGroups() {
		return ksGroups;
	}

	public void setKsGroups(List<KSGroup> ksGroups) {
		this.ksGroups = ksGroups;
	}

	public List<KitchenGroup> getKitechenGroups() {
		return kitechenGroups;
	}

	public void setKitechenGroups(List<KitchenGroup> kitechenGroups) {
		this.kitechenGroups = kitechenGroups;
	}

	public List<PPGGroup> getPpgGroups() {
		return ppgGroups;
	}

	public void setPpgGroups(List<PPGGroup> ppgGroups) {
		this.ppgGroups = ppgGroups;
	}
	
}
