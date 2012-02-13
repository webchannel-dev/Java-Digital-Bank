/*     */ package com.bright.assetbank.assetbox.bean;
/*     */ 
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class AssetBoxComparator
/*     */   implements Comparator, Serializable
/*     */ {
/*     */   public int compare(Object a_o1, Object a_o2)
/*     */   {
/*  54 */     int iResult = 0;
/*     */ 
/*  56 */     if (!(a_o1 instanceof AssetInList))
/*  57 */       throw new ClassCastException();
/*  58 */     if (!(a_o2 instanceof AssetInList)) {
/*  59 */       throw new ClassCastException();
/*     */     }
/*  61 */     AssetInList a1 = (AssetInList)a_o1;
/*  62 */     AssetInList a2 = (AssetInList)a_o2;
/*     */ 
/*  64 */     if (getStatusScore(a1) > getStatusScore(a2))
/*     */     {
/*  67 */       iResult = -1;
/*     */     }
/*  69 */     if (getStatusScore(a1) < getStatusScore(a2))
/*     */     {
/*  72 */       iResult = 1;
/*     */     }
/*     */ 
/*  75 */     if (getStatusScore(a1) == getStatusScore(a2))
/*     */     {
/*  78 */       if (a1.getSequenceNumber() <= a2.getSequenceNumber())
/*     */       {
/*  80 */         iResult = -1;
/*     */       }
/*  82 */       if (a1.getSequenceNumber() > a2.getSequenceNumber())
/*     */       {
/*  84 */         iResult = 1;
/*     */       }
/*     */     }
/*     */ 
/*  88 */     return iResult;
/*     */   }
/*     */ 
/*     */   private int getStatusScore(AssetInList a_ail)
/*     */   {
/*  93 */     int iScore = 0;
/*     */ 
/*  96 */     if (a_ail.getIsApprovalRejected()) iScore = 1;
/*  97 */     if (a_ail.getIsApprovalPending()) iScore = 2;
/*  98 */     if (a_ail.getIsApprovalRequestable()) iScore = 3;
/*  99 */     if (a_ail.getIsApprovalApproved()) iScore = 4;
/* 100 */     if (a_ail.getIsDownloadable()) iScore = 5;
/*     */ 
/* 102 */     return iScore;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.bean.AssetBoxComparator
 * JD-Core Version:    0.6.0
 */