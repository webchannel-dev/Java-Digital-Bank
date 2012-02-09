/*     */ package com.bright.framework.user.bean;
/*     */ 
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class UserProfile
/*     */   implements CommonConstants
/*     */ {
/*  41 */   protected User m_user = null;
/*  42 */   private boolean m_bCheckedCookies = false;
/*  43 */   private static UserProfileFactory c_factory = null;
/*  44 */   private Language m_currentLanguage = null;
/*  45 */   protected boolean m_bPasswordExpired = false;
/*  46 */   protected long m_lSessionId = 0L;
/*  47 */   protected long m_lOriginalUserId = -1L;
/*  48 */   private boolean m_bCMSEditMode = false;
/*     */ 
/*     */   public static void setUserProfileFactory(UserProfileFactory a_factory)
/*     */   {
/*  75 */     c_factory = a_factory;
/*     */   }
/*     */ 
/*     */   public static UserProfile getUserProfile(HttpSession a_session)
/*     */   {
/*  93 */     Object objProfile = a_session.getAttribute("userprofile");
/*  94 */     UserProfile userProfile = null;
/*     */ 
/*  96 */     if (objProfile != null)
/*     */     {
/*  98 */       userProfile = (UserProfile)(UserProfile)objProfile;
/*     */     }
/* 102 */     else if (c_factory == null)
/*     */     {
/* 104 */       GlobalApplication.getInstance().getLogger().error("UserProfileFactory has not been set. It needs to be in [App name]ActionServlet");
/*     */     }
/*     */     else
/*     */     {
/* 109 */       userProfile = c_factory.createUserProfile(a_session);
/*     */ 
/* 111 */       a_session.setAttribute("userprofile", userProfile);
/*     */     }
/*     */ 
/* 115 */     return userProfile;
/*     */   }
/*     */ 
/*     */   public static void resetUserProfile(HttpSession a_session)
/*     */   {
/* 130 */     UserProfile up = getUserProfile(a_session);
/* 131 */     up.invalidate();
/*     */ 
/* 134 */     a_session.removeAttribute("userprofile");
/*     */ 
/* 137 */     up.setCurrentLanguage((Language)LanguageConstants.k_defaultLanguage.clone());
/* 138 */     a_session.removeAttribute("currentLanguage");
/*     */   }
/*     */ 
/*     */   public static UserProfile getUserProfile(HttpServletRequest a_request)
/*     */   {
/* 155 */     HttpSession session = a_request.getSession(false);
/* 156 */     return getUserProfile(session);
/*     */   }
/*     */ 
/*     */   public static long getLongIdForSession(HttpSession a_session)
/*     */   {
/* 167 */     return a_session.getCreationTime() % 1000000L * 1000000L + a_session.getId().hashCode() % 1000000;
/*     */   }
/*     */ 
/*     */   public boolean getIsLoggedIn()
/*     */   {
/* 183 */     return (this.m_user != null) && (this.m_user.getId() > 0L);
/*     */   }
/*     */ 
/*     */   public void invalidate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public long getUserId()
/*     */   {
/* 204 */     long lUserId = 0L;
/*     */ 
/* 206 */     if (getUser() != null)
/*     */     {
/* 208 */       lUserId = getUser().getId();
/*     */     }
/*     */ 
/* 211 */     return lUserId;
/*     */   }
/*     */ 
/*     */   public abstract boolean getIsAdmin();
/*     */ 
/*     */   public void setUser(User a_user)
/*     */   {
/* 224 */     this.m_user = a_user;
/*     */   }
/*     */ 
/*     */   public User getUser()
/*     */   {
/* 229 */     return this.m_user;
/*     */   }
/*     */ 
/*     */   public boolean getCheckedCookies()
/*     */   {
/* 234 */     return this.m_bCheckedCookies;
/*     */   }
/*     */ 
/*     */   public void setCheckedCookies(boolean a_bCheckedCookies) {
/* 238 */     this.m_bCheckedCookies = a_bCheckedCookies;
/*     */   }
/*     */ 
/*     */   public void setSessionId(long a_lSessionId) {
/* 242 */     this.m_lSessionId = a_lSessionId;
/*     */   }
/*     */ 
/*     */   public void setIsPasswordExpired(boolean a_bPasswordExpired)
/*     */   {
/* 247 */     this.m_bPasswordExpired = a_bPasswordExpired;
/*     */   }
/*     */ 
/*     */   public boolean isPasswordExpired()
/*     */   {
/* 252 */     return this.m_bPasswordExpired;
/*     */   }
/*     */ 
/*     */   public Language getCurrentLanguage()
/*     */   {
/* 257 */     return this.m_currentLanguage;
/*     */   }
/*     */ 
/*     */   public void setCurrentLanguage(Language currentLanguage)
/*     */   {
/* 262 */     this.m_currentLanguage = currentLanguage;
/*     */   }
/*     */ 
/*     */   public String getCurrentLanguageIdAsString()
/*     */   {
/* 267 */     return String.valueOf(getCurrentLanguage().getId());
/*     */   }
/*     */ 
/*     */   public long getSessionId()
/*     */   {
/* 273 */     return this.m_lSessionId;
/*     */   }
/*     */ 
/*     */   public void setOriginalUserId(long a_lOriginalUserId)
/*     */   {
/* 278 */     this.m_lOriginalUserId = a_lOriginalUserId;
/*     */   }
/*     */ 
/*     */   public long getOriginalUserId()
/*     */   {
/* 283 */     return this.m_lOriginalUserId;
/*     */   }
/*     */ 
/*     */   public void setCMSEditMode(boolean a_bCMSEditMode)
/*     */   {
/* 288 */     this.m_bCMSEditMode = a_bCMSEditMode;
/*     */   }
/*     */ 
/*     */   public boolean getCMSEditMode()
/*     */   {
/* 293 */     return this.m_bCMSEditMode;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.bean.UserProfile
 * JD-Core Version:    0.6.0
 */