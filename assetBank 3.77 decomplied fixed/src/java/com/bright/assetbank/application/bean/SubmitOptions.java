/*     */ package com.bright.assetbank.application.bean;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class SubmitOptions
/*     */ {
/*  26 */   private ArrayList m_listOptions = new ArrayList();
/*  27 */   private Vector m_vecHiddenOptions = new Vector();
/*     */ 
/*  29 */   private boolean m_bHasMixedPermissions = false;
/*     */ 
/*     */   public void addOption(int a_iOption)
/*     */   {
/*  38 */     this.m_listOptions.add(new Integer(a_iOption));
/*     */   }
/*     */ 
/*     */   public int getOptionCount()
/*     */   {
/*  43 */     if (this.m_listOptions != null)
/*     */     {
/*  45 */       return this.m_listOptions.size();
/*     */     }
/*  47 */     return 0;
/*     */   }
/*     */ 
/*     */   public void addHiddenOption(int a_iOption)
/*     */   {
/*  57 */     this.m_vecHiddenOptions.add(new Integer(a_iOption));
/*     */   }
/*     */ 
/*     */   public Vector getHiddenOptions()
/*     */   {
/*  63 */     return this.m_vecHiddenOptions;
/*     */   }
/*     */ 
/*     */   public void removeOption(int a_iOption)
/*     */   {
/*  73 */     if (getContains(a_iOption))
/*     */     {
/*  75 */       this.m_listOptions.remove(new Integer(a_iOption));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getContains(int a_iOption)
/*     */   {
/*  87 */     boolean bContains = this.m_listOptions.contains(new Integer(a_iOption));
/*  88 */     return bContains;
/*     */   }
/*     */ 
/*     */   public int getFirstOption()
/*     */   {
/*  98 */     int i = 0;
/*  99 */     if (this.m_listOptions.size() >= 1)
/*     */     {
/* 101 */       i = ((Integer)this.m_listOptions.get(0)).intValue();
/*     */     }
/* 103 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean getOnlyContainsSingleOption()
/*     */   {
/* 113 */     boolean b = this.m_listOptions.size() == 1;
/* 114 */     return b;
/*     */   }
/*     */ 
/*     */   public boolean getContainsMoreThanOneOption()
/*     */   {
/* 119 */     boolean b = this.m_listOptions.size() > 1;
/* 120 */     return b;
/*     */   }
/*     */ 
/*     */   public boolean getIsEmpty()
/*     */   {
/* 125 */     boolean b = (this.m_listOptions.size() == 0) && ((this.m_vecHiddenOptions == null) || (this.m_vecHiddenOptions.size() == 0));
/* 126 */     return b;
/*     */   }
/*     */ 
/*     */   public boolean getHasVisibleOptions()
/*     */   {
/* 131 */     return this.m_listOptions.size() == 0;
/*     */   }
/*     */ 
/*     */   public boolean getHasMixedPermissions()
/*     */   {
/* 136 */     return this.m_bHasMixedPermissions;
/*     */   }
/*     */ 
/*     */   public void setHasMixedPermissions(boolean a_sHasMixedPermissions)
/*     */   {
/* 141 */     this.m_bHasMixedPermissions = a_sHasMixedPermissions;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.bean.SubmitOptions
 * JD-Core Version:    0.6.0
 */