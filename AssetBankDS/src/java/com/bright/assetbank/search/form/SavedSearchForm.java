/*     */ package com.bright.assetbank.search.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.search.bean.SavedSearch;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class SavedSearchForm extends Bn2Form
/*     */ {
/*  35 */   private SavedSearch m_savedSearch = null;
/*  36 */   private Vector m_savedSearches = null;
/*  37 */   private List m_recentSearches = null;
/*  38 */   private boolean m_bRecent = false;
/*  39 */   private FormFile m_ffImage = null;
/*  40 */   private boolean m_bRemoveImage = false;
/*     */ 
/*     */   public SavedSearch getSavedSearch()
/*     */   {
/*  44 */     if (this.m_savedSearch == null)
/*     */     {
/*  46 */       this.m_savedSearch = new SavedSearch();
/*     */     }
/*  48 */     return this.m_savedSearch;
/*     */   }
/*     */ 
/*     */   public void setSavedSearch(SavedSearch savedSearch)
/*     */   {
/*  53 */     this.m_savedSearch = savedSearch;
/*     */   }
/*     */ 
/*     */   public List getRecentSearches()
/*     */   {
/*  58 */     return this.m_recentSearches;
/*     */   }
/*     */ 
/*     */   public void setRecentSearches(List a_recentSearches)
/*     */   {
/*  63 */     this.m_recentSearches = a_recentSearches;
/*     */   }
/*     */ 
/*     */   public Vector getSavedSearches()
/*     */   {
/*  68 */     return this.m_savedSearches;
/*     */   }
/*     */ 
/*     */   public void setSavedSearches(Vector savedSearches)
/*     */   {
/*  73 */     this.m_savedSearches = savedSearches;
/*     */   }
/*     */ 
/*     */   public boolean isRecent()
/*     */   {
/*  78 */     return this.m_bRecent;
/*     */   }
/*     */ 
/*     */   public void setRecent(boolean recent)
/*     */   {
/*  83 */     this.m_bRecent = recent;
/*     */   }
/*     */ 
/*     */   public void setImage(FormFile a_ffImage)
/*     */   {
/*  88 */     this.m_ffImage = a_ffImage;
/*     */   }
/*     */ 
/*     */   public FormFile getImage()
/*     */   {
/*  93 */     return this.m_ffImage;
/*     */   }
/*     */ 
/*     */   public void setRemoveImage(boolean a_bRemoveImage)
/*     */   {
/*  98 */     this.m_bRemoveImage = a_bRemoveImage;
/*     */   }
/*     */ 
/*     */   public boolean getRemoveImage()
/*     */   {
/* 103 */     return this.m_bRemoveImage;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.form.SavedSearchForm
 * JD-Core Version:    0.6.0
 */