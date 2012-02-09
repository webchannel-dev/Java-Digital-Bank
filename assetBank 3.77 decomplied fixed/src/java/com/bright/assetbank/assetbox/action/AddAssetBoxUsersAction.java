/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.form.ShareAssetBoxForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddAssetBoxUsersAction extends BTransactionAction
/*     */   implements AssetBoxConstants, AssetBankConstants, FrameworkConstants
/*     */ {
/*  48 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     ActionForward afForward = null;
/*  58 */     ShareAssetBoxForm form = (ShareAssetBoxForm)a_form;
/*  59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  60 */     long lId = getLongParameter(a_request, "assetBoxId");
/*  61 */     String[] asUserIds = a_request.getParameterValues("selectedUsers");
/*     */ 
/*  64 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  66 */       this.m_logger.debug("The user must be logged in to access " + getClass().getSimpleName());
/*  67 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  70 */     if ((asUserIds != null) && (asUserIds.length > 0))
/*     */     {
/*  72 */       boolean bCanEdit = Boolean.parseBoolean(a_request.getParameter("canEdit"));
/*  73 */       long[] alUserIds = new long[asUserIds.length];
/*     */ 
/*  75 */       for (int i = 0; i < asUserIds.length; i++)
/*     */       {
/*  77 */         alUserIds[i] = Long.parseLong(asUserIds[i]);
/*     */       }
/*     */ 
/*  80 */       String sUrl = ServletUtil.getApplicationUrl(a_request) + "/action/" + "viewAssetBox" + "?" + "assetBoxId" + "=" + lId;
/*     */ 
/*  82 */       this.m_assetBoxManager.addAssetBoxShares(a_dbTransaction, lId, alUserIds, bCanEdit, (AssetBankSettings.getEmailUserUponLightboxShare()) && (form.getSendEmailOnShare()), sUrl, form.getEmailTemplateId(), form.getEmailContent(), userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/*  93 */     afForward = a_mapping.findForward("Success");
/*     */ 
/*  96 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 102 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.AddAssetBoxUsersAction
 * JD-Core Version:    0.6.0
 */