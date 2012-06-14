/*    */ package com.bright.assetbank.checkout.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.checkout.form.CheckedOutAssetsForm;
/*    */ import com.bright.assetbank.checkout.service.CheckoutManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewCheckedOutAssetsAction extends BTransactionAction
/*    */ {
/* 31 */   private CheckoutManager m_checkoutManager = null;
/*    */ 
/*    */   public void setCheckoutManager(CheckoutManager a_checkoutManager)
/*    */   {
/* 35 */     this.m_checkoutManager = a_checkoutManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 46 */     ActionForward afForward = null;
/* 47 */     CheckedOutAssetsForm form = (CheckedOutAssetsForm)a_form;
/* 48 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 51 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 53 */       this.m_logger.debug("ViewCheckedOutAssetsAction: not logged in");
/* 54 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 58 */     List assets = this.m_checkoutManager.getCheckedOutAssetsForUser(null, userProfile.getUserId());
/*    */ 
/* 65 */     Collections.sort(assets, new Comparator()
/*    */     {
/*    */       //public int compare(Asset right, Asset left)
                public int compare(Object right, Object left)
/*    */       {
/* 62 */         return ((Asset)left).getDateCheckedOut().compareTo(((Asset)right).getDateCheckedOut());
/*    */       }
/*    */     });
/* 68 */     form.setCheckedOutAssets(assets);
/*    */ 
/* 70 */     afForward = a_mapping.findForward("Success");
/* 71 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.checkout.action.ViewCheckedOutAssetsAction
 * JD-Core Version:    0.6.0
 */