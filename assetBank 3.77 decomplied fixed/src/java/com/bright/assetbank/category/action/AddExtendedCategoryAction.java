/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.category.constant.CategoryConstants;
/*     */ import com.bright.assetbank.category.form.CategoryAdminForm;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class AddExtendedCategoryAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants, MessageConstants
/*     */ {
/*  49 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*  60 */   protected ListManager m_listManager = null;
/*     */ 
/*  66 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*  72 */   protected AssetEntityRelationshipManager m_aerManager = null;
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/*  52 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public CategoryManager getCategoryManager()
/*     */   {
/*  57 */     return this.m_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  63 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/*  69 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityRelationshipManager(AssetEntityRelationshipManager a_aerManager)
/*     */   {
/*  75 */     this.m_aerManager = a_aerManager;
/*     */   }
/*     */ 
/*     */   public boolean saveCategory(DBTransaction a_transaction, HttpServletRequest a_request, Category a_category, CategoryAdminForm a_form, ABUserProfile a_userProfile, long a_lParentId, long a_lTreeId)
/*     */     throws Bn2Exception
/*     */   {
/*  99 */     boolean bValid = CategoryUtil.validateCategory(a_category.getName(), a_form, this.m_listManager, a_userProfile, a_transaction);
/*     */ 
/* 102 */     if (a_lTreeId == 2L)
/*     */     {
/* 104 */       String sReturn = a_request.getParameter("catExtensionReturnLocation");
/* 105 */       if (((sReturn.equals("explorer")) || (sReturn.equals("browse"))) && (AssetBankSettings.getDefaultAccessLevelsToAssignable()))
/*     */       {
/* 109 */         a_category.setCanAssignIfNotLeaf(true);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 114 */     if (bValid)
/*     */     {
/* 116 */       AssetCategoryManager assetCategoryManager = (AssetCategoryManager)this.m_categoryManager;
/* 117 */       boolean bSuccess = CategoryUtil.checkCategoryTypeAndSave(a_transaction, assetCategoryManager, a_category, a_lTreeId, a_lParentId, a_form.getExtendedCategory());
/*     */ 
/* 120 */       if (!bSuccess)
/*     */       {
/* 122 */         a_form.addError(this.m_listManager.getListItem(a_transaction, "categoryErrorDuplicateName", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */ 
/* 126 */     return bValid;
/*     */   }
/*     */ 
/*     */   public ActionForward getExtendedCategoryForward(DBTransaction a_transaction, HttpServletRequest a_request, ActionMapping a_mapping, Category a_category, String a_sQueryString, long a_lParentCatId)
/*     */     throws Bn2Exception
/*     */   {
/* 150 */     ActionForward afForward = null;
/* 151 */     if (StringUtil.stringIsPopulated(a_sQueryString))
/*     */     {
/* 153 */       a_sQueryString = a_sQueryString + "&";
/*     */     }
/* 155 */     a_sQueryString = a_sQueryString + "catExtensionCatId=" + a_category.getId() + "&" + "catExtensionTreeId" + "=" + a_category.getCategoryTypeId() + "&" + "catExtensionParentId" + "=" + a_lParentCatId + "&" + "catExtensionReturnLocation" + "=" + a_request.getParameter("catExtensionReturnLocation");
/*     */ 
/* 159 */     if (a_lParentCatId > 0L)
/*     */     {
/* 161 */       Category parent = this.m_categoryManager.getCategory(a_transaction, a_category.getCategoryTypeId(), a_lParentCatId);
/* 162 */       if (parent.getExtensionAssetId() > 0L)
/*     */       {
/* 165 */         a_sQueryString = a_sQueryString + "&" + "parentId" + "=" + parent.getExtensionAssetId();
/*     */ 
/* 168 */         SearchCriteria criteria = new SearchCriteria();
/* 169 */         criteria.setAssetIds(String.valueOf(parent.getExtensionAssetId()));
/* 170 */         LightweightAsset asset = (LightweightAsset)this.m_searchManager.search(criteria).getSearchResults().firstElement();
/* 171 */         if ((asset != null) && (asset.getEntity() != null) && (asset.getEntity().getId() > 0L))
/*     */         {
/* 173 */           ArrayList alEntities = this.m_aerManager.getChildEntitiesOf(a_transaction, asset.getEntity().getId());
/* 174 */           if ((alEntities != null) && (alEntities.size() == 1))
/*     */           {
/* 176 */             a_sQueryString = a_sQueryString + "&" + "entityId" + "=" + ((AssetEntity)alEntities.get(0)).getId();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 181 */     afForward = createRedirectingForward(a_sQueryString, a_mapping, "AddExtensionAsset");
/* 182 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.AddExtendedCategoryAction
 * JD-Core Version:    0.6.0
 */