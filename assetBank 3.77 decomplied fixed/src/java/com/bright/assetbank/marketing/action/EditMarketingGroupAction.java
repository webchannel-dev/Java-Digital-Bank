/*    */ package com.bright.assetbank.marketing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.marketing.bean.MarketingGroup;
/*    */ import com.bright.assetbank.marketing.form.MarketingGroupForm;
/*    */ import com.bright.assetbank.marketing.service.MarketingGroupManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class EditMarketingGroupAction extends MarketingGroupAction
/*    */   implements AssetBankConstants
/*    */ {
/* 87 */   protected ListManager m_listManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 63 */     MarketingGroupForm form = (MarketingGroupForm)a_form;
/*    */ 
/* 65 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 68 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 70 */       this.m_logger.error("EditMarketingGroupAction.execute : User must be an administrator.");
/* 71 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 75 */     if (StringUtils.isEmpty(form.getGroup().getName()))
/*    */     {
/* 77 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "marketingGroupNameRequired", userProfile.getCurrentLanguage()).getBody());
/* 78 */       return a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 82 */     getMarketingGroupManager().saveMarketingGroup(a_dbTransaction, form.getGroup());
/*    */ 
/* 84 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setListManager(ListManager listManager)
/*    */   {
/* 90 */     this.m_listManager = listManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.EditMarketingGroupAction
 * JD-Core Version:    0.6.0
 */