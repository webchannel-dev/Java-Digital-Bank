/*    */ package com.bright.assetbank.orgunit.bean;
/*    */ 
/*    */ public class OrgUnitContent
/*    */ {
/* 33 */   private String m_sContentListItemIdentifier = null;
/* 34 */   private String m_sMenuListItemIdentifier = null;
/* 35 */   private long m_lContentPurposeId = -1L;
/* 36 */   private boolean m_bEnabled = false;
/* 37 */   private long m_lOrgUnitId = -1L;
/*    */ 
/*    */   public OrgUnitContent(long a_lContentPurposeId)
/*    */   {
/* 42 */     this.m_lContentPurposeId = a_lContentPurposeId;
/*    */   }
/*    */ 
/*    */   public String getContentListItemIdentifier()
/*    */   {
/* 47 */     return this.m_sContentListItemIdentifier;
/*    */   }
/*    */ 
/*    */   public void setContentListItemIdentifier(String a_sContentListItemIdentifier)
/*    */   {
/* 52 */     this.m_sContentListItemIdentifier = a_sContentListItemIdentifier;
/*    */   }
/*    */ 
/*    */   public String getMenuListItemIdentifier()
/*    */   {
/* 57 */     return this.m_sMenuListItemIdentifier;
/*    */   }
/*    */ 
/*    */   public void setMenuListItemIdentifier(String a_sMenuListItemIdentifier)
/*    */   {
/* 62 */     this.m_sMenuListItemIdentifier = a_sMenuListItemIdentifier;
/*    */   }
/*    */ 
/*    */   public long getContentPurposeId()
/*    */   {
/* 67 */     return this.m_lContentPurposeId;
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean a_bEnabled)
/*    */   {
/* 72 */     this.m_bEnabled = a_bEnabled;
/*    */   }
/*    */ 
/*    */   public boolean getEnabled()
/*    */   {
/* 77 */     return this.m_bEnabled;
/*    */   }
/*    */ 
/*    */   public void setOrgUnitId(long a_lOrgUnitId)
/*    */   {
/* 82 */     this.m_lOrgUnitId = a_lOrgUnitId;
/*    */   }
/*    */ 
/*    */   public long getOrgUnitId()
/*    */   {
/* 87 */     return this.m_lOrgUnitId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.bean.OrgUnitContent
 * JD-Core Version:    0.6.0
 */