/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import com.bright.framework.image.util.ImageUtil;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewFullSizedImageAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*  52 */   private IAssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/*  60 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  63 */     long lImageId = getLongParameter(a_request, "id");
/*     */ 
/*  66 */     int iSize = getIntParameter(a_request, "size");
/*     */ 
/*  69 */     if (iSize > AssetBankSettings.getMaxImageDownloadDimension())
/*     */     {
/*  71 */       iSize = AssetBankSettings.getMaxImageDownloadDimension();
/*     */     }
/*     */ 
/*  76 */     ImageAsset image = (ImageAsset)this.m_assetManager.getAsset(a_transaction, lImageId, null, false, false);
/*     */ 
/*  79 */     if (((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanViewAsset(userProfile, image))) || (!userProfile.getUserCanViewLargerSize()))
/*     */     {
/*  81 */       this.m_logger.debug("This user does not have permission to view image id=" + lImageId);
/*  82 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */     String sDownloadPath;
/*     */     // String sDownloadPath;
                //String sDownloadPath;
label264:
/*  89 */     if ((AssetBankSettings.getWatermarkFullSize()) || (!ImageUtil.isWebImageFile(image.getFileLocation())) || (iSize > 0))
/*     */     {
/*  93 */       int iLayerNo = getIntParameter(a_request, "layer");
/*     */ 
/*  96 */       if ((image.getNumPages() > 1) && (iLayerNo < 1))
/*     */       {
/*  98 */         iLayerNo = 1;
/*     */       }
/*     */ 
/* 102 */       if ((iLayerNo <= 1) && (image.getLargeImageFile() != null)) if (iSize != AssetBankSettings.getLargeImageSize()) { if ((image.getWidth() > image.getHeight() ? image.getWidth() : image.getHeight()) != iSize);
/*     */         }
/*     */         else
/*     */         {
/* 107 */            sDownloadPath = image.getLargeImageFile().getPath(); break label264;
/*     */         }
/*     */ 
/*     */ 
/* 112 */       sDownloadPath = this.m_assetManager.getTemporaryLargeFile(image, iSize, iLayerNo, false);
/*     */     }
/*     */     else
/*     */     {
/* 118 */       sDownloadPath = image.getFileLocation();
/*     */     }
/*     */     int iOrigHeight;
/*     */   //  int iOrigHeight;
/*     */     int iOrigWidth;
/* 127 */     if (image.getWidth() > image.getHeight())
/*     */     {
/*     */       //int iOrigHeight;
/* 129 */       if (image.getWidth() > iSize)
/*     */       {
/* 131 */         iOrigWidth = iSize;
/* 132 */         iOrigHeight = iSize * image.getHeight() / image.getWidth();
/*     */       }
/*     */       else
/*     */       {
/* 137 */         iOrigWidth = image.getWidth();
/* 138 */         iOrigHeight = image.getHeight();
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */      
/* 143 */       if (image.getHeight() > iSize)
/*     */       {
/* 145 */         iOrigHeight = iSize;
/* 146 */         iOrigWidth = iSize * image.getWidth() / image.getHeight();
/*     */       }
/*     */       else
/*     */       {
/* 151 */         iOrigHeight = image.getHeight();
/* 152 */         iOrigWidth = image.getWidth();
/*     */       }
/*     */     }
/*     */ 
/* 156 */     a_request.setAttribute("file", FileUtil.encryptFilepath(sDownloadPath));
/* 157 */     a_request.setAttribute("originalWidth", new Integer(iOrigWidth));
/* 158 */     a_request.setAttribute("originalHeight", new Integer(iOrigHeight));
/*     */ 
/* 160 */     if (image.getFormat().getCanConvertIndividualLayers())
/*     */     {
/* 162 */       a_request.setAttribute("numLayers", Integer.valueOf(image.getNumPages()));
/*     */     }
/*     */     else
/*     */     {
/* 166 */       a_request.setAttribute("numLayers", Integer.valueOf(1));
/*     */     }
/*     */ 
/* 170 */     return postExecute(a_mapping, a_form, a_request, a_response, a_transaction, image);
/*     */   }
/*     */ 
/*     */   protected ActionForward postExecute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction, ImageAsset a_image)
/*     */     throws Bn2Exception
/*     */   {
/* 187 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 193 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 199 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewFullSizedImageAction
 * JD-Core Version:    0.6.0
 */