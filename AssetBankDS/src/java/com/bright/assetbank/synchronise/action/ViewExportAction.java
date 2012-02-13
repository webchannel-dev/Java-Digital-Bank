/*     */ package com.bright.assetbank.synchronise.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.synchronise.constant.SynchronisationSettings;
/*     */ import com.bright.assetbank.synchronise.form.ExportForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.storage.service.StorageDeviceManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewExportAction extends Bn2Action
/*     */ {
/*  50 */   private LanguageManager m_languageManager = null;
/*  51 */   private StorageDeviceManager m_storageDeviceManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Exception
/*     */   {
/*  73 */     ActionForward forward = null;
/*  74 */     ExportForm form = (ExportForm)a_form;
/*     */ 
/*  76 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  78 */     if ((!userProfile.getIsAdmin()) && ((!AssetBankSettings.getOrgUnitAdminsCanExport()) || (!userProfile.getIsOrgUnitAdmin())))
/*     */     {
/*  80 */       this.m_logger.error("ViewExportAction.execute : User does not have admin permission : " + userProfile);
/*  81 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  84 */     String sName = SynchronisationSettings.getExportFileStem();
/*     */ 
/*  86 */     if (SynchronisationSettings.getIncludeDateInExportFilename())
/*     */     {
/*  88 */       sName = sName + "." + new SimpleDateFormat("yyyy-MM-dd.kk-mm-ss").format(new Date());
/*     */     }
/*     */ 
/*  91 */     Vector vAssets = (Vector)a_request.getSession().getAttribute("ExportAssetIds");
/*     */ 
/*  93 */     form.setNumAssets(vAssets != null ? vAssets.size() : 0);
/*  94 */     form.setName(sName);
/*     */ 
/*  96 */     List languages = this.m_languageManager.getLanguages(null, true, true);
/*  97 */     form.setNumLanguages(languages.size());
/*     */ 
/*  99 */     form.setExportDirectory(this.m_storageDeviceManager.getAvailableExportDirectory());
/*     */ 
/* 101 */     if (!form.getHasErrors())
/*     */     {
/* 103 */       form.setExportFilesInZips(true);
/*     */     }
/*     */ 
/* 106 */     forward = a_mapping.findForward("Success");
/* 107 */     return forward;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_manager)
/*     */   {
/* 112 */     this.m_languageManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setStorageDeviceManager(StorageDeviceManager a_storageDeviceManager)
/*     */   {
/* 117 */     this.m_storageDeviceManager = a_storageDeviceManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.ViewExportAction
 * JD-Core Version:    0.6.0
 */