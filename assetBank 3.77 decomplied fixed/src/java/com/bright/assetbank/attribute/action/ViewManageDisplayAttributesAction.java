/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.form.DisplayAttributeForm;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewManageDisplayAttributesAction extends DisplayAttributeAction
/*    */ {
/*    */   public ActionForward performAction(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 56 */     long lGroupId = getGroupId(a_request);
/*    */ 
/* 58 */     DisplayAttributeForm form = (DisplayAttributeForm)a_form;
/*    */ 
/* 61 */     form.setDisplayAttributeGroups(getAttributeManager().getDisplayAtttributeGroups(a_dbTransaction));
/*    */ 
/* 64 */     form.setAttributes(getAttributeManager().getNonDisplayAttributes(a_dbTransaction, lGroupId));
/* 65 */     form.setDisplayAttributes(getAttributeManager().getDisplayAttributes(a_dbTransaction, lGroupId));
/* 66 */     form.setNameAttributes(getAttributeManager().getAttributes(a_dbTransaction, -1L));
/* 67 */     form.setDescriptionAttribute(getAttributeManager().getDescriptionAttributeId(a_dbTransaction));
/*    */ 
/* 69 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewManageDisplayAttributesAction
 * JD-Core Version:    0.6.0
 */