/*    */ package com.bright.assetbank.entity.relationship.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.form.AssetForm;
/*    */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.HashMap;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewDefaultRelationshipsForAssetAction extends AssetRelationshipsUpdateAction
/*    */ {
/*    */   protected ActionForward performAction(Asset a_asset, ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     AssetForm form = (AssetForm)a_form;
/* 55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 56 */     HashMap hmDefaultRelatedAssets = getAssetRelationshipManager().getDefaultRelatedAssetsForAsset(null, (ABUser)userProfile.getUser(), a_asset);
/* 57 */     ActionForward forward = null;
/* 58 */     form.setAsset(a_asset);
/*    */ 
/* 60 */     if ((hmDefaultRelatedAssets != null) && (!hmDefaultRelatedAssets.isEmpty()))
/*    */     {
/* 64 */       form.setDefaultRelatedAssets(hmDefaultRelatedAssets);
/*    */     }
/*    */     else
/*    */     {
/* 71 */       forward = (ActionForward)a_request.getAttribute("forward");
/*    */     }
/*    */ 
/* 74 */     return forward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.ViewDefaultRelationshipsForAssetAction
 * JD-Core Version:    0.6.0
 */