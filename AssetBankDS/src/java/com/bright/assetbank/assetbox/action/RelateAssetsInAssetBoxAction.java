/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.constant.ImageConstants;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RelateAssetsInAssetBoxAction extends BTransactionAction
/*     */   implements AssetBoxConstants, MessageConstants, AssetBankConstants, ImageConstants
/*     */ {
/*  56 */   private IAssetManager m_assetManager = null;
/*  57 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*     */ 
/*  59 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  68 */     ActionForward afForward = null;
/*  69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  70 */     AssetBoxForm asForm = (AssetBoxForm)a_form;
/*     */ 
/*  73 */     AssetBox assetBox = userProfile.getAssetBox();
/*     */ 
/*  75 */     if (assetBox.getNumAssetsSelected() <= 1)
/*     */     {
/*  77 */       asForm.addError(this.m_listManager.getListItem(a_dbTransaction, "relatedAssetsFailure", userProfile.getCurrentLanguage()).getBody());
/*  78 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  82 */     Iterator iterator = assetBox.getAssets().iterator();
/*  83 */     Vector vecAssets = new Vector();
/*     */ 
/*  85 */     while (iterator.hasNext())
/*     */     {
/*  87 */       AssetInList tempAssetInList = (AssetInList)iterator.next();
/*     */ 
/*  90 */       if (((userProfile.getIsAdmin()) || (this.m_assetManager.userCanUpdateAsset(userProfile, tempAssetInList.getAsset()))) && (tempAssetInList.getIsSelected()))
/*     */       {
/*  93 */         vecAssets.add(tempAssetInList);
/*     */       }
/*     */     }
/*     */ 
/*  97 */     if (vecAssets.size() > 1)
/*     */     {
/* 100 */       int iSize = vecAssets.size();
/* 101 */       this.m_assetRelationshipManager.relateAssets(null, vecAssets);
/*     */ 
/* 103 */       if (iSize == assetBox.getNumAssetsSelected())
/*     */       {
/* 106 */         asForm.setUpdateMessage(this.m_listManager.getListItem(a_dbTransaction, "relatedAssets", userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */       else
/*     */       {
/* 110 */         int iDifference = assetBox.getNumAssetsSelected() - iSize;
/* 111 */         String sMessage = this.m_listManager.getListItem(a_dbTransaction, "relatedAssetSubset1", userProfile.getCurrentLanguage()).getBody() + " " + iDifference + " ";
/*     */ 
/* 113 */         if (iDifference == 1)
/*     */         {
/* 115 */           sMessage = sMessage + this.m_listManager.getListItem(a_dbTransaction, "relatedAssetSubset2Singular", userProfile.getCurrentLanguage()).getBody();
/*     */         }
/*     */         else
/*     */         {
/* 119 */           sMessage = sMessage + this.m_listManager.getListItem(a_dbTransaction, "relatedAssetSubset2", userProfile.getCurrentLanguage()).getBody();
/*     */         }
/*     */ 
/* 122 */         asForm.setUpdateMessage(sMessage);
/*     */       }
/*     */ 
/* 126 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     else
/*     */     {
/* 130 */       asForm.addError(this.m_listManager.getListItem(a_dbTransaction, "relatedAssetNoPermission", userProfile.getCurrentLanguage()).getBody());
/* 131 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 134 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 140 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetRelationshipManager(AssetRelationshipManager a_assetRelationshipManager)
/*     */   {
/* 145 */     this.m_assetRelationshipManager = a_assetRelationshipManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 150 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.RelateAssetsInAssetBoxAction
 * JD-Core Version:    0.6.0
 */