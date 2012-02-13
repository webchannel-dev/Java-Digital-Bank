/*    */ package com.bright.framework.struts;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class ValidationException extends Exception
/*    */ {
/*    */   private String m_listItemId;
/*    */   private String[] m_fields;
/*    */ 
/*    */   public ValidationException(String a_listItemId)
/*    */   {
/* 39 */     this(a_listItemId, null);
/*    */   }
/*    */ 
/*    */   public ValidationException(String a_listItemId, String[] a_fields)
/*    */   {
/* 48 */     super("ValidationException: ListItem.Identifier: " + a_listItemId + ", fields = " + Arrays.toString(a_fields));
/*    */ 
/* 50 */     this.m_listItemId = a_listItemId;
/* 51 */     this.m_fields = a_fields;
/*    */   }
/*    */ 
/*    */   String getListItemId()
/*    */   {
/* 56 */     return this.m_listItemId;
/*    */   }
/*    */ 
/*    */   String[] getFields()
/*    */   {
/* 61 */     return this.m_fields;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.struts.ValidationException
 * JD-Core Version:    0.6.0
 */