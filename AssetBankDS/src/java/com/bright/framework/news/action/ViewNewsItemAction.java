/*    */ package com.bright.framework.news.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.news.form.NewsForm;
/*    */ import com.bright.framework.news.service.NewsManager;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewNewsItemAction extends BTransactionAction
/*    */ {
/* 42 */   private NewsManager m_newsManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 58 */     NewsForm form = (NewsForm)a_form;
/* 59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 61 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 64 */     form.setNewsItem(this.m_newsManager.getNewsItem(a_dbTransaction, lId, userProfile.getCurrentLanguage(), true));
/*    */ 
/* 66 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setNewsManager(NewsManager a_manager)
/*    */   {
/* 72 */     this.m_newsManager = a_manager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.news.action.ViewNewsItemAction
 * JD-Core Version:    0.6.0
 */