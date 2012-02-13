/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RemoveSharedAssetBoxAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 42 */   private AssetBoxManager m_assetBoxManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 53 */     long lId = getLongParameter(a_request, "id");
/* 54 */     this.m_assetBoxManager.removeSharedAssetBox(a_dbTransaction, userProfile.getUser().getId(), lId);
/* 55 */     this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/* 56 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*    */   {
/* 62 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.RemoveSharedAssetBoxAction
 * JD-Core Version:    0.6.0
 */