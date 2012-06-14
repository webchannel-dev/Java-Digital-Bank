/*     */ package com.bright.assetbank.application.servlet;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.image.util.ImageUtil;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.util.DateUtil;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class FileDisplayServlet extends HttpServlet
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final long serialVersionUID = 7486147622914773561L;
/*  62 */   private static SimpleDateFormat m_sdfLastModifiedFormat = null;
/*     */ 
/*  64 */   private FileStoreManager m_fileStoreManager = null;
/*  65 */   private Log m_log = GlobalApplication.getInstance().getLogger();
/*     */ 
/*     */   public void init()
/*     */     throws ServletException
/*     */   {
/*  70 */     super.init();
/*     */ 
/*  73 */     m_sdfLastModifiedFormat = DateUtil.getRFC2822DateFormat();
/*     */     try
/*     */     {
/*  77 */       this.m_fileStoreManager = ((FileStoreManager)GlobalApplication.getInstance().getComponentManager().lookup("FileStoreManager"));
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/*  81 */       this.m_log.error("ComponentException whilst getting FileStoreManager in FileDisplayServlet : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/* 107 */     File file = null;
/* 108 */     String sFile = null;
/* 109 */     String sContentType = null;
/* 110 */     String sFilename = null;
/* 111 */     boolean bDeleteFile = false;
/*     */ 
/* 115 */     sFile = a_request.getParameter("file");
/*     */ 
/* 118 */     if ((sFile == null) || (sFile.trim().length() == 0))
/*     */     {
/* 120 */       sFile = (String)a_request.getAttribute("file");
/*     */     }
/*     */ 
/* 125 */     if ((sFile == null) || (sFile.length() == 0))
/*     */     {
/* 127 */       String sPath = a_request.getPathInfo();
/*     */ 
/* 129 */       if (sPath != null)
/*     */       {
/* 131 */         int iLastSlash = sPath.lastIndexOf("/");
/*     */ 
/* 133 */         if (iLastSlash >= 0)
/*     */         {
/* 135 */           sFile = sPath.substring(iLastSlash + 1, sPath.length());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 142 */     sContentType = a_request.getParameter("contentType");
/*     */ 
/* 145 */     if (!StringUtil.stringIsPopulated(sContentType))
/*     */     {
/* 147 */       sContentType = (String)a_request.getAttribute("contentType");
/*     */     }
/*     */ 
/* 150 */     if (sContentType != null)
/*     */     {
/* 152 */       sFilename = a_request.getParameter("filename");
/*     */ 
/* 154 */       if (sFilename != null)
/*     */       {
/* 156 */         sFilename = FileUtil.getSafeFilename(sFilename, true);
/*     */       }
/*     */       else
/*     */       {
/* 160 */         sFilename = "tmpfile";
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 165 */     if (a_request.getAttribute("deleteFileAfterUse") != null)
/*     */     {
/* 167 */       bDeleteFile = ((Boolean)a_request.getAttribute("deleteFileAfterUse")).booleanValue();
/*     */     }
/*     */ 
/* 171 */     if ((sFile != null) && (sFile.length() > 0))
/*     */     {
/* 174 */       sFile = FileUtil.decryptFilepath(sFile);
/*     */       try
/*     */       {
/* 179 */         sFile = this.m_fileStoreManager.getAbsolutePath(sFile);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/* 183 */         throw new ServletException("Bn2Exception caught whilst getting full path of file to display : " + e.getLocalizedMessage(), e);
/*     */       }
/*     */ 
/* 187 */       file = new File(sFile);
/*     */ 
/* 190 */       if (((!file.exists()) || (!file.canRead())) && (ImageUtil.isWebImageFile(sFile)))
/*     */       {
/* 192 */         sFile = GlobalSettings.getApplicationPath() + "/" + AssetBankSettings.getImageNotFoundImage();
/* 193 */         file = new File(sFile);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 199 */       sFile = GlobalSettings.getApplicationPath() + "/" + AssetBankSettings.getThumbnailsPendingImage();
/* 200 */       file = new File(sFile);
/*     */     }
/*     */ 
/* 203 */     InputStream is = null;
/* 204 */     OutputStream os = null;
/*     */ 
/* 207 */     if ((file != null) && (file.exists()) && (file.canRead()))
/*     */     {
/* 210 */       if (sContentType != null)
/*     */       {
/* 212 */         a_response.setContentType(sContentType);
/* 213 */         a_response.addHeader("content-disposition", "attachment; filename=" + sFilename);
/*     */       }
/*     */ 
/* 217 */       a_response.setContentLength((int)file.length());
/*     */ 
/* 221 */       synchronized (m_sdfLastModifiedFormat)
/*     */       {
/* 223 */         a_response.addHeader("last-modified", m_sdfLastModifiedFormat.format(new Date(file.lastModified())));
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 229 */         is = new BufferedInputStream(new FileInputStream(sFile));
/* 230 */         os = new BufferedOutputStream(a_response.getOutputStream());
/*     */ 
/* 232 */         byte[] buffer = new byte[65536];
/*     */         int nbytes;
/* 236 */         while ((nbytes = is.read(buffer)) != -1)
/*     */         {
/* 238 */           a_response.getOutputStream().write(buffer, 0, nbytes);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/*     */       }
/*     */       finally
/*     */       {
/* 247 */         is.close();
/*     */       }
/*     */ 
/* 268 */       return;
/*     */     }
/*     */     else
/*     */     {
/* 276 */       a_response.sendError(404);
/* 277 */       a_response.flushBuffer();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.servlet.FileDisplayServlet
 * JD-Core Version:    0.6.0
 */