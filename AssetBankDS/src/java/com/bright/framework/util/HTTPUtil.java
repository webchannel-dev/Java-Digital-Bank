/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import com.bright.framework.constant.FrameworkSettings;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ 
/*    */ public class HTTPUtil
/*    */ {
/* 24 */   static AtomicBoolean mDoneInit = new AtomicBoolean(false);
/*    */ 
/*    */   public static void setProxySettings()
/*    */   {
/* 32 */     if (mDoneInit.getAndSet(true)) {
/* 33 */       return;
/*    */     }
/*    */ 
/* 37 */     String sProxyHost = FrameworkSettings.getProxyHost();
/*    */ 
/* 39 */     if ((sProxyHost == null) || (sProxyHost.length() == 0))
/*    */     {
/* 41 */       return;
/*    */     }
/*    */ 
/* 44 */     System.setProperty("http.proxySet", "true");
/* 45 */     System.setProperty("http.proxyHost", sProxyHost);
/* 46 */     System.setProperty("http.proxyPort", "" + FrameworkSettings.getProxyPort());
/*    */ 
/* 49 */     String sProxyUsername = FrameworkSettings.getProxyUsername();
/* 50 */     String sNTLMDomain = FrameworkSettings.getNTLMDomain();
/* 51 */     if ((sNTLMDomain != null) && (sNTLMDomain.length() > 0))
/*    */     {
/* 53 */       System.setProperty("http.auth.ntlm.domain", sNTLMDomain);
/*    */     }
/*    */ 
/* 56 */     if ((sProxyUsername != null) && (sProxyUsername.length() > 0))
/*    */     {
/* 58 */       System.setProperty("http.proxyUser", sProxyUsername);
/* 59 */       System.setProperty("http.proxyPassword", FrameworkSettings.getProxyPassword());
/*    */     }
/*    */ 
/* 64 */     String sExclusions = FrameworkSettings.getProxyExclusions();
/* 65 */     if (StringUtil.stringIsPopulated(sExclusions))
/*    */     {
/* 67 */       System.setProperty("http.nonProxyHosts", sExclusions);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.HTTPUtil
 * JD-Core Version:    0.6.0
 */