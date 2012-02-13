/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBoxAttributeComparator;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SortAssetBoxAction extends BTransactionAction
/*     */ {
/*  52 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  71 */     ActionForward afForward = null;
/*  72 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  73 */     AssetBoxForm form = (AssetBoxForm)a_form;
/*     */ 
/*  76 */     long lAssetBoxId = getLongParameter(a_request, "id");
/*     */ 
/*  78 */     AssetBoxAttributeComparator comparator = new AssetBoxAttributeComparator(form.getSortingAttribute(), form.isSortDescending());
/*     */ 
/*  81 */     Collection[] assetsArray = { userProfile.getAssetBox().getAssetsInState(1), userProfile.getAssetBox().getAssetsInState(2), userProfile.getAssetBox().getAssetsInState(3), userProfile.getAssetBox().getAssetsInState(4), userProfile.getAssetBox().getAssetsInState(5), userProfile.getAssetBox().getAssetsInState(6) };
/*     */ 
/*  89 */     for (Collection assets : assetsArray)
/*     */     {
/*  91 */       if ((assets == null) || (assets.size() <= 0))
/*     */         continue;
/*  93 */       ArrayList<AssetInList> alAssets = new ArrayList();
/*  94 */       alAssets.addAll(assets);
/*  95 */       Collections.sort(alAssets, comparator);
/*     */ 
/*  97 */       int iSequence = 1;

/*  98 */       for (AssetInList asset : alAssets)
/*     */       {
/* 100 */         asset.setSequenceNumber(iSequence);
/* 101 */         iSequence++;
/*     */       }
/*     */ 
/* 104 */       this.m_assetBoxManager.updateAssetSequenceNumbers(a_dbTransaction, lAssetBoxId, alAssets);
/*     */     }
/*     */ 
/* 109 */     userProfile.setLastAssetBoxSortAscending(!form.isSortDescending());
/* 110 */     userProfile.setLastAssetBoxSortAttributeId(form.getSortingAttribute());
/*     */ 
/* 112 */     afForward = a_mapping.findForward("Success");
/* 113 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 118 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.SortAssetBoxAction
 * JD-Core Version:    0.6.0
 */