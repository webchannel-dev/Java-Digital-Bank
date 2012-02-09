/*    */ package com.bright.assetbank.restapi.resources;
/*    */ 
/*    */ import com.bright.assetbank.restapi.representations.AssetIdRepr;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URISyntaxException;
/*    */ import java.net.URL;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.ws.rs.POST;
/*    */ import javax.ws.rs.Path;
/*    */ import javax.ws.rs.Produces;
/*    */ import javax.ws.rs.core.Context;
/*    */ import javax.ws.rs.core.Response;
/*    */ import javax.ws.rs.core.Response.ResponseBuilder;
/*    */ import javax.ws.rs.core.Response.Status;
/*    */ 
/*    */ @Path("editor-dependencies/")
/*    */ public class EditorDependenciesBaseResource extends BaseResource
/*    */ {
/*    */   public static URL getURL(URL apiRootURL)
/*    */     throws MalformedURLException
/*    */   {
/* 42 */     String path = ((Path)EditorDependenciesBaseResource.class.getAnnotation(Path.class)).value();
/* 43 */     return new URL(apiRootURL, path);
/*    */   }
/*    */ 
/*    */   @POST
/*    */   @Produces({"application/xml", "application/json"})
/*    */   public Response getEditorDependencies(@Context HttpServletRequest request, AssetIdRepr assetId)
/*    */   {
/* 61 */     checkPermissions(request);
/*    */     try
/*    */     {
/* 65 */       URL apiRootURL = RootResource.getAPIRootURL(request);
/*    */ 
/* 68 */       URL other = EditorDependenciesResource.getURL(apiRootURL, assetId.assetId);
/* 69 */       return Response.seeOther(other.toURI()).build();
/*    */     } catch (MalformedURLException exc) {
/* 71 */       throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR); } catch (URISyntaxException exc) {
/*    */     
/* 73 */     throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);}
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.resources.EditorDependenciesBaseResource
 * JD-Core Version:    0.6.0
 */