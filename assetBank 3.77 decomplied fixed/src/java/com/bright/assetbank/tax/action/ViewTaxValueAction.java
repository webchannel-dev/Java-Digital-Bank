/*    */ package com.bright.assetbank.tax.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.tax.bean.TaxValue;
/*    */ import com.bright.assetbank.tax.form.TaxValueForm;
/*    */ import com.bright.assetbank.tax.service.TaxManager;
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
/*    */ public class ViewTaxValueAction extends BTransactionAction
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
/* 72 */     ActionForward afForward = null;
/* 73 */     TaxValueForm form = (TaxValueForm)a_form;
/* 74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 77 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 79 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 80 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 84 */     long lTaxTypeId = getLongParameter(a_request, "taxtypeid");
/* 85 */     long lTaxRegionId = getLongParameter(a_request, "taxregionid");
/*    */ 
/* 87 */     if (!form.getHasErrors())
/*    */     {
/* 89 */       TaxValue tv = this.m_taxManager.getTaxValue(a_dbTransaction, lTaxTypeId, lTaxRegionId);
/* 90 */       form.setTaxValue(tv);
/*    */     }
/*    */ 
/* 97 */     afForward = a_mapping.findForward("Success");
/* 98 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.tax.action.ViewTaxValueAction
 * JD-Core Version:    0.6.0
 */