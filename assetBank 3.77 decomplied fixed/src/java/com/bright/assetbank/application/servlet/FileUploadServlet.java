/*     */ package com.bright.assetbank.application.servlet;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.fileupload.FileItem;
/*     */ import org.apache.commons.fileupload.disk.DiskFileItemFactory;
/*     */ import org.apache.commons.fileupload.servlet.ServletFileUpload;
/*     */ 
/*     */ public abstract class FileUploadServlet extends HttpServlet
/*     */   implements AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "FileUploadServlet";
/*     */   private static final int m_lMaxFileSizeDefault = 2000000000;
/*     */   protected static final int m_iMaxMemorySize = 10000000;
/*     */   public static final String c_ksFilePartExt = "part";
/*     */   public static final String c_ksFileTempExt = "tmp";
/*     */ 
/*     */   protected abstract String getUploadDirectory(HttpServletRequest paramHttpServletRequest);
/*     */ 
/*     */   protected abstract boolean checkPermissions(HttpServletRequest paramHttpServletRequest);
/*     */ 
/*     */   protected long getMaxFileSize()
/*     */   {
/*  60 */     return 2000000000L;
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/*  83 */     processUpload(a_request, a_response);
/*     */   }
/*     */ 
/*     */   public void doPost(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/* 106 */     processUpload(a_request, a_response);
/*     */   }
/*     */ 
/*     */   private void processUpload(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException
/*     */   {
/* 125 */     String ksMethodName = "processUpload";
/*     */ 
/* 128 */     boolean bLastChunk = false;
/* 129 */     int iNumChunk = 0;
/* 130 */     long lMaxFileSize = getMaxFileSize();
/*     */ 
/* 133 */     if (!checkPermissions(a_request))
/*     */     {
/* 135 */       throw new IOException("FileUploadServlet.processUpload: User does not have permission to access this servlet.");
/*     */     }
/*     */ 
/* 142 */     if (ServletFileUpload.isMultipartContent(a_request))
/*     */     {
/* 145 */       String sPart = a_request.getParameter("jupart");
/* 146 */       String sIsFinal = a_request.getParameter("jufinal");
/*     */ 
/* 148 */       if ((sPart != null) && (sPart.length() > 0))
/*     */       {
/* 151 */         iNumChunk = Integer.parseInt(sPart);
/*     */ 
/* 156 */         if ((sIsFinal != null) && (sIsFinal.equals("1")))
/*     */         {
/* 158 */           bLastChunk = true;
/*     */         }
/*     */       }
/*     */ 
/* 162 */       a_response.setContentType("text/plain");
/*     */       try
/*     */       {
/* 166 */         DiskFileItemFactory diskFactory = new DiskFileItemFactory();
/*     */ 
/* 169 */         diskFactory.setSizeThreshold(10000000);
/* 170 */         diskFactory.setRepository(new File(getUploadDirectory(a_request)));
/*     */ 
/* 173 */         ServletFileUpload fileUpload = new ServletFileUpload(diskFactory);
/*     */ 
/* 177 */         fileUpload.setSizeMax(lMaxFileSize);
/*     */ 
/* 180 */         List items = fileUpload.parseRequest(a_request);
/*     */ 
/* 182 */         Iterator iter = items.iterator();
/*     */ 
/* 185 */         while (iter.hasNext())
/*     */         {
/* 187 */           FileItem fileItem = (FileItem)iter.next();
/*     */ 
/* 189 */           if (fileItem.isFormField())
/*     */           {
/*     */             continue;
/*     */           }
/*     */ 
/* 194 */           String sNewFileName = getUploadDirectory(a_request) + fileItem.getName();
/*     */ 
/* 196 */           String sTempFilePath = sNewFileName + (iNumChunk > 0 ? ".part" : "");
/*     */ 
/* 199 */           InputStream isItem = fileItem.getInputStream();
/*     */ 
/* 202 */           byte[] byteBuff = new byte[1024];
/*     */ 
/* 204 */           boolean bNotFirstChunk = false;
/*     */ 
/* 206 */           if (iNumChunk > 1)
/*     */           {
/* 208 */             bNotFirstChunk = true;
/*     */           }
/*     */ 
/* 212 */           FileOutputStream fos = new FileOutputStream(sTempFilePath, bNotFirstChunk);
/*     */           int iBytes;
/* 215 */           while ((iBytes = isItem.read(byteBuff)) >= 0)
/*     */           {
/* 217 */             fos.write(byteBuff, 0, iBytes);
/*     */           }
/*     */ 
/* 221 */           isItem.close();
/* 222 */           fos.close();
/*     */ 
/* 225 */           fileItem.delete();
/*     */ 
/* 227 */           a_response.setStatus(200);
/* 228 */           PrintWriter out = a_response.getWriter();
/* 229 */           out.print("All OK!");
/*     */ 
/* 232 */           File fUploadedFile = new File(sTempFilePath);
/*     */ 
/* 234 */           if (fUploadedFile.length() > lMaxFileSize)
/*     */           {
/* 236 */             throw new IOException("FileUploadServlet.processUpload: Uploaded file is too large. The max file size for uploaded files can be changed in the ApplicationSettings.properties file");
/*     */           }
/*     */ 
/* 242 */           if (bLastChunk)
/*     */           {
/* 244 */             File fRenameTo = new File(sNewFileName);
/* 245 */             fUploadedFile.renameTo(fRenameTo);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 252 */         throw new IOException("FileUploadServlet.processUpload: Exception occurred, unable to process uploaded files: " + e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.servlet.FileUploadServlet
 * JD-Core Version:    0.6.0
 */