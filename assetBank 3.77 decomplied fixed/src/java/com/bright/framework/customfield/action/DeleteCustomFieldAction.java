/*    */ package com.bright.framework.customfield.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.customfield.service.CustomFieldManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteCustomFieldAction extends BTransactionAction
/*    */ {
/* 42 */   private CustomFieldManager m_fieldManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 69 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 72 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 74 */       this.m_logger.error("This user does not have permission to view the admin pages");
/* 75 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 79 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 82 */     this.m_fieldManager.deleteCustomField(a_transaction, lId);
/* 83 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setCustomFieldManager(CustomFieldManager a_fieldManager)
/*    */   {
/* 88 */     this.m_fieldManager = a_fieldManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.action.DeleteCustomFieldAction
 * JD-Core Version:    0.6.0
 */