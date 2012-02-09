/*    */ package com.bright.assetbank.repurposing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.repurposing.bean.RepurposedSlideshow;
/*    */ import com.bright.assetbank.repurposing.form.RepurposeSlideshowForm;
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
/*    */ public class ViewEditRepurposedSlideshowAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 37 */   private AssetRepurposingManager m_repurposingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 46 */     RepurposeSlideshowForm form = (RepurposeSlideshowForm)a_form;
/*    */ 
/* 49 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request.getSession());
/* 50 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 52 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 55 */     long lSlideshowId = getLongParameter(a_request, "id");
/* 56 */     RepurposedSlideshow slideshow = this.m_repurposingManager.getRepurposedSlideshow(a_dbTransaction, lSlideshowId, a_request);
/*    */ 
/* 59 */     form.setDescription(slideshow.getDescription());
/* 60 */     form.setShowInListOnHomepage(slideshow.getShowInListOnHomepage());
/* 61 */     form.setDefaultOnHomepage(slideshow.getDefaultOnHomepage());
/*    */ 
/* 63 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager a_repurposingManager)
/*    */   {
/* 68 */     this.m_repurposingManager = a_repurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ViewEditRepurposedSlideshowAction
 * JD-Core Version:    0.6.0
 */