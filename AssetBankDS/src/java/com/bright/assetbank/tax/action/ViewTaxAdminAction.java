/*    */ package com.bright.assetbank.tax.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.tax.form.TaxValueForm;
/*    */ import com.bright.assetbank.tax.service.TaxManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewTaxAdminAction extends BTransactionAction
/*    */ {
/* 45 */   private TaxManager m_taxManager = null;
/*    */ 
/*    */   public void setTaxManager(TaxManager a_taxManager) {
/* 48 */     this.m_taxManager = a_taxManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 68 */     ActionForward afForward = null;
/* 69 */     TaxValueForm form = (TaxValueForm)a_form;
/* 70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 73 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 75 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 76 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 80 */     Vector vecTaxList = this.m_taxManager.getTaxValueList(a_dbTransaction);
/* 81 */     form.setTaxValueList(vecTaxList);
/*    */ 
/* 83 */     afForward = a_mapping.findForward("Success");
/* 84 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.tax.action.ViewTaxAdminAction
 * JD-Core Version:    0.6.0
 */