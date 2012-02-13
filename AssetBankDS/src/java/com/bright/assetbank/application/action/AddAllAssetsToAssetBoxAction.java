/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddAllAssetsToAssetBoxAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  48 */   private ListManager m_listManager = null;
/*  49 */   private AssetBoxManager m_assetBoxManager = null;
/*  50 */   private AssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     ActionForward afForward = null;
/*  60 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  61 */     Asset asset = null;
/*  62 */     AssetBoxForm form = (AssetBoxForm)a_form;
/*  63 */     int iNoPermissonCount = 0;
/*  64 */     int iAssetNotFoundCount = 0;
/*  65 */     int iSuccessfulAddedAssetCount = 0;
/*     */ 
/*  68 */     String sSelectedAssetIds = a_request.getParameter("ids");
/*     */ 
/*  70 */     if ((sSelectedAssetIds == null) || (sSelectedAssetIds.length() == 0))
/*     */     {
/*  72 */       afForward = a_mapping.findForward("Success");
/*     */ 
/*  74 */       return afForward;
/*     */     }
/*     */ 
/*  77 */     String[] assetIds = sSelectedAssetIds.split(",");
/*     */ 
/*  79 */     for (String assetId : assetIds)
/*     */     {
/*     */       try
/*     */       {
/*  83 */         asset = this.m_assetManager.getAsset(a_dbTransaction, Long.parseLong(assetId));
/*     */ 
/*  86 */         if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanViewAsset(userProfile, asset)))
/*     */         {
/*  89 */           iNoPermissonCount++;
/*     */         }
/*     */         else
/*     */         {
/*  93 */           this.m_assetBoxManager.addAsset(a_dbTransaction, userProfile, asset.getId());
/*  94 */           iSuccessfulAddedAssetCount++;
/*     */         }
/*     */       }
/*     */       catch (AssetNotFoundException anfe)
/*     */       {
/*  99 */         iAssetNotFoundCount++;
/*     */       }
/*     */       catch (AssetBoxNotFoundException e)
/*     */       {
/* 103 */         iAssetNotFoundCount++;
/*     */       }
/*     */     }
/*     */ 
/* 107 */     String sErrorType = iAssetNotFoundCount + iNoPermissonCount > 0 ? "failedAddingAssetsToAssetBox" : "successfulAddedAssetsToAssetBox";
/*     */ 
/* 109 */     form.setUpdateMessage(this.m_listManager.getListItem(a_dbTransaction, sErrorType, userProfile.getCurrentLanguage(), new String[] { String.valueOf(iSuccessfulAddedAssetCount), String.valueOf(iAssetNotFoundCount + iNoPermissonCount) }));
/*     */ 
/* 111 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 113 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 118 */     this.m_listManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 123 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 128 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.AddAllAssetsToAssetBoxAction
 * JD-Core Version:    0.6.0
 */