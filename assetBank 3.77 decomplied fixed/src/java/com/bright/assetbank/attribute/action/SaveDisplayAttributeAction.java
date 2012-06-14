/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.DisplayAttribute;
/*     */ import com.bright.assetbank.attribute.form.DisplayAttributeForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveDisplayAttributeAction extends DisplayAttributeAction
/*     */   implements AssetBankConstants
/*     */ {
/* 110 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward performAction(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     ActionForward afForward = null;
/*  67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  70 */     DisplayAttributeForm form = (DisplayAttributeForm)a_form;
/*  71 */     form.validate(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/*  73 */     if (!form.getHasErrors())
/*     */     {
/*  75 */       if (StringUtil.stringIsPopulated(form.getDisplayLengthString()))
/*     */       {
/*  77 */         form.getDisplayAttribute().setDisplayLength(Integer.parseInt(form.getDisplayLengthString()));
/*     */       }
/*     */ 
/*  81 */       boolean bNew = false;
/*  82 */       int iNew = getIntParameter(a_request, "new");
/*  83 */       if (iNew > 0)
/*     */       {
/*  85 */         bNew = true;
/*     */       }
/*     */ 
/*  89 */       long lAttributeId = form.getDisplayAttribute().getAttribute().getId();
/*  90 */       if (lAttributeId > 0L)
/*     */       {
/*  92 */         Attribute attribute = getAttributeManager().getAttribute(a_dbTransaction, lAttributeId);
/*  93 */         if (attribute.getStatic())
/*     */         {
/*  95 */           form.getDisplayAttribute().setShowOnChild(false);
/*     */         }
/*     */       }
/*     */ 
/*  99 */       getAttributeManager().saveDisplayAttribute(a_dbTransaction, form.getDisplayAttribute(), bNew);
/* 100 */       afForward = getForwardWithGroupId(a_mapping, form.getDisplayAttribute().getGroupId(), "Success");
/*     */     }
/*     */     else
/*     */     {
/* 104 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 107 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 113 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.SaveDisplayAttributeAction
 * JD-Core Version:    0.6.0
 */