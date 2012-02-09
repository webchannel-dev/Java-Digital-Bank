/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ActionOnSelectedAssetsAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*  48 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     ActionForward afForward = null;
/*     */ 
/*  59 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  62 */     int lActionOnSelectedAsset = getIntParameter(a_request, "actionOnSelectedAssets");
/*     */ 
/*  65 */     String[] asComparedAssetIds = a_request.getParameterValues("selectedAssetIds");
/*     */ 
/*  67 */     if ((asComparedAssetIds == null) || (asComparedAssetIds.length == 0))
/*     */     {
/*  69 */       return createRedirectingForward("error=" + this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoAssetsSelectedLightbox", userProfile.getCurrentLanguage()).getBody(), a_mapping, "Failure");
/*     */     }
/*     */ 
/*  73 */     AssetBox assetBox = userProfile.getAssetBox();
/*  74 */     Iterator iterator = assetBox.getAssets().iterator();
/*     */ 
/*  77 */     while (iterator.hasNext())
/*     */     {
/*  79 */       AssetInList tempAssetInList = (AssetInList)iterator.next();
/*  80 */       tempAssetInList.setIsSelected(false);
/*     */     }
/*     */ 
/*  84 */     iterator = assetBox.getAssets().iterator();
/*  85 */     while (iterator.hasNext())
/*     */     {
/*  87 */       AssetInList tempAssetInList = (AssetInList)iterator.next();
/*     */ 
/*  89 */       if (asComparedAssetIds != null)
/*     */       {
/*  91 */         for (int i = 0; i < asComparedAssetIds.length; i++)
/*     */         {
/*  93 */           long lCurrentAssetId = Long.parseLong(asComparedAssetIds[i]);
/*     */ 
/*  95 */           if (lCurrentAssetId != tempAssetInList.getId())
/*     */             continue;
/*  97 */           tempAssetInList.setIsSelected(true);
/*  98 */           break;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 104 */     if (lActionOnSelectedAsset <= 0)
/*     */     {
/* 106 */       return createRedirectingForward("error=" + this.m_listManager.getListItem(a_dbTransaction, "failedValidationNoAssetActionSelected", userProfile.getCurrentLanguage()).getBody(), a_mapping, "Failure");
/*     */     }
/* 108 */     if ((asComparedAssetIds != null) && (asComparedAssetIds.length == 1) && (lActionOnSelectedAsset == 2))
/*     */     {
/* 110 */       return createRedirectingForward("error=" + this.m_listManager.getListItem(a_dbTransaction, "failedValidationOneAssetSelectedLightbox", userProfile.getCurrentLanguage()).getBody(), a_mapping, "Failure");
/*     */     }
/*     */     String sQueryString;
/* 115 */     switch (lActionOnSelectedAsset)
/*     */     {
/*     */     case 1:
/* 119 */       afForward = createRedirectingForward("", a_mapping, "DownloadAssets");
/* 120 */       break;
/*     */     case 2:
/* 124 */       afForward = createRedirectingForward("", a_mapping, "CompareAssets");
/* 125 */       break;
/*     */     case 3:
/* 129 */       afForward = createRedirectingForward("", a_mapping, "RemoveAssets");
/* 130 */       break;
/*     */     case 4:
/* 134 */       afForward = createRedirectingForward("", a_mapping, "RequestOnCd");
/* 135 */       break;
/*     */     case 5:
/* 141 */       afForward = createRedirectingForward("", a_mapping, "BulkUpdate");
/* 142 */       break;
/*     */     case 6:
/* 146 */       afForward = createRedirectingForward("", a_mapping, "RelateAssets");
/* 147 */       break;
/*     */     case 7:
/* 151 */       afForward = createRedirectingForward("", a_mapping, "DeleteAssets");
/* 152 */       break;
/*     */     case 8:
/* 157 */       sQueryString = "returnUrl=" + a_request.getParameter("returnUrl");
/* 158 */       afForward = createRedirectingForward(sQueryString, a_mapping, "ViewSlideshow");
/* 159 */       break;
/*     */     case 9:
/* 164 */       sQueryString = "returnUrl=" + a_request.getParameter("returnUrl") + "&type=" + "assetbox";
/*     */ 
/* 166 */       afForward = createRedirectingForward(sQueryString, a_mapping, "CreateSlideshow");
/*     */     }
/*     */ 
/* 170 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 175 */     this.m_listManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ActionOnSelectedAssetsAction
 * JD-Core Version:    0.6.0
 */