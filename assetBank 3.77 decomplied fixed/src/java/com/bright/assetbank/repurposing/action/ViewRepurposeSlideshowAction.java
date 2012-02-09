/*     */ package com.bright.assetbank.repurposing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.repurposing.form.RepurposeSlideshowForm;
/*     */ import com.bright.assetbank.repurposing.util.RepurposingUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewRepurposeSlideshowAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  50 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     RepurposeSlideshowForm form = (RepurposeSlideshowForm)a_form;
/*     */ 
/*  62 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request.getSession());
/*  63 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  65 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  69 */     ArrayList textAttributes = new ArrayList();
/*     */ 
/*  71 */     Vector textFieldLongAttributes = this.m_attributeManager.getAttributes(a_dbTransaction, 1L);
/*  72 */     Vector textAreaLongAttributes = this.m_attributeManager.getAttributes(a_dbTransaction, 2L);
/*  73 */     Vector textFieldShortAttributes = this.m_attributeManager.getAttributes(a_dbTransaction, 14L);
/*  74 */     Vector textAreaShortAttributes = this.m_attributeManager.getAttributes(a_dbTransaction, 15L);
/*     */ 
/*  76 */     if (textFieldLongAttributes != null)
/*     */     {
/*  78 */       textAttributes.addAll(textFieldLongAttributes);
/*     */     }
/*  80 */     if (textAreaLongAttributes != null)
/*     */     {
/*  82 */       textAttributes.addAll(textAreaLongAttributes);
/*     */     }
/*  84 */     if (textFieldShortAttributes != null)
/*     */     {
/*  86 */       textAttributes.addAll(textFieldShortAttributes);
/*     */     }
/*  88 */     if (textAreaShortAttributes != null)
/*     */     {
/*  90 */       textAttributes.addAll(textAreaShortAttributes);
/*     */     }
/*     */ 
/*  93 */     form.setTextAttributes(textAttributes);
/*     */ 
/*  96 */     Vector vecAttributes = this.m_attributeManager.getVisibleAttributes(a_dbTransaction);
/*     */ 
/*  99 */     vecAttributes = RepurposingUtil.filterAttributes(vecAttributes, userProfile, null);
/*     */ 
/* 102 */     String sCaptionIds = a_request.getParameter("captionIds");
/*     */ 
/* 104 */     Vector vecCaptionIds = StringUtil.convertToVector(sCaptionIds, ",");
/*     */ 
/* 106 */     Iterator iterator = vecAttributes.iterator();
/* 107 */     while (iterator.hasNext())
/*     */     {
/* 109 */       Attribute attribute = (Attribute)iterator.next();
/*     */ 
/* 111 */       if (vecCaptionIds.contains(String.valueOf(attribute.getId())))
/*     */       {
/* 113 */         attribute.setSelected(true);
/*     */       }
/*     */     }
/*     */ 
/* 117 */     form.setAttributes(vecAttributes);
/*     */ 
/* 119 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_sAttributeManager)
/*     */   {
/* 124 */     this.m_attributeManager = a_sAttributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ViewRepurposeSlideshowAction
 * JD-Core Version:    0.6.0
 */