/*     */ package com.bright.assetbank.assetbox.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ public class AssetBox extends AssetBoxSummary
/*     */   implements AssetBoxConstants
/*     */ {
/*  44 */   private long m_lId = 0L;
/*     */ 
/*  47 */   protected HashMap m_hmAssets = null;
/*     */ 
/*  50 */   protected boolean m_bHasPrint = false;
/*     */ 
/*     */   public AssetBox()
/*     */   {
/*  55 */     this.m_hmAssets = new HashMap(50);
/*     */   }
/*     */ 
/*     */   public Collection getAssets()
/*     */   {
/*  73 */     Collection colAssetList = new TreeSet(new AssetBoxComparator());
/*  74 */     colAssetList.addAll(this.m_hmAssets.values());
/*     */ 
/*  76 */     return colAssetList;
/*     */   }
/*     */ 
/*     */   public Collection<AssetInList> getAllAssetsSorted()
/*     */   {
/*  84 */     Collection colAssetList = new TreeSet(new AssetBoxComparator());
/*  85 */     Iterator it = this.m_hmAssets.values().iterator();
/*     */ 
/*  87 */     while (it.hasNext())
/*     */     {
/*  89 */       AssetInList ail = (AssetInList)it.next();
/*  90 */       colAssetList.add(ail);
/*     */     }
/*     */ 
/*  93 */     return colAssetList;
/*     */   }
/*     */ 
/*     */   public Collection getAssetsInState(int a_iState)
/*     */   {
/* 112 */     Collection colAssetList = new TreeSet(new AssetBoxComparator());
/* 113 */     Iterator it = this.m_hmAssets.values().iterator();
/*     */ 
/* 115 */     while (it.hasNext())
/*     */     {
/* 117 */       AssetInList ail = (AssetInList)it.next();
/*     */ 
/* 119 */       switch (a_iState)
/*     */       {
/*     */       case 7:
/* 122 */         if ((!ail.getIsDownloadable()) && (!ail.getIsApprovalApproved()))
/*     */           break;
/* 124 */         colAssetList.add(ail); break;
/*     */       case 1:
/* 129 */         if (!ail.getIsDownloadable())
/*     */           break;
/* 131 */         colAssetList.add(ail); break;
/*     */       case 2:
/* 136 */         if (!ail.getIsApprovalApproved())
/*     */           break;
/* 138 */         colAssetList.add(ail); break;
/*     */       case 3:
/* 143 */         if (!ail.getIsApprovalRequestable())
/*     */           break;
/* 145 */         colAssetList.add(ail); break;
/*     */       case 4:
/* 150 */         if (!ail.getIsApprovalPending())
/*     */           break;
/* 152 */         colAssetList.add(ail); break;
/*     */       case 5:
/* 157 */         if (!ail.getIsApprovalRejected())
/*     */           break;
/* 159 */         colAssetList.add(ail); break;
/*     */       case 6:
/* 164 */         if (!ail.getIsNeverDownloadable())
/*     */           break;
/* 166 */         colAssetList.add(ail); break;
/*     */       case 8:
/* 171 */         if (!ail.getRequiresHighResApproval())
/*     */           break;
/* 173 */         colAssetList.add(ail);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 183 */     return colAssetList;
/*     */   }
/*     */ 
/*     */   public boolean addAsset(AssetInList a_assetRecord)
/*     */   {
/* 202 */     long lAssetId = a_assetRecord.getAsset().getId();
/* 203 */     Long assetId = new Long(lAssetId);
/*     */ 
/* 206 */     if (this.m_hmAssets.containsKey(assetId))
/*     */     {
/* 208 */       return true;
/*     */     }
/*     */ 
/* 212 */     this.m_hmAssets.put(assetId, a_assetRecord);
/*     */ 
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean containsAsset(long a_lAssetId)
/*     */   {
/* 230 */     return this.m_hmAssets.containsKey(new Long(a_lAssetId));
/*     */   }
/*     */ 
/*     */   public AssetInList getAsset(long a_lAssetId)
/*     */   {
/* 246 */     return (AssetInList)this.m_hmAssets.get(new Long(a_lAssetId));
/*     */   }
/*     */ 
/*     */   public int getNumAssets()
/*     */   {
/* 262 */     return this.m_hmAssets.size();
/*     */   }
/*     */ 
/*     */   public int getNumAssetsSelected()
/*     */   {
/* 278 */     int iNumAssetsSelected = 0;
/*     */ 
/* 280 */     Iterator it = this.m_hmAssets.values().iterator();
/*     */ 
/* 282 */     while (it.hasNext())
/*     */     {
/* 284 */       AssetInList ail = (AssetInList)it.next();
/*     */ 
/* 286 */       if (ail.getIsSelected())
/*     */       {
/* 288 */         iNumAssetsSelected++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 293 */     return iNumAssetsSelected;
/*     */   }
/*     */ 
/*     */   public boolean getContainsImages()
/*     */   {
/* 304 */     Collection<AssetInList> assets = getAssets();
/* 305 */     if (assets != null)
/*     */     {
/* 307 */       for (AssetInList ail : assets)
/*     */       {
/* 309 */         if (ail.getAsset().getIsImage())
/*     */         {
/* 311 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 315 */     return false;
/*     */   }
/*     */ 
/*     */   public void removeAsset(long a_lAssetId)
/*     */   {
/* 331 */     Long assetId = new Long(a_lAssetId);
/*     */ 
/* 333 */     if (this.m_hmAssets.containsKey(assetId))
/*     */     {
/* 335 */       this.m_hmAssets.remove(assetId);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeAllAssets()
/*     */   {
/* 351 */     this.m_hmAssets.clear();
/*     */   }
/*     */ 
/*     */   public boolean getHasPrint()
/*     */   {
/* 365 */     return this.m_bHasPrint;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/* 371 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public void setId(long a_sId)
/*     */   {
/* 377 */     this.m_lId = a_sId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.bean.AssetBox
 * JD-Core Version:    0.6.0
 */