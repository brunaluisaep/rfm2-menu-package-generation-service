package com.rfm.packagegeneration.dto;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
//@JsonInclude(value=Include.NON_NULL)
public class StoreDetails {
	private Long rfmId;
	private Long companyId;
	private Long storeId;
	private Long storeLegacyId;
	private String storeAddress;
	private String storeZipCode;
	private String city;
	private String state;
	private String country;
	private String email;
	private String homePage;
	private String storePhone;
	private String helpDeskInfo;
	private String ownershipType;
	private List<String> storeType;
	private String storeLatitude;
	private String storeLongitude;
	public Long getRfmId() {
		return rfmId;
	}
	public void setRfmId(Long rfmId) {
		this.rfmId = rfmId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public Long getStoreLegacyId() {
		return storeLegacyId;
	}
	public void setStoreLegacyId(Long storeLegacyId) {
		this.storeLegacyId = storeLegacyId;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public String getStoreZipCode() {
		return storeZipCode;
	}
	public void setStoreZipCode(String storeZipCode) {
		this.storeZipCode = storeZipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public String getStorePhone() {
		return storePhone;
	}
	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}
	public String getHelpDeskInfo() {
		return helpDeskInfo;
	}
	public void setHelpDeskInfo(String helpDeskInfo) {
		this.helpDeskInfo = helpDeskInfo;
	}
	public String getOwnershipType() {
		return ownershipType;
	}
	public void setOwnershipType(String ownershipType) {
		this.ownershipType = ownershipType;
	}
	public List<String> getStoreType() {
		return storeType;
	}
	public void setStoreType(List<String> storeType) {
		this.storeType = storeType;
	}
	public String getStoreLatitude() {
		return storeLatitude;
	}
	public void setStoreLatitude(String storeLatitude) {
		this.storeLatitude = storeLatitude;
	}
	public String getStoreLongitude() {
		return storeLongitude;
	}
	public void setStoreLongitude(String storeLongitude) {
		this.storeLongitude = storeLongitude;
	}
	
}
