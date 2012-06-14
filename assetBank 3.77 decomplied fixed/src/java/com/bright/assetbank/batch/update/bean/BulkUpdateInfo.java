/*     */ package com.bright.assetbank.batch.update.bean;
/*     */ 
/*     */ import com.bright.assetbank.workflow.bean.WorkflowUpdate;
/*     */ import java.util.Map;
/*     */ import org.apache.struts.upload.FormFile;
/*     */ 
/*     */ public class BulkUpdateInfo extends BatchAssetInfo
/*     */ {
/*  45 */   private Batch m_batchUpdate = null;
/*  46 */   private Map<String, String> m_hmAttributesToReplace = null;
/*  47 */   private Map<String, String> m_hmAttributesToAppend = null;
/*  48 */   private Map<String, String> m_hmAttributesToPrepend = null;
/*  49 */   private Map<String, String> m_hmAttributesToRemove = null;
/*  50 */   private Map<String, String> m_hmAttributeDelimiters = null;
/*  51 */   private boolean m_bUpdateAgreement = false;
/*     */ 
/*  53 */   private int m_iRotateImagesAngle = 0;
/*  54 */   private long m_lSessionId = 0L;
/*  55 */   private WorkflowUpdate m_update = null;
/*     */ 
/*  57 */   private boolean m_bDeleteAssets = false;
/*     */ 
/*  59 */   private boolean m_bAppendCategories = false;
/*  60 */   private boolean m_bReplaceCategories = false;
/*  61 */   private boolean m_bRemoveCategories = false;
/*     */ 
/*  63 */   private boolean m_bAppendAccessLevels = false;
/*  64 */   private boolean m_bReplaceAccessLevels = false;
/*  65 */   private boolean m_bRemoveAccessLevels = false;
/*     */ 
/*  67 */   private boolean m_bUnrelateAssets = false;
/*     */ 
/*  69 */   private FormFile m_substituteFile = null;
/*     */ 
/*     */   public Batch getBatchUpdate()
/*     */   {
/*  73 */     return this.m_batchUpdate;
/*     */   }
/*     */ 
/*     */   public void setBatchUpdate(Batch a_sBatchUpdate) {
/*  77 */     this.m_batchUpdate = a_sBatchUpdate;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttributesToReplace()
/*     */   {
/*  82 */     return this.m_hmAttributesToReplace;
/*     */   }
/*     */ 
/*     */   public void setAttributesToReplace(Map<String, String> a_sHmAttributesToReplace) {
/*  86 */     this.m_hmAttributesToReplace = a_sHmAttributesToReplace;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttributesToAppend()
/*     */   {
/*  91 */     return this.m_hmAttributesToAppend;
/*     */   }
/*     */ 
/*     */   public void setAttributesToAppend(Map<String, String> a_attributesToAppend) {
/*  95 */     this.m_hmAttributesToAppend = a_attributesToAppend;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttributesToPrepend()
/*     */   {
/* 100 */     return this.m_hmAttributesToPrepend;
/*     */   }
/*     */ 
/*     */   public void setAttributesToPrepend(Map<String, String> a_attributesToPrepend) {
/* 104 */     this.m_hmAttributesToPrepend = a_attributesToPrepend;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttributesToRemove()
/*     */   {
/* 109 */     return this.m_hmAttributesToRemove;
/*     */   }
/*     */ 
/*     */   public void setAttributesToRemove(Map<String, String> a_attributesToRemove) {
/* 113 */     this.m_hmAttributesToRemove = a_attributesToRemove;
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAttributeDelimiters()
/*     */   {
/* 118 */     return this.m_hmAttributeDelimiters;
/*     */   }
/*     */ 
/*     */   public void setAttributeDelimiters(Map<String, String> a_hmAttributeDelimiters) {
/* 122 */     this.m_hmAttributeDelimiters = a_hmAttributeDelimiters;
/*     */   }
/*     */ 
/*     */   public void setRotateImagesAngle(int a_iRotateImagesAngle)
/*     */   {
/* 127 */     this.m_iRotateImagesAngle = a_iRotateImagesAngle;
/*     */   }
/*     */ 
/*     */   public int getRotateImagesAngle()
/*     */   {
/* 132 */     return this.m_iRotateImagesAngle;
/*     */   }
/*     */ 
/*     */   public void setSessionId(long a_lSessionId)
/*     */   {
/* 137 */     this.m_lSessionId = a_lSessionId;
/*     */   }
/*     */ 
/*     */   public long getSessionId()
/*     */   {
/* 142 */     return this.m_lSessionId;
/*     */   }
/*     */ 
/*     */   public void setUpdateAgreement(boolean a_bUpdateAgreement)
/*     */   {
/* 147 */     this.m_bUpdateAgreement = a_bUpdateAgreement;
/*     */   }
/*     */ 
/*     */   public boolean getUpdateAgreement()
/*     */   {
/* 152 */     return this.m_bUpdateAgreement;
/*     */   }
/*     */ 
/*     */   public void setDeleteAssets(boolean a_bDeleteAssets)
/*     */   {
/* 157 */     this.m_bDeleteAssets = a_bDeleteAssets;
/*     */   }
/*     */ 
/*     */   public boolean getDeleteAssets()
/*     */   {
/* 162 */     return this.m_bDeleteAssets;
/*     */   }
/*     */ 
/*     */   public void setWorkflowUpdate(WorkflowUpdate a_update)
/*     */   {
/* 167 */     this.m_update = a_update;
/*     */   }
/*     */ 
/*     */   public WorkflowUpdate getWorkflowUpdate()
/*     */   {
/* 172 */     return this.m_update;
/*     */   }
/*     */ 
/*     */   public boolean getAppendCategories()
/*     */   {
/* 177 */     return this.m_bAppendCategories;
/*     */   }
/*     */ 
/*     */   public void setAppendCategories(boolean a_bAppendCategories) {
/* 181 */     this.m_bAppendCategories = a_bAppendCategories;
/*     */   }
/*     */ 
/*     */   public boolean getReplaceCategories() {
/* 185 */     return this.m_bReplaceCategories;
/*     */   }
/*     */ 
/*     */   public void setReplaceCategories(boolean a_bReplaceCategories) {
/* 189 */     this.m_bReplaceCategories = a_bReplaceCategories;
/*     */   }
/*     */ 
/*     */   public boolean getRemoveCategories() {
/* 193 */     return this.m_bRemoveCategories;
/*     */   }
/*     */ 
/*     */   public void setRemoveCategories(boolean a_bRemoveCategories) {
/* 197 */     this.m_bRemoveCategories = a_bRemoveCategories;
/*     */   }
/*     */ 
/*     */   public boolean getAppendAccessLevels()
/*     */   {
/* 202 */     return this.m_bAppendAccessLevels;
/*     */   }
/*     */ 
/*     */   public void setAppendAccessLevels(boolean a_bAppendAccessLevels) {
/* 206 */     this.m_bAppendAccessLevels = a_bAppendAccessLevels;
/*     */   }
/*     */ 
/*     */   public boolean getReplaceAccessLevels() {
/* 210 */     return this.m_bReplaceAccessLevels;
/*     */   }
/*     */ 
/*     */   public void setReplaceAccessLevels(boolean a_bReplaceAccessLevels) {
/* 214 */     this.m_bReplaceAccessLevels = a_bReplaceAccessLevels;
/*     */   }
/*     */ 
/*     */   public boolean getRemoveAccessLevels() {
/* 218 */     return this.m_bRemoveAccessLevels;
/*     */   }
/*     */ 
/*     */   public void setRemoveAccessLevels(boolean a_bRemoveAccessLevels) {
/* 222 */     this.m_bRemoveAccessLevels = a_bRemoveAccessLevels;
/*     */   }
/*     */ 
/*     */   public boolean getUnrelateAssets()
/*     */   {
/* 227 */     return this.m_bUnrelateAssets;
/*     */   }
/*     */ 
/*     */   public void setUnrelateAssets(boolean a_bUnrelateAssets)
/*     */   {
/* 232 */     this.m_bUnrelateAssets = a_bUnrelateAssets;
/*     */   }
/*     */ 
/*     */   public FormFile getSubstituteFile()
/*     */   {
/* 237 */     return this.m_substituteFile;
/*     */   }
/*     */ 
/*     */   public void setSubstituteFile(FormFile a_substituteFile) {
/* 241 */     this.m_substituteFile = a_substituteFile;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.bean.BulkUpdateInfo
 * JD-Core Version:    0.6.0
 */