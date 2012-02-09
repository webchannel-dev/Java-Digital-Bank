/*     */ package com.bright.framework.util;
/*     */ 
/*     */ public class UnicodeUtil
/*     */ {
/*     */   public static String convertHTML2Unicode(String a_sValue)
/*     */   {
/*  40 */     StringBuffer sb = new StringBuffer();
/*     */ 
/*  42 */     while (!a_sValue.equals(""))
/*     */     {
/*  44 */       a_sValue = processFirstEntity(sb, a_sValue);
/*     */     }
/*     */ 
/*  47 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private static String processFirstEntity(StringBuffer a_sb, String s)
/*     */   {
/*  65 */     int iPos = s.indexOf("&");
/*     */ 
/*  67 */     if (iPos >= 0)
/*     */     {
/*  69 */       int iPos2 = s.indexOf(";", iPos);
/*  70 */       a_sb.append(s.substring(0, iPos));
/*     */ 
/*  72 */       if (iPos2 > 0)
/*     */       {
/*  74 */         String sEnt = s.substring(iPos + 1, iPos2);
/*     */ 
/*  76 */         if (sEnt.equals("amp"))
/*     */         {
/*  78 */           a_sb.append("&");
/*  79 */           return s.substring(iPos2 + 1);
/*     */         }
/*  81 */         if (sEnt.equals("gt"))
/*     */         {
/*  83 */           a_sb.append(">");
/*  84 */           return s.substring(iPos2 + 1);
/*  85 */         }if (sEnt.equals("lt"))
/*     */         {
/*  87 */           a_sb.append("<");
/*  88 */           return s.substring(iPos2 + 1);
/*     */         }
/*  90 */         if (sEnt.equals("apos"))
/*     */         {
/*  92 */           a_sb.append("'");
/*  93 */           return s.substring(iPos2 + 1);
/*     */         }
/*  95 */         if (sEnt.equals("quot"))
/*     */         {
/*  97 */           a_sb.append("\"");
/*  98 */           return s.substring(iPos2 + 1);
/*     */         }
/* 100 */         if (sEnt.startsWith("#"))
/*     */         {
/* 102 */           sEnt = sEnt.substring(1);
/*     */ 
/* 104 */           String sHexValue = null;
/*     */ 
/* 106 */           if (sEnt.startsWith("x"))
/*     */           {
/* 108 */             sHexValue = sEnt.substring(1);
/*     */           }
/*     */           else
/*     */           {
/*     */             try
/*     */             {
/* 114 */               int iDecValue = Integer.parseInt(sEnt);
/* 115 */               sHexValue = Integer.toHexString(iDecValue);
/*     */             }
/*     */             catch (NumberFormatException nfe)
/*     */             {
/* 119 */               a_sb.append("&");
/* 120 */               return s.substring(1);
/*     */             }
/*     */           }
/*     */ 
/* 124 */           a_sb.append("\\u");
/*     */ 
/* 127 */           for (int i = sHexValue.length(); i < 4; i++)
/*     */           {
/* 129 */             a_sb.append("0");
/*     */           }
/*     */ 
/* 133 */           a_sb.append(sHexValue);
/*     */ 
/* 135 */           return s.substring(iPos2 + 1);
/*     */         }
/*     */ 
/* 139 */         a_sb.append("&");
/* 140 */         return s.substring(iPos + 1);
/*     */       }
/*     */ 
/* 144 */       a_sb.append("&");
/* 145 */       return s.substring(iPos + 1);
/*     */     }
/*     */ 
/* 149 */     a_sb.append(s);
/* 150 */     return "";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.UnicodeUtil
 * JD-Core Version:    0.6.0
 */