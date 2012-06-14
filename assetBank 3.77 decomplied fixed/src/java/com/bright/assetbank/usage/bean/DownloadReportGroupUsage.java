/*    */ package com.bright.assetbank.usage.bean;
/*    */ 
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class DownloadReportGroupUsage extends ReportEntity
/*    */ {
/* 29 */   private String m_sGroupName = null;
/* 30 */   private Vector m_vecUserUses = new Vector();
/*    */ 
/*    */   public String getGroupName()
/*    */   {
/* 35 */     return this.m_sGroupName;
/*    */   }
/*    */ 
/*    */   public void setGroupName(String a_sGroupName)
/*    */   {
/* 41 */     this.m_sGroupName = a_sGroupName;
/*    */   }
/*    */ 
/*    */   public Vector getUserUses()
/*    */   {
/* 47 */     return this.m_vecUserUses;
/*    */   }
/*    */ 
/*    */   public void setUserUses(Vector a_vecUserUses)
/*    */   {
/* 53 */     this.m_vecUserUses = a_vecUserUses;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.DownloadReportGroupUsage
 * JD-Core Version:    0.6.0
 */