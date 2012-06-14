/*    */ package com.bright.assetbank.synchronise.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.synchronise.bean.DeleteResult;
/*    */ import com.bright.assetbank.synchronise.form.DeleteExportedStatusForm;
/*    */ import com.bright.assetbank.synchronise.service.AssetExportManager;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewDeleteExportedAssetsStatusAction extends Bn2Action
/*    */ {
/* 41 */   private AssetExportManager m_exportManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 65 */     ActionForward forward = null;
/*    */ 
/* 68 */     String sFileLocation = a_request.getParameter("filename");
/* 69 */     if (sFileLocation == null)
/*    */     {
/* 71 */       if (a_request.getAttribute("filename") != null)
/*    */       {
/* 73 */         sFileLocation = (String)a_request.getAttribute("filename");
/*    */       }
/*    */     }
/*    */ 
/* 77 */     DeleteResult result = null;
/* 78 */     result = this.m_exportManager.getDeletionResult(sFileLocation);
/* 79 */     DeleteExportedStatusForm form = (DeleteExportedStatusForm)a_form;
/* 80 */     if (result != null)
/*    */     {
/* 82 */       forward = createRedirectingForward("successful=" + result.getNumDeleted() + "&" + "errors" + "=" + result.getNumErrors(), a_mapping, "Success");
/*    */     }
/*    */     else
/*    */     {
/* 87 */       form.setFileLocation(sFileLocation);
/* 88 */       forward = a_mapping.findForward("Failure");
/*    */     }
/* 90 */     return forward;
/*    */   }
/*    */ 
/*    */   public void setExportManager(AssetExportManager a_sExportManager)
/*    */   {
/* 96 */     this.m_exportManager = a_sExportManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.ViewDeleteExportedAssetsStatusAction
 * JD-Core Version:    0.6.0
 */