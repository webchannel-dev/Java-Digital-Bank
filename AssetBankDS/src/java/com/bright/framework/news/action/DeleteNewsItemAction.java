/*    */ package com.bright.framework.news.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.news.service.NewsManager;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteNewsItemAction extends BTransactionAction
/*    */ {
/* 41 */   private NewsManager m_newsManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 57 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 60 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 62 */       this.m_logger.error("SaveNewsItemAction.execute : User must be an administrator.");
/* 63 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 66 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 68 */     this.m_newsManager.deleteNewsItem(a_dbTransaction, lId);
/*    */ 
/* 70 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setNewsManager(NewsManager a_manager)
/*    */   {
/* 75 */     this.m_newsManager = a_manager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.news.action.DeleteNewsItemAction
 * JD-Core Version:    0.6.0
 */