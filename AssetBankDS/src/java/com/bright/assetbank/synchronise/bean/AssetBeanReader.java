/*     */ package com.bright.assetbank.synchronise.bean;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.synchronise.util.SynchUtil;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.framework.file.BeanWrapper;
/*     */ import com.bright.framework.file.DefaultBeanReader;
/*     */ //import com.bright.framework.file.DefaultBeanReader;
/*     */ import com.bright.framework.file.DefaultBeanReader.NoMatchForColumnHeaderException;
/*     */ import com.bright.framework.file.FileFormat;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ public class AssetBeanReader extends DefaultBeanReader
/*     */ {
/*     */   public static final String k_sAttributeNamePrefix = "att:";
/*     */   public static final String k_sParentAttributeNamePrefix = "parent:";
/*     */   public static final String k_sPeerAttributeNamePrefix = "peer:";
/*     */   public static final String k_sChildAttributeNamePrefix = "child:";
/*     */   private boolean m_bCheckOnly;
/*     */   private AttributeManager m_attManager;
/*     */   private AttributeValueManager m_attValueManager;
/*     */   private TaxonomyManager m_taxonomyManager;
/*     */   private LanguageManager m_languageManager;
/*     */   private HashMap m_languageMap;
/*  69 */   private HashMap m_hmAttributeHeaders = null;
/*  70 */   private HashMap m_hmParentAttributeHeaders = null;
/*  71 */   private HashMap m_hmChildAttributeHeaders = null;
/*  72 */   private HashMap m_hmPeerAttributeHeaders = null;
/*     */ 
/*     */   public AssetBeanReader(BufferedReader a_reader, FileFormat a_format, Class a_beanClass, BeanWrapper a_wrapper, int a_iStartLineIndex, boolean a_bCheckOnly)
/*     */     throws IOException, DefaultBeanReader.NoMatchForColumnHeaderException
/*     */   {
/*  91 */     super(a_reader, a_format, a_beanClass, a_wrapper, a_iStartLineIndex);
/*  92 */     this.m_bCheckOnly = a_bCheckOnly;
/*  93 */     initialiseComponents();
/*     */ 
/*  96 */     processHeaderAttributes();
/*     */   }
/*     */ 
/*     */   public AssetBeanReader(BufferedReader a_reader, FileFormat a_format, Class a_beanClass, BeanWrapper a_wrapper)
/*     */     throws IOException, DefaultBeanReader.NoMatchForColumnHeaderException
/*     */   {
/* 112 */     super(a_reader, a_format, a_beanClass, a_wrapper);
/* 113 */     initialiseComponents();
/*     */   }
/*     */ 
/*     */   private void initialiseComponents()
/*     */   {
/*     */     try
/*     */     {
/* 120 */       this.m_taxonomyManager = ((TaxonomyManager)GlobalApplication.getInstance().getComponentManager().lookup("TaxonomyManager"));
/*     */ 
/* 123 */       this.m_attManager = ((AttributeManager)GlobalApplication.getInstance().getComponentManager().lookup("AttributeManager"));
/*     */ 
/* 126 */       this.m_attValueManager = ((AttributeValueManager)GlobalApplication.getInstance().getComponentManager().lookup("AttributeValueManager"));
/*     */ 
/* 129 */       this.m_languageManager = ((LanguageManager)GlobalApplication.getInstance().getComponentManager().lookup("LanguageManager"));
/*     */ 
/* 132 */       this.m_languageMap = new HashMap();
/* 133 */       List languages = this.m_languageManager.getLanguages(null, true, true);
/*     */ 
/* 135 */       for (Iterator iter = languages.iterator(); iter.hasNext(); )
/*     */       {
/* 137 */         Language language = (Language)iter.next();
/* 138 */         this.m_languageMap.put(language.getCode(), language);
/*     */       }
/*     */     }
/*     */     catch (ComponentException ce)
/*     */     {
/*     */       Iterator iter;
/* 143 */       throw new RuntimeException("AssetBeanReader.processPropertyHeader() : Couldn't find component AttributeManager", ce);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 147 */       throw new RuntimeException("AssetBeanReader.processPropertyHeader() : Couldn't load languages", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processHeaderAttributes()
/*     */   {
/* 159 */     Map hmAttributes = SynchUtil.buildAvailableAttributeMap(this.m_attManager);
/*     */ 
/* 162 */     this.m_hmAttributeHeaders = new HashMap(20);
/* 163 */     this.m_hmParentAttributeHeaders = new HashMap(5);
/* 164 */     this.m_hmChildAttributeHeaders = new HashMap(5);
/* 165 */     this.m_hmPeerAttributeHeaders = new HashMap(5);
/*     */ 
/* 167 */     String[] propertyNames = this.m_sHeaderLine.split(getFormat().getFieldDelimiter());
/*     */ 
/* 170 */     for (int i = 0; i < propertyNames.length; i++)
/*     */     {
/* 172 */       String sCol = propertyNames[i];
/*     */ 
/* 175 */       checkForPrefix(sCol, "att:", hmAttributes, this.m_hmAttributeHeaders, this.m_vecMissingHeaders);
/*     */ 
/* 178 */       checkForPrefix(sCol, "parent:", hmAttributes, this.m_hmParentAttributeHeaders, this.m_vecMissingHeaders);
/* 179 */       checkForPrefix(sCol, "child:", hmAttributes, this.m_hmChildAttributeHeaders, this.m_vecMissingHeaders);
/* 180 */       checkForPrefix(sCol, "peer:", hmAttributes, this.m_hmPeerAttributeHeaders, this.m_vecMissingHeaders);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkForPrefix(String sCol, String a_sPrefix, Map a_hmAttributes, HashMap a_hmHeaders, Vector a_vecMissingColumns)
/*     */   {
/* 196 */     if (sCol.startsWith(a_sPrefix))
/*     */     {
/* 198 */       String sAttr = sCol.replace(a_sPrefix, "");
/*     */ 
/* 201 */       if (sAttr.startsWith("lang_"))
/*     */       {
/* 203 */         sAttr = sAttr.replaceFirst("lang_[a-zA-Z]+:", "");
/*     */       }
/*     */ 
/* 206 */       int iIndex = sAttr.indexOf(":");
/*     */ 
/* 209 */       String sAttName = iIndex >= 0 ? sAttr.substring(0, iIndex) : sAttr;
/* 210 */       Long lAttId = null;
/*     */       try
/*     */       {
/* 213 */         lAttId = Long.valueOf(sAttr.substring(iIndex + 1));
/*     */       }
/*     */       catch (NumberFormatException nfe)
/*     */       {
/*     */       }
/*     */ 
/* 221 */       if ((lAttId != null) && (a_hmAttributes.containsKey(lAttId)))
/*     */       {
/* 223 */         a_hmHeaders.put(sCol, a_hmAttributes.get(lAttId));
/*     */       }
/* 225 */       else if (a_hmAttributes.containsKey(sAttName))
/*     */       {
/* 227 */         a_hmHeaders.put(sCol, a_hmAttributes.get(sAttName));
/*     */       }
/*     */       else
/*     */       {
/* 232 */         if (a_vecMissingColumns == null)
/*     */         {
/* 234 */           a_vecMissingColumns = new Vector();
/*     */         }
/* 236 */         a_vecMissingColumns.add(sCol);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void populateBean(DefaultBeanReader.BeanPropertyHeader a_header, String a_sValue, Object a_bean)
/*     */     throws InvocationTargetException, IllegalAccessException, IllegalArgumentException, Bn2Exception
/*     */   {
/* 256 */     ImportAsset asset = (ImportAsset)a_bean;
/*     */ 
/* 260 */     String sHeader = a_header.getHeaderName();
/* 261 */     if (this.m_hmAttributeHeaders.containsKey(sHeader))
/*     */     {
/* 263 */       Attribute attribute = (Attribute)this.m_hmAttributeHeaders.get(sHeader);
/*     */ 
/* 265 */       String sVal = sHeader.substring("att:".length());
/*     */ 
/* 269 */       if ((sVal.startsWith("lang_")) && (attribute.getIsTranslatable()))
/*     */       {
/* 271 */         String sLanguageCode = sVal.substring("lang_".length(), sVal.indexOf(":"));
/*     */ 
/* 273 */         Language language = (Language)this.m_languageMap.get(sLanguageCode);
/*     */ 
/* 275 */         if (language != null)
/*     */         {
/* 277 */           SynchUtil.setAssetAttributeValueTranslation(this.m_attManager, this.m_taxonomyManager, asset.getAsset(), attribute, a_sValue, language, this.m_bCheckOnly);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 282 */         SynchUtil.setAssetAttributeValue(this.m_attValueManager, this.m_taxonomyManager, asset.getAsset(), attribute, a_sValue, this.m_bCheckOnly);
/*     */       }
/*     */     }
/* 285 */     else if (this.m_hmParentAttributeHeaders.containsKey(sHeader))
/*     */     {
/* 288 */       Attribute attribute = (Attribute)this.m_hmParentAttributeHeaders.get(sHeader);
/* 289 */       SynchUtil.addAssetParents(this.m_attValueManager, asset.getAsset(), attribute, a_sValue);
/*     */     }
/* 291 */     else if (this.m_hmChildAttributeHeaders.containsKey(sHeader))
/*     */     {
/* 294 */       Attribute attribute = (Attribute)this.m_hmChildAttributeHeaders.get(sHeader);
/* 295 */       SynchUtil.addAssetChildren(this.m_attValueManager, asset.getAsset(), attribute, a_sValue);
/*     */     }
/* 297 */     else if (this.m_hmPeerAttributeHeaders.containsKey(sHeader))
/*     */     {
/* 300 */       Attribute attribute = (Attribute)this.m_hmPeerAttributeHeaders.get(sHeader);
/* 301 */       SynchUtil.addAssetPeers(this.m_attValueManager, asset.getAsset(), attribute, a_sValue);
/*     */     }
/*     */     else
/*     */     {
/* 305 */       super.populateBean(a_header, a_sValue, a_bean);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.AssetBeanReader
 * JD-Core Version:    0.6.0
 */