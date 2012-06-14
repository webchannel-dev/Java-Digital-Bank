/*     */ package com.bright.assetbank.approval.form;
/*     */ 
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class UserApprovalListForm extends UserForm
/*     */ {
/*     */   private Vector m_usersWithApprovalLists;
/*  41 */   private int m_iUnapprovedUploadedAssetsCount = 0;
/*  42 */   private Vector m_vecUnapprovedCategoryAssets = null;
/*     */ 
/*  44 */   private boolean m_bShowMoveBetweenCategories = true;
/*  45 */   private Category m_fromCategory = null;
/*  46 */   private Category m_toCategory = null;
/*  47 */   private int m_iAssetsToMoveCount = 0;
/*     */ 
/*     */   public Vector getUsersWithApprovalLists()
/*     */   {
/*  55 */     return this.m_usersWithApprovalLists;
/*     */   }
/*     */ 
/*     */   public void setUsersWithApprovalLists(Vector a_sUsersWithApprovalLists)
/*     */   {
/*  64 */     this.m_usersWithApprovalLists = a_sUsersWithApprovalLists;
/*     */   }
/*     */ 
/*     */   public int getUnapprovedUploadedAssetsCount()
/*     */   {
/*  70 */     return this.m_iUnapprovedUploadedAssetsCount;
/*     */   }
/*     */ 
/*     */   public void setUnapprovedUploadedAssetsCount(int a_iUnapprovedUploadedAssetsCount)
/*     */   {
/*  75 */     this.m_iUnapprovedUploadedAssetsCount = a_iUnapprovedUploadedAssetsCount;
/*     */   }
/*     */ 
/*     */   public Vector getUnapprovedCategoryAssets()
/*     */   {
/*  80 */     return this.m_vecUnapprovedCategoryAssets;
/*     */   }
/*     */ 
/*     */   public void setUnapprovedCategoryAssets(Vector a_vecUnapprovedCategoryAssets)
/*     */   {
/*  85 */     this.m_vecUnapprovedCategoryAssets = a_vecUnapprovedCategoryAssets;
/*     */   }
/*     */ 
/*     */   public boolean getShowMoveBetweenCategories()
/*     */   {
/*  92 */     return this.m_bShowMoveBetweenCategories;
/*     */   }
/*     */ 
/*     */   public void setShowMoveBetweenCategories(boolean a_bShowMoveBetweenCategories)
/*     */   {
/*  98 */     this.m_bShowMoveBetweenCategories = a_bShowMoveBetweenCategories;
/*     */   }
/*     */ 
/*     */   public Category getFromCategory()
/*     */   {
/* 104 */     return this.m_fromCategory;
/*     */   }
/*     */ 
/*     */   public void setFromCategory(Category a_fromCategory)
/*     */   {
/* 110 */     this.m_fromCategory = a_fromCategory;
/*     */   }
/*     */ 
/*     */   public Category getToCategory()
/*     */   {
/* 116 */     return this.m_toCategory;
/*     */   }
/*     */ 
/*     */   public void setToCategory(Category a_toCategory)
/*     */   {
/* 122 */     this.m_toCategory = a_toCategory;
/*     */   }
/*     */ 
/*     */   public int getAssetsToMoveCount()
/*     */   {
/* 128 */     return this.m_iAssetsToMoveCount;
/*     */   }
/*     */ 
/*     */   public void setAssetsToMoveCount(int a_iAssetsToMoveCount)
/*     */   {
/* 134 */     this.m_iAssetsToMoveCount = a_iAssetsToMoveCount;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.form.UserApprovalListForm
 * JD-Core Version:    0.6.0
 */