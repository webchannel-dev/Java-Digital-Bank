/*    */ package com.bright.assetbank.restapi.representations;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ @XmlRootElement
/*    */ public class EditorDependenciesRepr
/*    */ {
/*    */   public URL primaryUrl;
/*    */   public List<URL> secondaryUrls;
/*    */ 
/*    */   public EditorDependenciesRepr()
/*    */   {
/*    */   }
/*    */ 
/*    */   public EditorDependenciesRepr(URL a_primaryUrl, List<URL> a_secondaryUrls)
/*    */   {
/* 17 */     this.primaryUrl = a_primaryUrl;
/* 18 */     this.secondaryUrls = a_secondaryUrls;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.representations.EditorDependenciesRepr
 * JD-Core Version:    0.6.0
 */