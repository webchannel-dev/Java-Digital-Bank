/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MultiValueMap<K, V, C extends Collection<V>>
/*    */ {
/* 27 */   Map<K, C> m_map = new HashMap();
/*    */   private Factory<C> m_collectionFactory;
/*    */ 
/*    */   public MultiValueMap(Factory<C> a_collectionFactory)
/*    */   {
/* 32 */     this.m_collectionFactory = a_collectionFactory;
/*    */   }
/*    */ 
/*    */   public void add(K a_key, V a_value)
/*    */   {
/* 37 */     Collection values = (Collection)this.m_map.get(a_key);
/* 38 */     if (values == null)
/*    */     {
/* 43 */       values = (Collection)this.m_collectionFactory.newInstance();
/* 44 */       this.m_map.put(a_key,(C) values);
/*    */     }
/*    */ 
/* 47 */     values.add(a_value);
/*    */   }
/*    */ 
/*    */   public C get(K a_key)
/*    */   {
/* 52 */     return this.m_map.get(a_key);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.MultiValueMap
 * JD-Core Version:    0.6.0
 */