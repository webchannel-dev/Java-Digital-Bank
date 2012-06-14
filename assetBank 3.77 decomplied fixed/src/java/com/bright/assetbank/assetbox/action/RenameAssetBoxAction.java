/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RenameAssetBoxAction extends BTransactionAction
/*     */   implements ImageConstants, AssetBankConstants
/*     */ {
/*  48 */   private AssetBoxManager m_assetBoxManager = null;
/*  49 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     AssetBoxForm form = (AssetBoxForm)a_form;
/*  59 */     ActionForward forward = null;
/*  60 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  63 */     if (!this.m_assetBoxManager.isAssetBoxNameUnique(form.getName(), userProfile))
/*     */     {
/*  65 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetboxNameNotUnique", userProfile.getCurrentLanguage()).getBody());
/*  66 */       return a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/*  70 */     if ((!userProfile.getAssetBox().isShared()) && (StringUtils.isEmpty(form.getName())))
/*     */     {
/*  72 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetboxNameRequired", userProfile.getCurrentLanguage()).getBody());
/*  73 */       return a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/*  76 */     if (form.isShared())
/*     */     {
/*     */       try
/*     */       {
/*  80 */         this.m_assetBoxManager.renameSharedAssetBox(a_dbTransaction, form.getCurrentAssetBoxId(), userProfile.getUser().getId(), form.getName());
/*  81 */         forward = a_mapping.findForward("Success");
/*     */       }
/*     */       catch (AssetBoxNotFoundException e)
/*     */       {
/*  86 */         ListItem listItem = this.m_listManager.getListItem(a_dbTransaction, "a-lightbox", userProfile.getCurrentLanguage());
/*     */ 
/*  88 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetBoxNotAvailable", userProfile.getCurrentLanguage(), new String[] { listItem.getBody() }));
/*  89 */         forward = a_mapping.findForward("Failure");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  94 */       this.m_assetBoxManager.renameAssetBox(a_dbTransaction, form.getCurrentAssetBoxId(), userProfile.getUser().getId(), form.getName());
/*  95 */       forward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/*  98 */     form.setName("");
/*     */ 
/* 100 */     return forward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 106 */     this.m_listManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 112 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.RenameAssetBoxAction
 * JD-Core Version:    0.6.0
 */