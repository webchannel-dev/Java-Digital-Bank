/*     */ package com.bright.framework.mail.bean;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.mail.internet.InternetAddress;
/*     */ 
/*     */ public class DuplicateEmailKey
/*     */ {
/*  33 */   private ArrayList m_alToList = null;
/*  34 */   private String m_sTemplateName = null;
/*     */ 
/*     */   public ArrayList getToList()
/*     */   {
/*  38 */     return this.m_alToList;
/*     */   }
/*     */ 
/*     */   public void setToList(ArrayList a_alToList)
/*     */   {
/*  43 */     this.m_alToList = a_alToList;
/*     */   }
/*     */ 
/*     */   public String getTemplateName()
/*     */   {
/*  48 */     return this.m_sTemplateName;
/*     */   }
/*     */ 
/*     */   public void setTemplateName(String a_sTemplateName)
/*     */   {
/*  53 */     this.m_sTemplateName = a_sTemplateName;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if (this == obj)
/*     */     {
/*  61 */       return true;
/*     */     }
/*  63 */     if (obj == null)
/*     */     {
/*  65 */       return false;
/*     */     }
/*  67 */     if (getClass() != obj.getClass())
/*     */     {
/*  69 */       return false;
/*     */     }
/*     */ 
/*  72 */     DuplicateEmailKey other = (DuplicateEmailKey)obj;
/*  73 */     if (this.m_sTemplateName == null)
/*     */     {
/*  75 */       if (other.m_sTemplateName != null)
/*     */       {
/*  77 */         return false;
/*     */       }
/*     */     }
/*  80 */     else if (!this.m_sTemplateName.equals(other.m_sTemplateName))
/*     */     {
/*  82 */       return false;
/*     */     }
/*     */ 
/*  85 */     if (this.m_alToList == null)
/*     */     {
/*  87 */       if (other.m_alToList != null)
/*     */       {
/*  89 */         return false;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  94 */       if (this.m_alToList.size() != other.m_alToList.size())
/*     */       {
/*  96 */         return false;
/*     */       }
/*     */ 
/* 100 */       for (int i = 0; i < this.m_alToList.size(); i++)
/*     */       {
/* 102 */         InternetAddress address = (InternetAddress)this.m_alToList.get(i);
/* 103 */         if (!other.m_alToList.contains(address))
/*     */         {
/* 105 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 110 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.mail.bean.DuplicateEmailKey
 * JD-Core Version:    0.6.0
 */