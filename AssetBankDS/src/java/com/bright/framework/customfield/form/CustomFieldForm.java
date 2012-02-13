/*     */ package com.bright.framework.customfield.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.customfield.bean.CustomField;
/*     */ import com.bright.framework.customfield.bean.CustomFieldType;
/*     */ import com.bright.framework.customfield.bean.CustomFieldUsageType;
/*     */ import com.bright.framework.customfield.bean.CustomFieldValue;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CustomFieldForm extends Bn2Form
/*     */   implements FrameworkConstants
/*     */ {
/*  45 */   private Vector m_vecCustomFields = null;
/*  46 */   private Vector m_vecCustomFieldTypes = null;
/*  47 */   private Vector m_vecCustomFieldUsageTypes = null;
/*  48 */   private Vector m_vecCustomFieldValues = null;
/*  49 */   protected CustomField m_customField = null;
/*  50 */   private CustomFieldValue m_customFieldValue = new CustomFieldValue();
/*     */ 
/*     */   public void setCustomFields(Vector a_vecCustomFields)
/*     */   {
/*  54 */     this.m_vecCustomFields = a_vecCustomFields;
/*     */   }
/*     */ 
/*     */   public Vector getCustomFields()
/*     */   {
/*  59 */     return this.m_vecCustomFields;
/*     */   }
/*     */ 
/*     */   public void setTypes(Vector a_vecCustomFieldTypes)
/*     */   {
/*  64 */     this.m_vecCustomFieldTypes = a_vecCustomFieldTypes;
/*     */   }
/*     */ 
/*     */   public Vector getTypes()
/*     */   {
/*  69 */     return this.m_vecCustomFieldTypes;
/*     */   }
/*     */ 
/*     */   public void setUsageTypes(Vector a_vecCustomFieldUsageTypes)
/*     */   {
/*  74 */     this.m_vecCustomFieldUsageTypes = a_vecCustomFieldUsageTypes;
/*     */   }
/*     */ 
/*     */   public Vector getUsageTypes()
/*     */   {
/*  79 */     return this.m_vecCustomFieldUsageTypes;
/*     */   }
/*     */ 
/*     */   public void setValues(Vector a_vecCustomFieldValues)
/*     */   {
/*  84 */     this.m_vecCustomFieldValues = a_vecCustomFieldValues;
/*     */   }
/*     */ 
/*     */   public Vector getValues()
/*     */   {
/*  89 */     return this.m_vecCustomFieldValues;
/*     */   }
/*     */ 
/*     */   public CustomFieldUsageType getFirstUsageType()
/*     */   {
/*  94 */     if (getUsageTypes() != null)
/*     */     {
/*  96 */       return (CustomFieldUsageType)getUsageTypes().firstElement();
/*     */     }
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   public int getUsageTypeCount()
/*     */   {
/* 103 */     if (getUsageTypes() != null)
/*     */     {
/* 105 */       return getUsageTypes().size();
/*     */     }
/* 107 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setCustomField(CustomField a_customField)
/*     */   {
/* 112 */     this.m_customField = a_customField;
/*     */   }
/*     */ 
/*     */   public CustomField getCustomField()
/*     */   {
/* 117 */     if (this.m_customField == null)
/*     */     {
/* 119 */       this.m_customField = new CustomField();
/*     */ 
/* 122 */       if (FrameworkSettings.getSupportMultiLanguage())
/*     */       {
/* 124 */         LanguageUtils.createEmptyTranslations(this.m_customField, 20);
/*     */       }
/*     */     }
/* 127 */     return this.m_customField;
/*     */   }
/*     */ 
/*     */   public void setCustomFieldValue(CustomFieldValue a_customFieldValue)
/*     */   {
/* 132 */     this.m_customFieldValue = a_customFieldValue;
/*     */   }
/*     */ 
/*     */   public CustomFieldValue getCustomFieldValue()
/*     */   {
/* 137 */     return this.m_customFieldValue;
/*     */   }
/*     */ 
/*     */   public void validateSaveField(ListManager a_listManager, UserProfile a_userProfile, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 143 */     if (!StringUtil.stringIsPopulated(getCustomField().getName()))
/*     */     {
/* 145 */       addError(a_listManager.getListItem(a_transaction, "customFieldName", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 148 */     if (getCustomField().getUsageType().getId() <= 0L)
/*     */     {
/* 150 */       addError(a_listManager.getListItem(a_transaction, "customFieldUsageType", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 153 */     if (getCustomField().getType().getId() <= 0L)
/*     */     {
/* 155 */       addError(a_listManager.getListItem(a_transaction, "customFieldType", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validateSaveFieldValue(ListManager a_listManager, UserProfile a_userProfile, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 163 */     if (!StringUtil.stringIsPopulated(getCustomFieldValue().getValue()))
/*     */     {
/* 165 */       addError(a_listManager.getListItem(a_transaction, "customFieldValueValue", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.form.CustomFieldForm
 * JD-Core Version:    0.6.0
 */