/*    */ package com.bright.assetbank.repurposing.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.repurposing.bean.RepurposedVersion;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class RepurposingForm extends Bn2Form
/*    */ {
/*    */   private static final long serialVersionUID = 3374241064312561245L;
/* 33 */   private RepurposedVersion m_repurposedVersion = null;
/* 34 */   private String m_sHtmlSnippet = null;
/* 35 */   private String m_sBaseUrl = null;
/* 36 */   private ArrayList m_alRepurposedVersions = null;
/* 37 */   private long m_lAssetId = 0L;
/* 38 */   private String m_sDownloadUrl = null;
/*    */ 
/*    */   public RepurposedVersion getRepurposedVersion()
/*    */   {
/* 43 */     return this.m_repurposedVersion;
/*    */   }
/*    */ 
/*    */   public void setRepurposedVersion(RepurposedVersion repurposedVersion)
/*    */   {
/* 48 */     this.m_repurposedVersion = repurposedVersion;
/*    */   }
/*    */ 
/*    */   public String getHtmlSnippet()
/*    */   {
/* 53 */     return this.m_sHtmlSnippet;
/*    */   }
/*    */ 
/*    */   public void setHtmlSnippet(String htmlSnippet)
/*    */   {
/* 58 */     this.m_sHtmlSnippet = htmlSnippet;
/*    */   }
/*    */ 
/*    */   public String getBaseUrl()
/*    */   {
/* 63 */     return this.m_sBaseUrl;
/*    */   }
/*    */ 
/*    */   public void setBaseUrl(String baseUrl)
/*    */   {
/* 68 */     this.m_sBaseUrl = baseUrl;
/*    */   }
/*    */ 
/*    */   public ArrayList getRepurposedVersions()
/*    */   {
/* 73 */     return this.m_alRepurposedVersions;
/*    */   }
/*    */ 
/*    */   public void setRepurposedVersions(ArrayList a_alRepurposedVersions)
/*    */   {
/* 78 */     this.m_alRepurposedVersions = a_alRepurposedVersions;
/*    */   }
/*    */ 
/*    */   public long getAssetId()
/*    */   {
/* 83 */     return this.m_lAssetId;
/*    */   }
/*    */ 
/*    */   public void setAssetId(long assetId)
/*    */   {
/* 88 */     this.m_lAssetId = assetId;
/*    */   }
/*    */ 
/*    */   public void setDownloadUrl(String a_sDownloadUrl)
/*    */   {
/* 93 */     this.m_sDownloadUrl = a_sDownloadUrl;
/*    */   }
/*    */ 
/*    */   public String getDownloadUrl()
/*    */   {
/* 98 */     return this.m_sDownloadUrl;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.form.RepurposingForm
 * JD-Core Version:    0.6.0
 */