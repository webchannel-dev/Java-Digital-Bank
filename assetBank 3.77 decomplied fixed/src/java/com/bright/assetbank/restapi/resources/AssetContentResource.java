/*     */ package com.bright.assetbank.restapi.resources;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.bean.AssetFileSource;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.ws.rs.Consumes;
/*     */ import javax.ws.rs.GET;
/*     */ import javax.ws.rs.PUT;
/*     */ import javax.ws.rs.Path;
/*     */ import javax.ws.rs.PathParam;
/*     */ import javax.ws.rs.Produces;
/*     */ import javax.ws.rs.WebApplicationException;
/*     */ import javax.ws.rs.core.Context;
/*     */ import javax.ws.rs.core.Response;
/*     */ import javax.ws.rs.core.Response.ResponseBuilder;
/*     */ import javax.ws.rs.core.Response.Status;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ @Path("assets/{assetId}/content/")
/*     */ public class AssetContentResource extends BaseResource
/*     */ {
/*     */   private static final String bracesRegex = "\\{[^}]*\\}";
/*     */ 
/*     */   public static URL getURL(URL apiRootURL, long assetId)
/*     */     throws MalformedURLException
/*     */   {
/*  75 */     String path = ((Path)AssetContentResource.class.getAnnotation(Path.class)).value();
/*  76 */     path = path.replaceFirst("\\{[^}]*\\}", Long.toString(assetId));
/*  77 */     return new URL(apiRootURL, path);
/*     */   }
/*     */ 
/*     */   @GET
/*     */   @Produces({"application/octet-stream"})
/*     */   public Response getAsset(@Context HttpServletRequest request, @PathParam("assetId") String assetId)
/*     */   {
/*  86 */     checkPermissions(request);
/*     */     try
/*     */     {
/*  89 */       AssetManager assetManager = (AssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager");
/*  90 */       FileStoreManager fileStoreManager = (FileStoreManager)GlobalApplication.getInstance().getComponentManager().lookup("FileStoreManager");
/*     */ 
/*  92 */       Asset asset = assetManager.getAsset(null, Long.parseLong(assetId), null, false, false);
/*     */ 
/*  94 */       String sSource = asset.getOriginalFileLocation();
/*  95 */       if (StringUtils.isEmpty(sSource))
/*     */       {
/*  97 */         sSource = asset.getFileLocation();
/*     */       }
/*     */ 
/* 100 */       sSource = fileStoreManager.getAbsolutePath(sSource);
/* 101 */       String contentDisposition = "attachment; filename=" + asset.getOriginalFilename();
/*     */ 
/* 103 */       return Response.ok(new FileInputStream(new File(sSource)), "application/octet-stream").header("Content-Disposition", contentDisposition).build();
/*     */     }
/*     */     catch (ComponentException exc) {
/* 106 */       throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);
/*     */     } catch (Bn2Exception exc) {
/* 108 */       throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR); } catch (FileNotFoundException exc) {
/*     */     
/* 110 */     throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);}
/*     */   }
/*     */ 
/*     */   @PUT
/*     */   @Consumes({"application/octet-stream"})
/*     */   public Response putAsset(@Context HttpServletRequest request, @PathParam("assetId") String assetId, InputStream file)
/*     */   {
/* 121 */     ABUserProfile userProfile = checkPermissions(request);
/*     */     try
/*     */     {
/* 124 */       AssetManager assetManager = (AssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager");
/* 125 */       ABUserManager userManager = (ABUserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager");
/*     */ 
/* 128 */       Asset asset = assetManager.getAsset(null, Long.parseLong(assetId), null, false, false);
/*     */ 
/* 131 */       AssetFileSource source = new AssetFileSource();
/* 132 */       source.setInputStream(file);
/* 133 */       source.setFilename(asset.getOriginalFilename());
/* 134 */       source.setOriginalFilename(asset.getOriginalFilename());
/*     */       long userId;
/*     */       //long userId;
/* 139 */       if (userProfile == null)
/*     */       {
/* 141 */         ABUser user = userManager.getApplicationUser();
/* 142 */         userId = user.getId();
/*     */       } else {
/* 144 */         userId = userProfile.getUserId();
/*     */       }
/*     */ 
/* 148 */       assetManager.saveAsset(null, asset, source, userId, null, null, false, 0);
/*     */ 
/* 150 */       return Response.ok().build();
/*     */     } catch (ComponentException e) {
/* 152 */       throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); } catch (Bn2Exception e) {
/*     */     }
/* 154 */     throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.resources.AssetContentResource
 * JD-Core Version:    0.6.0
 */