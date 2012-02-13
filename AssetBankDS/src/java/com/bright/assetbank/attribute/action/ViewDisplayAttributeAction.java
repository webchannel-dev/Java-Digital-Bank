/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.bean.DisplayAttribute;
/*    */ import com.bright.assetbank.attribute.form.DisplayAttributeForm;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewDisplayAttributeAction extends DisplayAttributeAction
/*    */ {
/*    */   public ActionForward performAction(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 58 */     ActionForward afForward = null;
/* 59 */     DisplayAttributeForm form = (DisplayAttributeForm)a_form;
/*    */ 
/* 61 */     if (!form.getHasErrors())
/*    */     {
/* 63 */       long lId = getAttributeId(a_request);
/* 64 */       long lGroupId = getGroupId(a_request);
/* 65 */       DisplayAttribute dispAttribute = getAttributeManager().getDisplayAttribute(a_dbTransaction, lId, lGroupId);
/* 66 */       form.setDisplayAttribute(dispAttribute);
/*    */     }
/*    */ 
/* 69 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 71 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewDisplayAttributeAction
 * JD-Core Version:    0.6.0
 */