/*     */ package com.bright.assetbank.search.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderClause;
/*     */ import com.bright.assetbank.search.bean.SearchBuilderQuery;
/*     */ import com.bright.assetbank.search.form.SearchBuilderForm;
/*     */ import com.bright.assetbank.search.util.SearchBuilderUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.ObjectUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewSearchBuilderAction extends ViewSearchAction
/*     */ {
/*  53 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  63 */     ActionForward forward = null;
/*  64 */     SearchBuilderForm form = (SearchBuilderForm)a_form;
/*  65 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  67 */     forward = super.execute(a_mapping, a_form, a_request, a_response, a_dbTransaction);
/*     */ 
/*  69 */     a_request.getSession().setAttribute("searchBuilder", String.valueOf(true));
/*  70 */     Vector vAttributes = null;
/*     */ 
/*  73 */     if ((form.getAssetAttributes() != null) && (form.getAssetAttributes().size() > 0))
/*     */     {
/*  75 */       vAttributes = (Vector)form.getAssetAttributes().clone();
/*     */ 
/*  78 */       for (int i = vAttributes.size() - 1; i >= 0; i--)
/*     */       {
/*  80 */         Attribute att = (Attribute)vAttributes.get(i);
/*  81 */         if ((att.getIsVisible()) && ((att.getStatic()) || (!StringUtils.isEmpty(att.getLabel()))) && (!ObjectUtils.equals(att.getFieldName(), "categories")) && (!ObjectUtils.equals(att.getFieldName(), "accessLevels")) && (!ObjectUtils.equals(att.getFieldName(), "file")) && ((!ObjectUtils.equals(att.getFieldName(), "price")) || (AssetBankSettings.ecommerce())))
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/*  89 */         vAttributes.remove(i);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  94 */     form.setSearchAttributes(vAttributes);
/*  95 */     boolean bAllHidden = true;
/*  96 */     int iRefineSearch = getIntParameter(a_request, "refineSearch");
/*  97 */     if ((iRefineSearch > 0) && ((userProfile.getSearchCriteria() instanceof SearchBuilderQuery)))
/*     */     {
/*  99 */       SearchBuilderQuery query = (SearchBuilderQuery)userProfile.getSearchCriteria();
/* 100 */       Vector vClauses = (Vector)query.getClauses().clone();
/* 101 */       Vector vNewClauses = new Vector();
/* 102 */       Vector vHiddenClauses = new Vector();
/*     */ 
/* 104 */       if (vClauses != null)
/*     */       {
/* 106 */         for (int i = 0; i < vClauses.size(); i++)
/*     */         {
/* 108 */           SearchBuilderClause temp = (SearchBuilderClause)vClauses.elementAt(i);
/*     */ 
/* 111 */           if ((!userProfile.getIsAdmin()) && ((temp.getAttributeId() == -2L) || (temp.getAttributeId() == -3L)))
/*     */           {
/*     */             continue;
/*     */           }
/* 115 */           if ((temp.getAttributeId() > 0L) && (form.clauseIsHidden(temp)))
/*     */           {
/* 117 */             temp.setIsHidden(true);
/* 118 */             vHiddenClauses.add(vClauses.elementAt(i));
/*     */           }
/*     */           else
/*     */           {
/* 122 */             vNewClauses.add(vClauses.elementAt(i));
/* 123 */             bAllHidden = false;
/*     */           }
/*     */         }
/*     */ 
/* 127 */         vHiddenClauses.addAll(vNewClauses);
/* 128 */         vClauses = vHiddenClauses;
/*     */       }
/*     */ 
/* 132 */       boolean bFirst = true;
/* 133 */       vClauses.ensureCapacity(10);
/* 134 */       for (int i = vClauses.size(); i < 10; i++)
/*     */       {
/* 136 */         SearchBuilderClause clause = new SearchBuilderClause();
/* 137 */         if ((bAllHidden) && (bFirst))
/*     */         {
/* 139 */           bFirst = false;
/* 140 */           clause.setVisible(true);
/*     */         }
/* 142 */         vClauses.add(clause);
/*     */       }
/* 144 */       form.setClauses(vClauses);
/*     */     }
/*     */ 
/* 147 */     form.setOperators(SearchBuilderUtil.getSearchBuilderOperators(this.m_listManager, userProfile.getCurrentLanguage()));
/*     */ 
/* 149 */     return forward;
/*     */   }
/*     */ 
/*     */   protected boolean shouldShowAttribute(Attribute attribute)
/*     */   {
/* 155 */     return attribute.isSearchBuilderField();
/*     */   }
/*     */ 
/*     */   protected Vector getSearchAttributes(DBTransaction a_dbTransaction, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 161 */     Vector vAttributes = null;
/*     */ 
/* 163 */     if (a_userProfile.getIsAdmin())
/*     */     {
/* 166 */       vAttributes = this.m_assetManager.getAssetAttributes(null, null, false, true);
/*     */     }
/*     */     else
/*     */     {
/* 171 */       vAttributes = this.m_assetManager.getAssetAttributes(null, a_userProfile.getVisibleAttributeIds(), false, true);
/*     */     }
/*     */ 
/* 174 */     if (((AssetBankSettings.getCanSearchAgreementsAdminOnly()) && (a_userProfile.getIsAdmin())) || (AssetBankSettings.getCanSearchAgreements()))
/*     */     {
/* 176 */       vAttributes.add(SearchBuilderUtil.getPseudoSearchAttribute(a_dbTransaction, this.m_listManager, a_userProfile, -1L));
/*     */     }
/*     */ 
/* 179 */     if ((AssetBankSettings.getAllowSearchByCompleteness()) && (a_userProfile.getCanUpdateAssets()))
/*     */     {
/* 181 */       vAttributes.add(SearchBuilderUtil.getPseudoSearchAttribute(a_dbTransaction, this.m_listManager, a_userProfile, -3L));
/*     */     }
/*     */ 
/* 184 */     if (a_userProfile.getIsAdmin())
/*     */     {
/* 186 */       vAttributes.add(SearchBuilderUtil.getPseudoSearchAttribute(a_dbTransaction, this.m_listManager, a_userProfile, -2L));
/*     */     }
/*     */ 
/* 190 */     if (!LanguageConstants.k_defaultLanguage.equals(a_userProfile.getCurrentLanguage()))
/*     */     {
/* 192 */       LanguageUtils.setLanguageOnAll(vAttributes, a_userProfile.getCurrentLanguage());
/*     */     }
/*     */ 
/* 196 */     AttributeUtil.sortAttributesByLabel(vAttributes);
/*     */ 
/* 198 */     return vAttributes;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 203 */     this.m_listManager = a_manager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.search.action.ViewSearchBuilderAction
 * JD-Core Version:    0.6.0
 */