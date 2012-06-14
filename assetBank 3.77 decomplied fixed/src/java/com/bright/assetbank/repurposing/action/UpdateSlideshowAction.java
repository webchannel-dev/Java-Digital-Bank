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
/*    */ public class UpdateSlideshowAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 36 */   private AssetRepurposingManager m_assetRepurposingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 45 */     RepurposeSlideshowForm form = (RepurposeSlideshowForm)a_form;
/*    */ 
/* 48 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request.getSession());
/* 49 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 51 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 55 */     RepurposedSlideshow rs = new RepurposedSlideshow();
/* 56 */     rs.setId(form.getId());
/* 57 */     rs.setDescription(form.getDescription());
/* 58 */     rs.setDefaultOnHomepage(form.getDefaultOnHomepage());
/* 59 */     rs.setShowInListOnHomepage(form.getShowInListOnHomepage());
/*    */ 
/* 62 */     this.m_assetRepurposingManager.updateRepurposedSlideshow(a_dbTransaction, rs);
/*    */ 
/* 65 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager a_assetRepurposingManager)
/*    */   {
/* 70 */     this.m_assetRepurposingManager = a_assetRepurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.UpdateSlideshowAction
 * JD-Core Version:    0.6.0
 */