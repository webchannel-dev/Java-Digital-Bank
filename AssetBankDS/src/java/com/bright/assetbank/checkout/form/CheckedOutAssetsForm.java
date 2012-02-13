/*    */ package com.bright.assetbank.checkout.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CheckedOutAssetsForm extends Bn2Form
/*    */ {
/* 16 */   private List<Asset> m_checkedOutAssets = null;
/*    */ 
/*    */   public void setCheckedOutAssets(List<Asset> a_checkedOutAssets) {
/* 19 */     this.m_checkedOutAssets = a_checkedOutAssets;
/*    */   }
/*    */ 
/*    */   public List<Asset> getCheckedOutAssets() {
/* 23 */     return this.m_checkedOutAssets;
/*    */   }
/*    */ 
/*    */   public boolean hasCheckedOutAssets()
/*    */   {
/* 32 */     return (this.m_checkedOutAssets != null) && (this.m_checkedOutAssets.size() > 0);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.checkout.form.CheckedOutAssetsForm
 * JD-Core Version:    0.6.0
 */