/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.approval.constant.AssetApprovalConstants;
/*     */ import com.bright.assetbank.approval.form.RequestApprovalForm;
/*     */ import com.bright.assetbank.approval.service.AssetApprovalManager;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveRequestApprovalAction extends BTransactionAction
/*     */   implements FrameworkConstants, AssetApprovalConstants, AssetBoxConstants, AssetBankConstants
/*     */ {
/*  65 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*  70 */   private AssetApprovalManager m_approvalManager = null;
/*     */ 
/*  76 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/*  67 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public void setAssetApprovalManager(AssetApprovalManager a_approvalManager)
/*     */   {
/*  73 */     this.m_approvalManager = a_approvalManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  79 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  89 */     ActionForward afForward = null;
/*  90 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  91 */     RequestApprovalForm form = (RequestApprovalForm)a_form;
/*     */ 
/*  94 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  96 */       this.m_logger.debug("This user does not have permission ruest approval");
/*  97 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 102 */     validateFieldLengths(form, a_request);
/*     */ 
/* 105 */     String sAppeal = a_request.getParameter("appeal");
/* 106 */     boolean bAppeal = false;
/* 107 */     if ((sAppeal != null) && (sAppeal.length() > 0))
/*     */     {
/* 109 */       bAppeal = true;
/*     */     }
/*     */ 
/* 113 */     AssetBox assetBox = userProfile.getAssetBox();
/* 114 */     Collection colToBeApproved = null;
/* 115 */     if (bAppeal)
/*     */     {
/* 117 */       colToBeApproved = assetBox.getAssetsInState(5);
/*     */     }
/* 119 */     else if (form.getIsHighResRequest())
/*     */     {
/* 121 */       colToBeApproved = assetBox.getAssetsInState(8);
/*     */     }
/*     */     else
/*     */     {
/* 125 */       colToBeApproved = assetBox.getAssetsInState(3);
/*     */     }
/*     */ 
/* 130 */     Iterator it = null;
/* 131 */     it = colToBeApproved.iterator();
/* 132 */     while (it.hasNext())
/*     */     {
/* 134 */       AssetInList ail = (AssetInList)it.next();
/* 135 */       long lAssetId = ail.getAsset().getId();
/*     */ 
/* 137 */       String sInclude = a_request.getParameter("include_" + lAssetId);
/* 138 */       if ((sInclude != null) && (sInclude.length() > 0))
/*     */       {
/* 141 */         String sUsageTypeFieldName = "usageTypeId_" + lAssetId;
/* 142 */         String sMandatoryFieldName = "mandatory_" + sUsageTypeFieldName;
/* 143 */         String sMandatoryMessage = a_request.getParameter(sMandatoryFieldName);
/* 144 */         boolean bMandatory = (sMandatoryMessage != null) && (sMandatoryMessage.length() > 0);
/*     */ 
/* 147 */         long lUsageTypeId = getLongParameter(a_request, "usageTypeId_" + lAssetId);
/*     */ 
/* 149 */         if ((lUsageTypeId <= 0L) && (bMandatory))
/*     */         {
/* 151 */           form.addError(sMandatoryMessage);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 156 */     if (form.getHasErrors())
/*     */     {
/* 158 */       return a_mapping.findForward("ValidationFailure");
/*     */     }
/*     */ 
/* 166 */     HashMap hmAdminEmails = new HashMap();
/* 167 */     boolean bNeedSuperUsers = false;
/*     */ 
/* 170 */     String sRequestedAssetIds = null;
/*     */ 
/* 172 */     it = colToBeApproved.iterator();
/* 173 */     while (it.hasNext())
/*     */     {
/* 175 */       AssetInList ail = (AssetInList)it.next();
/* 176 */       long lAssetId = ail.getAsset().getId();
/*     */ 
/* 178 */       String sInclude = a_request.getParameter("include_" + lAssetId);
/* 179 */       if ((sInclude != null) && (sInclude.length() > 0))
/*     */       {
/* 181 */         ail.setUserNotes(a_request.getParameter("usernotes_" + lAssetId));
/*     */ 
/* 184 */         long lUsageTypeId = getLongParameter(a_request, "usageTypeId_" + lAssetId);
/*     */ 
/* 186 */         if (lUsageTypeId > 0L)
/*     */         {
/* 188 */           ail.setUsageTypeId(lUsageTypeId);
/*     */         }
/*     */ 
/* 191 */         this.m_approvalManager.addAssetForApproval(a_dbTransaction, ail, userProfile.getUser().getId(), form.getIsHighResRequest());
/*     */ 
/* 194 */         if (!this.m_userManager.getApproverEmailsForAsset(lAssetId, hmAdminEmails))
/*     */         {
/* 197 */           bNeedSuperUsers = true;
/*     */         }
/*     */ 
/* 201 */         if (sRequestedAssetIds == null)
/*     */         {
/* 203 */           sRequestedAssetIds = "";
/*     */         }
/*     */         else
/*     */         {
/* 207 */           sRequestedAssetIds = sRequestedAssetIds + ", ";
/*     */         }
/* 209 */         sRequestedAssetIds = sRequestedAssetIds + ail.getAsset().getIdWithPadding();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 215 */     this.m_assetBoxManager.refreshAssetBoxesInProfile(a_dbTransaction, userProfile);
/*     */ 
/* 218 */     ABUser user = (ABUser)userProfile.getUser();
/* 219 */     this.m_approvalManager.sendApprovalEmail(user, hmAdminEmails, bNeedSuperUsers, sRequestedAssetIds);
/*     */ 
/* 221 */     afForward = a_mapping.findForward("Success");
/* 222 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.SaveRequestApprovalAction
 * JD-Core Version:    0.6.0
 */