/*    */ package com.bright.assetbank.commercialoption.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.commercialoption.form.CommercialOptionForm;
/*    */ import com.bright.assetbank.commercialoption.service.CommercialOptionManager;
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
/*    */ public class ViewCommercialOptionAdminAction extends BTransactionAction
/*    */ {
/* 46 */   private CommercialOptionManager m_coManager = null;
/*    */ 
/*    */   public void setCommercialOptionManager(CommercialOptionManager a_coManager)
/*    */   {
/* 50 */     this.m_coManager = a_coManager;
/*    */   }
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 80 */     ActionForward afForward = null;
/* 81 */     CommercialOptionForm form = (CommercialOptionForm)a_form;
/* 82 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 85 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 87 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/* 88 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 92 */     Vector vecCommercialOptionList = this.m_coManager.getCommercialOptionList(a_dbTransaction);
/* 93 */     form.setCommercialOptionList(vecCommercialOptionList);
/*    */ 
/* 95 */     afForward = a_mapping.findForward("Success");
/* 96 */     return afForward;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.commercialoption.action.ViewCommercialOptionAdminAction
 * JD-Core Version:    0.6.0
 */