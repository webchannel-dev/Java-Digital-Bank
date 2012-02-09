/*    */ package com.bright.framework.customfield.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.customfield.bean.CustomFieldSelectedValueSet;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class CustomFieldUseForm extends Bn2Form
/*    */ {
/* 33 */   protected Vector m_vecCustomFields = null;
/* 34 */   protected CustomFieldSelectedValueSet m_customFieldValues = null;
/*    */ 
/*    */   public void setCustomFields(Vector a_vecCustomFields)
/*    */   {
/* 38 */     this.m_vecCustomFields = a_vecCustomFields;
/*    */   }
/*    */ 
/*    */   public Vector getCustomFields()
/*    */   {
/* 43 */     return this.m_vecCustomFields;
/*    */   }
/*    */ 
/*    */   public void setCustomFieldValues(CustomFieldSelectedValueSet a_customFieldValues)
/*    */   {
/* 48 */     this.m_customFieldValues = a_customFieldValues;
/*    */   }
/*    */ 
/*    */   public CustomFieldSelectedValueSet getCustomFieldValues()
/*    */   {
/* 53 */     return this.m_customFieldValues;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.form.CustomFieldUseForm
 * JD-Core Version:    0.6.0
 */