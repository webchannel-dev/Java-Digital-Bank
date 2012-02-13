/*      */ package com.bright.framework.util;
/*      */ 
/*      */ import com.bn2web.common.exception.Bn2Exception;
/*      */ import com.bn2web.common.service.GlobalApplication;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.framework.common.bean.NameValueBean;
/*      */ import com.bright.framework.database.bean.DataBean;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.text.ParseException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.struts.util.ResponseUtils;
/*      */ 
/*      */ public class StringUtil
/*      */ {
/*      */   private static final int S_TEXT = 0;
/*      */   private static final int S_AMP = 1;
/*      */   private static final int S_HASH = 2;
/*      */   private static final int S_DIGIT = 3;
/*      */   public static final String k_sEncryptionPassword = "534836598276491234623965329837659823";
/*  102 */   private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*      */ 
/*  108 */   private static final Pattern c_kHTMLNewLinePattern = Pattern.compile("</li>|</div>|</p>|</br>|<br.*?/>|&nbsp;");
/*      */ 
/*  110 */   private static final Pattern c_kHTMLTagPattern = Pattern.compile("<.*?>");
/*      */   private static final char c_kcDefaultDelim = ',';
/*      */   private static final char c_kcDefaultEscape = '\\';
/*      */ 
/*      */   public static String replaceMarkupText(String a_sText, String a_sStartTag, String a_sEndTag, String a_sReplacementPattern, String a_sReplacementMarkerHTML, String a_sReplacementMarkerJavascript)
/*      */   {
/*  152 */     StringBuffer sbNewText = new StringBuffer("");
/*      */ 
/*  154 */     int iStartSearchPos = 0;
/*  155 */     int iStartTagPos = 0;
/*  156 */     int iEndTagPos = 0;
/*  157 */     String sReplacement = null;
/*  158 */     String sID = null;
/*  159 */     String sJavascriptSafeID = null;
/*      */ 
/*  162 */     if (a_sText == null)
/*      */     {
/*  164 */       return null;
/*      */     }
/*      */ 
/*  167 */     while (iStartTagPos >= 0)
/*      */     {
/*  170 */       iStartTagPos = a_sText.indexOf(a_sStartTag, iStartSearchPos);
/*      */ 
/*  172 */       if (iStartTagPos < 0)
/*      */       {
/*  175 */         sbNewText.append(a_sText.substring(iStartSearchPos, a_sText.length())); continue;
/*      */       }
/*      */ 
/*  180 */       sbNewText.append(a_sText.substring(iStartSearchPos, iStartTagPos));
/*      */ 
/*  183 */       iEndTagPos = a_sText.indexOf(a_sEndTag, iStartTagPos + a_sStartTag.length());
/*      */ 
/*  186 */       if (iEndTagPos < 0)
/*      */       {
/*  189 */         return sbNewText.toString() + a_sText.substring(iStartTagPos);
/*      */       }
/*      */ 
/*  193 */       sID = a_sText.substring(iStartTagPos + a_sStartTag.length(), iEndTagPos);
/*      */ 
/*  196 */       sJavascriptSafeID = getJavascriptLiteralString(sID);
/*      */ 
/*  199 */       sReplacement = replaceString(a_sReplacementPattern, a_sReplacementMarkerHTML, sID);
/*      */ 
/*  202 */       sReplacement = replaceString(sReplacement, a_sReplacementMarkerJavascript, sJavascriptSafeID);
/*      */ 
/*  205 */       sbNewText.append(sReplacement);
/*      */ 
/*  208 */       iStartSearchPos = iEndTagPos + a_sEndTag.length();
/*      */     }
/*      */ 
/*  214 */     return sbNewText.toString();
/*      */   }
/*      */ 
/*      */   public static boolean delimitedListOfStringsAreEqual(String a_sList1, String a_sList2, String a_sDelim)
/*      */     throws Bn2Exception
/*      */   {
/*  222 */     if ((a_sList1 == null) || (a_sList2 == null))
/*      */     {
/*  224 */       throw new Bn2Exception("StringUtil.delimitedListOfStringsAreEqual: Strings to check cannot be null");
/*      */     }
/*      */ 
/*  227 */     String[] aList1 = a_sList1.split(a_sDelim);
/*  228 */     String[] aList2 = a_sList2.split(a_sDelim);
/*      */ 
/*  231 */     if (aList1.length != aList2.length)
/*      */     {
/*  233 */       return false;
/*      */     }
/*      */ 
/*  237 */     for (String sString1 : aList1)
/*      */     {
/*  239 */       boolean bFound = false;
/*      */ 
/*  241 */       for (String sString2 : aList2)
/*      */       {
/*  243 */         if (!sString1.equals(sString2))
/*      */           continue;
/*  245 */         bFound = true;
/*  246 */         break;
/*      */       }
/*      */ 
/*  250 */       if (!bFound)
/*      */       {
/*  252 */         return false;
/*      */       }
/*      */     }
/*  255 */     return true;
/*      */   }
/*      */ 
/*      */   public static String convertStringVectorToString(Vector<String> a_vecStrings, String a_sDelim)
/*      */   {
/*  261 */     if (a_vecStrings != null)
/*      */     {
/*  263 */       String[] aArray = new String[a_vecStrings.size()];
/*  264 */       for (int i = 0; i < a_vecStrings.size(); i++)
/*      */       {
/*  266 */         aArray[i] = ((String)a_vecStrings.elementAt(i));
/*      */       }
/*  268 */       return convertStringArrayToString(aArray, a_sDelim);
/*      */     }
/*  270 */     return "";
/*      */   }
/*      */ 
/*      */   public static String convertStringArrayToString(String[] a_asStrings, String a_sDelim)
/*      */   {
/*  289 */     StringBuffer sb = new StringBuffer("");
/*  290 */     boolean bAddDelim = false;
/*      */ 
/*  292 */     for (int i = 0; (a_asStrings != null) && (i < a_asStrings.length); i++)
/*      */     {
/*  294 */       if (a_asStrings[i] == null)
/*      */         continue;
/*  296 */       if (bAddDelim)
/*      */       {
/*  298 */         sb.append(a_sDelim);
/*      */       }
/*      */       else
/*      */       {
/*  302 */         bAddDelim = true;
/*      */       }
/*      */ 
/*  305 */       sb.append(a_asStrings[i]);
/*      */     }
/*      */ 
/*  308 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String convertStringCollectionToString(Collection<String> a_strings, String a_sDelim)
/*      */   {
/*  321 */     StringBuilder sb = new StringBuilder();
/*  322 */     for (Iterator iterator = a_strings.iterator(); iterator.hasNext(); )
/*      */     {
/*  324 */       String s = (String)iterator.next();
/*  325 */       sb.append(s);
/*  326 */       if (iterator.hasNext())
/*      */       {
/*  328 */         sb.append(a_sDelim);
/*      */       }
/*      */     }
/*  331 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static boolean isValidPassword(String a_sPassword)
/*      */   {
/*  345 */     boolean valid = true;
/*      */ 
/*  348 */     if ((a_sPassword == null) || (a_sPassword.length() < 8))
/*      */     {
/*  350 */       valid = false;
/*      */     }
/*  354 */     else if (!a_sPassword.matches("(.*[0-9]+.*)"))
/*      */     {
/*  356 */       valid = false;
/*      */     }
/*  360 */     else if (!a_sPassword.matches("(.*[A-Z]+.*)"))
/*      */     {
/*  362 */       valid = false;
/*      */     }
/*      */ 
/*  365 */     return valid;
/*      */   }
/*      */ 
/*      */   public static boolean isValidEmailAddress(String a_sEmailAddress)
/*      */   {
/*  380 */     return (a_sEmailAddress != null) && (a_sEmailAddress.matches("[^@ ]+@[^@ ]+\\.[^@ ]{2}[^@ ]*"));
/*      */   }
/*      */ 
/*      */   public static boolean isValidEmailAddressList(String a_sEmailAddressList)
/*      */   {
/*  393 */     boolean isValid = true;
/*      */ 
/*  396 */     String[] sAddresses = a_sEmailAddressList.split(";");
/*  397 */     for (int x = 0; x < sAddresses.length; x++)
/*      */     {
/*  399 */       if ((isValidEmailAddress(sAddresses[x])) || (sAddresses[x].matches("#[a-zA-Z]+#")))
/*      */         continue;
/*  401 */       isValid = false;
/*      */     }
/*      */ 
/*  406 */     return isValid;
/*      */   }
/*      */ 
/*      */   public static String getJavascriptLiteralString(String a_sText)
/*      */   {
/*  424 */     String sOutput = null;
/*      */ 
/*  426 */     sOutput = replaceString(a_sText, "\\u0", "__u0");
/*  427 */     sOutput = replaceString(a_sText, "\\", "\\\\");
/*  428 */     sOutput = replaceString(a_sText, "__u0", "\\u0");
/*  429 */     sOutput = replaceString(sOutput, "'", "\\'");
/*  430 */     sOutput = replaceString(sOutput, "\"", "&quot;");
/*  431 */     sOutput = sOutput.replaceAll("\r\n", "\\\\n");
/*  432 */     sOutput = sOutput.replaceAll("\r", "\\\\n");
/*  433 */     sOutput = sOutput.replaceAll("\n", "\\\\n");
/*      */ 
/*  435 */     return sOutput;
/*      */   }
/*      */ 
/*      */   public static String escapeJS(String a_s)
/*      */   {
/*  446 */     return a_s.replace("\\", "\\\\").replace("\"", "\\\"").replace("'", "\\'");
/*      */   }
/*      */ 
/*      */   public static String replaceString(String a_sSource, String a_sToReplace, String a_sReplacement)
/*      */   {
/*  470 */     String sNewString = "";
/*  471 */     int iIndex = 0; int iOldIndex = 0;
/*  472 */     int iToReplaceLength = a_sToReplace.length();
/*      */ 
/*  474 */     if (a_sSource != null)
/*      */     {
/*  476 */       iIndex = a_sSource.indexOf(a_sToReplace);
/*      */ 
/*  478 */       while (iIndex >= 0)
/*      */       {
/*  480 */         sNewString = sNewString + a_sSource.substring(iOldIndex, iIndex) + a_sReplacement;
/*      */ 
/*  482 */         iOldIndex = iIndex + iToReplaceLength;
/*      */ 
/*  484 */         iIndex = a_sSource.indexOf(a_sToReplace, iOldIndex);
/*      */       }
/*      */ 
/*  487 */       sNewString = sNewString + a_sSource.substring(iOldIndex, a_sSource.length());
/*      */     }
/*      */ 
/*  490 */     return sNewString;
/*      */   }
/*      */ 
/*      */   public static String replaceFirst(String a_sSource, String a_sToReplace, String a_sReplacement)
/*      */   {
/*  508 */     String sNewString = "";
/*  509 */     int iIndex = 0; int iOldIndex = 0;
/*  510 */     int iToReplaceLength = a_sToReplace.length();
/*      */ 
/*  512 */     if (a_sSource != null)
/*      */     {
/*  514 */       iIndex = a_sSource.indexOf(a_sToReplace);
/*      */ 
/*  516 */       if (iIndex >= 0)
/*      */       {
/*  518 */         sNewString = sNewString + a_sSource.substring(iOldIndex, iIndex) + a_sReplacement;
/*      */ 
/*  520 */         iOldIndex = iIndex + iToReplaceLength;
/*      */ 
/*  522 */         iIndex = a_sSource.indexOf(a_sToReplace, iOldIndex);
/*      */       }
/*      */ 
/*  525 */       sNewString = sNewString + a_sSource.substring(iOldIndex, a_sSource.length());
/*      */     }
/*      */ 
/*  528 */     return sNewString;
/*      */   }
/*      */ 
/*      */   public static String[] convertToArray(String a_sText, String a_sDelim)
/*      */   {
/*  554 */     Vector vecStrings = new Vector();
/*      */ 
/*  556 */     convertToList(vecStrings, a_sText, a_sDelim);
/*      */ 
/*  559 */     String[] aStrings = new String[vecStrings.size()];
/*      */ 
/*  561 */     for (int i = 0; i < aStrings.length; i++)
/*      */     {
/*  563 */       aStrings[i] = ((String)vecStrings.elementAt(i));
/*      */     }
/*      */ 
/*  566 */     return aStrings;
/*      */   }
/*      */ 
/*      */   public static Vector convertToVector(String a_sText, String a_sDelim)
/*      */   {
/*  587 */     Vector vecStrings = new Vector();
/*      */ 
/*  589 */     convertToList(vecStrings, a_sText, a_sDelim);
/*      */ 
/*  591 */     return vecStrings;
/*      */   }
/*      */ 
/*      */   public static List<String> convertToList(String a_sText, String a_sDelim)
/*      */   {
/*  610 */     List liStrings = new ArrayList();
/*      */ 
/*  612 */     convertToList(liStrings, a_sText, a_sDelim);
/*      */ 
/*  614 */     return liStrings;
/*      */   }
/*      */ 
/*      */   private static List convertToList(List a_dest, String a_sText, String a_sDelim)
/*      */   {
/*  624 */     if (a_sText != null)
/*      */     {
/*  627 */       StringTokenizer st = new StringTokenizer(a_sText, a_sDelim);
/*      */ 
/*  629 */       while (st.hasMoreTokens())
/*      */       {
/*  631 */         a_dest.add(st.nextToken());
/*      */       }
/*      */     }
/*      */ 
/*  635 */     return a_dest;
/*      */   }
/*      */ 
/*      */   public static Vector<Long> convertToVectorOfLongs(String a_sText, String a_sDelim)
/*      */   {
/*  649 */     Vector vecLongs = new Vector();
/*      */ 
/*  651 */     addToCollectionOfLongs(vecLongs, a_sText, a_sDelim);
/*      */ 
/*  653 */     return vecLongs;
/*      */   }
/*      */ 
/*      */   public static Vector<String> getAsVectorOfStrings(Vector<?> a_vObjects)
/*      */   {
/*  663 */     if (a_vObjects == null)
/*      */     {
/*  665 */       return null;
/*      */     }
/*      */ 
/*  668 */     Vector vStrings = new Vector(a_vObjects.size());
/*  669 */     for (Iterator i$ = a_vObjects.iterator(); i$.hasNext(); ) { Object object = i$.next();
/*      */ 
/*  671 */       vStrings.add(String.valueOf(object));
/*      */     }
/*  673 */     return vStrings;
/*      */   }
/*      */ 
/*      */   public static List<Long> convertToListOfLongs(String a_sText, String a_sDelim)
/*      */   {
/*  692 */     List liLongs = new ArrayList();
/*      */ 
/*  694 */     addToCollectionOfLongs(liLongs, a_sText, a_sDelim);
/*      */ 
/*  696 */     return liLongs;
/*      */   }
/*      */ 
/*      */   public static Set<Long> convertToSetOfLongs(String a_sText, String a_sDelim)
/*      */   {
/*  710 */     Set longs = new LinkedHashSet();
/*      */ 
/*  712 */     addToCollectionOfLongs(longs, a_sText, a_sDelim);
/*      */ 
/*  714 */     return longs;
/*      */   }
/*      */ 
/*      */   private static void addToCollectionOfLongs(Collection<? super Long> a_dest, String a_sText, String a_sDelim)
/*      */   {
/*  734 */     if (StringUtils.isNotEmpty(a_sText))
/*      */     {
/*  737 */       StringTokenizer st = new StringTokenizer(a_sText, a_sDelim);
/*      */ 
/*  739 */       while (st.hasMoreTokens())
/*      */       {
/*  741 */         a_dest.add(new Long(Long.parseLong(st.nextToken())));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static long[] convertToArrayOfLongs(String a_sVal, String a_sDelim)
/*      */   {
/*  749 */     String[] aStrings = a_sVal.split(a_sDelim);
/*      */ 
/*  751 */     if (aStrings != null)
/*      */     {
/*  753 */       ArrayList<Long> longs = new ArrayList();
/*  754 */       for (String sVal : aStrings)
/*      */       {
/*      */         try
/*      */         {
/*  758 */           longs.add(Long.valueOf(Long.parseLong(sVal)));
/*      */         }
/*      */         catch (NumberFormatException e)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  766 */       long[] longArray = new long[longs.size()];
/*  767 */       int iCount = 0;
/*  768 */       for (Long longVal : longs)
/*      */       {
/*  770 */         longArray[iCount] = longVal.longValue();
/*  771 */         iCount++;
/*      */       }
/*  773 */       return longArray;
/*      */     }
/*  775 */     return null;
/*      */   }
/*      */ 
/*      */   public static Vector<Long> convertToVectorOfLongs(String[] a_asValues)
/*      */   {
/*  792 */     Vector vecLongs = new Vector();
/*      */ 
/*  794 */     addToCollectionOfLongs(vecLongs, a_asValues);
/*      */ 
/*  796 */     return vecLongs;
/*      */   }
/*      */ 
/*      */   public static Set<Long> convertToSetOfLongs(String[] a_asValues)
/*      */   {
/*  801 */     Set longs = new HashSet();
/*      */ 
/*  803 */     addToCollectionOfLongs(longs, a_asValues);
/*      */ 
/*  805 */     return longs;
/*      */   }
/*      */ 
/*      */   private static void addToCollectionOfLongs(Collection<? super Long> a_vecLongs, String[] a_asValues)
/*      */   {
/*  810 */     if (a_asValues != null)
/*      */     {
/*  812 */       for (int i = 0; i < a_asValues.length; i++)
/*      */       {
/*  814 */         a_vecLongs.add(Long.valueOf(a_asValues[i]));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String convertNumbersToString(long[] a_alNumbers, String a_sDelim)
/*      */   {
/*  835 */     StringBuffer sb = new StringBuffer("");
/*      */ 
/*  837 */     if ((a_alNumbers != null) && (a_alNumbers.length > 0))
/*      */     {
/*  839 */       sb.append(a_alNumbers[0]);
/*      */ 
/*  841 */       if (a_alNumbers.length > 1)
/*      */       {
/*  843 */         for (int i = 1; i < a_alNumbers.length; i++)
/*      */         {
/*  845 */           sb.append(a_sDelim + a_alNumbers[i]);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  850 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String convertNumbersToString(Collection<?> a_numbers, String a_sDelim)
/*      */   {
/*  869 */     StringBuilder sb = new StringBuilder();
/*      */     Iterator i$;
/*  871 */     if (a_numbers != null)
/*      */     {
/*  873 */       for (i$ = a_numbers.iterator(); i$.hasNext(); ) { Object object = i$.next();
/*      */ 
/*  875 */         if (sb.length() > 0)
/*      */         {
/*  877 */           sb.append(a_sDelim);
/*      */         }
/*  879 */         sb.append(String.valueOf(object));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  884 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String convertNumbersToString(List a_numbers, String a_sDelim, int a_iStart, int a_iEnd)
/*      */   {
/*  903 */     StringBuffer sb = new StringBuffer();
/*      */ 
/*  905 */     if ((a_numbers != null) && (a_numbers.size() > 0))
/*      */     {
/*  907 */       int iUpperIndex = a_iEnd >= 0 ? Math.min(a_numbers.size() - 1, a_iEnd) : a_numbers.size() - 1;
/*      */ 
/*  909 */       for (int i = a_iStart; i <= iUpperIndex; i++)
/*      */       {
/*  911 */         if (i > a_iStart)
/*      */         {
/*  913 */           sb.append(a_sDelim);
/*      */         }
/*  915 */         sb.append(a_numbers.get(i).toString());
/*      */       }
/*      */     }
/*  918 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String convertDataBeansToString(Vector a_vec, String a_sDelim)
/*      */   {
/*  936 */     StringBuffer sb = new StringBuffer("");
/*      */ 
/*  938 */     if ((a_vec != null) && (a_vec.size() > 0))
/*      */     {
/*  941 */       DataBean db = (DataBean)a_vec.get(0);
/*  942 */       sb.append(db.getId());
/*      */ 
/*  944 */       if (a_vec.size() > 1)
/*      */       {
/*  946 */         for (int i = 1; i < a_vec.size(); i++)
/*      */         {
/*  948 */           db = (DataBean)a_vec.get(i);
/*  949 */           sb.append(a_sDelim + db.getId());
/*      */         }
/*      */       }
/*      */     }
/*  953 */     return sb.toString();
/*      */   }
/*      */ 
/*      */   public static String getDateString(Date a_date)
/*      */   {
/*  972 */     if (a_date == null)
/*      */     {
/*  974 */       return null;
/*      */     }
/*      */ 
/*  977 */     GregorianCalendar calendar = new GregorianCalendar();
/*  978 */     calendar.setTime(a_date);
/*      */ 
/*  982 */     String sDay = String.valueOf(calendar.get(5));
/*  983 */     if (sDay.length() == 1)
/*      */     {
/*  985 */       sDay = "0" + sDay;
/*      */     }
/*      */ 
/*  989 */     int iMonth = calendar.get(2);
/*  990 */     iMonth++;
/*  991 */     String sMonth = String.valueOf(iMonth);
/*  992 */     if (sMonth.length() == 1)
/*      */     {
/*  994 */       sMonth = "0" + sMonth;
/*      */     }
/*      */ 
/*  997 */     String sYear = String.valueOf(calendar.get(1));
/*      */ 
/*  999 */     return sDay + "/" + sMonth + "/" + sYear;
/*      */   }
/*      */ 
/*      */   public static String getSQLDateString(String a_sDate)
/*      */   {
/*      */     try
/*      */     {
/* 1019 */       String sDay = a_sDate.substring(0, 2);
/* 1020 */       String sMonth = a_sDate.substring(3, 5);
/* 1021 */       String sYear = a_sDate.substring(6, 10);
/*      */ 
/* 1023 */       return sYear + "-" + sMonth + "-" + sDay;
/*      */     }
/*      */     catch (StringIndexOutOfBoundsException e)
/*      */     {
/* 1028 */       return null;
/*      */     }
/*      */     catch (NullPointerException e) {
/*      */     }
/* 1032 */     return null;
/*      */   }
/*      */ 
/*      */   public static String encodeString(String s)
/*      */   {
/* 1055 */     if ((s == null) || (s.length() == 0))
/*      */     {
/* 1057 */       return s;
/*      */     }
/*      */ 
/* 1060 */     StringBuffer b = new StringBuffer(s.length() + 2);
/*      */ 
/* 1062 */     for (int i = 0; i < s.length(); i++)
/*      */     {
/* 1064 */       char c = s.charAt(i);
/* 1065 */       if ((c < ' ') || (c > '¿'))
/*      */       {
/* 1067 */         b.append("&#");
/* 1068 */         b.append(c);
/* 1069 */         b.append(';');
/*      */       }
/*      */       else
/*      */       {
/* 1073 */         b.append(c);
/*      */       }
/*      */     }
/*      */ 
/* 1077 */     return b.toString();
/*      */   }
/*      */ 
/*      */   public static String decodeString(String s)
/*      */   {
/* 1097 */     if ((s == null) || (s.length() == 0))
/*      */     {
/* 1099 */       return s;
/*      */     }
/*      */ 
/* 1102 */     int len = s.length();
/* 1103 */     StringBuffer b = new StringBuffer(len);
/* 1104 */     int i = 0;
/* 1105 */     int pos = s.indexOf("&#0", i);
/*      */ 
/* 1107 */     if (pos < 0)
/*      */     {
/* 1109 */       return s;
/*      */     }
/*      */ 
/* 1112 */     int state = 0;
/* 1113 */     int start = 0;
/*      */ 
/* 1115 */     while (i < len)
/*      */     {
/* 1117 */       char c = s.charAt(i);
/*      */ 
/* 1119 */       switch (state)
/*      */       {
/*      */       case 0:
/* 1125 */         if (c == '&')
/*      */         {
/* 1127 */           state = 1;
/*      */         }
/*      */         else
/*      */         {
/* 1131 */           b.append(c);
/*      */         }
/* 1133 */         i++;
/* 1134 */         break;
/*      */       case 1:
/* 1139 */         if (c == '#')
/*      */         {
/* 1141 */           state = 2;
/*      */         }
/*      */         else
/*      */         {
/* 1145 */           b.append('&');
/* 1146 */           b.append(c);
/* 1147 */           state = 0;
/*      */         }
/* 1149 */         i++;
/* 1150 */         break;
/*      */       case 2:
/* 1157 */         if (c == '0')
/*      */         {
/* 1159 */           state = 3;
/* 1160 */           start = i;
/*      */         }
/*      */         else
/*      */         {
/* 1164 */           b.append('&');
/* 1165 */           b.append('#');
/* 1166 */           b.append(c);
/* 1167 */           state = 0;
/*      */         }
/* 1169 */         i++;
/* 1170 */         break;
/*      */       case 3:
/* 1176 */         if ((c < '0') || (c > '9'))
/*      */         {
/* 1178 */           b.append('&');
/* 1179 */           b.append('#');
/* 1180 */           b.append('0');
/* 1181 */           b.append(c);
/* 1182 */           state = 0;
/* 1183 */           i++;
/*      */         }
/*      */         else
/*      */         {
/* 1187 */           int val = 0;
/*      */           while (true)
/*      */           {
/* 1190 */             val = 10 * val + (c - '0');
/* 1191 */             i++;
/* 1192 */             if (i == len)
/*      */             {
/* 1194 */               b.append('&');
/* 1195 */               b.append('#');
/* 1196 */               i = start;
/*      */             }
/*      */             else {
/* 1199 */               c = s.charAt(i);
/* 1200 */               if (c != ';')
/*      */                 continue;
/* 1202 */               b.append((char)val);
/* 1203 */               i++;
                        state = 0;
                        }}
/*      */             //}
/*      */           
/*      */ 
/* 1207 */          // state = 0;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1212 */     return b.toString();
/*      */   }
/*      */ 
/*      */   public static boolean equalOrNull(String a_s1, String a_s2)
/*      */   {
/* 1232 */     if (((a_s1 == null) && (a_s2 != null)) || ((a_s1 != null) && (a_s2 == null)))
/*      */     {
/* 1235 */       return false;
/*      */     }
/*      */ 
/* 1238 */     if ((a_s1 != null) && (a_s2 != null))
/*      */     {
/* 1240 */       if (!a_s1.equals(a_s2))
/*      */       {
/* 1242 */         return false;
/*      */       }
/*      */     }
/*      */ 
/* 1246 */     return true;
/*      */   }
/*      */ 
/*      */   public static int getLastIndexOf(String[] a_aStringArray, String a_sString)
/*      */   {
/* 1266 */     int iLastIndex = 0;
/*      */ 
/* 1268 */     for (int i = 0; i < a_aStringArray.length; i++)
/*      */     {
/* 1270 */       if (!a_aStringArray[i].equals(a_sString))
/*      */         continue;
/* 1272 */       iLastIndex = i;
/*      */     }
/*      */ 
/* 1276 */     return iLastIndex;
/*      */   }
/*      */ 
/*      */   public static boolean stringIsDate(String a_sString)
/*      */   {
/* 1297 */     BrightDateFormat bdf = AssetBankSettings.getStandardDateFormat();
/*      */     try
/*      */     {
/* 1301 */       if (stringIsPopulated(a_sString))
/*      */       {
/* 1303 */         bdf.parse(a_sString);
/*      */       }
/*      */     }
/*      */     catch (ParseException pe)
/*      */     {
/* 1308 */       return false;
/*      */     }
/*      */ 
/* 1311 */     return true;
/*      */   }
/*      */ 
/*      */   public static long[] getIdsArray(String a_sIds)
/*      */     throws Bn2Exception
/*      */   {
/* 1327 */     return getIdsArray(a_sIds, ",");
/*      */   }
/*      */ 
/*      */   public static long[] getIdsArray(String a_sIds, String a_sDelimiter)
/*      */     throws Bn2Exception
/*      */   {
/* 1344 */     if (a_sIds == null)
/*      */     {
/* 1346 */       return new long[0];
/*      */     }
/*      */ 
/* 1349 */     long[] laIds = null;
/* 1350 */     Vector vecCatIds = new Vector();
/*      */ 
/* 1353 */     String sIdsNoSpaces = a_sIds.replace(" ", "");
/*      */ 
/* 1356 */     StringTokenizer st = new StringTokenizer(sIdsNoSpaces, a_sDelimiter);
/* 1357 */     String sToken = null;
/*      */     try
/*      */     {
/* 1362 */       while (st.hasMoreTokens())
/*      */       {
/* 1364 */         sToken = st.nextToken();
/* 1365 */         vecCatIds.add(new Long(Long.parseLong(sToken)));
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/* 1370 */       throw new Bn2Exception("Error in StringUtil.getIdArray: one of the IDs specified in the param was not a valid number - unable to create array of Category ID's", e);
/*      */     }
/*      */ 
/* 1374 */     laIds = new long[vecCatIds.size()];
/*      */ 
/* 1376 */     for (int i = 0; i < laIds.length; i++)
/*      */     {
/* 1378 */       laIds[i] = ((Long)vecCatIds.elementAt(i)).longValue();
/*      */     }
/*      */ 
/* 1381 */     return laIds;
/*      */   }
/*      */ 
/*      */   public static String getHTMLBodyOnly(String a_sSource)
/*      */   {
/* 1399 */     String sBody = a_sSource;
/* 1400 */     int iStart = 0;
/* 1401 */     int iEnd = 0;
/*      */ 
/* 1404 */     iStart = a_sSource.indexOf("<BODY");
/*      */ 
/* 1406 */     if (iStart < 0)
/*      */     {
/* 1408 */       iStart = a_sSource.indexOf("<body");
/*      */     }
/*      */ 
/* 1411 */     if (iStart > 0)
/*      */     {
/* 1413 */       iStart += 5;
/*      */ 
/* 1415 */       iEnd = a_sSource.indexOf("</BODY>");
/*      */ 
/* 1417 */       if (iEnd < 0)
/*      */       {
/* 1419 */         iEnd = a_sSource.indexOf("</body>");
/*      */       }
/*      */ 
/* 1422 */       if (iEnd < 0)
/*      */       {
/* 1424 */         sBody = a_sSource.substring(iStart).trim();
/*      */       }
/*      */ 
/* 1427 */       sBody = a_sSource.substring(iStart, iEnd).trim();
/*      */ 
/* 1430 */       sBody = sBody.replaceFirst("[^>]*?>", "");
/*      */ 
/* 1433 */       if (sBody.equalsIgnoreCase("<br>"))
/*      */       {
/* 1435 */         sBody = "";
/*      */       }
/*      */     }
/*      */ 
/* 1439 */     return sBody;
/*      */   }
/*      */ 
/*      */   public static String getHTMLBlankTargetsOnly(String a_sSourceHTML)
/*      */   {
/* 1453 */     String sNewHTML = null;
/*      */ 
/* 1456 */     sNewHTML = a_sSourceHTML.replaceAll("target=[\"'_a-zA-Z]*>", ">");
/* 1457 */     sNewHTML = sNewHTML.replaceAll("target=[\"'_a-zA-Z]* ", " ");
/*      */ 
/* 1460 */     sNewHTML = sNewHTML.replaceAll("<[aA] ", "<a target=\"_blank\" ");
/*      */ 
/* 1462 */     return sNewHTML;
/*      */   }
/*      */ 
/*      */   public static byte[] encryptMD5(String a_sInput)
/*      */     throws Bn2Exception
/*      */   {
/* 1470 */     MessageDigest d = null;
/* 1471 */     byte[] abOutput = null;
/*      */     try
/*      */     {
/* 1474 */       d = MessageDigest.getInstance("MD5");
/*      */ 
/* 1476 */       GlobalApplication.getInstance().getLogger().debug("StringUtil.encrypt : Using MD5 encryption.");
/*      */     }
/*      */     catch (NoSuchAlgorithmException nsae)
/*      */     {
/* 1480 */       throw new Bn2Exception("MD5 not supported", nsae);
/*      */     }
/*      */ 
/* 1483 */     d.reset();
/* 1484 */     d.update(a_sInput.getBytes());
/* 1485 */     abOutput = d.digest();
/* 1486 */     return abOutput;
/*      */   }
/*      */ 
/*      */   public static byte[] encryptSHA1(String a_sInput)
/*      */     throws Bn2Exception
/*      */   {
/* 1492 */     MessageDigest d = null;
/* 1493 */     byte[] abOutput = null;
/*      */     try
/*      */     {
/* 1496 */       d = MessageDigest.getInstance("SHA1");
/*      */ 
/* 1498 */       GlobalApplication.getInstance().getLogger().debug("StringUtil.encrypt : Using SHA1 encryption.");
/*      */     }
/*      */     catch (NoSuchAlgorithmException nsae)
/*      */     {
/* 1502 */       throw new Bn2Exception("SHA1 not supported", nsae);
/*      */     }
/*      */ 
/* 1505 */     d.reset();
/* 1506 */     d.update(a_sInput.getBytes());
/* 1507 */     abOutput = d.digest();
/* 1508 */     return abOutput;
/*      */   }
/*      */ 
/*      */   public static byte[] encrypt(String a_sInput)
/*      */   {
/* 1532 */     MessageDigest d = null;
/* 1533 */     byte[] abOutput = null;
/*      */     try
/*      */     {
/* 1537 */       d = MessageDigest.getInstance("MD5");
/*      */ 
/* 1539 */       GlobalApplication.getInstance().getLogger().debug("StringUtil.encrypt : Using MD5 encryption.");
/*      */     }
/*      */     catch (NoSuchAlgorithmException nsae)
/*      */     {
/*      */       try
/*      */       {
/* 1545 */         d = MessageDigest.getInstance("SHA-1");
/*      */ 
/* 1547 */         GlobalApplication.getInstance().getLogger().debug("StringUtil.encrypt : Using SHA-1 encryption.");
/*      */       }
/*      */       catch (NoSuchAlgorithmException nsae2)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1555 */     if (d != null)
/*      */     {
/* 1557 */       d.reset();
/* 1558 */       d.update(a_sInput.getBytes());
/* 1559 */       abOutput = d.digest();
/*      */     }
/*      */     else
/*      */     {
/* 1565 */       GlobalApplication.getInstance().getLogger().debug("StringUtil.encrypt : No standard encryption alorithm found.");
/*      */ 
/* 1567 */       char[] ct = a_sInput.toCharArray();
/*      */ 
/* 1569 */       abOutput = a_sInput.getBytes();
/*      */ 
/* 1571 */       for (int i = 0; i < ct.length; i++)
/*      */       {
/* 1573 */         abOutput[i] = (byte)((abOutput[i] + "534836598276491234623965329837659823".charAt(i % "534836598276491234623965329837659823".length())) % 256);
/*      */       }
/*      */     }
/*      */ 
/* 1577 */     return abOutput;
/*      */   }
/*      */ 
/*      */   public static final String toHex(byte[] hash)
/*      */   {
/* 1592 */     StringBuffer buf = new StringBuffer(hash.length * 2);
/*      */ 
/* 1594 */     for (int idx = 0; idx < hash.length; idx++) {
/* 1595 */       buf.append(hex[(hash[idx] >> 4 & 0xF)]).append(hex[(hash[idx] & 0xF)]);
/*      */     }
/* 1597 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public static String hexDigest(String a_sInput)
/*      */   {
/* 1609 */     byte[] hash = encrypt(a_sInput);
/*      */ 
/* 1611 */     String sHexDigest = toHex(hash);
/*      */ 
/* 1613 */     return sHexDigest;
/*      */   }
/*      */ 
/*      */   public static String hexDigestSHA1(String a_sInput)
/*      */     throws Bn2Exception
/*      */   {
/* 1619 */     byte[] hash = encryptSHA1(a_sInput);
/*      */ 
/* 1621 */     String sHexDigest = toHex(hash);
/* 1622 */     return sHexDigest;
/*      */   }
/*      */ 
/*      */   public static String hexDigestMD5(String a_sInput)
/*      */     throws Bn2Exception
/*      */   {
/* 1628 */     byte[] hash = encryptMD5(a_sInput);
/*      */ 
/* 1630 */     String sHexDigest = toHex(hash);
/* 1631 */     return sHexDigest;
/*      */   }
/*      */ 
/*      */   public static boolean stringIsPopulated(String a_sStr)
/*      */   {
/* 1646 */     return (a_sStr != null) && (a_sStr.trim().length() > 0);
/*      */   }
/*      */ 
/*      */   public static boolean stringIsInteger(String a_sStr)
/*      */   {
/*      */     try
/*      */     {
/* 1661 */       Integer.parseInt(a_sStr);
/* 1662 */       return true;
/*      */     }
/*      */     catch (NumberFormatException ne) {
/*      */     }
/* 1666 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean stringIsLong(String a_sStr)
/*      */   {
/*      */     try
/*      */     {
/* 1680 */       Long.parseLong(a_sStr);
/* 1681 */       return true;
/*      */     }
/*      */     catch (NumberFormatException ne) {
/*      */     }
/* 1685 */     return false;
/*      */   }
/*      */ 
/*      */   public static String makeQueryString(Map a_mParameters)
/*      */   {
/* 1704 */     String sQueryString = "";
/*      */ 
/* 1706 */     Iterator itParams = a_mParameters.entrySet().iterator();
/*      */ 
/* 1708 */     while (itParams.hasNext())
/*      */     {
/* 1710 */       Map.Entry param = (Map.Entry)itParams.next();
/*      */ 
/* 1712 */       sQueryString = sQueryString + param.getKey().toString() + "=" + ResponseUtils.encodeURL(((String[])(String[])param.getValue())[0]);
/*      */ 
/* 1714 */       if (itParams.hasNext())
/*      */       {
/* 1716 */         sQueryString = sQueryString + "&";
/*      */       }
/*      */     }
/*      */ 
/* 1720 */     return sQueryString;
/*      */   }
/*      */ 
/*      */   public static String formatNewlineForHTML(String a_sSource)
/*      */   {
/* 1736 */     String sNew = null;
/*      */ 
/* 1738 */     if (a_sSource != null)
/*      */     {
/* 1740 */       sNew = a_sSource.replaceAll("\r\n", "<br/>");
/* 1741 */       sNew = sNew.replaceAll("\r", "<br/>");
/* 1742 */       sNew = sNew.replaceAll("\n", "<br/>");
/*      */     }
/*      */ 
/* 1745 */     return sNew;
/*      */   }
/*      */ 
/*      */   public static String removeLeadingChars(String a_sSource, String a_sChar)
/*      */   {
/* 1763 */     if ((a_sSource == null) || (a_sChar == null))
/*      */     {
/* 1765 */       return a_sSource;
/*      */     }
/*      */ 
/* 1768 */     Matcher matcher = Pattern.compile(a_sChar + "*(.*)").matcher(a_sSource);
/*      */ 
/* 1770 */     String sResult = null;
/* 1771 */     if (matcher.matches())
/*      */     {
/* 1773 */       sResult = matcher.group(1);
/*      */     }
/*      */     else
/*      */     {
/* 1777 */       sResult = a_sSource;
/*      */     }
/*      */ 
/* 1780 */     return sResult;
/*      */   }
/*      */ 
/*      */   public static boolean arrayContains(String[] a_aStringArray, String a_sString)
/*      */   {
/* 1798 */     for (int i = 0; (a_aStringArray != null) && (i < a_aStringArray.length); i++)
/*      */     {
/* 1800 */       if ((a_aStringArray[i] != null) && (a_aStringArray[i].equals(a_sString)))
/*      */       {
/* 1802 */         return true;
/*      */       }
/*      */     }
/* 1805 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean isEmpty(String[] a_aStringArray)
/*      */   {
/* 1822 */     if (a_aStringArray != null)
/*      */     {
/* 1824 */       for (int i = 0; (a_aStringArray != null) && (i < a_aStringArray.length); i++)
/*      */       {
/* 1826 */         if (stringIsPopulated(a_aStringArray[i]))
/*      */         {
/* 1828 */           return false;
/*      */         }
/*      */       }
/*      */     }
/* 1832 */     return true;
/*      */   }
/*      */ 
/*      */   public static String getEmailAddressesFromHashMap(Map<String, String> a_hmEmails)
/*      */   {
/* 1845 */     String sEmailAddresses = "";
/*      */ 
/* 1847 */     Iterator itEmails = a_hmEmails.values().iterator();
/* 1848 */     while (itEmails.hasNext())
/*      */     {
/* 1850 */       String sEmail = (String)itEmails.next();
/*      */ 
/* 1852 */       if (stringIsPopulated(sEmailAddresses))
/*      */       {
/* 1855 */         sEmailAddresses = sEmailAddresses + ";";
/*      */       }
/*      */ 
/* 1858 */       sEmailAddresses = sEmailAddresses + sEmail;
/*      */     }
/*      */ 
/* 1861 */     return sEmailAddresses;
/*      */   }
/*      */ 
/*      */   public static String getEmptyStringIfNull(String a_sValue)
/*      */   {
/* 1866 */     if (a_sValue == null)
/*      */     {
/* 1868 */       return "";
/*      */     }
/*      */ 
/* 1871 */     return a_sValue;
/*      */   }
/*      */ 
/*      */   public static String stripHTML(String a_sHTML)
/*      */   {
/* 1891 */     String plainText = "";
/*      */ 
/* 1893 */     plainText = c_kHTMLNewLinePattern.matcher(a_sHTML).replaceAll("\n");
/* 1894 */     plainText = c_kHTMLTagPattern.matcher(plainText).replaceAll("");
/*      */ 
/* 1897 */     plainText = plainText.replace("&rsquo;", "'");
/* 1898 */     plainText = plainText.replace("&ndash;", "-");
/* 1899 */     plainText = plainText.replace("&gt;", ">");
/* 1900 */     plainText = plainText.replace("&lt;", "<");
/* 1901 */     plainText = plainText.replace("&pound;", "£");
/* 1902 */     plainText = plainText.replace("&laquo;", "<<");
/* 1903 */     plainText = plainText.replace("&raquo;", ">>");
/*      */ 
/* 1906 */     return plainText;
/*      */   }
/*      */ 
/*      */   public static String xssProcess(String a_sInput)
/*      */   {
/* 1918 */     String sRet = null;
/*      */ 
/* 1920 */     if (a_sInput != null)
/*      */     {
/* 1922 */       sRet = stripHTML(a_sInput);
/*      */     }
/*      */ 
/* 1925 */     return sRet;
/*      */   }
/*      */ 
/*      */   public static String capitaliseWord(String a_sToCaps)
/*      */   {
/* 1944 */     return String.valueOf(Character.toUpperCase(a_sToCaps.charAt(0))).concat(a_sToCaps.substring(1));
/*      */   }
/*      */ 
/*      */   public static String capitaliseSentence(String a_sToCaps)
/*      */   {
/* 1963 */     String capped = "";
/* 1964 */     StringTokenizer tok = new StringTokenizer(a_sToCaps);
/* 1965 */     while (tok.hasMoreTokens())
/*      */     {
/* 1967 */       capped = capped + capitaliseWord(tok.nextToken());
/*      */     }
/*      */ 
/* 1970 */     return capped;
/*      */   }
/*      */ 
/*      */   public static boolean matchesRegexs(String sCheck, String[] a_regexes)
/*      */   {
/* 1990 */     for (int i = 0; i < a_regexes.length; i++)
/*      */     {
/* 1992 */       if (sCheck.matches(a_regexes[i]))
/*      */       {
/* 1994 */         return true;
/*      */       }
/*      */     }
/*      */ 
/* 1998 */     return false;
/*      */   }
/*      */ 
/*      */   public static String toMixedCase(String a_s)
/*      */   {
/* 2003 */     String sValue = a_s;
/*      */ 
/* 2005 */     if ((stringIsPopulated(a_s)) && (a_s.length() > 0))
/*      */     {
/* 2008 */       sValue = a_s.toLowerCase();
/*      */ 
/* 2010 */       String sNewValue = new String();
/*      */ 
/* 2013 */       boolean bReplaceNext = true;
/* 2014 */       for (int i = 0; i < sValue.length(); i++)
/*      */       {
/* 2017 */         String sChar = new String() + sValue.charAt(i);
/* 2018 */         if (bReplaceNext)
/*      */         {
/* 2020 */           sNewValue = sNewValue + sChar.toUpperCase();
/*      */         }
/*      */         else
/*      */         {
/* 2024 */           sNewValue = sNewValue + sChar;
/*      */         }
/*      */ 
/* 2028 */         bReplaceNext = sChar.compareToIgnoreCase(" ") == 0;
/*      */       }
/*      */ 
/* 2031 */       sValue = sNewValue;
/*      */     }
/* 2033 */     return sValue;
/*      */   }
/*      */ 
/*      */   public static int compareDotNotationNumbers(String a_sNumberA, String a_sNumberB)
/*      */   {
/* 2056 */     String[] numberA = a_sNumberA.split("\\Q.\\E");
/* 2057 */     String[] numberB = a_sNumberB.split("\\Q.\\E");
/*      */ 
/* 2060 */     for (int i = 0; (i < numberA.length) || (i < numberB.length); i++)
/*      */     {
/* 2063 */       int num1 = 0;
/* 2064 */       int num2 = 0;
/* 2065 */       if (i < numberA.length)
/*      */       {
/*      */         try
/*      */         {
/* 2069 */           num1 = Integer.parseInt(numberA[i]);
/*      */         }
/*      */         catch (NumberFormatException e)
/*      */         {
/* 2073 */           num1 = 0;
/*      */         }
/*      */       }
/* 2076 */       if (i < numberB.length)
/*      */       {
/*      */         try
/*      */         {
/* 2080 */           num2 = Integer.parseInt(numberB[i]);
/*      */         }
/*      */         catch (NumberFormatException e)
/*      */         {
/* 2084 */           num2 = 0;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2091 */       if (num1 < num2)
/*      */       {
/* 2093 */         return -1;
/*      */       }
/* 2095 */       if (num1 > num2)
/*      */       {
/* 2097 */         return 1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2102 */     return 0;
/*      */   }
/*      */ 
/*      */   public static final void appendDelimitedValue(StringBuffer a_sbTarget, Object a_value, char a_cDelimiter)
/*      */   {
/* 2113 */     if (a_sbTarget.length() > 0)
/*      */     {
/* 2115 */       a_sbTarget.append(a_cDelimiter);
/*      */     }
/* 2117 */     a_sbTarget.append(a_value);
/*      */   }
/*      */ 
/*      */   public static final void appendDelimitedValue(StringBuffer a_sbTarget, long a_lValue, char a_cDelimiter)
/*      */   {
/* 2128 */     if (a_sbTarget.length() > 0)
/*      */     {
/* 2130 */       a_sbTarget.append(a_cDelimiter);
/*      */     }
/* 2132 */     a_sbTarget.append(a_lValue);
/*      */   }
/*      */ 
/*      */   public static String appendToString(String a_string1, String a_string2, char a_cDelimiter)
/*      */   {
/* 2145 */     String sRet = null;
/*      */ 
/* 2147 */     if (stringIsPopulated(a_string1))
/*      */     {
/* 2149 */       sRet = a_string1;
/*      */ 
/* 2151 */       if (stringIsPopulated(a_string2))
/*      */       {
/* 2153 */         sRet = sRet + a_cDelimiter + a_string2;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 2158 */       sRet = a_string2;
/*      */     }
/*      */ 
/* 2161 */     return sRet;
/*      */   }
/*      */ 
/*      */   public static NameValueBean[] getNameValuePairs(String a_sValue, String a_sPairDelimiter, String a_sNameValueDelimiter, boolean a_bSkipIncompletePairs)
/*      */   {
/* 2172 */     NameValueBean[] result = null;
/*      */ 
/* 2174 */     if (a_sValue != null)
/*      */     {
/* 2176 */       String[] pairs = a_sValue.split(a_sPairDelimiter);
/*      */ 
/* 2178 */       ArrayList resultList = new ArrayList(pairs.length);
/*      */ 
/* 2180 */       int i = 0;
/*      */ 
/* 2182 */       while (i < pairs.length)
/*      */       {
/* 2184 */         String[] pair = pairs[(i++)].split(a_sNameValueDelimiter);
/*      */ 
/* 2186 */         NameValueBean nameValue = new NameValueBean();
/* 2187 */         if ((pair.length > 1) && (StringUtils.isNotEmpty(pair[1])))
/*      */         {
/* 2189 */           nameValue.setValue(pair[0].trim());
/* 2190 */           nameValue.setName(pair[1].trim());
/* 2191 */           resultList.add(nameValue);
/*      */         }
/* 2193 */         else if (!a_bSkipIncompletePairs)
/*      */         {
/* 2195 */           nameValue.setValue(pair[0].trim());
/* 2196 */           resultList.add(nameValue);
/*      */         }
/*      */       }
/*      */ 
/* 2200 */       result = (NameValueBean[])(NameValueBean[])resultList.toArray(new NameValueBean[resultList.size()]);
/*      */     }
/*      */ 
/* 2203 */     return result;
/*      */   }
/*      */ 
/*      */   public static String[] splitOnNewlines(String a_sToSplit)
/*      */   {
/* 2208 */     if ((a_sToSplit == null) || (a_sToSplit.length() == 0))
/*      */     {
/* 2210 */       return null;
/*      */     }
/*      */ 
/* 2213 */     return a_sToSplit.split("[\\r\\n]+");
/*      */   }
/*      */ 
/*      */   public static boolean isInList(String a_sList, String a_sValue)
/*      */   {
/* 2225 */     if ((a_sList == null) || (a_sValue == null))
/*      */     {
/* 2227 */       return false;
/*      */     }
/* 2229 */     return convertToList(a_sList.toLowerCase(), ",").contains(a_sValue.toLowerCase());
/*      */   }
/*      */ 
/*      */   public static String pack(String[] a_stringsToPack)
/*      */   {
/* 2243 */     return pack(',', '\\', a_stringsToPack);
/*      */   }
/*      */ 
/*      */   public static String pack(char a_cDelim, char a_cEscape, String[] a_stringsToPack)
/*      */   {
/* 2258 */     if (a_cDelim == a_cEscape)
/*      */     {
/* 2260 */       throw new IllegalArgumentException("Delimiter character must not be the same as the escape character");
/*      */     }
/*      */ 
/* 2263 */     String[] escapedStrings = new String[a_stringsToPack.length];
/* 2264 */     String sDelim = String.valueOf(a_cDelim);
/* 2265 */     String sEscape = String.valueOf(a_cEscape);
/* 2266 */     String sEscapedDelim = sEscape + sDelim;
/* 2267 */     String sEscapedEscape = sEscape + sEscape;
/*      */ 
/* 2269 */     for (int i = 0; i < a_stringsToPack.length; i++)
/*      */     {
/* 2271 */       String s = a_stringsToPack[i];
/* 2272 */       escapedStrings[i] = s.replace(sEscape, sEscapedEscape).replace(sDelim, sEscapedDelim);
/*      */     }
/*      */ 
/* 2276 */     StringBuilder sbPacked = new StringBuilder();
/* 2277 */     for (int i = 0; i < escapedStrings.length; i++)
/*      */     {
/* 2279 */       String escapedString = escapedStrings[i];
/* 2280 */       sbPacked.append(escapedString);
/* 2281 */       sbPacked.append(a_cDelim);
/*      */     }
/*      */ 
/* 2284 */     if (sbPacked.length() > 0)
/*      */     {
/* 2286 */       sbPacked.setLength(sbPacked.length() - 1);
/*      */     }
/*      */ 
/* 2289 */     return sbPacked.toString();
/*      */   }
/*      */ 
/*      */   public static String[] unpack(String a_sPacked)
/*      */   {
/* 2305 */     return unpack(',', '\\', a_sPacked);
/*      */   }
/*      */ 
/*      */   public static String[] unpack(char a_cDelim, char a_cEscape, String a_sPacked)
/*      */   {
/* 2322 */     List unpacked = new ArrayList();
/*      */ 
/* 2324 */     int iLength = a_sPacked.length();
/* 2325 */     StringBuilder sbCurrent = new StringBuilder();
/* 2326 */     for (int i = 0; i < iLength; i++)
/*      */     {
/* 2328 */       char c = a_sPacked.charAt(i);
/* 2329 */       if ((c == a_cEscape) && (i < iLength - 1))
/*      */       {
/* 2331 */         char cNext = a_sPacked.charAt(i + 1);
/* 2332 */         if ((cNext == a_cDelim) || (cNext == a_cEscape))
/*      */         {
/* 2336 */           sbCurrent.append(cNext);
/* 2337 */           i++;
/* 2338 */           continue;
/*      */         }
/*      */       }
/*      */ 
/* 2342 */       if (c == a_cDelim)
/*      */       {
/* 2345 */         unpacked.add(sbCurrent.toString());
/* 2346 */         sbCurrent = new StringBuilder();
/*      */       }
/*      */       else
/*      */       {
/* 2350 */         sbCurrent.append(c);
/*      */       }
/*      */     }
/*      */ 
/* 2354 */     unpacked.add(sbCurrent.toString());
/*      */ 
/* 2356 */     return (String[])unpacked.toArray(new String[unpacked.size()]);
/*      */   }
/*      */ 
/*      */   public static String padLeadingZeros(long a_lNumber, int a_iWidth)
/*      */   {
/* 2368 */     String sCode = String.valueOf(a_lNumber);
/*      */ 
/* 2370 */     if ((stringIsPopulated(sCode)) && (a_iWidth > 0))
/*      */     {
/* 2372 */       String sPadding = "";
/* 2373 */       for (int i = sCode.length(); i < a_iWidth; i++)
/*      */       {
/* 2375 */         sPadding = sPadding + "0";
/*      */       }
/*      */ 
/* 2378 */       sCode = sPadding + sCode;
/*      */     }
/*      */ 
/* 2381 */     return sCode;
/*      */   }
/*      */ 
/*      */   public static String getAsReadableList(boolean a_bAndNotOr, String[] a_sValues)
/*      */   {
/* 2391 */     StringBuffer sbList = new StringBuffer();
/* 2392 */     List<String> values = new ArrayList();
/* 2393 */     for (int i = 0; i < a_sValues.length; i++)
/*      */     {
/* 2395 */       if (!StringUtils.isNotEmpty(a_sValues[i]))
/*      */         continue;
/* 2397 */       values.add(a_sValues[i]);
/*      */     }
/*      */ 
/* 2401 */     int i = 0;
/* 2402 */     for (String sValue : values)
/*      */     {
/* 2404 */       sbList.append(sValue);
/* 2405 */       if (i < values.size() - 2)
/*      */       {
/* 2407 */         sbList.append(", ");
/*      */       }
/* 2409 */       else if (i == values.size() - 2)
/*      */       {
/* 2411 */         sbList.append(a_bAndNotOr ? " and " : " or ");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2416 */     return sbList.toString();
/*      */   }
/*      */ 
/*      */   public static long[] getAsArrayOfLongs(String a_sInput, char a_sDelimiter)
/*      */   {
/* 2428 */     if ((a_sInput == null) || (a_sInput.length() == 0))
/*      */     {
/* 2430 */       return new long[0];
/*      */     }
/*      */ 
/* 2433 */     int iSize = 1;
/* 2434 */     for (int i = 1; i < a_sInput.length() - 1; i++)
/*      */     {
/* 2436 */       if (a_sInput.charAt(i) != a_sDelimiter)
/*      */         continue;
/* 2438 */       iSize++;
/*      */     }
/*      */ 
/* 2442 */     long[] alResults = new long[iSize];
/*      */ 
/* 2444 */     StringBuilder sbVal = new StringBuilder();
/*      */ 
/* 2446 */     int iVal = 0;
/* 2447 */     for (int i = 0; i < a_sInput.length(); i++)
/*      */     {
/* 2449 */       char c = a_sInput.charAt(i);
/* 2450 */       if (c == a_sDelimiter)
/*      */       {
/* 2452 */         if (sbVal.length() <= 0)
/*      */           continue;
/* 2454 */         alResults[(iVal++)] = Long.parseLong(sbVal.toString());
/* 2455 */         sbVal.setLength(0);
/*      */       }
/*      */       else
/*      */       {
/* 2460 */         sbVal.append(c);
/*      */       }
/*      */     }
/*      */ 
/* 2464 */     if (sbVal.length() > 0)
/*      */     {
/* 2466 */       alResults[iVal] = Long.parseLong(sbVal.toString());
/*      */     }
/*      */ 
/* 2469 */     return alResults;
/*      */   }
/*      */ 
/*      */   public static String getIdStringFromDataBeans(Collection<? extends DataBean> a_colBeans, String a_sDelim)
/*      */   {
/* 2484 */     if ((a_colBeans != null) && (a_colBeans.size() > 0))
/*      */     {
/* 2486 */       String sIds = "";
/* 2487 */       for (DataBean bean : a_colBeans)
/*      */       {
/* 2489 */         sIds = sIds + bean.getId() + a_sDelim;
/*      */       }
/* 2491 */       sIds = sIds.substring(0, sIds.length() - 1);
/* 2492 */       return sIds;
/*      */     }
/* 2494 */     return null;
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.StringUtil
 * JD-Core Version:    0.6.0
 */