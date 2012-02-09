/*     */ package com.bright.datamigration;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ public class HttpApiDataDownloadApp
/*     */ {
/*     */   private static final int NUM_FILES_IN_DIR = 100;
/*     */   private static final int WAIT_PERIOD_MS = 500;
/*     */   public static final long IMAGE_START_ID = 1L;
/*     */   public static final long IMAGE_END_ID = 22000L;
/*     */   public static final String IMAGE_ASSET_URL_BASE = "https://intranet.amnesty.org/adam/en/";
/*     */   public static final String IMAGE_ASSET_URL_SUFFIX = "/detail.xml";
/*     */   public static final String IMAGE_DOWNLOAD_DIR = "c:\\Java\\temp\\download\\images\\";
/*     */   public static final long VIDEO_START_ID = 12600L;
/*     */   public static final long VIDEO_END_ID = 12999L;
/*     */   public static final String VIDEO_ASSET_URL_BASE = "https://intranet.amnesty.org/adam_M4_SNAPSHOT/api/rest/search-tbm-id/id:";
/*     */   public static final String VIDEO_ASSET_URL_SUFFIX = "";
/*     */   public static final String VIDEO_DOWNLOAD_DIR = "c:\\Java\\temp\\download\\video\\";
/*     */   public static final int NUM_RETRIES_PER_ID = 2;
/*     */   public static final int MAX_ERRORS = 1000;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  61 */     System.out.println("Running AmnestyDataMigrationApp...");
/*     */ 
/*  63 */     List arguments = Arrays.asList(args);
/*     */ 
/*  65 */     if (arguments.contains("download_images"))
/*     */     {
/*  67 */       downloadData("https://intranet.amnesty.org/adam/en/", "/detail.xml", "/j_security_check", "c:\\Java\\temp\\download\\images\\", 1L, 22000L, "asset+bank", "adam999");
/*     */     }
/*     */ 
/*  70 */     if (arguments.contains("download_video"))
/*     */     {
/*  72 */       downloadData("https://intranet.amnesty.org/adam_M4_SNAPSHOT/api/rest/search-tbm-id/id:", "", "", "c:\\Java\\temp\\download\\video\\", 12600L, 12999L, "erik bruchez", "orbeon2010k");
/*     */     }
/*     */ 
/*  75 */     System.out.println("Exiting AmnestyDataMigrationApp.");
/*     */   }
/*     */ 
/*     */   private static SessionCredentials doLogin(String username, String password, String cookieUrl, String loginUrl)
/*     */     throws IOException
/*     */   {
/*  84 */     SessionCredentials result = new SessionCredentials();
/*     */ 
/*  87 */     URLConnection urlConn = new URL(cookieUrl).openConnection();
/*  88 */     urlConn.connect();
/*     */ 
/*  90 */     String cookie = urlConn.getHeaderField("SET-COOKIE");
/*  91 */     if (cookie != null)
/*     */     {
/*  93 */       result.sessionId = getCookie("JSESSIONID", cookie);
/*     */     }
/*     */ 
/*  96 */     String authHeader = urlConn.getHeaderField("WWW-Authenticate");
/*     */ 
/*  98 */     if ((authHeader != null) && (authHeader.trim().toLowerCase().startsWith("basic")))
/*     */     {
/* 100 */       result.basicAuthResponse = new BASE64Encoder().encode((username + ":" + password).getBytes());
/*     */     }
/*     */ 
/* 104 */     urlConn = new URL(loginUrl).openConnection();
/* 105 */     ((HttpURLConnection)urlConn).setRequestMethod("POST");
/* 106 */     urlConn.setUseCaches(false);
/* 107 */     urlConn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/* 108 */     urlConn.addRequestProperty("Content-Disposition", "form-data;");
/* 109 */     urlConn.addRequestProperty("Connection", "keep-alive");
/* 110 */     urlConn.addRequestProperty("Keep-Alive", "300");
/* 111 */     urlConn.addRequestProperty("Referer", cookieUrl);
/* 112 */     urlConn.addRequestProperty("Cookie", "JSESSIONID=" + result.sessionId);
/*     */ 
/* 114 */     if (result.basicAuthResponse != null)
/*     */     {
/* 116 */       urlConn.setRequestProperty("Authorization", "Basic " + result.basicAuthResponse);
/*     */     }
/*     */ 
/* 119 */     urlConn.setDoOutput(true);
/* 120 */     urlConn.setDoInput(true);
/*     */ 
/* 122 */     urlConn.connect();
/*     */ 
/* 124 */     if (result.basicAuthResponse == null)
/*     */     {
/* 126 */       OutputStreamWriter writer = new OutputStreamWriter(urlConn.getOutputStream());
/*     */ 
/* 128 */       writer.write("j_username=" + username + "&j_password=" + password);
/* 129 */       writer.flush();
/* 130 */       writer.close();
/*     */     }
/*     */ 
/* 133 */     if (cookie == null)
/*     */     {
/* 135 */       cookie = urlConn.getHeaderField("SET-COOKIE");
/* 136 */       if (cookie != null)
/*     */       {
/* 138 */         result.sessionId = getCookie("JSESSIONID", cookie);
/*     */       }
/*     */     }
/*     */ 
/* 142 */     urlConn.getInputStream().close();
/*     */ 
/* 144 */     return result;
/*     */   }
/*     */ 
/*     */   private static void downloadData(String baseUrl, String urlSuffix, String loginSuffix, String downloadDir, long startId, long endId, String username, String password)
/*     */   {
/* 157 */     int iFailureCount = 0;
/* 158 */     int subdir = (int)((startId - 1L) / 100L) + 1;
/*     */ 
/* 160 */     System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
/*     */     try
/*     */     {
/* 164 */       FileUtils.forceMkdir(new File(downloadDir + subdir));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 168 */       e.printStackTrace();
/* 169 */       System.exit(1);
/*     */     }
/*     */ 
/* 172 */     SessionCredentials session = null;
/*     */     try
/*     */     {
/* 176 */       session = doLogin(username, password, baseUrl + 1 + urlSuffix, baseUrl + 1 + loginSuffix);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 180 */       System.err.println("Cannot log in - giving up!");
/* 181 */       System.exit(1);
/*     */     }
/*     */ 
/* 184 */     String additionalCookies = "";
/*     */ 
/* 186 */     for (long id = startId; id <= endId; id += 1L)
/*     */     {
/* 188 */       String url = baseUrl + id + urlSuffix;
/*     */ 
/* 190 */       System.out.println("----------------------------------------------------------------------------------");
/* 191 */       System.out.println("Attempting to call: " + url);
/*     */ 
/* 193 */       int iAttempt = 1;
/* 194 */       boolean bSuccess = false;
/*     */       try
/*     */       {
/* 198 */         if ((id > startId) && ((id - 1L) % 100L == 0L))
/*     */         {
/* 200 */           if (FileUtils.sizeOfDirectory(new File(downloadDir + subdir)) == 0L)
/*     */           {
/* 202 */             FileUtils.deleteDirectory(new File(downloadDir + subdir));
/*     */           }
/* 204 */           subdir++; FileUtils.forceMkdir(new File(downloadDir + subdir));
/*     */         }
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 209 */         e.printStackTrace();
/* 210 */         System.exit(1);
/*     */       }
/*     */ 
/*     */       do
/*     */       {
/* 216 */         if (iAttempt > 2)
/*     */         {
/*     */           try
/*     */           {
/* 220 */             Thread.sleep((iAttempt - 2) * 500);
/*     */           }
/*     */           catch (InterruptedException e)
/*     */           {
/*     */           }
/*     */         }
/*     */         try {
/* 227 */           URLConnection urlConn = new URL(url).openConnection();
/* 228 */           urlConn.addRequestProperty("Cookie", "JSESSIONID=" + session.sessionId);
/* 229 */           urlConn.addRequestProperty("Connection", "keep-alive");
/* 230 */           urlConn.setRequestProperty("Accept-Charset", "UTF-8");
/* 231 */           urlConn.addRequestProperty("Keep-Alive", "300");
/*     */ 
/* 233 */           if (StringUtils.isNotEmpty(additionalCookies))
/*     */           {
/* 235 */             urlConn.addRequestProperty("Cookie", additionalCookies);
/*     */           }
/*     */ 
/* 238 */           if (session.basicAuthResponse != null)
/*     */           {
/* 240 */             urlConn.setRequestProperty("Authorization", "Basic " + session.basicAuthResponse);
/*     */           }
/*     */ 
/* 243 */           urlConn.setUseCaches(false);
/* 244 */           urlConn.connect();
/*     */ 
/* 246 */           String response = getStreamContents(urlConn.getInputStream());
/*     */ 
/* 248 */           HttpURLConnection httpConn = (HttpURLConnection)urlConn;
/*     */ 
/* 251 */           String cookie = urlConn.getHeaderField("SET-COOKIE");
/* 252 */           if (cookie != null)
/*     */           {
/* 254 */             if (additionalCookies.indexOf(cookie) < 0)
/*     */             {
/* 256 */               if (additionalCookies.length() > 0)
/*     */               {
/* 258 */                 additionalCookies = additionalCookies + ";";
/*     */               }
/* 260 */               additionalCookies = additionalCookies + cookie;
/*     */             }
/*     */           }
/*     */ 
/* 264 */           if (response.indexOf("hits=\"0\"") > 0)
/*     */           {
/* 266 */             System.out.println("No item found with id " + id);
/* 267 */             break;
/*     */           }
/* 269 */           if (response.trim().startsWith("<?xml"))
/*     */           {
/* 271 */             FileUtils.writeStringToFile(new File(downloadDir + subdir + "/" + id + ".xml"), response, "UTF-8");
/* 272 */             System.out.println("Success! Wrote file: " + downloadDir + subdir + "/" + id + ".xml");
/* 273 */             bSuccess = true;
/*     */           }
/* 275 */           else if (httpConn.getResponseCode() == 200)
/*     */           {
/* 277 */             System.out.println("No xml output returned in response for id " + id + ", making one more attempt...");
/* 278 */             iAttempt = Math.max(1, iAttempt);
/*     */           }
/*     */           else
/*     */           {
/* 282 */             System.out.println("Unexpected response code from server: " + httpConn.getResponseCode() + " - " + httpConn.getResponseMessage());
/*     */           }
/*     */ 
/* 285 */           urlConn.getInputStream().close();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 289 */           System.err.println("Error encountered during attempt " + iAttempt);
/*     */ 
/* 291 */           e.printStackTrace();
/*     */ 
/* 293 */           if (iAttempt == 2)
/*     */           {
/* 295 */             iFailureCount++; if (iFailureCount > 1000)
/*     */             {
/* 297 */               System.err.println("\nToo many errors - giving up!");
/* 298 */               System.exit(1);
/*     */             }
/*     */             else
/*     */             {
/* 302 */               System.err.println("No more attempts left, giving up on id " + id);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 307 */             System.err.println("Retrying...");
/*     */           }
/*     */         }
/*     */       }
/* 311 */       while ((!bSuccess) && (iAttempt++ < 2));
/*     */ 
/* 313 */       System.out.println("----------------------------------------------------------------------------------\n");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getStreamContents(InputStream is) throws IOException
/*     */   {
/* 319 */     StringBuffer response = new StringBuffer();
/* 320 */     byte[] buffer = new byte[65535];
/* 321 */     int count = 0;
/*     */ 
/* 323 */     while ((count = is.read(buffer)) >= 0)
/*     */     {
/* 325 */       response.append(new String(buffer, 0, count, "UTF-8"));
/*     */     }
/*     */ 
/* 328 */     return response.toString();
/*     */   }
/*     */ 
/*     */   public static String getCookie(String a_sCookieName, String a_sCookieString)
/*     */   {
/* 338 */     if ((a_sCookieString != null) && (a_sCookieString.indexOf(a_sCookieName + "=") >= 0))
/*     */     {
/* 340 */       int iStartOfValue = a_sCookieString.indexOf(a_sCookieName) + a_sCookieName.length() + 1;
/* 341 */       return a_sCookieString.substring(iStartOfValue, Math.min(a_sCookieString.length(), a_sCookieString.indexOf(";", iStartOfValue)));
/*     */     }
/* 343 */     return null;
/*     */   }
/*     */ 
/*     */   public static class SessionCredentials
/*     */   {
/*     */     public String sessionId;
/*     */     public String basicAuthResponse;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.datamigration.HttpApiDataDownloadApp
 * JD-Core Version:    0.6.0
 */