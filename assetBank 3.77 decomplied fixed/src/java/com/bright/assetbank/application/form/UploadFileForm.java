/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.framework.struts.Bn2ExtensibleForm;
/*    */ import org.apache.struts.upload.FormFile;
/*    */ 
/*    */ public class UploadFileForm extends Bn2ExtensibleForm
/*    */   implements AssetBankConstants
/*    */ {
/* 29 */   private FormFile m_file = null;
/* 30 */   private boolean m_bNoUploadFileSpecified = false;
/* 31 */   private boolean m_bConditionsAccepted = false;
/* 32 */   private String m_sUploadToolOption = null;
/*    */ 
/*    */   public FormFile getFile()
/*    */   {
/* 37 */     return this.m_file;
/*    */   }
/*    */ 
/*    */   public void setFile(FormFile a_file)
/*    */   {
/* 43 */     this.m_file = a_file;
/*    */   }
/*    */ 
/*    */   public boolean isNoUploadFileSpecified()
/*    */   {
/* 49 */     return this.m_bNoUploadFileSpecified;
/*    */   }
/*    */ 
/*    */   public void setNoUploadFileSpecified(boolean a_sNoUploadFileSpecified)
/*    */   {
/* 55 */     this.m_bNoUploadFileSpecified = a_sNoUploadFileSpecified;
/*    */   }
/*    */ 
/*    */   public boolean getConditionsAccepted()
/*    */   {
/* 60 */     return this.m_bConditionsAccepted;
/*    */   }
/*    */ 
/*    */   public void setConditionsAccepted(boolean a_sConditionsAccepted)
/*    */   {
/* 65 */     this.m_bConditionsAccepted = a_sConditionsAccepted;
/*    */   }
/*    */ 
/*    */   public void setUploadToolOption(String a_sUploadToolOption)
/*    */   {
/* 70 */     this.m_sUploadToolOption = a_sUploadToolOption;
/*    */   }
/*    */ 
/*    */   public String getUploadToolOption()
/*    */   {
/* 75 */     return this.m_sUploadToolOption;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.UploadFileForm
 * JD-Core Version:    0.6.0
 */