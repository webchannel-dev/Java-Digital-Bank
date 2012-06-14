/*    */ package com.bright.assetbank.synchronise.bean;
/*    */ 
/*    */ import com.bright.framework.file.FileFormat;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class SynchAssetBeanWriter extends AssetBeanWriter
/*    */ {
/*    */   public SynchAssetBeanWriter(BufferedWriter a_fileWriter, FileFormat a_format, Vector a_vAttributes)
/*    */   {
/* 37 */     super(a_fileWriter, a_format, a_vAttributes);
/*    */   }
/*    */ 
/*    */   protected void writePropertyNames(Vector<Method> a_vecMethods, ArrayList<String> a_extras)
/*    */     throws IOException
/*    */   {
/* 53 */     writePropertyNames(a_vecMethods, a_extras, false);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.SynchAssetBeanWriter
 * JD-Core Version:    0.6.0
 */