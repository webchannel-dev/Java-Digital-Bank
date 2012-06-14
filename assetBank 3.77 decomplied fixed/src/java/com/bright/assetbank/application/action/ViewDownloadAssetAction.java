/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.service.ImageAssetManagerImpl;
/*     */ import com.bright.assetbank.approval.bean.AssetApproval;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxDownloadForm;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.cmsintegration.bean.CmsInfo;
/*     */ import com.bright.assetbank.ecommerce.service.OrderManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.usage.bean.AssetUse;
/*     */ import com.bright.assetbank.usage.bean.UsageType;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeSelectOption;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.SelectOption;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewDownloadAssetAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*     */   private static final String k_sParamName_Advanced = "advanced";
/*  94 */   protected IAssetManager m_assetManager = null;
/*     */ 
/* 100 */   protected UsageManager m_usageManager = null;
/*     */ 
/* 107 */   private OrderManager m_orderManager = null;
/*     */ 
/* 113 */   protected AgreementsManager m_agreementsManager = null;
/*     */ 
/* 119 */   protected AssetApprovalManager m_approvalManager = null;
/*     */ 
/* 125 */   protected ImageAssetManagerImpl m_imageAssetManager = null;
/*     */ 
/*     */   protected long getAssetTypeId()
/*     */   {
/*  91 */     return 1L;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_assetManager)
/*     */   {
/*  97 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_usageManager)
/*     */   {
/* 103 */     this.m_usageManager = a_usageManager;
/*     */   }
/*     */ 
/*     */   public void setOrderManager(OrderManager a_manager)
/*     */   {
/* 110 */     this.m_orderManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*     */   {
/* 116 */     this.m_agreementsManager = a_agreementsManager;
/*     */   }
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/* 122 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setImageAssetManager(ImageAssetManagerImpl a_sImageAssetManager)
/*     */   {
/* 128 */     this.m_imageAssetManager = a_sImageAssetManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 149 */     ActionForward afForward = null;
/* 150 */     DownloadForm form = (DownloadForm)a_form;
/* 151 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 152 */     long lUserId = 0L;
/* 153 */     if (userProfile.getUser() != null)
/*     */     {
/* 155 */       lUserId = userProfile.getUser().getId();
/*     */     }
/*     */ 
/* 159 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/* 162 */     if (lAssetId <= 0L)
/*     */     {
/* 164 */       lAssetId = form.getAsset().getId();
/*     */     }
/*     */ 
/* 168 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, userProfile.getVisibleAttributeIds(), false, userProfile.getCurrentLanguage(), false);
/*     */ 
/* 171 */     List values = new ArrayList();
/* 172 */     Set downloadAtts = new HashSet();
/* 173 */     for (AttributeValue attVal : asset.getAttributeValues())
/*     */     {
/* 175 */       if ((attVal.getAttribute().getShowOnDownload()) && (!StringUtils.isEmpty(attVal.getValue())) && (!downloadAtts.contains(attVal.getAttribute())))
/*     */       {
/* 179 */         values.add(attVal);
/* 180 */         downloadAtts.add(attVal.getAttribute());
/*     */       }
/*     */     }
/* 183 */     form.setDownloadAttributes(values);
/*     */ 
/* 186 */     if (asset.getAgreementTypeId() == 2L)
/*     */     {
/* 188 */       long lAssetIdForAgreement = asset.getId();
/*     */ 
/* 191 */       if ((asset.getEntity().getId() > 0L) && (StringUtils.isNotEmpty(asset.getParentAssetIdsAsString())) && (asset.getAgreementTypeId() == 2L) && (!asset.getEntity().getIncludesAttribute(400L)))
/*     */       {
/* 196 */         lAssetIdForAgreement = StringUtil.getIdsArray(asset.getParentAssetIdsAsString())[0];
/*     */       }
/*     */ 
/* 199 */       asset.setAgreement(this.m_agreementsManager.getCurrentAgreementForAsset(a_dbTransaction, lAssetIdForAgreement));
/*     */     }
/*     */ 
/* 203 */     if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanDownloadAssetNow(userProfile, asset)))
/*     */     {
/* 205 */       this.m_logger.debug("This user does not have permission to download asset id=" + lAssetId);
/*     */ 
/* 207 */       form.setUserDoesNotHavePermission(true);
/*     */ 
/* 212 */       String sQueryString = "id=" + lAssetId + "&" + "noDownloadPermission" + "=true";
/*     */ 
/* 215 */       return createRedirectingForward(sQueryString, a_mapping, "NoDownloadPermission");
/*     */     }
/*     */ 
/* 220 */     form.setAsset(asset);
/*     */ 
/* 223 */     if ((!userProfile.getIsAdmin()) && (userProfile.getUser() != null) && (userProfile.getUser().getId() > 0L))
/*     */     {
/* 225 */       AssetApproval app = this.m_approvalManager.getAssetApproval(a_dbTransaction, asset.getId(), userProfile.getUser().getId());
/* 226 */       if (StringUtil.stringIsPopulated(app.getAdminNotes()))
/*     */       {
/* 228 */         form.setAssetApproval(app);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 233 */     boolean bAdvanced = Boolean.parseBoolean(a_request.getParameter("advanced"));
/* 234 */     form.setAdvanced(bAdvanced);
/*     */ 
/* 238 */     if ((!this.m_assetManager.userCanDownloadAsset(userProfile, asset)) && (this.m_assetManager.userCanDownloadWithApprovalAsset(userProfile, asset)))
/*     */     {
/* 240 */       setUsageSelectorInfo(a_dbTransaction, form, userProfile.getUsageExclusions(), lAssetId, userProfile);
/*     */     }
/*     */     else
/*     */     {
/* 244 */       setUsageSelectorInfo(a_dbTransaction, form, userProfile.getUsageExclusions(), null, userProfile);
/*     */     }
/*     */ 
/* 248 */     if ((userProfile.getCmsInfo() != null) && (StringUtil.stringIsPopulated(userProfile.getCmsInfo().getCurrentLocation())))
/*     */     {
/* 250 */       form.getAssetUse().setUsageOther("CMS: " + userProfile.getCmsInfo().getCurrentLocation());
/* 251 */       form.getAssetUse().setEditable(false);
/*     */     }
/*     */ 
/* 256 */     boolean bCanDownloadThroughOrder = this.m_orderManager.getUserCanDownloadOriginal(a_dbTransaction, lUserId, lAssetId);
/*     */ 
/* 258 */     if ((this.m_assetManager.userCanDownloadOriginal(userProfile, asset)) || (bCanDownloadThroughOrder))
/*     */     {
/* 260 */       form.setUserCanDownloadOriginal(true);
/*     */     }
/* 262 */     if ((this.m_assetManager.userCanDownloadAdvanced(userProfile, asset)) || (bCanDownloadThroughOrder))
/*     */     {
/* 264 */       form.setUserCanDownloadAdvanced(true);
/*     */     }
/*     */ 
/* 268 */     Vector vec = this.m_orderManager.getSuccessfulCommercialOrders(a_dbTransaction, lAssetId, lUserId);
/* 269 */     if ((vec != null) && (vec.size() > 0))
/*     */     {
/* 271 */       form.setIsCommercialPurchase(true);
/*     */     }
/*     */ 
/* 275 */     if (userProfile.getIsLoggedIn())
/*     */     {
/* 277 */       form.setUserMustRequestApprovalForHighRes(this.m_assetManager.userMustRequestApprovalForHighRes(userProfile, asset));
/* 278 */       form.setUserHasApprovalForHighRes(this.m_assetManager.userApprovedForHighResAsset(userProfile, asset));
/*     */ 
/* 281 */       if (form.getUserMustRequestApprovalForHighRes())
/*     */       {
/* 283 */         form.setApprovalIsPending(this.m_assetManager.userApprovalIsPending(userProfile, asset));
/*     */       }
/*     */     }
/*     */ 
/* 287 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 289 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void setUsageSelectorInfo(DBTransaction a_dbTransaction, DownloadForm form, Vector a_vecExclusions, long a_lAssetId, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 307 */     Vector vecAssets = new Vector();
/* 308 */     vecAssets.add(new Long(a_lAssetId));
/* 309 */     setUsageSelectorInfo(a_dbTransaction, form, a_vecExclusions, vecAssets, a_userProfile);
/*     */   }
/*     */ 
/*     */   protected void setUsageSelectorInfo(DBTransaction a_dbTransaction, DownloadForm form, Vector a_vecExclusions, Vector a_assets, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 333 */     boolean bImagesConsideredLowRes = false;
/*     */ 
/* 341 */     long lUsageTypeId = form.getSelectedUsageType().getId();
/*     */ 
/* 343 */     if (lUsageTypeId <= 0L)
/*     */     {
/* 345 */       lUsageTypeId = form.getAssetUse().getUsageTypeId();
/*     */     }
/*     */ 
/* 348 */     long lUserId = 0L;
/* 349 */     if (a_userProfile.getUser() != null)
/*     */     {
/* 351 */       lUserId = a_userProfile.getUser().getId();
/*     */     }
/*     */ 
/* 355 */     if (AssetBankSettings.getImageSizeConsideredLowRes() > 0)
/*     */     {
/* 358 */       if (form.getAsset().getTypeId() == 2L)
/*     */       {
/* 360 */         ImageAsset image = (ImageAsset)form.getAsset();
/*     */ 
/* 362 */         if ((image.getHeight() * image.getWidth() != 0) && (image.getHeight() * image.getWidth() <= AssetBankSettings.getImageSizeConsideredLowRes()))
/*     */         {
/* 365 */           bImagesConsideredLowRes = true;
/*     */         }
/*     */ 
/*     */       }
/* 369 */       else if ((form.getAsset() == null) || (form.getAsset().getId() < 1L))
/*     */       {
/* 371 */         AssetBoxDownloadForm assetBoxDownloadForm = (AssetBoxDownloadForm)form;
/*     */ 
/* 373 */         Iterator itAssets = assetBoxDownloadForm.getAssets().iterator();
/*     */ 
/* 375 */         while (itAssets.hasNext())
/*     */         {
/* 377 */           AssetInList asset = (AssetInList)itAssets.next();
/*     */ 
/* 379 */           if (asset.getAsset().getTypeId() == 2L)
/*     */           {
/* 381 */             ImageAsset image = (ImageAsset)asset.getAsset();
/*     */ 
/* 383 */             if ((image.getHeight() * image.getWidth() != 0) && (image.getHeight() * image.getWidth() < AssetBankSettings.getImageSizeConsideredLowRes()))
/*     */             {
/* 386 */               bImagesConsideredLowRes = true;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 395 */     long lAssetTypeId = getAssetTypeId();
/*     */ 
/* 397 */     UsageTypeSelectOption selectedUsage = new UsageTypeSelectOption();
/* 398 */     Vector vecUsageTypeLists = this.m_usageManager.getUsageTypeLists(a_dbTransaction, lUsageTypeId, selectedUsage, a_vecExclusions, lUserId, a_assets, form.getAdvanced(), lAssetTypeId, a_userProfile.getCurrentLanguage(), bImagesConsideredLowRes);
/*     */ 
/* 410 */     if (AssetBankSettings.getCanSelectMultipleUsageTypes())
/*     */     {
/* 412 */       ArrayList vecSecondaryUsageTypes = this.m_usageManager.getUsageTypesTree(a_dbTransaction, lAssetTypeId, form.getSecondaryUsageTypeIds());
/* 413 */       form.setSecondaryUsageTypes(vecSecondaryUsageTypes);
/*     */     }
/*     */ 
/* 416 */     form.setUsageTypeLists(vecUsageTypeLists);
/* 417 */     form.setSelectedUsageType(selectedUsage);
/*     */ 
/* 420 */     boolean bUsageListIsFlat = this.m_usageManager.getListIsFlat(a_dbTransaction, lAssetTypeId);
/* 421 */     form.setUsageListIsFlat(bUsageListIsFlat);
/*     */ 
/* 424 */     List topLevel = (List)vecUsageTypeLists.firstElement();
/* 425 */     if ((selectedUsage.getId() <= 0L) && (topLevel != null) && (topLevel.size() == 1))
/*     */     {
/* 427 */       lUsageTypeId = ((SelectOption)topLevel.get(0)).getId();
/* 428 */       vecUsageTypeLists = this.m_usageManager.getUsageTypeLists(a_dbTransaction, lUsageTypeId, selectedUsage, a_vecExclusions, lUserId, a_assets, form.getAdvanced(), lAssetTypeId, a_userProfile.getCurrentLanguage(), bImagesConsideredLowRes);
/*     */ 
/* 438 */       form.setUsageTypeLists(vecUsageTypeLists);
/* 439 */       form.setSelectedUsageType(selectedUsage);
/*     */     }
/*     */ 
/* 442 */     form.getAssetUse().setUsageTypeId(selectedUsage.getId());
/*     */ 
/* 445 */     if ((form.getUsageTypeLists() != null) && (form.getUsageTypeLists().size() > 0))
/*     */     {
/* 448 */       Vector vecLast = (Vector)form.getUsageTypeLists().get(form.getUsageTypeLists().size() - 1);
/*     */ 
/* 451 */       if ((vecLast != null) && (vecLast.size() == 1))
/*     */       {
/* 453 */         UsageTypeSelectOption usageTypeOption = (UsageTypeSelectOption)vecLast.get(0);
/* 454 */         form.getAssetUse().setUsageTypeId(usageTypeOption.getId());
/*     */ 
/* 456 */         form.getSelectedUsageType().setLeaf(true);
/* 457 */         form.getSelectedUsageType().setId(usageTypeOption.getId());
/* 458 */         form.getSelectedUsageType().setName(usageTypeOption.getName());
/* 459 */         form.getSelectedUsageType().setUsageType(usageTypeOption.getUsageType());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 464 */     boolean bValidateUsage = (AssetBankSettings.getShowUseDropdownBeforeDownload()) && (form.getUsageTypesAvailable());
/* 465 */     form.setValidateUsageType(bValidateUsage);
/*     */ 
/* 468 */     boolean bValidateUsageDescription = (bValidateUsage) && (form.getSelectedUsageType().getUsageType().getDetailsMandatory());
/* 469 */     form.setValidateUsageDescription(bValidateUsageDescription);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewDownloadAssetAction
 * JD-Core Version:    0.6.0
 */