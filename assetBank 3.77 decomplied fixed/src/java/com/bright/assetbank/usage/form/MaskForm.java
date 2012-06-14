/*    */ package com.bright.assetbank.usage.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import org.apache.struts.upload.FormFile;
/*    */ 
/*    */ public class MaskForm extends Bn2Form
/*    */ {
/*    */   private long m_maskId;
/*    */   private FormFile m_file;
/*    */   private String m_name;
/*    */ 
/*    */   public long getMaskId()
/*    */   {
/* 39 */     return this.m_maskId;
/*    */   }
/*    */ 
/*    */   public void setMaskId(long a_maskId)
/*    */   {
/* 44 */     this.m_maskId = a_maskId;
/*    */   }
/*    */ 
/*    */   public FormFile getFile()
/*    */   {
/* 49 */     return this.m_file;
/*    */   }
/*    */ 
/*    */   public void setFile(FormFile a_file)
/*    */   {
/* 54 */     this.m_file = a_file;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 59 */     return this.m_name;
/*    */   }
/*    */ 
/*    */   public void setName(String a_name)
/*    */   {
/* 64 */     this.m_name = a_name;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.form.MaskForm
 * JD-Core Version:    0.6.0
 */