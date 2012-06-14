/*     */ package com.bright.assetbank.approval.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.approval.bean.AssetApproval;
/*     */ import com.bright.assetbank.approval.bean.AssetApprovalSearchCriteria;
/*     */ import com.bright.assetbank.approval.constant.AssetApprovalConstants;
/*     */ import com.bright.assetbank.approval.form.AssetApprovalListForm;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewApprovedAssetsAction extends BTransactionAction
/*     */   implements AssetApprovalConstants, FrameworkConstants
/*     */ {
/*  57 */   private AssetApprovalManager m_approvalManager = null;
/*     */ 
/*  63 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/*  60 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  66 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  89 */     ActionForward afForward = null;
/*  90 */     AssetApprovalListForm form = (AssetApprovalListForm)a_form;
/*  91 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  94 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  96 */       this.m_logger.error("ViewApprovedAssetsAction.execute : User must be logged in to view approved assets.");
/*  97 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 101 */     if (!StringUtil.stringIsPopulated(form.getStartDateString()))
/*     */     {
/* 104 */       Calendar calendar = Calendar.getInstance();
/* 105 */       calendar.add(2, 0 - AssetBankSettings.getNumberMonthsApprovedItemsDefault());
/* 106 */       BrightDate dt = new BrightDate();
/* 107 */       dt.setDate(calendar.getTime());
/* 108 */       form.setStartDateString(dt.getFormDate());
/*     */     }
/* 110 */     if (!StringUtil.stringIsPopulated(form.getEndDateString()))
/*     */     {
/* 113 */       Calendar calendar = Calendar.getInstance();
/* 114 */       BrightDate dt = new BrightDate();
/* 115 */       dt.setDate(calendar.getTime());
/* 116 */       form.setEndDateString(dt.getFormDate());
/*     */     }
/*     */ 
/* 120 */     form.validate(a_request, userProfile, a_dbTransaction, this.m_listManager);
/*     */ 
/* 122 */     if (!form.getHasErrors())
/*     */     {
/* 124 */       form.processReportDates();
/* 125 */       Date dtStartDate = form.getStartDate();
/* 126 */       Date dtEndDate = form.getEndDate();
/*     */ 
/* 129 */       long lUserId = userProfile.getUser().getId();
/* 130 */       AssetApprovalSearchCriteria search = new AssetApprovalSearchCriteria();
/* 131 */       search.setUserId(lUserId);
/* 132 */       search.setApprovalStatusId(2L);
/* 133 */       search.setDateStart(dtStartDate);
/* 134 */       search.setDateEnd(dtEndDate);
/*     */ 
/* 136 */       Vector vec = this.m_approvalManager.getAssetApprovalList(a_dbTransaction, search, userProfile.getCurrentLanguage());
/*     */ 
/* 139 */       AssetBox assetBox = userProfile.getAssetBox();
/* 140 */       for (int i = 0; i < vec.size(); i++)
/*     */       {
/* 142 */         AssetApproval approval = (AssetApproval)vec.get(i);
/*     */ 
/* 144 */         if (!assetBox.containsAsset(approval.getAsset().getId()))
/*     */           continue;
/* 146 */         approval.getAsset().setInAssetBox(true);
/*     */       }
/*     */ 
/* 149 */       form.setApprovalList(vec);
/*     */     }
/*     */ 
/* 154 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 156 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.action.ViewApprovedAssetsAction
 * JD-Core Version:    0.6.0
 */