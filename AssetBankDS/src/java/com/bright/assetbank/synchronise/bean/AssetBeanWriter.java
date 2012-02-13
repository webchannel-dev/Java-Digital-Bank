/*     */ package com.bright.assetbank.synchronise.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.attribute.bean.AttributeInList;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.framework.common.bean.StringDataBean;
/*     */ import com.bright.framework.file.DefaultBeanWriter;
/*     */ import com.bright.framework.file.FileFormat;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ public class AssetBeanWriter extends DefaultBeanWriter
/*     */ {
/*     */   public static final String k_sAttributeNamePrefix = "att:";
/*     */   public static final String k_sAttributeIdDelimiter = ":";
/*     */   public static final String k_sLanguageCodePrefix = "lang_";
/*  52 */   private Vector m_vAttributes = null;
/*  53 */   private Vector m_vAttributeProperties = null;
/*  54 */   private List m_languages = null;
/*  55 */   private boolean m_bWriteAllLanguages = false;
/*     */ 
/*     */   public AssetBeanWriter(BufferedWriter a_fileWriter, FileFormat a_format, Vector a_vAttributes)
/*     */   {
/*  64 */     super(a_fileWriter, a_format);
/*  65 */     this.m_vAttributes = a_vAttributes;
/*  66 */     initialiseComponents();
/*     */   }
/*     */ 
/*     */   private void initialiseComponents()
/*     */   {
/*  71 */     if (AssetBankSettings.getSupportMultiLanguage())
/*     */     {
/*     */       try
/*     */       {
/*  75 */         LanguageManager m_languageManager = (LanguageManager)GlobalApplication.getInstance().getComponentManager().lookup("LanguageManager");
/*     */ 
/*  78 */         this.m_languages = m_languageManager.getLanguages(null, false, true);
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/*  82 */         throw new RuntimeException("AssetBeanWriter.initialiseComponents() : Couldn't find component", ce);
/*     */       }
/*     */       catch (Bn2Exception e)
/*     */       {
/*  86 */         throw new RuntimeException("AssetBeanWriter.initialiseComponents() : Couldn't load languages", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeHeader(BufferedWriter a_writer, String a_sIdentifier, String a_sName, String a_sDescription, String a_sAssetFilenamePattern, FileFormat a_format)
/*     */     throws IOException
/*     */   {
/* 104 */     String sHeader = a_sIdentifier + a_format.getFieldDelimiter();
/* 105 */     sHeader = sHeader + a_sName;
/* 106 */     sHeader = sHeader + a_format.getFieldDelimiter();
/* 107 */     if (a_sDescription != null)
/*     */     {
/* 109 */       sHeader = sHeader + a_sDescription;
/*     */     }
/* 111 */     sHeader = sHeader + a_format.getFieldDelimiter();
/* 112 */     if (a_sAssetFilenamePattern != null)
/*     */     {
/* 114 */       sHeader = sHeader + a_sAssetFilenamePattern;
/*     */     }
/* 116 */     a_writer.write(sHeader + a_format.getRecordDelimiter());
/*     */   }
/*     */ 
/*     */   protected void writePropertyNames(Vector<Method> a_vecMethods, ArrayList<String> a_extras)
/*     */     throws IOException
/*     */   {
/* 132 */     writePropertyNames(a_vecMethods, a_extras, true);
/*     */   }
/*     */ 
/*     */   protected void writePropertyNames(Vector a_vecMethods, ArrayList<String> a_extras, boolean a_bIncludeId)
/*     */     throws IOException
/*     */   {
/* 140 */     super.writePropertyNames(a_vecMethods, null);
/*     */ 
/* 142 */     this.m_vAttributeProperties = new Vector(this.m_vAttributes.size());
/*     */     String sAttHeading;
/*     */     Iterator iLangs;
/* 145 */     for (int i = 0; i < this.m_vAttributes.size(); i++)
/*     */     {
/* 147 */       getWriter().write(getFormat().getFieldDelimiter());
/* 148 */       StringDataBean att = (StringDataBean)this.m_vAttributes.get(i);
/*     */ 
/* 150 */       sAttHeading = att.getName();
/*     */ 
/* 152 */       if (a_bIncludeId)
/*     */       {
/* 154 */         sAttHeading = sAttHeading + ":" + att.getId();
/*     */       }
/*     */ 
/* 157 */       getWriter().write("att:" + sAttHeading);
/*     */ 
/* 159 */       this.m_vAttributeProperties.add(new Long(att.getId()));
/*     */ 
/* 163 */       if ((!this.m_bWriteAllLanguages) || (this.m_languages == null) || (!(att instanceof AttributeInList)) || (!((AttributeInList)att).getIsTranslatable()))
/*     */         continue;
/* 165 */       for (iLangs = this.m_languages.iterator(); iLangs.hasNext(); )
/*     */       {
/* 167 */         Language language = (Language)iLangs.next();
/* 168 */         getWriter().write(getFormat().getFieldDelimiter());
/* 169 */         getWriter().write("att:lang_" + language.getCode() + ":" + sAttHeading);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void writePropertyValues(Object a_object, Vector a_vecMethods, ArrayList<String> a_extras)
/*     */     throws IOException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 181 */     super.writePropertyValues(a_object, a_vecMethods, a_extras);
/*     */ 
/* 183 */     ExportAsset asset = (ExportAsset)a_object;
/*     */     long lAttId;
/*     */     Iterator iLangs;
/* 186 */     for (int i = 0; i < this.m_vAttributeProperties.size(); i++)
/*     */     {
/* 188 */       getWriter().write(getFormat().getFieldDelimiter());
/* 189 */       lAttId = ((Long)this.m_vAttributeProperties.get(i)).longValue();
/* 190 */       String sValue = asset.getAttributeValue(lAttId);
/* 191 */       if (sValue != null)
/*     */       {
/* 193 */         getWriter().write(getEscapedValue(sValue));
/*     */       }
/*     */ 
/* 197 */       if ((!this.m_bWriteAllLanguages) || (this.m_languages == null) || (!asset.isAttributeTranslatable(lAttId)))
/*     */         continue;
/* 199 */       for (iLangs = this.m_languages.iterator(); iLangs.hasNext(); )
/*     */       {
/* 201 */         Language language = (Language)iLangs.next();
/* 202 */         getWriter().write(getFormat().getFieldDelimiter());
/* 203 */         String sTranslatedValue = asset.getAttributeValueTranslation(lAttId, language);
/* 204 */         getWriter().write(getEscapedValue(sTranslatedValue));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean getWriteAllLanguages()
/*     */   {
/* 212 */     return this.m_bWriteAllLanguages;
/*     */   }
/*     */ 
/*     */   public void setWriteAllLanguages(boolean allLanguages)
/*     */   {
/* 217 */     this.m_bWriteAllLanguages = allLanguages;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.AssetBeanWriter
 * JD-Core Version:    0.6.0
 */