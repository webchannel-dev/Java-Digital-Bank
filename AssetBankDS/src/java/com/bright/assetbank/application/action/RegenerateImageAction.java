/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.bean.AudioAsset;
/*     */ import com.bright.assetbank.application.bean.ImageAsset;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.bean.VideoAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.service.AudioAssetManagerImpl;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.application.service.ImageAssetManagerImpl;
/*     */ import com.bright.assetbank.application.service.VideoAssetManagerImpl;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RegenerateImageAction extends Bn2Action
/*     */ {
/*  62 */   private IAssetManager m_assetManager = null;
/*  63 */   private ImageAssetManagerImpl m_imageAssetManager = null;
/*  64 */   private VideoAssetManagerImpl m_videoAssetManager = null;
/*  65 */   private AudioAssetManagerImpl m_audioAssetManager = null;
/*  66 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  94 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  96 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  97 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 102 */       long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/* 104 */       String sLowerDate = a_request.getParameter("lowerDate");
/* 105 */       String sUpperDate = a_request.getParameter("upperDate");
/*     */ 
/* 107 */       String sLowerId = a_request.getParameter("lowerId");
/* 108 */       String sUpperId = a_request.getParameter("upperId");
/*     */ 
/* 110 */       if (lAssetId <= 0L)
/*     */       {
/* 112 */         if ((sLowerDate != null) && (sLowerDate.length() > 0) && (sUpperDate != null) && (sUpperDate.length() > 0))
/*     */         {
/* 114 */           DateFormat format = AssetBankSettings.getStandardDateFormat();
/*     */ 
/* 116 */           SearchCriteria searchCriteria = new SearchCriteria();
/* 117 */           searchCriteria.setDateImageAddedLower(format.parse(sLowerDate));
/* 118 */           searchCriteria.setDateImageAddedUpper(format.parse(sUpperDate));
/*     */           SearchResults searchResults = this.m_searchManager.search(searchCriteria,"en");
/* 121 */          // SearchResults searchResults = this.m_searchManager.search(searchCriteria, 
/* 122 */          //   -1, 
/* 123 */          //   -1, 
/* 124 */          //   "en");
/*     */ 
/* 126 */           this.m_logger.error("Number of images in search = " + searchResults.getTotalHits());
/*     */ 
/* 129 */           for (int i = 0; i < searchResults.getSearchResults().size(); i++)
/*     */           {
/* 131 */             LightweightAsset asset = (LightweightAsset)searchResults.getSearchResults().get(i);
/*     */ 
/* 134 */             regenerateAsset(asset.getId());
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 141 */         if ((sLowerId != null) && (sLowerId.length() > 0) && (sUpperId != null) && (sUpperId.length() > 0))
/*     */         {
/* 143 */           long lLower = Long.parseLong(sLowerId);
/* 144 */           long lUpper = Long.parseLong(sUpperId);
/*     */ 
/* 147 */           for (lAssetId = lLower; lAssetId <= lUpper; lAssetId += 1L)
/*     */           {
/*     */             try
/*     */             {
/* 153 */               regenerateAsset(lAssetId);
/*     */             }
/*     */             catch (AssetNotFoundException localAssetNotFoundException)
/*     */             {
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 166 */         regenerateAsset(lAssetId);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 171 */       this.m_logger.error("Exception in RegenerateImageAction: " + e.getMessage());
/*     */     }
/*     */ 
/* 174 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   private void regenerateAsset(long a_lId)
/*     */     throws Bn2Exception
/*     */   {
/* 181 */     Asset asset = this.m_assetManager.getAsset(null, 
/* 182 */       a_lId, 
/* 183 */       null, false, false);
/*     */ 
/* 185 */     if ((asset instanceof AudioAsset))
/*     */     {
/* 187 */       AudioAsset audioAsset = (AudioAsset)asset;
/*     */ 
/* 189 */       this.m_audioAssetManager.saveAudio(null, 
/* 190 */         audioAsset, 
/* 191 */         true, 
/* 192 */         false);
/*     */     }
/* 194 */     if ((asset instanceof VideoAsset))
/*     */     {
/* 196 */       VideoAsset videoAsset = (VideoAsset)asset;
/*     */ 
/* 199 */     // this.m_imageAssetManager.createThumbnails(videoAsset, 
/* 200 */      //   null, 
/* 201 */     //    null);
/*     */ 
/* 203 */     //  this.m_videoAssetManager.saveVideo(null, 
/* 204 */      //   videoAsset, 
/* 205 */      //   true, 
/* 206 */      //   false);
/*     */     }
/* 208 */     else if ((asset instanceof ImageAsset))
/*     */     {
/* 210 */       ImageAsset imageAsset = (ImageAsset)asset;
/*     */ 
/* 212 */       boolean bSaveLarge = false;
/* 213 */       if ((imageAsset.getLargeImageFile() == null) && 
/* 214 */         (AssetBankSettings.getCacheLargeImage()) && 
/* 215 */         (imageAsset.getNumPages() <= 1))
/*     */       {
/* 217 */         bSaveLarge = true;
/*     */       }
/*     */ 
/* 220 */      // this.m_imageAssetManager.createThumbnails(imageAsset, 
/* 221 */      //   null, 
/* 222 */      //   null);
/*     */ 
/* 224 */       if (bSaveLarge)
/*     */       {
/* 226 */         AssetFileSource thumbnailSource = new AssetFileSource();
/* 227 */         thumbnailSource.setRegenerate(true);
/* 228 */        // this.m_imageAssetManager.saveAsset(null, 
/* 229 */        //   imageAsset, 
/* 230 */        //   null, 
/* 231 */        //   imageAsset.getLastModifiedByUser().getId(), 
/* 232 */        //   null, 
/* 233 */        //   thumbnailSource, false);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 242 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_sSearchManager)
/*     */   {
/* 248 */     this.m_searchManager = a_sSearchManager;
/*     */   }
/*     */ 
/*     */   public void setImageAssetManager(ImageAssetManagerImpl a_sImageAssetManager)
/*     */   {
/* 254 */     this.m_imageAssetManager = a_sImageAssetManager;
/*     */   }
/*     */ 
/*     */   public void setVideoAssetManager(VideoAssetManagerImpl a_sAssetManager)
/*     */   {
/* 259 */     this.m_videoAssetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAudioAssetManager(AudioAssetManagerImpl a_sAssetManager)
/*     */   {
/* 264 */     this.m_audioAssetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.RegenerateImageAction
 * JD-Core Version:    0.6.0
 */