/*     */ package com.bright.assetbank.synchronise.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.synchronise.form.ExportStatusForm;
/*     */ import com.bright.assetbank.synchronise.service.AssetExportManager;
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
/*     */ public class ViewExportStatusAction extends Bn2Action
/*     */ {
/*  44 */   private AssetExportManager m_exportManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  70 */     if ((!userProfile.getIsAdmin()) && ((!AssetBankSettings.getOrgUnitAdminsCanExport()) || (!userProfile.getIsOrgUnitAdmin())))
/*     */     {
/*  72 */       this.m_logger.error("ViewExportStatusAction.execute : User does not have admin permission : " + userProfile);
/*  73 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  78 */       ExportStatusForm form = (ExportStatusForm)a_form;
/*     */ 
/*  82 */       form.setMessages(this.m_exportManager.getMessages(userProfile.getUser().getId()));
/*  83 */       form.setInProgress(this.m_exportManager.isBatchInProgress(userProfile.getUser().getId()));
/*     */ 
/*  85 */       if (!form.getInProgress())
/*     */       {
/*  87 */         a_request.getSession().setAttribute("ExportAssetIds", null);
/*  88 */         form.setResult(this.m_exportManager.getExportResult(userProfile.getUser().getId()));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  93 */       this.m_logger.error("Exception in ViewExportStatusAction: " + e.getMessage());
/*  94 */       throw new Bn2Exception("Exception in ViewExportStatusAction: " + e.getMessage(), e);
/*     */     }
/*  96 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setExportManager(AssetExportManager a_sExportManager)
/*     */   {
/* 102 */     this.m_exportManager = a_sExportManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.ViewExportStatusAction
 * JD-Core Version:    0.6.0
 */