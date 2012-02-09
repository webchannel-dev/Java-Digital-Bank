/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.SendEmailRule;
/*     */ import com.bright.assetbank.attribute.form.AttributeRulesForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeDateRuleManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
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
/*     */ public class ViewEditSendEmailRuleAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "ViewEditSendEmailRuleAction";
/*  51 */   private AttributeDateRuleManager m_attributeDateRuleManager = null;
/*     */ 
/*  57 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public void setAttributeDateRuleManager(AttributeDateRuleManager a_attributeDateRuleManager)
/*     */   {
/*  54 */     this.m_attributeDateRuleManager = a_attributeDateRuleManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  60 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     ActionForward afForward = null;
/*  84 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  85 */     AttributeRulesForm form = (AttributeRulesForm)a_form;
/*     */ 
/*  88 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  90 */       this.m_logger.error("ViewEditSendEmailRuleActionThis user does not have permission to view the admin pages");
/*  91 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  95 */     long lRuleId = getLongParameter(a_request, "ruleId");
/*  96 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*     */ 
/*  99 */     if (!form.getHasErrors())
/*     */     {
/* 101 */       if (lRuleId > 0L)
/*     */       {
/* 104 */         SendEmailRule rule = this.m_attributeDateRuleManager.getSendEmailRule(a_dbTransaction, lRuleId);
/* 105 */         form.setSendEmailRule(rule);
/*     */ 
/* 108 */         form.setGroupSelectedList(getIdsAsArray(rule.getGroups()));
/*     */ 
/* 111 */         if (rule.getIsDaysAfter())
/*     */         {
/* 113 */           form.setDaysBeforeSelector(1);
/*     */         }
/*     */         else
/*     */         {
/* 117 */           form.setDaysBeforeSelector(0);
/*     */         }
/*     */ 
/* 120 */         form.setEmailUsersWhoDownloadedAsset(rule.getEmailUsersWhoDownloadedAsset());
/*     */       }
/*     */       else
/*     */       {
/* 124 */         if (lAttributeId <= 0L)
/*     */         {
/* 126 */           throw new Bn2Exception("ViewEditSendEmailRuleAction: No parameter attributeId supplied for new rule.");
/*     */         }
/*     */ 
/* 129 */         SendEmailRule rule = new SendEmailRule();
/* 130 */         rule.getAttributeRef().setId(lAttributeId);
/* 131 */         form.setSendEmailRule(rule);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 136 */     form.setAllGroups(this.m_userManager.getAllGroups());
/*     */ 
/* 139 */     afForward = a_mapping.findForward("Success");
/* 140 */     return afForward;
/*     */   }
/*     */ 
/*     */   private long[] getIdsAsArray(List<StringDataBean> a_vecListValues)
/*     */   {
/* 146 */     if (a_vecListValues == null)
/*     */     {
/* 148 */       return new long[0];
/*     */     }
/*     */ 
/* 151 */     int iVectorSize = a_vecListValues.size();
/*     */ 
/* 153 */     long[] alIds = new long[iVectorSize];
/*     */ 
/* 155 */     for (int i = 0; i < iVectorSize; i++)
/*     */     {
/* 158 */       alIds[i] = ((StringDataBean)a_vecListValues.get(i)).getId();
/*     */     }
/*     */ 
/* 161 */     return alIds;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewEditSendEmailRuleAction
 * JD-Core Version:    0.6.0
 */