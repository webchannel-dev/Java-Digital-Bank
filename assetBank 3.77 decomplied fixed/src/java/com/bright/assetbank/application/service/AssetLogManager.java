/*      */ package com.bright.assetbank.application.service;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.Bn2Manager;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.agreements.bean.Agreement;
/*      */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.AssetAuditLogEntry;
/*      */ import com.bright.assetbank.application.bean.AssetLog;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.application.constant.AssetLogConstants;
/*      */ import com.bright.assetbank.application.util.AssetUtil;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*      */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*      */ import com.bright.assetbank.database.AssetBankSql;
/*      */ import com.bright.assetbank.taxonomy.bean.Keyword;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.database.service.DBTransactionManager;
/*      */ import com.bright.framework.database.sql.ApplicationSql;
/*      */ import com.bright.framework.database.sql.SQLGenerator;
/*      */ import com.bright.framework.database.util.DBUtil;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.user.bean.User;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.http.HttpSessionAttributeListener;
/*      */ import javax.servlet.http.HttpSessionBindingEvent;
/*      */ import org.apache.avalon.framework.component.ComponentException;
/*      */ import org.apache.avalon.framework.component.ComponentManager;
/*      */ import org.apache.commons.lang.ObjectUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.jdom.Document;
/*      */ import org.jdom.Element;
/*      */ import org.jdom.output.Format;
/*      */ import org.jdom.output.XMLOutputter;
/*      */ 
/*      */ public class AssetLogManager extends Bn2Manager
/*      */   implements AssetBankConstants, AssetLogConstants, HttpSessionAttributeListener
/*      */ {
/*      */   private static final String c_ksClassName = "AssetLogManager";
/*   89 */   private DBTransactionManager transactionManager = null;
/*      */   private AttributeValueManager m_attributeValueManager;
/*   93 */   private AgreementsManager m_agreementsManager = null;
/*      */ 
/*   95 */   private AssetManager m_assetManager = null;
/*      */ 
/* 1544 */   private DBTransactionManager m_transactionManager = null;
/*      */ 
/*      */   public void attributeReplaced(HttpSessionBindingEvent a_beEvent)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void attributeRemoved(HttpSessionBindingEvent a_beEvent)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void attributeAdded(HttpSessionBindingEvent a_beEvent)
/*      */   {
/*  127 */     if ((AssetBankSettings.getAuditLogEnabled()) && (GlobalApplication.getInstance().isInitialised()))
/*      */     {
/*  129 */       if (a_beEvent.getName().equals("userprofile"))
/*      */       {
/*      */         try
/*      */         {
/*  135 */           ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_beEvent.getSession());
/*      */ 
/*  137 */           String sIpAddress = (String)a_beEvent.getSession().getAttribute("ipAddress");
/*      */ 
/*  139 */           long lNewSessionId = saveNewSessionLog(sIpAddress);
/*      */ 
/*  141 */           userProfile.setSessionId(lNewSessionId);
/*      */         }
/*      */         catch (Bn2Exception bn2)
/*      */         {
/*  145 */           GlobalApplication.getInstance().getLogger().error("SQL Exception whilst trying to log session " + bn2.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void generateChangeLogEntry(Asset a_originalAsset, Asset a_updatedAsset, DBTransaction a_dbTransaction, ABUser a_abUser, long a_lSessionId)
/*      */     throws Bn2Exception
/*      */   {
/*  170 */     Vector assetLogs = new Vector();
/*  171 */     Date dtLogDateTime = new Date();
/*      */ 
/*  174 */     if (a_originalAsset.getVersionNumber() < a_updatedAsset.getVersionNumber())
/*      */     {
/*  176 */       String sOldV = String.valueOf(a_originalAsset.getVersionNumber());
/*  177 */       String sNewV = String.valueOf(a_updatedAsset.getVersionNumber());
/*  178 */       assetLogs.add(createAssetLog("version", sNewV, sOldV, null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  182 */     assetLogs.addAll(attributeChanges(a_dbTransaction, a_originalAsset.getAttributeValues(), a_updatedAsset.getAttributeValues()));
/*      */ 
/*  184 */     assetLogs.addAll(categoryChanges("category", a_originalAsset, a_updatedAsset));
/*      */ 
/*  186 */     assetLogs.addAll(categoryChanges("accessLevel", a_originalAsset, a_updatedAsset));
/*      */ 
/*  188 */     assetLogs.addAll(fieldChanges(a_originalAsset, a_updatedAsset, a_dbTransaction));
/*      */ 
/*  190 */     String sXmlLog = generateXmlLog(assetLogs, dtLogDateTime, a_abUser.getUsername());
/*      */ 
/*  192 */     saveLog(a_originalAsset.getId(), a_updatedAsset.getFileName(), sXmlLog, a_dbTransaction, dtLogDateTime, 1L, a_lSessionId, a_updatedAsset.getVersionNumber());
/*      */   }
/*      */ 
/*      */   private AssetLog createAssetLog(String a_sType, String a_sNewValue, String a_sOldValue, String a_sName, long a_lId, boolean a_bAdded, long a_lLanguageId, String a_sLanguageName, Vector a_vTranslations)
/*      */   {
/*  208 */     AssetLog assetLog = new AssetLog();
/*      */ 
/*  210 */     assetLog.setType(a_sType);
/*  211 */     assetLog.setNewValue(a_sNewValue);
/*  212 */     assetLog.setOldValue(a_sOldValue);
/*  213 */     assetLog.setName(a_sName);
/*  214 */     assetLog.setId(a_lId);
/*  215 */     assetLog.setIsAdded(a_bAdded);
/*  216 */     assetLog.setLanguageId(a_lLanguageId);
/*  217 */     assetLog.setLanguageName(a_sLanguageName);
/*  218 */     assetLog.setTranslations(a_vTranslations);
/*      */ 
/*  220 */     return assetLog;
/*      */   }
/*      */ 
/*      */   private Vector attributeChanges(DBTransaction a_dbTransaction, Vector a_vecCurrentAttributes, Vector a_vecNewAttributes)
/*      */     throws Bn2Exception
/*      */   {
/*  241 */     Vector assetLogs = new Vector();
/*      */ 
/*  244 */     Iterator itCurrentValues = a_vecCurrentAttributes.iterator();
/*      */ 
/*  246 */     while (itCurrentValues.hasNext())
/*      */     {
/*  248 */       AttributeValue currentAttVal = (AttributeValue)itCurrentValues.next();
/*      */ 
/*  250 */       Iterator itNewValues = a_vecNewAttributes.iterator();
/*  251 */       while (itNewValues.hasNext())
/*      */       {
/*  253 */         AttributeValue newAttVal = (AttributeValue)itNewValues.next();
/*      */ 
/*  255 */         if (currentAttVal.getAttribute().getId() == newAttVal.getAttribute().getId())
/*      */         {
/*  257 */           if ((currentAttVal.getAttribute().getIsDropdownList()) || (currentAttVal.getAttribute().getIsCheckList()) || (currentAttVal.getAttribute().getIsOptionList()))
/*      */           {
/*  260 */             if (currentAttVal.getId() != newAttVal.getId())
/*      */             {
/*  264 */               String sAttributeValue = "";
/*      */ 
/*  267 */               AttributeValue newAttValFromDB = this.m_attributeValueManager.getListAttributeValue(a_dbTransaction, newAttVal.getId());
/*  268 */               if (newAttValFromDB != null)
/*      */               {
/*  270 */                 sAttributeValue = newAttValFromDB.getValue();
/*      */               }
/*      */ 
/*  273 */               AssetLog assetLog = createAssetLog("attribute", sAttributeValue, currentAttVal.getValue(), currentAttVal.getAttribute().getLabel(), currentAttVal.getAttribute().getId(), false, 0L, null, null);
/*  274 */               assetLogs.add(assetLog);
/*      */             }
/*      */           }
/*  277 */           else if (currentAttVal.getAttribute().getIsKeywordPicker())
/*      */           {
/*  279 */             boolean bChanged = false;
/*      */ 
/*  281 */             Vector vecOriginalKeywords = currentAttVal.getKeywordCategories();
/*  282 */             Vector vecNewKeywords = newAttVal.getKeywordCategories();
/*      */ 
/*  284 */             if (vecOriginalKeywords == null)
/*      */             {
/*  286 */               vecOriginalKeywords = new Vector();
/*      */             }
/*      */ 
/*  289 */             if (vecNewKeywords == null)
/*      */             {
/*  291 */               vecNewKeywords = new Vector();
/*      */             }
/*      */ 
/*  295 */             String sOriginalValues = "";
/*  296 */             String sNewValues = "";
/*      */ 
/*  298 */             Iterator itOriginalValues = vecOriginalKeywords.iterator();
/*  299 */             Keyword originalKeyword = null;
/*      */ 
/*  301 */             while (itOriginalValues.hasNext())
/*      */             {
/*  303 */               originalKeyword = (Keyword)itOriginalValues.next();
/*  304 */               if (!vecNewKeywords.contains(originalKeyword))
/*      */               {
/*  307 */                 bChanged = true;
/*      */               }
/*      */ 
/*  310 */               if (!sOriginalValues.equals(""))
/*      */               {
/*  312 */                 sOriginalValues = sOriginalValues + ",";
/*      */               }
/*  314 */               sOriginalValues = sOriginalValues + originalKeyword.getName();
/*      */             }
/*      */ 
/*  317 */             Iterator itNewVals = vecNewKeywords.iterator();
/*  318 */             Keyword newKeyword = null;
/*      */ 
/*  320 */             while (itNewVals.hasNext())
/*      */             {
/*  322 */               newKeyword = (Keyword)itNewVals.next();
/*      */ 
/*  324 */               if (!vecOriginalKeywords.contains(newKeyword))
/*      */               {
/*  327 */                 bChanged = true;
/*      */               }
/*      */ 
/*  330 */               if (!sNewValues.equals(""))
/*      */               {
/*  332 */                 sNewValues = sNewValues + ",";
/*      */               }
/*  334 */               sNewValues = sNewValues + newKeyword.getName();
/*      */             }
/*      */ 
/*  338 */             if (bChanged)
/*      */             {
/*  340 */               AssetLog assetLog = createAssetLog("attribute", sNewValues, sOriginalValues, currentAttVal.getAttribute().getLabel(), currentAttVal.getAttribute().getId(), false, 0L, null, null);
/*  341 */               assetLogs.add(assetLog);
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  347 */             AssetLog assetLog = new AssetLog();
/*      */ 
/*  350 */             if (!ObjectUtils.equals(currentAttVal.getValue(), newAttVal.getValue()))
/*      */             {
/*  352 */               assetLogs.add(createAssetLog("attribute", newAttVal.getValue(), currentAttVal.getValue(), currentAttVal.getAttribute().getLabel(), currentAttVal.getAttribute().getId(), false, 0L, null, null));
/*      */             }
/*      */ 
/*  355 */             Iterator currentTranslationValues = currentAttVal.getTranslations().iterator();
/*  356 */             Iterator newTranslationValues = newAttVal.getTranslations().iterator();
/*      */ 
/*  358 */             Vector assetTranslations = new Vector();
/*      */ 
/*  360 */             while (newTranslationValues.hasNext())
/*      */             {
/*  362 */               AttributeValue.Translation newAv = (AttributeValue.Translation)newTranslationValues.next();
/*      */ 
/*  364 */               while (currentTranslationValues.hasNext())
/*      */               {
/*  366 */                 AttributeValue.Translation currentAv = (AttributeValue.Translation)currentTranslationValues.next();
/*      */ 
/*  369 */                 String sCurrentValue = "";
/*  370 */                 String sNewValue = "";
/*  371 */                 if (currentAv.getValue() != null)
/*      */                 {
/*  373 */                   sCurrentValue = currentAv.getValue();
/*      */                 }
/*      */ 
/*  376 */                 if (newAv.getValue() == null)
/*      */                 {
/*  378 */                   sNewValue = newAv.getValue();
/*      */                 }
/*      */ 
/*  381 */                 if ((currentAv.getLanguage().getId() == newAv.getLanguage().getId()) && (!sCurrentValue.equals(sNewValue)))
/*      */                 {
/*  384 */                   AssetLog assetLanguageLog = createAssetLog("attribute", sNewValue, sCurrentValue, currentAttVal.getAttribute().getLabel(), currentAttVal.getAttribute().getId(), false, currentAv.getLanguage().getId(), currentAv.getLanguage().getName(), null);
/*  385 */                   assetTranslations.add(assetLanguageLog);
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/*  390 */               assetLog.setTranslations(assetTranslations);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  397 */     return assetLogs;
/*      */   }
/*      */ 
/*      */   private Vector categoryChanges(String a_sCategoryType, Asset a_original, Asset a_updated)
/*      */     throws Bn2Exception
/*      */   {
/*  417 */     Vector vecAssetLogs = new Vector();
/*  418 */     Vector vecOriginalCategories = null;
/*  419 */     Vector vecNewCategories = null;
/*  420 */     boolean bCheckApprovalStatuses = false;
/*      */ 
/*  422 */     if (a_sCategoryType.equals("category"))
/*      */     {
/*  424 */       vecOriginalCategories = new Vector();
/*  425 */       vecOriginalCategories.addAll(a_original.getDescriptiveCategories());
/*  426 */       vecNewCategories = new Vector();
/*  427 */       vecNewCategories.addAll(a_updated.getDescriptiveCategories());
/*      */     }
/*  429 */     else if (a_sCategoryType.equals("accessLevel"))
/*      */     {
/*  431 */       vecOriginalCategories = a_original.getPermissionCategories();
/*  432 */       vecNewCategories = a_updated.getPermissionCategories();
/*  433 */       bCheckApprovalStatuses = true;
/*      */     }
/*      */ 
/*  437 */     HashMap hmOriginalCategories = getHashMapFromCategoryVectorHelper(vecOriginalCategories);
/*  438 */     HashMap hmNewCategories = getHashMapFromCategoryVectorHelper(vecNewCategories);
/*      */ 
/*  440 */     if (vecOriginalCategories != null)
/*      */     {
/*  442 */       Category originalCategory = null;
/*      */ 
/*  445 */       for (int i = 0; i < vecOriginalCategories.size(); i++)
/*      */       {
/*  447 */         originalCategory = (Category)vecOriginalCategories.elementAt(i);
/*  448 */         if (!hmNewCategories.containsKey(new Long(originalCategory.getId())))
/*      */         {
/*  451 */           vecAssetLogs.add(createAssetLog(a_sCategoryType, null, null, originalCategory.getName(), originalCategory.getId(), false, 0L, null, null));
/*      */         } else {
/*  453 */           if (!bCheckApprovalStatuses) {
/*      */             continue;
/*      */           }
/*  456 */           Category newCat = (Category)hmNewCategories.get(new Long(originalCategory.getId()));
/*      */ 
/*  459 */           Category newRestrictiveCat = newCat.getClosestRestrictiveAncestor();
/*  460 */           Category oldRestrictiveCat = originalCategory.getClosestRestrictiveAncestor();
/*      */ 
/*  462 */           if ((newRestrictiveCat == null) || (oldRestrictiveCat == null) || (!newRestrictiveCat.equals(oldRestrictiveCat)) || (a_updated.isApproved(newRestrictiveCat) == a_original.isApproved(oldRestrictiveCat)))
/*      */           {
/*      */             continue;
/*      */           }
/*      */ 
/*  467 */           vecAssetLogs.add(createAssetLog("accessLevelApproval", String.valueOf(a_updated.isApproved(newRestrictiveCat)), String.valueOf(a_original.isApproved(oldRestrictiveCat)), oldRestrictiveCat.getName(), oldRestrictiveCat.getId(), false, 0L, null, null));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  483 */     if (vecNewCategories != null)
/*      */     {
/*  485 */       Category newCategory = null;
/*      */ 
/*  487 */       for (int i = 0; i < vecNewCategories.size(); i++)
/*      */       {
/*  489 */         newCategory = (Category)vecNewCategories.elementAt(i);
/*      */ 
/*  491 */         if (hmOriginalCategories.containsKey(new Long(newCategory.getId()))) {
/*      */           continue;
/*      */         }
/*  494 */         vecAssetLogs.add(createAssetLog(a_sCategoryType, null, null, newCategory.getName(), newCategory.getId(), true, 0L, null, null));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  499 */     return vecAssetLogs;
/*      */   }
/*      */ 
/*      */   private HashMap getHashMapFromCategoryVectorHelper(Vector a_vecCategories)
/*      */   {
/*  504 */     HashMap hmHM = new HashMap();
/*  505 */     if (a_vecCategories != null)
/*      */     {
/*  507 */       for (int i = 0; i < a_vecCategories.size(); i++)
/*      */       {
/*  509 */         Category temp = (Category)a_vecCategories.elementAt(i);
/*  510 */         hmHM.put(new Long(temp.getId()), temp);
/*      */       }
/*      */     }
/*  513 */     return hmHM;
/*      */   }
/*      */ 
/*      */   private Vector fieldChanges(Asset a_original, Asset a_updated, DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/*  533 */     Vector assetLogs = new Vector();
/*      */ 
/*  536 */     if ((a_original.getFileName() != null) && (!a_original.getFileName().equals(a_updated.getFileName())))
/*      */     {
/*  538 */       assetLogs.add(createAssetLog("file", a_updated.getFileName(), a_original.getFileName(), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  542 */     if (a_original.getIsFeatured() != a_updated.getIsFeatured())
/*      */     {
/*  544 */       assetLogs.add(createAssetLog("isFeatured", new Boolean(a_updated.getIsFeatured()).toString(), String.valueOf(!a_updated.getIsFeatured()), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  548 */     if (a_original.getIsPromoted() != a_updated.getIsPromoted())
/*      */     {
/*  550 */       assetLogs.add(createAssetLog("isPromoted", new Boolean(a_updated.getIsPromoted()).toString(), String.valueOf(!a_updated.getIsPromoted()), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  554 */     if (a_original.getIsUnsubmitted() != a_updated.getIsUnsubmitted())
/*      */     {
/*  556 */       assetLogs.add(createAssetLog("isUnsubmitted", new Boolean(a_updated.getIsUnsubmitted()).toString(), String.valueOf(!a_updated.getIsUnsubmitted()), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  560 */     if ((a_updated.getAgreement().getId() > 0L) && (a_original.getAgreement().getId() != a_updated.getAgreement().getId()))
/*      */     {
/*  563 */       Agreement newAgreement = this.m_agreementsManager.getAgreement(a_dbTransaction, a_updated.getAgreement().getId());
/*  564 */       a_updated.setAgreement(newAgreement);
/*      */ 
/*  566 */       assetLogs.add(createAssetLog("agreementChanged", newAgreement.getTitle(), a_original.getAgreement().getTitle(), "title", -1L, true, 0L, null, null));
/*  567 */       assetLogs.add(createAssetLog("agreementChanged", Long.valueOf(newAgreement.getId()).toString(), Long.valueOf(a_original.getAgreement().getId()).toString(), "id", -1L, true, 0L, null, null));
/*  568 */       assetLogs.add(createAssetLog("agreementChanged", newAgreement.getBody(), a_original.getAgreement().getBody(), "body", -1L, true, 0L, null, null));
/*  569 */       assetLogs.add(createAssetLog("agreementChanged", newAgreement.getExpiryString(), a_original.getAgreement().getExpiryString(), "expiry", -1L, true, 0L, null, null));
/*      */     }
/*      */ 
/*  574 */     if ((a_original.getAgreementTypeId() != 2L) && (a_updated.getAgreementTypeId() == 2L))
/*      */     {
/*  576 */       assetLogs.add(createAssetLog("agreementApplies", new Boolean(true).toString(), new Boolean(false).toString(), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  580 */     if ((a_original.getAgreementTypeId() == 2L) && (a_updated.getAgreementTypeId() != 2L))
/*      */     {
/*  582 */       assetLogs.add(createAssetLog("agreementApplies", new Boolean(false).toString(), new Boolean(true).toString(), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  586 */     if ((a_original.getAgreementTypeId() != 3L) && (a_updated.getAgreementTypeId() == 3L))
/*      */     {
/*  588 */       assetLogs.add(createAssetLog("restricted", new Boolean(true).toString(), new Boolean(false).toString(), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  592 */     if ((a_original.getAgreementTypeId() == 3L) && (a_updated.getAgreementTypeId() != 3L))
/*      */     {
/*  594 */       assetLogs.add(createAssetLog("restricted", new Boolean(false).toString(), new Boolean(true).toString(), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  598 */     if ((a_original.getAgreementTypeId() != 1L) && (a_updated.getAgreementTypeId() == 1L))
/*      */     {
/*  600 */       assetLogs.add(createAssetLog("unrestricted", new Boolean(true).toString(), new Boolean(false).toString(), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  604 */     if ((a_original.getAgreementTypeId() == 1L) && (a_updated.getAgreementTypeId() != 1L))
/*      */     {
/*  606 */       assetLogs.add(createAssetLog("unrestricted", new Boolean(false).toString(), new Boolean(true).toString(), null, 0L, false, 0L, null, null));
/*      */     }
/*      */ 
/*  609 */     return assetLogs;
/*      */   }
/*      */ 
/*      */   private String generateXmlLog(Vector a_assetLogs, Date a_dtLogDateTime, String a_sUsername)
/*      */   {
/*  628 */     boolean bCatChanged = false;
/*  629 */     boolean bAccessLevelChanged = false;
/*  630 */     boolean bAccessLevelApprovalChanged = false;
/*  631 */     boolean bAgreementsChanged = false;
/*      */ 
/*  633 */     Element root = new Element("change");
/*  634 */     Element categories = new Element("categories");
/*  635 */     Element accessLevels = new Element("accessLevels");
/*  636 */     Element accessLevelApprovalChanges = new Element("accessLevelApprovalChanges");
/*  637 */     Element catAdded = new Element("added");
/*  638 */     Element catRemoved = new Element("removed");
/*  639 */     Element accAdded = new Element("added");
/*  640 */     Element accRemoved = new Element("removed");
/*  641 */     Element agreement = new Element("agreement");
/*      */ 
/*  643 */     root.setAttribute("date", a_dtLogDateTime.toString());
/*  644 */     root.setAttribute("user", a_sUsername);
/*      */ 
/*  647 */     Iterator it = a_assetLogs.iterator();
/*  648 */     while (it.hasNext())
/*      */     {
/*  650 */       AssetLog assetLog = (AssetLog)it.next();
/*      */ 
/*  652 */       if (assetLog.getType().equals("version"))
/*      */       {
/*  654 */         Element log = new Element("version");
/*  655 */         Element before = new Element("before");
/*  656 */         before.addContent(assetLog.getOldValue());
/*  657 */         Element after = new Element("after");
/*  658 */         after.addContent(assetLog.getNewValue());
/*  659 */         log.addContent(before);
/*  660 */         log.addContent(after);
/*  661 */         root.addContent(log);
/*      */       }
/*  663 */       else if (assetLog.getType().equals("attribute"))
/*      */       {
/*  665 */         boolean bTranslations = false;
/*      */ 
/*  667 */         Element attribute = new Element("attribute");
/*  668 */         Element translations = new Element("translations");
/*      */ 
/*  670 */         attribute.setAttribute("id", new Long(assetLog.getId()).toString());
/*  671 */         attribute.setAttribute("label", assetLog.getName());
/*      */ 
/*  673 */         Element before = new Element("before");
/*  674 */         before.addContent(assetLog.getOldValue());
/*      */ 
/*  676 */         Element after = new Element("after");
/*  677 */         after.addContent(assetLog.getNewValue());
/*      */ 
/*  679 */         attribute.addContent(before);
/*  680 */         attribute.addContent(after);
/*      */ 
/*  683 */         if (assetLog.getTranslations() != null)
/*      */         {
/*  685 */           Iterator langIt = assetLog.getTranslations().iterator();
/*      */ 
/*  687 */           while (langIt.hasNext())
/*      */           {
/*  689 */             bTranslations = true;
/*  690 */             AssetLog langAssetLog = (AssetLog)langIt.next();
/*      */ 
/*  692 */             Element language = new Element("language");
/*  693 */             language.setAttribute("id", new Long(langAssetLog.getLanguageId()).toString());
/*  694 */             language.setAttribute("name", langAssetLog.getLanguageName());
/*      */ 
/*  696 */             Element langBefore = new Element("before");
/*  697 */             language.addContent(langBefore);
/*  698 */             langBefore.addContent(langAssetLog.getOldValue());
/*      */ 
/*  700 */             Element langAfter = new Element("after");
/*  701 */             language.addContent(langAfter);
/*  702 */             langAfter.addContent(langAssetLog.getNewValue());
/*      */ 
/*  704 */             translations.addContent(language);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  709 */         if (bTranslations)
/*      */         {
/*  711 */           attribute.addContent(translations);
/*      */         }
/*      */ 
/*  714 */         root.addContent(attribute);
/*      */       }
/*  717 */       else if (assetLog.getType().equals("category"))
/*      */       {
/*  719 */         bCatChanged = true;
/*      */ 
/*  721 */         Element category = new Element("category");
/*  722 */         category.setAttribute("id", new Long(assetLog.getId()).toString());
/*  723 */         category.setAttribute("name", assetLog.getName());
/*      */ 
/*  726 */         if (assetLog.getIsAdded())
/*      */         {
/*  728 */           catAdded.addContent(category);
/*      */         }
/*      */         else
/*      */         {
/*  732 */           catRemoved.addContent(category);
/*      */         }
/*      */ 
/*      */       }
/*  736 */       else if (assetLog.getType().equals("accessLevel"))
/*      */       {
/*  738 */         bAccessLevelChanged = true;
/*      */ 
/*  740 */         Element accessLevel = new Element("accessLevel");
/*  741 */         accessLevel.setAttribute("id", new Long(assetLog.getId()).toString());
/*  742 */         accessLevel.setAttribute("name", assetLog.getName());
/*      */ 
/*  746 */         if (assetLog.getIsAdded())
/*      */         {
/*  748 */           accAdded.addContent(accessLevel);
/*      */         }
/*      */         else
/*      */         {
/*  752 */           accRemoved.addContent(accessLevel);
/*      */         }
/*      */ 
/*      */       }
/*  756 */       else if (assetLog.getType().equals("accessLevelApproval"))
/*      */       {
/*  758 */         bAccessLevelApprovalChanged = true;
/*      */ 
/*  760 */         Element accessLevel = new Element("accessLevel");
/*  761 */         accessLevel.setAttribute("id", new Long(assetLog.getId()).toString());
/*  762 */         accessLevel.setAttribute("name", assetLog.getName());
/*  763 */         Element before = new Element("before");
/*  764 */         before.addContent(assetLog.getOldValue());
/*  765 */         Element after = new Element("after");
/*  766 */         after.addContent(assetLog.getNewValue());
/*      */ 
/*  768 */         accessLevel.addContent(before);
/*  769 */         accessLevel.addContent(after);
/*  770 */         accessLevelApprovalChanges.addContent(accessLevel);
/*      */       }
/*  772 */       else if (assetLog.getType().equals("agreementChanged"))
/*      */       {
/*  774 */         bAgreementsChanged = true;
/*  775 */         Element log = new Element(assetLog.getName());
/*  776 */         log.setAttribute("before", assetLog.getOldValue());
/*  777 */         log.setAttribute("after", assetLog.getNewValue());
/*      */ 
/*  779 */         agreement.addContent(log);
/*      */       }
/*  783 */       else if ((assetLog.getType().equals("file")) || (assetLog.getType().equals("isFeatured")) || (assetLog.getType().equals("isPromoted")) || (assetLog.getType().equals("isApproved")) || (assetLog.getType().equals("agreement")) || (assetLog.getType().equals("agreementApplies")) || (assetLog.getType().equals("restricted")) || (assetLog.getType().equals("unrestricted")))
/*      */       {
/*  788 */         Element log = new Element("property");
/*  789 */         log.setAttribute("name", assetLog.getType());
/*  790 */         log.setAttribute("before", assetLog.getOldValue());
/*  791 */         log.setAttribute("after", assetLog.getNewValue());
/*  792 */         root.addContent(log);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  797 */     if (bCatChanged)
/*      */     {
/*  799 */       categories.addContent(catAdded);
/*  800 */       categories.addContent(catRemoved);
/*  801 */       root.addContent(categories);
/*      */     }
/*      */ 
/*  804 */     if (bAccessLevelChanged)
/*      */     {
/*  806 */       accessLevels.addContent(accAdded);
/*  807 */       accessLevels.addContent(accRemoved);
/*  808 */       root.addContent(accessLevels);
/*      */     }
/*      */ 
/*  811 */     if (bAccessLevelApprovalChanged)
/*      */     {
/*  813 */       root.addContent(accessLevelApprovalChanges);
/*      */     }
/*      */ 
/*  816 */     if (bAgreementsChanged)
/*      */     {
/*  818 */       root.addContent(agreement);
/*      */     }
/*      */ 
/*  822 */     Document doc = new Document(root);
/*      */ 
/*  824 */     XMLOutputter xmlOut = new XMLOutputter(Format.getRawFormat().setIndent("\t").setLineSeparator("\n"));
/*      */ 
/*  826 */     String sXml = xmlOut.outputString(doc);
/*      */ 
/*  828 */     return sXml;
/*      */   }
/*      */ 
/*      */   public void saveWorkflowLog(Asset a_asset, DBTransaction a_dbTransaction, long a_lUserId, long a_lSessionId)
/*      */     throws Bn2Exception
/*      */   {
/*  844 */     if ((a_asset != null) && (a_lUserId > 0L))
/*      */     {
/*  846 */       saveLog(a_asset.getId(), a_asset.getFileName(), a_dbTransaction, a_lUserId, new Date(), 5L, a_lSessionId, a_asset.getVersionNumber());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveLog(long a_lAssetId, String a_sFileName, DBTransaction a_dbTransaction, long a_lUserId, Date a_dtLogDateTime, long a_lLogType, long a_lSessionId, long a_lVersionNumber)
/*      */     throws Bn2Exception
/*      */   {
/*  860 */     saveLog(a_lAssetId, a_sFileName, null, a_dbTransaction, a_dtLogDateTime, a_lLogType, a_lSessionId, a_lVersionNumber);
/*      */   }
/*      */ 
/*      */   private void saveLog(long a_lAssetId, String a_sFileName, String a_sXmlLog, DBTransaction a_dbTransaction, Date a_dtLogDateTime, long a_lLogType, long a_lSessionId, long a_lVersionNumber)
/*      */     throws Bn2Exception
/*      */   {
/*  885 */     String ksMethodName = "saveLog";
/*      */ 
/*  887 */     Connection con = null;
/*  888 */     PreparedStatement psql = null;
/*  889 */     String sSQL = null;
/*  890 */     long lNewId = 0L;
/*      */     try
/*      */     {
/*  894 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  896 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/*  898 */       sSQL = "INSERT INTO AssetChangeLog (";
/*      */ 
/*  900 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  902 */         lNewId = sqlGenerator.getUniqueId(con, "AssetChangeLogSequence");
/*  903 */         sSQL = sSQL + "Id, ";
/*      */       }
/*      */ 
/*  906 */       sSQL = sSQL + "AssetId, ChangeTime, LogEntry, LogTypeId, SessionLogId, VersionNumber, AssetDescription) VALUES (";
/*      */ 
/*  908 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  910 */         sSQL = sSQL + "?, ";
/*      */       }
/*      */ 
/*  913 */       sSQL = sSQL + "?,?,?,?,?,?,?)";
/*      */ 
/*  915 */       psql = con.prepareStatement(sSQL);
/*  916 */       int iField = 1;
/*      */ 
/*  918 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/*  920 */         psql.setLong(iField++, lNewId);
/*      */       }
/*      */ 
/*  923 */       DBUtil.setFieldIdOrNull(psql, iField++, a_lAssetId);
/*  924 */       DBUtil.setFieldTimestampOrNull(psql, iField++, new Date());
/*  925 */       psql.setString(iField++, a_sXmlLog);
/*  926 */       psql.setLong(iField++, a_lLogType);
/*  927 */       DBUtil.setFieldLongOrNull(psql, iField++, a_lSessionId);
/*  928 */       psql.setLong(iField++, a_lVersionNumber);
/*      */ 
/*  930 */       if (a_lAssetId > 0L)
/*      */       {
/*  932 */         psql.setString(iField++, a_lAssetId + " " + a_sFileName);
/*      */       }
/*      */       else
/*      */       {
/*  936 */         psql.setString(iField++, a_sFileName);
/*      */       }
/*      */ 
/*  939 */       psql.executeUpdate();
/*  940 */       psql.close();
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/*  945 */       this.m_logger.error("AssetLogManager.saveLog - " + sqe);
/*  946 */       throw new Bn2Exception("AssetLogManager.saveLog", sqe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public long saveLoginLog(DBTransaction a_dbTransaction, long a_lUserId, long a_lSessionId, Date a_loginDateTime)
/*      */     throws Bn2Exception
/*      */   {
/*  969 */     String ksMethodName = "saveLoginLog";
/*      */ 
/*  971 */     Connection con = null;
/*  972 */     PreparedStatement psql = null;
/*  973 */     String sSQL = null;
/*  974 */     long lNewId = 0L;
/*      */     try
/*      */     {
/*  978 */       con = a_dbTransaction.getConnection();
/*      */ 
/*  980 */       sSQL = "UPDATE SessionLog SET UserId=?, LoginDateTime=? WHERE Id=?";
/*      */ 
/*  982 */       psql = con.prepareStatement(sSQL);
/*  983 */       int iField = 1;
/*      */ 
/*  985 */       psql.setLong(iField++, a_lUserId);
/*  986 */       DBUtil.setFieldTimestampOrNull(psql, iField++, a_loginDateTime);
/*      */ 
/*  988 */       psql.setLong(iField++, a_lSessionId);
/*      */ 
/*  990 */       psql.executeUpdate();
/*  991 */       psql.close();
/*      */ 
/*  993 */       return lNewId;
/*      */     }
/*      */     catch (SQLException sqe)
/*      */     {
/*  998 */       this.m_logger.error("AssetLogManager.saveLoginLog - " + sqe);
/*  999 */     throw new Bn2Exception("AssetLogManager.saveLoginLog", sqe);}
/*      */   }
/*      */ 
/*      */   private long saveNewSessionLog(String a_sIpAddress)
/*      */     throws Bn2Exception
/*      */   {
/* 1024 */     DBTransaction dbTransaction = null;
/* 1025 */     Connection con = null;
/* 1026 */     PreparedStatement psql = null;
/* 1027 */     String sSQL = null;
/* 1028 */     long lNewId = 0L;
/*      */     try
/*      */     {
/* 1033 */       if (this.transactionManager == null)
/*      */       {
/* 1035 */         this.transactionManager = ((DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager"));
/*      */       }
/*      */ 
/* 1039 */       dbTransaction = this.transactionManager.getNewTransaction();
/*      */ 
/* 1041 */       con = dbTransaction.getConnection();
/*      */ 
/* 1043 */       AssetBankSql sqlGenerator = (AssetBankSql)SQLGenerator.getInstance();
/*      */ 
/* 1045 */       sSQL = "INSERT INTO SessionLog (";
/*      */ 
/* 1047 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1049 */         lNewId = sqlGenerator.getUniqueId(con, "SessionLogSequence");
/* 1050 */         sSQL = sSQL + "Id, ";
/*      */       }
/*      */ 
/* 1053 */       sSQL = sSQL + "IpAddress, StartDateTime) VALUES (";
/*      */ 
/* 1055 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1057 */         sSQL = sSQL + "?, ";
/*      */       }
/*      */ 
/* 1060 */       sSQL = sSQL + "?,?)";
/*      */ 
/* 1062 */       psql = con.prepareStatement(sSQL);
/* 1063 */       int iField = 1;
/*      */ 
/* 1065 */       if (!sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1067 */         psql.setLong(iField++, lNewId);
/*      */       }
/* 1069 */       psql.setString(iField++, a_sIpAddress);
/* 1070 */       DBUtil.setFieldTimestampOrNull(psql, iField++, new Date());
/*      */ 
/* 1072 */       psql.executeUpdate();
/* 1073 */       psql.close();
/*      */ 
/* 1075 */       if (sqlGenerator.usesAutoincrementFields())
/*      */       {
/* 1077 */         lNewId = sqlGenerator.getUniqueId(con, null);
/*      */       }
/*      */     }
/*      */     catch (ComponentException ce)
/*      */     {
/* 1082 */       this.m_logger.error("ComponentException whilst loading dbtransaction manager: " + ce.getMessage(), ce);
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1086 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1090 */           dbTransaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1094 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/* 1097 */       this.m_logger.error("SQL Exception whilst logging an asset view in the database : " + e);
/* 1098 */       throw new Bn2Exception("SQL Exception whilst logging an asset view in the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1103 */       if (dbTransaction != null)
/*      */       {
/*      */         try
/*      */         {
/* 1107 */           dbTransaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1111 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1116 */     return lNewId;
/*      */   }
/*      */ 
/*      */   public Vector getAssetAuditXmlLog(DBTransaction a_dbTransaction, long a_lxmlId)
/*      */     throws Bn2Exception
/*      */   {
/* 1134 */     return getAssetAuditLog(null, 0L, a_lxmlId, null, null, null, null, false, 0L);
/*      */   }
/*      */ 
/*      */   public Vector getAssetAuditList(DBTransaction a_dbTransaction, long a_lAssetId, long a_lVersionId)
/*      */     throws Bn2Exception
/*      */   {
/* 1149 */     return getAssetAuditLog(null, a_lAssetId, 0L, null, null, null, null, false, a_lVersionId);
/*      */   }
/*      */ 
/*      */   public Vector getAssetAuditLog(DBTransaction a_dbTransaction, long a_lAssetId, long a_lxmlId, Date a_dtStartDate, Date a_dtEndDate, String a_sUsername, String a_sIpAddress, boolean a_bIncludeViewsDownloads, long a_lVersionId)
/*      */     throws Bn2Exception
/*      */   {
/* 1176 */     Vector vResults = null;
/* 1177 */     Connection con = null;
/* 1178 */     ResultSet rs = null;
/* 1179 */     PreparedStatement psql = null;
/* 1180 */     String sSQL = null;
/* 1181 */     DBTransaction transaction = a_dbTransaction;
/*      */ 
/* 1183 */     if (transaction == null)
/*      */     {
/* 1185 */       transaction = this.m_transactionManager.getNewTransaction();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1190 */       con = transaction.getConnection();
/*      */ 
/* 1192 */       if (a_lxmlId <= 0L)
/*      */       {
/* 1194 */         sSQL = "SELECT acl.AssetId, acl.ChangeTime, acl.Id, acl.AssetDescription, ass.OriginalFilename, ass.ThumbnailFileLocation, abu.Username, sl.IpAddress, lt.Name FROM AssetChangeLog acl INNER JOIN Asset ass ON ass.Id = acl.AssetId INNER JOIN SessionLog sl ON sl.Id = acl.SessionLogId INNER JOIN AssetBankUser abu ON abu.Id = sl.UserId INNER JOIN LogType lt ON lt.Id = acl.LogTypeId WHERE 1=1";
/*      */       }
/*      */       else
/*      */       {
/* 1213 */         sSQL = "SELECT LogEntry FROM AssetChangeLog WHERE 1=1";
/*      */       }
/*      */ 
/* 1220 */       if (a_lAssetId > 0L)
/*      */       {
/* 1222 */         sSQL = sSQL + " AND acl.AssetId=? ";
/*      */       }
/* 1224 */       if (a_lxmlId > 0L)
/*      */       {
/* 1226 */         sSQL = sSQL + " AND Id=? ";
/*      */       }
/* 1228 */       if (a_dtStartDate != null)
/*      */       {
/* 1230 */         sSQL = sSQL + " AND ChangeTime>=?";
/*      */       }
/* 1232 */       if (a_dtEndDate != null)
/*      */       {
/* 1234 */         sSQL = sSQL + " AND ChangeTime<=?";
/*      */       }
/* 1236 */       if (StringUtil.stringIsPopulated(a_sUsername))
/*      */       {
/* 1238 */         sSQL = sSQL + " AND Username=?";
/*      */       }
/* 1240 */       if (StringUtil.stringIsPopulated(a_sIpAddress))
/*      */       {
/* 1242 */         sSQL = sSQL + " AND IpAddress=?";
/*      */       }
/* 1244 */       if (a_lVersionId > 0L)
/*      */       {
/* 1246 */         sSQL = sSQL + " AND acl.VersionNumber<=?";
/*      */       }
/*      */ 
/* 1249 */       if (a_lxmlId <= 0L)
/*      */       {
/* 1251 */         sSQL = sSQL + " ORDER BY acl.ChangeTime DESC";
/*      */       }
/*      */ 
/* 1254 */       if (AssetBankSettings.getMaxReportResults() > 0)
/*      */       {
/* 1256 */         sSQL = SQLGenerator.getInstance().setRowLimit(sSQL, AssetBankSettings.getMaxReportResults());
/*      */       }
/*      */ 
/* 1259 */       psql = con.prepareStatement(sSQL);
/*      */ 
/* 1261 */       int iCol = 1;
/*      */ 
/* 1263 */       if (a_lAssetId > 0L)
/*      */       {
/* 1265 */         psql.setLong(iCol++, a_lAssetId);
/*      */       }
/* 1267 */       if (a_lxmlId > 0L)
/*      */       {
/* 1269 */         psql.setLong(iCol++, a_lxmlId);
/*      */       }
/* 1271 */       if (a_dtStartDate != null)
/*      */       {
/* 1273 */         DBUtil.setFieldDateOrNull(psql, iCol++, a_dtStartDate);
/*      */       }
/* 1275 */       if (a_dtEndDate != null)
/*      */       {
/* 1277 */         DBUtil.setFieldDateOrNull(psql, iCol++, a_dtEndDate);
/*      */       }
/* 1279 */       if (StringUtil.stringIsPopulated(a_sUsername))
/*      */       {
/* 1281 */         psql.setString(iCol++, a_sUsername);
/*      */       }
/* 1283 */       if (StringUtil.stringIsPopulated(a_sIpAddress))
/*      */       {
/* 1285 */         psql.setString(iCol++, a_sIpAddress);
/*      */       }
/* 1287 */       if (a_lVersionId > 0L)
/*      */       {
/* 1289 */         psql.setLong(iCol++, a_lVersionId);
/*      */       }
/*      */ 
/* 1292 */       rs = psql.executeQuery();
/*      */ 
/* 1294 */       vResults = new Vector();
/*      */ 
/* 1296 */       if ((a_lxmlId > 0L) && (rs.next()))
/*      */       {
/* 1298 */         AssetAuditLogEntry entry = new AssetAuditLogEntry();
/* 1299 */         entry.setLog(SQLGenerator.getInstance().getStringFromLargeTextField(rs, "LogEntry"));
/* 1300 */         vResults.add(entry);
/*      */       }
/*      */ 
/* 1303 */       while (rs.next())
/*      */       {
/* 1305 */         AssetAuditLogEntry entry = new AssetAuditLogEntry();
/* 1306 */         entry.setDate(rs.getTimestamp("ChangeTime"));
/* 1307 */         entry.setUsername(rs.getString("Username"));
/* 1308 */         entry.setIpAddress(rs.getString("IpAddress"));
/* 1309 */         entry.setType(rs.getString("Name"));
/* 1310 */         entry.setId(rs.getLong("Id"));
/* 1311 */         entry.setAssetId(rs.getLong("AssetId"));
/*      */ 
/* 1313 */         String sThumbnailPath = AssetUtil.getThumbnailFileLocation(transaction, this.m_assetManager, rs.getString("ThumbnailFileLocation"), rs.getString("OriginalFilename"));
/*      */ 
/* 1316 */         entry.setThumbnailPath(sThumbnailPath);
/* 1317 */         entry.setIdentifier(rs.getString("AssetDescription"));
/* 1318 */         vResults.add(entry);
/*      */       }
/*      */ 
/* 1321 */       psql.close();
/*      */ 
/* 1324 */       if (a_bIncludeViewsDownloads)
/*      */       {
/* 1327 */         sSQL = "SELECT av.AssetId, av.Time, abu.Username, sl.IpAddress FROM AssetView av INNER JOIN SessionLog sl ON sl.Id = av.SessionLogId INNER JOIN AssetBankUser abu ON abu.Id = sl.UserId WHERE 1=1";
/*      */ 
/* 1338 */         if (a_dtStartDate != null)
/*      */         {
/* 1340 */           sSQL = sSQL + " AND Time>?";
/*      */         }
/* 1342 */         if (a_dtEndDate != null)
/*      */         {
/* 1344 */           sSQL = sSQL + " AND Time<?";
/*      */         }
/* 1346 */         if (StringUtil.stringIsPopulated(a_sUsername))
/*      */         {
/* 1348 */           sSQL = sSQL + " AND Username=?";
/*      */         }
/* 1350 */         if (StringUtil.stringIsPopulated(a_sIpAddress))
/*      */         {
/* 1352 */           sSQL = sSQL + " AND IpAddress=?";
/*      */         }
/*      */ 
/* 1355 */         sSQL = sSQL + " ORDER BY av.Time DESC";
/*      */ 
/* 1357 */         if (AssetBankSettings.getMaxReportResults() > 0)
/*      */         {
/* 1359 */           sSQL = SQLGenerator.getInstance().setRowLimit(sSQL, AssetBankSettings.getMaxReportResults());
/*      */         }
/*      */ 
/* 1362 */         psql = con.prepareStatement(sSQL);
/*      */ 
/* 1364 */         iCol = 1;
/*      */ 
/* 1366 */         if (a_dtStartDate != null)
/*      */         {
/* 1368 */           DBUtil.setFieldDateOrNull(psql, iCol++, a_dtStartDate);
/*      */         }
/* 1370 */         if (a_dtEndDate != null)
/*      */         {
/* 1372 */           DBUtil.setFieldDateOrNull(psql, iCol++, a_dtEndDate);
/*      */         }
/* 1374 */         if (StringUtil.stringIsPopulated(a_sUsername))
/*      */         {
/* 1376 */           psql.setString(iCol++, a_sUsername);
/*      */         }
/* 1378 */         if (StringUtil.stringIsPopulated(a_sIpAddress))
/*      */         {
/* 1380 */           psql.setString(iCol++, a_sIpAddress);
/*      */         }
/*      */ 
/* 1383 */         rs = psql.executeQuery();
/*      */ 
/* 1385 */         while (rs.next())
/*      */         {
/* 1387 */           AssetAuditLogEntry entry = new AssetAuditLogEntry();
/* 1388 */           entry.setDate(rs.getTimestamp("Time"));
/* 1389 */           entry.setUsername(rs.getString("Username"));
/* 1390 */           entry.setIpAddress(rs.getString("IpAddress"));
/* 1391 */           entry.setType("View");
/* 1392 */           entry.setAssetId(rs.getLong("AssetId"));
/* 1393 */           vResults.add(entry);
/*      */         }
/*      */ 
/* 1398 */         sSQL = "SELECT au.AssetId, au.TimeOfDownload, abu.Username, sl.IpAddress FROM AssetUse au INNER JOIN SessionLog sl ON sl.Id = au.SessionLogId INNER JOIN AssetBankUser abu ON abu.Id = sl.UserId WHERE 1=1";
/*      */ 
/* 1409 */         if (a_dtStartDate != null)
/*      */         {
/* 1411 */           sSQL = sSQL + " AND TimeOfDownload>?";
/*      */         }
/* 1413 */         if (a_dtEndDate != null)
/*      */         {
/* 1415 */           sSQL = sSQL + " AND TimeOfDownload<?";
/*      */         }
/* 1417 */         if (StringUtil.stringIsPopulated(a_sUsername))
/*      */         {
/* 1419 */           sSQL = sSQL + " AND Username=?";
/*      */         }
/* 1421 */         if (StringUtil.stringIsPopulated(a_sIpAddress))
/*      */         {
/* 1423 */           sSQL = sSQL + " AND IpAddress=?";
/*      */         }
/*      */ 
/* 1426 */         sSQL = sSQL + " ORDER BY au.TimeOfDownload DESC";
/*      */ 
/* 1428 */         if (AssetBankSettings.getMaxReportResults() > 0)
/*      */         {
/* 1430 */           sSQL = SQLGenerator.getInstance().setRowLimit(sSQL, AssetBankSettings.getMaxReportResults());
/*      */         }
/*      */ 
/* 1433 */         psql = con.prepareStatement(sSQL);
/*      */ 
/* 1435 */         iCol = 1;
/*      */ 
/* 1437 */         if (a_dtStartDate != null)
/*      */         {
/* 1439 */           DBUtil.setFieldDateOrNull(psql, iCol++, a_dtStartDate);
/*      */         }
/* 1441 */         if (a_dtEndDate != null)
/*      */         {
/* 1443 */           DBUtil.setFieldDateOrNull(psql, iCol++, a_dtEndDate);
/*      */         }
/* 1445 */         if (StringUtil.stringIsPopulated(a_sUsername))
/*      */         {
/* 1447 */           psql.setString(iCol++, a_sUsername);
/*      */         }
/* 1449 */         if (StringUtil.stringIsPopulated(a_sIpAddress))
/*      */         {
/* 1451 */           psql.setString(iCol++, a_sIpAddress);
/*      */         }
/*      */ 
/* 1454 */         rs = psql.executeQuery();
/*      */ 
/* 1456 */         while (rs.next())
/*      */         {
/* 1458 */           AssetAuditLogEntry entry = new AssetAuditLogEntry();
/* 1459 */           entry.setDate(rs.getTimestamp("TimeOfDownload"));
/* 1460 */           entry.setUsername(rs.getString("Username"));
/* 1461 */           entry.setIpAddress(rs.getString("IpAddress"));
/* 1462 */           entry.setType("Download");
/* 1463 */           entry.setAssetId(rs.getLong("AssetId"));
/* 1464 */           vResults.add(entry);
/*      */         }
/*      */ 
/* 1467 */         psql.close();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1474 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1478 */           transaction.rollback();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1482 */           this.m_logger.error("SQL Exception whilst rolling back connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */ 
/* 1486 */       this.m_logger.error("SQL Exception whilst getting asset usage inforamtion from the database : " + e);
/* 1487 */       throw new Bn2Exception("SQL Exception whilst getting asset usage inforamtion from the database : " + e, e);
/*      */     }
/*      */     finally
/*      */     {
/* 1492 */       if ((a_dbTransaction == null) && (transaction != null))
/*      */       {
/*      */         try
/*      */         {
/* 1496 */           transaction.commit();
/*      */         }
/*      */         catch (SQLException sqle)
/*      */         {
/* 1500 */           this.m_logger.error("SQL Exception whilst trying to close connection " + sqle.getMessage());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1505 */     return vResults;
/*      */   }
/*      */ 
/*      */   public void logAgreementAddEdit(Agreement a_oldAgreement, Agreement a_newAgreement, UserProfile a_userProfile, DBTransaction a_dbTransaction)
/*      */     throws Bn2Exception
/*      */   {
/* 1520 */     a_newAgreement.getBody();
/* 1521 */     a_newAgreement.getExpiryString();
/*      */ 
/* 1523 */     if (a_oldAgreement == null)
/*      */     {
/* 1525 */       a_oldAgreement = new Agreement();
/*      */     }
/*      */ 
/* 1528 */     Vector agreementLogs = new Vector();
/*      */ 
/* 1531 */     agreementLogs.add(createAssetLog("agreementChanged", a_newAgreement.getTitle(), a_oldAgreement.getTitle(), "title", -1L, true, 0L, null, null));
/* 1532 */     agreementLogs.add(createAssetLog("agreementChanged", Long.valueOf(a_newAgreement.getId()).toString(), Long.valueOf(a_oldAgreement.getId()).toString(), "id", -1L, true, 0L, null, null));
/* 1533 */     agreementLogs.add(createAssetLog("agreementChanged", a_newAgreement.getBody(), a_oldAgreement.getBody(), "body", -1L, true, 0L, null, null));
/* 1534 */     agreementLogs.add(createAssetLog("agreementChanged", a_newAgreement.getExpiryString(), a_oldAgreement.getExpiryString(), "expiry", -1L, true, 0L, null, null));
/*      */ 
/* 1537 */     String sXml = generateXmlLog(agreementLogs, new Date(), a_userProfile.getUser().getUsername());
/*      */ 
/* 1539 */     saveLog(0L, a_newAgreement.getTitle(), sXml, a_dbTransaction, new Date(), 6L, a_userProfile.getSessionId(), 0L);
/*      */   }
/*      */ 
/*      */   public void setTransactionManager(DBTransactionManager a_sTransactionManager)
/*      */   {
/* 1553 */     this.m_transactionManager = a_sTransactionManager;
/*      */   }
/*      */ 
/*      */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*      */   {
/* 1558 */     this.m_attributeValueManager = a_attributeValueManager;
/*      */   }
/*      */ 
/*      */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*      */   {
/* 1563 */     this.m_agreementsManager = a_agreementsManager;
/*      */   }
/*      */ 
/*      */   public void setAssetManager(AssetManager manager)
/*      */   {
/* 1568 */     this.m_assetManager = manager;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetLogManager
 * JD-Core Version:    0.6.0
 */