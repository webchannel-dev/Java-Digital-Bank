/*     */ package com.bright.framework.category.action;
/*     */ 
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.form.CategoryAdminForm;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.message.constant.MessageConstants;
/*     */ import java.text.Collator;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public abstract class ViewCategoryAdminAction extends BTransactionAction
/*     */   implements CommonConstants, CategoryConstants, MessageConstants
/*     */ {
/*     */   private CategoryManager m_categoryManager;
/*     */ 
/*     */   public ViewCategoryAdminAction()
/*     */   {
/*  58 */     this.m_categoryManager = null;
/*     */   }
/*     */   public void setCategoryManager(CategoryManager a_categoryManager) {
/*  61 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ 
/*     */   public abstract ActionForward execute(ActionMapping paramActionMapping, ActionForm paramActionForm, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, DBTransaction paramDBTransaction)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   public void getCategories(CategoryAdminForm categoryAdminForm, DBTransaction a_dbTransaction, long a_lCatTreeId, long a_lCatId, Language a_language)
/*     */     throws Bn2Exception
/*     */   {
/*  90 */     Category cat = this.m_categoryManager.getCategory(a_dbTransaction, a_lCatTreeId, a_lCatId);
/*  91 */     categoryAdminForm.setCategoryName(cat.getName());
/*     */ 
/*  94 */     if (a_lCatId != -1L)
/*     */     {
/*  96 */       categoryAdminForm.setAncestorCategoryList(this.m_categoryManager.getAncestors(a_dbTransaction, a_lCatTreeId, a_lCatId));
/*     */     }
/*     */ 
/*  99 */     categoryAdminForm.setCategoryId(a_lCatId);
/* 100 */     categoryAdminForm.setCategoryTreeId(a_lCatTreeId);
/* 101 */     categoryAdminForm.setSubCategoryList(this.m_categoryManager.getCategories(a_dbTransaction, a_lCatTreeId, a_lCatId, a_language));
/* 102 */     categoryAdminForm.setCategoryTreeName(this.m_categoryManager.getCategoryTypeName(a_lCatTreeId));
/* 103 */     categoryAdminForm.setNodeURL("viewCategoryAdmin");
/* 104 */     categoryAdminForm.setTopLevel(cat.getDepth() == 0);
/*     */ 
/* 107 */     categoryAdminForm.getNewCategory().setIsBrowsable(true);
/*     */ 
/* 110 */     categoryAdminForm.getNewCategory().setIsRestrictive((cat.getIsRestrictive()) && (cat.getDepth() == 0));
/*     */   }
/*     */ 
/*     */   public Vector<Category> filterCategories(Vector<Category> a_vec, String a_sFilter)
/*     */   {
/* 122 */     Vector filtered = new Vector();
/*     */ 
/* 124 */     Iterator it = a_vec.iterator();
/* 125 */     while (it.hasNext())
/*     */     {
/* 127 */       Category cat = (Category)it.next();
/*     */ 
/* 129 */       if (cat.getName().toLowerCase().startsWith(a_sFilter))
/*     */       {
/* 131 */         filtered.add(cat);
/*     */       }
/*     */     }
/* 134 */     Collections.sort(filtered, new CategoryComparator());
/*     */ 
/* 136 */     return filtered;
/*     */   }
/*     */ 
/*     */   private static class CategoryComparator
/*     */     implements Comparator<Category>
/*     */   {
/* 144 */     private Collator c = Collator.getInstance();
/*     */ 
/*     */     public int compare(Category c1, Category c2)
/*     */     {
/* 149 */       return c1.getName().compareToIgnoreCase(c2.getName());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.action.ViewCategoryAdminAction
 * JD-Core Version:    0.6.0
 */