/*    */ package com.bright.assetbank.autocomplete.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.autocomplete.constant.ACWebConstants;
/*    */ import com.bright.assetbank.autocomplete.service.AutoCompleteUpdateManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RebuildACIndexAction extends Bn2Action
/*    */   implements AssetBankConstants, ACWebConstants
/*    */ {
/*    */   public static final String c_ksClassName = "RebuildACIndexAction";
/*    */   private AutoCompleteUpdateManager m_autoCompleteUpdateManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 53 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 56 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 58 */       this.m_logger.error("RebuildACIndexAction : User does not have admin permission : " + userProfile);
/* 59 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 62 */     this.m_autoCompleteUpdateManager.queueRebuildIndex(userProfile.getUser().getId());
/*    */ 
/* 64 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAutoCompleteUpdateManager(AutoCompleteUpdateManager a_autoCompleteUpdateManager)
/*    */   {
/* 69 */     this.m_autoCompleteUpdateManager = a_autoCompleteUpdateManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.action.RebuildACIndexAction
 * JD-Core Version:    0.6.0
 */