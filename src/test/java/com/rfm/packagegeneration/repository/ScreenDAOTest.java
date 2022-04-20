package com.rfm.packagegeneration.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import com.rfm.packagegeneration.dao.ScreenDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScreenDAOTest {

	@Autowired
	ScreenDAO screenDAO;

	@Test
	void getAllEventsTest() throws Exception {
		final Map<String, String> getAllEvents = screenDAO.getAllEvents(2L);
		Assert.notEmpty(getAllEvents, "getAllEvents is not Empty");
	}

	@Test
	void getAllGrillGroupsTest() throws Exception {
		final Map<String, String> getAllGrillGroups = screenDAO.getAllGrillGroups(2L);
		Assert.notEmpty(getAllGrillGroups, "getAllGrillGroups is not Empty");
	}

	@Test
	void getAllSmartRemindersTest() throws Exception {
		final Map<String, String> getAllSmartReminders = screenDAO.getAllSmartReminders(2L);
		Assert.notEmpty(getAllSmartReminders, "getAllSmartReminders is not Empty");
	}

	@Test
	void getAllWorkflowParamsTest() throws Exception {
		final Map<String, String> getAllWorkflowParams = screenDAO.getAllWorkflowParams(2L);
		Assert.notEmpty(getAllWorkflowParams, "getAllWorkflowParams is not Empty");
	}

	@Test
	void getAllWorkflowsTest() throws Exception {
		final Map<String, String> getAllWorkflows = screenDAO.getAllWorkflows(2L);
		Assert.notEmpty(getAllWorkflows, "getAllWorkflows is not Empty");
	}

	@Test
	void getAssignedScreenSetTest() throws Exception {
		final Map<String, String> getAssignedScreenSet = screenDAO.getAssignedScreenSet(2L, "1812819", "60003290",
				"12/14/2021");
		Assert.notEmpty(getAssignedScreenSet, "getAssignedScreenSet is not Empty");
	}

	@Test
	void getButtonProductCodeTest() throws Exception {
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(56069679L);
		screenInstanceIds.add(56069679L);
		ArrayList<Long> buttonIds = new ArrayList<>();
		buttonIds.add(5976512L);
		buttonIds.add(5976559L);
		final Map<String, String> getButtonProductCode = screenDAO.getButtonProductCode(buttonIds, screenInstanceIds);
		Assert.notNull(getButtonProductCode, "getButtonProductCode is not Empty");
	}

	@Test
	void getLocalizationSetsTest() throws Exception {
		final Map<String, String> getLocalizationSets = screenDAO.getLocalizationSets("1812819", "60003290",2L);
		Assert.notEmpty(getLocalizationSets, "getLocalizationSets is not Empty");
	}

	@Test
	void getLocalizedFieldsTest() throws Exception {
		final Map<String, String> getLocalizedFields = screenDAO.getLocalizedFields(14L, 14L, "12/14/2021");
		Assert.notEmpty(getLocalizedFields, "getLocalizedFields is not Empty");
	}

	@Test
	void getScreenLookupParameterTest() throws Exception {
		List<Long> liskWorkflowParams = new ArrayList<>();
		liskWorkflowParams.add(1047L);
		liskWorkflowParams.add(1047L);
		liskWorkflowParams.add(1047L);
		liskWorkflowParams.add(1045L);
		final Map<String, String> getScreenLookupParameter = screenDAO.getScreenLookupParameter(liskWorkflowParams,
				"1");
		Assert.notEmpty(getScreenLookupParameter, "getScreenLookupParameter is not Empty");
	}

	@Test
	void getAllLocalesTest() throws Exception {
		final List<Map<String, String>> getAllLocales = screenDAO.getAllLocales(2L, "1", "12/14/2021");
		Assert.notEmpty(getAllLocales, "getAllLocales is not Empty");
	}

	@Test
	void getBLMButtonDetailsTest() throws Exception {
		ArrayList<Long> screenIds = new ArrayList<>();
		screenIds.add(135367L);
		screenIds.add(135444L);
		final Map<String, Map<String, Map<String, String>>> getBLMButtonDetails = screenDAO.getBLMButtonDetails(screenIds, "56 - MISS-TENN", "2469", "12/15/2021");
		Assert.notNull(getBLMButtonDetails, "getBLMButtonDetails is not Empty");
	}

	@Test
	void getBLMLangDetailsTest() throws Exception {
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(56069679L);
		screenInstanceIds.add(56069679L);
		ArrayList<Long> buttonIds = new ArrayList<>();
		buttonIds.add(5976512L);
		buttonIds.add(5976559L);
		final Map<String, Map<String, Map<String, String>>> getBLMLangDetails = screenDAO.getBLMLangDetails(buttonIds,
				screenInstanceIds);
		Assert.notNull(getBLMLangDetails, "getBLMLangDetails is not Empty");
	}

	@Test
	void getButtonLangDetailsTest() throws Exception {
		ArrayList<Long> screenIds = new ArrayList<>();
		screenIds.add(117108L);
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(54821163L);
		ArrayList<Long> langIds = new ArrayList<>();
		langIds.add(1L);
		String restaurantDefaultLang = "1";
		final Map<String, Map<String, Map<String, String>>> getButtonLangDetails = screenDAO
				.getButtonLangDetails(screenIds, screenInstanceIds, langIds, restaurantDefaultLang);
		Assert.notEmpty(getButtonLangDetails, "getButtonLangDetails is not Empty");
	}

	@Test
	void getButtonWorkflowParametersTest() throws Exception {
		ArrayList<Long> buttonWorkflowAssignIds = new ArrayList<>();
		buttonWorkflowAssignIds.add(188955261L);
		final Map<String, List<Map<String, String>>> getButtonWorkflowParameters = screenDAO
				.getButtonWorkflowParameters(buttonWorkflowAssignIds);
		Assert.notEmpty(getButtonWorkflowParameters, "getButtonWorkflowParameters is not Empty");
	}

	@Test
	void getScreenWorkflowParametersTest() throws Exception {
		ArrayList<Long> screenWorkflowAssignId = new ArrayList<>();
		screenWorkflowAssignId.add(1495451L);
		final Map<String, List<Map<String, String>>> getScreenWorkflowParameters = screenDAO
				.getScreenWorkflowParameters(screenWorkflowAssignId);
		Assert.notEmpty(getScreenWorkflowParameters, "getScreenWorkflowParameters is not Empty");
	}

	@Test
	void getScreenWorkflowsTest() throws Exception {
		ArrayList<Long> screenIds = new ArrayList<>();
		screenIds.add(117108L);
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(54821163L);
		final Map<String, List<Map<String, String>>> getScreenWorkflows = screenDAO.getScreenWorkflows(screenIds,
				screenInstanceIds);
		Assert.notEmpty(getScreenWorkflows, "getScreenWorkflows is not Empty");
	}

	@Test
	void getDefaultButtonCaptionTest() throws Exception {
		final String getDefaultButtonCaption = screenDAO.getDefaultButtonCaption(2L);
		Assert.hasText(getDefaultButtonCaption, "getDefaultButtonCaption is not Empty");
	}

	@Test
	void getDefaultLocaleIdTest() throws Exception {
		final String getDefaultLocaleId = screenDAO.getDefaultLocaleId(2L);
		Assert.hasText(getDefaultLocaleId, "getDefaultLocaleId is not Empty");
	}

	@Test
	void getDynamicButtonDetailsTest() throws Exception {
		ArrayList<Long> screenIds = new ArrayList<>();
		screenIds.add(117108L);
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(54821163L);
		final Map<String, Map<String, Map<String, String>>> getDynamicButtonDetails = screenDAO
				.getDynamicButtonDetails(screenIds, screenInstanceIds, new HashedMap<>(), "12/14/2021");
		Assert.notEmpty(getDynamicButtonDetails, "getDynamicButtonDetails is not Empty");
	}

	@Test
	void getMasterButtonDetailsTest() throws Exception {
		ArrayList<Long> screenIds = new ArrayList<>();
		screenIds.add(117108L);
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(54821163L);
		final Map<String, Map<String, Map<String, String>>> getMasterButtonDetails = screenDAO
				.getMasterButtonDetails(screenIds, screenInstanceIds);
		Assert.notEmpty(getMasterButtonDetails, "getMasterButtonDetails is not Empty");
	}

	@Test
	void getHotScreensTest() throws Exception {
		final List<String> getHotScreens = screenDAO.getHotScreens();
		Assert.notEmpty(getHotScreens, "getHotScreens is not Empty");
	}

	@Test
	void getRestaurantMenuItemsTest() throws Exception {
		final List<String> getRestaurantMenuItems = screenDAO.getRestaurantMenuItems(24693062092L, "12/14/2021");
		Assert.notEmpty(getRestaurantMenuItems, "getRestaurantMenuItems is not Empty");
	}

	@Test
	void getScheduleSizeTest() throws Exception {
		final Map<String, Long> getScheduleSize = screenDAO.getScheduleSize("564567", 2L);
		Assert.notEmpty(getScheduleSize, "getScheduleSize is not Empty");
	}

	@Test
	void getScreenDetailsTest() throws Exception {
		final Map<String, Map<String, String>> getScreenDetails = screenDAO.getScreenDetails(2078835L, "12/14/2021");
		Assert.notNull(getScreenDetails, "getScreenDetails is not Empty");
	}

	@Test
	void getButtonWorkflowsTest() throws Exception {
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(56069679L);
		screenInstanceIds.add(56069679L);
		ArrayList<Long> buttonIds = new ArrayList<>();
		buttonIds.add(5976512L);
		buttonIds.add(5976559L);
		final Map<String, List<Map<String, String>>> getButtonWorkflows = screenDAO.getButtonWorkflows(buttonIds,
				screenInstanceIds);
		Assert.notNull(getButtonWorkflows, "getButtonWorkflows is not Empty");
	}

	@Test
	void getDynamicWorkflowParametersTest() throws Exception {
		final List<String> getDynamicWorkflowParameters = screenDAO.getDynamicWorkflowParameters(2L, "WF_DoSale");
		Assert.notNull(getDynamicWorkflowParameters, "getDynamicWorkflowParameters is not Empty");
	}
	@Test
	void getButtonWorkflowParametersTest2() throws Exception {
		ArrayList<Long> buttonWorkflowAssignIds = new ArrayList<>();
		final Map<String, List<Map<String, String>>> getButtonWorkflowParameters = screenDAO.getButtonWorkflowParameters(buttonWorkflowAssignIds);
		Assert.notNull(getButtonWorkflowParameters, "getButtonWorkflowParameters is not Empty");
	}
	@Test
	void getScreenWorkflowParametersTest2() throws Exception {
		ArrayList<Long> screenWorkflowAssignId = new ArrayList<>();
		final Map<String, List<Map<String, String>>> getScreenWorkflowParameters = screenDAO.getScreenWorkflowParameters(screenWorkflowAssignId);
		Assert.notNull(getScreenWorkflowParameters, "getScreenWorkflowParameters is not Empty");
	}
	@Test
	void getScreenLookupParameterTest2() throws Exception {
		List<Long> liskWorkflowParams = new ArrayList<>();
		final Map<String, String> getScreenLookupParameter = screenDAO.getScreenLookupParameter(liskWorkflowParams, "1");
		Assert.notNull(getScreenLookupParameter, "getScreenLookupParameter is not Empty");
	}
	@Test
	void getButtonWorkflowsTest2() throws Exception {
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(56069679L);
		screenInstanceIds.add(56069679L);
		ArrayList<Long> buttonIds = new ArrayList<>();
		final Map<String, List<Map<String, String>>> getButtonWorkflows = screenDAO.getButtonWorkflows(buttonIds, screenInstanceIds);
		Assert.notNull(getButtonWorkflows, "getButtonWorkflows is not Empty");
	}
	@Test
	void getButtonProductCodeTest2() throws Exception {
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(56069679L);
		screenInstanceIds.add(56069679L);
		ArrayList<Long> buttonIds = new ArrayList<>();
		final Map<String, String> getButtonProductCode = screenDAO.getButtonProductCode(buttonIds, screenInstanceIds);
		Assert.notNull(getButtonProductCode, "getButtonProductCode is not Empty");
	}
	@Test
	void getBLMLangDetailsTest2() throws Exception {
		ArrayList<Long> screenInstanceIds = new ArrayList<>();
		screenInstanceIds.add(56069679L);
		screenInstanceIds.add(56069679L);
		ArrayList<Long> buttonIds = new ArrayList<>();
		final Map<String, Map<String, Map<String, String>>> getBLMLangDetails = screenDAO.getBLMLangDetails(buttonIds,
				screenInstanceIds);
		Assert.notNull(getBLMLangDetails, "getBLMLangDetails is not Empty");
	}

}
