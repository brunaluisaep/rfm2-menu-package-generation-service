package com.rfm.packagegeneration.cache.redis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.Pool;
import com.rfm.packagegeneration.cache.CacheService;
import com.rfm.packagegeneration.dto.AssociatedCategories;
import com.rfm.packagegeneration.dto.AssociatedPromoProducts;
import com.rfm.packagegeneration.dto.BaseDTO;
import com.rfm.packagegeneration.dto.BunBufferDetails;
import com.rfm.packagegeneration.dto.BusinessLimits;
import com.rfm.packagegeneration.dto.Category;
import com.rfm.packagegeneration.dto.Code;
import com.rfm.packagegeneration.dto.Component;
import com.rfm.packagegeneration.dto.DayPartSet;
import com.rfm.packagegeneration.dto.Deposit;
import com.rfm.packagegeneration.dto.EffectiveDate;
import com.rfm.packagegeneration.dto.Fee;
import com.rfm.packagegeneration.dto.FlavourSet;
import com.rfm.packagegeneration.dto.GeneratorDefinedValues;
import com.rfm.packagegeneration.dto.GenericEntry;
import com.rfm.packagegeneration.dto.HotBusinessLimit;
import com.rfm.packagegeneration.dto.IngredientGroupDetails;
import com.rfm.packagegeneration.dto.Item;
import com.rfm.packagegeneration.dto.KVSRoutes;
import com.rfm.packagegeneration.dto.LanguageDetails;
import com.rfm.packagegeneration.dto.LayoutBaseDTO;
import com.rfm.packagegeneration.dto.LayoutSearchDTO;
import com.rfm.packagegeneration.dto.LogStatus;
import com.rfm.packagegeneration.dto.PPG;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PackageMainStoreDBDTO;
import com.rfm.packagegeneration.dto.PackageSmartReminderDTO;
import com.rfm.packagegeneration.dto.PackageStatusDTO;
import com.rfm.packagegeneration.dto.PackageXMLParametersDTO;
import com.rfm.packagegeneration.dto.Parameter;
import com.rfm.packagegeneration.dto.PopulateDrinkVol;
import com.rfm.packagegeneration.dto.PriceList;
import com.rfm.packagegeneration.dto.PriceTag;
import com.rfm.packagegeneration.dto.PriceTax;
import com.rfm.packagegeneration.dto.Pricing;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductAbsSettings;
import com.rfm.packagegeneration.dto.ProductCCMSettings;
import com.rfm.packagegeneration.dto.ProductDBRequest;
import com.rfm.packagegeneration.dto.ProductDetails;
import com.rfm.packagegeneration.dto.ProductGeneralSettingMenuItemNames;
import com.rfm.packagegeneration.dto.ProductGroup;
import com.rfm.packagegeneration.dto.ProductList;
import com.rfm.packagegeneration.dto.ProductPosKvs;
import com.rfm.packagegeneration.dto.ProductPresentation;
import com.rfm.packagegeneration.dto.ProductPromotionRange;
import com.rfm.packagegeneration.dto.ProductShortCutSettings;
import com.rfm.packagegeneration.dto.ProductSmartRouting;
import com.rfm.packagegeneration.dto.ProductTags;
import com.rfm.packagegeneration.dto.Production;
import com.rfm.packagegeneration.dto.PromotionGroup;
import com.rfm.packagegeneration.dto.PromotionGroupDetail;
import com.rfm.packagegeneration.dto.PromotionGroups;
import com.rfm.packagegeneration.dto.PromotionImages;
import com.rfm.packagegeneration.dto.Reduction;
import com.rfm.packagegeneration.dto.RequestDTO;
import com.rfm.packagegeneration.dto.ResponseMicroServiceDTO;
import com.rfm.packagegeneration.dto.Restaurant;
import com.rfm.packagegeneration.dto.Route;
import com.rfm.packagegeneration.dto.ScreenDTO;
import com.rfm.packagegeneration.dto.ScreenDetails;
import com.rfm.packagegeneration.dto.ScreenRequest;
import com.rfm.packagegeneration.dto.Set;
import com.rfm.packagegeneration.dto.SetIds;
import com.rfm.packagegeneration.dto.ShortCutDetails;
import com.rfm.packagegeneration.dto.Size;
import com.rfm.packagegeneration.dto.SizeSelection;
import com.rfm.packagegeneration.dto.SmartRoutingTask;
import com.rfm.packagegeneration.dto.StoreDB;
import com.rfm.packagegeneration.dto.StoreDBRequest;
import com.rfm.packagegeneration.dto.StoreDetails;
import com.rfm.packagegeneration.dto.StoreHours;
import com.rfm.packagegeneration.dto.Tax;
import com.rfm.packagegeneration.dto.TimeFrames;
import com.rfm.packagegeneration.dto.WeekDays;
import com.rfm.packagegeneration.dto.WorkflowDTO;
import com.rfm.packagegeneration.dto.WorkflowParam;
import com.rfm.packagegeneration.utility.Pair;

