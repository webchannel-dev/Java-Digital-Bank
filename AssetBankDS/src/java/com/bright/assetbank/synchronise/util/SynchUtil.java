/*     */ package com.bright.assetbank.synchronise.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*     */ import com.bright.assetbank.synchronise.constant.SynchronisationConstants;
/*     */ import com.bright.assetbank.taxonomy.bean.Keyword;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ //import com.bright.framework.language.bean.Translation;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.util.DateUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.sql.SQLException;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class SynchUtil
/*     */   implements AssetBankConstants, SynchronisationConstants
/*     */ {
/*  82 */   private static final SimpleDateFormat s_importFormat = DateUtil.getImportDateFormat();
/*     */   private static final char c_kcPackDelim = ';';
/*     */   private static final char c_kcPackEscape = '\\';
/*     */ 
/*     */   public static Map buildAvailableAttributeMap(AttributeManager a_attManager)
/*     */   {
/* 102 */     HashMap hmAttributes = new HashMap(20);
/*     */     try
/*     */     {
/* 105 */       Vector vAtts = a_attManager.getAttributePositionList();
/*     */ 
/* 109 */       for (int i = 0; i < vAtts.size(); i++)
/*     */       {
/* 111 */         Attribute att = (Attribute)vAtts.get(i);
/* 112 */         if (att.getStatic())
/*     */           continue;
/* 114 */         hmAttributes.put(att.getLabel(), att);
/* 115 */         hmAttributes.put(Long.valueOf(att.getId()), att);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 121 */       throw new RuntimeException("SynchUtil.processPropertyHeader() : Bn2Exception whilst getting attributes", bn2e);
/*     */     }
/* 123 */     return hmAttributes;
/*     */   }
/*     */ 
/*     */   public static void setAssetAttributeValue(AttributeValueManager a_attValueManager, TaxonomyManager a_taxManager, Asset a_asset, Attribute a_attribute, String a_sValue, boolean a_bCheckOnly)
/*     */   {
/* 144 */     Vector vecAttributeValues = getAttributeValue(a_attValueManager, a_taxManager, a_attribute, a_sValue, a_bCheckOnly);
/*     */ 
/* 146 */     if (vecAttributeValues != null)
/*     */     {
/* 148 */       a_asset.getAttributeValues().addAll(vecAttributeValues);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setAssetAttributeValueTranslation(AttributeManager a_attManager, TaxonomyManager a_taxManager, Asset a_asset, Attribute a_attribute, String a_sValue, Language a_language, boolean a_bCheckOnly)
/*     */   {
/* 172 */     AttributeValue attVal = a_asset.getAttributeValue(a_attribute.getId());
/*     */ 
/* 174 */     if (attVal != null)
/*     */     {
/* 176 */       Translation trans = (com.bright.assetbank.attribute.bean.AttributeValue.Translation)LanguageUtils.getTranslation(a_language, attVal.getTranslations());
/*     */ 
/* 178 */       if (trans != null)
/*     */       {
/* 180 */         ((AttributeValue.Translation)trans).setValue(a_sValue);
/*     */       }
/*     */       else
/*     */       {
/*     */         //AttributeValue tmp51_49 = attVal; tmp51_49.getClass(); AttributeValue.Translation newTranlation = new AttributeValue.Translation(tmp51_49, a_language);
/* 185 */         AttributeValue.Translation newTranlation = attVal.new Translation(a_language);
                  newTranlation.setValue(a_sValue);
/* 186 */         attVal.getTranslations().add(newTranlation);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setLastModifiedDatesForSyncAssets(DBTransaction a_dbTransaction, Vector a_assetsToIndex, IAssetManager a_assetManager)
/*     */     throws Bn2Exception
/*     */   {
/* 209 */     if ((AssetBankSettings.getAllowPublishing()) && (AssetBankSettings.getIncludeParentMetadataForExport()))
/*     */     {
/* 211 */       if ((a_assetsToIndex != null) && (a_assetsToIndex.size() > 0))
/*     */       {
/* 213 */         Vector vecAssetIds = new Vector();
/* 214 */         for (int i = 0; i < a_assetsToIndex.size(); i++)
/*     */         {
/* 216 */           vecAssetIds.add(new Long(((LightweightAsset)a_assetsToIndex.elementAt(i)).getId()));
/*     */         }
/* 218 */         a_assetManager.updateDateLastModifiedForAssets(a_dbTransaction, new GregorianCalendar().getTime(), vecAssetIds);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Vector<AttributeValue> getAttributeValue(AttributeValueManager a_attValueManager, TaxonomyManager a_taxManager, Attribute a_attribute, String a_sValue, boolean a_bCheckOnly)
/*     */   {
/* 230 */     Vector vecAttributeValues = new Vector();
/*     */ 
/* 232 */     if ((a_attribute != null) && ((StringUtil.stringIsPopulated(a_sValue)) || ((AssetBankSettings.getSupportMultiLanguage()) && (a_attribute.getIsTranslatable()))))
/*     */     {
/* 236 */       if (a_attribute.getIsDatepicker())
/*     */       {
/*     */         try
/*     */         {
/* 241 */           Date date = s_importFormat.parse(a_sValue);
/* 242 */           BrightDate bd = new BrightDate(date);
/* 243 */           AttributeValue value = new AttributeValue();
/* 244 */           value.setAttribute(a_attribute);
/* 245 */           value.setValue(bd.getFormDate());
/* 246 */           vecAttributeValues.add(value);
/*     */         }
/*     */         catch (ParseException pe)
/*     */         {
/* 250 */           throw new IllegalArgumentException("Argument value " + a_sValue + " not a parsable date.", pe);
/*     */         }
/*     */       }
/* 253 */       else if (a_attribute.getIsDateTime())
/*     */       {
/*     */         try
/*     */         {
/* 258 */           Date date = s_importFormat.parse(a_sValue);
/* 259 */           BrightDateTime bd = new BrightDateTime(date);
/* 260 */           AttributeValue value = new AttributeValue();
/* 261 */           value.setAttribute(a_attribute);
/* 262 */           value.setValue(bd.getFormDateTime());
/* 263 */           vecAttributeValues.add(value);
/*     */         }
/*     */         catch (ParseException pe)
/*     */         {
/* 267 */           throw new IllegalArgumentException("Argument value " + a_sValue + " not a parsable datetime.", pe);
/*     */         }
/*     */       }
/* 270 */       else if ((a_attribute.getIsDropdownList()) || (a_attribute.getIsCheckList()) || (a_attribute.getIsOptionList()))
/*     */       {
/* 274 */         Collection<String> sValues = new ArrayList();
/*     */ 
/* 276 */         if ((a_attribute.getIsCheckList()) || (a_attribute.getIsOptionList()))
/*     */         {
/* 280 */           sValues.addAll(unpackToReadOnlyList(a_sValue));
/*     */         }
/*     */         else
/*     */         {
/* 285 */           sValues.add(a_sValue);
/*     */         }
/*     */ 
/* 288 */         for (String sValue : sValues)
/*     */         {
/* 290 */           if ((sValue.startsWith("id:")) && (sValue.length() > "id:".length()))
/*     */           {
/* 292 */             String id = sValue.substring("id:".length()).trim();
/* 293 */             AttributeValue value = new AttributeValue();
/* 294 */             value.setAttribute(a_attribute);
/* 295 */             value.setId(Long.parseLong(id));
/* 296 */             vecAttributeValues.add(value);
/*     */           }
/*     */           else
/*     */           {
/*     */             try
/*     */             {
/* 302 */               AttributeValue av = a_attValueManager.getListAttributeValueId(null, a_attribute.getId(), sValue, !a_bCheckOnly);
/*     */ 
/* 305 */               if (av != null)
/*     */               {
/* 307 */                 AttributeValue value = new AttributeValue();
/* 308 */                 value.setAttribute(a_attribute);
/* 309 */                 value.setId(av.getId());
/* 310 */                 vecAttributeValues.add(value);
/*     */               }
/*     */             }
/*     */             catch (Bn2Exception bn2e)
/*     */             {
/* 315 */               GlobalApplication.getInstance().getLogger().error("AssetBeanReader.populateBean() : Bn2Exception whilst getting attribute value id for string " + sValue, bn2e);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 320 */       else if (a_attribute.getIsKeywordPicker())
/*     */       {
/*     */         try
/*     */         {
/* 324 */           Vector vecKeywords = a_taxManager.getKeywordsForAttributeValue(null, a_sValue, a_attribute, LanguageConstants.k_defaultLanguage);
/*     */ 
/* 326 */           if (vecKeywords != null)
/*     */           {
/* 328 */             List<Keyword> keywordsToAdd = a_taxManager.checkKeywordAutoAdd(null, vecKeywords, a_attribute.getTreeId(), AssetBankSettings.getKeywordAutoAdd());
/* 329 */             if ((!AssetBankSettings.getKeywordAutoAdd()) && (keywordsToAdd.size() > 0))
/*     */             {
/* 331 */               String reason = "Argument value " + a_sValue + " contained invalid keywords: ";
/* 332 */               boolean first = true;
/* 333 */               for (Keyword keyword : keywordsToAdd) {
/* 334 */                 if (first)
/* 335 */                   first = false;
/*     */                 else {
/* 337 */                   reason = reason + ", ";
/*     */                 }
/* 339 */                 reason = reason + keyword.getName();
/*     */               }
/* 341 */               throw new IllegalArgumentException(reason);
/*     */             }
/* 343 */             AttributeValue value = new AttributeValue();
/* 344 */             value.setAttribute(a_attribute);
/* 345 */             value.setKeywordCategories(vecKeywords);
/* 346 */             vecAttributeValues.add(value);
/*     */           }
/*     */         }
/*     */         catch (Bn2Exception be)
/*     */         {
/* 351 */           throw new IllegalArgumentException("Argument value " + a_sValue + " not valid keywords.", be);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 356 */         AttributeValue value = new AttributeValue();
/* 357 */         value.setAttribute(a_attribute);
/* 358 */         value.setValue(a_sValue);
/* 359 */         vecAttributeValues.add(value);
/*     */       }
/*     */     }
/* 362 */     return vecAttributeValues;
/*     */   }
/*     */ 
/*     */   public static void addAssetParents(AttributeValueManager a_attributeValueManager, Asset a_asset, Attribute a_attribute, String a_sValue)
/*     */     throws Bn2Exception
/*     */   {
/* 380 */     Vector vecEntityIds = getEntityIdsForRelation(a_asset.getEntity().getId(), 3L);
/*     */ 
/* 382 */     if (vecEntityIds.size() > 0)
/*     */     {
/* 386 */       GlobalApplication.getInstance().getLogger().debug("SynchUtil.addAssetParents: finding parents with attr value start: " + a_sValue);
/* 387 */       String sAssetIds = a_attributeValueManager.getAssetsWithAttributeValues(null, a_attribute.getId(), a_sValue, vecEntityIds);
/* 388 */       String sNewAssetIds = StringUtil.appendToString(a_asset.getParentAssetIdsAsString(), sAssetIds, ',');
/* 389 */       a_asset.setParentAssetIdsAsString(sNewAssetIds);
/* 390 */       GlobalApplication.getInstance().getLogger().debug("SynchUtil.addAssetParents: finding parents with attr value end");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void addAssetChildren(AttributeValueManager a_attributeValueManager, Asset a_asset, Attribute a_attribute, String a_sValue)
/*     */     throws Bn2Exception
/*     */   {
/* 408 */     Vector vecEntityIds = getEntityIdsForRelation(a_asset.getEntity().getId(), 2L);
/*     */ 
/* 410 */     if (vecEntityIds.size() > 0)
/*     */     {
/* 413 */       String sAssetIds = a_attributeValueManager.getAssetsWithAttributeValues(null, a_attribute.getId(), a_sValue, vecEntityIds);
/* 414 */       String sNewAssetIds = StringUtil.appendToString(a_asset.getChildAssetIdsAsString(), sAssetIds, ',');
/* 415 */       a_asset.setChildAssetIdsAsString(sNewAssetIds);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void addAssetPeers(AttributeValueManager a_attributeValueManager, Asset a_asset, Attribute a_attribute, String a_sValue)
/*     */     throws Bn2Exception
/*     */   {
/* 433 */     Vector vecEntityIds = getEntityIdsForRelation(a_asset.getEntity().getId(), 1L);
/*     */ 
/* 435 */     if (vecEntityIds.size() > 0)
/*     */     {
/* 438 */       String sAssetIds = a_attributeValueManager.getAssetsWithAttributeValues(null, a_attribute.getId(), a_sValue, vecEntityIds);
/* 439 */       String sNewAssetIds = StringUtil.appendToString(a_asset.getPeerAssetIdsAsString(), sAssetIds, ",".charAt(0));
/* 440 */       a_asset.setPeerAssetIdsAsString(sNewAssetIds);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static Vector<Long> getEntityIdsForRelation(long a_lEntityTypeId, long a_lRelType)
/*     */     throws Bn2Exception
/*     */   {
/* 456 */     Vector vecEntityIds = new Vector();
/* 457 */     DBTransaction transaction = null;
/*     */     try
/*     */     {
/* 461 */       DBTransactionManager transactionManager = (DBTransactionManager)(DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager");
/* 462 */       AssetEntityRelationshipManager relManager = (AssetEntityRelationshipManager)(AssetEntityRelationshipManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetEntityRelationshipManager");
/* 463 */       transaction = transactionManager.getNewTransaction();
/*     */ 
/* 465 */       if (a_lRelType == 3L)
/*     */       {
/* 468 */         Vector<AssetEntityRelationship> vecRels = relManager.getParentRelationshipsForAssetEntity(transaction, a_lEntityTypeId);
/* 469 */         for (AssetEntityRelationship rel : vecRels)
/*     */         {
/* 471 */           vecEntityIds.add(new Long(rel.getRelatesFromAssetEntityId()));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 476 */         ArrayList<AssetEntityRelationship> alRels = relManager.getAllowableAssetEntityRelationships(transaction, a_lEntityTypeId, a_lRelType);
/* 477 */         for (AssetEntityRelationship rel : alRels)
/*     */         {
/* 479 */           vecEntityIds.add(new Long(rel.getRelatesToAssetEntityId()));
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/* 485 */       throw new Bn2Exception("SynchUtil.getEntityIdsForRelation: Error getting components: ", e);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 491 */         transaction.commit();
/*     */       }
/*     */       catch (SQLException e) {
/*     */       }
/*     */     }
/* 496 */     return vecEntityIds;
/*     */   }
/*     */ 
/*     */   public static String getExportFilename(Asset a_asset)
/*     */   {
/* 509 */     if (StringUtils.isNotEmpty(a_asset.getOriginalFilename()))
/*     */     {
/* 511 */       return FilenameUtils.getBaseName(a_asset.getOriginalFilename()) + "." + FilenameUtils.getExtension(a_asset.getFileName());
/*     */     }
/* 513 */     return a_asset.getFileName();
/*     */   }
/*     */ 
/*     */   public static String getExportFilenameNoExtension(Asset a_asset)
/*     */   {
/* 524 */     if (StringUtils.isNotEmpty(a_asset.getOriginalFilename()))
/*     */     {
/* 526 */       return FilenameUtils.getBaseName(a_asset.getOriginalFilename());
/*     */     }
/* 528 */     return FilenameUtils.getBaseName(a_asset.getFileName());
/*     */   }
/*     */ 
/*     */   public static String pack(String[] a_stringsToPack)
/*     */   {
/* 536 */     return StringUtil.pack(';', '\\', a_stringsToPack);
/*     */   }
/*     */ 
/*     */   public static String[] unpack(String a_sPacked)
/*     */   {
/* 549 */     if (StringUtils.isEmpty(a_sPacked))
/*     */     {
/* 551 */       return new String[0];
/*     */     }
/*     */ 
/* 555 */     return StringUtil.unpack(';', '\\', a_sPacked);
/*     */   }
/*     */ 
/*     */   public static List<String> unpackToReadOnlyList(String a_sIds)
/*     */   {
/* 561 */     String[] sIds = unpack(a_sIds);
/* 562 */     return Arrays.asList(sIds);
/*     */   }
/*     */ 
/*     */   public static Vector<String> unpackToVector(String a_sIds)
/*     */   {
/* 567 */     List strings = unpackToReadOnlyList(a_sIds);
/* 568 */     return new Vector(strings);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.util.SynchUtil
 * JD-Core Version:    0.6.0
 */