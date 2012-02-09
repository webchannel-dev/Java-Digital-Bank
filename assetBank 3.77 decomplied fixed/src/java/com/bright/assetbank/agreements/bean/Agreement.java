/*     */ package com.bright.assetbank.agreements.bean;
/*     */ 
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class Agreement extends DataBean
/*     */ {
/*     */   private Vector m_vecOrgUnitIds;
/*  37 */   private String m_sTitle = "";
/*  38 */   private String m_sBody = "";
/*  39 */   private String m_sAccessLevelName = "";
/*  40 */   private String m_sExpiryString = "";
/*     */   private Date m_dtDateActivated;
/*     */   private boolean m_bIsAvailableToAll;
/*     */   private boolean m_bIsSharedWithOU;
/*     */   private Category m_category;
/*     */   private BrightDate m_dtExpiry;
/*     */ 
/*     */   public Vector getOrgUnitIds()
/*     */   {
/*  49 */     if (this.m_vecOrgUnitIds == null)
/*     */     {
/*  51 */       return new Vector();
/*     */     }
/*     */ 
/*  54 */     return this.m_vecOrgUnitIds;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitIds(Vector a_vecOrgUnitIds) {
/*  58 */     this.m_vecOrgUnitIds = a_vecOrgUnitIds;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  64 */     return this.m_sTitle;
/*     */   }
/*     */ 
/*     */   public void setTitle(String a_sTitle) {
/*  68 */     this.m_sTitle = a_sTitle;
/*     */   }
/*     */ 
/*     */   public String getBody()
/*     */   {
/*  74 */     return this.m_sBody;
/*     */   }
/*     */ 
/*     */   public void setBody(String a_sBody) {
/*  78 */     this.m_sBody = a_sBody;
/*     */   }
/*     */ 
/*     */   public String getAccessLevelName()
/*     */   {
/*  84 */     return this.m_sAccessLevelName;
/*     */   }
/*     */ 
/*     */   public void setAccessLevelName(String a_sAccessLevelName) {
/*  88 */     this.m_sAccessLevelName = a_sAccessLevelName;
/*     */   }
/*     */ 
/*     */   public BrightDate getExpiry()
/*     */   {
/*  94 */     if (this.m_dtExpiry == null)
/*     */     {
/*  96 */       this.m_dtExpiry = new BrightDate();
/*     */     }
/*     */ 
/*  99 */     return this.m_dtExpiry;
/*     */   }
/*     */ 
/*     */   public void setExpiry(BrightDate a_dtExpiry) {
/* 103 */     this.m_dtExpiry = a_dtExpiry;
/*     */   }
/*     */ 
/*     */   public Date getDateActivated()
/*     */   {
/* 108 */     return this.m_dtDateActivated;
/*     */   }
/*     */ 
/*     */   public void setDateActivated(Date a_dtDateActivated) {
/* 112 */     this.m_dtDateActivated = a_dtDateActivated;
/*     */   }
/*     */ 
/*     */   public boolean getIsAvailableToAll()
/*     */   {
/* 117 */     return this.m_bIsAvailableToAll;
/*     */   }
/*     */ 
/*     */   public void setIsAvailableToAll(boolean a_bIsAvailableToAll) {
/* 121 */     this.m_bIsAvailableToAll = a_bIsAvailableToAll;
/*     */   }
/*     */ 
/*     */   public boolean getIsSharedWithOU()
/*     */   {
/* 127 */     return this.m_bIsSharedWithOU;
/*     */   }
/*     */ 
/*     */   public void setIsSharedWithOU(boolean a_bIsSharedWithOU) {
/* 131 */     this.m_bIsSharedWithOU = a_bIsSharedWithOU;
/*     */   }
/*     */ 
/*     */   public Category getCategory()
/*     */   {
/* 136 */     return this.m_category;
/*     */   }
/*     */ 
/*     */   public void setCategory(Category a_category) {
/* 140 */     this.m_category = a_category;
/*     */   }
/*     */ 
/*     */   public String getExpiryString()
/*     */   {
/* 145 */     if (this.m_sExpiryString == null)
/*     */     {
/* 147 */       this.m_sExpiryString = "";
/*     */     }
/*     */ 
/* 150 */     return this.m_sExpiryString;
/*     */   }
/*     */ 
/*     */   public void setExpiryString(String a_sExpiryString) {
/* 154 */     this.m_sExpiryString = a_sExpiryString;
/*     */   }
/*     */ 
/*     */   public boolean isBodyHtml()
/*     */   {
/* 159 */     return (this.m_sBody != null) && (this.m_sBody.matches(".*<[a-zA-Z\\-]>.*"));
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.agreements.bean.Agreement
 * JD-Core Version:    0.6.0
 */