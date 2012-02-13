/*     */ package com.bright.assetbank.orgunit.bean;
/*     */ 
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.CategoryImpl;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ 
/*     */ public class OrgUnit extends OrgUnitMetadata
/*     */ {
/*     */   private Group m_userGroup;
/*     */   private Category m_category;
/*     */   private Group m_adminGroup;
/*     */   private Category m_RootDescriptiveCategory;
/*     */ 
/*     */   public OrgUnit()
/*     */   {
/*  50 */     this.m_userGroup = new Group();
/*  51 */     this.m_adminGroup = new Group();
/*  52 */     this.m_category = new CategoryImpl();
/*     */   }
/*     */ 
/*     */   public boolean containsCategory(long a_lCategoryId)
/*     */   {
/*  69 */     boolean bContainsCat = false;
/*  70 */     long[] alCats = this.m_category.getAllDescendantsIds();
/*     */ 
/*  72 */     for (int i = 0; i < alCats.length; i++)
/*     */     {
/*  74 */       if (alCats[i] != a_lCategoryId)
/*     */         continue;
/*  76 */       bContainsCat = true;
/*     */     }
/*     */ 
/*  81 */     if (this.m_category.getId() == a_lCategoryId)
/*     */     {
/*  83 */       bContainsCat = true;
/*     */     }
/*     */ 
/*  86 */     return bContainsCat;
/*     */   }
/*     */ 
/*     */   public Group getUserGroup()
/*     */   {
/* 100 */     return this.m_userGroup;
/*     */   }
/*     */ 
/*     */   public void setUserGroup(Group a_sUserGroup)
/*     */   {
/* 109 */     this.m_userGroup = a_sUserGroup;
/*     */   }
/*     */ 
/*     */   public Category getCategory()
/*     */   {
/* 118 */     return this.m_category;
/*     */   }
/*     */ 
/*     */   public void setCategory(Category a_sCategory)
/*     */   {
/* 127 */     this.m_category = a_sCategory;
/*     */   }
/*     */ 
/*     */   public Group getAdminGroup()
/*     */   {
/* 136 */     return this.m_adminGroup;
/*     */   }
/*     */ 
/*     */   public void setAdminGroup(Group a_sAdminGroup)
/*     */   {
/* 145 */     this.m_adminGroup = a_sAdminGroup;
/*     */   }
/*     */ 
/*     */   public Category getRootDescriptiveCategory()
/*     */   {
/* 150 */     return this.m_RootDescriptiveCategory;
/*     */   }
/*     */ 
/*     */   public void setRootDescriptiveCategory(Category rootDescriptiveCategory)
/*     */   {
/* 155 */     this.m_RootDescriptiveCategory = rootDescriptiveCategory;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 160 */     if (StringUtil.stringIsPopulated(super.getName()))
/*     */     {
/* 162 */       return super.getName();
/*     */     }
/* 164 */     return getCategory().getName();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.bean.OrgUnit
 * JD-Core Version:    0.6.0
 */