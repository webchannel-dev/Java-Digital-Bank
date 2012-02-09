/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.form.EmbeddedDataForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewManageEmbeddedDataMappingsAction extends BTransactionAction
/*     */   implements AttributeConstants
/*     */ {
/*  48 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  79 */       this.m_logger.error("This user does not have permission to view the admin pages");
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  84 */     EmbeddedDataForm form = (EmbeddedDataForm)a_form;
/*  85 */     Vector vecAttributes = this.m_attributeManager.getAttributes(a_dbTransaction, -1L);
/*  86 */     Vector vecValidAttributes = null;
/*     */ 
/*  89 */     if (vecAttributes != null)
/*     */     {
/*  91 */       for (int i = 0; i < vecAttributes.size(); i++)
/*     */       {
/*  93 */         Attribute attribute = (Attribute)vecAttributes.elementAt(i);
/*  94 */         if ((attribute.getStatic()) && ("dateAdded".contains(attribute.getFieldName())))
/*     */           continue;
/*  96 */         if (vecValidAttributes == null)
/*     */         {
/*  98 */           vecValidAttributes = new Vector();
/*     */         }
/* 100 */         vecValidAttributes.add(attribute);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     form.setAttributes(vecValidAttributes);
/* 106 */     form.setEmbeddedDataValues(this.m_attributeManager.getEmbeddedDataValuesByType(a_dbTransaction));
/* 107 */     form.setMappingDirections(this.m_attributeManager.getEmbeddedDataMappingDirections(a_dbTransaction));
/* 108 */     form.setEmbeddedDataMappings(this.m_attributeManager.getEmbeddedDataMappings(a_dbTransaction, -1L));
/* 109 */     form.setEmbeddedDataTypes(this.m_attributeManager.getEmbeddedDataTypes(a_dbTransaction));
/*     */ 
/* 111 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 116 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewManageEmbeddedDataMappingsAction
 * JD-Core Version:    0.6.0
 */