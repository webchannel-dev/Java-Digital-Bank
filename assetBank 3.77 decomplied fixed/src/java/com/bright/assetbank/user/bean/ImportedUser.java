/*    */ package com.bright.assetbank.user.bean;
/*    */ 
/*    */ import com.bright.framework.customfield.bean.CustomFieldSelectedValueSet;
/*    */ import com.bright.framework.customfield.bean.CustomFieldValueMapping;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ImportedUser extends ABUser
/*    */ {
/* 18 */   private String m_sGroupNames = null;
/*    */ 
/* 31 */   private CustomFieldSelectedValueSet customFieldsSet = null;
/*    */ 
/*    */   public String getGroupNames()
/*    */   {
/* 22 */     return this.m_sGroupNames;
/*    */   }
/*    */ 
/*    */   public void setGroupNames(String a_sGroupNames)
/*    */   {
/* 27 */     this.m_sGroupNames = a_sGroupNames;
/*    */   }
/*    */ 
/*    */   public CustomFieldSelectedValueSet getCustomFieldValues()
/*    */   {
/* 35 */     return this.customFieldsSet;
/*    */   }
/*    */ 
/*    */   public void addCustomFieldValue(CustomFieldValueMapping a_value)
/*    */   {
/* 40 */     if (this.customFieldsSet == null)
/*    */     {
/* 42 */       this.customFieldsSet = new CustomFieldSelectedValueSet();
/* 43 */       this.customFieldsSet.setUsageTypeId(1L);
/*    */     }
/* 45 */     Vector vecValues = this.customFieldsSet.getSelectedValues();
/* 46 */     if (vecValues == null)
/*    */     {
/* 48 */       vecValues = new Vector();
/*    */     }
/* 50 */     vecValues.add(a_value);
/* 51 */     this.customFieldsSet.setSelectedValues(vecValues);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.ImportedUser
 * JD-Core Version:    0.6.0
 */