/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.service.RemoteStoreManager;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewRemoteStorageUploadProgress extends Bn2Action
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/*    */   private RemoteStoreManager m_remoteStoreManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 37 */     long taskId = getLongParameter(a_request, "queuedTaskId");
/* 38 */     a_request.setAttribute("messages", this.m_remoteStoreManager.getMessages(taskId));
/* 39 */     a_request.setAttribute("inProgress", Boolean.valueOf(this.m_remoteStoreManager.isBatchInProgress(taskId)));
/* 40 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setRemoteStoreManager(RemoteStoreManager a_remoteStoreManager)
/*    */   {
/* 45 */     this.m_remoteStoreManager = a_remoteStoreManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewRemoteStorageUploadProgress
 * JD-Core Version:    0.6.0
 */