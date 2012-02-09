/*    */ package com.bright.framework.mail.bean;
/*    */ 
/*    */ import java.util.GregorianCalendar;
/*    */ import org.apache.commons.mail.HtmlEmail;
/*    */ 
/*    */ public class DuplicateEmail
/*    */ {
/* 33 */   private HtmlEmail m_message = null;
/* 34 */   private GregorianCalendar m_timeSent = null;
/*    */ 
/*    */   public HtmlEmail getMessage()
/*    */   {
/* 38 */     return this.m_message;
/*    */   }
/*    */ 
/*    */   public void setMessage(HtmlEmail a_message)
/*    */   {
/* 43 */     this.m_message = a_message;
/*    */   }
/*    */ 
/*    */   public GregorianCalendar getTimeSent()
/*    */   {
/* 48 */     return this.m_timeSent;
/*    */   }
/*    */ 
/*    */   public void setTimeSent(GregorianCalendar a_timeSent)
/*    */   {
/* 53 */     this.m_timeSent = a_timeSent;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.bean.DuplicateEmail
 * JD-Core Version:    0.6.0
 */