/*     */ package com.bright.assetbank.synchronise.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.synchronise.service.CategoryHandler;
/*     */ import com.bright.assetbank.synchronise.util.SynchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ExternalAssetImpl extends ExportAssetBeanWrapper
/*     */ {
/*     */   private CategoryHandler m_categoryHandler;
/*     */ 
/*     */   public ExternalAssetImpl(CategoryHandler a_categoryHandler)
/*     */   {
/*  53 */     this.m_categoryHandler = a_categoryHandler;
/*     */   }
/*     */ 
/*     */   public String getDescriptiveCategories()
/*     */   {
/*  58 */     return getCategoryNameList(getAsset().getDescriptiveCategories(), false);
/*     */   }
/*     */ 
/*     */   public String getAccessLevels()
/*     */   {
/*  63 */     return getCategoryNameList(getAsset().getPermissionCategories(), false);
/*     */   }
/*     */ 
/*     */   public void setAccessLevels(String a_sValue) throws Bn2Exception
/*     */   {
/*  68 */     Set categoryIds = getCategoryIdsFromStringList(a_sValue, 2L);
/*     */ 
/*  72 */     Vector vecCats = CategoryUtil.getCategoriesFromIds(2L, categoryIds);
/*  73 */     getAsset().setPermissionCategories(vecCats);
/*     */   }
/*     */ 
/*     */   public void setDescriptiveCategories(String a_sValue) throws Bn2Exception
/*     */   {
/*  78 */     Set categoryIds = getCategoryIdsFromStringList(a_sValue, 1L);
/*     */ 
/*  82 */     Vector vecCats = CategoryUtil.getCategoriesFromIds(1L, categoryIds);
/*  83 */     getAsset().setDescriptiveCategories(vecCats);
/*     */   }
/*     */ 
/*     */   private Set<Long> getCategoryIdsFromStringList(String a_sValue, long a_treeId)
/*     */     throws Bn2Exception
/*     */   {
/*  89 */     if (a_sValue == null)
/*     */     {
/*  91 */       return null;
/*     */     }
/*     */ 
/*  94 */     List values = SynchUtil.unpackToReadOnlyList(a_sValue);
/*  95 */     if (values.isEmpty())
/*     */     {
/*  97 */       return null;
/*     */     }
/*     */ 
/* 100 */     return this.m_categoryHandler.getCategoryIds(values, a_treeId);
/*     */   }
/*     */ 
/*     */   public void setAddedByUser(String a_sValue)
/*     */   {
/* 108 */     if (StringUtils.isNotEmpty(a_sValue))
/*     */     {
/* 110 */       if (getAsset().getAddedByUser() == null)
/*     */       {
/* 112 */         getAsset().setAddedByUser(new ABUser());
/*     */       }
/*     */ 
/* 115 */       String[] name = a_sValue.split(" ", 2);
/* 116 */       getAsset().getAddedByUser().setForename(name[0]);
/* 117 */       if (name.length > 1)
/*     */       {
/* 119 */         getAsset().getAddedByUser().setSurname(name[1]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getAddedByUser()
/*     */   {
/* 126 */     ABUser user = getAsset().getAddedByUser();
/* 127 */     if ((user != null) && (user.getId() > 0L))
/*     */     {
/* 129 */       return user.getForename() + " " + user.getSurname();
/*     */     }
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */   public void setLastModifiedByUser(String a_sValue)
/*     */   {
/* 139 */     if (StringUtils.isNotEmpty(a_sValue))
/*     */     {
/* 141 */       if (getAsset().getLastModifiedByUser() == null)
/*     */       {
/* 143 */         getAsset().setLastModifiedByUser(new ABUser());
/*     */       }
/*     */ 
/* 146 */       String[] name = a_sValue.split(" ", 2);
/* 147 */       getAsset().getLastModifiedByUser().setForename(name[0]);
/* 148 */       if (name.length > 1)
/*     */       {
/* 150 */         getAsset().getLastModifiedByUser().setSurname(name[1]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getLastModifiedByUser()
/*     */   {
/* 157 */     ABUser user = getAsset().getLastModifiedByUser();
/* 158 */     if ((user != null) && (user.getId() > 0L))
/*     */     {
/* 160 */       return user.getForename() + " " + user.getSurname();
/*     */     }
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */   protected String getCategoryNameList(List<Category> vCats, boolean a_bCheckSynchFlag)
/*     */   {
/* 172 */     if (vCats == null)
/*     */     {
/* 174 */       return null;
/*     */     }
/*     */ 
/* 177 */     List categoryNames = new ArrayList();
/* 178 */     for (int i = 0; i < vCats.size(); i++)
/*     */     {
/* 180 */       Category cat = (Category)vCats.get(i);
/* 181 */       if ((a_bCheckSynchFlag) && (!cat.isSynchronised()))
/*     */         continue;
/* 183 */       categoryNames.add(getCategoryPath(cat, "/"));
/*     */     }
/*     */ 
/* 187 */     if (categoryNames.isEmpty())
/*     */     {
/* 189 */       return null;
/*     */     }
/*     */ 
/* 193 */     String[] categoryNamesArray = (String[])categoryNames.toArray(new String[categoryNames.size()]);
/* 194 */     return SynchUtil.pack(categoryNamesArray);
/*     */   }
/*     */ 
/*     */   public String getEntityId()
/*     */   {
/* 200 */     return String.valueOf(getAsset().getEntity().getId());
/*     */   }
/*     */ 
/*     */   public String getRelatedAssets()
/*     */   {
/* 205 */     if (AssetBankSettings.getExportRelationshipData())
/*     */     {
/* 207 */       return getAsset().getPeerAssetIdsAsString();
/*     */     }
/* 209 */     return "";
/*     */   }
/*     */ 
/*     */   public String getParentAssets()
/*     */   {
/* 214 */     if (AssetBankSettings.getExportRelationshipData())
/*     */     {
/* 216 */       return getAsset().getParentAssetIdsAsString();
/*     */     }
/* 218 */     return "";
/*     */   }
/*     */ 
/*     */   public String getChildAssets()
/*     */   {
/* 223 */     if (AssetBankSettings.getExportRelationshipData())
/*     */     {
/* 225 */       return getAsset().getChildAssetIdsAsString();
/*     */     }
/* 227 */     return "";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.ExternalAssetImpl
 * JD-Core Version:    0.6.0
 */