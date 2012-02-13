/*    */ package com.bright.assetbank.application.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class Tint extends DataBean
/*    */ {
/* 23 */   private String m_sName = "";
/* 24 */   private String m_sColour = "";
/* 25 */   private String m_sPercentage = "";
/*    */ 
/*    */   public String getName()
/*    */   {
/* 29 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName)
/*    */   {
/* 34 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public String getColour()
/*    */   {
/* 39 */     return this.m_sColour;
/*    */   }
/*    */ 
/*    */   public void setColour(String a_sColour)
/*    */   {
/* 44 */     this.m_sColour = a_sColour;
/*    */   }
/*    */ 
/*    */   public String getPercentage()
/*    */   {
/* 49 */     return this.m_sPercentage;
/*    */   }
/*    */ 
/*    */   public void setPercentage(String a_sPercentage)
/*    */   {
/* 54 */     this.m_sPercentage = a_sPercentage;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.Tint
 * JD-Core Version:    0.6.0
 */