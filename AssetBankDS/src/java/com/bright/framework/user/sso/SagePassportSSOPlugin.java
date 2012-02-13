/*     */ package com.bright.framework.user.sso;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.constant.UserSettings;
/*     */ import com.bright.framework.user.form.LoginForm;
/*     */ import com.bright.framework.user.util.sage.Keyring;
/*     */ import com.bright.framework.user.util.sage.SageKey;
/*     */ import com.bright.framework.user.util.sage.SageSigVerify;
/*     */ import com.bright.framework.util.EncodingUtil;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import com.bright.framework.util.ServletUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.TimeZone;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.apache.commons.codec.DecoderException;
/*     */ import org.apache.commons.codec.binary.Hex;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ public class SagePassportSSOPlugin extends BaseSSOPlugin
/*     */   implements SSOPlugin
/*     */ {
/*     */   private static final String k_sClassName = "SagePassportSSOPlugin";
/* 101 */   private SagePassportSSOSettings m_settings = null;
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/* 113 */     super.startup();
/*     */ 
/* 115 */     if (UserSettings.getSagePassportEnabled())
/*     */     {
/* 117 */       this.m_settings = new SagePassportSSOSettings("SagePassportSSOSettings");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSSOMode()
/*     */   {
/* 124 */     return "RemoteSessionCheck";
/*     */   }
/*     */ 
/*     */   public String getLoginJsp()
/*     */   {
/* 131 */     String sJsp = this.m_settings.getLoginJsp();
/* 132 */     return sJsp;
/*     */   }
/*     */ 
/*     */   public void processRequest(HttpServletRequest a_request, LoginForm a_form)
/*     */   {
/* 146 */     String sErrorParam = "SignInState";
/* 147 */     String sErrorState = a_request.getParameter(sErrorParam);
/*     */ 
/* 149 */     String sSignInNameParam = "SignInName";
/* 150 */     String sSignInName = a_request.getParameter(sSignInNameParam);
/*     */ 
/* 152 */     if (StringUtil.stringIsPopulated(sErrorState))
/*     */     {
/* 154 */       if (sErrorState.equalsIgnoreCase("InvalidSignInName"))
/*     */       {
/* 156 */         a_form.setLoginFailed(true);
/*     */       }
/* 158 */       else if (sErrorState.equalsIgnoreCase("WrongPassword"))
/*     */       {
/* 160 */         a_form.setLoginFailed(true);
/*     */       }
/* 162 */       else if (sErrorState.equalsIgnoreCase("UnknownSignInName"))
/*     */       {
/* 164 */         a_form.setLoginFailed(true);
/*     */       }
/* 166 */       else if (sErrorState.equalsIgnoreCase("AccountLockedOut"))
/*     */       {
/* 168 */         a_form.setAccountSuspended(true);
/*     */       }
/* 170 */       else if (sErrorState.equalsIgnoreCase("IsDisabled"))
/*     */       {
/* 172 */         a_form.addError("Passport is currently disabled by administrator");
/*     */       }
/* 174 */       else if (sErrorState.equalsIgnoreCase("EmailNotVerified"))
/*     */       {
/* 176 */         a_form.addError("Your Email is not yet verified - please complete your registration");
/*     */       }
/*     */       else
/*     */       {
/* 180 */         a_form.addError("There was an error authenticating with Passport, please try again or contact the administrator");
/*     */       }
/*     */ 
/* 184 */       a_form.setUsername(sSignInName);
/*     */     }
/*     */   }
/*     */ 
/*     */   public User initialSessionCheck(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/* 199 */     User user = null;
/*     */ 
/* 202 */     Cookie cookie = RequestUtil.getCookieByName(a_request, this.m_settings.getCookieName());
/*     */ 
/* 204 */     String sCookieValue = "";
/*     */ 
/* 207 */     if (cookie != null)
/*     */     {
/* 209 */       sCookieValue = cookie.getValue();
/*     */     }
/*     */ 
/* 213 */     if (StringUtil.stringIsPopulated(sCookieValue))
/*     */     {
/* 218 */       if (!sCookieValue.endsWith("="))
/*     */       {
/* 220 */         sCookieValue = EncodingUtil.padBase64String(sCookieValue);
/*     */       }
/*     */ 
/* 224 */       String sAuthenticationTokenXml = "";
/*     */       try
/*     */       {
/* 228 */         byte[] authenticationTokenBytes = EncodingUtil.base64Decode(sCookieValue);
/*     */ 
/* 230 */         sAuthenticationTokenXml = new String(authenticationTokenBytes, "UTF8");
/*     */       }
/*     */       catch (UnsupportedEncodingException e)
/*     */       {
/* 235 */         this.m_logger.error("SagePassportSSOPlugin : Unable to decode cookie: ", e);
/* 236 */         return null;
/*     */       }
/*     */ 
/* 240 */       boolean bValid = validateAuthenticationToken(sAuthenticationTokenXml);
/*     */ 
/* 243 */       if (bValid)
/*     */       {
/* 245 */         String sEmail = "";
/* 246 */         String sName = "";
/* 247 */         String sXmlToSend = "";
/* 248 */         String sMemberId = "";
/*     */         try
/*     */         {
/* 253 */           Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(sAuthenticationTokenXml)));
/*     */ 
/* 255 */           sEmail = d.getElementsByTagName(this.m_settings.getCookieXmlEmail()).item(0).getTextContent();
/* 256 */           sName = d.getElementsByTagName(this.m_settings.getCookieXmlName()).item(0).getTextContent();
/* 257 */           sMemberId = d.getElementsByTagName(this.m_settings.getCookieXmlMemberId()).item(0).getTextContent();
/*     */ 
/* 262 */           sXmlToSend = this.m_settings.getQueryMemberXml();
/*     */ 
/* 265 */           sXmlToSend = sXmlToSend.replace("{email}", sEmail);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 271 */           this.m_logger.error("SagePassportSSOPlugin : Unable to parse XML from cookie: ", e);
/* 272 */           return null;
/*     */         }
/*     */ 
/* 276 */         HttpURLConnection connection = null;
/*     */ 
/* 278 */         boolean bQueryMemberValid = false;
/*     */         try
/*     */         {
/* 282 */           URL url = new URL(this.m_settings.getQueryMemberUrl());
/* 283 */           connection = (HttpURLConnection)url.openConnection();
/* 284 */           connection.setRequestMethod("POST");
/* 285 */           connection.setRequestProperty("Content-Type", "text/xml");
/* 286 */           connection.setRequestProperty("Content-Length", "" + Integer.toString(sXmlToSend.getBytes().length));
/* 287 */           connection.setRequestProperty("Content-Language", "en-US");
/* 288 */           connection.setUseCaches(false);
/* 289 */           connection.setDoInput(true);
/* 290 */           connection.setDoOutput(true);
/*     */ 
/* 292 */           DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
/* 293 */           wr.writeBytes(sXmlToSend);
/* 294 */           wr.flush();
/* 295 */           wr.close();
/*     */ 
/* 298 */           InputStream is = connection.getInputStream();
/* 299 */           BufferedReader rd = new BufferedReader(new InputStreamReader(is));
/*     */ 
/* 302 */           StringBuffer response = new StringBuffer();
/*     */           String line;
/* 304 */           while ((line = rd.readLine()) != null)
/*     */           {
/* 306 */             response.append(line);
/*     */           }
/*     */ 
/* 309 */           rd.close();
/*     */ 
/* 311 */           String sResponse = response.toString();
/*     */ 
/* 315 */           Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(sResponse)));
/*     */ 
/* 317 */           String sState = d.getElementsByTagName(this.m_settings.getQueryMemberXmlState()).item(0).getTextContent();
/*     */ 
/* 319 */           if (sState.equalsIgnoreCase(this.m_settings.getQueryMemberValid()))
/*     */           {
/* 321 */             bQueryMemberValid = true;
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 326 */           this.m_logger.error("SagePassportSSOPlugin : Invalid response from QueryMember: ", e);
/* 327 */           return null;
/*     */         }
/*     */ 
/* 332 */         if (bQueryMemberValid)
/*     */         {
/* 334 */           user = new User();
/* 335 */           String[] asNames = BaseSSOPlugin.parseFullUsername(sName);
/* 336 */           user.setForename(asNames[0]);
/* 337 */           user.setSurname(asNames[1]);
/* 338 */           user.setDisplayName(sName);
/* 339 */           user.setEmailAddress(sEmail);
/* 340 */           user.setRemoteUsername(sMemberId);
/* 341 */           user.setUsername(sEmail);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 349 */     return user;
/*     */   }
/*     */ 
/*     */   public String getSessionCheckUrl(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 368 */     String sUrl = getSagePassportUrl() + "?";
/*     */ 
/* 371 */     String sCreateSessionUrl = BaseSSOPlugin.getSSOCreateSessionActionUrl(a_request).toString();
/*     */ 
/* 374 */     String sDestroySessionUrl = BaseSSOPlugin.getSSODestroySessionActionUrl(a_request).toString();
/*     */ 
/* 377 */     String sSSOLoginFormUrl = BaseSSOPlugin.getSSOLoginFormUrl(a_request).toString();
/*     */ 
/* 380 */     String sReturnUrl = "";
/* 381 */     String sPassThrough = "";
/* 382 */     String sForwardUrl = a_form.getForwardUrl();
/* 383 */     if (sForwardUrl != null)
/*     */     {
/* 385 */       sReturnUrl = RequestUtil.getActionPart(sForwardUrl);
/*     */ 
/* 388 */       sPassThrough = RequestUtil.getQueryStringPart(sForwardUrl);
/* 389 */       sPassThrough = new String(Hex.encodeHex(sPassThrough.getBytes()));
/*     */     }
/*     */ 
/* 393 */     sUrl = sUrl + "OperationID=" + getOperationId() + "&";
/* 394 */     sUrl = sUrl + "CSCreate=" + sCreateSessionUrl + "&";
/* 395 */     sUrl = sUrl + "CSDestroy=" + sDestroySessionUrl + "&";
/* 396 */     sUrl = sUrl + "ReturnURL=" + sReturnUrl + "&";
/* 397 */     sUrl = sUrl + "PassThrough=" + sPassThrough + "&";
/* 398 */     sUrl = sUrl + "NoSessionURL=" + sSSOLoginFormUrl;
/*     */ 
/* 400 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public String getLoginUrlForRedirect(DBTransaction a_dbTransaction, HttpServletRequest a_request, LoginForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/* 419 */     String sUrl = getSagePassportUrl() + "?";
/*     */ 
/* 422 */     String sCreateSessionUrl = BaseSSOPlugin.getSSOCreateSessionActionUrl(a_request).toString();
/*     */ 
/* 425 */     String sDestroySessionUrl = BaseSSOPlugin.getSSODestroySessionActionUrl(a_request).toString();
/*     */ 
/* 428 */     String sSSOLoginFormUrl = BaseSSOPlugin.getSSOLoginFormUrl(a_request).toString();
/*     */ 
/* 431 */     String sReturnUrl = "";
/* 432 */     String sPassThrough = "";
/* 433 */     if (a_request.getParameter("forwardUrl") != null)
/*     */     {
/* 435 */       String sForwardUrl = a_request.getParameter("forwardUrl");
/*     */ 
/* 437 */       sReturnUrl = RequestUtil.getActionPart(sForwardUrl);
/*     */ 
/* 440 */       sPassThrough = RequestUtil.getQueryStringPart(sForwardUrl);
/* 441 */       sPassThrough = new String(Hex.encodeHex(sPassThrough.getBytes()));
/*     */     }
/*     */ 
/* 445 */     sUrl = sUrl + "OperationID=" + getOperationId() + "&";
/* 446 */     sUrl = sUrl + "CSCreate=" + sCreateSessionUrl + "&";
/* 447 */     sUrl = sUrl + "CSDestroy=" + sDestroySessionUrl + "&";
/* 448 */     sUrl = sUrl + "ReturnURL=" + sReturnUrl + "&";
/* 449 */     sUrl = sUrl + "PassThrough=" + sPassThrough + "&";
/* 450 */     sUrl = sUrl + "ErrorURL=" + sSSOLoginFormUrl + "&";
/* 451 */     sUrl = sUrl + "SignInName=" + a_form.getUsername() + "&";
/* 452 */     sUrl = sUrl + "Password=" + a_form.getPassword();
/*     */ 
/* 454 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public String getLoginBaseUrl()
/*     */   {
/* 465 */     String sBaseUrl = getSagePassportUrl();
/* 466 */     return sBaseUrl;
/*     */   }
/*     */ 
/*     */   public HashMap getLoginFormForPost(HttpServletRequest a_request)
/*     */   {
/* 480 */     HashMap hmForm = new HashMap();
/*     */ 
/* 483 */     String sCreateSessionUrl = BaseSSOPlugin.getSSOCreateSessionActionUrl(a_request).toString();
/*     */ 
/* 486 */     String sDestroySessionUrl = BaseSSOPlugin.getSSODestroySessionActionUrl(a_request).toString();
/*     */ 
/* 489 */     String sSSOLoginFormUrl = BaseSSOPlugin.getSSOLoginFormUrl(a_request).toString();
/*     */ 
/* 492 */     String sReturnUrl = "";
/* 493 */     String sPassThrough = "";
/* 494 */     if (a_request.getParameter("forwardUrl") != null)
/*     */     {
/* 496 */       String sForwardUrl = a_request.getParameter("forwardUrl");
/*     */ 
/* 498 */       sReturnUrl = RequestUtil.getActionPart(sForwardUrl);
/*     */ 
/* 501 */       sPassThrough = RequestUtil.getQueryStringPart(sForwardUrl);
/* 502 */       sPassThrough = new String(Hex.encodeHex(sPassThrough.getBytes()));
/*     */     }
/*     */ 
/* 506 */     hmForm.put("OperationID", getOperationId());
/* 507 */     hmForm.put("CSCreate", sCreateSessionUrl);
/* 508 */     hmForm.put("CSDestroy", sDestroySessionUrl);
/* 509 */     hmForm.put("ReturnURL", sReturnUrl);
/* 510 */     hmForm.put("PassThrough", sPassThrough);
/* 511 */     hmForm.put("ErrorURL", sSSOLoginFormUrl);
/*     */ 
/* 513 */     return hmForm;
/*     */   }
/*     */ 
/*     */   public boolean validateForm(LoginForm a_form)
/*     */   {
/* 526 */     boolean bValid = (StringUtil.stringIsPopulated(a_form.getUsername())) && (StringUtil.stringIsPopulated(a_form.getPassword()));
/* 527 */     return bValid;
/*     */   }
/*     */ 
/*     */   public String getForwardUrl(HttpServletRequest a_request)
/*     */   {
/* 543 */     String ksMethodName = "SagePassportSSOPlugin.getForwardUrl";
/*     */ 
/* 545 */     String sForwardUrl = "";
/*     */ 
/* 547 */     String sReturnUrl = a_request.getParameter("ReturnURL");
/* 548 */     String sPassThrough = a_request.getParameter("PassThrough");
/*     */ 
/* 550 */     if (StringUtil.stringIsPopulated(sPassThrough))
/*     */     {
/* 553 */       String sQueryString = "";
/*     */       try
/*     */       {
/* 556 */         sQueryString = new String(Hex.decodeHex(sPassThrough.toCharArray()));
/*     */       }
/*     */       catch (DecoderException e)
/*     */       {
/* 560 */         this.m_logger.error("SagePassportSSOPlugin.getForwardUrl : Could not decode the passthrough:" + sPassThrough);
/*     */       }
/*     */ 
/* 564 */       if (StringUtil.stringIsPopulated(sReturnUrl))
/*     */       {
/* 566 */         sQueryString = sQueryString.replaceAll("&amp;", "&");
/* 567 */         sForwardUrl = sReturnUrl + "?" + sQueryString;
/*     */       }
/*     */     }
/*     */ 
/* 571 */     return sForwardUrl;
/*     */   }
/*     */ 
/*     */   public User getRemoteUser(HttpServletRequest a_request)
/*     */     throws Bn2Exception
/*     */   {
/* 586 */     String ksMethodName = "SagePassportSSOPlugin.getRemoteUser";
/*     */ 
/* 588 */     User user = null;
/* 589 */     boolean bValid = true;
/*     */ 
/* 592 */     String sMemberId = a_request.getParameter("MemberID");
/* 593 */     String sSignInName = a_request.getParameter("SignInName");
/* 594 */     String sUserName = a_request.getParameter("UserName");
/* 595 */     String sReturnURL = a_request.getParameter("ReturnURL");
/* 596 */     String sNotValidBefore = a_request.getParameter("NotValidBefore");
/* 597 */     String sNotValidAfter = a_request.getParameter("NotValidAfter");
/* 598 */     String sSigner = a_request.getParameter("Signer");
/* 599 */     String sSignature = a_request.getParameter("Signature");
/*     */ 
/* 609 */     byte[] authHash = computeAuthHash(a_request);
/* 610 */     boolean bValidSignature = validateSignature(authHash, sSignature, sSigner);
/* 611 */     if (!bValidSignature)
/*     */     {
/* 613 */       bValid = false;
/* 614 */       this.m_logger.warn("SagePassportSSOPlugin.getRemoteUser : Invalid signature from:" + sSigner);
/*     */     }
/*     */ 
/* 618 */     boolean bValidDates = validateDates(sNotValidBefore, sNotValidAfter);
/* 619 */     if (!bValidDates)
/*     */     {
/* 621 */       bValid = false;
/* 622 */       this.m_logger.warn("SagePassportSSOPlugin.getRemoteUser : Invalid date range:" + sNotValidBefore + " - " + sNotValidAfter);
/*     */     }
/*     */ 
/* 627 */     boolean bReturnURLValid = validateReturnUrl(sReturnURL, a_request);
/* 628 */     if (!bReturnURLValid)
/*     */     {
/* 630 */       bValid = false;
/* 631 */       this.m_logger.warn("SagePassportSSOPlugin.getRemoteUser : Invalid return url:" + sReturnURL);
/*     */     }
/*     */ 
/* 636 */     if (bValid)
/*     */     {
/* 638 */       user = new User();
/*     */ 
/* 640 */       user.setRemoteUsername(sMemberId);
/* 641 */       user.setEmailAddress(sSignInName);
/* 642 */       user.setUsername(sSignInName);
/*     */ 
/* 644 */       String[] asNames = BaseSSOPlugin.parseFullUsername(sUserName);
/* 645 */       user.setForename(asNames[0]);
/* 646 */       user.setSurname(asNames[1]);
/* 647 */       user.setDisplayName(sUserName);
/*     */     }
/*     */ 
/* 655 */     return user;
/*     */   }
/*     */ 
/*     */   public void doPreLogoutActions(HttpServletResponse a_response)
/*     */   {
/* 664 */     Cookie cookie = new Cookie(this.m_settings.getCookieName(), null);
/* 665 */     cookie.setMaxAge(0);
/* 666 */     cookie.setDomain(this.m_settings.getCookieDomain());
/* 667 */     cookie.setPath(this.m_settings.getCookiePath());
/* 668 */     a_response.addCookie(cookie);
/*     */   }
/*     */ 
/*     */   public String getLogoutUrl(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */   {
/* 674 */     String sUrl = this.m_settings.getLogoutUrl();
/*     */ 
/* 677 */     String sSSOLoginFormUrl = BaseSSOPlugin.getSSOLoginFormUrl(a_request).toString();
/*     */ 
/* 679 */     sUrl = sUrl + "?ReturnURL=" + sSSOLoginFormUrl;
/* 680 */     return sUrl;
/*     */   }
/*     */ 
/*     */   public void doDestroyAction(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */   {
/*     */     try
/*     */     {
/* 704 */       String sPath = GlobalSettings.getApplicationPath() + this.m_settings.getLogoutImagePath();
/* 705 */       File file = new File(sPath);
/* 706 */       BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
/* 707 */       byte[] bytes = new byte[in.available()];
/* 708 */       in.read(bytes);
/* 709 */       in.close();
/*     */ 
/* 711 */       a_response.setContentType("image/gif");
/* 712 */       a_response.setHeader("Content-disposition", "inline; filename=\"" + sPath + "\"");
/* 713 */       a_response.setContentLength(bytes.length);
/*     */ 
/* 715 */       OutputStream out = a_response.getOutputStream();
/* 716 */       out.write(bytes);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getOperationId()
/*     */   {
/* 730 */     return this.m_settings.getOperationId();
/*     */   }
/*     */ 
/*     */   private String getSagePassportUrl()
/*     */   {
/* 735 */     String sUrl = this.m_settings.getSagePassportUrl();
/* 736 */     return sUrl;
/*     */   }
/*     */ 
/*     */   private boolean validateSignature(byte[] a_authHash, String a_sSignature, String a_sSigner)
/*     */     throws Bn2Exception
/*     */   {
/* 750 */     String ksMethodName = "SagePassportSSOPlugin.validateSignature";
/*     */ 
/* 752 */     SageKey sageKey = Keyring.instance.getPublicKeyForSite(a_sSigner);
/* 753 */     if (sageKey == null)
/*     */     {
/* 755 */       this.m_logger.error("SagePassportSSOPlugin.validateSignature : Could not get public key for site:" + a_sSigner);
/* 756 */       return false;
/*     */     }
/*     */ 
/* 759 */     byte[] decHash = sageKey.decryptHash(a_sSignature);
/* 760 */     boolean bMatch = Arrays.equals(decHash, a_authHash);
/* 761 */     return bMatch;
/*     */   }
/*     */ 
/*     */   private byte[] computeAuthHash(HttpServletRequest req)
/*     */     throws Bn2Exception
/*     */   {
/* 773 */     String ksMethodName = "SagePassportSSOPlugin.computeAuthHash";
/*     */ 
/* 775 */     byte[] authHash = null;
/*     */ 
/* 778 */     List values = new ArrayList();
/* 779 */     for (Enumeration i = req.getParameterNames(); i.hasMoreElements(); )
/*     */     {
/* 781 */       String name = (String)i.nextElement();
/* 782 */       if (!name.equals("Signature"))
/*     */       {
/* 784 */         String value = req.getParameter(name).toLowerCase();
/* 785 */         values.add(value);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 790 */     Collections.sort(values);
/*     */ 
/* 793 */     StringBuffer sb = new StringBuffer();
/* 794 */     for (Iterator i = values.iterator(); i.hasNext(); )
/*     */     {
/* 796 */       String s = (String)i.next();
/* 797 */       sb.append(s);
/*     */     }
/* 799 */     String concat = sb.toString();
/*     */     byte[] authStringB;
/*     */     try
/*     */     {
/* 805 */       authStringB = concat.getBytes("iso-8859-1");
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 809 */       this.m_logger.error("SagePassportSSOPlugin.computeAuthHash : Could not validate signature due to UnsupportedEncodingException" + e.getMessage());
/* 810 */       throw new Bn2Exception("SagePassportSSOPlugin.computeAuthHash : Could not validate signature due to UnsupportedEncodingException" + e.getMessage(), e);
/*     */     }
/*     */     try
/*     */     {
/* 814 */       authHash = MessageDigest.getInstance("MD5").digest(authStringB);
/*     */     }
/*     */     catch (NoSuchAlgorithmException e)
/*     */     {
/* 818 */       this.m_logger.error("SagePassportSSOPlugin.computeAuthHash : Could not validate signature due to NoSuchAlgorithmException" + e.getMessage());
/* 819 */       throw new Bn2Exception("SagePassportSSOPlugin.computeAuthHash : Could not validate signature due to NoSuchAlgorithmException" + e.getMessage(), e);
/*     */     }
/*     */ 
/* 822 */     return authHash;
/*     */   }
/*     */ 
/*     */   public boolean validateDates(String sNotValidBefore, String sNotValidAfter)
/*     */     throws Bn2Exception
/*     */   {
/* 837 */     String ksMethodName = "SagePassportSSOPlugin.validateDates";
/*     */ 
/* 839 */     DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
/* 840 */     df.setTimeZone(TimeZone.getTimeZone("GMT"));
/* 841 */     Date dateNotValidBefore = null;
/*     */     try
/*     */     {
/* 844 */       dateNotValidBefore = df.parse(sNotValidBefore);
/*     */     }
/*     */     catch (ParseException e)
/*     */     {
/* 848 */       this.m_logger.error("SagePassportSSOPlugin.validateDates : Could not validate dates due to ParseException in date before:" + sNotValidBefore);
/* 849 */       throw new Bn2Exception("SagePassportSSOPlugin.validateDates : Could not validate dates due to ParseException in date before:" + sNotValidBefore, e);
/*     */     }
/* 851 */     long lNotValidBefore = dateNotValidBefore.getTime();
/*     */ 
/* 853 */     Date dateNotValidAfter = null;
/*     */     try
/*     */     {
/* 856 */       dateNotValidAfter = df.parse(sNotValidAfter);
/*     */     }
/*     */     catch (ParseException e)
/*     */     {
/* 860 */       this.m_logger.error("SagePassportSSOPlugin.validateDates : Could not validate dates due to ParseException in date after:" + sNotValidAfter);
/* 861 */       throw new Bn2Exception("SagePassportSSOPlugin.validateDates : Could not validate dates due to ParseException in date after:" + sNotValidAfter, e);
/*     */     }
/* 863 */     long lNotValidAfter = dateNotValidAfter.getTime();
/*     */ 
/* 865 */     Date dateNow = new Date();
/* 866 */     long lDateNow = dateNow.getTime();
/*     */ 
/* 868 */     boolean bTooEarly = lDateNow < lNotValidBefore;
/* 869 */     if (bTooEarly)
/*     */     {
/* 871 */       this.m_logger.warn("SagePassportSSOPlugin.validateDates : Signature not valid, now=" + df.format(dateNow) + ", not valid before=" + sNotValidBefore);
/*     */     }
/* 873 */     boolean bTooLate = lDateNow > lNotValidAfter;
/* 874 */     if (bTooLate)
/*     */     {
/* 876 */       this.m_logger.warn("SagePassportSSOPlugin.validateDates : Signature not valid, now=" + df.format(dateNow) + ", not valid after=" + sNotValidAfter);
/*     */     }
/*     */ 
/* 879 */     boolean bValid = (!bTooLate) && (!bTooEarly);
/* 880 */     return bValid;
/*     */   }
/*     */ 
/*     */   private boolean validateReturnUrl(String sReturnURL, HttpServletRequest a_request)
/*     */   {
/* 892 */     boolean bReturnURLValid = true;
/*     */ 
/* 894 */     if (StringUtil.stringIsPopulated(sReturnURL))
/*     */     {
/* 896 */       String sAppBase = ServletUtil.getApplicationUrl(a_request);
/*     */ 
/* 898 */       int iLengthReturnUrl = sReturnURL.length();
/* 899 */       int iLengthAppBase = sAppBase.length();
/*     */ 
/* 901 */       bReturnURLValid = (iLengthReturnUrl >= iLengthAppBase) && (sAppBase.substring(0, iLengthAppBase).equals(sAppBase));
/*     */     }
/*     */ 
/* 904 */     return bReturnURLValid;
/*     */   }
/*     */ 
/*     */   private boolean validateAuthenticationToken(String authenticationToken)
/*     */   {
/* 913 */     boolean bValid = true;
/*     */     try
/*     */     {
/* 917 */       Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(authenticationToken)));
/*     */ 
/* 920 */       NodeList nodeList = d.getElementsByTagName("CreateSession");
/* 921 */       if (nodeList != null)
/*     */       {
/* 923 */         nodeList = d.getElementsByTagName("CreateSession").item(0).getChildNodes();
/*     */       }
/*     */       else
/*     */       {
/* 927 */         this.m_logger.error("SagePassportSSOPlugin : Invalid username/password");
/* 928 */         bValid = false;
/*     */       }
/*     */ 
/* 931 */       if (nodeList != null)
/*     */       {
/* 933 */         Calendar cal = Calendar.getInstance();
/* 934 */         SageSigVerify sageSigVerify = new SageSigVerify(nodeList);
/* 935 */         sageSigVerify.validateSignatureOnly();
/*     */       }
/*     */       else
/*     */       {
/* 939 */         this.m_logger.error("SagePassportSSOPlugin : Invalid validation signature");
/* 940 */         bValid = false;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 945 */       this.m_logger.error("SagePassportSSOPlugin : Unable to validate authentication token: ", e);
/* 946 */       bValid = false;
/*     */     }
/*     */ 
/* 949 */     return bValid;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.user.sso.SagePassportSSOPlugin
 * JD-Core Version:    0.6.0
 */