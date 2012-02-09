/*     */ package com.bright.assetbank.thumbnails.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.constant.GeneratedImageVersion;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.thumbnails.service.ThumbnailGenerationManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.DateFormat;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.BooleanUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RegenerateImageAction extends Bn2Action
/*     */ {
/*  56 */   protected MultiLanguageSearchManager m_searchManager = null;
/*  57 */   protected ThumbnailGenerationManager m_thumbnailGenerationManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  84 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  87 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  89 */       this.m_logger.debug("This user does not have permission to view the admin pages");
/*  90 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  95 */       long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  97 */       String sLowerDate = a_request.getParameter("lowerDate");
/*  98 */       String sUpperDate = a_request.getParameter("upperDate");
/*     */ 
/* 100 */       String sLowerId = a_request.getParameter("lowerId");
/* 101 */       String sUpperId = a_request.getParameter("upperId");
/*     */ 
/* 103 */       String skipVideoThumbnailsParameter = a_request.getParameter("skipVideoThumbnails");
/*     */ 
/* 106 */       boolean skipVideoThumbnails = true;
/* 107 */       if ((skipVideoThumbnailsParameter != null) && ((skipVideoThumbnailsParameter.equals("false")) || (skipVideoThumbnailsParameter.equals("0")))) {
/* 108 */         skipVideoThumbnails = false;
/*     */       }
/*     */ 
/* 113 */       Set versionsToGenerate = new HashSet();
/* 114 */       if (BooleanUtils.toBoolean(a_request.getParameter("thumbnail")))
/*     */       {
/* 116 */         versionsToGenerate.add(GeneratedImageVersion.THUMBNAIL);
/*     */       }
/* 118 */       if ((BooleanUtils.toBoolean(a_request.getParameter("small"))) || (BooleanUtils.toBoolean(a_request.getParameter("homogenized"))) || (BooleanUtils.toBoolean(a_request.getParameter("homogenised"))))
/*     */       {
/* 122 */         versionsToGenerate.add(GeneratedImageVersion.HOMOGENIZED);
/*     */       }
/* 124 */       if ((BooleanUtils.toBoolean(a_request.getParameter("medium"))) || (BooleanUtils.toBoolean(a_request.getParameter("preview"))))
/*     */       {
/* 127 */         versionsToGenerate.add(GeneratedImageVersion.PREVIEW);
/*     */       }
/* 129 */       if (BooleanUtils.toBoolean(a_request.getParameter("featured")))
/*     */       {
/* 131 */         versionsToGenerate.add(GeneratedImageVersion.FEATURED);
/*     */       }
/* 133 */       if ((BooleanUtils.toBoolean(a_request.getParameter("large"))) || (BooleanUtils.toBoolean(a_request.getParameter("watermarked"))))
/*     */       {
/* 136 */         versionsToGenerate.add(GeneratedImageVersion.LARGE_WATERMARKED);
/*     */       }
/* 138 */       if (BooleanUtils.toBoolean(a_request.getParameter("unwatermarked")))
/*     */       {
/* 140 */         versionsToGenerate.add(GeneratedImageVersion.LARGE_UNWATERMARKED);
/*     */       }
/* 142 */       if (versionsToGenerate.isEmpty())
/*     */       {
/* 144 */         versionsToGenerate = null;
/*     */       }
/*     */ 
/* 147 */       if (lAssetId <= 0L)
/*     */       {
/* 149 */         if ((sLowerDate != null) && (sLowerDate.length() > 0) && (sUpperDate != null) && (sUpperDate.length() > 0))
/*     */         {
/* 151 */           DateFormat format = AssetBankSettings.getStandardDateFormat();
/*     */ 
/* 153 */           SearchCriteria searchCriteria = new SearchCriteria();
/* 154 */           searchCriteria.setDateImageAddedLower(format.parse(sLowerDate));
/* 155 */           searchCriteria.setDateImageAddedUpper(format.parse(sUpperDate));
/*     */ 
/* 158 */           SearchResults searchResults = this.m_searchManager.search(searchCriteria);
/*     */ 
/* 160 */           this.m_logger.error("Number of images in search = " + searchResults.getNumResults());
/*     */ 
/* 163 */           for (int i = 0; i < searchResults.getSearchResults().size(); i++)
/*     */           {
/* 165 */             LightweightAsset asset = (LightweightAsset)searchResults.getSearchResults().get(i);
/*     */ 
/* 167 */             this.m_thumbnailGenerationManager.regenerateAssetThumbnails(asset.getId(), versionsToGenerate, skipVideoThumbnails);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 173 */         if ((sLowerId != null) && (sLowerId.length() > 0) && (sUpperId != null) && (sUpperId.length() > 0))
/*     */         {
/* 175 */           long lLower = Long.parseLong(sLowerId);
/* 176 */           long lUpper = Long.parseLong(sUpperId);
/*     */ 
/* 179 */           for (lAssetId = lLower; lAssetId <= lUpper; lAssetId += 1L)
/*     */           {
/*     */             try
/*     */             {
/* 184 */               this.m_thumbnailGenerationManager.regenerateAssetThumbnails(lAssetId, versionsToGenerate, skipVideoThumbnails);
/*     */             }
/*     */             catch (Exception anf)
/*     */             {
/* 188 */               this.m_logger.error("RegenerateImageAction: Unable to regenerate asset with id: " + lAssetId, anf);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 196 */         this.m_thumbnailGenerationManager.regenerateAssetThumbnails(lAssetId, versionsToGenerate, skipVideoThumbnails);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 201 */       this.m_logger.error("Exception in RegenerateImageAction: " + e.getMessage(), e);
/*     */     }
/*     */ 
/* 204 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_sSearchManager)
/*     */   {
/* 209 */     this.m_searchManager = a_sSearchManager;
/*     */   }
/*     */ 
/*     */   public void setThumbnailGenerationManager(ThumbnailGenerationManager a_thumbnailGenerationManager)
/*     */   {
/* 214 */     this.m_thumbnailGenerationManager = a_thumbnailGenerationManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.thumbnails.action.RegenerateImageAction
 * JD-Core Version:    0.6.0
 */