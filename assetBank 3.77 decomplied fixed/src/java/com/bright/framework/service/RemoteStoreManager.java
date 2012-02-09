/*     */ package com.bright.framework.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.RemoteStorageRequest;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.service.ScheduleManager;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.mail.service.EmailManager;
/*     */ import com.bright.framework.queue.bean.QueuedItem;
/*     */ import com.bright.framework.queue.service.MessagingQueueManager;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.HTTPUtil;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.ConnectException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ import org.apache.commons.logging.Log;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ public class RemoteStoreManager extends MessagingQueueManager
/*     */ {
/*     */   private boolean m_usesRemoteStorage;
/*     */   private URL m_remoteStorageURL;
/*     */   private String m_remoteStorageAuthEncoding;
/*     */   private ScheduleManager m_scheduleManager;
/*     */   private EmailManager m_emailManager;
/*     */ 
/*     */   public RemoteStoreManager()
/*     */   {
/*  46 */     this.m_usesRemoteStorage = false;
/*  47 */     this.m_remoteStorageURL = null;
/*  48 */     this.m_remoteStorageAuthEncoding = null;
/*  49 */     this.m_scheduleManager = null;
/*  50 */     this.m_emailManager = null;
/*     */   }
/*     */ 
/*     */   public boolean usesRemoteStorage()
/*     */   {
/*  66 */     return this.m_usesRemoteStorage;
/*     */   }
/*     */ 
/*     */   public void setScheduleManager(ScheduleManager a_scheduleManager)
/*     */   {
/*  71 */     this.m_scheduleManager = a_scheduleManager;
/*     */   }
/*     */ 
/*     */   public void setEmailManager(EmailManager a_emailManager)
/*     */   {
/*  76 */     this.m_emailManager = a_emailManager;
/*     */   }
/*     */ 
/*     */   private String getHostPort()
/*     */   {
/*  81 */     int port = this.m_remoteStorageURL.getPort();
/*  82 */     if (port >= 0)
/*     */     {
/*  84 */       return this.m_remoteStorageURL.getHost() + ":" + Integer.toString(port);
/*     */     }
/*  86 */     return this.m_remoteStorageURL.getHost();
/*     */   }
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  99 */     super.startup();
/*     */ 
/* 102 */     HTTPUtil.setProxySettings();
/*     */ 
/* 105 */     String remoteStorageURL = FrameworkSettings.getRemoteStorageUrl();
/* 106 */     String remoteStorageUser = FrameworkSettings.getRemoteStorageUser();
/* 107 */     String remoteStoragePassword = FrameworkSettings.getRemoteStoragePassword();
/*     */ 
/* 109 */     if (remoteStorageURL.length() > 0)
/*     */     {
/* 112 */       if (remoteStorageUser.length() > 0)
/*     */       {
/* 114 */         remoteStorageURL = remoteStorageURL + remoteStorageUser + "/";
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 119 */         this.m_remoteStorageURL = new URL(remoteStorageURL);
/*     */       }
/*     */       catch (MalformedURLException err)
/*     */       {
/* 123 */         this.m_logger.error("Malformed remote storage URL '" + remoteStorageURL + "'", err);
/* 124 */         return;
/*     */       }
/*     */     }
/*     */     else {
/* 128 */       return;
/*     */     }
/* 130 */     this.m_usesRemoteStorage = true;
/*     */ 
/* 133 */     if ((remoteStorageUser.length() > 0) || (remoteStoragePassword.length() > 0))
/*     */     {
/* 135 */       String remoteStorageAuth = remoteStorageUser + ":" + remoteStoragePassword;
/* 136 */       this.m_remoteStorageAuthEncoding = ("Basic " + new BASE64Encoder().encode(remoteStorageAuth.getBytes()));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 142 */       listRemoteFiles();
/* 143 */       this.m_logger.info("Using remote storage at " + this.m_remoteStorageURL);
/*     */     }
/*     */     catch (RestfulAPICallFailed err)
/*     */     {
/* 147 */       this.m_logger.error("Initial attempt to access remote storage at " + this.m_remoteStorageURL + " failed", err);
/*     */     }
/*     */ 
/* 151 */     TimerTask task = new TimerTask()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/* 157 */           RemoteStoreManager.this.clearRemoteStorage();
/*     */         }
/*     */         catch (RemoteStoreManager.RestfulAPICallFailed err)
/*     */         {
/* 161 */           RemoteStoreManager.this.m_logger.error("Error clearing files from remote storage at " + RemoteStoreManager.this.m_remoteStorageURL, err);
/*     */         }
/*     */       }
/*     */     };
/* 166 */     int iHourOfDay = FrameworkSettings.getClearTempFilesHourOfDay();
/* 167 */     this.m_scheduleManager.scheduleDailyTask(task, iHourOfDay, false);
/*     */   }
/*     */ 
/*     */   public void queueStorageRequest(RemoteStorageRequest a_request)
/*     */   {
/* 177 */     long requestId = a_request.getRequestId().longValue();
/* 178 */     startBatchProcess(requestId);
/* 179 */     queueItem(a_request);
/*     */   }
/*     */   public void processQueueItem(QueuedItem a_item) {
/* 189 */     RemoteStorageRequest request = (RemoteStorageRequest)a_item;
/* 190 */     String filepath = request.getFilepath();
/* 191 */     long requestId = request.getRequestId().longValue();
/*     */ 
/* 194 */     addMessage(requestId, "Uploading file " + FileUtil.getFilename(filepath));
/*     */     URL downloadPageURL;
/*     */     try {
/* 199 */       downloadPageURL = copyLocalFileToRemoteStorage(request.getFilepath(), requestId);
/*     */     }
/*     */     catch (IOException err)
/*     */     {
/* 203 */       addMessage(requestId, "File upload failed: " + err.toString());
/* 204 */       this.m_logger.error("IOException copying local file to remote storage: ", err);
/* 205 */       failBatchProcess(requestId);
/* 206 */       return;
/*     */     }
/*     */     catch (RestfulAPICallFailed err)
/*     */     {
/* 210 */       addMessage(requestId, "File upload failed: " + err.getMessage());
/* 211 */       this.m_logger.error("RestfulAPICallFailed copying local file to remote storage", err);
/* 212 */       failBatchProcess(requestId);
/* 213 */       return;
/*     */     }
/*     */ 
/* 216 */     addMessage(requestId, "Completed file upload");
/*     */ 
/* 219 */     Map params = request.getEmailParams();
/* 220 */     params.put("url", downloadPageURL.toString());
/*     */     try
/*     */     {
/* 223 */       this.m_emailManager.sendTemplatedEmail(params, null, false, request.getUserProfile().getCurrentLanguage());
/*     */     }
/*     */     catch (Bn2Exception err)
/*     */     {
/* 227 */       addMessage(requestId, "Failed to send email: " + err.getMessage());
/* 228 */       this.m_logger.error("Bn2Exception sending email after updloading local file to remote storage", err);
/* 229 */       failBatchProcess(requestId);
/* 230 */       return;
/*     */     }
/*     */ 
/* 233 */     addMessage(requestId, "Email Sent");
/* 234 */     endBatchProcess(requestId);
/*     */   }
/*     */ 
/*     */   public URL copyLocalFileToRemoteStorage(String a_filepath, long a_userId)
/*     */     throws RemoteStoreManager.RestfulAPICallFailed, IOException
/*     */   {
/* 251 */     String prefix = UUID.randomUUID().toString();
/* 252 */     String basename = FileUtil.getFilename(a_filepath);
/* 253 */     String fileKey = prefix + "/" + basename;
/*     */ 
/* 256 */     File file = new File(a_filepath);
/* 257 */     FileInputStream fileInputStream = new FileInputStream(file);
/*     */ 
/* 260 */     long fileLength = file.length();
/* 261 */     if (fileLength > 2147483647L)
/*     */     {
/* 263 */       throw new RestfulAPICallFailed("Cannot upload file sizes larger than 2GB");
/*     */     }
/*     */ 
/* 267 */     apiGetList();
/*     */ 
/* 270 */     URL fileURL = new URL(this.m_remoteStorageURL + fileKey);
/* 271 */     JsonElement response = apiPutFile(fileURL, fileInputStream, (int)fileLength, a_userId);
/*     */     try
/*     */     {
/* 276 */       return new URL(response.getAsJsonObject().getAsJsonPrimitive("download_page_url").getAsString());
/*     */     }
/*     */     catch (IllegalStateException err)
/*     */     {
/* 280 */       throw new RestfulAPICallFailed("Unexpected JSON response from remote storage API '" + this.m_remoteStorageURL + "' " + err.getMessage(), err);
/*     */     }
/*     */     catch (ClassCastException err)
/*     */     {
/* 284 */       throw new RestfulAPICallFailed("Unexpected JSON response from remote storage API '" + this.m_remoteStorageURL + "' " + err.getMessage(), err);
/*     */     }
/*     */     catch (MalformedURLException err) {
/*     */     
/* 288 */     throw new RestfulAPICallFailed("Unexpected JSON response from remote storage API '" + this.m_remoteStorageURL + "' " + err.getMessage(), err);
/*     */  } }
/*     */ 
/*     */   private void clearRemoteStorage()
/*     */     throws RemoteStoreManager.RestfulAPICallFailed
/*     */   {
/* 298 */     JsonArray files = listRemoteFiles();
/*     */ 
/* 301 */     for (JsonElement file : files)
/*     */     {
/*     */       int fileAgeSeconds;
/*     */       URL fileURL;
/*     */       try
/*     */       {
/* 310 */         fileAgeSeconds = file.getAsJsonObject().getAsJsonPrimitive("age_seconds").getAsInt();
/* 311 */         fileURL = new URL(file.getAsJsonObject().getAsJsonPrimitive("url").getAsString());
/*     */       }
/*     */       catch (Exception err)
/*     */       {
/* 315 */         this.m_logger.error("Unexpected JSON response from remote storage API '" + file.toString() + "'", err);
                  continue;
/* 316 */       }//continue;
/*     */ 
/* 320 */       if (fileAgeSeconds < FrameworkSettings.getDeleteRemoteFilesOlderThanMins() * 60)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 328 */         apiDeleteFile(fileURL);
/*     */       }
/*     */       catch (RestfulAPICallFailed err)
/*     */       {
/* 332 */         this.m_logger.error("Error attempting to call remote storage API '" + fileURL + "'", err);
/* 333 */         return;
/*     */       }
/*     */       catch (Exception err)
/*     */       {
/* 337 */         this.m_logger.warn("Unexpected JSON response from remote storage API '" + fileURL + "'", err);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private JsonArray listRemoteFiles()
/*     */     throws RemoteStoreManager.RestfulAPICallFailed
/*     */   {
/*     */     String service;
/*     */     String version;
/*     */     JsonArray files;
/*     */     try
/*     */     {
/* 356 */       JsonElement listResponse = apiGetList();
/* 357 */       service = listResponse.getAsJsonObject().getAsJsonPrimitive("service").getAsString();
/* 358 */       version = listResponse.getAsJsonObject().getAsJsonPrimitive("version").getAsString();
/* 359 */       files = listResponse.getAsJsonObject().getAsJsonArray("files");
/*     */     }
/*     */     catch (IllegalStateException err)
/*     */     {
/* 363 */       throw new RestfulAPICallFailed("Unexpected JSON response from remote storage API '" + this.m_remoteStorageURL + "' " + err.getMessage(), err);
/*     */     }
/*     */     catch (ClassCastException err)
/*     */     {
/* 367 */       throw new RestfulAPICallFailed("Unexpected JSON response from remote storage API '" + this.m_remoteStorageURL + "' " + err.getMessage(), err);
/*     */     }
/*     */ 
/* 371 */     if (!service.equals("bright-restful-storage"))
/*     */     {
/* 373 */       throw new RestfulAPICallFailed("Unexpected service returned from remote storage API '" + this.m_remoteStorageURL + "' Service: '" + service + "'");
/*     */     }
/* 375 */     if (!version.equals("1.0"))
/*     */     {
/* 377 */       throw new RestfulAPICallFailed("Unexpected version returned from remote storage API '" + this.m_remoteStorageURL + "' Version: '" + version + "'");
/*     */     }
/*     */ 
/* 380 */     return files;
/*     */   }
/*     */ 
/*     */   private JsonElement apiGetList()
/*     */     throws RemoteStoreManager.RestfulAPICallFailed
/*     */   {
/*     */     try
/*     */     {
/* 397 */       HttpURLConnection connection = (HttpURLConnection)this.m_remoteStorageURL.openConnection();
/* 398 */       if (this.m_remoteStorageAuthEncoding != null)
/*     */       {
/* 400 */         connection.setRequestProperty("Authorization", this.m_remoteStorageAuthEncoding);
/*     */       }
/*     */ 
/* 404 */       BufferedReader responseStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 405 */       return new JsonParser().parse(responseStream);
/*     */     }
/*     */     catch (ConnectException err)
/*     */     {
/* 409 */       throw new RestfulAPICallFailed("Could not connect to " + getHostPort() + " - " + err.getMessage(), err);
/*     */     }
/*     */     catch (IOException err)
/*     */     {
/* 413 */       throw new RestfulAPICallFailed("Error listing files on " + getHostPort() + ", " + err.getMessage(), err);
/*     */     }
/*     */     catch (JsonParseException err) {
/*     */     
/* 417 */     throw new RestfulAPICallFailed("Unexpected JSON response from " + getHostPort() + ", " + err.getMessage(), err);
/*     */  } }
/*     */ 
/*     */   private JsonElement apiPutFile(URL a_fileURL, InputStream a_input, int a_fileSize, long a_userId)
/*     */     throws RemoteStoreManager.RestfulAPICallFailed
/*     */   {
/*     */     try
/*     */     {
/* 440 */       HttpURLConnection connection = (HttpURLConnection)a_fileURL.openConnection();
/* 441 */       if (this.m_remoteStorageAuthEncoding != null)
/*     */       {
/* 443 */         connection.setRequestProperty("Authorization", this.m_remoteStorageAuthEncoding);
/*     */       }
/* 445 */       connection.setRequestMethod("PUT");
/* 446 */       connection.setFixedLengthStreamingMode(a_fileSize);
/* 447 */       connection.setDoOutput(true);
/*     */ 
/* 450 */       OutputStream output = connection.getOutputStream();
/* 451 */       int BUF_SIZE = 4096;
/*     */ 
/* 453 */       byte[] buffer = new byte[BUF_SIZE];
/* 454 */       long totBytes = 0L;
/* 455 */       int percentMarker = 0;
/*     */       int numBytes;
/* 456 */       while ((numBytes = a_input.read(buffer)) != -1)
/*     */       {
/* 458 */         output.write(buffer, 0, numBytes);
/*     */ 
/* 461 */         totBytes += numBytes;
/* 462 */         int currentPercentMarker = (int)(totBytes * 100L / a_fileSize);
/* 463 */         while ((percentMarker < currentPercentMarker) && (percentMarker < 100))
/*     */         {
/* 465 */           percentMarker++;
/* 466 */           if (percentMarker % 5 != 0)
/*     */             continue;
/* 468 */           addMessage(a_userId, Integer.toString(percentMarker) + "% uploaded");
/*     */         }
/*     */       }
/*     */ 
/* 472 */       output.close();
/*     */ 
/* 475 */       BufferedReader responseStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 476 */       return new JsonParser().parse(responseStream);
/*     */     }
/*     */     catch (ConnectException err)
/*     */     {
/* 480 */       throw new RestfulAPICallFailed("Could not connect to " + getHostPort() + ", " + err.getMessage(), err);
/*     */     }
/*     */     catch (IOException err)
/*     */     {
/* 484 */       throw new RestfulAPICallFailed("Error uploading file to " + getHostPort() + ", " + err.getMessage(), err);
/*     */     }
/*     */     catch (JsonParseException err) {
/*     */     
/* 488 */     throw new RestfulAPICallFailed("Unexpected JSON response from " + getHostPort() + ", " + err.getMessage(), err);
/*     */  } }
/*     */ 
/*     */   private JsonElement apiDeleteFile(URL a_fileURL)
/*     */     throws RemoteStoreManager.RestfulAPICallFailed
/*     */   {
/*     */     try
/*     */     {
/* 504 */       HttpURLConnection connection = (HttpURLConnection)a_fileURL.openConnection();
/* 505 */       connection.setRequestMethod("DELETE");
/* 506 */       if (this.m_remoteStorageAuthEncoding != null)
/*     */       {
/* 508 */         connection.setRequestProperty("Authorization", this.m_remoteStorageAuthEncoding);
/*     */       }
/*     */ 
/* 512 */       BufferedReader responseStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 513 */       return new JsonParser().parse(responseStream);
/*     */     }
/*     */     catch (ConnectException err)
/*     */     {
/* 517 */       throw new RestfulAPICallFailed("Could not connect to " + getHostPort() + " - " + err.getMessage(), err);
/*     */     }
/*     */     catch (IOException err)
/*     */     {
/* 521 */       throw new RestfulAPICallFailed("Error deleting file from " + getHostPort() + ", " + err.getMessage(), err);
/*     */     }
/*     */     catch (JsonParseException err) {
/*     */     
/* 525 */     throw new RestfulAPICallFailed("Unexpected JSON response from " + getHostPort() + ", " + err.getMessage(), err);
/*     */  } }
/*     */ 
/*     */   public class RestfulAPICallFailed extends Exception
/*     */   {
/*     */     public RestfulAPICallFailed(String a_message)
/*     */     {
/*  56 */       super();
/*     */     }
/*     */ 
/*     */     public RestfulAPICallFailed(String a_message, Throwable a_cause) {
/*  60 */       super(a_cause);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.service.RemoteStoreManager
 * JD-Core Version:    0.6.0
 */