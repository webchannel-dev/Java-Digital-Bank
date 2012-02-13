/*     */ package com.bright.framework.customfield.util;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.customfield.bean.CustomField;
/*     */ import com.bright.framework.customfield.bean.CustomFieldSelectedValueSet;
/*     */ import com.bright.framework.customfield.bean.CustomFieldValue;
/*     */ import com.bright.framework.customfield.bean.CustomFieldValueHolder;
/*     */ import com.bright.framework.customfield.bean.CustomFieldValueMapping;
/*     */ import com.bright.framework.customfield.constant.CustomFieldConstants;
/*     */ import com.bright.framework.customfield.form.CustomFieldHolder;
/*     */ import com.bright.framework.customfield.service.CustomFieldManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class CustomFieldUtil
/*     */   implements CustomFieldConstants
/*     */ {
/*     */   public static void getAndSaveFieldValues(HttpServletRequest a_request, CustomFieldManager a_fieldManager, DBTransaction a_dbTransaction, long a_lUsageTypeId, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/*  67 */     CustomFieldSelectedValueSet set = getValueMappings(a_request, a_fieldManager, a_dbTransaction, a_lUsageTypeId, a_lUserId);
/*     */ 
/*  70 */     a_fieldManager.saveValueMappings(a_dbTransaction, set);
/*     */   }
/*     */ 
/*     */   public static CustomFieldSelectedValueSet getValueMappings(HttpServletRequest a_request, CustomFieldManager a_fieldManager, DBTransaction a_dbTransaction, long a_lUsageTypeId, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/*  88 */     CustomFieldSelectedValueSet set = new CustomFieldSelectedValueSet();
/*  89 */     Vector vecSelectedValues = new Vector();
/*  90 */     HashMap hmFields = new HashMap();
/*     */ 
/*  93 */     Enumeration e = a_request.getParameterNames();
/*  94 */     while (e.hasMoreElements())
/*     */     {
/*  96 */       String sName = (String)e.nextElement();
/*     */ 
/*  98 */       if ((sName.startsWith("field")) || (sName.startsWith("listField")))
/*     */       {
/* 101 */         String[] aIds = sName.split(":");
/* 102 */         long lFieldId = Long.parseLong(aIds[1]);
/* 103 */         CustomField field = a_fieldManager.getCustomField(a_dbTransaction, lFieldId);
/*     */ 
/* 106 */         if (sName.startsWith("listField"))
/*     */         {
/*     */           CustomFieldValueMapping mapping;
/*     */           //CustomFieldValueMapping mapping;
/* 110 */           if (hmFields.containsKey(new Long(lFieldId)))
/*     */           {
/* 112 */             mapping = (CustomFieldValueMapping)hmFields.get(new Long(lFieldId));
/*     */           }
/*     */           else
/*     */           {
/* 116 */             mapping = new CustomFieldValueMapping();
/* 117 */             mapping.setCustomField(field);
/* 118 */             mapping.setItemId(a_lUserId);
/* 119 */             mapping.setListValues(new Vector());
/*     */           }
/*     */ 
/*     */           try
/*     */           {
/* 125 */             CustomFieldValue value = new CustomFieldValue();
/* 126 */             value.setId(Long.parseLong(a_request.getParameter(sName)));
/* 127 */             mapping.getListValues().add(value);
/* 128 */             hmFields.put(new Long(lFieldId), mapping);
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/*     */           }
/*     */ 
/*     */         }
/* 135 */         else if (sName.startsWith("field"))
/*     */         {
/* 137 */           String sValue = StringUtil.xssProcess(a_request.getParameter(sName));
/*     */ 
/* 140 */           CustomFieldValueMapping mapping = new CustomFieldValueMapping();
/* 141 */           mapping.setCustomField(field);
/* 142 */           mapping.setItemId(a_lUserId);
/* 143 */           mapping.setTextValue(sValue);
/* 144 */           vecSelectedValues.add(mapping);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 150 */     Set s = hmFields.keySet();
/* 151 */     Iterator i = s.iterator();
/* 152 */     while (i.hasNext())
/*     */     {
/* 154 */       vecSelectedValues.add(hmFields.get(i.next()));
/*     */     }
/*     */ 
/* 158 */     set.setUsageTypeId(a_lUsageTypeId);
/* 159 */     set.setSelectedValues(vecSelectedValues);
/* 160 */     set.setItemId(a_lUserId);
/*     */ 
/* 162 */     return set;
/*     */   }
/*     */ 
/*     */   public static void prepCustomFields(HttpServletRequest a_request, CustomFieldHolder a_holder, CustomFieldValueHolder a_valueHolder, CustomFieldManager a_fieldManager, DBTransaction a_transaction, long a_lUsageTypeId, User a_userViewingFields)
/*     */     throws Bn2Exception
/*     */   {
/* 181 */     ArrayList colValueHolders = new ArrayList();
/* 182 */     colValueHolders.add(a_valueHolder);
/* 183 */     prepCustomFields(a_request, a_holder, colValueHolders, a_fieldManager, a_transaction, a_lUsageTypeId, a_userViewingFields);
/*     */   }
/*     */ 
/*     */   public static void prepCustomFields(HttpServletRequest a_request, CustomFieldHolder a_holder, List<? extends CustomFieldValueHolder> a_colValueHolders, CustomFieldManager a_fieldManager, DBTransaction a_transaction, long a_lUsageTypeId, User a_userViewingFields)
/*     */     throws Bn2Exception
/*     */   {
/* 204 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/* 205 */     Vector vecFields = a_fieldManager.getCustomFields(a_transaction, -1L, a_lUsageTypeId, a_userViewingFields);
/* 206 */     LanguageUtils.setLanguageOnAll(vecFields, userProfile.getCurrentLanguage());
/*     */ 
/* 209 */     a_holder.setCustomFields(vecFields);
/*     */ 
/* 212 */     CustomFieldSelectedValueSet set = null;
/*     */ 
/* 217 */     if ((a_colValueHolders.size() == 1) && (a_request.getAttribute("valueMapping") != null))
/*     */     {
/* 219 */       set = (CustomFieldSelectedValueSet)a_request.getAttribute("valueMapping");
/* 220 */       ((CustomFieldValueHolder)a_colValueHolders.get(0)).setCustomFieldValues(set);
/*     */     }
/*     */     else
/*     */     {
/* 225 */       for (CustomFieldValueHolder holder : a_colValueHolders)
/*     */       {
/* 228 */         if (holder.getItemId() > 0L)
/*     */         {
/* 230 */           set = a_fieldManager.getValueMappings(a_transaction, holder.getItemId(), a_lUsageTypeId);
/* 231 */           holder.setCustomFieldValues(set);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HashMap<String, String> getEmailTemplateParams(HttpServletRequest a_request, CustomFieldManager m_fieldManager, DBTransaction a_dbTransaction, long a_lCustomFieldType, long a_lUserId)
/*     */     throws Bn2Exception
/*     */   {
/* 246 */     HashMap params = new HashMap();
/*     */ 
/* 248 */     CustomFieldSelectedValueSet customFields = m_fieldManager.getValueMappings(a_dbTransaction, a_lUserId, a_lCustomFieldType);
/* 249 */     Vector customFieldValues = customFields.getSelectedValues();
/*     */ 
/* 252 */     Iterator itCustomFieldValues = customFieldValues.iterator();
/*     */ 
/* 254 */     while (itCustomFieldValues.hasNext())
/*     */     {
/* 256 */       CustomFieldValueMapping customFieldValueMapping = (CustomFieldValueMapping)itCustomFieldValues.next();
/*     */ 
/* 258 */       String sName = customFieldValueMapping.getCustomField().getName();
/* 259 */       String sValue = customFieldValueMapping.getTextValue();
/*     */ 
/* 262 */       if (sValue == null)
/*     */       {
/* 264 */         if ((customFieldValueMapping.getListValues() != null) && (!customFieldValueMapping.getListValues().isEmpty()))
/*     */         {
/* 267 */           sValue = ((CustomFieldValue)customFieldValueMapping.getListValues().firstElement()).getValue();
/*     */         }
/*     */       }
/*     */ 
/* 271 */       params.put(sName, sValue);
/*     */     }
/*     */ 
/* 274 */     return params;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.customfield.util.CustomFieldUtil
 * JD-Core Version:    0.6.0
 */