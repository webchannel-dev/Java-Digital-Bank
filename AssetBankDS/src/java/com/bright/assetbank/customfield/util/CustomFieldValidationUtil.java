/*     */ package com.bright.assetbank.customfield.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.form.Bn2Form;
/*     */ import com.bright.assetbank.customfield.bean.CustomField;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.framework.customfield.bean.CustomFieldSelectedValueSet;
/*     */ import com.bright.framework.customfield.constant.CustomFieldConstants;
/*     */ import com.bright.framework.customfield.util.CustomFieldUtil;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class CustomFieldValidationUtil
/*     */   implements CustomFieldConstants
/*     */ {
/*     */   public static void validateFields(HttpServletRequest a_request, CustomFieldManager a_fieldManager, DBTransaction a_dbTransaction, Bn2Form a_form, long a_lUsageTypeId, long a_lUserId, long a_lOrgUnitId)
/*     */     throws Bn2Exception
/*     */   {
/*  52 */     HashMap hmFields = new HashMap();
/*     */ 
/*  55 */     Enumeration e = a_request.getParameterNames();
/*  56 */     while (e.hasMoreElements())
/*     */     {
/*  58 */       String sName = (String)e.nextElement();
/*     */ 
/*  60 */       if ((sName.startsWith("field")) || (sName.startsWith("listField")))
/*     */       {
/*  63 */         String[] aIds = sName.split(":");
/*  64 */         long lFieldId = Long.parseLong(aIds[1]);
/*  65 */         CustomField field = (CustomField)a_fieldManager.getCustomField(a_dbTransaction, lFieldId);
/*     */ 
/*  68 */         if ((a_lOrgUnitId <= 0L) || (field.getOrgUnitId() == a_lOrgUnitId))
/*     */         {
/*  70 */           if (field.getIsRequired())
/*     */           {
/*  73 */             if (sName.startsWith("listField"))
/*     */             {
/*  76 */               String sKey = field.getId() + ":" + field.getName();
/*  77 */               if (!hmFields.containsKey(sKey))
/*     */               {
/*  79 */                 hmFields.put(sKey, new Vector());
/*     */               }
/*     */ 
/*     */               try
/*     */               {
/*  84 */                 String sValue = a_request.getParameter(sName);
/*  85 */                 long lId = Long.parseLong(sValue);
/*     */ 
/*  87 */                 if (lId > 0L)
/*     */                 {
/*  89 */                   ((Vector)hmFields.get(sKey)).add(new Long(lId));
/*     */                 }
/*     */               }
/*     */               catch (Exception ex)
/*     */               {
/*     */               }
/*     */ 
/*     */             }
/*  97 */             else if (sName.startsWith("field"))
/*     */             {
/*  99 */               String sValue = a_request.getParameter(sName);
/*     */ 
/* 101 */               if (!StringUtil.stringIsPopulated(sValue))
/*     */               {
/* 103 */                 a_form.addError(field.getName() + " is a required field");
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 112 */     Set keys = hmFields.keySet();
/* 113 */     Iterator i = keys.iterator();
/* 114 */     while (i.hasNext())
/*     */     {
/* 116 */       String sKey = (String)i.next();
/* 117 */       String[] aPair = sKey.split(":");
/* 118 */       Vector vecValues = (Vector)hmFields.get(sKey);
/* 119 */       if ((vecValues == null) || (vecValues.size() <= 0))
/*     */       {
/* 121 */         a_form.addError(aPair[1] + " is a required field");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 126 */     if (a_form.getHasErrors())
/*     */     {
/* 128 */       setValuesForRedirect(a_request, a_fieldManager, a_dbTransaction, a_lUsageTypeId, a_lUserId);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setValuesForRedirect(HttpServletRequest a_request, CustomFieldManager a_fieldManager, DBTransaction a_dbTransaction, long a_lUsageTypeId, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 135 */     CustomFieldSelectedValueSet set = CustomFieldUtil.getValueMappings(a_request, a_fieldManager, a_dbTransaction, a_lUsageTypeId, a_lUserId);
/* 136 */     a_request.setAttribute("valueMapping", set);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.customfield.util.CustomFieldValidationUtil
 * JD-Core Version:    0.6.0
 */