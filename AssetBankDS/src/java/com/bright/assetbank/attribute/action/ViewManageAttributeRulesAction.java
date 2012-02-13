/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.form.AttributeRulesForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeDateRuleManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewManageAttributeRulesAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "ViewManageAttributeRulesAction";
/*  48 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*  54 */   private AttributeDateRuleManager m_attributeDateRuleManager = null;
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/*  51 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeDateRuleManager(AttributeDateRuleManager a_attributeDateRuleManager)
/*     */   {
/*  57 */     this.m_attributeDateRuleManager = a_attributeDateRuleManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     ActionForward afForward = null;
/*  81 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  84 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  86 */       this.m_logger.error("ViewManageAttributeRulesActionThis user does not have permission to view the admin pages");
/*  87 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  90 */     AttributeRulesForm form = (AttributeRulesForm)a_form;
/*     */ 
/*  93 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*     */ 
/*  95 */     if (lAttributeId > 0L)
/*     */     {
/*  97 */       Attribute attribute = this.m_attributeManager.getAttribute(a_dbTransaction, lAttributeId);
/*  98 */       form.setAttribute(attribute);
/*     */ 
/* 101 */       form.setChangeAttributeValueRules(this.m_attributeDateRuleManager.getChangeAttributeValueRuleList(a_dbTransaction, lAttributeId));
/* 102 */       form.setSendEmailRules(this.m_attributeDateRuleManager.getSendEmailRuleList(a_dbTransaction, lAttributeId));
/*     */     }
/*     */ 
/* 105 */     afForward = a_mapping.findForward("Success");
/* 106 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewManageAttributeRulesAction
 * JD-Core Version:    0.6.0
 */