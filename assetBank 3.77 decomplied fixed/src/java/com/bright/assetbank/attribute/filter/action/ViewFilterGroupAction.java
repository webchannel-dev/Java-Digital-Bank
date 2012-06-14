/*    */ package com.bright.assetbank.attribute.filter.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*    */ import com.bright.assetbank.attribute.filter.form.FilterGroupForm;
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
/*    */ public class ViewFilterGroupAction extends BTransactionAction
/*    */   implements AttributeConstants
/*    */ {
/* 44 */   private FilterManager m_filterManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 74 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 76 */       this.m_logger.error("This user does not have permission to view the admin pages");
/* 77 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 80 */     FilterGroupForm form = (FilterGroupForm)a_form;
/*    */ 
/* 85 */     long lFilterGroupId = getLongParameter(a_request, "id");
/*    */ 
/* 87 */     if (lFilterGroupId > 0L)
/*    */     {
/* 89 */       form.setFilterGroup(this.m_filterManager.getFilterGroup(a_transaction, lFilterGroupId));
/*    */     }
/*    */ 
/* 92 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setFilterManager(FilterManager a_filterManager)
/*    */   {
/* 97 */     this.m_filterManager = a_filterManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.ViewFilterGroupAction
 * JD-Core Version:    0.6.0
 */