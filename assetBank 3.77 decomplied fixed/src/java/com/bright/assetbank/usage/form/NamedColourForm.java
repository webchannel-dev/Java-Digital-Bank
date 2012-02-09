/*     */ package com.bright.assetbank.usage.form;
/*     */ 
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.usage.bean.NamedColour;
/*     */ import com.bright.framework.struts.ValidationException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class NamedColourForm extends Bn2Form
/*     */ {
/*     */   private long m_id;
/*     */   private String m_name;
/*     */   private String m_hexString;
/*     */ 
/*     */   public void reset(ActionMapping mapping, HttpServletRequest request)
/*     */   {
/*  45 */     super.reset(mapping, request);
/*  46 */     this.m_hexString = "#ffffff";
/*     */   }
/*     */ 
/*     */   public void fromModel(NamedColour a_namedColour)
/*     */   {
/*  54 */     this.m_id = a_namedColour.getId();
/*  55 */     this.m_name = a_namedColour.getName();
/*  56 */     this.m_hexString = ("#" + a_namedColour.getHexString());
/*     */   }
/*     */ 
/*     */   public NamedColour toModel() throws ValidationException {
/*  67 */     String hexString = getHexString();
/*     */     NamedColour colour;
/*     */     try {
/*  70 */       colour = NamedColour.namedColourFromHexString(getName(), hexString);
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/*  76 */       throw new ValidationException("failedValidationHexColour", new String[] { hexString });
/*     */     }
/*     */ 
/*  81 */     if (this.m_id > 0L)
/*     */     {
/*  83 */       colour.setId(this.m_id);
/*     */     }
/*     */ 
/*  86 */     return colour;
/*     */   }
/*     */ 
/*     */   public long getId()
/*     */   {
/*  91 */     return this.m_id;
/*     */   }
/*     */ 
/*     */   public void setId(long a_id)
/*     */   {
/*  96 */     this.m_id = a_id;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 101 */     return this.m_name;
/*     */   }
/*     */ 
/*     */   public void setName(String a_name)
/*     */   {
/* 106 */     this.m_name = a_name;
/*     */   }
/*     */ 
/*     */   public String getHexString()
/*     */   {
/* 111 */     return this.m_hexString;
/*     */   }
/*     */ 
/*     */   public void setHexString(String a_hexString)
/*     */   {
/* 116 */     this.m_hexString = a_hexString;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.usage.form.NamedColourForm
 * JD-Core Version:    0.6.0
 */