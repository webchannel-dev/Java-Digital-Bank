/*     */ package com.bright.assetbank.search.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.framework.category.form.CategorySelectionForm;
/*     */ import com.bright.framework.common.bean.BrightMoney;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.DateUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class SearchForm extends BaseSearchForm
/*     */   implements ImageConstants, AssetBankConstants
/*     */ {
/*  70 */   private String m_sTitle = null;
/*  71 */   private String m_sFilename = null;
/*  72 */   private String m_sAddedBy = null;
/*  73 */   private long m_lAddedByUserId = 0L;
/*  74 */   private String m_sDateAddedLower = null;
/*  75 */   private String m_sDateAddedUpper = null;
/*  76 */   private String m_sDateModLower = null;
/*  77 */   private String m_sDateModUpper = null;
/*  78 */   private String m_sAssetIds = null;
/*  79 */   private int m_bCompleteness = 0;
/*  80 */   private int m_iOrientation = 0;
/*  81 */   private String m_sDateDownloadedLower = null;
/*  82 */   private String m_sDateDownloadedUpper = null;
/*  83 */   private String m_sFilesizeLower = null;
/*  84 */   private String m_sFilesizeUpper = null;
/*     */ 
/*  86 */   private String m_sAgreementText = null;
/*  87 */   private long m_lAgreementType = 0L;
/*     */ 
/*  89 */   private String m_sIsSensitive = null;
/*     */ 
/*  91 */   private double m_dAverageRating = -1.0D;
/*  92 */   private int m_iMinimumVotes = -1;
/*  93 */   private int m_iMaximumVotes = -1;
/*  94 */   private int m_iEmptyFileStatus = -1;
/*     */ 
/*     */   public SearchForm()
/*     */   {
/* 101 */     this.m_priceLower = new BrightMoney();
/* 102 */     this.m_priceUpper = new BrightMoney();
/*     */   }
/*     */ 
/*     */   public SearchCriteria populateSearchCriteria(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/* 124 */     SearchCriteria searchCriteria = new SearchCriteria();
/*     */ 
/* 127 */     DateFormat format = AssetBankSettings.getStandardDateFormat();
/* 128 */     DateFormat formatYearOnly = DateUtil.getYearOnlyDateFormat();
/*     */ 
/* 130 */     if ((getDateAddedLower() != null) && (!getDateAddedLower().equals("")))
/*     */     {
/*     */       try
/*     */       {
/* 134 */         searchCriteria.setDateImageAddedLower(format.parse(getDateAddedLower()));
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*     */         try
/*     */         {
/* 140 */           searchCriteria.setDateImageAddedLower(formatYearOnly.parse(getDateAddedLower()));
/*     */         }
/*     */         catch (ParseException peYearOnly)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 149 */     if ((getDateAddedUpper() != null) && (!getDateAddedUpper().equals("")))
/*     */     {
/*     */       try
/*     */       {
/* 153 */         searchCriteria.setDateImageAddedUpper(DateUtil.getEndOfDay(format.parse(getDateAddedUpper())));
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*     */         try
/*     */         {
/* 159 */           searchCriteria.setDateImageAddedUpper(DateUtil.getEndOfYear(formatYearOnly.parse(getDateAddedUpper())));
/*     */         }
/*     */         catch (ParseException peYearOnly)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 168 */     if ((getDateModLower() != null) && (!getDateModLower().equals("")))
/*     */     {
/*     */       try
/*     */       {
/* 172 */         searchCriteria.setDateImageModLower(format.parse(getDateModLower()));
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*     */         try
/*     */         {
/* 178 */           searchCriteria.setDateImageModLower(formatYearOnly.parse(getDateModLower()));
/*     */         }
/*     */         catch (ParseException peYearOnly)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 187 */     if ((getDateModUpper() != null) && (!getDateModUpper().equals("")))
/*     */     {
/*     */       try
/*     */       {
/* 191 */         searchCriteria.setDateImageModUpper(DateUtil.getEndOfDay(format.parse(getDateModUpper())));
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*     */         try
/*     */         {
/* 197 */           searchCriteria.setDateImageModUpper(DateUtil.getEndOfYear(formatYearOnly.parse(getDateModUpper())));
/*     */         }
/*     */         catch (ParseException peYearOnly)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 206 */     if ((getDateDownloadedLower() != null) && (!getDateDownloadedLower().equals("")))
/*     */     {
/*     */       try
/*     */       {
/* 210 */         searchCriteria.setDateDownloadedLower(format.parse(getDateDownloadedLower()));
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*     */         try
/*     */         {
/* 216 */           searchCriteria.setDateDownloadedLower(formatYearOnly.parse(getDateDownloadedLower()));
/*     */         }
/*     */         catch (ParseException peYearOnly)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 225 */     if ((getDateDownloadedUpper() != null) && (!getDateDownloadedUpper().equals("")))
/*     */     {
/*     */       try
/*     */       {
/* 229 */         searchCriteria.setDateDownloadedUpper(DateUtil.getEndOfDay(format.parse(getDateDownloadedUpper())));
/*     */       }
/*     */       catch (ParseException pe)
/*     */       {
/*     */         try
/*     */         {
/* 235 */           searchCriteria.setDateDownloadedUpper(DateUtil.getEndOfYear(formatYearOnly.parse(getDateDownloadedUpper())));
/*     */         }
/*     */         catch (ParseException peYearOnly)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 244 */     searchCriteria.setKeywords(getKeywords());
/* 245 */     searchCriteria.setTitle(getTitle());
/* 246 */     searchCriteria.setFilename(getFilename());
/* 247 */     searchCriteria.setCategoryIds(getAllCategoryIds());
/*     */ 
/* 250 */     String sDescriptiveCats = getDescriptiveCategoryForm().getCategoryIds();
/* 251 */     String sPermissionCats = getPermissionCategoryForm().getCategoryIds();
/* 252 */     searchCriteria.setPermissionCategoriesToRefine(StringUtil.convertToVector(sPermissionCats, ","));
/* 253 */     searchCriteria.setDescriptiveCategoriesToRefine(StringUtil.convertToVector(sDescriptiveCats, ","));
/*     */ 
/* 255 */     searchCriteria.setAddedBy(getAddedBy());
/* 256 */     searchCriteria.setAddedByUserId(getAddedByUserId());
/*     */ 
/* 258 */     String sAssetIds = getAssetIds();
/* 259 */     searchCriteria.setOriginalAssetIdsString(sAssetIds);
/* 260 */     searchCriteria.setEmptyFileStatus(getEmptyFileStatus());
/*     */ 
/* 264 */     if (sAssetIds != null)
/*     */     {
/* 266 */       String[] asRanges = sAssetIds.split("-");
/*     */ 
/* 269 */       if ((asRanges != null) && (asRanges.length == 2))
/*     */       {
/*     */         try
/*     */         {
/* 274 */           long lLower = Long.parseLong(asRanges[0]);
/* 275 */           long lUpper = Long.parseLong(asRanges[1]);
/*     */ 
/* 277 */           if (lUpper - lLower > 1000L)
/*     */           {
/* 280 */             if ((a_userProfile != null) && (a_listManager != null))
/*     */             {
/* 282 */               addError(a_listManager.getListItem(a_dbTransaction, "searchCriteriaIdRangeTooLarge", a_userProfile.getCurrentLanguage()).getBody() + " " + 1000);
/*     */             }
/*     */ 
/*     */           }
/* 287 */           else if (lLower < lUpper)
/*     */           {
/* 289 */             sAssetIds = null;
/* 290 */             for (long lId = lLower; lId <= lUpper; lId += 1L)
/*     */             {
/* 293 */               if (sAssetIds != null)
/*     */               {
/* 295 */                 sAssetIds = sAssetIds + " OR ";
/*     */               }
/*     */               else
/*     */               {
/* 299 */                 sAssetIds = "";
/*     */               }
/*     */ 
/* 303 */               sAssetIds = sAssetIds + lId;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (NumberFormatException nfe)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 317 */     if ((AssetBankSettings.getAssetCodeCharCount() > 0) && (sAssetIds != null))
/*     */     {
/* 320 */       String[] saIds = sAssetIds.split("\\s");
/*     */ 
/* 323 */       for (int i = 0; (saIds != null) && (i < saIds.length); i++)
/*     */       {
/* 326 */         String sNewId = StringUtil.removeLeadingChars(saIds[i], AssetBankSettings.getAssetCodePaddingChar());
/*     */ 
/* 329 */         if ((sNewId == null) || (sNewId.length() <= 0)) {
/*     */           continue;
/*     */         }
/* 332 */         if (sAssetIds == null)
/*     */         {
/* 334 */           sAssetIds = "";
/*     */         }
/*     */         else
/*     */         {
/* 338 */           sAssetIds = sAssetIds + " ";
/*     */         }
/*     */ 
/* 342 */         sAssetIds = sAssetIds + sNewId;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 348 */     searchCriteria.setAssetIds(sAssetIds);
/*     */ 
/* 350 */     searchCriteria.setOrCategories(getOrCategories());
/* 351 */     searchCriteria.setWithoutCategory(getWithoutCategory());
/* 352 */     searchCriteria.setIncludeImplicitCategoryMembers(getIncludeImplicitCategoryMembers());
/*     */ 
/* 357 */     getPriceLower().processFormData();
/* 358 */     getPriceUpper().processFormData();
/* 359 */     searchCriteria.setPriceLower(getPriceLower());
/* 360 */     searchCriteria.setPriceUpper(getPriceUpper());
/*     */ 
/* 363 */     searchCriteria.setOrientation(getOrientation());
/* 364 */     searchCriteria.setLanguageCode(this.m_sLanguage);
/*     */ 
/* 366 */     if (this.m_aSelectedEntities != null)
/*     */     {
/* 368 */       for (long lEntityId : this.m_aSelectedEntities)
/*     */       {
/* 370 */         searchCriteria.addAssetEntityIdToInclude(lEntityId);
/*     */       }
/*     */     }
/*     */ 
/* 374 */     if (StringUtils.isNotEmpty(getIsSensitive()))
/*     */     {
/* 376 */       searchCriteria.setIsSensitive(Boolean.valueOf(getIsSensitive()));
/*     */     }
/*     */     else
/*     */     {
/* 380 */       searchCriteria.setIsSensitive(null);
/*     */     }
/*     */ 
/* 383 */     searchCriteria.setAgreementType(getAgreementType());
/* 384 */     searchCriteria.setAgreementText(getAgreementText());
/*     */ 
/* 387 */     if (StringUtils.isNotEmpty(getFilesizeLower()))
/*     */     {
/*     */       try
/*     */       {
/* 391 */         searchCriteria.setFilesizeLower(Double.valueOf(getFilesizeLower()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/*     */       }
/*     */     }
/*     */ 
/* 398 */     if (StringUtils.isNotEmpty(getFilesizeUpper()))
/*     */     {
/*     */       try
/*     */       {
/* 402 */         searchCriteria.setFilesizeUpper(Double.valueOf(getFilesizeUpper()));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 410 */     return searchCriteria;
/*     */   }
/*     */ 
/*     */   public Attribute getFilenameAttribute()
/*     */   {
/* 415 */     return getStaticAttribute("originalFilename");
/*     */   }
/*     */ 
/*     */   public void setAddedBy(String a_sAddedBy)
/*     */   {
/* 420 */     this.m_sAddedBy = a_sAddedBy;
/*     */   }
/*     */ 
/*     */   public String getAddedBy()
/*     */   {
/* 425 */     return this.m_sAddedBy;
/*     */   }
/*     */ 
/*     */   public long getAddedByUserId()
/*     */   {
/* 430 */     return this.m_lAddedByUserId;
/*     */   }
/*     */ 
/*     */   public void setAddedByUserId(long a_addedByUserId)
/*     */   {
/* 435 */     this.m_lAddedByUserId = a_addedByUserId;
/*     */   }
/*     */ 
/*     */   public void setDateAddedLower(String a_sDateAddedLower)
/*     */   {
/* 440 */     this.m_sDateAddedLower = a_sDateAddedLower;
/*     */   }
/*     */ 
/*     */   public String getDateAddedLower()
/*     */   {
/* 445 */     return this.m_sDateAddedLower;
/*     */   }
/*     */ 
/*     */   public void setDateAddedUpper(String a_sDateAddedUpper)
/*     */   {
/* 450 */     this.m_sDateAddedUpper = a_sDateAddedUpper;
/*     */   }
/*     */ 
/*     */   public String getDateAddedUpper()
/*     */   {
/* 455 */     return this.m_sDateAddedUpper;
/*     */   }
/*     */ 
/*     */   public String getAssetIds()
/*     */   {
/* 461 */     return this.m_sAssetIds;
/*     */   }
/*     */ 
/*     */   public void setAssetIds(String a_sAssetIds)
/*     */   {
/* 467 */     this.m_sAssetIds = a_sAssetIds;
/*     */   }
/*     */ 
/*     */   public int getOrientation()
/*     */   {
/* 473 */     return this.m_iOrientation;
/*     */   }
/*     */ 
/*     */   public void setOrientation(int a_iOrientation)
/*     */   {
/* 478 */     this.m_iOrientation = a_iOrientation;
/*     */   }
/*     */ 
/*     */   public String getDateModLower() {
/* 482 */     return this.m_sDateModLower;
/*     */   }
/*     */ 
/*     */   public void setDateModLower(String a_sDateModLower) {
/* 486 */     this.m_sDateModLower = a_sDateModLower;
/*     */   }
/*     */ 
/*     */   public String getDateModUpper() {
/* 490 */     return this.m_sDateModUpper;
/*     */   }
/*     */ 
/*     */   public void setDateModUpper(String a_sDateModUpper) {
/* 494 */     this.m_sDateModUpper = a_sDateModUpper;
/*     */   }
/*     */ 
/*     */   public String getFilename()
/*     */   {
/* 499 */     return this.m_sFilename;
/*     */   }
/*     */ 
/*     */   public void setFilename(String a_sFilename)
/*     */   {
/* 504 */     this.m_sFilename = a_sFilename;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 509 */     return this.m_sTitle;
/*     */   }
/*     */ 
/*     */   public void setTitle(String a_sTitle)
/*     */   {
/* 514 */     this.m_sTitle = a_sTitle;
/*     */   }
/*     */ 
/*     */   public int getCompleteness()
/*     */   {
/* 519 */     return this.m_bCompleteness;
/*     */   }
/*     */ 
/*     */   public void setCompleteness(int a_iCompleteness)
/*     */   {
/* 524 */     this.m_bCompleteness = a_iCompleteness;
/*     */   }
/*     */ 
/*     */   public String getDateDownloadedLower()
/*     */   {
/* 529 */     return this.m_sDateDownloadedLower;
/*     */   }
/*     */ 
/*     */   public void setDateDownloadedLower(String dateDownloadedLower)
/*     */   {
/* 535 */     this.m_sDateDownloadedLower = dateDownloadedLower;
/*     */   }
/*     */ 
/*     */   public String getDateDownloadedUpper()
/*     */   {
/* 541 */     return this.m_sDateDownloadedUpper;
/*     */   }
/*     */ 
/*     */   public void setDateDownloadedUpper(String dateDownloadedUpper)
/*     */   {
/* 547 */     this.m_sDateDownloadedUpper = dateDownloadedUpper;
/*     */   }
/*     */ 
/*     */   public String getAgreementText()
/*     */   {
/* 553 */     return this.m_sAgreementText;
/*     */   }
/*     */ 
/*     */   public void setAgreementText(String agreementText)
/*     */   {
/* 559 */     this.m_sAgreementText = agreementText;
/*     */   }
/*     */ 
/*     */   public long getAgreementType()
/*     */   {
/* 565 */     return this.m_lAgreementType;
/*     */   }
/*     */ 
/*     */   public void setAgreementType(long agreementType)
/*     */   {
/* 571 */     this.m_lAgreementType = agreementType;
/*     */   }
/*     */ 
/*     */   public String getIsSensitive()
/*     */   {
/* 577 */     return this.m_sIsSensitive;
/*     */   }
/*     */ 
/*     */   public void setIsSensitive(String isSensitive)
/*     */   {
/* 583 */     this.m_sIsSensitive = isSensitive;
/*     */   }
/*     */ 
/*     */   public void setAverageRating(double a_dAverageRating)
/*     */   {
/* 588 */     this.m_dAverageRating = a_dAverageRating;
/*     */   }
/*     */ 
/*     */   public double getAverageRating()
/*     */   {
/* 593 */     return this.m_dAverageRating;
/*     */   }
/*     */ 
/*     */   public void setMaximumVotes(int a_iMaximumVotes)
/*     */   {
/* 598 */     this.m_iMaximumVotes = a_iMaximumVotes;
/*     */   }
/*     */ 
/*     */   public int getMaximumVotes()
/*     */   {
/* 603 */     return this.m_iMaximumVotes;
/*     */   }
/*     */ 
/*     */   public void setMinimumVotes(int a_iMinimumVotes)
/*     */   {
/* 608 */     this.m_iMinimumVotes = a_iMinimumVotes;
/*     */   }
/*     */ 
/*     */   public int getMinimumVotes()
/*     */   {
/* 613 */     return this.m_iMinimumVotes;
/*     */   }
/*     */ 
/*     */   public String getFilesizeLower()
/*     */   {
/* 618 */     return this.m_sFilesizeLower;
/*     */   }
/*     */ 
/*     */   public void setFilesizeLower(String a_sFilesizeLower)
/*     */   {
/* 623 */     this.m_sFilesizeLower = a_sFilesizeLower;
/*     */   }
/*     */ 
/*     */   public String getFilesizeUpper()
/*     */   {
/* 629 */     return this.m_sFilesizeUpper;
/*     */   }
/*     */ 
/*     */   public void setFilesizeUpper(String a_sFilesizeUpper)
/*     */   {
/* 635 */     this.m_sFilesizeUpper = a_sFilesizeUpper;
/*     */   }
/*     */ 
/*     */   public void setEmptyFileStatus(int a_iEmptyFileStatus)
/*     */   {
/* 640 */     this.m_iEmptyFileStatus = a_iEmptyFileStatus;
/*     */   }
/*     */ 
/*     */   public int getEmptyFileStatus()
/*     */   {
/* 645 */     return this.m_iEmptyFileStatus;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.form.SearchForm
 * JD-Core Version:    0.6.0
 */