/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class AdjustAttributeVisibilityAction extends BTransactionAction
/*    */ {
/* 44 */   private AttributeManager m_attributeManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     ActionForward afForward = null;
/* 55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 58 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 60 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 64 */     long lAttributeId = getLongParameter(a_request, "id");
/*    */ 
/* 67 */     String sHide = a_request.getParameter("hide");
/* 68 */     boolean bHide = false;
/* 69 */     if (StringUtil.stringIsPopulated(sHide))
/*    */     {
/* 71 */       bHide = Boolean.parseBoolean(sHide);
/*    */     }
/*    */ 
/* 75 */     if (bHide)
/*    */     {
/* 77 */       this.m_attributeManager.hideAttribute(a_transaction, lAttributeId);
/* 78 */       afForward = a_mapping.findForward("Success");
/*    */     }
/*    */     else
/*    */     {
/* 82 */       this.m_attributeManager.showAttribute(a_transaction, lAttributeId);
/* 83 */       afForward = a_mapping.findForward("Showing");
/*    */     }
/*    */ 
/* 87 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAttributeManager(AttributeManager a_attributeManager)
/*    */   {
/* 92 */     this.m_attributeManager = a_attributeManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.AdjustAttributeVisibilityAction
 * JD-Core Version:    0.6.0
 */