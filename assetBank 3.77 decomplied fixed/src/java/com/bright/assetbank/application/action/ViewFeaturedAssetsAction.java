/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.form.AssetListForm;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewFeaturedAssetsAction extends BTransactionAction
/*    */ {
/* 44 */   private IAssetManager m_assetManager = null;
/*    */ 
/*    */   public void setAssetManager(IAssetManager a_sAssetManager) {
/* 47 */     this.m_assetManager = a_sAssetManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     AssetListForm form = (AssetListForm)a_form;
/* 71 */     Vector vecAssets = this.m_assetManager.getFeaturedAssets(a_dbTransaction);
/* 72 */     form.setAssets(vecAssets);
/*    */ 
/* 74 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewFeaturedAssetsAction
 * JD-Core Version:    0.6.0
 */