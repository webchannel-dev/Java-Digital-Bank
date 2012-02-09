/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RemoveAllAssetBoxSharesAction extends BTransactionAction
/*    */   implements AssetBoxConstants, FrameworkConstants
/*    */ {
/* 40 */   private AssetBoxManager m_assetBoxManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 49 */     ActionForward afForward = null;
/*    */ 
/* 52 */     long lAssetBoxId = getLongParameter(a_request, "assetBoxId");
/* 53 */     this.m_assetBoxManager.removeAllAssetBoxShares(a_dbTransaction, lAssetBoxId);
/*    */ 
/* 55 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 57 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*    */   {
/* 63 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.RemoveAllAssetBoxSharesAction
 * JD-Core Version:    0.6.0
 */