/*     */ package com.bright.assetbank.entity.util;
/*     */ 
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.framework.common.bean.RefDataItem;

          import java.util.Collection;
/*     */ import java.util.HashMap;

/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class AssetEntityUtil
/*     */ {
/*     */   private static final String k_sAllFilesToken = "*";
/*     */ 
/*     */   public static final boolean isEntitySelectionRequired(Vector<AssetEntity> a_vAllEntities, RefDataItem[] a_allAssetTypes)
/*     */   {
        String[] includedFiles;
        String[] excludedFiles;
/*  45 */     if ((a_vAllEntities != null) && (a_vAllEntities.size() > 0))
/*     */     {
/*  49 */       for (AssetEntity entity : a_vAllEntities)
/*     */       {
/*  53 */         if ((entity.getAllowableAssetTypes() == null) || (entity.getAllowableAssetTypes().size() == 0))
/*     */         {
/*  55 */           return true;
/*     */         }
/*     */       }
/*     */ 
/*  59 */       HashMap<String,HashMap> hmFormatsForAssetType = new HashMap(a_allAssetTypes.length * 2);
/*     */ 
/*  61 */       for (int i = 0; i < a_allAssetTypes.length; i++)
/*     */       {
/*  63 */         hmFormatsForAssetType.put(String.valueOf(a_allAssetTypes[i].getId()), new HashMap());
/*     */       }
/*     */ 
/*  66 */       for (AssetEntity entity : a_vAllEntities)
/*     */       {
/*  68 */         includedFiles = getFileFormatArray(entity.getIncludedFileFormats(), new String[] { "*" });
/*  69 */         excludedFiles = getFileFormatArray(entity.getExcludedFileFormats(), new String[0]);
/*     */ 
/*  71 */         for (Long longId : entity.getAllowableAssetTypes())
/*     */         {
/*  73 */           HashMap hmFormatCount = (HashMap)hmFormatsForAssetType.get(longId.toString());
/*     */ 
/*  75 */           buildFormatCount(includedFiles, hmFormatCount, 1);
/*  76 */           buildFormatCount(excludedFiles, hmFormatCount, -1);
/*     */         }
/*     */       }
/*     */       //String[] includedFiles;
/*     */       //String[] excludedFiles;
                boolean bEncounteredAll;
                
/*  81 */       for (HashMap hmFormatCount : hmFormatsForAssetType.values())
/*     */       {
/*  83 */         bEncounteredAll = false;
/*     */ 
/*  85 */         if (hmFormatCount.containsKey("*"))
/*     */         {
/*  87 */           bEncounteredAll = true;
/*     */ 
/*  90 */           if (((Integer)hmFormatCount.get("*")).intValue() > 1)
/*     */           {
/*  92 */             return true;
/*     */           }
/*     */ 
/*  95 */           hmFormatCount.remove("*");
/*     */         }
/*     */         Collection<Integer> sets = hmFormatCount.values();
/*  98 */         for (Integer count : sets )
/*     */         {
/* 102 */           if ((count.intValue() > 1) || ((bEncounteredAll) && (count.intValue() > 0)))
/*     */           {
/* 104 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */    
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   private static void buildFormatCount(String[] a_asFileFormats, HashMap<String, Integer> a_hmFormatCount, int a_iIncrement)
/*     */   {
/* 114 */     for (int i = 0; i < a_asFileFormats.length; i++)
/*     */     {
/* 116 */       if (!StringUtils.isNotEmpty(a_asFileFormats[i]))
/*     */         continue;
/* 118 */       Integer count = Integer.valueOf(0);
/*     */ 
/* 120 */       if (a_hmFormatCount.containsKey(a_asFileFormats[i]))
/*     */       {
/* 122 */         count = (Integer)a_hmFormatCount.get(a_asFileFormats[i]);
/*     */       }
/*     */ 
/* 125 */       a_hmFormatCount.put(a_asFileFormats[i], Integer.valueOf(count.intValue() + a_iIncrement));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String[] getFileFormatArray(String a_sFileFormats, String[] a_asEmptyValue)
/*     */   {
/* 132 */     if (StringUtils.isNotEmpty(a_sFileFormats))
/*     */     {
/* 134 */       return a_sFileFormats.toLowerCase().split("[ ,;]+");
/*     */     }
/* 136 */     return a_asEmptyValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.util.AssetEntityUtil
 * JD-Core Version:    0.6.0
 */