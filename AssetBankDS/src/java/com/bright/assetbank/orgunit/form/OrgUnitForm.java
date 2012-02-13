/*     */ package com.bright.assetbank.orgunit.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.category.form.CategoryValidationForm;
/*     */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.customfield.bean.CustomField;
/*     */ import com.bright.framework.customfield.form.CustomFieldHolder;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class OrgUnitForm extends Bn2Form
/*     */   implements CustomFieldHolder, CategoryValidationForm
/*     */ {
/*     */   private OrgUnit m_orgUnit;
/*  42 */   private String m_sDiskQuotaString = null;
/*  43 */   private long m_lRootDescriptiveCategoryId = 0L;
/*     */ 
/*  45 */   private Vector<Category> m_descriptiveCategories = null;
/*  46 */   private Vector<ABUser> m_vecUserList = null;
/*  47 */   private Vector<? extends CustomField> m_vecCustomFields = null;
/*  48 */   private boolean m_bExtendedCategory = false;
/*     */   private Vector<OrgUnit> m_vecOrgUnitList;
/*     */ 
/*     */   public OrgUnitForm()
/*     */   {
/*  53 */     this.m_orgUnit = new OrgUnit();
/*     */   }
/*     */ 
/*     */   public Vector<OrgUnit> getOrgUnitList()
/*     */   {
/*  60 */     return this.m_vecOrgUnitList;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitList(Vector<OrgUnit> a_vecOrgUnitList)
/*     */   {
/*  65 */     this.m_vecOrgUnitList = a_vecOrgUnitList;
/*     */   }
/*     */ 
/*     */   public OrgUnit getOrgUnit()
/*     */   {
/*  70 */     return this.m_orgUnit;
/*     */   }
/*     */ 
/*     */   public void setOrgUnit(OrgUnit a_orgUnit)
/*     */   {
/*  75 */     this.m_orgUnit = a_orgUnit;
/*     */   }
/*     */ 
/*     */   public String getDiskQuotaString()
/*     */   {
/*  80 */     return this.m_sDiskQuotaString;
/*     */   }
/*     */ 
/*     */   public void setDiskQuotaString(String a_sDiskQuotaString)
/*     */   {
/*  85 */     this.m_sDiskQuotaString = a_sDiskQuotaString;
/*     */   }
/*     */ 
/*     */   public long getRootDescriptiveCategoryId()
/*     */   {
/*  90 */     return this.m_lRootDescriptiveCategoryId;
/*     */   }
/*     */ 
/*     */   public void setRootDescriptiveCategoryId(long a_lRootDescriptiveCategoryId)
/*     */   {
/*  95 */     this.m_lRootDescriptiveCategoryId = a_lRootDescriptiveCategoryId;
/*     */   }
/*     */ 
/*     */   public Vector<Category> getDescriptiveCategories()
/*     */   {
/* 100 */     return this.m_descriptiveCategories;
/*     */   }
/*     */ 
/*     */   public void setDescriptiveCategories(Vector<Category> a_categories)
/*     */   {
/* 105 */     this.m_descriptiveCategories = a_categories;
/*     */   }
/*     */ 
/*     */   public void setUserList(Vector<ABUser> a_vecUserList)
/*     */   {
/* 110 */     this.m_vecUserList = a_vecUserList;
/*     */   }
/*     */ 
/*     */   public Vector<ABUser> getUserList()
/*     */   {
/* 115 */     return this.m_vecUserList;
/*     */   }
/*     */ 
/*     */   public Vector<? extends CustomField> getCustomFields()
/*     */   {
/* 120 */     return this.m_vecCustomFields;
/*     */   }
/*     */ 
/*     */   public void setCustomFields(Vector<? extends CustomField> a_vecCustomFields)
/*     */   {
/* 125 */     this.m_vecCustomFields = a_vecCustomFields;
/*     */   }
/*     */ 
/*     */   public void setExtendedCategory(boolean a_bExtendedCategory)
/*     */   {
/* 130 */     this.m_bExtendedCategory = a_bExtendedCategory;
/*     */   }
/*     */ 
/*     */   public boolean getExtendedCategory()
/*     */   {
/* 135 */     return this.m_bExtendedCategory;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.form.OrgUnitForm
 * JD-Core Version:    0.6.0
 */