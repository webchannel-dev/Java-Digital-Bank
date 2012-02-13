/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class Currency extends DataBean
/*    */ {
/* 29 */   private String m_sDescription = null;
/* 30 */   private String m_sSymbol = null;
/* 31 */   private String m_sCode = null;
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 39 */     return this.m_sDescription;
/*    */   }
/*    */ 
/*    */   public void setDescription(String a_sDescription)
/*    */   {
/* 48 */     this.m_sDescription = a_sDescription;
/*    */   }
/*    */ 
/*    */   public String getSymbol()
/*    */   {
/* 57 */     return this.m_sSymbol;
/*    */   }
/*    */ 
/*    */   public void setSymbol(String a_sSymbol)
/*    */   {
/* 66 */     this.m_sSymbol = a_sSymbol;
/*    */   }
/*    */ 
/*    */   public String getCode()
/*    */   {
/* 75 */     return this.m_sCode;
/*    */   }
/*    */ 
/*    */   public void setCode(String a_sCode)
/*    */   {
/* 84 */     this.m_sCode = a_sCode;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.Currency
 * JD-Core Version:    0.6.0
 */