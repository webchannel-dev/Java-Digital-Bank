/*     */ package com.bright.assetbank.repurposing.bean;
/*     */ 
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class RepurposedImage extends DataBean
/*     */ {
/*  34 */   private String m_sUrl = null;
/*  35 */   private ABUser m_createdByUser = null;
/*  36 */   private long m_lAssetId = 0L;
/*  37 */   private long m_lFileFormatId = 0L;
/*  38 */   private int m_iHeight = 0;
/*  39 */   private int m_iWidth = 0;
/*  40 */   private Date m_dtCreatedDate = null;
/*  41 */   private String m_sEmbeddableHtml = null;
/*     */ 
/*     */   public Date getCreatedDate()
/*     */   {
/*  45 */     return this.m_dtCreatedDate;
/*     */   }
/*     */ 
/*     */   public void setCreatedDate(Date a_dtCreatedDate) {
/*  49 */     this.m_dtCreatedDate = a_dtCreatedDate;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/*  53 */     return this.m_iHeight;
/*     */   }
/*     */ 
/*     */   public void setHeight(int a_iHeight) {
/*  57 */     this.m_iHeight = a_iHeight;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/*  61 */     return this.m_iWidth;
/*     */   }
/*     */ 
/*     */   public void setWidth(int a_iWidth) {
/*  65 */     this.m_iWidth = a_iWidth;
/*     */   }
/*     */ 
/*     */   public long getAssetId() {
/*  69 */     return this.m_lAssetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_lAssetId) {
/*  73 */     this.m_lAssetId = a_lAssetId;
/*     */   }
/*     */ 
/*     */   public ABUser getCreatedByUser() {
/*  77 */     return this.m_createdByUser;
/*     */   }
/*     */ 
/*     */   public void setCreatedByUser(ABUser a_createdByUserId) {
/*  81 */     this.m_createdByUser = a_createdByUserId;
/*     */   }
/*     */ 
/*     */   public long getFileFormatId() {
/*  85 */     return this.m_lFileFormatId;
/*     */   }
/*     */ 
/*     */   public void setFileFormatId(long a_lFileFormatId) {
/*  89 */     this.m_lFileFormatId = a_lFileFormatId;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  93 */     return this.m_sUrl;
/*     */   }
/*     */ 
/*     */   public void setUrl(String a_sUrl) {
/*  97 */     this.m_sUrl = a_sUrl;
/*     */   }
/*     */ 
/*     */   public String getSuffix()
/*     */   {
/* 102 */     if (StringUtils.isNotEmpty(this.m_sUrl))
/*     */     {
/* 104 */       return FileUtil.getSuffix(this.m_sUrl);
/*     */     }
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */   public String getEmbeddableHtml() {
/* 110 */     return this.m_sEmbeddableHtml;
/*     */   }
/*     */ 
/*     */   public void setEmbeddableHtml(String embeddableHtml) {
/* 114 */     this.m_sEmbeddableHtml = embeddableHtml;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.bean.RepurposedImage
 * JD-Core Version:    0.6.0
 */