@Configuration
@ConditionalOnProperty(name = "application.redis.enabled", havingValue = "true")
public class RedisCacheConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheConfig.class);
	
	@Value("${application.redis.server}")
    private String redisHostName;

    @Value("${application.redis.port}")
    private int redisPort;

    @Value("${application.redis.password}")
    private String redisAuthToken;
    
    
    @Value("${spring.profiles.active}")
    private String activeProfile;
    

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
       
        if (activeProfile.equals("local")) {
            return localJedisConnectionFactory();
        }

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.clusterNode(redisHostName, redisPort);
        redisClusterConfiguration.setPassword(redisAuthToken);

        LOGGER.info("Setting up AWS Redis Cache at {}:{}", redisHostName, redisPort);

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
                .useSsl()
                .build();

        return new JedisConnectionFactory(redisClusterConfiguration, jedisClientConfiguration);
    }

    public JedisConnectionFactory localJedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHostName);
        redisStandaloneConfiguration.setPort(redisPort);
        LOGGER.info("Setting up local Redis Cache at {}:{}", redisHostName, redisPort);

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setDefaultSerializer(new KryoRedisSeralizer(kryoPool()));
        template.setKeySerializer(StringRedisSerializer.UTF_8);
        return template;
    }
    
    @Bean
    public Pool<Kryo> kryoPool() {
    	return new Pool<Kryo>(true, true) {
    		
			@Override
			protected Kryo create() {
				Kryo k = new Kryo();

				k.register(LinkedHashMap.class);
				k.register(HashMap.class);
				k.register(ArrayList.class);
				k.register(LocalDate.class);
				k.register(org.springframework.util.LinkedCaseInsensitiveMap.class);
				k.register(Pair.class);
				k.register(AssociatedCategories.class);
				k.register(AssociatedPromoProducts.class);
				k.register(BaseDTO.class);
				k.register(BunBufferDetails.class);
				k.register(BusinessLimits.class);
				k.register(Category.class);
				k.register(Code.class);
				k.register(Component.class);
				k.register(DayPartSet.class);
				k.register(Deposit.class);
				k.register(EffectiveDate.class);
				k.register(Fee.class);
				k.register(FlavourSet.class);
				k.register(GeneratorDefinedValues.class);
				k.register(GenericEntry.class);
				k.register(HotBusinessLimit.class);
				k.register(IngredientGroupDetails.class);
				k.register(Item.class);
				k.register(KVSRoutes.class);
				k.register(LayoutBaseDTO.class);
				k.register(LayoutSearchDTO.class);
				k.register(LogStatus.class);
				k.register(PackageGeneratorDTO.class);
				k.register(PackageMainStoreDBDTO.class);
				k.register(PackageSmartReminderDTO.class);
				k.register(PackageStatusDTO.class);
				k.register(PackageXMLParametersDTO.class);
				k.register(Parameter.class);
				k.register(PopulateDrinkVol.class);
				k.register(PPG.class);
				k.register(PriceList.class);
				k.register(PriceTag.class);
				k.register(PriceTax.class);
				k.register(Pricing.class);
				k.register(Product.class);
				k.register(ProductAbsSettings.class);
				k.register(ProductCCMSettings.class);
				k.register(ProductDBRequest.class);
				k.register(ProductDetails.class);
				k.register(ProductGeneralSettingMenuItemNames.class);
				k.register(ProductGroup.class);
				k.register(Production.class);
				k.register(ProductList.class);
				k.register(ProductPosKvs.class);
				k.register(ProductPresentation.class);
				k.register(ProductPromotionRange.class);
				k.register(ProductShortCutSettings.class);
				k.register(ProductSmartRouting.class);
				k.register(ProductTags.class);
				k.register(PromotionGroup.class);
				k.register(PromotionGroups.class);
				k.register(PromotionImages.class);
				k.register(Reduction.class);
				k.register(RequestDTO.class);
				k.register(ResponseMicroServiceDTO.class);
				k.register(Restaurant.class);
				k.register(Route.class);
				k.register(ScreenDetails.class);
				k.register(ScreenDTO.class);
				k.register(ScreenRequest.class);
				k.register(Set.class);
				k.register(SetIds.class);
				k.register(ShortCutDetails.class);
				k.register(Size.class);
				k.register(SmartRoutingTask.class);
				k.register(StoreDB.class);
				k.register(StoreDBRequest.class);
				k.register(StoreDetails.class);
				k.register(StoreHours.class);
				k.register(SizeSelection.class);
				k.register(Tax.class);
				k.register(TimeFrames.class);
				k.register(WeekDays.class);
				k.register(WorkflowDTO.class);
				k.register(WorkflowParam.class);
				k.register(java.math.BigDecimal.class);
				 k.register(ArrayList.class);
			     k.register(java.util.Arrays.asList().getClass());
				k.register(PromotionGroupDetail.class);
				k.register(LanguageDetails.class);
				
				return k;
			}
    		
		};
    }
    
    @Bean
    public CacheService cacheService() {
    
       return new RedisCacheService(redisTemplate());
    }
    
}
