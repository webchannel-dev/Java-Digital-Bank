/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AddAssetBoxAction extends BTransactionAction
/*     */   implements ImageConstants, AssetBankConstants
/*     */ {
/*  47 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/* 121 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  56 */     AssetBoxForm form = (AssetBoxForm)a_form;
/*  57 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  58 */     ActionForward afForward = null;
/*     */ 
/*  61 */     if (StringUtils.isEmpty(form.getName()))
/*     */     {
/*  63 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetboxNameRequired", userProfile.getCurrentLanguage()).getBody());
/*  64 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  69 */     if ((!form.getAjax()) && (!this.m_assetBoxManager.isAssetBoxNameUnique(form.getName(), userProfile)))
/*     */     {
/*  71 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "assetboxNameNotUnique", userProfile.getCurrentLanguage()).getBody());
/*  72 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  75 */     if (form.getAjax())
/*     */     {
/*  77 */       String sOriginalName = form.getName();
/*  78 */       String sNewName = sOriginalName;
/*  79 */       int iCount = 1;
/*     */ 
/*  81 */       while (!this.m_assetBoxManager.isAssetBoxNameUnique(sNewName, userProfile))
/*     */       {
/*  83 */         sNewName = sOriginalName + " (" + iCount + ")";
/*  84 */         iCount++;
/*     */       }
/*     */ 
/*  87 */       form.setName(sNewName);
/*     */     }
/*     */ 
/*  91 */     long lNewId = this.m_assetBoxManager.createAssetBox(null, userProfile.getUser().getId(), form.getName());
/*  92 */     form.setNewAssetBoxId(lNewId);
/*     */ 
/*  95 */     this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */ 
/*  97 */     if (!form.getAjax())
/*     */     {
/* 100 */       afForward = createRedirectingForward("", a_mapping, "Success");
/*     */     }
/*     */     else
/*     */     {
/* 105 */       afForward = a_mapping.findForward("Ajax");
/*     */     }
/*     */ 
/* 108 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 118 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 124 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.AddAssetBoxAction
 * JD-Core Version:    0.6.0
 */