/*    */ package com.bright.assetbank.commercialoption.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
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
/*    */ public class DeleteCommercialOptionAction extends BTransactionAction
/*    */ {
/* 44 */   private CommercialOptionManager m_coManager = null;
/*    */ 
/*    */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*    */   {
/* 48 */     this.m_coManager = a_coManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 77 */     ActionForward afForward = null;
/* 78 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 81 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 83 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 84 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 88 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 90 */     this.m_coManager.deleteCommercialOption(a_dbTransaction, lId);
/*    */ 
/* 93 */     String sQueryString = "";
/* 94 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*    */ 
/* 96 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.commercialoption.action.DeleteCommercialOptionAction
 * JD-Core Version:    0.6.0
 */