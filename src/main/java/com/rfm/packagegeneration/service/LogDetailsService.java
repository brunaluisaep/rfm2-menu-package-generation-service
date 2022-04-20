package com.rfm.packagegeneration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfm.packagegeneration.constants.ProductDBConstant;
import com.rfm.packagegeneration.dao.LayeringProductDBDAO;
import com.rfm.packagegeneration.dao.ProductDBDAO;
import com.rfm.packagegeneration.dto.Component;
import com.rfm.packagegeneration.dto.Item;
import com.rfm.packagegeneration.dto.LogStatus;
import com.rfm.packagegeneration.dto.PackageGeneratorDTO;
import com.rfm.packagegeneration.dto.Product;
import com.rfm.packagegeneration.dto.ProductDBRequest;


/**
 * @author mc95692
 *
 */
@Service
public class LogDetailsService {
	private static final String NOT_AVAILABLE_IN_PRODUCT_DB_XML_EXPORT = " is not available in product-db.xml export.";
	private static final String CHECK_CATEGORY_MI = "Check Category Menu Item ";
	private static final String NO_ACTIVE_COMPONENT_MI =  " has no active component menu Item. ";
	private static final Logger LOGGER = LogManager.getLogger("LogDetailsService");	

	@Autowired
	LayeringProductService layeringProductService;
	@Autowired
	LayeringProductDBDAO layeringDBDAO;
	@Autowired
	ProductDBDAO productDBDAO;
	/**
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<LogStatus> getLogDetailsProductDB(final Map<Long, Product> allProducts,PackageGeneratorDTO dto) throws Exception {
		if(LOGGER.isInfoEnabled()) LOGGER.info("getLogDetailsProductDB: start");
				
		Long layeringLogicTyp = Long.parseLong(productDBDAO.getValuesFromGlobalParam(dto.getMarketID(), ProductDBConstant.MENU_ITEM_LAYERING_LOGIC_TYPE));
		List<LogStatus> logList=new ArrayList<>();
		ProductDBRequest productDBReq=new ProductDBRequest();
		productDBReq.setEffectiveDate(dto.getDate());
		productDBReq.setMktId(dto.getMarketID());
		productDBReq.setNodeId(dto.getNodeID());		
		List<LogStatus> logChoiceList=getChoiceCategoryLogDetailsTyp1(layeringLogicTyp,allProducts,dto.getDate());
		List<LogStatus> logEVMContainerList=getEVMContainerLogDetailsTyp1(layeringLogicTyp,allProducts,dto.getDate());
		List<LogStatus> logChoiceCatListType2=getChoiceCategoryLogDetailsTyp2( layeringLogicTyp,allProducts,dto.getDate());
		List<LogStatus> exportWquivalent=getExportEquivalent(allProducts,dto.getDate());
		List<LogStatus>exporPriceList=getExportLogAuXPrice(allProducts,productDBReq);
		List<LogStatus>exportStatusLogList=calculateSubsGroupStatusForAuxMenuItem(allProducts, productDBReq);
		logList.addAll(logChoiceList);
		logList.addAll(logEVMContainerList);
		logList.addAll(logChoiceCatListType2);
		logList.addAll(exportWquivalent);
		logList.addAll(exporPriceList);
		logList.addAll(exportStatusLogList);
		return logList; 
	}

	/**
	 * @param layering
	 * @param productListStatus
	 * @param productgeneralList
	 * @param productListcopment
	 * @param effDate
	 * @return
	 */
	private List<LogStatus> getChoiceCategoryLogDetailsTyp1(final Long layering,Map<Long, Product> allProducts ,String effDate){
		if(LOGGER.isInfoEnabled()) LOGGER.info("getChoiceCategoryLogDetailsTyp1: start");
		List<LogStatus> logList=new ArrayList<>();
		for(Map.Entry<Long, Product> entry : allProducts.entrySet()){
			if(layering==1L && entry!=null && entry.getValue().getActive()==1 ) {
				Product product=allProducts.get(entry.getKey());
				if(null!=product) {
					Long prdCd=product.getProductCode();
					if(product.getProductClass() != null && product.getProductClass().equals("CHECK CATEGORY")) {						
						List<Component> componentList=product.getComponents();
						int countChoice=0;
						if(componentList!=null) {
						for(Component component:componentList) {
							Product productCompoStatus=allProducts.get(component.getComponentProductId());
							if(null!=productCompoStatus&&productCompoStatus.getActive()==1) {
								countChoice++;
							}

						}}
						if(countChoice==0) {
							LogStatus logStatus=new LogStatus();
							String message="Choice Category Menu Item "+ prdCd+ NO_ACTIVE_COMPONENT_MI
									+ "Choice Category Menu Item " 
									+prdCd +" along with all its component menu Item have been exported as Inactive." ;
							logStatus.setEffectiveDate(effDate);
							logStatus.setInfoType(3L);
							logStatus.setMessage(message);
							logList.add(logStatus);
						}
						
					}else if(product.getProductClass() != null && product.getProductClass().equals("CHOICE")) {
							List<Component> componentList=product.getComponents();
						int countChoice=0;
						if(componentList!=null) {
						for(Component component:componentList) {
							Product productCompoStatus=allProducts.get(component.getComponentProductId());
							if(null!=productCompoStatus&&productCompoStatus.getActive()==1) {
								countChoice++;
							}							

						}
						}
						if(countChoice==0) {
							LogStatus logStatus=new LogStatus();
							String message="Choice  Menu Item "+ prdCd+ NO_ACTIVE_COMPONENT_MI
									+ "Choice  Menu Item " 
									+prdCd +" along with all its component menu Item have been exported as Inactive." ;
							logStatus.setEffectiveDate(effDate);
							logStatus.setInfoType(3L);
							logStatus.setMessage(message);
							logList.add(logStatus);
						}

					}
					}
			}

		}
		return logList;
	}
	private List<LogStatus> getEVMContainerLogDetailsTyp1(Long layering,final Map<Long, Product> allProducts, String effDate){
		if(LOGGER.isInfoEnabled()) LOGGER.info("getEVMContainerLogDetailsTyp1: start");
		List<LogStatus> logList=new ArrayList<>();
		for(Map.Entry<Long, Product> entry : allProducts.entrySet()){
			if(layering==1L && entry!=null && entry.getValue().getActive()==1 ) {
				Product product=allProducts.get(entry.getKey());
				if(null!=product) {
					Long prdCd=product.getProductCode();
					if(product.getProductClass().equals("VALUE_MEAL")) {
						List<Component> componentList=product.getComponents();
						int evmContainer=0;
						if (componentList != null && !componentList.isEmpty()) {
							for(Component component:componentList) {
								Product productCompoStatus=allProducts.get(component.getComponentProductId());
								if(null!=productCompoStatus&&productCompoStatus.getActive()==0) {
									evmContainer++;
								}
	
							}
						}
						if(evmContainer>0) {
							LogStatus logst=new LogStatus();
							String message=effDate+"#3#"+"Menu Item"+ prdCd +" has a component menu Item which is unapproved." ;
							logst.setEffectiveDate(effDate);
							logst.setInfoType(3L);
							logst.setMessage(message);
							logList.add(logst);
						}
					}
				}}

		}
		return logList;
	}
	/**
	 * @param layering
	 * @param productListStatus
	 * @param productgeneralList
	 * @param productListcopment
	 * @param effDate
	 * @return
	 */
	private List<LogStatus> getChoiceCategoryLogDetailsTyp2(Long layering,final Map<Long,Product> allProducts, String effDate){
		if(LOGGER.isInfoEnabled()) LOGGER.info("getChoiceCategoryLogDetailsTyp2: start");
		List<LogStatus> logList=new ArrayList<>();
		for(Map.Entry<Long, Product> entry : allProducts.entrySet()){
			if(layering==2L && entry!=null && entry.getValue().getActive()==1 ) {
				Product product=allProducts.get(entry.getKey());
				if(product != null) {
					Long prdCd=product.getProductCode();
					if(product.getProductClass() != null  && product.getProductClass().equals("CHECK CATEGORY")) {
						List<Component> componentList=product.getComponents();
						int countChoice=0;
						if(componentList != null) {
							for(Component component:componentList) {
								Product productCompoStatus=allProducts.get(component.getComponentProductId());
								if(productCompoStatus != null && productCompoStatus.getActive()==1) {
									countChoice++;
								}

							}
						}
						if(countChoice==0) {
							LogStatus logst=new LogStatus();
							String message= CHECK_CATEGORY_MI + prdCd + NO_ACTIVE_COMPONENT_MI
									+ CHECK_CATEGORY_MI + prdCd +" along with all its component menu Item have been exported as Active." ;
							logst.setEffectiveDate(effDate);
							logst.setInfoType(3L);
							logst.setMessage(message);
							logList.add(logst);
						}
					}else if(product.getProductClass() != null && product.getProductClass().equals("CHOICE")){
						int countChoice=0;
						List<Component> componentList=product.getComponents();
						if(componentList != null) {

							for(Component component:componentList) {
								Product productCompoStatus=allProducts.get(component.getComponentProductId());
								if(productCompoStatus != null && productCompoStatus.getActive()==1) {
									countChoice++;
								}

							}
						}
						
						if(countChoice==0) {
							String message= CHECK_CATEGORY_MI + prdCd + NO_ACTIVE_COMPONENT_MI
									+ CHECK_CATEGORY_MI + prdCd +" along with all its component menu Item have been exported as Active." ;
							LogStatus logst=new LogStatus();
							logst.setEffectiveDate(effDate);
							logst.setInfoType(3L);
							logst.setMessage(message);
							logList.add(logst);
						}
					}
				}
				
			}

		}
		return logList;
	}
	private List<LogStatus> getExportEquivalent(final Map<Long,Product> allProducts, String effDate){
		if(LOGGER.isInfoEnabled()) LOGGER.info("getExportEquivalent");

		Long prdEqId = null;
		Long prdEqCd= null;
		Long prdDisplayId=null;
		Long prdDisplayCd=null;
		Long prdTargetId=null;
		Long prdTargetCd = null;
		List<LogStatus> logList=new ArrayList<>();
		for(Map.Entry<Long, Product> entry : allProducts.entrySet()){
			if( entry!=null && entry.getValue().getApprovalStatus()==1 ) {
				Product product=allProducts.get(entry.getKey());
				if(null!=product) {
					if(null!=product.getProductPosKvs()) {
					if(null!=product.getProductPosKvs().getEquivalent())prdEqId=product.getProductPosKvs().getEquivalent().getCode();
					if(null!=product.getProductPosKvs().getEquivalentAsID()) prdEqCd=product.getProductPosKvs().getEquivalentAsID().getCode();
					if(null!=product.getProductPosKvs().getDisplayAsID()) prdDisplayId=product.getProductPosKvs().getDisplayAsID().getCode();
					if(null!=product.getProductPosKvs().getDisplayAs()) prdDisplayCd=product.getProductPosKvs().getDisplayAs().getCode();
					if(null!=product.getProductPosKvs().getTargetSubstitutionItem()) prdTargetId=product.getProductPosKvs().getTargetSubstitutionItem().getCode();
					if(null!=product.getProductPosKvs().getTargetSubstitutionItemCD()) prdTargetCd=product.getProductPosKvs().getTargetSubstitutionItemCD().getCode();}
					if(prdEqId!=null &&prdEqId!=0L) {
						if(!allProducts.containsKey(prdEqId)) { 
								String message="Equivalent Product "+ prdEqCd + NOT_AVAILABLE_IN_PRODUCT_DB_XML_EXPORT;
								LogStatus logst=new LogStatus();
								logst.setEffectiveDate(effDate);
								logst.setInfoType(3L);
								logst.setMessage(message);
								logList.add(logst);
								
							
						}
					}
					if(prdDisplayId!=null &&prdDisplayId!=0L) {
						if(!allProducts.containsKey(prdDisplayId)) {							
								String message="Display As Product "+ prdDisplayCd + NOT_AVAILABLE_IN_PRODUCT_DB_XML_EXPORT;
								LogStatus logst=new LogStatus();
								logst.setEffectiveDate(effDate);
								logst.setInfoType(3L);
								logst.setMessage(message);
								logList.add(logst);
						
						}
					}
					if(prdTargetId!=null &&prdTargetId!=0L) {
						if(!allProducts.containsKey(prdTargetId)) {
								String message="Target Substitution "+ prdTargetCd + NOT_AVAILABLE_IN_PRODUCT_DB_XML_EXPORT;
								LogStatus logst=new LogStatus();
								logst.setEffectiveDate(effDate);
								logst.setInfoType(3L);
								logst.setMessage(message);
								logList.add(logst);
							}
					}
				}
			}	
		}
		return logList;
	}
	
