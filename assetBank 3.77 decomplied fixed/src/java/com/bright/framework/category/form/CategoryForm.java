/*     */ package com.bright.framework.category.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.CategoryImpl;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import java.util.Vector;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class CategoryForm extends Bn2Form
/*     */   implements CategoryConstants
/*     */ {
/*  42 */   private Vector m_vecAncestorCategoryList = null;
/*  43 */   private Category m_category = null;
/*  44 */   private String m_sCategoryTypeName = null;
/*  45 */   private int m_iReturnCode = 0;
/*  46 */   private FormFile m_file = null;
/*  47 */   private boolean m_bRemoveImage = false;
/*  48 */   private boolean m_bFailedValidation = false;
/*  49 */   private boolean m_bBrowsable = false;
/*  50 */   private boolean m_bIsRestrictive = false;
/*  51 */   private long m_lParentId = 0L;
/*     */ 
/*     */   public Category getCategory()
/*     */   {
/*  55 */     if (this.m_category == null)
/*     */     {
/*  57 */       this.m_category = new CategoryImpl();
/*     */ 
/*  60 */       if (FrameworkSettings.getSupportMultiLanguage())
/*     */       {
/*  62 */         LanguageUtils.createEmptyTranslations(this.m_category, 20);
/*     */       }
/*     */     }
/*  65 */     return this.m_category;
/*     */   }
/*     */ 
/*     */   public void setCategory(Category a_vecCategory)
/*     */   {
/*  70 */     this.m_category = a_vecCategory;
/*     */   }
/*     */ 
/*     */   public void setCategoryTypeName(String a_sCategoryTreeName)
/*     */   {
/*  75 */     this.m_sCategoryTypeName = a_sCategoryTreeName;
/*     */   }
/*     */ 
/*     */   public String getCategoryTypeName()
/*     */   {
/*  80 */     return this.m_sCategoryTypeName;
/*     */   }
/*     */ 
/*     */   public void setAncestorCategoryList(Vector a_vecAncestorCategoryList)
/*     */   {
/*  85 */     this.m_vecAncestorCategoryList = a_vecAncestorCategoryList;
/*     */   }
/*     */ 
/*     */   public Vector getAncestorCategoryList()
/*     */   {
/*  90 */     return this.m_vecAncestorCategoryList;
/*     */   }
/*     */ 
/*     */   public int getReturnCode()
/*     */   {
/*  95 */     return this.m_iReturnCode;
/*     */   }
/*     */ 
/*     */   public void setReturnCode(int a_sReturnCode)
/*     */   {
/* 100 */     this.m_iReturnCode = a_sReturnCode;
/*     */   }
/*     */ 
/*     */   public FormFile getImageFile()
/*     */   {
/* 106 */     return this.m_file;
/*     */   }
/*     */ 
/*     */   public void setImageFile(FormFile a_file)
/*     */   {
/* 112 */     this.m_file = a_file;
/*     */   }
/*     */ 
/*     */   public boolean getRemoveImage() {
/* 116 */     return this.m_bRemoveImage;
/*     */   }
/*     */ 
/*     */   public void setRemoveImage(boolean a_bRemoveImage) {
/* 120 */     this.m_bRemoveImage = a_bRemoveImage;
/*     */   }
/*     */ 
/*     */   public void failValidation()
/*     */   {
/* 125 */     this.m_bFailedValidation = true;
/*     */   }
/*     */ 
/*     */   public boolean failedValidation()
/*     */   {
/* 130 */     return this.m_bFailedValidation;
/*     */   }
/*     */ 
/*     */   public boolean isBrowsable()
/*     */   {
/* 135 */     return this.m_bBrowsable;
/*     */   }
/*     */ 
/*     */   public void setBrowsable(boolean a_bBrowsable)
/*     */   {
/* 140 */     this.m_bBrowsable = a_bBrowsable;
/*     */   }
/*     */ 
/*     */   public boolean isIsRestrictive()
/*     */   {
/* 145 */     return this.m_bIsRestrictive;
/*     */   }
/*     */ 
/*     */   public void setIsRestrictive(boolean a_bIsRestrictive)
/*     */   {
/* 150 */     this.m_bIsRestrictive = a_bIsRestrictive;
/*     */   }
/*     */ 
/*     */   public long getParentId()
/*     */   {
/* 155 */     return this.m_lParentId;
/*     */   }
/*     */ 
/*     */   public void setParentId(long a_lParentId)
/*     */   {
/* 160 */     this.m_lParentId = a_lParentId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.form.CategoryForm
 * JD-Core Version:    0.6.0
 */