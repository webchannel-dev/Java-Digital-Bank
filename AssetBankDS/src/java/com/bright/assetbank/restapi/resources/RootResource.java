/*    */ package com.bright.assetbank.restapi.resources;
/*    */ 
/*    */ import com.bright.assetbank.restapi.representations.RootRepr;
/*    */ import com.bright.framework.util.ServletUtil;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.ws.rs.GET;
/*    */ import javax.ws.rs.Path;
/*    */ import javax.ws.rs.Produces;
/*    */ import javax.ws.rs.core.Context;
/*    */ import javax.ws.rs.core.Response;
/*    */ 
/*    */ @Path("/")
/*    */ public class RootResource extends BaseResource
/*    */ {
/*    */   public static URL getAPIRootURL(HttpServletRequest request)
/*    */     throws MalformedURLException
/*    */   {
/* 49 */     return new URL(ServletUtil.getApplicationUrl(request) + "/rest/");
/*    */   }
/*    */ 
/*    */   @GET
/*    */   @Produces({"application/xml", "application/json"})
/*    */   public RootRepr getRootResource(@Context HttpServletRequest request)
/*    */   {
/* 63 */     checkPermissions(request);
/*    */     try
/*    */     {
/* 67 */       URL apiRootURL = getAPIRootURL(request);
/*    */ 
/* 70 */       URL checkoutUrl = CheckoutBaseResource.getURL(apiRootURL);
/* 71 */       URL editorDependanciesUrl = EditorDependenciesBaseResource.getURL(apiRootURL);
/* 72 */       return new RootRepr(checkoutUrl, editorDependanciesUrl); } catch (MalformedURLException exc) {
/*    */     
/* 74 */     throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);}
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.resources.RootResource
 * JD-Core Version:    0.6.0
 */