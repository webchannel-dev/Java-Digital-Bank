/*    */ package com.bright.framework.database.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ 
/*    */ public class RowNotFoundException extends Bn2Exception
/*    */ {
/*    */   public RowNotFoundException(String a_tableName, long a_id)
/*    */   {
/* 25 */     super("Could not find a " + a_tableName + " row with ID " + a_id);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.exception.RowNotFoundException
 * JD-Core Version:    0.6.0
 */