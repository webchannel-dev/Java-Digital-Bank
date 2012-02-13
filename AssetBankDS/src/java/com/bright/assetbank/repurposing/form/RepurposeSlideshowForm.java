/*     */ package com.bright.assetbank.repurposing.form;
/*     */ 
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.repurposing.bean.RepurposedSlideshow;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class RepurposeSlideshowForm extends DownloadForm
/*     */ {
/*     */   private static final long serialVersionUID = 8391701225689985060L;
/*     */   private long m_lId;
/*  36 */   private RepurposedSlideshow m_slideshow = null;
/*  37 */   private String m_sType = null;
/*  38 */   private int m_iPage = -1;
/*  39 */   private int m_sPageSize = -1;
/*  40 */   private String m_sCaptionIds = null;
/*  41 */   private int m_iDisplayTime = -1;
/*  42 */   private boolean m_bLabels = false;
/*  43 */   private int m_iSlideshowWidth = AssetBankSettings.getDefaultRepurposedSlideshowWidth();
/*  44 */   private int m_iSlideshowHeight = AssetBankSettings.getDefaultRepurposedSlideshowHeight();
/*  45 */   private boolean m_bConvertImages = false;
/*  46 */   private String m_sDimensions = null;
/*  47 */   private boolean m_bMaintainAspectRatio = true;
/*  48 */   private String m_sDescription = "";
/*  49 */   private int m_iDisplayType = 1;
/*  50 */   private boolean m_bShowInListOnHomepage = false;
/*  51 */   private boolean m_bDefaultOnHomepage = false;
/*  52 */   private ArrayList<Attribute> m_textAttributes = null;
/*  53 */   private long m_lCaptionAttributeId = 0L;
/*  54 */   private long m_lCreditAttributeId = 0L;
/*  55 */   private Vector<Attribute> m_attributes = null;
/*     */ 
/*     */   public long getId()
/*     */   {
/*  59 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public void setId(long a_lId) {
/*  63 */     this.m_lId = a_lId;
/*     */   }
/*     */ 
/*     */   public RepurposedSlideshow getSlideshow()
/*     */   {
/*  68 */     return this.m_slideshow;
/*     */   }
/*     */ 
/*     */   public void setSlideshow(RepurposedSlideshow a_slideshow) {
/*  72 */     this.m_slideshow = a_slideshow;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  77 */     return this.m_sType;
/*     */   }
/*     */ 
/*     */   public void setType(String a_sType)
/*     */   {
/*  82 */     this.m_sType = a_sType;
/*     */   }
/*     */ 
/*     */   public int getPage()
/*     */   {
/*  87 */     return this.m_iPage;
/*     */   }
/*     */ 
/*     */   public void setPage(int a_iPage)
/*     */   {
/*  92 */     this.m_iPage = a_iPage;
/*     */   }
/*     */ 
/*     */   public int getPageSize()
/*     */   {
/*  97 */     return this.m_sPageSize;
/*     */   }
/*     */ 
/*     */   public void setPageSize(int a_sPageSize)
/*     */   {
/* 102 */     this.m_sPageSize = a_sPageSize;
/*     */   }
/*     */ 
/*     */   public String getCaptionIds()
/*     */   {
/* 107 */     return this.m_sCaptionIds;
/*     */   }
/*     */ 
/*     */   public void setCaptionIds(String a_sCaptionIds)
/*     */   {
/* 112 */     this.m_sCaptionIds = a_sCaptionIds;
/*     */   }
/*     */ 
/*     */   public int getDisplayTime()
/*     */   {
/* 117 */     return this.m_iDisplayTime;
/*     */   }
/*     */ 
/*     */   public void setDisplayTime(int a_iDisplayTime)
/*     */   {
/* 122 */     this.m_iDisplayTime = a_iDisplayTime;
/*     */   }
/*     */ 
/*     */   public boolean getLabels()
/*     */   {
/* 127 */     return this.m_bLabels;
/*     */   }
/*     */ 
/*     */   public void setLabels(boolean a_bLabels)
/*     */   {
/* 132 */     this.m_bLabels = a_bLabels;
/*     */   }
/*     */ 
/*     */   public int getSlideshowWidth()
/*     */   {
/* 137 */     return this.m_iSlideshowWidth;
/*     */   }
/*     */ 
/*     */   public void setSlideshowWidth(int a_iSlideshowWidth)
/*     */   {
/* 142 */     this.m_iSlideshowWidth = a_iSlideshowWidth;
/*     */   }
/*     */ 
/*     */   public int getSlideshowHeight()
/*     */   {
/* 147 */     return this.m_iSlideshowHeight;
/*     */   }
/*     */ 
/*     */   public void setSlideshowHeight(int a_iSlideshowHeight)
/*     */   {
/* 152 */     this.m_iSlideshowHeight = a_iSlideshowHeight;
/*     */   }
/*     */ 
/*     */   public boolean getConvertImages()
/*     */   {
/* 157 */     return this.m_bConvertImages;
/*     */   }
/*     */ 
/*     */   public void setConvertImages(boolean a_bConvertImages)
/*     */   {
/* 162 */     this.m_bConvertImages = a_bConvertImages;
/*     */   }
/*     */ 
/*     */   public void setDimensions(String a_sDimensions)
/*     */   {
/* 167 */     this.m_sDimensions = a_sDimensions;
/*     */   }
/*     */ 
/*     */   public String getDimensions()
/*     */   {
/* 172 */     return this.m_sDimensions;
/*     */   }
/*     */ 
/*     */   public void setMaintainAspectRatio(boolean a_bMaintainAspectRatio)
/*     */   {
/* 177 */     this.m_bMaintainAspectRatio = a_bMaintainAspectRatio;
/*     */   }
/*     */ 
/*     */   public boolean getMaintainAspectRatio()
/*     */   {
/* 182 */     return this.m_bMaintainAspectRatio;
/*     */   }
/*     */ 
/*     */   public void setDescription(String a_sDescription)
/*     */   {
/* 187 */     this.m_sDescription = a_sDescription;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 192 */     return this.m_sDescription;
/*     */   }
/*     */ 
/*     */   public int getDisplayType()
/*     */   {
/* 197 */     return this.m_iDisplayType;
/*     */   }
/*     */ 
/*     */   public void setDisplayType(int a_iDisplayType) {
/* 201 */     this.m_iDisplayType = a_iDisplayType;
/*     */   }
/*     */ 
/*     */   public void setShowInListOnHomepage(boolean a_bShowInListOnHomepage)
/*     */   {
/* 206 */     this.m_bShowInListOnHomepage = a_bShowInListOnHomepage;
/*     */   }
/*     */ 
/*     */   public boolean getShowInListOnHomepage() {
/* 210 */     return this.m_bShowInListOnHomepage;
/*     */   }
/*     */ 
/*     */   public void setDefaultOnHomepage(boolean a_bDefaultOnHomepage)
/*     */   {
/* 215 */     this.m_bDefaultOnHomepage = a_bDefaultOnHomepage;
/*     */   }
/*     */ 
/*     */   public boolean getDefaultOnHomepage() {
/* 219 */     return this.m_bDefaultOnHomepage;
/*     */   }
/*     */ 
/*     */   public ArrayList<Attribute> getTextAttributes()
/*     */   {
/* 224 */     return this.m_textAttributes;
/*     */   }
/*     */ 
/*     */   public void setTextAttributes(ArrayList<Attribute> a_textAttributes) {
/* 228 */     this.m_textAttributes = a_textAttributes;
/*     */   }
/*     */ 
/*     */   public long getCaptionAttributeId()
/*     */   {
/* 233 */     return this.m_lCaptionAttributeId;
/*     */   }
/*     */ 
/*     */   public void setCaptionAttributeId(long a_lCaptionAttributeId) {
/* 237 */     this.m_lCaptionAttributeId = a_lCaptionAttributeId;
/*     */   }
/*     */ 
/*     */   public long getCreditAttributeId()
/*     */   {
/* 242 */     return this.m_lCreditAttributeId;
/*     */   }
/*     */ 
/*     */   public void setCreditAttributeId(long a_lCreditAttributeId) {
/* 246 */     this.m_lCreditAttributeId = a_lCreditAttributeId;
/*     */   }
/*     */ 
/*     */   public Vector<Attribute> getAttributes()
/*     */   {
/* 251 */     return this.m_attributes;
/*     */   }
/*     */ 
/*     */   public void setAttributes(Vector<Attribute> a_attributes) {
/* 255 */     this.m_attributes = a_attributes;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.form.RepurposeSlideshowForm
 * JD-Core Version:    0.6.0
 */