/*    */ package com.bright.assetbank.attribute.filter.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*    */ import com.bright.assetbank.attribute.filter.form.FilterForm;
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
/*    */ public class SetDefaultFilterAction extends BTransactionAction
/*    */   implements AttributeConstants, AssetBankConstants
/*    */ {
/*    */   private static final String c_ksClassName = "SetDefaultFilterAction";
/* 46 */   private FilterManager m_filterManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 73 */     ActionForward afForward = null;
/* 74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 77 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 79 */       this.m_logger.error("SetDefaultFilterActionThis user does not have permission to view the admin pages");
/* 80 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 84 */     FilterForm form = (FilterForm)a_form;
/* 85 */     this.m_filterManager.setDefaultFilter(a_dbTransaction, form.getDefaultFilterId());
/* 86 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 89 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setFilterManager(FilterManager a_filterManager)
/*    */   {
/* 94 */     this.m_filterManager = a_filterManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.SetDefaultFilterAction
 * JD-Core Version:    0.6.0
 */