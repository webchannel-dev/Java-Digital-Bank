/*    */ package com.bright.assetbank.attribute.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
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
/*    */ public class ViewSortAttributeAction extends BTransactionAction
/*    */ {
/* 44 */   private AttributeManager m_attributeManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 73 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 75 */       this.m_logger.error("This user does not have permission to view the admin pages");
/* 76 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 79 */     long lId = getLongParameter(a_request, "id");
/* 80 */     long lSortAreaId = getLongParameter(a_request, "sortArea");
/* 81 */     SortAttributeForm form = (SortAttributeForm)a_form;
/* 82 */     form.setSortAttribute(this.m_attributeManager.getSortAttribute(a_dbTransaction, lId, lSortAreaId));
/*    */ 
/* 84 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAttributeManager(AttributeManager a_attributeManager)
/*    */   {
/* 89 */     this.m_attributeManager = a_attributeManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewSortAttributeAction
 * JD-Core Version:    0.6.0
 */