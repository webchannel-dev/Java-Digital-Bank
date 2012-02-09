/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.exception.StaleDataException;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBoxSummary;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ReorderAssetBoxesAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  53 */   private AssetBoxManager m_assetBoxManager = null;
/*  54 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  63 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  65 */     boolean bUp = Boolean.parseBoolean(a_request.getParameter("up"));
/*  66 */     long lAssetBoxId = getLongParameter(a_request, "id");
/*     */ 
/*  69 */     List assetBoxes = new ArrayList(userProfile.getAssetBoxes());
/*     */ 
/*  72 */     int increment = bUp ? -1 : 1;
/*  73 */     for (int i = 0; i < assetBoxes.size(); i++)
/*     */     {
/*  75 */       AssetBoxSummary box = (AssetBoxSummary)assetBoxes.get(i);
/*  76 */       if ((box.getId() != lAssetBoxId) || (i + increment < 0) || (i + increment >= assetBoxes.size()))
/*     */         continue;
/*  78 */       Collections.swap(assetBoxes, i, i + increment);
/*  79 */       break;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  86 */       this.m_assetBoxManager.reorderAssetBoxes(a_dbTransaction, assetBoxes, userProfile.getUser().getId());
/*     */     }
/*     */     catch (StaleDataException e)
/*     */     {
/*  90 */       this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */ 
/*  92 */       ListItem listItem = this.m_listManager.getListItem(a_dbTransaction, "lightboxes", userProfile.getCurrentLanguage());
/*     */ 
/*  94 */       a_request.setAttribute("assetBoxErrorMessage", this.m_listManager.getListItem(a_dbTransaction, "assetBoxesOutOfDate", userProfile.getCurrentLanguage(), new String[] { listItem.getBody() }));
/*     */     }
/*     */ 
/*  97 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 103 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 109 */     this.m_listManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ReorderAssetBoxesAction
 * JD-Core Version:    0.6.0
 */