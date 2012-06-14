/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateController;
/*     */ import com.bright.assetbank.batch.update.service.BulkUpdateControllerImpl;
/*     */ import com.bright.assetbank.batch.update.service.UpdateManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class CreateNewBulkUpdateFromLightboxAction extends BTransactionAction
/*     */   implements MessageConstants, AssetBankConstants
/*     */ {
/*  52 */   private UpdateManager m_updateManager = null;
/*     */   private IAssetManager m_assetManager;
/*  64 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setUpdateManager(UpdateManager a_updateManager)
/*     */   {
/*  55 */     this.m_updateManager = a_updateManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_assetManager)
/*     */   {
/*  61 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  67 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  78 */     AssetBoxForm form = (AssetBoxForm)a_form;
/*     */ 
/*  80 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  82 */       this.m_logger.debug("This user does not have permission to view the batch update page");
/*  83 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  87 */     boolean bAssetsLocked = createControllerFromLightbox(userProfile, a_dbTransaction);
/*  88 */     BatchUpdateController controller = userProfile.getBatchUpdateController();
/*     */ 
/*  91 */     if (bAssetsLocked)
/*     */     {
/*  93 */       controller.cancelCurrentBatchUpdate();
/*  94 */       userProfile.setBatchUpdateController(null);
/*  95 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationAssetsLocked", userProfile.getCurrentLanguage()).getBody());
/*     */ 
/*  97 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 100 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   private boolean createControllerFromLightbox(ABUserProfile userProfile, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/* 108 */     BatchUpdateController existingController = userProfile.getBatchUpdateController();
/* 109 */     if (existingController != null)
/*     */     {
/* 111 */       existingController.cancelCurrentBatchUpdate();
/*     */     }
/*     */ 
/* 115 */     BulkUpdateControllerImpl controller = this.m_updateManager.createNewBulkUpdateController();
/* 116 */     userProfile.setBatchUpdateController(controller);
/*     */ 
/* 119 */     controller.startNewBatchUpdate(null, userProfile.getUser().getId());
/*     */ 
/* 123 */     boolean bAssetsLocked = false;
/*     */ 
/* 126 */     Vector vecAssets = new Vector();
/*     */ 
/* 128 */     int numberUpdatePermissionDenied = 0;
/*     */ 
/* 130 */     Iterator itAssetBox = userProfile.getAssetBox().getAssets().iterator();
/* 131 */     while (itAssetBox.hasNext())
/*     */     {
/* 133 */       AssetInList ail = (AssetInList)itAssetBox.next();
/*     */ 
/* 136 */       if (ail.getIsSelected())
/*     */       {
/* 138 */         if (this.m_assetManager.userCanUpdateAsset(userProfile, ail.getAsset()))
/*     */         {
/* 140 */           LightweightAsset asset = ail.getAsset();
/*     */ 
/* 142 */           for (long lGroupId : AttributeConstants.k_aDisplayAttributeGroups)
/*     */           {
/* 144 */             asset.setDescriptions(lGroupId, ail.getDescriptions(lGroupId));
/*     */           }
/* 146 */           vecAssets.add(asset);
/*     */         }
/*     */         else
/*     */         {
/* 150 */           numberUpdatePermissionDenied++;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 155 */     controller.setNumberUpdatePermissionDenied(numberUpdatePermissionDenied);
/* 156 */     controller.setNumberMatchingSearch(vecAssets.size());
/* 157 */     bAssetsLocked = controller.addToBatchUpdate(vecAssets);
/*     */ 
/* 160 */     if ((bAssetsLocked) || (controller.getNumberInBatch() == 0))
/*     */     {
/* 162 */       controller.cancelCurrentBatchUpdate();
/*     */     }
/*     */ 
/* 165 */     return bAssetsLocked;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.CreateNewBulkUpdateFromLightboxAction
 * JD-Core Version:    0.6.0
 */