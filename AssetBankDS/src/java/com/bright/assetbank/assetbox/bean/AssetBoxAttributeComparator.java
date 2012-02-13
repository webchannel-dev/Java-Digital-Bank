/*     */ package com.bright.assetbank.assetbox.bean;
/*     */ 
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.approval.bean.AssetInList;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.framework.common.bean.BrightDate;
/*     */ import com.bright.framework.common.bean.BrightDateTime;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class AssetBoxAttributeComparator
/*     */   implements Comparator<AssetInList>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8018060130837686298L;
/*  38 */   private long m_lAttributeId = -1L;
/*  39 */   private boolean m_bSortIsDescending = false;
/*     */ 
/*     */   public AssetBoxAttributeComparator(long a_lAttributeId, boolean a_bSortIsDescending)
/*     */   {
/*  43 */     this.m_lAttributeId = a_lAttributeId;
/*  44 */     this.m_bSortIsDescending = a_bSortIsDescending;
/*     */   }
/*     */ 
/*     */   public int compare(AssetInList a_o1, AssetInList a_o2)
/*     */   {
/*  59 */     int iResult = 0;
/*     */ 
/*  61 */     if (!(a_o1 instanceof AssetInList))
/*     */     {
/*  63 */       throw new ClassCastException();
/*     */     }
/*     */ 
/*  66 */     if (!(a_o2 instanceof AssetInList))
/*     */     {
/*  68 */       throw new ClassCastException();
/*     */     }
/*     */ 
/*  71 */     AssetInList a1 = a_o1;
/*  72 */     AssetInList a2 = a_o2;
/*     */ 
/*  74 */     if (this.m_lAttributeId == -2L)
/*     */     {
/*  77 */       long lTime1 = a1.getTimeAddedToAssetBox().getTime();
/*  78 */       long lTime2 = a2.getTimeAddedToAssetBox().getTime();
/*     */ 
/*  80 */       iResult = longComparison(lTime1, lTime2);
/*     */     }
/*  82 */     else if (this.m_lAttributeId > 0L)
/*     */     {
/*  84 */       AttributeValue av1 = a1.getAsset().getAttributeValue(this.m_lAttributeId);
/*  85 */       AttributeValue av2 = a2.getAsset().getAttributeValue(this.m_lAttributeId);
/*     */ 
/*  87 */       if ((av1 != null) && (av2 != null))
/*     */       {
/*  89 */         if ((attributeIsString(av1.getAttribute())) && (attributeIsString(av2.getAttribute())))
/*     */         {
/*  91 */           iResult = stringComparison(av1, av2);
/*     */         }
/*     */ 
/*  94 */         if ((attributeIsNumber(av1.getAttribute())) && (attributeIsNumber(av2.getAttribute())))
/*     */         {
/*  96 */           iResult = numberComparison(av1, av2, a1, a2);
/*     */         }
/*     */ 
/*  99 */         if ((attributeIsDate(av1.getAttribute())) && (attributeIsDate(av2.getAttribute())))
/*     */         {
/* 101 */           long lTime1 = -1L;
/* 102 */           long lTime2 = -1L;
/*     */ 
/* 104 */           if ((av1.getAttribute().getIsDatepicker()) && (av1.getDateValue() != null) && (av1.getDateValue().getDate() != null))
/*     */           {
/* 106 */             lTime1 = av1.getDateValue().getDate().getTime();
/*     */           }
/*     */ 
/* 109 */           if ((av1.getAttribute().getIsDateTime()) && (av1.getDateTimeValue() != null) && (av1.getDateTimeValue().getDate() != null))
/*     */           {
/* 111 */             lTime1 = av1.getDateTimeValue().getDate().getTime();
/*     */           }
/*     */ 
/* 114 */           if ((av2.getAttribute().getIsDatepicker()) && (av2.getDateValue() != null) && (av2.getDateValue().getDate() != null))
/*     */           {
/* 116 */             lTime2 = av2.getDateValue().getDate().getTime();
/*     */           }
/*     */ 
/* 119 */           if ((av2.getAttribute().getIsDateTime()) && (av2.getDateTimeValue() != null) && (av2.getDateTimeValue().getDate() != null))
/*     */           {
/* 121 */             lTime2 = av2.getDateTimeValue().getDate().getTime();
/*     */           }
/* 123 */           iResult = longComparison(lTime1, lTime2);
/* 124 */           if (iResult == 0)
/*     */           {
/* 126 */             iResult = getSequenceNumberCheck(a1, a2);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 132 */         iResult = nullComparison(av1, av2);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 137 */     if (this.m_bSortIsDescending)
/*     */     {
/* 139 */       iResult = 1 - iResult;
/*     */     }
/*     */ 
/* 142 */     return iResult;
/*     */   }
/*     */ 
/*     */   private int longComparison(long a_l1, long a_l2)
/*     */   {
/* 155 */     if (a_l2 > a_l1)
/*     */     {
/* 157 */       return -1;
/*     */     }
/* 159 */     if (a_l2 < a_l1)
/*     */     {
/* 161 */       return 1;
/*     */     }
/* 163 */     return 0;
/*     */   }
/*     */ 
/*     */   private int stringComparison(AttributeValue av1, AttributeValue av2)
/*     */   {
/* 169 */     if ((av1.getValue() == null) || (av2.getValue() == null))
/*     */     {
/* 171 */       return nullComparison(av1.getValue(), av2.getValue());
/*     */     }
/*     */ 
/* 174 */     return av1.getValue().compareTo(av2.getValue());
/*     */   }
/*     */ 
/*     */   private int numberComparison(AttributeValue av1, AttributeValue av2, AssetInList a1, AssetInList a2)
/*     */   {
/* 180 */     if ((av1.getValue() == null) || (av2.getValue() == null))
/*     */     {
/* 182 */       return nullComparison(av1.getValue(), av2.getValue());
/*     */     }
/*     */ 
/* 185 */     long lValue1 = Long.parseLong(av1.getValue());
/* 186 */     long lValue2 = Long.parseLong(av2.getValue());
/*     */ 
/* 188 */     int iResult = longComparison(lValue1, lValue2);
/* 189 */     if (iResult == 0)
/*     */     {
/* 191 */       iResult = getSequenceNumberCheck(a1, a2);
/*     */     }
/* 193 */     return iResult;
/*     */   }
/*     */ 
/*     */   private int nullComparison(Object ob1, Object ob2)
/*     */   {
/* 199 */     if ((ob1 == null) && (ob2 != null))
/*     */     {
/* 201 */       return -1;
/*     */     }
/* 203 */     if ((ob1 != null) && (ob2 == null))
/*     */     {
/* 205 */       return 1;
/*     */     }
/* 207 */     return 0;
/*     */   }
/*     */ 
/*     */   private int getSequenceNumberCheck(AssetInList a1, AssetInList a2)
/*     */   {
/* 213 */     if (a1.getSequenceNumber() <= a2.getSequenceNumber())
/*     */     {
/* 215 */       return -1;
/*     */     }
/* 217 */     return 1;
/*     */   }
/*     */ 
/*     */   private boolean attributeIsString(Attribute a_attribute)
/*     */   {
/* 222 */     if (a_attribute != null)
/*     */     {
/* 224 */       if ((a_attribute.getIsTextarea()) || (a_attribute.getIsTextareaLong()) || (a_attribute.getIsTextareaShort()) || (a_attribute.getIsTextfield()) || (a_attribute.getIsTextfieldLong()) || (a_attribute.getIsTextfieldShort()) || ((StringUtil.stringIsPopulated(a_attribute.getFieldName())) && (a_attribute.getFieldName().equals("originalFilename"))))
/*     */       {
/* 229 */         return true;
/*     */       }
/*     */     }
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean attributeIsNumber(Attribute a_attribute)
/*     */   {
/* 237 */     if (a_attribute != null)
/*     */     {
/* 239 */       if ((StringUtil.stringIsPopulated(a_attribute.getFieldName())) && ((a_attribute.getFieldName().equals("assetId")) || (a_attribute.getFieldName().equals("size"))))
/*     */       {
/* 242 */         return true;
/*     */       }
/*     */     }
/* 245 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean attributeIsDate(Attribute a_attribute)
/*     */   {
/* 250 */     if (a_attribute != null)
/*     */     {
/* 252 */       if ((a_attribute.getIsDatepicker()) || (a_attribute.getIsDateTime()) || ((StringUtil.stringIsPopulated(a_attribute.getFieldName())) && ((a_attribute.getFieldName().equals("dateAdded")) || (a_attribute.getFieldName().equals("dateLastModified")) || (a_attribute.getFieldName().equals("dateLastDownloaded")))))
/*     */       {
/* 257 */         return true;
/*     */       }
/*     */     }
/* 260 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.bean.AssetBoxAttributeComparator
 * JD-Core Version:    0.6.0
 */