/*    */ package com.bright.assetbank.repurposing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ReorderSlideshowAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 35 */   private AssetRepurposingManager m_assetRepurposingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 46 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request.getSession());
/* 47 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 49 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 52 */     int iId = getIntParameter(a_request, "id");
/*    */ 
/* 54 */     int iMoveToId = getIntParameter(a_request, "moveTo");
/*    */ 
/* 57 */     this.m_assetRepurposingManager.moveSlideshowOnHomepage(a_dbTransaction, iId, iMoveToId);
/*    */ 
/* 60 */     return a_mapping.findForward("Ajax");
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager a_assetRepurposingManager)
/*    */   {
/* 65 */     this.m_assetRepurposingManager = a_assetRepurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ReorderSlideshowAction
 * JD-Core Version:    0.6.0
 */