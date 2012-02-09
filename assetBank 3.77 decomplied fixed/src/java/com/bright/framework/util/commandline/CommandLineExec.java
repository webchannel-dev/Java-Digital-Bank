/*     */ package com.bright.framework.util.commandline;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class CommandLineExec
/*     */ {
/*     */   public static final long k_lWaitTimeInMillis = 50L;
/*     */ 
/*     */   public static int execute(String[] a_saCommand, StringBuffer a_sbOutput, StringBuffer a_sbErrors)
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     return execute(a_saCommand, a_sbOutput, a_sbErrors, -1L);
/*     */   }
/*     */ 
/*     */   public static int execute(String[] a_saCommand, StringBuffer a_sbOutput, StringBuffer a_sbErrors, long a_lProcessTimeout)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     return execute(a_saCommand, a_sbOutput, a_sbErrors, a_lProcessTimeout, "ISO-8859-1");
/*     */   }
/*     */ 
/*     */   public static int execute(String[] a_saCommand, StringBuffer a_sbOutput, StringBuffer a_sbErrors, long a_lProcessTimeout, String a_sOutputCharSet)
/*     */     throws Bn2Exception
/*     */   {
/*  98 */     int iExitVal = -1;
/*  99 */     Process proc = null;
/*     */     try
/*     */     {
/* 104 */       if (GlobalApplication.getInstance().getLogger().isDebugEnabled())
/*     */       {
/* 106 */         GlobalApplication.getInstance().getLogger().debug("CommandLineExec.execute: about to run command " + StringUtil.convertStringArrayToString(a_saCommand, " "));
/*     */       }
/*     */ 
/* 109 */       Runtime rt = Runtime.getRuntime();
/*     */ 
/* 111 */       proc = rt.exec(a_saCommand);
/*     */ 
/* 114 */       StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), a_sbErrors, a_sOutputCharSet);
/*     */ 
/* 119 */       StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), a_sbOutput, a_sOutputCharSet);
/*     */ 
/* 124 */       errorGobbler.start();
/*     */ 
/* 127 */       outputGobbler.run();
/*     */ 
/* 130 */       if (a_lProcessTimeout > 0L)
/*     */       {
/* 133 */         ProcessWaiter processWaiter = new ProcessWaiter(proc);
/* 134 */         processWaiter.start();
/*     */ 
/* 136 */         long lStartTime = System.currentTimeMillis();
/*     */ 
/* 139 */         while ((!processWaiter.hasFinished()) && (lStartTime + a_lProcessTimeout > System.currentTimeMillis()))
/*     */         {
/* 143 */           Thread.sleep(50L);
/*     */         }
/*     */ 
/* 147 */         if (!processWaiter.hasFinished())
/*     */         {
/* 150 */           proc.destroy();
/*     */         }
/*     */         else
/*     */         {
/* 154 */           iExitVal = processWaiter.getExitValue();
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 160 */         iExitVal = proc.waitFor();
/*     */       }
/*     */ 
/* 164 */       while ((!errorGobbler.hasFinished()) || (!outputGobbler.hasFinished()))
/*     */       {
/* 167 */         Thread.sleep(50L);
/*     */       }
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 172 */       throw new Bn2Exception(t.toString(), t);
/*     */     }
/*     */     finally
/*     */     {
/* 177 */       if (proc != null)
/*     */       {
/*     */         try
/*     */         {
/* 181 */           proc.getErrorStream().close();
/* 182 */           proc.getInputStream().close();
/* 183 */           proc.getOutputStream().close();
/*     */         }
/*     */         catch (IOException io)
/*     */         {
/* 187 */           a_sbErrors.append("\nCommandLineExec: IOException when attempting to close streams.");
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 193 */     return iExitVal;
/*     */   }
/*     */ 
/*     */   public static String execute(String[] a_saCommand)
/*     */     throws Bn2Exception
/*     */   {
/* 212 */     StringBuffer sbOut = new StringBuffer();
/*     */ 
/* 215 */     String sDebugCommand = "";
/* 216 */     for (int i = 0; i < a_saCommand.length; i++)
/*     */     {
/* 218 */       sDebugCommand = sDebugCommand + a_saCommand[i] + " ";
/*     */     }
/*     */ 
/* 222 */     GlobalApplication.getInstance().getLogger().debug("CommandLineExec.execute: about to run command " + sDebugCommand);
/*     */ 
/* 225 */     int iCode = -1;
/*     */     try
/*     */     {
/* 229 */       StringBuffer sbErrors = new StringBuffer();
/* 230 */       iCode = execute(a_saCommand, sbOut, sbErrors);
/*     */ 
/* 232 */       if (iCode != 0)
/*     */       {
/* 234 */         throw new Bn2Exception("Failed to run command: " + sDebugCommand + " returned code: " + iCode + ". Error output: " + sbErrors.toString());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 239 */       throw new Bn2Exception("Failed to run command: " + sDebugCommand + " : " + e.getMessage(), e);
/*     */     }
/*     */ 
/* 242 */     return sbOut.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.commandline.CommandLineExec
 * JD-Core Version:    0.6.0
 */