package com.rfm.packagegeneration.cache;

import static java.util.Arrays.asList;

import java.util.List;

public class KeyNameHelper {

	private KeyNameHelper() {
		super();
	}

	public static final String SEPARATOR = "_";

	public static final String getProductDataKey(String requestId, String effectiveDate, Long setId) {

		return getProductDataKey(requestId, effectiveDate, asList(setId));
	}

	private static String removeDateSeparator(String effectiveDate) {
		return effectiveDate.replace("/", "");
	}

	public static final String getProductDataKey(String requestId, String effectiveDate, List<Long> setLayer) {

		String[] parts = new String[setLayer.size() + 2];
		int i = 0;
		parts[i++] = requestId;
		parts[i++] = removeDateSeparator(effectiveDate);

		for (int j = 0; j < setLayer.size(); j++) {
			parts[i++] = String.valueOf(setLayer.get(j));
		}

		return String.join(SEPARATOR, parts);
	}
	
	public static final String getRedisDataKey(String requestId,String objectName) {
		return requestId+SEPARATOR+objectName;
	}
	
}
