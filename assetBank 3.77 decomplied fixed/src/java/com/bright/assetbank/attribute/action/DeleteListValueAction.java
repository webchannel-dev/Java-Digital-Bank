/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bright.assetbank.attribute.form.ListAttributeForm;
/*    */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteListValueAction extends Bn2Action
/*    */ {
/* 43 */   private AttributeValueManager m_attributeValueManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Exception
/*    */   {
/* 66 */     ActionForward afForward = null;
/* 67 */     ListAttributeForm form = (ListAttributeForm)a_form;
/* 68 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 71 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 73 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 74 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 77 */     long lAttributeValueId = getLongParameter(a_request, "id");
/*    */ 
/* 79 */     this.m_attributeValueManager.deleteListAttributeValue(null, lAttributeValueId);
/*    */ 
/* 81 */     form.setValue(this.m_attributeValueManager.getListAttributeValue(null, lAttributeValueId));
/*    */ 
/* 83 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 85 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*    */   {
/* 90 */     this.m_attributeValueManager = a_attributeValueManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.DeleteListValueAction
 * JD-Core Version:    0.6.0
 */