/*     */ package com.bright.assetbank.taxonomy.action;
/*     */ 
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.category.action.BrowseItemsAction;
/*     */ import com.bright.assetbank.taxonomy.constant.KeywordConstants;
/*     */ import com.bright.assetbank.taxonomy.form.KeywordChooserForm;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class BrowseByKeywordAction extends BrowseItemsAction
/*     */   implements CommonConstants, KeywordConstants
/*     */ {
/*  50 */   private TaxonomyManager m_taxManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  69 */     ActionForward afForward = super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  73 */     long lCategoryTreeId = getLongParameter(a_request, "categoryTypeId");
/*  74 */     KeywordChooserForm browseItemsForm = (KeywordChooserForm)a_form;
/*     */ 
/*  76 */     String sFilter = a_request.getParameter("filter");
/*  77 */     browseItemsForm.setFilter(sFilter);
/*  78 */     browseItemsForm.setCategoryTreeId(lCategoryTreeId);
/*     */ 
/*  81 */     if (!StringUtil.stringIsPopulated(sFilter))
/*     */     {
/*  83 */       sFilter = "a";
/*     */     }
/*  85 */     if (sFilter.compareToIgnoreCase("all") == 0)
/*     */     {
/*  87 */       sFilter = "";
/*     */     }
/*     */ 
/*  90 */     Vector vKeywords = this.m_taxManager.getKeywordsForFilter(a_dbTransaction, sFilter, lCategoryTreeId, false, true, userProfile.getCurrentLanguage());
/*  91 */     browseItemsForm.setKeywordList(vKeywords);
/*  92 */     browseItemsForm.setBrowserName(this.m_taxManager.getAttributeNameForKeywordTree(a_dbTransaction, String.valueOf(lCategoryTreeId)));
/*     */ 
/*  94 */     browseItemsForm.setRootCategoryName(this.m_listManager.getListItem("keyword-root", userProfile.getCurrentLanguage()).getBody());
/*     */ 
/*  96 */     return afForward;
/*     */   }
/*     */ 
/*     */   protected boolean setupPanels()
/*     */   {
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   public void setTaxonomyManager(TaxonomyManager a_taxManager)
/*     */   {
/* 106 */     this.m_taxManager = a_taxManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.taxonomy.action.BrowseByKeywordAction
 * JD-Core Version:    0.6.0
 */