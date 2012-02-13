/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetLogManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.usage.form.AssetAuditForm;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAssetAuditAction extends Bn2Action
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*  47 */   private IAssetManager m_assetManager = null;
/*  48 */   private UsageManager m_usageManager = null;
/*  49 */   private AssetLogManager m_assetLogManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     AssetAuditForm form = (AssetAuditForm)a_form;
/*     */ 
/*  76 */     long lXmlId = getLongParameter(a_request, "xmlLog");
/*     */ 
/*  79 */     if (lXmlId > 0L)
/*     */     {
/*  81 */       form.setAssetAuditLog(this.m_assetLogManager.getAssetAuditXmlLog(null, lXmlId));
/*     */ 
/*  83 */       afForward = a_mapping.findForward("ViewXml");
/*     */     }
/*     */     else
/*     */     {
/*  88 */       long lAssetId = getLongParameter(a_request, "id");
/*  89 */       long lVersionNumber = getLongParameter(a_request, "versionNumber");
/*     */ 
/*  91 */       Asset asset = this.m_assetManager.getAsset(null, lAssetId, null, false, false);
/*     */ 
/*  93 */       form.setAssetTitle(asset.getName());
/*     */ 
/*  95 */       form.setAssetAuditLog(this.m_assetLogManager.getAssetAuditList(null, lAssetId, lVersionNumber));
/*     */ 
/*  98 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 101 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/* 107 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 113 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*     */   {
/* 118 */     this.m_assetLogManager = a_assetLogManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewAssetAuditAction
 * JD-Core Version:    0.6.0
 */