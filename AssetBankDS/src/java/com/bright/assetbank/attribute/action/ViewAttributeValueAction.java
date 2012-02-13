/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.form.AttributeValueForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAttributeValueAction extends BTransactionAction
/*     */   implements FrameworkConstants
/*     */ {
/*  50 */   private AttributeValueManager m_attributeValueManager = null;
/*  51 */   private AssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     long lAssetId = getLongParameter(a_request, "assetId");
/*  78 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*     */ 
/*  81 */     AttributeValueForm form = (AttributeValueForm)a_form;
/*     */ 
/*  83 */     AttributeValue val = null;
/*     */ 
/*  86 */     if (Boolean.parseBoolean(a_request.getParameter("dataFromChildren")))
/*     */     {
/*  88 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, false);
/*  89 */       val = asset.getAttributeValue(lAttributeId);
/*     */     }
/*     */     else
/*     */     {
/*  94 */       val = this.m_attributeValueManager.getAttributeValue(a_dbTransaction, lAssetId, lAttributeId, LanguageConstants.k_defaultLanguage);
/*     */     }
/*     */ 
/*  97 */     if (val != null)
/*     */     {
/*  99 */       val.setLanguage(userProfile.getCurrentLanguage());
/* 100 */       form.setAttributeValue(val);
/*     */     }
/*     */ 
/* 103 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 105 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeValueManager(AttributeValueManager a_sAttributeValueManager)
/*     */   {
/* 111 */     this.m_attributeValueManager = a_sAttributeValueManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_manager)
/*     */   {
/* 116 */     this.m_assetManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewAttributeValueAction
 * JD-Core Version:    0.6.0
 */