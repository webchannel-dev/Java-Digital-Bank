/*    */ package com.bright.assetbank.filetransfer.form;
/*    */ 
/*    */ import com.bright.assetbank.application.form.UploadFileForm;
/*    */ 
/*    */ public class FileTransferForm extends UploadFileForm
/*    */ {
/*    */   String m_sFilename;
/*    */   String m_sFileLocation;
/*    */   String m_sRecipients;
/*    */   String m_sMessage;
/*    */   boolean m_bNotify;
/*    */ 
/*    */   public String getFilename()
/*    */   {
/* 35 */     return this.m_sFilename;
/*    */   }
/*    */ 
/*    */   public void setFilename(String a_originalFilename)
/*    */   {
/* 40 */     this.m_sFilename = a_originalFilename;
/*    */   }
/*    */ 
/*    */   public String getFileLocation()
/*    */   {
/* 45 */     return this.m_sFileLocation;
/*    */   }
/*    */ 
/*    */   public void setFileLocation(String a_fileLocation)
/*    */   {
/* 50 */     this.m_sFileLocation = a_fileLocation;
/*    */   }
/*    */ 
/*    */   public String getRecipients()
/*    */   {
/* 55 */     return this.m_sRecipients;
/*    */   }
/*    */ 
/*    */   public void setRecipients(String a_recipients)
/*    */   {
/* 60 */     this.m_sRecipients = a_recipients;
/*    */   }
/*    */ 
/*    */   public String[] getRecipientArray()
/*    */   {
/* 65 */     return this.m_sRecipients.replaceAll(" ", "").split(";");
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 70 */     return this.m_sMessage;
/*    */   }
/*    */ 
/*    */   public void setMessage(String a_message)
/*    */   {
/* 75 */     this.m_sMessage = a_message;
/*    */   }
/*    */ 
/*    */   public boolean getNotify()
/*    */   {
/* 80 */     return this.m_bNotify;
/*    */   }
/*    */ 
/*    */   public void setNotify(boolean a_notify)
/*    */   {
/* 85 */     this.m_bNotify = a_notify;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.filetransfer.form.FileTransferForm
 * JD-Core Version:    0.6.0
 */