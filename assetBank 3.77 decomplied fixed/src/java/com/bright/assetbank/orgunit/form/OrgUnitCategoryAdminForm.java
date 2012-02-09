/*    */ package com.bright.assetbank.orgunit.form;
/*    */ 
/*    */ import com.bright.assetbank.category.form.CategoryAdminForm;
/*    */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*    */ 
/*    */ public class OrgUnitCategoryAdminForm extends CategoryAdminForm
/*    */ {
/*    */   private OrgUnit m_orgUnit;
/*    */ 
/*    */   public OrgUnitCategoryAdminForm()
/*    */   {
/* 36 */     this.m_orgUnit = new OrgUnit();
/*    */   }
/*    */ 
/*    */   public OrgUnit getOrgUnit()
/*    */   {
/* 52 */     return this.m_orgUnit;
/*    */   }
/*    */ 
/*    */   public void setOrgUnit(OrgUnit a_sOrgUnit)
/*    */   {
/* 61 */     this.m_orgUnit = a_sOrgUnit;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.form.OrgUnitCategoryAdminForm
 * JD-Core Version:    0.6.0
 */