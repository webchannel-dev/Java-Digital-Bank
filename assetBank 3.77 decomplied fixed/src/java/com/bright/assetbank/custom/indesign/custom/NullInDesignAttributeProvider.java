/*    */ package com.bright.assetbank.custom.indesign.custom;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class NullInDesignAttributeProvider
/*    */   implements InDesignAttributeProvider
/*    */ {
/*    */   public Collection<Long> getAvailableAttributeIds(long a_inDesignDocumentAssetId)
/*    */     throws Bn2Exception
/*    */   {
/* 29 */     return Collections.emptySet();
/*    */   }
/*    */ 
/*    */   public Map<Long, String> getAttributeValues(long a_inDesignDocumentAssetId, Collection<Long> a_attributeIds) throws Bn2Exception
/*    */   {
/* 34 */     return Collections.emptyMap();
/*    */   }
/*    */ 
/*    */   public Collection<Long> getAttributeDependentAssetIds(long a_assetId, Set<Long> a_attributeIds)
/*    */   {
/* 39 */     return Collections.emptySet();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.custom.NullInDesignAttributeProvider
 * JD-Core Version:    0.6.0
 */