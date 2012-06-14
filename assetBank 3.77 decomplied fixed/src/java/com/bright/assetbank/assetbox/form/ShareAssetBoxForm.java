/*    */ package com.bright.assetbank.assetbox.form;
/*    */ 
/*    */ import com.bright.assetbank.marketing.form.SendMarketingEmailForm;
/*    */ 
/*    */ public class ShareAssetBoxForm extends SendMarketingEmailForm
/*    */ {
/* 31 */   private long m_lAssetBoxId = 0L;
/* 32 */   private String m_sName = null;
/* 33 */   private boolean m_bSendEmailOnShare = false;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 37 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setName(String a_name)
/*    */   {
/* 42 */     this.m_sName = a_name;
/*    */   }
/*    */ 
/*    */   public long getAssetBoxId()
/*    */   {
/* 47 */     return this.m_lAssetBoxId;
/*    */   }
/*    */ 
/*    */   public void setAssetBoxId(long a_lAssetBoxId)
/*    */   {
/* 52 */     this.m_lAssetBoxId = a_lAssetBoxId;
/*    */   }
/*    */ 
/*    */   public boolean getSendEmailOnShare()
/*    */   {
/* 57 */     return this.m_bSendEmailOnShare;
/*    */   }
/*    */ 
/*    */   public void setSendEmailOnShare(boolean a_bSendEmailOnShare)
/*    */   {
/* 62 */     this.m_bSendEmailOnShare = a_bSendEmailOnShare;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.form.ShareAssetBoxForm
 * JD-Core Version:    0.6.0
 */