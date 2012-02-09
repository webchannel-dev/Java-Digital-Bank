/*     */ package com.bright.assetbank.application.servlet;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.FileFormat;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class RelatedAssetFileServlet extends HttpServlet
/*     */   implements AssetBankConstants
/*     */ {
/*  61 */   private FileStoreManager m_fileStoreManager = null;
/*  62 */   private IAssetManager m_assetManager = null;
/*  63 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*     */ 
/*  65 */   private Log m_log = GlobalApplication.getInstance().getLogger();
/*     */ 
/*     */   public void init() throws ServletException
/*     */   {
/*  69 */     super.init();
/*     */     try
/*     */     {
/*  73 */       this.m_fileStoreManager = ((FileStoreManager)GlobalApplication.getInstance().getComponentManager().lookup("FileStoreManager"));
/*  74 */       this.m_assetManager = ((IAssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager"));
/*  75 */       this.m_assetRelationshipManager = ((AssetRelationshipManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetRelationshipManager"));
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/*  79 */       this.m_log.error("ComponentException whilst getting FileStoreManager in FileDisplayServlet : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws IOException, ServletException
/*     */   {
/* 103 */     File file = null;
/* 104 */     String sContentType = null;
/* 105 */     String sFilename = null;
/* 106 */     long lId = 0L;
/* 107 */     String sPathInfo = null;
/* 108 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/* 110 */     sPathInfo = a_request.getPathInfo();
/*     */ 
/* 112 */     int iIndexOfSlash = sPathInfo.lastIndexOf("/");
/*     */ 
/* 115 */     if ((iIndexOfSlash < 0) || (iIndexOfSlash == sPathInfo.length() - 1))
/*     */     {
/* 117 */       a_response.sendError(404);
/* 118 */       return;
/*     */     }
/*     */ 
/* 121 */     String sId = sPathInfo.substring(1, iIndexOfSlash > 0 ? iIndexOfSlash : sPathInfo.length());
/*     */ 
/* 124 */     if (iIndexOfSlash <= 0)
/*     */     {
/* 126 */       int iExt = sId.lastIndexOf(".");
/* 127 */       if (iExt > 0)
/*     */       {
/* 129 */         sId = sId.substring(0, sId.lastIndexOf("."));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 136 */       lId = Long.parseLong(sId);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/* 140 */       a_response.sendError(404);
/* 141 */       return;
/*     */     }
/*     */ 
/* 144 */     if (iIndexOfSlash > 0)
/*     */     {
/* 146 */       sFilename = sPathInfo.substring(iIndexOfSlash + 1);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 152 */       Asset asset = this.m_assetRelationshipManager.getRelatedAssetByFilename(null, lId, sFilename);
/*     */ 
/* 155 */       if ((!asset.getCanEmbedFile()) && (!this.m_assetManager.userCanViewAsset(userProfile, asset)))
/*     */       {
/* 157 */         String sError = "AssetFileServlet.doGet() : " + (userProfile.getUser() != null ? "User id=" + userProfile.getUser().getId() : "Public user") + " doesn't have permission to view asset file using path: " + sPathInfo;
/*     */ 
/* 161 */         this.m_log.error(sError);
/* 162 */         throw new AssetNotFoundException(sError);
/*     */       }
/*     */ 
/* 166 */       sFilename = this.m_fileStoreManager.getAbsolutePath(asset.getFileLocation());
/*     */ 
/* 169 */       sContentType = asset.getFormat().getContentType();
/*     */     }
/*     */     catch (AssetNotFoundException e)
/*     */     {
/* 173 */       a_response.sendError(404);
/* 174 */       return;
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 178 */       throw new ServletException("Bn2Exception caught whilst getting full path of file to display : " + e.getLocalizedMessage(), e);
/*     */     }
/*     */ 
/* 182 */     file = new File(sFilename);
/*     */ 
/* 184 */     InputStream is = null;
/* 185 */     OutputStream os = null;
/*     */ 
/* 188 */     if ((file.exists()) && (file.canRead()))
/*     */     {
/* 191 */       if (sContentType != null)
/*     */       {
/* 193 */         a_response.setContentType(sContentType);
/*     */       }
/*     */ 
/* 197 */       a_response.setContentLength((int)file.length());
/*     */       try
/*     */       {
/* 201 */         is = new BufferedInputStream(new FileInputStream(file));
/* 202 */         os = new BufferedOutputStream(a_response.getOutputStream());
/*     */ 
/* 204 */         byte[] buffer = new byte[65535];
/*     */         int nbytes;
/* 208 */         while ((nbytes = is.read(buffer)) != -1)
/*     */         {
/* 210 */           a_response.getOutputStream().write(buffer, 0, nbytes);
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/*     */       }
/*     */       finally
/*     */       {
/* 219 */         if (is != null)
/*     */         {
/* 221 */           is.close();
/*     */         }
/*     */ 
/* 224 */         if (os != null)
/*     */         {
/* 226 */           os.flush();
/*     */         }
/* 228 */         a_response.flushBuffer();
/* 229 */         if (os != null)
/*     */         {
/* 231 */           os.close();
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 238 */       a_response.sendError(404);
/* 239 */       a_response.flushBuffer();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.servlet.RelatedAssetFileServlet
 * JD-Core Version:    0.6.0
 */