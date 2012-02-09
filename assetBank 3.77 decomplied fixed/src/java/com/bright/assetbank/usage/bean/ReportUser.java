/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ReportUser extends ReportEntity
/*    */ {
/* 29 */   private long m_lUserId = 0L;
/* 30 */   private String m_sUsername = null;
/* 31 */   private String m_sForename = null;
/* 32 */   private String m_sSurname = null;
/*    */ 
/* 34 */   private Vector m_vUsers = null;
/*    */ 
/*    */   public long getUserId()
/*    */   {
/* 39 */     return this.m_lUserId;
/*    */   }
/*    */ 
/*    */   public void setUserId(long a_sGroupId)
/*    */   {
/* 45 */     this.m_lUserId = a_sGroupId;
/*    */   }
/*    */ 
/*    */   public String getUsername()
/*    */   {
/* 51 */     return this.m_sUsername;
/*    */   }
/*    */ 
/*    */   public void setUsername(String a_sGroupName)
/*    */   {
/* 57 */     this.m_sUsername = a_sGroupName;
/*    */   }
/*    */ 
/*    */   public Vector getUsers()
/*    */   {
/* 63 */     return this.m_vUsers;
/*    */   }
/*    */ 
/*    */   public void setUsers(Vector a_sUsers)
/*    */   {
/* 69 */     this.m_vUsers = a_sUsers;
/*    */   }
/*    */ 
/*    */   public String getForename()
/*    */   {
/* 75 */     return this.m_sForename;
/*    */   }
/*    */ 
/*    */   public void setForename(String a_sForename)
/*    */   {
/* 81 */     this.m_sForename = a_sForename;
/*    */   }
/*    */ 
/*    */   public String getSurname()
/*    */   {
/* 87 */     return this.m_sSurname;
/*    */   }
/*    */ 
/*    */   public void setSurname(String a_sSurname)
/*    */   {
/* 93 */     this.m_sSurname = a_sSurname;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.ReportUser
 * JD-Core Version:    0.6.0
 */