/*     */ package com.bright.assetbank.workset.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.workset.bean.UnsubmittedAssets;
/*     */ import com.bright.assetbank.workset.bean.UnsubmittedItemOptions;
/*     */ import com.bright.assetbank.workset.form.UnsubmittedAssetsForm;
/*     */ import com.bright.assetbank.workset.service.AssetWorksetManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewUnsubmittedAssetsAction extends BTransactionAction
/*     */ {
/*  54 */   private AssetWorksetManager m_assetWorksetManager = null;
/*     */ 
/*     */   public void setAssetWorksetManager(AssetWorksetManager a_wm) {
/*  57 */     this.m_assetWorksetManager = a_wm;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     ActionForward afForward = null;
/*  69 */     UnsubmittedAssetsForm form = (UnsubmittedAssetsForm)a_form;
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     if ((!userProfile.getCanUploadWithApproval()) && (!userProfile.getCanUpdateAssets()))
/*     */     {
/*  75 */       this.m_logger.debug("ViewUnsubmittedAssetsAction: not upload permission");
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  79 */     long lUserId = userProfile.getUser().getId();
/*  80 */     String sLangCode = userProfile.getCurrentLanguage().getCode();
/*  81 */     UnsubmittedAssets list = this.m_assetWorksetManager.getUnsubmittedAssetsForUser(lUserId, sLangCode);
/*     */ 
/*  84 */     boolean bUsersWithUpdateCanAddAssetsToWorkflow = AssetBankSettings.getWorkflowUsersWithUpdateCanAddAssetsToWorkflow();
/*     */ 
/*  87 */     boolean bCanPutLive = true;
/*  88 */     boolean bCanSendToApproval = true;
/*  89 */     boolean bCanDelete = true;
/*     */ 
/*  95 */     HashMap hmAssetSubmitOptions = new HashMap();
/*  96 */     Iterator it = list.getListAssets().iterator();
/*  97 */     while (it.hasNext())
/*     */     {
/*  99 */       LightweightAsset la = (LightweightAsset)it.next();
/*     */ 
/* 101 */       Set vCatIds = la.getRestrictiveCatIds();
/*     */ 
/* 103 */       boolean bUWA = AssetManager.userHasCategoryPermissions(userProfile.getPermissionCategoryIds(6), vCatIds, true);
/*     */ 
/* 106 */       bCanPutLive = true;
/* 107 */       bCanSendToApproval = (bUsersWithUpdateCanAddAssetsToWorkflow) || (bUWA) || (userProfile.getIsAdmin());
/*     */ 
/* 109 */       Set permissionIds = la.getRestrictiveCatIds();
/* 110 */       if ((!userProfile.getIsAdmin()) && (!AssetManager.userHasCategoryPermissions(userProfile.getPermissionCategoryIds(3), permissionIds, true)))
/*     */       {
/* 114 */         bCanPutLive = false;
/*     */       }
/* 116 */       else if (bUsersWithUpdateCanAddAssetsToWorkflow)
/*     */       {
/* 119 */         bCanSendToApproval = true;
/*     */       }
/*     */ 
/* 122 */       UnsubmittedItemOptions options = createOptions(bCanPutLive, bCanSendToApproval, bCanDelete);
/* 123 */       hmAssetSubmitOptions.put(new Long(la.getId()), options);
/*     */     }
/*     */ 
/* 126 */     list.setAssetSubmitOptions(hmAssetSubmitOptions);
/*     */ 
/* 129 */     form.setListAssets(list);
/*     */ 
/* 131 */     afForward = a_mapping.findForward("Success");
/* 132 */     return afForward;
/*     */   }
/*     */ 
/*     */   public static UnsubmittedItemOptions createOptions(boolean bCanPutLive, boolean bCanSendToApproval, boolean bCanDelete)
/*     */   {
/* 143 */     UnsubmittedItemOptions options = new UnsubmittedItemOptions();
/*     */ 
/* 145 */     if (bCanPutLive)
/*     */     {
/* 147 */       options.addOption(0);
/*     */     }
/*     */ 
/* 150 */     if (bCanSendToApproval)
/*     */     {
/* 152 */       options.addOption(1);
/*     */     }
/*     */ 
/* 155 */     if (bCanDelete)
/*     */     {
/* 157 */       options.addOption(2);
/*     */     }
/*     */ 
/* 160 */     return options;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.workset.action.ViewUnsubmittedAssetsAction
 * JD-Core Version:    0.6.0
 */