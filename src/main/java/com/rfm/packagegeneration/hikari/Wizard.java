package com.rfm.packagegeneration.hikari;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.rfm.packagegeneration.hikari.domain.DynammicDatasource;
import com.rfm.packagegeneration.hikari.exception.InvalidDataSourceNameException;

@Configuration
public class Wizard {
	private static final Logger LOGGER = LogManager.getLogger("Wizard");
	private static Manager manager;

	@Autowired
	private DynammicDatasource datasourceProperties;

	@EventListener(ApplicationReadyEvent.class)
	private void postinit() {
		manager = new Manager();
		manager.loadTemplates(datasourceProperties);
	}

	private static JdbcTemplate getJDBCTemplateByName(final String jdbcName) {
		Map<String, Boolean> connectionsHealth = manager.getConnectionsHealth();

		if (!connectionsHealth.containsKey(jdbcName)) {
			throw new InvalidDataSourceNameException(jdbcName);
		}

		if (Boolean.FALSE.equals(connectionsHealth.get(jdbcName))) {
			manager.refreshDataSource(jdbcName);
		}

		return manager.getNamedTemplates().get(jdbcName).getJdbcTemplate();
	}

	private static NamedParameterJdbcTemplate getNamedParameterJDBCTemplateByName(final String jdbcName) {
		Map<String, Boolean> connectionsHealth = manager.getConnectionsHealth();

		if (!connectionsHealth.containsKey(jdbcName)) {
			throw new InvalidDataSourceNameException(jdbcName);
		}

		if (Boolean.FALSE.equals(connectionsHealth.get(jdbcName))) {
			manager.refreshDataSource(jdbcName);
		}

		return manager.getNamedTemplates().get(jdbcName);
	}

