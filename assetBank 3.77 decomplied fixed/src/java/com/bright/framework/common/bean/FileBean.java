/*    */ package com.bright.framework.common.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ 
/*    */ public class FileBean
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8353755613237058235L;
/* 32 */   private String m_sFileName = null;
/* 33 */   private String m_sFilePath = null;
/*    */ 
/*    */   public FileBean(String a_sFileName, String a_sFilePath)
/*    */   {
/* 42 */     this.m_sFileName = a_sFileName;
/* 43 */     this.m_sFilePath = a_sFilePath;
/*    */   }
/*    */ 
/*    */   public String getFileName()
/*    */   {
/* 48 */     return this.m_sFileName;
/*    */   }
/*    */ 
/*    */   public void setFileName(String a_sFileName) {
/* 52 */     this.m_sFileName = a_sFileName;
/*    */   }
/*    */ 
/*    */   public String getFilePath() {
/* 56 */     return this.m_sFilePath;
/*    */   }
/*    */ 
/*    */   public void setFilePath(String a_sFilePath) {
/* 60 */     this.m_sFilePath = a_sFilePath;
/*    */   }
/*    */ 
/*    */   public String getFileNameWithoutExtension() {
/* 64 */     return FilenameUtils.getBaseName(this.m_sFileName);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.common.bean.FileBean
 * JD-Core Version:    0.6.0
 */