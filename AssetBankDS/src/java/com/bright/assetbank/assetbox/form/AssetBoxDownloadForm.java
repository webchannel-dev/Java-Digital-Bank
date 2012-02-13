/*     */ package com.bright.assetbank.assetbox.form;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class AssetBoxDownloadForm extends DownloadForm
/*     */ {
/*  39 */   private Collection m_vAssets = null;
/*     */ 
/*  41 */   private int m_iTotalFileSize = 0;
/*  42 */   private String m_sFileFormats = null;
/*  43 */   private boolean m_bCanDownloadAllAssets = true;
/*  44 */   private boolean m_bNotAllRGB = false;
/*  45 */   private boolean m_bOnlyDownloadSelected = false;
/*  46 */   private Vector m_vecAssetApprovals = null;
/*     */ 
/*  48 */   private boolean m_bDownloadingLightbox = false;
/*  49 */   private boolean m_bDownloadingChildAssets = false;
/*     */ 
/*  52 */   private boolean m_bSomeAssetsRequireHighResApproval = false;
/*     */ 
/*  54 */   private String m_sFileName = null;
/*     */ 
/*     */   public Collection getAssets()
/*     */   {
/*  59 */     return this.m_vAssets;
/*     */   }
/*     */ 
/*     */   public void setAssets(Collection a_sAssets)
/*     */   {
/*  65 */     this.m_vAssets = a_sAssets;
/*     */   }
/*     */ 
/*     */   public int getTotalFileSize()
/*     */   {
/*  71 */     return this.m_iTotalFileSize;
/*     */   }
/*     */ 
/*     */   public void setTotalFileSize(int a_sTotalFileSize)
/*     */   {
/*  77 */     this.m_iTotalFileSize = a_sTotalFileSize;
/*     */   }
/*     */ 
/*     */   public String getFileFormats()
/*     */   {
/*  83 */     return this.m_sFileFormats;
/*     */   }
/*     */ 
/*     */   public void setFileFormats(String a_sFileFormats)
/*     */   {
/*  89 */     this.m_sFileFormats = a_sFileFormats;
/*     */   }
/*     */ 
/*     */   public boolean getCanDownloadAllAssets()
/*     */   {
/*  95 */     return this.m_bCanDownloadAllAssets;
/*     */   }
/*     */ 
/*     */   public void setCanDownloadAllAssets(boolean a_sCanDownloadAllAssets)
/*     */   {
/* 101 */     this.m_bCanDownloadAllAssets = a_sCanDownloadAllAssets;
/*     */   }
/*     */ 
/*     */   public boolean getNotAllRGB() {
/* 105 */     return this.m_bNotAllRGB;
/*     */   }
/*     */ 
/*     */   public void setNotAllRGB(boolean a_bNotAllRGB) {
/* 109 */     this.m_bNotAllRGB = a_bNotAllRGB;
/*     */   }
/*     */ 
/*     */   public boolean getOnlyDownloadSelected()
/*     */   {
/* 114 */     return this.m_bOnlyDownloadSelected;
/*     */   }
/*     */ 
/*     */   public void setOnlyDownloadSelected(boolean a_bOnlyDownloadSelected) {
/* 118 */     this.m_bOnlyDownloadSelected = a_bOnlyDownloadSelected;
/*     */   }
/*     */ 
/*     */   public int getExcludedFromDownloadOriginalCount()
/*     */   {
/* 123 */     return getAssets().size() - getDownloadOriginalCount();
/*     */   }
/*     */ 
/*     */   public boolean getContainsImages()
/*     */   {
/* 128 */     if (getAssets() != null)
/*     */     {
/* 130 */       Iterator it = getAssets().iterator();
/*     */ 
/* 132 */       while (it.hasNext())
/*     */       {
/* 134 */         AssetInList temp = (AssetInList)it.next();
/* 135 */         if (temp.getAsset().getIsImage())
/*     */         {
/* 137 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */   public void setAssetApprovals(Vector a_vecAssetApprovals)
/*     */   {
/* 146 */     this.m_vecAssetApprovals = a_vecAssetApprovals;
/*     */   }
/*     */ 
/*     */   public Vector getAssetApprovals()
/*     */   {
/* 151 */     return this.m_vecAssetApprovals;
/*     */   }
/*     */ 
/*     */   public Asset getAssetWithId(String a_sId)
/*     */   {
/* 156 */     if (getAssets() != null)
/*     */     {
/* 158 */       long lId = Long.parseLong(a_sId);
/* 159 */       Iterator i = getAssets().iterator();
/* 160 */       while (i.hasNext())
/*     */       {
/* 162 */         AssetInList asset = (AssetInList)i.next();
/* 163 */         if (asset.getAsset().getId() == lId)
/*     */         {
/* 165 */           return asset.getAsset();
/*     */         }
/*     */       }
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean getDownloadingLightbox()
/*     */   {
/* 175 */     return this.m_bDownloadingLightbox;
/*     */   }
/*     */ 
/*     */   public void setDownloadingLightbox(boolean a_bDownloadingLightbox) {
/* 179 */     this.m_bDownloadingLightbox = a_bDownloadingLightbox;
/*     */   }
/*     */ 
/*     */   public boolean getDownloadingChildAssets()
/*     */   {
/* 185 */     return this.m_bDownloadingChildAssets;
/*     */   }
/*     */ 
/*     */   public void setDownloadingChildAssets(boolean a_bDownloadingChildAssets) {
/* 189 */     this.m_bDownloadingChildAssets = a_bDownloadingChildAssets;
/*     */   }
/*     */ 
/*     */   public boolean getSomeAssetsRequireHighResApproval()
/*     */   {
/* 196 */     return this.m_bSomeAssetsRequireHighResApproval;
/*     */   }
/*     */ 
/*     */   public void setSomeAssetsRequireHighResApproval(boolean a_bSomeAssetsRequireHighResApproval) {
/* 200 */     this.m_bSomeAssetsRequireHighResApproval = a_bSomeAssetsRequireHighResApproval;
/*     */   }
/*     */ 
/*     */   public String getFileName()
/*     */   {
/* 207 */     return this.m_sFileName;
/*     */   }
/*     */ 
/*     */   public void setFileName(String a_sFileName) {
/* 211 */     this.m_sFileName = a_sFileName;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.form.AssetBoxDownloadForm
 * JD-Core Version:    0.6.0
 */