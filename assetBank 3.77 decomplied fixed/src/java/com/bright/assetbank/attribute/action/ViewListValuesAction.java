/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.actiononasset.service.ActionOnAssetManager;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.form.ListAttributeForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
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
/*     */ public class ViewListValuesAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  56 */   private AttributeManager m_attributeManager = null;
/*  57 */   private AttributeValueManager m_attributeValueManager = null;
/*  58 */   private LanguageManager m_languageManager = null;
/*  59 */   private ActionOnAssetManager m_actionOnAssetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     ActionForward afForward = null;
/*  84 */     ListAttributeForm form = (ListAttributeForm)a_form;
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  88 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  90 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  91 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  94 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*     */ 
/*  96 */     if (lAttributeId <= 0L)
/*     */     {
/*  98 */       lAttributeId = form.getValue().getAttribute().getId();
/*     */     }
/*     */ 
/* 101 */     Attribute attribute = this.m_attributeManager.getAttribute(null, lAttributeId);
/*     */ 
/* 104 */     if ((attribute != null) && ((attribute.getTypeId() == 4L) || (attribute.getTypeId() == 5L) || (attribute.getTypeId() == 6L)))
/*     */     {
/* 108 */       if (!form.getHasErrors())
/*     */       {
/* 110 */         AttributeValue newValue = new AttributeValue();
/*     */ 
/* 113 */         if (AssetBankSettings.getSupportMultiLanguage())
/*     */         {
/* 115 */           this.m_languageManager.createEmptyTranslations(a_transaction, newValue);
/*     */         }
/*     */ 
/* 118 */         form.setValue(newValue);
/*     */       }
/*     */ 
/* 121 */       form.setValues(this.m_attributeValueManager.getListAttributeValues(null, lAttributeId));
/* 122 */       form.getValue().setAttribute(attribute);
/*     */ 
/* 124 */       List actions = this.m_actionOnAssetManager.getAvailableActions(a_transaction);
/* 125 */       form.setActionsOnAssets(actions);
/*     */ 
/* 127 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     else
/*     */     {
/* 131 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 134 */     return afForward;
/*     */   }
/*     */ 
/*     */   public AttributeManager getAttributeManager()
/*     */   {
/* 140 */     return this.m_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_sAttributeManager)
/*     */   {
/* 146 */     this.m_attributeManager = a_sAttributeManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*     */   {
/* 151 */     this.m_attributeValueManager = a_attributeValueManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 156 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ 
/*     */   public void setActionOnAssetManager(ActionOnAssetManager a_manager)
/*     */   {
/* 161 */     this.m_actionOnAssetManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewListValuesAction
 * JD-Core Version:    0.6.0
 */