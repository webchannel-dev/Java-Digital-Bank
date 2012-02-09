/*     */ package com.bright.assetbank.language.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.Attribute.Translation;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ //import com.bright.assetbank.attribute.filter.bean.Filter.Translation;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.autocomplete.service.AutoCompleteUpdateManager;
/*     */ import com.bright.assetbank.commercialoption.bean.CommercialOption;
/*     */ //import com.bright.assetbank.commercialoption.bean.CommercialOption.Translation;
/*     */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */// import com.bright.assetbank.entity.bean.AssetEntity.Translation;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.marketing.bean.MarketingGroup;
/*     */ //import com.bright.assetbank.marketing.bean.MarketingGroup.Translation;
/*     */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*     */ import com.bright.assetbank.priceband.bean.PriceBand;
/*     */ //import com.bright.assetbank.priceband.bean.PriceBand.Translation;
/*     */ import com.bright.assetbank.priceband.service.PriceBandManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.subscription.bean.SubscriptionModel;
/*     */ //import com.bright.assetbank.subscription.bean.SubscriptionModel.Translation;
/*     */ import com.bright.assetbank.subscription.service.SubscriptionManager;
/*     */ import com.bright.assetbank.usage.bean.UsageType;
/*     */ //import com.bright.assetbank.usage.bean.UsageType.Translation;
/*     */ import com.bright.assetbank.usage.bean.UsageTypeFormat;
/*     */// import com.bright.assetbank.usage.bean.UsageTypeFormat.Translation;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.news.bean.NewsItem;
/*     */// import com.bright.framework.news.bean.NewsItem.Translation;
/*     */ import com.bright.framework.news.service.NewsManager;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class LanguageManager extends com.bright.framework.language.service.LanguageManager
/*     */ {
/*  64 */   private static final String c_ksClassName = LanguageManager.class.getName();
/*     */ 
/*  66 */   private AttributeManager m_attributeManager = null;
/*  67 */   private UsageManager m_usageManager = null;
/*  68 */   private FilterManager m_filterManager = null;
/*  69 */   private MarketingGroupManager m_marketingGroupManager = null;
/*  70 */   private CommercialOptionManager m_commercialOptionManager = null;
/*  71 */   private PriceBandManager m_priceBandManager = null;
/*  72 */   private SubscriptionManager m_subscriptionManager = null;
/*  73 */   private MultiLanguageSearchManager m_searchManager = null;
/*  74 */   private AssetEntityManager m_assetEntityManager = null;
/*  75 */   private NewsManager m_newsManager = null;
/*  76 */   private AutoCompleteUpdateManager m_autoCompleteUpdateManager = null;
/*     */ 
/*     */   public Language addLanguage(DBTransaction a_transaction, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/*  86 */     String ksMethodName = "addLanguage";
/*     */ 
/*  88 */     Language newLanguage = super.addLanguage(a_transaction, a_language);
/*     */ 
/*  91 */     Vector attributes = this.m_attributeManager.getAttributes(a_transaction, 0L, true);
/*  92 */     Iterator itAttributes = attributes.iterator();
/*  93 */     while (itAttributes.hasNext())
/*     */     {
/*  95 */       Attribute attribute = (Attribute)itAttributes.next();
/*     */       //Attribute tmp58_56 = attribute;
                //tmp58_56.getClass(); 
                Attribute.Translation translation = attribute.new Translation(newLanguage);
/*  97 */       attribute.getTranslations().add(translation);
/*     */ 
/* 100 */       this.m_attributeManager.saveAttribute(a_transaction, attribute);
/*     */     }
/*     */ 
/* 104 */     Vector usageTypes = this.m_usageManager.getUsageTypes(a_transaction);
/* 105 */     Iterator itUsageTypes = usageTypes.iterator();
/* 106 */     while (itUsageTypes.hasNext())
/*     */     {
/* 108 */       UsageType usageType = (UsageType)itUsageTypes.next();
/*     */      // UsageType tmp140_138 = usageType; 
                //tmp140_138.getClass(); 
//Translation(newLanguage);
                UsageType.Translation translation = usageType.new Translation(newLanguage);
/* 110 */       usageType.getTranslations().add(translation);
/*     */ 
/* 112 */       this.m_usageManager.updateUsageTypeValue(a_transaction, usageType);
/*     */     }
/*     */ 
/* 116 */     Vector usageTypeFormats = this.m_usageManager.getUsageTypeFormats(a_transaction);
/* 117 */     Iterator itUsageTypeFormats = usageTypeFormats.iterator();
/* 118 */     while (itUsageTypeFormats.hasNext())
/*     */     {
/* 120 */       UsageTypeFormat usageTypeFormat = (UsageTypeFormat)itUsageTypeFormats.next();
/*     */       //UsageTypeFormat tmp221_219 = usageTypeFormat; tmp221_219.getClass(); UsageTypeFormat.Translation translation = new UsageTypeFormat.Translation(tmp221_219, newLanguage);
/* 122 */      // UsageTypeFormat tmp221_219 = usageTypeFormat; tmp221_219.getClass(); 
                 UsageTypeFormat.Translation translation = usageTypeFormat.new Translation(newLanguage);
                 usageTypeFormat.getTranslations().add(translation);
/*     */ 
/* 124 */       this.m_usageManager.updateUsageTypeFormat(a_transaction, usageTypeFormat);
/*     */     }
/*     */ 
/* 127 */     Vector filters = this.m_filterManager.getAllFilters(a_transaction, LanguageConstants.k_defaultLanguage, null);
/* 128 */     Iterator itFilters = filters.iterator();
/* 129 */     while (itFilters.hasNext())
/*     */     {
/* 131 */       Filter filter = (Filter)itFilters.next();
/*     */       //Filter tmp306_304 = filter; tmp306_304.getClass(); Filter.Translation translation = new Filter.Translation(tmp306_304, newLanguage);
/* 133 */      //Filter tmp306_304 = filter; tmp306_304.getClass(); 
                Filter.Translation translation = filter.new Translation(newLanguage);
                filter.getTranslations().add(translation);
/*     */ 
/* 135 */       this.m_filterManager.saveFilter(a_transaction, filter, 0L);
/*     */     }
/*     */ 
/* 138 */     List marketingGroups = this.m_marketingGroupManager.getMarketingGroups(a_transaction);
/* 139 */     Iterator itGroups = marketingGroups.iterator();
/* 140 */     while (itGroups.hasNext())
/*     */     {
/* 142 */       MarketingGroup group = (MarketingGroup)itGroups.next();
/*     */      // MarketingGroup tmp390_388 = group; tmp390_388.getClass(); MarketingGroup.Translation translation = new MarketingGroup.Translation(tmp390_388, newLanguage);
                MarketingGroup.Translation translation = group.new Translation(newLanguage);
/* 144 */       group.getTranslations().add(translation);
/*     */ 
/* 146 */       this.m_marketingGroupManager.saveMarketingGroup(a_transaction, group);
/*     */     }
/*     */ 
/* 149 */     Vector priceBands = this.m_priceBandManager.getPriceBandList(a_transaction);
/* 150 */     Iterator itPriceBands = priceBands.iterator();
/* 151 */     while (itPriceBands.hasNext())
/*     */     {
/* 153 */       PriceBand priceBand = (PriceBand)itPriceBands.next();
/*     */       //PriceBand tmp471_469 = priceBand; tmp471_469.getClass(); PriceBand.Translation translation = new PriceBand.Translation(tmp471_469, newLanguage);
/* 155 */       PriceBand.Translation translation = priceBand.new Translation( newLanguage);
                priceBand.getTranslations().add(translation);
/*     */ 
/* 157 */       this.m_priceBandManager.savePriceBand(a_transaction, priceBand);
/*     */     }
/*     */ 
/* 160 */     Vector subscriptionModels = this.m_subscriptionManager.getSubscriptionModels(a_transaction, false);
/* 161 */     Iterator itSubscriptionModels = subscriptionModels.iterator();
/* 162 */     while (itSubscriptionModels.hasNext())
/*     */     {
/* 164 */       SubscriptionModel subscriptionModel = (SubscriptionModel)itSubscriptionModels.next();
/*     */       //SubscriptionModel tmp553_551 = subscriptionModel; tmp553_551.getClass(); SubscriptionModel.Translation translation = new SubscriptionModel.Translation(tmp553_551, newLanguage);
                SubscriptionModel.Translation translation = subscriptionModel.new Translation(newLanguage);
/* 166 */       subscriptionModel.getTranslations().add(translation);
/*     */ 
/* 168 */       this.m_subscriptionManager.saveSubscriptionModel(a_transaction, subscriptionModel);
/*     */     }
/*     */ 
/* 171 */     Vector commercialOptions = this.m_commercialOptionManager.getCommercialOptionList(a_transaction);
/* 172 */     Iterator itCommercialOptions = commercialOptions.iterator();
/* 173 */     while (itCommercialOptions.hasNext())
/*     */     {
/* 175 */       CommercialOption commercialOption = (CommercialOption)itCommercialOptions.next();
/*     */       //CommercialOption tmp634_632 = commercialOption; tmp634_632.getClass(); CommercialOption.Translation translation = new CommercialOption.Translation(tmp634_632, newLanguage);
/* 177 */       CommercialOption.Translation translation = commercialOption.new Translation(newLanguage);
                commercialOption.getTranslations().add(translation);
/*     */ 
/* 179 */       this.m_commercialOptionManager.saveCommercialOption(a_transaction, commercialOption);
/*     */     }
/*     */ 
/* 182 */     Vector newsItems = this.m_newsManager.getNewsItems(a_transaction, false, -1, 0);
/* 183 */     Iterator itNewsItems = newsItems.iterator();
/* 184 */     while (itNewsItems.hasNext())
/*     */     {
/* 186 */       NewsItem item = (NewsItem)itNewsItems.next();
/*     */       //NewsItem tmp718_716 = item; tmp718_716.getClass(); NewsItem.Translation translation = new NewsItem.Translation(tmp718_716, newLanguage);
/* 188 */       NewsItem.Translation translation = item.new Translation(newLanguage);
                item.getTranslations().add(translation);
/*     */ 
/* 190 */       this.m_newsManager.saveNewsItem(a_transaction, item);
/*     */     }
/*     */ 
/* 193 */     Vector assetEntities = this.m_assetEntityManager.getAllEntities(a_transaction);
/* 194 */     Iterator itAssetEntities = assetEntities.iterator();
/* 195 */     while (itAssetEntities.hasNext())
/*     */     {
/* 197 */       AssetEntity entity = (AssetEntity)itAssetEntities.next();
/*     */       //AssetEntity tmp799_797 = entity; tmp799_797.getClass(); AssetEntity.Translation translation = new AssetEntity.Translation(tmp799_797, );
/* 199 */       AssetEntity.Translation translation = entity.new Translation(newLanguage);
                entity.getTranslations().add(translation);
/*     */ 
/* 201 */       this.m_assetEntityManager.saveEntityOnly(a_transaction, entity);
/*     */     }
/*     */ 
/* 205 */     createTranslationsForCategoryTree(a_transaction, 1L, a_language);
/* 206 */     createTranslationsForCategoryTree(a_transaction, 2L, a_language);
/*     */     try
/*     */     {
/* 211 */       a_transaction.commit();
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 215 */       this.m_logger.error(c_ksClassName + "." + "addLanguage" + " : SQL Exception : " + e);
/* 216 */       throw new Bn2Exception(c_ksClassName + "." + "addLanguage" + " : SQL Exception : " + e, e);
/*     */     }
/*     */ 
/* 221 */     this.m_searchManager.addLanguage(a_language);
/*     */ 
/* 227 */     this.m_autoCompleteUpdateManager.addLanguage(a_language);
/*     */ 
/* 229 */     return newLanguage;
/*     */   }
/*     */ 
/*     */   public void updateLanguage(DBTransaction a_transaction, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/* 240 */     Language existingLanguage = getLanguage(a_transaction, a_language.getId());
/*     */ 
/* 242 */     super.updateLanguage(a_transaction, a_language);
/*     */ 
/* 245 */     if (!a_language.getCode().equalsIgnoreCase(existingLanguage.getCode()))
/*     */     {
/* 247 */       this.m_searchManager.removeLanguage(existingLanguage.getCode());
/* 248 */       this.m_searchManager.addLanguage(a_language);
/*     */ 
/* 250 */       this.m_autoCompleteUpdateManager.renameLanguage(existingLanguage, a_language);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void deleteLanguage(DBTransaction a_transaction, long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 259 */     String ksMethodName = "deleteLanguage";
/*     */ 
/* 261 */     Language existingLanguage = getLanguage(a_transaction, a_lId);
/*     */     try
/*     */     {
/* 265 */       Connection con = a_transaction.getConnection();
/*     */ 
/* 268 */       String sSql = "UPDATE AssetBankUser SET LanguageId=? WHERE LanguageId=?";
/* 269 */       PreparedStatement psql = con.prepareStatement(sSql);
/* 270 */       psql.setLong(1, 1L);
/* 271 */       psql.setLong(2, a_lId);
/* 272 */       psql.executeUpdate();
/* 273 */       psql.close();
/*     */ 
/* 275 */       String[] asSQl = { "DELETE FROM TranslatedAssetEntity WHERE LanguageId=?", "DELETE FROM TranslatedERDescription WHERE LanguageId=?", "DELETE FROM TranslatedUsageTypeFormat WHERE LanguageId=?", "DELETE FROM TranslatedUsageType WHERE LanguageId=?", "DELETE FROM TranslatedPriceBand WHERE LanguageId=?", "DELETE FROM TranslatedCommercialOption WHERE LanguageId=?", "DELETE FROM TranslatedTaxRegion WHERE LanguageId=?", "DELETE FROM TranslatedTaxType WHERE LanguageId=?", "DELETE FROM TranslatedPriceBandType WHERE LanguageId=?", "DELETE FROM TranslatedOrderStatus WHERE LanguageId=?", "DELETE FROM TranslatedSubscriptionModel WHERE LanguageId=?", "DELETE FROM TranslatedMarketingGroup WHERE LanguageId=?", "DELETE FROM TranslatedFilter WHERE LanguageId=?", "DELETE FROM TranslatedListAttributeValue WHERE LanguageId=?", "DELETE FROM TranslatedAssetAttributeValues WHERE LanguageId=?", "DELETE FROM TranslatedAttribute WHERE LanguageId=?", "DELETE FROM TranslatedMarketingEmail WHERE LanguageId=?", "DELETE FROM TranslatedNewsItem WHERE LanguageId=?", "DELETE FROM TranslatedCustomField WHERE LanguageId=?" };
/*     */ 
/* 297 */       for (int i = 0; i < asSQl.length; i++)
/*     */       {
/* 299 */         psql = con.prepareStatement(asSQl[i]);
/* 300 */         psql.setLong(1, a_lId);
/* 301 */         psql.executeUpdate();
/* 302 */         psql.close();
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 307 */       this.m_logger.error(c_ksClassName + "." + "deleteLanguage" + " : SQL Exception : " + e);
/* 308 */       throw new Bn2Exception(c_ksClassName + "." + "deleteLanguage" + " : SQL Exception : " + e, e);
/*     */     }
/*     */ 
/* 311 */     super.deleteLanguage(a_transaction, a_lId);
/*     */ 
/* 313 */     this.m_searchManager.removeLanguage(existingLanguage.getCode());
/* 314 */     this.m_autoCompleteUpdateManager.removeLanguage(existingLanguage.getCode());
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager attributeManager)
/*     */   {
/* 319 */     this.m_attributeManager = attributeManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager searchManager)
/*     */   {
/* 324 */     this.m_searchManager = searchManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager usageManager)
/*     */   {
/* 329 */     this.m_usageManager = usageManager;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager filterManager)
/*     */   {
/* 334 */     this.m_filterManager = filterManager;
/*     */   }
/*     */ 
/*     */   public void setMarketingGroupManager(MarketingGroupManager marketingGroupManager)
/*     */   {
/* 339 */     this.m_marketingGroupManager = marketingGroupManager;
/*     */   }
/*     */ 
/*     */   public void setCommercialOptionManager(CommercialOptionManager commercialOptionManager)
/*     */   {
/* 344 */     this.m_commercialOptionManager = commercialOptionManager;
/*     */   }
/*     */ 
/*     */   public void setPriceBandManager(PriceBandManager priceBandManager)
/*     */   {
/* 349 */     this.m_priceBandManager = priceBandManager;
/*     */   }
/*     */ 
/*     */   public void setSubscriptionManager(SubscriptionManager subscriptionManager)
/*     */   {
/* 354 */     this.m_subscriptionManager = subscriptionManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntity)
/*     */   {
/* 359 */     this.m_assetEntityManager = assetEntity;
/*     */   }
/*     */ 
/*     */   public void setNewsManager(NewsManager a_manager)
/*     */   {
/* 364 */     this.m_newsManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setAutoCompleteUpdateManager(AutoCompleteUpdateManager a_autoCompleteUpdateManager)
/*     */   {
/* 369 */     this.m_autoCompleteUpdateManager = a_autoCompleteUpdateManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.language.service.LanguageManager
 * JD-Core Version:    0.6.0
 */