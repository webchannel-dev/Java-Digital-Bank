/*     */ package com.bright.assetbank.converter;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.converter.exception.AssetConversionException;
/*     */ import com.bright.framework.util.commandline.CommandLineExec;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class CommandLineConverter
/*     */   implements AssetConverter
/*     */ {
/*     */   protected abstract String getCommand();
/*     */ 
/*     */   protected boolean getQuotePaths()
/*     */   {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean getUniformSlashes()
/*     */   {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   protected int getSuccessCode()
/*     */   {
/*  55 */     return 0;
/*     */   }
/*     */ 
/*     */   public void convert(String a_sBaseSourcePath, String a_sBaseDestPath, String a_sRelativeSourcePath, String a_sRelativeDestinationPath, String[] a_asExtraParams)
/*     */     throws AssetConversionException
/*     */   {
/*  77 */     Vector vecParams = new Vector();
/*     */ 
/*  81 */     String sCommandStem = getCommand();
/*  82 */     String[] asCommandStem = sCommandStem.split(" ");
/*  83 */     for (int i = 0; i < asCommandStem.length; i++)
/*     */     {
/*  85 */       vecParams.add(asCommandStem[i]);
/*     */     }
/*     */ 
/*  89 */     if (a_sBaseSourcePath == null)
/*     */     {
/*  91 */       a_sBaseSourcePath = "";
/*     */     }
/*     */     else
/*     */     {
/*  95 */       a_sBaseSourcePath = a_sBaseSourcePath + "/";
/*     */     }
/*     */ 
/*  99 */     if (a_sBaseDestPath == null)
/*     */     {
/* 101 */       a_sBaseDestPath = "";
/*     */     }
/*     */     else
/*     */     {
/* 105 */       a_sBaseDestPath = a_sBaseDestPath + "/";
/*     */     }
/*     */ 
/* 108 */     String sTempPath = a_sBaseSourcePath + a_sRelativeSourcePath;
/* 109 */     if (getQuotePaths())
/*     */     {
/* 111 */       sTempPath = "\"" + sTempPath + "\"";
/*     */     }
/*     */ 
/* 114 */     if (getUniformSlashes())
/*     */     {
/* 116 */       sTempPath = sTempPath.replaceAll("/", "\\\\");
/*     */     }
/* 118 */     vecParams.add(sTempPath);
/*     */ 
/* 122 */     if (a_sRelativeDestinationPath != null)
/*     */     {
/* 124 */       sTempPath = a_sBaseDestPath + a_sRelativeDestinationPath;
/* 125 */       if (getQuotePaths())
/*     */       {
/* 127 */         sTempPath = "\"" + sTempPath + "\"";
/*     */       }
/* 129 */       if (getUniformSlashes())
/*     */       {
/* 131 */         sTempPath = sTempPath.replaceAll("/", "\\\\");
/*     */       }
/* 133 */       vecParams.add(sTempPath);
/*     */     }
/*     */ 
/* 137 */     if (a_asExtraParams != null)
/*     */     {
/* 139 */       for (int i = 0; i < a_asExtraParams.length; i++)
/*     */       {
/* 141 */         vecParams.add(a_asExtraParams[i]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 147 */     int iCount = vecParams.size();
/* 148 */     String[] aCmd = new String[iCount];
/* 149 */     for (int i = 0; i < iCount; i++)
/*     */     {
/* 151 */       aCmd[i] = ((String)vecParams.get(i));
/*     */     }
/*     */ 
/* 155 */     String sCommandForLog = "";
/* 156 */     for (int i = 0; i < iCount; i++)
/*     */     {
/* 158 */       sCommandForLog = sCommandForLog + aCmd[i] + " ";
/*     */     }
/* 160 */     GlobalApplication.getInstance().getLogger().debug("CommandLineConverter: about to run command " + sCommandForLog);
/*     */ 
/* 164 */     StringBuffer sbOut = new StringBuffer();
/* 165 */     int iCode = -1;
/*     */     try
/*     */     {
/* 168 */       StringBuffer sbErrors = new StringBuffer();
/* 169 */       iCode = CommandLineExec.execute(aCmd, sbOut, sbErrors);
/*     */ 
/* 171 */       if (iCode != getSuccessCode())
/*     */       {
/* 173 */         throw new Exception("Returned code: " + iCode + ", error output: " + sbErrors.toString());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 178 */       throw new AssetConversionException("CommandLineConverter: " + sCommandForLog + " : " + e.getMessage());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.CommandLineConverter
 * JD-Core Version:    0.6.0
 */