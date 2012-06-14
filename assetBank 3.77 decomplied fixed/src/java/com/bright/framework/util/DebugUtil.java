/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.StringWriter;
/*    */ import java.io.Writer;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class DebugUtil
/*    */ {
/*    */   public static void threadDump(Writer a_w)
/*    */     throws IOException
/*    */   {
/* 36 */     Map stackTraces = Thread.getAllStackTraces();
/* 37 */     for (Iterator it = stackTraces.entrySet().iterator(); it.hasNext(); )
/*    */     {
/* 39 */       Map.Entry entry = (Map.Entry)it.next();
/* 40 */       Thread thread = (Thread)entry.getKey();
/* 41 */       a_w.write(thread.toString() + "\n");
/* 42 */       StackTraceElement[] stackTrace = (StackTraceElement[])entry.getValue();
/* 43 */       for (StackTraceElement stackTraceElement : stackTrace)
/*    */       {
/* 45 */         a_w.write("\t at " + stackTraceElement.toString() + "\n");
/*    */       }
/*    */ 
/* 48 */       if (it.hasNext())
/*    */       {
/* 50 */         a_w.write("\n");
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String getThreadDumpAsString()
/*    */   {
/* 60 */     StringWriter sw = new StringWriter();
/*    */     try
/*    */     {
/* 63 */       threadDump(sw);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 67 */       throw new RuntimeException(e);
/*    */     }
/* 69 */     return sw.toString();
/*    */   }
/*    */ 
/*    */   public static String stackTraceElementsToString(StackTraceElement[] a_stackTraceElements)
/*    */   {
/* 74 */     StringBuilder sbTrace = new StringBuilder();
/* 75 */     for (StackTraceElement a_stackTraceElement : a_stackTraceElements)
/*    */     {
/* 77 */       sbTrace.append("\n\t at ").append(a_stackTraceElement.toString());
/*    */     }
/* 79 */     return sbTrace.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.DebugUtil
 * JD-Core Version:    0.6.0
 */