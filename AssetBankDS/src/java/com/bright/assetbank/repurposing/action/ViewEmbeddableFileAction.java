/*    */ package com.bright.assetbank.repurposing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.form.AssetForm;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
/*    */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewEmbeddableFileAction extends BTransactionAction
/*    */ {
/* 43 */   protected IAssetManager m_assetManager = null;
/* 44 */   protected AssetRepurposingManager m_assetRepurposingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     AssetForm form = (AssetForm)a_form;
/* 55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 56 */     long lId = getLongParameter(a_request, "id");
/* 57 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lId, null, false, false);
/* 58 */     form.setAsset(asset);
/*    */ 
/* 60 */     if (!this.m_assetManager.userCanDownloadAsset(userProfile, asset))
/*    */     {
/* 62 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 66 */     if (!asset.getCanEmbedFile())
/*    */     {
/* 68 */       this.m_assetRepurposingManager.updateAssetEmbedStatus(a_dbTransaction, lId, true);
/*    */     }
/*    */ 
/* 71 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAssetManager(IAssetManager a_assetManager)
/*    */   {
/* 76 */     this.m_assetManager = a_assetManager;
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager a_repurposingManager)
/*    */   {
/* 81 */     this.m_assetRepurposingManager = a_repurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ViewEmbeddableFileAction
 * JD-Core Version:    0.6.0
 */