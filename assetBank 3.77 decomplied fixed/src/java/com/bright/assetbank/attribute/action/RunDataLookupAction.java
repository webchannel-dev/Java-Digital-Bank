/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.attribute.bean.DataLookupRequest;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RunDataLookupAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "RunDataLookupAction";
/*  51 */   private AttributeManager m_attributeManager = null;
/*  52 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  79 */     ActionForward afForward = null;
/*  80 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  81 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/*  84 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  86 */       this.m_logger.error("RunDataLookupActionThis user does not have permission to view the admin pages");
/*  87 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  94 */       DataLookupRequest dlr = AttributeUtil.checkForDataLookup(a_request);
/*     */ 
/*  97 */       Vector vecDataLookup = this.m_attributeManager.dataLookup(a_dbTransaction, dlr);
/*  98 */       form.setDataLookupValues(vecDataLookup);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 102 */       this.m_logger.error("RunDataLookupActionError performing data lookup: " + e.getMessage());
/* 103 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedDataLookup", userProfile.getCurrentLanguage()).getBody() + e.getMessage());
/*     */     }
/*     */ 
/* 107 */     afForward = a_mapping.findForward("Success");
/* 108 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 114 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 119 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.RunDataLookupAction
 * JD-Core Version:    0.6.0
 */