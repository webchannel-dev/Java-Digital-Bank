/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ClearAssetBoxAction extends BTransactionAction
/*     */   implements ImageConstants, AssetBankConstants
/*     */ {
/*  56 */   private AssetBoxManager m_assetBoxManager = null;
/*  57 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  66 */     ActionForward afForward = null;
/*  67 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  70 */     if (StringUtil.stringIsPopulated(a_request.getParameter("onlyRemoveSelected")))
/*     */     {
/*  72 */       AssetBox assetBox = userProfile.getAssetBox();
/*  73 */       Iterator itAssetBox = assetBox.getAssets().iterator();
/*     */ 
/*  75 */       while (itAssetBox.hasNext())
/*     */       {
/*  77 */         AssetInList tempAssetInList = (AssetInList)itAssetBox.next();
/*     */ 
/*  80 */         if (tempAssetInList.getIsSelected())
/*     */         {
/*     */           try
/*     */           {
/*  85 */             long lUserId = userProfile.getUser() != null ? userProfile.getUser().getId() : 0L;
/*     */ 
/*  87 */             this.m_assetBoxManager.removeAsset(a_dbTransaction, assetBox, tempAssetInList.getId(), lUserId);
/*     */           }
/*     */           catch (AssetBoxNotFoundException e)
/*     */           {
/*  92 */             this.m_assetBoxManager.resetAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */ 
/*  95 */             ListItem listItem = this.m_listManager.getListItem(a_dbTransaction, "a-lightbox", userProfile.getCurrentLanguage());
/*  96 */             a_request.setAttribute("assetBoxErrorMessage", this.m_listManager.getListItem(a_dbTransaction, "assetBoxRemoved", userProfile.getCurrentLanguage(), new String[] { listItem.getBody() }));
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 106 */       long lUserId = userProfile.getUser() != null ? userProfile.getUser().getId() : 0L;
/*     */       try
/*     */       {
/* 109 */         this.m_assetBoxManager.removeAllAssets(a_dbTransaction, userProfile.getAssetBox(), lUserId);
/*     */       }
/*     */       catch (AssetBoxNotFoundException e)
/*     */       {
/* 116 */         this.m_assetBoxManager.resetAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */ 
/* 119 */         ListItem listItem = this.m_listManager.getListItem(a_dbTransaction, "a-lightbox", userProfile.getCurrentLanguage());
/* 120 */         a_request.setAttribute("assetBoxErrorMessage", this.m_listManager.getListItem(a_dbTransaction, "assetBoxRemoved", userProfile.getCurrentLanguage(), new String[] { listItem.getBody() }));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 126 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 128 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 134 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 140 */     this.m_listManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ClearAssetBoxAction
 * JD-Core Version:    0.6.0
 */