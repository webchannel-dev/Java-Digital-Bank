/*     */ package com.bright.assetbank.usage.bean;
/*     */ 
/*     */ import com.bright.assetbank.usage.util.MaskUtil;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import java.awt.Dimension;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Mask extends DataBean
/*     */   implements Serializable
/*     */ {
/*     */   private String m_name;
/*     */   private String m_filename;
/*     */   private int m_width;
/*     */   private int m_height;
/*     */ 
/*     */   public String getWebappRelativeThumbnailPath()
/*     */   {
/*  42 */     return MaskUtil.getWebappRelativeThumbnailPath(getId());
/*     */   }
/*     */ 
/*     */   public String getWebappRelativeImagePath()
/*     */   {
/*  50 */     return MaskUtil.getWebappRelativeMaskPath(this.m_filename);
/*     */   }
/*     */ 
/*     */   public String getImageFullPath()
/*     */   {
/*  58 */     return MaskUtil.getMaskFullPath(this.m_filename);
/*     */   }
/*     */ 
/*     */   public Dimension getImageSize()
/*     */   {
/*  66 */     return new Dimension(getWidth(), getHeight());
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  71 */     return this.m_name;
/*     */   }
/*     */ 
/*     */   public void setName(String a_name)
/*     */   {
/*  76 */     this.m_name = a_name;
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/*  81 */     return this.m_filename;
/*     */   }
/*     */ 
/*     */   public void setFilename(String a_filename)
/*     */   {
/*  86 */     this.m_filename = a_filename;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  91 */     return this.m_width;
/*     */   }
/*     */ 
/*     */   public void setWidth(int a_width)
/*     */   {
/*  96 */     this.m_width = a_width;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 101 */     return this.m_height;
/*     */   }
/*     */ 
/*     */   public void setHeight(int a_height)
/*     */   {
/* 106 */     this.m_height = a_height;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.Mask
 * JD-Core Version:    0.6.0
 */