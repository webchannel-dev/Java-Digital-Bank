/*    */ package com.bright.assetbank.category.bean;
/*    */ 
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ 
/*    */ public class CategoryImportBatch extends QueuedItem
/*    */ {
/* 30 */   private ABUserProfile m_userProfile = null;
/* 31 */   private long m_lCategoryTypeId = -1L;
/* 32 */   private String m_sImportFileLocation = null;
/*    */ 
/*    */   public ABUserProfile getUserProfile()
/*    */   {
/* 37 */     return this.m_userProfile;
/*    */   }
/*    */ 
/*    */   public void setUserProfile(ABUserProfile a_userProfile)
/*    */   {
/* 42 */     this.m_userProfile = a_userProfile;
/*    */   }
/*    */ 
/*    */   public long getCategoryTypeId()
/*    */   {
/* 47 */     return this.m_lCategoryTypeId;
/*    */   }
/*    */ 
/*    */   public void setCategoryTypeId(long m_lCategoryTypeId)
/*    */   {
/* 52 */     this.m_lCategoryTypeId = m_lCategoryTypeId;
/*    */   }
/*    */ 
/*    */   public String getImportFileLocation()
/*    */   {
/* 57 */     return this.m_sImportFileLocation;
/*    */   }
/*    */ 
/*    */   public void setImportFileLocation(String m_sImportFileLocation)
/*    */   {
/* 62 */     this.m_sImportFileLocation = m_sImportFileLocation;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.bean.CategoryImportBatch
 * JD-Core Version:    0.6.0
 */