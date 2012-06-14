/*     */ package com.bright.assetbank.attribute.plugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.framework.common.bean.IdValueBean;
/*     */ import com.bright.framework.util.BrightDateFormat;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public abstract class BeanLookupPlugin
/*     */   implements DataLookupPlugin
/*     */ {
/*     */   public static final String c_ksClassName = "BeanLookupPlugin";
/*     */ 
/*     */   public Vector<IdValueBean> getAttributeValueMap(String a_sRequestParams)
/*     */     throws Bn2Exception
/*     */   {
/*  50 */     String ksMethodName = "getAttributeValueMap";
/*  51 */     Vector vecValues = new Vector();
/*     */     try
/*     */     {
/*  55 */       Object m_objBean = getBean(a_sRequestParams);
/*     */ 
/*  57 */       if (m_objBean != null)
/*     */       {
/*  59 */         GlobalApplication.getInstance().getLogger().debug("BeanLookupPlugin.getAttributeValueMap: Got bean for update");
/*     */ 
/*  61 */         String sMappings = getAttributeMappings();
/*  62 */         String[] aPairs = sMappings.split(getAttributeMappingsDelimiter());
/*     */ 
/*  64 */         GlobalApplication.getInstance().getLogger().debug("BeanLookupPlugin.getAttributeValueMap: Attribute mappings retreived: " + sMappings);
/*     */ 
/*  68 */         Class objClass = m_objBean.getClass();
/*     */ 
/*  70 */         GlobalApplication.getInstance().getLogger().debug("BeanLookupPlugin.getAttributeValueMap: Bean is of class: " + objClass.getName());
/*     */ 
/*  72 */         Method[] methods = objClass.getDeclaredMethods();
/*  73 */         for (String sPair : aPairs)
/*     */         {
/*  75 */           String[] aPair = sPair.split(getAttributeMappingDelimiter());
/*  76 */           long lAttributeId = Long.parseLong(aPair[0]);
/*  77 */           String sMethodName = "get" + aPair[1];
/*     */ 
/*  79 */           GlobalApplication.getInstance().getLogger().debug("BeanLookupPlugin.getAttributeValueMap: Pair: " + aPair[0] + " | " + aPair[1]);
/*     */ 
/*  82 */           Method meth = getMethodFromList(methods, sMethodName);
/*     */ 
/*  84 */           GlobalApplication.getInstance().getLogger().debug("BeanLookupPlugin.getAttributeValueMap: Got method");
/*     */ 
/*  86 */           Object objResponse = meth.invoke(m_objBean, (Object[])null);
/*     */ 
/*  88 */           GlobalApplication.getInstance().getLogger().debug("BeanLookupPlugin.getAttributeValueMap: Invoked method");
/*     */ 
/*  90 */           String sResponse = getStringValueFromObject(objResponse);
/*     */ 
/*  92 */           GlobalApplication.getInstance().getLogger().debug("BeanLookupPlugin.getAttributeValueMap: Got method response as string: " + sResponse);
/*     */ 
/*  94 */           IdValueBean bean = new IdValueBean();
/*  95 */           bean.setId(lAttributeId);
/*  96 */           bean.setValue(sResponse);
/*     */ 
/*  98 */           GlobalApplication.getInstance().getLogger().debug("BeanLookupPlugin.getAttributeValueMap: Bean: Id:" + bean.getId() + " Value:" + bean.getValue());
/*     */ 
/* 100 */           vecValues.add(bean);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 106 */       String sMessage = "BeanLookupPlugin.getAttributeValueMap: Error looking up data: " + e.getMessage();
/* 107 */       GlobalApplication.getInstance().getLogger().error(sMessage);
/* 108 */       throw new Bn2Exception(sMessage, e);
/*     */     }
/*     */ 
/* 122 */     return vecValues;
/*     */   }
/*     */ 
/*     */   protected abstract String getAttributeMappings();
/*     */ 
/*     */   protected abstract String getAttributeMappingsDelimiter();
/*     */ 
/*     */   protected abstract String getAttributeMappingDelimiter();
/*     */ 
/*     */   protected abstract Object getBean(String paramString)
/*     */     throws Exception;
/*     */ 
/*     */   private Method getMethodFromList(Method[] a_aMethods, String a_sMethodName)
/*     */   {
/* 158 */     if (a_aMethods != null)
/*     */     {
/* 160 */       for (Method method : a_aMethods)
/*     */       {
/* 162 */         if (method.getName().equals(a_sMethodName))
/*     */         {
/* 164 */           return method;
/*     */         }
/*     */       }
/*     */     }
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */   private String getStringValueFromObject(Object a_objResponse)
/*     */   {
/* 186 */     if ((a_objResponse instanceof String))
/*     */     {
/* 188 */       return (String)a_objResponse;
/*     */     }
/* 190 */     if ((a_objResponse instanceof Integer))
/*     */     {
/* 192 */       return String.valueOf((Integer)a_objResponse);
/*     */     }
/* 194 */     if ((a_objResponse instanceof Long))
/*     */     {
/* 196 */       return String.valueOf((Long)a_objResponse);
/*     */     }
/* 198 */     if ((a_objResponse instanceof Calendar))
/*     */     {
/* 200 */       Calendar cal = (Calendar)a_objResponse;
/* 201 */       Date dtDate = cal.getTime();
/*     */ 
/* 204 */       BrightDateFormat format = AssetBankSettings.getStandardDateFormat();
/* 205 */       String sDate = format.format(dtDate);
/* 206 */       return sDate;
/*     */     }
/* 208 */     return a_objResponse.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.plugin.BeanLookupPlugin
 * JD-Core Version:    0.6.0
 */