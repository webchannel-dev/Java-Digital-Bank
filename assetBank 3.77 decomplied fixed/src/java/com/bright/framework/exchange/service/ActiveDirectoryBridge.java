/*     */ package com.bright.framework.exchange.service;
/*     */ 
/*     */ import com.bright.framework.activedirectory.constant.ActiveDirectorySettings;
/*     */ import com.bright.framework.exchange.bean.ADUser;
/*     */ import com.bright.framework.exchange.exception.ADException;
/*     */ import com.bright.framework.exchange.exception.ConnectException;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.naming.ldap.Control;
/*     */ import javax.naming.ldap.InitialLdapContext;
/*     */ import javax.naming.ldap.LdapContext;
/*     */ import javax.naming.ldap.PagedResultsControl;
/*     */ import javax.naming.ldap.PagedResultsResponseControl;
/*     */ 
/*     */ public class ActiveDirectoryBridge
/*     */ {
/*     */   private static final String c_ksClassName = "ActiveDirectoryBridge";
/*     */   private static final String c_ksDefaultICFactory = "com.sun.jndi.ldap.LdapCtxFactory";
/*     */   private static final String c_ksDefaultSecurityLevel = "simple";
/*     */   private static final String c_ksADName_CN = "cn";
/*     */   private static final String c_ksADName_DisplayName = "displayName";
/*     */   private static final String c_ksADName_sForename = "givenName";
/*     */   private static final String c_ksADName_sSurname = "sn";
/*     */   private static final String c_ksADName_MemberOf = "memberOf";
/*     */   private static final String c_ksADName_Company = "company";
/*  70 */   private String m_sLdapServerUrl = null;
/*  71 */   private String m_sInitialContextFactory = null;
/*  72 */   private String m_sSecurityLevel = null;
/*     */ 
/*  76 */   private LdapContext m_dcActiveDirectory = null;
/*     */ 
/*     */   public ActiveDirectoryBridge(String a_sLdapServerUrl, String a_sInitialContextFactory, String a_sSecurityLevel)
/*     */   {
/*  98 */     this.m_sLdapServerUrl = a_sLdapServerUrl;
/*  99 */     this.m_sInitialContextFactory = a_sInitialContextFactory;
/* 100 */     this.m_sSecurityLevel = a_sSecurityLevel;
/*     */   }
/*     */ 
/*     */   public ActiveDirectoryBridge(String a_sLapServerUrl)
/*     */   {
/* 121 */     this(a_sLapServerUrl, "com.sun.jndi.ldap.LdapCtxFactory", "simple");
/*     */   }
/*     */ 
/*     */   public void connect(String a_sUserDistinguishedName, String a_sPassword)
/*     */     throws ConnectException, com.bright.framework.user.exception.AuthenticationException
/*     */   {
/* 142 */     String ksMethodName = "connect";
/*     */     try
/*     */     {
/* 146 */       if (this.m_dcActiveDirectory != null)
/*     */       {
/* 148 */         this.m_dcActiveDirectory.close();
/*     */       }
/*     */ 
/* 151 */       Hashtable env = new Hashtable();
/* 152 */       env.put("java.naming.factory.initial", this.m_sInitialContextFactory);
/* 153 */       env.put("java.naming.provider.url", this.m_sLdapServerUrl);
/* 154 */       env.put("java.naming.security.authentication", this.m_sSecurityLevel);
/* 155 */       env.put("java.naming.security.principal", a_sUserDistinguishedName);
/* 156 */       env.put("java.naming.security.credentials", a_sPassword);
/*     */ 
/* 159 */       env.put("java.naming.ldap.derefAliases", ActiveDirectorySettings.getLdapDerefAliases());
/*     */ 
/* 163 */       this.m_dcActiveDirectory = new InitialLdapContext(env, null);
/*     */     }
/*     */     catch (javax.naming.AuthenticationException e)
/*     */     {
/* 168 */       throw new com.bright.framework.user.exception.AuthenticationException("ActiveDirectoryBridge.connect - could not authenticate: " + e.getMessage());
/*     */     }
/*     */     catch (NamingException e)
/*     */     {
/* 172 */       throw new ConnectException("ActiveDirectoryBridge.connect - could not connect: " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */     try
/*     */     {
/* 190 */       if (this.m_dcActiveDirectory != null)
/*     */       {
/* 192 */         this.m_dcActiveDirectory.close();
/*     */       }
/*     */     }
/*     */     catch (NamingException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public Vector getAllUsers(String[] a_saBaseList, String a_sUsernameProperty, String a_sDNProperty, String a_sEmailProperty, String a_sUserSearchCriteria, long a_lMaxNumber)
/*     */     throws ADException
/*     */   {
/* 223 */     Vector vecResults = new Vector();
/*     */ 
/* 226 */     for (int i = 0; i < a_saBaseList.length; i++)
/*     */     {
/* 228 */       Vector vecResultsForNode = getAllUsers(a_saBaseList[i], a_sUsernameProperty, a_sDNProperty, a_sEmailProperty, a_sUserSearchCriteria, a_lMaxNumber);
/*     */ 
/* 236 */       for (int iCount = 0; iCount < vecResultsForNode.size(); iCount++)
/*     */       {
/* 238 */         vecResults.add(vecResultsForNode.get(iCount));
/*     */       }
/*     */     }
/*     */ 
/* 242 */     return vecResults;
/*     */   }
/*     */ 
/*     */   public Vector getAllUsers(String a_sBase, String a_sUsernameProperty, String a_sDNProperty, String a_sEmailProperty, String a_sUserSearchCriteria, long a_lMaxNumber)
/*     */     throws ADException
/*     */   {
/* 266 */     Vector vecUsers = new Vector();
/*     */ 
/* 269 */     String[] attrs = { "cn", a_sDNProperty, a_sUsernameProperty, "displayName", a_sEmailProperty, "givenName", "sn", "company", "memberOf" };
/*     */ 
/* 280 */     SearchControls ctls = new SearchControls();
/* 281 */     ctls.setReturningAttributes(attrs);
/* 282 */     ctls.setSearchScope(2);
/* 283 */     ctls.setCountLimit(a_lMaxNumber);
/*     */ 
/* 285 */     boolean bDoPaging = ActiveDirectorySettings.getAdLdapSupportsPaging();
/* 286 */     int iPageSize = ActiveDirectorySettings.getAdLdapPageSize();
/*     */ 
/* 288 */     if (bDoPaging)
/*     */     {
/* 290 */       vecUsers = getAllUsersWithPaging(iPageSize, a_sBase, a_sUserSearchCriteria, ctls, a_sDNProperty, a_sUsernameProperty, a_sEmailProperty);
/*     */     }
/*     */     else
/*     */     {
/* 300 */       vecUsers = getAllUsersNoPaging(a_sBase, a_sUserSearchCriteria, ctls, a_sDNProperty, a_sUsernameProperty, a_sEmailProperty);
/*     */     }
/*     */ 
/* 308 */     return vecUsers;
/*     */   }
/*     */ 
/*     */   private Vector getAllUsersNoPaging(String a_sBase, String a_sUserSearchCriteria, SearchControls a_ctls, String a_sDNProperty, String a_sUsernameProperty, String a_sEmailProperty)
/*     */     throws ADException
/*     */   {
/* 338 */     String ksMethodName = "getAllUsersNoPaging";
/* 339 */     Vector vecUsers = new Vector();
/*     */     try
/*     */     {
/* 344 */       NamingEnumeration enList = this.m_dcActiveDirectory.search(a_sBase, a_sUserSearchCriteria, a_ctls);
/*     */ 
/* 350 */       while (enList.hasMoreElements())
/*     */       {
/* 353 */         SearchResult srUser = (SearchResult)enList.nextElement();
/*     */ 
/* 355 */         ADUser user = populateUser(srUser, a_sDNProperty, a_sUsernameProperty, a_sEmailProperty);
/*     */ 
/* 357 */         vecUsers.add(user);
/*     */       }
/*     */     }
/*     */     catch (NamingException e)
/*     */     {
/* 362 */       throw new ConnectException("ActiveDirectoryBridge.getAllUsersNoPaging : " + e.getMessage());
/*     */     }
/*     */ 
/* 365 */     return vecUsers;
/*     */   }
/*     */ 
/*     */   private Vector getAllUsersWithPaging(int a_iPageSize, String a_sBase, String a_sUserSearchCriteria, SearchControls a_ctls, String a_sDNProperty, String a_sUsernameProperty, String a_sEmailProperty)
/*     */     throws ADException
/*     */   {
/* 378 */     String ksMethodName = "getAllUsersWithPaging";
/* 379 */     Vector vecUsers = new Vector();
/* 380 */     Control[] ctlsRequest = null;
/*     */     try
/*     */     {
/* 385 */       ctlsRequest = new Control[1];
/* 386 */       ctlsRequest[0] = new PagedResultsControl(a_iPageSize, true);
/* 387 */       this.m_dcActiveDirectory.setRequestControls(ctlsRequest);
/*     */ 
/* 389 */       byte[] cookie = null;
/*     */       do
/*     */       {
/* 394 */         NamingEnumeration enList = this.m_dcActiveDirectory.search(a_sBase, a_sUserSearchCriteria, a_ctls);
/*     */ 
/* 400 */         while (enList.hasMoreElements())
/*     */         {
/* 403 */           SearchResult srUser = (SearchResult)enList.nextElement();
/*     */ 
/* 405 */           ADUser user = populateUser(srUser, a_sDNProperty, a_sUsernameProperty, a_sEmailProperty);
/*     */ 
/* 407 */           vecUsers.add(user);
/*     */         }
/*     */ 
/* 411 */         Control[] controls = this.m_dcActiveDirectory.getResponseControls();
/* 412 */         if (controls != null)
/*     */         {
/* 414 */           for (int i = 0; i < controls.length; i++)
/*     */           {
/* 416 */             if (!(controls[i] instanceof PagedResultsResponseControl))
/*     */               continue;
/* 418 */             PagedResultsResponseControl prrc = (PagedResultsResponseControl)controls[i];
/*     */ 
/* 420 */             cookie = prrc.getCookie();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 426 */         ctlsRequest = new Control[1];
/* 427 */         ctlsRequest[0] = new PagedResultsControl(a_iPageSize, cookie, true);
/* 428 */         this.m_dcActiveDirectory.setRequestControls(ctlsRequest);
/*     */       }
/* 430 */       while (cookie != null);
/*     */     }
/*     */     catch (NamingException e)
/*     */     {
/* 435 */       throw new ConnectException("ActiveDirectoryBridge.getAllUsersWithPaging : " + e.getMessage());
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 439 */       throw new ConnectException("ActiveDirectoryBridge.getAllUsersWithPaging IOException in paging control: " + e.getMessage());
/*     */     }
/*     */ 
/* 442 */     return vecUsers;
/*     */   }
/*     */ 
/*     */   private ADUser populateUser(SearchResult srUser, String a_sDNProperty, String a_sUsernameProperty, String a_sEmailProperty)
/*     */     throws NamingException
/*     */   {
/* 468 */     ADUser user = new ADUser();
/* 469 */     user.setCN(getAttributeValue(srUser.getAttributes().get("cn")));
/*     */ 
/* 472 */     if ((a_sDNProperty != null) && (a_sDNProperty.length() > 0))
/*     */     {
/* 474 */       user.setDistinguishedName(getAttributeValue(srUser.getAttributes().get(a_sDNProperty)));
/*     */     }
/*     */     else
/*     */     {
/* 480 */       user.setDistinguishedName(srUser.getNameInNamespace());
/*     */     }
/*     */ 
/* 483 */     user.setAccountName(getAttributeValue(srUser.getAttributes().get(a_sUsernameProperty)));
/* 484 */     user.setDisplayName(getAttributeValue(srUser.getAttributes().get("displayName")));
/* 485 */     user.setEmailAddress(getAttributeValue(srUser.getAttributes().get(a_sEmailProperty)));
/* 486 */     user.setForename(getAttributeValue(srUser.getAttributes().get("givenName")));
/* 487 */     user.setSurname(getAttributeValue(srUser.getAttributes().get("sn")));
/* 488 */     user.setCompany(getAttributeValue(srUser.getAttributes().get("company")));
/*     */ 
/* 491 */     user.setMemberOf(getAllAttributeValue(srUser.getAttributes().get("memberOf")));
/*     */ 
/* 493 */     return user;
/*     */   }
/*     */ 
/*     */   private String getAttributeValue(Attribute a_att)
/*     */     throws NamingException
/*     */   {
/* 512 */     String sValue = null;
/*     */ 
/* 514 */     if (a_att != null)
/*     */     {
/* 516 */       sValue = (String)a_att.get();
/*     */     }
/*     */ 
/* 519 */     return sValue;
/*     */   }
/*     */ 
/*     */   private String[] getAllAttributeValue(Attribute a_att)
/*     */     throws NamingException
/*     */   {
/* 537 */     String[] saValues = null;
/*     */ 
/* 539 */     if (a_att != null)
/*     */     {
/* 541 */       Vector vecValues = new Vector();
/* 542 */       NamingEnumeration aeValues = a_att.getAll();
/*     */ 
/* 544 */       while (aeValues.hasMoreElements())
/*     */       {
/* 547 */         vecValues.add(aeValues.nextElement());
/*     */       }
/*     */ 
/* 550 */       saValues = new String[vecValues.size()];
/*     */ 
/* 552 */       for (int i = 0; i < vecValues.size(); i++)
/*     */       {
/* 554 */         saValues[i] = ((String)vecValues.get(i));
/*     */       }
/*     */     }
/*     */ 
/* 558 */     return saValues;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.exchange.service.ActiveDirectoryBridge
 * JD-Core Version:    0.6.0
 */