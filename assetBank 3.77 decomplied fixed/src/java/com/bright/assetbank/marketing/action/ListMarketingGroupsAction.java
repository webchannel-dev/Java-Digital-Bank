/*    */ package com.bright.assetbank.marketing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.marketing.form.MarketingGroupForm;
/*    */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ListMarketingGroupsAction extends MarketingGroupAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 58 */     MarketingGroupForm form = (MarketingGroupForm)a_form;
/* 59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 62 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 64 */       this.m_logger.error("EditMarketingGroupAction.execute : User must be an administrator.");
/* 65 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 69 */     form.setMarketingGroups(getMarketingGroupManager().getMarketingGroups(a_dbTransaction));
/*    */ 
/* 71 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.ListMarketingGroupsAction
 * JD-Core Version:    0.6.0
 */