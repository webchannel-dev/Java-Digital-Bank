/*     */ package com.bright.assetbank.user.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.framework.customfield.bean.CustomField;
/*     */ import com.bright.framework.customfield.bean.CustomFieldSelectedValueSet;
/*     */ import com.bright.framework.customfield.bean.CustomFieldValue;
/*     */ import com.bright.framework.file.DefaultBeanWriter;
/*     */ import com.bright.framework.file.FileFormat;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedWriter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ public class UserBeanWriter extends DefaultBeanWriter
/*     */ {
/*     */   private static final String c_ksClassName = "UserBeanWriter";
/*  41 */   private boolean m_bHidePasswords = false;
/*     */ 
/*     */   public UserBeanWriter(BufferedWriter a_fileWriter, FileFormat a_format)
/*     */   {
/*  45 */     super(a_fileWriter, a_format);
/*     */   }
/*     */ 
/*     */   public void setHidePasswords(boolean a_bHidePasswords)
/*     */   {
/*  51 */     this.m_bHidePasswords = a_bHidePasswords;
/*     */   }
/*     */ 
/*     */   protected Vector<Method> getStringGetters(Class a_class)
/*     */   {
/*  68 */     Vector<Method> vecMethods = super.getStringGetters(a_class);
/*     */ 
/*  71 */     if (this.m_bHidePasswords)
/*     */     {
/*  73 */       Method tempMethod = null;
/*  74 */       for (Method method : vecMethods)
/*     */       {
/*  76 */         if (method.getName().equals("getPassword"))
/*     */         {
/*  78 */           tempMethod = method;
/*     */         }
/*     */       }
/*  81 */       if (tempMethod != null)
/*     */       {
/*  83 */         vecMethods.remove(tempMethod);
/*     */       }
/*     */     }
/*     */ 
/*  87 */     return vecMethods;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getExtraHeaders()
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 103 */       CustomFieldManager m_cfManager = (CustomFieldManager)GlobalApplication.getInstance().getComponentManager().lookup("CustomFieldManager");
/* 104 */       Vector<CustomField> vecFields = m_cfManager.getCustomFields(null, -1L, 1L, null);
/*     */ 
/* 106 */       ArrayList alExtraHeaders = new ArrayList();
/* 107 */       if (vecFields != null)
/*     */       {
/* 109 */         for (CustomField field : vecFields)
/*     */         {
/* 111 */           alExtraHeaders.add(field.getName());
/*     */         }
/*     */       }
/* 114 */       return alExtraHeaders;
/*     */     }
/*     */     catch (ComponentException e) {
/*     */     
/* 118 */     throw new Bn2Exception("UserBeanWriter : Error getting extra headers: ", e);}
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getExtraValues(Object a_object, ArrayList<String> a_alHeaders)
/*     */     throws Bn2Exception
/*     */   {
/*     */     try
/*     */     {
/* 135 */       CustomFieldManager m_cfManager = (CustomFieldManager)GlobalApplication.getInstance().getComponentManager().lookup("CustomFieldManager");
/* 136 */       ABUser user = (ABUser)a_object;
/* 137 */       CustomFieldSelectedValueSet values = m_cfManager.getValueMappings(null, user.getId(), 1L);
/*     */ 
/* 140 */       String sTextValue = "";
/* 141 */       ArrayList alValues = new ArrayList(a_alHeaders.size());
/* 142 */       for (String sName : a_alHeaders)
/*     */       {
/* 144 */         sTextValue = values.getSelectedTextValue(null, sName);
/* 145 */         if (!StringUtil.stringIsPopulated(sTextValue))
/*     */         {
/* 147 */           sTextValue = "";
/* 148 */           Vector<CustomFieldValue> vecListValues = values.getSelectedListValues(null, sName);
/* 149 */           if (vecListValues != null)
/*     */           {
/* 151 */             for (CustomFieldValue value : vecListValues)
/*     */             {
/* 153 */               sTextValue = sTextValue + value.getValue() + ",";
/*     */             }
/*     */           }
/*     */         }
/* 157 */         alValues.add(sTextValue);
/*     */       }
/* 159 */       return alValues;
/*     */     }
/*     */     catch (Exception e) {
/*     */     
/* 163 */     throw new Bn2Exception("UserBeanWriter : Error getting extra values: ", e);}
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.bean.UserBeanWriter
 * JD-Core Version:    0.6.0
 */