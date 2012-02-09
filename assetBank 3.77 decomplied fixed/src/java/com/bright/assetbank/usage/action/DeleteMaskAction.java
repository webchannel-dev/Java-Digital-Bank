/*    */ package com.bright.assetbank.usage.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.exception.CannotDeleteReferredToRowException;
/*    */ import com.bright.assetbank.usage.form.MaskForm;
/*    */ import com.bright.assetbank.usage.service.MaskManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteMaskAction extends BTransactionAction
/*    */ {
/*    */   private MaskManager m_maskManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 49 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/* 50 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 52 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 55 */     MaskForm form = (MaskForm)a_form;
/*    */ 
/* 57 */     long lMaskId = getLongParameter(a_request, "maskId");
/*    */     try
/*    */     {
/* 61 */       getMaskManager().deleteMask(a_transaction, lMaskId);
/*    */     }
/*    */     catch (CannotDeleteReferredToRowException e)
/*    */     {
/* 65 */       form.addError("This mask cannot be deleted because it is in use (for example by a usage type size)");
/* 66 */       return a_mapping.findForward("Failure");
/*    */     }
/*    */ 
/* 69 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public MaskManager getMaskManager()
/*    */   {
/* 74 */     return this.m_maskManager;
/*    */   }
/*    */ 
/*    */   public void setMaskManager(MaskManager a_maskManager)
/*    */   {
/* 79 */     this.m_maskManager = a_maskManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.DeleteMaskAction
 * JD-Core Version:    0.6.0
 */