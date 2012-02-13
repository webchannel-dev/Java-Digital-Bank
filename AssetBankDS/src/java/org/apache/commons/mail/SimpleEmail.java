/*    */ package org.apache.commons.mail;
/*    */ 
/*    */ public class SimpleEmail extends Email
/*    */ {
/*    */   public Email setMsg(String msg)
/*    */   {
/* 40 */     setContent(msg, "text/plain");
/* 41 */     return this;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.mail.SimpleEmail
 * JD-Core Version:    0.6.0
 */