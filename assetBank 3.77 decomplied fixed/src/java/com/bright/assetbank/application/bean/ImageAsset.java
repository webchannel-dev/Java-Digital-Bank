/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bright.framework.image.bean.ImageFile;
/*     */ import org.apache.lucene.document.Document;
/*     */ import org.apache.lucene.document.Field;
/*     */ import org.apache.lucene.document.Field.Store;
/*     */ 
/*     */ public class ImageAsset extends Asset
/*     */ {
/*  44 */   protected int m_iHeight = 0;
/*  45 */   private int m_iWidth = 0;
/*  46 */   private int m_iColorSpace = 0;
/*  47 */   protected ImageFile m_largeImageFile = null;
/*  48 */   protected ImageFile m_unwatermarkedLargeImageFile = null;
/*  49 */   protected ImageFile m_featuredImageFile = null;
/*  50 */   protected ImageFile m_comparisonImageFile = null;
/*     */ 
/*     */   public ImageAsset()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ImageAsset(Asset a_asset)
/*     */   {
/*  77 */     super(a_asset);
/*     */   }
/*     */ 
/*     */   public int getOrientation()
/*     */   {
/*  94 */     int iOrientation = super.getOrientation();
/*     */ 
/*  96 */     if (this.m_iHeight > this.m_iWidth)
/*     */     {
/*  98 */       iOrientation = 2;
/*     */     }
/* 100 */     else if (this.m_iHeight < this.m_iWidth)
/*     */     {
/* 102 */       iOrientation = 1;
/*     */     }
/* 104 */     else if (this.m_iHeight == this.m_iWidth)
/*     */     {
/* 106 */       iOrientation = 3;
/*     */     }
/*     */ 
/* 109 */     return iOrientation;
/*     */   }
/*     */ 
/*     */   public Document createLuceneDocument(Object a_params)
/*     */   {
/* 121 */     CreateLuceneDocumentFromAssetParameters params = (CreateLuceneDocumentFromAssetParameters)a_params;
/* 122 */     String[] a_asSortFieldNames = params.getSortFieldNames();
/*     */ 
/* 124 */     Document doc = super.createLuceneDocument(a_params);
/*     */ 
/* 126 */     addFieldToDoc(doc, "f_width", String.valueOf(getWidth()), Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/* 127 */     addFieldToDoc(doc, "f_height", String.valueOf(getHeight()), Field.Store.YES, Field.Index.NO, a_asSortFieldNames);
/*     */ 
/* 129 */     return doc;
/*     */   }
/*     */ 
/*     */   public void populateFromLuceneDocument(Document a_document)
/*     */   {
/* 139 */     super.populateFromLuceneDocument(a_document);
/*     */     try
/*     */     {
/* 143 */       this.m_iHeight = Integer.parseInt(a_document.get("f_height"));
/* 144 */       this.m_iWidth = Integer.parseInt(a_document.get("f_width"));
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/*     */     }
/*     */     catch (NullPointerException npe)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 159 */     return this.m_iHeight;
/*     */   }
/*     */ 
/*     */   public void setHeight(int a_iHeight)
/*     */   {
/* 165 */     this.m_iHeight = a_iHeight;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 171 */     return this.m_iWidth;
/*     */   }
/*     */ 
/*     */   public void setWidth(int a_iWidth)
/*     */   {
/* 177 */     this.m_iWidth = a_iWidth;
/*     */   }
/*     */ 
/*     */   public int getColorSpace()
/*     */   {
/* 182 */     return this.m_iColorSpace;
/*     */   }
/*     */ 
/*     */   public void setColorSpace(int a_iColorSpace) {
/* 186 */     this.m_iColorSpace = a_iColorSpace;
/*     */   }
/*     */ 
/*     */   public ImageFile getLargeImageFile() {
/* 190 */     return this.m_largeImageFile;
/*     */   }
/*     */ 
/*     */   public void setLargeImageFile(ImageFile a_largeImageFile) {
/* 194 */     this.m_largeImageFile = a_largeImageFile;
/*     */   }
/*     */ 
/*     */   public ImageFile getUnwatermarkedLargeImageFile()
/*     */   {
/* 199 */     return this.m_unwatermarkedLargeImageFile;
/*     */   }
/*     */ 
/*     */   public void setUnwatermarkedLargeImageFile(ImageFile a_unwatermarkedLargeImageFile) {
/* 203 */     this.m_unwatermarkedLargeImageFile = a_unwatermarkedLargeImageFile;
/*     */   }
/*     */ 
/*     */   public ImageFile getFeaturedImageFile()
/*     */   {
/* 208 */     if (this.m_featuredImageFile == null)
/*     */     {
/* 210 */       this.m_featuredImageFile = new ImageFile();
/*     */     }
/* 212 */     return this.m_featuredImageFile;
/*     */   }
/*     */ 
/*     */   public void setFeaturedImageFile(ImageFile a_sFeaturedImageFile) {
/* 216 */     this.m_featuredImageFile = a_sFeaturedImageFile;
/*     */   }
/*     */ 
/*     */   public ImageFile getComparisonImageFile()
/*     */   {
/* 221 */     return this.m_comparisonImageFile;
/*     */   }
/*     */ 
/*     */   public void setComparisonImageFile(ImageFile a_comparisonImageFile) {
/* 225 */     this.m_comparisonImageFile = a_comparisonImageFile;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.ImageAsset
 * JD-Core Version:    0.6.0
 */