/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.UploadedFileInfo;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.FileUploadManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.util.UploadUtil;
/*     */ import com.bright.assetbank.application.util.VideoUtil;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class UploadAssetFileAction extends UploadFileAction
/*     */   implements AssetBankConstants, MessageConstants
/*     */ {
/*  80 */   protected IAssetManager m_assetManager = null;
/*  81 */   private AssetEntityManager m_assetEntityManager = null;
/*  82 */   private FilterManager m_filterManager = null;
/*  83 */   protected AgreementsManager m_agreementsManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 111 */     ActionForward afForward = null;
/* 112 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/* 114 */     String sFile = a_request.getParameter("filePath");
/* 115 */     String sDirName = a_request.getParameter("dirName");
/* 116 */     String sIndex = a_request.getParameter("index");
/*     */ 
/* 118 */     if ((StringUtil.stringIsPopulated(sFile)) && (StringUtil.stringIsPopulated(sDirName)) && (StringUtil.stringIsPopulated(sIndex)))
/*     */     {
/* 123 */       form.setTempFileLocation(sFile);
/* 124 */       form.setTempDirName(sDirName);
/* 125 */       form.setTempFileIndex(sIndex);
/* 126 */       return a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 129 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 132 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getCanUpdateAssets()) && (!userProfile.getCanUploadWithApproval())))
/*     */     {
/* 134 */       this.m_logger.debug("This user does not have permission to view the add image page");
/* 135 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 139 */     if (AssetBankSettings.getAttributeTemplatesEnabled())
/*     */     {
/* 142 */       long lTemplateId = getLongParameter(a_request, "templateId");
/*     */ 
/* 144 */       if (lTemplateId > 0L)
/*     */       {
/* 147 */         form.setTemplate(this.m_filterManager.getFilter(a_dbTransaction, lTemplateId));
/*     */       }
/*     */     }
/*     */ 
/* 151 */     boolean bEmptyAsset = ((a_request.getParameter("emptyAsset") != null) && (Boolean.parseBoolean(a_request.getParameter("emptyAsset")))) || ((a_request.getParameter("forceEmptyAsset") != null) && (Boolean.parseBoolean(a_request.getParameter("forceEmptyAsset"))));
/*     */ 
/* 154 */     form.setEmptyAsset(bEmptyAsset);
/*     */ 
/* 157 */     if (form.getAsset().getEntity().getId() > 0L)
/*     */     {
/* 159 */       AssetEntity entity = this.m_assetEntityManager.getEntity(a_dbTransaction, form.getAsset().getEntity().getId());
/* 160 */       form.getAsset().setEntity(entity);
/*     */     }
/*     */ 
/* 163 */     File uploadedFile = null;
/*     */ 
/* 166 */     if ((!bEmptyAsset) && (StringUtils.isEmpty(form.getTempFileLocation())) && ((form.getAsset().getEntity().getId() <= 0L) || (form.getAsset().getEntity().getAllowAssetFiles())))
/*     */     {
/* 170 */       uploadedFile = getUploadedFileAndValidate(a_dbTransaction, userProfile, a_request.getSession(), form);
/* 171 */       if (form.getHasErrors())
/*     */       {
/* 173 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */     }
/*     */ 
/* 177 */     if (!form.getConditionsAccepted())
/*     */     {
/* 179 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationConditions", userProfile.getCurrentLanguage()).getBody());
/* 180 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 184 */     String maxFileUploadSizeError = getFileUploadManager().validateMaxFileUploadSize(a_dbTransaction, userProfile, form.getFile());
/* 185 */     if (maxFileUploadSizeError != null)
/*     */     {
/* 187 */       form.addError(maxFileUploadSizeError);
/* 188 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 194 */       AssetFileSource source = null;
/* 195 */       UploadedFileInfo uploadedFileInfo = null;
/*     */ 
/* 197 */       if ((!bEmptyAsset) && (StringUtils.isEmpty(form.getTempFileLocation())) && ((form.getAsset().getEntity().getId() <= 0L) || (form.getAsset().getEntity().getAllowAssetFiles())))
/*     */       {
/* 201 */         String filename = form.getFile() != null ? form.getFile().getFileName() : uploadedFile.getName();
/* 202 */         Vector formats = this.m_assetManager.getAllFileFormatsForFile(a_dbTransaction, filename);
/* 203 */         FileFormat format = null;
/*     */ 
/* 206 */         if (formats.size() == 1)
/*     */         {
/* 208 */           format = (FileFormat)formats.firstElement();
/*     */         }
/*     */         else
/*     */         {
/*     */           Iterator iFormats;
/* 211 */           if (form.getAsset().getFormat().getId() > 0L)
/*     */           {
/* 213 */             for (iFormats = formats.iterator(); iFormats.hasNext(); )
/*     */             {
/* 215 */               FileFormat possibleFormat = (FileFormat)iFormats.next();
/* 216 */               if (possibleFormat.getId() == form.getAsset().getFormat().getId())
/*     */               {
/* 218 */                 format = possibleFormat;
/* 219 */                 break;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 227 */             if (form.getFile() != null)
/*     */             {
/* 229 */               UploadUtil.storeUploadedFile(form.getFile().getFileName(), form.getFile().getInputStream(), userProfile, a_request.getSession());
/*     */             }
/* 231 */             form.setPossibleFileFormats(formats);
/* 232 */             form.setUploadedFilename(UploadUtil.getUploadedFile(userProfile, a_request.getSession()).getName());
/* 233 */             return a_mapping.findForward("Failure");
/*     */           }
/*     */         }
/* 236 */         if (form.getFile() != null)
/*     */         {
/* 238 */           source = new AssetFileSource(form.getFile());
/* 239 */           form.setOriginalFilename(form.getFile().getFileName());
/*     */         }
/*     */         else
/*     */         {
/* 243 */           source = new AssetFileSource(uploadedFile);
/* 244 */           form.setOriginalFilename(uploadedFile.getName());
/*     */         }
/*     */ 
/* 248 */         uploadedFileInfo = getAssetManager().storeTempUploadedFile(source, format, StoredFileType.ASSET);
/* 249 */         form.setTempFileLocation(uploadedFileInfo.getFileLocation());
/*     */ 
/* 252 */         source.close();
/*     */       }
/*     */ 
/* 257 */       if (form.getDeferEntitySelection())
/*     */       {
/* 259 */         long lEntityId = this.m_assetEntityManager.getAssetEntityIdForAssetTypeAndFileFormat(null, uploadedFileInfo.getAssetTypeId(), FileUtil.getSuffix(uploadedFileInfo.getFileLocation()), form.getParentId() > 0L);
/*     */ 
/* 264 */         if (lEntityId > 0L)
/*     */         {
/* 266 */           form.setSelectedAssetEntityId(lEntityId);
/*     */         }
/*     */         else
/*     */         {
/* 270 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoType", userProfile.getCurrentLanguage(), new String[] { form.getAsset().getEntity().getName() }));
/* 271 */           return a_mapping.findForward("Failure");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 276 */       if (form.getSelectedAssetEntityId() > 0L)
/*     */       {
/* 278 */         form.getAsset().setEntity(this.m_assetEntityManager.getEntity(a_dbTransaction, form.getSelectedAssetEntityId()));
/* 279 */         form.getAsset().getEntity().setLanguage(userProfile.getCurrentLanguage());
/*     */       }
/*     */ 
/* 283 */       if (form.getParentId() > 0L)
/*     */       {
/* 285 */         form.getAsset().setParentAssetIdsAsString(String.valueOf(form.getParentId()));
/*     */       }
/* 287 */       else if (form.getPeerId() > 0L)
/*     */       {
/* 289 */         form.getAsset().setPeerAssetIdsAsString(String.valueOf(form.getPeerId()));
/*     */       }
/*     */ 
/* 294 */       if ((form.getAsset().getEntity() != null) && (form.getAsset().getEntity().getId() > 0L))
/*     */       {
/* 296 */         if ((bEmptyAsset) && (form.getAsset().getEntity().getAllowAssetFiles()) && (!userProfile.getUserCanAddEmptyAssets()))
/*     */         {
/* 298 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationFileRequired", userProfile.getCurrentLanguage()).getBody());
/*     */         }
/* 300 */         else if ((!bEmptyAsset) && (form.getAsset().getEntity().getAllowAssetFiles()) && (!form.getAsset().getEntity().getAllowAssetType(uploadedFileInfo.getAssetTypeId())))
/*     */         {
/* 302 */           if (form.getDeferEntitySelection())
/*     */           {
/* 304 */             form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoType", userProfile.getCurrentLanguage()).getBody());
/* 305 */             return a_mapping.findForward("Failure");
/*     */           }
/*     */ 
/* 309 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationWrongType", userProfile.getCurrentLanguage(), new String[] { form.getAsset().getEntity().getName() }));
/* 310 */           return a_mapping.findForward("Failure");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 316 */       if (AssetBankSettings.getAgreementsEnabled())
/*     */       {
/* 319 */         Vector vOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, userProfile.getUser().getId());
/*     */ 
/* 322 */         form.setAgreements(this.m_agreementsManager.getAgreements(a_dbTransaction, true, vOrgUnits, 0L, true));
/*     */       }
/*     */ 
/* 326 */       if ((uploadedFileInfo != null) && (uploadedFileInfo.getAssetTypeId() == 3L))
/*     */       {
/* 329 */         sDirName = this.m_fileStoreManager.getUniqueFilepath("vidthumbs", StoredFileType.TEMP);
/* 330 */         String sDirectory = this.m_fileStoreManager.getAbsolutePath(sDirName);
/*     */ 
/* 332 */         form.setAsset(new VideoAsset(form.getAsset()));
/* 333 */         form.getAsset().setPreviewImageFile(uploadedFileInfo.getPreviewImage());
/* 334 */         VideoUtil.clearThumbnailDirectory(this.m_fileStoreManager.getAbsolutePath(sDirName));
/*     */ 
/* 337 */         File f = new File(sDirectory);
/*     */ 
/* 339 */         if (!f.exists())
/*     */         {
/* 341 */           f.mkdir();
/*     */         }
/*     */ 
/* 344 */         GregorianCalendar now = new GregorianCalendar();
/* 345 */         form.setTempDirName(sDirName);
/* 346 */         form.setTempFileIndex(String.valueOf(now.getTimeInMillis()));
/*     */       }
/*     */ 
/* 349 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     catch (FileNotFoundException fnfe)
/*     */     {
/* 353 */       this.m_logger.error("FileNotFoundException in UploadAssetFileAction:", fnfe);
/* 354 */       throw new Bn2Exception("FileNotFoundException in UploadAssetFileAction ", fnfe);
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 358 */       this.m_logger.error("IOException in UploadAssetFileAction:", ioe);
/* 359 */       throw new Bn2Exception("IOException in UploadAssetFileAction ", ioe);
/*     */     }
/*     */ 
/* 362 */     return afForward;
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 369 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 375 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 380 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 385 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ 
/*     */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*     */   {
/* 390 */     this.m_agreementsManager = a_agreementsManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.UploadAssetFileAction
 * JD-Core Version:    0.6.0
 */