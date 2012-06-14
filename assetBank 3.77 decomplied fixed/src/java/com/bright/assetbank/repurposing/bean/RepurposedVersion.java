/*     */ package com.bright.assetbank.repurposing.bean;
/*     */ 
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class RepurposedVersion extends DataBean
/*     */ {
/*  35 */   private String m_sUrl = null;
/*  36 */   private ABUser m_createdByUser = null;
/*  37 */   private Date m_dtCreatedDate = null;
/*  38 */   private String m_sEmbeddableHtml = null;
/*  39 */   private String m_sHeight = "0";
/*  40 */   private String m_sWidth = "0";
/*  41 */   private String m_sHomepageEmbeddableHtml = null;
/*     */ 
/*     */   public String getHeight()
/*     */   {
/*  45 */     return this.m_sHeight;
/*     */   }
/*     */ 
/*     */   public void setHeight(String a_sHeight) {
/*  49 */     this.m_sHeight = a_sHeight;
/*     */   }
/*     */ 
/*     */   public String getWidth() {
/*  53 */     return this.m_sWidth;
/*     */   }
/*     */ 
/*     */   public void setWidth(String a_sWidth) {
/*  57 */     this.m_sWidth = a_sWidth;
/*     */   }
/*     */ 
/*     */   public Date getCreatedDate() {
/*  61 */     return this.m_dtCreatedDate;
/*     */   }
/*     */ 
/*     */   public void setCreatedDate(Date a_dtCreatedDate) {
/*  65 */     this.m_dtCreatedDate = a_dtCreatedDate;
/*     */   }
/*     */ 
/*     */   public ABUser getCreatedByUser() {
/*  69 */     return this.m_createdByUser;
/*     */   }
/*     */ 
/*     */   public void setCreatedByUser(ABUser a_createdByUserId) {
/*  73 */     this.m_createdByUser = a_createdByUserId;
/*     */   }
/*     */ 
/*     */   public String getEmbeddableHtml() {
/*  77 */     return this.m_sEmbeddableHtml;
/*     */   }
/*     */ 
/*     */   public void setEmbeddableHtml(String embeddableHtml) {
/*  81 */     this.m_sEmbeddableHtml = embeddableHtml;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/*  86 */     return this.m_sUrl;
/*     */   }
/*     */ 
/*     */   public void setUrl(String a_sUrl) {
/*  90 */     this.m_sUrl = a_sUrl;
/*     */   }
/*     */ 
/*     */   public String getSuffix()
/*     */   {
/*  95 */     if (StringUtils.isNotEmpty(this.m_sUrl))
/*     */     {
/*  97 */       return FileUtil.getSuffix(this.m_sUrl);
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   public String getHomepageEmbeddableHtml()
/*     */   {
/* 104 */     return this.m_sHomepageEmbeddableHtml;
/*     */   }
/*     */ 
/*     */   public void setHomepageEmbeddableHtml(String a_sHomepageEmbeddableHtml) {
/* 108 */     this.m_sHomepageEmbeddableHtml = a_sHomepageEmbeddableHtml;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.bean.RepurposedVersion
 * JD-Core Version:    0.6.0
 */