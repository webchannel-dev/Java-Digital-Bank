/*     */ package com.bright.assetbank.usage.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.usage.form.DateRangeForm;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class ScheduledReport extends DateRangeForm
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  34 */   private long m_iId = 0L;
/*  35 */   private Date m_dtNextSendDate = null;
/*  36 */   private String m_sReportName = null;
/*  37 */   private String m_sReportPeriod = null;
/*  38 */   private int m_sReportType = 0;
/*  39 */   private String m_sUsername = null;
/*  40 */   private boolean m_bSortAscending = false;
/*  41 */   private Vector m_vGroups = null;
/*     */ 
/*     */   public long getId()
/*     */   {
/*  45 */     return this.m_iId;
/*     */   }
/*     */ 
/*     */   public void setId(long a_iId) {
/*  49 */     this.m_iId = a_iId;
/*     */   }
/*     */ 
/*     */   public Date getNextSendDate() {
/*  53 */     return this.m_dtNextSendDate;
/*     */   }
/*     */ 
/*     */   public void setNextSendDate(Date a_dtNextSendDate) {
/*  57 */     this.m_dtNextSendDate = a_dtNextSendDate;
/*     */   }
/*     */ 
/*     */   public String getReportPeriod() {
/*  61 */     return this.m_sReportPeriod;
/*     */   }
/*     */ 
/*     */   public void setReportPeriod(String a_sReportPeriod) {
/*  65 */     this.m_sReportPeriod = a_sReportPeriod;
/*     */   }
/*     */ 
/*     */   public String getReportName() {
/*  69 */     return this.m_sReportName;
/*     */   }
/*     */ 
/*     */   public void setReportName(String a_sReportName) {
/*  73 */     this.m_sReportName = a_sReportName;
/*     */   }
/*     */ 
/*     */   public int getReportType() {
/*  77 */     return this.m_sReportType;
/*     */   }
/*     */ 
/*     */   public void setReportType(int a_sReportType) {
/*  81 */     this.m_sReportType = a_sReportType;
/*     */   }
/*     */ 
/*     */   public boolean getSortAscending() {
/*  85 */     return this.m_bSortAscending;
/*     */   }
/*     */ 
/*     */   public void setSortAscending(boolean a_bSortAscending) {
/*  89 */     this.m_bSortAscending = a_bSortAscending;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/*  93 */     return this.m_sUsername;
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sUsername) {
/*  97 */     this.m_sUsername = a_sUsername;
/*     */   }
/*     */ 
/*     */   public Vector getGroups() {
/* 101 */     return this.m_vGroups;
/*     */   }
/*     */ 
/*     */   public void setGroups(Vector a_vGroups) {
/* 105 */     this.m_vGroups = a_vGroups;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.ScheduledReport
 * JD-Core Version:    0.6.0
 */