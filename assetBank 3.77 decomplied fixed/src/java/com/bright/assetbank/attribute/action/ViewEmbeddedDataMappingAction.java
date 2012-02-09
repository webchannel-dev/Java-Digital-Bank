/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.EmbeddedDataMapping;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.form.EmbeddedDataForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEmbeddedDataMappingAction extends BTransactionAction
/*     */   implements AttributeConstants
/*     */ {
/*     */   private static final String c_ksClassName = "ViewEmbeddedDataMappingAction";
/*  47 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  77 */       this.m_logger.error("ViewEmbeddedDataMappingActionThis user does not have permission to delete a sort attribute");
/*  78 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  84 */       long lAttributeId = getLongParameter(a_request, "attributeId");
/*  85 */       long lEmbeddedDataValueId = getLongParameter(a_request, "embeddedDataValueId");
/*  86 */       long lMappingDirectionId = getLongParameter(a_request, "mappingDirectionId");
/*  87 */       long lTypeId = getLongParameter(a_request, "embeddedDataTypeId");
/*     */ 
/*  90 */       if (lAttributeId <= 0L)
/*     */       {
/*  92 */         if (a_request.getAttribute("attributeId") != null)
/*     */         {
/*  94 */           lAttributeId = ((Long)a_request.getAttribute("attributeId")).longValue();
/*     */         }
/*     */       }
/*     */ 
/*  98 */       if (lEmbeddedDataValueId <= 0L)
/*     */       {
/* 100 */         if (a_request.getAttribute("embeddedDataValueId") != null)
/*     */         {
/* 102 */           lEmbeddedDataValueId = ((Long)a_request.getAttribute("embeddedDataValueId")).longValue();
/*     */         }
/*     */       }
/*     */ 
/* 106 */       if (lMappingDirectionId <= 0L)
/*     */       {
/* 108 */         if (a_request.getAttribute("mappingDirectionId") != null)
/*     */         {
/* 110 */           lMappingDirectionId = ((Long)a_request.getAttribute("mappingDirectionId")).longValue();
/*     */         }
/*     */       }
/*     */ 
/* 114 */       if (lTypeId <= 0L)
/*     */       {
/* 116 */         if (a_request.getAttribute("embeddedDataTypeId") != null)
/*     */         {
/* 118 */           lTypeId = ((Long)a_request.getAttribute("embeddedDataTypeId")).longValue();
/*     */         }
/*     */       }
/*     */ 
/* 122 */       if ((lAttributeId < 0L) || (lEmbeddedDataValueId < 0L) || (lMappingDirectionId < 0L))
/*     */       {
/* 124 */         throw new Bn2Exception("ViewEmbeddedDataMappingAction - missing parameter called");
/*     */       }
/*     */ 
/* 127 */       EmbeddedDataForm form = (EmbeddedDataForm)a_form;
/* 128 */       EmbeddedDataMapping mapping = this.m_attributeManager.getEmbeddedDataMapping(a_dbTransaction, lAttributeId, lEmbeddedDataValueId, lMappingDirectionId);
/*     */ 
/* 131 */       form.setOldAttributeId(lAttributeId);
/* 132 */       form.setOldValueId(lEmbeddedDataValueId);
/* 133 */       form.setOldDirectionId(lMappingDirectionId);
/* 134 */       form.setOldTypeId(lTypeId);
/*     */ 
/* 137 */       if (!form.getFailedValidation())
/*     */       {
/* 139 */         form.setEmbeddedDataMapping(mapping);
/*     */       }
/*     */ 
/* 142 */       form.setAttributes(this.m_attributeManager.getAttributes(a_dbTransaction, -1L));
/* 143 */       form.setEmbeddedDataValues(this.m_attributeManager.getEmbeddedDataValuesByType(a_dbTransaction));
/* 144 */       form.setMappingDirections(this.m_attributeManager.getEmbeddedDataMappingDirections(a_dbTransaction));
/* 145 */       form.setEmbeddedDataTypes(this.m_attributeManager.getEmbeddedDataTypes(a_dbTransaction));
/*     */ 
/* 147 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 151 */       this.m_logger.error("ViewEmbeddedDataMappingAction" + bn2e.getMessage());
/* 152 */       throw bn2e;
/*     */     }
/*     */ 
/* 155 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 161 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewEmbeddedDataMappingAction
 * JD-Core Version:    0.6.0
 */