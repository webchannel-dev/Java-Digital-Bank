/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.form.UpdateUserForm;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.common.bean.RefDataItem;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public abstract class UserAction extends BTransactionAction
/*     */   implements UserConstants
/*     */ {
/*  45 */   protected ABUserManager m_userManager = null;
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/*  50 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  56 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public Vector getGroupIds(HttpServletRequest a_request)
/*     */   {
/*  72 */     Vector vecGroupIds = new Vector();
/*     */ 
/*  75 */     Enumeration e = a_request.getParameterNames();
/*  76 */     String sName = null;
/*     */ 
/*  78 */     while (e.hasMoreElements())
/*     */     {
/*  80 */       sName = (String)e.nextElement();
/*     */ 
/*  83 */       if (!sName.startsWith("group")) {
/*     */         continue;
/*     */       }
/*  86 */       long lId = getLongParameter(a_request, sName);
/*     */ 
/*  88 */       if (lId > 0L)
/*     */       {
/*  90 */         vecGroupIds.add(new Long(lId));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  95 */     return vecGroupIds;
/*     */   }
/*     */ 
/*     */   public boolean existsGroupOtherThanDefault(Vector a_vecGroupIds)
/*     */   {
/* 111 */     boolean bExistsGroupOtherThanDefault = false;
/*     */ 
/* 113 */     Iterator it = a_vecGroupIds.iterator();
/* 114 */     while (it.hasNext())
/*     */     {
/* 116 */       Long ol = (Long)it.next();
/* 117 */       if ((ol.longValue() != 1L) && (ol.longValue() != 0L))
/*     */       {
/* 119 */         bExistsGroupOtherThanDefault = true;
/* 120 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 124 */     return bExistsGroupOtherThanDefault;
/*     */   }
/*     */ 
/*     */   protected void updateUserWithCommonFields(ABUser user, UpdateUserForm a_form)
/*     */   {
/* 136 */     user.setForename(a_form.getUser().getForename());
/* 137 */     user.setSurname(a_form.getUser().getSurname());
/* 138 */     user.setEmailAddress(a_form.getUser().getEmailAddress());
/* 139 */     user.setTaxNumber(a_form.getUser().getTaxNumber());
/* 140 */     user.setOrganisation(a_form.getUser().getOrganisation());
/* 141 */     user.setJobTitle(a_form.getUser().getJobTitle());
/*     */ 
/* 144 */     if (AssetBankSettings.getUsersHaveDivisions())
/*     */     {
/* 146 */       user.getRefDivision().setId(a_form.getUser().getRefDivision().getId());
/*     */     }
/*     */ 
/* 149 */     if (AssetBankSettings.getUsersHaveTitle())
/*     */     {
/* 151 */       user.setTitle(a_form.getUser().getTitle());
/*     */     }
/*     */ 
/* 155 */     user.setAdminNotes(a_form.getUser().getAdminNotes());
/*     */ 
/* 158 */     user.setIsSuspended(a_form.getUser().getIsSuspended());
/*     */ 
/* 161 */     user.setIsDeleted(a_form.getUser().getIsDeleted());
/*     */ 
/* 165 */     if (AssetBankSettings.getUsersHaveStructuredAddress())
/*     */     {
/* 167 */       user.setHomeAddress(a_form.getUser().getHomeAddress());
/*     */     }
/*     */     else
/*     */     {
/* 171 */       user.setAddress(a_form.getUser().getAddress());
/*     */     }
/*     */ 
/* 175 */     user.setRegistrationInfo(a_form.getUser().getRegistrationInfo());
/*     */ 
/* 177 */     user.setLanguage(a_form.getUser().getLanguage());
/*     */ 
/* 179 */     user.setCanLoginFromCms(a_form.getUser().getCanLoginFromCms());
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.UserAction
 * JD-Core Version:    0.6.0
 */