/*     */ package com.bright.framework.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ 
/*     */ public abstract class StringReplaceParser
/*     */ {
/*     */   public static final char k_cDefaultVariableStartToken = '#';
/*     */   public static final char k_cDefaultVariableEndToken = '#';
/*     */   public static final int k_iVariableMaxLength = 50;
/*  23 */   private char m_cVariableStartToken = '\000';
/*  24 */   private char m_cVariableEndToken = '\000';
/*     */ 
/*     */   protected abstract String getReplacement(String paramString)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public StringReplaceParser()
/*     */   {
/*  33 */     this('#', '#');
/*     */   }
/*     */ 
/*     */   public StringReplaceParser(char a_cVariableStartToken, char a_cVariableEndToken)
/*     */   {
/*  45 */     this.m_cVariableStartToken = a_cVariableStartToken;
/*  46 */     this.m_cVariableEndToken = a_cVariableEndToken;
/*     */   }
/*     */ 
/*     */   public String parse(String a_sText)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     StringBuffer sbNewText = new StringBuffer();
/*  60 */     boolean bInPossibleVariable = false;
/*  61 */     String sVariableName = "";
/*  62 */     String sContentIfNotVariable = "";
/*     */ 
/*  64 */     for (int i = 0; i < a_sText.length(); i++)
/*     */     {
/*  66 */       if ((!bInPossibleVariable) && (a_sText.charAt(i) == this.m_cVariableStartToken))
/*     */       {
/*  70 */         bInPossibleVariable = true;
/*     */ 
/*  73 */         sContentIfNotVariable = "" + a_sText.charAt(i);
/*     */       }
/*  75 */       else if ((bInPossibleVariable) && ((a_sText.charAt(i) == this.m_cVariableEndToken) || (sVariableName.length() > 50)))
/*     */       {
/*  79 */         String sReplacement = null;
/*  80 */         if (a_sText.charAt(i) == this.m_cVariableEndToken)
/*     */         {
/*  83 */           sReplacement = getReplacement(sVariableName);
/*     */ 
/*  86 */           if (sReplacement != null)
/*     */           {
/*  88 */             sbNewText.append(sReplacement);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*  93 */         if (sReplacement == null)
/*     */         {
/*  96 */           sbNewText.append(sContentIfNotVariable);
/*     */ 
/*  99 */           i--;
/*     */         }
/*     */ 
/* 103 */         bInPossibleVariable = false;
/* 104 */         sVariableName = "";
/* 105 */         sContentIfNotVariable = "";
/*     */       }
/* 107 */       else if ((bInPossibleVariable) && (a_sText.charAt(i) != this.m_cVariableEndToken))
/*     */       {
/* 111 */         sVariableName = sVariableName + a_sText.charAt(i);
/* 112 */         sContentIfNotVariable = sContentIfNotVariable + a_sText.charAt(i);
/*     */       }
/*     */       else
/*     */       {
/* 117 */         sbNewText.append(a_sText.charAt(i));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 122 */     sbNewText.append(sContentIfNotVariable);
/*     */ 
/* 124 */     return sbNewText.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.StringReplaceParser
 * JD-Core Version:    0.6.0
 */