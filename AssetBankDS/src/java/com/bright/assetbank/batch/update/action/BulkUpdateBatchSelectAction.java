/*     */ package com.bright.assetbank.batch.update.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.batch.update.bean.BulkUpdateBatch;
/*     */ import com.bright.assetbank.batch.update.form.BulkUpdateBatchSelectForm;
/*     */ import com.bright.assetbank.batch.update.service.BulkUpdateControllerImpl;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class BulkUpdateBatchSelectAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/* 104 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  58 */     BulkUpdateBatchSelectForm form = (BulkUpdateBatchSelectForm)a_form;
/*     */ 
/*  61 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  62 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  64 */       this.m_logger.debug("Only logged in users can perform bulk uploads");
/*  65 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  69 */     BulkUpdateControllerImpl controller = (BulkUpdateControllerImpl)userProfile.getBatchUpdateController();
/*  70 */     if (controller == null)
/*     */     {
/*  72 */       throw new Bn2Exception("BulkUpdateStartAction.exceute: called without a valid BatchUpdateController");
/*     */     }
/*     */ 
/*  76 */     BulkUpdateBatch batch = (BulkUpdateBatch)controller.getBatchUpdate();
/*  77 */     batch.clearAssetsToUpdate();
/*     */ 
/*  79 */     Enumeration enumParams = a_request.getParameterNames();
/*  80 */     while (enumParams.hasMoreElements())
/*     */     {
/*  82 */       String sParamName = (String)enumParams.nextElement();
/*     */ 
/*  84 */       if (sParamName.startsWith("update_"))
/*     */       {
/*  87 */         String sAssetId = sParamName.substring("update_".length() + 2);
/*  88 */         batch.addAssetToUpdate(Long.parseLong(sAssetId));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  93 */     if (batch.getAssetsToUpdate().size() == 0)
/*     */     {
/*  95 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoAssetsSelected", userProfile.getCurrentLanguage()).getBody());
/*  96 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 101 */     return createRedirectingForward("", a_mapping, "Success");
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 107 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.update.action.BulkUpdateBatchSelectAction
 * JD-Core Version:    0.6.0
 */