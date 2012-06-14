/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.AssetCopyManager;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.util.AssetUtil;
/*     */ import com.bright.assetbank.entity.relationship.util.RelationshipUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.LinkedHashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CopyAssetAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  54 */   private AssetManager m_assetManager = null;
/*     */ 
/*  60 */   private AssetCopyManager m_assetCopyManager = null;
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/*  57 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetCopyManager(AssetCopyManager a_assetCopyManager)
/*     */   {
/*  63 */     this.m_assetCopyManager = a_assetCopyManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  76 */     long lAssetId = getLongParameter(a_request, "assetId");
/*  77 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, true, false);
/*     */ 
/*  80 */     if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanUpdateAsset(userProfile, asset)))
/*     */     {
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  86 */     LinkedHashMap lhmRelationshipMap = null;
/*  87 */     if (asset.getNoOfChildAssets() > 0)
/*     */     {
/*  89 */       if (asset.getNoOfChildAssets() > AssetBankSettings.getCopyAssetChildChoiceLimit())
/*     */       {
/*  92 */         lhmRelationshipMap = RelationshipUtil.getCopyChildRelationshipActions(asset, 1);
/*     */       }
/*     */       else
/*     */       {
/*  97 */         lhmRelationshipMap = RelationshipUtil.getCopyChildRelationshipActions(asset, a_request);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 102 */     WorkflowUpdate update = WorkflowUtil.getUpdateThatMirrorsAsset(asset, userProfile.getUserId(), userProfile.getSessionId());
/* 103 */     asset = this.m_assetCopyManager.createCopy(a_dbTransaction, asset, userProfile.getUserId(), lhmRelationshipMap, true);
/*     */ 
/* 106 */     asset = this.m_assetManager.saveAsset(a_dbTransaction, asset, new AssetFileSource(), userProfile.getUserId(), AssetUtil.getNewConversionInfoForAsset(asset), new AssetFileSource(), false, 0, asset.getFormat().getId(), update, false, false);
/*     */ 
/* 109 */     AssetForm assetForm = (AssetForm)a_form;
/* 110 */     assetForm.setAsset(asset);
/* 111 */     assetForm.setEnableSaveAsNewVersion(false);
/*     */ 
/* 113 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 115 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.CopyAssetAction
 * JD-Core Version:    0.6.0
 */