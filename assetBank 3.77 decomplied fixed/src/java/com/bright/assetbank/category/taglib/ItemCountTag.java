/*     */ package com.bright.assetbank.category.taglib;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.struts.taglib.TagUtils;
/*     */ 
/*     */ public class ItemCountTag extends BodyTagSupport
/*     */ {
/*  51 */   protected String m_sId = null;
/*  52 */   protected String m_sName = null;
/*  53 */   protected String m_sProperty = null;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  57 */     return this.m_sName;
/*     */   }
/*     */ 
/*     */   public void setName(String a_sName) {
/*  61 */     this.m_sName = a_sName;
/*     */   }
/*     */ 
/*     */   public String getProperty() {
/*  65 */     return this.m_sProperty;
/*     */   }
/*     */ 
/*     */   public void setProperty(String a_sProperty) {
/*  69 */     this.m_sProperty = a_sProperty;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/*  74 */     return this.m_sId;
/*     */   }
/*     */ 
/*     */   public void setId(String a_sId)
/*     */   {
/*  79 */     this.m_sId = a_sId;
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  98 */     return 2;
/*     */   }
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/* 115 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/* 135 */       Object oCategory = TagUtils.getInstance().lookup(this.pageContext, this.m_sName, this.m_sProperty, null);
/*     */ 
/* 140 */       if (oCategory == null) {
/* 141 */         return 0;
/*     */       }
/*     */ 
/* 144 */       Category cat = (Category)oCategory;
/*     */ 
/* 147 */       HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
/*     */ 
/* 150 */       ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(request);
/*     */ 
/* 153 */       CategoryCountCacheManager cacheManager = (CategoryCountCacheManager)GlobalApplication.getInstance().getComponentManager().lookup("CategoryCountCacheManager");
/*     */ 
/* 155 */       Integer intCount = cacheManager.getItemCount(cat.getId(), cat.getCategoryTypeId(), userProfile);
/*     */ 
/* 158 */       this.pageContext.setAttribute(this.m_sId, intCount, 1);
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/* 162 */       throw new JspException("Component Exception occured whilst looking up component called CategoryCountCacheManager: " + e.getMessage());
/*     */     }
/*     */     catch (Bn2Exception be)
/*     */     {
/* 166 */       throw new JspException("Bn2Exception in ItemCountTag " + be.getMessage());
/*     */     }
/*     */ 
/* 170 */     return 6;
/*     */   }
/*     */ 
/*     */   public void release()
/*     */   {
/* 179 */     super.release();
/* 180 */     this.m_sId = null;
/* 181 */     this.m_sName = null;
/* 182 */     this.m_sProperty = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.category.taglib.ItemCountTag
 * JD-Core Version:    0.6.0
 */