/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ 
/*     */ public class VideoConversionInfo extends ImageConversionInfo
/*     */ {
/*  34 */   private double m_dDuration = -1.0D;
/*  35 */   private boolean m_bPreview = false;
/*  36 */   private boolean m_bCompressFile = false;
/*  37 */   private int m_iFrameRate = -1;
/*  38 */   private int m_iAudioSampleRate = -1;
/*  39 */   private double m_dStartOffset = -1.0D;
/*  40 */   private ABUserProfile m_userProfile = null;
/*  41 */   private String m_sVideoBitrate = null;
/*  42 */   private long m_sAudioBitrate = -1L;
/*  43 */   private int m_iPreviewStartFrame = 0;
/*     */ 
/*     */   public void setDuration(double a_dDuration)
/*     */   {
/*  47 */     this.m_dDuration = a_dDuration;
/*     */   }
/*     */ 
/*     */   public double getDuration()
/*     */   {
/*  52 */     return this.m_dDuration;
/*     */   }
/*     */ 
/*     */   public void setPreview(boolean a_bPreview)
/*     */   {
/*  57 */     this.m_bPreview = a_bPreview;
/*     */   }
/*     */ 
/*     */   public boolean getPreview()
/*     */   {
/*  62 */     return this.m_bPreview;
/*     */   }
/*     */ 
/*     */   public void setCompressFile(boolean a_bCompressFile)
/*     */   {
/*  67 */     this.m_bCompressFile = a_bCompressFile;
/*     */   }
/*     */ 
/*     */   public boolean getCompressFile()
/*     */   {
/*  72 */     return this.m_bCompressFile;
/*     */   }
/*     */ 
/*     */   public void setFrameRate(int a_iFrameRate)
/*     */   {
/*  77 */     this.m_iFrameRate = a_iFrameRate;
/*     */   }
/*     */ 
/*     */   public int getFrameRate()
/*     */   {
/*  82 */     return this.m_iFrameRate;
/*     */   }
/*     */ 
/*     */   public void setAudioSampleRate(int a_iAudioSampleRate)
/*     */   {
/*  87 */     this.m_iAudioSampleRate = a_iAudioSampleRate;
/*     */   }
/*     */ 
/*     */   public int getAudioSampleRate()
/*     */   {
/*  92 */     return this.m_iAudioSampleRate;
/*     */   }
/*     */ 
/*     */   public void setStartOffset(double a_dStartOffset)
/*     */   {
/*  97 */     this.m_dStartOffset = a_dStartOffset;
/*     */   }
/*     */ 
/*     */   public double getStartOffset()
/*     */   {
/* 102 */     return this.m_dStartOffset;
/*     */   }
/*     */ 
/*     */   public void setUserProfile(ABUserProfile a_userProfile)
/*     */   {
/* 107 */     this.m_userProfile = a_userProfile;
/*     */   }
/*     */ 
/*     */   public ABUserProfile getUserProfile()
/*     */   {
/* 112 */     return this.m_userProfile;
/*     */   }
/*     */ 
/*     */   public void setVideoBitrate(String a_sVideoBitrate)
/*     */   {
/* 117 */     this.m_sVideoBitrate = a_sVideoBitrate;
/*     */   }
/*     */ 
/*     */   public String getVideoBitrate()
/*     */   {
/* 122 */     return this.m_sVideoBitrate;
/*     */   }
/*     */ 
/*     */   public void setAudioBitrate(long a_sAudioBitrate)
/*     */   {
/* 127 */     this.m_sAudioBitrate = a_sAudioBitrate;
/*     */   }
/*     */ 
/*     */   public long getAudioBitrate()
/*     */   {
/* 132 */     return this.m_sAudioBitrate;
/*     */   }
/*     */ 
/*     */   public void setPreviewStartFrame(int a_iPreviewStartFrame)
/*     */   {
/* 137 */     this.m_iPreviewStartFrame = a_iPreviewStartFrame;
/*     */   }
/*     */ 
/*     */   public int getPreviewStartFrame()
/*     */   {
/* 142 */     return this.m_iPreviewStartFrame;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.VideoConversionInfo
 * JD-Core Version:    0.6.0
 */