/*     */ package com.bright.assetbank.category.taglib;
/*     */ 
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ 
/*     */ public class AddItemLinkTag extends CategoryLinkTag
/*     */ {
/*  26 */   private String m_sItemName = null;
/*  27 */   private String m_sExtraParameters = null;
/*  28 */   private long m_lExtensionEntityId = -1L;
/*  29 */   private boolean m_bBoolCheck = false;
/*     */ 
/*     */   public void setExtensionEntityId(long a_lExtensionEntityId)
/*     */   {
/*  33 */     this.m_lExtensionEntityId = a_lExtensionEntityId;
/*     */   }
/*     */ 
/*     */   public void setBoolCheck(boolean a_bBoolCheck)
/*     */   {
/*  38 */     this.m_bBoolCheck = a_bBoolCheck;
/*     */   }
/*     */ 
/*     */   public void setItemName(String a_sItemName)
/*     */   {
/*  43 */     this.m_sItemName = a_sItemName;
/*     */   }
/*     */ 
/*     */   public void setExtraParameters(String a_sExtraParameters)
/*     */   {
/*  48 */     this.m_sExtraParameters = a_sExtraParameters;
/*     */   }
/*     */ 
/*     */   public boolean showLink(ABUserProfile a_userProfile, Category a_cat)
/*     */   {
/*  59 */     boolean bShowLink = false;
/*     */ 
/*  61 */     if (a_cat.getCategoryTypeId() == 2L)
/*     */     {
/*  63 */       bShowLink = (a_cat.isAssignable()) && ((a_userProfile.getIsAdmin()) || (a_userProfile.getCanUpdateAssetsInAccessLevel(a_cat.getId())));
/*     */     }
/*     */     else
/*     */     {
/*  67 */       bShowLink = (a_userProfile.getIsAdmin()) || (a_userProfile.getCanUpdateAssets()) || (a_userProfile.getCanUploadWithApproval());
/*     */     }
/*     */ 
/*  70 */     return (bShowLink) && (this.m_bBoolCheck);
/*     */   }
/*     */ 
/*     */   protected String getLinkText(DBTransaction a_transaction, Category a_cat, ABUserProfile a_userProfile)
/*     */     throws Exception
/*     */   {
/*  81 */     String sLinkText = "";
/*  82 */     if (StringUtil.stringIsPopulated(this.m_sItemName))
/*     */     {
/*  84 */       sLinkText = getListManager().getListItem(a_transaction, "link-add", a_userProfile.getCurrentLanguage()).getBody() + " " + this.m_sItemName;
/*     */     }
/*     */     else
/*     */     {
/*  88 */       sLinkText = getListManager().getListItem(a_transaction, "link-add-an-item", a_userProfile.getCurrentLanguage()).getBody();
/*     */     }
/*  90 */     return sLinkText;
/*     */   }
/*     */ 
/*     */   protected String getLinkUrl(Category a_cat)
/*     */   {
/* 101 */     String sUrlAddItem = "viewUploadAssetFile?addingFromBrowseCatId=" + a_cat.getId() + "&" + "addingFromBrowseTreeId" + "=" + a_cat.getCategoryTypeId();
/* 102 */     if (StringUtil.stringIsPopulated(this.m_sExtraParameters))
/*     */     {
/* 104 */       sUrlAddItem = sUrlAddItem + "&" + this.m_sExtraParameters;
/*     */     }
/*     */ 
/* 107 */     if (this.m_lExtensionEntityId > 0L)
/*     */     {
/* 109 */       sUrlAddItem = sUrlAddItem + "&" + "parentEntityId" + "=" + this.m_lExtensionEntityId;
/*     */     }
/*     */ 
/* 112 */     return sUrlAddItem;
/*     */   }
/*     */ 
/*     */   public void release()
/*     */   {
/* 120 */     super.release();
/* 121 */     this.m_sItemName = null;
/* 122 */     this.m_sExtraParameters = null;
/* 123 */     this.m_lExtensionEntityId = -1L;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.taglib.AddItemLinkTag
 * JD-Core Version:    0.6.0
 */