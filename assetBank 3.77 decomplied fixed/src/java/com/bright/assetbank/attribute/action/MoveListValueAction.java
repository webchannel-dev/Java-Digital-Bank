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
/*    */ public class MoveListValueAction extends Bn2Action
/*    */ {
/* 44 */   private AttributeValueManager m_attributeValueManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Exception
/*    */   {
/* 62 */     ActionForward afForward = null;
/* 63 */     ListAttributeForm form = (ListAttributeForm)a_form;
/* 64 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 67 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 69 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 70 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 73 */     long lAttributeValueId = getLongParameter(a_request, "id");
/* 74 */     boolean bMoveUp = new Boolean(a_request.getParameter("up")).booleanValue();
/*    */ 
/* 76 */     this.m_attributeValueManager.moveListAttributeValue(null, lAttributeValueId, bMoveUp);
/*    */ 
/* 78 */     form.setValue(this.m_attributeValueManager.getListAttributeValue(null, lAttributeValueId));
/*    */ 
/* 80 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 82 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*    */   {
/* 87 */     this.m_attributeValueManager = a_attributeValueManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.MoveListValueAction
 * JD-Core Version:    0.6.0
 */