	private List<LogStatus> getExportLogAuXPrice(final Map<Long,Product> allProduct, ProductDBRequest productDBRequest){
		if(LOGGER.isInfoEnabled()) LOGGER.info("getExportLogAuXPrice");
		List<LogStatus> logList=new ArrayList<>();
		
		for(Map.Entry<Long, Product> entry : allProduct.entrySet()){
			if( entry!=null && entry.getValue().getApprovalStatus()==1 ) {
				Product product=allProduct.get(entry.getKey());
				if(null!=product && null!=product.getAuxiliaryMenuItem() && product.getAuxiliaryMenuItem()==1L) {
					final List<Item> substitutionList= product.getSubstitutionList();
					int countPriceSubtitution=0;
					int counTaxSubtitution=0;
					String  subGrpNa="";
					if (!substitutionList.isEmpty()) {
						for (Item item : substitutionList) {
							// to add auxmenuitem check
							if (!(item.getGroupId().equals("-1"))) {
								subGrpNa=item.getGroupName();							
								countPriceSubtitution=product.getCountPriceSubtitution();
								counTaxSubtitution=product.getCounTaxSubtitution();								
							}
						}	
						if(countPriceSubtitution==0||countPriceSubtitution>1) {
							String messagePrice="The price of auxiliary item "
									+ "" +product.getProductCode() +" will be assumed as zero based on Substitution List "+subGrpNa ;
							LogStatus logst=new LogStatus();
							logst.setEffectiveDate(productDBRequest.getEffectiveDate());
							logst.setInfoType(1L);
							logst.setMessage(messagePrice);
							logList.add(logst);
						}
						if(counTaxSubtitution==0) {
							String messagTax="The tax of auxiliary item "
									+ "" +product.getProductCode() +" will be assumed as zero based on Substitution List "+subGrpNa ;
							LogStatus logst=new LogStatus();
							logst.setEffectiveDate(productDBRequest.getEffectiveDate());
							logst.setInfoType(1L);
							logst.setMessage(messagTax);
							logList.add(logst);
						}
					}else {
						String message="The price of auxiliary item"
								+ "" +product.getProductCode() +"will be assumed as zero as menu item does not belong to Substitution List. " ;
						String messagetax="#1#"+"The tax of auxiliary item"
								+ "" +product.getProductCode() +"will be assumed as zero as menu item does not belong to Substitution List. " ;
						LogStatus logst=new LogStatus();
						logst.setEffectiveDate(productDBRequest.getEffectiveDate());
						logst.setInfoType(1L);
						logst.setMessage(message);
						logList.add(logst);
						LogStatus logstTax=new LogStatus();
						logstTax.setEffectiveDate(productDBRequest.getEffectiveDate());
						logstTax.setInfoType(1L);
						logstTax.setMessage(messagetax);
						logList.add(logstTax);

					}
				}

			}
		}
		return logList;
	}
	private List<LogStatus> calculateSubsGroupStatusForAuxMenuItem(final Map<Long,Product>allProducr,ProductDBRequest productReq){
		if(LOGGER.isInfoEnabled()) LOGGER.info("calculateSubsGroupStatusForAuxMenuItem");
		List<LogStatus> logList=new ArrayList<>();
		for(Map.Entry<Long, Product> entry : allProducr.entrySet()){ 
			if( entry!=null&& entry.getValue().getApprovalStatus()==1 ) { 
				Product product=allProducr.get(entry.getKey());
				if(null!=product && null!=product.getAuxiliaryMenuItem() && product.getAuxiliaryMenuItem()==1L) { 
					final List<Item>substitutionList=product.getSubstitutionList() ;
					Long prdCd=product.getProductCode(); 
					int maxStatus =	0;
					String subGroupNA="";
					if (!substitutionList.isEmpty()) {
						for (Item item : substitutionList) {
							Product subsMenuItem = null;
							Long productID =  item.getProductId();
							Long auxMenuItemFlag = 0L;
							 
							if (!(allProducr.isEmpty()) &&allProducr.size() > 0) {
								subsMenuItem = allProducr.get(productID);
								if (subsMenuItem != null)
									auxMenuItemFlag = subsMenuItem.getAuxiliaryMenuItem();
							}
							if (!(item.getGroupId().equals("-1")) && (auxMenuItemFlag != 1)
									&& (subsMenuItem != null && 
									!subsMenuItem.getProductId().equals(entry.getValue().getProductId()))) {
									int  status = subsMenuItem.getActive(); 
									if (status > maxStatus) {
										maxStatus = status;
										break;
									}
							}
						}
						product.setActive(maxStatus);
					}
					if(maxStatus!=entry.getValue().getActive()) { 
						String	message="The status of auxiliary item "
								+prdCd+" will be assumed as inactive based on Substitution List "
								+subGroupNA+ "."; 
						LogStatus logstTax=new LogStatus();
						logstTax.setEffectiveDate(productReq.getEffectiveDate());
						logstTax.setInfoType(1L);
						logstTax.setMessage(message);
						logList.add(logstTax);	

					}
				}
			} 
		}

		return logList;
	}
}