/*    */ package org.apache.commons.mail;
/*    */ 
/*    */ import javax.mail.Authenticator;
/*    */ import javax.mail.PasswordAuthentication;
/*    */ 
/*    */ public class DefaultAuthenticator extends Authenticator
/*    */ {
/*    */   private PasswordAuthentication authentication;
/*    */ 
/*    */   public DefaultAuthenticator(String userName, String password)
/*    */   {
/* 41 */     this.authentication = new PasswordAuthentication(userName, password);
/*    */   }
/*    */ 
/*    */   protected PasswordAuthentication getPasswordAuthentication()
/*    */   {
/* 54 */     return this.authentication;
/*    */   }
/*    */ }

/* Location:           D:\Project - Photint Asset-bank\org.zip
 * Qualified Name:     org.apache.commons.mail.DefaultAuthenticator
 * JD-Core Version:    0.6.0
 */