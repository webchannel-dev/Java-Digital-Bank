/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.ChangeAttributeValueRule;
/*     */ import com.bright.assetbank.attribute.form.AttributeRulesForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeDateRuleManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveChangeAttributeValueRuleAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "SaveChangeAttributeValueRuleAction";
/*  47 */   private AttributeDateRuleManager m_attributeDateRuleManager = null;
/*     */ 
/*     */   public void setAttributeDateRuleManager(AttributeDateRuleManager a_attributeDateRuleManager) {
/*  50 */     this.m_attributeDateRuleManager = a_attributeDateRuleManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  75 */     AttributeRulesForm form = (AttributeRulesForm)a_form;
/*     */ 
/*  78 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  80 */       this.m_logger.error("SaveChangeAttributeValueRuleActionThis user does not have permission to view the admin pages");
/*  81 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  85 */     ChangeAttributeValueRule rule = form.getChangeAttributeValueRule();
/*  86 */     long lRuleId = rule.getId();
/*  87 */     long lAttributeId = rule.getAttributeRef().getId();
/*     */ 
/*  90 */     validateMandatoryFields(form, a_request);
/*  91 */     if (form.getHasErrors())
/*     */     {
/*  93 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  96 */     if (lRuleId > 0L)
/*     */     {
/*  99 */       this.m_attributeDateRuleManager.updateChangeAttributeValueRule(a_dbTransaction, rule);
/*     */     }
/*     */     else
/*     */     {
/* 103 */       if (lAttributeId <= 0L)
/*     */       {
/* 105 */         throw new Bn2Exception("SaveChangeAttributeValueRuleAction: No parameter attributeId supplied for new rule.");
/*     */       }
/*     */ 
/* 108 */       this.m_attributeDateRuleManager.addChangeAttributeValueRule(a_dbTransaction, rule);
/*     */     }
/*     */ 
/* 112 */     String sQueryString = "attributeId=" + lAttributeId;
/* 113 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/* 114 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.SaveChangeAttributeValueRuleAction
 * JD-Core Version:    0.6.0
 */