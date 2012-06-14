/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.EmbeddedDataMapping;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.form.EmbeddedDataForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveEmbeddedDataMappingAction extends BTransactionAction
/*     */   implements AttributeConstants, AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "SaveEmbeddedDataMappingAction";
/*  49 */   private AttributeManager m_attributeManager = null;
/*     */ 
/* 155 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ActionForward afForward = null;
/*  77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  80 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  82 */       this.m_logger.error("SaveEmbeddedDataMappingActionThis user does not have permission to view the admin pages");
/*  83 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  87 */     EmbeddedDataForm form = (EmbeddedDataForm)a_form;
/*  88 */     form.validateMapping(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/*  91 */     long lOldAttributeId = getLongParameter(a_request, "oldAttributeId");
/*  92 */     long lOldEmbeddedDataValueId = getLongParameter(a_request, "oldEmbeddedDataValueId");
/*  93 */     long lOldMappingDirectionId = getLongParameter(a_request, "oldMappingDirectionId");
/*  94 */     long lOldTypeId = getLongParameter(a_request, "oldEmbeddedDataTypeId");
/*     */ 
/*  96 */     if (!form.getHasErrors())
/*     */     {
/*     */       try
/*     */       {
/* 100 */         EmbeddedDataMapping oldMapping = null;
/*     */ 
/* 103 */         if ((lOldAttributeId > 0L) && (lOldEmbeddedDataValueId > 0L) && (lOldMappingDirectionId > 0L))
/*     */         {
/* 107 */           oldMapping = this.m_attributeManager.getEmbeddedDataMapping(a_dbTransaction, lOldAttributeId, lOldEmbeddedDataValueId, lOldMappingDirectionId);
/*     */         }
/*     */ 
/* 110 */         boolean bExists = this.m_attributeManager.mappingExists(a_dbTransaction, form.getEmbeddedDataMapping());
/* 111 */         if (((oldMapping == null) && (bExists)) || ((oldMapping != null) && (bExists) && ((lOldAttributeId != form.getEmbeddedDataMapping().getAttributeId()) || (lOldEmbeddedDataValueId != form.getEmbeddedDataMapping().getEmbeddedDataValueId()) || (lOldMappingDirectionId != form.getEmbeddedDataMapping().getMappingDirectionId()))))
/*     */         {
/* 115 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "edmAlreadyExists", userProfile.getCurrentLanguage()).getBody());
/* 116 */           return fail(form, a_request, a_mapping, lOldAttributeId, lOldEmbeddedDataValueId, lOldMappingDirectionId, lOldTypeId);
/*     */         }
/*     */ 
/* 120 */         this.m_attributeManager.saveEmbeddedDataMapping(a_dbTransaction, form.getEmbeddedDataMapping(), form.getEmbeddedDataTypeId(), oldMapping);
/* 121 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */       catch (Bn2Exception bn2e)
/*     */       {
/* 125 */         this.m_logger.error("SaveEmbeddedDataMappingAction" + bn2e.getMessage());
/* 126 */         throw bn2e;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 131 */       return fail(form, a_request, a_mapping, lOldAttributeId, lOldEmbeddedDataValueId, lOldMappingDirectionId, lOldTypeId);
/*     */     }
/*     */ 
/* 134 */     return afForward;
/*     */   }
/*     */ 
/*     */   public ActionForward fail(EmbeddedDataForm a_form, HttpServletRequest a_request, ActionMapping a_mapping, long a_lAttributeId, long a_lValId, long a_lDirectionId, long a_lTypeId)
/*     */   {
/* 139 */     a_form.setFailedValidation(true);
/*     */ 
/* 142 */     a_request.setAttribute("attributeId", new Long(a_lAttributeId));
/* 143 */     a_request.setAttribute("embeddedDataValueId", new Long(a_lValId));
/* 144 */     a_request.setAttribute("mappingDirectionId", new Long(a_lDirectionId));
/* 145 */     a_request.setAttribute("embeddedDataTypeId", new Long(a_lTypeId));
/*     */ 
/* 147 */     return a_mapping.findForward("Failure");
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 152 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 158 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.SaveEmbeddedDataMappingAction
 * JD-Core Version:    0.6.0
 */