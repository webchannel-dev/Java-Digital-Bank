/*    */ package com.bright.framework.simplelist.bean;
/*    */ 
/*    */ import com.bright.framework.database.bean.DataBean;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class List extends DataBean
/*    */ {
/* 32 */   private String m_sIdentifier = null;
/* 33 */   private String m_sName = null;
/* 34 */   private String m_sDescription = null;
/* 35 */   private Vector m_vecItems = null;
/* 36 */   private boolean m_bCannotAddNew = false;
/* 37 */   private boolean m_bNoHTMLMarkup = false;
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 41 */     return this.m_sDescription;
/*    */   }
/*    */ 
/*    */   public void setDescription(String a_sDescription) {
/* 45 */     this.m_sDescription = a_sDescription;
/*    */   }
/*    */ 
/*    */   public String getIdentifier() {
/* 49 */     return this.m_sIdentifier;
/*    */   }
/*    */ 
/*    */   public void setIdentifier(String a_sIdentifier) {
/* 53 */     this.m_sIdentifier = a_sIdentifier;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 57 */     return this.m_sName;
/*    */   }
/*    */ 
/*    */   public void setName(String a_sName) {
/* 61 */     this.m_sName = a_sName;
/*    */   }
/*    */ 
/*    */   public Vector getItems() {
/* 65 */     return this.m_vecItems;
/*    */   }
/*    */ 
/*    */   public void setItems(Vector a_vecItems) {
/* 69 */     this.m_vecItems = a_vecItems;
/*    */   }
/*    */ 
/*    */   public boolean getCannotAddNew() {
/* 73 */     return this.m_bCannotAddNew;
/*    */   }
/*    */ 
/*    */   public void setCannotAddNew(boolean a_bCannotAddNew) {
/* 77 */     this.m_bCannotAddNew = a_bCannotAddNew;
/*    */   }
/*    */ 
/*    */   public void setNoHTMLMarkup(boolean a_bNoHTMLMarkup)
/*    */   {
/* 82 */     this.m_bNoHTMLMarkup = a_bNoHTMLMarkup;
/*    */   }
/*    */ 
/*    */   public boolean getNoHTMLMarkup()
/*    */   {
/* 87 */     return this.m_bNoHTMLMarkup;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.bean.List
 * JD-Core Version:    0.6.0
 */