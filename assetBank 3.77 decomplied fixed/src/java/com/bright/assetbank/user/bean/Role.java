/*    */ package com.bright.assetbank.user.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ 
/*    */ public class Role extends DataBean
/*    */ {
/* 29 */   private String m_sName = null;
/* 30 */   private String m_sDescription = null;
/* 31 */   private String m_sIdentifier = null;
/*    */ 
/*    */   public void setName(String a_sName)
/*    */   {
/* 35 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 40 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setIdentifier(String a_sIdentifier)
/*    */   {
/* 45 */     this.m_sIdentifier = a_sIdentifier;
/*    */   }
/*    */ 
/*    */   public String getIdentifier()
/*    */   {
/* 50 */     return this.m_sIdentifier;
/*    */   }
/*    */ 
/*    */   public void setDescription(String a_sDescription)
/*    */   {
/* 55 */     this.m_sDescription = a_sDescription;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 60 */     return this.m_sDescription;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.Role
 * JD-Core Version:    0.6.0
 */