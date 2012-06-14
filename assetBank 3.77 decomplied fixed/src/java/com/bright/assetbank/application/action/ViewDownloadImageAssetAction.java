/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.ImageAssetManagerImpl;
/*     */ import com.bright.assetbank.application.util.DownloadUtil;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxDownloadForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.usage.bean.AssetUse;
/*     */ import com.bright.assetbank.usage.bean.NamedColour;
/*     */ import com.bright.assetbank.usage.bean.UsageType;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeSelectOption;
/*     */ import com.bright.assetbank.usage.service.MaskManager;
/*     */ import com.bright.assetbank.usage.service.NamedColourManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collection;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewDownloadImageAssetAction extends ViewDownloadAssetAction
/*     */ {
/*  65 */   protected AttributeManager m_attributeManager = null;
/*     */   private MaskManager m_maskManager;
/*     */   private NamedColourManager m_namedColourManager;
/*     */ 
/*     */   protected long getAssetTypeId()
/*     */   {
/*  72 */     return 2L;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  94 */     ActionForward afForward = null;
/*  95 */     DownloadForm form = (DownloadForm)a_form;
/*  96 */     ImageAsset asset = null;
/*  97 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 100 */     afForward = super.execute(a_mapping, form, a_request, a_response, a_dbTransaction);
/*     */ 
/* 103 */     if (!form.getUserDoesNotHavePermission())
/*     */     {
/* 106 */       if (a_request.getParameter("repurposeAsset") != null)
/*     */       {
/* 108 */         form.setRepurpose(Boolean.parseBoolean(a_request.getParameter("repurposeAsset")));
/*     */       }
/*     */ 
/* 112 */       if ((!form.getUsageTypesAvailable()) && (!form.getAdvanced()) && (form.getUserCanDownloadAdvanced()))
/*     */       {
/* 114 */         return createRedirectingForward("id=" + form.getAsset().getId() + "&advanced=true&showTabs=false", a_mapping, "Failure");
/*     */       }
/*     */ 
/* 118 */       if ((form.getAsset() != null) && ((form.getAsset() instanceof ImageAsset)))
/*     */       {
/* 120 */         asset = (ImageAsset)form.getAsset();
/*     */ 
/* 123 */         if ((form.getHeight() <= 0) || (form.getWidth() <= 0))
/*     */         {
/* 126 */           form.setHeight(asset.getHeight());
/* 127 */           form.setWidth(asset.getWidth());
/*     */ 
/* 129 */           form.setJpegQuality((int)(AssetBankSettings.getJpgConversionQuality() * 100.0F));
/* 130 */           form.setDensity(AssetBankSettings.getDefaultDensity());
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 136 */         this.m_logger.error("ViewDownloadImageAssetAction.execute() : The asset in the form is not a valid ImageAsset");
/*     */       }
/*     */ 
/* 141 */       if (asset.getFormat().getId() > 0L)
/*     */       {
/* 143 */         form.setDownloadAsDocument(DownloadUtil.treatFileAsDocumentForDownload(asset.getFormat().getFileExtension()));
/*     */       }
/*     */ 
/* 147 */       setUsageFormats(a_dbTransaction, form, userProfile);
/*     */ 
/* 150 */       form.setColorSpaces(this.m_usageManager.getColorSpaces(a_dbTransaction, asset.getColorSpace(), true));
/*     */ 
/* 153 */       form.setTints(this.m_imageAssetManager.getTints());
/*     */ 
/* 156 */       Vector vecMappings = this.m_attributeManager.getEmbeddedDataMappings(a_dbTransaction, -1L, 2L);
/* 157 */       if ((vecMappings != null) && (!vecMappings.isEmpty()))
/*     */       {
/* 159 */         form.setAssetHasDownloadMetadata(true);
/*     */       }
/*     */ 
/* 163 */       UsageTypeFormat selectedUsageTypeFormat = form.getSelectedUsageTypeFormat();
/* 164 */       if ((selectedUsageTypeFormat != null) && (selectedUsageTypeFormat.getPresetMaskColourId() > 0L))
/*     */       {
/* 167 */         NamedColour presetMaskColour = getNamedColourManager().getColourById(a_dbTransaction, selectedUsageTypeFormat.getPresetMaskColourId());
/*     */ 
/* 170 */         a_request.setAttribute("presetMaskColour", presetMaskColour);
/*     */       }
/*     */ 
/* 175 */       boolean allowMasking = ((selectedUsageTypeFormat == null) || (selectedUsageTypeFormat.getAllowMasking())) && (getMaskManager().haveMasks(a_dbTransaction));
/*     */ 
/* 177 */       a_request.setAttribute("allowMasking", Boolean.valueOf(allowMasking));
/*     */     }
/*     */ 
/* 181 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected void setUsageFormats(DBTransaction a_dbTransaction, DownloadForm form, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 201 */     long lUsageTypeId = form.getAssetUse().getUsageTypeId();
/*     */ 
/* 203 */     int iHeight = form.getHeight();
/* 204 */     int iWidth = form.getWidth();
/* 205 */     if ((form instanceof AssetBoxDownloadForm))
/*     */     {
/* 207 */       AssetBoxDownloadForm abdForm = (AssetBoxDownloadForm)form;
/* 208 */       ImageAsset tempAsset = getSmallestResImage(abdForm);
/* 209 */       if (tempAsset != null)
/*     */       {
/* 211 */         iHeight = tempAsset.getHeight();
/* 212 */         iWidth = tempAsset.getWidth();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 217 */     Vector<UsageTypeFormat> usageTypeFormats = this.m_usageManager.getUsageTypeFormats(a_dbTransaction, lUsageTypeId, a_userProfile.getCurrentLanguage(), iHeight, iWidth);
/*     */ 
/* 220 */     if ((usageTypeFormats.size() <= 0) && ((form.getSelectedUsageType() == null) || (!form.getSelectedUsageType().getUsageType().getDownloadOriginal())))
/*     */     {
/* 224 */       usageTypeFormats = this.m_usageManager.getUsageTypeFormats(a_dbTransaction, -1L, a_userProfile.getCurrentLanguage(), form.getHeight(), form.getWidth());
/*     */     }
/*     */ 
/* 231 */     long usageTypeFormatId = form.getUsageTypeFormatId();
/* 232 */     if (usageTypeFormatId > 0L)
/*     */     {
/* 234 */       boolean found = false;
/* 235 */       for (UsageTypeFormat usageTypeFormat : usageTypeFormats)
/*     */       {
/* 237 */         if (usageTypeFormat.getId() == usageTypeFormatId)
/*     */         {
/* 239 */           found = true;
/* 240 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 244 */       if (!found)
/*     */       {
/* 246 */         if (usageTypeFormats.size() == 0)
/*     */         {
/* 248 */           form.setUsageTypeFormatId(0L);
/*     */         }
/*     */         else
/*     */         {
/* 252 */           form.setUsageTypeFormatId(((UsageTypeFormat)usageTypeFormats.get(0)).getId());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 257 */     form.setUsageTypeFormats(usageTypeFormats);
/*     */   }
/*     */ 
/*     */   private ImageAsset getSmallestResImage(AssetBoxDownloadForm a_form)
/*     */   {
/* 264 */     long lSmallestRes = 9223372036854775807L;
/* 265 */     ImageAsset ret = null;
/* 266 */     if ((a_form.getAssets() != null) && (a_form.getAssets().size() > 0))
/*     */     {
/* 268 */       for (AssetInList assetInList : (Vector<AssetInList>)a_form.getAssets())
/*     */       {
/* 270 */         if ((assetInList.getAsset() != null) && ((assetInList.getAsset() instanceof ImageAsset)))
/*     */         {
/* 273 */           ImageAsset ia = (ImageAsset)assetInList.getAsset();
/* 274 */           long lRes = ia.getHeight() * ia.getWidth();
/* 275 */           if (lRes < lSmallestRes)
/*     */           {
/* 277 */             lSmallestRes = lRes;
/* 278 */             ret = ia;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 283 */     return ret;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 289 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public MaskManager getMaskManager()
/*     */   {
/* 294 */     return this.m_maskManager;
/*     */   }
/*     */ 
/*     */   public void setMaskManager(MaskManager a_maskManager)
/*     */   {
/* 299 */     this.m_maskManager = a_maskManager;
/*     */   }
/*     */ 
/*     */   public NamedColourManager getNamedColourManager()
/*     */   {
/* 304 */     return this.m_namedColourManager;
/*     */   }
/*     */ 
/*     */   public void setNamedColourManager(NamedColourManager a_namedColourManager)
/*     */   {
/* 309 */     this.m_namedColourManager = a_namedColourManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewDownloadImageAssetAction
 * JD-Core Version:    0.6.0
 */