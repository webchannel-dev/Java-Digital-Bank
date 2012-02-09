/*    */ package com.bright.assetbank.custom.indesign.batchrel;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.AssetLog;
/*    */ import com.bright.assetbank.batchrel.service.DependencyProvider;
/*    */ import com.bright.assetbank.custom.indesign.custom.InDesignAttributeProvider;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignDependencyProvider
/*    */   implements DependencyProvider
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private InDesignAttributeProvider m_attributeProvider;
/*    */ 
/*    */   public Collection<Long> getDependentAssetIds(long a_assetId, Collection<AssetLog> a_changes)
/*    */   {
/* 39 */     Collection dependentAssetIds = new HashSet();
/*    */ 
/* 49 */     Set changedAttributeIds = null;
/*    */ 
/* 51 */     dependentAssetIds.addAll(this.m_attributeProvider.getAttributeDependentAssetIds(a_assetId, changedAttributeIds));
/*    */ 
/* 53 */     return dependentAssetIds;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.batchrel.InDesignDependencyProvider
 * JD-Core Version:    0.6.0
 */