/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.application.form.AssetForm;
/*    */ import com.bright.assetbank.application.service.AssetManager;
/*    */ import com.bright.assetbank.entity.relationship.util.RelationshipUtil;
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
/*    */ public class ViewCopyAssetAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 46 */   private AssetManager m_assetManager = null;
/*    */ 
/*    */   public void setAssetManager(AssetManager a_assetManager) {
/* 49 */     this.m_assetManager = a_assetManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 59 */     ActionForward afForward = null;
/* 60 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 61 */     AssetForm form = (AssetForm)a_form;
/*    */ 
/* 63 */     long lAssetId = getLongParameter(a_request, "assetId");
/* 64 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, true, false);
/*    */ 
/* 67 */     if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanUpdateAsset(userProfile, asset)))
/*    */     {
/* 69 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 73 */     if ((AssetBankSettings.getGetRelatedAssets()) && (asset.getNoOfChildAssets() > 0) && (asset.getNoOfChildAssets() <= AssetBankSettings.getCopyAssetChildChoiceLimit()))
/*    */     {
/* 78 */       RelationshipUtil.populateRelatedAssets(a_dbTransaction, userProfile, asset, form);
/* 79 */       form.setAsset(asset);
/*    */ 
/* 83 */       afForward = a_mapping.findForward("Success");
/*    */     }
/*    */     else
/*    */     {
/* 88 */       String sQueryString = "assetId=" + asset.getId();
/* 89 */       afForward = createRedirectingForward(sQueryString, a_mapping, "CopyAsset");
/*    */     }
/*    */ 
/* 93 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewCopyAssetAction
 * JD-Core Version:    0.6.0
 */