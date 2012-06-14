/*    */ package com.bright.assetbank.tax.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
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
/*    */ public class DeleteTaxValueAction extends BTransactionAction
/*    */ {
/* 43 */   private TaxManager m_taxManager = null;
/*    */ 
/*    */   public void setTaxManager(TaxManager a_taxManager) {
/* 46 */     this.m_taxManager = a_taxManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 70 */     ActionForward afForward = null;
/* 71 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 74 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 76 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 77 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 81 */     long lTaxTypeId = getLongParameter(a_request, "taxtypeid");
/* 82 */     long lTaxRegionId = getLongParameter(a_request, "taxregionid");
/*    */ 
/* 84 */     this.m_taxManager.deleteTaxValue(a_dbTransaction, lTaxTypeId, lTaxRegionId);
/*    */ 
/* 87 */     String sQueryString = "";
/* 88 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*    */ 
/* 90 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.tax.action.DeleteTaxValueAction
 * JD-Core Version:    0.6.0
 */