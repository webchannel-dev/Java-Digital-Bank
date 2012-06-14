/*     */ package com.bright.assetbank.report.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.usage.form.ReportForm;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class AssetReportForm extends ReportForm
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*     */   private Attribute m_attribute;
/*     */   private int m_iDateType;
/*     */   private int m_iFileSizeOperator;
/*     */   private float m_iFileSize;
/*  54 */   private int m_iFileSizeMultiplier = 1024;
/*  55 */   private Vector m_vecKeywordPickers = null;
/*     */ 
/*  57 */   private boolean m_bShowCategoryTotalsOnly = false;
/*     */ 
/*     */   public AssetReportForm()
/*     */   {
/*  62 */     this.m_attribute = new Attribute();
/*     */   }
/*     */ 
/*     */   public Attribute getAttribute()
/*     */   {
/*  68 */     return this.m_attribute;
/*     */   }
/*     */ 
/*     */   public void setAttribute(Attribute a_sAttribute) {
/*  72 */     this.m_attribute = a_sAttribute;
/*     */   }
/*     */ 
/*     */   public int getDateType()
/*     */   {
/*  77 */     return this.m_iDateType;
/*     */   }
/*     */ 
/*     */   public void setDateType(int a_sDateType) {
/*  81 */     this.m_iDateType = a_sDateType;
/*     */   }
/*     */ 
/*     */   public float getFileSize()
/*     */   {
/*  86 */     return this.m_iFileSize;
/*     */   }
/*     */ 
/*     */   public void setFileSize(float a_fFileSize)
/*     */   {
/*  91 */     this.m_iFileSize = a_fFileSize;
/*     */   }
/*     */ 
/*     */   public int getFileSizeMultiplier()
/*     */   {
/*  96 */     return this.m_iFileSizeMultiplier;
/*     */   }
/*     */ 
/*     */   public void setFileSizeMultiplier(int a_sFileSizeMultiplier)
/*     */   {
/* 101 */     this.m_iFileSizeMultiplier = a_sFileSizeMultiplier;
/*     */   }
/*     */ 
/*     */   public int getFileSizeOperator()
/*     */   {
/* 106 */     return this.m_iFileSizeOperator;
/*     */   }
/*     */ 
/*     */   public void setFileSizeOperator(int a_sFileSizeOperator)
/*     */   {
/* 111 */     this.m_iFileSizeOperator = a_sFileSizeOperator;
/*     */   }
/*     */ 
/*     */   public void setKeywordPickers(Vector a_vecKeywordPickers)
/*     */   {
/* 116 */     this.m_vecKeywordPickers = a_vecKeywordPickers;
/*     */   }
/*     */ 
/*     */   public Vector getKeywordPickers()
/*     */   {
/* 121 */     return this.m_vecKeywordPickers;
/*     */   }
/*     */ 
/*     */   public boolean getShowCategoryTotalsOnly()
/*     */   {
/* 126 */     return this.m_bShowCategoryTotalsOnly;
/*     */   }
/*     */ 
/*     */   public void setShowCategoryTotalsOnly(boolean a_bShowCategoryTotalsOnly) {
/* 130 */     this.m_bShowCategoryTotalsOnly = a_bShowCategoryTotalsOnly;
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, ABUserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager) throws Bn2Exception
/*     */   {
/* 135 */     super.validate(a_request, a_userProfile, a_dbTransaction, a_listManager);
/*     */ 
/* 137 */     if (!getHasErrors())
/*     */     {
/* 139 */       if ((StringUtils.isEmpty(getStartDateString())) && (StringUtils.isEmpty(getEndDateString())) && (getFileSize() == 0.0F))
/*     */       {
/* 141 */         addError("Please select some report criteria.");
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.report.form.AssetReportForm
 * JD-Core Version:    0.6.0
 */