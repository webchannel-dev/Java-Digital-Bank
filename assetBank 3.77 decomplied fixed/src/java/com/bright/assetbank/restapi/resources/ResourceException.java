/*    */ package com.bright.assetbank.restapi.resources;
/*    */ 
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
/*    */ import javax.ws.rs.core.Response.Status;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class ResourceException extends WebApplicationException
/*    */ {
/*    */   public ResourceException(Throwable exc, Response.Status status)
/*    */   {
/* 26 */     super(exc, status);
/* 27 */     Log logger = GlobalApplication.getInstance().getLogger();
/* 28 */     logger.error("Exception in Jersey REST API.  (Returned HTTP Status " + status.getStatusCode() + ": " + status.toString() + ")\n", exc);
/*    */   }
/*    */ 
/*    */   public ResourceException(Response.Status status)
/*    */   {
/* 37 */     super(status);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.resources.ResourceException
 * JD-Core Version:    0.6.0
 */