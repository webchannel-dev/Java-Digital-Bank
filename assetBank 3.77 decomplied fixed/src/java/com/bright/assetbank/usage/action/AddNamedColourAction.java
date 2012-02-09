/*     */ package com.bright.assetbank.usage.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.usage.bean.NamedColour;
/*     */ import com.bright.assetbank.usage.form.NamedColourForm;
/*     */ import com.bright.assetbank.usage.service.NamedColourManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.struts.ValidationException;
/*     */ import com.bright.framework.struts.ValidationUtil;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddNamedColourAction extends BTransactionAction
/*     */ {
/*     */   private ListManager m_listManager;
/*     */   private NamedColourManager m_namedColourManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  50 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  52 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  54 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  57 */     NamedColourForm form = (NamedColourForm)a_form;
/*     */ 
/*  60 */     validateMandatoryFields(form, a_request);
/*  61 */     if (form.getHasErrors())
/*     */     {
/*  63 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*     */     NamedColour colour;
/*     */     try
/*     */     {
/*  70 */       colour = form.toModel();
/*     */     }
/*     */     catch (ValidationException e)
/*     */     {
/*  74 */       form.addError(ValidationUtil.displayStringFromException(a_transaction, getListManager(), userProfile.getCurrentLanguage(), e));
/*     */ 
/*  79 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  83 */     getNamedColourManager().addColour(a_transaction, colour);
/*     */ 
/*  85 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public ListManager getListManager()
/*     */   {
/*  90 */     return this.m_listManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/*  96 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ 
/*     */   public NamedColourManager getNamedColourManager()
/*     */   {
/* 101 */     return this.m_namedColourManager;
/*     */   }
/*     */ 
/*     */   public void setNamedColourManager(NamedColourManager a_namedColourManager)
/*     */   {
/* 107 */     this.m_namedColourManager = a_namedColourManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.AddNamedColourAction
 * JD-Core Version:    0.6.0
 */