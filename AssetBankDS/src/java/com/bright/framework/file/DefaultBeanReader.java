/*     */ package com.bright.framework.file;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class DefaultBeanReader
/*     */   implements BeanReader
/*     */ {
/*  45 */   private BufferedReader m_reader = null;
/*  46 */   private FileFormat m_format = null;
/*  47 */   private Class m_beanClass = null;
/*  48 */   private BeanWrapper m_wrapper = null;
/*  49 */   private int m_iStartLineIndex = 0;
/*  50 */   private List m_vHeaders = null;
/*     */ 
/*  52 */   protected Vector m_vecMissingHeaders = null;
/*  53 */   protected String m_sHeaderLine = null;
/*     */ 
/*  55 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*     */   public DefaultBeanReader(BufferedReader a_reader, FileFormat a_format, Class a_beanClass, BeanWrapper a_wrapper)
/*     */     throws IOException, DefaultBeanReader.NoMatchForColumnHeaderException
/*     */   {
/*  70 */     this(a_reader, a_format, a_beanClass, a_wrapper, 0);
/*     */   }
/*     */ 
/*     */   public DefaultBeanReader(BufferedReader a_reader, FileFormat a_format, Class a_beanClass, BeanWrapper a_wrapper, int a_iStartLineIndex)
/*     */     throws IOException
/*     */   {
/*  87 */     this.m_reader = a_reader;
/*  88 */     this.m_format = a_format;
/*  89 */     this.m_beanClass = a_beanClass;
/*  90 */     this.m_wrapper = a_wrapper;
/*  91 */     this.m_iStartLineIndex = a_iStartLineIndex;
/*     */ 
/*  94 */     for (int i = 0; i < this.m_iStartLineIndex; i++)
/*     */     {
/*  96 */       this.m_reader.readLine();
/*     */     }
/*  98 */     this.m_sHeaderLine = this.m_reader.readLine();
/*     */ 
/* 100 */     processPropertyHeader(this.m_sHeaderLine);
/*     */ 
/* 102 */     if ((this.m_vHeaders == null) || (this.m_vHeaders.size() == 0))
/*     */     {
/* 104 */       throw new IllegalArgumentException("Cannot read any bean property names from the passed FileReader");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processPropertyHeader(String propertyHeader)
/*     */   {
/* 115 */     String[] propertyNames = propertyHeader.split(this.m_format.getFieldDelimiter());
/*     */ 
/* 117 */     Method method = null;
/* 118 */     this.m_vHeaders = new Vector();
/* 119 */     Class beanClass = null;
/*     */ 
/* 121 */     if (this.m_wrapper == null)
/*     */     {
/* 123 */       beanClass = this.m_beanClass;
/*     */     }
/*     */     else
/*     */     {
/* 127 */       beanClass = this.m_wrapper.getClass();
/*     */     }
/*     */ 
/* 131 */     for (int i = 0; i < propertyNames.length; i++)
/*     */     {
/*     */       try
/*     */       {
/* 135 */         String propertyName = propertyNames[i].replace(" ", "");
/* 136 */         method = beanClass.getMethod("set" + propertyName, new Class[] { String.class });
/*     */ 
/* 139 */         if (!method.getReturnType().equals(Void.TYPE))
/*     */         {
/* 141 */           method = null;
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (NoSuchMethodException nsme)
/*     */       {
/* 148 */         method = null;
/*     */       }
/*     */ 
/* 151 */       this.m_vHeaders.add(new BeanPropertyHeader(method, propertyNames[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Reader getReader()
/*     */   {
/* 158 */     return this.m_reader;
/*     */   }
/*     */ 
/*     */   protected FileFormat getFormat()
/*     */   {
/* 163 */     return this.m_format;
/*     */   }
/*     */ 
/*     */   public Vector getMissingHeaders()
/*     */   {
/* 168 */     return this.m_vecMissingHeaders;
/*     */   }
/*     */ 
/*     */   public Vector readBeans()
/*     */     throws Bn2Exception, IOException, InstantiationException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 184 */     return readBeans(2147483647);
/*     */   }
/*     */ 
/*     */   public Vector readBeans(int a_iNumBeansToRead)
/*     */     throws Bn2Exception, IOException, DefaultBeanReader.TooManyColumnsException, DefaultBeanReader.BeanPopulationException
/*     */   {
/* 202 */     int iNumBeansRead = 0;
/* 203 */     Vector vBeans = new Vector();
/*     */ 
/* 206 */     while ((this.m_reader.ready()) && (iNumBeansRead++ < a_iNumBeansToRead))
/*     */     {
/* 208 */       Object bean = readBean();
/* 209 */       if (bean != null)
/*     */       {
/* 211 */         vBeans.add(bean);
/*     */       }
/*     */     }
/*     */ 
/* 215 */     return vBeans;
/*     */   }
/*     */ 
/*     */   public Object readBean()
/*     */     throws IOException, DefaultBeanReader.TooManyColumnsException, DefaultBeanReader.BeanPopulationException
/*     */   {
/* 230 */     String sLine = null;
/* 231 */     Object bean = null;
/*     */ 
/* 233 */     sLine = readLine(this.m_reader);
/*     */ 
/* 235 */     if (StringUtils.isNotEmpty(sLine))
/*     */     {
/* 237 */       String[] asValues = sLine.split(this.m_format.getFieldDelimiter());
/* 238 */       if (asValues.length > this.m_vHeaders.size())
/*     */       {
/* 240 */         throw new TooManyColumnsException(sLine);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 245 */         bean = this.m_beanClass.newInstance();
/*     */       }
/*     */       catch (InstantiationException e)
/*     */       {
/* 249 */         this.m_logger.error("DefaultBeanReader.readBean() : InstantiationException whilst creating bean to read into : ", e);
/* 250 */         return null;
/*     */       }
/*     */       catch (IllegalAccessException e)
/*     */       {
/* 254 */         this.m_logger.error("DefaultBeanReader.readBean() : IllegalAccessException whilst creating bean to read into : ", e);
/* 255 */         return null;
/*     */       }
/*     */ 
/* 259 */       if (this.m_wrapper != null)
/*     */       {
/* 261 */         this.m_wrapper.setObjectToWrap(bean);
/* 262 */         bean = this.m_wrapper;
/*     */       }
/*     */ 
/* 266 */       for (int i = 0; (i < this.m_vHeaders.size()) && (i < asValues.length); i++)
/*     */       {
/*     */         try
/*     */         {
/* 272 */           String sValue = asValues[i].trim();
/* 273 */           populateBean((BeanPropertyHeader)this.m_vHeaders.get(i), sValue, bean);
/*     */         }
/*     */         catch (IllegalAccessException e)
/*     */         {
/* 277 */           this.m_logger.error("DefaultBeanReader.readBean() : IllegalAccessException whilst populating bean : ", e);
/* 278 */           return null;
/*     */         }
/*     */         catch (InvocationTargetException e)
/*     */         {
/* 282 */           this.m_logger.error("DefaultBeanReader.readBean() : InvocationTargetException whilst populating bean : ", e);
/* 283 */           return null;
/*     */         }
/*     */         catch (Bn2Exception e)
/*     */         {
/* 287 */           this.m_logger.error("DefaultBeanReader.readBean() : Bn2Exception whilst populating bean : ", e);
/* 288 */           return null;
/*     */         }
/*     */         catch (IllegalArgumentException e)
/*     */         {
/* 292 */           throw new BeanPopulationException("DefaultBeanReader.readBean() : Header '" + ((BeanPropertyHeader)this.m_vHeaders.get(i)).getHeaderName() + "' could not be populated with value '" + asValues[i] + "'", e);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 299 */       if (this.m_wrapper != null)
/*     */       {
/* 301 */         bean = this.m_wrapper.getWrappedObject();
/*     */       }
/*     */     }
/* 304 */     return bean;
/*     */   }
/*     */ 
/*     */   private String readLine(BufferedReader a_reader)
/*     */     throws IOException
/*     */   {
/* 321 */     StringBuffer line = new StringBuffer();
/* 322 */     int c = -1;
/* 323 */     int previous = -1;
/* 324 */     boolean bInDelimitedSection = false;
/* 325 */     boolean firstChar = true;
/*     */ 
/* 327 */     while ((c = a_reader.read()) != -1)
/*     */     {
/* 330 */       if (firstChar)
/*     */       {
/* 332 */         if ((c == 10) || (c == 13)) {
/*     */           continue;
/*     */         }
/* 335 */         firstChar = false;
/*     */       }
/*     */ 
/* 339 */       if (c == 34)
/*     */       {
/* 342 */         if (!bInDelimitedSection)
/*     */         {
/* 344 */           bInDelimitedSection = true;
/* 345 */           previous = -1;
/* 346 */           continue;
/*     */         }
/*     */ 
/* 349 */         if (previous != 34)
/*     */         {
/* 351 */           previous = c;
/* 352 */           continue;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 357 */       if ((bInDelimitedSection) && (previous == 34) && (c != 34))
/*     */       {
/* 359 */         bInDelimitedSection = false;
/*     */       }
/*     */ 
/* 363 */       if ((!bInDelimitedSection) && ((c == 10) || (c == 13)))
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/* 369 */       if ((bInDelimitedSection) && (c == 9))
/*     */       {
/* 371 */         line.append("   ");
/* 372 */         previous = c; continue;
/*     */       }
/*     */ 
/* 377 */       line.append((char)c);
/*     */ 
/* 380 */       if (c == 34)
/*     */       {
/* 382 */         previous = -1; continue;
/*     */       }
/*     */ 
/* 386 */       previous = c;
/*     */     }
/*     */ 
/* 391 */     return line.toString();
/*     */   }
/*     */ 
/*     */   protected void populateBean(BeanPropertyHeader a_header, String a_sValue, Object a_bean)
/*     */     throws InvocationTargetException, IllegalAccessException, IllegalArgumentException, Bn2Exception
/*     */   {
/* 405 */     if (a_header.getMethod() != null)
/*     */     {
/* 407 */       a_header.getMethod().invoke(a_bean, new Object[] { a_sValue });
/*     */     }
/*     */     else
/*     */     {
/* 411 */       setExtraValue(a_bean, a_header, a_sValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setExtraValue(Object a_bean, BeanPropertyHeader a_header, String a_sValue)
/*     */     throws Bn2Exception
/*     */   {
/*     */   }
/*     */ 
/*     */   public static class BeanPopulationException extends Bn2Exception
/*     */   {
/*     */     public BeanPopulationException(String a_sMessage)
/*     */     {
/* 512 */       super();
/*     */     }
/*     */ 
/*     */     public BeanPopulationException(String a_sMessage, Throwable a_cause)
/*     */     {
/* 517 */       super(a_cause);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class TooManyColumnsException extends Bn2Exception
/*     */   {
/* 488 */     private String m_sRow = null;
/*     */ 
/*     */     public TooManyColumnsException(String a_sRow)
/*     */     {
/* 492 */       super();
/* 493 */       this.m_sRow = a_sRow;
/*     */     }
/*     */ 
/*     */     public String getRow()
/*     */     {
/* 498 */       return this.m_sRow;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class NoMatchForColumnHeaderException extends Bn2Exception
/*     */   {
/* 465 */     private String m_sHeader = null;
/*     */ 
/*     */     public NoMatchForColumnHeaderException(String a_sHeaderColumn)
/*     */     {
/* 469 */       super();
/* 470 */       this.m_sHeader = a_sHeaderColumn;
/*     */     }
/*     */ 
/*     */     public String getHeader()
/*     */     {
/* 475 */       return this.m_sHeader;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class BeanPropertyHeader
/*     */   {
/* 432 */     private Method m_method = null;
/* 433 */     private String m_headerName = null;
/*     */ 
/*     */     public BeanPropertyHeader(Method a_sMethod, String a_sHeaderName)
/*     */     {
/* 442 */       this.m_method = a_sMethod;
/* 443 */       this.m_headerName = a_sHeaderName;
/*     */     }
/*     */ 
/*     */     public String getHeaderName()
/*     */     {
/* 448 */       return this.m_headerName;
/*     */     }
/*     */ 
/*     */     public Method getMethod() {
/* 452 */       return this.m_method;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.file.DefaultBeanReader
 * JD-Core Version:    0.6.0
 */