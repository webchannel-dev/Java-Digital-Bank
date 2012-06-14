/*    */ package com.bright.framework.image.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.image.util.ImageMagick;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ClearImageMagickCacheAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Exception
/*    */   {
/* 42 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 45 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 47 */       this.m_logger.debug("This user does not have permission to clear the image magick cache");
/* 48 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 51 */     ImageMagick.clearCaches();
/*    */ 
/* 53 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.action.ClearImageMagickCacheAction
 * JD-Core Version:    0.6.0
 */