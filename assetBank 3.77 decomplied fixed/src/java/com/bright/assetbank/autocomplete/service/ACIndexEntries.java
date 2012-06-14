/*    */ package com.bright.assetbank.autocomplete.service;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.collections.Factory;
/*    */ import org.apache.commons.collections.MapUtils;
/*    */ 
/*    */ public class ACIndexEntries
/*    */ {
/* 32 */   private static Factory acIndexEntryListFactory = new Factory()
/*    */   {
/*    */     public Object create()
/*    */     {
/* 36 */       return new ArrayList();
/*    */     }
/* 32 */   };
/*    */ 
/* 40 */   private Map<String, List<ACIndexEntry>> indexEntriesToSaveByLanguage = MapUtils.lazyMap(new HashMap(), acIndexEntryListFactory);
/* 41 */   private Map<String, List<ACIndexEntry>> indexEntriesToDeleteByLanguage = MapUtils.lazyMap(new HashMap(), acIndexEntryListFactory);
/*    */ 
/*    */   public Map<String, List<ACIndexEntry>> getIndexEntriesToDeleteByLanguage()
/*    */   {
/* 45 */     return this.indexEntriesToDeleteByLanguage;
/*    */   }
/*    */ 
/*    */   public Map<String, List<ACIndexEntry>> getIndexEntriesToSaveByLanguage()
/*    */   {
/* 50 */     return this.indexEntriesToSaveByLanguage;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.ACIndexEntries
 * JD-Core Version:    0.6.0
 */