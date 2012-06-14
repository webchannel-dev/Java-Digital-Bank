/*     */ package com.bright.framework.user.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.util.ServletUtil;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
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
/*  59 */   private ConsumerManager m_consumerManager = null;
/*     */ 
/*     */   public void startup() throws Bn2Exception
/*     */   {
/*  63 */     super.startup();
/*     */     try
/*     */     {
/*  67 */       this.m_consumerManager = new ConsumerManager();
/*     */ 
/*  70 */       this.m_consumerManager.getRealmVerifier().setEnforceRpId(false);
/*     */     }
/*     */     catch (ConsumerException e)
/*     */     {
/*  74 */       this.m_logger.error("OpenIdManager.startup() : Could not create ConsumerManager instance due to exception : " + e.getMessage());
/*  75 */       throw new Bn2Exception("OpenIdManager.startup() : Could not create ConsumerManager instance due to exception : " + e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getAuthenticationUrl(String a_sOpenId, HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/*  90 */     String sMethodName = "OpenIdManager.getAuthenticationUrl()";
/*  91 */     String sUrl = null;
/*     */     try
/*     */     {
/*  96 */       List discoveries = this.m_consumerManager.discover(a_sOpenId);
/*     */ 
/* 100 */       DiscoveryInformation discovered = this.m_consumerManager.associate(discoveries);
/*     */ 
/* 103 */       a_request.getSession().setAttribute("discovered", discovered);
/*     */ 
/* 106 */       StringBuffer returnURL = new StringBuffer(ServletUtil.getApplicationUrl(a_request));
/* 107 */       returnURL.append("/action/").append("loginWithOpenIdResponse");
/*     */ 
/* 109 */       if (a_request.getParameter("forwardUrl") != null)
/*     */       {
/* 111 */         String sForwardUrl = a_request.getParameter("forwardUrl");
/* 112 */         sForwardUrl = sForwardUrl.replaceAll("&", "&amp;");
/* 113 */         returnURL.append("?").append("forwardUrl").append("=").append(sForwardUrl);
/*     */       }
/*     */ 
/* 116 */       SRegRequest sregReq = SRegRequest.createFetchRequest();
/*     */ 
/* 118 */       sregReq.addAttribute("fullname", true);
/* 119 */       sregReq.addAttribute("email", true);
/*     */ 
/* 122 */       AuthRequest authReq = this.m_consumerManager.authenticate(discovered, returnURL.toString());
/* 123 */       authReq.addExtension(sregReq);
/*     */ 
/* 126 */       sUrl = authReq.getDestinationUrl(true);
/*     */     }
/*     */     catch (ConsumerException e)
/*     */     {
/* 131 */       this.m_logger.debug("OpenIdManager.getAuthenticationUrl() : Could not retrieve authentication url due to ConsumerException : " + e.getMessage());
/*     */     }
/*     */     catch (DiscoveryException e)
/*     */     {
/* 136 */       this.m_logger.debug("OpenIdManager.getAuthenticationUrl() : Could not retrieve authentication url due to DiscoveryException : " + e.getMessage());
/*     */     }
/*     */     catch (MessageException e)
/*     */     {
/* 140 */       this.m_logger.error("OpenIdManager.getAuthenticationUrl() : Could not retrieve authentication url due to MessageException : " + e.getMessage());
/* 141 */       throw new Bn2Exception("OpenIdManager.getAuthenticationUrl() : Could not retrieve authentication url due to MessageException" + e.getMessage(), e);
/*     */     }
/*     */ 
/* 144 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public User getVerifiedUser(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 157 */     String sMethodName = "OpenIdManager.getVerifiedUser()";
/*     */ 
/* 159 */     User user = null;
/*     */     try
/*     */     {
/* 165 */       ParameterList openidResp = new ParameterList(a_request.getParameterMap());
/*     */ 
/* 168 */       DiscoveryInformation discovered = (DiscoveryInformation)a_request.getSession().getAttribute("discovered");
/*     */ 
/* 171 */       StringBuffer receivingURL = new StringBuffer(ServletUtil.getApplicationUrl(a_request));
/* 172 */       receivingURL.append("/action/").append("loginWithOpenIdResponse");
/*     */ 
/* 175 */       String queryString = a_request.getQueryString();
/*     */ 
/* 177 */       if ((queryString != null) && (queryString.length() > 0))
/*     */       {
/* 179 */         receivingURL.append("?").append(queryString);
/*     */       }
/*     */ 
/* 183 */       VerificationResult verification = this.m_consumerManager.verify(receivingURL.toString(), openidResp, discovered);
/*     */ 
/* 186 */       Identifier verified = verification.getVerifiedId();
/*     */ 
/* 188 */       if (verified != null)
/*     */       {
/* 190 */         user = new User();
/* 191 */         user.setRemoteUsername(verified.getIdentifier());
/*     */ 
/* 193 */         if (verification.getAuthResponse().hasExtension("http://openid.net/sreg/1.0"))
/*     */         {
/* 195 */           MessageExtension ext = verification.getAuthResponse().getExtension("http://openid.net/sreg/1.0");
/*     */ 
/* 198 */           if ((ext instanceof SRegResponse))
/*     */           {
/* 200 */             SRegResponse fetchResp = (SRegResponse)ext;
/*     */ 
/* 202 */             user.setEmailAddress(fetchResp.getAttributeValue("email"));
/*     */ 
/* 204 */             String sFullName = fetchResp.getAttributeValue("fullname");
/*     */ 
/* 207 */             if (StringUtils.isNotEmpty(sFullName))
/*     */             {
/* 209 */               sFullName = sFullName.trim();
/* 210 */               user.setForename(sFullName.substring(0, Math.max(0, sFullName.lastIndexOf(" "))));
/* 211 */               user.setSurname(sFullName.substring(sFullName.lastIndexOf(" ") + 1));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (DiscoveryException e)
/*     */     {
/* 219 */       this.m_logger.error("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to DiscoveryException" + e.getMessage());
/* 220 */       throw new Bn2Exception("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to DiscoveryException" + e.getMessage(), e);
/*     */     }
/*     */     catch (MessageException e)
/*     */     {
/* 224 */       this.m_logger.error("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to MessageException" + e.getMessage());
/* 225 */       throw new Bn2Exception("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to MessageException" + e.getMessage(), e);
/*     */     }
/*     */     catch (AssociationException e)
/*     */     {
/* 229 */       this.m_logger.error("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to AssociationException" + e.getMessage());
/* 230 */       throw new Bn2Exception("OpenIdManager.getVerifiedUser() : Could not retrieve authentication url due to AssociationException" + e.getMessage(), e);
/*     */     }
/*     */ 
/* 233 */     return user;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.service.OpenIdManager
 * JD-Core Version:    0.6.0
 */