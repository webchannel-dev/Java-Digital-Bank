/*    */ package com.bright.assetbank.autocomplete.service;
/*    */ 
/*    */ import com.bright.framework.search.bean.IndexableDocument;
/*    */ import java.io.Serializable;
/*    */ import org.apache.lucene.document.Document;
/*    */ import org.apache.lucene.document.Field;
/*    */ import org.apache.lucene.document.Field.Index;
/*    */ import org.apache.lucene.document.Field.Store;
/*    */ 
/*    */ class ACIndexEntry
/*    */   implements Serializable, IndexableDocument
/*    */ {
/*    */   private AutoCompleteUpdateManager.ACField m_field;
/*    */   private String m_keyword;
/*    */   private int m_hitCount;
/*    */ 
/*    */   ACIndexEntry(AutoCompleteUpdateManager.ACField a_field, String a_keyword, int a_hitCount)
/*    */   {
/* 40 */     this.m_field = a_field;
/* 41 */     this.m_keyword = a_keyword;
/* 42 */     this.m_hitCount = a_hitCount;
/*    */   }
/*    */ 
/*    */   public String getIndexableDocId()
/*    */   {
/* 47 */     return this.m_field.getFieldId() + "-" + this.m_keyword;
/*    */   }
/*    */ 
/*    */   public Document createLuceneDocument(Object a_params)
/*    */   {
/* 52 */     Document doc = new Document();
/* 53 */     doc.add(new Field("f_fieldId", String.valueOf(this.m_field.getFieldId()), Field.Store.NO, Field.Index.NOT_ANALYZED));
/* 54 */     doc.add(new Field("f_keyword", this.m_keyword, Field.Store.YES, Field.Index.ANALYZED));
/* 55 */     doc.add(new Field("f_hitCount", String.valueOf(this.m_hitCount), Field.Store.YES, Field.Index.NOT_ANALYZED));
/* 56 */     doc.add(new Field("f_isAC", this.m_field.isAutoCompleteOnForField() ? "1" : "0", Field.Store.YES, Field.Index.NOT_ANALYZED));
/*    */ 
/* 58 */     return doc;
/*    */   }
/*    */ 
/*    */   public AutoCompleteUpdateManager.ACField getField()
/*    */   {
/* 63 */     return this.m_field;
/*    */   }
/*    */ 
/*    */   public String getKeyword()
/*    */   {
/* 68 */     return this.m_keyword;
/*    */   }
/*    */ 
/*    */   public int getHitCount()
/*    */   {
/* 73 */     return this.m_hitCount;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.autocomplete.service.ACIndexEntry
 * JD-Core Version:    0.6.0
 */