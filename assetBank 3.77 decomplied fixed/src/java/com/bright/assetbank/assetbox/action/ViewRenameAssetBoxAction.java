/*    */ package com.bright.assetbank.assetbox.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*    */ import com.bright.framework.image.constant.ImageConstants;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewRenameAssetBoxAction extends Bn2Action
/*    */   implements ImageConstants, AssetBankConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 47 */     AssetBoxForm form = (AssetBoxForm)a_form;
/*    */ 
/* 49 */     long lId = getLongParameter(a_request, "id");
/* 50 */     String sName = a_request.getParameter("name");
/* 51 */     boolean isShared = Boolean.parseBoolean(a_request.getParameter("shared"));
/*    */ 
/* 53 */     form.setCurrentAssetBoxId(lId);
/* 54 */     form.setName(sName);
/* 55 */     form.setPreviousName(sName);
/* 56 */     form.setShared(isShared);
/*    */ 
/* 58 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ViewRenameAssetBoxAction
 * JD-Core Version:    0.6.0
 */