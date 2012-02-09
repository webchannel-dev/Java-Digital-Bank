/*    */ package com.bright.assetbank.orgunit.form;
/*    */ 
/*    */ import com.bright.assetbank.orgunit.bean.OrgUnit;
/*    */ import com.bright.framework.category.form.DeleteCategoryForm;
/*    */ 
/*    */ public class DeletePermissionCategoryForm extends DeleteCategoryForm
/*    */ {
/*    */   private OrgUnit m_orgUnit;
/*    */ 
/*    */   public DeletePermissionCategoryForm()
/*    */   {
/* 23 */     this.m_orgUnit = new OrgUnit();
/*    */   }
/*    */ 
/*    */   public OrgUnit getOrgUnit()
/*    */   {
/* 39 */     return this.m_orgUnit;
/*    */   }
/*    */ 
/*    */   public void setOrgUnit(OrgUnit a_sOrgUnit)
/*    */   {
/* 48 */     this.m_orgUnit = a_sOrgUnit;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.orgunit.form.DeletePermissionCategoryForm
 * JD-Core Version:    0.6.0
 */