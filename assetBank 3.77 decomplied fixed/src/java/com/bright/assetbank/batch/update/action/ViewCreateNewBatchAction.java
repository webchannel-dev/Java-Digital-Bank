/*    */ package com.bright.assetbank.batch.update.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.search.action.ViewSearchAction;
/*    */ import com.bright.assetbank.search.form.BaseSearchForm;
/*    */ import com.bright.assetbank.search.util.SearchUtil;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewCreateNewBatchAction extends ViewSearchAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 56 */     SearchUtil.populateBulkUploads(a_dbTransaction, getUserManager(), (BaseSearchForm)a_form, userProfile);
/*    */ 
/* 58 */     return super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*    */   }
/*    */ 
/*    */   protected void loadSearchableAssetEntities(DBTransaction a_dbTransaction, ABUserProfile a_userProfile, BaseSearchForm a_form)
/*    */     throws Bn2Exception
/*    */   {
/* 72 */     SearchUtil.populateAssetEntities(a_dbTransaction, getAssetEntityManager(), a_userProfile, a_form);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.ViewCreateNewBatchAction
 * JD-Core Version:    0.6.0
 */