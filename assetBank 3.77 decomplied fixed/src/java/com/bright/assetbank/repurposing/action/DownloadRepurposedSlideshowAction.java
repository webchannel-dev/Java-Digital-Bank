/*    */ package com.bright.assetbank.repurposing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.repurposing.form.RepurposingForm;
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
/*    */ public class DownloadRepurposedSlideshowAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 42 */   private AssetRepurposingManager m_repurposingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request.getSession());
/* 53 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 55 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 59 */     long lSlideshowId = getLongParameter(a_request, "id");
/* 60 */     String sSlideshowDownloadPath = this.m_repurposingManager.downloadRepurposedSlideshow(a_dbTransaction, lSlideshowId, a_request);
/* 61 */     RepurposingForm form = (RepurposingForm)a_form;
/* 62 */     form.setDownloadUrl(sSlideshowDownloadPath);
/*    */ 
/* 64 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager a_repurposingManager)
/*    */   {
/* 69 */     this.m_repurposingManager = a_repurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.DownloadRepurposedSlideshowAction
 * JD-Core Version:    0.6.0
 */