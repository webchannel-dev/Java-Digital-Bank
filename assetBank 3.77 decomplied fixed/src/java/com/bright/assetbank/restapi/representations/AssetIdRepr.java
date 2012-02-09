/*    */ package com.bright.assetbank.restapi.representations;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ 
/*    */ @XmlRootElement
/*    */ public class AssetIdRepr
/*    */ {
/*    */   public long assetId;
/*    */ 
/*    */   public AssetIdRepr()
/*    */   {
/* 22 */     this.assetId = 0L;
/*    */   }
/*    */ 
/*    */   public AssetIdRepr(long a_assetId) {
/* 26 */     this.assetId = a_assetId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.restapi.representations.AssetIdRepr
 * JD-Core Version:    0.6.0
 */