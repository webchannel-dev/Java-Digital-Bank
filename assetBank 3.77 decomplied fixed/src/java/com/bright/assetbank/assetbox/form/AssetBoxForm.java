/*     */ package com.bright.assetbank.assetbox.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public class AssetBoxForm extends Bn2Form
/*     */ {
/*  39 */   private String m_sName = null;
/*  40 */   private String m_sUpdateMessage = null;
/*  41 */   private String m_sPreviousName = null;
/*     */ 
/*  43 */   private long m_lCurrentAssetBoxId = 0L;
/*  44 */   private boolean m_bShared = false;
/*     */   private Collection m_listWithPriceBands;
/*     */   private Collection m_listPurchaseRequiredWithoutPriceBands;
/*     */   private Collection m_listCanDownload;
/*     */   private Collection m_listApprovalRequired;
/*     */   private Collection m_listApprovalRejected;
/*     */   private Collection m_listViewOnly;
/*     */   private Collection m_listApprovalApproved;
/*     */   private Collection m_listApprovalPending;
/*  55 */   private long m_lActionOnSelectedAssets = 0L;
/*     */ 
/*  57 */   private ArrayList<Attribute> m_alSortingAttributes = null;
/*  58 */   private long m_lSortingAttribute = -1L;
/*  59 */   private boolean m_bSortDescending = false;
/*     */ 
/*  61 */   private boolean m_bAjax = false;
/*  62 */   private long m_lNewAssetBoxId = 0L;
/*  63 */   private String m_sErrorMessage = null;
/*     */ 
/*  68 */   private boolean m_bIsEmail = false;
/*     */ 
/*     */   public Collection getListApprovalRequired()
/*     */   {
/*  72 */     return this.m_listApprovalRequired;
/*     */   }
/*     */ 
/*     */   public void setListApprovalRequired(Collection a_sListApprovalRequired)
/*     */   {
/*  77 */     this.m_listApprovalRequired = a_sListApprovalRequired;
/*     */   }
/*     */ 
/*     */   public int getNoApprovalRequired()
/*     */   {
/*  82 */     if (getListApprovalRequired() != null)
/*     */     {
/*  84 */       return getListApprovalRequired().size();
/*     */     }
/*  86 */     return 0;
/*     */   }
/*     */ 
/*     */   public Collection getListApprovalRejected()
/*     */   {
/*  91 */     return this.m_listApprovalRejected;
/*     */   }
/*     */ 
/*     */   public void setListApprovalRejected(Collection a_sListApprovalRejected)
/*     */   {
/*  96 */     this.m_listApprovalRejected = a_sListApprovalRejected;
/*     */   }
/*     */ 
/*     */   public int getNoApprovalRejected()
/*     */   {
/* 101 */     if (getListApprovalRejected() != null)
/*     */     {
/* 103 */       return getListApprovalRejected().size();
/*     */     }
/* 105 */     return 0;
/*     */   }
/*     */ 
/*     */   public Collection getListViewOnly()
/*     */   {
/* 110 */     return this.m_listViewOnly;
/*     */   }
/*     */ 
/*     */   public void setListViewOnly(Collection a_sListViewOnly)
/*     */   {
/* 115 */     this.m_listViewOnly = a_sListViewOnly;
/*     */   }
/*     */ 
/*     */   public int getNoViewOnly()
/*     */   {
/* 120 */     if (getListViewOnly() != null)
/*     */     {
/* 122 */       return getListViewOnly().size();
/*     */     }
/* 124 */     return 0;
/*     */   }
/*     */ 
/*     */   public Collection getListApprovalApproved()
/*     */   {
/* 129 */     return this.m_listApprovalApproved;
/*     */   }
/*     */ 
/*     */   public void setListApprovalApproved(Collection a_sListApprovalApproved)
/*     */   {
/* 134 */     this.m_listApprovalApproved = a_sListApprovalApproved;
/*     */   }
/*     */ 
/*     */   public int getNoApprovalApproved()
/*     */   {
/* 139 */     if (getListApprovalApproved() != null)
/*     */     {
/* 141 */       return getListApprovalApproved().size();
/*     */     }
/* 143 */     return 0;
/*     */   }
/*     */ 
/*     */   public Collection getListCanDownload()
/*     */   {
/* 148 */     return this.m_listCanDownload;
/*     */   }
/*     */ 
/*     */   public void setListCanDownload(Collection a_sListCanDownload)
/*     */   {
/* 153 */     this.m_listCanDownload = a_sListCanDownload;
/*     */   }
/*     */ 
/*     */   public int getNoCanDownload()
/*     */   {
/* 158 */     if (getListCanDownload() != null)
/*     */     {
/* 160 */       return getListCanDownload().size();
/*     */     }
/* 162 */     return 0;
/*     */   }
/*     */ 
/*     */   public Collection getListApprovalPending()
/*     */   {
/* 167 */     return this.m_listApprovalPending;
/*     */   }
/*     */ 
/*     */   public void setListApprovalPending(Collection a_sListApprovalPending)
/*     */   {
/* 172 */     this.m_listApprovalPending = a_sListApprovalPending;
/*     */   }
/*     */ 
/*     */   public int getNoApprovalPending()
/*     */   {
/* 177 */     if (getListApprovalPending() != null)
/*     */     {
/* 179 */       return getListApprovalPending().size();
/*     */     }
/* 181 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setUpdateMessage(String a_sUpdateMessage)
/*     */   {
/* 186 */     this.m_sUpdateMessage = a_sUpdateMessage;
/*     */   }
/*     */ 
/*     */   public String getUpdateMessage()
/*     */   {
/* 191 */     return this.m_sUpdateMessage;
/*     */   }
/*     */ 
/*     */   public boolean getEmail()
/*     */   {
/* 196 */     return this.m_bIsEmail;
/*     */   }
/*     */ 
/*     */   public void setEmail(boolean a_bIsEmail)
/*     */   {
/* 201 */     this.m_bIsEmail = a_bIsEmail;
/*     */   }
/*     */ 
/*     */   public Collection getListPurchaseRequiredWithoutPriceBands()
/*     */   {
/* 206 */     return this.m_listPurchaseRequiredWithoutPriceBands;
/*     */   }
/*     */ 
/*     */   public void setListPurchaseRequiredWithoutPriceBands(Collection a_sListPurchaseRequiredWithoutPriceBands)
/*     */   {
/* 211 */     this.m_listPurchaseRequiredWithoutPriceBands = a_sListPurchaseRequiredWithoutPriceBands;
/*     */   }
/*     */ 
/*     */   public Collection getListWithPriceBands()
/*     */   {
/* 216 */     return this.m_listWithPriceBands;
/*     */   }
/*     */ 
/*     */   public void setListWithPriceBands(Collection a_sListPurchaseRequiredWithPriceBands)
/*     */   {
/* 221 */     this.m_listWithPriceBands = a_sListPurchaseRequiredWithPriceBands;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 226 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName)
/*     */   {
/* 231 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public long getCurrentAssetBoxId()
/*     */   {
/* 236 */     return this.m_lCurrentAssetBoxId;
/*     */   }
/*     */ 
/*     */   public void setCurrentAssetBoxId(long a_lCurrentAssetBoxId)
/*     */   {
/* 241 */     this.m_lCurrentAssetBoxId = a_lCurrentAssetBoxId;
/*     */   }
/*     */ 
/*     */   public boolean isShared()
/*     */   {
/* 246 */     return this.m_bShared;
/*     */   }
/*     */ 
/*     */   public void setShared(boolean a_bShared)
/*     */   {
/* 251 */     this.m_bShared = a_bShared;
/*     */   }
/*     */ 
/*     */   public String getPreviousName()
/*     */   {
/* 256 */     return this.m_sPreviousName;
/*     */   }
/*     */ 
/*     */   public void setPreviousName(String previousName)
/*     */   {
/* 261 */     this.m_sPreviousName = previousName;
/*     */   }
/*     */ 
/*     */   public long getActionOnSelectedAssets()
/*     */   {
/* 266 */     return this.m_lActionOnSelectedAssets;
/*     */   }
/*     */ 
/*     */   public void setActionOnSelectedAssets(long a_lActionOnSelectedAssets)
/*     */   {
/* 271 */     this.m_lActionOnSelectedAssets = a_lActionOnSelectedAssets;
/*     */   }
/*     */ 
/*     */   public void setSortingAttributes(ArrayList<Attribute> a_alSortingAttributes)
/*     */   {
/* 276 */     this.m_alSortingAttributes = a_alSortingAttributes;
/*     */   }
/*     */ 
/*     */   public ArrayList<Attribute> getSortingAttributes()
/*     */   {
/* 281 */     return this.m_alSortingAttributes;
/*     */   }
/*     */ 
/*     */   public void setSortingAttribute(long a_lSortingAttribute)
/*     */   {
/* 286 */     this.m_lSortingAttribute = a_lSortingAttribute;
/*     */   }
/*     */ 
/*     */   public long getSortingAttribute()
/*     */   {
/* 291 */     return this.m_lSortingAttribute;
/*     */   }
/*     */ 
/*     */   public boolean isSortDescending()
/*     */   {
/* 296 */     return this.m_bSortDescending;
/*     */   }
/*     */ 
/*     */   public void setSortDescending(boolean a_iSortDescending)
/*     */   {
/* 301 */     this.m_bSortDescending = a_iSortDescending;
/*     */   }
/*     */ 
/*     */   public boolean getAjax()
/*     */   {
/* 306 */     return this.m_bAjax;
/*     */   }
/*     */ 
/*     */   public void setAjax(boolean a_bAjax)
/*     */   {
/* 311 */     this.m_bAjax = a_bAjax;
/*     */   }
/*     */ 
/*     */   public long getNewAssetBoxId()
/*     */   {
/* 316 */     return this.m_lNewAssetBoxId;
/*     */   }
/*     */ 
/*     */   public void setNewAssetBoxId(long a_lNewAssetBoxId)
/*     */   {
/* 321 */     this.m_lNewAssetBoxId = a_lNewAssetBoxId;
/*     */   }
/*     */ 
/*     */   public String getErrorMessage()
/*     */   {
/* 326 */     return this.m_sErrorMessage;
/*     */   }
/*     */ 
/*     */   public void setErrorMessage(String a_sErrorMessage)
/*     */   {
/* 331 */     this.m_sErrorMessage = a_sErrorMessage;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.form.AssetBoxForm
 * JD-Core Version:    0.6.0
 */