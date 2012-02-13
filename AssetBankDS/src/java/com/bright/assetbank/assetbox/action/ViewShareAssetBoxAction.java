/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*    */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*    */ import com.bright.assetbank.assetbox.form.ShareAssetBoxForm;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewShareAssetBoxAction extends BTransactionAction
/*    */   implements AssetBoxConstants, FrameworkConstants
/*    */ {
/* 46 */   private AssetBoxManager m_assetBoxManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 55 */     ActionForward afForward = null;
/* 56 */     ShareAssetBoxForm form = (ShareAssetBoxForm)a_form;
/* 57 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 59 */     if (!AssetBankSettings.isSharedLightboxesEnabled())
/*    */     {
/* 61 */       throw new Bn2Exception("ViewShareAssetBoxAction : Lightbox sharing is not enabled in the application settings");
/*    */     }
/*    */ 
/* 65 */     long lId = getLongParameter(a_request, "assetBoxId");
/*    */ 
/* 67 */     this.m_assetBoxManager.refreshAssetBoxInProfile(a_dbTransaction, userProfile, lId);
/*    */ 
/* 70 */     List users = this.m_assetBoxManager.getSharedAssetBoxUsers(a_dbTransaction, lId, userProfile.getUser().getId());
/*    */ 
/* 72 */     form.setAssetBoxId(lId);
/* 73 */     form.setUsers(users);
/* 74 */     form.setName(userProfile.getAssetBox().getName());
/*    */ 
/* 76 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 78 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*    */   {
/* 84 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ViewShareAssetBoxAction
 * JD-Core Version:    0.6.0
 */