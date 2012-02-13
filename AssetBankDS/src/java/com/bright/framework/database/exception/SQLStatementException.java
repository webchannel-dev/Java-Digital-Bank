/*    */ package com.bright.framework.database.exception;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class SQLStatementException extends Bn2Exception
/*    */ {
/*    */   private String m_sSQL;
/*    */   private SQLException m_sqlException;
/*    */ 
/*    */   public SQLStatementException(String a_sSQL, SQLException a_sqlException)
/*    */   {
/* 36 */     super("Error preparing or executing SQL \"" + a_sSQL + "\"", a_sqlException);
/* 37 */     this.m_sSQL = a_sSQL;
/* 38 */     this.m_sqlException = a_sqlException;
/*    */   }
/*    */ 
/*    */   public String getSQL()
/*    */   {
/* 46 */     return this.m_sSQL;
/*    */   }
/*    */ 
/*    */   public SQLException getSQLException()
/*    */   {
/* 53 */     return this.m_sqlException;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.database.exception.SQLStatementException
 * JD-Core Version:    0.6.0
 */