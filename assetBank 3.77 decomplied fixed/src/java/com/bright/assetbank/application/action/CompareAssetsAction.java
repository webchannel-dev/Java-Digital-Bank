/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.PrintImageForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.service.ImageAssetManagerImpl;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CompareAssetsAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants, AttributeConstants
/*     */ {
/*  65 */   protected IAssetManager m_assetManager = null;
/*  66 */   protected FileStoreManager m_fileStoreManager = null;
/*  67 */   protected ImageAssetManagerImpl m_imageAssetManager = null;
/*  68 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     ActionForward afForward = null;
/*  78 */     PrintImageForm form = (PrintImageForm)a_form;
/*  79 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  81 */     Vector vecAssetsForComparison = new Vector();
/*     */ 
/*  83 */     Vector vecAttributeIds = new Vector();
/*  84 */     Vector<Asset> vecAssets = new Vector();
/*     */ 
/*  87 */     if ((form.getAssetsPerPage() <= 4) && (a_request.getParameter("attributesSelected") != null))
/*     */     {
/*  90 */       form.setAttributesSelected(true);
/*  91 */       vecAttributeIds = AttributeUtil.getControlPanelSelectedAttributes(a_request);
/*     */     }
/*  93 */     else if (form.getAssetsPerPage() > 4)
/*     */     {
/*  95 */       Attribute idAttribute = this.m_attributeManager.getStaticAttribute("assetId");
/*  96 */       String sValue = a_request.getParameter("requiredAttribute_" + idAttribute.getId());
/*  97 */       if ((sValue != null) && (sValue.length() > 0))
/*     */       {
/*  99 */         vecAttributeIds.add(new Long(idAttribute.getId()));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     if (a_request.getParameter("attributesSelected") == null)
/*     */     {
/* 107 */       Vector<Attribute> vecAttributes = this.m_attributeManager.getAttributes(a_dbTransaction, -1L);
/* 108 */       for (Attribute attribute : vecAttributes)
/*     */       {
/* 110 */         if (attribute.getIsNameAttribute())
/*     */         {
/* 112 */           vecAttributeIds.add(new Long(attribute.getId()));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 118 */     if (form.getAssetsPerPage() < 1)
/*     */     {
/* 120 */       form.setAssetsPerPage(4);
/*     */     }
/*     */ 
/* 124 */     AssetBox  assetBox = userProfile.getAssetBox();
/* 125 */     Collection<AssetInList> colAssetBox = assetBox.getAssets();
/*     */ 
/* 128 */     for (AssetInList currentAsset : colAssetBox)
/*     */     {
/* 131 */       if (currentAsset.getIsSelected())
/*     */       {
/* 133 */         long lAssetId = currentAsset.getId();
/*     */ 
/* 135 */         Asset asset = this.m_assetManager.getAsset(null, lAssetId, vecAttributeIds, false, true);
/* 136 */         vecAssets.add(asset);
/*     */ 
/* 139 */         if (asset.getTypeId() == 2L)
/*     */         {
/* 141 */           ImageAsset image = (ImageAsset)asset;
/*     */ 
/* 144 */           if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanViewAsset(userProfile, image)))
/*     */           {
/* 146 */             this.m_logger.debug("This user does not have permission to view image id=" + image.getId());
/* 147 */             return a_mapping.findForward("NoPermission");
/*     */           }
/*     */ 
/* 151 */           if (form.getAssetsPerPage() <= 4)
/*     */           {
/* 153 */             if (image.getLargeImageFile() == null)
/*     */             {
/* 155 */               String sFile = this.m_assetManager.getTemporaryLargeFile((ImageAsset)asset, AssetBankSettings.getLargeImageSize(), -1, false);
/* 156 */               ImageFile file = new ImageFile();
/* 157 */               file.setPath(sFile);
/* 158 */               image.setComparisonImageFile(file);
/*     */             }
/*     */             else
/*     */             {
/* 162 */               image.setComparisonImageFile(image.getLargeImageFile());
/*     */             }
/*     */ 
/* 165 */             vecAssetsForComparison.add(image);
/*     */           }
/* 167 */           else if ((form.getAssetsPerPage() > 4) && (form.getAssetsPerPage() <= 16) && (image.getPreviewImageFile() != null))
/*     */           {
/* 171 */             image.setComparisonImageFile(image.getPreviewImageFile());
/* 172 */             vecAssetsForComparison.add(image);
/*     */           }
/* 174 */           else if ((form.getAssetsPerPage() > 16) && (image.getThumbnailImageFile() != null))
/*     */           {
/* 177 */             image.setComparisonImageFile(image.getThumbnailImageFile());
/* 178 */             vecAssetsForComparison.add(image);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 185 */     form.setAssets(vecAssetsForComparison);
/*     */ 
/* 188 */     Vector<Attribute> vecAttributes = new Vector();
/* 189 */     Vector vecVisableAttributes = userProfile.getVisibleAttributeIds();
/*     */ 
/* 191 */     if (vecVisableAttributes == null)
/*     */     {
/* 194 */       vecAttributes = this.m_attributeManager.getAttributes(a_dbTransaction, -1L);
/*     */ 
/* 196 */       for (Attribute attribute : vecAttributes)
/*     */       {
/* 198 */         attribute.setLanguage(userProfile.getCurrentLanguage());
/*     */ 
/* 200 */         if ((a_request.getParameter("attributesSelected") != null) && (vecAttributeIds.contains(Long.valueOf(attribute.getId()))))
/*     */         {
/* 203 */           attribute.setSelected(true);
/*     */         }
/*     */ 
/* 207 */         if ((a_request.getParameter("attributesSelected") == null) && (attribute.getIsNameAttribute()))
/*     */         {
/* 210 */           attribute.setSelected(true);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 217 */       Iterator itAttributes = vecVisableAttributes.iterator();
/*     */ 
/* 219 */       while (itAttributes.hasNext())
/*     */       {
/* 221 */         Long lAttributeId = (Long)itAttributes.next();
/*     */ 
/* 223 */         Attribute attribute = this.m_attributeManager.getAttribute(a_dbTransaction, lAttributeId.longValue());
/* 224 */         attribute.setLanguage(userProfile.getCurrentLanguage());
/*     */ 
/* 227 */         if (a_request.getParameter("attributesSelected") != null)
/*     */         {
/* 229 */           if (vecAttributeIds.contains(lAttributeId))
/*     */           {
/* 231 */             attribute.setSelected(true);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 236 */         if ((a_request.getParameter("attributesSelected") == null) && (attribute.getIsNameAttribute()))
/*     */         {
/* 239 */           attribute.setSelected(true);
/*     */         }
/*     */ 
/* 242 */         vecAttributes.add(attribute);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 248 */     for (Iterator i$ = vecAttributes.iterator(); i$.hasNext(); ) { Attribute attribute = (Attribute)i$.next();
/*     */ 
/* 251 */       attribute.setIsVisible(false);
/*     */ 
/* 254 */       if (!attribute.isHidden())
/*     */       {
/* 257 */         for (Asset asset : vecAssets)
/*     */         {
/* 259 */           AttributeValue attValue = asset.getAttributeValue(attribute.getId());
/*     */ 
/* 262 */           if (((attribute.getFieldName() != null) && ((attribute.getFieldName().equals("originalFilename")) || (attribute.getFieldName().equals("addedBy")) || (attribute.getFieldName().equals("lastModifiedBy")) || (attribute.getFieldName().equals("dateAdded")) || (attribute.getFieldName().equals("dateLastModified")) || (attribute.getFieldName().equals("dateLastDownloaded")))) || ((attValue != null) && (StringUtil.stringIsPopulated(attValue.getValue()))) || ((attribute.getIsKeywordPicker()) && (attValue != null) && (attValue.getKeywordCategories() != null) && (attValue.getKeywordCategories() != null) && (!attValue.getKeywordCategories().isEmpty())))
/*     */           {
/* 275 */             attribute.setIsVisible(true);
/* 276 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     Attribute attribute;
/* 283 */     form.setAttributeList(vecAttributes);
/* 284 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 286 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 291 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 296 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setImageAssetManager(ImageAssetManagerImpl a_sImageAssetManager)
/*     */   {
/* 301 */     this.m_imageAssetManager = a_sImageAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_sAttributeManager)
/*     */   {
/* 306 */     this.m_attributeManager = a_sAttributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.CompareAssetsAction
 * JD-Core Version:    0.6.0
 */