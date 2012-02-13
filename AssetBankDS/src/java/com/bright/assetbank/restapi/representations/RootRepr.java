/*    */ package com.bright.assetbank.restapi.representations;
/*    */ 
/*    */ import java.net.URL;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ @XmlRootElement
/*    */ public class RootRepr
/*    */ {
/* 27 */   private static String API_NAME = "AssetBank RESTful API";
/*    */   public String api_name;
/*    */   public RootServices api_version_1_0;
/*    */ 
/*    */   public RootRepr()
/*    */   {
/* 38 */     this.api_name = null;
/* 39 */     this.api_version_1_0 = null;
/*    */   }
/*    */ 
/*    */   public RootRepr(URL a_checkoutUrl, URL a_editorDependanciesUrl) {
/* 43 */     this.api_name = API_NAME;
/* 44 */     this.api_version_1_0 = new RootServices();
/* 45 */     this.api_version_1_0.checkoutUrl = a_checkoutUrl;
/* 46 */     this.api_version_1_0.editorDependanciesUrl = a_editorDependanciesUrl;
/*    */   }
/*    */ 
/*    */   public static class RootServices
/*    */   {
/*    */     public URL checkoutUrl;
/*    */     public URL editorDependanciesUrl;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.representations.RootRepr
 * JD-Core Version:    0.6.0
 */