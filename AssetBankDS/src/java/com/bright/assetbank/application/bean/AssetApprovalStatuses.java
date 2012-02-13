/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.category.bean.Category;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ public class AssetApprovalStatuses
/*     */ {
/*  36 */   private Map<Long, Boolean> m_approvalStatusByCatId = new HashMap();
/*     */   private CategoryManager m_categoryManager;
/*     */ 
/*     */   public AssetApprovalStatuses()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AssetApprovalStatuses(Map<Long, Boolean> a_approvalStatusByCatId)
/*     */   {
/*  47 */     for (Map.Entry entry : a_approvalStatusByCatId.entrySet())
/*     */     {
/*  49 */       long catId = ((Long)entry.getKey()).longValue();
/*  50 */       boolean approved = ((Boolean)entry.getValue()).booleanValue();
/*  51 */       setApproved(catId, approved);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setApproved(long a_catId, boolean a_approved)
/*     */   {
/*  61 */     Category cat = getAccessLevel(a_catId);
/*     */ 
/*  63 */     if ((!cat.getIsRestrictive()) && (cat.getParent() != null))
/*     */     {
/*  65 */       setApproved(cat.getParent().getId(), a_approved);
/*     */     }
/*     */ 
/*  69 */     this.m_approvalStatusByCatId.put(Long.valueOf(a_catId), Boolean.valueOf(a_approved));
/*     */   }
/*     */ 
/*     */   public boolean isApproved(long a_catId)
/*     */   {
/*  78 */     Category cat = getAccessLevel(a_catId);
/*     */ 
/*  80 */     if ((!cat.getIsRestrictive()) && (cat.getParent() != null))
/*     */     {
/*  82 */       return isApproved(cat.getParent().getId());
/*     */     }
/*     */ 
/*  85 */     Boolean approved = (Boolean)this.m_approvalStatusByCatId.get(Long.valueOf(a_catId));
/*     */ 
/*  89 */     return (approved != null) && (approved.booleanValue());
/*     */   }
/*     */ 
/*     */   private CategoryManager getCategoryManager()
/*     */   {
/*  94 */     if (this.m_categoryManager == null)
/*     */     {
/*     */       try
/*     */       {
/*  98 */         this.m_categoryManager = ((CategoryManager)GlobalApplication.getInstance().getComponentManager().lookup("CategoryManager"));
/*     */       }
/*     */       catch (ComponentException e)
/*     */       {
/* 102 */         throw new RuntimeException("Error looking up CategoryManager", e);
/*     */       }
/*     */     }
/* 105 */     return this.m_categoryManager;
/*     */   }
/*     */ 
/*     */   private Category getAccessLevel(long a_catId)
/*     */   {
/*     */     try
/*     */     {
/* 112 */       return getCategoryManager().getCategory(null, 2L, a_catId);
/*     */     }
/*     */     catch (Bn2Exception e) {
/*     */     
/* 116 */     throw new RuntimeException(e);

}
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.AssetApprovalStatuses
 * JD-Core Version:    0.6.0
 */