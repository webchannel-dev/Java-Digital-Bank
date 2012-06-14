/*     */ package com.bright.assetbank.category.taglib;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ public class AddExtendedSubCategoryLinkTag extends CategoryLinkTag
/*     */ {
/*  41 */   private String m_sDefaultLinkIdentifier = null;
/*  42 */   private boolean m_bExplorer = false;
/*     */ 
/* 164 */   private AssetEntityManager m_entityManager = null;
/*     */ 
/* 177 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public void setDefaultLinkIdentifier(String a_sDefaultLinkIdentifier)
/*     */   {
/*  46 */     this.m_sDefaultLinkIdentifier = a_sDefaultLinkIdentifier;
/*     */   }
/*     */ 
/*     */   public void setExplorer(boolean a_bExplorer)
/*     */   {
/*  51 */     this.m_bExplorer = a_bExplorer;
/*     */   }
/*     */ 
/*     */   protected boolean showLink(ABUserProfile a_userProfile, Category a_cat)
/*     */   {
/*  57 */     return (a_userProfile.getIsAdmin()) && (AssetBankSettings.getCategoryExtensionAssetsEnabled());
/*     */   }
/*     */ 
/*     */   protected String getLinkText(DBTransaction a_transaction, Category a_cat, ABUserProfile a_userProfile)
/*     */     throws Exception
/*     */   {
/*  63 */     String sText = "";
/*     */ 
/*  65 */     if (a_cat.getExtensionAssetId() > 0L)
/*     */     {
/*  68 */       SearchCriteria criteria = new SearchCriteria();
/*  69 */       criteria.setAssetIds(String.valueOf(a_cat.getExtensionAssetId()));
/*  70 */       SearchResults results = getSearchManager().search(criteria);
/*     */ 
/*  73 */       if ((results != null) && (results.getSearchResults() != null) && (results.getSearchResults().size() == 1))
/*     */       {
/*  77 */         LightweightAsset asset = (LightweightAsset)results.getSearchResults().firstElement();
/*  78 */         if ((asset.getEntity() != null) && (asset.getEntity().getId() > 0L))
/*     */         {
/*  81 */           AssetEntity entity = getEntityManager().getEntity(a_transaction, asset.getEntity().getId());
/*     */ 
/*  84 */           if (entity.getChildRelationships() != null)
/*     */           {
/*  86 */             long[] aIds = new long[entity.getChildRelationships().size()];
/*  87 */             for (int i = 0; i < entity.getChildRelationships().size(); i++)
/*     */             {
/*  89 */               AssetEntityRelationship aer = (AssetEntityRelationship)entity.getChildRelationships().get(i);
/*  90 */               aIds[i] = aer.getRelatesToAssetEntityId();
/*     */             }
/*     */ 
/*  94 */             Vector<AssetEntity> vecEntities = getEntityManager().getAllSelectedEntities(a_transaction, aIds);
/*  95 */             Vector vecExtensionEntities = new Vector();
/*  96 */             for (AssetEntity tempEntity : vecEntities)
/*     */             {
/*  98 */               if (tempEntity.getIsCategoryExtension())
/*     */               {
/* 100 */                 vecExtensionEntities.add(tempEntity);
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 105 */             if (vecExtensionEntities.size() == 1)
/*     */             {
/* 107 */               sText = ((AssetEntity)vecExtensionEntities.firstElement()).getName();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 114 */     if (!StringUtil.stringIsPopulated(sText))
/*     */     {
/* 117 */       sText = getListManager().getListItem(a_transaction, this.m_sDefaultLinkIdentifier, a_userProfile.getCurrentLanguage()).getBody();
/*     */     }
/*     */ 
/* 121 */     sText = getListManager().getListItem(a_transaction, "link-add", a_userProfile.getCurrentLanguage()).getBody() + " " + sText;
/*     */ 
/* 123 */     return sText;
/*     */   }
/*     */ 
/*     */   protected String getLinkUrl(Category a_cat)
/*     */   {
/* 131 */     String sUrlAddExtendedCategory = "?extendedCategory=true&categoryId=" + a_cat.getId() + "&" + "newCategory.name" + "=&" + "catExtensionReturnLocation" + "=";
/* 132 */     if (this.m_bExplorer)
/*     */     {
/* 134 */       sUrlAddExtendedCategory = sUrlAddExtendedCategory + "explorer";
/*     */     }
/*     */     else
/*     */     {
/* 138 */       sUrlAddExtendedCategory = sUrlAddExtendedCategory + "browse";
/*     */     }
/*     */ 
/* 141 */     if (a_cat.getCategoryTypeId() == 2L)
/*     */     {
/* 143 */       sUrlAddExtendedCategory = "addPermissionCategory" + sUrlAddExtendedCategory;
/*     */     }
/*     */     else
/*     */     {
/* 147 */       sUrlAddExtendedCategory = "addCategory" + sUrlAddExtendedCategory;
/*     */     }
/*     */ 
/* 151 */     return sUrlAddExtendedCategory;
/*     */   }
/*     */ 
/*     */   public void release()
/*     */   {
/* 159 */     super.release();
/* 160 */     this.m_sDefaultLinkIdentifier = null;
/*     */   }
/*     */ 
/*     */   protected AssetEntityManager getEntityManager()
/*     */     throws ComponentException
/*     */   {
/* 168 */     if (this.m_entityManager == null)
/*     */     {
/* 170 */       this.m_entityManager = ((AssetEntityManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetEntityManager"));
/*     */     }
/*     */ 
/* 173 */     return this.m_entityManager;
/*     */   }
/*     */ 
/*     */   protected MultiLanguageSearchManager getSearchManager()
/*     */     throws ComponentException
/*     */   {
/* 181 */     if (this.m_searchManager == null)
/*     */     {
/* 183 */       this.m_searchManager = ((MultiLanguageSearchManager)GlobalApplication.getInstance().getComponentManager().lookup("SearchManager"));
/*     */     }
/*     */ 
/* 186 */     return this.m_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.taglib.AddExtendedSubCategoryLinkTag
 * JD-Core Version:    0.6.0
 */