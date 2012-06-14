/*     */ package com.bright.assetbank.approval.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.approval.constant.AssetApprovalConstants;
/*     */ import com.bright.assetbank.approval.form.UserApprovalListForm;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAssetApproval extends BTransactionAction
/*     */   implements AssetApprovalConstants, AssetBankConstants
/*     */ {
/*  58 */   private AssetApprovalManager m_approvalManager = null;
/*  59 */   private AssetCategoryManager m_categoryManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  81 */     ActionForward afForward = null;
/*     */ 
/*  84 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  85 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  87 */       this.m_logger.debug("ViewAssetApprovalAction: not logged in");
/*  88 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  93 */       UserApprovalListForm form = (UserApprovalListForm)a_form;
/*     */ 
/*  99 */       Vector vecUsersWithApprovalLists = this.m_approvalManager.getAllUsersWithApprovalLists(a_dbTransaction, userProfile.getCategoryIdsAsVector(7));
/*     */ 
/* 101 */       form.setUsersWithApprovalLists(vecUsersWithApprovalLists);
/*     */ 
/* 104 */       if ((userProfile.getIsAdmin()) || (userProfile.getIsInitialOrgUnitAdmin()))
/*     */       {
/* 106 */         long lFromCatId = new Long(AssetBankSettings.getSubmitUniversalCategoryId()).longValue();
/* 107 */         long lToCatId = new Long(AssetBankSettings.getUniversalOrgUnitCategoryId()).longValue();
/*     */ 
/* 109 */         if ((lFromCatId > 0L) && (lToCatId > 0L))
/*     */         {
/* 112 */           form.setFromCategory(this.m_categoryManager.getCategory(a_dbTransaction, 2L, lFromCatId));
/* 113 */           form.setToCategory(this.m_categoryManager.getCategory(a_dbTransaction, 2L, lToCatId));
/*     */ 
/* 116 */           form.setAssetsToMoveCount(this.m_categoryManager.getItemsInCategoryCount(a_dbTransaction, lFromCatId));
/*     */         }
/*     */         else
/*     */         {
/* 121 */           form.setShowMoveBetweenCategories(false);
/*     */         }
/*     */       }
/*     */ 
/* 125 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 129 */       this.m_logger.error("ViewCategoryAssetsApproval - exception: " + e.getMessage());
/* 130 */       throw e;
/*     */     }
/*     */ 
/* 133 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/* 138 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/* 143 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.action.ViewAssetApproval
 * JD-Core Version:    0.6.0
 */