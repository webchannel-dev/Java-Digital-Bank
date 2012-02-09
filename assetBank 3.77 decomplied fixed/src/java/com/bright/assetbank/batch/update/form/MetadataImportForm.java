/*     */ package com.bright.assetbank.batch.update.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import java.util.Vector;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class MetadataImportForm extends Bn2Form
/*     */ {
/*  39 */   private FormFile m_file = null;
/*  40 */   private String m_sUrl = null;
/*  41 */   private boolean m_bFatalError = false;
/*  42 */   private int m_bNumItemsImported = 0;
/*  43 */   private boolean m_bAddMissingAssets = false;
/*  44 */   private boolean m_bAddMissingCategories = false;
/*  45 */   private boolean m_bCheckInProgress = false;
/*  46 */   private boolean m_bSkipCheck = false;
/*  47 */   private boolean m_bRemoveFromExistingCategories = false;
/*  48 */   private Attribute m_attribute = null;
/*  49 */   private Vector m_vecAttributes = null;
/*  50 */   private long m_lTreeId = 0L;
/*     */ 
/*  53 */   private Vector m_vecMessages = null;
/*     */ 
/*     */   public FormFile getFile()
/*     */   {
/*  57 */     return this.m_file;
/*     */   }
/*     */ 
/*     */   public void setFile(FormFile a_sFile) {
/*  61 */     this.m_file = a_sFile;
/*     */   }
/*     */ 
/*     */   public String getUrl() {
/*  65 */     return this.m_sUrl;
/*     */   }
/*     */ 
/*     */   public void setUrl(String a_sUrl) {
/*  69 */     this.m_sUrl = a_sUrl;
/*     */   }
/*     */ 
/*     */   public boolean getFatalError() {
/*  73 */     return this.m_bFatalError;
/*     */   }
/*     */ 
/*     */   public void setFatalError(boolean a_bFatalError) {
/*  77 */     this.m_bFatalError = a_bFatalError;
/*     */   }
/*     */ 
/*     */   public int getNumItemsImported() {
/*  81 */     return this.m_bNumItemsImported;
/*     */   }
/*     */ 
/*     */   public void setNumItemsImported(int a_sAssetsImported) {
/*  85 */     this.m_bNumItemsImported = a_sAssetsImported;
/*     */   }
/*     */ 
/*     */   public Vector getMessages()
/*     */   {
/*  90 */     return this.m_vecMessages;
/*     */   }
/*     */ 
/*     */   public void setMessages(Vector a_vecMessages) {
/*  94 */     this.m_vecMessages = a_vecMessages;
/*     */   }
/*     */ 
/*     */   public boolean getAddMissingCategories() {
/*  98 */     return this.m_bAddMissingCategories;
/*     */   }
/*     */ 
/*     */   public void setAddMissingCategories(boolean addMissingCategories) {
/* 102 */     this.m_bAddMissingCategories = addMissingCategories;
/*     */   }
/*     */ 
/*     */   public boolean getCheckInProgress() {
/* 106 */     return this.m_bCheckInProgress;
/*     */   }
/*     */ 
/*     */   public void setCheckInProgress(boolean a_bCheckInProgress) {
/* 110 */     this.m_bCheckInProgress = a_bCheckInProgress;
/*     */   }
/*     */ 
/*     */   public int getMessageCount() {
/* 114 */     if (getMessages() != null)
/*     */     {
/* 116 */       return getMessages().size();
/*     */     }
/* 118 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean getAddMissingAssets() {
/* 122 */     return this.m_bAddMissingAssets;
/*     */   }
/*     */ 
/*     */   public void setAddMissingAssets(boolean addMissingAssets) {
/* 126 */     this.m_bAddMissingAssets = addMissingAssets;
/*     */   }
/*     */ 
/*     */   public boolean getSkipCheck()
/*     */   {
/* 131 */     return this.m_bSkipCheck;
/*     */   }
/*     */ 
/*     */   public void setSkipCheck(boolean a_bSkipCheck) {
/* 135 */     this.m_bSkipCheck = a_bSkipCheck;
/*     */   }
/*     */ 
/*     */   public boolean getRemoveFromExistingCategories()
/*     */   {
/* 140 */     return this.m_bRemoveFromExistingCategories;
/*     */   }
/*     */ 
/*     */   public void setRemoveFromExistingCategories(boolean a_bRemoveFromExistingCategories) {
/* 144 */     this.m_bRemoveFromExistingCategories = a_bRemoveFromExistingCategories;
/*     */   }
/*     */ 
/*     */   public Attribute getAttribute()
/*     */   {
/* 149 */     return this.m_attribute;
/*     */   }
/*     */ 
/*     */   public void setAttribute(Attribute a_attribute) {
/* 153 */     this.m_attribute = a_attribute;
/*     */   }
/*     */ 
/*     */   public Vector getAttributes()
/*     */   {
/* 158 */     return this.m_vecAttributes;
/*     */   }
/*     */ 
/*     */   public void setAttributes(Vector a_vecAttributes) {
/* 162 */     this.m_vecAttributes = a_vecAttributes;
/*     */   }
/*     */ 
/*     */   public long getTreeId()
/*     */   {
/* 167 */     return this.m_lTreeId;
/*     */   }
/*     */ 
/*     */   public void setTreeId(long a_lTreeId) {
/* 171 */     this.m_lTreeId = a_lTreeId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.form.MetadataImportForm
 * JD-Core Version:    0.6.0
 */