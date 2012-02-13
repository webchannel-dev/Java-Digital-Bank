/*    */ package com.bright.assetbank.entity.exception;
/*    */ 
/*    */ public class InvalidRelationshipException extends Exception
/*    */ {
/* 26 */   boolean m_bInvalidEfference = false;
/*    */ 
/* 29 */   private long m_lAfferentId = 0L;
/*    */ 
/* 32 */   private String m_sRelationshipName = null;
/*    */ 
/*    */   public InvalidRelationshipException(boolean a_bInvalidEfference)
/*    */   {
/* 36 */     this.m_bInvalidEfference = a_bInvalidEfference;
/*    */   }
/*    */ 
/*    */   public InvalidRelationshipException(boolean a_bInvalidEfference, long a_lAfferentId, String a_sRelationshipName)
/*    */   {
/* 41 */     this(a_bInvalidEfference);
/* 42 */     this.m_lAfferentId = a_lAfferentId;
/* 43 */     this.m_sRelationshipName = a_sRelationshipName;
/*    */   }
/*    */ 
/*    */   public long getAfferentId()
/*    */   {
/* 48 */     return this.m_lAfferentId;
/*    */   }
/*    */ 
/*    */   public void setAfferentId(long afferentId)
/*    */   {
/* 53 */     this.m_lAfferentId = afferentId;
/*    */   }
/*    */ 
/*    */   public String getRelationshipName()
/*    */   {
/* 58 */     return this.m_sRelationshipName;
/*    */   }
/*    */ 
/*    */   public void setRelationshipName(String relationshipName)
/*    */   {
/* 63 */     this.m_sRelationshipName = relationshipName;
/*    */   }
/*    */ 
/*    */   public boolean isInvalidEfference()
/*    */   {
/* 68 */     return this.m_bInvalidEfference;
/*    */   }
/*    */ 
/*    */   public void setInvalidEfference(boolean invalidEfference)
/*    */   {
/* 73 */     this.m_bInvalidEfference = invalidEfference;
/*    */   }
/*    */ 
/*    */   public boolean isInvalidAfference()
/*    */   {
/* 78 */     return !this.m_bInvalidEfference;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.exception.InvalidRelationshipException
 * JD-Core Version:    0.6.0
 */