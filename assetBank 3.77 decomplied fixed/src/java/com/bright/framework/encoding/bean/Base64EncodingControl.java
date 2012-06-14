/*    */ package com.bright.framework.encoding.bean;
/*    */ 
/*    */ import org.apache.commons.codec.binary.Base64;
/*    */ 
/*    */ public class Base64EncodingControl
/*    */   implements EncodingControl
/*    */ {
/*    */   public byte[] encode(byte[] a_bytes)
/*    */   {
/* 23 */     return Base64.encodeBase64(a_bytes);
/*    */   }
/*    */ 
/*    */   public byte[] decode(byte[] a_bytes) {
/* 27 */     return Base64.decodeBase64(a_bytes);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.encoding.bean.Base64EncodingControl
 * JD-Core Version:    0.6.0
 */