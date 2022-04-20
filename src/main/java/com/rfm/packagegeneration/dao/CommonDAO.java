package com.rfm.packagegeneration.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;


public class CommonDAO {
	private static final Logger LOGGER = LogManager.getLogger("CommonDAO");
    protected final Map<String, String> mapQueryCache = new ConcurrentHashMap<>();

    public String getDaoXml(final String queryName, DAOResources productDBDao) throws Exception {

		if (mapQueryCache.containsKey(queryName)) {
			return mapQueryCache.get(queryName);
		}

		Resource res = new ClassPathResource(productDBDao.getFileName());

		final SAXReader saxReaderObj = new SAXReader();
        //Disabling access to external entities in XML parsing
        setFeatureSaxReader(saxReaderObj);
        
		Document daoDocument = null;
		try {
			daoDocument = saxReaderObj.read(res.getInputStream());
		} catch (Exception e) {
			LOGGER.error(e);
			throw new Exception(e);
		}

		String query = null;
		final Element elQueryXML = daoDocument.getRootElement();
		if (elQueryXML != null) {
			final Element elGetRestListDetail = elQueryXML.element(queryName) != null ? elQueryXML.element(queryName)
					: null;
			if (elGetRestListDetail != null) {
				query = elGetRestListDetail.getText();
			}
		}

		if (query != null) {
			mapQueryCache.put(queryName, query);
		}

		return query;
	}

    /**
     * Disables access to external entities in XML parsing
     *
     * @param saxReaderObj
     */
    public void setFeatureSaxReader(SAXReader saxReaderObj) {
        try {
            saxReaderObj.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        } catch (SAXException e) {
        	LOGGER.error("CommonDAO: setFeatureSaxReader {} ", e.getMessage(), e);
        }
    }

}
