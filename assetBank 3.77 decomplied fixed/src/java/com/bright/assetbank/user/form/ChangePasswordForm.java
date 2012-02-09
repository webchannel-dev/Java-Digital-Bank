/*     */ package com.bright.assetbank.user.form;
/*     */ 
/*     */ public class ChangePasswordForm extends UserForm
/*     */ {
/*  27 */   private long m_lUserId = 0L;
/*  28 */   private String m_sOldPassword = null;
/*  29 */   private String m_sNewPassword = null;
/*  30 */   private String m_sConfirmNewPassword = null;
/*  31 */   private boolean m_bChangeFailed = false;
/*  32 */   private boolean m_bNotifyUser = false;
/*     */ 
/*     */   public void setUserId(long a_lId)
/*     */   {
/*  36 */     this.m_lUserId = a_lId;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/*  41 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public String getOldPassword()
/*     */   {
/*  56 */     return this.m_sOldPassword;
/*     */   }
/*     */ 
/*     */   public void setOldPassword(String a_sOldPassword)
/*     */   {
/*  71 */     this.m_sOldPassword = a_sOldPassword;
/*     */   }
/*     */ 
/*     */   public String getNewPassword()
/*     */   {
/*  86 */     return this.m_sNewPassword;
/*     */   }
/*     */ 
/*     */   public void setNewPassword(String a_sNewPassword)
/*     */   {
/* 101 */     this.m_sNewPassword = a_sNewPassword;
/*     */   }
/*     */ 
/*     */   public String getConfirmNewPassword()
/*     */   {
/* 116 */     return this.m_sConfirmNewPassword;
/*     */   }
/*     */ 
/*     */   public void setConfirmNewPassword(String a_sConfirmNewPassword)
/*     */   {
/* 131 */     this.m_sConfirmNewPassword = a_sConfirmNewPassword;
/*     */   }
/*     */ 
/*     */   public void setChangeFailed(boolean a_bChangeFailed)
/*     */   {
/* 136 */     this.m_bChangeFailed = a_bChangeFailed;
/*     */   }
/*     */ 
/*     */   public boolean getChangeFailed()
/*     */   {
/* 141 */     return this.m_bChangeFailed;
/*     */   }
/*     */ 
/*     */   public boolean getNotifyUser()
/*     */   {
/* 147 */     return this.m_bNotifyUser;
/*     */   }
/*     */ 
/*     */   public void setNotifyUser(boolean a_sNotifyUser)
/*     */   {
/* 153 */     this.m_bNotifyUser = a_sNotifyUser;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.ChangePasswordForm
 * JD-Core Version:    0.6.0
 */