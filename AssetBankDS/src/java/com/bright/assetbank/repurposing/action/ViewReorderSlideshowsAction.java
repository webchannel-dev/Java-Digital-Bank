/*    */ package com.bright.assetbank.repurposing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.repurposing.form.RepurposingForm;
/*    */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.util.ArrayList;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewReorderSlideshowsAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 38 */   private AssetRepurposingManager m_repurposingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 48 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request.getSession());
/* 49 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 51 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 55 */     RepurposingForm form = (RepurposingForm)a_form;
/* 56 */     ArrayList alSlideshows = this.m_repurposingManager.getRepurposedSlideshows(a_dbTransaction, false, true, a_request);
/* 57 */     form.setRepurposedVersions(alSlideshows);
/*    */ 
/* 59 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager a_repurposingManager)
/*    */   {
/* 64 */     this.m_repurposingManager = a_repurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ViewReorderSlideshowsAction
 * JD-Core Version:    0.6.0
 */