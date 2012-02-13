/*     */ package com.bright.framework.storage.constant;
/*     */ 
/*     */ public enum StorageDeviceType
/*     */ {
/*  19 */   SYSTEM(1L), 
/*  20 */   ASSETS(2L), 
/*  21 */   THUMBNAILS(3L), 
/*  22 */   SYSTEM_AND_THUMBNAILS(4L), 
/*  23 */   SYSTEM_AND_ASSETS_AND_THUMBNAILS(5L), 
/*  24 */   ASSETS_AND_THUMBNAILS(6L), 
/*  25 */   REPURPOSING(7L);
/*     */ 
/*     */   long m_lId;
/*     */ 
/*     */   private StorageDeviceType(long a_lId) {
/*  31 */     this.m_lId = a_lId;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/*  36 */     return this.m_lId;
/*     */   }
/*     */ 
/*     */   public boolean includes(StorageDeviceType a_typeToTest)
/*     */   {
/*  46 */     if (equals(a_typeToTest))
/*     */     {
/*  48 */       return true;
/*     */     }
/*  50 */     if (SYSTEM.equals(a_typeToTest))
/*     */     {
/*  52 */       return (SYSTEM_AND_THUMBNAILS.equals(this)) || (SYSTEM_AND_ASSETS_AND_THUMBNAILS.equals(this));
/*     */     }
/*  54 */     if (SYSTEM_AND_THUMBNAILS.equals(a_typeToTest))
/*     */     {
/*  56 */       return SYSTEM_AND_ASSETS_AND_THUMBNAILS.equals(this);
/*     */     }
/*  58 */     if (ASSETS.equals(a_typeToTest))
/*     */     {
/*  60 */       return (ASSETS_AND_THUMBNAILS.equals(this)) || (SYSTEM_AND_ASSETS_AND_THUMBNAILS.equals(this));
/*     */     }
/*  62 */     if (ASSETS_AND_THUMBNAILS.equals(a_typeToTest))
/*     */     {
/*  64 */       return SYSTEM_AND_ASSETS_AND_THUMBNAILS.equals(this);
/*     */     }
/*  66 */     if (THUMBNAILS.equals(a_typeToTest))
/*     */     {
/*  68 */       return (ASSETS_AND_THUMBNAILS.equals(this)) || (SYSTEM_AND_THUMBNAILS.equals(this)) || (SYSTEM_AND_ASSETS_AND_THUMBNAILS.equals(this));
/*     */     }
/*     */ 
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   public static StorageDeviceType getTypeFor(StoredFileType a_fileType)
/*     */   {
/*  81 */     if (StoredFileType.ASSET.equals(a_fileType))
/*     */     {
/*  83 */       return ASSETS;
/*     */     }
/*  85 */     if (StoredFileType.PREVIEW_OR_THUMBNAIL.equals(a_fileType))
/*     */     {
/*  87 */       return THUMBNAILS;
/*     */     }
/*  89 */     if (StoredFileType.REPURPOSED.equals(a_fileType))
/*     */     {
/*  91 */       return REPURPOSING;
/*     */     }
/*  93 */     return SYSTEM;
/*     */   }
/*     */ 
/*     */   public static StorageDeviceType forId(long a_lId)
/*     */   {
/* 103 */     for (StorageDeviceType type : values())
/*     */     {
/* 105 */       if (type.getId() == a_lId)
/*     */       {
/* 107 */         return type;
/*     */       }
/*     */     }
/* 110 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.constant.StorageDeviceType
 * JD-Core Version:    0.6.0
 */