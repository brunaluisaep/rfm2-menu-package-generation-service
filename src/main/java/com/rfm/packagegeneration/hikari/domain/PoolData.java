package com.rfm.packagegeneration.hikari.domain;

import java.io.Serializable;

public class PoolData implements Serializable {

	private static final long serialVersionUID = 2085800060276458778L;

	private String connectionTimeout;
	private String minimumIdle;
	private String maximumPoolSize;
	private String idleTimeout;
	private String maxLifetime;
	private String autoCommit;

	private String cachePrepStmts;
	private String prepStmtCacheSize;
	private String prepStmtCacheSqlLimit;
	private String useServerPrepStmts;
	public String getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(String connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public String getMinimumIdle() {
		return minimumIdle;
	}
	public void setMinimumIdle(String minimumIdle) {
		this.minimumIdle = minimumIdle;
	}
	public String getMaximumPoolSize() {
		return maximumPoolSize;
	}
	public void setMaximumPoolSize(String maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
	public String getIdleTimeout() {
		return idleTimeout;
	}
	public void setIdleTimeout(String idleTimeout) {
		this.idleTimeout = idleTimeout;
	}
	public String getMaxLifetime() {
		return maxLifetime;
	}
	public void setMaxLifetime(String maxLifetime) {
		this.maxLifetime = maxLifetime;
	}
	public String getAutoCommit() {
		return autoCommit;
	}
	public void setAutoCommit(String autoCommit) {
		this.autoCommit = autoCommit;
	}
	public String getCachePrepStmts() {
		return cachePrepStmts;
	}
	public void setCachePrepStmts(String cachePrepStmts) {
		this.cachePrepStmts = cachePrepStmts;
	}
	public String getPrepStmtCacheSize() {
		return prepStmtCacheSize;
	}
	public void setPrepStmtCacheSize(String prepStmtCacheSize) {
		this.prepStmtCacheSize = prepStmtCacheSize;
	}
	public String getPrepStmtCacheSqlLimit() {
		return prepStmtCacheSqlLimit;
	}
	public void setPrepStmtCacheSqlLimit(String prepStmtCacheSqlLimit) {
		this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
	}
	public String getUseServerPrepStmts() {
		return useServerPrepStmts;
	}
	public void setUseServerPrepStmts(String useServerPrepStmts) {
		this.useServerPrepStmts = useServerPrepStmts;
	}
}
