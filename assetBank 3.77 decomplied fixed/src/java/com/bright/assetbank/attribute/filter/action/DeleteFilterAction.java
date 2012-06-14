/*    */ package com.bright.assetbank.attribute.filter.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.message.constant.MessageConstants;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteFilterAction extends BTransactionAction
/*    */   implements MessageConstants
/*    */ {
/* 45 */   private FilterManager m_filterManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 75 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 77 */       this.m_logger.error("This user does not have permission to view the admin pages");
/* 78 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 82 */     long lFilterId = getLongParameter(a_request, "id");
/*    */ 
/* 85 */     long lTypeId = getLongParameter(a_request, "type");
/*    */ 
/* 88 */     this.m_filterManager.deleteFilter(a_transaction, lFilterId);
/*    */ 
/* 90 */     return createRedirectingForward("type=" + lTypeId, a_mapping, "Success");
/*    */   }
/*    */ 
/*    */   public void setFilterManager(FilterManager a_filterManager)
/*    */   {
/* 95 */     this.m_filterManager = a_filterManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.DeleteFilterAction
 * JD-Core Version:    0.6.0
 */