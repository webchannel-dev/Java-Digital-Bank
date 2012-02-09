/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*    */ import com.bright.assetbank.attribute.form.SortAttributeForm;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewManageSortAttributesAction extends BTransactionAction
/*    */   implements AttributeConstants
/*    */ {
/* 47 */   private AttributeManager m_attributeManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 75 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 78 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 80 */       this.m_logger.error("This user does not have permission to view the admin pages");
/* 81 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 84 */     long lSortArea = 1L;
/* 85 */     if (getIntParameter(a_request, "browse") > 0)
/*    */     {
/* 87 */       lSortArea = 2L;
/*    */     }
/*    */ 
/* 90 */     SortAttributeForm form = (SortAttributeForm)a_form;
/* 91 */     form.setAttributes(this.m_attributeManager.getUnsortedAttributes(a_dbTransaction, lSortArea));
/* 92 */     form.setSortAttributes(this.m_attributeManager.getSortAttributeList(a_dbTransaction, lSortArea));
/*    */ 
/* 94 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAttributeManager(AttributeManager a_attributeManager)
/*    */   {
/* 99 */     this.m_attributeManager = a_attributeManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewManageSortAttributesAction
 * JD-Core Version:    0.6.0
 */