/*     */ package com.bright.assetbank.application.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetConversionInfo;
/*     */ import com.bright.assetbank.application.bean.AudioAsset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.bean.VideoConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.category.util.CategoryUtil;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.bright.framework.util.XMLUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AssetUtil
/*     */   implements AttributeConstants, AssetBankConstants, LanguageConstants
/*     */ {
/*     */   private static final String c_ksClassName = "AssetUtil";
/*     */ 
/*     */   public static void mergeParentAssetData(DBTransaction dbTransaction, Asset a_asset, AttributeValueManager a_attributeValueManager, String a_sDelimiter, boolean a_bKeepAnyExistingChildDataAsIs, boolean a_bMergeTextareasOnly, boolean a_bSelectedAssetsOnly, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 102 */     Vector vIds = null;
/*     */ 
/* 105 */     if (StringUtils.isNotEmpty(a_asset.getParentAssetIdsAsString()))
/*     */     {
/* 107 */       vIds = StringUtil.convertToVectorOfLongs(a_asset.getParentAssetIdsAsString().trim(), ",");
/*     */ 
/* 110 */       if ((vIds != null) && (vIds.size() > 0))
/*     */       {
/* 112 */         HashSet hsAttIdsNotToMerge = new HashSet();
/*     */ 
/* 115 */         if (a_bKeepAnyExistingChildDataAsIs)
/*     */         {
/* 118 */           for (int i = 0; i < a_asset.getAttributeValues().size(); i++)
/*     */           {
/* 120 */             AttributeValue temp = (AttributeValue)a_asset.getAttributeValues().get(i);
/*     */ 
/* 122 */             if (!StringUtils.isNotEmpty(temp.getValue()))
/*     */               continue;
/* 124 */             hsAttIdsNotToMerge.add(Long.valueOf(temp.getAttribute().getId()));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 129 */         for (int i = 0; i < vIds.size(); i++)
/*     */         {
/* 134 */           long lParentId = ((Long)vIds.get(i)).longValue();
/* 135 */           Vector vecParentAttributeValues = a_attributeValueManager.getDynamicAttributesForAsset(dbTransaction, lParentId, null, null, a_language);
/*     */ 
/* 137 */           Vector vMergedValues = AttributeUtil.mergeAttributeData(a_asset.getAttributeValues(), vecParentAttributeValues, a_sDelimiter, a_bMergeTextareasOnly, hsAttIdsNotToMerge, a_bSelectedAssetsOnly);
/* 138 */           a_asset.setAttributeValues(vMergedValues);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void reindexParents(DBTransaction a_dbTransaction, Asset a_asset, IAssetManager a_assetManager, MultiLanguageSearchManager a_searchManager)
/*     */     throws Bn2Exception, AssetNotFoundException
/*     */   {
/* 156 */     boolean bPopFromChild = AttributeUtil.getPopulatingFromChild(a_asset);
/* 157 */     if (((bPopFromChild) || (AssetBankSettings.getSortByPresenceOfChildAssets())) && (StringUtils.isNotEmpty(a_asset.getParentAssetIdsAsString())))
/*     */     {
/* 159 */       long[] ids = StringUtil.getIdsArray(a_asset.getParentAssetIdsAsString(), ",");
/* 160 */       for (int i = 0; i < ids.length; i++)
/*     */       {
/* 162 */         Asset parent = a_assetManager.getAsset(a_dbTransaction, ids[i], null, true, true);
/* 163 */         a_searchManager.indexDocument(parent, true);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getAssetsAsXML(Collection<Asset> a_vecAssets, boolean a_bFullList, Collection<String> a_captionIds, boolean a_bIncludeCaptionLabels, String a_sUrlPrefix, boolean a_bGenerateTempLargeImages, boolean a_bGenerateTempUnwatermarkedLargeImages, HashMap<Long, String> a_hmSubstituteLargeImageFiles, HashMap<Long, String> a_hmSubstituteUnwatermarkedLargeImageFiles, HashMap<Long, String> a_hmSubstituteThumbnailFiles, HttpServletRequest a_request, long a_lDescriptionAttributeId, long a_lCreditAttributeId)
/*     */     throws Bn2Exception, IOException
/*     */   {
/* 183 */     StringWriter writer = new StringWriter();
/* 184 */     writeAssetsAsXML(a_vecAssets, a_bFullList, a_captionIds, a_bIncludeCaptionLabels, a_sUrlPrefix, a_bGenerateTempLargeImages, a_bGenerateTempUnwatermarkedLargeImages, a_hmSubstituteLargeImageFiles, a_hmSubstituteUnwatermarkedLargeImageFiles, a_hmSubstituteThumbnailFiles, a_request, writer, a_lDescriptionAttributeId, a_lCreditAttributeId);
/*     */ 
/* 198 */     return writer.toString();
/*     */   }
/*     */ 
/*     */   public static void writeAssetsAsXML(Collection<Asset> a_vecAssets, boolean a_bFullList, Collection<String> a_captionIds, boolean a_bIncludeCaptionLabels, String a_sUrlPrefix, boolean a_bGenerateTempLargeImages, boolean a_bGenerateTempUnwatermarkedLargeImages, HashMap<Long, String> a_hmSubstituteLargeImageFiles, HashMap<Long, String> a_hmSubstituteUnwatermarkedLargeImageFiles, HashMap<Long, String> a_hmSubstituteThumbnailFiles, HttpServletRequest a_request, Writer a_output, long a_lDescriptionAttributeId, long a_lCreditAttributeId)
/*     */     throws Bn2Exception, IOException
/*     */   {
/* 226 */     a_output.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><search_results>");
/* 227 */     String ksMethodName = "getAssetsAsXML";
/* 228 */     DBTransaction transaction = null;
/*     */     try
/*     */     {
/* 233 */       DBTransactionManager transactionManager = (DBTransactionManager)(DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager");
/* 234 */       AssetManager assetManager = (AssetManager)(AssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager");
/* 235 */       transaction = transactionManager.getNewTransaction();
/*     */ 
/* 238 */       if ((a_vecAssets != null) && (a_vecAssets.size() > 0))
/*     */       {
/* 240 */         for (Asset asset : a_vecAssets)
/*     */         {
/*     */           try
/*     */           {
/* 244 */             a_output.write("<result id='" + asset.getId() + "'");
/* 245 */             a_output.write(" restricted='" + asset.getIsRestricted() + "'");
/* 246 */             String sDisplayServlet = "/servlet/display?file=";
/*     */ 
/* 248 */             if (StringUtil.stringIsPopulated(a_sUrlPrefix))
/*     */             {
/* 250 */               sDisplayServlet = a_sUrlPrefix + sDisplayServlet;
/*     */             }
/*     */             else
/*     */             {
/* 254 */               sDisplayServlet = ServletUtil.getApplicationUrl(a_request) + sDisplayServlet;
/*     */             }
/*     */ 
/* 258 */             if ((asset.getIsImage()) && (StringUtil.stringIsPopulated(asset.getFileLocation())))
/*     */             {
/* 260 */               ImageAsset imgAsset = (ImageAsset)asset;
/*     */ 
/* 262 */               String sDisplayUrl = sDisplayServlet + FileUtil.encryptFilepath(imgAsset.getFileLocation());
/*     */ 
/* 264 */               a_output.write(" displayUrl='" + sDisplayUrl + "'");
/* 265 */               a_output.write(getLargeImageFileAttribute(assetManager, imgAsset, imgAsset.getLargeImageFile(), a_bGenerateTempLargeImages, sDisplayServlet, AssetBankSettings.getLargeImageSize(), "largeUrl", a_hmSubstituteLargeImageFiles));
/* 266 */               a_output.write(getLargeImageFileAttribute(assetManager, imgAsset, imgAsset.getUnwatermarkedLargeImageFile(), a_bGenerateTempUnwatermarkedLargeImages, sDisplayServlet, AssetBankSettings.getUnwatermarkedLargeImageSize(), "unwatermarkedlargeUrl", a_hmSubstituteUnwatermarkedLargeImageFiles));
/* 267 */               a_output.write(getLargeImageFileAttribute(assetManager, imgAsset, imgAsset.getThumbnailImageFile(), a_bGenerateTempUnwatermarkedLargeImages, sDisplayServlet, AssetBankSettings.getUnwatermarkedLargeImageSize(), "thumbnailUrl", a_hmSubstituteThumbnailFiles));
/*     */ 
/* 269 */               if (imgAsset.getWidth() > 0)
/*     */               {
/* 271 */                 a_output.write(" width='" + imgAsset.getWidth() + "'");
/*     */               }
/* 273 */               if (imgAsset.getHeight() > 0)
/*     */               {
/* 275 */                 a_output.write(" height='" + imgAsset.getHeight() + "'");
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 286 */             a_output.write(">");
/*     */ 
/* 289 */             if (a_bFullList)
/*     */             {
/* 292 */               a_output.write(getAllAttributes(transaction, asset, a_captionIds, a_bIncludeCaptionLabels));
/*     */             }
/* 294 */             else if (StringUtil.stringIsPopulated(asset.getAPIAttributes()))
/*     */             {
/* 296 */               String[] aPairs = asset.getAPIAttributes().split("<>");
/* 297 */               if (aPairs != null)
/*     */               {
/* 299 */                 for (int x = 0; x < aPairs.length; x++)
/*     */                 {
/* 301 */                   String[] aPair = aPairs[x].split("!!");
/* 302 */                   if (aPair.length != 2)
/*     */                     continue;
/* 304 */                   a_output.write(getAttributeElement(aPair[0], aPair[1]));
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 311 */             if (a_lDescriptionAttributeId > 0L)
/*     */             {
/* 314 */               Iterator attributeIterator = asset.getAttributeValues().iterator();
/* 315 */               while (attributeIterator.hasNext())
/*     */               {
/* 317 */                 AttributeValue attributeValue = (AttributeValue)attributeIterator.next();
/*     */ 
/* 319 */                 if (attributeValue.getAttribute().getId() == a_lDescriptionAttributeId)
/*     */                 {
/* 321 */                   a_output.write("<caption><![CDATA[" + attributeValue.getValue() + "]]></caption>");
/* 322 */                   break;
/*     */                 }
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 328 */             if (a_lCreditAttributeId > 0L)
/*     */             {
/* 330 */               Iterator attributeIterator = asset.getAttributeValues().iterator();
/* 331 */               while (attributeIterator.hasNext())
/*     */               {
/* 333 */                 AttributeValue attributeValue = (AttributeValue)attributeIterator.next();
/*     */ 
/* 335 */                 if (attributeValue.getAttribute().getId() == a_lCreditAttributeId)
/*     */                 {
/* 337 */                   a_output.write("<credit><![CDATA[" + attributeValue.getValue() + "]]></credit>");
/* 338 */                   break;
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/* 343 */             a_output.write("</result>");
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 348 */             GlobalApplication.getInstance().getLogger().error("AssetUtil.getAssetsAsXML: Error getting xml for asset: ", e);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 353 */       a_output.write("</search_results>");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 357 */       if (transaction != null)
/*     */         try {
/* 359 */           transaction.rollback(); } catch (Exception ex) {
/*     */         }
/* 361 */       throw new Bn2Exception("AssetUtil.getAssetsAsXML: Error formating results: " + e.getMessage(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 365 */       if (transaction != null)
/*     */         try {
/* 367 */           transaction.commit();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getLargeImageFileAttribute(AssetManager a_assetManager, ImageAsset a_imgAsset, ImageFile a_imageFile, boolean a_bGenerateTempFile, String a_sDisplayServlet, int a_iSize, String a_sAttributeName, HashMap<Long, String> a_hmSubstituteFiles)
/*     */     throws Bn2Exception
/*     */   {
/* 388 */     String sPath = null;
/* 389 */     boolean bIncDisplayServlet = true;
/*     */ 
/* 392 */     if ((a_hmSubstituteFiles != null) && (a_hmSubstituteFiles.get(new Long(a_imgAsset.getId())) != null))
/*     */     {
/* 394 */       sPath = (String)a_hmSubstituteFiles.get(new Long(a_imgAsset.getId()));
/* 395 */       bIncDisplayServlet = false;
/*     */     }
/* 400 */     else if (a_imageFile == null)
/*     */     {
/* 402 */       if (a_bGenerateTempFile)
/*     */       {
/* 404 */         sPath = a_assetManager.getTemporaryLargeFile(a_imgAsset, a_iSize, -1, true);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 409 */       sPath = a_imageFile.getPath();
/*     */     }
/*     */ 
/* 414 */     if (StringUtil.stringIsPopulated(sPath))
/*     */     {
/* 416 */       if (bIncDisplayServlet)
/*     */       {
/* 418 */         sPath = a_sDisplayServlet + sPath;
/*     */       }
/* 420 */       return " " + a_sAttributeName + "='" + sPath + "'";
/*     */     }
/* 422 */     return "";
/*     */   }
/*     */ 
/*     */   private static String getAttributeElement(String a_sLabel, String a_sValue)
/*     */   {
/* 432 */     String sWrappedValue = "<![CDATA[" + a_sValue + "]]>";
/* 433 */     a_sLabel = XMLUtil.getXMLSafeValue(a_sLabel);
/*     */ 
/* 435 */     String sElement = "<attribute label='" + a_sLabel + "'>" + sWrappedValue + "</attribute>";
/* 436 */     return sElement;
/*     */   }
/*     */ 
/*     */   private static boolean excludedValue(AttributeValue a_value)
/*     */   {
/* 448 */     for (int i = 0; i < k_aXMLExcludedAttributeNames.length; i++)
/*     */     {
/* 450 */       if ((a_value.getAttribute().getFieldName() != null) && (a_value.getAttribute().getFieldName().equals(k_aXMLExcludedAttributeNames[i])))
/*     */       {
/* 453 */         return true;
/*     */       }
/*     */     }
/* 456 */     return k_xmlExcludedAttributeIds.contains(Long.valueOf(a_value.getId()));
/*     */   }
/*     */ 
/*     */   private static String getAllAttributes(DBTransaction a_transaction, Asset a_fullAsset, Collection<String> a_captionIds, boolean a_bIncludeCaptionLabels)
/*     */     throws Bn2Exception, ComponentException
/*     */   {
/* 475 */     String sAllAttributes = "";
/* 476 */     String sCaption = "";
/*     */ 
/* 478 */     if (a_transaction == null)
/*     */     {
/* 480 */       a_transaction = ((DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager")).getNewTransaction();
/*     */     }
/*     */ 
/* 484 */     Vector <AttributeValue> vecAttributeValues = a_fullAsset.getAttributeValues();
/* 485 */     for (AttributeValue value : vecAttributeValues)
/*     */     {
/* 487 */       if (!excludedValue(value))
/*     */       {
/* 489 */         String sValue = "";
/* 490 */         if ((value.getAttribute().getFieldName() != null) && ((value.getAttribute().getFieldName().equals("categories")) || (value.getAttribute().getFieldName().equals("accessLevels"))))
/*     */         {
/* 494 */           long lTree = 1L;
/* 495 */           if (value.getAttribute().getFieldName().equals("accessLevels"))
/*     */           {
/* 497 */             lTree = 2L;
/*     */           }
/*     */ 
/* 500 */           CategoryManager catManager = (CategoryManager)(CategoryManager)GlobalApplication.getInstance().getComponentManager().lookup("CategoryManager");
/* 501 */           Vector vecCategories = catManager.getCategoriesForItem(a_transaction, lTree, a_fullAsset.getId());
/* 502 */           sValue = CategoryUtil.getCategoryDescriptionString(vecCategories);
/*     */         }
/*     */         else
/*     */         {
/* 506 */           sValue = AttributeUtil.getAttributeValueAsString(a_fullAsset, value, k_defaultLanguage);
/*     */         }
/* 508 */         sAllAttributes = sAllAttributes + getAttributeElement(value.getAttribute().getLabel(), sValue);
/*     */ 
/* 511 */         if ((a_captionIds != null) && (a_captionIds.contains(String.valueOf(value.getAttribute().getId()))) && (StringUtil.stringIsPopulated(sValue)))
/*     */         {
/* 515 */           if (!StringUtil.stringIsPopulated(sCaption))
/*     */           {
/* 517 */             sCaption = "";
/*     */           }
/* 519 */           if (a_bIncludeCaptionLabels)
/*     */           {
/* 521 */             sCaption = sCaption + value.getAttribute().getLabel() + ": ";
/*     */           }
/* 523 */           sCaption = sCaption + sValue + ", ";
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 529 */     if (StringUtil.stringIsPopulated(sCaption))
/*     */     {
/* 532 */       sCaption = sCaption.substring(0, sCaption.length() - ", ".length());
/* 533 */       sCaption = "<caption><![CDATA[ " + sCaption + "  ]]></caption>";
/* 534 */       sAllAttributes = sCaption + sAllAttributes;
/*     */     }
/*     */ 
/* 537 */     return sAllAttributes;
/*     */   }
/*     */ 
/*     */   public static ArrayList<String> checkForCaptionAttributeIds(HttpServletRequest a_request)
/*     */   {
/* 550 */     String sIds = a_request.getParameter("captionIds");
/* 551 */     ArrayList alIds = null;
/*     */ 
/* 553 */     if (StringUtil.stringIsPopulated(sIds))
/*     */     {
/* 555 */       String[] aIds = sIds.split(",");
/* 556 */       for (String sId : aIds)
/*     */       {
/* 558 */         if (alIds == null)
/*     */         {
/* 560 */           alIds = new ArrayList();
/*     */         }
/* 562 */         alIds.add(sId);
/*     */       }
/*     */     }
/*     */ 
/* 566 */     return alIds;
/*     */   }
/*     */ 
/*     */   public static String getThumbnailFileLocation(DBTransaction a_dbTransaction, AssetManager a_assetManager, String a_sThumbnailLocation, String a_sOriginalFileName)
/*     */     throws Bn2Exception
/*     */   {
/* 581 */     if (StringUtils.isNotEmpty(a_sThumbnailLocation))
/*     */     {
/* 583 */       return a_sThumbnailLocation;
/*     */     }
/*     */ 
/* 586 */     return a_assetManager.getFileFormatForFile(a_dbTransaction, a_sOriginalFileName).getThumbnailImageLocation();
/*     */   }
/*     */ 
/*     */   public static AssetConversionInfo getNewConversionInfoForAsset(Asset a_asset)
/*     */   {
/* 598 */     AssetConversionInfo conversionInfo = null;
/*     */ 
/* 600 */     if ((a_asset instanceof ImageAsset))
/*     */     {
/* 602 */       conversionInfo = new ImageConversionInfo();
/*     */     }
/* 604 */     else if ((a_asset instanceof VideoAsset))
/*     */     {
/* 606 */       conversionInfo = new VideoConversionInfo();
/*     */     }
/* 608 */     else if ((a_asset instanceof AudioAsset))
/*     */     {
/* 610 */       conversionInfo = new VideoConversionInfo();
/*     */     }
/*     */     else
/*     */     {
/* 614 */       conversionInfo = new AssetConversionInfo();
/*     */     }
/*     */ 
/* 617 */     return conversionInfo;
/*     */   }
/*     */ 
/*     */   public static Asset copyAsset(Asset a_asset)
/*     */   {
/* 629 */     Asset copy = null;
/*     */ 
/* 631 */     if ((a_asset instanceof ImageAsset))
/*     */     {
/* 633 */       copy = new ImageAsset(a_asset);
/*     */     }
/* 635 */     else if ((a_asset instanceof VideoAsset))
/*     */     {
/* 637 */       copy = new VideoAsset(a_asset);
/*     */     }
/* 639 */     else if ((a_asset instanceof AudioAsset))
/*     */     {
/* 641 */       copy = new AudioAsset(a_asset);
/*     */     }
/*     */     else
/*     */     {
/* 645 */       copy = new Asset(a_asset);
/*     */     }
/*     */ 
/* 648 */     return copy;
/*     */   }
/*     */ 
/*     */   public static boolean doNamesMatch(Asset a_asset1, Asset a_asset2)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 665 */       LanguageManager languageManager = (LanguageManager)(LanguageManager)GlobalApplication.getInstance().getComponentManager().lookup("LanguageManager");
/* 666 */       List<Language> languages = languageManager.getLanguages(null);
/*     */ 
/* 669 */       for (Language lang : languages)
/*     */       {
/* 671 */         if (!a_asset1.getName(lang).equals(a_asset2.getName(lang)))
/*     */         {
/* 673 */           return false;
/*     */         }
/*     */       }
/* 676 */       return true;
/*     */     }
/*     */     catch (Exception e) {
/*     */     
/* 680 */     throw new Bn2Exception("AssetUtil.doNamesMatch: Error checking whether asset names match: ", e);
            }   
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.util.AssetUtil
 * JD-Core Version:    0.6.0
 */