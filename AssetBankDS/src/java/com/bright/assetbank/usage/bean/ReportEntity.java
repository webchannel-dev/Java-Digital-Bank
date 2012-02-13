/*     */ package com.bright.assetbank.usage.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class ReportEntity
/*     */ {
/*     */   private int m_iCount;
/*     */   private long m_lId;
/*     */   String m_sPaddedId;
/*     */ 
/*     */   public ReportEntity()
/*     */   {
/*  31 */     this.m_iCount = 0;
/*  32 */     this.m_lId = 0L;
/*  33 */     this.m_sPaddedId = null;
/*     */   }
/*     */ 
/*     */   public int getCount()
/*     */   {
/*  75 */     return this.m_iCount;
/*     */   }
/*     */ 
/*     */   public void setCount(int a_sNumUploads)
/*     */   {
/*  83 */     this.m_iCount = a_sNumUploads;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  89 */     if (this.m_sPaddedId == null)
/*     */     {
/*  91 */       this.m_sPaddedId = Asset.getPaddedId(this.m_lId);
/*     */     }
/*     */ 
/*  94 */     return this.m_sPaddedId;
/*     */   }
/*     */ 
/*     */   public void setId(long a_sId)
/*     */   {
/* 100 */     this.m_lId = a_sId;
/*     */   }
/*     */ 
/*     */   public long getIdForAsset()
/*     */   {
/* 105 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public static class CountComparatorAsc
/*     */     implements Comparator
/*     */   {
/*     */     public int compare(Object o1, Object o2)
/*     */     {
/*  60 */       if (((o1 instanceof ReportEntity)) && ((o2 instanceof ReportEntity)))
/*     */       {
/*  62 */         return ((ReportEntity)o1).getCount() - ((ReportEntity)o2).getCount();
/*     */       }
/*     */ 
/*  65 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class CountComparatorDesc
/*     */     implements Comparator
/*     */   {
/*     */     public int compare(Object o1, Object o2)
/*     */     {
/*  43 */       if (((o1 instanceof ReportEntity)) && ((o2 instanceof ReportEntity)))
/*     */       {
/*  45 */         return ((ReportEntity)o2).getCount() - ((ReportEntity)o1).getCount();
/*     */       }
/*     */ 
/*  48 */       return 0;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.ReportEntity
 * JD-Core Version:    0.6.0
 */