/*     */ package com.bright.framework.cache;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class SizeLimitedObjectCache extends LinkedHashMap
/*     */ {
/*  32 */   private int m_iMaxSize = 0;
/*     */ 
/*     */   public SizeLimitedObjectCache(int a_iMaxSize)
/*     */   {
/*  36 */     super((int)Math.min(a_iMaxSize * 1.6D, 100.0D), 0.75F, true);
/*  37 */     this.m_iMaxSize = a_iMaxSize;
/*     */   }
/*     */ 
/*     */   protected boolean removeEldestEntry(Map.Entry arg0)
/*     */   {
/*  42 */     return size() > this.m_iMaxSize;
/*     */   }
/*     */ 
/*     */   public synchronized Object put(Object a_key, Object a_value)
/*     */   {
/*  47 */     return super.put(a_key, a_value);
/*     */   }
/*     */ 
/*     */   public synchronized void putAll(Map a_map)
/*     */   {
/*  52 */     super.putAll(a_map);
/*     */   }
/*     */ 
/*     */   public synchronized void clear()
/*     */   {
/*  57 */     super.clear();
/*     */   }
/*     */ 
/*     */   public synchronized boolean containsValue(Object value)
/*     */   {
/*  62 */     return super.containsValue(value);
/*     */   }
/*     */ 
/*     */   public synchronized Object get(Object key)
/*     */   {
/*  67 */     return super.get(key);
/*     */   }
/*     */ 
/*     */   public synchronized Object clone()
/*     */   {
/*  72 */     return super.clone();
/*     */   }
/*     */ 
/*     */   public synchronized boolean containsKey(Object key)
/*     */   {
/*  77 */     return super.containsKey(key);
/*     */   }
/*     */ 
/*     */   public synchronized Set entrySet()
/*     */   {
/*  82 */     return super.entrySet();
/*     */   }
/*     */ 
/*     */   public synchronized boolean isEmpty()
/*     */   {
/*  87 */     return super.isEmpty();
/*     */   }
/*     */ 
/*     */   public synchronized Set keySet()
/*     */   {
/*  92 */     return super.keySet();
/*     */   }
/*     */ 
/*     */   public synchronized Object remove(Object key)
/*     */   {
/*  97 */     return super.remove(key);
/*     */   }
/*     */ 
/*     */   public synchronized int size()
/*     */   {
/* 102 */     return super.size();
/*     */   }
/*     */ 
/*     */   public synchronized Collection values()
/*     */   {
/* 107 */     return super.values();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.cache.SizeLimitedObjectCache
 * JD-Core Version:    0.6.0
 */