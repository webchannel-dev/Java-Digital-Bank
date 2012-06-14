/*    */ package com.bright.framework.encryption.bean;
/*    */ 
/*    */ import com.bright.framework.encoding.bean.Base64EncodingControl;
/*    */ 
/*    */ public class AESCBCPKCS5Padding extends EncryptionMethod
/*    */ {
/* 22 */   private String m_sEncryptionName = "AES/CBC/PKCS5Padding";
/* 23 */   private String m_sKeySpec = "AES";
/*    */ 
/*    */   public AESCBCPKCS5Padding(String a_sKey, String a_sIV, boolean bEncrypting)
/*    */   {
/* 27 */     super(a_sKey, a_sIV, new Base64EncodingControl(), bEncrypting);
/*    */   }
/*    */ 
/*    */   public String getEncryptionName()
/*    */   {
/* 32 */     return this.m_sEncryptionName;
/*    */   }
/*    */ 
/*    */   public String getKeySpec()
/*    */   {
/* 37 */     return this.m_sKeySpec;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.encryption.bean.AESCBCPKCS5Padding
 * JD-Core Version:    0.6.0
 */