/*    */ package com.bright.assetbank.attribute.filter.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.filter.service.FilterManager;
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
/*    */ public class DeleteFilterGroupAction extends BTransactionAction
/*    */ {
/* 43 */   private FilterManager m_filterManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 73 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 75 */       this.m_logger.error("This user does not have permission to view the admin pages");
/* 76 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 79 */     long lGroupId = getLongParameter(a_request, "id");
/* 80 */     this.m_filterManager.deleteFilterGroup(a_transaction, lGroupId);
/*    */ 
/* 82 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setFilterManager(FilterManager a_filterManager)
/*    */   {
/* 87 */     this.m_filterManager = a_filterManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.DeleteFilterGroupAction
 * JD-Core Version:    0.6.0
 */