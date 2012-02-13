/*    */ package com.bright.assetbank.clientsideedit.service;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.Bn2Manager;
/*    */ import com.bright.assetbank.restapi.representations.EditorDependenciesRepr;
/*    */ import com.bright.assetbank.restapi.resources.AssetContentResource;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ClientSideEditingManager extends Bn2Manager
/*    */ {
/* 31 */   private List<EditorDependenciesProvider> m_editorDependenciesProviders = new ArrayList();
/*    */ 
/*    */   public EditorDependenciesRepr getEditorDependencies(URL a_apiRootURL, long a_assetId)
/*    */     throws MalformedURLException, Bn2Exception
/*    */   {
/* 49 */     EditorDependenciesRepr editorDependencies = null;
/*    */ 
/* 52 */     for (EditorDependenciesProvider editorDependenciesProvider : this.m_editorDependenciesProviders)
/*    */     {
/* 54 */       EditorDependenciesRepr overriddenEditorDependencies = editorDependenciesProvider.getEditorDependenciesForAsset(a_apiRootURL, a_assetId);
/* 55 */       if (editorDependencies != null)
/*    */       {
/* 59 */         throw new Bn2Exception("More than one EditorDependenciesProvider can provide dependencies for asset " + a_assetId + ". There should be at most one EditorDependenciesProvider that can provide dependencies for each asset.");
/*    */       }
/*    */ 
/* 64 */       editorDependencies = overriddenEditorDependencies;
/*    */     }
/*    */ 
/* 68 */     if (editorDependencies == null)
/*    */     {
/* 73 */       URL primaryUrl = AssetContentResource.getURL(a_apiRootURL, a_assetId);
/* 74 */       List secondaryUrls = new ArrayList();
/* 75 */       editorDependencies = new EditorDependenciesRepr(primaryUrl, secondaryUrls);
/*    */     }
/* 77 */     return editorDependencies;
/*    */   }
/*    */ 
/*    */   public void addEditorDependencyProvider(EditorDependenciesProvider a_editorDependenciesProvider)
/*    */   {
/* 82 */     this.m_editorDependenciesProviders.add(a_editorDependenciesProvider);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.clientsideedit.service.ClientSideEditingManager
 * JD-Core Version:    0.6.0
 */