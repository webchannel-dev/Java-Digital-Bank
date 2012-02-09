/*    */ package com.bright.assetbank.restapi.resources;
/*    */ 
/*    */ import com.bright.assetbank.api.constant.ApiSettings;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.ws.rs.core.Response;
/*    */ 
/*    */ public class BaseResource
/*    */ {
/*    */   protected ABUserProfile checkPermissions(HttpServletRequest a_request)
/*    */   {
/* 34 */     String[] allowedIps = ApiSettings.getAllowedIpAddresses();
/* 35 */     String sRequestIp = a_request.getRemoteAddr();
/*    */ 
/* 37 */     for (int i = 0; i < allowedIps.length; i++)
/*    */     {
/* 39 */       if ((StringUtil.stringIsPopulated(allowedIps[i])) && (allowedIps[i].equals(sRequestIp)))
/*    */       {
/* 41 */         return null;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 46 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 48 */     if (!userProfile.getIsLoggedIn()) {
/* 49 */       throw new ResourceException(Response.Status.FORBIDDEN);
/*    */     }
/*    */ 
/* 52 */     return userProfile;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.resources.BaseResource
 * JD-Core Version:    0.6.0
 */