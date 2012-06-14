/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.actiononasset.service.ActionOnAssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.form.ListAttributeForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUpdateListValueAction extends BTransactionAction
/*     */ {
/*  51 */   private AttributeValueManager m_attributeValueManager = null;
/*  52 */   private ActionOnAssetManager m_actionOnAssetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ActionForward afForward = null;
/*  77 */     ListAttributeForm form = (ListAttributeForm)a_form;
/*  78 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  81 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  83 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  84 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  87 */     if (!form.getHasErrors())
/*     */     {
/*  89 */       long lAttributeValueId = getLongParameter(a_request, "id");
/*     */ 
/*  91 */       if (lAttributeValueId <= 0L)
/*     */       {
/*  93 */         lAttributeValueId = form.getValue().getId();
/*     */       }
/*     */ 
/*  96 */       AttributeValue value = this.m_attributeValueManager.getListAttributeValue(null, lAttributeValueId);
/*     */ 
/*  98 */       form.setValue(value);
/*     */     }
/*     */ 
/* 101 */     form.setValues(this.m_attributeValueManager.getListAttributeValues(null, form.getValue().getAttribute().getId()));
/*     */ 
/* 103 */     List actions = this.m_actionOnAssetManager.getAvailableActions(a_transaction);
/* 104 */     form.setActionsOnAssets(actions);
/*     */ 
/* 106 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 108 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*     */   {
/* 113 */     this.m_attributeValueManager = a_attributeValueManager;
/*     */   }
/*     */ 
/*     */   public void setActionOnAssetManager(ActionOnAssetManager a_manager)
/*     */   {
/* 118 */     this.m_actionOnAssetManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewUpdateListValueAction
 * JD-Core Version:    0.6.0
 */