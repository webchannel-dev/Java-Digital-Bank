/*     */ package com.bright.assetbank.autocomplete.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.autocomplete.bean.AutoCompleteType;
/*     */ import com.bright.assetbank.autocomplete.constant.ACWebConstants;
/*     */ import com.bright.assetbank.autocomplete.service.AutoCompleteQueryManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.util.ResponseUtils;
/*     */ 
/*     */ public class AutoCompleteAction extends Bn2Action
/*     */   implements AssetBankConstants, ACWebConstants
/*     */ {
/*     */   private AutoCompleteQueryManager m_autoCompleteQueryManager;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  61 */     int attributeId = getIntParameter(a_request, "attributeId");
/*  62 */     String sPrefix = a_request.getParameter("prefix");
/*     */ 
/*  64 */     String sLangCode = a_request.getParameter("languageCode");
/*  65 */     if (StringUtils.isEmpty(sLangCode))
/*     */     {
/*  67 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  68 */       sLangCode = userProfile.getCurrentLanguage().getCode();
/*     */     }
/*     */ 
/*  72 */     String sCompleteType = a_request.getParameter("completeType");
/*     */     AutoCompleteType completeType;
/*     */    // AutoCompleteType completeType;
/*  73 */     if ((StringUtils.isNotEmpty(sCompleteType)) && (sCompleteType.equals("edit")))
/*     */     {
/*  75 */       completeType = AutoCompleteType.EDIT;
/*     */     }
/*     */     else
/*     */     {
/*  79 */       completeType = AutoCompleteType.SEARCH;
/*     */     }
/*     */ 
/*  82 */     int maxResults = AssetBankSettings.getAutoCompleteMaxResults();
/*     */     List keywords;
/*     */     //List keywords;
/*  85 */     if (attributeId == -1)
/*     */     {
/*  87 */       keywords = this.m_autoCompleteQueryManager.findKeywordsByPrefix(completeType, sLangCode, sPrefix, maxResults);
/*     */     }
/*     */     else
/*     */     {
/*  91 */       keywords = this.m_autoCompleteQueryManager.findKeywordsByPrefix(completeType, attributeId, sLangCode, sPrefix, maxResults);
/*     */     }
/*     */ 
/*  97 */     String sFilteredPrefix = ResponseUtils.filter(sPrefix);
/*  98 */     Pattern filteredPrefixPattern = Pattern.compile("^" + Pattern.quote(sFilteredPrefix), 2);
/*     */ 
/* 100 */     for (ListIterator iterator = keywords.listIterator(); iterator.hasNext(); )
/*     */     {
/* 102 */       String sKeyword = (String)iterator.next();
/* 103 */       sKeyword = ResponseUtils.filter(sKeyword);
/*     */ 
/* 105 */       Matcher matcher = filteredPrefixPattern.matcher(sKeyword);
/* 106 */       String sHighlightedKeyword = matcher.replaceFirst("<strong>$0</strong>");
/*     */ 
/* 109 */       iterator.set(sHighlightedKeyword);
/*     */     }
/*     */ 
/* 112 */     a_request.setAttribute("keywords", keywords);
/*     */ 
/* 114 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setAutoCompleteQueryManager(AutoCompleteQueryManager a_autoCompleteQueryManager)
/*     */   {
/* 119 */     this.m_autoCompleteQueryManager = a_autoCompleteQueryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.action.AutoCompleteAction
 * JD-Core Version:    0.6.0
 */