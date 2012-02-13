/*     */ package com.bright.assetbank.usage.bean;
/*     */ 
/*     */ import com.bright.framework.database.bean.DataBean;
/*     */ import com.bright.framework.image.bean.MutableColour;
/*     */ import com.bright.framework.image.util.ColourUtil;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class NamedColour extends DataBean
/*     */   implements MutableColour, Serializable
/*     */ {
/*     */   private String m_name;
/*     */   private int m_red;
/*     */   private int m_green;
/*     */   private int m_blue;
/*     */ 
/*     */   public static NamedColour namedColourFromHexString(String a_name, String a_hexString)
/*     */   {
/*  47 */     NamedColour c = new NamedColour();
/*  48 */     c.setName(a_name);
/*  49 */     ColourUtil.populateColourFromHexString(c, a_hexString);
/*  50 */     return c;
/*     */   }
/*     */ 
/*     */   public String getHexString()
/*     */   {
/*  61 */     return ColourUtil.hexStringFromColour(this);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  69 */     return this.m_name;
/*     */   }
/*     */ 
/*     */   public void setName(String a_name)
/*     */   {
/*  74 */     this.m_name = a_name;
/*     */   }
/*     */ 
/*     */   public int getRed()
/*     */   {
/*  79 */     return this.m_red;
/*     */   }
/*     */ 
/*     */   public void setRed(int a_red)
/*     */   {
/*  84 */     this.m_red = a_red;
/*     */   }
/*     */ 
/*     */   public int getGreen()
/*     */   {
/*  89 */     return this.m_green;
/*     */   }
/*     */ 
/*     */   public void setGreen(int a_green)
/*     */   {
/*  94 */     this.m_green = a_green;
/*     */   }
/*     */ 
/*     */   public int getBlue()
/*     */   {
/*  99 */     return this.m_blue;
/*     */   }
/*     */ 
/*     */   public void setBlue(int a_blue)
/*     */   {
/* 104 */     this.m_blue = a_blue;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.bean.NamedColour
 * JD-Core Version:    0.6.0
 */