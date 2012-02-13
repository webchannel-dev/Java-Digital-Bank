/*      */ package com.bright.assetbank.workflow.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.service.AssetDeleteContext;
/*      */ import com.bright.assetbank.application.service.AssetDeleteParticipant;
/*      */ import com.bright.assetbank.application.service.AssetLogManager;
import com.bright.assetbank.application.service.AssetManager;
/*      */ import com.bright.assetbank.application.service.AssetManager;
/*      */ import com.bright.assetbank.application.service.AssetSaveContext;
/*      */ import com.bright.assetbank.application.service.AssetSaveParticipant;
/*      */ import com.bright.assetbank.application.service.FileAssetManagerImpl;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.assetbank.user.service.ABUserManager;
/*      */ import com.bright.assetbank.workflow.bean.AssetInState;
/*      */ import com.bright.assetbank.workflow.bean.AssetWorkflowAuditEntry;
/*      */ import com.bright.assetbank.workflow.bean.AssetsInState;
/*      */ import com.bright.assetbank.workflow.bean.StatesInVariation;
/*      */ import com.bright.assetbank.workflow.bean.TransitionableAssets;
/*      */ import com.bright.assetbank.workflow.bean.VariationsInWorkflow;
/*      */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*      */ import com.bright.assetbank.workflow.processor.AssetUploadedAlertProcessor;
/*      */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.common.bean.StringDataBean;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.image.bean.ImageFile;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import com.bright.framework.workflow.bean.State;
/*      */ import com.bright.framework.workflow.bean.Transition;
/*      */ import com.bright.framework.workflow.bean.Workflow;
/*      */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*      */ import com.bright.framework.workflow.constant.WorkflowSettings;
/*      */ import com.bright.framework.workflow.service.WorkflowManager;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ 
/*      */ public class AssetWorkflowManager extends Bn2Manager
/*      */   implements AssetSaveParticipant, AssetDeleteParticipant
/*      */ {
/*      */   private static final String c_ksClassName = "AssetWorkflowManager";
/*   97 */   private Object m_objUnsubmittedCountLock = new Object();
/*   98 */   private HashMap<Long, Integer> m_hmNumUnsubmittedWorkflowedAssets = null;
/*      */ 
/*  100 */   private AssetManager m_assetManager = null;
/*      */ 
/*  106 */   private FileAssetManagerImpl m_assetFileManager = null;
/*      */   private WorkflowManager m_workflowManager;
/*  118 */   protected MultiLanguageSearchManager m_searchManager = null;
/*      */ 
/*  124 */   private ABUserManager m_userManager = null;
/*      */ 
/*  130 */   private AssetUploadedAlertProcessor m_assetUploadedAlertProcessor = null;
/*      */ 
/*  136 */   private AssetLogManager m_assetLogManager = null;
/*      */ 
/*  142 */   private DBTransactionManager m_transactionManager = null;
/*      */ 
/*      */   public void setAssetManager(AssetManager a_sAssetManager)
/*      */   {
/*  103 */     this.m_assetManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public void setFileAssetManager(FileAssetManagerImpl a_sAssetManager)
/*      */   {
/*  109 */     this.m_assetFileManager = a_sAssetManager;
/*      */   }
/*      */ 
/*      */   public void setWorkflowManager(WorkflowManager a_wm)
/*      */   {
/*  115 */     this.m_workflowManager = a_wm;
/*      */   }
/*      */ 
/*      */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*      */   {
/*  121 */     this.m_searchManager = a_searchManager;
/*      */   }
/*      */ 
/*      */   public void setUserManager(ABUserManager a_userManager)
/*      */   {
/*  127 */     this.m_userManager = a_userManager;
/*      */   }
/*      */ 
/*      */   public void setAssetUploadedAlertProcessor(AssetUploadedAlertProcessor a_processor)
/*      */   {
/*  133 */     this.m_assetUploadedAlertProcessor = a_processor;
/*      */   }
/*      */ 
/*      */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*      */   {
/*  139 */     this.m_assetLogManager = a_assetLogManager;
/*      */   }
/*      */ 
/*      */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*      */   {
/*  145 */     this.m_transactionManager = a_transactionManager;
/*      */   }
/*      */ 
/*      */   public void startup() throws Bn2Exception
/*      */   {
/*  150 */     super.startup();
/*      */ 
/*  153 */     this.m_assetManager.registerAssetSaveParticipant(this);
/*  154 */     this.m_assetManager.registerAssetDeleteParticipant(this);
/*      */   }
/*      */ 
/*      */   public void initAssetSave(AssetSaveContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/*  166 */     a_context.needOriginalAsset();
/*      */   }
/*      */ 
/*      */   public void assetWillBeDeleted(AssetDeleteContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   public void assetWasDeleted(AssetDeleteContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/*  181 */     if ((a_context != null) && (a_context.getOriginalAsset() != null) && (a_context.getOriginalAsset().getAddedByUser() != null))
/*      */     {
/*  183 */       invalidateUnsubmittedCountCache(a_context.getOriginalAsset().getAddedByUser().getId());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void save(AssetSaveContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/*  193 */     if (a_context.getWorkflowUpdate() != null)
/*      */     {
/*  196 */       WorkflowUtil.setAssetSubmissionStatus(a_context.getWorkflowUpdate(), a_context.getAsset());
/*      */ 
/*  200 */       if ((a_context.getAsset().getPermissionCategories() != null) && (!a_context.isNew()))
/*      */       {
/*  202 */         Vector vecNewPermissionCats = new Vector();
/*  203 */         for (Category a_accessLevel : a_context.getAsset().getPermissionCategories())
/*      */         {
/*  205 */           boolean bFound = false;
/*  206 */           if (a_context.getOriginalAsset() != null)
/*      */           {
/*  208 */             for (Category a_oldAccessLevel : a_context.getOriginalAsset().getPermissionCategories())
/*      */             {
/*  210 */               if (a_accessLevel.getId() == a_oldAccessLevel.getId())
/*      */               {
/*  212 */                 vecNewPermissionCats.add(a_oldAccessLevel);
/*  213 */                 bFound = true;
/*  214 */                 break;
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  220 */           if (!bFound)
/*      */           {
/*  222 */             vecNewPermissionCats.add(a_accessLevel);
/*      */           }
/*      */         }
/*  225 */         a_context.getAsset().setPermissionCategories(vecNewPermissionCats);
/*      */       }
/*      */ 
/*  228 */       if ((a_context.getAsset().getPermissionCategories() != null) && (!a_context.isNew()) && (a_context.getWorkflowUpdate().getUpdateType() == 4))
/*      */       {
/*  233 */         Set setMissing = new HashSet();
/*  234 */         for (Category cat : a_context.getOriginalAsset().getPermissionCategories())
/*      */         {
/*  236 */           Vector vecCats = a_context.getAsset().getPermissionCategoriesForWorkflow(cat.getWorkflowName());
/*  237 */           if ((!a_context.getAsset().getPermissionCategories().contains(cat)) && ((vecCats == null) || (vecCats.size() <= 0)))
/*      */           {
/*  239 */             setMissing.add(cat);
/*      */           }
/*      */         }
/*  242 */         a_context.getWorkflowUpdate().setMissingCategories(new Vector(setMissing));
/*      */       }
/*      */ 
/*  246 */       performWorkflowSetup(null, a_context.getWorkflowUpdate(), a_context.getAsset(), a_context.getOriginalAsset(), a_context.isNew());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void assetWasSaved(AssetSaveContext a_context)
/*      */     throws Bn2Exception
/*      */   {
/*      */   }
/*      */ 
/*      */   public void initiateAssetWorkflow(DBTransaction a_dbTransaction, long a_lAssetId, boolean a_bSendAlerts, long a_lUserId, long a_lSessionId, boolean a_bIgnoreApproved)
/*      */     throws Bn2Exception
/*      */   {
/*  275 */     initiateAssetWorkflowForBatch(a_dbTransaction, a_lAssetId, 0L, a_bSendAlerts, a_lUserId, a_lSessionId, a_bIgnoreApproved);
/*      */   }
/*      */ 
/*      */   public void initiateAssetWorkflowForBatch(DBTransaction a_dbTransaction, long a_lAssetId, long a_lBatchId, boolean a_bSendAlerts, long a_lUserId, long a_lSessionId, boolean a_bIgnoreApproved)
/*      */     throws Bn2Exception
/*      */   {
/*  293 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, null, false, false);
/*      */ 
/*  296 */     String sType = asset.getTypeIdAsString();
/*      */ 
/*  299 */     Vector<String> vecWorkflowNames = null;
/*      */ 
/*  301 */     if (a_bIgnoreApproved)
/*      */     {
/*  303 */       vecWorkflowNames = asset.getMissingWorkflows();
/*      */     }
/*      */     else
/*      */     {
/*  307 */       vecWorkflowNames = WorkflowUtil.getUnitialisedWorkflowsForAsset(asset);
/*      */     }
/*      */ 
/*  310 */     for (String sWFName : vecWorkflowNames)
/*      */     {
/*  313 */       WorkflowInfo wf = this.m_workflowManager.createInitialWorkflowForBatch(a_dbTransaction, sWFName, sType, asset.getId(), a_lBatchId, a_bSendAlerts);
/*  314 */       asset.addWorkflowAndSetUnapproved(wf);
/*      */     }
/*      */ 
/*  318 */     asset.setIsUnsubmitted(false);
/*      */ 
/*  321 */     saveAssetChanges(a_dbTransaction, asset, true);
/*      */ 
/*  324 */     if (AssetBankSettings.getAuditLogEnabled())
/*      */     {
/*  326 */       this.m_assetLogManager.saveWorkflowLog(asset, a_dbTransaction, a_lUserId, a_lSessionId);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void startAssetUploadAlertBatch(long a_lBatchId)
/*      */     throws Bn2Exception
/*      */   {
/*  339 */     this.m_workflowManager.startAlertProcessorBatch(this.m_assetUploadedAlertProcessor, a_lBatchId);
/*      */   }
/*      */ 
/*      */   public void sendAssetUploadAlertBatch(DBTransaction a_dbTransaction, long a_lBatchId)
/*      */     throws Bn2Exception
/*      */   {
/*  352 */     this.m_workflowManager.sendAlertProcessorBatch(a_dbTransaction, this.m_assetUploadedAlertProcessor, a_lBatchId);
/*      */   }
/*      */ 
/*      */   public void endAssetUploadAlertBatch(long a_lBatchId)
/*      */   {
/*  362 */     this.m_workflowManager.endAlertProcessorBatch(this.m_assetUploadedAlertProcessor, a_lBatchId);
/*      */   }
/*      */ 
/*      */   public TransitionableAssets getTransitionableAssetsForApprover(DBTransaction a_dbTransaction, long a_lUserId, Set<Long> a_userPermissionsCatIds, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/*  377 */     ABUser user = (ABUser)this.m_userManager.getUser(a_dbTransaction, a_lUserId);
/*      */ 
/*  379 */     TransitionableAssets list = getTransitionableAssets(a_dbTransaction, a_lUserId, user, a_userPermissionsCatIds, false, a_sWorkflowName);
/*  380 */     return list;
/*      */   }
/*      */ 
/*      */   public TransitionableAssets getTransitionableAssetsForOwner(DBTransaction a_dbTransaction, long a_lUserId, Set<Long> a_userPermissionsCatIds, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/*  396 */     TransitionableAssets list = getTransitionableAssets(a_dbTransaction, a_lUserId, null, a_userPermissionsCatIds, true, a_sWorkflowName);
/*  397 */     return list;
/*      */   }
/*      */ 
/*      */   private TransitionableAssets getTransitionableAssets(DBTransaction a_dbTransaction, long a_lUserId, ABUser a_user, Set<Long> a_userPermissionsCatIds, boolean a_bIsOwner, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/*  416 */     HashMap hmStates = new HashMap();
/*      */ 
/*  419 */     Vector<State> vecAllStates = this.m_workflowManager.getAllStates();
/*  420 */     for (State state : vecAllStates)
/*      */     {
/*  424 */       boolean bAddState = (a_bIsOwner) || ((!state.getVisibleToOwner()) && (checkUserCanActOnState(a_dbTransaction, a_user, state)));
/*      */ 
/*  426 */       if (bAddState)
/*      */       {
/*  428 */         AssetsInState ais = new AssetsInState();
/*  429 */         ais.setState(state);
/*      */ 
/*  432 */         String sKey = state.getWorkflowName() + ":" + state.getVariationName() + ":" + state.getName();
/*  433 */         hmStates.put(sKey, ais);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  439 */     int iMaxAssetsToShow = WorkflowSettings.getMaxAssetsOnList();
/*  440 */     int iNumAssetsToDo = 0;
/*  441 */     int iCountShown = 0;
/*      */ 
/*  443 */     Vector<AssetInState> vecUnapprovedAssets = new Vector();
/*      */ 
/*  445 */     if (hmStates.size() > 0)
/*      */     {
/*  449 */       if (a_bIsOwner)
/*      */       {
/*  451 */         vecUnapprovedAssets = getUnapprovedAssets(a_dbTransaction, null, 0, a_userPermissionsCatIds, a_lUserId, a_sWorkflowName);
/*      */       }
/*      */       else
/*      */       {
/*  455 */         vecUnapprovedAssets = getUnapprovedAssets(a_dbTransaction, null, 0, a_userPermissionsCatIds, 0L, a_sWorkflowName);
/*      */       }
/*      */ 
/*  459 */       for (AssetInState ais : vecUnapprovedAssets)
/*      */       {
/*  462 */         String sVariationMatch = ais.getMatchedVariationName();
/*  463 */         String sWorkflowName = ais.getWorkflowName();
/*  464 */         String sAssetState = ais.getStateName();
/*  465 */         String sKey = sWorkflowName + ":" + sVariationMatch + ":" + sAssetState;
/*      */ 
/*  468 */         if (hmStates.containsKey(sKey))
/*      */         {
/*  470 */           AssetsInState assets = (AssetsInState)hmStates.get(sKey);
/*      */ 
/*  473 */           assets.incrementNumberAssetsActual();
/*  474 */           iNumAssetsToDo++;
/*      */ 
/*  477 */           if (assets.getNumberAssets() < iMaxAssetsToShow)
/*      */           {
/*  479 */             assets.getAssetList().add(ais);
/*  480 */             iCountShown++;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  490 */     TreeSet tsAssets = new TreeSet();
/*  491 */     Iterator itHmStates = hmStates.values().iterator();
/*  492 */     while (itHmStates.hasNext())
/*      */     {
/*  494 */       AssetsInState ais = (AssetsInState)itHmStates.next();
/*      */ 
/*  496 */       if (ais.getAssetList().size() > 0)
/*      */       {
/*  498 */         tsAssets.add(ais);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  504 */     Vector vec = new Vector();
/*  505 */     String sCurrWorkflow = null;
/*  506 */     String sCurrVariation = null;
/*  507 */     VariationsInWorkflow wf = null;
/*  508 */     StatesInVariation var = null;
/*      */ 
/*  510 */     Iterator itTsStates = tsAssets.iterator();
/*  511 */     while (itTsStates.hasNext())
/*      */     {
/*  513 */       AssetsInState ais = (AssetsInState)itTsStates.next();
/*  514 */       String sWorkflow = ais.getState().getWorkflowName();
/*  515 */       String sVariation = ais.getState().getVariationName();
/*      */ 
/*  517 */       if ((sCurrVariation == null) || (!sVariation.equals(sCurrVariation)))
/*      */       {
/*  521 */         if ((sCurrWorkflow == null) || (!sWorkflow.equals(sCurrWorkflow)))
/*      */         {
/*  524 */           wf = new VariationsInWorkflow();
/*  525 */           wf.setWorkflowName(sWorkflow);
/*  526 */           vec.add(wf);
/*  527 */           sCurrWorkflow = sWorkflow;
/*      */         }
/*      */ 
/*  531 */         var = new StatesInVariation();
/*  532 */         var.setVariationName(sVariation);
/*  533 */         wf.getVariationList().add(var);
/*  534 */         sCurrVariation = sVariation;
/*      */       }
/*      */ 
/*  538 */       var.getStateList().add(ais);
/*      */     }
/*      */ 
/*  541 */     TransitionableAssets list = new TransitionableAssets();
/*  542 */     list.setWorkflowList(vec);
/*  543 */     list.setTotalSize(iNumAssetsToDo);
/*  544 */     list.setReturnSize(iCountShown);
/*      */ 
/*  546 */     return list;
/*      */   }
/*      */ 
/*      */   public WorkflowInfo changeAssetState(DBTransaction a_dbTransaction, long a_lAssetId, long a_lWorfklowInfoId, int a_iTransition, long a_lUserId, String a_sMessage, boolean a_bIsOwner, long a_lSessionId)
/*      */     throws Bn2Exception
/*      */   {
/*  561 */     DBTransaction dbTransaction = a_dbTransaction;
/*  562 */     String ksMethodName = "changeAssetState";
/*  563 */     WorkflowInfo wi = null;
/*      */     try
/*      */     {
/*  567 */       if (dbTransaction == null)
/*      */       {
/*  569 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/*  574 */       Asset asset = this.m_assetManager.getAsset(dbTransaction, a_lAssetId, null, false, false);
/*      */ 
/*  577 */       State currState = this.m_workflowManager.getCurrentState(dbTransaction, a_lWorfklowInfoId);
/*  578 */       wi = this.m_workflowManager.getWorkflowInfo(dbTransaction, a_lWorfklowInfoId);
/*  579 */       boolean bPermitted = false;
/*  580 */       ABUser user = (ABUser)this.m_userManager.getUser(dbTransaction, a_lUserId);
/*      */ 
/*  582 */       if (a_bIsOwner)
/*      */       {
/*  585 */         bPermitted = (currState.getVisibleToOwner()) && ((AssetManager.checkUserOwnsAsset(user, asset)) || (user.getIsAdmin()));
/*      */       }
/*      */       else
/*      */       {
/*  589 */         bPermitted = checkUserCanActOnState(dbTransaction, user, currState);
/*      */       }
/*      */ 
/*  592 */       if (!bPermitted)
/*      */       {
/*  594 */         String sMessage = "AssetWorkflowManager, changeAssetState: User not permitted to change asset state. UserId=" + a_lUserId + ", AssetId=" + a_lAssetId;
/*  595 */         this.m_logger.error(sMessage);
/*  596 */         throw new Bn2Exception(sMessage);
/*      */       }
/*      */ 
/*  601 */       Transition trans = currState.getTransition(a_iTransition);
/*  602 */       String sTrans = "";
/*  603 */       if (trans != null)
/*      */       {
/*  605 */         sTrans = trans.getDescription();
/*      */       }
/*  607 */       saveWorkflowAudit(dbTransaction, a_lUserId, a_lAssetId, wi.getWorkflowName(), a_sMessage, sTrans);
/*      */ 
/*  610 */       if (AssetBankSettings.getAuditLogEnabled())
/*      */       {
/*  612 */         this.m_assetLogManager.saveWorkflowLog(asset, dbTransaction, a_lUserId, a_lSessionId);
/*      */       }
/*      */ 
/*  618 */       wi = this.m_workflowManager.processTransition(dbTransaction, a_iTransition, a_lWorfklowInfoId, a_lAssetId);
/*      */ 
/*  621 */       if ((asset != null) && (asset.getAddedByUser() != null))
/*      */       {
/*  623 */         invalidateUnsubmittedCountCache(asset.getAddedByUser().getId());
/*      */       }
/*      */     }
/*      */     catch (Exception se)
/*      */     {
/*      */       try
/*      */       {
/*  630 */         if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */         {
/*  632 */           dbTransaction.rollback();
/*      */         }
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  637 */         this.m_logger.error("AssetWorkflowManager.changeAssetState: Exception commiting transaction:", sqle);
/*      */       }
/*      */ 
/*  640 */       this.m_logger.error("AssetWorkflowManager.changeAssetState:", se);
/*  641 */       throw new Bn2Exception("AssetWorkflowManager.changeAssetState:", se);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  648 */         if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */         {
/*  650 */           dbTransaction.commit();
/*      */         }
/*      */       }
/*      */       catch (SQLException sqle)
/*      */       {
/*  655 */         this.m_logger.error("AssetWorkflowManager.changeAssetState: Exception commiting transaction:", sqle);
/*  656 */         throw new Bn2Exception("AssetWorkflowManager.changeAssetState:", sqle);
/*      */       }
/*      */     }
/*      */ 
/*  660 */     return wi;
/*      */   }
/*      */ 
/*      */   public boolean transitionRequiresMessage(DBTransaction a_dbTransaction, long a_lWorkflowInfoId, int a_iTransition)
/*      */     throws Bn2Exception
/*      */   {
/*  677 */     State currState = this.m_workflowManager.getCurrentState(a_dbTransaction, a_lWorkflowInfoId);
/*  678 */     Transition trans = currState.getTransition(a_iTransition);
/*      */ 
/*  680 */     return trans.getMessageMandatory();
/*      */   }
/*      */ 
/*      */   public void approveAssetEndAllWorkflows(DBTransaction a_dbTransaction, Asset asset, boolean a_bPopulatedWorkflows)
/*      */     throws Bn2Exception
/*      */   {
/*  697 */     Vector vecWorklowIdsToRemove = null;
/*      */ 
/*  699 */     if ((a_bPopulatedWorkflows) && (asset.getWorkflows() != null))
/*      */     {
/*  702 */       Vector<WorkflowInfo> vecWorklowsToRemove = (Vector)asset.getWorkflows().clone();
/*  703 */       for (WorkflowInfo wf : vecWorklowsToRemove)
/*      */       {
/*  705 */         if (vecWorklowIdsToRemove == null)
/*      */         {
/*  707 */           vecWorklowIdsToRemove = new Vector();
/*      */         }
/*  709 */         vecWorklowIdsToRemove.add(new Long(wf.getId()));
/*  710 */         asset.removeWorkflowAndSetApproved(wf);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  715 */       Vector<Category> permissionCategories = asset.getPermissionCategories();
/*  716 */       if (permissionCategories != null)
/*      */       {
/*  719 */         for (Category cat : permissionCategories)
/*      */         {
/*  721 */           asset.setApproved(cat, true);
/*      */         }
/*      */       }
/*      */     }
/*  725 */     asset.setIsUnsubmitted(false);
/*  726 */     saveAssetChanges(a_dbTransaction, asset, true);
/*      */ 
/*  729 */     if ((vecWorklowIdsToRemove != null) && (vecWorklowIdsToRemove.size() > 0))
/*      */     {
/*  731 */       this.m_workflowManager.removeWorkflows(a_dbTransaction, vecWorklowIdsToRemove);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void updateAssetWorkflows(DBTransaction a_dbTransaction, Asset a_asset, WorkflowUpdate a_appUpdate)
/*      */     throws Bn2Exception
/*      */   {
/*  750 */     Set keys = a_appUpdate.getWorkflowApprovalUpdates().keySet();
/*  751 */     Iterator itKeys = keys.iterator();
/*  752 */     boolean bChangedWF = false;
/*  753 */     Vector vecWFIds = new Vector();
/*  754 */     Vector<String> vecForApproval = new Vector<String>();
/*  755 */     Vector vecPutLive = new Vector();
               String sWorkflowName;
/*  756 */     while (itKeys.hasNext())
/*      */     {
/*  758 */       sWorkflowName = (String)itKeys.next();
/*  759 */       int iUpdateType = ((Integer)a_appUpdate.getWorkflowApprovalUpdates().get(sWorkflowName)).intValue();
/*  760 */       if (iUpdateType == 1)
/*      */       {
/*  762 */         vecForApproval.add(sWorkflowName);
/*      */       }
/*  765 */       else if ((iUpdateType == 6) && (a_asset.getIsApprovedForWorkflow(sWorkflowName)))
/*      */       {
/*  768 */         vecForApproval.add(sWorkflowName);
/*      */       }
/*  770 */       else if ((iUpdateType == 0) || (iUpdateType == 4))
/*      */       {
/*  773 */         vecPutLive.add(sWorkflowName);
/*      */       }
/*      */ 
/*      */     }
                Category cat;
/*      */ 
/*  778 */     for (Iterator i$ = vecPutLive.iterator(); i$.hasNext(); ) { sWorkflowName = (String)i$.next();
/*      */ 
/*  780 */       if ((a_asset.getWorkflows() == null) || (!a_asset.getInWorkflow(sWorkflowName)))
/*      */       {
/*  784 */         Vector<Category> vecCats = a_asset.getPermissionCategoriesForWorkflow(sWorkflowName);
/*  785 */         if (vecCats != null)
/*      */         {
/*  787 */           for (Category cat1 : vecCats)
/*      */           {
/*  789 */             a_asset.setApproved(cat1, true);
/*  790 */             bChangedWF = true;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*  797 */       else if (a_asset.getWorkflows() != null)
/*      */       {
/*  799 */         Vector<WorkflowInfo> vecAssetWorkflows = (Vector)a_asset.getWorkflows().clone();
/*  800 */         for (WorkflowInfo wf : vecAssetWorkflows)
/*      */         {
/*  802 */           if (wf.getWorkflowName().equals(sWorkflowName))
/*      */           {
/*  804 */             a_asset.removeWorkflowAndSetApproved(wf);
/*      */ 
/*  807 */             vecWFIds.add(new Long(wf.getId()));
/*  808 */             bChangedWF = true;
/*  809 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     //String sWorkflowName;
/*  817 */     for (String sWorkflowName1 : vecForApproval)
/*      */     {
/*  820 */       if (!a_asset.getInWorkflow(sWorkflowName1))
/*      */       {
/*  822 */         WorkflowInfo wf = this.m_workflowManager.createInitialWorkflow(a_dbTransaction, sWorkflowName1, a_asset.getTypeIdAsString(), a_asset.getId(), true);
/*  823 */         a_asset.addWorkflowAndSetUnapproved(wf);
/*  824 */         bChangedWF = true;
/*      */       }
/*      */     }
/*      */     Iterator i$;
/*  829 */     if ((a_appUpdate.getMissingCategories() != null) && (a_appUpdate.getMissingCategories().size() > 0))
/*      */     {
/*  831 */       for (i$ = a_appUpdate.getMissingCategories().iterator(); i$.hasNext(); ) { cat = (Category)i$.next();
/*      */ 
/*  834 */         if (a_asset.getWorkflows() != null)
/*      */         {
/*  836 */           Vector<WorkflowInfo> vecAssetWorkflows = (Vector)a_asset.getWorkflows().clone();
/*  837 */           for (WorkflowInfo wf : vecAssetWorkflows)
/*      */           {
/*  839 */             if (wf.getWorkflowName().equals(cat.getWorkflowName()))
/*      */             {
/*  841 */               a_asset.removeWorkflowAndSetApproved(wf);
/*  842 */               vecWFIds.add(new Long(wf.getId()));
/*  843 */               bChangedWF = true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     //Category cat;
/*  851 */     if (bChangedWF)
/*      */     {
/*  853 */       saveAssetChanges(a_dbTransaction, a_asset, true);
/*      */     }
/*      */ 
/*  857 */     this.m_workflowManager.removeWorkflows(a_dbTransaction, vecWFIds);
/*      */   }
/*      */ 
/*      */   public void approveAssetEndWorkflow(DBTransaction a_dbTransaction, long a_lAssetId, long a_lWorkflowInfoId)
/*      */     throws Bn2Exception
/*      */   {
/*  873 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, null, false, false);
/*  874 */     WorkflowInfo wf = this.m_workflowManager.getWorkflowInfo(a_dbTransaction, a_lWorkflowInfoId);
/*      */ 
/*  877 */     asset.removeWorkflowAndSetApproved(wf);
/*      */ 
/*  880 */     asset.setIsUnsubmitted(false);
/*      */ 
/*  883 */     saveAssetChanges(a_dbTransaction, asset, true);
/*      */ 
/*  886 */     this.m_workflowManager.removeWorkflow(a_dbTransaction, a_lWorkflowInfoId);
/*      */   }
/*      */ 
/*      */   public void removeAssetWorkflow(DBTransaction a_dbTransaction, long a_lWorkflowInfoId)
/*      */     throws Bn2Exception
/*      */   {
/*  902 */     this.m_workflowManager.removeWorkflow(a_dbTransaction, a_lWorkflowInfoId);
/*      */   }
/*      */ 
/*      */   public void deleteAsset(DBTransaction a_dbTransaction, long a_lAssetId, long a_lWorkflowInfoId)
/*      */     throws Bn2Exception
/*      */   {
/*  917 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, a_lAssetId, null, false, false);
/*      */ 
/*  920 */     Vector vecApprovedAL = asset.getApprovedPermissionCategories();
/*  921 */     Vector vecWorkflowsToRemove = new Vector();
/*      */ 
/*  924 */     if (((asset.getWorkflows() != null) && (asset.getWorkflows().size() > 1)) || ((vecApprovedAL != null) && (vecApprovedAL.size() > 0)))
/*      */     {
/*  928 */       WorkflowInfo wf = null;
/*  929 */       if (asset.getWorkflows() != null)
/*      */       {
/*  931 */         for (int i = 0; i < asset.getWorkflows().size(); i++)
/*      */         {
/*  933 */           wf = (WorkflowInfo)asset.getWorkflows().elementAt(i);
/*  934 */           if (wf.getId() != a_lWorkflowInfoId)
/*      */             continue;
/*  936 */           vecWorkflowsToRemove.add(new Long(wf.getId()));
/*  937 */           asset.getWorkflows().remove(wf);
/*  938 */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  943 */       Vector vecUnapprovedPC = asset.getUnapprovedPermissionCategories();
/*  944 */       if ((vecUnapprovedPC != null) && (wf != null))
/*      */       {
/*  946 */         for (int i = 0; i < vecUnapprovedPC.size(); i++)
/*      */         {
/*  948 */           Category cat = (Category)vecUnapprovedPC.elementAt(i);
/*  949 */           if (!cat.getWorkflowName().equals(wf.getWorkflowName()))
/*      */             continue;
/*  951 */           asset.getPermissionCategories().remove(cat);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  957 */       saveAssetChanges(a_dbTransaction, asset, true, true);
/*      */     }
/*  959 */     else if (asset.getWorkflows().size() == 1)
/*      */     {
/*  961 */       this.m_assetManager.deleteAsset(a_dbTransaction, a_lAssetId);
/*      */     }
/*      */ 
/*  965 */     if (vecWorkflowsToRemove.size() > 0)
/*      */     {
/*  967 */       this.m_workflowManager.removeWorkflows(a_dbTransaction, vecWorkflowsToRemove);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector getAssetsForBatchUpdate(DBTransaction a_dbTransaction, String a_sStateName, int a_iMaxResults, Set<Long> a_userPermissionCatIds, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/*  982 */     return getUnapprovedAssets(a_dbTransaction, a_sStateName, a_iMaxResults, a_userPermissionCatIds, 0L, a_sWorkflowName);
/*      */   }
/*      */ 
/*      */   public Vector getAssetsForBatchUpdateOwner(DBTransaction a_dbTransaction, String a_sStateName, int a_iMaxResults, Set<Long> a_userPermissions, long a_lUserId, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/* 1000 */     return getUnapprovedAssets(a_dbTransaction, a_sStateName, a_iMaxResults, a_userPermissions, a_lUserId, a_sWorkflowName);
/*      */   }
/*      */ 
/*      */   private Vector<AssetInState> getUnapprovedAssets(DBTransaction a_dbTransaction, String a_sStateName, int a_iMaxResults, Set<Long> a_userPermissionCatIds, long a_lOwnerUserId, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/* 1020 */     Vector vecAssets = new Vector();
/*      */ 
/* 1022 */     String ksMethodName = "getUnapprovedAssets";
/* 1023 */     Connection con = null;
/* 1024 */     String sSQL = null;
/* 1025 */     PreparedStatement psql = null;
/*      */     try
/*      */     {
/* 1029 */       con = a_dbTransaction.getConnection();
/* 1030 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 1033 */       sSQL = "SELECT Id FROM WorkflowInfo";
/* 1034 */       if (a_sWorkflowName != null)
/*      */       {
/* 1036 */         sSQL = sSQL + " WHERE WorkflowName=?";
/*      */       }
/* 1038 */       psql = con.prepareStatement(sSQL);
/* 1039 */       if (a_sWorkflowName != null)
/*      */       {
/* 1041 */         psql.setString(1, a_sWorkflowName);
/*      */       }
/* 1043 */       ResultSet rs = psql.executeQuery();
/* 1044 */       Vector vecIds = new Vector();
/* 1045 */       while (rs.next())
/*      */       {
/* 1047 */         vecIds.add(Long.valueOf(rs.getLong("Id")));
/*      */       }
/* 1049 */       psql.close();
/*      */ 
/* 1051 */       if (vecIds.size() > 0)
/*      */       {
/* 1055 */         sSQL = "SELECT ass.Id assId, ass.AssetTypeId, ass.ThumbnailFileLocation, ass.Price, ass.DateAdded assDateAdded, u.Id userId, u.Username, u.Forename, u.Surname, u.EmailAddress, wi.Id wfId, wi.WorkflowName, wi.VariationName, wi.StateName, wi.LastStateChangeDate, aic.CategoryId alId, wa.Message waMessage, wa.DateAdded waDateAdded, wau.Username wauUsername, wau.Forename wauForename, wau.Surname wauSurname FROM Asset ass INNER JOIN CM_ItemInCategory aic ON ass.Id = aic.ItemId INNER JOIN CM_Category cat ON cat.Id = aic.CategoryId INNER JOIN AssetBankUser u ON ass.AddedByUserId = u.Id INNER JOIN WorkflowInfo wi ON ass.Id=wi.WorkflowableEntityId LEFT JOIN AssetWorkflowAudit wa ON ass.Id = wa.AssetId ";
/*      */ 
/* 1083 */         if (a_sWorkflowName != null)
/*      */         {
/* 1085 */           sSQL = sSQL + "AND wa.WorkflowName=? ";
/*      */         }
/*      */ 
/* 1088 */         String sWorkflowIdsStatement = sqlGenerator.getIdInStatement(vecIds, "wi.Id");
/*      */ 
/* 1090 */         sSQL = sSQL + "LEFT JOIN AssetBankUser wau ON wa.UserId = wau.Id " + "WHERE " + sWorkflowIdsStatement + "AND CategoryTypeId = " + 2L + " " + "AND (ass.CurrentVersionId <= 0 OR " + sqlGenerator.getNullCheckStatement("ass.CurrentVersionId") + ") ";
/*      */ 
/* 1095 */         if (StringUtil.stringIsPopulated(a_sStateName))
/*      */         {
/* 1097 */           sSQL = sSQL + "AND wi.StateName = ? ";
/*      */         }
/*      */ 
/* 1100 */         if (a_lOwnerUserId > 0L)
/*      */         {
/* 1102 */           sSQL = sSQL + "AND ass.AddedByUserId = ? ";
/*      */         }
/*      */ 
/* 1105 */         sSQL = sSQL + " ORDER BY wi.StateName, wi.WorkflowName, wi.VariationName, ass.id, aic.CategoryId, wa.Id DESC";
/*      */ 
/* 1109 */         psql = con.prepareStatement(sSQL);
/*      */ 
/* 1111 */         int iCol = 1;
/* 1112 */         if (a_sWorkflowName != null)
/*      */         {
/* 1114 */           psql.setString(iCol++, a_sWorkflowName);
/*      */         }
/*      */ 
/* 1117 */         if (StringUtil.stringIsPopulated(a_sStateName))
/*      */         {
/* 1119 */           psql.setString(iCol++, a_sStateName);
/*      */         }
/*      */ 
/* 1122 */         if (a_lOwnerUserId > 0L)
/*      */         {
/* 1124 */           psql.setLong(iCol++, a_lOwnerUserId);
/*      */         }
/*      */ 
/* 1127 */         rs = psql.executeQuery();
/*      */ 
/* 1129 */         AssetInState ais = null;
/* 1130 */         long lLastAssetId = 0L;
/* 1131 */         long lLastAccessLevelId = 0L;
/* 1132 */         int iNumAssets = 0;
/*      */ 
/* 1134 */         while (rs.next())
/*      */         {
/* 1136 */           long lAssetId = rs.getLong("assId");
/* 1137 */           long lAccessLevelId = rs.getLong("alId");
/*      */ 
/* 1139 */           if ((lAccessLevelId == lLastAccessLevelId) && (lAssetId == lLastAssetId))
/*      */           {
/*      */             continue;
/*      */           }
/*      */ 
/* 1146 */           lLastAccessLevelId = lAccessLevelId;
/*      */ 
/* 1148 */           if (lAssetId != lLastAssetId)
/*      */           {
/* 1151 */             lLastAssetId = lAssetId;
/*      */ 
/* 1154 */             if ((a_iMaxResults > 0) && (iNumAssets == a_iMaxResults)) {
/*      */               break;
/*      */             }
/* 1157 */             if ((ais != null) && (AssetManager.userHasRequiredPermissions(ais.getPermissionCategoryIds(), a_userPermissionCatIds)))
/*      */             {
/* 1159 */               vecAssets.add(ais);
/* 1160 */               iNumAssets++;
/*      */             }
/*      */ 
/* 1163 */             ais = new AssetInState();
/*      */ 
/* 1166 */             ais.setStateName(rs.getString("StateName"));
/* 1167 */             ais.setWorkflowName(rs.getString("WorkflowName"));
/* 1168 */             ais.setWorkflowInfoId(rs.getLong("wfId"));
/* 1169 */             ais.setMatchedVariationName(rs.getString("VariationName"));
/* 1170 */             ais.setDateAdded(rs.getDate("assDateAdded"));
/*      */ 
/* 1173 */             ais.setAssetId(lAssetId);
/*      */ 
/* 1176 */             if (StringUtil.stringIsPopulated(rs.getString("ThumbnailFileLocation")))
/*      */             {
/* 1178 */               ais.setThumbnailImageFile(new ImageFile(rs.getString("ThumbnailFileLocation")));
/*      */             }
/*      */             else
/*      */             {
/* 1182 */               ais.setThumbnailImageFile(new ImageFile(AssetBankSettings.getNoFileThumbnail()));
/*      */             }
/*      */ 
/* 1186 */             String sUserName = rs.getString("Forename") + " " + rs.getString("Surname");
/* 1187 */             if (!StringUtil.stringIsPopulated(sUserName))
/*      */             {
/* 1189 */               sUserName = rs.getString("Username");
/*      */             }
/* 1191 */             ais.setUserName(sUserName);
/* 1192 */             ais.setUserEmail(rs.getString("EmailAddress"));
/*      */ 
/* 1195 */             ais.getAuditEntry().setMessage(rs.getString("waMessage"));
/* 1196 */             ais.getAuditEntry().setName(rs.getString("wauForename") + " " + rs.getString("wauSurname"));
/* 1197 */             ais.getAuditEntry().setUsername(rs.getString("wauUsername"));
/* 1198 */             ais.getAuditEntry().setDateAdded(rs.getTimestamp("waDateAdded"));
/*      */           }
/*      */ 
/* 1203 */           ais.getPermissionCategoryIds().add(Long.valueOf(rs.getLong("alId")));
/*      */         }
/*      */ 
/* 1207 */         if ((ais != null) && ((a_iMaxResults == 0) || (iNumAssets < a_iMaxResults)) && (AssetManager.userHasRequiredPermissions(ais.getPermissionCategoryIds(), a_userPermissionCatIds)))
/*      */         {
/* 1211 */           vecAssets.add(ais);
/*      */         }
/*      */ 
/* 1214 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1220 */       throw new Bn2Exception("AssetWorkflowManager.getUnapprovedAssets: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1223 */     return vecAssets;
/*      */   }
/*      */ 
/*      */   public Vector<StringDataBean> getUsersWithSubmittedAssets(DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1237 */     String ksMethodName = "getUsersWithSubmittedAssets";
/* 1238 */     Vector vecResults = new Vector();
/*      */     try
/*      */     {
/* 1242 */       Connection con = a_dbTransaction.getConnection();
/*      */ 
/* 1245 */       String sSql = "SELECT u.Id userId, Forename, Surname, Username FROM AssetBankUser u WHERE u.Id IN (SELECT AddedByUserId FROM Asset a JOIN CM_ItemInCategory iic ON a.Id=iic.ItemId WHERE iic.IsApproved=?)";
/*      */ 
/* 1251 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 1252 */       psql.setBoolean(1, false);
/* 1253 */       ResultSet rs = psql.executeQuery();
/* 1254 */       populateUsersFromRs(rs, vecResults);
/* 1255 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/* 1259 */       this.m_logger.error("AssetWorkflowManager.getUsersWithSubmittedAssets - " + sqe);
/* 1260 */       throw new Bn2Exception("AssetWorkflowManager.getUsersWithSubmittedAssets", sqe);
/*      */     }
/*      */ 
/* 1263 */     return vecResults;
/*      */   }
/*      */ 
/*      */   public static void populateUsersFromRs(ResultSet rs, Vector<StringDataBean> vecResults)
/*      */     throws SQLException
/*      */   {
/* 1276 */     while (rs.next())
/*      */     {
/* 1278 */       StringDataBean user = new StringDataBean();
/* 1279 */       user.setId(rs.getLong("userId"));
/* 1280 */       String sFullname = rs.getString("Forename") + " " + rs.getString("Surname");
/*      */ 
/* 1282 */       String sUsername = rs.getString("Username");
/* 1283 */       if (StringUtil.stringIsPopulated(sUsername))
/*      */       {
/* 1285 */         if (StringUtil.stringIsPopulated(sFullname))
/*      */         {
/* 1287 */           sFullname = sFullname + " ";
/*      */         }
/*      */ 
/* 1290 */         sFullname = sFullname + "(" + sUsername + ")";
/*      */       }
/*      */ 
/* 1293 */       user.setName(sFullname);
/*      */ 
/* 1295 */       vecResults.add(user);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean checkUserCanActOnState(DBTransaction a_dbTransaction, ABUser a_user, State a_state)
/*      */     throws Bn2Exception
/*      */   {
/* 1313 */     boolean bSuccess = false;
/*      */ 
/* 1316 */     if (a_user.getIsAdmin())
/*      */     {
/* 1318 */       return true;
/*      */     }
/*      */ 
/* 1321 */     String sVisibleTo = a_state.getVisibleTo();
/* 1322 */     String sWorkflowName = a_state.getWorkflowName();
/*      */ 
/* 1327 */     sVisibleTo = substituteApproversWithWorkflowApprovers(a_dbTransaction, sVisibleTo, sWorkflowName);
/*      */ 
/* 1330 */     long[] alIds = StringUtil.getIdsArray(sVisibleTo);
/*      */ 
/* 1333 */     for (int i = 0; i < alIds.length; i++)
/*      */     {
/* 1335 */       int iMatchGroupId = (int)alIds[i];
/*      */ 
/* 1337 */       if (!a_user.getIsInGroup(iMatchGroupId))
/*      */         continue;
/* 1339 */       bSuccess = true;
/* 1340 */       break;
/*      */     }
/*      */ 
/* 1344 */     return bSuccess;
/*      */   }
/*      */ 
/*      */   public String substituteApproversWithAssetApprovers(DBTransaction a_dbTransaction, String a_sToList, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1359 */     String sSub = a_sToList;
/*      */ 
/* 1362 */     if (sSub.contains("approvers"))
/*      */     {
/* 1369 */       String sApproverGroupIds = getApprovalGroupsForAsset(a_dbTransaction, a_lAssetId);
/*      */ 
/* 1371 */       sSub = sSub.replace("approvers", sApproverGroupIds);
/*      */     }
/*      */ 
/* 1374 */     return sSub;
/*      */   }
/*      */ 
/*      */   private String getApprovalGroupsForAsset(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1389 */     String ksMethodName = "getApprovalGroupsForAsset";
/* 1390 */     Connection con = null;
/* 1391 */     String sSQL = null;
/* 1392 */     PreparedStatement psql = null;
/* 1393 */     ResultSet rs = null;
/* 1394 */     String sIds = "";
/*      */     try
/*      */     {
/* 1398 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1400 */       sSQL = "SELECT cat.Id catId, UserGroupId FROM CM_Category cat INNER JOIN CategoryVisibleToGroup cvg ON cat.Id = cvg.CategoryId INNER JOIN CM_ItemInCategory aic ON aic.CategoryId = cat.Id WHERE cat.CategoryTypeId = ? AND cvg.CanApproveAssetUploads = 1 AND aic.ItemId = ? ORDER BY cat.Id, UserGroupId";
/*      */ 
/* 1409 */       psql = con.prepareStatement(sSQL);
/* 1410 */       psql.setLong(1, 2L);
/* 1411 */       psql.setLong(2, a_lAssetId);
/* 1412 */       rs = psql.executeQuery();
/*      */ 
/* 1415 */       HashMap hmGroupIds = new HashMap();
/* 1416 */       HashMap hmLastGroupIds = null;
/* 1417 */       long lLastAccessLevelId = 0L;
/* 1418 */       boolean bFirst = true;
/*      */ 
/* 1420 */       while (rs.next())
/*      */       {
/* 1422 */         long lAccessLevelId = rs.getLong("catId");
/* 1423 */         long lGroupId = rs.getLong("UserGroupId");
/*      */ 
/* 1425 */         if (lLastAccessLevelId != lAccessLevelId)
/*      */         {
/* 1429 */           if (AssetBankSettings.getUserRequiresAllAccessLevelsToViewAsset())
/*      */           {
/* 1433 */             hmLastGroupIds = hmGroupIds;
/* 1434 */             hmGroupIds = new HashMap();
/*      */           }
/*      */         }
/*      */ 
/* 1438 */         if (AssetBankSettings.getUserRequiresAllAccessLevelsToViewAsset())
/*      */         {
/* 1441 */           if ((hmLastGroupIds.containsKey(new Long(lGroupId))) || (bFirst))
/*      */           {
/* 1443 */             hmGroupIds.put(new Long(lGroupId), "");
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1449 */           hmGroupIds.put(new Long(lGroupId), "");
/*      */         }
/* 1451 */         bFirst = false;
/*      */       }
/*      */ 
/* 1454 */       psql.close();
/*      */ 
/* 1457 */       Iterator it = hmGroupIds.keySet().iterator();
/* 1458 */       while (it.hasNext())
/*      */       {
/* 1460 */         if (sIds.length() > 0)
/*      */         {
/* 1462 */           sIds = sIds + ",";
/*      */         }
/*      */ 
/* 1465 */         long lGroupId = ((Long)it.next()).longValue();
/* 1466 */         sIds = sIds + lGroupId;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1474 */       throw new Bn2Exception("AssetWorkflowManager.getApprovalGroupsForAsset: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1478 */     return sIds;
/*      */   }
/*      */ 
/*      */   public String substituteApproversWithWorkflowApprovers(DBTransaction a_dbTransaction, String a_sToList, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/* 1493 */     String sSub = a_sToList;
/*      */ 
/* 1496 */     if (sSub.contains("approvers"))
/*      */     {
/* 1503 */       String sApproverGroupIds = getApprovalGroupsForWorkflow(a_dbTransaction, a_sWorkflowName);
/*      */ 
/* 1505 */       sSub = sSub.replace("approvers", sApproverGroupIds);
/*      */     }
/*      */ 
/* 1508 */     return sSub;
/*      */   }
/*      */ 
/*      */   private String getApprovalGroupsForWorkflow(DBTransaction a_dbTransaction, String a_sWorkflowName)
/*      */     throws Bn2Exception
/*      */   {
/* 1524 */     String ksMethodName = "getApprovalGroupsForWorkflow";
/* 1525 */     Connection con = null;
/* 1526 */     String sSQL = null;
/* 1527 */     PreparedStatement psql = null;
/* 1528 */     ResultSet rs = null;
/* 1529 */     String sIds = "";
/*      */     try
/*      */     {
/* 1533 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1535 */       sSQL = "SELECT UserGroupId FROM CM_Category cat INNER JOIN CategoryVisibleToGroup cvg ON cat.Id = cvg.CategoryId WHERE cat.CategoryTypeId = ? AND cvg.CanApproveAssetUploads = 1 ";
/*      */ 
/* 1542 */       if (a_sWorkflowName.equals("default"))
/*      */       {
/* 1544 */         sSQL = sSQL + "AND (cat.WorkflowName = ? OR cat.WorkflowName = '' OR cat.WorkflowName IS NULL) ";
/*      */       }
/*      */       else
/*      */       {
/* 1548 */         sSQL = sSQL + "AND cat.WorkflowName = ? ";
/*      */       }
/*      */ 
/* 1551 */       psql = con.prepareStatement(sSQL);
/* 1552 */       psql.setLong(1, 2L);
/* 1553 */       psql.setString(2, a_sWorkflowName);
/* 1554 */       rs = psql.executeQuery();
/*      */ 
/* 1556 */       while (rs.next())
/*      */       {
/* 1558 */         if (sIds.length() > 0)
/*      */         {
/* 1560 */           sIds = sIds + ",";
/*      */         }
/*      */ 
/* 1563 */         long lGroupId = rs.getLong("UserGroupId");
/* 1564 */         sIds = sIds + lGroupId;
/*      */       }
/*      */ 
/* 1567 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1572 */       throw new Bn2Exception("AssetWorkflowManager.getApprovalGroupsForWorkflow: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1576 */     return sIds;
/*      */   }
/*      */ 
/*      */   public Vector getApprovableWorkflowBeansForUser(DBTransaction a_dbTransaction, long a_lUserId, boolean a_bIsAdmin)
/*      */     throws Bn2Exception
/*      */   {
/* 1584 */     Vector vecStrings = getApprovableWorkflowsForUser(a_dbTransaction, a_lUserId, a_bIsAdmin);
/* 1585 */     Vector vecBeans = null;
/* 1586 */     if (vecStrings != null)
/*      */     {
/* 1588 */       for (int i = 0; i < vecStrings.size(); i++)
/*      */       {
/* 1590 */         String sName = (String)vecStrings.elementAt(i);
/* 1591 */         Workflow wf = this.m_workflowManager.getWorkflowBean(sName);
/* 1592 */         if (vecBeans == null)
/*      */         {
/* 1594 */           vecBeans = new Vector();
/*      */         }
/* 1596 */         vecBeans.add(wf);
/*      */       }
/*      */     }
/* 1599 */     return vecBeans;
/*      */   }
/*      */ 
/*      */   public Vector<String> getApprovableWorkflowsForUser(DBTransaction a_dbTransaction, long a_lUserId, boolean a_bIsAdmin)
/*      */     throws Bn2Exception
/*      */   {
/* 1614 */     String ksMethodName = "getApprovableWorkflowsForUser";
/* 1615 */     Connection con = null;
/* 1616 */     String sSQL = null;
/* 1617 */     PreparedStatement psql = null;
/* 1618 */     ResultSet rs = null;
/* 1619 */     Vector vecWorkflows = null;
/*      */     try
/*      */     {
/* 1623 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1625 */       if (a_bIsAdmin)
/*      */       {
/* 1628 */         vecWorkflows = this.m_workflowManager.getWorkflows();
/*      */       }
/*      */       else
/*      */       {
/* 1632 */         sSQL = "SELECT DISTINCT c.WorkflowName FROM CM_Category c LEFT JOIN CategoryVisibleToGroup cvg ON c.Id = cvg.CategoryId LEFT JOIN UserGroup ug ON cvg.UserGroupId = ug.Id LEFT JOIN UserInGroup uig ON ug.Id = uig.UserGroupId WHERE uig.UserId=? AND cvg.CanApproveAssetUploads = ? AND c.CategoryTypeId=?";
/*      */ 
/* 1637 */         psql = con.prepareStatement(sSQL);
/* 1638 */         psql.setLong(1, a_lUserId);
/* 1639 */         psql.setBoolean(2, true);
/* 1640 */         psql.setLong(3, 2L);
/* 1641 */         rs = psql.executeQuery();
/*      */ 
/* 1643 */         while (rs.next())
/*      */         {
/* 1645 */           if (vecWorkflows == null)
/*      */           {
/* 1647 */             vecWorkflows = new Vector();
/*      */           }
/* 1649 */           vecWorkflows.add(rs.getString("WorkflowName"));
/*      */         }
/*      */ 
/* 1652 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1658 */       throw new Bn2Exception("AssetWorkflowManager.getApprovableWorkflowsForUser: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1662 */     return vecWorkflows;
/*      */   }
/*      */ 
/*      */   private void saveAssetChanges(DBTransaction a_dbTransaction, Asset a_asset, boolean a_bWorkflowChanges)
/*      */     throws Bn2Exception
/*      */   {
/* 1670 */     saveAssetChanges(a_dbTransaction, a_asset, a_bWorkflowChanges, false);
/*      */   }
/*      */ 
/*      */   private void saveAssetChanges(DBTransaction a_dbTransaction, Asset a_asset, boolean a_bWorkflowChanges, boolean a_bUpdateCategories)
/*      */     throws Bn2Exception
/*      */   {
/* 1684 */     DBTransaction dbTransaction = a_dbTransaction;
/*      */     try
/*      */     {
/* 1687 */       if (dbTransaction == null)
/*      */       {
/* 1689 */         dbTransaction = this.m_transactionManager.getNewTransaction();
/*      */       }
/*      */ 
/* 1692 */       Connection con = dbTransaction.getConnection();
/* 1693 */       if (a_bUpdateCategories)
/*      */       {
/* 1695 */         this.m_assetFileManager.saveAssetCategoriesToDatabase(a_asset, con, a_asset.getPermissionCategories(), 2L);
/*      */       }
/*      */ 
/* 1698 */       String sSQL = "UPDATE Asset SET IsUnsubmitted=? WHERE Id=?";
/* 1699 */       PreparedStatement psql = con.prepareStatement(sSQL);
/* 1700 */       psql.setBoolean(1, a_asset.getIsUnsubmitted());
/* 1701 */       psql.setLong(2, a_asset.getId());
/* 1702 */       psql.executeUpdate();
/* 1703 */       psql.close();
/*      */ 
/* 1705 */       if (a_bWorkflowChanges)
/*      */       {
/* 1708 */         if (a_asset.getPermissionCategories() != null)
/*      */         {
/* 1710 */           String sApprovedCatIds = "";
/* 1711 */           String sUnnapprovedCatIds = "";
/* 1712 */           for (Category cat : a_asset.getPermissionCategories())
/*      */           {
/* 1714 */             Category restrictiveCat = cat.getClosestRestrictiveAncestor();
/*      */ 
/* 1716 */             if (a_asset.isApproved(restrictiveCat))
/*      */             {
/* 1718 */               sApprovedCatIds = sApprovedCatIds + cat.getId() + ",";
/*      */             }
/*      */             else
/*      */             {
/* 1722 */               sUnnapprovedCatIds = sUnnapprovedCatIds + cat.getId() + ",";
/*      */             }
/*      */           }
/*      */ 
/* 1726 */           if (StringUtil.stringIsPopulated(sApprovedCatIds))
/*      */           {
/* 1728 */             sApprovedCatIds = sApprovedCatIds.substring(0, sApprovedCatIds.length() - 1);
/*      */ 
/* 1730 */             sSQL = "UPDATE CM_ItemInCategory SET IsApproved=1 WHERE ItemId=? AND CategoryId IN (" + sApprovedCatIds + ")";
/* 1731 */             psql = con.prepareStatement(sSQL);
/* 1732 */             psql.setLong(1, a_asset.getId());
/* 1733 */             psql.executeUpdate();
/* 1734 */             psql.close();
/*      */           }
/* 1736 */           if (StringUtil.stringIsPopulated(sUnnapprovedCatIds))
/*      */           {
/* 1738 */             sUnnapprovedCatIds = sUnnapprovedCatIds.substring(0, sUnnapprovedCatIds.length() - 1);
/*      */ 
/* 1740 */             sSQL = "UPDATE CM_ItemInCategory SET IsApproved=0 WHERE ItemId=? AND CategoryId IN (" + sUnnapprovedCatIds + ")";
/* 1741 */             psql = con.prepareStatement(sSQL);
/* 1742 */             psql.setLong(1, a_asset.getId());
/* 1743 */             psql.executeUpdate();
/* 1744 */             psql.close();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1749 */         this.m_assetManager.clearAssetCaches();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       throw new Bn2Exception("AssetWorkflowManager.saveAssetChanges: Error: " + e.getMessage(), e);
/*      */     }
/*      */     finally
/*      */     {
/* 1765 */       if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1769 */           dbTransaction.commit();
/*      */         }
/*      */         catch (Exception ex) {
/*      */         }
/*      */       }
/*      */     }
/* 1775 */     a_asset.forceApprovalStatusRecalculation();
/* 1776 */     this.m_searchManager.indexDocument(a_asset, true);
/*      */ 
/* 1779 */     if ((a_asset != null) && (a_asset.getAddedByUser() != null))
/*      */     {
/* 1781 */       invalidateUnsubmittedCountCache(a_asset.getAddedByUser().getId());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void saveWorkflowAudit(DBTransaction a_dbTransaction, long a_lUserId, long a_lAssetId, String a_sWorkflowName, String a_sMessage, String a_sTransition)
/*      */     throws Bn2Exception
/*      */   {
/* 1797 */     String ksMethodName = "saveWorkflowAudit";
/* 1798 */     Connection con = null;
/*      */     try
/*      */     {
/* 1802 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1805 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 1807 */       long lNewId = 0L;
/* 1808 */       String sSql = "INSERT INTO AssetWorkflowAudit (";
/*      */ 
/* 1810 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1812 */         lNewId = sqlGenerator.getUniqueId(con, "WorkflowAuditSequence");
/* 1813 */         sSql = sSql + "Id,";
/*      */       }
/*      */ 
/* 1816 */       sSql = sSql + "UserId, AssetId, Message, DateAdded, Transition, WorkflowName) VALUES (?,?,?,?,?,?";
/*      */ 
/* 1818 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1820 */         sSql = sSql + ",?";
/*      */       }
/*      */ 
/* 1823 */       sSql = sSql + ")";
/*      */ 
/* 1825 */       int iCol = 1;
/* 1826 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 1828 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1830 */         psql.setLong(iCol++, lNewId);
/*      */       }
/*      */ 
/* 1833 */       psql.setLong(iCol++, a_lUserId);
/* 1834 */       psql.setLong(iCol++, a_lAssetId);
/* 1835 */       psql.setString(iCol++, a_sMessage);
/* 1836 */       psql.setTimestamp(iCol++, new Timestamp(new Date().getTime()));
/* 1837 */       psql.setString(iCol++, a_sTransition);
/* 1838 */       psql.setString(iCol++, a_sWorkflowName);
/* 1839 */       psql.executeUpdate();
/* 1840 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1846 */       throw new Bn2Exception("AssetWorkflowManager.saveWorkflowAudit: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteWorkflowAudit(DBTransaction a_dbTransaction, long a_lWorkflowAuditId)
/*      */     throws Bn2Exception
/*      */   {
/* 1861 */     String ksMethodName = "deleteWorkflowAudit";
/* 1862 */     Connection con = null;
/*      */     try
/*      */     {
/* 1866 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1868 */       String sSql = "DELETE FROM AssetWorkflowAudit";
/*      */ 
/* 1870 */       if (a_lWorkflowAuditId > 0L)
/*      */       {
/* 1872 */         sSql = sSql + " WHERE Id=?";
/*      */       }
/*      */ 
/* 1875 */       PreparedStatement psql = con.prepareStatement(sSql);
/*      */ 
/* 1877 */       if (a_lWorkflowAuditId > 0L)
/*      */       {
/* 1879 */         psql.setLong(1, a_lWorkflowAuditId);
/*      */       }
/*      */ 
/* 1882 */       psql.executeUpdate();
/* 1883 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1889 */       throw new Bn2Exception("AssetWorkflowManager.deleteWorkflowAudit: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Vector<AssetWorkflowAuditEntry> getWorkflowAudit(DBTransaction a_dbTransaction, long a_lAssetId)
/*      */     throws Bn2Exception
/*      */   {
/* 1899 */     String ksMethodName = "getWorkflowAudit";
/* 1900 */     Connection con = null;
/* 1901 */     String sSQL = null;
/* 1902 */     PreparedStatement psql = null;
/* 1903 */     ResultSet rs = null;
/*      */ 
/* 1905 */     Vector workflowAudits = new Vector();
/*      */     try
/*      */     {
/* 1909 */       con = a_dbTransaction.getConnection();
/*      */ 
/* 1911 */       sSQL = "SELECT wa.Id waId, WorkflowName, Message, DateAdded, Transition, Forename, Surname, Username FROM AssetWorkflowAudit wa INNER JOIN AssetBankUser u ON wa.UserId = u.Id WHERE wa.AssetId = ? ORDER BY waId DESC";
/*      */ 
/* 1917 */       psql = con.prepareStatement(sSQL);
/* 1918 */       psql.setLong(1, a_lAssetId);
/* 1919 */       rs = psql.executeQuery();
/*      */ 
/* 1921 */       while (rs.next())
/*      */       {
/* 1923 */         AssetWorkflowAuditEntry workflowAudit = new AssetWorkflowAuditEntry();
/*      */ 
/* 1925 */         workflowAudit.setId(rs.getLong("waId"));
/* 1926 */         workflowAudit.setMessage(rs.getString("Message"));
/* 1927 */         workflowAudit.setWorkflowName(rs.getString("WorkflowName"));
/* 1928 */         workflowAudit.setTransition(rs.getString("Transition"));
/* 1929 */         workflowAudit.setDateAdded(rs.getTimestamp("DateAdded"));
/* 1930 */         workflowAudit.setAssetId(a_lAssetId);
/* 1931 */         workflowAudit.setName(rs.getString("Forename") + " " + rs.getString("Surname"));
/* 1932 */         workflowAudit.setUsername(rs.getString("Username"));
/*      */ 
/* 1934 */         workflowAudits.add(workflowAudit);
/*      */       }
/*      */ 
/* 1937 */       psql.close();
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1942 */       throw new Bn2Exception("AssetWorkflowManager.getWorkflowAudit: Exception occurred: " + e, e);
/*      */     }
/*      */ 
/* 1946 */     return workflowAudits;
/*      */   }
/*      */ 
/*      */   public void generateAssetWorkflows()
/*      */     throws Bn2Exception
/*      */   {
/* 1959 */     String ksMethodName = "generateAssetWorkflows";
/* 1960 */     Connection con = null;
/* 1961 */     String sSQL = null;
/* 1962 */     PreparedStatement psql = null;
/* 1963 */     DBTransaction transaction = null;
/*      */     try
/*      */     {
/* 1967 */       transaction = this.m_transactionManager.getNewTransaction();
/* 1968 */       con = transaction.getConnection();
/*      */ 
/* 1972 */       sSQL = "SELECT a.Id, wf.Id wfId FROM Asset a LEFT JOIN CM_ItemInCategory iic ON a.Id=iic.Itemid LEFT JOIN CM_Category c ON iic.CategoryId=c.Id LEFT JOIN WorkflowInfo wf ON c.WorkflowName=wf.WorkflowName WHERE iic.IsApproved=? AND c.CategoryTypeId=?";
/*      */ 
/* 1979 */       psql = con.prepareStatement(sSQL);
/* 1980 */       psql.setBoolean(1, false);
/* 1981 */       psql.setLong(2, 2L);
/* 1982 */       ResultSet rs = psql.executeQuery();
/* 1983 */       Vector vecIds = new Vector();
/*      */ 
/* 1985 */       while (rs.next())
/*      */       {
/* 1987 */         Long longId = new Long(rs.getLong("Id"));
/* 1988 */         if ((rs.getString("wfId") == null) && (!vecIds.contains(longId)))
/*      */         {
/* 1991 */           vecIds.add(longId);
/*      */         }
/*      */       }
/*      */ 
/* 1995 */       psql.close();
/* 1996 */       transaction.commit();
/* 1997 */       this.m_logger.warn("AssetWorkflowManager.generateAssetWorkflows: Adding " + vecIds.size() + " assets to workflow");
/* 1998 */       long lAssetId = -1L;
/*      */ 
/* 2000 */       for (int i = 0; i < vecIds.size(); i++)
/*      */       {
/* 2002 */         transaction = this.m_transactionManager.getNewTransaction();
/* 2003 */         lAssetId = ((Long)vecIds.elementAt(i)).longValue();
/*      */ 
/* 2006 */         initiateAssetWorkflow(transaction, lAssetId, false, 0L, 0L, true);
/* 2007 */         this.m_logger.warn("AssetWorkflowManager.generateAssetWorkflows: Added asset " + lAssetId + " to the workflow");
/*      */ 
/* 2009 */         transaction.commit();
/*      */       }
/* 2011 */       this.m_logger.warn("AssetWorkflowManager.generateAssetWorkflows: Finished");
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*      */       try
/*      */       {
/* 2017 */         if (transaction != null)
/*      */         {
/* 2019 */           transaction.rollback();
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {
/*      */       }
/* 2024 */       throw new Bn2Exception("AssetWorkflowManager.generateAssetWorkflows: Exception occurred: " + e, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void performWorkflowSetup(DBTransaction a_dbTransaction, WorkflowUpdate a_update, Asset a_asset, Asset a_originalAsset, boolean a_bNew)
/*      */     throws Bn2Exception
/*      */   {
/* 2040 */     DBTransaction dbTransaction = a_dbTransaction;
/* 2041 */     long lAssetId = a_asset.getId();
/*      */     try
/*      */     {
/* 2045 */       if (dbTransaction == null)
/*      */       {
/* 2047 */         dbTransaction = this.m_transactionManager.getCurrentOrNewTransaction();
/*      */       }
/*      */ 
/* 2051 */       if (a_update.getUpdateType() == 2)
/*      */       {
/* 2054 */         initiateAssetWorkflow(dbTransaction, lAssetId, true, a_update.getUserId(), a_update.getSessionId(), false);
/*      */       }
/* 2056 */       else if (a_update.getUpdateType() == 1)
/*      */       {
/* 2059 */         approveAssetEndAllWorkflows(dbTransaction, a_asset, !a_bNew);
/*      */       }
/* 2061 */       else if (a_update.getUpdateType() == 4)
/*      */       {
/* 2063 */         updateAssetWorkflows(dbTransaction, a_asset, a_update);
/*      */       }
/* 2065 */       else if (a_update.getUpdateType() == 3)
/*      */       {
/* 2068 */         boolean bUpdate = false;
/* 2069 */         if (a_originalAsset != null)
/*      */         {
/* 2071 */           bUpdate = retainExistingApprovedCategories(a_asset, a_originalAsset);
/*      */         }
/*      */ 
/* 2075 */         saveAssetChanges(dbTransaction, a_asset, bUpdate);
/*      */       }
/*      */     }
/*      */     catch (Bn2Exception se)
/*      */     {
/* 2080 */       GlobalApplication.getInstance().getLogger().error("AssetWorkflowManager.performWorkflowSetup: exception creating workflow for asset:" + lAssetId, se);
/*      */ 
/* 2082 */       if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2086 */           dbTransaction.rollback();
/*      */         } catch (SQLException se1) {
/*      */         }
/*      */       }
/*      */     }
/*      */     finally {
/* 2092 */       if ((a_dbTransaction == null) && (dbTransaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 2096 */           dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException se)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean retainExistingApprovedCategories(Asset a_asset, Asset a_originalAsset)
/*      */   {
/* 2110 */     Vector<Category> vecCats = a_originalAsset.getApprovedPermissionCategories();
/* 2111 */     Set hsApprovedWorkflows = new HashSet();
/* 2112 */     boolean bUpdate = false;
/* 2113 */     if (vecCats != null)
/*      */     {
/* 2115 */       for (Category cat : vecCats)
/*      */       {
/* 2117 */         hsApprovedWorkflows.add(cat.getWorkflowName());
/*      */       }
/*      */     }
/*      */ 
/* 2121 */     vecCats = a_asset.getPermissionCategories();
/* 2122 */     if (vecCats != null)
/*      */     {
/* 2124 */       for (Category cat : vecCats)
/*      */       {
/* 2126 */         if (hsApprovedWorkflows.contains(cat.getWorkflowName()))
/*      */         {
/* 2128 */           a_asset.setApproved(cat, true);
/* 2129 */           bUpdate = true;
/*      */         }
/*      */       }
/*      */     }
/* 2133 */     return bUpdate;
/*      */   }
/*      */ 
/*      */   public int getNumUnsubmittedWorkflowedAssets(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/* 2148 */     int iNumUnsubmittedAssets = -1;
/*      */ 
/* 2150 */     if ((a_userProfile != null) && (a_userProfile.getUser() != null))
/*      */     {
/* 2153 */       if ((this.m_hmNumUnsubmittedWorkflowedAssets != null) && (this.m_hmNumUnsubmittedWorkflowedAssets.containsKey(new Long(a_userProfile.getUser().getId()))))
/*      */       {
/* 2156 */         iNumUnsubmittedAssets = ((Integer)this.m_hmNumUnsubmittedWorkflowedAssets.get(new Long(a_userProfile.getUser().getId()))).intValue();
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/* 2163 */           String sSQL = "SELECT wf.StateName, wf.WorkflowName, wf.VariationName FROM WorkflowInfo wf LEFT JOIN Asset a ON wf.WorkflowableEntityId=a.Id WHERE a.AddedByUserId=?";
/*      */ 
/* 2167 */           Connection con = a_dbTransaction.getConnection();
/* 2168 */           PreparedStatement psql = con.prepareStatement(sSQL);
/* 2169 */           psql.setLong(1, a_userProfile.getUser().getId());
/* 2170 */           ResultSet rs = psql.executeQuery();
/* 2171 */           iNumUnsubmittedAssets = 0;
/*      */ 
/* 2173 */           while (rs.next())
/*      */           {
/* 2175 */             String sStateName = rs.getString("StateName");
/* 2176 */             String sWorkflowName = rs.getString("WorkflowName");
/* 2177 */             String sVariationName = rs.getString("VariationName");
/*      */ 
/* 2179 */             State state = this.m_workflowManager.getState(sWorkflowName, sVariationName, sStateName);
/* 2180 */             if ((state != null) && (state.getVisibleToOwner()))
/*      */             {
/* 2182 */               iNumUnsubmittedAssets++;
/*      */             }
/*      */           }
/* 2185 */           psql.close();
/* 2186 */           updateUnsubmittedCountCache(a_userProfile.getUser().getId(), iNumUnsubmittedAssets);
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2190 */           throw new Bn2Exception("AssetWorkflowManager.getNumUnsubmittedWorkflowedAssets: Error", e);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 2195 */     return iNumUnsubmittedAssets;
/*      */   }
/*      */ 
/*      */   public void updateUnsubmittedCountCache(long a_lUserId, int a_iCount)
/*      */   {
/* 2201 */     synchronized (this.m_objUnsubmittedCountLock)
/*      */     {
/* 2203 */       if (this.m_hmNumUnsubmittedWorkflowedAssets == null)
/*      */       {
/* 2205 */         this.m_hmNumUnsubmittedWorkflowedAssets = new HashMap();
/*      */       }
/*      */ 
/* 2208 */       this.m_hmNumUnsubmittedWorkflowedAssets.put(new Long(a_lUserId), new Integer(a_iCount));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void invalidateUnsubmittedCountCache(long a_lUserId)
/*      */   {
/* 2215 */     synchronized (this.m_objUnsubmittedCountLock)
/*      */     {
/* 2217 */       if ((a_lUserId > 0L) && (this.m_hmNumUnsubmittedWorkflowedAssets != null))
/*      */       {
/* 2219 */         this.m_hmNumUnsubmittedWorkflowedAssets.remove(new Long(a_lUserId));
/*      */       }
/*      */       else
/*      */       {
/* 2223 */         this.m_hmNumUnsubmittedWorkflowedAssets = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.service.AssetWorkflowManager
 * JD-Core Version:    0.6.0
 */