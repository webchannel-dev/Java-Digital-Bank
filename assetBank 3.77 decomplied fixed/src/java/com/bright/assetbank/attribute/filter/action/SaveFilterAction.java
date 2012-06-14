/*     */ package com.bright.assetbank.attribute.filter.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.filter.bean.Filter;
/*     */ import com.bright.assetbank.attribute.filter.form.FilterForm;
/*     */ import com.bright.assetbank.attribute.filter.service.FilterManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveFilterAction extends BTransactionAction
/*     */   implements MessageConstants, AttributeConstants
/*     */ {
/*  66 */   private FilterManager m_filterManager = null;
/*  67 */   private AttributeManager m_attributeManager = null;
/*  68 */   protected ListManager m_listManager = null;
/*  69 */   protected CategoryCountCacheManager m_countManager = null;
/*  70 */   private AssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*     */     throws Bn2Exception
/*     */   {
/* 101 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 104 */     if (!userProfile.getIsAdmin())
/*     */     {
/* 106 */       this.m_logger.error("This user does not have permission to view the admin pages");
/* 107 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 110 */     Vector allAttributes = this.m_attributeManager.getAttributes(a_transaction, -1L);
/*     */ 
/* 114 */     FilterForm form = (FilterForm)a_form;
/*     */ 
/* 118 */     AttributeUtil.populateAttributeValuesFromRequest(a_request, form, form.getFilter().getAttributeValues(), allAttributes, false, true, true, a_transaction, null, this.m_listManager, LanguageConstants.k_defaultLanguage, true);
/*     */ 
/* 131 */     String sCatIds = "";
/* 132 */     String sAclIds = "";
/* 133 */     String sGroupIds = "";
/* 134 */     Vector vecCategories = new Vector();
/* 135 */     Enumeration e = a_request.getParameterNames();
/* 136 */     while (e.hasMoreElements())
/*     */     {
/* 138 */       String sName = (String)e.nextElement();
/* 139 */       if (sName.startsWith("cat"))
/*     */       {
/* 141 */         sCatIds = sCatIds + "," + a_request.getParameter(sName);
/*     */       }
/* 143 */       else if (sName.startsWith("al"))
/*     */       {
/* 145 */         sAclIds = sAclIds + "," + a_request.getParameter(sName);
/*     */       }
/* 147 */       else if (sName.startsWith("group"))
/*     */       {
/* 149 */         sGroupIds = sGroupIds + "," + a_request.getParameter(sName);
/*     */       }
/* 151 */       else if (sName.startsWith("linkedToCat"))
/*     */       {
/* 153 */         long lCat = getLongParameter(a_request, sName);
/* 154 */         vecCategories.add(new Long(lCat));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 159 */     if (StringUtil.stringIsPopulated(sCatIds))
/*     */     {
/* 161 */       sCatIds = sCatIds + ",";
/*     */     }
/*     */ 
/* 164 */     if (StringUtil.stringIsPopulated(sAclIds))
/*     */     {
/* 166 */       sAclIds = sAclIds + ",";
/*     */     }
/*     */ 
/* 169 */     form.getFilter().setAccessLevelIds(sAclIds);
/* 170 */     form.getFilter().setCategoryIds(sCatIds);
/* 171 */     form.getFilter().setLinkedToCategories(vecCategories);
/*     */ 
/* 175 */     if ((form.getFilter().getLinkedToCategories() != null) && (form.getFilter().getLinkedToCategories().size() > 0))
/*     */     {
/* 177 */       sGroupIds = "";
/*     */     }
/*     */ 
/* 181 */     form.getErrors().clear();
/* 182 */     form.validate(a_request, userProfile, a_transaction, this.m_listManager);
/*     */ 
/* 185 */     form.getFilter().setType(form.getType());
/*     */ 
/* 187 */     if (!form.getHasErrors())
/*     */     {
/* 191 */       this.m_filterManager.saveFilter(a_transaction, form.getFilter(), userProfile.getUser().getId());
/*     */ 
/* 193 */       long[] aGroups = StringUtil.getIdsArray(sGroupIds, ",");
/* 194 */       this.m_filterManager.updateFilterGroups(a_transaction, form.getFilter().getId(), aGroups);
/*     */ 
/* 197 */       if (userProfile.getSelectedFilters() != null)
/*     */       {
/* 199 */         for (int i = 0; i < userProfile.getSelectedFilters().size(); i++)
/*     */         {
/* 201 */           Filter filter = (Filter)userProfile.getSelectedFilters().elementAt(i);
/* 202 */           if (filter.getId() != form.getFilter().getId())
/*     */             continue;
/* 204 */           userProfile.getSelectedFilters().setElementAt(this.m_filterManager.getFilter(a_transaction, form.getFilter().getId()), i);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 210 */       this.m_countManager.invalidateCache();
/* 211 */       this.m_assetManager.clearAssetCaches();
/*     */ 
/* 213 */       return createRedirectingForward("type=" + form.getType(), a_mapping, "Success");
/*     */     }
/*     */ 
/* 216 */     a_request.setAttribute("filter", form.getFilter());
/*     */ 
/* 218 */     return a_mapping.findForward("Failure");
/*     */   }
/*     */ 
/*     */   public void setFilterManager(FilterManager a_filterManager)
/*     */   {
/* 223 */     this.m_filterManager = a_filterManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 228 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 233 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryCountCacheManager(CategoryCountCacheManager a_countManager)
/*     */   {
/* 238 */     this.m_countManager = a_countManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 243 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.filter.action.SaveFilterAction
 * JD-Core Version:    0.6.0
 */