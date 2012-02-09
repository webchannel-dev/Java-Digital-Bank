/*     */ package com.bright.framework.user.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ 
/*     */ public class ChangePasswordForm extends Bn2Form
/*     */ {
/*  29 */   private long m_lUserId = 0L;
/*  30 */   private String m_sOldPassword = null;
/*  31 */   private String m_sNewPassword = null;
/*  32 */   private String m_sConfirmNewPassword = null;
/*  33 */   private boolean m_bChangeFailed = false;
/*     */ 
/*     */   public void setUserId(long a_lId)
/*     */   {
/*  37 */     this.m_lUserId = a_lId;
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/*  42 */     return this.m_lUserId;
/*     */   }
/*     */ 
/*     */   public String getOldPassword()
/*     */   {
/*  57 */     return this.m_sOldPassword;
/*     */   }
/*     */ 
/*     */   public void setOldPassword(String a_sOldPassword)
/*     */   {
/*  72 */     this.m_sOldPassword = a_sOldPassword;
/*     */   }
/*     */ 
/*     */   public String getNewPassword()
/*     */   {
/*  87 */     return this.m_sNewPassword;
/*     */   }
/*     */ 
/*     */   public void setNewPassword(String a_sNewPassword)
/*     */   {
/* 102 */     this.m_sNewPassword = a_sNewPassword;
/*     */   }
/*     */ 
/*     */   public String getConfirmNewPassword()
/*     */   {
/* 117 */     return this.m_sConfirmNewPassword;
/*     */   }
/*     */ 
/*     */   public void setConfirmNewPassword(String a_sConfirmNewPassword)
/*     */   {
/* 132 */     this.m_sConfirmNewPassword = a_sConfirmNewPassword;
/*     */   }
/*     */ 
/*     */   public void setChangeFailed(boolean a_bChangeFailed)
/*     */   {
/* 137 */     this.m_bChangeFailed = a_bChangeFailed;
/*     */   }
/*     */ 
/*     */   public boolean getChangeFailed()
/*     */   {
/* 142 */     return this.m_bChangeFailed;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.form.ChangePasswordForm
 * JD-Core Version:    0.6.0
 */