/*     */ package com.bright.assetbank.attribute.bean;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class DisplayAttribute
/*     */ {
/*  31 */   private Attribute m_attribute = new Attribute();
/*  32 */   private String m_sDisplayValue = null;
/*  33 */   private int m_iDisplayLength = -1;
/*  34 */   private int m_iSequenceNumber = 0;
/*  35 */   private boolean m_bShowLabel = false;
/*  36 */   private boolean m_bIsLink = false;
/*  37 */   private boolean m_bShowOnChild = false;
/*  38 */   private long m_lGroupId = -1L;
/*     */ 
/*  41 */   private HashMap<String, String> m_hmTranslatedValues = null;
/*     */ 
/*     */   public DisplayAttribute(DisplayAttribute createFrom)
/*     */   {
/*  47 */     setAttribute(createFrom.getAttribute());
/*  48 */     setDisplayLength(createFrom.getDisplayLength());
/*  49 */     setSequenceNumber(createFrom.getSequenceNumber());
/*  50 */     setDisplayValue(createFrom.getDisplayValue());
/*  51 */     setShowLabel(createFrom.getShowLabel());
/*  52 */     setIsLink(createFrom.getIsLink());
/*  53 */     setShowOnChild(createFrom.getShowOnChild());
/*  54 */     setGroupId(createFrom.getGroupId());
/*     */   }
/*     */ 
/*     */   public DisplayAttribute()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setTranslation(String a_sLanguageCode, String a_sValue)
/*     */   {
/*  64 */     if (this.m_hmTranslatedValues == null)
/*     */     {
/*  66 */       this.m_hmTranslatedValues = new HashMap();
/*     */     }
/*  68 */     this.m_hmTranslatedValues.put(a_sLanguageCode, a_sValue);
/*     */   }
/*     */ 
/*     */   public String getValue(String a_sLanguageCode)
/*     */   {
/*  78 */     if ((this.m_hmTranslatedValues != null) && (this.m_hmTranslatedValues.containsKey(a_sLanguageCode)))
/*     */     {
/*  80 */       return (String)this.m_hmTranslatedValues.get(a_sLanguageCode);
/*     */     }
/*  82 */     return this.m_sDisplayValue;
/*     */   }
/*     */ 
/*     */   public Attribute getAttribute()
/*     */   {
/*  89 */     return this.m_attribute;
/*     */   }
/*     */ 
/*     */   public void setAttribute(Attribute a_attribute)
/*     */   {
/*  95 */     this.m_attribute = a_attribute;
/*     */   }
/*     */ 
/*     */   public void setDisplayLength(int a_iDisplayLength)
/*     */   {
/* 100 */     this.m_iDisplayLength = a_iDisplayLength;
/*     */   }
/*     */ 
/*     */   public int getDisplayLength()
/*     */   {
/* 105 */     return this.m_iDisplayLength;
/*     */   }
/*     */ 
/*     */   public void setSequenceNumber(int a_iSequenceNumber)
/*     */   {
/* 110 */     this.m_iSequenceNumber = a_iSequenceNumber;
/*     */   }
/*     */ 
/*     */   public int getSequenceNumber()
/*     */   {
/* 115 */     return this.m_iSequenceNumber;
/*     */   }
/*     */ 
/*     */   public void setDisplayValue(String a_sDisplayValue)
/*     */   {
/* 120 */     this.m_sDisplayValue = a_sDisplayValue;
/*     */   }
/*     */ 
/*     */   public String getDisplayValue()
/*     */   {
/* 125 */     return this.m_sDisplayValue;
/*     */   }
/*     */ 
/*     */   public void setShowLabel(boolean a_bShowLabel)
/*     */   {
/* 130 */     this.m_bShowLabel = a_bShowLabel;
/*     */   }
/*     */ 
/*     */   public boolean getShowLabel()
/*     */   {
/* 135 */     return this.m_bShowLabel;
/*     */   }
/*     */ 
/*     */   public void setIsLink(boolean a_bIsLink)
/*     */   {
/* 140 */     this.m_bIsLink = a_bIsLink;
/*     */   }
/*     */ 
/*     */   public boolean getIsLink()
/*     */   {
/* 145 */     return this.m_bIsLink;
/*     */   }
/*     */ 
/*     */   public boolean getShowOnChild()
/*     */   {
/* 150 */     return this.m_bShowOnChild;
/*     */   }
/*     */ 
/*     */   public void setShowOnChild(boolean a_sShowOnChild)
/*     */   {
/* 155 */     this.m_bShowOnChild = a_sShowOnChild;
/*     */   }
/*     */ 
/*     */   public void setGroupId(long a_lGroupId)
/*     */   {
/* 160 */     this.m_lGroupId = a_lGroupId;
/*     */   }
/*     */ 
/*     */   public long getGroupId()
/*     */   {
/* 165 */     return this.m_lGroupId;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.DisplayAttribute
 * JD-Core Version:    0.6.0
 */