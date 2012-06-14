/*     */ package com.bright.assetbank.entity.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.form.AssetEntityForm;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.plugin.service.PluginManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.service.ManualReindexQueueManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveAssetEntityAction extends AssetEntityAction
/*     */   implements AssetBankConstants
/*     */ {
/* 185 */   protected ManualReindexQueueManager m_searchManager = null;
/*     */ 
/* 191 */   protected ListManager m_listManager = null;
/*     */   private CategoryManager m_categoryManager;
/*     */   private PluginManager m_pluginManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     AssetEntityForm form = (AssetEntityForm)a_form;
/*     */ 
/*  64 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  67 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  69 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  72 */     validateMandatoryFields(form, a_request);
/*     */ 
/*  74 */     AssetEntity entity = form.getEntity();
/*     */ 
/*  77 */     boolean bAdding = entity.getId() <= 0L;
/*     */ 
/*  80 */     if ((StringUtils.isNotEmpty(entity.getIncludedFileFormats())) || (StringUtils.isNotEmpty(entity.getExcludedFileFormats())))
/*     */     {
/*  82 */       if ((form.getAllowableAssetTypeIds() == null) || (form.getAllowableAssetTypeIds().length == 0))
/*     */       {
/*  84 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationFileFormatsRequireMediaTypes", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*  86 */       else if ((StringUtils.isNotEmpty(entity.getIncludedFileFormats())) && (StringUtils.isNotEmpty(entity.getExcludedFileFormats())))
/*     */       {
/*  88 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationCannotExcludeAndInclude", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */ 
/*  92 */     if (entity.getDefaultCategoryId() > 0L)
/*     */     {
/*     */       String sUnwantedCategoryTreeName;
/*     */       long lRequiredCategoryTreeId;
/*     */       long lUnwantedCategoryTreeId;
/*     */       String sRequiredCategoryTreeName;
/*     */       //String sUnwantedCategoryTreeName;
/*  98 */       if (AssetBankSettings.getEntityDefaultCategoryIdsDescriptive())
/*     */       {
/* 100 */          lRequiredCategoryTreeId = 1L;
/* 101 */          lUnwantedCategoryTreeId = 2L;
/* 102 */          sRequiredCategoryTreeName = "descriptive";
/* 103 */          sUnwantedCategoryTreeName = "permission";
/*     */       }
/*     */       else
/*     */       {
/* 107 */         lRequiredCategoryTreeId = 2L;
/* 108 */         lUnwantedCategoryTreeId = 1L;
/* 109 */         sRequiredCategoryTreeName = "permission";
/* 110 */         sUnwantedCategoryTreeName = "descriptive";
/*     */       }
/*     */ 
/* 114 */       if (!this.m_categoryManager.categoryExists(a_dbTransaction, lRequiredCategoryTreeId, entity.getDefaultCategoryId()))
/*     */       {
/*     */         String sCategoryErrorMessage;
/*     */         //String sCategoryErrorMessage;
/* 117 */         if (this.m_categoryManager.categoryExists(a_dbTransaction, lUnwantedCategoryTreeId, entity.getDefaultCategoryId()))
/*     */         {
/* 119 */           sCategoryErrorMessage = this.m_listManager.getListItem(a_dbTransaction, "assetEntityDefaultCategoryInWrongTree", userProfile.getCurrentLanguage(), new String[] { String.valueOf(entity.getDefaultCategoryId()), sRequiredCategoryTreeName, sUnwantedCategoryTreeName });
/*     */         }
/*     */         else
/*     */         {
/* 125 */           sCategoryErrorMessage = this.m_listManager.getListItem(a_dbTransaction, "assetEntityDefaultCategoryDoesNotExist", userProfile.getCurrentLanguage(), new String[] { String.valueOf(entity.getDefaultCategoryId()), sRequiredCategoryTreeName });
/*     */         }
/*     */ 
/* 130 */         form.addError(sCategoryErrorMessage);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 135 */     if (bAdding)
/*     */     {
/* 137 */       getPluginManager().validateAdd(a_dbTransaction, userProfile, "assetEntity", entity, form);
/*     */     }
/*     */     else
/*     */     {
/* 141 */       getPluginManager().validateEdit(a_dbTransaction, userProfile, "assetEntity", entity, form);
/*     */     }
/*     */ 
/* 144 */     if (form.getHasErrors())
/*     */     {
/* 146 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 149 */     if (bAdding)
/*     */     {
/* 151 */       getPluginManager().extractAddDataFromForm(a_dbTransaction, "assetEntity", entity, form);
/*     */     }
/*     */     else
/*     */     {
/* 155 */       getPluginManager().extractEditDataFromForm(a_dbTransaction, "assetEntity", entity, form);
/*     */     }
/*     */ 
/* 159 */     long lOriginalEntityId = entity.getId();
/*     */ 
/* 161 */     String[] allowableAttributeIdsArray = form.getAllowableAttributeIds();
/* 162 */     entity.setAllowableAttributes(StringUtil.convertToVectorOfLongs(allowableAttributeIdsArray));
/* 163 */     entity.setAllowableAssetTypes(StringUtil.convertToVectorOfLongs(form.getAllowableAssetTypeIds()));
/*     */ 
/* 166 */     entity = getAssetEntityManager().saveEntity(a_dbTransaction, entity);
/*     */ 
/* 169 */     a_request.getSession().getServletContext().setAttribute("quickSearchEntities", getAssetEntityManager().getAllQuickSearchableEntities(a_dbTransaction));
/*     */ 
/* 172 */     int iReindex = getIntParameter(a_request, "reindex");
/* 173 */     if ((lOriginalEntityId > 0L) && (iReindex > 0))
/*     */     {
/* 175 */       Vector vecIdsToReindex = getAssetEntityManager().searchForIdsOfAssetEntityInstances(lOriginalEntityId);
/*     */ 
/* 177 */       this.m_searchManager.queueRebuildIndex(true, userProfile.getUserId(), vecIdsToReindex);
/*     */ 
/* 179 */       return a_mapping.findForward("ReindexStatus");
/*     */     }
/*     */ 
/* 182 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setSearchManager(ManualReindexQueueManager a_searchManager)
/*     */   {
/* 188 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 194 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 200 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public PluginManager getPluginManager()
/*     */   {
/* 206 */     return this.m_pluginManager;
/*     */   }
/*     */ 
/*     */   public void setPluginManager(PluginManager a_pluginManager)
/*     */   {
/* 212 */     this.m_pluginManager = a_pluginManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.action.SaveAssetEntityAction
 * JD-Core Version:    0.6.0
 */