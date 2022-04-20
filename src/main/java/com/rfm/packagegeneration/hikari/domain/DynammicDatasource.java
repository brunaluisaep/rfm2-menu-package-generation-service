package com.rfm.packagegeneration.hikari.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application")
public class DynammicDatasource implements Serializable {

	private static final long serialVersionUID = -6859484733708233854L;

	private PoolData poolData = new PoolData();
	private List<Credential> datasources = new ArrayList<>();
	public PoolData getPoolData() {
		return poolData;
	}
	public void setPoolData(PoolData poolData) {
		this.poolData = poolData;
	}
	public List<Credential> getDatasources() {
		return datasources;
	}
	public void setDatasources(List<Credential> datasources) {
		this.datasources = datasources;
	}

}
