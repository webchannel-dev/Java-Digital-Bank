/*     */ package com.bright.assetbank.assetbox.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.approval.form.RequestApprovalForm;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.assetbox.constant.AssetBoxConstants;
/*     */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Collection;
/*     */ import java.util.TreeSet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewRequestApprovalAction extends BTransactionAction
/*     */   implements AssetBoxConstants, AssetBankConstants
/*     */ {
/*  57 */   private UsageManager m_usageManager = null;
/*     */ 
/*  63 */   protected AssetManager m_assetManager = null;
/*     */ 
/*  69 */   private AssetBoxManager m_assetBoxManager = null;
/*     */ 
/*     */   public void setUsageManager(UsageManager a_usageManager)
/*     */   {
/*  60 */     this.m_usageManager = a_usageManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager assetManager)
/*     */   {
/*  66 */     this.m_assetManager = assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetBoxManager(AssetBoxManager a_sAssetBoxManager)
/*     */   {
/*  72 */     this.m_assetBoxManager = a_sAssetBoxManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  83 */     ActionForward afForward = null;
/*  84 */     RequestApprovalForm form = (RequestApprovalForm)a_form;
/*  85 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  86 */     long lUserId = 0L;
/*  87 */     if (userProfile.getUser() != null)
/*     */     {
/*  89 */       lUserId = userProfile.getUser().getId();
/*     */     }
/*     */ 
/*  93 */     String sAppeal = a_request.getParameter("appeal");
/*  94 */     boolean bAppeal = false;
/*  95 */     if ((sAppeal != null) && (sAppeal.length() > 0))
/*     */     {
/*  97 */       bAppeal = true;
/*     */     }
/*     */ 
/* 100 */     Collection colToBeApproved = new TreeSet();
/*     */ 
/* 104 */     AssetBox assetBox = userProfile.getAssetBox();
/*     */ 
/* 106 */     if (bAppeal)
/*     */     {
/* 108 */       colToBeApproved = assetBox.getAssetsInState(5);
/*     */     }
/*     */     else
/*     */     {
/* 112 */       colToBeApproved = assetBox.getAssetsInState(3);
/*     */     }
/*     */ 
/* 116 */     form.setApprovalList(colToBeApproved);
/* 117 */     form.setIsAppeal(bAppeal);
/*     */ 
/* 121 */     form.setUsageTypeList(this.m_usageManager.getUsageTypes(a_dbTransaction, 0L, userProfile.getUsageExclusions(), lUserId, userProfile.getCurrentLanguage()));
/*     */ 
/* 123 */     afForward = a_mapping.findForward("Success");
/* 124 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.action.ViewRequestApprovalAction
 * JD-Core Version:    0.6.0
 */