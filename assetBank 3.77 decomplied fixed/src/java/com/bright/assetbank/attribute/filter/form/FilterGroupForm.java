/*    */ package com.bright.assetbank.attribute.filter.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.attribute.filter.bean.FilterGroup;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class FilterGroupForm extends Bn2Form
/*    */ {
/* 33 */   private Vector m_vecFilterGroups = null;
/* 34 */   private FilterGroup m_group = new FilterGroup();
/*    */ 
/*    */   public void setFilterGroups(Vector a_vecFilterGroups)
/*    */   {
/* 39 */     this.m_vecFilterGroups = a_vecFilterGroups;
/*    */   }
/*    */ 
/*    */   public Vector getFilterGroups()
/*    */   {
/* 44 */     return this.m_vecFilterGroups;
/*    */   }
/*    */ 
/*    */   public void setFilterGroup(FilterGroup a_group)
/*    */   {
/* 49 */     this.m_group = a_group;
/*    */   }
/*    */ 
/*    */   public FilterGroup getFilterGroup()
/*    */   {
/* 54 */     return this.m_group;
/*    */   }
/*    */ 
/*    */   public void validateSaveFilterGroup()
/*    */   {
/* 59 */     if (!StringUtil.stringIsPopulated(getFilterGroup().getName()))
/*    */     {
/* 61 */       addError("You need to provide a name");
/*    */     }
/*    */ 
/* 64 */     if (getFilterGroup().getFilterTypeId() <= 0)
/*    */     {
/* 66 */       addError("You need to select a filter type");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.form.FilterGroupForm
 * JD-Core Version:    0.6.0
 */