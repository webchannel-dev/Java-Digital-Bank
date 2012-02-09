/*    */ package com.bright.assetbank.restapi.resources;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.clientsideedit.service.ClientSideEditingManager;
/*    */ import com.bright.assetbank.restapi.representations.EditorDependenciesRepr;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.ws.rs.GET;
/*    */ import javax.ws.rs.Path;
/*    */ import javax.ws.rs.PathParam;
/*    */ import javax.ws.rs.Produces;
/*    */ import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
/*    */ import javax.ws.rs.core.Response.Status;
/*    */ import org.apache.avalon.framework.component.ComponentException;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ 
/*    */ @Path("assets/{id}/editor-dependancies/")
/*    */ public class EditorDependenciesResource extends BaseResource
/*    */ {
/*    */   private static final String bracesRegex = "\\{[^}]*\\}";
/*    */ 
/*    */   public static URL getURL(URL apiRootURL, long assetId)
/*    */     throws MalformedURLException
/*    */   {
/* 60 */     String path = ((Path)EditorDependenciesResource.class.getAnnotation(Path.class)).value();
/* 61 */     path = path.replaceFirst("\\{[^}]*\\}", Long.toString(assetId));
/* 62 */     return new URL(apiRootURL, path);
/*    */   }
/*    */ 
/*    */   @GET
/*    */   @Produces({"application/xml", "application/json"})
/*    */   public EditorDependenciesRepr getEditorDependencies(@Context HttpServletRequest request, @PathParam("id") long id)
/*    */   {
/* 71 */     checkPermissions(request);
/*    */     try
/*    */     {
/* 76 */       URL apiRootURL = RootResource.getAPIRootURL(request);
/*    */ 
/* 78 */       ClientSideEditingManager clientSideEditingManager = (ClientSideEditingManager)GlobalApplication.getInstance().getComponentManager().lookup("ClientSideEditingManager");
/*    */ 
/* 80 */       return clientSideEditingManager.getEditorDependencies(apiRootURL, id);
/*    */     }
/*    */     catch (ComponentException exc)
/*    */     {
/* 84 */       throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);
/*    */     }
/*    */     catch (MalformedURLException exc)
/*    */     {
/* 88 */       throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);
/*    */     }
/*    */     catch (Bn2Exception exc) {
/*    */     
/* 92 */     throw new ResourceException(exc, Response.Status.INTERNAL_SERVER_ERROR);}
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.resources.EditorDependenciesResource
 * JD-Core Version:    0.6.0
 */