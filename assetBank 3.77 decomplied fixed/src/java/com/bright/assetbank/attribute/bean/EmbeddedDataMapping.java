/*     */ package com.bright.assetbank.attribute.bean;
/*     */ 
/*     */ public class EmbeddedDataMapping
/*     */ {
/*  30 */   private long m_lEmbeddedDataValueId = -1L;
/*  31 */   private long m_lAttributeId = -1L;
/*  32 */   private long m_lMappingDirectionId = -1L;
/*     */ 
/*  34 */   private Attribute m_attribute = null;
/*  35 */   private EmbeddedDataValue m_value = null;
/*  36 */   private MappingDirection m_direction = null;
/*     */ 
/*  38 */   private String m_sDelimiter = null;
/*  39 */   private int m_iSequence = -1;
/*     */ 
/*  41 */   private boolean m_bBinaryData = false;
/*     */ 
/*     */   public long getAttributeId() {
/*  44 */     return this.m_lAttributeId;
/*     */   }
/*     */ 
/*     */   public void setAttributeId(long a_lAttributeId) {
/*  48 */     this.m_lAttributeId = a_lAttributeId;
/*     */   }
/*     */ 
/*     */   public long getEmbeddedDataValueId() {
/*  52 */     return this.m_lEmbeddedDataValueId;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedDataValueId(long a_lEmbeddedDataValueId) {
/*  56 */     this.m_lEmbeddedDataValueId = a_lEmbeddedDataValueId;
/*     */   }
/*     */ 
/*     */   public long getMappingDirectionId() {
/*  60 */     return this.m_lMappingDirectionId;
/*     */   }
/*     */ 
/*     */   public void setMappingDirectionId(long a_lMappingDirectionid) {
/*  64 */     this.m_lMappingDirectionId = a_lMappingDirectionid;
/*     */   }
/*     */ 
/*     */   public Attribute getAttribute() {
/*  68 */     return this.m_attribute;
/*     */   }
/*     */ 
/*     */   public void setAttribute(Attribute m_attribute) {
/*  72 */     this.m_attribute = m_attribute;
/*     */   }
/*     */ 
/*     */   public MappingDirection getDirection() {
/*  76 */     return this.m_direction;
/*     */   }
/*     */ 
/*     */   public void setDirection(MappingDirection m_direction) {
/*  80 */     this.m_direction = m_direction;
/*     */   }
/*     */ 
/*     */   public EmbeddedDataValue getValue() {
/*  84 */     return this.m_value;
/*     */   }
/*     */ 
/*     */   public void setValue(EmbeddedDataValue m_value) {
/*  88 */     this.m_value = m_value;
/*     */   }
/*     */ 
/*     */   public void setDelimiter(String a_sDelimiter)
/*     */   {
/*  93 */     this.m_sDelimiter = a_sDelimiter;
/*     */   }
/*     */ 
/*     */   public String getDelimiter()
/*     */   {
/*  98 */     return this.m_sDelimiter;
/*     */   }
/*     */ 
/*     */   public void setSequence(int a_iSequence)
/*     */   {
/* 103 */     this.m_iSequence = a_iSequence;
/*     */   }
/*     */ 
/*     */   public int getSequence()
/*     */   {
/* 108 */     return this.m_iSequence;
/*     */   }
/*     */ 
/*     */   public void setBinaryData(boolean a_bBinaryData)
/*     */   {
/* 113 */     this.m_bBinaryData = a_bBinaryData;
/*     */   }
/*     */ 
/*     */   public boolean getBinaryData()
/*     */   {
/* 118 */     return this.m_bBinaryData;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.EmbeddedDataMapping
 * JD-Core Version:    0.6.0
 */