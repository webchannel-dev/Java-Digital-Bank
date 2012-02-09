/*      */ package com.bright.assetbank.attribute.util;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.form.Bn2Form;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.bean.Asset;
/*      */ import com.bright.assetbank.application.bean.Description;
/*      */ import com.bright.assetbank.application.bean.ImageAsset;
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.attribute.bean.Attribute;
/*      */ import com.bright.assetbank.attribute.bean.Attribute.Translation;
/*      */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*      */ //import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*      */ import com.bright.assetbank.attribute.bean.DataLookupRequest;
/*      */ import com.bright.assetbank.attribute.bean.DisplayAttribute;
/*      */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*      */ import com.bright.assetbank.entity.bean.AssetEntity;
/*      */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*      */ import com.bright.assetbank.search.bean.SearchCriteria;
/*      */ import com.bright.assetbank.search.constant.AssetBankSearchConstants;
/*      */ import com.bright.assetbank.taxonomy.bean.Keyword;
/*      */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*      */ import com.bright.assetbank.user.bean.ABUser;
/*      */ import com.bright.assetbank.user.bean.ABUserProfile;
/*      */ import com.bright.framework.category.bean.Category;
/*      */ //import com.bright.framework.category.bean.Category.Translation;
/*      */ import com.bright.framework.common.bean.BrightDate;
/*      */ import com.bright.framework.common.bean.BrightDateTime;
/*      */ import com.bright.framework.common.bean.BrightMoney;
/*      */ import com.bright.framework.database.bean.DBTransaction;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.language.util.LanguageUtils;
/*      */ import com.bright.framework.message.constant.MessageConstants;
/*      */ import com.bright.framework.simplelist.bean.ListItem;
/*      */ import com.bright.framework.simplelist.service.ListManager;
/*      */ import com.bright.framework.user.bean.UserProfile;
/*      */ import com.bright.framework.util.BrightDateFormat;
/*      */ import com.bright.framework.util.CollectionUtil;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.math.BigDecimal;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.lucene.search.SortField;
/*      */ 
/*      */ public class AttributeUtil
/*      */   implements AttributeConstants, AssetBankConstants, AssetBankSearchConstants, MessageConstants
/*      */ {
/*      */   private static final char c_kcExternalDictionaryPackDelim = '_';
/*      */   private static final char c_kcExternalDictionaryPackEscape = '\\';
/*      */ 
/*      */   public static AttributeValue getListOptionValue(Vector a_vecListOptionValues, String a_sValue)
/*      */   {
/*  121 */     AttributeValue attValue = null;
/*  122 */     int iNumericMapValue = 0;
/*      */ 
/*  125 */     for (int i = 0; i < a_vecListOptionValues.size(); i++)
/*      */     {
/*  127 */       attValue = (AttributeValue)a_vecListOptionValues.get(i);
/*      */ 
/*  129 */       if ((attValue.getValue() != null) && (attValue.getValue().equalsIgnoreCase(a_sValue)))
/*      */       {
/*      */         break;
/*      */       }
/*      */ 
/*  136 */       if ((attValue.getMapToFieldValue() != null) && (attValue.getMapToFieldValue().length() > 0))
/*      */       {
/*  138 */         if (attValue.getMapToFieldValue().equalsIgnoreCase(a_sValue))
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*  145 */         boolean bAND = false;
/*  146 */         String[] aMapValues = null;
/*      */ 
/*  148 */         if (attValue.getMapToFieldValue().contains("&"))
/*      */         {
/*  150 */           bAND = true;
/*  151 */           aMapValues = attValue.getMapToFieldValue().split("&");
/*      */         }
/*      */         else
/*      */         {
/*  156 */           aMapValues = attValue.getMapToFieldValue().split("\\|");
/*      */         }
/*      */ 
/*  160 */         boolean bMatch = false;
/*  161 */         for (int iMapIndex = 0; iMapIndex < aMapValues.length; iMapIndex++)
/*      */         {
/*  163 */           bMatch = false;
/*  164 */           aMapValues[iMapIndex] = aMapValues[iMapIndex].trim();
/*      */ 
/*  167 */           if (aMapValues[iMapIndex].equalsIgnoreCase(a_sValue))
/*      */           {
/*  170 */             bMatch = true;
/*      */           }
/*      */           else
/*      */           {
/*      */             try
/*      */             {
/*  177 */               boolean bIncludesEqual = false;
/*  178 */               int iNumericValue = Integer.parseInt(a_sValue);
/*  179 */               if ((aMapValues[iMapIndex].startsWith("<=")) || (aMapValues[iMapIndex].startsWith(">=")))
/*      */               {
/*  181 */                 iNumericMapValue = Integer.parseInt(aMapValues[iMapIndex].substring(2));
/*  182 */                 bIncludesEqual = true;
/*      */               }
/*  184 */               else if ((aMapValues[iMapIndex].startsWith("<")) || (aMapValues[iMapIndex].startsWith(">")))
/*      */               {
/*  186 */                 iNumericMapValue = Integer.parseInt(aMapValues[iMapIndex].substring(1));
/*      */               }
/*      */ 
/*  189 */               if (aMapValues[iMapIndex].startsWith("<"))
/*      */               {
/*  191 */                 if ((iNumericValue < iNumericMapValue) || ((bIncludesEqual) && (iNumericValue == iNumericMapValue)))
/*      */                 {
/*  194 */                   bMatch = true;
/*      */                 }
/*      */               }
/*  197 */               else if (aMapValues[iMapIndex].startsWith(">"))
/*      */               {
/*  199 */                 if ((iNumericValue > iNumericMapValue) || ((bIncludesEqual) && (iNumericValue == iNumericMapValue)))
/*      */                 {
/*  202 */                   bMatch = true;
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */             catch (NumberFormatException nfe)
/*      */             {
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  213 */           if ((bMatch) && (!bAND))
/*      */           {
/*      */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  221 */         if (bMatch)
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  228 */       attValue = null;
/*      */     }
/*      */ 
/*  231 */     return attValue;
/*      */   }
/*      */ 
/*      */   public static void setAttributeValuesForAttributeInAsset(Asset a_asset, Vector a_vecAttributeValues, boolean a_bPreserveTranslations)
/*      */   {
/*  243 */     if (a_vecAttributeValues != null)
/*      */     {
/*  245 */       boolean bRemovedOldVals = false;
/*  246 */       for (int x = 0; x < a_vecAttributeValues.size(); x++)
/*      */       {
/*  248 */         AttributeValue value = (AttributeValue)a_vecAttributeValues.elementAt(x);
/*      */ 
/*  250 */         if (!bRemovedOldVals)
/*      */         {
/*  253 */           for (int i = a_asset.getAttributeValues().size() - 1; i >= 0; i--)
/*      */           {
/*  255 */             AttributeValue aVal = (AttributeValue)a_asset.getAttributeValues().get(i);
/*      */ 
/*  257 */             if ((aVal == null) || (aVal.getAttribute() == null)) {
/*      */               continue;
/*      */             }
/*  260 */             if (aVal.getAttribute().getId() != value.getAttribute().getId()) {
/*      */               continue;
/*      */             }
/*  263 */             a_asset.getAttributeValues().remove(i);
/*      */ 
/*  266 */             if ((!a_bPreserveTranslations) || (!aVal.getAttribute().getIsTranslatable()) || (aVal.getTranslations().size() <= 0))
/*      */               continue;
/*  268 */             LinkedHashSet translations = new LinkedHashSet();
/*  269 */             translations.addAll(value.getTranslations());
/*  270 */             translations.addAll(aVal.getTranslations());
/*  271 */             value.setTranslations(new Vector(translations));
/*      */           }
/*      */ 
/*  276 */           bRemovedOldVals = true;
/*      */         }
/*      */ 
/*  280 */         a_asset.addAttributeValue(value);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String getDisplayDescriptionAsString(Vector a_vecDisplayAttributes, String a_sLangCode, String a_sDelim)
/*      */   {
/*  296 */     String sDescriptions = getDisplayAttributeDescriptions(a_vecDisplayAttributes, a_sLangCode);
/*  297 */     Vector vecDescriptions = getDisplayDescriptions(sDescriptions);
/*      */ 
/*  299 */     String sDescriptionsAsString = "";
/*  300 */     Iterator it = vecDescriptions.iterator();
/*  301 */     while (it.hasNext())
/*      */     {
/*  303 */       Description desc = (Description)it.next();
/*      */ 
/*  305 */       sDescriptionsAsString = sDescriptionsAsString + desc.getDescription();
/*      */ 
/*  307 */       if (it.hasNext())
/*      */       {
/*  309 */         sDescriptionsAsString = sDescriptionsAsString + a_sDelim;
/*      */       }
/*      */     }
/*      */ 
/*  313 */     return sDescriptionsAsString;
/*      */   }
/*      */ 
/*      */   public static Vector<Description> getDisplayDescriptions(String a_sDescriptions)
/*      */   {
/*  319 */     Vector vecDescriptions = new Vector();
/*      */ 
/*  321 */     if (StringUtil.stringIsPopulated(a_sDescriptions))
/*      */     {
/*  323 */       String[] aDescriptions = a_sDescriptions.split("<>");
/*      */ 
/*  325 */       if (aDescriptions != null)
/*      */       {
/*  327 */         for (int i = 0; i < aDescriptions.length; i++)
/*      */         {
/*  329 */           if (!StringUtil.stringIsPopulated(aDescriptions[i]))
/*      */             continue;
/*  331 */           Description temp = new Description();
/*  332 */           String[] aVals = aDescriptions[i].split("!!");
/*      */ 
/*  334 */           if (aVals == null)
/*      */             continue;
/*  336 */           if (aVals.length < 1)
/*      */             continue;
/*  338 */           temp.setDescription(aVals[0]);
/*      */ 
/*  340 */           if (aVals.length > 1)
/*      */           {
/*      */             try
/*      */             {
/*  344 */               temp.setIsLink(Boolean.parseBoolean(aVals[1]));
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/*      */             }
/*      */           }
/*      */ 
/*  351 */           vecDescriptions.add(temp);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  359 */     return vecDescriptions;
/*      */   }
/*      */ 
/*      */   public static String getDisplayAttributeDescriptions(Vector a_vecDisplayAttributes, String a_sLanguageCode)
/*      */   {
/*  371 */     String sSummary = "";
/*      */ 
/*  373 */     if ((a_vecDisplayAttributes != null) && (a_vecDisplayAttributes.size() > 0))
/*      */     {
/*  375 */       for (int i = 0; i < a_vecDisplayAttributes.size(); i++)
/*      */       {
/*  377 */         DisplayAttribute dispAttribute = (DisplayAttribute)a_vecDisplayAttributes.elementAt(i);
/*  378 */         String sValue = dispAttribute.getValue(a_sLanguageCode);
/*      */ 
/*  380 */         int iLen = dispAttribute.getDisplayLength();
/*      */ 
/*  382 */         if ((iLen > 0) && (sValue != null) && (sValue.length() > iLen + 1))
/*      */         {
/*  384 */           sValue = sValue.substring(0, iLen);
/*      */ 
/*  387 */           int iPos = sValue.lastIndexOf(' ');
/*  388 */           if (iPos > 0)
/*      */           {
/*  390 */             sValue = sValue.substring(0, iPos);
/*      */           }
/*      */ 
/*  393 */           sValue = sValue + "...";
/*      */         }
/*      */ 
/*  396 */         sSummary = sSummary + "<>";
/*      */ 
/*  398 */         if (dispAttribute.getShowLabel())
/*      */         {
/*  400 */           dispAttribute.getAttribute().setLanguage(new Language(0L, "", a_sLanguageCode));
/*  401 */           sSummary = sSummary + dispAttribute.getAttribute().getLabel() + ": ";
/*      */         }
/*      */ 
/*  405 */         if (sValue == null)
/*      */         {
/*  407 */           sValue = "";
/*      */         }
/*      */ 
/*  410 */         sSummary = sSummary + sValue;
/*  411 */         sSummary = sSummary + "!!" + dispAttribute.getIsLink();
/*      */       }
/*      */     }
/*      */ 
/*  415 */     return sSummary;
/*      */   }
/*      */ 
/*      */   public static String getAPIAttributeString(Vector a_vecDisplayAttributes, String a_sLanguageCode)
/*      */   {
/*  426 */     String sSummary = "";
/*      */ 
/*  428 */     if ((a_vecDisplayAttributes != null) && (a_vecDisplayAttributes.size() > 0))
/*      */     {
/*  430 */       for (int i = 0; i < a_vecDisplayAttributes.size(); i++)
/*      */       {
/*  432 */         DisplayAttribute dispAttribute = (DisplayAttribute)a_vecDisplayAttributes.elementAt(i);
/*      */ 
/*  434 */         if ((dispAttribute == null) || (dispAttribute.getAttribute() == null))
/*      */           continue;
/*  436 */         if ((dispAttribute.getAttribute().getFieldName() != null) && (dispAttribute.getAttribute().getFieldName().equals("assetId")))
/*      */           continue;
/*  438 */         String sValue = dispAttribute.getValue(a_sLanguageCode);
/*  439 */         sSummary = sSummary + "<>";
/*      */ 
/*  441 */         dispAttribute.getAttribute().setLanguage(new Language(0L, "", a_sLanguageCode));
/*  442 */         sSummary = sSummary + dispAttribute.getAttribute().getLabel() + "!!";
/*  443 */         sSummary = sSummary + sValue;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  449 */     return sSummary;
/*      */   }
/*      */ 
/*      */   public static String getAttributeValueAsString(Asset a_asset, AttributeValue a_value, Language a_language)
/*      */   {
/*  466 */     String sValue = "";
/*      */ 
/*  468 */     if (StringUtil.stringIsPopulated(a_value.getAttribute().getFieldName()))
/*      */     {
/*  470 */       BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/*      */ 
/*  473 */       if (a_value.getAttribute().getFieldName().equals("file"))
/*      */       {
/*  475 */         sValue = a_asset.getFileLocation();
/*      */       }
/*  477 */       else if (a_value.getAttribute().getFieldName().equals("assetId"))
/*      */       {
/*  479 */         sValue = String.valueOf(a_asset.getId());
/*      */       }
/*  481 */       else if (a_value.getAttribute().getFieldName().equals("originalFilename"))
/*      */       {
/*  483 */         sValue = a_asset.getOriginalFilename();
/*      */       }
/*  485 */       else if (a_value.getAttribute().getFieldName().equals("dateAdded"))
/*      */       {
/*  487 */         if (a_asset.getDateAdded() != null)
/*      */         {
/*  489 */           sValue = format.format(a_asset.getDateAdded());
/*      */         }
/*      */       }
/*  492 */       else if (a_value.getAttribute().getFieldName().equals("dateLastModified"))
/*      */       {
/*  494 */         if (a_asset.getDateLastModified() != null)
/*      */         {
/*  496 */           sValue = format.format(a_asset.getDateLastModified());
/*      */         }
/*      */       }
/*  499 */       else if (a_value.getAttribute().getFieldName().equals("addedBy"))
/*      */       {
/*  501 */         if (StringUtil.stringIsPopulated(a_asset.getAddedByUser().getDisplayName()))
/*      */         {
/*  503 */           sValue = a_asset.getAddedByUser().getDisplayName();
/*      */         }
/*  505 */         else if (StringUtil.stringIsPopulated(a_asset.getAddedByUser().getUsername()))
/*      */         {
/*  507 */           sValue = a_asset.getAddedByUser().getUsername();
/*      */         }
/*      */       }
/*  510 */       else if (a_value.getAttribute().getFieldName().equals("lastModifiedBy"))
/*      */       {
/*  512 */         if (a_asset.getLastModifiedByUser() != null)
/*      */         {
/*  514 */           if (StringUtil.stringIsPopulated(a_asset.getLastModifiedByUser().getDisplayName()))
/*      */           {
/*  516 */             sValue = a_asset.getLastModifiedByUser().getDisplayName();
/*      */           }
/*  518 */           else if (StringUtil.stringIsPopulated(a_asset.getLastModifiedByUser().getUsername()))
/*      */           {
/*  520 */             sValue = a_asset.getLastModifiedByUser().getUsername();
/*      */           }
/*      */         }
/*      */       }
/*  524 */       else if (a_value.getAttribute().getFieldName().equals("orientation"))
/*      */       {
/*  526 */         if (a_asset.getOrientation() == 2)
/*      */         {
/*  528 */           sValue = "Portrait";
/*      */         }
/*  530 */         else if (a_asset.getOrientation() == 1)
/*      */         {
/*  532 */           sValue = "Landscape";
/*      */         }
/*      */         else
/*      */         {
/*  536 */           sValue = "Square";
/*      */         }
/*      */       }
/*  539 */       else if (a_value.getAttribute().getFieldName().equals("price"))
/*      */       {
/*  541 */         if (a_asset.getPrice() != null)
/*      */         {
/*  543 */           if (AssetBankSettings.getUsePriceBands())
/*      */           {
/*  545 */             if (a_asset.getPrice().getAmount() < 0L)
/*      */             {
/*  547 */               sValue = "Free";
/*      */             }
/*      */             else
/*      */             {
/*  551 */               sValue = "From: " + a_asset.getPrice().getDisplayAmountTextFormat();
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  556 */             sValue = a_asset.getPrice().getDisplayAmountTextFormat();
/*      */           }
/*      */         }
/*      */       }
/*  560 */       else if (a_value.getAttribute().getFieldName().equals("size"))
/*      */       {
/*  562 */         double dFileSize = a_asset.getFileSizeInBytes() / 1048576.0D;
/*  563 */         String sUnits = "Mb";
/*      */ 
/*  565 */         if (dFileSize <= 1.0D)
/*      */         {
/*  567 */           sUnits = "Kb";
/*  568 */           dFileSize *= 1024.0D;
/*      */         }
/*      */ 
/*  572 */         dFileSize = Math.rint(dFileSize * 100.0D) / 100.0D;
/*  573 */         sValue = dFileSize + sUnits;
/*      */ 
/*  576 */         if (a_asset.getIsImage())
/*      */         {
/*  578 */           sValue = sValue + "<br/>" + ((ImageAsset)a_asset).getWidth() + " x " + ((ImageAsset)a_asset).getHeight() + "px";
/*      */         }
/*      */ 
/*      */       }
/*  581 */       else if (!a_value.getAttribute().getFieldName().equals("rating"));
/*      */     }
/*      */     else
/*      */     {
/*  588 */       sValue = getFlexibleAttributeValueAsString(a_value, a_language);
/*      */     }
/*      */ 
/*  591 */     return sValue;
/*      */   }
/*      */ 
/*      */   public static String getFlexibleAttributeValueAsString(AttributeValue a_value, Language a_language)
/*      */   {
/*  604 */     String sValue = "";
/*      */ 
/*  606 */     if (a_value.getAttribute().getIsKeywordPicker())
/*      */     {
/*  608 */       if (a_value.getKeywordCategories() != null)
/*      */       {
/*  610 */         for (int a = 0; a < a_value.getKeywordCategories().size(); a++)
/*      */         {
/*  612 */           sValue = sValue + ((Category)a_value.getKeywordCategories().elementAt(a)).getName();
/*      */ 
/*  614 */           if (a == a_value.getKeywordCategories().size() - 1)
/*      */             continue;
/*  616 */           sValue = sValue + ", ";
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  621 */     else if ((a_value.getAttribute() != null) && (a_value.getAttribute().getIsCheckList()) && (a_value.getAttribute().getListOptionValues() != null))
/*      */     {
/*  623 */       for (int i = 0; i < a_value.getAttribute().getListOptionValues().size(); i++)
/*      */       {
/*  625 */         AttributeValue val = (AttributeValue)a_value.getAttribute().getListOptionValues().elementAt(i);
/*  626 */         sValue = sValue + val.getValue();
/*  627 */         if (i >= a_value.getAttribute().getListOptionValues().size() - 1)
/*      */           continue;
/*  629 */         sValue = sValue + ", ";
/*      */       }
/*      */ 
/*      */     }
/*  633 */     else if (a_value.getAttribute().getIsExternalDictionary())
/*      */     {
/*  636 */       sValue = a_value.getAdditionalValue(a_language);
/*      */     }
/*      */     else
/*      */     {
/*  641 */       sValue = a_value.getValue(a_language);
/*      */     }
/*      */ 
/*  644 */     return sValue;
/*      */   }
/*      */ 
/*      */   public static void populateAttributeValuesFromRequest(HttpServletRequest a_request, Bn2Form a_form, Vector a_vecAttributeValues, Vector a_vecAllAttributes, boolean a_bAlreadyAddedDateError, boolean a_bCheckMandatory, boolean a_bIgnoreKeywords, DBTransaction a_dbTransaction, TaxonomyManager a_taxonomyManager, ListManager a_listManager, Language a_language, boolean a_bSavingFilter)
/*      */     throws Bn2Exception
/*      */   {
/*  671 */     populateAttributeValuesFromRequest(a_request, a_form, a_vecAttributeValues, a_vecAllAttributes, a_bAlreadyAddedDateError, a_bCheckMandatory ? new SingleUploadMandatoryPredicate() : new NoCheckMandatoryPredicate(), a_bIgnoreKeywords, a_dbTransaction, a_taxonomyManager, a_listManager, a_language, a_bSavingFilter);
/*      */   }
/*      */ 
/*      */   public static void populateAttributeValuesFromRequest(HttpServletRequest a_request, Bn2Form a_form, Vector a_vecAttributeValues, Vector a_vecAllAttributes, boolean a_bAlreadyAddedDateError, MandatoryPredicate a_mandPred, boolean a_bIgnoreKeywords, DBTransaction a_dbTransaction, TaxonomyManager a_taxonomyManager, ListManager a_listManager, Language a_language, boolean a_bSavingFilter)
/*      */     throws Bn2Exception
/*      */   {
/*  708 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*      */ 
/*  710 */     HashMap hmWriteableAttributeIds = userProfile.getWriteableAttributeIdsAsHashMap();
/*      */ 
/*  713 */     for (int i = 0; i < a_vecAllAttributes.size(); i++)
/*      */     {
/*  715 */       Attribute att = (Attribute)a_vecAllAttributes.get(i);
/*      */ 
/*  718 */       att.setLanguage(a_language);
/*      */ 
/*  720 */       String sParamName = "attribute_" + att.getId();
/*      */ 
/*  723 */       String[] asOptionValues = a_request.getParameterValues(sParamName);
/*      */ 
/*  727 */       String sAdditionalParamName = "attribute_additional_" + att.getId();
/*  728 */       String sAdditionalValue = a_request.getParameter(sAdditionalParamName);
/*      */ 
/*  732 */       if ((att.getIsCheckList()) || (att.getIsOptionList()))
/*      */       {
/*  734 */         if ((att.getStatic()) || (att.getIsVisible()))
/*      */         {
/*  736 */           if (((asOptionValues == null) || (asOptionValues.length == 0)) && (a_mandPred.isMandatory(att)) && ((userProfile.getIsAdmin()) || (hmWriteableAttributeIds.containsKey(new Long(att.getId())))) && (!StringUtil.stringIsPopulated(sAdditionalValue)))
/*      */           {
/*  741 */             a_form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationForField", userProfile.getCurrentLanguage()).getBody() + " " + att.getLabel());
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  746 */       boolean bAddedValue = false;
/*      */ 
/*  748 */       if ((StringUtil.stringIsPopulated(sAdditionalValue)) && (!att.getIsExternalDictionary()))
/*      */       {
/*  750 */         AttributeValue attVal = new AttributeValue();
/*  751 */         attVal.setAttribute(att);
/*  752 */         attVal.setValue(sAdditionalValue);
/*  753 */         attVal.setForceAdd(true);
/*      */ 
/*  756 */         att.setValue(attVal);
/*      */ 
/*  758 */         a_vecAttributeValues.add(attVal);
/*      */ 
/*  760 */         if (att.getIsDropdownList())
/*      */         {
/*  762 */           bAddedValue = true;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  767 */       for (int iParamIndex = 0; (asOptionValues != null) && (iParamIndex < asOptionValues.length); iParamIndex++)
/*      */       {
/*  769 */         String sParamVal = asOptionValues[iParamIndex];
/*  770 */         long lAttributeValueId = 0L;
/*  771 */         Vector vecKeywords = null;
/*      */ 
/*  773 */         if (sParamVal == null) {
/*      */           continue;
/*      */         }
/*  776 */         if (sParamVal.trim().length() == 0)
/*      */         {
/*  778 */           if ((att.getStatic()) || (att.getIsVisible()))
/*      */           {
/*  780 */             if ((a_mandPred.isMandatory(att)) && ((userProfile.getIsAdmin()) || (hmWriteableAttributeIds.containsKey(new Long(att.getId())))) && ((sAdditionalValue == null) || (sAdditionalValue.trim().length() == 0)))
/*      */             {
/*  784 */               a_form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationForField", userProfile.getCurrentLanguage()).getBody() + " " + att.getLabel());
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  791 */           if ((att.getStatic()) || (att.getIsVisible()))
/*      */           {
/*  793 */             if (((att.getIsDatepicker()) && (!BrightDate.validateFormat(sParamVal))) || ((att.getIsDateTime()) && (!BrightDateTime.validateFormat(sParamVal))))
/*      */             {
/*  795 */               if (!a_bAlreadyAddedDateError)
/*      */               {
/*  797 */                 a_form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", userProfile.getCurrentLanguage()).getBody());
/*  798 */                 a_bAlreadyAddedDateError = true;
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  804 */           if ((att.getIsKeywordPicker()) && (!a_bIgnoreKeywords))
/*      */           {
/*  806 */             vecKeywords = a_taxonomyManager.getKeywordStatusList(a_dbTransaction, sParamVal, att.getTreeId(), a_language, true);
/*      */ 
/*  811 */             if ((AssetBankSettings.getKeywordAutoAdd()) && (!LanguageConstants.k_defaultLanguage.equals(a_language)))
/*      */             {
/*  813 */               Iterator it = vecKeywords.iterator();
/*  814 */               while (it.hasNext())
/*      */               {
/*  816 */                 Keyword keyword = (Keyword)it.next();
/*  817 */                 if (!keyword.getInMasterList())
/*      */                 {
/*  819 */                   Category.Translation translation = keyword.createTranslation(a_language);
/*  820 */                   translation.setName(keyword.getName());
/*  821 */                   translation.setDescription(keyword.getDescription());
/*  822 */                   keyword.getTranslations().add(translation);
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*  828 */             a_taxonomyManager.checkKeywordAutoAdd(a_dbTransaction, vecKeywords, att.getTreeId(), AssetBankSettings.getKeywordAutoAdd());
/*      */ 
/*  831 */             if ((!LanguageConstants.k_defaultLanguage.equals(a_language)) || (!AssetBankSettings.getKeywordAutoAdd()))
/*      */             {
/*  834 */               Iterator it = vecKeywords.iterator();
/*  835 */               while (it.hasNext())
/*      */               {
/*  837 */                 Keyword keyword = (Keyword)it.next();
/*      */ 
/*  839 */                 if (!keyword.getInMasterList())
/*      */                 {
/*      */                   String sMessage;
/*  842 */                   if (keyword.getKeywordsForThisSynonym().size() > 0)
/*      */                   {
/*  845 */                     sMessage = a_listManager.getListItem(a_dbTransaction, "keywordErrorKeywordIsSynonym", userProfile.getCurrentLanguage()).getBody().replaceFirst("%", keyword.getName()) + " ";
/*      */ 
/*  847 */                     Iterator itSynKeywords = keyword.getKeywordsForThisSynonym().iterator();
/*      */ 
/*  850 */                     while (itSynKeywords.hasNext())
/*      */                     {
/*  852 */                       Keyword synKeyword = (Keyword)itSynKeywords.next();
/*  853 */                       sMessage = sMessage + synKeyword.getName();
/*      */ 
/*  855 */                       if (itSynKeywords.hasNext())
/*      */                       {
/*  857 */                         sMessage = sMessage + ", ";
/*      */                       }
/*      */                     }
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*  864 */                     sMessage = a_listManager.getListItem(a_dbTransaction, "keywordErrorKeywordNotInList", userProfile.getCurrentLanguage()).getBody().replaceFirst("%", keyword.getName());
/*      */                   }
/*      */ 
/*  867 */                   a_form.addError(sMessage);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  874 */         AttributeValue attVal = new AttributeValue();
/*  875 */         attVal.setAttribute(att);
/*      */ 
/*  878 */         if ((StringUtils.isNotEmpty(sParamVal)) && (att.getIsNumeric()))
/*      */         {
/*      */           try
/*      */           {
/*  882 */             BigDecimal bd = new BigDecimal(sParamVal);
/*      */ 
/*  885 */             if (bd.scale() > att.getMaxDecimalPlaces())
/*      */             {
/*  887 */               if (att.getMaxDecimalPlaces() == 0)
/*      */               {
/*  889 */                 a_form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationNotWholeNumber", userProfile.getCurrentLanguage()).getBody().replaceFirst("%", att.getLabel()));
/*      */               }
/*      */               else
/*      */               {
/*  893 */                 String sError = a_listManager.getListItem(a_dbTransaction, "failedValidationFractionDigits", userProfile.getCurrentLanguage()).getBody();
/*  894 */                 sError = sError.replaceFirst("%", att.getLabel());
/*  895 */                 sError = sError.replaceFirst("%", String.valueOf(att.getMaxDecimalPlaces()));
/*  896 */                 a_form.addError(sError);
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (NumberFormatException e)
/*      */           {
/*  902 */             a_form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationNumberFormatField", userProfile.getCurrentLanguage()).getBody().replaceFirst("%", att.getLabel()));
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  907 */         if ((att.getIsDropdownList()) || (att.getIsCheckList()) || (att.getIsOptionList()))
/*      */         {
/*  910 */           if ((!StringUtil.stringIsPopulated(sAdditionalValue)) || (!att.getIsDropdownList()))
/*      */           {
/*  912 */             if (sParamVal.trim().length() > 0)
/*      */             {
/*  914 */               lAttributeValueId = Long.parseLong(sParamVal);
/*  915 */               attVal.setId(lAttributeValueId);
/*      */             }
/*      */             else
/*      */             {
/*  919 */               attVal.setId(0L);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*  924 */         else if (att.getIsKeywordPicker())
/*      */         {
/*  926 */           LanguageUtils.setLanguageOnAll(vecKeywords, a_language);
/*  927 */           attVal.setKeywordCategories(vecKeywords);
/*      */         }
/*  929 */         else if (att.getIsExternalDictionary())
/*      */         {
/*  931 */           attVal.setValue(sParamVal);
/*  932 */           attVal.setAdditionalValue(sAdditionalValue);
/*      */         }
/*      */         else
/*      */         {
/*  937 */           attVal.setValue(sParamVal);
/*      */ 
/*  940 */           Vector translations = att.getTranslations();
/*  941 */           Iterator itTranslations = translations.iterator();
/*      */ 
/*  943 */           while (itTranslations.hasNext())
/*      */           {
/*  945 */             Attribute.Translation translation = (Attribute.Translation)itTranslations.next();
/*      */ 
/*  947 */             String sParamNameTranslation = "attribute_" + att.getId() + "_" + translation.getLanguage().getId();
/*      */ 
/*  950 */             String value = a_request.getParameter(sParamNameTranslation);
/*      */             //AttributeValue tmp1385_1383 = attVal; tmp1385_1383.getClass(); AttributeValue.Translation valueTranslation = new AttributeValue.Translation(tmp1385_1383, translation.getLanguage());
/*  952 */              AttributeValue.Translation valueTranslation = attVal.new Translation(translation.getLanguage());
                       valueTranslation.setValue(value);
/*  953 */             attVal.getTranslations().add(valueTranslation);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  958 */         if (!bAddedValue)
/*      */         {
/*  960 */           a_vecAttributeValues.add(attVal);
/*      */         }
/*      */ 
/*  964 */         if ((att.getIsCheckList()) || (att.getIsOptionList()) || (att.getIsDropdownList()))
/*      */         {
/*  967 */           for (int j = 0; j < att.getListOptionValues().size(); j++)
/*      */           {
/*  969 */             AttributeValue av = (AttributeValue)att.getListOptionValues().get(j);
/*  970 */             if (av.getId() != lAttributeValueId)
/*      */               continue;
/*  972 */             attVal.setActionOnAssetId(av.getActionOnAssetId());
/*      */ 
/*  974 */             if ((att.getIsCheckList()) || (att.getIsOptionList()))
/*      */             {
/*  976 */               av.setIsSelected(true); break;
/*      */             }
/*      */ 
/*  980 */             att.setValue(attVal);
/*      */ 
/*  982 */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  988 */           att.setValue(attVal);
/*      */         }
/*      */ 
/*  993 */         if (!att.getIsAutoincrement())
/*      */           continue;
/*  995 */         String sValue = null;
/*      */         try
/*      */         {
/*  998 */           if (a_bSavingFilter)
/*      */           {
/* 1002 */             if (StringUtil.stringIsPopulated(att.getValue().getValue()))
/*      */             {
/* 1004 */               AttributeValue.getAutoincrementNumberValue(att.getPrefix(), sValue);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (NumberFormatException e)
/*      */         {
/* 1010 */           a_form.addError(a_listManager.getListItem(a_dbTransaction, "failedValidationAutoincrementNumber", userProfile.getCurrentLanguage()).getBody());
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1014 */           GlobalApplication.getInstance().getLogger().error("AttributeUtil.populateAttributeValuesFromRequest: Unable to validate auto increment field " + e.getMessage());
/* 1015 */           throw new Bn2Exception("AttributeUtil.populateAttributeValuesFromRequest: Unable to validate auto increment field " + e.getMessage(), e);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static AttributeValue getAttributeValueForAttribute(Vector a_vecAttributeValues, String a_sAttributeId)
/*      */   {
/* 1041 */     if ((a_vecAttributeValues != null) && (a_vecAttributeValues.size() > 0))
/*      */     {
/*      */       try
/*      */       {
/* 1046 */         long lId = new Long(a_sAttributeId).longValue();
/*      */ 
/* 1048 */         for (int i = 0; i < a_vecAttributeValues.size(); i++)
/*      */         {
/* 1050 */           AttributeValue temp = (AttributeValue)a_vecAttributeValues.elementAt(i);
/*      */ 
/* 1052 */           if (temp.getAttribute().getId() == lId)
/*      */           {
/* 1054 */             return temp;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1061 */         GlobalApplication.getInstance().getLogger().error("Filter.getAttributeValueForAttribute: Unable to get attribute value for attribute with id : " + a_sAttributeId + " : " + e.getMessage());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1066 */     return new AttributeValue();
/*      */   }
/*      */ 
/*      */   public static boolean getAttributeHasValue(String a_sIdValuePair, Vector a_vecAttributeValues)
/*      */   {
/*      */     try
/*      */     {
/* 1087 */       String[] aPair = a_sIdValuePair.split("%%");
/* 1088 */       long lId = new Long(aPair[0]).longValue();
/* 1089 */       String sCheckValue = "%%" + aPair[1] + "%%";
/*      */ 
/* 1091 */       String sCombinedValues = getCombinedValuesForAttribute(lId, a_vecAttributeValues);
/*      */ 
/* 1093 */       if ((!sCombinedValues.equals("%%")) && (sCombinedValues.indexOf(sCheckValue) > -1))
/*      */       {
/* 1096 */         return true;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1102 */       GlobalApplication.getInstance().getLogger().error("Filter.getAttributeHasValue: " + e.getMessage());
/*      */     }
/*      */ 
/* 1105 */     return false;
/*      */   }
/*      */ 
/*      */   private static String getCombinedValuesForAttribute(long a_lId, Vector a_vecAttributeValues)
/*      */   {
/* 1122 */     String sValues = "%%";
/*      */ 
/* 1125 */     if ((a_vecAttributeValues != null) && (a_vecAttributeValues.size() > 0))
/*      */     {
/* 1128 */       for (int i = 0; i < a_vecAttributeValues.size(); i++)
/*      */       {
/* 1130 */         AttributeValue temp = (AttributeValue)a_vecAttributeValues.elementAt(i);
/*      */ 
/* 1132 */         if (temp.getAttribute().getId() != a_lId)
/*      */           continue;
/* 1134 */         sValues = sValues + temp.getValue() + "%%";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1140 */     return sValues;
/*      */   }
/*      */ 
/*      */   public static Vector<Long> getWritableRelevantAttributeIds(DBTransaction a_dbTransaction, AssetEntityManager a_assetEntityManager, Asset a_asset, ABUserProfile a_userProfile, boolean a_bOmitWhenDataFromChildren)
/*      */     throws Bn2Exception
/*      */   {
/* 1159 */     Vector attIds = null;
/*      */ 
/* 1161 */     if (a_asset.getEntity().getId() > 0L)
/*      */     {
/* 1163 */       attIds = a_assetEntityManager.getAttributeIdsForEntity(a_dbTransaction, a_asset.getEntity().getId(), a_bOmitWhenDataFromChildren);
/*      */     }
/*      */ 
/* 1167 */     if (!a_userProfile.getIsAdmin())
/*      */     {
/* 1169 */       if (attIds != null)
/*      */       {
/* 1171 */         attIds = CollectionUtil.intersection(attIds, a_userProfile.getWriteableAttributeIds());
/*      */       }
/*      */       else
/*      */       {
/* 1175 */         attIds = a_userProfile.getWriteableAttributeIds();
/*      */       }
/*      */     }
/* 1178 */     return attIds;
/*      */   }
/*      */ 
/*      */   public static void markRelevantToEntity(Vector a_vecAttributes, DBTransaction a_dbTransaction, AssetEntityManager a_assetEntityManager, long a_lEntityId)
/*      */     throws Bn2Exception
/*      */   {
/* 1196 */     if ((a_lEntityId > 0L) && (a_vecAttributes != null))
/*      */     {
/* 1199 */       Vector attIds = a_assetEntityManager.getAttributeIdsForEntity(a_dbTransaction, a_lEntityId, true);
/*      */ 
/* 1201 */       Iterator it = a_vecAttributes.iterator();
/*      */ 
/* 1203 */       while (it.hasNext())
/*      */       {
/* 1205 */         Attribute att = (Attribute)it.next();
/*      */ 
/* 1208 */         if (attIds.contains(new Long(att.getId())))
/*      */         {
/* 1210 */           att.setIsRelevant(true);
/*      */         }
/*      */         else
/*      */         {
/* 1214 */           att.setIsRelevant(false);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean hasActionableAttributeValue(Vector a_vAtttributeValues)
/*      */   {
/* 1229 */     boolean bResult = false;
/*      */ 
/* 1231 */     for (int i = 0; i < a_vAtttributeValues.size(); i++)
/*      */     {
/* 1233 */       AttributeValue value = (AttributeValue)a_vAtttributeValues.get(i);
/* 1234 */       if (value.getActionOnAssetId() <= 0L)
/*      */         continue;
/* 1236 */       bResult = true;
/* 1237 */       break;
/*      */     }
/*      */ 
/* 1241 */     return bResult;
/*      */   }
/*      */ 
/*      */   public static void nullifyAttributeValue(AttributeValue a_value)
/*      */   {
/* 1250 */     if (a_value != null)
/*      */     {
/* 1252 */       a_value.setValue(null);
/*      */ 
/* 1254 */       if (a_value.getTranslations() != null)
/*      */       {
/* 1256 */         Iterator itTranslations = a_value.getTranslations().iterator();
/*      */ 
/* 1258 */         while (itTranslations.hasNext())
/*      */         {
/* 1260 */           AttributeValue.Translation translation = (AttributeValue.Translation)itTranslations.next();
/* 1261 */           translation.setValue(null);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Vector mergeAttributeData(Vector a_vBaseAttributeValues, Vector a_vAttributeValuesToMerge, String a_sDelimiter, boolean a_bMergeTextareasOnly, Set a_attributeIdsToExclude, boolean a_bSelectedAssetsOnly)
/*      */   {
/* 1292 */     HashMap hmAttValuesToMerge = new HashMap();
/*      */ 
/* 1295 */     if ((a_vAttributeValuesToMerge != null) && (a_vAttributeValuesToMerge.size() > 0))
/*      */     {
/* 1298 */       for (int i = 0; i < a_vAttributeValuesToMerge.size(); i++)
/*      */       {
/* 1300 */         AttributeValue temp = (AttributeValue)a_vAttributeValuesToMerge.get(i);
/* 1301 */         Long id = Long.valueOf(temp.getAttribute().getId());
/*      */ 
/* 1303 */         if ((!StringUtils.isNotEmpty(temp.getValue())) || (a_attributeIdsToExclude.contains(id)) || ((a_bSelectedAssetsOnly) && (!temp.getAttribute().getIncludeInSearchForChild())))
/*      */           continue;
/* 1305 */         if (hmAttValuesToMerge.get(id) == null)
/*      */         {
/* 1307 */           hmAttValuesToMerge.put(id, new Vector());
/*      */         }
/* 1309 */         ((Vector)hmAttValuesToMerge.get(id)).add(temp);
/*      */       }
/*      */ 
/* 1313 */       if (a_vBaseAttributeValues.size() > 0)
/*      */       {
/* 1316 */         for (int i = 0; i < a_vBaseAttributeValues.size(); i++)
/*      */         {
/* 1318 */           AttributeValue temp = (AttributeValue)a_vBaseAttributeValues.get(i);
/* 1319 */           Long id = Long.valueOf(temp.getAttribute().getId());
/*      */ 
/* 1321 */           if (!hmAttValuesToMerge.containsKey(id))
/*      */             continue;
/* 1323 */           Vector vecToMerge = (Vector)hmAttValuesToMerge.get(id);
/*      */ 
/* 1325 */           if (vecToMerge != null)
/*      */           {
/* 1327 */             for (int x = 0; x < vecToMerge.size(); x++)
/*      */             {
/* 1329 */               AttributeValue toMerge = (AttributeValue)vecToMerge.elementAt(x);
/*      */ 
/* 1331 */               String sNewVal = temp.getValue();
/*      */ 
/* 1333 */               if ((StringUtils.isNotEmpty(sNewVal)) && ((temp.getAttribute().getIsTextarea()) || ((!a_bMergeTextareasOnly) && (temp.getAttribute().getIsTextfield()))))
/*      */               {
/* 1337 */                 sNewVal = sNewVal + a_sDelimiter + toMerge.getValue();
/*      */               }
/* 1339 */               else if (StringUtils.isEmpty(sNewVal))
/*      */               {
/* 1341 */                 sNewVal = toMerge.getValue();
/*      */               }
/*      */ 
/* 1344 */               temp.setValue(sNewVal);
/*      */             }
/*      */           }
/*      */ 
/* 1348 */           hmAttValuesToMerge.remove(id);
/*      */         }
/*      */ 
/* 1353 */         Collection c = hmAttValuesToMerge.values();
/* 1354 */         if (c != null)
/*      */         {
/* 1356 */           Iterator it = c.iterator();
/* 1357 */           while (it.hasNext())
/*      */           {
/* 1359 */             Vector vecValues = (Vector)it.next();
/* 1360 */             if (vecValues != null)
/*      */             {
/* 1362 */               a_vBaseAttributeValues.addAll(vecValues);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1368 */     return a_vBaseAttributeValues;
/*      */   }
/*      */ 
/*      */   public static boolean getDataFromChildrenSet(Vector a_vAttributeValues)
/*      */   {
/* 1379 */     for (int i = 0; i < a_vAttributeValues.size(); i++)
/*      */     {
/* 1381 */       if (((AttributeValue)a_vAttributeValues.get(i)).getAttribute().getDataFromChildren())
/*      */       {
/* 1383 */         return true;
/*      */       }
/*      */     }
/* 1386 */     return false;
/*      */   }
/*      */ 
/*      */   public static void sortAttributesByLabel(Vector a_vAttributes)
/*      */   {
/* 1395 */     Collections.sort(a_vAttributes, new Comparator()
/*      */     {
/*      */       public int compare(Object o1, Object o2) {
/* 1398 */         if (((o1 instanceof Attribute)) && ((o2 instanceof Attribute)))
/*      */         {
/* 1400 */           return ((Attribute)o1).getLabel().compareToIgnoreCase(((Attribute)o2).getLabel());
/*      */         }
/* 1402 */         return 0;
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   public static void validateInputMasks(DBTransaction a_transaction, Vector a_vAttributeValues, Bn2Form a_form, ListManager a_listManager, UserProfile a_userProfile)
/*      */     throws Bn2Exception
/*      */   {
/* 1411 */     if (a_vAttributeValues != null)
/*      */     {
/* 1413 */       for (int i = 0; i < a_vAttributeValues.size(); i++)
/*      */       {
/* 1415 */         AttributeValue value = (AttributeValue)a_vAttributeValues.elementAt(i);
/*      */ 
/* 1417 */         if ((value == null) || (value.getAttribute() == null) || (!StringUtil.stringIsPopulated(value.getValue())) || (!StringUtil.stringIsPopulated(value.getAttribute().getInputMask())))
/*      */         {
/*      */           continue;
/*      */         }
/*      */ 
/* 1423 */         Pattern pat = Pattern.compile(value.getAttribute().getInputMask());
/* 1424 */         Matcher match = pat.matcher(value.getValue());
/*      */ 
/* 1426 */         if (match.matches())
/*      */           continue;
/* 1428 */         a_form.addError(a_listManager.getListItem(a_transaction, "failedValidationValid", a_userProfile.getCurrentLanguage()).getBody() + " " + value.getAttribute().getLabel());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String getIndexFieldForDateStaticFieldName(String a_sField)
/*      */   {
/* 1438 */     if (a_sField.equals("dateAdded"))
/*      */     {
/* 1440 */       return "f_long_added";
/*      */     }
/* 1442 */     if (a_sField.equals("dateLastModified"))
/*      */     {
/* 1444 */       return "f_long_modified";
/*      */     }
/* 1446 */     if (a_sField.equals("dateLastDownloaded"))
/*      */     {
/* 1448 */       return "f_long_lastDownload";
/*      */     }
/* 1450 */     return null;
/*      */   }
/*      */ 
/*      */   public static boolean getPopulatingFromChild(Asset a_asset)
/*      */   {
/* 1455 */     boolean bPopFromChild = false;
/* 1456 */     if (a_asset != null)
/*      */     {
/* 1458 */       if (a_asset.getAttributeValues() != null)
/*      */       {
/* 1460 */         for (int i = 0; i < a_asset.getAttributeValues().size(); i++)
/*      */         {
/* 1462 */           AttributeValue value = (AttributeValue)a_asset.getAttributeValues().elementAt(i);
/* 1463 */           if ((value == null) || (value.getAttribute() == null) || (!value.getAttribute().getDataFromChildren()))
/*      */             continue;
/* 1465 */           bPopFromChild = true;
/* 1466 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1471 */     return bPopFromChild;
/*      */   }
/*      */ 
/*      */   public static String getKeywordsStringForAttributeValue(AttributeValue a_value, Language a_language)
/*      */   {
/* 1488 */     Collection keywords = getKeywordsCollectionForAttributeValue(a_value, a_language);
/* 1489 */     String sDelimiter = a_value.getAttribute().getDefaultTokenDelimiter();
/* 1490 */     return StringUtil.convertStringCollectionToString(keywords, sDelimiter);
/*      */   }
/*      */ 
/*      */   public static Collection<String> getKeywordsCollectionForAttributeValue(AttributeValue a_value, Language a_language)
/*      */   {
/* 1500 */     Attribute attribute = a_value.getAttribute();
/* 1501 */     if (!attribute.getIsKeywordPicker())
/*      */     {
/* 1503 */       throw new IllegalStateException("getKeywordsCollectionForAttributeValue only works for keyword picker attributes (not type " + attribute.getTypeId() + " attributes");
/*      */     }
/*      */ 
/* 1510 */     Pattern tokenDelimiterPattern = Pattern.compile(attribute.getTokenDelimiterRegex());
/*      */ 
/* 1512 */     Vector<Category> keywordCategories = a_value.getKeywordCategories();
/* 1513 */     Collection keywords = new ArrayList();
/*      */ 
/* 1515 */     if (keywordCategories != null)
/*      */     {
/* 1517 */       for (Category keywordCat : keywordCategories)
/*      */       {
/* 1521 */         keywords.add(keywordCat.getName(a_language));
/* 1522 */         addSynonyms(keywords, a_language, keywordCat, tokenDelimiterPattern);
/*      */ 
/* 1525 */         if (keywordCat.getAncestors() != null)
/*      */         {
/* 1527 */           for (int j = 0; j < keywordCat.getAncestors().size(); j++)
/*      */           {
/* 1529 */             Category ansKeywordCat = (Category)keywordCat.getAncestors().elementAt(j);
/* 1530 */             keywords.add(ansKeywordCat.getName(a_language));
/* 1531 */             addSynonyms(keywords, a_language, ansKeywordCat, tokenDelimiterPattern);
/*      */           }
/*      */ 
/* 1535 */           keywords.add(keywordCat.getFullName());
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1540 */     return keywords;
/*      */   }
/*      */ 
/*      */   private static void addSynonyms(Collection<String> a_keywords, Language a_language, Category a_keywordCategory, Pattern a_tokenDelimiterPattern)
/*      */   {
/* 1545 */     String description = a_keywordCategory.getDescription(a_language);
/* 1546 */     if (StringUtils.isNotEmpty(description))
/*      */     {
/* 1548 */       String[] synonyms = a_tokenDelimiterPattern.split(description);
/* 1549 */       a_keywords.addAll(Arrays.asList(synonyms));
/*      */     }
/*      */   }
/*      */ 
/*      */   public static DataLookupRequest checkForDataLookup(HttpServletRequest a_request)
/*      */   {
/* 1556 */     Enumeration e = a_request.getParameterNames();
/* 1557 */     DataLookupRequest dlr = null;
/* 1558 */     while (e.hasMoreElements())
/*      */     {
/* 1560 */       String sParamName = (String)e.nextElement();
/* 1561 */       if (sParamName.startsWith("dataLookupAttribute"))
/*      */       {
/* 1564 */         String[] aPair = sParamName.split(":");
/*      */         try
/*      */         {
/* 1567 */           dlr = new DataLookupRequest();
/* 1568 */           dlr.setAttributeId(Long.parseLong(aPair[1]));
/* 1569 */           dlr.setRequest(a_request);
/*      */         }
/*      */         catch (Exception ex) {
/*      */         }
/*      */       }
/*      */     }
/* 1575 */     return dlr;
/*      */   }
/*      */ 
/*      */   public static String packExternalDictionaryIDsAndValues(String a_sIDs, String a_sValues)
/*      */   {
/* 1593 */     if (a_sIDs == null) a_sIDs = "";
/* 1594 */     if (a_sValues == null) a_sValues = "";
/*      */ 
/* 1596 */     if ((a_sIDs.equals("")) && (a_sValues.equals("")))
/*      */     {
/* 1598 */       return null;
/*      */     }
/* 1600 */     return StringUtil.pack('_', '\\', new String[] { a_sIDs, a_sValues });
/*      */   }
/*      */ 
/*      */   public static String[] unpackExternalDictionaryIDsAndValues(String a_sPacked)
/*      */   {
/* 1612 */     if (StringUtils.isEmpty(a_sPacked))
/*      */     {
/* 1614 */       return new String[] { null, null };
/*      */     }
/*      */ 
/* 1618 */     return StringUtil.unpack('_', '\\', a_sPacked);
/*      */   }
/*      */ 
/*      */   public static Vector<Long> getControlPanelSelectedAttributes(HttpServletRequest a_request)
/*      */   {
/* 1627 */     Vector vecAttributeIds = new Vector();
/*      */ 
/* 1630 */     Enumeration enumParams = a_request.getParameterNames();
/*      */ 
/* 1632 */     while (enumParams.hasMoreElements())
/*      */     {
/* 1634 */       String sParamName = (String)enumParams.nextElement();
/*      */ 
/* 1637 */       if (sParamName.startsWith("requiredAttribute_"))
/*      */       {
/* 1640 */         String sValue = a_request.getParameter(sParamName);
/*      */ 
/* 1643 */         if ((sValue != null) && (sValue.length() > 0))
/*      */         {
/* 1647 */           String sAttId = sParamName.substring("requiredAttribute_".length(), sParamName.length());
/*      */ 
/* 1650 */           Long lAttId = new Long(sAttId.trim());
/*      */ 
/* 1652 */           vecAttributeIds.add(lAttId);
/*      */         }
/*      */       }
/*      */     }
/* 1656 */     return vecAttributeIds;
/*      */   }
/*      */ 
/*      */   public static String getIndexFieldName(Attribute a_attribute)
/*      */   {
/* 1668 */     String sField = null;
/*      */ 
/* 1670 */     if (!a_attribute.getStatic())
/*      */     {
/* 1672 */       sField = getFlexibleAttributeIndexFieldName(a_attribute);
/*      */     }
/* 1677 */     else if (a_attribute.getFieldName().equals("file"))
/*      */     {
/* 1679 */       sField = "f_filename";
/*      */     }
/* 1681 */     else if (a_attribute.getFieldName().equals("originalFilename"))
/*      */     {
/* 1683 */       sField = "f_filename";
/*      */     }
/* 1685 */     else if (a_attribute.getFieldName().equals("assetId"))
/*      */     {
/* 1687 */       sField = "f_id";
/*      */     }
/* 1689 */     else if (a_attribute.getFieldName().equals("addedBy"))
/*      */     {
/* 1691 */       sField = "f_addedBy";
/*      */     }
/* 1693 */     else if (a_attribute.getFieldName().equals("size"))
/*      */     {
/* 1695 */       sField = "f_long_fileSize";
/*      */     }
/* 1697 */     else if (a_attribute.getFieldName().equals("orientation"))
/*      */     {
/* 1699 */       sField = "f_orientation";
/*      */     }
/* 1701 */     else if (a_attribute.getFieldName().equals("dateAdded"))
/*      */     {
/* 1703 */       sField = "f_long_added";
/*      */     }
/* 1705 */     else if (a_attribute.getFieldName().equals("dateLastModified"))
/*      */     {
/* 1707 */       sField = "f_long_modified";
/*      */     }
/* 1709 */     else if (a_attribute.getFieldName().equals("dateLastDownloaded"))
/*      */     {
/* 1711 */       sField = "f_long_lastDownload";
/*      */     }
/* 1713 */     else if (a_attribute.getFieldName().equals("sensitive"))
/*      */     {
/* 1715 */       sField = "f_sensitive";
/*      */     }
/* 1717 */     else if (a_attribute.getId() == 400L)
/*      */     {
/* 1719 */       sField = "f_agreementTypeId";
/*      */     }
/* 1721 */     else if (a_attribute.getId() == 600L)
/*      */     {
/* 1723 */       sField = "f_dbl_averageRating";
/*      */     }
/*      */ 
/* 1727 */     return sField;
/*      */   }
/*      */ 
/*      */   public static String getFlexibleAttributeIndexFieldName(Attribute a_attribute)
/*      */   {
/*      */     String sField;
/*      */     //String sField;
/* 1739 */     if ((a_attribute.getIsDatepicker()) || (a_attribute.getIsDateTime()) || ((a_attribute.getIsNumeric()) && (a_attribute.getMaxDecimalPlaces() == 0)))
/*      */     {
/* 1743 */       sField = "f_long_att_" + a_attribute.getId();
/*      */     }
/*      */     else
/*      */     {
/*      */       //String sField;
/* 1745 */       if ((a_attribute.getIsNumeric()) && (a_attribute.getMaxDecimalPlaces() > 0))
/*      */       {
/* 1747 */         sField = "f_dbl_att_" + a_attribute.getId();
/*      */       }
/*      */       else
/*      */       {
/* 1751 */         sField = "f_att_" + a_attribute.getId();
/*      */       }
/*      */     }
/* 1753 */     return sField;
/*      */   }
/*      */ 
/*      */   public static SortField getSortFieldForAttributeSort(Attribute a_attribute, boolean a_bReverse)
/*      */   {
/* 1765 */     String sField = getIndexFieldName(a_attribute) + "_sort";
/*      */ 
/* 1767 */     int iType = 3;
/*      */ 
/* 1769 */     if ((StringUtil.stringIsPopulated(a_attribute.getFieldName())) && ((a_attribute.getFieldName().equals("orientation")) || (a_attribute.getFieldName().equals("sensitive")) || (a_attribute.getFieldName().equals("agreements"))))
/*      */     {
/* 1774 */       iType = 4;
/*      */     }
/* 1776 */     else if ((StringUtil.stringIsPopulated(a_attribute.getFieldName())) && ((a_attribute.getFieldName().equals("assetId")) || (a_attribute.getFieldName().equals("price")) || (a_attribute.getFieldName().equals("size"))))
/*      */     {
/* 1781 */       iType = 6;
/*      */     }
/* 1784 */     else if ((a_attribute.getIsNumeric()) || ((StringUtil.stringIsPopulated(a_attribute.getFieldName())) && (a_attribute.getFieldName().equals("rating"))))
/*      */     {
/* 1788 */       iType = 7;
/*      */     }
/*      */ 
/* 1791 */     return new SortField(sField, iType, a_bReverse);
/*      */   }
/*      */ 
/*      */   public static SearchCriteria getAttributeValueSearch(String a_sAttributeValue, Attribute a_attribute)
/*      */   {
/* 1796 */     AttributeValue searchValue = new AttributeValue();
/* 1797 */     searchValue.setValue(a_sAttributeValue);
/* 1798 */     searchValue.setAttribute(a_attribute);
/* 1799 */     Vector vAttSearches = new Vector();
/* 1800 */     vAttSearches.add(searchValue);
/* 1801 */     SearchCriteria criteria = new SearchCriteria();
/* 1802 */     criteria.setAttributeSearches(vAttSearches);
/*      */ 
/* 1804 */     return criteria;
/*      */   }
/*      */ 
/*      */   public static Vector copyAttributeVector(Vector a_vecAssetAttributes)
/*      */   {
/* 1809 */     Vector vecNewAttributes = null;
/*      */ 
/* 1811 */     if (a_vecAssetAttributes != null)
/*      */     {
/* 1813 */       vecNewAttributes = new Vector();
/* 1814 */       for (int i = 0; i < a_vecAssetAttributes.size(); i++)
/*      */       {
/* 1816 */         Attribute att = (Attribute)a_vecAssetAttributes.get(i);
/* 1817 */         Attribute attCopy = new Attribute(att);
/* 1818 */         vecNewAttributes.add(attCopy);
/*      */       }
/*      */     }
/* 1821 */     return vecNewAttributes;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.util.AttributeUtil
 * JD-Core Version:    0.6.0
 */