	public static <T> T execute(final String name, final ConnectionCallback<T> action) {
		try {
			return getJDBCTemplateByName(name).execute(action);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T execute(final String name, final StatementCallback<T> action) {
		try {
			return getJDBCTemplateByName(name).execute(action);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static void execute(final String name, final String sql) {
		try {
			getJDBCTemplateByName(name).execute(sql);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T query(final String name, final String sql, ResultSetExtractor<T> rse) {
		try {
			return getJDBCTemplateByName(name).query(sql, rse);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static void query(final String name, final String sql, RowCallbackHandler rch) {
		try {
			getJDBCTemplateByName(name).query(sql, rch);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> List<T> query(final String name, final String sql, RowMapper<T> rowMapper) {
		try {
			return getJDBCTemplateByName(name).query(sql, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String name, final String sql, RowMapper<T> rowMapper) {
		try {
			return getJDBCTemplateByName(name).queryForObject(sql, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String name, final String sql, Class<T> requiredType) {
		try {
			return getJDBCTemplateByName(name).queryForObject(sql, requiredType);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static Map<String, Object> queryForMap(final String name, final String sql) {
		try {
			return getJDBCTemplateByName(name).queryForMap(sql);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> List<T> queryForList(final String name, final String sql, Class<T> elementType) {
		try {
			return getJDBCTemplateByName(name).queryForList(sql, elementType);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static List<Map<String, Object>> queryForList(final String name, final String sql, String queryName) {
		long startTime = System.currentTimeMillis();
		try {
			return queryForList(name, sql);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		} finally {
			long endTime = System.currentTimeMillis();
			LOGGER.info(queryName + ": " + (endTime - startTime) + " ms");
		}
	}

	public static List<Map<String, Object>> queryForList(final String name, final String sql) {
		try {
			return getJDBCTemplateByName(name).queryForList(sql);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static SqlRowSet queryForRowSet(final String name, final String sql) {
		try {
			return getJDBCTemplateByName(name).queryForRowSet(sql);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int update(final String name, final String sql) {
		try {
			return getJDBCTemplateByName(name).update(sql);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int[] batchUpdate(final String name, final String... sql) {
		try {
			return getJDBCTemplateByName(name).batchUpdate(sql);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T execute(final String name, final PreparedStatementCreator psc,
			PreparedStatementCallback<T> action) {
		try {
			return getJDBCTemplateByName(name).execute(psc, action);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T execute(final String name, final String sql, PreparedStatementCallback<T> action) {
		try {
			return getJDBCTemplateByName(name).execute(sql, action);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T query(final String name, final PreparedStatementCreator psc,
			ResultSetExtractor<T> rse) {
		try {
			return getJDBCTemplateByName(name).query(psc, rse);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T query(final String name, final String sql, PreparedStatementSetter pss,
			ResultSetExtractor<T> rse) {
		try {
			return getJDBCTemplateByName(name).query(sql, pss, rse);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T query(final String name, final String sql, Object[] args, int[] argTypes,
			ResultSetExtractor<T> rse) {
		try {
			return getJDBCTemplateByName(name).query(sql, args, argTypes, rse);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T query(final String name, final String sql, ResultSetExtractor<T> rse,
			Object... args) {
		try {
			return getJDBCTemplateByName(name).query(sql, rse, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static void query(final String name, final PreparedStatementCreator psc,
			RowCallbackHandler rch) {
		try {
			getJDBCTemplateByName(name).query(psc, rch);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static void query(final String name, final String sql, PreparedStatementSetter pss,
			RowCallbackHandler rch) {
		try {
			getJDBCTemplateByName(name).query(sql, pss, rch);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static void query(final String name, final String sql, Object[] args, int[] argTypes,
			RowCallbackHandler rch) {
		try {
			getJDBCTemplateByName(name).query(sql, args, argTypes, rch);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static void query(final String name, final String sql, RowCallbackHandler rch, Object... args) {
		try {
			getJDBCTemplateByName(name).query(sql, rch, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> List<T> query(final String name, final PreparedStatementCreator psc,
			RowMapper<T> rowMapper) {
		try {
			return getJDBCTemplateByName(name).query(psc, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> List<T> query(final String name, final String sql, PreparedStatementSetter pss,
			RowMapper<T> rowMapper) {
		try {
			return getJDBCTemplateByName(name).query(sql, pss, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> List<T> query(final String name, final String sql, Object[] args, int[] argTypes,
			RowMapper<T> rowMapper) {
		try {
			return getJDBCTemplateByName(name).query(sql, args, argTypes, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> List<T> query(final String name, final String sql, RowMapper<T> rowMapper,
			Object... args) {
		try {
			return getJDBCTemplateByName(name).query(sql, rowMapper, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String name, final String sql, Object[] args, int[] argTypes,
			RowMapper<T> rowMapper) {
		try {
			return getJDBCTemplateByName(name).queryForObject(sql, args, argTypes, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String name, final String sql, RowMapper<T> rowMapper,
			Object... args) {
		try {
			return getJDBCTemplateByName(name).queryForObject(sql, rowMapper, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String name, final String sql, Object[] args, int[] argTypes,
			Class<T> requiredType) {
		try {
			return getJDBCTemplateByName(name).queryForObject(sql, args, argTypes, requiredType);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String name, final String sql, Class<T> requiredType,
			Object... args) {
		try {
			return getJDBCTemplateByName(name).queryForObject(sql, requiredType, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static Map<String, Object> queryForMap(final String name, final String sql, Object[] args,
			int[] argTypes) {
		try {
			return getJDBCTemplateByName(name).queryForMap(sql, args, argTypes);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static Map<String, Object> queryForMap(final String name, final String sql, Object... args) {
		try {
			return getJDBCTemplateByName(name).queryForMap(sql, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> List<T> queryForList(final String name, final String sql, Object[] args,
			int[] argTypes, Class<T> elementType) {
		try {
			return getJDBCTemplateByName(name).queryForList(sql, args, argTypes, elementType);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> List<T> queryForList(final String name, final String sql, Class<T> elementType,
			Object... args) {
		try {
			return getJDBCTemplateByName(name).queryForList(sql, elementType, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static List<Map<String, Object>> queryForList(final String name, final String sql,
			Object[] args, int[] argTypes) {
		try {
			return getJDBCTemplateByName(name).queryForList(sql, args, argTypes);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static List<Map<String, Object>> queryForList(final String name, final String sql,
			Object... args) {
		try {
			return getJDBCTemplateByName(name).queryForList(sql, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static SqlRowSet queryForRowSet(final String name, final String sql, Object[] args,
			int[] argTypes) {
		try {
			return getJDBCTemplateByName(name).queryForRowSet(sql, args, argTypes);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static SqlRowSet queryForRowSet(final String name, final String sql, Object... args) {
		try {
			return getJDBCTemplateByName(name).queryForRowSet(sql, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int update(final String name, final PreparedStatementCreator psc) {
		try {
			return getJDBCTemplateByName(name).update(psc);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int update(final String name, final PreparedStatementCreator psc,
			KeyHolder generatedKeyHolder) {
		try {
			return getJDBCTemplateByName(name).update(psc, generatedKeyHolder);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int update(final String name, final String sql, PreparedStatementSetter pss) {
		try {
			return getJDBCTemplateByName(name).update(sql, pss);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int update(final String name, final String sql, Object[] args, int[] argTypes) {
		try {
			return getJDBCTemplateByName(name).update(sql, args, argTypes);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int update(final String name, final String sql, Object... args) {
		try {
			return getJDBCTemplateByName(name).update(sql, args);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int[] batchUpdate(final String name, final String sql,
			BatchPreparedStatementSetter pss) {
		try {
			return getJDBCTemplateByName(name).batchUpdate(sql, pss);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int[] batchUpdate(final String name, final String sql, List<Object[]> batchArgs) {
		try {
			return getJDBCTemplateByName(name).batchUpdate(sql, batchArgs);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static int[] batchUpdate(final String name, final String sql, List<Object[]> batchArgs,
			int[] argTypes) {
		try {
			return getJDBCTemplateByName(name).batchUpdate(sql, batchArgs, argTypes);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> int[][] batchUpdate(final String name, final String sql, Collection<T> batchArgs,
			int batchSize, ParameterizedPreparedStatementSetter<T> pss) {
		try {
			return getJDBCTemplateByName(name).batchUpdate(sql, batchArgs, batchSize, pss);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T execute(final String name, final CallableStatementCreator csc,
			CallableStatementCallback<T> action) {
		try {
			return getJDBCTemplateByName(name).execute(csc, action);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T execute(final String name, final String callString,
			CallableStatementCallback<T> action) {
		try {
			return getJDBCTemplateByName(name).execute(callString, action);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static Map<String, Object> call(final String name, final CallableStatementCreator csc,
			List<SqlParameter> declaredParameters) {
		try {
			return getJDBCTemplateByName(name).call(csc, declaredParameters);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(name, false);
			throw e;
		}
	}

	public static <T> T execute(final String jdbcName, String sql, SqlParameterSource paramSource,
			PreparedStatementCallback<T> action) throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).execute(sql, paramSource, action);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> T execute(final String jdbcName, String sql, Map<String, ?> paramMap, PreparedStatementCallback<T> action)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).execute(sql, paramMap, action);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> T query(final String jdbcName, String sql, SqlParameterSource paramSource, ResultSetExtractor<T> rse)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).query(sql, paramSource, rse);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> T query(final String jdbcName, String sql, Map<String, ?> paramMap, ResultSetExtractor<T> rse)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).query(sql, paramMap, rse);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static void query(final String jdbcName, String sql, SqlParameterSource paramSource, RowCallbackHandler rch)
			throws DataAccessException {
		try {
			getNamedParameterJDBCTemplateByName(jdbcName).query(sql, paramSource, rch);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}   
	}

	public static void query(final String jdbcName, String sql, Map<String, ?> paramMap, RowCallbackHandler rch)
			throws DataAccessException {
		try {
			getNamedParameterJDBCTemplateByName(jdbcName).query(sql, paramMap, rch);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> List<T> query(final String jdbcName, String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).query(sql, paramSource, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> List<T> query(final String jdbcName, String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).query(sql, paramMap, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String jdbcName, String sql, SqlParameterSource paramSource, RowMapper<T> rowMapper)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForObject(sql, paramSource, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String jdbcName, String sql, Map<String, ?> paramMap, RowMapper<T> rowMapper)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForObject(sql, paramMap, rowMapper);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String jdbcName, String sql, SqlParameterSource paramSource, Class<T> requiredType)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForObject(sql, paramSource, requiredType);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> T queryForObject(final String jdbcName, String sql, Map<String, ?> paramMap, Class<T> requiredType, String queryName)
			throws DataAccessException {
		long startTime = System.currentTimeMillis();
		try {
			return queryForObject(jdbcName, sql, paramMap, requiredType, "");
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		} finally {
			long endTime = System.currentTimeMillis();
			LOGGER.info("queryForObject: " + (endTime - startTime) + " ms");
		}		
	}

	public static <T> T queryForObject(final String jdbcName, String sql, Map<String, ?> paramMap, Class<T> requiredType)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForObject(sql, paramMap, requiredType);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static Map<String, Object> queryForMap(final String jdbcName, String sql, SqlParameterSource paramSource)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForMap(sql, paramSource);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static Map<String, Object> queryForMap(final String jdbcName, String sql, Map<String, ?> paramMap, String queryName)
			throws DataAccessException {
		long startTime = System.currentTimeMillis();
		try {
			return queryForMap(jdbcName, sql, paramMap);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		} finally {
			long endTime = System.currentTimeMillis();
			LOGGER.info(queryName + ": " + (endTime - startTime) + " ms");
		}

	}
	
	public static Map<String, Object> queryForMap(final String jdbcName, String sql, Map<String, ?> paramMap)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForMap(sql, paramMap);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> List<T> queryForList(final String jdbcName, String sql, SqlParameterSource paramSource,
			Class<T> elementType) throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForList(sql, paramSource, elementType);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static <T> List<T> queryForList(final String jdbcName, String sql, Map<String, ?> paramMap, Class<T> elementType)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForList(sql, paramMap, elementType);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static List<Map<String, Object>> queryForList(final String jdbcName, String sql, SqlParameterSource paramSource)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForList(sql, paramSource);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static List<Map<String, Object>> queryForList(final String jdbcName, String sql, Map<String, ?> paramMap, String queryName)
			throws DataAccessException {
		long startTime = System.currentTimeMillis();
		try {
			return queryForList(jdbcName, sql, paramMap);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		} finally {
			long endTime = System.currentTimeMillis();
			LOGGER.info(queryName + ": " + (endTime - startTime) + " ms");
		}
	}
	public static List<Map<String, Object>> queryForList(final String jdbcName, String sql, Map<String, ?> paramMap)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForList(sql, paramMap);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static SqlRowSet queryForRowSet(final String jdbcName, String sql, SqlParameterSource paramSource)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForRowSet(sql, paramSource);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static SqlRowSet queryForRowSet(final String jdbcName, String sql, Map<String, ?> paramMap)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).queryForRowSet(sql, paramMap);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static int update(final String jdbcName, String sql, SqlParameterSource paramSource) throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).update(sql, paramSource);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static int update(final String jdbcName, String sql, Map<String, ?> paramMap) throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).update(sql, paramMap);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static int update(final String jdbcName, String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder)
			throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).update(sql, paramSource, generatedKeyHolder);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static int update(final String jdbcName, String sql, SqlParameterSource paramSource, KeyHolder generatedKeyHolder,
			String[] keyColumnNames) throws DataAccessException {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).update(sql, paramSource, generatedKeyHolder, keyColumnNames);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static int[] batchUpdate(final String jdbcName, String sql, Map<String, ?>[] batchValues) {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).batchUpdate(sql, batchValues);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}

	public static int[] batchUpdate(final String jdbcName, String sql, SqlParameterSource[] batchArgs) {
		try {
			return getNamedParameterJDBCTemplateByName(jdbcName).batchUpdate(sql, batchArgs);
		} catch (Exception e) {
			manager.getConnectionsHealth().put(jdbcName, false);
			throw e;
		}
	}
}