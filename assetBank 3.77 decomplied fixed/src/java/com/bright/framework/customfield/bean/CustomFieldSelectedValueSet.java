/*     */ package com.bright.framework.customfield.bean;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class CustomFieldSelectedValueSet
/*     */ {
/*  33 */   private Vector<CustomFieldValueMapping> m_vecSelectedValues = null;
/*  34 */   private long m_lUsageTypeId = -1L;
/*  35 */   private long m_lItemId = -1L;
/*     */ 
/*     */   public void setSelectedValues(Vector<CustomFieldValueMapping> a_vecSelectedValues)
/*     */   {
/*  39 */     this.m_vecSelectedValues = a_vecSelectedValues;
/*     */   }
/*     */ 
/*     */   public Vector<CustomFieldValueMapping> getSelectedValues()
/*     */   {
/*  44 */     return this.m_vecSelectedValues;
/*     */   }
/*     */ 
/*     */   public void setUsageTypeId(long a_lUsageTypeId)
/*     */   {
/*  49 */     this.m_lUsageTypeId = a_lUsageTypeId;
/*     */   }
/*     */ 
/*     */   public long getUsageTypeId()
/*     */   {
/*  54 */     return this.m_lUsageTypeId;
/*     */   }
/*     */ 
/*     */   public void setItemId(long a_lItemId)
/*     */   {
/*  59 */     this.m_lItemId = a_lItemId;
/*     */   }
/*     */ 
/*     */   public long getItemId()
/*     */   {
/*  64 */     return this.m_lItemId;
/*     */   }
/*     */ 
/*     */   public boolean getSelectedListValue(String a_sInput)
/*     */   {
/*  70 */     String[] aIds = a_sInput.split(":");
/*  71 */     String a_sFieldId = aIds[0];
/*  72 */     String a_sValueId = aIds[1];
/*     */     try
/*     */     {
/*  76 */       Vector vecSelectedValues = getSelectedListValues(a_sFieldId);
/*  77 */       long lValueId = Long.parseLong(a_sValueId);
/*     */ 
/*  79 */       if (vecSelectedValues != null)
/*     */       {
/*  81 */         for (int i = 0; i < vecSelectedValues.size(); i++)
/*     */         {
/*  83 */           CustomFieldValue value = (CustomFieldValue)vecSelectedValues.elementAt(i);
/*  84 */           if (value.getId() == lValueId)
/*     */           {
/*  86 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  93 */       GlobalApplication.getInstance().getLogger().error("CustomFieldSelectedValueSet.getSelectedListValue: Unable to get selected list values " + e.getMessage());
/*     */     }
/*     */ 
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   public CustomFieldValue getFirstSelectedListValue(String a_sFieldId)
/*     */     throws Exception
/*     */   {
/* 102 */     Vector vecTemp = getSelectedListValues(a_sFieldId);
/*     */ 
/* 104 */     if ((vecTemp != null) && (vecTemp.size() > 0))
/*     */     {
/* 106 */       return (CustomFieldValue)vecTemp.firstElement();
/*     */     }
/* 108 */     return new CustomFieldValue();
/*     */   }
/*     */ 
/*     */   public Vector<CustomFieldValue> getSelectedListValues(String a_sFieldId)
/*     */     throws Exception
/*     */   {
/* 114 */     return getSelectedListValues(a_sFieldId, null);
/*     */   }
/*     */ 
/*     */   public Vector<CustomFieldValue> getSelectedListValues(String a_sFieldId, String a_sName) throws Exception
/*     */   {
/* 119 */     Vector vecReturn = new Vector();
/*     */ 
/* 121 */     long lFieldId = -1L;
/*     */ 
/* 123 */     if (StringUtil.stringIsPopulated(a_sFieldId))
/*     */     {
/* 125 */       lFieldId = Long.parseLong(a_sFieldId);
/*     */     }
/*     */ 
/* 129 */     if (getSelectedValues() != null)
/*     */     {
/* 131 */       for (int i = 0; i < getSelectedValues().size(); i++)
/*     */       {
/* 133 */         CustomFieldValueMapping mapping = (CustomFieldValueMapping)getSelectedValues().elementAt(i);
/*     */ 
/* 135 */         if (((lFieldId > 0L) && (mapping.getCustomField().getId() == lFieldId)) || ((StringUtil.stringIsPopulated(a_sName)) && (mapping.getCustomField().getName().equals(a_sName))))
/*     */         {
/* 139 */           return mapping.getListValues();
/*     */         }
/*     */       }
/*     */     }
/* 143 */     return vecReturn;
/*     */   }
/*     */ 
/*     */   public String getSelectedTextValue(String a_sFieldId)
/*     */   {
/* 148 */     return getSelectedTextValue(a_sFieldId, null);
/*     */   }
/*     */ 
/*     */   public String getSelectedTextValue(String a_sFieldId, String a_sName)
/*     */   {
/* 153 */     String sReturn = "";
/*     */     try
/*     */     {
/* 157 */       long lFieldId = -1L;
/*     */ 
/* 159 */       if (StringUtil.stringIsPopulated(a_sFieldId))
/*     */       {
/* 161 */         lFieldId = Long.parseLong(a_sFieldId);
/*     */       }
/*     */ 
/* 165 */       if (getSelectedValues() != null)
/*     */       {
/* 167 */         for (int i = 0; i < getSelectedValues().size(); i++)
/*     */         {
/* 169 */           CustomFieldValueMapping mapping = (CustomFieldValueMapping)getSelectedValues().elementAt(i);
/*     */ 
/* 171 */           if (((lFieldId > 0L) && (mapping.getCustomField().getId() == lFieldId) && (mapping.getTextValue() != null)) || ((StringUtil.stringIsPopulated(a_sName)) && (mapping.getCustomField().getName().equals(a_sName))))
/*     */           {
/* 175 */             return mapping.getTextValue();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 182 */       GlobalApplication.getInstance().getLogger().error("CustomFieldSelectedValueSet.getSelectedTextValue: Unable to get selected list values " + e.getMessage());
/*     */     }
/*     */ 
/* 185 */     return sReturn;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.bean.CustomFieldSelectedValueSet
 * JD-Core Version:    0.6.0
 */