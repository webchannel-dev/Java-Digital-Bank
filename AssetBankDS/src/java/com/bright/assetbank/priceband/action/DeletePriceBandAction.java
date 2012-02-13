/*    */ package com.bright.assetbank.priceband.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.priceband.service.PriceBandManager;
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
/*    */ public class DeletePriceBandAction extends BTransactionAction
/*    */ {
/* 42 */   private PriceBandManager m_pbManager = null;
/*    */ 
/*    */   public void setPriceBandManager(PriceBandManager a_pbManager) {
/* 45 */     this.m_pbManager = a_pbManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 68 */     ActionForward afForward = null;
/* 69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 72 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 74 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 75 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 79 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 81 */     this.m_pbManager.deletePriceBand(a_dbTransaction, lId);
/*    */ 
/* 84 */     String sQueryString = "";
/* 85 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*    */ 
/* 87 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.action.DeletePriceBandAction
 * JD-Core Version:    0.6.0
 */