/*     */ package com.bright.assetbank.entity.relationship.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.form.AssetEntityForm;
/*     */ import com.bright.assetbank.entity.relationship.bean.AssetEntityRelationship;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveAssetEntityRelationshipsAction extends AssetEntityRelationshipAction
/*     */   implements AssetBankConstants
/*     */ {
/* 210 */   protected ListManager m_listManager = null;
/*     */ 
/* 216 */   protected CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  56 */     AssetEntityForm form = (AssetEntityForm)a_form;
/*     */ 
/*  58 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  61 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  63 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  66 */     validateMandatoryFields(form, a_request);
/*     */ 
/*  69 */     if (form.getEntity().getAllowChildren())
/*     */     {
/*  71 */       if ((StringUtils.isEmpty(form.getEntity().getChildRelationshipToName())) || (StringUtils.isEmpty(form.getEntity().getChildRelationshipToNamePlural())))
/*     */       {
/*  74 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetEntityRelationshipNamesRequired", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */ 
/*  78 */     if (form.getEntity().getAllowPeers())
/*     */     {
/*  80 */       if ((StringUtils.isEmpty(form.getEntity().getPeerRelationshipToName())) || (StringUtils.isEmpty(form.getEntity().getPeerRelationshipToNamePlural())))
/*     */       {
/*  83 */         form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetEntityRelationshipNamesRequired", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */ 
/*  87 */     if (form.getHasErrors())
/*     */     {
/*  89 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  94 */     AssetEntity saveEntity = getAssetEntityManager().getEntity(a_dbTransaction, form.getEntity().getId());
/*  95 */     saveEntity.setTermForSiblings(form.getEntity().getTermForSiblings());
/*  96 */     saveEntity.setTermForSibling(form.getEntity().getTermForSibling());
/*  97 */     saveEntity.setMustHaveParent(form.getEntity().getMustHaveParent());
/*  98 */     saveEntity.setTranslations(form.getEntity().getTranslations());
/*     */ 
/* 100 */     saveEntity.setChildRelationshipFromName(form.getEntity().getChildRelationshipFromName());
/* 101 */     saveEntity.setChildRelationshipFromNamePlural(form.getEntity().getChildRelationshipFromNamePlural());
/* 102 */     saveEntity.setChildRelationshipToName(form.getEntity().getChildRelationshipToName());
/* 103 */     saveEntity.setChildRelationshipToNamePlural(form.getEntity().getChildRelationshipToNamePlural());
/*     */ 
/* 105 */     saveEntity.setPeerRelationshipToName(form.getEntity().getPeerRelationshipToName());
/* 106 */     saveEntity.setPeerRelationshipToNamePlural(form.getEntity().getPeerRelationshipToNamePlural());
/*     */ 
/* 108 */     getAssetEntityManager().saveEntityOnly(a_dbTransaction, saveEntity);
/*     */ 
/* 111 */     if (form.getEntity().getAllowChildren())
/*     */     {
/* 114 */       if (form.getSelectedChildEntities() != null)
/*     */       {
/* 116 */         for (long lId : form.getSelectedChildEntities())
/*     */         {
/* 118 */           form.getEntity().addChildRelationship(getAER(a_dbTransaction, userProfile, a_request, form, lId, "child"));
/*     */         }
/*     */       }
/* 121 */       getAssetEntityRelationshipManager().saveAllowableEntityRelationships(a_dbTransaction, form.getEntity().getId(), 2L, form.getEntity().getChildRelationships());
/*     */     }
/*     */     else
/*     */     {
/* 126 */       getAssetEntityRelationshipManager().deleteAllowableEntityRelationships(a_dbTransaction, form.getEntity().getId(), 2L);
/*     */     }
/*     */ 
/* 130 */     if (form.getEntity().getAllowPeers())
/*     */     {
/* 132 */       if (form.getSelectedPeerEntities() != null)
/*     */       {
/* 134 */         for (long lId : form.getSelectedPeerEntities())
/*     */         {
/* 136 */           form.getEntity().addPeerRelationship(getAER(a_dbTransaction, userProfile, a_request, form, lId, "peer"));
/*     */         }
/*     */       }
/* 139 */       getAssetEntityRelationshipManager().saveAllowableEntityRelationships(a_dbTransaction, form.getEntity().getId(), 1L, form.getEntity().getPeerRelationships());
/*     */     }
/*     */     else
/*     */     {
/* 144 */       getAssetEntityRelationshipManager().deleteAllowableEntityRelationships(a_dbTransaction, form.getEntity().getId(), 1L);
/*     */     }
/*     */ 
/* 148 */     if (form.getHasErrors())
/*     */     {
/* 150 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 153 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   private AssetEntityRelationship getAER(DBTransaction a_transaction, ABUserProfile a_userProfile, HttpServletRequest a_request, AssetEntityForm a_form, long a_lId, String a_sParamPrefix)
/*     */     throws Bn2Exception
/*     */   {
/* 160 */     AssetEntityRelationship aer = new AssetEntityRelationship();
/* 161 */     aer.setRelatesFromAssetEntityId(a_form.getEntity().getId());
/* 162 */     aer.setRelatesToAssetEntityId(a_lId);
/*     */ 
/* 165 */     String sLabel = a_request.getParameter(a_sParamPrefix + "RelationshipDescriptionLabel" + a_lId);
/* 166 */     aer.setRelationshipDescriptionLabel(sLabel);
/*     */ 
/* 169 */     String sCatId = a_request.getParameter(a_sParamPrefix + "EntityDefaultCategoryId" + a_lId);
/* 170 */     long lCatId = validateDefaultRelationshipCategoryId(a_transaction, a_userProfile, a_form, sCatId);
/*     */ 
/* 172 */     if (lCatId > 0L)
/*     */     {
/* 174 */       aer.setDefaultRelationshipCategoryId(lCatId);
/*     */     }
/* 176 */     return aer;
/*     */   }
/*     */ 
/*     */   private long validateDefaultRelationshipCategoryId(DBTransaction a_transaction, ABUserProfile a_userprofile, AssetEntityForm a_form, String a_sCatId)
/*     */     throws Bn2Exception
/*     */   {
/* 183 */     long lId = -1L;
/* 184 */     if (StringUtil.stringIsPopulated(a_sCatId))
/*     */     {
/*     */       try
/*     */       {
/* 188 */         lId = Long.parseLong(a_sCatId);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 192 */         a_form.addError(this.m_listManager.getListItem(a_transaction, "failedValidationDefRelCatIdNAN", a_userprofile.getCurrentLanguage()).getBody());
/*     */       }
/*     */ 
/* 196 */       Category cat = this.m_categoryManager.getCategory(a_transaction, 1L, lId);
/* 197 */       if (cat == null)
/*     */       {
/* 199 */         Category al = this.m_categoryManager.getCategory(a_transaction, 2L, lId);
/* 200 */         if (al == null)
/*     */         {
/* 202 */           lId = -1L;
/* 203 */           a_form.addError(this.m_listManager.getListItem(a_transaction, "failedValidationDefRelCatInvalid", a_userprofile.getCurrentLanguage()).getBody());
/*     */         }
/*     */       }
/*     */     }
/* 207 */     return lId;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 213 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 219 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.SaveAssetEntityRelationshipsAction
 * JD-Core Version:    0.6.0
 */