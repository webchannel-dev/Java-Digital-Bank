/*     */ package com.bright.framework.customfield.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*     */ import com.bright.framework.common.bean.TranslatableStringDataBean.Translation;
/*     */ import com.bright.framework.language.bean.Language;
///*     */ import com.bright.framework.language.bean.Translation;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CustomField extends TranslatableStringDataBean
/*     */ {
/*     */   private CustomFieldType m_type;
/*     */   private CustomFieldUsageType m_usageType;
/*     */   private Vector<CustomFieldValue> m_vecAvailableValues;
/*     */   private boolean m_bIsRequired;
/*     */   private boolean m_bIsSubtype;
/*     */ 
/*     */   public CustomField()
/*     */   {
/*  36 */     this.m_type = new CustomFieldType();
/*  37 */     this.m_usageType = new CustomFieldUsageType();
/*  38 */     this.m_vecAvailableValues = null;
/*  39 */     this.m_bIsRequired = false;
/*     */ 
/*  42 */     this.m_bIsSubtype = false;
/*     */   }
/*     */ 
/*     */   public void setType(CustomFieldType a_type) {
/*  46 */     this.m_type = a_type;
/*     */   }
/*     */ 
/*     */   public CustomFieldType getType()
/*     */   {
/*  51 */     return this.m_type;
/*     */   }
/*     */ 
/*     */   public void setUsageType(CustomFieldUsageType a_usageType)
/*     */   {
/*  56 */     this.m_usageType = a_usageType;
/*     */   }
/*     */ 
/*     */   public CustomFieldUsageType getUsageType()
/*     */   {
/*  61 */     return this.m_usageType;
/*     */   }
/*     */ 
/*     */   public void setAvailableValues(Vector<CustomFieldValue> a_vecAvailableValues)
/*     */   {
/*  66 */     this.m_vecAvailableValues = a_vecAvailableValues;
/*     */   }
/*     */ 
/*     */   public Vector<CustomFieldValue> getAvailableValues()
/*     */   {
/*  71 */     return this.m_vecAvailableValues;
/*     */   }
/*     */ 
/*     */   public void setIsRequired(boolean a_bIsRequired)
/*     */   {
/*  76 */     this.m_bIsRequired = a_bIsRequired;
/*     */   }
/*     */ 
/*     */   public boolean getIsRequired()
/*     */   {
/*  81 */     return this.m_bIsRequired;
/*     */   }
/*     */ 
/*     */   public boolean getIsTextfield()
/*     */   {
/*  86 */     if (getType() != null)
/*     */     {
/*  88 */       return getType().getId() == 1L;
/*     */     }
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getIsTextarea()
/*     */   {
/*  95 */     if (getType() != null)
/*     */     {
/*  97 */       return getType().getId() == 2L;
/*     */     }
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getIsDropdown()
/*     */   {
/* 104 */     if (getType() != null)
/*     */     {
/* 106 */       return getType().getId() == 3L;
/*     */     }
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getIsCheckboxList()
/*     */   {
/* 113 */     if (getType() != null)
/*     */     {
/* 115 */       return getType().getId() == 4L;
/*     */     }
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */   public Translation createTranslation(Language a_language)
/*     */   {
/* 122 */     return new Translation(a_language);
/*     */   }
/*     */ 
/*     */   public boolean getIsSubtype()
/*     */   {
/* 140 */     return this.m_bIsSubtype;
/*     */   }
/*     */ 
/*     */   public void setIsSubtype(boolean a_bIsSubtype)
/*     */   {
/* 145 */     this.m_bIsSubtype = a_bIsSubtype;
/*     */   }
/*     */ 
/*     */   public class Translation extends TranslatableStringDataBean.Translation
/*     */   {
/*     */     public Translation(Language a_language)
/*     */     {
/* 129 */       super(a_language);
/*     */     }
/*     */ 
/*     */     public long getCustomFieldId()
/*     */     {
/* 134 */       return CustomField.this.m_lId;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.bean.CustomField
 * JD-Core Version:    0.6.0
 */