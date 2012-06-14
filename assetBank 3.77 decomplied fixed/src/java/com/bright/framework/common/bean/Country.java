/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class Country extends DataBean
/*    */ {
/* 31 */   private String m_sName = null;
/* 32 */   private String m_sNativeName = null;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 40 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName)
/*    */   {
/* 45 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public String getNativeName()
/*    */   {
/* 50 */     return this.m_sNativeName;
/*    */   }
/*    */ 
/*    */   public void setNativeName(String nativeName)
/*    */   {
/* 55 */     this.m_sNativeName = nativeName;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.Country
 * JD-Core Version:    0.6.0
 */