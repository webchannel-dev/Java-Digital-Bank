/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class ColorSpace extends DataBean
/*    */ {
/* 28 */   private String m_sDescription = null;
/* 29 */   private String m_sFileLocation = null;
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 33 */     return this.m_sDescription;
/*    */   }
/*    */ 
/*    */   public void setDescription(String a_sDescription) {
/* 37 */     this.m_sDescription = a_sDescription;
/*    */   }
/*    */ 
/*    */   public String getFileLocation()
/*    */   {
/* 42 */     return this.m_sFileLocation;
/*    */   }
/*    */ 
/*    */   public void setFileLocation(String a_sFileLocation) {
/* 46 */     this.m_sFileLocation = a_sFileLocation;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.ColorSpace
 * JD-Core Version:    0.6.0
 */