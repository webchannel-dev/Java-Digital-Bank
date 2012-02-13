/*     */ package com.bright.assetbank.application.servlet;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bn2web.common.servlet.Bn2ActionServlet;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.ABUserProfileFactory;
/*     */ import com.bright.framework.util.HTTPUtil;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import com.websina.license.LicenseManager;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class ApplicationActionServlet extends Bn2ActionServlet
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String k_sEndorsedLibsDirectory = "WEB-INF/manager-config/endorsed-lib";
/*     */   private static final String k_sSystemProperty_EndorsedDirs = "java.endorsed.dirs";
/*     */   private static final String c_ksInitParam_NoStoreGetActions = "noStoreGetActions";
/*     */   private static final String c_ksClassName = "ApplicationActionServlet";
/*  66 */   HashSet m_noStoreActionSet = null;
/*     */ 
/*     */   public void init()
/*     */     throws ServletException
/*     */   {
/*     */     try
/*     */     {
/*  82 */       setupGlobalSettings();
/*     */     }
/*     */     catch (Throwable te)
/*     */     {
/*  87 */       System.out.println("ApplicationActionServlet.init: Bn2Exception: " + te.getMessage());
/*     */     }
/*     */ 
/*  91 */     String[] noStoreActions = getServletConfig().getInitParameter("noStoreGetActions").split("[,\t\n ]+");
/*  92 */     this.m_noStoreActionSet = new HashSet((int)((noStoreActions.length + 1) * 1.5D));
/*  93 */     this.m_noStoreActionSet.addAll(Arrays.asList(noStoreActions));
/*     */ 
/*  95 */     boolean bSuspendLicenseValidation = false;
/*  96 */     boolean bLicenseValid = false;
/*  97 */     boolean bLicenseException = false;
/*  98 */     int iLicenseDaysLeft = 0;
/*  99 */     String sLicenseDate = "";
/* 100 */     String sLicenseType = "";
/* 101 */     boolean bLicenseNeverExpires = false;
/* 102 */     boolean bLicenseIPUnbound = false;
/* 103 */     boolean bLicenseWebappUnbound = false;
/*     */ 
/* 106 */     String sLicenseIPs = "";
/*     */ 
/* 109 */     String sLicenseWebapps = "";
/*     */     try
/*     */     {
/* 114 */       LicenseManager licManager = LicenseManager.getInstance();
/* 115 */       bLicenseValid = licManager.isValid();
/* 116 */       iLicenseDaysLeft = licManager.daysLeft();
/* 117 */       sLicenseIPs = licManager.getFeature("IP");
/* 118 */       sLicenseWebapps = licManager.getFeature("Webapp");
/* 119 */       sLicenseDate = licManager.getFeature("Expiration");
/* 120 */       sLicenseType = licManager.getFeature("Type");
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 124 */       bLicenseValid = false;
/* 125 */       bLicenseException = true;
/*     */     }
/*     */ 
/* 128 */     bLicenseNeverExpires = (iLicenseDaysLeft == 0) && ((sLicenseDate == null) || (sLicenseDate.length() == 0) || (sLicenseDate.compareToIgnoreCase("never") == 0));
/* 129 */     bLicenseIPUnbound = (sLicenseIPs == null) || (sLicenseIPs.length() == 0);
/* 130 */     bLicenseWebappUnbound = (sLicenseWebapps == null) || (sLicenseWebapps.length() == 0);
/*     */ 
/* 133 */     boolean bIPValid = false;
/* 134 */     boolean bIPException = false;
/* 135 */     String sLocalIPs = "";
/* 136 */     Vector vecLocalIPs = new Vector();
/* 137 */     String sValidIP = "";
/*     */ 
/* 139 */     if (bLicenseIPUnbound)
/*     */     {
/* 141 */       bIPValid = true;
/*     */     }
/*     */     else
/*     */     {
/*     */       try
/*     */       {
/* 148 */         Vector vecLicensedIPs = StringUtil.convertToVector(sLicenseIPs, ",");
/*     */ 
/* 151 */         InetAddress ipLocalhost = InetAddress.getLocalHost();
/* 152 */         InetAddress[] arrAddress = InetAddress.getAllByName(ipLocalhost.getHostName());
/* 153 */         for (int i = 0; i < arrAddress.length; i++)
/*     */         {
/* 158 */           String sIP = arrAddress[i].getHostAddress().trim();
/* 159 */           vecLocalIPs.add(sIP);
/* 160 */           sLocalIPs = sLocalIPs + sIP + ", ";
/*     */         }
/*     */ 
/* 164 */         Iterator it = vecLicensedIPs.iterator();
/* 165 */         while (it.hasNext())
/*     */         {
/* 167 */           String sLicensedIP = (String)it.next();
/*     */ 
/* 169 */           Iterator itLocal = vecLocalIPs.iterator();
/* 170 */           while (itLocal.hasNext())
/*     */           {
/* 172 */             String sLocalIP = (String)itLocal.next();
/*     */ 
/* 174 */             if (sLicensedIP.trim().compareToIgnoreCase(sLocalIP) == 0)
/*     */             {
/* 176 */               bIPValid = true;
/* 177 */               sValidIP = sLocalIP;
/* 178 */               break;
/*     */             }
/*     */           }
/*     */ 
/* 182 */           if (bIPValid)
/*     */           {
/*     */             break;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (UnknownHostException e)
/*     */       {
/* 191 */         bIPValid = false;
/* 192 */         bIPException = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 197 */     boolean bWebappValid = false;
/* 198 */     String sLocalWebapp = "";
/*     */ 
/* 200 */     if (bLicenseWebappUnbound)
/*     */     {
/* 202 */       bWebappValid = true;
/*     */     }
/*     */     else
/*     */     {
/* 207 */       Vector vecLicensedWebapps = StringUtil.convertToVector(sLicenseWebapps, ",");
/*     */ 
/* 210 */       String sApplicationPath = getServletContext().getRealPath("");
/* 211 */       File dir = new File(sApplicationPath);
/* 212 */       sLocalWebapp = dir.getName();
/*     */ 
/* 215 */       Iterator it = vecLicensedWebapps.iterator();
/* 216 */       while (it.hasNext())
/*     */       {
/* 218 */         String sLicensedWebapp = (String)it.next();
/*     */ 
/* 220 */         if (sLicensedWebapp.trim().compareToIgnoreCase(sLocalWebapp) == 0)
/*     */         {
/* 222 */           bWebappValid = true;
/* 223 */           break;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 229 */     boolean bTypeValid = false;
/*     */ 
/* 231 */     bTypeValid = checkLicenseType(sLicenseType);
/*     */ 
/* 234 */     if (((!bLicenseValid) || (!bIPValid) || (!bWebappValid) || (!bTypeValid)) && (!bSuspendLicenseValidation))
/*     */     {
/* 236 */       GlobalApplication.getInstance().suspendWebsite();
/*     */     }
/*     */ 
/* 240 */     if (AssetBankSettings.isApplicationUpdateInProgress())
/*     */     {
/* 243 */       GlobalApplication.getInstance().setSkipStartups(true);
/*     */     }
/*     */ 
/* 247 */     if (AssetBankSettings.getRequiresEndorsedLibs())
/*     */     {
/* 249 */       addEndorsedLibToSystemProperties();
/*     */     }
/*     */ 
/* 253 */     super.init();
/*     */ 
/* 256 */     if (!bLicenseValid)
/*     */     {
/* 258 */       GlobalApplication.getInstance().getLogger().error("ApplicationActionServlet.init: License validation failed.");
/*     */     }
/* 260 */     if (bLicenseException)
/*     */     {
/* 262 */       GlobalApplication.getInstance().getLogger().error("ApplicationActionServlet.init: Error in license validation is most likely due to missing license file. Check under WEB-INF/classes.");
/*     */     }
/* 264 */     if (!bTypeValid)
/*     */     {
/* 266 */       GlobalApplication.getInstance().getLogger().info("ApplicationActionServlet.init: License validation failed: License is of the incorrect type");
/*     */     }
/* 268 */     if (bIPValid)
/*     */     {
/* 270 */       GlobalApplication.getInstance().getLogger().info("ApplicationActionServlet.init: License valid for IP address: " + sValidIP);
/*     */     }
/*     */     else
/*     */     {
/* 274 */       GlobalApplication.getInstance().getLogger().error("ApplicationActionServlet.init: License validation failed: IP address " + sLocalIPs + " not in permitted range: " + sLicenseIPs);
/*     */     }
/* 276 */     if (bIPException)
/*     */     {
/* 278 */       GlobalApplication.getInstance().getLogger().error("ApplicationActionServlet.init: Error in reading IP address of local host server.");
/*     */     }
/* 280 */     if (bWebappValid)
/*     */     {
/* 282 */       GlobalApplication.getInstance().getLogger().info("ApplicationActionServlet.init: License valid for webapp: " + sLocalWebapp);
/*     */     }
/*     */     else
/*     */     {
/* 286 */       GlobalApplication.getInstance().getLogger().error("ApplicationActionServlet.init: License validation failed: webapp " + sLocalWebapp + " not in permitted range: " + sLicenseWebapps);
/*     */     }
/*     */ 
/* 289 */     if ((bLicenseNeverExpires) && (bLicenseValid))
/*     */     {
/* 291 */       GlobalApplication.getInstance().getLogger().info("ApplicationActionServlet.init: License never expires.");
/*     */     }
/*     */     else
/*     */     {
/* 295 */       if (iLicenseDaysLeft < 0)
/*     */       {
/* 297 */         GlobalApplication.getInstance().getLogger().error("ApplicationActionServlet.init: License expired!");
/*     */       }
/* 299 */       GlobalApplication.getInstance().getLogger().info("ApplicationActionServlet.init: License days left: " + iLicenseDaysLeft);
/*     */     }
/*     */ 
/* 303 */     GlobalApplication.getInstance().getLogger().info("ApplicationActionServlet.init: Application Version: " + AssetBankSettings.getApplicationVersion());
/*     */ 
/* 307 */     ABUserProfile.setUserProfileFactory(new ABUserProfileFactory());
/*     */ 
/* 312 */     HTTPUtil.setProxySettings();
/*     */   }
/*     */ 
/*     */   private boolean checkLicenseType(String sLicenseType)
/*     */   {
/* 326 */     boolean bValid = true;
/*     */ 
/* 329 */     if (AssetBankSettings.getSupportMultiLanguage())
/*     */     {
/* 331 */       if ((sLicenseType == null) || (!sLicenseType.equalsIgnoreCase("Enterprise")))
/*     */       {
/* 333 */         bValid = false;
/*     */       }
/*     */     }
/* 336 */     return bValid;
/*     */   }
/*     */ 
/*     */   private void addEndorsedLibToSystemProperties()
/*     */   {
/* 345 */     String sEndorsedDirs = System.getProperty("java.endorsed.dirs");
/*     */ 
/* 347 */     if (StringUtils.isNotEmpty(sEndorsedDirs))
/*     */     {
/* 349 */       sEndorsedDirs = sEndorsedDirs + ";";
/*     */     }
/* 351 */     else if (sEndorsedDirs == null)
/*     */     {
/* 353 */       sEndorsedDirs = "";
/*     */     }
/*     */ 
/* 356 */     String sEndorsedLibsPath = getServletContext().getRealPath("WEB-INF/manager-config/endorsed-lib");
/*     */ 
/* 359 */     if (!sEndorsedDirs.contains(sEndorsedLibsPath))
/*     */     {
/* 361 */       sEndorsedDirs = sEndorsedDirs + sEndorsedLibsPath;
/* 362 */       System.setProperty("java.endorsed.dirs", sEndorsedDirs);
/*     */ 
/* 364 */       String sMsg = "ApplicationActionServlet.init: Added " + sEndorsedLibsPath + " to " + "java.endorsed.dirs";
/*     */ 
/* 366 */       if (GlobalApplication.getInstance().getLogger() != null)
/*     */       {
/* 368 */         GlobalApplication.getInstance().getLogger().info(sMsg);
/*     */       }
/*     */       else
/*     */       {
/* 372 */         System.out.println(sMsg);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/* 384 */     super.doGet(a_request, a_response);
/*     */ 
/* 386 */     String action = a_request.getPathInfo();
/*     */ 
/* 388 */     if ((StringUtils.isNotEmpty(action)) && (!this.m_noStoreActionSet.contains(action.substring(1))))
/*     */     {
/* 391 */       if (RequestUtil.getOriginalRequestUrl(a_request) == null)
/*     */       {
/* 395 */         boolean bManualOveride = false;
/* 396 */         if (a_request.getSession().getAttribute("manualOveride") != null)
/*     */         {
/*     */           try
/*     */           {
/* 400 */             bManualOveride = ((Boolean)a_request.getSession().getAttribute("manualOveride")).booleanValue();
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 405 */             GlobalApplication.getInstance().getLogger().error(e.getMessage());
/*     */           }
/*     */         }
/*     */ 
/* 409 */         a_request.getSession().removeAttribute("manualOveride");
/*     */ 
/* 411 */         if (!bManualOveride)
/*     */         {
/* 413 */           String sPath = a_request.getRequestURI().substring(a_request.getContextPath().length()) + (StringUtils.isNotEmpty(a_request.getQueryString()) ? "?" + a_request.getQueryString() : "");
/* 414 */           a_request.getSession().setAttribute("lastGetRequestUri", sPath);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.servlet.ApplicationActionServlet
 * JD-Core Version:    0.6.0
 */