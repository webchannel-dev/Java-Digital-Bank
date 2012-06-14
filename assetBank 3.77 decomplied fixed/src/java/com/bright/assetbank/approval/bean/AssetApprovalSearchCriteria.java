/*     */ package com.bright.assetbank.approval.bean;
/*     */ 
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetApprovalSearchCriteria
/*     */ {
/*  33 */   private Date m_dateStart = null;
/*  34 */   private Date m_dateEnd = null;
/*     */   private long m_userId;
/*  42 */   private Vector m_vecCategoryIds = null;
/*  43 */   private Vector m_vecAssetIds = null;
/*     */   private long m_approvalStatusId;
/*     */ 
/*     */   public long getUserId()
/*     */   {
/*  51 */     return this.m_userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(long a_sUserId)
/*     */   {
/*  60 */     this.m_userId = a_sUserId;
/*     */   }
/*     */ 
/*     */   public long getApprovalStatusId()
/*     */   {
/*  76 */     return this.m_approvalStatusId;
/*     */   }
/*     */ 
/*     */   public void setApprovalStatusId(long a_sApprovalStatusId)
/*     */   {
/*  85 */     this.m_approvalStatusId = a_sApprovalStatusId;
/*     */   }
/*     */ 
/*     */   public Vector getCategoryIds()
/*     */   {
/*  91 */     return this.m_vecCategoryIds;
/*     */   }
/*     */ 
/*     */   public void setCategoryIds(Vector a_vecCategoryIds)
/*     */   {
/*  96 */     this.m_vecCategoryIds = a_vecCategoryIds;
/*     */   }
/*     */ 
/*     */   public void setAssetIds(Vector a_vecAssetIds)
/*     */   {
/* 101 */     this.m_vecAssetIds = a_vecAssetIds;
/*     */   }
/*     */ 
/*     */   public Vector getAssetIds()
/*     */   {
/* 106 */     return this.m_vecAssetIds;
/*     */   }
/*     */ 
/*     */   public void addAssetId(long a_lAssetId)
/*     */   {
/* 111 */     if (getAssetIds() == null)
/*     */     {
/* 113 */       setAssetIds(new Vector());
/*     */     }
/* 115 */     getAssetIds().add(new Long(a_lAssetId));
/*     */   }
/*     */ 
/*     */   public String getAssetIdsString()
/*     */   {
/* 120 */     if (getAssetIds() != null)
/*     */     {
/* 122 */       String[] aIds = new String[getAssetIds().size()];
/* 123 */       for (int i = 0; i < getAssetIds().size(); i++)
/*     */       {
/* 125 */         aIds[i] = String.valueOf(((Long)getAssetIds().elementAt(i)).longValue());
/*     */       }
/* 127 */       return StringUtil.convertStringArrayToString(aIds, ",");
/*     */     }
/* 129 */     return "";
/*     */   }
/*     */ 
/*     */   public Date getDateEnd()
/*     */   {
/* 134 */     return this.m_dateEnd;
/*     */   }
/*     */ 
/*     */   public void setDateEnd(Date a_sDateEnd) {
/* 138 */     this.m_dateEnd = a_sDateEnd;
/*     */   }
/*     */ 
/*     */   public Date getDateStart() {
/* 142 */     return this.m_dateStart;
/*     */   }
/*     */ 
/*     */   public void setDateStart(Date a_sDateStart) {
/* 146 */     this.m_dateStart = a_sDateStart;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.bean.AssetApprovalSearchCriteria
 * JD-Core Version:    0.6.0
 */