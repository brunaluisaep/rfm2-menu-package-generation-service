package com.rfm.packagegeneration.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfm.packagegeneration.cache.CacheService;
import com.rfm.packagegeneration.constants.GeneratorConstant;
import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.dao.ProductDBDAO;
import com.rfm.packagegeneration.dao.PromotionDBDAO;
import com.rfm.packagegeneration.dto.GttPriority;
import com.rfm.packagegeneration.dto.GttPromoParentNodes;
import com.rfm.packagegeneration.dto.GttPromoPriority;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.PromoGtt;
import com.rfm.packagegeneration.dto.PromotionData;
import com.rfm.packagegeneration.dto.ResponseMicroServiceDTO;
import com.rfm.packagegeneration.dto.SuggestivePromotion;
import com.rfm.packagegeneration.utility.PackageGenerationUtility;
import com.rfm.packagegeneration.utility.PackageWriter;


@Service
public class PromotionDBService {
	
	@Autowired
	ProductDBDAO productDBDAO;
	
	@Autowired
	ScreenService screenService;

	@Autowired
	PromotionDBDAO promotionDBDAO;

	@Autowired
	CacheService cacheService;
	
	private static final Logger LOGGER = LogManager.getLogger("PromotionDBService");

	public ResponseMicroServiceDTO generateFileString(PackageGeneratorDTO packageGeneratorDTO) throws Exception {
		ResponseMicroServiceDTO genDto = new ResponseMicroServiceDTO();
		if (!PackageGenerationUtility.getInstance().shouldFileBeCreated(GeneratorConstant.FILETYPE_PROMOTION_DB_XML,
				packageGeneratorDTO)) {
			genDto.setGenerated(false);
			return genDto;
		}

		List<PromotionData> promotionDataList=getPromotionData(packageGeneratorDTO.getNodeID(),packageGeneratorDTO.getMarketID(),
				packageGeneratorDTO.getDate(),packageGeneratorDTO.getScheduleRequestID());
		//if there is no promotion to the markert, the file will not be generated
		if (promotionDataList == null) {
			genDto.setGenerated(true);
			return genDto;
		}
		PackageWriter bufferWriter = new PackageWriter(GeneratorConstant.PROMOTION_DB_XML_FILENAME,
				GeneratorConstant.PROMOTION_DB_XML, packageGeneratorDTO, GeneratorConstant.PROMOTION_SCHEMA_TYPE_NAMES);

		breakline(bufferWriter);
		bufferWriter.append("<PromotionDB version = \"1.0\" xmlns:xsi = \"http://www.w3.org/2001/XMLSchema-instance\">");
		breakline(bufferWriter);
		String sequenceNumber = productDBDAO.getValuesFromGlobalParam(packageGeneratorDTO.getMarketID(), ProductDBConstant.CONSTANT_ENABLE_SEAMLESS );
		if(sequenceNumber.equals("true")) {
		if (packageGeneratorDTO.isGeneratedSeqNum() && packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO() != null
				&& !packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO().isEmpty()) {
			bufferWriter.append("<PromotionDBSeqNumber>" + packageGeneratorDTO.getPackageStatusDTO().getSEQ_NO()+ "</PromotionDBSeqNumber>");
			breakline(bufferWriter);
			}
		}

		Long promoPriority=0L;
		
		if(promotionDataList!=null && promotionDataList.size()>0) {
			for(PromotionData promotionData:promotionDataList) {
				++promoPriority;
				if(!promotionData.getImage().isEmpty()) {
					String imagePath=promotionData.getImage();
					imagePath=Arrays.asList(imagePath).stream().map(word->new StringBuilder(word).reverse()).collect(Collectors.joining(" "));
					promotionData.setImage(new StringBuilder(imagePath.substring(0,imagePath.indexOf('/'))).reverse().toString()); 
				}
				bufferWriter.append("<PromotionData code="+"\""+promotionData.getCode()+"\"  instanceID=\""+promotionData.getInstanceID()+"\" "
						+ "priority="+"\""+ calculatePriority(promoPriority)+"\" status="+"\""+promotionData.getStatus()+"\" template="+"\""+promotionData.getTemplate()+"\" "
								+ "templateId="+"\""+promotionData.getTemplateId()+"\" barcode="+"\""+promotionData.getBarcode()+"\" "
										+ "image="+"\""+promotionData.getImage()+"\">");breakline(bufferWriter);
				bufferWriter.append(promotionData.getLanguage());breakline(bufferWriter);
				bufferWriter.append(promotionData.getPromotion());breakline(bufferWriter);
				bufferWriter.append("</PromotionData>");breakline(bufferWriter);
			}
		}

		bufferWriter.append("</PromotionDB>");
		LOGGER.info("End of PromotionDB XML writing");
		bufferWriter.close();
		genDto.setGenerated(true);
		return genDto;
	}	
	
