/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.LightweightAsset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
            //import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.application.service.DirectLinkCacheManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.search.bean.SearchCriteria;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.bean.GroupCategoryPermission;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DirectLinkToImageAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  61 */   private static final String c_ksClassName = DirectLinkToImageAction.class.getSimpleName();
/*     */ 
/*  63 */   private AssetManager m_assetManager = null;
/*  64 */   private ABUserManager m_userManager = null;
/*  65 */   private DirectLinkCacheManager m_cacheManager = null;
/*  66 */   private AttributeManager m_attributeManager = null;
/*  67 */   private MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     long lAssetId = getLongParameter(a_request, "assetId");
/*  78 */     long lAttributeId = getLongParameter(a_request, "attributeId");
/*  79 */     String sAttributeValue = a_request.getParameter("attributeValue");
/*  80 */     int iWidth = getIntParameter(a_request, "width");
/*  81 */     int iHeight = getIntParameter(a_request, "height");
/*     */ 
/*  83 */     this.m_logger.debug(c_ksClassName + ".execute: Params: " + lAssetId + ":" + lAttributeId + ":" + sAttributeValue + ":" + iWidth + ":" + iHeight);
/*     */ 
/*  85 */     if ((iWidth <= 0) || (iHeight <= 0) || (iWidth > AssetBankSettings.getMaxImageDownloadDimension()) || (iHeight > AssetBankSettings.getMaxImageDownloadDimension()) || ((lAssetId <= 0L) && ((lAttributeId <= 0L) || (!StringUtil.stringIsPopulated(sAttributeValue)))))
/*     */     {
/*  88 */       this.m_logger.error(c_ksClassName + ": Invalid parameters passed");
/*  89 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  93 */     Group group = this.m_userManager.getGroup(AssetBankSettings.getDirectImageLinkPermissionGroup());
/*     */ 
/*  95 */     if (group == null)
/*     */     {
/*  97 */       this.m_logger.error(c_ksClassName + ": direct-image-link-permission-group setting not set to a valid group ID");
/*  98 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 101 */     this.m_logger.debug(c_ksClassName + ".execute: Group: " + group.getName());
/*     */ 
/* 103 */     if (lAttributeId > 0L)
/*     */     {
/* 106 */       Attribute attribute = this.m_attributeManager.getAttribute(a_dbTransaction, lAttributeId);
/*     */ 
/* 109 */       if ((attribute.getIsTextarea()) || (attribute.getIsTextareaLong()) || (attribute.getIsTextareaShort()) || (attribute.getIsTextfield()) || (attribute.getIsTextfieldLong()) || (attribute.getIsTextfieldShort()))
/*     */       {
/* 113 */         SearchCriteria criteria = AttributeUtil.getAttributeValueSearch(sAttributeValue, attribute);
/* 114 */         SearchResults results = this.m_searchManager.search(criteria, "en");
/*     */ 
/* 116 */         if ((results != null) && (results.getTotalHits() > 0))
/*     */         {
/* 119 */           for (int i = 0; i < results.getSearchResults().size(); i++)
/*     */           {
/* 121 */             LightweightAsset asset = (LightweightAsset)(LightweightAsset)results.getSearchResults().elementAt(i);
/* 122 */             if (!asset.getIsImage())
/*     */               continue;
/* 124 */             lAssetId = asset.getId();
/* 125 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 133 */         this.m_logger.error(c_ksClassName + ": Attribute search requested with non text attribute");
/* 134 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 139 */     if (lAssetId > 0L)
/*     */     {
/* 142 */       this.m_logger.debug(c_ksClassName + ".execute: About to retreive asset");
/* 143 */       LightweightAsset asset = null;
/*     */       try
/*     */       {
/* 146 */         asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, null, false, false);
/*     */       }
/*     */       catch (AssetNotFoundException e)
/*     */       {
/* 150 */         this.m_logger.error(c_ksClassName + ".execute: Error retrieving asset", e);
/*     */       }
/*     */ 
/* 153 */       if ((asset != null) && (asset.getIsImage()))
/*     */       {
/* 155 */         this.m_logger.debug(c_ksClassName + ".execute: Got asset");
/*     */ 
/* 158 */         Vector<GroupCategoryPermission> vecPerms = group.getCategoryPermissions();
/* 159 */         boolean bHasPermission = false;
/* 160 */         for (GroupCategoryPermission perm : vecPerms)
/*     */         {
/* 163 */           boolean bContainsCat = asset.getPermissionCategories().contains(perm.getCategory());
/* 164 */           if (!bContainsCat)
/*     */           {
/* 166 */             for (Category cat : asset.getPermissionCategories())
/*     */             {
/* 168 */               Category restrictiveCat = cat.getClosestRestrictiveAncestor();
/* 169 */               this.m_logger.debug(c_ksClassName + ".execute: Restrictive id: " + restrictiveCat.getId());
/*     */ 
/* 171 */               if (restrictiveCat.getId() == perm.getCategory().getId())
/*     */               {
/* 173 */                 bContainsCat = true;
/* 174 */                 break;
/*     */               }
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 180 */           if ((bContainsCat) && ((perm.getDownloadPermissionLevel() == 1) || (perm.getDownloadPermissionLevel() == 2)))
/*     */           {
/* 184 */             bHasPermission = true;
/*     */           }
/*     */         }
/*     */ 
/* 188 */         if (!bHasPermission)
/*     */         {
/* 190 */           this.m_logger.debug(c_ksClassName + ".execute: No permission");
/* 191 */           return a_mapping.findForward("Failure");
/*     */         }
/*     */ 
/* 195 */         String sPAR = a_request.getParameter("preserveAspectRatio");
/* 196 */         boolean bPreserveAspectRatio = true;
/* 197 */         if ((sPAR != null) && (sPAR.equals("false")))
/*     */         {
/* 199 */           bPreserveAspectRatio = false;
/*     */         }
/* 201 */         String sPath = this.m_cacheManager.getImageFromCache(a_dbTransaction, asset, iHeight, iWidth, bPreserveAspectRatio);
/*     */ 
/* 204 */         a_request.setAttribute("file", sPath);
/* 205 */         a_request.setAttribute("contentType", URLConnection.guessContentTypeFromName(sPath));
/*     */ 
/* 208 */         return a_mapping.findForward("Success");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 213 */     this.m_logger.error(c_ksClassName + ": Unable to retreive Asset with given parameters");
/* 214 */     return a_mapping.findForward("Failure");
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 219 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 224 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setDirectLinkCacheManager(DirectLinkCacheManager a_cacheManager)
/*     */   {
/* 229 */     this.m_cacheManager = a_cacheManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 234 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/* 239 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DirectLinkToImageAction
 * JD-Core Version:    0.6.0
 */