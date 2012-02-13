/*     */ package com.bright.framework.search.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class SerializationUtil
/*     */ {
/*     */   public static void serializeFlag(String a_sSerializationPath, Boolean a_value)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/*  53 */       File fSFile = new File(a_sSerializationPath);
/*     */ 
/*  56 */       if (fSFile.exists())
/*     */       {
/*  58 */         fSFile.delete();
/*  59 */         FileUtil.logFileDeletion(fSFile);
/*     */       }
/*     */ 
/*  63 */       fSFile.createNewFile();
/*     */ 
/*  66 */       FileOutputStream fos = new FileOutputStream(a_sSerializationPath);
/*  67 */       ObjectOutputStream oos = new ObjectOutputStream(fos);
/*  68 */       oos.writeObject(a_value);
/*  69 */       oos.close();
/*  70 */       fos.close();
/*     */     }
/*     */     catch (FileNotFoundException fnfe)
/*     */     {
/*  74 */       throw new Bn2Exception("SerializationUtil.serializeFlag", fnfe);
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*  78 */       throw new Bn2Exception("SerializationUtil.serializeFlag", ioe);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Boolean unserializeFlag(String a_sSerializationPath)
/*     */     throws Bn2Exception
/*     */   {
/*  95 */     FileInputStream fos = null;
/*  96 */     ObjectInputStream ois = null;
              Boolean localBoolean = null;
/*     */     try
/*     */     {
/* 101 */       File fSFile = new File(a_sSerializationPath);
/*     */ 
/* 104 */       if (fSFile.exists())
/*     */       {
/* 107 */         fos = new FileInputStream(a_sSerializationPath);
/* 108 */         ois = new ObjectInputStream(fos);
/* 109 */         localBoolean = (Boolean)ois.readObject(); 
                  return localBoolean; 
                    //jsr 59;
                  
/*     */       }
/* 111 */       localBoolean = null;
/*     */     }
/*     */     catch (FileNotFoundException fnfe)
/*     */     {
/*     */      // Boolean localBoolean;
/* 115 */       throw new Bn2Exception("SingleIndexSearchManager.unserialiseIndexRequiredFlag", fnfe);
/*     */     }
/*     */     catch (ClassNotFoundException cnfe)
/*     */     {
/* 119 */       throw new Bn2Exception("SingleIndexSearchManager.unserialiseIndexRequiredFlag", cnfe);
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/* 123 */       throw new Bn2Exception("SingleIndexSearchManager.unserialiseIndexRequiredFlag", ioe);
/*     */     }
/*     */     finally
/*     */     {
/* 127 */       if (ois != null)
/*     */       {
/* 129 */         IOUtils.closeQuietly(ois);
/*     */       }
/* 131 */       if (fos != null)
/*     */       {
/* 133 */         IOUtils.closeQuietly(fos);
/*     */       }
                return localBoolean; 
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.search.util.SerializationUtil
 * JD-Core Version:    0.6.0
 */