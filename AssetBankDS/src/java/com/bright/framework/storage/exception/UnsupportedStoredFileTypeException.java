/*    */ package com.bright.framework.storage.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.framework.storage.constant.StoredFileType;
/*    */ 
/*    */ public class UnsupportedStoredFileTypeException extends Bn2Exception
/*    */ {
/*    */   public UnsupportedStoredFileTypeException(StoredFileType a_type)
/*    */   {
/* 30 */     super("The file type " + a_type.name() + " is not supported.");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.exception.UnsupportedStoredFileTypeException
 * JD-Core Version:    0.6.0
 */