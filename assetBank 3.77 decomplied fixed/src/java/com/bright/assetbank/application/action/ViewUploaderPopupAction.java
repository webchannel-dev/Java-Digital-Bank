/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bright.assetbank.application.form.AssetForm;
/*    */ import com.bright.assetbank.application.util.UploadUtil;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewUploaderPopupAction extends Bn2Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */   {
/* 53 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 54 */     AssetForm form = (AssetForm)a_form;
/*    */ 
/* 56 */     if (a_request.getParameter("fileChosen") != null)
/*    */     {
/* 58 */       File uploadedFile = UploadUtil.getUploadedFile(userProfile, a_request.getSession());
/*    */ 
/* 60 */       if (uploadedFile != null)
/*    */       {
/* 62 */         a_request.setAttribute("chosenFile", uploadedFile.getName());
/*    */       }
/*    */ 
/*    */     }
/*    */     else
/*    */     {
/*    */       try
/*    */       {
/* 70 */         File dir = new File(userProfile.getSingleUploadDir() + "/" + a_request.getSession().getId());
/* 71 */         if (dir.exists())
/*    */         {
/* 73 */           FileUtils.cleanDirectory(dir);
/*    */         }
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 78 */         this.m_logger.error("ViewUploadAssetFileAction.execute() : Could not clear single upload directory " + userProfile.getSingleUploadDir() + "/" + a_request.getSession().getId(), e);
/*    */       }
/*    */     }
/*    */ 
/* 82 */     if (!form.getHasErrors())
/*    */     {
/* 84 */       UploadUtil.setUploadToolOption(a_request, form);
/*    */     }
/*    */ 
/* 87 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewUploaderPopupAction
 * JD-Core Version:    0.6.0
 */