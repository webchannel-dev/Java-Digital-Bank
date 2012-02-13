/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.util.UploadUtil;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.bean.AssetEntityRetreivalCriteria;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.entity.util.AssetEntityUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.RefDataItem;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUploadAssetFileAction extends BTransactionAction
/*     */ {
/*     */   private static final String k_sForwardName_NoFile = "NoFile";
/*  65 */   private AssetEntityManager m_assetEntityManager = null;
/*  66 */   private AssetManager m_assetManager = null;
/*  67 */   private FilterManager m_filterManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  84 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/*  86 */     if ((!userProfile.getIsLoggedIn()) || ((!userProfile.getCanUpdateAssets()) && (!userProfile.getCanUploadWithApproval())))
/*     */     {
/*  88 */       this.m_logger.debug("This user does not have permission to upload files");
/*  89 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  93 */     if (AssetBankSettings.getAttributeTemplatesEnabled())
/*     */     {
/*  95 */       form.setTemplates(this.m_filterManager.getTemplates(a_dbTransaction, userProfile));
/*     */     }
/*     */ 
/*  99 */     if ((form.getSelectedAssetEntityId() <= 0L) || (form.getHasErrors()))
/*     */     {
/* 101 */       long lEntityId = getLongParameter(a_request, "entityId");
/*     */ 
/* 103 */       if (lEntityId > 0L)
/*     */       {
/* 105 */         form.getAsset().setEntity(this.m_assetEntityManager.getEntity(a_dbTransaction, lEntityId));
/* 106 */         form.setSelectedAssetEntityId(lEntityId);
/* 107 */         form.setEntityPreSelected(true);
/*     */ 
/* 110 */         if ((!form.getHasErrors()) && (!form.getAsset().getEntity().getAllowAssetFiles()))
/*     */         {
/* 112 */           return a_mapping.findForward("NoFile");
/*     */         }
/*     */       }
/* 115 */       else if (!form.isEntityPreSelected())
/*     */       {
/* 117 */         long lParentId = getParentId(a_request, form);
/* 118 */         long lPeerId = getPeerId(a_request, form);
/* 119 */         Vector <AssetEntity> entities = getEntitiesList(a_dbTransaction, a_request, form, lPeerId, lParentId);
/* 120 */         LanguageUtils.setLanguageOnAll(entities, userProfile.getCurrentLanguage());
/*     */ 
/* 122 */         List allowFiles = new ArrayList(entities.size());
/* 123 */         for (AssetEntity entity : entities)
/*     */         {
/* 125 */           allowFiles.add(Boolean.valueOf((entity.getAllowAssetFiles()) && (this.m_assetManager.allowFileUploadOnAdd(entity))));
/*     */         }
/* 127 */         a_request.setAttribute("allowFiles", allowFiles);
/*     */ 
/* 129 */         form.setEntities(entities);
/*     */ 
/* 131 */         if (entities.size() == 1)
/*     */         {
/* 133 */           AssetEntity entity = (AssetEntity)entities.get(0);
/* 134 */           form.setSelectedAssetEntityId(entity.getId());
/* 135 */           form.getAsset().setEntity(entity);
/*     */ 
/* 138 */           if (!entity.getAllowAssetFiles())
/*     */           {
/* 141 */             return a_mapping.findForward("NoFile");
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 146 */           RefDataItem[] assetTypes = this.m_assetManager.getAssetTypes();
/*     */ 
/* 150 */           if ((!userProfile.getUserCanAddEmptyAssets()) && (entities != null) && (entities.size() > 0) && (lPeerId <= 0L) && (lParentId <= 0L) && (!AssetEntityUtil.isEntitySelectionRequired(entities, assetTypes)))
/*     */           {
/* 157 */             form.setDeferEntitySelection(true);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 164 */     if (form.getPossibleFileFormats() == null)
/*     */     {
/* 166 */       UploadUtil.clearSingleUploadDir(userProfile, a_request.getSession());
/*     */     }
/*     */ 
/* 169 */     if (!form.getHasErrors())
/*     */     {
/* 171 */       UploadUtil.setUploadToolOption(a_request, form);
/*     */     }
/*     */ 
/* 174 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   private Vector<AssetEntity> getEntitiesList(DBTransaction a_transaction, HttpServletRequest a_request, AssetForm a_form, long a_lPeerId, long a_lParentId)
/*     */     throws Bn2Exception
/*     */   {
/* 197 */     Vector vecRestrictions = new Vector();
/* 198 */     Vector vecParamRestrictions = new Vector();
/*     */ 
/* 201 */     String sParameterRestrictions = a_request.getParameter("entityRestrictions");
/* 202 */     if (StringUtil.stringIsPopulated(sParameterRestrictions))
/*     */     {
/* 204 */       vecParamRestrictions = StringUtil.convertToVectorOfLongs(sParameterRestrictions, ",");
/*     */     }
/*     */ 
/* 208 */     long a_lParentEntityId = getLongParameter(a_request, "parentEntity");
/* 209 */     if ((a_lParentId > 0L) || (a_lParentEntityId > 0L))
/*     */     {
/* 211 */       AssetEntity entity = null;
/* 212 */       if (a_lParentId > 0L)
/*     */       {
/* 214 */         Asset asset = this.m_assetManager.getAsset(a_transaction, a_lParentId, null, false, false);
/* 215 */         entity = asset.getEntity();
/*     */       }
/*     */       else
/*     */       {
/* 219 */         entity = this.m_assetEntityManager.getEntity(a_transaction, a_lParentEntityId);
/*     */       }
/* 221 */       if (entity.getChildRelationshipIds() != null)
/*     */       {
/* 223 */         vecRestrictions.addAll(CollectionUtil.convertToVectorOfLongs(entity.getChildRelationshipIds()));
/*     */       }
/* 225 */       a_form.setParentId(a_lParentId);
/*     */     }
/* 227 */     else if (a_lPeerId > 0L)
/*     */     {
/* 229 */       Asset asset = this.m_assetManager.getAsset(a_transaction, a_lPeerId, null, false, false);
/* 230 */       if (asset.getEntity().getPeerRelationshipIds() != null)
/*     */       {
/* 232 */         vecRestrictions.addAll(CollectionUtil.convertToVectorOfLongs(asset.getEntity().getPeerRelationshipIds()));
/*     */       }
/* 234 */       a_form.setPeerId(a_lPeerId);
/*     */     }
/*     */ 
/* 239 */     AssetEntityRetreivalCriteria criteria = new AssetEntityRetreivalCriteria();
/* 240 */     criteria.setOnlyParentlessEntities((a_lParentId <= 0L) && (a_lParentEntityId <= 0L));
/*     */ 
/* 244 */     if ((vecParamRestrictions.size() > 0) && (vecRestrictions.size() > 0))
/*     */     {
/* 246 */       vecRestrictions = CollectionUtil.intersection(vecRestrictions, vecParamRestrictions);
/*     */     }
/* 248 */     else if (vecRestrictions.size() <= 0)
/*     */     {
/* 250 */       vecRestrictions = vecParamRestrictions;
/*     */     }
/* 252 */     criteria.setRestrictions(CollectionUtil.getLongArray(vecRestrictions));
/*     */ 
/* 255 */     if (a_form.getCatExtensionCatId() > 0L)
/*     */     {
/* 257 */       criteria.setCategoryExtensionStatus(1);
/*     */     }
/*     */     else
/*     */     {
/* 261 */       criteria.setCategoryExtensionStatus(2);
/*     */     }
/* 263 */     Vector entities = this.m_assetEntityManager.getEntities(a_transaction, criteria);
/* 264 */     return entities;
/*     */   }
/*     */ 
/*     */   private long getPeerId(HttpServletRequest a_request, AssetForm a_form)
/*     */   {
/* 270 */     long lPeerId = getLongParameter(a_request, "peerId");
/* 271 */     if (lPeerId <= 0L)
/*     */     {
/* 273 */       lPeerId = a_form.getPeerId();
/*     */     }
/*     */ 
/* 276 */     return lPeerId;
/*     */   }
/*     */ 
/*     */   private long getParentId(HttpServletRequest a_request, AssetForm a_form)
/*     */   {
/* 282 */     long lParentId = getLongParameter(a_request, "parentId");
/* 283 */     if (lParentId <= 0L)
/*     */     {
/* 285 */       lParentId = a_form.getParentId();
/*     */     }
/*     */ 
/* 288 */     return lParentId;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager assetEntityManager)
/*     */   {
/* 294 */     this.m_assetEntityManager = assetEntityManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager assetManager)
/*     */   {
/* 299 */     this.m_assetManager = assetManager;
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 304 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewUploadAssetFileAction
 * JD-Core Version:    0.6.0
 */