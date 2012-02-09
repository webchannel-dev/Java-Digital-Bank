/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.batch.update.bean.BulkUpdateInfo;
/*     */ import com.bright.assetbank.batch.update.form.BulkUpdateForm;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*     */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class BulkUpdateStartAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  77 */   private IAssetManager m_assetManager = null;
/*     */ 
/*  83 */   private UpdateManager m_updateManager = null;
/*     */ 
/*  89 */   protected TaxonomyManager m_taxonomyManager = null;
/*     */ 
/*  95 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/*  80 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setUpdateManager(UpdateManager a_manager)
/*     */   {
/*  86 */     this.m_updateManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager a_taxonomyManager)
/*     */   {
/*  92 */     this.m_taxonomyManager = a_taxonomyManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  98 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 124 */     ActionForward afForward = null;
/* 125 */     BulkUpdateForm form = (BulkUpdateForm)a_form;
/*     */ 
/* 127 */     BulkUpdateInfo info = new BulkUpdateInfo();
/*     */ 
/* 130 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 131 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 133 */       this.m_logger.debug("Only logged in users can perform bulk uploads");
/* 134 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 138 */     BatchUpdateController controller = userProfile.getBatchUpdateController();
/* 139 */     if (controller == null)
/*     */     {
/* 141 */       throw new Bn2Exception("BulkUpdateStartAction.exceute: called without a valid BatchUpdateController");
/*     */     }
/*     */ 
/* 145 */     form.validatePrice(a_request, this.m_listManager, userProfile, a_dbTransaction);
/* 146 */     boolean bDateError = form.validateDates(a_request, this.m_listManager, userProfile, a_dbTransaction);
/*     */ 
/* 149 */     boolean bDeleteAssets = false;
/*     */ 
/* 151 */     if (a_request.getParameter("deleteAssets") != null)
/*     */     {
/* 153 */       bDeleteAssets = true;
/*     */     }
/*     */ 
/* 158 */     Map hmAttributesToReplace = new HashMap();
/* 159 */     Map hmAttributesToAppend = new HashMap();
/* 160 */     Map hmAttributesToPrepend = new HashMap();
/* 161 */     Map hmAttributesToRemove = new HashMap();
/* 162 */     Map hmAttributeDelimiters = new HashMap();
/*     */ 
/* 164 */     Enumeration enumParams = a_request.getParameterNames();
/* 165 */     while (enumParams.hasMoreElements())
/*     */     {
/* 167 */       String sParamName = (String)enumParams.nextElement();
/* 168 */       String sParamValue = a_request.getParameter(sParamName);
/*     */ 
/* 171 */       if (sParamName.equals("update_categories"))
/*     */       {
/* 173 */         if (sParamValue.equals("append"))
/*     */         {
/* 175 */           info.setAppendCategories(true);
/*     */         }
/* 177 */         else if (sParamValue.equals("replace"))
/*     */         {
/* 179 */           info.setReplaceCategories(true);
/*     */         }
/* 181 */         else if (sParamValue.equals("remove"))
/*     */         {
/* 183 */           info.setRemoveCategories(true);
/*     */         }
/*     */ 
/*     */       }
/* 187 */       else if (sParamName.equals("update_accessLevels"))
/*     */       {
/* 189 */         if (sParamValue.equals("append"))
/*     */         {
/* 191 */           info.setAppendAccessLevels(true);
/*     */         }
/* 193 */         else if (sParamValue.equals("replace"))
/*     */         {
/* 195 */           info.setReplaceAccessLevels(true);
/*     */         }
/* 197 */         else if (sParamValue.equals("remove"))
/*     */         {
/* 199 */           info.setRemoveAccessLevels(true);
/*     */         }
/*     */ 
/*     */       }
/* 203 */       else if (sParamName.startsWith("update_"))
/*     */       {
/* 205 */         String sFieldName = sParamName.substring("update_".length());
/*     */ 
/* 207 */         if (sParamValue.equals("append"))
/*     */         {
/* 209 */           hmAttributesToAppend.put(sFieldName, sFieldName);
/*     */         }
/* 211 */         else if (sParamValue.equals("prepend"))
/*     */         {
/* 213 */           hmAttributesToPrepend.put(sFieldName, sFieldName);
/*     */         }
/* 215 */         else if (sParamValue.equals("replace"))
/*     */         {
/* 218 */           hmAttributesToReplace.put(sFieldName, sFieldName);
/*     */         }
/* 220 */         else if (sParamValue.equals("remove"))
/*     */         {
/* 223 */           hmAttributesToRemove.put(sFieldName, sFieldName);
/*     */         }
/*     */ 
/*     */       }
/* 227 */       else if (sParamName.startsWith("delimiter_"))
/*     */       {
/* 229 */         String sFieldName = sParamName.substring("delimiter_".length());
/* 230 */         hmAttributeDelimiters.put(sFieldName, sParamValue);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 237 */     Vector allAttributes = null;
/* 238 */     if (userProfile.getIsAdmin())
/*     */     {
/* 241 */       allAttributes = this.m_assetManager.getAssetAttributes(a_dbTransaction, null);
/*     */     }
/*     */     else
/*     */     {
/* 246 */       allAttributes = this.m_assetManager.getAssetAttributes(a_dbTransaction, userProfile.getWriteableAttributeIds());
/*     */     }
/*     */ 
/* 249 */     AttributeUtil.populateAttributeValuesFromRequest(a_request, form, form.getAsset().getAttributeValues(), allAttributes, bDateError, false, false, a_dbTransaction, this.m_taxonomyManager, this.m_listManager, userProfile.getCurrentLanguage(), false);
/*     */ 
/* 261 */     Asset metaData = form.getAsset();
/*     */ 
/* 265 */     if ((hmAttributesToReplace.size() == 0) && (hmAttributesToAppend.size() == 0) && (hmAttributesToPrepend.size() == 0) && (hmAttributesToRemove.size() == 0) && (!StringUtil.stringIsPopulated(form.getAllCategoryIds())) && (form.getRotateImagesAngle() == 0) && (!bDeleteAssets) && (!form.getUnrelateAssets()))
/*     */     {
/* 271 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoAttributes", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 275 */     if ((form.getSubstituteFile() != null) && (hmAttributesToReplace.containsKey("substituteFile")) && (form.getSubstituteFile().getFileName() != null) && (form.getSubstituteFile().getFileName().length() > 0))
/*     */     {
/* 280 */       boolean bImageFormat = false;
/* 281 */       String sExt = FileUtil.getSuffix(form.getSubstituteFile().getFileName());
/*     */ 
/* 284 */       if (sExt != null)
/*     */       {
/* 286 */         FileFormat thumbFileFormat = this.m_assetManager.getFileFormatForExtension(null, sExt);
/* 287 */         if ((thumbFileFormat.getIsConvertable()) && (thumbFileFormat.getAssetTypeId() == 2L))
/*     */         {
/* 290 */           bImageFormat = true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 295 */       if (!bImageFormat)
/*     */       {
/* 297 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "thumbnailFileNotImage", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 302 */     if (!form.getHasErrors())
/*     */     {
/* 305 */       WorkflowUpdate update = new WorkflowUpdate(userProfile.getUser().getId(), userProfile.getSessionId());
/* 306 */       update.setUpdateType(3);
/* 307 */       if (hmAttributesToReplace.containsKey("approved"))
/*     */       {
/* 309 */         update.setSetSubmitted(true);
/* 310 */         int iSelectedOption = form.getSelectedSubmitOption();
/* 311 */         if (StringUtil.stringIsPopulated(form.getSelectedWorkflow()))
/*     */         {
/* 313 */           update.setUpdateType(4);
/* 314 */           HashMap hmApprovalChanges = new HashMap();
/* 315 */           hmApprovalChanges.put(form.getSelectedWorkflow(), Integer.valueOf(iSelectedOption));
/* 316 */           update.setWorkflowApprovalUpdates(hmApprovalChanges);
/*     */         }
/*     */         else
/*     */         {
/* 320 */           switch (iSelectedOption)
/*     */           {
/*     */           case 0:
/* 323 */             update.setUpdateType(1);
/* 324 */             break;
/*     */           case 1:
/* 327 */             update.setUpdateType(2);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 335 */       info.setUser((ABUser)userProfile.getUser());
/* 336 */       info.setAssetMetadata(metaData);
/* 337 */       info.setAssetAttributes(allAttributes);
/* 338 */       info.setBatchUpdate(controller.getBatchUpdate());
/* 339 */       info.setAttributesToReplace(hmAttributesToReplace);
/* 340 */       info.setAttributesToAppend(hmAttributesToAppend);
/* 341 */       info.setAttributesToPrepend(hmAttributesToPrepend);
/* 342 */       info.setAttributesToRemove(hmAttributesToRemove);
/* 343 */       info.setAttributeDelimiters(hmAttributeDelimiters);
/* 344 */       info.setRotateImagesAngle(form.getRotateImagesAngle());
/* 345 */       info.setUnrelateAssets(form.getUnrelateAssets());
/* 346 */       info.setSessionId(userProfile.getSessionId());
/* 347 */       info.setDeleteAssets(bDeleteAssets);
/* 348 */       info.setUserProfile(userProfile);
/* 349 */       info.setWorkflowUpdate(update);
/* 350 */       info.setSubstituteFile(form.getSubstituteFile());
/*     */ 
/* 352 */       String sUpdateAgreements = a_request.getParameter("update_agreements");
/* 353 */       if ((sUpdateAgreements != null) && (sUpdateAgreements.equals("replace")))
/*     */       {
/* 355 */         info.setUpdateAgreement(true);
/*     */       }
/*     */ 
/* 358 */       controller.setDelete(bDeleteAssets);
/*     */ 
/* 361 */       this.m_updateManager.queueBulkUpdate(info);
/*     */ 
/* 363 */       String sParam = "";
/*     */ 
/* 366 */       afForward = createRedirectingForward(sParam, a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 374 */       form.setAssetAttributes(allAttributes);
/* 375 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 378 */     this.m_logger.debug("successfully completed StartImportAction action now returning forward...");
/* 379 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.BulkUpdateStartAction
 * JD-Core Version:    0.6.0
 */