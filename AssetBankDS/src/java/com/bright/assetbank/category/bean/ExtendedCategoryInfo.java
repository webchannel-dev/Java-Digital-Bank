/*    */ package com.bright.assetbank.category.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class ExtendedCategoryInfo extends DataBean
/*    */ {
/* 24 */   private long m_lParentId = -1L;
/* 25 */   private long m_lCategoryTypeId = -99L;
/* 26 */   private String m_sName = null;
/*    */ 
/*    */   public void setParentId(long a_lParentId)
/*    */   {
/* 30 */     this.m_lParentId = a_lParentId;
/*    */   }
/*    */ 
/*    */   public long getParentId()
/*    */   {
/* 35 */     return this.m_lParentId;
/*    */   }
/*    */ 
/*    */   public void setCategoryTypeId(long a_lCategoryTypeId)
/*    */   {
/* 40 */     this.m_lCategoryTypeId = a_lCategoryTypeId;
/*    */   }
/*    */ 
/*    */   public long getCategoryTypeId()
/*    */   {
/* 45 */     return this.m_lCategoryTypeId;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName)
/*    */   {
/* 50 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 55 */     return this.m_sName;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.bean.ExtendedCategoryInfo
 * JD-Core Version:    0.6.0
 */