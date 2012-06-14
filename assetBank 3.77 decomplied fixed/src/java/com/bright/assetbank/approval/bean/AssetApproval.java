/*     */ package com.bright.assetbank.approval.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ public class AssetApproval extends AssetInList
/*     */ {
/*     */   private StringDataBean m_userReference;
/*     */   private BrightDate m_dateSubmitted;
/*     */   private BrightDate m_dateApproved;
/*     */   private BrightDate m_dateExpires;
/*     */ 
/*     */   public AssetApproval()
/*     */   {
/*  43 */     this((Language)LanguageConstants.k_defaultLanguage.clone());
/*     */   }
/*     */ 
/*     */   public AssetApproval(Language a_language)
/*     */   {
/*  51 */     super(a_language);
/*     */ 
/*  53 */     this.m_userReference = new StringDataBean();
/*  54 */     this.m_dateApproved = new BrightDate();
/*  55 */     this.m_dateSubmitted = new BrightDate();
/*  56 */     this.m_dateExpires = new BrightDate();
/*     */   }
/*     */ 
/*     */   public boolean getExpiresToday()
/*     */   {
/*  61 */     Calendar calToday = Calendar.getInstance();
/*  62 */     Calendar calExpires = Calendar.getInstance();
/*  63 */     calExpires.setTime(this.m_dateExpires.getDate());
/*     */ 
/*  65 */     return (calToday.get(1) == calExpires.get(1)) && (calToday.get(6) == calExpires.get(6));
/*     */   }
/*     */ 
/*     */   public StringDataBean getUserReference()
/*     */   {
/*  86 */     return this.m_userReference;
/*     */   }
/*     */ 
/*     */   public void setUserReference(StringDataBean a_sUserReference)
/*     */   {
/*  95 */     this.m_userReference = a_sUserReference;
/*     */   }
/*     */ 
/*     */   public BrightDate getDateSubmitted()
/*     */   {
/* 111 */     return this.m_dateSubmitted;
/*     */   }
/*     */ 
/*     */   public void setDateSubmitted(BrightDate a_sDateSubmitted)
/*     */   {
/* 120 */     this.m_dateSubmitted = a_sDateSubmitted;
/*     */   }
/*     */ 
/*     */   public BrightDate getDateApproved()
/*     */   {
/* 136 */     return this.m_dateApproved;
/*     */   }
/*     */ 
/*     */   public void setDateApproved(BrightDate a_sDateApproved)
/*     */   {
/* 145 */     this.m_dateApproved = a_sDateApproved;
/*     */   }
/*     */ 
/*     */   public BrightDate getDateExpires()
/*     */   {
/* 161 */     return this.m_dateExpires;
/*     */   }
/*     */ 
/*     */   public void setDateExpires(BrightDate a_sDateExpires)
/*     */   {
/* 170 */     this.m_dateExpires = a_sDateExpires;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 178 */     int PRIME = 31;
/* 179 */     int result = super.hashCode();
/* 180 */     result = 31 * result + (this.m_dateApproved == null ? 0 : this.m_dateApproved.hashCode());
/* 181 */     result = 31 * result + (this.m_dateExpires == null ? 0 : this.m_dateExpires.hashCode());
/* 182 */     result = 31 * result + (this.m_dateSubmitted == null ? 0 : this.m_dateSubmitted.hashCode());
/* 183 */     result = 31 * result + (this.m_userReference == null ? 0 : this.m_userReference.hashCode());
/* 184 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 192 */     if (this == obj)
/* 193 */       return true;
/* 194 */     if (!super.equals(obj))
/* 195 */       return false;
/* 196 */     if (getClass() != obj.getClass())
/* 197 */       return false;
/* 198 */     AssetApproval other = (AssetApproval)obj;
/* 199 */     if (this.m_dateApproved == null)
/*     */     {
/* 201 */       if (other.m_dateApproved != null)
/* 202 */         return false;
/*     */     }
/* 204 */     else if (!this.m_dateApproved.equals(other.m_dateApproved))
/* 205 */       return false;
/* 206 */     if (this.m_dateExpires == null)
/*     */     {
/* 208 */       if (other.m_dateExpires != null)
/* 209 */         return false;
/*     */     }
/* 211 */     else if (!this.m_dateExpires.equals(other.m_dateExpires))
/* 212 */       return false;
/* 213 */     if (this.m_dateSubmitted == null)
/*     */     {
/* 215 */       if (other.m_dateSubmitted != null)
/* 216 */         return false;
/*     */     }
/* 218 */     else if (!this.m_dateSubmitted.equals(other.m_dateSubmitted))
/* 219 */       return false;
/* 220 */     if (this.m_userReference == null)
/*     */     {
/* 222 */       if (other.m_userReference != null)
/* 223 */         return false;
/*     */     }
/* 225 */     else if (!this.m_userReference.equals(other.m_userReference))
/* 226 */       return false;
/* 227 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.bean.AssetApproval
 * JD-Core Version:    0.6.0
 */