/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.VideoAsset;
/*    */ import com.bright.assetbank.application.form.DownloadForm;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewDownloadVideoAssetAction extends ViewDownloadAssetAction
/*    */ {
/*    */   protected long getAssetTypeId()
/*    */   {
/* 42 */     return 3L;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 64 */     ActionForward afForward = null;
/* 65 */     DownloadForm form = (DownloadForm)a_form;
/* 66 */     VideoAsset asset = null;
/*    */ 
/* 69 */     afForward = super.execute(a_mapping, form, a_request, a_response, a_dbTransaction);
/*    */ 
/* 71 */     if (!form.getUserDoesNotHavePermission())
/*    */     {
/* 74 */       if (a_request.getParameter("repurposeAsset") != null)
/*    */       {
/* 76 */         form.setRepurpose(Boolean.parseBoolean(a_request.getParameter("repurposeAsset")));
/*    */       }
/*    */ 
/* 80 */       if ((form.getAsset() != null) && ((form.getAsset() instanceof VideoAsset)))
/*    */       {
/* 82 */         asset = (VideoAsset)form.getAsset();
/*    */ 
/* 85 */         if ((form.getHeight() <= 0) || (form.getWidth() <= 0))
/*    */         {
/* 88 */           form.setHeight(asset.getDisplayHeight());
/* 89 */           form.setWidth(asset.getWidth());
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 94 */         this.m_logger.error("ViewDownloadVideoAssetAction.execute() : The asset in the form is not a valid VideoAsset");
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 99 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewDownloadVideoAssetAction
 * JD-Core Version:    0.6.0
 */