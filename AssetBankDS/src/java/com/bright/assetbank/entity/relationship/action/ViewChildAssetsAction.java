/*    */ package com.bright.assetbank.entity.relationship.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.form.DownloadForm;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
/*    */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewChildAssetsAction extends BTransactionAction
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/* 89 */   protected IAssetManager m_assetManager = null;
/*    */ 
/* 95 */   protected AssetRelationshipManager m_assetRelationshipManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 60 */     ActionForward afForward = null;
/* 61 */     DownloadForm form = (DownloadForm)a_form;
/* 62 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 64 */     long lAssetId = getLongParameter(a_request, "id");
/*    */ 
/* 67 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, userProfile.getVisibleAttributeIds(), true, userProfile.getCurrentLanguage(), false);
/*    */ 
/* 70 */     Vector vChildAssetIds = this.m_assetRelationshipManager.getRelatedAssetIds(a_dbTransaction, asset.getId(), 2L);
/* 71 */     Vector vChildren = new Vector();
/*    */ 
/* 73 */     for (Iterator i$ = vChildAssetIds.iterator(); i$.hasNext(); ) { long lId = ((Long)i$.next()).longValue();
/*    */ 
/* 75 */       Asset childAsset = this.m_assetManager.getAsset(a_dbTransaction, lId, userProfile.getVisibleAttributeIds(), true, userProfile.getCurrentLanguage(), false);
/* 76 */       if (this.m_assetManager.userCanDownloadOriginal(userProfile, childAsset))
/*    */       {
/* 78 */         vChildren.add(childAsset);
/*    */       }
/*    */     }
/*    */ 
/* 82 */     form.setChildAssets(vChildren);
/* 83 */     form.setAsset(asset);
/*    */ 
/* 85 */     afForward = a_mapping.findForward("Success");
/* 86 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAssetManager(IAssetManager a_assetManager)
/*    */   {
/* 92 */     this.m_assetManager = a_assetManager;
/*    */   }
/*    */ 
/*    */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*    */   {
/* 98 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.ViewChildAssetsAction
 * JD-Core Version:    0.6.0
 */