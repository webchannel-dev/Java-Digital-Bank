/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.batch.update.form.MetadataImportForm;
/*     */ import com.bright.assetbank.synchronise.service.AssetImportManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MetadataImportCheckAction extends BTransactionAction
/*     */ {
/*  48 */   private AssetImportManager m_importManager = null;
/*     */ 
/* 114 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/*  76 */       MetadataImportForm form = (MetadataImportForm)a_form;
/*     */ 
/*  79 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  81 */       if (!userProfile.getIsAdmin())
/*     */       {
/*  83 */         this.m_logger.error("MetadataImportCheckAction.execute : User does not have permission.");
/*  84 */         return a_mapping.findForward("NoPermission");
/*     */       }
/*     */ 
/*  88 */       if (this.m_importManager.isBatchInProgress(userProfile.getUser().getId()))
/*     */       {
/*  90 */         form.setCheckInProgress(true);
/*  91 */         return a_mapping.findForward("BatchInProgress");
/*     */       }
/*     */ 
/*  95 */       form.setMessages(this.m_importManager.getMessages(userProfile.getUser().getId()));
/*     */ 
/*  98 */       form.setCheckInProgress(false);
/*  99 */       return a_mapping.findForward("Success");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 104 */       this.m_logger.error("Exception in DataImportCheckAction: ", e);
/* 105 */     throw new Bn2Exception("Exception in DataImportCheckAction: " + e.getMessage(), e);}
/*     */   }
/*     */ 
/*     */   public void setImportManager(AssetImportManager a_sImportManager)
/*     */   {
/* 111 */     this.m_importManager = a_sImportManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 117 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.MetadataImportCheckAction
 * JD-Core Version:    0.6.0
 */