/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.SendEmailRule;
/*     */ import com.bright.assetbank.attribute.form.AttributeRulesForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeDateRuleManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightNaturalNumber;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveSendEmailRuleAction extends BTransactionAction
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "SaveSendEmailRuleAction";
/*  54 */   private AttributeDateRuleManager m_attributeDateRuleManager = null;
/*     */ 
/*  60 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setAttributeDateRuleManager(AttributeDateRuleManager a_attributeDateRuleManager)
/*     */   {
/*  57 */     this.m_attributeDateRuleManager = a_attributeDateRuleManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  63 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     ActionForward afForward = null;
/*  88 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  89 */     AttributeRulesForm form = (AttributeRulesForm)a_form;
/*     */ 
/*  92 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  94 */       this.m_logger.error("SaveSendEmailRuleActionThis user does not have permission to view the admin pages");
/*  95 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  99 */     SendEmailRule rule = form.getSendEmailRule();
/* 100 */     long lRuleId = form.getSendEmailRule().getId();
/* 101 */     long lAttributeId = form.getSendEmailRule().getAttributeRef().getId();
/*     */ 
/* 104 */     rule.setGroups(getIdsAsVector(form.getGroupSelectedList()));
/*     */ 
/* 107 */     validateMandatoryFields(form, a_request);
/*     */ 
/* 109 */     if (!rule.getDaysDifference().processFormData())
/*     */     {
/* 111 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationEmailRuleDaysBefore", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 114 */     if (form.getHasErrors())
/*     */     {
/* 116 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 120 */     if (form.getDaysBeforeSelector() == 1)
/*     */     {
/* 122 */       rule.setIsDaysAfter(true);
/*     */     }
/*     */     else
/*     */     {
/* 126 */       rule.setIsDaysAfter(false);
/*     */     }
/*     */ 
/* 130 */     if (lRuleId > 0L)
/*     */     {
/* 133 */       this.m_attributeDateRuleManager.updateSendEmailRule(a_dbTransaction, rule);
/*     */     }
/*     */     else
/*     */     {
/* 137 */       if (lAttributeId <= 0L)
/*     */       {
/* 139 */         throw new Bn2Exception("SaveSendEmailRuleAction: No parameter attributeId supplied for new rule.");
/*     */       }
/*     */ 
/* 142 */       this.m_attributeDateRuleManager.addSendEmailRule(a_dbTransaction, rule);
/*     */     }
/*     */ 
/* 146 */     String sQueryString = "attributeId=" + lAttributeId;
/* 147 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 149 */     return afForward;
/*     */   }
/*     */ 
/*     */   private List<StringDataBean> getIdsAsVector(long[] a_al)
/*     */   {
/* 155 */     List vec = new Vector();
/*     */ 
/* 157 */     for (int i = 0; i < a_al.length; i++)
/*     */     {
/* 159 */       StringDataBean group = new StringDataBean(a_al[i], "");
/*     */ 
/* 161 */       vec.add(group);
/*     */     }
/*     */ 
/* 164 */     return vec;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.SaveSendEmailRuleAction
 * JD-Core Version:    0.6.0
 */