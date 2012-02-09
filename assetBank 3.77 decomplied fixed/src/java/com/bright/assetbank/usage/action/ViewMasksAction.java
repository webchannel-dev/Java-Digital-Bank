/*    */ package com.bright.assetbank.usage.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.usage.service.MaskManager;
/*    */ import com.bright.assetbank.usage.service.NamedColourManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewMasksAction extends BTransactionAction
/*    */ {
/*    */   private NamedColourManager m_namedColourManager;
/*    */   private MaskManager m_maskManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 53 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/* 54 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 56 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 59 */     List masks = getMaskManager().getMasks(a_transaction);
/* 60 */     a_request.setAttribute("masks", masks);
/*    */ 
/* 62 */     List colours = getNamedColourManager().getColours(a_transaction);
/* 63 */     a_request.setAttribute("colours", colours);
/*    */ 
/* 65 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public NamedColourManager getNamedColourManager()
/*    */   {
/* 70 */     return this.m_namedColourManager;
/*    */   }
/*    */ 
/*    */   public void setNamedColourManager(NamedColourManager a_namedColourManager)
/*    */   {
/* 75 */     this.m_namedColourManager = a_namedColourManager;
/*    */   }
/*    */ 
/*    */   public MaskManager getMaskManager()
/*    */   {
/* 80 */     return this.m_maskManager;
/*    */   }
/*    */ 
/*    */   public void setMaskManager(MaskManager a_maskManager)
/*    */   {
/* 85 */     this.m_maskManager = a_maskManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.action.ViewMasksAction
 * JD-Core Version:    0.6.0
 */