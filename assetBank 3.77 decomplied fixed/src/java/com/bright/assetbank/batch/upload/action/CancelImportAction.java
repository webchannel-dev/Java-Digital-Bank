/*    */ package com.bright.assetbank.batch.upload.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class CancelImportAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 50 */     ActionForward afForward = null;
/*    */ 
/* 58 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 60 */     String sCurrentUser = userProfile.getUser().getUsername();
/*    */     String sFullFilePath;
/* 63 */     if (FrameworkSettings.getUseRelativeDirectories())
/*    */     {
/* 65 */       sFullFilePath = AssetBankSettings.getApplicationPath() + "/";
/*    */     }
/*    */     else
/*    */     {
/* 69 */       sFullFilePath = "";
/*    */     }
/* 71 */     sFullFilePath = sFullFilePath + AssetBankSettings.getBulkUploadDirectory();
/*    */ 
/* 73 */     sFullFilePath = sFullFilePath + "/" + sCurrentUser + "/";
/*    */ 
/* 75 */     FileUtil.deleteDir(sFullFilePath);
/*    */ 
/* 77 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 79 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.action.CancelImportAction
 * JD-Core Version:    0.6.0
 */