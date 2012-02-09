/*    */ package com.bright.assetbank.custom.indesign.pdf.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class InDesignPDFJob
/*    */   implements Serializable
/*    */ {
/*    */   private long m_id;
/*    */   private long m_assetId;
/*    */   private int m_jobStatusId;
/*    */ 
/*    */   public long getId()
/*    */   {
/* 28 */     return this.m_id;
/*    */   }
/*    */ 
/*    */   public void setId(long a_id)
/*    */   {
/* 33 */     this.m_id = a_id;
/*    */   }
/*    */ 
/*    */   public long getAssetId()
/*    */   {
/* 38 */     return this.m_assetId;
/*    */   }
/*    */ 
/*    */   public void setAssetId(long a_assetId)
/*    */   {
/* 43 */     this.m_assetId = a_assetId;
/*    */   }
/*    */ 
/*    */   public int getJobStatusId()
/*    */   {
/* 48 */     return this.m_jobStatusId;
/*    */   }
/*    */ 
/*    */   public void setJobStatusId(int a_jobStatusId)
/*    */   {
/* 53 */     this.m_jobStatusId = a_jobStatusId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.pdf.bean.InDesignPDFJob
 * JD-Core Version:    0.6.0
 */