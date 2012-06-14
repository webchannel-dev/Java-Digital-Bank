/*     */ package com.bright.assetbank.updater.bean;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class VersionDetail
/*     */ {
/*  26 */   private String m_sVersionNumber = null;
/*     */ 
/*  29 */   private Vector m_changeDescriptions = new Vector();
/*     */ 
/*  32 */   private Vector m_successMessages = new Vector();
/*     */ 
/*  35 */   private boolean m_bUpdaterChanged = false;
/*     */ 
/*  38 */   private boolean m_bIsPrivate = false;
/*     */ 
/*     */   public String getVersionNumber()
/*     */   {
/*  46 */     return this.m_sVersionNumber;
/*     */   }
/*     */ 
/*     */   public void setVersionNumber(String a_dtVersionNumber)
/*     */   {
/*  54 */     this.m_sVersionNumber = a_dtVersionNumber;
/*     */   }
/*     */ 
/*     */   public Collection getChangeDescriptions()
/*     */   {
/*  62 */     return this.m_changeDescriptions;
/*     */   }
/*     */ 
/*     */   public void addChangeDescription(String a_sChangeDescription)
/*     */   {
/*  70 */     this.m_changeDescriptions.add(a_sChangeDescription);
/*     */   }
/*     */ 
/*     */   public Vector getSuccessMessages()
/*     */   {
/*  79 */     return this.m_successMessages;
/*     */   }
/*     */ 
/*     */   public void addSuccessMessage(String a_sSuccessMessage)
/*     */   {
/*  88 */     this.m_successMessages.add(a_sSuccessMessage);
/*     */   }
/*     */ 
/*     */   public boolean isUpdaterChanged()
/*     */   {
/*  97 */     return this.m_bUpdaterChanged;
/*     */   }
/*     */ 
/*     */   public void setUpdaterChanged(boolean a_bUpdaterChanged)
/*     */   {
/* 105 */     this.m_bUpdaterChanged = a_bUpdaterChanged;
/*     */   }
/*     */ 
/*     */   public boolean isPrivate()
/*     */   {
/* 113 */     return this.m_bIsPrivate;
/*     */   }
/*     */ 
/*     */   public void setPrivate(boolean a_dtIsPrivate)
/*     */   {
/* 121 */     this.m_bIsPrivate = a_dtIsPrivate;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.bean.VersionDetail
 * JD-Core Version:    0.6.0
 */