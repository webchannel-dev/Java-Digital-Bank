/*     */ package com.bright.assetbank.autocomplete.service;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.service.LanguageManager;
/*     */ import com.bright.framework.search.service.AnalyzerFactory;
/*     */ import com.bright.framework.search.service.IndexManager;
/*     */ import com.bright.framework.util.UnaryFunction;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.lucene.analysis.Analyzer;
/*     */ import org.apache.lucene.analysis.KeywordAnalyzer;
/*     */ 
/*     */ public class AutoCompleteIndexManagers extends Bn2Manager
/*     */ {
/*     */   private LanguageManager m_languageManager;
/*     */   boolean m_bMultiLanguage;
/*     */   private Map<String, IndexManager> m_mapIndexManagers;
/*     */   private String m_sIndexRootFilepath;
/*     */ 
/*     */   public AutoCompleteIndexManagers()
/*     */   {
/*  55 */     this.m_mapIndexManagers = new HashMap();
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     super.startup();
/*     */ 
/*  64 */     this.m_sIndexRootFilepath = (AssetBankSettings.getIndexDirectory() + File.separator + "autocomplete");
/*     */ 
/*  66 */     if (AssetBankSettings.getUseRelativeDirectories())
/*     */     {
/*  68 */       this.m_sIndexRootFilepath = (GlobalSettings.getApplicationPath() + "/" + this.m_sIndexRootFilepath);
/*     */     }
/*     */ 
/*  72 */     List<Language> languages = this.m_languageManager.getLanguages(null);
/*     */ 
/*  75 */     this.m_bMultiLanguage = ((FrameworkSettings.getSupportMultiLanguage()) || (languages.size() > 1));
/*     */ 
/*  78 */     for (Language language : languages)
/*     */     {
/*  80 */       addLanguage(language);
/*     */     }
/*     */   }
/*     */ 
/*     */   public IndexManager addLanguage(Language language)
/*     */   {
/*  88 */     AnalyzerFactory analyzerFactory = new KeywordAnalyzerFactory();
/*     */     String sIndexDirectory;
/*     */    // String sIndexDirectory;
/*  92 */     if (this.m_bMultiLanguage)
/*     */     {
/*  94 */       sIndexDirectory = this.m_sIndexRootFilepath + "/" + language.getCode();
/*     */     }
/*     */     else
/*     */     {
/*  99 */       sIndexDirectory = this.m_sIndexRootFilepath;
/*     */     }
/*     */ 
/* 102 */     IndexManager indexManager = new IndexManager(sIndexDirectory, "f_keyword", analyzerFactory);
/*     */ 
/* 104 */     this.m_mapIndexManagers.put(language.getCode(), indexManager);
/*     */ 
/* 106 */     return indexManager;
/*     */   }
/*     */ 
/*     */   public void removeLanguage(String a_sLanguageCode) throws Bn2Exception
/*     */   {
/* 111 */     IndexManager indexManager = (IndexManager)this.m_mapIndexManagers.remove(a_sLanguageCode);
/* 112 */     if (indexManager != null)
/*     */     {
/* 114 */       indexManager.deleteIndex();
/*     */     }
/*     */   }
/*     */ 
/*     */   public IndexManager indexManagerForLanguage(String a_sLanguageCode)
/*     */   {
/* 120 */     return (IndexManager)this.m_mapIndexManagers.get(a_sLanguageCode);
/*     */   }
/*     */ 
/*     */   public <R> Collection<R> forEachIndexManager(UnaryFunction<IndexManager, R> a_action)
/*     */     throws Bn2Exception
/*     */   {
/* 130 */     Collection results = new ArrayList();
/*     */ 
/* 132 */     for (IndexManager indexManager : this.m_mapIndexManagers.values())
/*     */     {
/* 134 */       results.add(a_action.execute(indexManager));
/*     */     }
/*     */ 
/* 137 */     return results;
/*     */   }
/*     */ 
/*     */   public Collection<String> getLanguageCodes()
/*     */   {
/* 142 */     return this.m_mapIndexManagers.keySet();
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 149 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   private static class KeywordAnalyzerFactory
/*     */     implements AnalyzerFactory
/*     */   {
/* 164 */     private static final KeywordAnalyzer c_kKeywordAnalyzer = new KeywordAnalyzer();
/*     */ 
/*     */     public Analyzer getAnalyzer() throws Bn2Exception
/*     */     {
/* 168 */       return c_kKeywordAnalyzer;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.AutoCompleteIndexManagers
 * JD-Core Version:    0.6.0
 */