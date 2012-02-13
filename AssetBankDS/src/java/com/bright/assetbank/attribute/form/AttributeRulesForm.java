/*     */ package com.bright.assetbank.attribute.form;
/*     */ 
/*     */ import com.bright.assetbank.actiononasset.action.ActionOnAsset;
/*     */ import com.bright.assetbank.attribute.bean.ChangeAttributeValueRule;
/*     */ import com.bright.assetbank.attribute.bean.SendEmailRule;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AttributeRulesForm extends AttributeForm
/*     */ {
/*  36 */   private Vector m_changeAttributeValueRules = null;
/*  37 */   private List<SendEmailRule> m_sendEmailRules = null;
/*  38 */   private SendEmailRule m_sendEmailRule = null;
/*  39 */   private ChangeAttributeValueRule m_changeAttributeValueRule = null;
/*  40 */   private int m_daysBeforeSelector = 0;
/*  41 */   private boolean m_emailUsersWhoDownloadedAsset = false;
/*     */   private long[] m_groupSelectedList;
/*  47 */   private Vector m_allGroups = null;
/*     */ 
/*  50 */   private Vector m_allAttributes = null;
/*     */ 
/*  52 */   private List<ActionOnAsset> m_actionsOnAssets = null;
/*     */ 
/*     */   public AttributeRulesForm()
/*     */   {
/*  59 */     this.m_sendEmailRule = new SendEmailRule();
/*  60 */     this.m_changeAttributeValueRule = new ChangeAttributeValueRule();
/*     */   }
/*     */ 
/*     */   public void reset(ActionMapping mapping, HttpServletRequest request)
/*     */   {
/*  74 */     this.m_groupSelectedList = new long[0];
/*     */ 
/*  76 */     super.reset(mapping, request);
/*     */   }
/*     */ 
/*     */   public Vector getChangeAttributeValueRules()
/*     */   {
/*  84 */     return this.m_changeAttributeValueRules;
/*     */   }
/*     */ 
/*     */   public void setChangeAttributeValueRules(Vector a_sVecChangeAttributeValueRules) {
/*  88 */     this.m_changeAttributeValueRules = a_sVecChangeAttributeValueRules;
/*     */   }
/*     */ 
/*     */   public List<SendEmailRule> getSendEmailRules()
/*     */   {
/*  93 */     return this.m_sendEmailRules;
/*     */   }
/*     */ 
/*     */   public void setSendEmailRules(List<SendEmailRule> a_sVecSendEmailRules) {
/*  97 */     this.m_sendEmailRules = a_sVecSendEmailRules;
/*     */   }
/*     */ 
/*     */   public int getDaysBeforeSelector()
/*     */   {
/* 102 */     return this.m_daysBeforeSelector;
/*     */   }
/*     */ 
/*     */   public void setDaysBeforeSelector(int a_sDaysBeforeSelector) {
/* 106 */     this.m_daysBeforeSelector = a_sDaysBeforeSelector;
/*     */   }
/*     */ 
/*     */   public boolean getEmailUsersWhoDownloadedAsset()
/*     */   {
/* 111 */     return this.m_emailUsersWhoDownloadedAsset;
/*     */   }
/*     */ 
/*     */   public void setEmailUsersWhoDownloadedAsset(boolean a_emailUsersWhoDownloadedAsset) {
/* 115 */     this.m_emailUsersWhoDownloadedAsset = a_emailUsersWhoDownloadedAsset;
/*     */   }
/*     */ 
/*     */   public long[] getGroupSelectedList() {
/* 119 */     return this.m_groupSelectedList;
/*     */   }
/*     */   public void setGroupSelectedList(long[] a_selectedList) {
/* 122 */     this.m_groupSelectedList = a_selectedList;
/*     */   }
/*     */ 
/*     */   public Vector getAllGroups()
/*     */   {
/* 127 */     return this.m_allGroups;
/*     */   }
/*     */ 
/*     */   public void setAllGroups(Vector a_sAllGroups) {
/* 131 */     this.m_allGroups = a_sAllGroups;
/*     */   }
/*     */ 
/*     */   public ChangeAttributeValueRule getChangeAttributeValueRule()
/*     */   {
/* 136 */     return this.m_changeAttributeValueRule;
/*     */   }
/*     */ 
/*     */   public void setChangeAttributeValueRule(ChangeAttributeValueRule a_sChangeAttributeValueRule) {
/* 140 */     this.m_changeAttributeValueRule = a_sChangeAttributeValueRule;
/*     */   }
/*     */ 
/*     */   public SendEmailRule getSendEmailRule() {
/* 144 */     return this.m_sendEmailRule;
/*     */   }
/*     */ 
/*     */   public void setSendEmailRule(SendEmailRule a_sSendEmailRule) {
/* 148 */     this.m_sendEmailRule = a_sSendEmailRule;
/*     */   }
/*     */ 
/*     */   public Vector getAllAttributes()
/*     */   {
/* 153 */     return this.m_allAttributes;
/*     */   }
/*     */ 
/*     */   public void setAllAttributes(Vector a_sAllAttributes)
/*     */   {
/* 158 */     this.m_allAttributes = a_sAllAttributes;
/*     */   }
/*     */ 
/*     */   public List<ActionOnAsset> getActionsOnAssets()
/*     */   {
/* 163 */     return this.m_actionsOnAssets;
/*     */   }
/*     */ 
/*     */   public void setActionsOnAssets(List<ActionOnAsset> a_actionsOnAssets)
/*     */   {
/* 168 */     this.m_actionsOnAssets = a_actionsOnAssets;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.form.AttributeRulesForm
 * JD-Core Version:    0.6.0
 */