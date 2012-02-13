/*    */ package com.bright.assetbank.batch.update.bean;
/*    */ 
/*    */ import com.bright.framework.queue.bean.QueuedItem;
/*    */ import com.bright.framework.user.bean.User;
/*    */ 
/*    */ public class MetadataImportInfo extends QueuedItem
/*    */ {
/* 32 */   private User m_user = null;
/* 33 */   private String m_sFileUrl = null;
/* 34 */   private boolean m_bAddMissingAssets = false;
/* 35 */   private boolean m_bAddMissingCategories = false;
/* 36 */   private boolean m_bCheckOnly = false;
/* 37 */   private long m_lSessionId = 0L;
/* 38 */   private boolean m_bRemoveFromExistingCategories = false;
/*    */ 
/*    */   public boolean getRemoveFromExistingCategories()
/*    */   {
/* 42 */     return this.m_bRemoveFromExistingCategories;
/*    */   }
/*    */ 
/*    */   public void setRemoveFromExistingCategories(boolean a_bRemoveFromExistingCategories) {
/* 46 */     this.m_bRemoveFromExistingCategories = a_bRemoveFromExistingCategories;
/*    */   }
/*    */ 
/*    */   public String getFileUrl()
/*    */   {
/* 51 */     return this.m_sFileUrl;
/*    */   }
/*    */ 
/*    */   public void setFileUrl(String a_sFileUrl) {
/* 55 */     this.m_sFileUrl = a_sFileUrl;
/*    */   }
/*    */ 
/*    */   public User getUser() {
/* 59 */     return this.m_user;
/*    */   }
/*    */ 
/*    */   public void setUser(User a_user) {
/* 63 */     this.m_user = a_user;
/*    */   }
/*    */ 
/*    */   public boolean getAddMissingCategories() {
/* 67 */     return this.m_bAddMissingCategories;
/*    */   }
/*    */ 
/*    */   public void setAddMissingCategories(boolean addMissingCategories) {
/* 71 */     this.m_bAddMissingCategories = addMissingCategories;
/*    */   }
/*    */ 
/*    */   public boolean getCheckOnly() {
/* 75 */     return this.m_bCheckOnly;
/*    */   }
/*    */ 
/*    */   public void setCheckOnly(boolean a_bCheckOnly) {
/* 79 */     this.m_bCheckOnly = a_bCheckOnly;
/*    */   }
/*    */ 
/*    */   public long getSessionId()
/*    */   {
/* 84 */     return this.m_lSessionId;
/*    */   }
/*    */ 
/*    */   public void setSessionId(long a_lSessionId) {
/* 88 */     this.m_lSessionId = a_lSessionId;
/*    */   }
/*    */ 
/*    */   public boolean getAddMissingAssets() {
/* 92 */     return this.m_bAddMissingAssets;
/*    */   }
/*    */ 
/*    */   public void setAddMissingAssets(boolean a_bAddMissingAssets) {
/* 96 */     this.m_bAddMissingAssets = a_bAddMissingAssets;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.bean.MetadataImportInfo
 * JD-Core Version:    0.6.0
 */