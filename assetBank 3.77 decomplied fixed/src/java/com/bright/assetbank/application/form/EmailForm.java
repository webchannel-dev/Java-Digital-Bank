/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ 
/*    */ public class EmailForm extends AssetForm
/*    */ {
/*    */   private String m_sFileId;
/*    */   private String m_sMessage;
/*    */   private String m_sMethod;
/*    */   private String m_sRecipients;
/* 33 */   private boolean m_bAssetIsAttachable = true;
/* 34 */   private boolean m_bIsAssetLinkedNotAttached = false;
/*    */ 
/*    */   public String getFileId()
/*    */   {
/* 38 */     return this.m_sFileId;
/*    */   }
/*    */ 
/*    */   public void setFileId(String a_sFileId) {
/* 42 */     this.m_sFileId = a_sFileId;
/*    */   }
/*    */ 
/*    */   public String getFilename() {
/* 46 */     return FileUtil.getFilename(this.m_sFileId);
/*    */   }
/*    */ 
/*    */   public String getMessage() {
/* 50 */     return this.m_sMessage;
/*    */   }
/*    */ 
/*    */   public void setMessage(String a_sMessage) {
/* 54 */     this.m_sMessage = a_sMessage;
/*    */   }
/*    */ 
/*    */   public String getMethod() {
/* 58 */     return this.m_sMethod;
/*    */   }
/*    */ 
/*    */   public String getRecipients() {
/* 62 */     return this.m_sRecipients;
/*    */   }
/*    */ 
/*    */   public String[] getRecipientArray() {
/* 66 */     return this.m_sRecipients.replaceAll(" ", "").split(";");
/*    */   }
/*    */ 
/*    */   public void setRecipients(String a_sRecipients) {
/* 70 */     this.m_sRecipients = a_sRecipients;
/*    */   }
/*    */ 
/*    */   public void setMethod(String a_sMethod) {
/* 74 */     this.m_sMethod = a_sMethod;
/*    */   }
/*    */ 
/*    */   public boolean getAssetLinkedNotAttached() {
/* 78 */     return this.m_bIsAssetLinkedNotAttached;
/*    */   }
/*    */ 
/*    */   public void setAssetIsAttachable(boolean a_bAssetIsAttachable) {
/* 82 */     this.m_bAssetIsAttachable = a_bAssetIsAttachable;
/*    */   }
/*    */ 
/*    */   public boolean getAssetIsAttachable() {
/* 86 */     return this.m_bAssetIsAttachable;
/*    */   }
/*    */ 
/*    */   public void setAssetLinkedNotAttached(boolean a_bIsAssetLinkedNotAttached) {
/* 90 */     this.m_bIsAssetLinkedNotAttached = a_bIsAssetLinkedNotAttached;
/*    */   }
/*    */ 
/*    */   public boolean getIsLightbox() {
/* 94 */     return getAsset().getId() == 0L;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.EmailForm
 * JD-Core Version:    0.6.0
 */