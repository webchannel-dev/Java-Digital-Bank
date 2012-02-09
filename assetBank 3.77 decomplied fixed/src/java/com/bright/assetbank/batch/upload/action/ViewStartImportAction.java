/*     */ package com.bright.assetbank.batch.upload.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.action.ViewAddAssetAction;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.SubmitOptions;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.UploadUtil;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.batch.upload.form.ImportForm;
/*     */ import com.bright.assetbank.batch.upload.service.ImportManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.bean.AssetEntityRetreivalCriteria;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.constant.UserConstants;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewStartImportAction extends ViewAddAssetAction
/*     */   implements UserConstants, AssetBankConstants
/*     */ {
/*     */   public static final String k_sParamValue_ZipFiles = "zip_files";
/*     */   private static final String k_sParamName_Uploaded = "uploaded";
/*     */   private static final String c_ksClassName = "ViewStartImportAction";
/*  83 */   private ImportManager m_importManager = null;
/*  84 */   protected ListManager m_listManager = null;
/*  85 */   private FilterManager m_filterManager = null;
/*  86 */   protected AssetEntityRelationshipManager m_assetEntityRelationshipManager = null;
/*     */ 
/*     */   public void setAssetEntityRelationshipManager(AssetEntityRelationshipManager a_assetEntityRelationshipManager)
/*     */   {
/*  90 */     this.m_assetEntityRelationshipManager = a_assetEntityRelationshipManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 112 */     String ksMethodName = "execute";
/*     */ 
/* 114 */     ImportForm form = (ImportForm)a_form;
/* 115 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 118 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 120 */       this.m_logger.error("ViewStartImportAction.execute : User does not have permission.");
/* 121 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 128 */     long lEntityId = getLongParameter(a_request, "entityId");
/* 129 */     long lParentId = getLongParameter(a_request, "parentId");
/* 130 */     long lPeerId = getLongParameter(a_request, "peerId");
/*     */ 
/* 133 */     String sUploadType = a_request.getParameter("whatToUpload");
/*     */ 
/* 135 */     if ((sUploadType != null) && (sUploadType.equals("addPlaceholders")))
/*     */     {
/* 139 */       String sQueryString = "entityId=" + lEntityId + "&" + "parentId" + "=" + lParentId;
/* 140 */       return createRedirectingForward(sQueryString, a_mapping, "AddPlaceholders");
/*     */     }
/*     */ 
/* 143 */     if (((sUploadType != null) && (sUploadType.equals("importFilesToExistingAssets"))) || (a_request.getParameter("importFilesToExistingAssets") != null))
/*     */     {
/* 146 */       form.setImportFilesToExistingAssets(true);
/*     */     }
/* 148 */     else if (((sUploadType != null) && (sUploadType.equals("importChildAssets"))) || (a_request.getParameter("importChildAssets") != null))
/*     */     {
/* 151 */       form.setImportChildAssets(true);
/*     */     }
/*     */     else
/*     */     {
/* 155 */       form.setImportNewAssets(true);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 162 */       int iStart = getIntParameter(a_request, "start");
/* 163 */       if ((iStart > 0) && (!form.getConditionsAccepted()))
/*     */       {
/* 165 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationConditions", userProfile.getCurrentLanguage()).getBody());
/* 166 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 169 */       UploadUtil.setUploadToolOption(a_request, form);
/*     */ 
/* 171 */       form.setUseFilenameAsTitle(AssetBankSettings.getPopulateNameFromFilenameDefault());
/* 172 */       form.setLinkAssets(AssetBankSettings.getLinkItemsDefault());
/* 173 */       form.setDeferThumbnailGeneration(AssetBankSettings.getDeferThumbnailCreationDefault());
/* 174 */       form.setDirectoryList(this.m_importManager.getImportDirectoryList(userProfile.getUser()));
/* 175 */       form.setZipFileList(this.m_importManager.getImportZipFileList(userProfile.getUser()));
/* 176 */       form.setAllTopLevelFilesList(this.m_importManager.getImportTopLevelFileList(userProfile.getUser(), true));
/* 177 */       form.setBulkUploadDirectory(this.m_importManager.getUploadDirectory());
/*     */ 
/* 181 */       form.setImportFileOption("directory_files");
/*     */ 
/* 183 */       form.setTopLevelFileCount(this.m_importManager.getImportTopLevelFileList(userProfile.getUser(), false).size());
/*     */ 
/* 185 */       form.setTopLevelFileCountIncludeZips(this.m_importManager.getImportTopLevelFileList(userProfile.getUser(), true).size());
/*     */ 
/* 187 */       form.setIdAppearsInFilenames(this.m_importManager.getIdAppearsInImportFilenames(userProfile.getUser()));
/*     */ 
/* 190 */       if ((form.getTopLevelFileCountIncludeZips() == 0) && (form.getDirectoryList().size() == 0) && (getIntParameter(a_request, "uploaded") == 1))
/*     */       {
/* 192 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoFilesToAdd", userProfile.getCurrentLanguage()).getBody());
/* 193 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 197 */       if ((form.getTopLevelFileCount() == 0) && (form.getTopLevelFileCountIncludeZips() >= 1))
/*     */       {
/* 199 */         form.setImportFileOption("zip_files");
/*     */       }
/*     */ 
/* 202 */       if (form.getTopLevelFileCountIncludeZips() - form.getTopLevelFileCount() == 1)
/*     */       {
/* 204 */         form.setZipPath(FileUtil.getFirstFilenameWithExtension(form.getZipFileList(), "zip"));
/*     */       }
/*     */ 
/* 208 */       String sFTPPath = AssetBankSettings.getFTPCommand() + "/" + this.m_importManager.getUserSubDirectoryName(userProfile.getUser());
/*     */ 
/* 211 */       String sFTPDirectory = this.m_importManager.getUserSubDirectoryName(userProfile.getUser());
/*     */ 
/* 214 */       if ((form.getImportFilesToExistingAssets()) && (form.getUploadToolOption() != null) && (form.getUploadToolOption().equals("ftp")))
/*     */       {
/* 216 */         UploadUtil.getUploadDirectory(a_request.getSession(), false, true);
/* 217 */         sFTPPath = sFTPPath + "/files_for_existing_assets";
/* 218 */         sFTPDirectory = sFTPDirectory + "/files_for_existing_assets";
/*     */       }
/*     */ 
/* 221 */       form.setFTPPath(sFTPPath);
/* 222 */       form.setFTPDir(sFTPDirectory);
/*     */ 
/* 226 */       if (lEntityId > 0L)
/*     */       {
/* 228 */         form.getAsset().setEntity(this.m_assetEntityManager.getEntity(a_dbTransaction, lEntityId));
/* 229 */         form.setSelectedAssetEntityId(lEntityId);
/* 230 */         form.setEntityPreSelected(true);
/*     */       }
/*     */       else
/*     */       {
/* 235 */         Vector vecEntities = null;
/*     */ 
/* 237 */         if (form.getImportNewAssets())
/*     */         {
/* 240 */           long[] aEntityIds = null;
/* 241 */           if (lParentId > 0L)
/*     */           {
/* 243 */             Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lParentId, null, false, false);
/* 244 */             aEntityIds = asset.getEntity().getChildRelationshipIds();
/*     */           }
/* 246 */           else if (lPeerId > 0L)
/*     */           {
/* 248 */             Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lPeerId, null, false, false);
/* 249 */             aEntityIds = asset.getEntity().getPeerRelationshipIds();
/*     */           }
/*     */ 
/* 252 */           AssetEntityRetreivalCriteria criteria = new AssetEntityRetreivalCriteria();
/* 253 */           criteria.setFileEntitiesOnly(true);
/* 254 */           criteria.setOnlyParentlessEntities(lParentId <= 0L);
/* 255 */           criteria.setRestrictions(aEntityIds);
/* 256 */           criteria.setCategoryExtensionStatus(2);
/* 257 */           vecEntities = this.m_assetEntityManager.getEntities(a_dbTransaction, criteria);
/*     */         }
/*     */         else
/*     */         {
/* 262 */           Vector vecFileMatchEntities = this.m_assetEntityManager.getEntitiesWithMatchAttributeFileEntitiesOnly(a_dbTransaction);
/*     */ 
/* 264 */           if (form.getImportChildAssets())
/*     */           {
/* 266 */             vecEntities = new Vector();
/*     */ 
/* 268 */             Iterator itEntities = vecFileMatchEntities.iterator();
/* 269 */             while (itEntities.hasNext())
/*     */             {
/* 271 */               AssetEntity entity = (AssetEntity)itEntities.next();
/*     */ 
/* 273 */               if (this.m_assetEntityRelationshipManager.isChildEntity(a_dbTransaction, entity.getId()))
/*     */               {
/* 277 */                 Vector vecCandidateParents = this.m_assetEntityManager.getAllEntities(a_dbTransaction);
/* 278 */                 Iterator itCandParents = vecCandidateParents.iterator();
/*     */                 AssetEntity candParent;
/* 279 */                 while (itCandParents.hasNext())
/*     */                 {
/* 281 */                   candParent = (AssetEntity)itCandParents.next();
/* 282 */                   for (AssetEntityRelationship aer : candParent.getChildRelationships())
/*     */                   {
/* 284 */                     if (aer.getRelatesToAssetEntityId() == entity.getId())
/*     */                     {
/* 288 */                       Vector vecAttrIds = this.m_assetEntityManager.getAttributeIdsForEntity(a_dbTransaction, candParent.getId(), true);
/* 289 */                       if (vecAttrIds.contains(new Long(entity.getMatchOnAttributeId())))
/*     */                       {
/* 292 */                         vecEntities.add(entity);
/* 293 */                         break;
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 304 */             vecEntities = vecFileMatchEntities;
/*     */           }
/*     */         }
/*     */ 
/* 308 */         form.setEntities(vecEntities);
/*     */ 
/* 310 */         LanguageUtils.setLanguageOnAll(form.getEntities(), userProfile.getCurrentLanguage());
/*     */ 
/* 313 */         if ((form.getEntities() != null) && (form.getEntities().size() == 1))
/*     */         {
/* 315 */           form.setSelectedAssetEntityId(((AssetEntity)form.getEntities().get(0)).getId());
/*     */         }
/*     */ 
/* 319 */         if ((form.getEntities() != null) && (form.getSelectedAssetEntityId() > 0L))
/*     */         {
/* 321 */           AssetEntity entity = (AssetEntity)CollectionUtil.getDataBeanWithId(form.getEntities(), form.getSelectedAssetEntityId());
/* 322 */           form.getAsset().setEntity(entity);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 327 */       Vector formatOptions = this.m_assetManager.getAmbiguousFileFormats(a_dbTransaction);
/* 328 */       if (!formatOptions.isEmpty())
/*     */       {
/* 330 */         form.setFormatOptions(formatOptions);
/*     */       }
/*     */ 
/* 334 */       long lSuccess = getLongParameter(a_request, "uploaded");
/*     */ 
/* 336 */       if (lSuccess > 0L)
/*     */       {
/* 338 */         form.setSuccess(true);
/*     */ 
/* 340 */         this.m_importManager.deletePartiallyUploadedFiles(this.m_importManager.getUserSubDirectoryName(userProfile.getUser()));
/*     */ 
/* 343 */         if (AssetBankSettings.getAttributeTemplatesEnabled())
/*     */         {
/* 345 */           form.setTemplates(this.m_filterManager.getTemplates(a_dbTransaction, userProfile));
/*     */ 
/* 348 */           int lTemplateId = getIntParameter(a_request, "templateId");
/*     */ 
/* 350 */           if (lTemplateId > 0)
/*     */           {
/* 353 */             form.setTemplate(this.m_filterManager.getFilter(a_dbTransaction, lTemplateId));
/* 354 */             form.setCurrentTemplateId(lTemplateId);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 362 */       this.m_logger.error("ViewStartImportAction.execute exception: " + bn2e.getMessage());
/* 363 */       throw bn2e;
/*     */     }
/*     */ 
/* 367 */     if (a_request.getParameter("Cancel") != null)
/*     */     {
/* 369 */       return a_mapping.findForward("Cancel");
/*     */     }
/*     */ 
/* 372 */     ActionForward afForward = super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*     */ 
/* 375 */     if (!form.getHasErrors())
/*     */     {
/* 377 */       if (form.getSubmitOptions().getContains(2))
/*     */       {
/* 379 */         form.setSelectedSubmitOption(2);
/*     */       }
/*     */ 
/* 383 */       int iFirst = getIntParameter(a_request, "bulkUploadFirst");
/* 384 */       if (iFirst > 0)
/*     */       {
/* 386 */         form.setBulkUploadFirst(true);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 391 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected void populateMetadataDefaults(AssetForm a_assetForm)
/*     */     throws Bn2Exception
/*     */   {
/* 407 */     if (AssetBankSettings.getBulkUploadPopulateDefaults())
/*     */     {
/* 409 */       super.populateMetadataDefaults(a_assetForm);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setImportManager(ImportManager a_importManager)
/*     */   {
/* 416 */     this.m_importManager = a_importManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 421 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 426 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.action.ViewStartImportAction
 * JD-Core Version:    0.6.0
 */