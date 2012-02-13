/*     */ package com.bright.assetbank.user.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.framework.customfield.bean.CustomField;
/*     */ import com.bright.framework.customfield.form.CustomFieldHolder;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class UpdateUserForm extends Bn2Form
/*     */   implements UserConstants, CustomFieldHolder
/*     */ {
/*  46 */   private ABUser m_user = null;
/*  47 */   private Vector m_divisionList = null;
/*     */   private List m_languages;
/*  51 */   private long m_lSelectedLanguage = 0L;
/*  52 */   private Vector<? extends CustomField> m_vecCustomFields = null;
/*     */ 
/*     */   public UpdateUserForm()
/*     */   {
/*  59 */     this.m_user = new ABUser();
/*     */   }
/*     */ 
/*     */   public void resetUser()
/*     */   {
/*  64 */     this.m_user = new ABUser();
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     String sEmailAddress = getUser().getEmailAddress();
/*  77 */     if ((!StringUtil.stringIsPopulated(sEmailAddress)) || (!StringUtil.isValidEmailAddress(sEmailAddress)))
/*     */     {
/*  79 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationEmailAddress", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setUser(ABUser a_user)
/*     */   {
/*  89 */     this.m_user = a_user;
/*     */   }
/*     */ 
/*     */   public ABUser getUser()
/*     */   {
/*  94 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public Vector getDivisionList()
/*     */   {
/*  99 */     return this.m_divisionList;
/*     */   }
/*     */ 
/*     */   public void setDivisionList(Vector a_sDivisionList) {
/* 103 */     this.m_divisionList = a_sDivisionList;
/*     */   }
/*     */ 
/*     */   public long getSelectedLanguage()
/*     */   {
/* 109 */     return this.m_lSelectedLanguage;
/*     */   }
/*     */ 
/*     */   public void setSelectedLanguage(long a_sSelectedLanguage) {
/* 113 */     this.m_lSelectedLanguage = a_sSelectedLanguage;
/*     */   }
/*     */ 
/*     */   public void setLanguages(List a_languages)
/*     */   {
/* 118 */     this.m_languages = a_languages;
/*     */   }
/*     */ 
/*     */   public List getLanguages()
/*     */   {
/* 123 */     return this.m_languages;
/*     */   }
/*     */ 
/*     */   public void setCustomFields(Vector<? extends CustomField> a_vecCustomFields)
/*     */   {
/* 128 */     this.m_vecCustomFields = a_vecCustomFields;
/*     */   }
/*     */ 
/*     */   public Vector<? extends CustomField> getCustomFields()
/*     */   {
/* 133 */     return this.m_vecCustomFields;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.UpdateUserForm
 * JD-Core Version:    0.6.0
 */