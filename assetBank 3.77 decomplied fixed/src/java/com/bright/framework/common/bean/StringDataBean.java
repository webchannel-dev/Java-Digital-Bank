/*     */ package com.bright.framework.common.bean;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ 
/*     */ public class StringDataBean extends DataBean
/*     */ {
/*     */   private String m_sName;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  41 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/*  50 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public StringDataBean(long a_lId, String a_sName)
/*     */   {
/*  60 */     setId(a_lId);
/*  61 */     setName(a_sName);
/*     */   }
/*     */ 
/*     */   public StringDataBean()
/*     */   {
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  75 */     int PRIME = 31;
/*  76 */     int result = super.hashCode();
/*  77 */     result = 31 * result + (this.m_sName == null ? 0 : this.m_sName.hashCode());
/*  78 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  86 */     if (this == obj)
/*  87 */       return true;
/*  88 */     if (!super.equals(obj))
/*  89 */       return false;
/*  90 */     if (getClass() != obj.getClass())
/*  91 */       return false;
/*  92 */     StringDataBean other = (StringDataBean)obj;
/*  93 */     if (this.m_sName == null)
/*     */     {
/*  95 */       if (other.m_sName != null)
/*  96 */         return false;
/*     */     }
/*  98 */     else if (!this.m_sName.equals(other.m_sName))
/*  99 */       return false;
/* 100 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.StringDataBean
 * JD-Core Version:    0.6.0
 */