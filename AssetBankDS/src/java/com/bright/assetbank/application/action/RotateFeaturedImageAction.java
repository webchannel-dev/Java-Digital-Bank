/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
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
/*    */ public class RotateFeaturedImageAction extends BTransactionAction
/*    */ {
/* 42 */   protected IAssetManager m_assetManager = null;
/*    */ 
/*    */   public void setAssetManager(IAssetManager a_sAssetManager) {
/* 45 */     this.m_assetManager = a_sAssetManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 72 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 74 */       this.m_logger.debug("This user does not have permission to rotate the featured asset");
/* 75 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 78 */     this.m_assetManager.rotateFeaturedImage(a_dbTransaction);
/*    */ 
/* 80 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.RotateFeaturedImageAction
 * JD-Core Version:    0.6.0
 */