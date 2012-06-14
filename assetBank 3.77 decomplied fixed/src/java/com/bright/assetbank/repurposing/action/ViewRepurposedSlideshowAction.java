/*    */ package com.bright.assetbank.repurposing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.repurposing.bean.RepurposedSlideshow;
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
/*    */ public class ViewRepurposedSlideshowAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 44 */   private AssetRepurposingManager m_repurposingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request.getSession());
/* 55 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 57 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 61 */     RepurposedSlideshow slideshow = null;
/* 62 */     if (a_request.getAttribute("repurposedSlideshow") != null)
/*    */     {
/* 64 */       slideshow = (RepurposedSlideshow)a_request.getAttribute("repurposedSlideshow");
/*    */     }
/*    */ 
/* 68 */     if (slideshow == null)
/*    */     {
/* 70 */       long lSlideshowId = getLongParameter(a_request, "id");
/* 71 */       slideshow = this.m_repurposingManager.getRepurposedSlideshow(a_dbTransaction, lSlideshowId, a_request);
/*    */     }
/*    */ 
/* 74 */     RepurposingForm form = (RepurposingForm)a_form;
/* 75 */     form.setRepurposedVersion(slideshow);
/*    */ 
/* 77 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager a_repurposingManager)
/*    */   {
/* 82 */     this.m_repurposingManager = a_repurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ViewRepurposedSlideshowAction
 * JD-Core Version:    0.6.0
 */