/*     */ package com.bright.assetbank.category.taglib;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.category.constant.CategoryConstants;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ public abstract class CategoryLinkTag extends BodyTagSupport
/*     */   implements AssetBankConstants, CategoryConstants
/*     */ {
/*  36 */   private long m_lCategoryId = -1L;
/*  37 */   private long m_lCategoryTreeId = -1L;
/*     */ 
/* 167 */   private CategoryManager m_categoryManager = null;
/*     */ 
/* 179 */   private DBTransactionManager m_transactionManager = null;
/*     */ 
/* 192 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public void setCategoryId(long a_lCategoryId)
/*     */   {
/*  41 */     this.m_lCategoryId = a_lCategoryId;
/*     */   }
/*     */ 
/*     */   public void setCategoryTreeId(long a_lCategoryTreeId)
/*     */   {
/*  46 */     this.m_lCategoryTreeId = a_lCategoryTreeId;
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  56 */     return 2;
/*     */   }
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/*  67 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*  78 */     DBTransaction transaction = null;
/*  79 */     boolean bError = false;
/*     */     try
/*     */     {
/*  83 */       String sText = "";
/*     */ 
/*  86 */       HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
/*  87 */       ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(request);
/*     */ 
/*  89 */       transaction = getTransactionManager().getCurrentOrNewTransaction();
/*     */ 
/*  92 */       Category cat = getCategoryManager().getCategory(transaction, this.m_lCategoryTreeId, this.m_lCategoryId);
/*     */ 
/*  94 */       if (showLink(userProfile, cat))
/*     */       {
/*  96 */         sText = "<li><a href='" + getLinkUrl(cat) + "'>" + getLinkText(transaction, cat, userProfile) + "</a></li>";
/*     */ 
/*  99 */         this.pageContext.getOut().print(sText);
/*     */       }
/*     */     }
/*     */     catch (Exception be)
/*     */     {
/* 104 */       bError = true;
/* 105 */       throw new JspException("Exception in " + getClass().getSimpleName(), be);
/*     */     }
/*     */     finally
/*     */     {
/* 109 */       if (transaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 113 */           if (bError)
/*     */           {
/* 115 */             transaction.rollback();
/*     */           }
/*     */           else
/*     */           {
/* 119 */             transaction.commit();
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 130 */     return 6;
/*     */   }
/*     */ 
/*     */   protected abstract boolean showLink(ABUserProfile paramABUserProfile, Category paramCategory);
/*     */ 
/*     */   protected abstract String getLinkText(DBTransaction paramDBTransaction, Category paramCategory, ABUserProfile paramABUserProfile)
/*     */     throws Exception;
/*     */ 
/*     */   protected abstract String getLinkUrl(Category paramCategory);
/*     */ 
/*     */   protected CategoryManager getCategoryManager()
/*     */     throws ComponentException
/*     */   {
/* 171 */     if (this.m_categoryManager == null)
/*     */     {
/* 173 */       this.m_categoryManager = ((CategoryManager)GlobalApplication.getInstance().getComponentManager().lookup("CategoryManager"));
/*     */     }
/*     */ 
/* 176 */     return this.m_categoryManager;
/*     */   }
/*     */ 
/*     */   protected DBTransactionManager getTransactionManager()
/*     */     throws ComponentException
/*     */   {
/* 183 */     if (this.m_transactionManager == null)
/*     */     {
/* 185 */       this.m_transactionManager = ((DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager"));
/*     */     }
/*     */ 
/* 188 */     return this.m_transactionManager;
/*     */   }
/*     */ 
/*     */   protected ListManager getListManager()
/*     */     throws ComponentException
/*     */   {
/* 196 */     if (this.m_listManager == null)
/*     */     {
/* 198 */       this.m_listManager = ((ListManager)GlobalApplication.getInstance().getComponentManager().lookup("ListManager"));
/*     */     }
/*     */ 
/* 201 */     return this.m_listManager;
/*     */   }
/*     */ 
/*     */   public void release()
/*     */   {
/* 209 */     super.release();
/* 210 */     this.m_lCategoryId = -1L;
/* 211 */     this.m_lCategoryTreeId = -1L;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.taglib.CategoryLinkTag
 * JD-Core Version:    0.6.0
 */