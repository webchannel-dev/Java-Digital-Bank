/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.batch.update.form.MetadataImportForm;
/*     */ import com.bright.assetbank.taxonomy.constant.KeywordConstants;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.action.ViewCategoryAdminAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewImportKeywordsAction extends ViewCategoryAdminAction
/*     */   implements AssetBankConstants, KeywordConstants
/*     */ {
/*     */   private static final String c_ksClassName = "ViewImportKeywordsAction";
/*  51 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     MetadataImportForm form = (MetadataImportForm)a_form;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  76 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  78 */       this.m_logger.error("ViewImportKeywordsAction.execute : User does not have permission.");
/*  79 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  86 */     long lTreeId = RequestUtil.getLongParameterOrAttribute(a_request, "treeId");
/*  87 */     long lAttributeId = RequestUtil.getLongParameterOrAttribute(a_request, "attributeId");
/*     */ 
/*  90 */     a_request.setAttribute("treeId", String.valueOf(lTreeId));
/*     */ 
/*  93 */     if ((lTreeId > 0L) && (lAttributeId > 0L))
/*     */     {
/*  95 */       Attribute attribute = this.m_attributeManager.getAttribute(a_dbTransaction, lAttributeId);
/*  96 */       form.setAttribute(attribute);
/*     */     }
/*     */     else
/*     */     {
/* 101 */       Vector vecKeywordPickers = this.m_attributeManager.getAttributes(a_dbTransaction, 7L);
/*     */ 
/* 104 */       if (vecKeywordPickers.size() == 1)
/*     */       {
/* 106 */         form.setAttribute((Attribute)vecKeywordPickers.get(0));
/*     */       }
/*     */       else
/*     */       {
/* 110 */         form.setAttributes(vecKeywordPickers);
/*     */       }
/*     */     }
/*     */ 
/* 114 */     ActionForward afForward = a_mapping.findForward("Success");
/* 115 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 120 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.ViewImportKeywordsAction
 * JD-Core Version:    0.6.0
 */