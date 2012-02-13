/*    */ package com.bright.assetbank.updater.action;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public abstract class ApplicationUpdateAction extends BTransactionAction
/*    */ {
/*    */   protected String getVersion(HttpServletRequest a_request)
/*    */   {
/* 37 */     String sVersion = a_request.getParameter("version");
/* 38 */     if (AssetBankSettings.isApplicationUpdateInProgress())
/*    */     {
/* 40 */       if (StringUtil.stringIsPopulated(AssetBankSettings.getApplicationUpdateInProgressType()))
/*    */       {
/* 42 */         sVersion = AssetBankSettings.getApplicationUpdateInProgressType();
/*    */       }
/*    */     }
/* 45 */     return sVersion;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.updater.action.ApplicationUpdateAction
 * JD-Core Version:    0.6.0
 */