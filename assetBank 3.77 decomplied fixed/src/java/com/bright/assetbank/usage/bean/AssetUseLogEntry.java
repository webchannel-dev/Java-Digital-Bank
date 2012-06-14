/*     */ package com.bright.assetbank.usage.bean;
/*     */ 
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class AssetUseLogEntry extends DataBean
/*     */ {
/*  33 */   private Date m_date = null;
/*  34 */   private String m_description = null;
/*  35 */   private String m_type = null;
/*  36 */   private ABUser m_user = null;
/*  37 */   private String m_sIpAddress = null;
/*  38 */   private String m_sMoreDetails = null;
/*  39 */   private ArrayList<String> m_vecSecondaryUsageTypes = null;
/*     */ 
/*     */   public Date getDate()
/*     */   {
/*  44 */     return this.m_date;
/*     */   }
/*     */ 
/*     */   public void setDate(Date a_sDate)
/*     */   {
/*  50 */     this.m_date = a_sDate;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  56 */     return this.m_description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/*  62 */     this.m_description = a_sDescription;
/*     */   }
/*     */ 
/*     */   public ABUser getUser()
/*     */   {
/*  68 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public void setUser(ABUser a_sUser)
/*     */   {
/*  74 */     this.m_user = a_sUser;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  80 */     return this.m_type;
/*     */   }
/*     */ 
/*     */   public void setType(String a_sType)
/*     */   {
/*  86 */     this.m_type = a_sType;
/*     */   }
/*     */ 
/*     */   public String getIpAddress()
/*     */   {
/*  91 */     return this.m_sIpAddress;
/*     */   }
/*     */ 
/*     */   public void setIpAddress(String a_sIpAddress)
/*     */   {
/*  96 */     this.m_sIpAddress = a_sIpAddress;
/*     */   }
/*     */ 
/*     */   public void setMoreDetails(String a_sMoreDetails)
/*     */   {
/* 101 */     this.m_sMoreDetails = a_sMoreDetails;
/*     */   }
/*     */ 
/*     */   public String getMoreDetails()
/*     */   {
/* 106 */     return this.m_sMoreDetails;
/*     */   }
/*     */ 
/*     */   public void setSecondaryUsageTypes(ArrayList<String> a_vecSecondaryUsageTypes)
/*     */   {
/* 111 */     this.m_vecSecondaryUsageTypes = a_vecSecondaryUsageTypes;
/*     */   }
/*     */ 
/*     */   public ArrayList<String> getSecondaryUsageTypes()
/*     */   {
/* 116 */     if (this.m_vecSecondaryUsageTypes == null)
/*     */     {
/* 118 */       this.m_vecSecondaryUsageTypes = new ArrayList();
/*     */     }
/*     */ 
/* 121 */     return this.m_vecSecondaryUsageTypes;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.AssetUseLogEntry
 * JD-Core Version:    0.6.0
 */