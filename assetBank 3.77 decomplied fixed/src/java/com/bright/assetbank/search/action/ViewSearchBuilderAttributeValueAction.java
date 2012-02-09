/*    */ package com.bright.assetbank.search.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.service.AssetManager;
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ import com.bright.assetbank.attribute.form.AttributeForm;
/*    */ import com.bright.assetbank.search.util.SearchBuilderUtil;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.simplelist.service.ListManager;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewSearchBuilderAttributeValueAction extends BTransactionAction
/*    */ {
/* 43 */   private AssetManager m_assetManager = null;
/* 44 */   private ListManager m_listManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 53 */     AttributeForm form = (AttributeForm)a_form;
/* 54 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 56 */     long lAttributeId = getLongParameter(a_request, "id");
/*    */ 
/* 58 */     if (lAttributeId > 0L)
/*    */     {
/* 60 */       form.setAttribute(this.m_assetManager.getAssetAttribute(a_dbTransaction, lAttributeId));
/*    */     }
/*    */     else
/*    */     {
/* 64 */       form.setAttribute(SearchBuilderUtil.getPseudoSearchAttribute(a_dbTransaction, this.m_listManager, userProfile, lAttributeId));
/*    */     }
/*    */ 
/* 67 */     if (form.getAttribute() != null)
/*    */     {
/* 69 */       form.getAttribute().setLanguage(userProfile.getCurrentLanguage());
/*    */     }
/*    */ 
/* 72 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public boolean getAvailableNotLoggedIn()
/*    */   {
/* 78 */     return true;
/*    */   }
/*    */ 
/*    */   public void setAssetManager(AssetManager a_manager)
/*    */   {
/* 83 */     this.m_assetManager = a_manager;
/*    */   }
/*    */ 
/*    */   public void setListManager(ListManager a_manager)
/*    */   {
/* 88 */     this.m_listManager = a_manager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.ViewSearchBuilderAttributeValueAction
 * JD-Core Version:    0.6.0
 */