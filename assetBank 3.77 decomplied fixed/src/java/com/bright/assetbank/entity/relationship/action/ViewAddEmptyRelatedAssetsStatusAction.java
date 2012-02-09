/*    */ package com.bright.assetbank.entity.relationship.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.batch.upload.form.ImportForm;
/*    */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*    */ import com.bright.assetbank.entity.relationship.util.RelationshipUtil;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAddEmptyRelatedAssetsStatusAction extends AssetRelationshipsUpdateAction
/*    */ {
/*    */   protected ActionForward performAction(Asset a_asset, ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 55 */     long lChildBatchId = RelationshipUtil.getEmptyRelatedAssetsBatchId(userProfile, a_asset, 2);
/* 56 */     long lPeerBatchId = RelationshipUtil.getEmptyRelatedAssetsBatchId(userProfile, a_asset, 1);
/*    */ 
/* 58 */     ImportForm importForm = (ImportForm)a_form;
/* 59 */     importForm.setAsset(a_asset);
/*    */ 
/* 61 */     Vector vecMessages = getAssetRelationshipManager().getMessages(lChildBatchId);
/* 62 */     Vector vecPeerMessages = getAssetRelationshipManager().getMessages(lPeerBatchId);
/* 63 */     if ((vecMessages != null) && (vecPeerMessages != null))
/*    */     {
/* 65 */       vecMessages.addAll(vecPeerMessages);
/*    */     }
/*    */     else
/*    */     {
/* 69 */       vecMessages = vecPeerMessages;
/*    */     }
/* 71 */     importForm.setMessages(vecMessages);
/*    */ 
/* 74 */     if ((getAssetRelationshipManager().isBatchInProgress(lChildBatchId)) || (getAssetRelationshipManager().isBatchInProgress(lPeerBatchId)))
/*    */     {
/* 79 */       importForm.setIsImportInProgress(true);
/*    */     }
/*    */     else
/*    */     {
/* 84 */       importForm.setIsImportInProgress(false);
/*    */     }
/*    */ 
/* 87 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.ViewAddEmptyRelatedAssetsStatusAction
 * JD-Core Version:    0.6.0
 */