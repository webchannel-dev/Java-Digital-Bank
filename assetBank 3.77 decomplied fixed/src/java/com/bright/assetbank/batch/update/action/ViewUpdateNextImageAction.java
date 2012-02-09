/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.action.ViewAddAssetAction;
/*     */ import com.bright.assetbank.application.action.ViewAssetAction;
/*     */ import com.bright.assetbank.application.action.ViewUpdateAssetAction;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.SubmitOptions;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.AssetForm;
/*     */ import com.bright.assetbank.batch.update.bean.Batch;
/*     */ import com.bright.assetbank.batch.update.form.BatchAssetForm;
/*     */ import com.bright.assetbank.batch.update.service.BatchUpdateControllerImpl;
/*     */ import com.bright.assetbank.category.bean.ExtendedCategoryInfo;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workflow.bean.AssetWorkflowAuditEntry;
/*     */ import com.bright.assetbank.workflow.service.AssetWorkflowManager;
/*     */ import com.bright.assetbank.workflow.util.WorkflowUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUpdateNextImageAction extends ViewUpdateAssetAction
/*     */ {
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  75 */     BatchUpdateControllerImpl controller = (BatchUpdateControllerImpl)userProfile.getBatchUpdateController();
/*     */ 
/*  77 */     if (controller == null)
/*     */     {
/*  79 */       throw new Bn2Exception("ViewUpdateNextImageAction.exceute: called without a valid BatchUpdateController");
/*     */     }
/*     */ 
/*  82 */     BatchAssetForm batchImageForm = (BatchAssetForm)a_form;
/*  83 */     long lImageId = 0L;
/*     */ 
/*  85 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  87 */       this.m_logger.debug("This user is not logged in!");
/*  88 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  92 */     if (batchImageForm.getHasErrors())
/*     */     {
/*  94 */       lImageId = batchImageForm.getAsset().getId();
/*     */ 
/*  97 */       populateForm(a_dbTransaction, lImageId, batchImageForm, a_request);
/*     */     } else {
/*  99 */       if (!controller.getHasNext())
/*     */       {
/* 102 */         ActionForward forward = null;
/*     */ 
/* 106 */         String sQueryString = "";
/* 107 */         if (controller.getNumberInBatch() == 0)
/*     */         {
/* 109 */           sQueryString = "empty=1";
/*     */ 
/* 111 */           if ((controller.getBatchUpdate() != null) && (controller.getBatchUpdate().getExistLockedAssetsInBatch()))
/*     */           {
/* 114 */             sQueryString = sQueryString + "&locked=1";
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 119 */         controller.cancelCurrentBatchUpdate();
/*     */ 
/* 122 */         if ((controller.getFinishedUrl() != null) && (controller.getFinishedUrl().length() > 0))
/*     */         {
/* 124 */           forward = createRedirectingForward(controller.getFinishedUrl());
/*     */         }
/*     */         else
/*     */         {
/* 128 */           forward = createForward(sQueryString, a_mapping, "Finished");
/*     */         }
/*     */ 
/* 131 */         return forward;
/*     */       }
/*     */ 
/* 136 */       lImageId = getImageId(controller, batchImageForm, a_request);
/*     */ 
/* 139 */       populateForm(a_dbTransaction, lImageId, batchImageForm, a_request);
/*     */ 
/* 141 */       batchImageForm.setAddedByUser(batchImageForm.getAsset().getAddedByUser().getFullName());
/*     */ 
/* 143 */       batchImageForm.setAddedDate(new SimpleDateFormat(AssetBankSettings.getDisplayDateTimeFormat()).format(batchImageForm.getAsset().getDateAdded()));
/*     */ 
/* 148 */       Asset asset = batchImageForm.getAsset();
/* 149 */       if (asset.getThumbnailImageFile() != null)
/*     */       {
/* 151 */         batchImageForm.setThumbnailUrl(asset.getThumbnailImageFile().getPath());
/*     */       }
/*     */ 
/* 155 */       Vector vecAuditEntries = this.m_assetWorkflowManager.getWorkflowAudit(a_dbTransaction, lImageId);
/* 156 */       if (vecAuditEntries.size() > 0)
/*     */       {
/* 158 */         batchImageForm.setAuditEntry((AssetWorkflowAuditEntry)vecAuditEntries.get(0));
/*     */       }
/*     */ 
/* 162 */       batchImageForm.setApprovalMode(controller.getIsApproval());
/* 163 */       batchImageForm.setUnsubmittedMode(controller.getIsUnsubmitted());
/* 164 */       batchImageForm.setOwnerMode(controller.getIsOwner());
/*     */     }
/*     */ 
/* 168 */     SubmitOptions options = ViewAddAssetAction.getSubmitOptions(userProfile, false, batchImageForm.getAsset().getIsUnsubmitted(), batchImageForm.getAsset().getHasWorkflow(), true, batchImageForm.getAsset().getExtendsCategory().getId() > 0L);
/* 169 */     batchImageForm.setSubmitOptions(options);
/*     */ 
/* 171 */     if (batchImageForm.getAsset().getExtendsCategory().getId() <= 0L)
/*     */     {
/* 173 */       Vector vecWorkflowOptions = WorkflowUtil.getWorkflowSubmitOptions(a_dbTransaction, userProfile, batchImageForm.getAsset(), this.m_assetManager, this.m_categoryManager, batchImageForm, this.m_workflowManager);
/* 174 */       batchImageForm.setWorkflowOptions(vecWorkflowOptions);
/*     */     }
/*     */ 
/* 178 */     batchImageForm.setTransitions(controller.getWorkflowTransitions());
/* 179 */     batchImageForm.setState(controller.getState());
/*     */ 
/* 182 */     ViewAssetAction.setPermissionsInAssetForm(batchImageForm, batchImageForm.getAsset(), userProfile, this.m_assetManager);
/*     */ 
/* 185 */     batchImageForm.setNumberLeftInBatch(controller.getNumberToGo());
/*     */ 
/* 187 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   private long getImageId(BatchUpdateControllerImpl a_controller, AssetForm a_form, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 205 */     String sResumeParam = a_request.getParameter("resume");
/*     */ 
/* 207 */     if ((sResumeParam != null) && (sResumeParam.equals("true")))
/*     */     {
/* 209 */       return a_controller.getCurrentAsset();
/*     */     }
/*     */ 
/* 212 */     return a_controller.getNextAsset();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.ViewUpdateNextImageAction
 * JD-Core Version:    0.6.0
 */