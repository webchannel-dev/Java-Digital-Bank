/*     */ package com.bright.assetbank.attribute.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.List;
/*     */ 
/*     */ public class AttributeForm extends Bn2Form
/*     */ {
/*  40 */   private Attribute m_attribute = null;
/*  41 */   private List<AttributeValue> m_listAttributeValues = null;
/*  42 */   private List<Group> m_listVisibleToGroups = null;
/*  43 */   private long m_lVisibleToGroupId = 0L;
/*  44 */   private String m_sMaxDisplayLengthString = null;
/*  45 */   private String m_sValue = null;
/*     */ 
/*     */   public Attribute getAttribute()
/*     */   {
/*  50 */     if (this.m_attribute == null)
/*     */     {
/*  52 */       this.m_attribute = new Attribute();
/*     */ 
/*  55 */       if (FrameworkSettings.getSupportMultiLanguage())
/*     */       {
/*  57 */         LanguageUtils.createEmptyTranslations(this.m_attribute, 20);
/*     */       }
/*     */     }
/*  60 */     return this.m_attribute;
/*     */   }
/*     */ 
/*     */   public void setAttribute(Attribute a_sAttribute)
/*     */   {
/*  65 */     this.m_attribute = a_sAttribute;
/*     */   }
/*     */ 
/*     */   public List<AttributeValue> getListAttributeValues()
/*     */   {
/*  70 */     return this.m_listAttributeValues;
/*     */   }
/*     */ 
/*     */   public void setListAttributeValues(List<AttributeValue> a_listAttributeValues)
/*     */   {
/*  75 */     this.m_listAttributeValues = a_listAttributeValues;
/*     */   }
/*     */ 
/*     */   public List<Group> getVisibleToGroups() {
/*  79 */     return this.m_listVisibleToGroups;
/*     */   }
/*     */ 
/*     */   public void setVisibleToGroups(List<Group> a_listVisibleToGroups) {
/*  83 */     this.m_listVisibleToGroups = a_listVisibleToGroups;
/*     */   }
/*     */ 
/*     */   public long getVisibleToGroupId() {
/*  87 */     return this.m_lVisibleToGroupId;
/*     */   }
/*     */ 
/*     */   public void setVisibleToGroupId(long a_sVisibleToGroupId) {
/*  91 */     this.m_lVisibleToGroupId = a_sVisibleToGroupId;
/*     */   }
/*     */ 
/*     */   public void setMaxDisplayLengthString(String a_sMaxDisplayLengthString)
/*     */   {
/*  96 */     this.m_sMaxDisplayLengthString = a_sMaxDisplayLengthString;
/*     */   }
/*     */ 
/*     */   public String getMaxDisplayLengthString()
/*     */   {
/* 101 */     return this.m_sMaxDisplayLengthString;
/*     */   }
/*     */ 
/*     */   public String getValue() {
/* 105 */     return this.m_sValue;
/*     */   }
/*     */ 
/*     */   public void setValue(String a_sValue) {
/* 109 */     this.m_sValue = a_sValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.form.AttributeForm
 * JD-Core Version:    0.6.0
 */