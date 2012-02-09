/*    */ package com.bright.assetbank.usage.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.usage.bean.NamedColour;
/*    */ import com.bright.assetbank.usage.form.NamedColourForm;
/*    */ import com.bright.assetbank.usage.service.NamedColourManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewEditNamedColourAction extends BTransactionAction
/*    */ {
/*    */   private NamedColourManager m_namedColourManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 49 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 51 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 53 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 57 */     long namedColourId = getLongParameter(a_request, "namedColourId");
/* 58 */     NamedColour colour = getNamedColourManager().getColourById(a_transaction, namedColourId);
/*    */ 
/* 61 */     NamedColourForm form = (NamedColourForm)a_form;
/* 62 */     form.fromModel(colour);
/*    */ 
/* 64 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public NamedColourManager getNamedColourManager()
/*    */   {
/* 69 */     return this.m_namedColourManager;
/*    */   }
/*    */ 
/*    */   public void setNamedColourManager(NamedColourManager a_namedColourManager)
/*    */   {
/* 75 */     this.m_namedColourManager = a_namedColourManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewEditNamedColourAction
 * JD-Core Version:    0.6.0
 */