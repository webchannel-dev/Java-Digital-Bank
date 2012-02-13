/*     */ package com.bright.assetbank.attribute.form;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.DisplayAttribute;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.framework.common.bean.IdValueBean;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class DisplayAttributeForm extends Bn2Form
/*     */   implements AttributeConstants, AssetBankConstants
/*     */ {
/*  46 */   private Vector<Attribute> m_vecAttributes = null;
/*  47 */   private Vector<DisplayAttribute> m_vecDisplayAttributes = null;
/*  48 */   private DisplayAttribute m_displayAttribute = new DisplayAttribute();
/*  49 */   private String m_sDisplayLengthString = null;
/*  50 */   private Vector<Attribute> m_vecNameAttributes = null;
/*  51 */   private long m_lDescriptionAttribute = -1L;
/*  52 */   private ArrayList<IdValueBean> m_alGroups = null;
/*     */ 
/*     */   public void setAttributes(Vector<Attribute> a_vecAttributes)
/*     */   {
/*  56 */     this.m_vecAttributes = a_vecAttributes;
/*     */   }
/*     */ 
/*     */   public Vector<Attribute> getAttributes()
/*     */   {
/*  61 */     return this.m_vecAttributes;
/*     */   }
/*     */ 
/*     */   public void setDisplayAttributes(Vector<DisplayAttribute> a_vecDisplayAttributes)
/*     */   {
/*  66 */     this.m_vecDisplayAttributes = a_vecDisplayAttributes;
/*     */   }
/*     */ 
/*     */   public Vector<DisplayAttribute> getDisplayAttributes()
/*     */   {
/*  71 */     return this.m_vecDisplayAttributes;
/*     */   }
/*     */ 
/*     */   public int getNoOfSortAttributes()
/*     */   {
/*  76 */     if (getDisplayAttributes() != null)
/*     */     {
/*  78 */       return getDisplayAttributes().size();
/*     */     }
/*  80 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setDisplayAttribute(DisplayAttribute a_displayAttribute)
/*     */   {
/*  85 */     this.m_displayAttribute = a_displayAttribute;
/*     */   }
/*     */ 
/*     */   public DisplayAttribute getDisplayAttribute()
/*     */   {
/*  90 */     return this.m_displayAttribute;
/*     */   }
/*     */ 
/*     */   public void setDisplayLengthString(String a_sDisplayLengthString)
/*     */   {
/*  95 */     this.m_sDisplayLengthString = a_sDisplayLengthString;
/*     */   }
/*     */ 
/*     */   public String getDisplayLengthString()
/*     */   {
/* 100 */     return this.m_sDisplayLengthString;
/*     */   }
/*     */ 
/*     */   public void setNameAttributes(Vector<Attribute> a_vecNameAttributes)
/*     */   {
/* 105 */     this.m_vecNameAttributes = a_vecNameAttributes;
/*     */   }
/*     */ 
/*     */   public Vector<Attribute> getNameAttributes()
/*     */   {
/* 110 */     return this.m_vecNameAttributes;
/*     */   }
/*     */ 
/*     */   public void validate(HttpServletRequest a_request, UserProfile a_userProfile, DBTransaction a_dbTransaction, ListManager a_listManager) throws Bn2Exception
/*     */   {
/* 115 */     if (getDisplayAttribute().getAttribute().getId() <= 0L)
/*     */     {
/* 117 */       addError(a_listManager.getListItem(a_dbTransaction, "displayAttributeNotSelected", a_userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/* 120 */     if (StringUtil.stringIsPopulated(getDisplayLengthString()))
/*     */     {
/*     */       try
/*     */       {
/* 124 */         Integer.parseInt(getDisplayLengthString());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 128 */         addError(a_listManager.getListItem(a_dbTransaction, "nonNumericDisplayLength", a_userProfile.getCurrentLanguage()).getBody());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public long getDescriptionAttribute()
/*     */   {
/* 135 */     return this.m_lDescriptionAttribute;
/*     */   }
/*     */ 
/*     */   public void setDescriptionAttribute(long descriptionAttribute)
/*     */   {
/* 140 */     this.m_lDescriptionAttribute = descriptionAttribute;
/*     */   }
/*     */ 
/*     */   public void setDisplayAttributeGroups(ArrayList<IdValueBean> a_alGroups)
/*     */   {
/* 145 */     this.m_alGroups = a_alGroups;
/*     */   }
/*     */ 
/*     */   public ArrayList<IdValueBean> getDisplayAttributeGroups()
/*     */   {
/* 150 */     return this.m_alGroups;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.form.DisplayAttributeForm
 * JD-Core Version:    0.6.0
 */