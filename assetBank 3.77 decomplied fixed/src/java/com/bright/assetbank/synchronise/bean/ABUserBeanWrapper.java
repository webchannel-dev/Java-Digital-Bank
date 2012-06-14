/*     */ package com.bright.assetbank.synchronise.bean;
/*     */ 
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.file.BeanWrapper;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ABUserBeanWrapper
/*     */   implements BeanWrapper
/*     */ {
/*  32 */   private ABUser m_user = null;
/*     */ 
/*     */   public Object getWrappedObject()
/*     */   {
/*  40 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public void setObjectToWrap(Object a_source)
/*     */   {
/*  49 */     if (!(a_source instanceof ABUser))
/*     */     {
/*  51 */       throw new IllegalArgumentException("ABUserBeanWrapper can only wrap ABUser instances");
/*     */     }
/*  53 */     this.m_user = ((ABUser)a_source);
/*     */   }
/*     */ 
/*     */   public void setOrganisation(String a_sValue)
/*     */   {
/*  58 */     this.m_user.setOrganisation(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setName(String a_sValue)
/*     */   {
/*  69 */     if (StringUtils.isNotEmpty(a_sValue))
/*     */     {
/*  71 */       String[] name = a_sValue.split(" ", 2);
/*  72 */       this.m_user.setForename(name[0]);
/*  73 */       if (name.length > 1)
/*     */       {
/*  75 */         this.m_user.setSurname(name[1]);
/*  76 */         this.m_user.setUsername((name[0].charAt(0) + name[1]).toLowerCase().replace(" ", ""));
/*     */       }
/*     */       else
/*     */       {
/*  80 */         this.m_user.setUsername(name[0].toLowerCase());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setForename(String a_sValue)
/*     */   {
/*  87 */     this.m_user.setForename(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setSurname(String a_sValue) {
/*  91 */     this.m_user.setSurname(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setUsername(String a_sValue) {
/*  95 */     this.m_user.setUsername(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setPassword(String a_sValue) {
/*  99 */     this.m_user.setPassword(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setEmailAddress(String a_sValue) {
/* 103 */     this.m_user.setEmailAddress(a_sValue);
/*     */   }
/*     */ 
/*     */   public void setAddress(String a_sAddress) {
/* 107 */     this.m_user.setAddress(a_sAddress);
/*     */   }
/*     */ 
/*     */   public void setTelephone(String a_sTelephone) {
/* 111 */     this.m_user.setTelephone(a_sTelephone);
/*     */   }
/*     */ 
/*     */   public void setFax(String a_sFax) {
/* 115 */     this.m_user.setFax(a_sFax);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ABUserBeanWrapper
 * JD-Core Version:    0.6.0
 */