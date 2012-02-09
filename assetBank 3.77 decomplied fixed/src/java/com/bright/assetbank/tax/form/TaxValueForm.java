/*    */ package com.bright.assetbank.tax.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.tax.bean.TaxValue;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class TaxValueForm extends Bn2Form
/*    */ {
/* 33 */   private TaxValue m_taxValue = null;
/* 34 */   private Vector m_taxValueList = null;
/*    */ 
/*    */   public TaxValueForm()
/*    */   {
/* 38 */     this.m_taxValue = new TaxValue();
/*    */   }
/*    */ 
/*    */   public TaxValue getTaxValue()
/*    */   {
/* 43 */     if (this.m_taxValue == null)
/*    */     {
/* 45 */       this.m_taxValue = new TaxValue();
/*    */     }
/* 47 */     return this.m_taxValue;
/*    */   }
/*    */ 
/*    */   public void setTaxValue(TaxValue a_sTaxValue) {
/* 51 */     this.m_taxValue = a_sTaxValue;
/*    */   }
/*    */ 
/*    */   public Vector getTaxValueList()
/*    */   {
/* 56 */     return this.m_taxValueList;
/*    */   }
/*    */ 
/*    */   public void setTaxValueList(Vector a_sTaxValueList) {
/* 60 */     this.m_taxValueList = a_sTaxValueList;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.tax.form.TaxValueForm
 * JD-Core Version:    0.6.0
 */