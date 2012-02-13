/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.batch.update.bean.MetadataImportInfo;
/*     */ import com.bright.assetbank.batch.update.form.MetadataImportForm;
/*     */ import com.bright.assetbank.synchronise.service.AssetImportManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MetadataImportStartImportAction extends Bn2Action
/*     */ {
/*  46 */   private AssetImportManager m_importManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/*  72 */       MetadataImportForm form = (MetadataImportForm)a_form;
/*     */ 
/*  75 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */       if (!userProfile.getIsAdmin())
/*     */       {
/*  79 */         this.m_logger.error("MetadataImportStartImportAction.execute : User does not have permission.");
/*  80 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*  83 */       if (this.m_importManager.isBatchInProgress(userProfile.getUser().getId()))
/*     */       {
/*  85 */         return a_mapping.findForward("BatchInProgress");
/*     */       }
/*     */ 
/*  88 */       if ((form.getUrl() == null) || (form.getUrl().length() == 0))
/*     */       {
/*  90 */         form.setUrl((String)a_request.getSession().getAttribute("url"));
/*  91 */         form.setAddMissingCategories(((Boolean)a_request.getSession().getAttribute("addCats")).booleanValue());
/*  92 */         form.setAddMissingAssets(((Boolean)a_request.getSession().getAttribute("addAssets")).booleanValue());
/*  93 */         form.setRemoveFromExistingCategories(((Boolean)a_request.getSession().getAttribute("removeExistingCats")).booleanValue());
/*     */       }
/*     */ 
/*  97 */       MetadataImportInfo importInfo = new MetadataImportInfo();
/*  98 */       importInfo.setFileUrl(form.getUrl());
/*  99 */       importInfo.setUser(userProfile.getUser());
/* 100 */       importInfo.setAddMissingAssets(form.getAddMissingAssets());
/* 101 */       importInfo.setAddMissingCategories(form.getAddMissingCategories());
/* 102 */       importInfo.setSessionId(userProfile.getSessionId());
/* 103 */       importInfo.setRemoveFromExistingCategories(form.getRemoveFromExistingCategories());
/*     */ 
/* 105 */       this.m_importManager.queueImport(importInfo);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 109 */       this.m_logger.error("Exception in DataImportCheckAction: " + e.getMessage());
/* 110 */       throw new Bn2Exception("Exception in DataImportCheckAction: " + e.getMessage(), e);
/*     */     }
/* 112 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setImportManager(AssetImportManager a_sImportManager)
/*     */   {
/* 117 */     this.m_importManager = a_sImportManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.MetadataImportStartImportAction
 * JD-Core Version:    0.6.0
 */