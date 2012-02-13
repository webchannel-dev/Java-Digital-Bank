/*     */ package com.bright.assetbank.application.servlet;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class FileDownloadServlet extends HttpServlet
/*     */   implements AssetBankConstants
/*     */ {
/*  52 */   private Log m_log = GlobalApplication.getInstance().getLogger();
/*  53 */   private FileStoreManager m_fileStoreManager = null;
/*     */ 
/*     */   public void init() throws ServletException
/*     */   {
/*  57 */     super.init();
/*     */     try
/*     */     {
/*  61 */       this.m_fileStoreManager = ((FileStoreManager)GlobalApplication.getInstance().getComponentManager().lookup("FileStoreManager"));
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/*  65 */       this.m_log.error("ComponentException whilst getting FileStoreManager in FileDownloadServlet : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/*  87 */     processDownload(a_request, a_response);
/*     */   }
/*     */ 
/*     */   public void doPost(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/* 108 */     processDownload(a_request, a_response);
/*     */   }
/*     */ 
/*     */   private void processDownload(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/* 127 */     File file = null;
/* 128 */     String sFile = null;
/* 129 */     String sFilename = null;
/* 130 */     String sFailureUrl = null;
/* 131 */     InputStream is = null;
/* 132 */     OutputStream os = null;
/* 133 */     boolean bDeleteDownloadedFile = false;
/*     */     try
/*     */     {
/* 138 */       sFile = (String)a_request.getAttribute("downloadFile");
/* 139 */       sFile = FileUtil.decryptFilepath(sFile);
/*     */       try
/*     */       {
/* 144 */         sFile = this.m_fileStoreManager.getAbsolutePath(sFile);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 148 */         throw new ServletException("Bn2Exception caught whilst getting full path of file to download : " + e.getLocalizedMessage(), e);
/*     */       }
/*     */ 
/* 152 */       if (a_request.getAttribute("downloadFilename") != null)
/*     */       {
/* 154 */         sFilename = (String)a_request.getAttribute("downloadFilename");
/*     */       }
/*     */       else
/*     */       {
/* 158 */         sFilename = FileUtil.getFilename(sFile);
/*     */       }
/*     */ 
/* 162 */       sFilename = FileUtil.getSafeFilename(sFilename, true);
/*     */ 
/* 165 */       sFailureUrl = (String)a_request.getAttribute("downloadFaliureUrl");
/*     */ 
/* 168 */       if (a_request.getAttribute("deleteFileAfterUse") != null)
/*     */       {
/* 170 */         bDeleteDownloadedFile = ((Boolean)a_request.getAttribute("deleteFileAfterUse")).booleanValue();
/*     */       }
/*     */ 
/* 174 */       file = new File(sFile);
/*     */ 
/* 176 */       if ((file.exists()) && (file.canRead()))
/*     */       {
/*     */         try
/*     */         {
/* 180 */           a_response.setContentType("application/x-download");
/* 181 */           a_response.addHeader("content-disposition", "attachment; filename=" + sFilename);
/*     */ 
/* 184 */           a_response.setHeader("Pragma", "public");
/* 185 */           a_response.setHeader("Cache-Control", "cache, must-revalidate");
/*     */ 
/* 187 */           a_response.setContentLength((int)file.length());
/*     */ 
/* 189 */           is = new BufferedInputStream(new FileInputStream(sFile));
/*     */ 
/* 191 */           byte[] buffer = new byte[65536];
/*     */ 
/* 194 */           os = new BufferedOutputStream(a_response.getOutputStream());
/*     */           int nbytes;
/* 197 */           while ((nbytes = is.read(buffer)) != -1)
/*     */           {
/* 199 */             os.write(buffer, 0, nbytes);
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (IOException ioe)
/*     */         {
/* 205 */           GlobalApplication.getInstance().getLogger().error("FileDownloadServlet.processDownload: IOException: " + ioe.getMessage(), ioe);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 211 */         GlobalApplication.getInstance().getLogger().error("FileDownloadServlet.processDownload() : File cannot be downloaded : " + sFile);
/* 212 */         a_response.sendRedirect(a_request.getContextPath() + sFailureUrl);
/*     */       }
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */ 
/* 244 */     //ret;
                return;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.servlet.FileDownloadServlet
 * JD-Core Version:    0.6.0
 */