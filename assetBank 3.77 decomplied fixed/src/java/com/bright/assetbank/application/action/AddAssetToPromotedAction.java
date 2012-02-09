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
/*    */ public class AddAssetToPromotedAction extends BTransactionAction
/*    */ {
/* 43 */   protected IAssetManager m_assetManager = null;
/*    */ 
/*    */   public void setAssetManager(IAssetManager a_sAssetManager) {
/* 46 */     this.m_assetManager = a_sAssetManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 76 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 78 */       this.m_logger.debug("This user does not have permission to promote an asset");
/* 79 */       return a_mapping.findForward("NoPermission");
/*    */     }
/* 81 */     long lAssetId = 0L;
/*    */ 
/* 84 */     lAssetId = getLongParameter(a_request, "id");
/* 85 */     this.m_assetManager.addAssetToPromoted(a_dbTransaction, lAssetId);
/*    */ 
/* 87 */     String sQueryString = "id=" + lAssetId;
/* 88 */     return createRedirectingForward(sQueryString, a_mapping, "Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.AddAssetToPromotedAction
 * JD-Core Version:    0.6.0
 */