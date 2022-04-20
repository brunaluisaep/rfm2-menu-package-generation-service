package com.rfm.packagegeneration.dto;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;

import com.rfm.packagegeneration.exception.ExceptionResponse;

import pl.pojo.tester.api.assertion.Method;
class GenericPojoCommonTest {
	@Test
	void menuItemComponent_Test() throws Exception {
		assertPojoMethodsFor(GeneratorDefinedValues.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(PackageGeneratorDTO.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(PackageSmartReminderDTO.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(PackageStatusDTO.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(PackageXMLParametersDTO.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
			
		assertPojoMethodsFor(Product.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Size.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(ProductDBRequest.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Category.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Code.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(GenericEntry.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Item.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(ProductList.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(PromotionGroup.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(PromotionGroups.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(RequestDTO.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Set.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Restaurant.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Route.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(KVSRoutes.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(PriceList.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(PriceTag.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Pricing.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		assertPojoMethodsFor(Tax.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
		
		
		
	}
}
