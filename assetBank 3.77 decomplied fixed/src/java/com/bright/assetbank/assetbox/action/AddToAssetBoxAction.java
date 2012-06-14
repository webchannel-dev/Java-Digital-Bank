/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddToAssetBoxAction extends AssetInAssetBoxAction
/*     */ {
/*  47 */   protected IAssetManager m_assetManager = null;
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_assetManager)
/*     */   {
/*  51 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   protected ActionForward performAssetBoxOperation(HttpServletRequest a_request, DBTransaction a_dbTransaction, ABUserProfile userProfile, ActionMapping a_mapping, ActionForm a_form)
/*     */     throws Bn2Exception, AssetBoxNotFoundException
/*     */   {
/*  62 */     AssetBoxForm form = (AssetBoxForm)a_form;
/*     */     try
/*     */     {
/*  66 */       long[] laIds = getAssetIds(a_request);
/*     */ 
/*  68 */       for (int i = 0; (laIds != null) && (i < laIds.length); i++)
/*     */       {
/*  73 */         if (AssetBankSettings.ecommerce())
/*     */         {
/*  76 */           Asset asset = this.m_assetManager.getAsset(a_dbTransaction, laIds[i], null, false, false);
/*     */ 
/*  79 */           if (!this.m_assetManager.userCanDownloadAssetNow(userProfile, asset))
/*     */           {
/*  81 */             String sQueryString = "id=" + laIds[i];
/*  82 */             return createRedirectingForward(sQueryString, a_mapping, "ViewAsset");
/*     */           }
/*     */         }
/*     */ 
/*  86 */         getAssetBoxManager().addAsset(a_dbTransaction, userProfile, laIds[i]);
/*     */       }
/*     */     }
/*     */     catch (AssetNotFoundException anfe) {
/*  90 */       form.setErrorMessage(this.m_listManager.getListItem(a_dbTransaction, "categoryErrorMissingAsset", userProfile.getCurrentLanguage()).getBody());
/*  91 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   protected long[] getAssetIds(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 113 */     long[] laAssetIds = new long[1];
/* 114 */     laAssetIds[0] = getLongParameter(a_request, "id");
/*     */ 
/* 116 */     if (laAssetIds[0] <= 0L)
/*     */     {
/* 118 */       throw new Bn2Exception("AddToAssetBoxAction: id parameter is missing or <=0");
/*     */     }
/*     */ 
/* 121 */     return laAssetIds;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.AddToAssetBoxAction
 * JD-Core Version:    0.6.0
 */