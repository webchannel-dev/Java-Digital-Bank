/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteAssetBoxAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 42 */   private AssetBoxManager m_assetBoxManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 54 */     if (userProfile.getNumOwnAssetBoxes() <= 1)
/*    */     {
/* 56 */       this.m_logger.error("DeleteAssetBoxAction: Cannot delete the last asset box - this should not be allowed by the UI");
/* 57 */       return a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 60 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 62 */     this.m_assetBoxManager.deleteAssetBox(a_dbTransaction, lId);
/*    */ 
/* 64 */     userProfile.deleteAssetBox(lId);
/*    */ 
/* 66 */     this.m_assetBoxManager.refreshAssetBoxInProfile(a_dbTransaction, userProfile, 0L);
/*    */ 
/* 68 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*    */   {
/* 74 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.DeleteAssetBoxAction
 * JD-Core Version:    0.6.0
 */