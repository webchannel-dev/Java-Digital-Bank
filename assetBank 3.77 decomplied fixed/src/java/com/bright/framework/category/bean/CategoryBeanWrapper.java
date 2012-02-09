/*     */ package com.bright.framework.category.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.file.BeanWrapper;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class CategoryBeanWrapper
/*     */   implements BeanWrapper
/*     */ {
/*  35 */   private Category m_category = null;
/*     */ 
/*     */   public Object getWrappedObject()
/*     */   {
/*  43 */     return this.m_category;
/*     */   }
/*     */ 
/*     */   public void setObjectToWrap(Object a_source)
/*     */   {
/*  52 */     if (!(a_source instanceof Category))
/*     */     {
/*  54 */       throw new IllegalArgumentException("CategoryBeanWrapper can only wrap Category instances");
/*     */     }
/*  56 */     this.m_category = ((Category)a_source);
/*     */   }
/*     */ 
/*     */   public void setName(String a_sValue) throws Bn2Exception
/*     */   {
/*  61 */     if (StringUtils.isNotEmpty(a_sValue))
/*     */     {
/*  63 */       a_sValue = a_sValue.trim();
/*     */     }
/*     */ 
/*  66 */     this.m_category.setName(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setExtensionEntityId(String a_sValue) throws Bn2Exception
/*     */   {
/*  71 */     if (StringUtils.isNotEmpty(a_sValue))
/*     */     {
/*  73 */       a_sValue = a_sValue.trim();
/*     */     }
/*     */ 
/*  76 */     this.m_category.setExtensionEntityId(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sValue) throws Bn2Exception
/*     */   {
/*  81 */     if (StringUtils.isNotEmpty(a_sValue))
/*     */     {
/*  83 */       a_sValue = a_sValue.trim();
/*     */     }
/*     */ 
/*  86 */     this.m_category.setDescription(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setSynonyms(String a_sValue)
/*     */     throws Bn2Exception
/*     */   {
/*  93 */     this.m_category.setDescription(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setSearchable(String a_sValue) throws Bn2Exception
/*     */   {
/*  98 */     setBrowsable(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setBrowsable(String a_sValue) throws Bn2Exception
/*     */   {
/* 103 */     if ((a_sValue.equalsIgnoreCase("no")) || (a_sValue.equalsIgnoreCase("false")))
/*     */     {
/* 105 */       this.m_category.setIsBrowsable(false);
/*     */     }
/*     */     else
/*     */     {
/* 109 */       this.m_category.setIsBrowsable(true);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.bean.CategoryBeanWrapper
 * JD-Core Version:    0.6.0
 */