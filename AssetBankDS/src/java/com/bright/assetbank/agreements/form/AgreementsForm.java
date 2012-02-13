/*    */ package com.bright.assetbank.agreements.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.agreements.bean.Agreement;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class AgreementsForm extends Bn2Form
/*    */ {
/*    */   private Agreement m_agreement;
/*    */   private Vector m_vecAgreements;
/*    */   boolean m_bIsComplete;
/* 37 */   boolean m_bIsNew = false;
/*    */ 
/*    */   public AgreementsForm()
/*    */   {
/* 42 */     this.m_agreement = new Agreement();
/*    */   }
/*    */ 
/*    */   public Agreement getAgreement() {
/* 46 */     return this.m_agreement;
/*    */   }
/*    */   public void setAgreement(Agreement a_agreement) {
/* 49 */     this.m_agreement = a_agreement;
/*    */   }
/*    */ 
/*    */   public Vector getAgreements()
/*    */   {
/* 55 */     return this.m_vecAgreements;
/*    */   }
/*    */   public void setAgreements(Vector a_vecAgreements) {
/* 58 */     this.m_vecAgreements = a_vecAgreements;
/*    */   }
/*    */ 
/*    */   public boolean getIsComplete() {
/* 62 */     return this.m_bIsComplete;
/*    */   }
/*    */   public void setIsComplete(boolean a_bIsComplete) {
/* 65 */     this.m_bIsComplete = a_bIsComplete;
/*    */   }
/*    */ 
/*    */   public void setIsNew(boolean a_bIsNew)
/*    */   {
/* 70 */     this.m_bIsNew = a_bIsNew;
/*    */   }
/*    */ 
/*    */   public boolean getIsNew()
/*    */   {
/* 75 */     return this.m_bIsNew;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.agreements.form.AgreementsForm
 * JD-Core Version:    0.6.0
 */