/*     */ package com.bright.framework.image.xmp;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ 
/*     */ public class XMPNamespaceContext
/*     */   implements NamespaceContext
/*     */ {
/*  40 */   private static HashMap m_hmURIs = buildURIMap();
/*  41 */   private static HashMap m_hmPrefixes = reverseMap(m_hmURIs);
/*     */ 
/*     */   public String getNamespaceURI(String a_sPrefix)
/*     */   {
/*  51 */     String uri = (String)m_hmURIs.get(a_sPrefix);
/*  52 */     if (uri == null)
/*     */     {
/*  54 */       uri = "";
/*     */     }
/*  56 */     return uri;
/*     */   }
/*     */ 
/*     */   public String getPrefix(String a_sNamespace)
/*     */   {
/*  66 */     String prefix = (String)m_hmPrefixes.get(a_sNamespace);
/*  67 */     if (prefix == null)
/*     */     {
/*  69 */       prefix = "";
/*     */     }
/*  71 */     return prefix;
/*     */   }
/*     */ 
/*     */   public static Collection getPrefixes()
/*     */   {
/*  79 */     return m_hmURIs.keySet();
/*     */   }
/*     */ 
/*     */   public Iterator getPrefixes(String a_sNamespace)
/*     */   {
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */   private static HashMap buildURIMap()
/*     */   {
/*  94 */     HashMap map = new HashMap();
/*     */ 
/*  96 */     map.put("xmp", "http://ns.adobe.com/xap/1.0/");
/*  97 */     map.put("photoshop", "http://ns.adobe.com/photoshop/1.0/");
/*  98 */     map.put("tiff", "http://ns.adobe.com/tiff/1.0/");
/*  99 */     map.put("xap", "http://ns.adobe.com/xap/1.0/");
/* 100 */     map.put("exif", "http://ns.adobe.com/exif/1.0/");
/* 101 */     map.put("dc", "http://purl.org/dc/elements/1.1/");
/*     */ 
/* 103 */     map.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
/* 104 */     map.put("xapMM", "http://ns.adobe.com/xap/1.0/mm/");
/* 105 */     map.put("xapRights", "http://ns.adobe.com/xap/1.0/rights/");
/* 106 */     map.put("Iptc4xmpCore", "http://iptc.org/std/Iptc4xmpCore/1.0/xmlns/");
/*     */ 
/* 109 */     return map;
/*     */   }
/*     */ 
/*     */   private static HashMap reverseMap(Map a_map)
/*     */   {
/* 120 */     HashMap map = new HashMap();
/*     */ 
/* 122 */     Iterator it = a_map.keySet().iterator();
/* 123 */     while (it.hasNext())
/*     */     {
/* 125 */       Object key = it.next();
/* 126 */       map.put(a_map.get(key), key);
/*     */     }
/*     */ 
/* 129 */     return map;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.xmp.XMPNamespaceContext
 * JD-Core Version:    0.6.0
 */