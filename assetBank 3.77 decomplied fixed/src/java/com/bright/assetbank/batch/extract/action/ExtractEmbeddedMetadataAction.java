/*     */ package com.bright.assetbank.batch.extract.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.batch.extract.service.ManualExtractQueueManager;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ExtractEmbeddedMetadataAction extends Bn2Action
/*     */ {
/*  50 */   private ManualExtractQueueManager m_extractQueueManager = null;
/*     */ 
/*  56 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public void setManualExtractQueueManager(ManualExtractQueueManager a_sManager)
/*     */   {
/*  53 */     this.m_extractQueueManager = a_sManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_sSearchManager)
/*     */   {
/*  59 */     this.m_searchManager = a_sSearchManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  72 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  74 */       this.m_logger.debug("This user does not have permission to extract metadata");
/*  75 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  79 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  81 */     String sLowerDate = a_request.getParameter("lowerDate");
/*  82 */     String sUpperDate = a_request.getParameter("upperDate");
/*     */ 
/*  84 */     String sLowerId = a_request.getParameter("lowerId");
/*  85 */     String sUpperId = a_request.getParameter("upperId");
/*     */ 
/*  88 */     Vector vecAssetIds = new Vector();
/*     */ 
/*  90 */     if (lAssetId <= 0L)
/*     */     {
/*  92 */       if ((sLowerDate != null) && (sLowerDate.length() > 0) && (sUpperDate != null) && (sUpperDate.length() > 0))
/*     */       {
/*  94 */         DateFormat format = AssetBankSettings.getStandardDateFormat();
/*     */         try
/*     */         {
/*  98 */           SearchCriteria searchCriteria = new SearchCriteria();
/*  99 */           searchCriteria.setDateImageAddedLower(format.parse(sLowerDate));
/* 100 */           searchCriteria.setDateImageAddedUpper(format.parse(sUpperDate));
/*     */ 
/* 103 */           SearchResults searchResults = this.m_searchManager.search(searchCriteria);
/*     */ 
/* 105 */           this.m_logger.debug("Number of images in search = " + searchResults.getNumResults());
/*     */ 
/* 108 */           for (int i = 0; i < searchResults.getSearchResults().size(); i++)
/*     */           {
/* 110 */             LightweightAsset asset = (LightweightAsset)searchResults.getSearchResults().get(i);
/*     */ 
/* 112 */             vecAssetIds.add(new Long(asset.getId()));
/*     */           }
/*     */         }
/*     */         catch (ParseException e)
/*     */         {
/* 117 */           this.m_logger.error("ExtractEmbeddedMetadataAction: could not parse date range.");
/*     */         }
/*     */ 
/*     */       }
/* 123 */       else if ((sLowerId != null) && (sLowerId.length() > 0) && (sUpperId != null) && (sUpperId.length() > 0))
/*     */       {
/* 125 */         long lLower = Long.parseLong(sLowerId);
/* 126 */         long lUpper = Long.parseLong(sUpperId);
/*     */ 
/* 129 */         for (long lId = lLower; lId <= lUpper; lId += 1L)
/*     */         {
/* 131 */           vecAssetIds.add(new Long(lId));
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 138 */         SearchCriteria searchCriteria = new SearchCriteria();
/*     */ 
/* 140 */         SearchResults searchResults = this.m_searchManager.search(searchCriteria);
/*     */ 
/* 142 */         this.m_logger.debug("Number of images in search = " + searchResults.getNumResults());
/*     */ 
/* 145 */         for (int i = 0; i < searchResults.getSearchResults().size(); i++)
/*     */         {
/* 147 */           LightweightAsset asset = (LightweightAsset)searchResults.getSearchResults().get(i);
/*     */ 
/* 149 */           vecAssetIds.add(new Long(asset.getId()));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 157 */       vecAssetIds.add(new Long(lAssetId));
/*     */     }
/*     */ 
/* 160 */     this.m_extractQueueManager.queueExtractJob(userProfile.getUser().getId(), vecAssetIds);
/*     */ 
/* 162 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.extract.action.ExtractEmbeddedMetadataAction
 * JD-Core Version:    0.6.0
 */