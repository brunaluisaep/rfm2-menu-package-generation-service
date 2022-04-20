package com.rfm.packagegeneration.repository;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.rfm.packagegeneration.constants.GeneratorConstant;
import com.rfm.packagegeneration.dao.LayeringProductDBDAO;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.Set;

@RunWith(SpringRunner.class)
@SpringBootTest

class LayeringProductDBDAOTest {
	
	@Autowired
	LayeringProductDBDAO layeringProductDBDAO;
	@Test
	void getRestaurantSetsTest_MenuItemSet() throws Exception {
		final List<Set> sets = layeringProductDBDAO.getRestaurantSets(2, 15265, "10/10/2021", GeneratorConstant.TYPE_MENU_ITEM_SET);
		Assert.notEmpty(sets, "Menu/Price sets are not Empty");
	}
	
	@Test
	void getRestaurantSetsTest_PriceSet() throws Exception {
		final List<Set> sets = layeringProductDBDAO.getRestaurantSets(2, 15265, "10/10/2021", GeneratorConstant.TYPE_PRICE_SET);
		Assert.notEmpty(sets, "Menu/Price sets are not Empty");
	}
	
	@Test
	void getProductPosKvsPresentationByMasterTest() throws Exception {
		final Map<Long, Product> masterObject = layeringProductDBDAO.getProductPosKvsPresentationByMaster(2, "10/10/2021", 7);
		Assert.notEmpty(masterObject, "Master object is not Empty");
		final Map<Long, Product> setObject = layeringProductDBDAO.getProductPosKvsPresentationBySet(masterObject, 2, "10/10/2021", 3001, 29);
		Assert.notEmpty(setObject, "Set object is not Empty");
	}
	
	

}
