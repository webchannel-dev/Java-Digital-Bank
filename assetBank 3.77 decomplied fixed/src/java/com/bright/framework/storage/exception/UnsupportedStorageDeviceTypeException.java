/*    */ package com.bright.framework.storage.exception;
/*    */ 
/*    */ import com.bright.framework.storage.constant.StorageDeviceType;
/*    */ 
/*    */ public class UnsupportedStorageDeviceTypeException extends RuntimeException
/*    */ {
/*    */   public UnsupportedStorageDeviceTypeException(StorageDeviceType a_type)
/*    */   {
/* 29 */     super("The storage device type " + a_type.name() + " is not supported.");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.storage.exception.UnsupportedStorageDeviceTypeException
 * JD-Core Version:    0.6.0
 */