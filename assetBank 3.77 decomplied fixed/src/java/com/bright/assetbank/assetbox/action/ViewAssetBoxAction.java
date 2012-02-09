/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.AssetBoxNotFoundException;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBasket;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.form.AssetBoxForm;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.exception.NoPermissionException;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.CollectionUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAssetBoxAction extends BTransactionAction
/*     */   implements AssetBoxConstants, FrameworkConstants
/*     */ {
/*  70 */   private AssetBoxManager m_assetBoxManager = null;
/*  71 */   private ListManager m_listManager = null;
/*  72 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  81 */     ActionForward afForward = null;
/*  82 */     AssetBoxForm form = (AssetBoxForm)a_form;
/*  83 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  84 */     long lAssetBoxId = getLongParameter(a_request, "assetBoxId");
/*     */ 
/*  87 */     if (StringUtil.stringIsPopulated(a_request.getParameter("error")))
/*     */     {
/*  89 */       form.addError(a_request.getParameter("error"));
/*     */     }
/*     */ 
/*  92 */     if (lAssetBoxId <= 0L)
/*     */     {
/*  94 */       lAssetBoxId = form.getCurrentAssetBoxId();
/*     */     }
/*     */ 
/*  97 */     if (lAssetBoxId <= 0L)
/*     */     {
/*  99 */       lAssetBoxId = userProfile.getAssetBox().getId();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 104 */       this.m_assetBoxManager.refreshAssetBoxInProfileOrFail(a_dbTransaction, userProfile, lAssetBoxId);
/*     */     }
/*     */     catch (AssetBoxNotFoundException e)
/*     */     {
/* 109 */       this.m_assetBoxManager.resetAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */ 
/* 111 */       String messageKey = "assetBoxRemoved";
/* 112 */       if ((form.getCurrentAssetBoxId() > 0L) && (form.getCurrentAssetBoxId() != userProfile.getAssetBox().getId()))
/*     */       {
/* 114 */         messageKey = "assetBoxNotAvailable";
/*     */       }
/*     */ 
/* 118 */       ListItem listItem = this.m_listManager.getListItem(a_dbTransaction, "a-lightbox", userProfile.getCurrentLanguage());
/* 119 */       a_request.setAttribute("assetBoxErrorMessage", this.m_listManager.getListItem(a_dbTransaction, messageKey, userProfile.getCurrentLanguage(), new String[] { listItem.getBody() }));
/*     */     }
/*     */     catch (NoPermissionException e)
/*     */     {
/* 124 */       if ((!userProfile.getIsLoggedIn()) && (getLongParameter(a_request, "assetBoxId") > 0L))
/*     */       {
/* 126 */         afForward = createRedirectingForward("redirecturl=viewAssetBox?assetBoxId=" + lAssetBoxId, a_mapping, "NoPermission");
/* 127 */         return afForward;
/*     */       }
/*     */     }
/*     */ 
/* 131 */     form.setCurrentAssetBoxId(userProfile.getAssetBox().getId());
/*     */ 
/* 134 */     AssetBox assetBox = userProfile.getAssetBox();
/* 135 */     form.setListCanDownload(assetBox.getAssetsInState(1));
/* 136 */     form.setListApprovalApproved(assetBox.getAssetsInState(2));
/* 137 */     form.setListApprovalRequired(assetBox.getAssetsInState(3));
/* 138 */     form.setListApprovalRejected(assetBox.getAssetsInState(5));
/* 139 */     form.setListApprovalPending(assetBox.getAssetsInState(4));
/* 140 */     form.setListViewOnly(assetBox.getAssetsInState(6));
/*     */ 
/* 143 */     if (AssetBankSettings.ecommerce())
/*     */     {
/* 145 */       AssetBasket basket = userProfile.getBasket();
/* 146 */       form.setListWithPriceBands(basket.getAssetsWithPriceBands());
/* 147 */       form.setListPurchaseRequiredWithoutPriceBands(basket.getAssetsRequiringPurchaseWithoutPriceBands());
/*     */     }
/*     */ 
/* 151 */     Vector vSortAtts = this.m_attributeManager.getLightboxUserSortAttributes(a_dbTransaction, userProfile.getIsAdmin() ? null : userProfile.getVisibleAttributeIds());
/* 152 */     LanguageUtils.setLanguageOnAll(vSortAtts, userProfile.getCurrentLanguage());
/* 153 */     AttributeUtil.sortAttributesByLabel(vSortAtts);
/* 154 */     ArrayList alAttributes = filterSortableAttributes(vSortAtts);
/* 155 */     form.setSortingAttributes(alAttributes);
/*     */ 
/* 157 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 159 */     return afForward;
/*     */   }
/*     */ 
/*     */   private ArrayList<Attribute> filterSortableAttributes(Vector<Attribute> a_vecAttributes)
/*     */   {
/* 164 */     ArrayList alSortAttributes = null;
/* 165 */     if ((a_vecAttributes != null) && (a_vecAttributes.size() > 0))
/*     */     {
/* 167 */       alSortAttributes = new ArrayList();
/* 168 */       for (Attribute attribute : a_vecAttributes)
/*     */       {
/* 170 */         if ((CollectionUtil.longArrayContains(AssetBoxConstants.k_sValidSortingAttributeTypes, attribute.getTypeId())) || (CollectionUtil.stringArrayContains(AssetBoxConstants.k_sValidSortingStaticAttributeNames, attribute.getFieldName())))
/*     */         {
/* 174 */           alSortAttributes.add(attribute);
/*     */         }
/*     */       }
/*     */     }
/* 178 */     return alSortAttributes;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/* 184 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_manager)
/*     */   {
/* 190 */     this.m_listManager = a_manager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 195 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ViewAssetBoxAction
 * JD-Core Version:    0.6.0
 */