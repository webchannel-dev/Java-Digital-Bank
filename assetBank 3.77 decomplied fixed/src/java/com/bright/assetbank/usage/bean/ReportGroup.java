/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ReportGroup extends ReportEntity
/*    */ {
/* 29 */   private long m_lGroupId = 0L;
/* 30 */   private String m_sGroupName = null;
/*    */ 
/* 32 */   private Vector m_vUsers = null;
/*    */ 
/*    */   public long getGroupId()
/*    */   {
/* 38 */     return this.m_lGroupId;
/*    */   }
/*    */ 
/*    */   public void setGroupId(long a_sGroupId)
/*    */   {
/* 44 */     this.m_lGroupId = a_sGroupId;
/*    */   }
/*    */ 
/*    */   public String getGroupName()
/*    */   {
/* 50 */     return this.m_sGroupName;
/*    */   }
/*    */ 
/*    */   public void setGroupName(String a_sGroupName)
/*    */   {
/* 56 */     this.m_sGroupName = a_sGroupName;
/*    */   }
/*    */ 
/*    */   public Vector getUsers()
/*    */   {
/* 62 */     if (this.m_vUsers == null)
/*    */     {
/* 64 */       this.m_vUsers = new Vector();
/*    */     }
/*    */ 
/* 67 */     return this.m_vUsers;
/*    */   }
/*    */ 
/*    */   public void setUsers(Vector a_sUsers)
/*    */   {
/* 73 */     this.m_vUsers = a_sUsers;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.ReportGroup
 * JD-Core Version:    0.6.0
 */