/*    */ package com.bright.framework.mail.bean;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class BatchRecipient
/*    */ {
/* 28 */   private String m_sEmailAddress = null;
/* 29 */   private HashMap m_hmPersonalisation = null;
/*    */ 
/*    */   public void setEmailAddress(String a_sEmailAddress)
/*    */   {
/* 34 */     this.m_sEmailAddress = a_sEmailAddress;
/*    */   }
/*    */ 
/*    */   public String getEmailAddress()
/*    */   {
/* 39 */     return this.m_sEmailAddress;
/*    */   }
/*    */ 
/*    */   public void setPersonalisation(HashMap a_hmPersonalisation)
/*    */   {
/* 44 */     this.m_hmPersonalisation = a_hmPersonalisation;
/*    */   }
/*    */ 
/*    */   public HashMap getPersonalisation()
/*    */   {
/* 49 */     return this.m_hmPersonalisation;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.bean.BatchRecipient
 * JD-Core Version:    0.6.0
 */