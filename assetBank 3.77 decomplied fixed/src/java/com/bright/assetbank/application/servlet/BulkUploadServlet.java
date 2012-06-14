/*    */ package com.bright.assetbank.application.servlet;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.application.util.UploadUtil;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class BulkUploadServlet extends FileUploadServlet
/*    */ {
/*    */   public String getUploadDirectory(HttpServletRequest a_request)
/*    */   {
/* 41 */     return UploadUtil.getUploadDirectory(a_request.getSession(), Boolean.parseBoolean(a_request.getParameter("single")), Boolean.parseBoolean(a_request.getParameter("importFilesToExistingAssets")));
/*    */   }
/*    */ 
/*    */   protected boolean checkPermissions(HttpServletRequest a_request)
/*    */   {
/* 51 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 56 */     return (userProfile.getCanUpdateAssets()) || (userProfile.getCanUploadWithApproval());
/*    */   }
/*    */ 
/*    */   protected long getMaxFileSize()
/*    */   {
/* 64 */     return AssetBankSettings.getUploadAppletMaxFilesize();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.servlet.BulkUploadServlet
 * JD-Core Version:    0.6.0
 */