/*    */ package com.bright.framework.simplelist.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.simplelist.bean.List;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class ListForm extends Bn2Form
/*    */ {
/* 35 */   private List m_list = null;
/* 36 */   private ListItem m_listItem = null;
/* 37 */   private Vector m_vecList = null;
/*    */ 
/*    */   public List getList()
/*    */   {
/* 42 */     return this.m_list;
/*    */   }
/*    */ 
/*    */   public void setList(List a_list)
/*    */   {
/* 47 */     this.m_list = a_list;
/*    */   }
/*    */ 
/*    */   public Vector getListItems()
/*    */   {
/* 52 */     return this.m_vecList;
/*    */   }
/*    */ 
/*    */   public void setListItems(Vector a_vecList)
/*    */   {
/* 57 */     this.m_vecList = a_vecList;
/*    */   }
/*    */ 
/*    */   public ListItem getListItem()
/*    */   {
/* 62 */     if (this.m_listItem == null)
/*    */     {
/* 64 */       this.m_listItem = new ListItem();
/*    */     }
/*    */ 
/* 67 */     return this.m_listItem;
/*    */   }
/*    */ 
/*    */   public void setListItem(ListItem a_listItem)
/*    */   {
/* 72 */     this.m_listItem = a_listItem;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.form.ListForm
 * JD-Core Version:    0.6.0
 */