	private String calculatePriority(Long priority) {
		return String.valueOf(priority*1000);
	}

	private List<PromotionData> getPromotionData(Long nodeId,Long marketId,String effectiveDate,String scheduleRequestID) throws Exception {
		
		String defaultMktLocale = screenService.getDefaultLocaleId(scheduleRequestID, marketId);
		
		String exportOnlyActivePromotions=promotionDBDAO.getExportOnlyActivePromotionsFlag(marketId);

		List<GttPriority> gttPriority = getPriorityData(nodeId, marketId, effectiveDate, defaultMktLocale, 1L);
		
		List<SuggestivePromotion> suggestivePromotions = promotionDBDAO.getSusgtvPromos(marketId);

		//maxPkgPriority
		Long maxPkgPrty = 0L;
		for (GttPriority gttPrty : gttPriority) {
			if(gttPrty.getPkgPrty()> maxPkgPrty) {
				maxPkgPrty = gttPrty.getPkgPrty();
				}
			}
			
		List<GttPriority> sugstvPromos = new ArrayList<>();
		for (SuggestivePromotion sp : suggestivePromotions) { 
			Optional<GttPriority> gttP = gttPriority.stream().filter(gp -> sp.getPromoId().equals(gp.getPromoId())).findFirst();
			if (gttP.isPresent()) {
				maxPkgPrty++;
				sugstvPromos.add(new GttPriority(sp.getSugstvId(), gttP.get().getPkgPrty(),
						gttP.get().getPkgPrty() + maxPkgPrty, sp.getSugstvTyp()));
			}
		}
		
		Comparator<GttPriority> sgpcmp = (a, b) -> a.getPromoTyp().compareTo(b.getPromoTyp());
		Collections.sort(sugstvPromos, sgpcmp);
		
		Comparator<GttPriority> ascGttPriority = (a, b) ->a.getPkgPrty().compareTo(b.getPkgPrty()); 
		
		if (sugstvPromos!=null && sugstvPromos.size()>0) {
			gttPriority.addAll(sugstvPromos);
		}
		 
		Comparator<GttPriority> sort = (a, b) -> { int c =a.getPkgPrty().compareTo(b.getPkgPrty());
		   if (c == 0) { return a.getPromoTyp().compareTo(b.getPromoTyp());
		   }
		  return c; };
		Collections.sort(gttPriority, sort);
		
		//promotions at all levels except restaurant level
		List<PromotionData> promos = promotionDBDAO.getPromoGttApplied(nodeId, marketId, effectiveDate,defaultMktLocale, 1L,exportOnlyActivePromotions);
		
		//promotions at rest level
		List<PromotionData> restPromos=promotionDBDAO.populateRestPromotionData(nodeId, marketId, effectiveDate, defaultMktLocale, 1L,exportOnlyActivePromotions);
		
		//adding rest level data
		if (restPromos!=null && restPromos.size()>0) {
			promos.addAll(restPromos);
		}
			
		//groupBy with minPriority
		Map<Long, Map<Long,Optional<GttPriority>>> minMap=gttPriority.stream().collect(Collectors.groupingBy(GttPriority::getPromoId, Collectors.groupingBy(GttPriority::getPromoTyp, Collectors.minBy(ascGttPriority))));
		
		// merge
		if (promos != null && !promos.isEmpty()) {
			promos.forEach(e -> {
				if (e.getPromoId()!=null && e.getPromoTyp()!=null) 
				{
					if (minMap.containsKey(e.getPromoId())) { 
						Map<Long,Optional<GttPriority>> minMapValue=minMap.get(e.getPromoId());
						if (minMapValue.containsKey(e.getPromoTyp())) {
							e.setPriority(minMapValue.get(e.getPromoTyp()).get().getPkgPrty());
						}
					}
				}
			});
		}

		Comparator<PromotionData> sortingPromos = (a, b) ->a.getPriority().compareTo(b.getPriority()); 
		if (promos != null) {
			Collections.sort(promos, sortingPromos);	
		}
		
		return promos;

	}
		
	
	private List<GttPriority> getPriorityData(Long nodeId, Long marketId, String effectiveDate, String defaultMktLocale, long calledFrom) throws Exception 
	{
		List<GttPromoParentNodes> gttPromoParentNodes=new ArrayList<>();
		List<GttPromoPriority> gttPromoPriority=new ArrayList<>();
		List<PromoGtt> promoGtt=new ArrayList<>();
		List<GttPriority> gttPriority=new ArrayList<>();
		
		//added flags for getting which query is used at runtime
		boolean gPP=false;
		boolean gPPU=false;
		boolean gPPNS=false;
		boolean gPPNHq=false;
		
		
		String nodeLvl=promotionDBDAO.getNodeType(nodeId, marketId);
		
		if ("S".equals(nodeLvl)) {
			gPPNS=true;
			gttPromoParentNodes=promotionDBDAO.getGttPromoParentNodes(marketId, nodeId,"S");
		} else {
			gPPNHq=true;
			gttPromoParentNodes=promotionDBDAO.getGttPromoParentNodes(marketId, nodeId,"Hq");
		}
		String gppnQuery="";
		if (gPPNHq) {
			gppnQuery="hQuery";
		}
		if (gPPNS) {
			gppnQuery="sQuery";
		}
		String gppnQueryUsed=getGttPromoParentQuery(gppnQuery);
		
		Long varPrioritizedNode=0L;
		
		varPrioritizedNode=promotionDBDAO.getPrioritizedNodeCount(nodeId,marketId);
		
		Long maxInhOrd=promotionDBDAO.getMaxInhOrd(nodeId, marketId, gppnQueryUsed);
		
		if (varPrioritizedNode!=0 || String.valueOf('S').equals(nodeLvl.toString())) {
			gPP=true;
			gttPromoPriority=promotionDBDAO.getGttPromoPriority(marketId,nodeId,maxInhOrd,gppnQueryUsed,"n");
		} else {
			while (maxInhOrd != 0) 
			{	
				//as maxInhOrd value is required to calculate prioritizeNode which gets changed in rare cases
				varPrioritizedNode=promotionDBDAO.getPrioritizedNode(nodeId,marketId,maxInhOrd,gppnQueryUsed);
				if (varPrioritizedNode !=0) {
					gPPU=true;
					gttPromoPriority=promotionDBDAO.getGttPromoPriority(nodeId,marketId,maxInhOrd,gppnQueryUsed,"uw");
					break;
				} else {
					maxInhOrd = maxInhOrd - 1;
				}
			}
		}
		String gppQuery="";
		if (gPP) {
			gppQuery="normal";
		}
		if (gPPU) {
			gppQuery="unionWise";
		}
		String gppQueryUsed=getGttPromoPriorityQuery(gppQuery);
		if (varPrioritizedNode==0) 
		{
			if (calledFrom==0) {
				gttPriority=promotionDBDAO.getGttPriority(nodeId,marketId,effectiveDate,maxInhOrd,gppnQueryUsed,gppQueryUsed,"Odca");
			} else {
				gttPriority=promotionDBDAO.getGttPriority(nodeId,marketId,effectiveDate,maxInhOrd,gppnQueryUsed,gppQueryUsed,"Ia");
			}
		} else {
			
			if (calledFrom==0) {
				gttPriority=promotionDBDAO.getGttPriority(nodeId,marketId,effectiveDate,maxInhOrd,gppnQueryUsed,gppQueryUsed,"Odcb");
			} else {
				gttPriority=promotionDBDAO.getGttPriority(nodeId,marketId,effectiveDate,maxInhOrd,gppnQueryUsed,gppQueryUsed,"Ib");
			}
		}
		return gttPriority;
	}

	private String getGttPromoParentQuery(String gppnQuery) throws Exception {
		return promotionDBDAO.getGttPromoParentQueryUsed(gppnQuery);
	}

	private String getGttPromoPriorityQuery(String selectedQuery) throws Exception {
		return promotionDBDAO.getGttPromoPriorityQueryUsed(selectedQuery);
	}
	
	private void breakline(PackageWriter bufferWriter) throws IOException {
		bufferWriter.append(System.lineSeparator());
	}
}
