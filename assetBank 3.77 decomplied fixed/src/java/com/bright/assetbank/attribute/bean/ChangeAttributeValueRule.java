/*    */ package com.bright.assetbank.attribute.bean;
/*    */ 
/*    */ import com.bright.assetbank.actiononasset.action.ActionOnAsset;
/*    */ import com.bright.framework.common.bean.StringDataBean;
/*    */ 
/*    */ public class ChangeAttributeValueRule extends DateAttributeRule
/*    */ {
/*    */   private StringDataBean m_attributeToChange;
/*    */   private String m_sNewValue;
/* 37 */   private long m_lActionOnAssetId = 0L;
/* 38 */   private ActionOnAsset m_action = null;
/*    */ 
/*    */   public ChangeAttributeValueRule()
/*    */   {
/* 46 */     this.m_attributeToChange = new StringDataBean();
/* 47 */     this.m_sNewValue = "";
/*    */   }
/*    */ 
/*    */   public StringDataBean getAttributeToChange()
/*    */   {
/* 52 */     return this.m_attributeToChange;
/*    */   }
/*    */ 
/*    */   public void setAttributeToChange(StringDataBean a_sAttributeToChange) {
/* 56 */     this.m_attributeToChange = a_sAttributeToChange;
/*    */   }
/*    */ 
/*    */   public String getNewValue() {
/* 60 */     return this.m_sNewValue;
/*    */   }
/*    */ 
/*    */   public void setNewValue(String a_sNewValue) {
/* 64 */     this.m_sNewValue = a_sNewValue;
/*    */   }
/*    */ 
/*    */   public long getActionOnAssetId()
/*    */   {
/* 69 */     return this.m_lActionOnAssetId;
/*    */   }
/*    */ 
/*    */   public void setActionOnAssetId(long actionOnAssetId)
/*    */   {
/* 74 */     this.m_lActionOnAssetId = actionOnAssetId;
/*    */   }
/*    */ 
/*    */   public ActionOnAsset getAction()
/*    */   {
/* 79 */     return this.m_action;
/*    */   }
/*    */ 
/*    */   public void setAction(ActionOnAsset m_action)
/*    */   {
/* 84 */     this.m_action = m_action;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.ChangeAttributeValueRule
 * JD-Core Version:    0.6.0
 */