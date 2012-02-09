/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.application.form.EmailForm;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.service.FileStoreManager;
/*    */ import java.io.File;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewEmailAssetAction extends Bn2Action
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/*    */   private FileStoreManager m_fileStoreManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 58 */     ActionForward afForward = a_mapping.findForward("Success");
/* 59 */     EmailForm form = (EmailForm)a_form;
/*    */ 
/* 61 */     String sFileId = (String)a_request.getAttribute("downloadFile");
/* 62 */     long lMaxAttachmentSize = 1048576 * AssetBankSettings.getEmailAttachmentMaxSizeInMb();
/* 63 */     long lFileLength = new File(this.m_fileStoreManager.getAbsolutePath(sFileId)).length();
/*    */ 
/* 66 */     form.setFileId(sFileId);
/* 67 */     form.setAssetIsAttachable(lFileLength <= lMaxAttachmentSize);
/*    */ 
/* 69 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*    */   {
/* 74 */     this.m_fileStoreManager = a_fileStoreManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewEmailAssetAction
 * JD-Core Version:    0.6.0
 */