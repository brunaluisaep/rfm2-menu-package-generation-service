package com.rfm.packagegeneration.hikari;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.rfm.packagegeneration.hikari.domain.Credential;
import com.rfm.packagegeneration.hikari.domain.CredentialType;
import com.rfm.packagegeneration.hikari.domain.DynammicDatasource;
import com.rfm.packagegeneration.hikari.domain.PoolData;
import com.rfm.packagegeneration.hikari.exception.DatabaseUnavailableException;
import com.rfm.packagegeneration.hikari.exception.InvalidDataSourceNameException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
@ConfigurationProperties
@EnableConfigurationProperties(Manager.class)
class Manager {
	private static final Logger LOGGER = LogManager.getLogger("Manager");
	private Map<String, Boolean> connectionsHealth = new HashMap<>();
	private Map<String, NamedParameterJdbcTemplate> namedTemplates = new HashMap<>();
	private Map<String, HikariConfig> datasourcesConfig = new HashMap<>();

	public void loadTemplates(DynammicDatasource datasourceProperties) {
		Optional.ofNullable(datasourceProperties).ifPresent(dataSources -> {
			for (Credential credential : dataSources.getDatasources()) {
				boolean isUp = false;
				try {
					HikariConfig hikariConfig = configureConnection(dataSources.getPoolData(), credential);
					
					datasourcesConfig.put(credential.getName(), hikariConfig);

					HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

					NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(hikariDataSource);

					namedTemplates.put(credential.getName(), namedTemplate);
					isUp = true;
				} catch (Exception e) {
					LOGGER.error("Error to connect to database: " + credential.getName(), e);
				}
				connectionsHealth.put(credential.getName(), isUp);
			}
		});
	}

	public void refreshDataSource(final String datasourceName) {
		connectionsHealth.put(datasourceName, false);
		if (!datasourcesConfig.containsKey(datasourceName)) {
			throw new InvalidDataSourceNameException(datasourceName);
		}
		try {
			HikariDataSource hikariDataSource = new HikariDataSource(datasourcesConfig.get(datasourceName));
			NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(hikariDataSource);

			namedTemplates.put(datasourceName, namedTemplate);

			connectionsHealth.put(datasourceName, true);
		} catch (Exception e) {
			throw new DatabaseUnavailableException(datasourceName);
		}
	}

	private HikariConfig configureConnection(PoolData poolData, Credential credential) {
		HikariConfig hikariConfig = new HikariConfig();

		LOGGER.info("Configuring Connection {}", credential.getName());

		Optional.ofNullable(credential.getDriver()).ifPresent(c -> {

			hikariConfig.setDriverClassName(credential.getDriver());

			hikariConfig.setConnectionTestQuery(Optional
					.ofNullable(
							CredentialType.getConnectionTypeByDriverName(credential.getDriver()))
					.get().getDefaultQuery());
		});

		Optional.ofNullable(credential.getUrl())
		.ifPresent(c -> hikariConfig.setJdbcUrl(credential.getUrl()));

		Optional.ofNullable(credential.getUsername())
		.ifPresent(c -> hikariConfig.setUsername(credential.getUsername()));

		Optional.ofNullable(credential.getPassword())
		.ifPresent(c -> hikariConfig.setPassword(credential.getPassword()));

		Optional.ofNullable(credential.getName())
		.ifPresent(c -> hikariConfig.setPoolName(credential.getName()));

		LOGGER.info(" Configuring Pool... ");

		Optional.ofNullable(poolData.getConnectionTimeout())
		.ifPresent(c -> hikariConfig.setConnectionTimeout(Long.parseLong(poolData.getConnectionTimeout().trim())));

		Optional.ofNullable(poolData.getMinimumIdle())
		.ifPresent(c -> hikariConfig.setMinimumIdle(Integer.parseInt(poolData.getMinimumIdle().trim())));

		Optional.ofNullable(poolData.getMaximumPoolSize())
		.ifPresent(c -> hikariConfig.setMaximumPoolSize(Integer.parseInt(poolData.getMaximumPoolSize().trim())));

		Optional.ofNullable(poolData.getIdleTimeout())
		.ifPresent(c -> hikariConfig.setIdleTimeout(Long.parseLong(poolData.getIdleTimeout().trim())));

		Optional.ofNullable(poolData.getMaxLifetime())
		.ifPresent(c -> hikariConfig.setMaxLifetime(Long.parseLong(poolData.getMaxLifetime().trim())));

		Optional.ofNullable(poolData.getAutoCommit())
		.ifPresent(c -> hikariConfig.setAutoCommit(Boolean.parseBoolean(poolData.getAutoCommit().trim())));
		Optional.ofNullable(poolData.getCachePrepStmts()).ifPresent(c -> hikariConfig
				.addDataSourceProperty("cachePrepStmts", Boolean.parseBoolean(poolData.getCachePrepStmts().trim())));

		Optional.ofNullable(poolData.getPrepStmtCacheSize()).ifPresent(c -> hikariConfig
				.addDataSourceProperty("prepStmtCacheSize", Integer.parseInt(poolData.getPrepStmtCacheSize().trim())));

		Optional.ofNullable(poolData.getPrepStmtCacheSqlLimit())
		.ifPresent(c -> hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit",
				Integer.parseInt(poolData.getPrepStmtCacheSqlLimit().trim())));

		Optional.ofNullable(poolData.getUseServerPrepStmts()).ifPresent(c -> hikariConfig
				.addDataSourceProperty("useServerPrepStmts", Boolean.parseBoolean(poolData.getUseServerPrepStmts().trim())));
		return hikariConfig;
	}

	public Map<String, Boolean> getConnectionsHealth() {
		return connectionsHealth;
	}

	public void setConnectionsHealth(Map<String, Boolean> connectionsHealth) {
		this.connectionsHealth = connectionsHealth;
	}

	public Map<String, NamedParameterJdbcTemplate> getNamedTemplates() {
		return namedTemplates;
	}

	public void setNamedTemplates(Map<String, NamedParameterJdbcTemplate> namedTemplates) {
		this.namedTemplates = namedTemplates;
	}

	public Map<String, HikariConfig> getDatasourcesConfig() {
		return datasourcesConfig;
	}

	public void setDatasourcesConfig(Map<String, HikariConfig> datasourcesConfig) {
		this.datasourcesConfig = datasourcesConfig;
	}

}
