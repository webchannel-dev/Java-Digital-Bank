/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.image.constant.ImageConstants;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class RemoveFromAssetBoxAction extends AssetInAssetBoxAction
/*    */   implements ImageConstants, AssetBankConstants
/*    */ {
/*    */   protected ActionForward performAssetBoxOperation(HttpServletRequest a_request, DBTransaction a_dbTransaction, ABUserProfile userProfile, ActionMapping a_mapping, ActionForm a_form)
/*    */     throws Bn2Exception, AssetBoxNotFoundException
/*    */   {
/* 49 */     long lAssetId = getLongParameter(a_request, "id");
/* 50 */     long lUserId = userProfile.getUser() != null ? userProfile.getUser().getId() : 0L;
/* 51 */     getAssetBoxManager().removeAsset(a_dbTransaction, userProfile.getAssetBox(), lAssetId, lUserId);
/*    */ 
/* 54 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.RemoveFromAssetBoxAction
 * JD-Core Version:    0.6.0
 */