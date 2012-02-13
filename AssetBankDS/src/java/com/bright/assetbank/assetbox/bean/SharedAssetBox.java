/*    */ package com.bright.assetbank.assetbox.bean;
/*    */ 
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class SharedAssetBox extends AssetBoxSummary
/*    */ {
/*    */   private String m_sAlias;
/* 24 */   private long m_lOwningUserId = 0L;
/* 25 */   private String m_sOwningUsername = null;
/* 26 */   private String m_sOwningEmailAddress = null;
/*    */ 
/*    */   public SharedAssetBox()
/*    */   {
/* 30 */     setShared(true);
/*    */   }
/*    */ 
/*    */   public String getAlias()
/*    */   {
/* 35 */     return this.m_sAlias;
/*    */   }
/*    */ 
/*    */   public String getOriginalName()
/*    */   {
/* 40 */     return super.getName();
/*    */   }
/*    */ 
/*    */   public void setAlias(String alias)
/*    */   {
/* 45 */     this.m_sAlias = alias;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 53 */     if (StringUtils.isEmpty(getAlias()))
/*    */     {
/* 55 */       return super.getName();
/*    */     }
/* 57 */     return getAlias();
/*    */   }
/*    */ 
/*    */   public long getOwningUserId()
/*    */   {
/* 62 */     return this.m_lOwningUserId;
/*    */   }
/*    */ 
/*    */   public void setOwningUserId(long owningUserId)
/*    */   {
/* 67 */     this.m_lOwningUserId = owningUserId;
/*    */   }
/*    */ 
/*    */   public String getOwningUsername()
/*    */   {
/* 72 */     return this.m_sOwningUsername;
/*    */   }
/*    */ 
/*    */   public void setOwningUsername(String owningUsername)
/*    */   {
/* 77 */     this.m_sOwningUsername = owningUsername;
/*    */   }
/*    */ 
/*    */   public String getOwningEmailAddress()
/*    */   {
/* 82 */     return this.m_sOwningEmailAddress;
/*    */   }
/*    */ 
/*    */   public void setOwningEmailAddress(String owningEmailAddress)
/*    */   {
/* 87 */     this.m_sOwningEmailAddress = owningEmailAddress;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.bean.SharedAssetBox
 * JD-Core Version:    0.6.0
 */