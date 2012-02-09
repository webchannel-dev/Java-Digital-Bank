/*     */ package com.bright.assetbank.user.bean;
/*     */ 
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.bean.CategoryImpl;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import org.apache.commons.collections.Predicate;
/*     */ 
/*     */ public class GroupCategoryPermission extends DataBean
/*     */   implements UserConstants
/*     */ {
/*     */   private Category m_category;
/*     */   private int m_iDownloadPermissionLevel;
/*     */   private int m_iUploadPermissionLevel;
/*     */   private long m_lParentCategoryId;
/*     */   private boolean m_bCanDownloadOriginal;
/*     */   private boolean m_bCanDownloadAdvanced;
/*     */   private boolean m_bCanReviewAssets;
/*     */   private boolean m_bCanViewRestrictedAssets;
/*     */   private boolean m_bApprovalRequiredForHighRes;
/*     */   private boolean m_bCanEditSubcategories;
/*     */ 
/*     */   public GroupCategoryPermission()
/*     */   {
/*  40 */     this.m_category = new CategoryImpl();
/*  41 */     this.m_iDownloadPermissionLevel = 0;
/*  42 */     this.m_iUploadPermissionLevel = 0;
/*  43 */     this.m_lParentCategoryId = 0L;
/*     */ 
/*  51 */     this.m_bCanEditSubcategories = false;
/*     */   }
/*     */ 
/*     */   public void setCategory(Category a_category) {
/*  55 */     this.m_category = a_category;
/*     */   }
/*     */ 
/*     */   public Category getCategory()
/*     */   {
/*  60 */     return this.m_category;
/*     */   }
/*     */ 
/*     */   public void setDownloadPermissionLevel(int a_iDownloadPermissionLevel)
/*     */   {
/*  65 */     this.m_iDownloadPermissionLevel = a_iDownloadPermissionLevel;
/*     */   }
/*     */ 
/*     */   public int getDownloadPermissionLevel()
/*     */   {
/*  70 */     return this.m_iDownloadPermissionLevel;
/*     */   }
/*     */ 
/*     */   public void setUploadPermissionLevel(int a_iUploadPermissionLevel)
/*     */   {
/*  75 */     this.m_iUploadPermissionLevel = a_iUploadPermissionLevel;
/*     */   }
/*     */ 
/*     */   public int getUploadPermissionLevel()
/*     */   {
/*  80 */     return this.m_iUploadPermissionLevel;
/*     */   }
/*     */ 
/*     */   public void setParentCategoryId(long a_lParentCategoryId)
/*     */   {
/*  85 */     this.m_lParentCategoryId = a_lParentCategoryId;
/*     */   }
/*     */ 
/*     */   public long getParentCategoryId()
/*     */   {
/*  90 */     return this.m_lParentCategoryId;
/*     */   }
/*     */ 
/*     */   public boolean getCanDownloadAdvanced()
/*     */   {
/*  96 */     return this.m_bCanDownloadAdvanced;
/*     */   }
/*     */ 
/*     */   public void setCanDownloadAdvanced(boolean a_sCanDownloadAdvanced) {
/* 100 */     this.m_bCanDownloadAdvanced = a_sCanDownloadAdvanced;
/*     */   }
/*     */ 
/*     */   public boolean getCanDownloadOriginal() {
/* 104 */     return this.m_bCanDownloadOriginal;
/*     */   }
/*     */ 
/*     */   public void setCanDownloadOriginal(boolean a_sCanDownloadOriginal) {
/* 108 */     this.m_bCanDownloadOriginal = a_sCanDownloadOriginal;
/*     */   }
/*     */ 
/*     */   public boolean getCanReviewAssets()
/*     */   {
/* 113 */     return this.m_bCanReviewAssets;
/*     */   }
/*     */ 
/*     */   public void setCanReviewAssets(boolean a_bCanReviewAssets) {
/* 117 */     this.m_bCanReviewAssets = a_bCanReviewAssets;
/*     */   }
/*     */ 
/*     */   public boolean getCanViewRestrictedAssets()
/*     */   {
/* 122 */     return this.m_bCanViewRestrictedAssets;
/*     */   }
/*     */ 
/*     */   public void setCanViewRestrictedAssets(boolean a_bCanViewRestrictedAssets) {
/* 126 */     this.m_bCanViewRestrictedAssets = a_bCanViewRestrictedAssets;
/*     */   }
/*     */ 
/*     */   public boolean getCanEditSubcategories()
/*     */   {
/* 131 */     return this.m_bCanEditSubcategories;
/*     */   }
/*     */ 
/*     */   public void setCanEditSubcategories(boolean a_sCanEditSubcategories)
/*     */   {
/* 136 */     this.m_bCanEditSubcategories = a_sCanEditSubcategories;
/*     */   }
/*     */ 
/*     */   public boolean getCanUpdate()
/*     */   {
/* 141 */     return (getUploadPermissionLevel() == 3) || (getCanDeleteAssets());
/*     */   }
/*     */ 
/*     */   public boolean getCanDeleteAssets()
/*     */   {
/* 147 */     return (getUploadPermissionLevel() == 4) || (getCanApproveUploads());
/*     */   }
/*     */ 
/*     */   public boolean getCanApprove()
/*     */   {
/* 153 */     return getDownloadPermissionLevel() == 7;
/*     */   }
/*     */ 
/*     */   public boolean getCanApproveUploads()
/*     */   {
/* 158 */     return getUploadPermissionLevel() == 12;
/*     */   }
/*     */ 
/*     */   public boolean getApprovalRequiredForHighRes()
/*     */   {
/* 163 */     return this.m_bApprovalRequiredForHighRes;
/*     */   }
/*     */ 
/*     */   public void setApprovalRequiredForHighRes(boolean a_bApprovalRequiredForHighRes) {
/* 167 */     this.m_bApprovalRequiredForHighRes = a_bApprovalRequiredForHighRes;
/*     */   }
/*     */ 
/*     */   public static class CategoryIdEquals
/*     */     implements Predicate
/*     */   {
/* 178 */     private long m_lId = 0L;
/*     */ 
/*     */     public CategoryIdEquals(long a_lId)
/*     */     {
/* 182 */       this.m_lId = a_lId;
/*     */     }
/*     */ 
/*     */     public boolean evaluate(Object a_obj)
/*     */     {
/* 196 */       Category cat = ((GroupCategoryPermission)a_obj).getCategory();
/* 197 */       return (cat != null) && (cat.getId() == this.m_lId);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.GroupCategoryPermission
 * JD-Core Version:    0.6.0
 */