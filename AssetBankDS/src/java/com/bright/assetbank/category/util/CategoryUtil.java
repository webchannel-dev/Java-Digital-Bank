/*     */ package com.bright.assetbank.category.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.category.form.CategoryValidationForm;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.constant.CategorySettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ 
/*     */ public class CategoryUtil extends com.bright.framework.category.util.CategoryUtil
/*     */ {
/*     */   public static long getDescriptiveBrowseTopLevelCatId()
/*     */   {
/*  58 */     String sId = CategorySettings.getBrowsingRootCategoryId(1L);
/*  59 */     if (StringUtil.stringIsPopulated(sId))
/*     */     {
/*     */       try
/*     */       {
/*  63 */         return Long.parseLong(sId);
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/*  67 */         return -1L;
/*     */       }
/*     */     }
/*     */ 
/*  71 */     return -1L;
/*     */   }
/*     */ 
/*     */   public static boolean checkAccessLevelPermissions(Set<Long> a_userPermissions, Set<Long> a_requiredPermissions)
/*     */   {
/*  76 */     boolean bHasPermission = false;
/*  77 */     if (AssetBankSettings.getUserRequiresAllAccessLevelsToViewAsset())
/*     */     {
/*  80 */       if (a_userPermissions.containsAll(a_requiredPermissions))
/*     */       {
/*  82 */         bHasPermission = true;
/*     */       }
/*     */ 
/*     */     }
/*  88 */     else if (CollectionUtils.containsAny(a_userPermissions, a_requiredPermissions))
/*     */     {
/*  90 */       bHasPermission = true;
/*     */     }
/*     */ 
/*  93 */     return bHasPermission;
/*     */   }
/*     */ 
/*     */   public static boolean validateCategory(String a_sName, CategoryValidationForm a_form, ListManager a_listManager, UserProfile userProfile, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 112 */     boolean bValid = true;
/*     */ 
/* 114 */     if (!a_form.getExtendedCategory())
/*     */     {
/* 117 */       if ((a_sName == null) || (a_sName.trim().length() == 0))
/*     */       {
/* 119 */         a_form.addError(a_listManager.getListItem(a_dbTransaction, "categoryErrorBlankName", userProfile.getCurrentLanguage()).getBody());
/* 120 */         bValid = false;
/*     */       }
/*     */     }
/*     */ 
/* 124 */     return bValid;
/*     */   }
/*     */ 
/*     */   public static void checkForCategoryExtensionError(HttpServletRequest a_request, Bn2Form a_form)
/*     */   {
/* 138 */     String sCatExtensionError = (String)a_request.getAttribute("ExtensionError");
/* 139 */     if (StringUtil.stringIsPopulated(sCatExtensionError))
/*     */     {
/* 141 */       a_form.addError(sCatExtensionError);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean checkCategoryTypeAndSave(DBTransaction a_transaction, AssetCategoryManager a_categoryManager, Category a_category, long a_lTreeId, long a_lParentId, boolean a_bExtendedCategory)
/*     */     throws Bn2Exception
/*     */   {
/* 167 */     boolean bSuccess = false;
/* 168 */     if (a_lTreeId == 2L)
/*     */     {
/* 170 */       bSuccess = a_categoryManager.newPermissionCategory(a_transaction, a_lTreeId, a_category, a_lParentId);
/*     */     }
/*     */     else
/*     */     {
/* 174 */       bSuccess = a_categoryManager.newDescriptiveCategory(a_transaction, a_lTreeId, a_category, a_lParentId);
/*     */     }
/*     */ 
/* 178 */     if ((bSuccess) && (a_bExtendedCategory))
/*     */     {
/* 180 */       String sPrefix = "Extended Category ";
/* 181 */       if (a_category.getCategoryTypeId() == 2L)
/*     */       {
/* 183 */         sPrefix = "Extended Access Level ";
/*     */       }
/* 185 */       a_category.setName(sPrefix + a_category.getId());
/* 186 */       bSuccess = a_categoryManager.updateCategoryDetails(a_transaction, a_lTreeId, a_category);
/*     */     }
/*     */ 
/* 189 */     return bSuccess;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.util.CategoryUtil
 * JD-Core Version:    0.6.0
 */