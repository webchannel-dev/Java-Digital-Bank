/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class AssetInAssetBoxAction extends BTransactionAction
/*     */   implements ImageConstants, AssetBankConstants
/*     */ {
/*  51 */   private AssetBoxManager m_assetBoxManager = null;
/*  52 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  61 */     ActionForward afForward = null;
/*  62 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  63 */     String errorMsg = null;
/*     */     try
/*     */     {
/*  67 */       ActionForward forward = performAssetBoxOperation(a_request, a_dbTransaction, userProfile, a_mapping, a_form);
/*     */ 
/*  70 */       if (forward != null)
/*     */       {
/*  72 */         return forward;
/*     */       }
/*     */ 
/*  75 */       this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */     }
/*     */     catch (AssetBoxNotFoundException e)
/*     */     {
/*  80 */       this.m_assetBoxManager.resetAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */ 
/*  83 */       ListItem listItem = this.m_listManager.getListItem(a_dbTransaction, "a-lightbox", userProfile.getCurrentLanguage());
/*     */ 
/*  85 */       errorMsg = this.m_listManager.getListItem(a_dbTransaction, "assetBoxRemoved", userProfile.getCurrentLanguage(), new String[] { listItem.getBody() });
/*     */     }
/*     */ 
/*  89 */     String sForwardAction = a_request.getParameter("forward");
/*  90 */     String sType = a_request.getParameter("type");
/*     */ 
/*  93 */     String sQueryString = "?" + StringUtil.makeQueryString(a_request.getParameterMap());
/*     */ 
/*  95 */     if (errorMsg != null)
/*     */     {
/*  97 */       sQueryString = sQueryString + (sQueryString.length() > 1 ? "&" : "") + "assetBoxErrorMessage" + "=" + errorMsg;
/*     */     }
/*     */ 
/* 103 */     if ((StringUtil.stringIsPopulated(sType)) && (sType.equals("Ajax")))
/*     */     {
/* 106 */       if (StringUtil.stringIsPopulated(sForwardAction))
/*     */       {
/* 108 */         a_request.getSession().setAttribute("lastGetRequestUri", "/action/" + sForwardAction + sQueryString);
/* 109 */         a_request.getSession().setAttribute("manualOveride", new Boolean(true));
/*     */       }
/*     */ 
/* 112 */       afForward = a_mapping.findForward("Ajax");
/*     */     }
/*     */     else
/*     */     {
/* 116 */       afForward = new ActionForward(sForwardAction + sQueryString, true);
/*     */     }
/*     */ 
/* 120 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected abstract ActionForward performAssetBoxOperation(HttpServletRequest paramHttpServletRequest, DBTransaction paramDBTransaction, ABUserProfile paramABUserProfile, ActionMapping paramActionMapping, ActionForm paramActionForm)
/*     */     throws Bn2Exception, AssetBoxNotFoundException;
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 138 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 144 */     this.m_listManager = a_manager;
/*     */   }
/*     */ 
/*     */   protected AssetBoxManager getAssetBoxManager()
/*     */   {
/* 150 */     return this.m_assetBoxManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.AssetInAssetBoxAction
 * JD-Core Version:    0.6.0
 */