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
/*    */ public class  ViewAllCheckedOutAssetsAction extends BTransactionAction
/*    */ {
/* 32 */   private CheckoutManager m_checkoutManager = null;
/*    */ 
/*    */   public void setCheckoutManager(CheckoutManager a_checkoutManager)
/*    */   {
/* 36 */     this.m_checkoutManager = a_checkoutManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 48 */     ActionForward afForward = null;
/* 49 */     CheckedOutAssetsForm form = (CheckedOutAssetsForm)a_form;
/* 50 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 53 */     if (!userProfile.getIsLoggedIn())
/*    */     {
/* 55 */       this.m_logger.debug("ViewAllCheckedOutAssetsAction: not logged in");
/* 56 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 60 */     List assets = this.m_checkoutManager.getAllCheckedOutAssets(null);
/*    */ 
/* 67 */     Collections.sort(assets, new Comparator()
/*    */     {
/*    */       //public int compare(Asset right, Asset left)
                public int compare(Object right, Object left)
/*    */       {
/* 64 */         return ((Asset)left).getDateCheckedOut().compareTo(((Asset)right).getDateCheckedOut());
/*    */       }
/*    */     });
/* 70 */     form.setCheckedOutAssets(assets);
/*    */ 
/* 72 */     afForward = a_mapping.findForward("Success");
/* 73 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.checkout.action.ViewAllCheckedOutAssetsAction
 * JD-Core Version:    0.6.0
 */