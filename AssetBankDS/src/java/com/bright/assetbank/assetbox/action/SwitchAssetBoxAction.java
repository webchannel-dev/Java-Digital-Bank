/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SwitchAssetBoxAction extends BTransactionAction
/*     */   implements AssetBoxConstants, FrameworkConstants
/*     */ {
/*  55 */   private AssetBoxManager m_assetBoxManager = null;
/*  56 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  65 */     ActionForward afForward = null;
/*  66 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  69 */     long id = getLongParameter(a_request, "currentAssetBoxId");
/*     */ 
/*  71 */     if ((id > 0L) && (id != userProfile.getAssetBox().getId()))
/*     */     {
/*     */       try
/*     */       {
/*  75 */         this.m_assetBoxManager.refreshAssetBoxInProfileOrFail(a_dbTransaction, userProfile, id);
/*     */       }
/*     */       catch (AssetBoxNotFoundException e)
/*     */       {
/*  80 */         this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */ 
/*  83 */         ListItem listItem = this.m_listManager.getListItem(a_dbTransaction, "a-lightbox", userProfile.getCurrentLanguage());
/*  84 */         a_request.setAttribute("assetBoxErrorMessage", this.m_listManager.getListItem(a_dbTransaction, "assetBoxNotAvailable", userProfile.getCurrentLanguage(), new String[] { listItem.getBody() }));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  89 */     if (StringUtil.stringIsPopulated(a_request.getParameter("ajax")))
/*     */     {
/*  91 */       afForward = a_mapping.findForward("Ajax");
/*     */     }
/*  93 */     else if (StringUtils.isNotEmpty((String)a_request.getSession().getAttribute("lastGetRequestUri")))
/*     */     {
/*  96 */       String sForward = (String)a_request.getSession().getAttribute("lastGetRequestUri");
/*  97 */       afForward = new ActionForward(sForward);
/*     */     }
/*     */     else
/*     */     {
/* 101 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 104 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 110 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 116 */     this.m_listManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.SwitchAssetBoxAction
 * JD-Core Version:    0.6.0
 */