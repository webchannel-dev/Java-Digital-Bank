/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetEntityRelationshipManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
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
/*     */ public class ViewEditAssetRelationshipsAction extends BTransactionAction
/*     */   implements FrameworkConstants, AssetBankConstants, CategoryConstants
/*     */ {
/*  53 */   private IAssetManager m_assetManager = null;
/*  54 */   private AssetEntityRelationshipManager m_assetEntityRelationshipManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  63 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  64 */     AssetForm form = (AssetForm)a_form;
/*     */ 
/*  66 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  68 */       this.m_logger.debug("This user does not have permission to view the edit relationships page");
/*  69 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  72 */     long lAssetId = getAssetId(form, a_request);
/*  73 */     Asset asset = form.getAsset();
/*     */ 
/*  76 */     Vector vAttributes = new Vector();
/*  77 */     vAttributes.add(Long.valueOf(2L));
/*  78 */     vAttributes.add(Long.valueOf(3L));
/*  79 */     vAttributes.add(Long.valueOf(6L));
/*     */ 
/*  83 */     asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, vAttributes, false, userProfile.getCurrentLanguage(), false);
/*     */ 
/*  85 */     if (form.getHasErrors())
/*     */     {
/*  88 */       asset.setParentAssetIdsAsString(form.getAsset().getParentAssetIdsAsString());
/*  89 */       asset.setChildAssetIdsAsString(form.getAsset().getChildAssetIdsAsString());
/*  90 */       asset.setPeerAssetIdsAsString(form.getAsset().getPeerAssetIdsAsString());
/*     */     }
/*     */ 
/*  94 */     form.setAsset(asset);
/*  95 */     form.setEntityCanHaveParents(this.m_assetEntityRelationshipManager.isChildEntity(a_dbTransaction, asset.getEntity().getId()));
/*     */ 
/*  98 */     if ((!form.getHasErrors()) && (!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanUpdateAsset(userProfile, form.getAsset())))
/*     */     {
/* 101 */       this.m_logger.debug("This user does not have permission to update asset id=" + lAssetId);
/* 102 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 105 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   private long getAssetId(AssetForm a_form, HttpServletRequest a_request)
/*     */   {
/* 119 */     long lId = 0L;
/*     */ 
/* 121 */     if (!a_form.getHasErrors())
/*     */     {
/* 124 */       lId = getLongParameter(a_request, "id");
/*     */     }
/*     */ 
/* 127 */     if (lId <= 0L)
/*     */     {
/* 130 */       lId = a_form.getAsset().getId();
/*     */     }
/*     */ 
/* 133 */     return lId;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 139 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityRelationshipManager(AssetEntityRelationshipManager a_assetEntityRelationshipManager)
/*     */   {
/* 144 */     this.m_assetEntityRelationshipManager = a_assetEntityRelationshipManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewEditAssetRelationshipsAction
 * JD-Core Version:    0.6.0
 */