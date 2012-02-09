/*     */ package com.bright.assetbank.workset.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class UnsubmittedAssets
/*     */ {
/*  32 */   private Vector<LightweightAsset> m_listAssets = null;
/*  33 */   private boolean m_bMaxResultsExceeded = false;
/*     */ 
/*  36 */   private HashMap m_hmAssetSubmitOptions = null;
/*     */ 
/*  39 */   private UnsubmittedItemOptions m_singleSubmitOptions = null;
/*     */ 
/*     */   public long getReturnedNumberResults()
/*     */   {
/*  48 */     long l = 0L;
/*  49 */     if (this.m_listAssets != null)
/*     */     {
/*  51 */       l = this.m_listAssets.size();
/*     */     }
/*     */ 
/*  54 */     return l;
/*     */   }
/*     */ 
/*     */   public Vector<LightweightAsset> getListAssets()
/*     */   {
/*  60 */     return this.m_listAssets;
/*     */   }
/*     */ 
/*     */   public void setListAssets(Vector<LightweightAsset> a_sListAssets)
/*     */   {
/*  65 */     this.m_listAssets = a_sListAssets;
/*     */   }
/*     */ 
/*     */   public boolean getMaxResultsExceeded()
/*     */   {
/*  71 */     return this.m_bMaxResultsExceeded;
/*     */   }
/*     */ 
/*     */   public void setMaxResultsExceeded(boolean a_sMaxResultsExceeded)
/*     */   {
/*  77 */     this.m_bMaxResultsExceeded = a_sMaxResultsExceeded;
/*     */   }
/*     */ 
/*     */   public HashMap getAssetSubmitOptions()
/*     */   {
/*  83 */     return this.m_hmAssetSubmitOptions;
/*     */   }
/*     */ 
/*     */   public void setAssetSubmitOptions(HashMap a_sHmAssetSubmitOptions)
/*     */   {
/*  89 */     this.m_hmAssetSubmitOptions = a_sHmAssetSubmitOptions;
/*     */   }
/*     */ 
/*     */   public UnsubmittedItemOptions getSingleSubmitOptions()
/*     */   {
/*  95 */     return this.m_singleSubmitOptions;
/*     */   }
/*     */ 
/*     */   public void setSingleSubmitOptions(UnsubmittedItemOptions a_sSingleSubmitOptions)
/*     */   {
/* 101 */     this.m_singleSubmitOptions = a_sSingleSubmitOptions;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workset.bean.UnsubmittedAssets
 * JD-Core Version:    0.6.0
 */