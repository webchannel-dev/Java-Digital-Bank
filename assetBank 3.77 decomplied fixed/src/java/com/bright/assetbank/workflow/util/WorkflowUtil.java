/*     */ package com.bright.assetbank.workflow.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.SubmitOptionGroup;
/*     */ import com.bright.assetbank.application.bean.SubmitOptions;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.workflow.bean.Workflow;
/*     */ import com.bright.framework.workflow.bean.WorkflowInfo;
/*     */ import com.bright.framework.workflow.service.WorkflowManager;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ public class WorkflowUtil
/*     */   implements AssetBankConstants
/*     */ {
/*     */   public static WorkflowUpdate getWorkflowUpdateFromSubmitOptions(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, int a_iSubmitOption, HttpServletRequest a_request, String a_sPermissionCategoryIds)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     boolean bNoAccessLevelsSelected = false;
/*  71 */     if (!StringUtil.stringIsPopulated(a_request.getParameter("permissionCategoryForm.selectedCategories")))
/*     */     {
/*  73 */       bNoAccessLevelsSelected = true;
/*     */     }
/*     */ 
/*  76 */     boolean bSetUnapproved = false;
/*  77 */     boolean bSetApproved = false;
/*  78 */     HashMap hmWorkflowApprovals = new HashMap();
/*  79 */     WorkflowUpdate appUpdate = new WorkflowUpdate(a_userProfile.getUser().getId(), a_userProfile.getSessionId());
/*     */ 
/*  82 */     if (a_iSubmitOption < 0)
/*     */     {
/*  86 */       Enumeration names = a_request.getParameterNames();
/*     */ 
/*  88 */       while (names.hasMoreElements())
/*     */       {
/*  90 */         String sName = (String)names.nextElement();
/*  91 */         if ((sName.startsWith("selectedSubmitOption")) && (sName.length() > "selectedSubmitOption".length()))
/*     */         {
/*  94 */           String sWorkflowName = sName.split("_")[1];
/*  95 */           a_iSubmitOption = Integer.parseInt(a_request.getParameter(sName));
/*  96 */           hmWorkflowApprovals.put(sWorkflowName, new Integer(a_iSubmitOption));
/*     */         }
/*     */       }
/*  99 */       if (hmWorkflowApprovals.size() > 0)
/*     */       {
/* 101 */         appUpdate.setSetSubmitted(true);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 106 */       switch (a_iSubmitOption)
/*     */       {
/*     */       case 0:
/* 109 */         bSetApproved = true;
/* 110 */         appUpdate.setSetSubmitted(true);
/* 111 */         break;
/*     */       case 1:
/* 114 */         bSetUnapproved = true;
/* 115 */         appUpdate.setSetSubmitted(true);
/* 116 */         break;
/*     */       case 2:
/* 119 */         appUpdate.setSetUnsubmitted(true);
/* 120 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 129 */     if (hmWorkflowApprovals.size() > 0)
/*     */     {
/* 132 */       appUpdate.setUpdateType(4);
/* 133 */       appUpdate.setWorkflowApprovalUpdates(hmWorkflowApprovals);
/*     */     }
/* 135 */     else if (bSetUnapproved)
/*     */     {
/* 137 */       appUpdate.setUpdateType(2);
/*     */     }
/* 139 */     else if (bSetApproved)
/*     */     {
/* 141 */       appUpdate.setUpdateType(1);
/*     */     }
/*     */     else
/*     */     {
/* 145 */       appUpdate.setUpdateType(3);
/*     */     }
/*     */ 
/* 149 */     if ((a_iSubmitOption != 2) && (appUpdate.getUpdateType() != 2))
/*     */     {
/* 152 */       updateForAccessLevelsRequiringApproval(a_dbTransaction, a_userProfile, appUpdate, a_sPermissionCategoryIds, bNoAccessLevelsSelected);
/*     */     }
/*     */ 
/* 155 */     return appUpdate;
/*     */   }
/*     */ 
/*     */   private static void updateForAccessLevelsRequiringApproval(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, WorkflowUpdate a_update, String a_sPermissionCategoryIds, boolean bNoAccessLevelsSelected)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 182 */       if ((!a_userProfile.getIsAdmin()) && (a_sPermissionCategoryIds != null))
/*     */       {
/* 185 */         Vector vecSelectedCatIds = StringUtil.convertToVectorOfLongs(a_sPermissionCategoryIds, ",");
/*     */ 
/* 188 */         Set catNeedApproval = a_userProfile.getPermissionCategoryIds(6);
/*     */ 
/* 191 */         if ((!bNoAccessLevelsSelected) && (vecSelectedCatIds.size() == 0))
/*     */         {
/* 195 */           a_update.setUpdateType(2);
/*     */         }
/* 197 */         else if (catNeedApproval.size() > 0)
/*     */         {
/* 200 */           HashMap hmWorkflowApprovals = new HashMap();
/* 201 */           if (a_update.getUpdateType() == 4)
/*     */           {
/* 203 */             hmWorkflowApprovals = a_update.getWorkflowApprovalUpdates();
/*     */           }
/*     */ 
/* 207 */           Vector vecWorkflowsDone = new Vector();
/* 208 */           for (int i = 0; i < vecSelectedCatIds.size(); i++)
/*     */           {
/* 210 */             Long lId = (Long)vecSelectedCatIds.get(i);
/* 211 */             Category cat = ((CategoryManager)(CategoryManager)GlobalApplication.getInstance().getComponentManager().lookup("CategoryManager")).getCategory(a_dbTransaction, 2L, lId.longValue());
/*     */ 
/* 213 */             if (catNeedApproval.contains(lId))
/*     */             {
/* 216 */               Integer intCurrentOption = (Integer)hmWorkflowApprovals.get(cat.getWorkflowName());
/* 217 */               int iCurrentOptions = -1;
/*     */ 
/* 219 */               if (intCurrentOption != null)
/*     */               {
/* 221 */                 iCurrentOptions = intCurrentOption.intValue();
/*     */               }
/*     */ 
/* 224 */               if ((iCurrentOptions == 5) || (iCurrentOptions == 6))
/*     */               {
/* 227 */                 hmWorkflowApprovals.put(cat.getWorkflowName(), new Integer(6));
/*     */               }
/*     */               else
/*     */               {
/* 231 */                 hmWorkflowApprovals.put(cat.getWorkflowName(), new Integer(1));
/*     */               }
/* 233 */               vecWorkflowsDone.add(cat.getWorkflowName());
/*     */             } else {
/* 235 */               if (a_update.getUpdateType() != 1) {
/*     */                 continue;
/*     */               }
/* 238 */               if (vecWorkflowsDone.contains(cat.getWorkflowName())) {
/*     */                 continue;
/*     */               }
/* 241 */               hmWorkflowApprovals.put(cat.getWorkflowName(), new Integer(0));
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 247 */           a_update.setUpdateType(4);
/* 248 */           a_update.setWorkflowApprovalUpdates(hmWorkflowApprovals);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 254 */       throw new Bn2Exception("WorkflowUtil.updateForAccessLevelsRequiringApproval: Error: " + e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setAssetSubmissionStatus(WorkflowUpdate a_update, Asset a_asset)
/*     */   {
/* 268 */     if (a_update.getSetUnsubmitted())
/*     */     {
/* 270 */       a_asset.setIsUnsubmitted(true);
/*     */     }
/*     */ 
/* 273 */     if (a_update.getSetSubmitted())
/*     */     {
/* 275 */       a_asset.setIsUnsubmitted(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Vector<String> getApprovableWorkflowsForUser(DBTransaction a_dbTransaction, CategoryManager a_categoryManager, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 291 */     Set catNeedApproval = a_userProfile.getPermissionCategoryIds(12);
/* 292 */     Vector vecCategoryIds = new Vector(catNeedApproval);
/* 293 */     Vector vecAllowedWorkflows = new Vector();
/*     */ 
/* 295 */     for (int i = 0; i < vecCategoryIds.size(); i++)
/*     */     {
/* 297 */       long lId = ((Long)vecCategoryIds.elementAt(i)).longValue();
/* 298 */       Category cat = a_categoryManager.getCategory(a_dbTransaction, 2L, lId);
/* 299 */       vecAllowedWorkflows.add(cat.getWorkflowName());
/*     */     }
/*     */ 
/* 302 */     return vecAllowedWorkflows;
/*     */   }
/*     */ 
/*     */   public static Workflow getSelectedWorkflow(HttpServletRequest a_request, Vector a_vecAvailableWorkflows)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 320 */       String sWorkflowName = a_request.getParameter("workflowName");
/* 321 */       Workflow workflow = null;
/* 322 */       if (!StringUtil.stringIsPopulated(sWorkflowName))
/*     */       {
/* 324 */         if (a_vecAvailableWorkflows.contains("default"))
/*     */         {
/* 326 */           sWorkflowName = "default";
/*     */         }
/*     */         else
/*     */         {
/* 331 */           sWorkflowName = ((Workflow)a_vecAvailableWorkflows.firstElement()).getName();
/*     */         }
/*     */       }
/*     */ 
/* 335 */       if (StringUtil.stringIsPopulated(sWorkflowName))
/*     */       {
/* 337 */         WorkflowManager workflowManager = (WorkflowManager)GlobalApplication.getInstance().getComponentManager().lookup("WorkflowManager");
/* 338 */         workflow = workflowManager.getWorkflowBean(sWorkflowName);
/*     */       }
/*     */ 
/* 342 */       return workflow;
/*     */     }
/*     */     catch (Exception e) {
/*     */     
/* 346 */     throw new Bn2Exception("WorkflowUtil.getSelectedWorkflow: Error: " + e.getMessage(), e);
/*     */  } }
/*     */ 
/*     */   public static Set<String> getWorkflowNamesFromCategories(Vector<Category> a_vecCategories)
/*     */   {
/* 360 */     if (a_vecCategories != null)
/*     */     {
/* 362 */       Set workflowNames = new HashSet();
/* 363 */       for (int i = 0; i < a_vecCategories.size(); i++)
/*     */       {
/* 365 */         Category cat = (Category)a_vecCategories.elementAt(i);
/* 366 */         workflowNames.add(cat.getWorkflowName());
/*     */       }
/* 368 */       return workflowNames;
/*     */     }
/* 370 */     return null;
/*     */   }
/*     */ 
/*     */   public static Vector<String> getUnitialisedWorkflowsForAsset(Asset a_asset)
/*     */   {
/* 382 */     Vector <Category>vecAccessLevels = a_asset.getPermissionCategories();
/* 383 */     Vector vecWorkflows = new Vector();
/* 384 */     for (Category cat : vecAccessLevels)
/*     */     {
/* 386 */       if (StringUtil.stringIsPopulated(cat.getWorkflowName()))
/*     */       {
/* 388 */         if ((!vecWorkflows.contains(cat.getWorkflowName())) && (!a_asset.getInWorkflow(cat.getWorkflowName())))
/*     */         {
/* 391 */           vecWorkflows.add(cat.getWorkflowName());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 396 */     return vecWorkflows;
/*     */   }
/*     */ 
/*     */   public static Vector<SubmitOptionGroup> getWorkflowSubmitOptions(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, Asset a_asset, IAssetManager a_assetManager, CategoryManager a_categoryManager, AssetForm a_form, WorkflowManager a_workflowManager)
/*     */     throws Bn2Exception
/*     */   {
/* 417 */     Vector vecWorkflowNames = new Vector();
/*     */ 
/* 420 */     if (a_asset.getWorkflows() != null)
/*     */     {
/* 422 */       for (int i = 0; i < a_asset.getWorkflows().size(); i++)
/*     */       {
/* 424 */         WorkflowInfo wf = (WorkflowInfo)a_asset.getWorkflows().elementAt(i);
/* 425 */         vecWorkflowNames.add(wf.getWorkflowName());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 431 */     Vector vecAppCats = null;
/*     */ 
/* 433 */     if (a_asset.getIsUnsubmitted())
/*     */     {
/* 435 */       vecAppCats = a_asset.getPermissionCategories();
/*     */     }
/*     */     else
/*     */     {
/* 439 */       vecAppCats = a_asset.getApprovedPermissionCategories();
/*     */     }
/*     */ 
/* 442 */     if (vecAppCats != null)
/*     */     {
/* 444 */       for (int i = 0; i < vecAppCats.size(); i++)
/*     */       {
/* 446 */         Category cat = (Category)vecAppCats.elementAt(i);
/* 447 */         if (vecWorkflowNames.contains(cat.getWorkflowName()))
/*     */           continue;
/* 449 */         vecWorkflowNames.add(cat.getWorkflowName());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 455 */     return getSubmitOptionsFromWorkflowNames(a_dbTransaction, a_asset, vecWorkflowNames, a_userProfile, a_assetManager, false, a_form, a_workflowManager);
/*     */   }
/*     */ 
/*     */   public static Vector<SubmitOptionGroup> getSubmitOptionsFromWorkflowNames(DBTransaction a_dbTransaction, Asset a_asset, Vector<String> a_vecWorkflowNames, ABUserProfile a_userProfile, IAssetManager a_assetManager, boolean a_bInitialisingWorkflows, AssetForm a_form, WorkflowManager a_workflowManager)
/*     */     throws Bn2Exception
/*     */   {
/* 476 */     boolean bUsersWithUpdateCanAddAssetsToWorkflow = AssetBankSettings.getWorkflowUsersWithUpdateCanAddAssetsToWorkflow();
/*     */ 
/* 478 */     HashMap hmGroups = new HashMap();
/* 479 */     if ((a_vecWorkflowNames != null) && (a_vecWorkflowNames.size() > 0))
/*     */     {
/* 481 */       for (int i = 0; i < a_vecWorkflowNames.size(); i++)
/*     */       {
/* 483 */         String sWFName = (String)a_vecWorkflowNames.elementAt(i);
/* 484 */         boolean bInOwnerOnlyState = false;
/*     */ 
/* 486 */         if (!a_asset.getIsApprovedForWorkflow(sWFName))
/*     */         {
/* 489 */           if (!a_asset.getIsUnsubmitted())
/*     */           {
/* 491 */             boolean bOwnerOnly = a_workflowManager.getIsEntityOwnerOnlyForWorkflow(a_dbTransaction, a_asset.getId(), sWFName);
/* 492 */             if (bOwnerOnly)
/*     */             {
/* 494 */               bInOwnerOnlyState = true;
/* 495 */               addOptionToGroup(hmGroups, sWFName, 5, true);
/* 496 */               continue;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 501 */           boolean bCanApproveForWorkflow = false;
/* 502 */           boolean bHasToAddToWorkflow = false;
/*     */ 
/* 504 */           if (a_userProfile.getIsAdmin())
/*     */           {
/* 506 */             bCanApproveForWorkflow = true;
/*     */           }
/*     */           else
/*     */           {
/* 511 */             Vector vecCats = a_asset.getPermissionCategoriesForWorkflow(sWFName);
/* 512 */             for (int x = 0; x < vecCats.size(); x++)
/*     */             {
/* 514 */               Category cat = (Category)vecCats.elementAt(x);
/* 515 */               if ((!a_asset.getIsUnsubmitted()) && (a_userProfile.getPermissionCategoryIds(6).contains(new Long(cat.getId()))))
/*     */               {
/* 517 */                 bHasToAddToWorkflow = true;
/* 518 */                 break;
/*     */               }
/*     */ 
/* 523 */               if ((a_bInitialisingWorkflows) && (a_userProfile.getPermissionCategoryIds(3).contains(new Long(cat.getId()))))
/*     */               {
/* 526 */                 bCanApproveForWorkflow = true;
/* 527 */                 break;
/*     */               }
/*     */ 
/* 532 */               bCanApproveForWorkflow = true;
/* 533 */               if (!a_asset.getIsUnsubmitted())
/*     */               {
/* 536 */                 bCanApproveForWorkflow = (bCanApproveForWorkflow) && (a_assetManager.userCanApproveAssetsForCategory(a_userProfile, cat.getId()));
/*     */               }
/*     */               else
/*     */               {
/* 541 */                 bCanApproveForWorkflow = (bCanApproveForWorkflow) && (a_userProfile.getPermissionCategoryIds(3).contains(new Long(cat.getId())));
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 546 */           if (!bHasToAddToWorkflow)
/*     */           {
/* 549 */             if (bCanApproveForWorkflow)
/*     */             {
/* 551 */               addOptionToGroup(hmGroups, sWFName, 0, false);
/*     */             }
/*     */ 
/* 555 */             if ((!a_asset.getIsUnsubmitted()) && (!a_bInitialisingWorkflows))
/*     */             {
/* 557 */               addOptionToGroup(hmGroups, sWFName, 3, !bCanApproveForWorkflow);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 562 */             addOptionToGroup(hmGroups, sWFName, 1, true);
/*     */           }
/*     */         }
/*     */ 
/* 566 */         if ((bInOwnerOnlyState) || ((!a_asset.getIsApprovedForWorkflow(sWFName)) && (!a_asset.getIsUnsubmitted()) && (!a_bInitialisingWorkflows)))
/*     */         {
/*     */           continue;
/*     */         }
/* 570 */         Vector vecCats = a_asset.getPermissionCategoriesForWorkflow(sWFName);
/* 571 */         boolean bCanAddToWorkflow = false;
/* 572 */         boolean bHasToAddToWorkflow = false;
/*     */ 
/* 574 */         if (!a_userProfile.getIsAdmin())
/*     */         {
/* 576 */           for (int x = 0; x < vecCats.size(); x++)
/*     */           {
/* 578 */             Category cat = (Category)vecCats.elementAt(x);
/*     */ 
/* 581 */             if ((!a_asset.getIsUnsubmitted()) && (a_userProfile.getPermissionCategoryIds(6).contains(new Long(cat.getId()))))
/*     */             {
/* 583 */               bHasToAddToWorkflow = true;
/* 584 */               break;
/*     */             }
/*     */ 
/* 589 */             if (((!a_asset.getIsUnsubmitted()) || (!a_userProfile.getPermissionCategoryIds(6).contains(new Long(cat.getId())))) && ((!bUsersWithUpdateCanAddAssetsToWorkflow) || (!a_userProfile.getPermissionCategoryIds(3).contains(new Long(cat.getId()))))) {
/*     */               continue;
/*     */             }
/* 592 */             bCanAddToWorkflow = true;
/* 593 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 598 */         if (!bHasToAddToWorkflow)
/*     */         {
/* 601 */           if ((!a_asset.getIsUnsubmitted()) && (!a_bInitialisingWorkflows))
/*     */           {
/* 603 */             addOptionToGroup(hmGroups, sWFName, 4, (!a_userProfile.getIsAdmin()) && (!bCanAddToWorkflow));
/*     */           }
/*     */ 
/* 606 */           if ((!a_userProfile.getIsAdmin()) && (!bCanAddToWorkflow))
/*     */             continue;
/* 608 */           addOptionToGroup(hmGroups, sWFName, 1, false);
/*     */         }
/*     */         else
/*     */         {
/* 613 */           addOptionToGroup(hmGroups, sWFName, 1, true);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 619 */     Collection entries = hmGroups.values();
/* 620 */     Vector vecGroups = new Vector();
/* 621 */     vecGroups.addAll(entries);
/* 622 */     return vecGroups;
/*     */   }
/*     */ 
/*     */   private static void addOptionToGroup(HashMap<String, SubmitOptionGroup> a_hmGroups, String a_sWFName, int a_iPermissionLevel, boolean a_bHidden)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 631 */       SubmitOptionGroup group = null;
/* 632 */       SubmitOptions options = null;
/*     */ 
/* 635 */       WorkflowManager workflowManager = (WorkflowManager)GlobalApplication.getInstance().getComponentManager().lookup("WorkflowManager");
/* 636 */       Workflow workflow = workflowManager.getWorkflowBean(a_sWFName);
/*     */ 
/* 639 */       if (a_hmGroups.containsKey(a_sWFName))
/*     */       {
/* 641 */         group = (SubmitOptionGroup)a_hmGroups.get(a_sWFName);
/* 642 */         options = group.getOptions();
/*     */       }
/*     */       else
/*     */       {
/* 646 */         group = new SubmitOptionGroup();
/* 647 */         options = new SubmitOptions();
/* 648 */         group.setTitle(workflow.getDescription());
/* 649 */         group.setIdentifier(workflow.getName());
/*     */       }
/* 651 */       if (a_bHidden)
/*     */       {
/* 653 */         options.addHiddenOption(a_iPermissionLevel);
/*     */       }
/*     */       else
/*     */       {
/* 657 */         options.addOption(a_iPermissionLevel);
/*     */       }
/* 659 */       group.setOptions(options);
/* 660 */       a_hmGroups.put(a_sWFName, group);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 664 */       throw new Bn2Exception("WorkflowUtil.addOptionToGroup: Error: " + e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static WorkflowUpdate getUpdateThatMirrorsAsset(Asset a_asset, long a_lUserId, long a_lSessionId)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 684 */       WorkflowUpdate update = new WorkflowUpdate(a_lUserId, a_lSessionId);
/* 685 */       update.setUpdateType(4);
/* 686 */       update.setSetSubmitted(!a_asset.getIsUnsubmitted());
/* 687 */       HashMap hmApprovalUpdates = new HashMap();
/*     */ 
/* 689 */       WorkflowManager workflowManager = (WorkflowManager)GlobalApplication.getInstance().getComponentManager().lookup("WorkflowManager");
/* 690 */       Vector<String> vecWorkflows = workflowManager.getWorkflows();
/*     */ 
/* 693 */       if (vecWorkflows != null)
/*     */       {
/* 695 */         for (String sWorkflowName : vecWorkflows)
/*     */         {
/* 697 */           if (a_asset.getIsApprovedForWorkflow(sWorkflowName))
/*     */           {
/* 699 */             hmApprovalUpdates.put(sWorkflowName, Integer.valueOf(0));
/*     */           }
/*     */           else
/*     */           {
/* 703 */             hmApprovalUpdates.put(sWorkflowName, Integer.valueOf(1));
/*     */           }
/*     */         }
/*     */       }
/* 707 */       update.setWorkflowApprovalUpdates(hmApprovalUpdates);
/*     */ 
/* 709 */       return update;
/*     */     }
/*     */     catch (Exception e) {
/*     */     
/* 713 */     throw new Bn2Exception("WorkflowUtil.getUpdateThatMirrorsAsset: Error: " + e.getMessage(), e);
/*     */   }}
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workflow.util.WorkflowUtil
 * JD-Core Version:    0.6.0
 */