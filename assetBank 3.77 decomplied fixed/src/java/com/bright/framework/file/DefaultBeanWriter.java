/*     */ package com.bright.framework.file;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class DefaultBeanWriter
/*     */   implements BeanWriter
/*     */ {
/*     */   private static final String c_ksClassName = "DefaultFileFormatter";
/*  43 */   private BufferedWriter m_writer = null;
/*  44 */   private FileFormat m_format = null;
/*     */ 
/*     */   public DefaultBeanWriter(BufferedWriter a_fileWriter, FileFormat a_format)
/*     */   {
/*  53 */     this.m_writer = a_fileWriter;
/*  54 */     this.m_format = a_format;
/*     */   }
/*     */ 
/*     */   protected Writer getWriter()
/*     */   {
/*  59 */     return this.m_writer;
/*     */   }
/*     */ 
/*     */   protected FileFormat getFormat()
/*     */   {
/*  64 */     return this.m_format;
/*     */   }
/*     */ 
/*     */   public void writeBeansWithPropertyHeader(Vector a_vec, BeanWrapper a_wrapper)
/*     */     throws Bn2Exception
/*     */   {
/*  88 */     writeBeans(a_vec, a_wrapper, true);
/*     */   }
/*     */ 
/*     */   public void writeBeans(Vector a_vec, BeanWrapper a_wrapper)
/*     */     throws Bn2Exception
/*     */   {
/* 103 */     writeBeans(a_vec, a_wrapper, false);
/*     */   }
/*     */ 
/*     */   public void writeBeans(Vector a_vec, BeanWrapper a_wrapper, boolean a_bWriteHeader)
/*     */     throws Bn2Exception
/*     */   {
/* 122 */     String ksMethodName = "writeObjectsWithHeader";
/* 123 */     Object object = null;
/*     */     try
/*     */     {
/* 127 */       Class cObjectType = null;
/*     */ 
/* 129 */       if (a_wrapper != null)
/*     */       {
/* 131 */         cObjectType = a_wrapper.getClass();
/*     */       }
/*     */       else
/*     */       {
/* 136 */         cObjectType = a_vec.get(0).getClass();
/*     */       }
/*     */ 
/* 140 */       Vector vecMethods = getStringGetters(cObjectType);
/* 141 */       ArrayList vecExtraHeaders = getExtraHeaders();
/*     */ 
/* 143 */       if (a_bWriteHeader)
/*     */       {
/* 146 */         writePropertyNames(vecMethods, vecExtraHeaders);
/*     */       }
/*     */ 
/* 151 */       for (int i = 0; i < a_vec.size(); i++)
/*     */       {
/* 154 */         this.m_writer.write(this.m_format.getRecordDelimiter());
/*     */ 
/* 157 */         object = a_vec.get(i);
/*     */ 
/* 160 */         if (a_wrapper != null)
/*     */         {
/* 162 */           a_wrapper.setObjectToWrap(object);
/* 163 */           object = a_wrapper;
/*     */         }
/*     */ 
/* 167 */         ArrayList vecExtraValues = getExtraValues(object, vecExtraHeaders);
/* 168 */         writePropertyValues(object, vecMethods, vecExtraValues);
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 173 */       throw new Bn2Exception("DefaultFileFormatter.writeObjectsWithHeader - " + e.getMessage(), e);
/*     */     }
/*     */     catch (IllegalAccessException e)
/*     */     {
/* 177 */       throw new Bn2Exception("DefaultFileFormatter.writeObjectsWithHeader - " + e.getMessage(), e);
/*     */     }
/*     */     catch (InvocationTargetException e)
/*     */     {
/* 181 */       throw new Bn2Exception("DefaultFileFormatter.writeObjectsWithHeader - " + e + " calling method on " + object.getClass().getName() + " - " + e.getTargetException(), e.getTargetException());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void writePropertyNames(Vector<Method> a_vecMethods, ArrayList<String> a_extras)
/*     */     throws IOException
/*     */   {
/* 203 */     Method method = null;
/*     */ 
/* 205 */     for (int i = 0; i < a_vecMethods.size(); i++)
/*     */     {
/* 208 */       if (i > 0)
/*     */       {
/* 210 */         this.m_writer.write(this.m_format.getFieldDelimiter());
/*     */       }
/*     */ 
/* 214 */       method = (Method)a_vecMethods.get(i);
/*     */ 
/* 217 */       this.m_writer.write(getPropertyName(method));
/*     */     }
/*     */ 
/* 221 */     if (a_extras != null)
/*     */     {
/* 223 */       for (String header : a_extras)
/*     */       {
/* 225 */         this.m_writer.write(this.m_format.getFieldDelimiter());
/* 226 */         this.m_writer.write(header);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void writePropertyValues(Object a_object, Vector a_vecMethods, ArrayList<String> a_extras)
/*     */     throws IOException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 246 */     Method method = null;
/* 247 */     for (int i = 0; i < a_vecMethods.size(); i++)
/*     */     {
/* 250 */       if (i > 0)
/*     */       {
/* 252 */         this.m_writer.write(this.m_format.getFieldDelimiter());
/*     */       }
/*     */ 
/* 256 */       method = (Method)a_vecMethods.get(i);
/*     */ 
/* 259 */       String sValue = (String)method.invoke(a_object, (Object[])null);
/*     */ 
/* 261 */       if (sValue == null)
/*     */       {
/* 263 */         sValue = "";
/*     */       }
/*     */ 
/* 266 */       sValue = getEscapedValue(sValue);
/*     */ 
/* 269 */       this.m_writer.write(sValue);
/*     */     }
/*     */ 
/* 273 */     if (a_extras != null)
/*     */     {
/* 275 */       for (String sValue : a_extras)
/*     */       {
/* 277 */         this.m_writer.write(this.m_format.getFieldDelimiter());
/* 278 */         this.m_writer.write(sValue);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getEscapedValue(String sValue)
/*     */   {
/* 292 */     sValue = StringUtil.replaceString(sValue, "\t", "   ");
/*     */ 
/* 295 */     if (StringUtils.isNotEmpty(this.m_format.getLiteralDelimiter()))
/*     */     {
/* 297 */       if ((sValue.indexOf('\n') >= 0) || (sValue.indexOf('\r') >= 0))
/*     */       {
/* 299 */         sValue = sValue.replaceAll(this.m_format.getLiteralDelimiter(), this.m_format.getLiteralDelimiterEscapedReplacement());
/* 300 */         sValue = this.m_format.getLiteralDelimiter() + sValue + this.m_format.getLiteralDelimiter();
/*     */       }
/*     */     }
/* 303 */     return sValue;
/*     */   }
/*     */ 
/*     */   protected Vector<Method> getStringGetters(Class a_class)
/*     */   {
/* 320 */     Vector vecMethods = new Vector();
/*     */ 
/* 323 */     Method[] aMethods = a_class.getMethods();
/*     */ 
/* 326 */     if (aMethods != null)
/*     */     {
/* 328 */       for (int i = 0; i < aMethods.length; i++)
/*     */       {
/* 331 */         if ((!aMethods[i].getReturnType().equals(String.class)) || (!aMethods[i].getName().substring(0, 3).equals("get")) || (aMethods[i].getParameterTypes().length != 0))
/*     */         {
/*     */           continue;
/*     */         }
/* 335 */         vecMethods.add(aMethods[i]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 341 */     return vecMethods;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getExtraHeaders()
/*     */     throws Bn2Exception
/*     */   {
/* 354 */     return null;
/*     */   }
/*     */ 
/*     */   protected ArrayList<String> getExtraValues(Object a_object, ArrayList<String> a_alExtraHeaders)
/*     */     throws Bn2Exception
/*     */   {
/* 368 */     return null;
/*     */   }
/*     */ 
/*     */   private String getPropertyName(Method a_method)
/*     */   {
/* 383 */     String sPropertyName = null;
/* 384 */     if (a_method.getName().length() > 3)
/*     */     {
/* 387 */       sPropertyName = a_method.getName().substring(3, a_method.getName().length());
/*     */     }
/*     */ 
/* 390 */     return sPropertyName;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.DefaultBeanWriter
 * JD-Core Version:    0.6.0
 */