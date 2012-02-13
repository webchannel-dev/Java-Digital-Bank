/*     */ package com.bright.assetbank.entity.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeInList;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.constant.AssetEntityConstants;
/*     */ import com.bright.assetbank.entity.form.AssetEntityForm;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.plugin.service.PluginManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEditAssetEntityAction extends AssetEntityAction
/*     */ {
/*  56 */   private LanguageManager m_languageManager = null;
/*  57 */   private AttributeManager m_attributeManager = null;
/*  58 */   private AssetManager m_assetManager = null;
/*     */   private PluginManager m_pluginManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     AssetEntityForm form = (AssetEntityForm)a_form;
/*     */ 
/*  73 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  76 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  78 */       this.m_logger.error("ViewAddMarketingGroupAction.execute : User must be an administrator.");
/*  79 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  82 */     long lId = getLongParameter(a_request, "id");
/*     */ 
/*  84 */     boolean bAdding = lId <= 0L;
/*     */ 
/*  86 */     if (!bAdding)
/*     */     {
/*  89 */       Vector vAttributeIds = getAssetEntityManager().getAttributeIdsForEntity(a_dbTransaction, lId, false);
/*  90 */       Set setAttributeIds = CollectionUtil.toStringSet(vAttributeIds);
/*  91 */       form.setAllowableAttributeIds((String[])setAttributeIds.toArray(new String[vAttributeIds.size()]));
/*     */ 
/*  94 */       Vector vAssetTypeIds = getAssetEntityManager().getAssetTypeIdsForEntity(a_dbTransaction, lId);
/*  95 */       Set setAssetTypeIds = CollectionUtil.toStringSet(vAssetTypeIds);
/*  96 */       form.setAllowableAssetTypeIds((String[])setAssetTypeIds.toArray(new String[vAssetTypeIds.size()]));
/*     */     }
/*     */ 
/* 100 */     form.setAttributes(this.m_attributeManager.getFlexibleAndSpecifiedStaticAttributeList(a_dbTransaction, 0L, AssetEntityConstants.k_aOptionalStaticAttributes));
/*     */ 
/* 103 */     if (AssetBankSettings.getAgreementsEnabled())
/*     */     {
/* 105 */       Attribute agreement = this.m_attributeManager.getAttribute(a_dbTransaction, 400L);
/* 106 */       form.getAttributes().add(new AttributeInList(agreement.getId(), agreement.getLabel(), 0L, "agreements"));
/*     */     }
/*     */ 
/* 109 */     if (!form.getHasErrors())
/*     */     {
/* 111 */       if (!bAdding)
/*     */       {
/* 113 */         AssetEntity entity = getAssetEntityManager().getEntity(a_dbTransaction, lId);
/* 114 */         form.setEntity(entity);
/* 115 */         getPluginManager().populateEditExistingForm(a_dbTransaction, "assetEntity", entity, a_request, form);
/*     */       }
/*     */       else
/*     */       {
/* 121 */         form.getEntity().setSearchable(true);
/* 122 */         form.getEntity().setShowAttributeLabels(true);
/*     */ 
/* 124 */         if (AssetBankSettings.getSupportMultiLanguage())
/*     */         {
/* 127 */           this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getEntity());
/*     */         }
/*     */ 
/* 130 */         getPluginManager().populateAddForm(a_dbTransaction, "assetEntity", a_request, form);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 137 */     form.setAssetTypes(this.m_assetManager.getAssetTypes());
/*     */ 
/* 139 */     if (!bAdding)
/*     */     {
/* 141 */       getPluginManager().populateViewEditExistingRequest(a_dbTransaction, "assetEntity", a_request, form);
/*     */     }
/*     */     else
/*     */     {
/* 147 */       getPluginManager().populateViewAddRequest(a_dbTransaction, "assetEntity", a_request, form);
/*     */     }
/*     */ 
/* 152 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager languageManager)
/*     */   {
/* 157 */     this.m_languageManager = languageManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager attributeManager)
/*     */   {
/* 162 */     this.m_attributeManager = attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager assetManager)
/*     */   {
/* 167 */     this.m_assetManager = assetManager;
/*     */   }
/*     */ 
/*     */   public PluginManager getPluginManager()
/*     */   {
/* 172 */     return this.m_pluginManager;
/*     */   }
/*     */ 
/*     */   public void setPluginManager(PluginManager a_pluginManager)
/*     */   {
/* 177 */     this.m_pluginManager = a_pluginManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.action.ViewEditAssetEntityAction
 * JD-Core Version:    0.6.0
 */