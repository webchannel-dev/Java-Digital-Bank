/*     */ package com.bright.assetbank.orgunit.bean;
/*     */ 
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class OrgUnitMetadata extends StringDataBean
/*     */ {
/*  37 */   private int m_iDiskQuotaMb = 0;
/*  38 */   private long m_lDiskUsageBytes = 0L;
/*  39 */   private ArrayList<OrgUnitContent> m_alContent = null;
/*     */ 
/*     */   public int getDiskQuotaMb()
/*     */   {
/*  43 */     return this.m_iDiskQuotaMb;
/*     */   }
/*     */ 
/*     */   public void setDiskQuotaMb(int a_iDiskQuotaMb)
/*     */   {
/*  48 */     this.m_iDiskQuotaMb = a_iDiskQuotaMb;
/*     */   }
/*     */ 
/*     */   public long getDiskUsageBytes()
/*     */   {
/*  53 */     return this.m_lDiskUsageBytes;
/*     */   }
/*     */ 
/*     */   public void setDiskUsageBytes(long a_lDiskUsageBytes)
/*     */   {
/*  58 */     this.m_lDiskUsageBytes = a_lDiskUsageBytes;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitContent(ArrayList<OrgUnitContent> a_alContent)
/*     */   {
/*  63 */     this.m_alContent = a_alContent;
/*     */   }
/*     */ 
/*     */   public ArrayList<OrgUnitContent> getOrgUnitContent()
/*     */   {
/*  68 */     return this.m_alContent;
/*     */   }
/*     */ 
/*     */   public void addOrgUnitContent(OrgUnitContent a_content)
/*     */   {
/*  73 */     if (this.m_alContent == null)
/*     */     {
/*  75 */       this.m_alContent = new ArrayList();
/*     */     }
/*     */ 
/*  78 */     this.m_alContent.add(a_content);
/*     */   }
/*     */ 
/*     */   public ArrayList<OrgUnitContent> getOrgUnitExtraContentPages()
/*     */   {
/*  83 */     return getOrgUnitContentForPurpose(1L, false);
/*     */   }
/*     */ 
/*     */   public ArrayList<OrgUnitContent> getOrgUnitContentForPurpose(long a_lPurposeId, boolean a_bEnabledOnly)
/*     */   {
/*  88 */     ArrayList m_alPurposeList = new ArrayList();
/*     */ 
/*  90 */     if (this.m_alContent != null)
/*     */     {
/*  92 */       for (OrgUnitContent content : this.m_alContent)
/*     */       {
/*  94 */         if (content.getContentPurposeId() == a_lPurposeId)
/*     */         {
/*  96 */           if ((!a_bEnabledOnly) || (content.getEnabled()))
/*     */           {
/*  98 */             m_alPurposeList.add(content);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 103 */     return m_alPurposeList;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.bean.OrgUnitMetadata
 * JD-Core Version:    0.6.0
 */