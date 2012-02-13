/*    */ package com.bright.assetbank.marketing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.marketing.bean.MarketingGroup;
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
/*    */ public class ViewEditMarketingGroupAction extends MarketingGroupAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 61 */     MarketingGroupForm form = (MarketingGroupForm)a_form;
/* 62 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 65 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 67 */       this.m_logger.error("ViewEditMarketingGroupAction.execute : User must be an administrator.");
/* 68 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 71 */     if (!form.getHasErrors())
/*    */     {
/* 73 */       long lId = getIntParameter(a_request, "id");
/*    */ 
/* 76 */       MarketingGroup group = getMarketingGroupManager().getMarketingGroup(a_dbTransaction, lId);
/*    */ 
/* 78 */       form.setGroup(group);
/*    */     }
/*    */ 
/* 81 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.ViewEditMarketingGroupAction
 * JD-Core Version:    0.6.0
 */