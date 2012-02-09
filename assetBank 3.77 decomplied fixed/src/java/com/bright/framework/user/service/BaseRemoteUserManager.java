/*     */ package com.bright.framework.user.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.RemoteUserGroup;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public abstract class BaseRemoteUserManager
/*     */   implements RemoteUserManager
/*     */ {
/*     */   public final void updateUserInGroups(DBTransaction a_dbTransaction, LocalUserManager a_userManager, Vector a_vecMappedGroups, User a_appUser, String[] a_asMemberOf)
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     RemoteUserGroup group = null;
/*     */ 
/*  64 */     Vector vecGroupsForUser = new Vector();
/*  65 */     boolean bHasChanged = false;
/*     */ 
/*  68 */     for (int i = 0; i < a_vecMappedGroups.size(); i++)
/*     */     {
/*  70 */       group = (RemoteUserGroup)a_vecMappedGroups.get(i);
/*  71 */       String[] groupMappings = group.getMappingValues(AssetBankSettings.getRemoteGroupMappingDelimiter());
/*     */ 
/*  73 */       if (!userAlreadyInGroup(a_appUser, group))
/*     */       {
/*  76 */         if (!userIsMemberOf(a_asMemberOf, groupMappings)) {
/*     */           continue;
/*     */         }
/*  79 */         vecGroupsForUser.add(group);
/*  80 */         bHasChanged = true;
/*     */       }
/*  87 */       else if (userIsMemberOf(a_asMemberOf, groupMappings))
/*     */       {
/*  90 */         vecGroupsForUser.add(group);
/*     */       }
/*     */       else
/*     */       {
/*  95 */         bHasChanged = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 101 */     if (bHasChanged)
/*     */     {
/* 103 */       Vector vecGroupIds = new Vector();
/*     */ 
/* 107 */       for (int i = 0; (a_appUser.getGroups() != null) && (i < a_appUser.getGroups().size()); i++)
/*     */       {
/* 109 */         group = (RemoteUserGroup)a_appUser.getGroups().get(i);
/* 110 */         if ((group.getMapping() != null) && (group.getMapping().length() != 0))
/*     */           continue;
/* 112 */         vecGroupsForUser.add(group);
/*     */       }
/*     */ 
/* 117 */       for (int i = 0; i < vecGroupsForUser.size(); i++)
/*     */       {
/* 119 */         group = (RemoteUserGroup)vecGroupsForUser.get(i);
/* 120 */         vecGroupIds.add(new Long(group.getId()));
/*     */       }
/*     */ 
/* 124 */       a_userManager.addUserToGroups(a_dbTransaction, a_appUser.getId(), vecGroupIds);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean userAlreadyInGroup(User a_appUser, RemoteUserGroup a_group)
/*     */   {
/* 145 */     boolean bAlreadyIn = false;
/*     */ 
/* 147 */     for (int i = 0; (a_appUser.getGroups() != null) && (i < a_appUser.getGroups().size()); i++)
/*     */     {
/* 149 */       if (((RemoteUserGroup)a_appUser.getGroups().get(i)).getId() != a_group.getId())
/*     */         continue;
/* 151 */       bAlreadyIn = true;
/* 152 */       break;
/*     */     }
/*     */ 
/* 156 */     return bAlreadyIn;
/*     */   }
/*     */ 
/*     */   public static boolean userIsMemberOf(String[] a_userMemberOf, String[] asGroupMappings)
/*     */   {
/* 173 */     if (a_userMemberOf == null) {
/* 174 */       return false;
/*     */     }
/*     */ 
/* 178 */     String wildcard = AssetBankSettings.getRemoteGroupMappingWildcard();
/*     */ 
/* 181 */     for (int i = 0; i < a_userMemberOf.length; i++)
/*     */     {
/* 183 */       String userADGroup = a_userMemberOf[i];
/*     */ 
/* 186 */       if (userADGroup == null)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 191 */       for (int j = 0; j < asGroupMappings.length; j++)
/*     */       {
/* 193 */         String groupMapping = asGroupMappings[j];
/*     */ 
/* 196 */         if (groupMapping == null)
/*     */         {
/*     */           continue;
/*     */         }
/*     */ 
/* 201 */         if ((wildcard != null) && (wildcard.length() > 0))
/*     */         {
/* 206 */           boolean wildcardStart = groupMapping.startsWith(wildcard);
/* 207 */           boolean wildcardEnd = groupMapping.endsWith(wildcard);
/* 208 */           String[] tokens = groupMapping.split(Pattern.quote(wildcard));
/* 209 */           String regexPattern = "";
/*     */ 
/* 211 */           if (wildcardStart) {
/* 212 */             regexPattern = regexPattern + ".*";
/*     */           }
/* 214 */           for (String token : tokens) {
/* 215 */             if (regexPattern.length() > 0) {
/* 216 */               regexPattern = regexPattern + ".*";
/*     */             }
/* 218 */             regexPattern = regexPattern + Pattern.quote(token);
/*     */           }
/* 220 */           if (wildcardEnd) {
/* 221 */             regexPattern = regexPattern + ".*";
/*     */           }
/*     */ 
/* 225 */           Pattern regex = Pattern.compile(regexPattern, 2);
/* 226 */           if (regex.matcher(userADGroup).matches()) {
/* 227 */             return true;
/*     */           }
/*     */ 
/*     */         }
/* 232 */         else if (groupMapping.equalsIgnoreCase(userADGroup)) {
/* 233 */           return true;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */   public String getRemoteUserIdentifier(HttpServletRequest a_request)
/*     */   {
/* 248 */     return null;
/*     */   }
/*     */ 
/*     */   public void logOffUser(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean isUserAuthenticated(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */   {
/* 259 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.service.BaseRemoteUserManager
 * JD-Core Version:    0.6.0
 */