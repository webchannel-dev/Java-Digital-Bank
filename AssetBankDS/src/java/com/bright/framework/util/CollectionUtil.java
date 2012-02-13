/*     */ package com.bright.framework.util;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.collections.Predicate;
/*     */ 
/*     */ public class CollectionUtil
/*     */ {
/*     */   public static String join(Collection<String> a_collection, String a_sDelimiter)
/*     */   {
/*  56 */     StringBuffer buffer = new StringBuffer();
/*  57 */     Iterator it = a_collection.iterator();
/*  58 */     while (it.hasNext())
/*     */     {
/*  60 */       buffer.append((String)it.next());
/*  61 */       if (!it.hasNext())
/*     */         continue;
/*  63 */       buffer.append(a_sDelimiter);
/*     */     }
/*     */ 
/*  66 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public static Object getFirstMatch(Collection<Object> a_list, Predicate a_predicate)
/*     */   {
/*  83 */     if ((a_list != null) && (a_predicate != null))
/*     */     {
/*  85 */       Iterator it = a_list.iterator();
/*  86 */       while (it.hasNext())
/*     */       {
/*  88 */         Object obj = it.next();
/*  89 */         if (a_predicate.evaluate(obj))
/*     */         {
/*  91 */           return obj;
/*     */         }
/*     */       }
/*     */     }
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   public static long[] getLongArray(Collection<Long> a_collection)
/*     */   {
/* 109 */     long[] alResult = new long[0];
/*     */ 
/* 111 */     if (a_collection != null)
/*     */     {
/* 113 */       alResult = new long[a_collection.size()];
/* 114 */       int i = 0;
/*     */ 
/* 116 */       if (a_collection != null)
/*     */       {
/* 118 */         Iterator it = a_collection.iterator();
/* 119 */         while (it.hasNext())
/*     */         {
/* 121 */           alResult[(i++)] = ((Long)it.next()).longValue();
/*     */         }
/*     */       }
/*     */     }
/* 125 */     return alResult;
/*     */   }
/*     */ 
/*     */   public static Vector intersection(Vector v1, Vector v2)
/*     */   {
/* 141 */     Vector vResult = new Vector();
/* 142 */     Map mapa = CollectionUtils.getCardinalityMap(v1);
/* 143 */     Map mapb = CollectionUtils.getCardinalityMap(v2);
/* 144 */     Set elts = new HashSet(v1);
/* 145 */     elts.addAll(v2);
/* 146 */     for (Iterator it = elts.iterator(); it.hasNext(); )
/*     */     {
/* 148 */       Object obj = it.next();
/* 149 */       int i = 0;
/* 150 */       for (int m = Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++)
/*     */       {
/* 152 */         vResult.add(obj);
/*     */       }
/*     */     }
/*     */ 
/* 156 */     return vResult;
/*     */   }
/*     */ 
/*     */   public static Vector getSubSection(Vector a_vecInput, int a_iFromIndex, int a_iToIndex)
/*     */   {
/* 169 */     Vector vecReturn = new Vector();
/* 170 */     for (int i = 0; i < a_iToIndex; i++)
/*     */     {
/* 172 */       if (i >= a_vecInput.size())
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/* 177 */       if (i < a_iFromIndex)
/*     */         continue;
/* 179 */       vecReturn.add(a_vecInput.elementAt(i));
/*     */     }
/*     */ 
/* 182 */     return vecReturn;
/*     */   }
/*     */ 
/*     */   private static final int getFreq(Object obj, Map freqMap)
/*     */   {
/* 188 */     if (freqMap.containsKey(obj))
/*     */     {
/* 190 */       return ((Integer)freqMap.get(obj)).intValue();
/*     */     }
/* 192 */     return 0;
/*     */   }
/*     */ 
/*     */   public static final DataBean getDataBeanWithId(Collection a_beans, final long a_lId)
/*     */   {
/* 203 */     return (DataBean)getFirstMatch(a_beans, new Predicate()
/*     */     {
/*     */       public boolean evaluate(Object obj)
/*     */       {
/* 207 */         if ((obj instanceof DataBean))
/*     */         {
/* 209 */           return ((DataBean)obj).getId() == a_lId;
/*     */         }
/* 211 */         return false;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static <T> T[] convertToSingleArray(T[][] a_arrayOfArrays, Class<T[]> a_destType)
/*     */   {
/* 230 */     int iTotalLength = 0;
/* 231 */     for (Object[] srcArray : a_arrayOfArrays)
/*     */     {
/* 233 */       if (srcArray == null)
/*     */         continue;
/* 235 */       iTotalLength += srcArray.length;
/*     */     }
/*     */ 
/* 242 */     Object[] dest = (Object[])(Object[])Array.newInstance(a_destType.getComponentType(), iTotalLength);
/*     */ 
/* 245 */     int iDestPointer = 0;
/* 246 */     for (Object[] srcArray : a_arrayOfArrays)
/*     */     {
/* 248 */       if (srcArray == null)
/*     */         continue;
/* 250 */       System.arraycopy(srcArray, 0, dest, iDestPointer, srcArray.length);
/* 251 */       iDestPointer += srcArray.length;
/*     */     }
/*     */ 
/* 255 */     return (T[])dest;
/*     */   }
/*     */ 
/*     */   public static <T> Collection<T> unmodifiableCollectionOrNull(Collection<? extends T> a_collection)
/*     */   {
/* 264 */     return a_collection == null ? null : Collections.unmodifiableCollection(a_collection);
/*     */   }
/*     */ 
/*     */   public static <T> List<T> unmodifiableListOrNull(List<? extends T> a_list)
/*     */   {
/* 275 */     return a_list == null ? null : Collections.unmodifiableList(a_list);
/*     */   }
/*     */ 
/*     */   public static <T> Set<T> unmodifiableSetOrNull(Set<? extends T> a_set)
/*     */   {
/* 286 */     return a_set == null ? null : Collections.unmodifiableSet(a_set);
/*     */   }
/*     */ 
/*     */   public static <T> Set<T> asSet(T[] a_array)
/*     */   {
/* 298 */     if (a_array == null)
/*     */     {
/* 300 */       return null;
/*     */     }
/*     */ 
/* 304 */     List list = Arrays.asList(a_array);
/* 305 */     return new HashSet(list);
/*     */   }
/*     */ 
/*     */   public static <T> Set<T> asUnmodifiableSet(T[] a_array)
/*     */   {
/* 318 */     if (a_array == null)
/*     */     {
/* 320 */       return null;
/*     */     }
/*     */ 
/* 324 */     List list = Arrays.asList(a_array);
/* 325 */     Set set = new HashSet(list);
/* 326 */     return Collections.unmodifiableSet(set);
/*     */   }
/*     */ 
/*     */   public static <T> Set<T> asSet(Collection<T> a_col)
/*     */   {
/* 337 */     if (a_col == null)
/*     */     {
/* 339 */       return null;
/*     */     }
/*     */ 
/* 343 */     return new HashSet(a_col);
/*     */   }
/*     */ 
/*     */   public static boolean isSubSetOf(Collection a_set, Collection a_subset)
/*     */   {
/*     */     Iterator i$;
/* 358 */     if (a_subset != null)
/*     */     {
/* 360 */       for (i$ = a_subset.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/*     */ 
/* 362 */         if ((a_set == null) || (!a_set.contains(obj)))
/*     */         {
/* 364 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 368 */     return true;
/*     */   }
/*     */ 
/*     */   public static <T> Vector<T> asVector(T[] args)
/*     */   {
/* 379 */     if (args == null)
/*     */     {
/* 381 */       return null;
/*     */     }
/*     */ 
/* 384 */     Vector vResult = new Vector(args.length);
/*     */ 
/* 386 */     for (Object t : args)
/*     */     {
/* 388 */       vResult.add(t);
/*     */     }
/*     */ 
/* 391 */     return vResult;
/*     */   }
/*     */ 
/*     */   public static <T extends DataBean> Vector<T> deepCloneVectorOfDataBeans(Vector<T> source)
/*     */   {
/* 402 */     if (source == null)
/*     */     {
/* 404 */       return null;
/*     */     }
/*     */ 
/* 407 */     Vector clone = new Vector(source.size());
/*     */ 
/* 409 */     for (DataBean t : source)
/*     */     {
/* 411 */       clone.add((DataBean)t.clone());
/*     */     }
/*     */ 
/* 414 */     return clone;
/*     */   }
/*     */ 
/*     */   public static boolean longArrayContains(long[] a_longArray, long a_lValue)
/*     */   {
/* 419 */     for (long lValue : a_longArray)
/*     */     {
/* 421 */       if (lValue == a_lValue)
/*     */       {
/* 423 */         return true;
/*     */       }
/*     */     }
/* 426 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean stringArrayContains(String[] a_stringArray, String a_sValue)
/*     */   {
/* 431 */     for (String sValue : a_stringArray)
/*     */     {
/* 433 */       if (sValue.equals(a_sValue))
/*     */       {
/* 435 */         return true;
/*     */       }
/*     */     }
/* 438 */     return false;
/*     */   }
/*     */ 
/*     */   public static <T> Set<String> toStringSet(Collection<T> a_values)
/*     */   {
/* 443 */     Set values = new HashSet();
/*     */ 
/* 445 */     for (Iterator i$ = a_values.iterator(); i$.hasNext(); ) { Object value = i$.next();
/*     */ 
/* 447 */       values.add(value.toString());
/*     */     }
/*     */ 
/* 450 */     return values;
/*     */   }
/*     */ 
/*     */   public static String[] removeFromStringArray(String[] a_aArray, String a_sString)
/*     */   {
/* 456 */     String[] aReturnArray = a_aArray;
/*     */     int iCount;
/* 457 */     if (a_aArray != null)
/*     */     {
/* 459 */       ArrayList<String> alStrings = new ArrayList();
/* 460 */       for (String sTemp : a_aArray)
/*     */       {
/* 462 */         if (sTemp.equals(a_sString))
/*     */           continue;
/* 464 */         alStrings.add(sTemp);
/*     */       }
/*     */ 
/* 467 */       aReturnArray = new String[alStrings.size()];
/* 468 */       iCount = 0;
/* 469 */       for (String sTemp : alStrings)
/*     */       {
/* 471 */         aReturnArray[iCount] = sTemp;
/* 472 */         iCount++;
/*     */       }
/*     */     }
/* 475 */     return aReturnArray;
/*     */   }
/*     */ 
/*     */   public static Vector<Long> convertToVectorOfLongs(long[] a_aLongArray)
/*     */   {
/* 487 */     Vector vecReturn = null;
/* 488 */     if (a_aLongArray != null)
/*     */     {
/* 490 */       vecReturn = new Vector(a_aLongArray.length);
/* 491 */       for (long lLong : a_aLongArray)
/*     */       {
/* 493 */         vecReturn.add(Long.valueOf(lLong));
/*     */       }
/*     */     }
/*     */ 
/* 497 */     return vecReturn;
/*     */   }
/*     */ 
/*     */   public static <T> T[] asArrayRemoveNulls(Class<T> a_class, T[] values)
/*     */   {
/* 509 */     ArrayList valueList = new ArrayList(values.length);
/* 510 */     for (Object t : values)
/*     */     {
/* 512 */       if (t == null)
/*     */         continue;
/* 514 */       valueList.add(t);
/*     */     }
/*     */ 
/* 517 */     return (T[])valueList.toArray((Object[])(Object[])Array.newInstance(a_class, valueList.size()));
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.CollectionUtil
 * JD-Core Version:    0.6.0
 */