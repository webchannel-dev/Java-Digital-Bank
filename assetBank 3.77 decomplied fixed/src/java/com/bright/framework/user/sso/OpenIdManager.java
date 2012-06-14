/*     */ package com.bright.framework.user.sso;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.openid4java.association.AssociationException;
/*     */ import org.openid4java.consumer.ConsumerException;
/*     */ import org.openid4java.consumer.ConsumerManager;
/*     */ import org.openid4java.consumer.VerificationResult;
/*     */ import org.openid4java.discovery.DiscoveryException;
/*     */ import org.openid4java.discovery.DiscoveryInformation;
/*     */ import org.openid4java.discovery.Identifier;
/*     */ import org.openid4java.message.AuthRequest;
/*     */ import org.openid4java.message.Message;
/*     */ import org.openid4java.message.MessageException;
/*     */ import org.openid4java.message.MessageExtension;
/*     */ import org.openid4java.message.ParameterList;
/*     */ import org.openid4java.message.sreg.SRegRequest;
/*     */ import org.openid4java.message.sreg.SRegResponse;
/*     */ import org.openid4java.server.RealmVerifier;
/*     */ 
/*     */ public class OpenIdManager extends Bn2Manager
/*     */ {
/*     */   private static final String k_sClassName = "OpenIdManager";
/*     */   private static final String k_sAttribute_Discovered = "discovered";
/*     */   private static final String k_sAttribute_FullName = "fullname";
/*     */   private static final String k_sAttribute_Email = "email";
/*  60 */   private ConsumerManager m_consumerManager = null;
/*     */ 
/*     */   public void startup() throws Bn2Exception
/*     */   {
/*  64 */     super.startup();
/*     */ 
/*  66 */     if (UserSettings.getOpenIdEnabled())
/*     */     {
/*     */       try
/*     */       {
/*  70 */         this.m_consumerManager = new ConsumerManager();
/*     */ 
/*  73 */         this.m_consumerManager.getRealmVerifier().setEnforceRpId(false);
/*     */       }
/*     */       catch (ConsumerException e)
/*     */       {
/*  77 */         this.m_logger.error("OpenIdManager.startup() : Could not create ConsumerManager instance due to exception : " + e.getMessage());
/*  78 */         throw new Bn2Exception("OpenIdManager.startup() : Could not create ConsumerManager instance due to exception : " + e.getMessage(), e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getAuthenticationUrl(String a_sOpenId, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/*  94 */     String sMethodName = "OpenIdManager.getAuthenticationUrl()";
/*  95 */     String sUrl = null;
/*     */     try
/*     */     {
/* 100 */       List discoveries = this.m_consumerManager.discover(a_sOpenId);
/*     */ 
/* 104 */       DiscoveryInformation discovered = this.m_consumerManager.associate(discoveries);
/*     */ 
/* 107 */       a_request.getSession().setAttribute("discovered", discovered);
/*     */ 
/* 110 */       StringBuffer returnURL = BaseSSOPlugin.getSSOCreateSessionActionUrl(a_request);
/*     */ 
/* 112 */       if (a_request.getParameter("forwardUrl") != null)
/*     */       {
/* 114 */         String sForwardUrl = a_request.getParameter("forwardUrl");
/* 115 */         sForwardUrl = sForwardUrl.replaceAll("&", "&amp;");
/* 116 */         returnURL.append("?").append("forwardUrl").append("=").append(sForwardUrl);
/*     */       }
/*     */ 
/* 119 */       SRegRequest sregReq = SRegRequest.createFetchRequest();
/*     */ 
/* 121 */       sregReq.addAttribute("fullname", true);
/* 122 */       sregReq.addAttribute("email", true);
/*     */ 
/* 125 */       AuthRequest authReq = this.m_consumerManager.authenticate(discovered, returnURL.toString());
/* 126 */       authReq.addExtension(sregReq);
/*     */ 
/* 129 */       sUrl = authReq.getDestinationUrl(true);
/*     */     }
/*     */     catch (ConsumerException e)
/*     */     {
/* 134 */       this.m_logger.debug("OpenIdManager.getAuthenticationUrl() : Could not retrieve authentication url due to ConsumerException : " + e.getMessage());
/*     */     }
/*     */     catch (DiscoveryException e)
/*     */     {
/* 139 */       this.m_logger.debug("OpenIdManager.getAuthenticationUrl() : Could not retrieve authentication url due to DiscoveryException : " + e.getMessage());
/*     */     }
/*     */     catch (MessageException e)
/*     */     {
/* 143 */       this.m_logger.error("OpenIdManager.getAuthenticationUrl() : Could not retrieve authentication url due to MessageException : " + e.getMessage());
/* 144 */       throw new Bn2Exception("OpenIdManager.getAuthenticationUrl() : Could not retrieve authentication url due to MessageException" + e.getMessage(), e);
/*     */     }
/*     */ 
/* 147 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public User getVerifiedUser(HttpServletRequest a_request, String a_sPrefix)
/*     */     throws Bn2Exception
/*     */   {
/* 160 */     String sMethodName = "OpenIdManager.getVerifiedUser()";
/*     */ 
/* 162 */     User user = null;
/*     */     try
/*     */     {
/* 168 */       ParameterList openidResp = new ParameterList(a_request.getParameterMap());
/*     */ 
/* 171 */       DiscoveryInformation discovered = (DiscoveryInformation)a_request.getSession().getAttribute("discovered");
/*     */ 
/* 174 */       StringBuffer receivingURL = new StringBuffer(ServletUtil.getApplicationUrl(a_request));
/* 175 */       receivingURL.append("/action/").append("ssoCreateSession");
/*     */ 
/* 178 */       String queryString = a_request.getQueryString();
/*     */ 
/* 180 */       if ((queryString != null) && (queryString.length() > 0))
/*     */       {
/* 182 */         receivingURL.append("?").append(queryString);
/*     */       }
/*     */ 
/* 186 */       VerificationResult verification = this.m_consumerManager.verify(receivingURL.toString(), openidResp, discovered);
/*     */ 
/* 189 */       Identifier verified = verification.getVerifiedId();
/*     */ 
/* 191 */       if (verified != null)
/*     */       {
/* 193 */         user = new User();
/*     */ 
/* 195 */         String sRemoteId = verified.getIdentifier().substring(a_sPrefix.length());
/*     */ 
/* 197 */         if (sRemoteId.endsWith("/"))
/*     */         {
/* 199 */           sRemoteId = sRemoteId.substring(0, sRemoteId.length() - 1);
/*     */         }
/*     */ 
/* 202 */         if (sRemoteId.lastIndexOf("#") > 0)
/*     */         {
/* 204 */           sRemoteId = sRemoteId.substring(0, sRemoteId.lastIndexOf("#"));
/*     */         }
/*     */ 
/* 207 */         user.setRemoteUsername(sRemoteId);
/*     */ 
/* 210 */         user.setUsername(sRemoteId);
/*     */ 
/* 212 */         if (verification.getAuthResponse().hasExtension("http://openid.net/sreg/1.0"))
/*     */         {
/* 214 */           MessageExtension ext = verification.getAuthResponse().getExtension("http://openid.net/sreg/1.0");
/*     */ 
/* 217 */           if ((ext instanceof SRegResponse))
/*     */           {
/* 219 */             SRegResponse fetchResp = (SRegResponse)ext;
/*     */ 
/* 221 */             user.setEmailAddress(fetchResp.getAttributeValue("email"));
/*     */ 
/* 223 */             String sFullName = fetchResp.getAttributeValue("fullname");
/* 224 */             user.setDisplayName(sFullName);
/*     */ 
/* 226 */             String[] asNames = BaseSSOPlugin.parseFullUsername(sFullName);
/* 227 */             user.setForename(asNames[0]);
/* 228 */             user.setSurname(asNames[1]);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (DiscoveryException e)
/*     */     {
/* 235 */       this.m_logger.error("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to DiscoveryException" + e.getMessage());
/* 236 */       throw new Bn2Exception("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to DiscoveryException" + e.getMessage(), e);
/*     */     }
/*     */     catch (MessageException e)
/*     */     {
/* 240 */       this.m_logger.error("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to MessageException" + e.getMessage());
/* 241 */       throw new Bn2Exception("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to MessageException" + e.getMessage(), e);
/*     */     }
/*     */     catch (AssociationException e)
/*     */     {
/* 245 */       this.m_logger.error("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to AssociationException" + e.getMessage());
/* 246 */       throw new Bn2Exception("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to AssociationException" + e.getMessage(), e);
/*     */     }
/*     */ 
/* 249 */     return user;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.OpenIdManager
 * JD-Core Version:    0.6.0
 */