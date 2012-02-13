/*    */ package com.bright.framework.news.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.news.form.NewsForm;
/*    */ import com.bright.framework.news.service.NewsManager;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewNewsItemsAction extends BTransactionAction
/*    */ {
/* 41 */   private NewsManager m_newsManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 57 */     NewsForm form = (NewsForm)a_form;
/*    */ 
/* 60 */     form.setNewsItems(this.m_newsManager.getNewsItems(a_dbTransaction, true, AssetBankSettings.getNewsMaxCharacters(), 0));
/*    */ 
/* 62 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setNewsManager(NewsManager a_manager)
/*    */   {
/* 68 */     this.m_newsManager = a_manager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.news.action.ViewNewsItemsAction
 * JD-Core Version:    0.6.0
 */