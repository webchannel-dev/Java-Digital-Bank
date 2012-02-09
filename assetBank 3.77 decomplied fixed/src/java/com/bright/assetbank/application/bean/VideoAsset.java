/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.util.VideoUtil;
/*     */ 
/*     */ public class VideoAsset extends ImageAsset
/*     */   implements VideoInfo
/*     */ {
/*  34 */   private int m_iFrameOffset = 0;
/*  35 */   private long m_lDuration = 0L;
/*  36 */   private float m_fPAR = 0.0F;
/*  37 */   private boolean m_previewClipBeingGenerated = false;
/*     */ 
/*     */   public VideoAsset()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VideoAsset(Asset a_asset)
/*     */   {
/*  57 */     super(a_asset);
/*     */   }
/*     */ 
/*     */   public VideoAsset(AudioAsset a_asset)
/*     */   {
/*  65 */     super(a_asset);
/*  66 */     this.m_lDuration = a_asset.getDuration();
/*     */   }
/*     */ 
/*     */   public void setFrameOffset(int a_iFrameOffset)
/*     */   {
/*  71 */     this.m_iFrameOffset = a_iFrameOffset;
/*     */   }
/*     */ 
/*     */   public int getFrameOffset()
/*     */   {
/*  76 */     return this.m_iFrameOffset;
/*     */   }
/*     */ 
/*     */   public long getDuration()
/*     */   {
/*  81 */     return this.m_lDuration;
/*     */   }
/*     */ 
/*     */   public void setDuration(long a_iDuration)
/*     */   {
/*  86 */     this.m_lDuration = a_iDuration;
/*     */   }
/*     */ 
/*     */   public void setPAR(float a_fPAR)
/*     */   {
/*  91 */     this.m_fPAR = a_fPAR;
/*     */   }
/*     */ 
/*     */   public float getPAR()
/*     */   {
/*  96 */     return this.m_fPAR;
/*     */   }
/*     */ 
/*     */   public int getDisplayHeight()
/*     */   {
/* 101 */     return VideoUtil.getDisplayHeight(this.m_iHeight, this.m_fPAR);
/*     */   }
/*     */ 
/*     */   public void setPreviewClipBeingGenerated(boolean a_previewClipBeingGenerated)
/*     */   {
/* 106 */     this.m_previewClipBeingGenerated = a_previewClipBeingGenerated;
/*     */   }
/*     */ 
/*     */   public boolean getPreviewClipBeingGenerated()
/*     */   {
/* 111 */     return this.m_previewClipBeingGenerated;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.VideoAsset
 * JD-Core Version:    0.6.0
 */