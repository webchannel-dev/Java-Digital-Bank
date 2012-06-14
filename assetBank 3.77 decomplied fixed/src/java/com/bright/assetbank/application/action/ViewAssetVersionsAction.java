/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.form.AssetListForm;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAssetVersionsAction extends BTransactionAction
/*    */ {
/* 42 */   protected IAssetManager m_assetManager = null;
/*    */ 
/*    */   public final ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 61 */     this.m_logger.debug("In ViewAssetVersionsAction.execute");
/* 62 */     AssetListForm form = (AssetListForm)a_form;
/*    */ 
/* 64 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 66 */     form.setAssets(this.m_assetManager.getAssetVersions(a_dbTransaction, lId));
/*    */ 
/* 68 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetManager(IAssetManager a_sAssetManager)
/*    */   {
/* 74 */     this.m_assetManager = a_sAssetManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewAssetVersionsAction
 * JD-Core Version:    0.6.0
 */