/*     */ package com.bright.assetbank.presentation.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.presentation.constant.PresentationMergeConstants;
/*     */ import com.bright.assetbank.presentation.form.MergeAssetForm;
/*     */ import com.bright.assetbank.presentation.service.PresentationManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewMergeAssetBoxAction extends Bn2Action
/*     */   implements PresentationMergeConstants, AssetBankConstants
/*     */ {
/*  51 */   private PresentationManager m_presentationManager = null;
/*     */ 
/*     */   public void setPresentationManager(PresentationManager a_presentationManager) {
/*  54 */     this.m_presentationManager = a_presentationManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  64 */     ActionForward afForward = null;
/*  65 */     MergeAssetForm form = (MergeAssetForm)a_form;
/*  66 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     AssetBox assetBox = userProfile.getAssetBox();
/*  74 */     Collection colAssetBox = null;
/*  75 */     colAssetBox = assetBox.getAssets();
/*  76 */     Iterator it = colAssetBox.iterator();
/*     */ 
/*  78 */     int iSize = colAssetBox.size();
/*  79 */     AssetInList[] aAssets = new AssetInList[iSize];
/*  80 */     while (it.hasNext())
/*     */     {
/*  82 */       AssetInList ail = (AssetInList)it.next();
/*  83 */       long lAssetId = ail.getAsset().getId();
/*     */ 
/*  85 */       String sCheckbox = "inmerge_" + lAssetId;
/*  86 */       int iSequenceId = getIntParameter(a_request, sCheckbox);
/*  87 */       if (iSequenceId >= 0)
/*     */       {
/*  89 */         aAssets[iSequenceId] = ail;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  95 */     for (int i = 0; i < iSize; i++)
/*     */     {
/*  97 */       if (aAssets[i] == null)
/*     */       {
/*     */         continue;
/*     */       }
/* 101 */       String sMoveUp = "moveup_" + aAssets[i].getAsset().getId();
/* 102 */       int iMoveUp = getIntParameter(a_request, sMoveUp);
/* 103 */       if (iMoveUp == 1)
/*     */       {
/* 106 */         for (int j = i - 1; j >= 0; j--)
/*     */         {
/* 108 */           AssetInList temp = aAssets[j];
/* 109 */           if (temp == null) {
/*     */             continue;
/*     */           }
/* 112 */           aAssets[j] = aAssets[i];
/* 113 */           aAssets[i] = temp;
/* 114 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 120 */       String sMoveDown = "movedown_" + aAssets[i].getAsset().getId();
/* 121 */       int iMoveDown = getIntParameter(a_request, sMoveDown);
/* 122 */       if (iMoveDown != 1) {
/*     */         continue;
/*     */       }
/* 125 */       for (int j = i + 1; j < iSize; j++)
/*     */       {
/* 127 */         AssetInList temp = aAssets[j];
/* 128 */         if (temp == null) {
/*     */           continue;
/*     */         }
/* 131 */         aAssets[j] = aAssets[i];
/* 132 */         aAssets[i] = temp;
/* 133 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 141 */     form.setAssetList(aAssets);
/*     */ 
/* 145 */     String sDownload = a_request.getParameter("download");
/* 146 */     if ((sDownload != null) && (sDownload.length() > 0))
/*     */     {
/* 148 */       String sFileLocation = this.m_presentationManager.mergeAssets(aAssets);
/*     */ 
/* 151 */       a_request.setAttribute("downloadFile", FileUtil.encryptFilepath(sFileLocation));
/* 152 */       a_request.setAttribute("downloadFilename", AssetBankSettings.getAssetBoxFilename() + ".ppt");
/* 153 */       a_request.setAttribute("deleteFileAfterUse", Boolean.TRUE);
/* 154 */       a_request.setAttribute("downloadFaliureUrl", a_mapping.findForward("DownloadFailure").getPath());
/*     */ 
/* 157 */       afForward = a_mapping.findForward("Download");
/*     */     }
/*     */     else
/*     */     {
/* 162 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */ 
/* 165 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.presentation.action.ViewMergeAssetBoxAction
 * JD-Core Version:    0.6.0
 */