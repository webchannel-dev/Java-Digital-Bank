/*     */ package com.bright.assetbank.repurposing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.assetbox.util.AssetBoxUtil;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.repurposing.bean.RepurposedSlideshow;
/*     */ import com.bright.assetbank.repurposing.form.RepurposeSlideshowForm;
/*     */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*     */ import com.bright.assetbank.repurposing.util.RepurposingUtil;
/*     */ import com.bright.assetbank.search.util.SearchUtil;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.search.bean.SearchQuery;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RepurposeSlideshowAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  57 */   private AssetRepurposingManager m_assetRepurposingManager = null;
/*  58 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(a_request.getSession());
/*  70 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  72 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  76 */     RepurposeSlideshowForm form = (RepurposeSlideshowForm)a_form;
/*  77 */     String sHeight = String.valueOf(form.getSlideshowHeight());
/*  78 */     String sWidth = String.valueOf(form.getSlideshowWidth());
/*  79 */     validateForm(a_dbTransaction, form, userProfile, sHeight, sWidth);
/*     */ 
/*  81 */     if (form.getHasErrors())
/*     */     {
/*  83 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/*  86 */     RepurposedSlideshow rs = populateSlideshow(a_request, form, userProfile, sHeight, sWidth);
/*     */ 
/*  89 */     rs = this.m_assetRepurposingManager.addRepurposedSlideshow(a_dbTransaction, rs, "jpg", a_request);
/*     */ 
/*  92 */     a_request.setAttribute("repurposedSlideshow", rs);
/*     */ 
/*  95 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   private void validateForm(DBTransaction a_dbTransaction, RepurposeSlideshowForm a_form, ABUserProfile a_userProfile, String a_sHeight, String a_sWidth)
/*     */     throws Bn2Exception
/*     */   {
/* 106 */     int iHeight = -1;
/* 107 */     int iWidth = -1;
/*     */     try
/*     */     {
/* 110 */       iHeight = Integer.parseInt(a_sHeight);
/* 111 */       iWidth = Integer.parseInt(a_sWidth);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 115 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationSSSizes", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 118 */     if ((iHeight <= 0) || (iWidth <= 0))
/*     */     {
/* 120 */       a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationSizesPositive", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 123 */     if (a_form.getDimensions().equals("percent"))
/*     */     {
/* 125 */       if ((iHeight > 100) || (iWidth > 100))
/*     */       {
/* 127 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationSizesPercentages", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */ 
/* 131 */     if (a_form.getConvertImages())
/*     */     {
/* 133 */       if ((a_form.getHeight() <= 0) || (a_form.getWidth() <= 0))
/*     */       {
/* 135 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationSizesPositiveNumber", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */ 
/* 139 */       if ((a_form.getJpegQuality() < 0) || (a_form.getJpegQuality() > 100))
/*     */       {
/* 141 */         a_form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationInvalidJpegQuality", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private RepurposedSlideshow populateSlideshow(HttpServletRequest a_request, RepurposeSlideshowForm a_form, ABUserProfile a_userProfile, String a_sHeight, String a_sWidth)
/*     */     throws Bn2Exception
/*     */   {
/* 156 */     if (a_form.getDimensions().equals("percent"))
/*     */     {
/* 158 */       a_sWidth = a_sWidth + "%";
/* 159 */       a_sHeight = a_sHeight + "%";
/*     */     }
/*     */ 
/* 163 */     ImageConversionInfo conversionInfo = null;
/* 164 */     float fJpgQuality = -1.0F;
/*     */ 
/* 166 */     if (a_form.getConvertImages())
/*     */     {
/* 168 */       fJpgQuality = a_form.getJpegQuality() / 100.0F;
/* 169 */       int iMaintainAspect = getIntParameter(a_request, "maintainAspectRatio");
/* 170 */       conversionInfo = RepurposingUtil.getSlideshowConversion(a_form.getWidth(), a_form.getHeight(), fJpgQuality, iMaintainAspect > 0);
/*     */     }
/*     */ 
/* 174 */     SearchQuery criteria = null;
/*     */ 
/* 177 */     Vector vecAssets = null;
/* 178 */     if (a_form.getType().equals("assetbox"))
/*     */     {
/* 180 */       String sGetAll = a_request.getParameter("fullList");
/* 181 */       vecAssets = AssetBoxUtil.getAssetsFromAssetBox(a_userProfile, sGetAll.equals("true"), 2L);
/*     */     }
/*     */     else
/*     */     {
/* 185 */       if (a_form.getType().equals("browse"))
/*     */       {
/* 187 */         criteria = a_userProfile.getBrowseCriteria();
/*     */       }
/* 189 */       else if (a_form.getType().equals("search"))
/*     */       {
/* 191 */         criteria = a_userProfile.getSearchCriteria();
/*     */       }
/* 193 */       criteria.setPageIndex(getIntParameter(a_request, "page"));
/* 194 */       criteria.setPageSize(getIntParameter(a_request, "pageSize"));
/* 195 */       int[] aTypes = { 2 };
/* 196 */       SearchUtil.addAssetTypeIds(criteria, aTypes);
/*     */ 
/* 198 */       vecAssets = SearchUtil.getAssetsFromSearchCriteria(criteria, a_userProfile.getCurrentLanguage().getCode());
/*     */     }
/*     */ 
/* 205 */     Vector selectedAttributes = AttributeUtil.getControlPanelSelectedAttributes(a_request);
/* 206 */     Vector selectedAttributesStrings = new Vector();
/* 207 */     Iterator attributesIterator = selectedAttributes.iterator();
/* 208 */     while (attributesIterator.hasNext())
/*     */     {
/* 210 */       selectedAttributesStrings.add(String.valueOf(attributesIterator.next()));
/*     */     }
/*     */ 
/* 214 */     int iRefresh = getIntParameter(a_request, "refresh");
/* 215 */     SearchQuery criteriaToWrite = null;
/* 216 */     if (iRefresh > 0)
/*     */     {
/* 218 */       criteriaToWrite = criteria;
/*     */     }
/*     */ 
/* 223 */     RepurposedSlideshow rs = new RepurposedSlideshow();
/* 224 */     rs.setCreatedByUser((ABUser)a_userProfile.getUser());
/* 225 */     rs.setCreatedDate(new GregorianCalendar().getTime());
/* 226 */     rs.setUrl(" ");
/* 227 */     rs.setHeight(a_sHeight);
/* 228 */     rs.setWidth(a_sWidth);
/* 229 */     rs.setDisplayTime(a_form.getDisplayTime());
/* 230 */     rs.setSearchCriteria(criteriaToWrite);
/* 231 */     rs.setMaintainAspectRatio(a_form.getMaintainAspectRatio());
/* 232 */     rs.setIncludeLabels(a_form.getLabels());
/* 233 */     rs.setCaptionIds(selectedAttributesStrings);
/* 234 */     rs.setIntJpgConversionQuality(a_form.getJpegQuality());
/* 235 */     rs.setLanguageCode(a_userProfile.getCurrentLanguage().getCode());
/* 236 */     rs.setConversionInfo(conversionInfo);
/* 237 */     rs.setImageWidth(a_form.getWidth());
/* 238 */     rs.setImageHeight(a_form.getHeight());
/* 239 */     rs.setDescription(a_form.getDescription());
/* 240 */     rs.setAssets(vecAssets);
/* 241 */     rs.setDisplayTypeId(a_form.getDisplayType());
/* 242 */     rs.setShowInListOnHomepage(a_form.getShowInListOnHomepage());
/* 243 */     rs.setDefaultOnHomepage(a_form.getDefaultOnHomepage());
/* 244 */     rs.setCaptionAttributeId(a_form.getCaptionAttributeId());
/* 245 */     rs.setCreditAttributeId(a_form.getCreditAttributeId());
/*     */ 
/* 247 */     return rs;
/*     */   }
/*     */ 
/*     */   public void setAssetRepurposingManager(AssetRepurposingManager a_assetRepurposingManager)
/*     */   {
/* 253 */     this.m_assetRepurposingManager = a_assetRepurposingManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 258 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.RepurposeSlideshowAction
 * JD-Core Version:    0.6.0
 */