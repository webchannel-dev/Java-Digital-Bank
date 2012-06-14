/*    */ package com.bright.assetbank.repurposing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RemoveRepurposedSlideshowAction extends BTransactionAction
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/*    */   private AssetRepurposingManager m_assetRepurposingManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ABUserProfile profile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 52 */     if (!profile.getIsAdmin())
/*    */     {
/* 54 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 57 */     long lId = getLongParameter(a_request, "id");
/* 58 */     this.m_assetRepurposingManager.removeRepurposedSlideshow(a_dbTransaction, lId);
/*    */ 
/* 60 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager assetRepurposingManager)
/*    */   {
/* 65 */     this.m_assetRepurposingManager = assetRepurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.RemoveRepurposedSlideshowAction
 * JD-Core Version:    0.6.0
 */