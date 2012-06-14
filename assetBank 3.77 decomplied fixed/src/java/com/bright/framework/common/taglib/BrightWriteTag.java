/*     */ package com.bright.framework.common.taglib;
/*     */ 
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.WordUtils;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.util.ResponseUtils;
/*     */ 
/*     */ public class BrightWriteTag extends WriteTag
/*     */ {
/*     */   private static final String k_sUrlEncodedTokenPlaceholder = "\\[enc-token\\]";
/*     */   private static final String k_sTokenPlaceholder = "\\[token\\]";
/*     */   private static final String k_sTokenPlaceholderUnescaped = "[token]";
/*     */   public static final String k_sCASE_LOWER = "lower";
/*     */   public static final String k_sCASE_UPPER = "upper";
/*     */   public static final String k_sCASE_MIXED = "mixed";
/*  67 */   private int m_iMaxLength = 0;
/*  68 */   private String m_sEndString = null;
/*  69 */   private String m_sMaxLengthBean = null;
/*  70 */   private boolean m_bFormatCR = false;
/*  71 */   protected String m_sCase = null;
/*  72 */   private boolean m_bEncodeForUrl = false;
/*     */ 
/*  74 */   private String m_sTokenDelimiter = null;
/*  75 */   private String m_sOutputTokenWrapper = null;
/*  76 */   private String m_sTokenDelimiterReplacement = null;
/*  77 */   private int m_iMaxTokens = 0;
/*  78 */   private String m_sIgnoreTokens = null;
/*     */ 
/*     */   protected String formatValue(Object a_valueToFormat)
/*     */     throws JspException
/*     */   {
/*  98 */     String sValue = super.formatValue(a_valueToFormat);
/*     */ 
/* 101 */     if ((this.m_iMaxLength > 0) || (this.m_sMaxLengthBean != null))
/*     */     {
/* 104 */       if ((this.m_sEndString == null) || (this.m_sEndString.length() == 0))
/*     */       {
/* 106 */         throw new JspException("BrightWriteTag: if you specify a max length then you must specify a value for attribute endString.");
/*     */       }
/*     */ 
/* 110 */       int iMaxLength = this.m_iMaxLength;
/*     */ 
/* 112 */       if (iMaxLength <= 0)
/*     */       {
/* 115 */         String intMaxLength = (String)this.pageContext.getAttribute(this.m_sMaxLengthBean, 1);
/* 116 */         iMaxLength = Integer.parseInt(intMaxLength);
/*     */       }
/*     */ 
/* 120 */       if ((sValue != null) && (sValue.length() > iMaxLength))
/*     */       {
/* 123 */         int iPos = iMaxLength;
/*     */ 
/* 125 */         while ((iPos > -1) && (sValue.charAt(iPos) != ' '))
/*     */         {
/* 127 */           iPos--;
/*     */         }
/*     */ 
/* 130 */         if (iPos > -1)
/*     */         {
/* 132 */           sValue = sValue.substring(0, iPos) + this.m_sEndString;
/*     */         }
/*     */         else
/*     */         {
/* 136 */           sValue = sValue.substring(0, iMaxLength) + this.m_sEndString;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 142 */     if ((sValue != null) && (this.m_sCase != null))
/*     */     {
/* 144 */       sValue = getAlteredCaseValue(sValue, this.m_sCase);
/*     */     }
/*     */ 
/* 148 */     if ((StringUtils.isNotEmpty(this.m_sOutputTokenWrapper)) || (StringUtils.isNotEmpty(this.m_sTokenDelimiter)))
/*     */     {
/* 150 */       sValue = doTokenReplacement(sValue);
/*     */     }
/* 152 */     else if (this.m_bEncodeForUrl)
/*     */     {
/* 154 */       sValue = ResponseUtils.encodeURL(sValue);
/*     */     }
/*     */ 
/* 158 */     if ((!this.m_bEncodeForUrl) && (this.m_bFormatCR))
/*     */     {
/* 161 */       sValue = StringUtil.formatNewlineForHTML(sValue);
/*     */     }
/*     */ 
/* 164 */     return sValue;
/*     */   }
/*     */ 
/*     */   private String doTokenReplacement(String a_sValue)
/*     */   {
/* 174 */     if (StringUtils.isNotEmpty(this.m_sTokenDelimiter))
/*     */     {
/* 176 */       if (StringUtils.isEmpty(this.m_sOutputTokenWrapper))
/*     */       {
/* 178 */         this.m_sOutputTokenWrapper = "[token]";
/*     */       }
/*     */ 
/* 182 */       Pattern pattern = Pattern.compile(this.m_sTokenDelimiter);
/* 183 */       Matcher matcher = pattern.matcher(a_sValue);
/* 184 */       ArrayList delims = new ArrayList();
/*     */ 
/* 186 */       while (matcher.find())
/*     */       {
/* 188 */         delims.add(a_sValue.subSequence(matcher.start(), matcher.end()));
/*     */       }
/*     */ 
/* 191 */       String[] asIgnoreTokens = new String[0];
/*     */ 
/* 194 */       if (StringUtils.isNotEmpty(this.m_sIgnoreTokens))
/*     */       {
/* 196 */         asIgnoreTokens = this.m_sIgnoreTokens.split("\\|");
/* 197 */         Arrays.sort(asIgnoreTokens);
/*     */       }
/*     */ 
/* 201 */       String[] tokens = null;
/*     */ 
/* 203 */       if (this.m_iMaxTokens > 0)
/*     */       {
/* 205 */         tokens = a_sValue.split(this.m_sTokenDelimiter, this.m_iMaxTokens + 1);
/*     */       }
/*     */       else
/*     */       {
/* 209 */         tokens = a_sValue.split(this.m_sTokenDelimiter);
/*     */       }
/*     */ 
/* 213 */       StringBuffer output = new StringBuffer();
/*     */ 
/* 216 */       for (int i = 0; (i < tokens.length) && ((this.m_iMaxTokens == 0) || (i < this.m_iMaxTokens)); i++)
/*     */       {
/* 218 */         if ((tokens[i].length() <= 2) || (Arrays.binarySearch(asIgnoreTokens, tokens[i]) >= 0))
/*     */           continue;
/* 220 */         if (i > 0)
/*     */         {
/* 222 */           if (StringUtils.isNotEmpty(this.m_sTokenDelimiterReplacement))
/*     */           {
/* 224 */             if (output.length() > 0)
/*     */             {
/* 226 */               output.append(this.m_sTokenDelimiterReplacement);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 231 */             output.append(delims.size() > i - 1 ? delims.get(i - 1) : " ");
/*     */           }
/*     */         }
/* 234 */         String sWrapper = this.m_sOutputTokenWrapper.replaceAll("\\[enc-token\\]", ResponseUtils.encodeURL(tokens[i]));
/* 235 */         output.append(sWrapper.replaceAll("\\[token\\]", tokens[i]));
/*     */       }
/*     */ 
/* 239 */       a_sValue = output.toString();
/*     */     }
/*     */     else
/*     */     {
/* 243 */       String sWrapper = this.m_sOutputTokenWrapper.replaceAll("\\[enc-token\\]", ResponseUtils.encodeURL(a_sValue));
/* 244 */       a_sValue = sWrapper.replaceAll("\\[token\\]", a_sValue);
/*     */     }
/*     */ 
/* 247 */     return a_sValue;
/*     */   }
/*     */ 
/*     */   private static String getAlteredCaseValue(String a_sValue, String a_sCaseIdentifier)
/*     */   {
/* 259 */     String sResult = null;
/*     */ 
/* 261 */     if ((a_sValue != null) && (a_sCaseIdentifier != null))
/*     */     {
/* 263 */       if (a_sCaseIdentifier.equals("lower"))
/*     */       {
/* 265 */         sResult = a_sValue.toLowerCase();
/*     */       }
/* 267 */       else if (a_sCaseIdentifier.equals("upper"))
/*     */       {
/* 269 */         sResult = a_sValue.toUpperCase();
/*     */       }
/* 271 */       else if (a_sCaseIdentifier.equals("mixed"))
/*     */       {
/* 273 */         sResult = WordUtils.capitalize(a_sValue);
/*     */       }
/*     */     }
/* 276 */     return sResult;
/*     */   }
/*     */ 
/*     */   public void release()
/*     */   {
/* 284 */     super.release();
/* 285 */     this.m_iMaxLength = 0;
/* 286 */     this.m_sEndString = null;
/* 287 */     this.m_sMaxLengthBean = null;
/* 288 */     this.m_bFormatCR = false;
/* 289 */     this.m_sCase = null;
/* 290 */     this.m_bEncodeForUrl = false;
/*     */   }
/*     */ 
/*     */   public int getMaxLength()
/*     */   {
/* 296 */     return this.m_iMaxLength;
/*     */   }
/*     */ 
/*     */   public void setMaxLength(int a_iMaxLength) {
/* 300 */     this.m_iMaxLength = a_iMaxLength;
/*     */   }
/*     */ 
/*     */   public String getEndString() {
/* 304 */     return this.m_sEndString;
/*     */   }
/*     */ 
/*     */   public void setEndString(String a_sEndString) {
/* 308 */     this.m_sEndString = a_sEndString;
/*     */   }
/*     */ 
/*     */   public String getMaxLengthBean() {
/* 312 */     return this.m_sMaxLengthBean;
/*     */   }
/*     */ 
/*     */   public void setMaxLengthBean(String a_sMaxLengthBean) {
/* 316 */     this.m_sMaxLengthBean = a_sMaxLengthBean;
/*     */   }
/*     */ 
/*     */   public boolean getFormatCR() {
/* 320 */     return this.m_bFormatCR;
/*     */   }
/*     */ 
/*     */   public void setFormatCR(boolean a_bFormatCR) {
/* 324 */     this.m_bFormatCR = a_bFormatCR;
/*     */   }
/*     */ 
/*     */   public String getCase()
/*     */   {
/* 329 */     return this.m_sCase;
/*     */   }
/*     */ 
/*     */   public void setCase(String a_sCase) {
/* 333 */     this.m_sCase = a_sCase;
/*     */   }
/*     */ 
/*     */   public boolean getEncodeForUrl()
/*     */   {
/* 338 */     return this.m_bEncodeForUrl;
/*     */   }
/*     */ 
/*     */   public void setEncodeForUrl(boolean a_bEncodeForUrl)
/*     */   {
/* 343 */     this.m_bEncodeForUrl = a_bEncodeForUrl;
/*     */   }
/*     */ 
/*     */   public String getOutputTokenWrapper()
/*     */   {
/* 348 */     return this.m_sOutputTokenWrapper;
/*     */   }
/*     */ 
/*     */   public void setOutputTokenWrapper(String a_sOutputTokenWrapper)
/*     */   {
/* 353 */     this.m_sOutputTokenWrapper = a_sOutputTokenWrapper;
/*     */   }
/*     */ 
/*     */   public void setOutputTokenWrapper(Object a_oOutputTokenWrapper)
/*     */   {
/* 358 */     this.m_sOutputTokenWrapper = String.valueOf(a_oOutputTokenWrapper);
/*     */   }
/*     */ 
/*     */   public String getTokenDelimiter()
/*     */   {
/* 363 */     return this.m_sTokenDelimiter;
/*     */   }
/*     */ 
/*     */   public void setTokenDelimiter(String a_sTokenDelimiter)
/*     */   {
/* 368 */     this.m_sTokenDelimiter = a_sTokenDelimiter;
/*     */   }
/*     */ 
/*     */   public void setTokenDelimiter(Object a_oTokenDelimiter)
/*     */   {
/* 373 */     this.m_sTokenDelimiter = String.valueOf(a_oTokenDelimiter);
/*     */   }
/*     */ 
/*     */   public String getTokenDelimiterReplacement()
/*     */   {
/* 378 */     return this.m_sTokenDelimiterReplacement;
/*     */   }
/*     */ 
/*     */   public void setTokenDelimiterReplacement(String a_sTokenDelimiterReplacement)
/*     */   {
/* 383 */     this.m_sTokenDelimiterReplacement = a_sTokenDelimiterReplacement;
/*     */   }
/*     */ 
/*     */   public int getMaxTokens()
/*     */   {
/* 388 */     return this.m_iMaxTokens;
/*     */   }
/*     */ 
/*     */   public void setMaxTokens(int maxTokens)
/*     */   {
/* 393 */     this.m_iMaxTokens = maxTokens;
/*     */   }
/*     */ 
/*     */   public String getIgnoreTokens()
/*     */   {
/* 398 */     return this.m_sIgnoreTokens;
/*     */   }
/*     */ 
/*     */   public void setIgnoreTokens(String ignoreTokens)
/*     */   {
/* 403 */     this.m_sIgnoreTokens = ignoreTokens;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.taglib.BrightWriteTag
 * JD-Core Version:    0.6.0
 */