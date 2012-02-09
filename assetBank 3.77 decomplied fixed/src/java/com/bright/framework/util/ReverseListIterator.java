/*    */ package com.bright.framework.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.ListIterator;
/*    */ 
/*    */ public class ReverseListIterator<E>
/*    */   implements Iterator<E>
/*    */ {
/*    */   private ListIterator<E> m_forwardIterator;
/*    */ 
/*    */   public ReverseListIterator(List<E> a_list)
/*    */   {
/* 28 */     this.m_forwardIterator = a_list.listIterator(a_list.size());
/*    */   }
/*    */ 
/*    */   public boolean hasNext()
/*    */   {
/* 33 */     return this.m_forwardIterator.hasPrevious();
/*    */   }
/*    */ 
/*    */   public E next()
/*    */   {
/* 38 */     return this.m_forwardIterator.previous();
/*    */   }
/*    */ 
/*    */   public void remove()
/*    */   {
/* 43 */     this.m_forwardIterator.remove();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.util.ReverseListIterator
 * JD-Core Version:    0.6.0
 */