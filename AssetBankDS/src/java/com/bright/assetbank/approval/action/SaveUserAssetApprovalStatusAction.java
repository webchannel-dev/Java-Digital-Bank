/*     */ package com.bright.assetbank.approval.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.approval.bean.AssetApproval;
/*     */ import com.bright.assetbank.approval.bean.AssetApprovalSearchCriteria;
/*     */ import com.bright.assetbank.approval.constant.AssetApprovalConstants;
/*     */ import com.bright.assetbank.approval.form.AssetApprovalForm;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveUserAssetApprovalStatusAction extends BTransactionAction
/*     */   implements AssetBoxConstants, AssetApprovalConstants, FrameworkConstants, MessageConstants, AssetBankConstants
/*     */ {
/*  71 */   private AssetApprovalManager m_approvalManager = null;
/*  72 */   private EmailManager m_emailManager = null;
/*  73 */   private ABUserManager m_userManager = null;
/*     */ 
/* 398 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  95 */     ActionForward afForward = null;
/*  96 */     AssetApprovalForm form = (AssetApprovalForm)a_form;
/*     */ 
/*  99 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 101 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/* 103 */       this.m_logger.error("SaveUserAssetApprovalStatusAction.execute : User does not have admin permission : " + userProfile);
/* 104 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 108 */     long lUserId = getIntParameter(a_request, "userId");
/*     */ 
/* 114 */     AssetApprovalSearchCriteria search = new AssetApprovalSearchCriteria();
/* 115 */     search.setUserId(lUserId);
/* 116 */     search.setApprovalStatusId(1L);
/* 117 */     Vector vec = this.m_approvalManager.getAssetApprovalList(a_dbTransaction, search, userProfile.getCurrentLanguage());
/*     */ 
/* 120 */     Vector vecToUpdate = new Vector();
/*     */ 
/* 123 */     HashMap hmInitialAdminEmails = new HashMap();
/* 124 */     boolean bInitialNeedSuperUsers = false;
/*     */ 
/* 127 */     HashMap hmFinalAdminEmails = new HashMap();
/* 128 */     boolean bFinalNeedSuperUsers = false;
/*     */ 
/* 131 */     String sProcessedAssetIds = null;
/*     */ 
/* 134 */     String sMessageToIncludeInEmail = "";
/*     */ 
/* 137 */     Iterator it = vec.iterator();
/* 138 */     while (it.hasNext())
/*     */     {
/* 140 */       AssetApproval approval = (AssetApproval)it.next();
/* 141 */       long lAssetId = approval.getAsset().getId();
/*     */ 
/* 144 */       if (!this.m_userManager.getApproverEmailsForAsset(lAssetId, hmInitialAdminEmails))
/*     */       {
/* 147 */         bInitialNeedSuperUsers = true;
/*     */       }
/*     */ 
/* 152 */       boolean bDealtWith = false;
/* 153 */       String sSelectName = "approvalstatus_" + lAssetId;
/* 154 */       long lStatusSelectedId = getLongParameter(a_request, sSelectName);
/* 155 */       if (lStatusSelectedId >= 0L)
/*     */       {
/* 158 */         approval.getApprovalStatus().setId(lStatusSelectedId);
/* 159 */         approval.getApprovalStatus().setName("");
/*     */ 
/* 161 */         approval.setAdminNotes(a_request.getParameter("adminnotes_" + lAssetId));
/*     */ 
/* 164 */         if (StringUtil.stringIsPopulated(approval.getAdminNotes()))
/*     */         {
/* 166 */           sMessageToIncludeInEmail = sMessageToIncludeInEmail + lAssetId + ": " + approval.getAdminNotes() + "<br/>";
/*     */         }
/*     */ 
/* 170 */         validateFieldLengths(form, a_request);
/*     */ 
/* 172 */         BrightDate dateExpiry = new BrightDate();
/* 173 */         dateExpiry.setFormDate(a_request.getParameter("approvalexpiry_" + lAssetId));
/* 174 */         boolean bDateValid = dateExpiry.processFormData();
/* 175 */         if (bDateValid)
/*     */         {
/* 177 */           approval.setDateExpires(dateExpiry);
/*     */         }
/*     */         else
/*     */         {
/* 181 */           form.addError(this.m_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", userProfile.getCurrentLanguage()).getBody());
/*     */         }
/*     */ 
/* 185 */         if ((lStatusSelectedId == 3L) || (lStatusSelectedId == 2L) || (lStatusSelectedId == 4L) || (StringUtil.stringIsPopulated(approval.getAdminNotes())))
/*     */         {
/* 192 */           BrightDate dateApproved = new BrightDate(new Date());
/* 193 */           approval.setDateApproved(dateApproved);
/*     */ 
/* 195 */           if (!dateExpiry.getIsFormDateEntered())
/*     */           {
/* 198 */             int iExpiryDays = AssetBankSettings.getDefaultApprovalExpiryPeriod();
/* 199 */             Calendar cal = Calendar.getInstance();
/* 200 */             cal.setTime(new Date());
/* 201 */             cal.add(5, iExpiryDays);
/* 202 */             dateExpiry.setDate(cal.getTime());
/* 203 */             approval.setDateExpires(dateExpiry);
/*     */           }
/*     */ 
/* 207 */           if (sProcessedAssetIds == null)
/*     */           {
/* 209 */             sProcessedAssetIds = "";
/*     */           }
/*     */           else
/*     */           {
/* 213 */             sProcessedAssetIds = sProcessedAssetIds + ", ";
/*     */           }
/*     */ 
/* 216 */           sProcessedAssetIds = sProcessedAssetIds + approval.getAsset().getIdWithPadding();
/*     */ 
/* 219 */           bDealtWith = true;
/*     */         }
/*     */ 
/* 224 */         vecToUpdate.add(approval);
/*     */       }
/*     */ 
/* 228 */       if (!bDealtWith)
/*     */       {
/* 231 */         if (!this.m_userManager.getApproverEmailsForAsset(lAssetId, hmFinalAdminEmails))
/*     */         {
/* 234 */           bFinalNeedSuperUsers = true;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 240 */     if (!form.getHasErrors())
/*     */     {
/* 243 */       this.m_approvalManager.updateUserApprovalList(a_dbTransaction, vecToUpdate);
/*     */ 
/* 246 */       ABUser userSubject = (ABUser)userProfile.getUser();
/* 247 */       ABUser userObject = (ABUser)this.m_userManager.getUser(a_dbTransaction, lUserId);
/*     */ 
/* 250 */       if (sProcessedAssetIds != null)
/*     */       {
/* 252 */         sendUserNotificationEmail(userObject, sProcessedAssetIds, sMessageToIncludeInEmail);
/*     */       }
/*     */ 
/* 256 */       Iterator itFinal = hmFinalAdminEmails.keySet().iterator();
/* 257 */       while (itFinal.hasNext())
/*     */       {
/* 259 */         hmInitialAdminEmails.remove(itFinal.next());
/*     */       }
/*     */ 
/* 263 */       hmInitialAdminEmails.remove(userSubject.getEmailAddress());
/*     */ 
/* 266 */       boolean bNotNeededSuperUsers = (bInitialNeedSuperUsers) && (!bFinalNeedSuperUsers) && (!userProfile.getIsAdmin());
/*     */ 
/* 269 */       sendApproverNotNeededEmail(userObject, hmInitialAdminEmails, bNotNeededSuperUsers, sProcessedAssetIds);
/*     */ 
/* 271 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     else
/*     */     {
/* 275 */       afForward = a_mapping.findForward("ValidationFailure");
/*     */     }
/* 277 */     return afForward;
/*     */   }
/*     */ 
/*     */   private void sendUserNotificationEmail(ABUser user, String sProcessedAssetIds, String sMessage)
/*     */   {
/* 288 */     String sName = user.getFullName();
/* 289 */     if (!StringUtil.stringIsPopulated(sName))
/*     */     {
/* 291 */       sName = user.getUsername();
/*     */     }
/*     */ 
/* 294 */     if (sProcessedAssetIds == null)
/*     */     {
/* 296 */       sProcessedAssetIds = "None";
/*     */     }
/*     */ 
/* 299 */     HashMap params = new HashMap();
/* 300 */     params.put("template", "assets_approved");
/* 301 */     params.put("name", sName);
/* 302 */     params.put("email", user.getEmailAddress());
/* 303 */     params.put("assetids", sProcessedAssetIds);
/* 304 */     params.put("adminmessage", sMessage);
/*     */     try
/*     */     {
/* 308 */       this.m_emailManager.sendTemplatedEmail(params, user.getLanguage());
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 313 */       this.m_logger.debug("SaveUserAssetApprovalStatusAction: The alert email was not successfully sent to the user: " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void sendApproverNotNeededEmail(ABUser user, HashMap hmAdminEmails, boolean bNeedSuperUsers, String sProcessedAssetIds)
/*     */     throws Bn2Exception
/*     */   {
/* 330 */     String sName = user.getFullName();
/* 331 */     if (!StringUtil.stringIsPopulated(sName))
/*     */     {
/* 333 */       sName = user.getUsername();
/*     */     }
/*     */ 
/* 337 */     String sAdminEmailAddress = StringUtil.getEmailAddressesFromHashMap(hmAdminEmails);
/*     */ 
/* 339 */     if (bNeedSuperUsers)
/*     */     {
/* 341 */       if (StringUtil.stringIsPopulated(sAdminEmailAddress))
/*     */       {
/* 343 */         String sSuperEmailAddress = this.m_userManager.getAdminEmailAddresses();
/* 344 */         if (StringUtil.stringIsPopulated(sSuperEmailAddress))
/*     */         {
/* 346 */           sAdminEmailAddress = sAdminEmailAddress + ";" + sSuperEmailAddress;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 351 */         sAdminEmailAddress = this.m_userManager.getAdminEmailAddresses();
/*     */       }
/*     */     }
/*     */ 
/* 355 */     if (StringUtil.stringIsPopulated(sAdminEmailAddress))
/*     */     {
/* 358 */       HashMap params = new HashMap();
/* 359 */       params.put("template", "approver_work_done");
/* 360 */       params.put("name", sName);
/* 361 */       params.put("adminEmailAddresses", sAdminEmailAddress);
/* 362 */       params.put("assetids", sProcessedAssetIds);
/*     */       try
/*     */       {
/* 366 */         this.m_emailManager.sendTemplatedEmail(params, LanguageConstants.k_defaultLanguage);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 371 */         this.m_logger.debug("SaveUserAssetApprovalStatusAction: The alert email was not successfully sent to the admin: " + e.getMessage());
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 377 */       this.m_logger.debug("SaveUserAssetApprovalStatusAction: No admins to email alert to!");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/* 385 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/* 390 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 395 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 401 */     this.m_listManager = listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.approval.action.SaveUserAssetApprovalStatusAction
 * JD-Core Version:    0.6.0
 */