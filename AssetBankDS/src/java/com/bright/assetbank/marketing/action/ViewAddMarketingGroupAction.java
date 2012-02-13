/*    */ package com.bright.assetbank.marketing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.language.service.LanguageManager;
/*    */ import com.bright.assetbank.marketing.form.MarketingGroupForm;
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
/*    */ public class ViewAddMarketingGroupAction extends MarketingGroupAction
/*    */ {
/* 42 */   private LanguageManager m_languageManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 62 */     MarketingGroupForm form = (MarketingGroupForm)a_form;
/*    */ 
/* 64 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 67 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 69 */       this.m_logger.error("ViewAddMarketingGroupAction.execute : User must be an administrator.");
/* 70 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 73 */     if (!form.getHasErrors())
/*    */     {
/* 75 */       if (AssetBankSettings.getSupportMultiLanguage())
/*    */       {
/* 78 */         this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getGroup());
/*    */       }
/*    */     }
/*    */ 
/* 82 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setLanguageManager(LanguageManager languageManager)
/*    */   {
/* 87 */     this.m_languageManager = languageManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.marketing.action.ViewAddMarketingGroupAction
 * JD-Core Version:    0.6.0
 */