/*    */ package com.bright.framework.encoding.bean;
/*    */ 
/*    */ public class PassThruEncodingControl
/*    */   implements EncodingControl
/*    */ {
/*    */   public byte[] encode(byte[] a_bytes)
/*    */   {
/* 23 */     return a_bytes;
/*    */   }
/*    */ 
/*    */   public byte[] decode(byte[] a_bytes) {
/* 27 */     return a_bytes;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.encoding.bean.PassThruEncodingControl
 * JD-Core Version:    0.6.0
 */