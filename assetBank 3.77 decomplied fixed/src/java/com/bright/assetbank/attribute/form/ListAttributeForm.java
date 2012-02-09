/*    */ package com.bright.assetbank.attribute.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.actiononasset.action.ActionOnAsset;
/*    */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*    */ import com.bright.assetbank.attribute.bean.AttributeValue.Translation;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ListAttributeForm extends Bn2Form
/*    */ {
/* 37 */   private List<AttributeValue> m_attributeValues = null;
/* 38 */   private AttributeValue m_attributeValue = null;
/* 39 */   private boolean m_bAddInAlphabeticOrder = false;
/* 40 */   private List<ActionOnAsset> m_actionsOnAssets = null;
/*    */ 
/*    */   public AttributeValue getValue()
/*    */   {
/* 45 */     if (this.m_attributeValue == null)
/*    */     {
/* 47 */       this.m_attributeValue = new AttributeValue();
/*    */ 
/* 50 */       Vector translations = this.m_attributeValue.getTranslations();
/* 51 */       for (int i = 0; i < 20; i++)
/*    */       {
/*    */         //AttributeValue tmp43_40 = this.m_attributeValue;
                 //tmp43_40.getClass(); translations.add(new AttributeValue.Translation(tmp43_40, new Language()));
                 translations.add(this.m_attributeValue.new Translation(new Language()));
/*    */       }
/*    */     }
/*    */ 
/* 57 */     return this.m_attributeValue;
/*    */   }
/*    */ 
/*    */   public void setValue(AttributeValue a_sValue)
/*    */   {
/* 63 */     this.m_attributeValue = a_sValue;
/*    */   }
/*    */ 
/*    */   public List<AttributeValue> getValues()
/*    */   {
/* 69 */     return this.m_attributeValues;
/*    */   }
/*    */ 
/*    */   public void setValues(List<AttributeValue> a_sValues)
/*    */   {
/* 75 */     this.m_attributeValues = a_sValues;
/*    */   }
/*    */ 
/*    */   public boolean getAddInAlphabeticOrder()
/*    */   {
/* 81 */     return this.m_bAddInAlphabeticOrder;
/*    */   }
/*    */ 
/*    */   public void setAddInAlphabeticOrder(boolean a_sAddInAlphabeticOrder)
/*    */   {
/* 87 */     this.m_bAddInAlphabeticOrder = a_sAddInAlphabeticOrder;
/*    */   }
/*    */ 
/*    */   public List<ActionOnAsset> getActionsOnAssets()
/*    */   {
/* 92 */     return this.m_actionsOnAssets;
/*    */   }
/*    */ 
/*    */   public void setActionsOnAssets(List<ActionOnAsset> actionsOnAssets)
/*    */   {
/* 97 */     this.m_actionsOnAssets = actionsOnAssets;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.form.ListAttributeForm
 * JD-Core Version:    0.6.0
 */