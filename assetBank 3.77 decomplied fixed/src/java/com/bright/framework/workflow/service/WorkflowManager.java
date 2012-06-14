/*      */ package com.bright.framework.workflow.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.framework.common.bean.DescriptionDataBean;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import com.bright.framework.util.XMLUtil;
/*      */ import com.bright.framework.workflow.bean.AlertInfo;
/*      */ import com.bright.framework.workflow.bean.State;
/*      */ import com.bright.framework.workflow.bean.StateAlert;
/*      */ import com.bright.framework.workflow.bean.StateEvent;
/*      */ import com.bright.framework.workflow.bean.Transition;
/*      */ import com.bright.framework.workflow.bean.Workflow;
/*      */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*      */ import com.bright.framework.workflow.constant.WorkflowSettings;
/*      */ import com.bright.framework.workflow.processor.EnterStateProcessor;
/*      */ import com.bright.framework.workflow.processor.SendAlertProcessor;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import org.apache.avalon.framework.component.ComponentManager;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ public class WorkflowManager extends Bn2Manager
/*      */ {
/*      */   private static final String c_ksClassName = "WorkflowManager";
/*   74 */   private String m_sXmlFilePath = null;
/*   75 */   private Document m_xmlWorkflowDocument = null;
/*      */ 
/*   78 */   private HashMap m_hmStates = null;
/*      */ 
/*   81 */   private HashMap m_hmWorkflowStateLists = null;
/*      */ 
/*   84 */   private Vector<State> m_vecAllStates = null;
/*      */ 
/*   86 */   private HashMap m_hmWorkflowElements = new HashMap();
/*      */ 
/*   88 */   private DBTransactionManager m_transactionManager = null;
/*      */ 
/*      */   public void setDBTransactionManager(DBTransactionManager a_transactionManager) {
/*   91 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void startup()
/*      */     throws Bn2Exception
/*      */   {
/*   99 */     super.startup();
/*      */ 
/*  101 */     this.m_hmStates = new HashMap(20);
/*  102 */     this.m_hmWorkflowStateLists = new HashMap(10);
/*  103 */     this.m_vecAllStates = new Vector();
/*      */ 
/*  105 */     this.m_sXmlFilePath = (WorkflowSettings.getApplicationPath() + "/" + WorkflowSettings.getXmlFilePath());
                System.out.println(this.m_sXmlFilePath);
/*      */     try
/*      */     {
/*  110 */       loadWorkflows();
/*      */     }
/*      */     catch (Bn2Exception bn2e)
/*      */     {
/*  114 */       this.m_logger.error("Exception starting up WorkflowManager:", bn2e);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void loadWorkflows()
/*      */     throws Bn2Exception
/*      */   {
/*  129 */     String ksMethodName = "loadWorkflows";
/*      */     try
/*      */     {
/*  133 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  134 */       DocumentBuilder builder = factory.newDocumentBuilder();
/*      */ 
/*  137 */       File fXmlFile = new File(this.m_sXmlFilePath);
/*  138 */       this.m_xmlWorkflowDocument = builder.parse(fXmlFile);
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*  143 */       throw new Bn2Exception("WorkflowManager.loadWorkflows: IOException:", ioe);
/*      */     }
/*      */     catch (ParserConfigurationException pce)
/*      */     {
/*  147 */       throw new Bn2Exception("WorkflowManager.loadWorkflows: ParserConfigurationException:", pce);
/*      */     }
/*      */     catch (SAXException saxe)
/*      */     {
/*  151 */       throw new Bn2Exception("WorkflowManager.loadWorkflows: SAXException:", saxe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public WorkflowInfo createInitialWorkflow(DBTransaction a_dbTransaction, String a_sWorkflowName, String a_sForType, long a_lEntityId, boolean a_bSendAlerts)
/*      */     throws Bn2Exception
/*      */   {
/*  174 */     return createInitialWorkflowForBatch(a_dbTransaction, a_sWorkflowName, a_sForType, a_lEntityId, 0L, a_bSendAlerts);
/*      */   }
/*      */ 
/*      */   public WorkflowInfo createInitialWorkflowForBatch(DBTransaction a_dbTransaction, String a_sWorkflowName, String a_sForType, long a_lEntityId, long a_lBatchId, boolean a_bSendAlerts)
/*      */     throws Bn2Exception
/*      */   {
/*  196 */     return createWorkflowForState(a_dbTransaction, a_sWorkflowName, a_sForType, null, a_lEntityId, a_lBatchId, a_bSendAlerts);
/*      */   }
/*      */ 
/*      */   public WorkflowInfo createWorkflowForState(DBTransaction a_dbTransaction, String a_sWorkflowName, String a_sForType, String a_sStateName, long a_lEntityId, long a_lBatchId, boolean a_bSendAlerts)
/*      */     throws Bn2Exception
/*      */   {
/*  222 */     WorkflowInfo wfInfo = buildWorkflowInfoForState(a_sWorkflowName, a_sForType, a_sStateName, 0L, a_lEntityId, true);
/*      */ 
/*  225 */     saveWorkflowInfo(a_dbTransaction, wfInfo);
/*      */ 
/*  228 */     processStateEvents(a_dbTransaction, wfInfo);
/*      */ 
/*  230 */     if (a_bSendAlerts)
/*      */     {
/*  232 */       processStateAlerts(a_dbTransaction, wfInfo, a_lBatchId);
/*      */     }
/*      */ 
/*  235 */     return wfInfo;
/*      */   }
/*      */ 
/*      */   private WorkflowInfo buildWorkflowInfoForState(String a_sWorkflowName, String a_sTypeOrVarName, String a_sStateName, long a_lInfoId, long a_lEntityId, boolean a_bMatchVariationByType)
/*      */     throws Bn2Exception
/*      */   {
/*  260 */     Element elWorkflow = getWorkflowByName(a_sWorkflowName);
/*      */ 
/*  263 */     State state = null;
/*      */ 
/*  265 */     if (a_bMatchVariationByType)
/*      */     {
/*  267 */       state = getState(elWorkflow, a_sTypeOrVarName, a_sStateName, true);
/*      */     }
/*      */     else
/*      */     {
/*  271 */       state = getState(elWorkflow, a_sTypeOrVarName, a_sStateName, false);
/*      */     }
/*      */ 
/*  275 */     WorkflowInfo wfInfo = new WorkflowInfo();
/*      */ 
/*  278 */     wfInfo.setState(state);
/*  279 */     wfInfo.setWorkflowName(a_sWorkflowName);
/*  280 */     wfInfo.setVariationName(state.getVariationName());
/*  281 */     wfInfo.setStateName(state.getName());
/*  282 */     wfInfo.setId(a_lInfoId);
/*  283 */     wfInfo.setEntityId(a_lEntityId);
/*      */ 
/*  285 */     return wfInfo;
/*      */   }
/*      */ 
/*      */   public void removeWorkflow(DBTransaction a_dbTransaction, long a_lInfoId)
/*      */     throws Bn2Exception
/*      */   {
/*  298 */     long[] a_aIds = new long[1];
/*  299 */     a_aIds[0] = a_lInfoId;
/*  300 */     deleteWorkflowInfos(a_dbTransaction, a_aIds);
/*      */   }
/*      */ 
/*      */   public void removeWorkflows(DBTransaction a_dbTransaction, Vector<Long> a_vecInfoIds)
/*      */     throws Bn2Exception
/*      */   {
/*  307 */     if ((a_vecInfoIds != null) && (a_vecInfoIds.size() > 0))
/*      */     {
/*  309 */       long[] aIds = new long[a_vecInfoIds.size()];
/*  310 */       for (int i = 0; i < a_vecInfoIds.size(); i++)
/*      */       {
/*  312 */         aIds[i] = ((Long)a_vecInfoIds.elementAt(i)).longValue();
/*      */       }
/*  314 */       deleteWorkflowInfos(a_dbTransaction, aIds);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeWorkflows(DBTransaction a_dbTransaction, long[] a_aInfoIds)
/*      */     throws Bn2Exception
/*      */   {
/*  329 */     deleteWorkflowInfos(a_dbTransaction, a_aInfoIds);
/*      */   }
/*      */ 
/*      */   public WorkflowInfo processTransition(DBTransaction a_dbTransaction, int a_iTransitionNumber, long a_lInfoId, long a_lEntityId)
/*      */     throws Bn2Exception
/*      */   {
/*  349 */     return processTransitionForBatch(a_dbTransaction, a_iTransitionNumber, a_lInfoId, a_lEntityId, 0L);
/*      */   }
/*      */ 
/*      */   public WorkflowInfo processTransitionForBatch(DBTransaction a_dbTransaction, int a_iTransitionNumber, long a_lInfoId, long a_lEntityId, long a_lBatchId)
/*      */     throws Bn2Exception
/*      */   {
/*  376 */     String ksMethodName = "processTransition";
/*  377 */     this.m_logger.debug("In WorkflowManager.processTransition");
/*      */ 
/*  380 */     WorkflowInfo currInfo = getWorkflowInfo(a_dbTransaction, a_lInfoId);
/*  381 */     String sWorkflowName = currInfo.getWorkflowName();
/*  382 */     String sVariationName = currInfo.getVariationName();
/*  383 */     String sStartStateName = currInfo.getStateName();
/*      */ 
/*  386 */     Element elWorkflow = getWorkflowByName(sWorkflowName);
/*      */ 
/*  389 */     State currentState = getState(elWorkflow, sVariationName, sStartStateName, false);
/*      */ 
/*  392 */     Transition transition = currentState.getTransition(a_iTransitionNumber);
/*  393 */     if (transition == null)
/*      */     {
/*  395 */       throw new Bn2Exception("WorkflowManager.processTransition: there is no transition with number " + a_iTransitionNumber);
/*      */     }
/*      */ 
/*  399 */     String sNewState = transition.getNextStateName();
/*  400 */     WorkflowInfo newInfo = buildWorkflowInfoForState(sWorkflowName, sVariationName, sNewState, a_lInfoId, a_lEntityId, false);
/*      */ 
/*  403 */     saveWorkflowInfo(a_dbTransaction, newInfo);
/*      */ 
/*  406 */     processStateEvents(a_dbTransaction, newInfo);
/*  407 */     processStateAlerts(a_dbTransaction, newInfo, a_lBatchId);
/*      */ 
/*  409 */     return newInfo;
/*      */   }
/*      */ 
/*      */   public State getCurrentState(DBTransaction a_dbTransaction, long a_lInfoId)
/*      */     throws Bn2Exception
/*      */   {
/*  426 */     WorkflowInfo currInfo = getWorkflowInfo(a_dbTransaction, a_lInfoId);
/*  427 */     String sWorkflowName = currInfo.getWorkflowName();
/*  428 */     String sVariationName = currInfo.getVariationName();
/*  429 */     String sStateName = currInfo.getStateName();
/*      */ 
/*  431 */     Element elWorkflow = getWorkflowByName(sWorkflowName);
/*      */ 
/*  433 */     State state = getState(elWorkflow, sVariationName, sStateName, false);
/*      */ 
/*  435 */     return state;
/*      */   }
/*      */ 
/*      */   private void processStateEvents(DBTransaction a_dbTransaction, WorkflowInfo a_wfInfo)
/*      */     throws Bn2Exception
/*      */   {
/*  447 */     Vector vecEventList = a_wfInfo.getState().getEventList();
/*  448 */     if (vecEventList != null)
/*      */     {
/*  450 */       Iterator itEvents = vecEventList.iterator();
/*  451 */       while (itEvents.hasNext())
/*      */       {
/*  453 */         StateEvent event = (StateEvent)itEvents.next();
/*  454 */         processEvent(a_dbTransaction, event, a_wfInfo);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void processStateAlerts(DBTransaction a_dbTransaction, WorkflowInfo a_wfInfo, long a_lBatchId)
/*      */   {
/*  467 */     String ksMethodName = "processStateAlerts";
/*      */ 
/*  469 */     Vector vecAlertList = a_wfInfo.getState().getAlertList();
/*  470 */     if (vecAlertList != null)
/*      */     {
/*  472 */       Iterator itAlerts = vecAlertList.iterator();
/*  473 */       while (itAlerts.hasNext())
/*      */       {
/*  475 */         StateAlert alert = (StateAlert)itAlerts.next();
/*      */         try
/*      */         {
/*  479 */           processAlert(a_dbTransaction, alert, a_wfInfo, a_lBatchId);
/*      */         }
/*      */         catch (Bn2Exception e)
/*      */         {
/*  483 */           this.m_logger.error("WorkflowManager.processStateAlertsException occurred whilst processing alerts " + alert.getName() + ": " + e.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void processEvent(DBTransaction a_dbTransaction, StateEvent a_event, WorkflowInfo a_wfInfo)
/*      */     throws Bn2Exception
/*      */   {
/*  504 */     String ksMethodName = "processEvent";
/*  505 */     this.m_logger.debug("In WorkflowManager.processEvent");
/*      */ 
/*  508 */     EnterStateProcessor processor = null;
/*      */ 
/*  510 */     String sProcessorName = a_event.getHandlerClassName();
/*      */     try
/*      */     {
/*  514 */       processor = (EnterStateProcessor)GlobalApplication.getInstance().getComponentManager().lookup(sProcessorName);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  518 */       this.m_logger.error("WorkflowManager.processEventException occurred whilst getting processor " + sProcessorName + ": " + e.getMessage());
/*  519 */       throw new Bn2Exception("WorkflowManager.processEventComponent Exception occurred whilst getting processor " + sProcessorName + ": ", e);
/*      */     }
/*      */ 
/*  523 */     processor.process(a_dbTransaction, a_wfInfo);
/*      */   }
/*      */ 
/*      */   private void processAlert(DBTransaction a_dbTransaction, StateAlert a_alert, WorkflowInfo a_wfInfo, long a_lBatchId)
/*      */     throws Bn2Exception
/*      */   {
/*  541 */     String ksMethodName = "processAlert";
/*      */ 
/*  544 */     SendAlertProcessor processor = null;
/*      */ 
/*  546 */     String sProcessorName = a_alert.getHandlerClassName();
/*      */     try
/*      */     {
/*  550 */       processor = (SendAlertProcessor)GlobalApplication.getInstance().getComponentManager().lookup(sProcessorName);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  554 */       this.m_logger.error("WorkflowManager.processAlertException occurred whilst getting processor " + sProcessorName + ": " + e.getMessage());
/*  555 */       throw new Bn2Exception("WorkflowManager.processAlertComponent Exception occurred whilst getting processor " + sProcessorName + ": ", e);
/*      */     }
/*      */ 
/*  559 */     AlertInfo alertInfo = new AlertInfo();
/*  560 */     alertInfo.setAlert(a_alert);
/*  561 */     alertInfo.setWorkflowInfo(a_wfInfo);
/*      */ 
/*  563 */     if (a_lBatchId > 0L)
/*      */     {
/*  565 */       processor.addToBatch(alertInfo, a_lBatchId);
/*      */     }
/*      */     else
/*      */     {
/*  569 */       processor.sendAlert(a_dbTransaction, alertInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void startAlertProcessorBatch(SendAlertProcessor a_processor, long a_lBatchId)
/*      */     throws Bn2Exception
/*      */   {
/*  584 */     a_processor.startBatch(a_lBatchId);
/*      */   }
/*      */ 
/*      */   public void sendAlertProcessorBatch(DBTransaction a_dbTransaction, SendAlertProcessor a_processor, long a_lBatchId)
/*      */     throws Bn2Exception
/*      */   {
/*  598 */     a_processor.sendBatch(a_dbTransaction, a_lBatchId);
/*      */   }
/*      */ 
/*      */   public void endAlertProcessorBatch(SendAlertProcessor a_processor, long a_lBatchId)
/*      */   {
/*  610 */     a_processor.endBatch(a_lBatchId);
/*      */   }
/*      */ 
/*      */   public Vector<Workflow> getWorkflowBeans()
/*      */     throws Bn2Exception
/*      */   {
/*  624 */     Vector vecStrings = getWorkflows();
/*  625 */     Vector vecBeans = null;
/*  626 */     if (vecStrings != null)
/*      */     {
/*  628 */       for (int i = 0; i < vecStrings.size(); i++)
/*      */       {
/*  630 */         String sName = (String)vecStrings.elementAt(i);
/*  631 */         Workflow wf = getWorkflowBean(sName);
/*  632 */         if (vecBeans == null)
/*      */         {
/*  634 */           vecBeans = new Vector();
/*      */         }
/*  636 */         vecBeans.add(wf);
/*      */       }
/*      */     }
/*  639 */     return vecBeans;
/*      */   }
/*      */ 
/*      */   public Workflow getWorkflowBean(String a_sName)
/*      */     throws Bn2Exception
/*      */   {
/*  653 */     Element workflow = getWorkflowByName(a_sName);
/*  654 */     Workflow wf = null;
/*  655 */     if (workflow != null)
/*      */     {
/*  657 */       wf = new Workflow();
/*  658 */       wf.setName(workflow.getAttribute("name"));
/*  659 */       wf.setDescription(workflow.getAttribute("description"));
/*      */     }
/*  661 */     return wf;
/*      */   }
/*      */ 
/*      */   public Vector<String> getWorkflows()
/*      */     throws Bn2Exception
/*      */   {
/*  673 */     Vector vecWorkflows = new Vector();
/*      */ 
/*  676 */     NodeList list = this.m_xmlWorkflowDocument.getElementsByTagName("workflow");
/*      */ 
/*  679 */     for (int i = 0; i < list.getLength(); i++)
/*      */     {
/*  681 */       Element element = (Element)list.item(i);
/*  682 */       String sName = element.getAttribute("name");
/*      */ 
/*  685 */       if (sName == null)
/*      */       {
/*  687 */         throw new Bn2Exception("All workflows in the XML file must have a name attribute");
/*      */       }
/*      */ 
/*  690 */       vecWorkflows.add(sName);
/*      */     }
/*      */ 
/*  693 */     return vecWorkflows;
/*      */   }
/*      */ 
/*      */   private Element getWorkflowByName(String a_sName)
/*      */     throws Bn2Exception
/*      */   {
/*  706 */     String ksMethodName = "getWorkflowByName";
/*  707 */     Element elWorkflow = null;
/*      */ 
/*  709 */     if (this.m_hmWorkflowElements.containsKey(a_sName))
/*      */     {
/*  711 */       return (Element)this.m_hmWorkflowElements.get(a_sName);
/*      */     }
/*      */ 
/*  715 */     NodeList list = this.m_xmlWorkflowDocument.getElementsByTagName("workflow");
/*      */ 
/*  718 */     for (int i = 0; i < list.getLength(); i++)
/*      */     {
/*  720 */       Element element = (Element)list.item(i);
/*      */ 
/*  723 */       if (element.getAttribute("name") == null)
/*      */       {
/*  725 */         throw new Bn2Exception("All workflows in the XML file must have a name attribute");
/*      */       }
/*      */ 
/*  728 */       if (!element.getAttribute("name").equals(a_sName)) {
/*      */         continue;
/*      */       }
/*  731 */       elWorkflow = element;
/*  732 */       break;
/*      */     }
/*      */ 
/*  736 */     if (elWorkflow == null)
/*      */     {
/*  738 */       throw new Bn2Exception("WorkflowManager.getWorkflowByName: There is no workflow called " + a_sName);
/*      */     }
/*      */ 
/*  742 */     this.m_hmWorkflowElements.put(a_sName, elWorkflow);
/*      */ 
/*  744 */     return elWorkflow;
/*      */   }
/*      */ 
/*      */   private State getState(Element a_elWorkflow, String a_sTypeOrVariationName, String a_sName, boolean a_bMatchByType)
/*      */     throws Bn2Exception
/*      */   {
/*  761 */     String ksMethodName = "getState";
/*      */ 
/*  763 */     Element elState = null;
/*  764 */     State theState = null;
/*      */ 
/*  766 */     String sWorkflowName = a_elWorkflow.getAttribute("name");
/*  767 */     String sCacheName = sWorkflowName + a_sTypeOrVariationName + a_sName;
/*  768 */     String sVariationName = null;
/*      */ 
/*  771 */     synchronized (this.m_hmStates)
/*      */     {
/*  773 */       theState = (State)this.m_hmStates.get(sCacheName);
/*      */ 
/*  775 */       if (theState == null)
/*      */       {
/*  778 */         Element elVariation = null;
/*  779 */         NodeList listVariations = a_elWorkflow.getElementsByTagName("variation");
/*  780 */         for (int i = 0; i < listVariations.getLength(); i++)
/*      */         {
/*  783 */           Element element = (Element)listVariations.item(i);
/*      */ 
/*  785 */           boolean bMatch = false;
/*  786 */           if (a_bMatchByType)
/*      */           {
/*  789 */             String sVarPattern = element.getAttribute("for");
/*  790 */             if (checkVariationPatternMatchesType(sVarPattern, a_sTypeOrVariationName))
/*      */             {
/*  792 */               bMatch = true;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  797 */             String sVarName = element.getAttribute("name");
/*  798 */             if ((StringUtil.stringIsPopulated(sVarName)) && (sVarName.equals(a_sTypeOrVariationName)))
/*      */             {
/*  800 */               bMatch = true;
/*      */             }
/*      */           }
/*      */ 
/*  804 */           if (!bMatch)
/*      */             continue;
/*  806 */           elVariation = element;
/*  807 */           sVariationName = elVariation.getAttribute("name");
/*  808 */           break;
/*      */         }
/*      */ 
/*  813 */         if (elVariation == null)
/*      */         {
/*  815 */           this.m_logger.error("WorkflowManager.getState: no variation found for variation/type=" + a_sTypeOrVariationName + ", workflow name=" + sWorkflowName);
/*  816 */           throw new Bn2Exception("WorkflowManager.getState: Variation not found.");
/*      */         }
/*      */ 
/*  820 */         NodeList list = elVariation.getElementsByTagName("state");
/*      */ 
/*  823 */         for (int i = 0; i < list.getLength(); i++)
/*      */         {
/*  826 */           Element element = (Element)list.item(i);
/*      */ 
/*  829 */           if (((a_sName == null) || (!element.getAttribute("name").equals(a_sName))) && ((a_sName != null) || (element.getAttribute("initial") == null) || (!element.getAttribute("initial").equals("true"))))
/*      */           {
/*      */             continue;
/*      */           }
/*      */ 
/*  834 */           elState = element;
/*  835 */           break;
/*      */         }
/*      */ 
/*  840 */         if (elState == null)
/*      */         {
/*  842 */           this.m_logger.error("WorkflowManager.getState: no state found for state name= " + a_sName + ", workflow name=" + sWorkflowName);
/*  843 */           throw new Bn2Exception("WorkflowManager.getState:Name of state is wrong or there is no initial state.");
/*      */         }
/*      */ 
/*  848 */         theState = buildState(elState, sWorkflowName, sVariationName, 0);
/*      */ 
/*  851 */         this.m_hmStates.put(sCacheName, theState);
/*      */       }
/*      */     }
/*      */ 
/*  855 */     return theState;
/*      */   }
/*      */ 
/*      */   public Vector getStatesForWorkflow(String a_sWorkflowName, String a_sVariation)
/*      */     throws Bn2Exception
/*      */   {
/*  869 */     String ksMethodName = "getStatesForWorkflow";
/*  870 */     Vector vecStates = null;
/*      */ 
/*  873 */     synchronized (this.m_hmWorkflowStateLists)
/*      */     {
/*  875 */       vecStates = (Vector)this.m_hmWorkflowStateLists.get(a_sWorkflowName);
/*      */ 
/*  877 */       if (vecStates == null)
/*      */       {
/*  879 */         vecStates = new Vector();
/*      */ 
/*  882 */         Element elWorkflow = getWorkflowByName(a_sWorkflowName);
/*      */ 
/*  885 */         Element elVariation = null;
/*  886 */         NodeList listVariations = elWorkflow.getElementsByTagName("variation");
/*  887 */         for (int i = 0; i < listVariations.getLength(); i++)
/*      */         {
/*  889 */           Element element = (Element)listVariations.item(i);
/*      */ 
/*  892 */           String sVarName = element.getAttribute("for");
/*  893 */           if (!checkVariationPatternMatchesType(sVarName, a_sVariation))
/*      */             continue;
/*  895 */           elVariation = element;
/*  896 */           break;
/*      */         }
/*      */ 
/*  900 */         if (elVariation == null)
/*      */         {
/*  902 */           this.m_logger.error("WorkflowManager.getStatesForWorkflow: no variation found for variation name=" + a_sVariation + ", workflow name=" + a_sWorkflowName);
/*  903 */           throw new Bn2Exception("WorkflowManager.getStatesForWorkflow: Variation not found.");
/*      */         }
/*      */ 
/*  908 */         NodeList list = elVariation.getElementsByTagName("state");
/*      */ 
/*  911 */         for (int i = 0; i < list.getLength(); i++)
/*      */         {
/*  914 */           Element element = (Element)list.item(i);
/*      */ 
/*  917 */           DescriptionDataBean ddb = new DescriptionDataBean();
/*  918 */           ddb.setName(element.getAttribute("name"));
/*  919 */           ddb.setDescription(element.getAttribute("description"));
/*      */ 
/*  922 */           vecStates.add(ddb);
/*      */         }
/*      */ 
/*  926 */         this.m_hmWorkflowStateLists.put(a_sWorkflowName, vecStates);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  931 */     return vecStates;
/*      */   }
/*      */ 
/*      */   public Vector<State> getAllStates()
/*      */     throws Bn2Exception
/*      */   {
/*  944 */     String ksMethodName = "getAllStates";
/*      */ 
/*  947 */     synchronized (this.m_vecAllStates)
/*      */     {
/*  949 */       if (this.m_vecAllStates.size() == 0)
/*      */       {
/*  952 */         NodeList listWorkflows = this.m_xmlWorkflowDocument.getElementsByTagName("workflow");
/*      */ 
/*  955 */         for (int i = 0; i < listWorkflows.getLength(); i++)
/*      */         {
/*  957 */           Element elWorkflow = (Element)listWorkflows.item(i);
/*  958 */           String sWorkflowName = elWorkflow.getAttribute("name");
/*      */ 
/*  960 */           if (!StringUtil.stringIsPopulated(sWorkflowName))
/*      */           {
/*  962 */             throw new Bn2Exception("WorkflowManager:getAllStates: All workflows in the XML file must have a name attribute");
/*      */           }
/*      */ 
/*  966 */           NodeList listVariations = elWorkflow.getElementsByTagName("variation");
/*  967 */           for (int j = 0; j < listVariations.getLength(); j++)
/*      */           {
/*  970 */             Element elVariation = (Element)listVariations.item(j);
/*  971 */             String sVariationName = elVariation.getAttribute("name");
/*      */ 
/*  973 */             if (!StringUtil.stringIsPopulated(sVariationName))
/*      */             {
/*  975 */               throw new Bn2Exception("WorkflowManager:getAllStates: All variations in the XML file must have a name attribute");
/*      */             }
/*      */ 
/*  979 */             NodeList listStates = elVariation.getElementsByTagName("state");
/*      */ 
/*  982 */             for (int k = 0; k < listStates.getLength(); k++)
/*      */             {
/*  985 */               Element elState = (Element)listStates.item(k);
/*      */ 
/*  987 */               State state = buildState(elState, sWorkflowName, sVariationName, k);
/*      */ 
/*  989 */               this.m_vecAllStates.add(state);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  996 */     return this.m_vecAllStates;
/*      */   }
/*      */ 
/*      */   public State getState(String a_sWorkflowName, String a_sVariationName, String a_sStateName)
/*      */     throws Bn2Exception
/*      */   {
/* 1012 */     Vector allStates = getAllStates();
/* 1013 */     Iterator itStates = allStates.iterator();
/*      */ 
/* 1015 */     State thisState = null;
/*      */ 
/* 1017 */     while (itStates.hasNext())
/*      */     {
/* 1019 */       State state = (State)itStates.next();
/*      */ 
/* 1021 */       if ((state.getWorkflowName().equals(a_sWorkflowName)) && (state.getName().equals(a_sStateName)) && (state.getVariationName().equals(a_sVariationName)) && (state.getVariationName().equals(a_sVariationName)))
/*      */       {
/* 1023 */         thisState = state;
/* 1024 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1029 */     return thisState;
/*      */   }
/*      */ 
/*      */   private State buildState(Element a_elState, String a_sWorkflowName, String a_sVariationName, int a_iNum)
/*      */     throws Bn2Exception
/*      */   {
/* 1043 */     String ksMethodName = "buildState";
/* 1044 */     String sStateName = a_elState.getAttribute("name");
/*      */ 
/* 1046 */     if (!StringUtil.stringIsPopulated(sStateName))
/*      */     {
/* 1048 */       throw new Bn2Exception("WorkflowManager:buildState: All states in the XML file must have a name attribute");
/*      */     }
/*      */ 
/* 1052 */     State state = new State();
/* 1053 */     state.setStateNumber(a_iNum);
/* 1054 */     state.setWorkflowName(a_sWorkflowName);
/* 1055 */     state.setVariationName(a_sVariationName);
/* 1056 */     state.setName(sStateName);
/* 1057 */     state.setDescription(a_elState.getAttribute("description"));
/* 1058 */     state.setVisibleTo(a_elState.getAttribute("visible-to"));
/*      */ 
/* 1061 */     Vector vecHelpText = XMLUtil.getTextFromChildren(a_elState, "help-text");
/* 1062 */     if (vecHelpText.size() > 0)
/*      */     {
/* 1064 */       state.setHelpText((String)vecHelpText.get(0));
/*      */     }
/*      */ 
/* 1068 */     NodeList nlTransitions = a_elState.getElementsByTagName("transition");
/* 1069 */     Vector vecTransitions = new Vector();
/* 1070 */     for (int i = 0; i < nlTransitions.getLength(); i++)
/*      */     {
/* 1072 */       Element elTransition = (Element)nlTransitions.item(i);
/*      */ 
/* 1074 */       Transition transition = new Transition();
/* 1075 */       transition.setName(elTransition.getAttribute("name"));
/* 1076 */       transition.setDescription(elTransition.getAttribute("description"));
/* 1077 */       transition.setNextStateName(elTransition.getAttribute("next-state"));
/* 1078 */       transition.setTransitionNumber(i);
/*      */ 
/* 1081 */       boolean bHasMessage = (elTransition.getAttribute("has-message") != null) && (elTransition.getAttribute("has-message").equals("true"));
/* 1082 */       transition.setHasMessage(bHasMessage);
/*      */ 
/* 1084 */       boolean bMessageMandatory = (elTransition.getAttribute("message-mandatory") != null) && (elTransition.getAttribute("message-mandatory").equals("true"));
/* 1085 */       transition.setMessageMandatory(bMessageMandatory);
/*      */ 
/* 1088 */       Vector vecConfirmationText = XMLUtil.getTextFromChildren(elTransition, "confirmation-text");
/*      */ 
/* 1090 */       if (vecConfirmationText.size() > 0)
/*      */       {
/* 1092 */         transition.setConfirmationText((String)vecConfirmationText.elementAt(0));
/*      */       }
/*      */ 
/* 1096 */       vecHelpText = XMLUtil.getTextFromChildren(elTransition, "help-text");
/*      */ 
/* 1098 */       if (vecHelpText.size() > 0)
/*      */       {
/* 1100 */         transition.setHelpText((String)vecHelpText.get(0));
/*      */       }
/*      */ 
/* 1104 */       vecTransitions.add(transition);
/*      */     }
/* 1106 */     state.setTransitionList(vecTransitions);
/*      */ 
/* 1110 */     NodeList nlAlerts = a_elState.getElementsByTagName("alert");
/* 1111 */     Vector vecAlerts = new Vector();
/* 1112 */     for (int i = 0; i < nlAlerts.getLength(); i++)
/*      */     {
/* 1114 */       Element elAlert = (Element)nlAlerts.item(i);
/*      */ 
/* 1116 */       StateAlert alert = new StateAlert();
/* 1117 */       alert.setName(elAlert.getAttribute("name"));
/* 1118 */       alert.setSendTo(elAlert.getAttribute("send-to"));
/* 1119 */       alert.setHandlerClassName(elAlert.getAttribute("handler"));
/* 1120 */       alert.setTemplate(elAlert.getAttribute("template"));
/*      */ 
/* 1122 */       vecAlerts.add(alert);
/*      */     }
/* 1124 */     state.setAlertList(vecAlerts);
/*      */ 
/* 1127 */     NodeList nlEvents = a_elState.getElementsByTagName("event");
/* 1128 */     Vector vecEvents = new Vector();
/* 1129 */     for (int i = 0; i < nlEvents.getLength(); i++)
/*      */     {
/* 1131 */       Element elEvent = (Element)nlEvents.item(i);
/*      */ 
/* 1133 */       StateEvent event = new StateEvent();
/* 1134 */       event.setName(elEvent.getAttribute("name"));
/* 1135 */       event.setHandlerClassName(elEvent.getAttribute("handler"));
/*      */ 
/* 1137 */       vecEvents.add(event);
/*      */     }
/* 1139 */     state.setEventList(vecEvents);
/*      */ 
/* 1141 */     return state;
/*      */   }
/*      */ 
/*      */   private boolean checkVariationPatternMatchesType(String a_sVariationFor, String a_sType)
/*      */   {
/* 1154 */     if (a_sVariationFor.equals("*"))
/*      */     {
/* 1156 */       return true;
/*      */     }
/*      */ 
/* 1160 */     String[] asVariations = StringUtil.convertToArray(a_sVariationFor, ",");
/*      */ 
/* 1162 */     boolean bMatch = false;
/*      */ 
/* 1164 */     if (asVariations != null)
/*      */     {
/* 1166 */       for (int i = 0; i < asVariations.length; i++)
/*      */       {
/* 1168 */         String sVariation = asVariations[i].trim();
/* 1169 */         if (!sVariation.equals(a_sType))
/*      */           continue;
/* 1171 */         bMatch = true;
/* 1172 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1177 */     return bMatch;
/*      */   }
/*      */ 
/*      */   private void saveWorkflowInfo(DBTransaction a_dbTransaction, WorkflowInfo a_wfInfo)
/*      */     throws Bn2Exception
/*      */   {
/* 1195 */     String ksMethodName = "saveWorkflowInfo";
/* 1196 */     Connection con = null;
/*      */     try
/*      */     {
/* 1200 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1203 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 1205 */       if (a_wfInfo.getId() <= 0L)
/*      */       {
/* 1207 */         long lNewId = 0L;
/* 1208 */         String sSql = "INSERT INTO WorkflowInfo (";
/*      */ 
/* 1210 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1212 */           lNewId = sqlGenerator.getUniqueId(con, "WorkflowInfoSequence");
/* 1213 */           sSql = sSql + "Id,";
/*      */         }
/*      */ 
/* 1216 */         sSql = sSql + "WorkflowName, VariationName, StateName, LastStateChangeDate, WorkflowableEntityId) VALUES (?,?,?,?,?";
/*      */ 
/* 1218 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1220 */           sSql = sSql + ",?";
/*      */         }
/*      */ 
/* 1223 */         sSql = sSql + ")";
/*      */ 
/* 1225 */         int iCol = 1;
/* 1226 */         PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 1228 */         if (!sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1230 */           psql.setLong(iCol++, lNewId);
/*      */         }
/*      */ 
/* 1233 */         psql.setString(iCol++, a_wfInfo.getWorkflowName());
/* 1234 */         psql.setString(iCol++, a_wfInfo.getVariationName());
/* 1235 */         psql.setString(iCol++, a_wfInfo.getStateName());
/* 1236 */         psql.setTimestamp(iCol++, new Timestamp(new Date().getTime()));
/* 1237 */         psql.setLong(iCol++, a_wfInfo.getEntityId());
/* 1238 */         psql.executeUpdate();
/*      */ 
/* 1241 */         if (sqlGenerator.usesAutoincrementFields())
/*      */         {
/* 1243 */           lNewId = sqlGenerator.getUniqueId(con, "WorkflowInfo");
/*      */         }
/*      */ 
/* 1246 */         psql.close();
/*      */ 
/* 1248 */         a_wfInfo.setId(lNewId);
/*      */       }
/*      */       else
/*      */       {
/* 1252 */         String sSql = "UPDATE WorkflowInfo SET StateName=?, LastStateChangeDate=?, WorkflowableEntityId=? WHERE Id=?";
/*      */ 
/* 1254 */         PreparedStatement psql = con.prepareStatement(sSql);
/* 1255 */         psql.setString(1, a_wfInfo.getStateName());
/* 1256 */         psql.setTimestamp(2, new Timestamp(new Date().getTime()));
/* 1257 */         psql.setLong(3, a_wfInfo.getEntityId());
/* 1258 */         psql.setLong(4, a_wfInfo.getId());
/*      */ 
/* 1260 */         psql.executeUpdate();
/* 1261 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1267 */       throw new Bn2Exception("WorkflowManager.saveWorkflowInfo: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public WorkflowInfo getWorkflowInfo(DBTransaction a_dbTransaction, long a_lId)
/*      */     throws Bn2Exception
/*      */   {
/* 1286 */     String ksMethodName = "getWorkflowInfo";
/* 1287 */     Connection con = null;
/* 1288 */     String sSql = null;
/* 1289 */     PreparedStatement psql = null;
/* 1290 */     WorkflowInfo wfInfo = new WorkflowInfo();
/* 1291 */     DBTransaction dbTransaction = a_dbTransaction;
/*      */     try
/*      */     {
/* 1295 */       if (dbTransaction == null)
/*      */       {
/* 1297 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/* 1299 */       con = dbTransaction.getConnection();
/*      */ 
/* 1301 */       sSql = "SELECT Id, WorkflowName, VariationName, StateName, LastStateChangeDate, WorkflowableEntityId FROM WorkflowInfo wf WHERE wf.Id=?";
/*      */ 
/* 1305 */       psql = con.prepareStatement(sSql);
/* 1306 */       psql.setLong(1, a_lId);
/*      */ 
/* 1308 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1311 */       if (rs.next())
/*      */       {
/* 1313 */         wfInfo.setId(rs.getLong("Id"));
/* 1314 */         wfInfo.setWorkflowName(rs.getString("WorkflowName"));
/* 1315 */         wfInfo.setVariationName(rs.getString("VariationName"));
/* 1316 */         wfInfo.setStateName(rs.getString("StateName"));
/* 1317 */         wfInfo.setLastStateChangeDate(rs.getTimestamp("LastStateChangeDate"));
/* 1318 */         wfInfo.setEntityId(rs.getLong("WorkflowableEntityId"));
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       throw new Bn2Exception("WorkflowManager.getWorkflowInfo: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1335 */       if ((dbTransaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 1339 */           dbTransaction.commit();
/*      */         } catch (Exception ex) {
/*      */         }
/*      */       }
/*      */     }
/* 1344 */     return wfInfo;
/*      */   }
/*      */ 
/*      */   public boolean getIsEntityOwnerOnlyForWorkflow(DBTransaction a_dbTransaction, long a_lEntityId, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/* 1361 */     WorkflowInfo wf = getWorkflowInfoForEntity(a_dbTransaction, a_lEntityId, a_sWorkflowName);
/* 1362 */     if (wf != null)
/*      */     {
/* 1364 */       State state = getState(wf.getWorkflowName(), wf.getVariationName(), wf.getStateName());
/* 1365 */       if ((state != null) && (state.getVisibleToOwner()))
/*      */       {
/* 1367 */         return true;
/*      */       }
/*      */     }
/*      */ 
/* 1371 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean getIsEntityInAnyOwnerOnlyState(DBTransaction a_dbTransaction, long a_lEntityId)
/*      */     throws Bn2Exception
/*      */   {
/* 1389 */     Vector vecWorkflowsToCheck = getWorkflowsForEntity(a_dbTransaction, a_lEntityId);
/* 1390 */     return getIsEntityInAnyOwnerOnlyState(a_dbTransaction, a_lEntityId, vecWorkflowsToCheck);
/*      */   }
/*      */ 
/*      */   public boolean getIsEntityInAnyOwnerOnlyState(DBTransaction a_dbTransaction, long a_lEntityId, Vector<WorkflowInfo> a_vecWorkflowsToCheck)
/*      */     throws Bn2Exception
/*      */   {
/* 1406 */     if (a_vecWorkflowsToCheck != null)
/*      */     {
/* 1408 */       for (WorkflowInfo wf : a_vecWorkflowsToCheck)
/*      */       {
/* 1410 */         State state = getState(wf.getWorkflowName(), wf.getVariationName(), wf.getStateName());
/* 1411 */         if ((state != null) && (state.getVisibleToOwner()))
/*      */         {
/* 1413 */           return true;
/*      */         }
/*      */       }
/*      */     }
/* 1417 */     return false;
/*      */   }
/*      */ 
/*      */   private void deleteWorkflowInfos(DBTransaction a_dbTransaction, long[] a_aIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1431 */     String ksMethodName = "deleteWorkflowInfo";
/* 1432 */     Connection con = null;
/*      */     try
/*      */     {
/* 1436 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1438 */       String sSql = null;
/* 1439 */       PreparedStatement psql = null;
/* 1440 */       String sIds = "";
/*      */ 
/* 1442 */       for (int i = 0; i < a_aIds.length; i++)
/*      */       {
/* 1444 */         sIds = sIds + a_aIds[i];
/* 1445 */         if (i >= a_aIds.length - 1)
/*      */           continue;
/* 1447 */         sIds = sIds + ",";
/*      */       }
/*      */ 
/* 1451 */       sSql = "DELETE FROM WorkflowInfo WHERE Id IN (" + sIds + ")";
/* 1452 */       psql = con.prepareStatement(sSql);
/* 1453 */       psql.executeUpdate();
/* 1454 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1459 */       throw new Bn2Exception("WorkflowManager.deleteWorkflowInfo: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public WorkflowInfo getWorkflowInfoForEntity(DBTransaction a_dbTransaction, long a_lEntityId, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/* 1476 */     Vector<WorkflowInfo> vecAllWorkflowInfos = getWorkflowsForEntity(a_dbTransaction, a_lEntityId);
/* 1477 */     if (vecAllWorkflowInfos != null)
/*      */     {
/* 1479 */       for (WorkflowInfo wi : vecAllWorkflowInfos)
/*      */       {
/* 1481 */         if (wi.getWorkflowName().equals(a_sWorkflowName))
/*      */         {
/* 1483 */           return wi;
/*      */         }
/*      */       }
/*      */     }
/* 1487 */     return null;
/*      */   }
/*      */ 
/*      */   public Vector<WorkflowInfo> getWorkflowsForEntity(DBTransaction a_dbTransaction, long a_lEntityId)
/*      */     throws Bn2Exception
/*      */   {
/* 1501 */     Vector vecWorkflows = null;
/* 1502 */     String ksMethodName = "getWorkflowsForEntity";
/* 1503 */     DBTransaction dbTransaction = a_dbTransaction;
/*      */     try
/*      */     {
/* 1507 */       if (dbTransaction == null)
/*      */       {
/* 1509 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/* 1512 */       Connection con = dbTransaction.getConnection();
/*      */ 
/* 1514 */       String sSql = "SELECT Id, WorkflowName, VariationName, StateName, LastStateChangeDate FROM WorkflowInfo wf WHERE wf.WorkflowableEntityId=?";
/*      */ 
/* 1517 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 1518 */       psql.setLong(1, a_lEntityId);
/* 1519 */       ResultSet rs = psql.executeQuery();
/*      */ 
/* 1521 */       while (rs.next())
/*      */       {
/* 1523 */         WorkflowInfo wfInfo = new WorkflowInfo();
/* 1524 */         wfInfo.setId(rs.getLong("Id"));
/* 1525 */         wfInfo.setWorkflowName(rs.getString("WorkflowName"));
/* 1526 */         wfInfo.setVariationName(rs.getString("VariationName"));
/* 1527 */         wfInfo.setStateName(rs.getString("StateName"));
/* 1528 */         wfInfo.setLastStateChangeDate(rs.getTimestamp("LastStateChangeDate"));
/* 1529 */         wfInfo.setEntityId(a_lEntityId);
/*      */ 
/* 1531 */         if (vecWorkflows == null)
/*      */         {
/* 1533 */           vecWorkflows = new Vector();
/*      */         }
/*      */ 
/* 1536 */         vecWorkflows.add(wfInfo);
/*      */       }
/*      */ 
/* 1539 */       psql.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       throw new Bn2Exception("WorkflowManager.getWorkflowsForEntity: Exception occurred: " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1555 */       if ((dbTransaction != null) && (a_dbTransaction == null))
/*      */       {
/*      */         try
/*      */         {
/* 1559 */           dbTransaction.commit();
/*      */         } catch (SQLException ex) {
/*      */         }
/*      */       }
/*      */     }
/* 1564 */     return vecWorkflows;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.workflow.service.WorkflowManager
 * JD-Core Version:    0.6.0
 */