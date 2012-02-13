/*     */ package com.bright.assetbank.category.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Panel
/*     */ {
/*  45 */   private List<? extends LightweightAsset> m_listAssets = null;
/*  46 */   private boolean m_bIsPartOfMultiPanelSet = false;
/*  47 */   private int m_iVisibilityStatus = 1;
/*  48 */   private String m_sHeader = null;
/*  49 */   private boolean m_bListView = false;
/*  50 */   private boolean m_bCanAddItem = true;
/*  51 */   private String m_sAddItemParameters = "";
/*     */ 
/*     */   public Panel(boolean a_bIsPartOfMultiPanelSet, String a_sHeader)
/*     */   {
/*  41 */     this.m_bIsPartOfMultiPanelSet = a_bIsPartOfMultiPanelSet;
/*  42 */     this.m_sHeader = a_sHeader;
/*     */   }
/*     */ 
/*     */   public void setAssets(List<? extends LightweightAsset> a_listAssets)
/*     */   {
/*  55 */     this.m_listAssets = a_listAssets;
/*     */   }
/*     */ 
/*     */   public List<? extends LightweightAsset> getAssets()
/*     */   {
/*  60 */     return this.m_listAssets;
/*     */   }
/*     */ 
/*     */   public boolean isPopulated()
/*     */   {
/*  67 */     return (this.m_listAssets != null) && (this.m_listAssets.size() > 0);
/*     */   }
/*     */ 
/*     */   public boolean getIsPartOfMultiPanelSet()
/*     */   {
/*  80 */     return this.m_bIsPartOfMultiPanelSet;
/*     */   }
/*     */ 
/*     */   public String getHeader()
/*     */   {
/*  85 */     return this.m_sHeader;
/*     */   }
/*     */ 
/*     */   public int getVisibilityStatus()
/*     */   {
/*  90 */     return this.m_iVisibilityStatus;
/*     */   }
/*     */ 
/*     */   public void setVisibilityStatus(int a_iVisibilityStatus)
/*     */   {
/*  95 */     this.m_iVisibilityStatus = a_iVisibilityStatus;
/*     */   }
/*     */ 
/*     */   public boolean getListView()
/*     */   {
/* 100 */     return this.m_bListView;
/*     */   }
/*     */ 
/*     */   public void setListView(boolean a_bListView)
/*     */   {
/* 105 */     this.m_bListView = a_bListView;
/*     */   }
/*     */ 
/*     */   public void setCanAddItem(boolean a_bCanAddItem)
/*     */   {
/* 110 */     this.m_bCanAddItem = a_bCanAddItem;
/*     */   }
/*     */ 
/*     */   public boolean getCanAddItem()
/*     */   {
/* 120 */     return this.m_bCanAddItem;
/*     */   }
/*     */ 
/*     */   public void setAddItemParameters(String a_sAddItemParameters)
/*     */   {
/* 125 */     this.m_sAddItemParameters = a_sAddItemParameters;
/*     */   }
/*     */ 
/*     */   public String getAddItemParameters()
/*     */   {
/* 136 */     return this.m_sAddItemParameters;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.bean.Panel
 * JD-Core Version:    0.6.0
 */