/*     */ package com.bright.framework.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class TextPatternReplacer
/*     */ {
/*     */   private String m_sContents;
/*     */   private Matcher m_Matcher;
/*     */   private Vector m_vecReplacements;
/*     */ 
/*     */   public TextPatternReplacer()
/*     */   {
/*  48 */     this.m_sContents = null;
/*  49 */     this.m_Matcher = null;
/*  50 */     this.m_vecReplacements = new Vector();
/*     */   }
/*     */ 
/*     */   public void loadFromFile(String a_sFullFilePath, String a_sCharEncoding)
/*     */     throws IOException
/*     */   {
/*  75 */     StringBuffer sb = FileUtil.readIntoStringBuffer(a_sFullFilePath, a_sCharEncoding);
/*  76 */     this.m_sContents = sb.toString();
/*     */   }
/*     */ 
/*     */   public void setContents(String a_sContents)
/*     */   {
/*  91 */     this.m_sContents = a_sContents;
/*     */   }
/*     */ 
/*     */   public void setPattern(String a_sPattern)
/*     */     throws Bn2Exception
/*     */   {
/* 106 */     if (this.m_sContents == null)
/*     */     {
/* 108 */       throw new Bn2Exception("You must set the contents in TextFileChanger before setting the pattern");
/*     */     }
/*     */ 
/* 111 */     Pattern pattern = Pattern.compile(a_sPattern);
/* 112 */     this.m_Matcher = pattern.matcher(this.m_sContents);
/*     */   }
/*     */ 
/*     */   public boolean matchNext()
/*     */     throws Bn2Exception
/*     */   {
/* 128 */     if (this.m_Matcher == null)
/*     */     {
/* 130 */       throw new Bn2Exception("You must set the pattern in TextFileChanger before calling matchNext");
/*     */     }
/*     */ 
/* 133 */     boolean bFoundNewMatch = false;
/* 134 */     boolean bReachedEnd = false;
/*     */ 
/* 137 */     while ((!bFoundNewMatch) && (!bReachedEnd))
/*     */     {
/* 140 */       if (this.m_Matcher.find())
/*     */       {
/* 142 */         String sNextMatch = this.m_Matcher.group();
/*     */ 
/* 145 */         bFoundNewMatch = !alreadyAddedMatch(sNextMatch);
/*     */ 
/* 148 */         continue;
/*     */       }
/*     */ 
/* 152 */       bReachedEnd = true;
/*     */     }
/*     */ 
/* 156 */     return bFoundNewMatch;
/*     */   }
/*     */ 
/*     */   public String getCurrentMatch()
/*     */     throws Bn2Exception
/*     */   {
/* 171 */     if (this.m_Matcher == null)
/*     */     {
/* 173 */       throw new Bn2Exception("You must set the pattern and then call matchNext before calling getCurrentMatchString");
/*     */     }
/*     */ 
/* 176 */     String sCurrentMatch = null;
/*     */     try
/*     */     {
/* 180 */       sCurrentMatch = this.m_Matcher.group();
/*     */     }
/*     */     catch (IllegalStateException ise)
/*     */     {
/* 184 */       throw new Bn2Exception("Cannot call getCurrentMatch - either you have not called matchNext or matchNext return false", ise);
/*     */     }
/*     */ 
/* 187 */     return sCurrentMatch;
/*     */   }
/*     */ 
/*     */   public void addReplacementForCurrentMatch(String a_sReplacement)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 205 */       Replacement replacement = new Replacement();
/* 206 */       replacement.sMatch = this.m_Matcher.group();
/* 207 */       replacement.sReplacement = a_sReplacement;
/*     */ 
/* 210 */       this.m_vecReplacements.add(replacement);
/*     */     }
/*     */     catch (IllegalStateException ise)
/*     */     {
/* 214 */       throw new Bn2Exception("Cannot call addReplacementForCurrentMatch - either you have not called matchNext or matchNext return false", ise);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void makeReplacements()
/*     */   {
/* 232 */     for (int i = 0; i < this.m_vecReplacements.size(); i++)
/*     */     {
/* 234 */       Replacement replacement = (Replacement)this.m_vecReplacements.get(i);
/*     */ 
/* 237 */       this.m_sContents = StringUtil.replaceString(this.m_sContents, replacement.sMatch, replacement.sReplacement);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getContents()
/*     */   {
/* 254 */     return this.m_sContents;
/*     */   }
/*     */ 
/*     */   public void saveToFile(String a_sFilepath)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 271 */       FileWriter fwContents = new FileWriter(a_sFilepath);
/* 272 */       fwContents.write(this.m_sContents);
/* 273 */       fwContents.close();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 277 */       throw new Bn2Exception("Error in TextFileChanger.saveToFile:", ioe);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean alreadyAddedMatch(String a_sMatch)
/*     */   {
/* 293 */     boolean bAlready = false;
/*     */ 
/* 295 */     for (int i = 0; i < this.m_vecReplacements.size(); i++)
/*     */     {
/* 297 */       Replacement replacement = (Replacement)this.m_vecReplacements.get(i);
/* 298 */       if (!replacement.sMatch.equals(a_sMatch))
/*     */         continue;
/* 300 */       bAlready = true;
/* 301 */       break;
/*     */     }
/*     */ 
/* 305 */     return bAlready;
/*     */   }
/*     */ 
/*     */   class Replacement
/*     */   {
/*  55 */     protected String sMatch = null;
/*  56 */     protected String sReplacement = null;
/*     */ 
/*     */     Replacement()
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.TextPatternReplacer
 * JD-Core Version:    0.6.0
 */