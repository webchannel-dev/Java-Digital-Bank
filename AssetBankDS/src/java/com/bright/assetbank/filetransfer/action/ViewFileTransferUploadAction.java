/*    */ package com.bright.assetbank.filetransfer.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.util.UploadUtil;
/*    */ import com.bright.assetbank.filetransfer.form.FileTransferForm;
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
/*    */ public class ViewFileTransferUploadAction extends Bn2Action
/*    */   implements AssetBankConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 53 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 55 */       this.m_logger.debug("This user does not have permission to upload files");
/* 56 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 59 */     FileTransferForm form = (FileTransferForm)a_form;
/*    */     try
/*    */     {
/* 64 */       File dir = new File(userProfile.getSingleUploadDir() + "/" + a_request.getSession().getId());
/* 65 */       if (dir.exists())
/*    */       {
/* 67 */         FileUtils.cleanDirectory(dir);
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 72 */       this.m_logger.error("ViewUploadAssetFileAction.execute() : Could not clear single upload directory " + userProfile.getSingleUploadDir() + "/" + a_request.getSession().getId(), e);
/*    */     }
/*    */ 
/* 75 */     if (!form.getHasErrors())
/*    */     {
/* 77 */       UploadUtil.setUploadToolOption(a_request, form);
/*    */     }
/*    */ 
/* 80 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.filetransfer.action.ViewFileTransferUploadAction
 * JD-Core Version:    0.6.0
 */