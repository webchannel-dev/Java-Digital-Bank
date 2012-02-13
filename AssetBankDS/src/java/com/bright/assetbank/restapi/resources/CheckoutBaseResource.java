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
/*    */ @Path("checkout/")
/*    */ public class CheckoutBaseResource extends BaseResource
/*    */ {
/*    */   public static URL getURL(URL apiRootURL)
/*    */     throws MalformedURLException
/*    */   {
/* 43 */     String path = ((Path)CheckoutBaseResource.class.getAnnotation(Path.class)).value();
/* 44 */     return new URL(apiRootURL, path);
/*    */   }
/*    */ 
/*    */   @POST
/*    */   @Produces({"application/xml", "application/json"})
/*    */   public Response createCheckoutResource(@Context HttpServletRequest request, AssetIdRepr assetId)
/*    */   {
/* 53 */     checkPermissions(request);
/*    */     try
/*    */     {
/* 57 */       URL apiRootURL = RootResource.getAPIRootURL(request);
/*    */ 
/* 60 */       URL other = CheckoutResource.getURL(apiRootURL, assetId.assetId);
/* 61 */       return Response.seeOther(other.toURI()).build();
/*    */     }
/*    */     catch (MalformedURLException exc) {
/* 64 */       throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR); } catch (URISyntaxException exc) {
/*    */     
/* 66 */     throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);}
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.resources.CheckoutBaseResource
 * JD-Core Version:    0.6.0
 */