/*     */ package com.bright.assetbank.agreements.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.agreements.bean.Agreement;
/*     */ import com.bright.assetbank.agreements.form.AgreementsForm;
/*     */ import com.bright.assetbank.agreements.service.AgreementsManager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetLogManager;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveAgreementAction extends BTransactionAction
/*     */ {
/*  55 */   protected ListManager m_listManager = null;
/*     */ 
/*  61 */   private AgreementsManager m_agreementsManager = null;
/*     */ 
/*  66 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  72 */   protected AssetLogManager m_assetLogManager = null;
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/*  58 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setAgreementsManager(AgreementsManager a_agreementsManager)
/*     */   {
/*  63 */     this.m_agreementsManager = a_agreementsManager;
/*     */   }
/*     */ 
/*     */   public void setOrgUnitManager(OrgUnitManager orgUnitManager)
/*     */   {
/*  69 */     this.m_orgUnitManager = orgUnitManager;
/*     */   }
/*     */ 
/*     */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*     */   {
/*  75 */     this.m_assetLogManager = a_assetLogManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  98 */     ActionForward afForward = null;
/*  99 */     AgreementsForm form = (AgreementsForm)a_form;
/* 100 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 101 */     boolean bValid = true;
/*     */ 
/* 104 */     boolean bPopup = false;
/* 105 */     if ((a_request.getParameter("usePopup") != null) && (Boolean.parseBoolean(a_request.getParameter("usePopup"))))
/*     */     {
/* 107 */       bPopup = true;
/*     */     }
/*     */ 
/* 110 */     Agreement a = form.getAgreement();
/* 111 */     long lAgreementId = a.getId();
/*     */ 
/* 114 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()) && (!userProfile.getCanUpdateAssets()))
/*     */     {
/* 116 */       this.m_logger.error("SaveAgreementAction.execute : User does not have permission.");
/* 117 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 121 */     a.setTitle(StringUtils.trim(a.getTitle()));
/*     */ 
/* 124 */     if ((!bPopup) && (userProfile.getIsAdmin()))
/*     */     {
/* 126 */       a.setIsAvailableToAll(true);
/*     */     }
/*     */ 
/* 129 */     if (!bPopup)
/*     */     {
/* 131 */       a.setIsSharedWithOU(true);
/*     */     }
/*     */ 
/* 134 */     long lExistingAgreementId = this.m_agreementsManager.getAgreementId(a_dbTransaction, a.getTitle());
/*     */ 
/* 137 */     if (((a.getIsAvailableToAll()) || (a.getIsSharedWithOU())) && (lExistingAgreementId > 0L) && ((lAgreementId <= 0L) || (lExistingAgreementId != lAgreementId)))
/*     */     {
/* 139 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "agreementNameInUse", userProfile.getCurrentLanguage()).getBody());
/* 140 */       bValid = false;
/*     */     }
/*     */ 
/* 143 */     BrightDate dateExpiry = new BrightDate();
/*     */ 
/* 145 */     dateExpiry.setFormDate(a.getExpiryString());
/* 146 */     boolean bDateValid = dateExpiry.processFormData();
/*     */ 
/* 148 */     if (bDateValid)
/*     */     {
/* 150 */       a.setExpiry(dateExpiry);
/*     */     }
/*     */     else
/*     */     {
/* 154 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", userProfile.getCurrentLanguage()).getBody());
/* 155 */       bValid = false;
/*     */     }
/*     */ 
/* 158 */     if (!StringUtil.stringIsPopulated(form.getAgreement().getTitle()))
/*     */     {
/* 160 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationAgreementTitle", userProfile.getCurrentLanguage()).getBody());
/* 161 */       bValid = false;
/*     */     }
/*     */ 
/* 164 */     if (bValid)
/*     */     {
/* 167 */       Agreement existingAgreeement = null;
/* 168 */       if (AssetBankSettings.getAuditLogEnabled())
/*     */       {
/* 170 */         existingAgreeement = this.m_agreementsManager.getAgreement(a_dbTransaction, lAgreementId);
/*     */       }
/*     */ 
/* 174 */       if (lAgreementId > 0L)
/*     */       {
/* 176 */         this.m_agreementsManager.updateAgreement(a_dbTransaction, a);
/*     */       }
/*     */       else
/*     */       {
/* 182 */         Vector vOrgUnits = null;
/* 183 */         vOrgUnits = this.m_orgUnitManager.getOrgUnitsForUser(a_dbTransaction, userProfile.getUser().getId());
/*     */ 
/* 185 */         long lNewId = this.m_agreementsManager.addAgreement(a_dbTransaction, a, vOrgUnits);
/*     */ 
/* 187 */         a.setId(lNewId);
/* 188 */         form.getAgreement().setId(lNewId);
/* 189 */         form.setIsNew(true);
/*     */       }
/*     */ 
/* 192 */       form.setIsComplete(true);
/*     */ 
/* 195 */       if (AssetBankSettings.getAuditLogEnabled())
/*     */       {
/* 197 */         this.m_assetLogManager.logAgreementAddEdit(existingAgreeement, a, userProfile, a_dbTransaction);
/*     */       }
/*     */ 
/* 201 */       if (bPopup)
/*     */       {
/* 203 */         afForward = a_mapping.findForward("SuccessPopup");
/*     */       }
/*     */       else
/*     */       {
/* 207 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */ 
/*     */     }
/* 212 */     else if (bPopup)
/*     */     {
/* 214 */       afForward = a_mapping.findForward("FailurePopup");
/*     */     }
/*     */     else
/*     */     {
/* 218 */       afForward = a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 222 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.agreements.action.SaveAgreementAction
 * JD-Core Version:    0.6.0
 */