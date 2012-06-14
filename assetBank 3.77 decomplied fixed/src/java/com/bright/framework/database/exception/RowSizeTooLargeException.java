/*    */ package com.bright.framework.database.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class RowSizeTooLargeException extends Bn2Exception
/*    */ {
/*    */   public RowSizeTooLargeException(SQLException a_cause)
/*    */   {
/* 29 */     super("Row size too large", a_cause);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.exception.RowSizeTooLargeException
 * JD-Core Version:    0.6.0
 */