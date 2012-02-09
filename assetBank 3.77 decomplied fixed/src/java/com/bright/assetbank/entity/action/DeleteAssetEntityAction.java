/*     */ package com.bright.assetbank.entity.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.entity.exception.EntityHasAssetsException;
/*     */ import com.bright.assetbank.entity.exception.EntityHasRelationshipException;
/*     */ import com.bright.assetbank.entity.form.AssetEntityForm;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
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
/*     */ public class DeleteAssetEntityAction extends AssetEntityAction
/*     */ {
/*  46 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     long lId = getLongParameter(a_request, "id");
/*  67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  68 */     AssetEntityForm form = (AssetEntityForm)a_form;
/*     */ 
/*  71 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  73 */       this.m_logger.error("DeleteAssetEntityAction.execute : User must be an administrator.");
/*  74 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  80 */       getAssetEntityManager().deleteEntity(a_dbTransaction, lId);
/*     */     }
/*     */     catch (EntityHasAssetsException e)
/*     */     {
/*  84 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "cannotDeleteAssetEntity", userProfile.getCurrentLanguage()).getBody());
/*  85 */       a_dbTransaction.rollback2();
/*  86 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */     catch (EntityHasRelationshipException e)
/*     */     {
/*  90 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetEntityHasRelationships", userProfile.getCurrentLanguage()).getBody());
/*  91 */       a_dbTransaction.rollback2();
/*  92 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  95 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 100 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.action.DeleteAssetEntityAction
 * JD-Core Version:    0.6.0
 */