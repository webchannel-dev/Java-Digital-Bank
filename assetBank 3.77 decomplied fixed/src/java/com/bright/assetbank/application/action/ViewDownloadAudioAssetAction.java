/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.form.DownloadForm;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewDownloadAudioAssetAction extends ViewDownloadAssetAction
/*    */ {
/*    */   protected long getAssetTypeId()
/*    */   {
/* 42 */     return 4L;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 64 */     ActionForward afForward = null;
/* 65 */     DownloadForm form = (DownloadForm)a_form;
/*    */ 
/* 68 */     afForward = super.execute(a_mapping, form, a_request, a_response, a_dbTransaction);
/*    */ 
/* 70 */     if (!form.getUserDoesNotHavePermission())
/*    */     {
/* 73 */       if (!StringUtil.stringIsPopulated(form.getAudioSampleRate()))
/*    */       {
/* 75 */         form.setAudioSampleRate("standard");
/*    */       }
/*    */ 
/* 78 */       if (a_request.getParameter("repurposeAsset") != null)
/*    */       {
/* 80 */         form.setRepurpose(Boolean.parseBoolean(a_request.getParameter("repurposeAsset")));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 85 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewDownloadAudioAssetAction
 * JD-Core Version:    0.6.0
 */