/*    */ package com.bright.assetbank.batch.update.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.search.action.SearchWithBuilderAction;
/*    */ import com.bright.assetbank.search.form.BaseSearchForm;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.search.bean.SearchQuery;
/*    */ import java.text.ParseException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class CreateNewBatchWithBuilderAction extends CreateNewBatchAction
/*    */ {
/*    */   protected SearchQuery getNewSearchCriteria(DBTransaction a_dbTransaction, BaseSearchForm a_form, HttpServletRequest a_request)
/*    */     throws ParseException, Bn2Exception
/*    */   {
/* 39 */     return SearchWithBuilderAction.createNewSearchBuilderQuery(a_form, a_request);
/*    */   }
/*    */ 
/*    */   protected boolean searchCriteriaSpecified(BaseSearchForm a_searchForm, SearchQuery a_searchQuery)
/*    */   {
/* 45 */     return SearchWithBuilderAction.searchCriteriaSpecified(a_searchForm);
/*    */   }
/*    */ 
/*    */   protected void validateForm(BaseSearchForm a_form, HttpServletRequest a_request)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     SearchWithBuilderAction.validateSearchBuilderForm(getNewTransaction(), this.m_listManager, a_form, a_request);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.CreateNewBatchWithBuilderAction
 * JD-Core Version:    0.6.0
 */