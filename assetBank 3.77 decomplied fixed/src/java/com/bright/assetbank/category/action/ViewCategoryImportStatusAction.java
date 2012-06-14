/*    */ package com.bright.assetbank.category.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.service.AssetCategoryManager;
/*    */ import com.bright.assetbank.batch.upload.form.ImportForm;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewCategoryImportStatusAction extends Bn2Action
/*    */ {
/* 43 */   private AssetCategoryManager m_categoryManager = null;
/*    */ 
/*    */   public void setCategoryManager(AssetCategoryManager a_categoryManager)
/*    */   {
/* 47 */     this.m_categoryManager = a_categoryManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 56 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 59 */     ImportForm importForm = (ImportForm)a_form;
/*    */ 
/* 61 */     Vector vecMessages = this.m_categoryManager.getMessages(userProfile.getSessionId());
/* 62 */     importForm.setMessages(vecMessages);
/*    */ 
/* 65 */     if (this.m_categoryManager.isBatchInProgress(userProfile.getSessionId()))
/*    */     {
/* 67 */       importForm.setIsImportInProgress(true);
/*    */     }
/*    */     else
/*    */     {
/* 71 */       importForm.setIsImportInProgress(false);
/*    */     }
/*    */ 
/* 74 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.action.ViewCategoryImportStatusAction
 * JD-Core Version:    0.6.0
 */