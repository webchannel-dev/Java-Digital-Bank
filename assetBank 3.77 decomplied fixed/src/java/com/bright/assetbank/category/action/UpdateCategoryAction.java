/*     */ package com.bright.assetbank.category.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*     */ import com.bright.assetbank.category.form.ABCategoryForm;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.usage.bean.ColorSpace;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.CategoryForm;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class UpdateCategoryAction extends BTransactionAction
/*     */   implements AssetBankConstants, CategoryConstants, MessageConstants
/*     */ {
/*  66 */   protected AssetCategoryManager m_categoryManager = null;
/*     */ 
/*  72 */   protected FileStoreManager m_fileStoreManager = null;
/*     */ 
/*  78 */   protected ListManager m_listManager = null;
/*     */ 
/*  84 */   protected UsageManager m_usageManager = null;
/*     */ 
/*     */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*     */   {
/*  69 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_sFileStoreManager)
/*     */   {
/*  75 */     this.m_fileStoreManager = a_sFileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  81 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_sUsageManager)
/*     */   {
/*  87 */     this.m_usageManager = a_sUsageManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 110 */     ActionForward afForward = null;
/* 111 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 112 */     ABCategoryForm form = (ABCategoryForm)a_form;
/*     */ 
/* 114 */     long lCatId = getLongParameter(a_request, "categoryId");
/* 115 */     long lParentId = getLongParameter(a_request, "parentId");
/*     */ 
/* 118 */     String sQueryString = "";
/* 119 */     String sParentQueryString = "";
/* 120 */     if (lCatId > 0L)
/*     */     {
/* 122 */       sQueryString = "categoryId=" + lCatId;
/*     */     }
/* 124 */     if (lParentId > 0L)
/*     */     {
/* 126 */       sParentQueryString = "categoryId=" + lParentId;
/*     */     }
/*     */ 
/* 130 */     if (a_request.getParameter("cancel") != null)
/*     */     {
/* 132 */       afForward = createRedirectingForward(sParentQueryString, a_mapping, "Success");
/* 133 */       return afForward;
/*     */     }
/*     */ 
/* 137 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 139 */       this.m_logger.error("UpdateCategoryAction.execute : User does not have admin permission : " + userProfile);
/* 140 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 144 */     int iAdvanced = getIntParameter(a_request, "allowAdvancedOptions");
/* 145 */     if (iAdvanced > 0)
/*     */     {
/* 147 */       form.getCategory().setAllowAdvancedOptions(true);
/*     */     }
/*     */     else
/*     */     {
/* 151 */       form.getCategory().setAllowAdvancedOptions(false);
/*     */     }
/*     */ 
/* 155 */     Category cat = form.getCategory();
/* 156 */     cat.setId(lCatId);
/* 157 */     cat.setIsBrowsable(form.isBrowsable());
/* 158 */     boolean bValid = CategoryUtil.validateCategory(cat.getName(), form, this.m_listManager, userProfile, a_dbTransaction);
/*     */ 
/* 163 */     if (bValid)
/*     */     {
/* 165 */       if (!this.m_categoryManager.updateCategoryDetails(a_dbTransaction, 1L, cat))
/*     */       {
/* 167 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "categoryErrorDuplicateName", userProfile.getCurrentLanguage()).getBody());
/* 168 */         bValid = false;
/*     */       }
/*     */ 
/* 171 */       if (bValid)
/*     */       {
/* 173 */         bValid = updateCategoryImage(form, a_dbTransaction, userProfile);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 178 */     if (!bValid)
/*     */     {
/* 180 */       afForward = createForward(sQueryString, a_mapping, "Failure");
/*     */     }
/*     */     else
/*     */     {
/* 184 */       afForward = createRedirectingForward(sParentQueryString, a_mapping, "Success");
/*     */     }
/*     */ 
/* 187 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected boolean updateCategoryImage(CategoryForm form, DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 201 */     boolean bValid = true;
/* 202 */     Category cat = form.getCategory();
/*     */ 
/* 205 */     if (form.getRemoveImage())
/*     */     {
/* 207 */       this.m_categoryManager.removeCategoryImage(cat.getCategoryTypeId(), cat.getId());
/*     */     }
/*     */ 
/* 211 */     if ((form.getImageFile() != null) && (form.getImageFile().getFileSize() > 0))
/*     */     {
/* 214 */       InputStream ins = null;
/* 215 */       String sTempUrl = null;
/*     */ 
/* 218 */       String sRgbColorProfile = this.m_usageManager.getColorSpace(null, 1).getFileLocation();
/* 219 */       String sCmykColorProfile = this.m_usageManager.getColorSpace(null, 2).getFileLocation();
/*     */       try
/*     */       {
/* 223 */         ins = form.getImageFile().getInputStream();
/* 224 */         sTempUrl = this.m_fileStoreManager.addFile(ins, form.getImageFile().getFileName(), StoredFileType.TEMP);
/*     */ 
/* 229 */         this.m_categoryManager.setCategoryImage(cat, this.m_fileStoreManager.getAbsolutePath(sTempUrl), sRgbColorProfile, sCmykColorProfile);
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/* 233 */         throw new Bn2Exception("UpdateCategoryAction: " + ioe.getMessage(), ioe);
/*     */       }
/*     */       catch (Bn2Exception bn2e)
/*     */       {
/* 237 */         this.m_logger.error("UpdateCategoryAction.updateCategoryImage() : Exception whilst storing image for category", bn2e);
/* 238 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "imageFileError", a_userProfile.getCurrentLanguage()).getBody());
/* 239 */         bValid = false;
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 245 */           ins.close();
/*     */         }
/*     */         catch (IOException ioe)
/*     */         {
/*     */         }
/*     */ 
/* 253 */         if (sTempUrl != null)
/*     */         {
/* 255 */           this.m_fileStoreManager.deleteFile(sTempUrl);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 260 */     return bValid;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.UpdateCategoryAction
 * JD-Core Version:    0.6.0
 */