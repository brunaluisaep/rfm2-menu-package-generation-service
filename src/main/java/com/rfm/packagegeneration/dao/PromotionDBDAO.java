package com.rfm.packagegeneration.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.rfm.packagegeneration.dto.GttPriority;
import com.rfm.packagegeneration.dto.GttPromoParentNodes;
import com.rfm.packagegeneration.dto.GttPromoPriority;
import com.rfm.packagegeneration.dto.PromoGtt;
import com.rfm.packagegeneration.dto.PromotionData;
import com.rfm.packagegeneration.dto.SuggestivePromotion;
import com.rfm.packagegeneration.hikari.Wizard;
import com.rfm.packagegeneration.utility.DateUtility;

@Repository
public class PromotionDBDAO extends CommonDAO {
	
	private static final Logger LOGGER = LogManager.getLogger("PromotionDBDAO");

	@Value("${datasource.used.query}")
	private String dataSourceQuery;

	public List<PromotionData> populateRestPromotionData(Long nodeId, Long marketId, String effectiveDate, String defaultMktLocale,
			Long calledFrom,String exportOnlyActivePromotions) throws Exception {
		
		final String query = getDaoXml("getPromoGttRest", DAOResources.PROMOTION_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("n_node_id", nodeId);
		paramMap.put("n_mkt_id", marketId);
		paramMap.put("n_lcle_id", Long.valueOf(defaultMktLocale));
		paramMap.put("effectiveDate",DateUtility.convertStringToDate(effectiveDate));
		paramMap.put("exportOnlyActivePromotions", exportOnlyActivePromotions);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<PromotionData> promotionDataList = null;
		if (listOfResults != null && listOfResults.size() > 0) {
			promotionDataList = new ArrayList<>();
			mapPopulatePromotionData(listOfResults, promotionDataList,effectiveDate);
		}	
		return promotionDataList;
	}
	
	private void mapPopulatePromotionData(List<Map<String, Object>> listOfResults, List<PromotionData> promotionDataList,String effectiveDate) {
	
		for (final Map<String, Object> data : listOfResults) {
			
			
			PromotionData promotionData = new PromotionData();
			if (data.get("CODE") != null) {
				promotionData.setCode(data.get("CODE").toString());
			}
			if (data.get("promo_inst_id") != null) {
				promotionData.setInstanceID(data.get("promo_inst_id").toString());
			}
		
			
			if (data.get("stus") != null && new BigDecimal(data.get("stus").toString()).longValue()==1) {
				promotionData.setStatus("ACTIVE");
			}else {
				promotionData.setStatus("INACTIVE");
			}
			
			promotionData.setTemplate(data.get("templt_typ")!=null?data.get("templt_typ").toString():"");
			
			if (data.get("templt_id") != null) {
				promotionData.setTemplateId(data.get("templt_id").toString());
			}			
			promotionData.setBarcode(data.get("BARCODE")!=null?data.get("BARCODE").toString():"");
			promotionData.setImage(data.get("IMG_PATH")!=null?data.get("IMG_PATH").toString():"");
			promotionData.setLanguage(data.get("rfm_lang_xml")!=null?data.get("rfm_lang_xml").toString():"");
			if(data.get("ENGINE_XML")!=null) {
				promotionData.setPromotion(data.get("ENGINE_XML").toString());
			}
			if(data.get("rnk")!=null) {
				promotionData.setRank(Long.parseLong(data.get("rnk").toString()));
			}

			if(data.get("promo_id")!=null) {
				promotionData.setPromoId(Long.parseLong(data.get("promo_id").toString()));
			}
			
			if(data.get("promo_typ")!=null) {
				promotionData.setPromoTyp(new BigDecimal(data.get("promo_typ").toString()).longValue());
			}
			if(data.get("priority")!=null) {
				promotionData.setPriority(new BigDecimal(data.get("priority").toString()).longValue());
			}
			promotionDataList.add(promotionData);
			
		}
		
	}

	public String getNodeType(Long nodeId, Long marketId) throws Exception {
		String nodeType = null;
		final String query = getDaoXml("getNodeType", DAOResources.PROMOTION_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("nodeId", nodeId);
		paramMap.put("marketId", marketId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if(data.get("nodeType")!=null) {
					nodeType=data.get("nodeType").toString();
				}
			}
		}
		
		return nodeType;
	}

	public String getExportOnlyActivePromotionsFlag(Long marketId) throws Exception {
		String exportOnlyActivePromotionsFlag = "false";
		final String query = getDaoXml("getValuesFromGlobalParam", DAOResources.PRODUCT_DB_DAO);
		LOGGER.info("PromotionDBDAO :: getExportOnlyActivePromotionsFlag");
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mktId", marketId);
		paramMap.put("paramName", "EXPORT_ONLY_ACTIVE_PROMOTIONS");
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if(data.get("param_value")!=null && data.get("param_value").toString().equalsIgnoreCase("true")) {
					exportOnlyActivePromotionsFlag=data.get("param_value").toString().toLowerCase();
				}
			}
		}
		LOGGER.info("PromotionDBDAO :: getExportOnlyActivePromotionsFlag :: results :"+ exportOnlyActivePromotionsFlag);
		return exportOnlyActivePromotionsFlag;
	}
	
	public Long getPrioritizedNodeCount(Long nodeId,Long marketId) throws Exception {
		final String query = getDaoXml("getPrioritizedNodeCount", DAOResources.PROMOTION_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("n_node_id", nodeId);
		paramMap.put("marketId", marketId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		Long prioritizedNodeCount=null;
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if (data.get("count")!=null) {
					prioritizedNodeCount=Long.valueOf(data.get("count").toString());
				}
			}
		}
		return prioritizedNodeCount;
	}
	public List<PromoGtt> getPromoGtt(Long nodeId, Long marketId, String effectiveDate, Long localeId, String string) throws Exception{
		final String query = getDaoXml("getPromoGttN", DAOResources.PROMOTION_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("nodeId", nodeId);
		paramMap.put("marketId", marketId);
		paramMap.put("lcleId",localeId);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<PromoGtt> promoGtt=new ArrayList<>();
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				promoGtt.add(populatePromoGtt(data));
			}
		}
		return promoGtt;
	}
	
	private PromoGtt populatePromoGtt(Map<String, Object> data) {
		PromoGtt promoGtt=new PromoGtt();
		if (data.get("promo_id")!=null) {
			promoGtt.setPromoId(Long.valueOf(data.get("promo_id").toString()));
		}
		if (data.get("promo_inst_id")!=null) {
			promoGtt.setPromoInstId(Long.valueOf(data.get("promo_inst_id").toString()));
		}
		if (data.get("crtr_node_id")!=null) {
			promoGtt.setCrtrNodeId(Long.valueOf(data.get("crtr_node_id").toString()));
		}
		if (data.get("templt_id")!=null) {
			promoGtt.setTempltId((data.get("templt_id").toString()));
		}
		
		return promoGtt;
	}

	public List<GttPriority> getGttPriority(Long nodeId, Long marketId, String effectiveDate,Long maxInhOrd, String gppnQueryUsed, String gppQueryUsed, String string) throws Exception {
		String query =null;
	
		switch (string) {
		case "Odca":{query = getDaoXml("getGttPriorityOdca",DAOResources.PROMOTION_DAO).replace(":GTT_PROMO_PARENT_NODES", gppnQueryUsed).replace(":promo_gtt", getPromoGttQuery());}
			break;
		case "Ia":{query = getDaoXml("getGttPriorityIa",DAOResources.PROMOTION_DAO).replace(":GTT_PROMO_PARENT_NODES", gppnQueryUsed);}
			break;
		case "Ib":{query = getDaoXml("getGttPriorityIb", DAOResources.PROMOTION_DAO).replace(":GTT_PROMO_PRIORITY",gppQueryUsed).replace(":GTT_PROMO_PARENT_NODES",gppnQueryUsed);}	
			break;
		case "Odcb":{query = getDaoXml("getGttPriorityOdcb",DAOResources.PROMOTION_DAO).replace(":GTT_PROMO_PARENT_NODES", gppnQueryUsed).replace(":GTT_PROMO_PRIORITY", gppQueryUsed).replace(":promo_gtt", getPromoGttQuery());}
			break;
		}
	 	
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("n_node_id", nodeId);
		paramMap.put("n_mkt_id", marketId);
		paramMap.put("var_max_inh_ord", maxInhOrd);
		paramMap.put("effectiveDate", DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<GttPriority> gttPriority=new ArrayList<GttPriority>();
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				gttPriority.add(populateGttPriority(data));
			}
		}		
		return gttPriority;		 
	}

	private GttPriority populateGttPriority(Map<String, Object> data) {
		GttPriority gttPriority=new GttPriority();
		if (data.get("promo_id")!=null) {
			gttPriority.setPromoId(Long.valueOf(data.get("promo_id").toString()));
		}
		if (data.get("priority")!=null) {
			gttPriority.setPromoPriority(new BigDecimal(data.get("priority").toString()).longValue());
		}
		if (data.get("pkgpriority")!=null) {
			gttPriority.setPkgPrty(Long.valueOf(data.get("pkgpriority").toString()));
		}
		if (data.get("promo_typ")!=null) {
			gttPriority.setPromoTyp(new BigDecimal(data.get("promo_typ").toString()).longValue());
		}	
		return gttPriority;
	}

	public List<GttPromoPriority> getGttPromoPriority(Long marketId, Long nodeId, Long maxInhOrd, String selectedQuery, String string) throws Exception {
		String query=null;
		switch (string) {
		case "n":{query=getDaoXml("getGttPromoPriorityN", DAOResources.PROMOTION_DAO).replace(":GTT_PROMO_PARENT_NODES",selectedQuery);}	
			break;
		case "uw":{query = getDaoXml("getGttPromoPriorityUnionWise", DAOResources.PROMOTION_DAO).replace(":GTT_PROMO_PARENT_NODES",selectedQuery);}
			break;
		}
	
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("n_node_id", nodeId);
		paramMap.put("n_mkt_id", marketId);
		paramMap.put("var_max_inh_ord", maxInhOrd);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<GttPromoPriority> gttPromoPriority=new ArrayList<>();
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				gttPromoPriority.add(populateGttPromoPriority(data));
			}
		}	
		return gttPromoPriority;
	}

	private GttPromoPriority populateGttPromoPriority(Map<String, Object> data) {
		GttPromoPriority gttPromopriorities=new GttPromoPriority();
		if (data.get("node_id")!=null) {
			gttPromopriorities.setNodeId(new BigDecimal(data.get("node_id").toString()).longValue());
		}
		if (data.get("promo_id")!=null) {
			gttPromopriorities.setPromoId(Long.valueOf(data.get("promo_id").toString()));
		}
		if (data.get("priority")!=null) {
			gttPromopriorities.setPriority(new BigDecimal(data.get("priority").toString()).longValue());
		}
		if (data.get("mkt_id")!=null) {
			gttPromopriorities.setMktId(Long.valueOf(data.get("mkt_id").toString()));
		}
		if (data.get("dltd_fl")!=null) {
			gttPromopriorities.setDltdFl(Long.valueOf(data.get("mkt_id").toString()));
		}
		return gttPromopriorities;
	}

	public List<GttPromoParentNodes> getGttPromoParentNodes(Long marketId, Long nodeId, String string) throws Exception {
		String queryName=null;
		switch (string) {
		case "S":queryName="getGttPromoParentNodesS";	
			break;
		case "Hq":queryName="getGttPromoParentNodesHq";	
			break;
		}
		final String query = getDaoXml(queryName, DAOResources.PROMOTION_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("n_node_id", nodeId);
		paramMap.put("n_mkt_id", marketId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<GttPromoParentNodes> gttPromoParentNodes=new ArrayList<>();
		if (listOfResults != null && !listOfResults.isEmpty()) {
			for (final Map<String, Object> data : listOfResults) {
				gttPromoParentNodes.add(populateGttPromoNodes(data));
			}
		}
		return gttPromoParentNodes;
	}
	
	private GttPromoParentNodes populateGttPromoNodes(Map<String, Object> data) {
		GttPromoParentNodes promoParentNodes=new GttPromoParentNodes();
		if (data.get("node_id")!=null) {
			promoParentNodes.setNodeId(Long.valueOf(data.get("node_id").toString()));
		}
		if (data.get("inhr_ord")!=null) {
			promoParentNodes.setInhrOrd(Long.valueOf(data.get("inhr_ord").toString()));
		}
		if (data.get("node_typ")!=null) {
			promoParentNodes.setNodeType(data.get("node_typ").toString());
		}
		return promoParentNodes;
	}



	public Long getCustInhrOdr(Long nodeId) throws Exception {
		final String query = getDaoXml("getCustInhrOdr", DAOResources.PROMOTION_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("n_node_id", nodeId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		Long custInhOrd=null;
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if (data.get("inhr_ord")!=null) {
					custInhOrd=Long.valueOf(data.get("inhr_ord").toString());
				}
			}
		}
		return custInhOrd;
	}

	public String getGttPromoPriorityQueryUsed(String selectedQuery) throws Exception {
		String query="";
		switch (selectedQuery) {
		case "normal":{query=getDaoXml("getGttPromoPriorityN",DAOResources.PROMOTION_DAO);}
			break;
		case "unionWise":{query=getDaoXml("getGttPromoPriorityUnionWise",DAOResources.PROMOTION_DAO);}
			break;
		}
		return query;
	}

	public String getGttPromoParentQueryUsed(String gppnQuery) throws Exception {
		String query="";
		switch (gppnQuery) {
		case "hQuery":{query=getDaoXml("getGttPromoParentNodesHq",DAOResources.PROMOTION_DAO);}
			break;

		case "sQuery":{query=getDaoXml("getGttPromoParentNodesS",DAOResources.PROMOTION_DAO);}
			break;
		}
		return query;
	}

	public Long getPrioritizedNode(Long nodeId, Long marketId, Long maxInhOrd, String gppnQueryUsed) throws Exception {
		final String query = getDaoXml("getPrioritizedNodeCountM", DAOResources.PROMOTION_DAO).replace(":GTT_PROMO_PARENT_NODES", gppnQueryUsed);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("n_node_id", nodeId);
		paramMap.put("n_mkt_id", marketId);
		paramMap.put("var_max_inh_ord", maxInhOrd);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		Long prioritizedNodeCount=null;
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if (data.get("count")!=null) {
					prioritizedNodeCount=Long.valueOf(data.get("count").toString());
				}
			}
		}
		return prioritizedNodeCount;
	}
	
	public String getPromoGttQuery() throws Exception {
		String promoGttQuery=getDaoXml("getPromoGttN",DAOResources.PROMOTION_DAO);
		return promoGttQuery;
	}

	public List<SuggestivePromotion> getSusgtvPromos(Long marketId) throws Exception {
		List<SuggestivePromotion> allSugstvPromos=new ArrayList<SuggestivePromotion>();
		final String query = getDaoXml("getSugstvPromos", DAOResources.PROMOTION_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mkt_id", marketId);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				allSugstvPromos.add(mapSugstvPromo(data));
			}
		}
		return allSugstvPromos;
	}

	private SuggestivePromotion mapSugstvPromo(Map<String, Object> data) {
		SuggestivePromotion sugstvPromo=new SuggestivePromotion();
		if (data.get("promo_id")!=null) {
			sugstvPromo.setPromoId(Long.valueOf(data.get("promo_id").toString()));
		}
		if (data.get("sugstv_id")!=null) {
			sugstvPromo.setSugstvId(Long.valueOf(data.get("sugstv_id").toString()));
		}
		if (data.get("sugstv_typ")!=null) {
			sugstvPromo.setSugstvTyp(Long.valueOf(data.get("sugstv_typ").toString()));
		}
		return sugstvPromo;
	}

	public Long getMaxInhOrd(Long nodeId, Long marketId, String gppnQuery) throws Exception {
		Long getMaxInhOrd=null;
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("n_node_id", nodeId);
		paramMap.put("n_mkt_id", marketId);
		final String query = getDaoXml("getMaxInhOrd", DAOResources.PROMOTION_DAO).replace(":GTT_PROMO_PARENT_NODES", gppnQuery);
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		if (listOfResults != null && listOfResults.size() > 0) {
			for (final Map<String, Object> data : listOfResults) {
				if (data.get("max")!=null) {
					getMaxInhOrd=Long.valueOf(data.get("max").toString());
				}
			}
		}		
		return getMaxInhOrd;
	}

	public List<PromotionData> getPromoGttApplied(Long nodeId, Long marketId, String effectiveDate, String defaultMktLocale,
			Long calledFrom,String exportOnlyActivePromotions) throws Exception {
	
		final String query = getDaoXml("getPromoGtt", DAOResources.PROMOTION_DAO);
		final Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("nodeId", nodeId);
		paramMap.put("marketId", marketId);
		paramMap.put("exportOnlyActivePromotions", exportOnlyActivePromotions);
		paramMap.put("lcleId", Long.valueOf(defaultMktLocale));
		paramMap.put("effectiveDate",DateUtility.convertStringToDate(effectiveDate));
		List<Map<String, Object>> listOfResults = Wizard.queryForList(dataSourceQuery, query, paramMap);
		List<PromotionData> promotionDataList = null;
		if (listOfResults != null && listOfResults.size() > 0) {
			promotionDataList = new ArrayList<>();
			mapPopulatePromotionData(listOfResults, promotionDataList,effectiveDate);
		}	
		return promotionDataList;
	}

	
}
