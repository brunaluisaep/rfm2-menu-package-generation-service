package com.rfm.packagegeneration.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageGeneratorDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7719938037750038495L;


    private String marketts;
    private String                    date;
    private Long	                  nodeID;
    private Long                      packageStatusID;
    private String                    scheduleType;
    private PackageXMLParametersDTO[] packageXmlParameter;
    private Map<String, Object>       restaurantDataPointers;
    private Long	                  marketID;
    private boolean                   generatedSeqNum;
    private PackageStatusDTO          packageStatusDTO;
    private PackageSmartReminderDTO[] packageSmartReminderDTO;
    private GeneratorDefinedValues    generatorDefinedValues;
    private Map<String, Map<String, String>> products;
	private Map<String, List<ScreenDetails>> Screens;
    private String scheduleRequestID;
    private Restaurant restaurant;
    private Long masterSetId;
    private List<Long> restSetIds;
    private List<LogStatus> logStatusList;
    private boolean isGenerated;
    private Map<Long, ProductDetails> allProducts = new HashMap<>();
    

	public Map<Long, ProductDetails> getAllProducts() {
		return allProducts;
	}

	public void setAllProducts(Map<Long, ProductDetails> allProducts) {
		this.allProducts = allProducts;
	}
    private PackageMainStoreDBDTO storeDB;

    public PackageMainStoreDBDTO getStoreDB() {
		return storeDB;
	}

	public void setStoreDB(PackageMainStoreDBDTO storeDB) {
		this.storeDB = storeDB;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    private boolean generateProductXml;
	private boolean generateScreenXml;
    private boolean generateStoreXml;
    private boolean generatePromotionXml;
    

    public boolean isGeneratePromotionXml() {
		return generatePromotionXml;
	}

	public void setGeneratePromotionXml(boolean generatePromotionXml) {
		this.generatePromotionXml = generatePromotionXml;
	}

	public boolean isGenerateStoreXml() {
		return generateStoreXml;
	}

	public void setGenerateStoreXml(boolean generateStoreXml) {
		this.generateStoreXml = generateStoreXml;
	}

	public boolean isGenerateProductXml() {
		return generateProductXml;
	}

	public void setGenerateProductXml(boolean generateProductXml) {
		this.generateProductXml = generateProductXml;
	}

	public boolean isGenerateScreenXml() {
		return generateScreenXml;
	}

	public void setGenerateScreenXml(boolean generateScreenXml) {
		this.generateScreenXml = generateScreenXml;
	}

	public boolean isGenerated() {
		return isGenerated;
	}

	public void setGenerated(boolean isGenerated) {
		this.isGenerated = isGenerated;
	}

	public List<LogStatus> getLogStatusList() {
		return logStatusList;
	}

	public void setLogStatusList(List<LogStatus> logStatusList) {
		this.logStatusList = logStatusList;
	}
    public PackageGeneratorDTO() {
		super();
		
	}

	public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getNodeID() {
        return nodeID;
    }

    public void setNodeID(Long nodeID) {
        this.nodeID = nodeID;
    }

    public Long getPackageStatusID() {
        return packageStatusID;
    }

    public void setPackageStatusID(Long packageStatusID) {
        this.packageStatusID = packageStatusID;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public PackageXMLParametersDTO[] getPackageXmlParameter() {
        return packageXmlParameter;
    }

    public void setPackageXmlParameter(PackageXMLParametersDTO[] packageXmlParameter) {
        this.packageXmlParameter = packageXmlParameter;
    }

    public Map<String, Object> getRestaurantDataPointers() {
        return restaurantDataPointers;
    }

    public void setRestaurantDataPointers(Map<String, Object> restaurantDataPointers) {
        this.restaurantDataPointers = restaurantDataPointers;
    }

    public Long getMarketID() {
        return marketID;
    }

    public void setMarketID(Long marketID) {
        this.marketID = marketID;
    }

    public boolean isGeneratedSeqNum() {
        return generatedSeqNum;
    }

    public void setGeneratedSeqNum(boolean generatedSeqNum) {
        this.generatedSeqNum = generatedSeqNum;
    }

    public PackageStatusDTO getPackageStatusDTO() {
        return packageStatusDTO;
    }

    public void setPackageStatusDTO(PackageStatusDTO packageStatusDTO) {
        this.packageStatusDTO = packageStatusDTO;
    }

    public PackageSmartReminderDTO[] getPackageSmartReminderDTO() {
        return packageSmartReminderDTO;
    }

    public void setPackageSmartReminderDTO(PackageSmartReminderDTO[] packageSmartReminderDTO) {
        this.packageSmartReminderDTO = packageSmartReminderDTO;
    }

    public GeneratorDefinedValues getGeneratorDefinedValues() {
        return generatorDefinedValues;
    }

    public void setGeneratorDefinedValues(final GeneratorDefinedValues generatorDefinedValues) {
        this.generatorDefinedValues = generatorDefinedValues;
    }

    public Map<String, Map<String, String>> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Map<String, String>> products) {
        this.products = products;
    }

	public String getMarketts() {
		return marketts;
	}

	public void setMarketts(String marketts) {
		this.marketts = marketts;
	}
	
	@Override
    public String toString() {
        return "PackageGeneratorDTO{" + "marketts='" + marketts + '\'' + ", date='" + date + '\'' + ", nodeID='" + nodeID + '\'' + ", packageStatusID='" + packageStatusID + '\'' + ", scheduleType='" + scheduleType + '\'' + ", packageXmlParameter=" + Arrays.toString(
        packageXmlParameter) + ", restaurantDataPointers=" + restaurantDataPointers + ", marketID='" + marketID + '\'' + ", generatedSeqNum=" + generatedSeqNum + ", packageStatusDTO=" + packageStatusDTO + ", packageSmartReminderDTO=" + Arrays.toString(
        packageSmartReminderDTO) + ", generatorDefinedValues=" + generatorDefinedValues + ", products=" + products + '}';
    }

	public Long getMasterSetId() {
		return masterSetId;
	}

	public void setMasterSetId(Long masterSetId) {
		this.masterSetId = masterSetId;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<Long> getRestSetIds() {
		return restSetIds;
	}

	public void setRestSetIds(List<Long> restSetIds) {
		this.restSetIds = restSetIds;
	}

	public String getScheduleRequestID() {
		return scheduleRequestID;
	}

	public void setScheduleRequestID(String scheduleRequestID) {
		this.scheduleRequestID = scheduleRequestID;
	}

	public Map<String, List<ScreenDetails>> getScreens() {
		return Screens;
	}

	public void setScreens(Map<String, List<ScreenDetails>> screens) {
		Screens = screens;
	}

}
