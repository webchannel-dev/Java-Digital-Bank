/*    */ package com.bright.assetbank.priceband.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.priceband.form.PriceBandForm;
/*    */ import com.bright.assetbank.priceband.service.PriceBandManager;
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
/*    */ public class ViewPriceBandAdminAction extends BTransactionAction
/*    */ {
/* 45 */   private PriceBandManager m_pbManager = null;
/*    */ 
/*    */   public void setPriceBandManager(PriceBandManager a_pbManager) {
/* 48 */     this.m_pbManager = a_pbManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 71 */     ActionForward afForward = null;
/* 72 */     PriceBandForm form = (PriceBandForm)a_form;
/* 73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 76 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 78 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 79 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 83 */     Vector vecPriceBandList = this.m_pbManager.getPriceBandList(a_dbTransaction);
/* 84 */     form.setPriceBandList(vecPriceBandList);
/*    */ 
/* 86 */     afForward = a_mapping.findForward("Success");
/* 87 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.action.ViewPriceBandAdminAction
 * JD-Core Version:    0.6.0
 */