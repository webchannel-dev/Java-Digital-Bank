/*     */ package com.bright.assetbank.feedback.bean;
/*     */ 
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class AssetFeedback extends DataBean
/*     */ {
/*  33 */   private long m_lUserId = 0L;
/*  34 */   private long m_lAssetId = 0L;
/*  35 */   private int m_iRating = 0;
/*  36 */   private String m_sComments = null;
/*  37 */   private Date m_dtDate = null;
/*  38 */   private ABUser m_user = null;
/*  39 */   private String m_sSubject = null;
/*     */ 
/*     */   public long getUserId()
/*     */   {
/*  43 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_lUserId)
/*     */   {
/*  48 */     this.m_lUserId = a_lUserId;
/*     */   }
/*     */ 
/*     */   public long getAssetId()
/*     */   {
/*  53 */     return this.m_lAssetId;
/*     */   }
/*     */ 
/*     */   public void setAssetId(long a_lAssetId)
/*     */   {
/*  58 */     this.m_lAssetId = a_lAssetId;
/*     */   }
/*     */ 
/*     */   public int getRating()
/*     */   {
/*  63 */     return this.m_iRating;
/*     */   }
/*     */ 
/*     */   public void setRating(int a_iRating)
/*     */   {
/*  68 */     this.m_iRating = a_iRating;
/*     */   }
/*     */ 
/*     */   public String getComments()
/*     */   {
/*  73 */     return this.m_sComments;
/*     */   }
/*     */ 
/*     */   public void setComments(String a_sComments)
/*     */   {
/*  78 */     this.m_sComments = a_sComments;
/*     */   }
/*     */ 
/*     */   public void setDate(Date a_dtDate)
/*     */   {
/*  83 */     this.m_dtDate = a_dtDate;
/*     */   }
/*     */ 
/*     */   public Date getDate()
/*     */   {
/*  88 */     return this.m_dtDate;
/*     */   }
/*     */ 
/*     */   public void setUser(ABUser a_user)
/*     */   {
/*  93 */     this.m_user = a_user;
/*     */   }
/*     */ 
/*     */   public ABUser getUser()
/*     */   {
/*  98 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public void setSubject(String a_sSubject)
/*     */   {
/* 103 */     this.m_sSubject = a_sSubject;
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/* 108 */     return this.m_sSubject;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.feedback.bean.AssetFeedback
 * JD-Core Version:    0.6.0
 */