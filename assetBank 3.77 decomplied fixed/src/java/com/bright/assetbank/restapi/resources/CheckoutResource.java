/*     */ package com.bright.assetbank.restapi.resources;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.checkout.service.CheckoutManager;
/*     */ import com.bright.assetbank.checkout.service.CheckoutManager.CheckedOutException;
/*     */ import com.bright.assetbank.restapi.representations.CheckoutRepr;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.ws.rs.GET;
/*     */ import javax.ws.rs.PUT;
/*     */ import javax.ws.rs.Path;
/*     */ import javax.ws.rs.PathParam;
/*     */ import javax.ws.rs.Produces;
/*     */ import javax.ws.rs.WebApplicationException;
/*     */ import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
/*     */ import javax.ws.rs.core.Response.Status;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ @Path("assets/{assetId}/checkout/")
/*     */ public class CheckoutResource extends BaseResource
/*     */ {
/*     */   private static final String bracesRegex = "\\{[^}]*\\}";
/*     */ 
/*     */   public static URL getURL(URL apiRootURL, long assetId)
/*     */     throws MalformedURLException
/*     */   {
/*  66 */     String path = ((Path)CheckoutResource.class.getAnnotation(Path.class)).value();
/*  67 */     path = path.replaceFirst("\\{[^}]*\\}", Long.toString(assetId));
/*  68 */     return new URL(apiRootURL, path);
/*     */   }
/*     */ 
/*     */   @PUT
/*     */   @Produces({"application/xml", "application/json"})
/*     */   public CheckoutRepr updateCheckout(@Context HttpServletRequest request, @PathParam("assetId") String assetId, CheckoutRepr checkout)
/*     */   {
/*  86 */     ABUserProfile userProfile = checkPermissions(request);
/*  87 */     boolean isCheckout = checkout.userId > 0L;
/*     */ 
/*  90 */     if ((userProfile != null) && (isCheckout) && (checkout.userId != userProfile.getUserId())) {
/*  91 */       throw new WebApplicationException(Response.Status.FORBIDDEN);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  96 */       CheckoutManager checkoutManager = (CheckoutManager)GlobalApplication.getInstance().getComponentManager().lookup("CheckoutManager");
/*     */ 
/*  98 */       if (isCheckout)
/*     */       {
/* 101 */         checkoutManager.checkoutAsset(null, Long.parseLong(assetId), checkout.userId);
/*     */       }
/*     */       else
/*     */       {
/*     */         long userId;
/*     */         //long userId;
/* 107 */         if (userProfile != null)
/*     */         {
/* 109 */           userId = userProfile.getUserId();
/*     */         }
/*     */         else {
/* 112 */           userId = checkoutManager.getCheckoutStatus(null, Long.parseLong(assetId));
/*     */         }
/* 114 */         checkoutManager.checkinAsset(null, Long.parseLong(assetId), userId);
/*     */       }
/*     */ 
/* 118 */       return new CheckoutRepr(checkout.userId);
/*     */     }
/*     */     catch (NumberFormatException exc) {
/* 121 */       throw new ResourceException(Response.Status.BAD_REQUEST);
/*     */     } catch (ComponentException exc) {
/* 123 */       throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);
/*     */     } catch (Bn2Exception exc) {
/* 125 */       throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR); } catch (CheckoutManager.CheckedOutException exc) {
/*     */     }
/* 127 */     throw new ResourceException(Response.Status.CONFLICT);
/*     */   }
/*     */ 
/*     */   @GET
/*     */   @Produces({"application/xml", "application/json"})
/*     */   public CheckoutRepr readCheckout(@Context HttpServletRequest request, @PathParam("assetId") String assetId)
/*     */   {
/* 136 */     checkPermissions(request);
/*     */     try
/*     */     {
/* 140 */       CheckoutManager checkoutManager = (CheckoutManager)GlobalApplication.getInstance().getComponentManager().lookup("CheckoutManager");
/*     */ 
/* 142 */       long userId = checkoutManager.getCheckoutStatus(null, Long.parseLong(assetId));
/* 143 */       return new CheckoutRepr(userId);
/*     */     }
/*     */     catch (NumberFormatException exc) {
/* 146 */       throw new ResourceException(Response.Status.BAD_REQUEST);
/*     */     } catch (ComponentException exc) {
/* 148 */       throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR); } catch (Bn2Exception exc) {
/*     */     
/* 150 */     throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);}
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.resources.CheckoutResource
 * JD-Core Version:    0.6.0
 */