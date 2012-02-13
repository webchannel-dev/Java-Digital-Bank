/*     */ package com.bright.framework.category.taglib;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.category.service.CategoryCountCacheManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.constant.CategoryConstants;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class CategoryExplorerTag extends BodyTagSupport
/*     */   implements CategoryConstants, AssetBankConstants
/*     */ {
/*  58 */   protected String m_sClassName = "pde";
/*  59 */   protected String m_sCategoryManagerName = "CategoryManager";
/*  60 */   protected String m_sTransactionManagerName = "DBTransactionManager";
/*  61 */   private long m_lCategoryTypeId = 0L;
/*  62 */   protected long m_lTopLevelCategoryId = -1L;
/*  63 */   protected String m_sLink = "";
/*  64 */   protected String m_sOnclick = "";
/*  65 */   protected String m_sCategoryIdParamName = "categoryId";
/*  66 */   protected String m_sCategoryTypeIdParamName = "categoryTypeId";
/*  67 */   protected long m_lSelectedCategoryId = 0L;
/*     */ 
/*  69 */   private int m_iShowLevel = 0;
/*  70 */   private int m_iCurrentLevel = 0;
/*  71 */   private CategoryCountCacheManager m_cacheManager = null;
/*     */ 
/*     */   private CategoryCountCacheManager getCacheManager()
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/*  78 */       if (this.m_cacheManager == null)
/*     */       {
/*  80 */         this.m_cacheManager = ((CategoryCountCacheManager)GlobalApplication.getInstance().getComponentManager().lookup("CategoryCountCacheManager"));
/*     */       }
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/*  85 */       throw new Bn2Exception(e.getMessage(), e);
/*     */     }
/*  87 */     return this.m_cacheManager;
/*     */   }
/*     */ 
/*     */   public String getClassName()
/*     */   {
/*  92 */     return this.m_sClassName;
/*     */   }
/*     */ 
/*     */   public void setClassName(String a_sClassName)
/*     */   {
/*  97 */     this.m_sClassName = a_sClassName;
/*     */   }
/*     */ 
/*     */   public String getLink()
/*     */   {
/* 102 */     return this.m_sLink;
/*     */   }
/*     */ 
/*     */   public void setLink(String a_sLink)
/*     */   {
/* 107 */     this.m_sLink = a_sLink;
/*     */   }
/*     */ 
/*     */   public String getOnclick()
/*     */   {
/* 112 */     return this.m_sOnclick;
/*     */   }
/*     */ 
/*     */   public void setOnclick(String a_sOnclick)
/*     */   {
/* 117 */     this.m_sOnclick = a_sOnclick;
/*     */   }
/*     */ 
/*     */   public long getCategoryTypeId()
/*     */   {
/* 122 */     return this.m_lCategoryTypeId;
/*     */   }
/*     */ 
/*     */   public void setCategoryTypeId(long a_lCategoryTypeId)
/*     */   {
/* 127 */     this.m_lCategoryTypeId = a_lCategoryTypeId;
/*     */   }
/*     */ 
/*     */   public long getTopLevelCategoryId()
/*     */   {
/* 132 */     return this.m_lTopLevelCategoryId;
/*     */   }
/*     */ 
/*     */   public void setTopLevelCategoryId(long a_lTopLevelCategoryId)
/*     */   {
/* 137 */     this.m_lTopLevelCategoryId = a_lTopLevelCategoryId;
/*     */   }
/*     */ 
/*     */   public long getSelectedCategoryId()
/*     */   {
/* 142 */     return this.m_lSelectedCategoryId;
/*     */   }
/*     */ 
/*     */   public void setSelectedCategoryId(long a_lSelectedCategoryId)
/*     */   {
/* 147 */     this.m_lSelectedCategoryId = a_lSelectedCategoryId;
/*     */   }
/*     */ 
/*     */   public void setSelectedCategoryId(String a_sSelectedCategoryId)
/*     */   {
/*     */     try
/*     */     {
/* 154 */       this.m_lSelectedCategoryId = Long.parseLong(a_sSelectedCategoryId);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/* 158 */       this.m_lSelectedCategoryId = 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setSelectedCategoryIdBean(Object a_sBeanName)
/*     */     throws JspException
/*     */   {
/* 165 */     Object bean = this.pageContext.findAttribute(a_sBeanName.toString());
/*     */ 
/* 167 */     if ((bean != null) && (!StringUtils.isEmpty(bean.toString())))
/*     */     {
/*     */       try
/*     */       {
/* 171 */         this.m_lSelectedCategoryId = Long.parseLong(bean.toString());
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/* 175 */         throw new JspException("Parameter selectedCategoryIdBean does not contin a valid long value");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 180 */       throw new JspException("Parameter selectedCategoryIdBean is empty");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getCategoryManagerName()
/*     */   {
/* 186 */     return this.m_sCategoryManagerName;
/*     */   }
/*     */ 
/*     */   public void setCategoryManagerName(String a_sCategoryManagerName)
/*     */   {
/* 191 */     this.m_sCategoryManagerName = a_sCategoryManagerName;
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerName()
/*     */   {
/* 196 */     return this.m_sTransactionManagerName;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerName(String a_sTransactionManagerName)
/*     */   {
/* 201 */     this.m_sTransactionManagerName = a_sTransactionManagerName;
/*     */   }
/*     */ 
/*     */   public String getCategoryIdParamName()
/*     */   {
/* 206 */     return this.m_sCategoryIdParamName;
/*     */   }
/*     */ 
/*     */   public void setCategoryIdParamName(String a_sCategoryIdParamName)
/*     */   {
/* 211 */     this.m_sCategoryIdParamName = a_sCategoryIdParamName;
/*     */   }
/*     */ 
/*     */   public String getCategoryTypeIdParamName()
/*     */   {
/* 216 */     return this.m_sCategoryTypeIdParamName;
/*     */   }
/*     */ 
/*     */   public void setCategoryTypeIdParamName(String a_sCategoryTypeIdParamName)
/*     */   {
/* 221 */     this.m_sCategoryTypeIdParamName = a_sCategoryTypeIdParamName;
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/* 239 */     return 2;
/*     */   }
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/* 255 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/* 271 */     String sCategoryBrowser = null;
/* 272 */     DBTransaction dbTransaction = null;
/* 273 */     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
/* 274 */     ABUserProfile userProfile = (ABUserProfile)ABUserProfile.getUserProfile(request);
/*     */     try
/*     */     {
/* 279 */       CategoryManager categoryManager = (CategoryManager)GlobalApplication.getInstance().getComponentManager().lookup(getCategoryManagerName());
/* 280 */       DBTransactionManager transactionManager = (DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup(getTransactionManagerName());
/* 281 */       dbTransaction = transactionManager.getNewTransaction();
/*     */ 
/* 284 */       Vector vecDescriptiveCategories = categoryManager.getCategories(dbTransaction, getCategoryTypeId(), getTopLevelCategoryId());
/*     */ 
/* 287 */       sCategoryBrowser = getCategoryBrowser(vecDescriptiveCategories, true, userProfile);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 291 */       if (dbTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 295 */           dbTransaction.rollback();
/*     */         }
/*     */         catch (Exception se)
/*     */         {
/* 299 */           throw new JspException("Exception occured trying to rollback transaction in CategoryExplorerTag tag: " + se.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 303 */       GlobalApplication.getInstance().getLogger().error("CategoryExplorerTag: exception: " + e.getMessage());
/* 304 */       throw new JspException(e);
/*     */     }
/*     */     finally
/*     */     {
/* 308 */       if (dbTransaction != null)
/*     */       {
/*     */         try
/*     */         {
/* 312 */           dbTransaction.commit();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 316 */           throw new JspException("Exception occured trying to commit transaction in CategoryExplorerTag tag: " + e.getMessage());
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 324 */       this.pageContext.getOut().print(sCategoryBrowser);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 328 */       throw new JspException("IOException in CategoryExplorerTag tag: " + e.getMessage());
/*     */     }
/*     */ 
/* 331 */     return 6;
/*     */   }
/*     */ 
/*     */   protected String getCategoryBrowser(Vector<Category> a_vecCategories, boolean a_bFirst, ABUserProfile a_userProfile)
/*     */     throws Bn2Exception
/*     */   {
/* 356 */     StringBuffer sbBrowser = new StringBuffer();
/* 357 */     String sStart = "<ul>";
/* 358 */     this.m_iCurrentLevel += 1;
/*     */ 
/* 360 */     if (a_bFirst)
/*     */     {
/* 362 */       sStart = "<ul class=\"" + getClassName() + "\">";
/*     */     }
/*     */ 
/* 365 */     Set fullIds = a_userProfile.getPermissionCategoryIds(1);
/* 366 */     for (Category cat : a_vecCategories)
/*     */     {
/* 368 */       if ((cat.getIsBrowsable()) && ((getCategoryTypeId() == 1L) || (a_userProfile.getIsAdmin()) || (fullIds.contains(Long.valueOf(cat.getId())))))
/*     */       {
/* 370 */         Integer intCount = getCacheManager().getItemCount(cat.getId(), cat.getCategoryTypeId(), a_userProfile);
/*     */ 
/* 372 */         if (((getCategoryTypeId() == 1L) && (AssetBankSettings.getShowEmptyCategories())) || ((getCategoryTypeId() == 2L) && (AssetBankSettings.getShowEmptyAccessLevels())) || (intCount.intValue() > 0) || (a_userProfile.getIsAdmin()))
/*     */         {
/* 378 */           String sLink = getLink().replaceAll(getCategoryIdParamName() + "=", getCategoryIdParamName() + "=" + cat.getId());
/* 379 */           sLink = sLink.replaceAll(getCategoryTypeIdParamName() + "=", getCategoryTypeIdParamName() + "=" + cat.getCategoryTypeId());
/*     */ 
/* 381 */           String sOnclick = getOnclick().replaceAll(getCategoryIdParamName() + "=", getCategoryIdParamName() + "=" + cat.getId());
/* 382 */           sOnclick = sOnclick.replaceAll(getCategoryTypeIdParamName() + "=", getCategoryTypeIdParamName() + "=" + cat.getCategoryTypeId());
/* 383 */           boolean bIsEmpty = false;
/*     */ 
/* 385 */           if (((getCategoryTypeId() == 1L) && (!AssetBankSettings.getShowEmptyCategories())) || ((getCategoryTypeId() == 2L) && (!AssetBankSettings.getShowEmptyAccessLevels()) && (!a_userProfile.getIsAdmin())))
/*     */           {
/* 389 */             Integer intSubCount = this.m_cacheManager.getItemCountForSubCategories(cat, a_userProfile);
/* 390 */             if (intSubCount.intValue() <= 0)
/*     */             {
/* 392 */               bIsEmpty = true;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 397 */           if ((!bIsEmpty) && (cat.getChildCategories() != null) && (cat.getChildCategories().size() > 0))
/*     */           {
/* 399 */             sbBrowser.append("<li>");
/*     */           }
/*     */           else
/*     */           {
/* 403 */             sbBrowser.append("<li class=\"empty\">");
/*     */           }
/*     */ 
/* 406 */           String sCurrent = "";
/*     */ 
/* 408 */           if (cat.getId() == getSelectedCategoryId())
/*     */           {
/* 410 */             sCurrent = "current";
/*     */           }
/*     */ 
/* 414 */           boolean bSubList = (!bIsEmpty) && (cat.getChildCategories() != null) && (cat.getChildCategories().size() > 0);
/*     */ 
/* 417 */           if (bSubList)
/*     */           {
/* 419 */             sbBrowser.append("<a href=\"#\" class=\"expand\">&nbsp;</a> ");
/*     */           }
/*     */ 
/* 422 */           sbBrowser.append("<a href=\"" + sLink + "\"");
/* 423 */           if (StringUtil.stringIsPopulated(sOnclick))
/*     */           {
/* 425 */             sbBrowser.append(" onclick=\"" + sOnclick + "\"");
/*     */           }
/*     */ 
/* 428 */           sbBrowser.append(" class=\"" + sCurrent + "\">" + cat.getName() + " (" + intCount.intValue() + ") " + "</a>");
/*     */ 
/* 431 */           if (bSubList)
/*     */           {
/* 433 */             sbBrowser.append(getCategoryBrowser(cat.getChildCategories(), false, a_userProfile));
/* 434 */             this.m_iCurrentLevel -= 1;
/*     */           }
/*     */ 
/* 438 */           if ((!a_bFirst) && ((cat.getId() == getSelectedCategoryId()) || (this.m_iCurrentLevel <= this.m_iShowLevel)))
/*     */           {
/* 440 */             sStart = "<ul class=\"show\">";
/* 441 */             this.m_iShowLevel = this.m_iCurrentLevel;
/*     */           }
/* 444 */           else if (a_bFirst)
/*     */           {
/* 446 */             this.m_iShowLevel = 0;
/*     */           }
/*     */           else
/*     */           {
/* 450 */             sStart = "<ul class=\"hide\">";
/*     */           }
/*     */ 
/* 453 */           sbBrowser.append("</li>");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 458 */     sbBrowser.append("</ul>");
/*     */ 
/* 460 */     return sStart + sbBrowser.toString();
/*     */   }
/*     */ 
/*     */   public void release()
/*     */   {
/* 469 */     super.release();
/* 470 */     this.m_sClassName = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.category.taglib.CategoryExplorerTag
 * JD-Core Version:    0.6.0
 */