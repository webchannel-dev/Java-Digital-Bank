/*     */ package com.bright.assetbank.user.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.UserSearchCriteria;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.search.bean.SearchResults;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class UserForm extends UpdateUserForm
/*     */   implements AssetBankConstants
/*     */ {
/*  56 */   private Vector m_vUsers = null;
/*  57 */   private Vector m_vGroups = null;
/*  58 */   private String m_sConfirmPassword = null;
/*  59 */   private Vector m_vecGroupIds = null;
/*  60 */   private boolean m_bApproved = false;
/*  61 */   private String m_sMessage = null;
/*  62 */   private String m_sOldUsername = null;
/*  63 */   private UserSearchCriteria m_searchCriteria = null;
/*  64 */   private String m_sExpiryDate = null;
/*  65 */   private boolean m_bEmptyResult = false;
/*  66 */   private String m_sRequestedOrgUnit = null;
/*  67 */   private boolean m_bNotifyUser = false;
/*  68 */   private String m_sAdminNotes = null;
/*  69 */   private User m_usLastUpdatedBy = null;
/*  70 */   private boolean m_bIsSuspended = false;
/*  71 */   private User m_usInvitedByUser = null;
/*  72 */   private boolean m_bShowExpiryDate = false;
/*  73 */   private SearchResults m_searchResults = null;
/*     */ 
/*     */   public boolean getSelectedGroup(int a_iGroupId)
/*     */   {
/*  92 */     Long longVal = null;
/*     */ 
/*  94 */     if (getGroupIds() != null)
/*     */     {
/*  96 */       for (int i = 0; i < getGroupIds().size(); i++)
/*     */       {
/*  98 */         longVal = (Long)(Long)getGroupIds().elementAt(i);
/*     */ 
/* 100 */         if (a_iGroupId == longVal.longValue())
/*     */         {
/* 102 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   public void populate(ABUser a_abUser)
/*     */   {
/* 122 */     DateFormat format = AssetBankSettings.getStandardDateFormat();
/*     */ 
/* 124 */     if (a_abUser.getExpiryDate() != null)
/*     */     {
/* 126 */       setExpiryDate(format.format(a_abUser.getExpiryDate()));
/*     */     }
/*     */ 
/* 129 */     setUser(a_abUser);
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, boolean a_bValidatePasswords, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager)
/*     */     throws Bn2Exception
/*     */   {
/* 145 */     super.validate(a_request, a_userProfile, a_dbTransaction, a_listManager);
/*     */ 
/* 147 */     if (a_bValidatePasswords)
/*     */     {
/* 150 */       if ((getUser().getPassword() != null) && (!getUser().getPassword().equals(getConfirmPassword())))
/*     */       {
/* 152 */         addError(a_listManager.getListItem(a_dbTransaction, "failedValidationPasswordsDontMatch", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */ 
/* 156 */     DateFormat dfDateFormat = AssetBankSettings.getStandardDateFormat();
/*     */     try
/*     */     {
/* 159 */       if (StringUtil.stringIsPopulated(getExpiryDate()))
/*     */       {
/* 161 */         getUser().setExpiryDate(dfDateFormat.parse(getExpiryDate()));
/*     */       }
/*     */     }
/*     */     catch (ParseException pe)
/*     */     {
/* 166 */       addError(a_listManager.getListItem(a_dbTransaction, "failedValidationDateFormat", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getGroups()
/*     */   {
/* 175 */     return this.m_vGroups;
/*     */   }
/*     */ 
/*     */   public void setGroups(Vector a_sGroups)
/*     */   {
/* 181 */     this.m_vGroups = a_sGroups;
/*     */   }
/*     */ 
/*     */   public UserSearchCriteria getSearchCriteria()
/*     */   {
/* 187 */     if (this.m_searchCriteria == null)
/*     */     {
/* 189 */       this.m_searchCriteria = new UserSearchCriteria();
/*     */     }
/* 191 */     return this.m_searchCriteria;
/*     */   }
/*     */ 
/*     */   public void setSearchCriteria(UserSearchCriteria a_sSearchCriteria)
/*     */   {
/* 197 */     this.m_searchCriteria = a_sSearchCriteria;
/*     */   }
/*     */ 
/*     */   public Vector getUsers()
/*     */   {
/* 203 */     return this.m_vUsers;
/*     */   }
/*     */ 
/*     */   public void setUsers(Vector a_sUsers)
/*     */   {
/* 209 */     this.m_vUsers = a_sUsers;
/*     */   }
/*     */ 
/*     */   public String getConfirmPassword()
/*     */   {
/* 215 */     return this.m_sConfirmPassword;
/*     */   }
/*     */ 
/*     */   public void setConfirmPassword(String a_sConfirmPassword)
/*     */   {
/* 221 */     this.m_sConfirmPassword = a_sConfirmPassword;
/*     */   }
/*     */ 
/*     */   public void setGroupIds(Vector a_vecGroupIds)
/*     */   {
/* 226 */     this.m_vecGroupIds = a_vecGroupIds;
/*     */   }
/*     */ 
/*     */   public Vector getGroupIds()
/*     */   {
/* 231 */     return this.m_vecGroupIds;
/*     */   }
/*     */ 
/*     */   public boolean getApproved()
/*     */   {
/* 237 */     return this.m_bApproved;
/*     */   }
/*     */ 
/*     */   public void setApproved(boolean a_sApproved)
/*     */   {
/* 242 */     this.m_bApproved = a_sApproved;
/*     */   }
/*     */ 
/*     */   public void setOldUsername(String a_sOldUsername)
/*     */   {
/* 247 */     this.m_sOldUsername = a_sOldUsername;
/*     */   }
/*     */ 
/*     */   public String getOldUsername()
/*     */   {
/* 252 */     return this.m_sOldUsername;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 257 */     return this.m_sMessage;
/*     */   }
/*     */ 
/*     */   public void setMessage(String a_sMessage)
/*     */   {
/* 262 */     this.m_sMessage = a_sMessage;
/*     */   }
/*     */ 
/*     */   public String getExpiryDate()
/*     */   {
/* 267 */     return this.m_sExpiryDate;
/*     */   }
/*     */ 
/*     */   public void setExpiryDate(String a_sExpiryDate)
/*     */   {
/* 272 */     this.m_sExpiryDate = a_sExpiryDate;
/*     */   }
/*     */ 
/*     */   public boolean getEmptyResult() {
/* 276 */     return this.m_bEmptyResult;
/*     */   }
/*     */ 
/*     */   public void setEmptyResult(boolean a_bEmptyResult) {
/* 280 */     this.m_bEmptyResult = a_bEmptyResult;
/*     */   }
/*     */ 
/*     */   public String getRequestedOrgUnit()
/*     */   {
/* 285 */     return this.m_sRequestedOrgUnit;
/*     */   }
/*     */ 
/*     */   public void setRequestedOrgUnit(String a_sRequestedOrgUnit) {
/* 289 */     this.m_sRequestedOrgUnit = a_sRequestedOrgUnit;
/*     */   }
/*     */ 
/*     */   public boolean getNotifyUser()
/*     */   {
/* 294 */     return this.m_bNotifyUser;
/*     */   }
/*     */ 
/*     */   public void setNotifyUser(boolean a_sNotifyUser)
/*     */   {
/* 299 */     this.m_bNotifyUser = a_sNotifyUser;
/*     */   }
/*     */ 
/*     */   public String getAdminNotes()
/*     */   {
/* 304 */     return this.m_sAdminNotes;
/*     */   }
/*     */ 
/*     */   public void setAdminNotes(String a_sAdminNotes)
/*     */   {
/* 309 */     this.m_sAdminNotes = a_sAdminNotes;
/*     */   }
/*     */ 
/*     */   public User getLastUpdatedBy()
/*     */   {
/* 314 */     return this.m_usLastUpdatedBy;
/*     */   }
/*     */ 
/*     */   public void setLastUpdatedBy(User a_usLastUpdatedBy)
/*     */   {
/* 319 */     this.m_usLastUpdatedBy = a_usLastUpdatedBy;
/*     */   }
/*     */ 
/*     */   public User getInvitedByUser()
/*     */   {
/* 324 */     return this.m_usInvitedByUser;
/*     */   }
/*     */ 
/*     */   public void setInvitedByUser(User a_usInvitedByUser)
/*     */   {
/* 329 */     this.m_usInvitedByUser = a_usInvitedByUser;
/*     */   }
/*     */ 
/*     */   public boolean getIsSuspended()
/*     */   {
/* 334 */     return this.m_bIsSuspended;
/*     */   }
/*     */ 
/*     */   public void setIsSuspended(boolean a_bIsSuspended)
/*     */   {
/* 339 */     this.m_bIsSuspended = a_bIsSuspended;
/*     */   }
/*     */ 
/*     */   public boolean getShowExpiryDate()
/*     */   {
/* 344 */     return this.m_bShowExpiryDate;
/*     */   }
/*     */ 
/*     */   public void setShowExpiryDate(boolean a_bShowExpiryDate)
/*     */   {
/* 349 */     this.m_bShowExpiryDate = a_bShowExpiryDate;
/*     */   }
/*     */ 
/*     */   public SearchResults getSearchResults()
/*     */   {
/* 354 */     return this.m_searchResults;
/*     */   }
/*     */ 
/*     */   public void setSearchResults(SearchResults a_results)
/*     */   {
/* 359 */     this.m_searchResults = a_results;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.form.UserForm
 * JD-Core Version:    0.6.0
 */