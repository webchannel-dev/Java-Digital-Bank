/*    */ package com.bright.assetbank.attribute.bean;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class DataLookupRequest
/*    */ {
/* 28 */   private long m_lAttributeId = -1L;
/* 29 */   private HttpServletRequest m_request = null;
/*    */ 
/*    */   public long getAttributeId()
/*    */   {
/* 33 */     return this.m_lAttributeId;
/*    */   }
/*    */ 
/*    */   public void setAttributeId(long attributeId)
/*    */   {
/* 38 */     this.m_lAttributeId = attributeId;
/*    */   }
/*    */ 
/*    */   public HttpServletRequest getRequest()
/*    */   {
/* 43 */     return this.m_request;
/*    */   }
/*    */ 
/*    */   public void setRequest(HttpServletRequest a_request)
/*    */   {
/* 48 */     this.m_request = a_request;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.DataLookupRequest
 * JD-Core Version:    0.6.0
 */