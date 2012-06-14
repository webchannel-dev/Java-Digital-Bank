/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.actiononasset.service.ActionOnAssetManager;
/*     */ import com.bright.assetbank.attribute.bean.ChangeAttributeValueRule;
/*     */ import com.bright.assetbank.attribute.form.AttributeRulesForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeDateRuleManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEditChangeAttributeValueRuleAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "ViewEditChangeAttributeValueRuleAction";
/*  52 */   private AttributeManager m_attributeManager = null;
/*  53 */   private AttributeDateRuleManager m_attributeDateRuleManager = null;
/*  54 */   private ActionOnAssetManager m_actionOnAssetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ActionForward afForward = null;
/*  77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  78 */     AttributeRulesForm form = (AttributeRulesForm)a_form;
/*     */ 
/*  81 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  83 */       this.m_logger.error("ViewEditChangeAttributeValueRuleActionThis user does not have permission to view the admin pages");
/*  84 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  88 */     long lRuleId = getLongParameter(a_request, "ruleId");
/*  89 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*     */ 
/*  91 */     List vActions = this.m_actionOnAssetManager.getAvailableActions(a_dbTransaction);
/*  92 */     form.setActionsOnAssets(vActions);
/*     */ 
/*  95 */     if (!form.getHasErrors())
/*     */     {
/*  97 */       if (lRuleId > 0L)
/*     */       {
/* 100 */         form.setChangeAttributeValueRule(this.m_attributeDateRuleManager.getChangeAttributeValueRule(a_dbTransaction, lRuleId));
/*     */       }
/*     */       else
/*     */       {
/* 104 */         if (lAttributeId <= 0L)
/*     */         {
/* 106 */           throw new Bn2Exception("ViewEditChangeAttributeValueRuleAction: No parameter attributeId supplied for new rule.");
/*     */         }
/*     */ 
/* 109 */         ChangeAttributeValueRule rule = new ChangeAttributeValueRule();
/* 110 */         rule.getAttributeRef().setId(lAttributeId);
/* 111 */         form.setChangeAttributeValueRule(rule);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 116 */     form.setAllAttributes(this.m_attributeManager.getFlexibleAttributeList(a_dbTransaction));
/*     */ 
/* 118 */     afForward = a_mapping.findForward("Success");
/* 119 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 124 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeDateRuleManager(AttributeDateRuleManager a_attributeDateRuleManager)
/*     */   {
/* 129 */     this.m_attributeDateRuleManager = a_attributeDateRuleManager;
/*     */   }
/*     */ 
/*     */   public void setActionOnAssetManager(ActionOnAssetManager a_manager)
/*     */   {
/* 134 */     this.m_actionOnAssetManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewEditChangeAttributeValueRuleAction
 * JD-Core Version:    0.6.0
 */