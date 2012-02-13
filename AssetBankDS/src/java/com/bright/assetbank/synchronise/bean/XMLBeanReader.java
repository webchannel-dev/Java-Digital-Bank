/*     */ package com.bright.assetbank.synchronise.bean;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.synchronise.constant.ExportConstants;
/*     */ import com.bright.assetbank.synchronise.constant.ExternalDataSynchronisationSettings;
/*     */ import com.bright.assetbank.synchronise.constant.SynchronisationConstants;
/*     */ import com.bright.assetbank.synchronise.util.SynchUtil;
/*     */ import com.bright.assetbank.taxonomy.service.TaxonomyManager;
/*     */ import com.bright.framework.file.BeanReader;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ 
/*     */ public class XMLBeanReader
/*     */   implements BeanReader, SynchronisationConstants, ExportConstants
/*     */ {
/*     */   private static final String c_ksClassName = "XMLBeanReader";
/*  70 */   protected Vector m_vecAssets = null;
/*  71 */   protected AttributeManager m_attManager = null;
/*  72 */   protected AttributeValueManager m_attValueManager = null;
/*  73 */   protected Map m_hmAttributeMappings = null;
/*  74 */   protected Map m_hmFieldMappings = null;
/*  75 */   protected int m_iStartPoint = 0;
/*  76 */   protected String m_sCategoryPrefix = null;
/*  77 */   protected String m_sAccessLevelPrefix = null;
/*  78 */   protected Vector m_vecCategoryElements = null;
/*  79 */   protected Vector m_vecAccessLevelElements = null;
/*  80 */   protected String m_sRootElementName = null;
/*  81 */   protected String m_sRecordsElementName = null;
/*  82 */   protected String m_sRecordElementName = null;
/*  83 */   protected TaxonomyManager m_taxManager = null;
/*  84 */   protected String m_sXMLUrl = null;
/*  85 */   protected HashMap m_hmFileToIdMapping = null;
/*  86 */   protected String m_sFileElementName = null;
/*  87 */   protected String m_sAssetBankIdElementName = null;
/*  88 */   protected ExternalDataSynchronisationSettings m_settings = null;
/*  89 */   protected long m_lDefaultCategoryId = -1L;
/*  90 */   protected long m_lDefaultAccessLevelId = -1L;
/*  91 */   protected HashMap m_hmDefaultValues = null;
/*  92 */   protected boolean m_bAssetsApproved = false;
/*  93 */   protected Vector m_vecStaticAttributeValues = new Vector();
/*     */ 
/*     */   public XMLBeanReader(String a_sXMLUrl, HashMap a_hmFileToIdMapping, ExternalDataSynchronisationSettings a_settings, boolean a_bAssetsApproved)
/*     */   {
/*  97 */     this.m_sXMLUrl = a_sXMLUrl;
/*  98 */     this.m_hmFileToIdMapping = a_hmFileToIdMapping;
/*  99 */     this.m_settings = a_settings;
/* 100 */     this.m_bAssetsApproved = a_bAssetsApproved;
/* 101 */     initialiseComponents();
/*     */   }
/*     */ 
/*     */   public String getRecordElementName()
/*     */   {
/* 107 */     return this.m_sRecordElementName;
/*     */   }
/*     */ 
/*     */   public String getRecordsElementName()
/*     */   {
/* 112 */     return this.m_sRecordsElementName;
/*     */   }
/*     */ 
/*     */   public String getRootElementName()
/*     */   {
/* 117 */     return this.m_sRootElementName;
/*     */   }
/*     */ 
/*     */   public Vector readBeans()
/*     */     throws Bn2Exception
/*     */   {
/* 133 */     return readBeans(-1);
/*     */   }
/*     */ 
/*     */   public Vector readBeans(int a_iNoOfBeansToRead)
/*     */     throws Bn2Exception
/*     */   {
/* 153 */     if (this.m_vecAssets == null)
/*     */     {
/* 155 */       parseXML();
/*     */     }
/*     */ 
/* 159 */     if ((a_iNoOfBeansToRead > 0) && (this.m_vecAssets != null))
/*     */     {
/* 162 */       Vector vecAssets = new Vector();
/*     */ 
/* 164 */       if (this.m_iStartPoint < this.m_vecAssets.size())
/*     */       {
/* 166 */         for (int i = this.m_iStartPoint; i < this.m_iStartPoint + a_iNoOfBeansToRead; i++)
/*     */         {
/* 168 */           this.m_iStartPoint = i;
/* 169 */           if (i >= this.m_vecAssets.size())
/*     */           {
/*     */             break;
/*     */           }
/*     */ 
/* 174 */           vecAssets.add(this.m_vecAssets.elementAt(i));
/*     */         }
/* 176 */         this.m_iStartPoint += 1;
/*     */       }
/*     */ 
/* 179 */       return vecAssets;
/*     */     }
/*     */ 
/* 182 */     if (this.m_vecAssets == null)
/*     */     {
/* 184 */       this.m_vecAssets = new Vector();
/*     */     }
/*     */ 
/* 187 */     return this.m_vecAssets;
/*     */   }
/*     */ 
/*     */   protected void parseXML()
/*     */     throws Bn2Exception
/*     */   {
/* 202 */     String ksMethodName = "parseXML";
/*     */     try
/*     */     {
/* 207 */       if ((this.m_hmAttributeMappings == null) || (this.m_hmFieldMappings == null))
/*     */       {
/* 209 */         buildMappings();
/*     */       }
/*     */ 
/* 213 */       InputStream is = new URL(this.m_sXMLUrl).openStream();
/* 214 */       Reader reader = new InputStreamReader(is, this.m_settings.getFeedEncoding());
/*     */ 
/* 216 */       SAXBuilder parser = new SAXBuilder();
/* 217 */       Document doc = parser.build(reader); Element rootElement = doc.getRootElement();
/* 218 */       Element recordsElement = rootElement.getChild(this.m_sRecordsElementName);
/*     */ 
/* 221 */       if (recordsElement != null)
/*     */       {
/* 223 */         List listRecords = recordsElement.getChildren();
/*     */ 
/* 225 */         if (listRecords != null)
/*     */         {
/* 227 */           ListIterator recordIterator = listRecords.listIterator();
/*     */ 
/* 229 */           if (recordIterator != null)
/*     */           {
/* 232 */             while (recordIterator.hasNext())
/*     */             {
/* 234 */               if (this.m_vecAssets == null)
/*     */               {
/* 236 */                 this.m_vecAssets = new Vector();
/*     */               }
/*     */ 
/* 240 */               Element recordElement = (Element)recordIterator.next();
/* 241 */               XMLImportAsset asset = new XMLImportAsset();
/*     */ 
/* 244 */               asset.setFlatCategories(getCategoriesFromElements(recordElement, this.m_sCategoryPrefix, this.m_vecCategoryElements));
/* 245 */               asset.setFlatAccessLevels(getCategoriesFromElements(recordElement, this.m_sAccessLevelPrefix, this.m_vecAccessLevelElements));
/* 246 */               asset.setDefaultAccessLevelId(this.m_lDefaultAccessLevelId);
/* 247 */               asset.setDefaultCategoryId(this.m_lDefaultCategoryId);
/*     */ 
/* 250 */               String sFile = getFileName(recordElement);
/* 251 */               long lId = 0L;
/*     */ 
/* 253 */               if ((this.m_hmFileToIdMapping != null) && (this.m_hmFileToIdMapping.containsKey(sFile)))
/*     */               {
/* 256 */                 lId = ((Long)this.m_hmFileToIdMapping.get(sFile)).longValue();
/*     */               }
/*     */               else
/*     */               {
/* 260 */                 String sId = getAssetBankId(recordElement);
/* 261 */                 if (StringUtil.stringIsPopulated(sId))
/*     */                 {
/*     */                   try
/*     */                   {
/* 266 */                     lId = Long.parseLong(sId);
/*     */                   }
/*     */                   catch (Exception e)
/*     */                   {
/* 271 */                     GlobalApplication.getInstance().getLogger().error("XMLBeanReader.parseXML: Error getting existing id : " + sId);
/*     */                   }
/*     */                 }
/*     */               }
/*     */ 
/* 276 */               Set keys = null;
/* 277 */               Iterator keyIterator = null;
/*     */ 
/* 279 */               if (lId > 0L)
/*     */               {
/* 281 */                 asset.setId(lId);
/* 282 */                 GlobalApplication.getInstance().getLogger().debug("XMLBeanReader.parseXML: Asset : " + asset.getId() + " FlatCategories: " + asset.getFlatCategories() + " FlatAccessLevels: " + asset.getFlatAccessLevels());
/*     */ 
/* 285 */                 keys = this.m_hmAttributeMappings.keySet();
/*     */ 
/* 287 */                 if (keys != null)
/*     */                 {
/* 289 */                   keyIterator = keys.iterator();
/*     */ 
/* 291 */                   if (keyIterator != null)
/*     */                   {
/* 293 */                     GlobalApplication.getInstance().getLogger().debug("XMLBeanReader.parseXML: About to set the asset attributes");
/* 294 */                     while (keyIterator.hasNext())
/*     */                     {
/* 296 */                       String sKey = (String)keyIterator.next();
/* 297 */                       String sValue = getValue(sKey, recordElement);
/*     */ 
/* 299 */                       if (sValue == null)
/*     */                       {
/* 302 */                         if (this.m_hmDefaultValues.containsKey(sKey))
/*     */                         {
/* 304 */                           sValue = (String)this.m_hmDefaultValues.get(sKey);
/*     */                         }
/*     */                       }
/*     */ 
/* 308 */                       if (sValue != null)
/*     */                       {
/* 311 */                         Attribute att = (Attribute)this.m_hmAttributeMappings.get(sKey);
/* 312 */                         SynchUtil.setAssetAttributeValue(this.m_attValueManager, this.m_taxManager, asset, att, sValue, false);
/* 313 */                         GlobalApplication.getInstance().getLogger().debug("XMLBeanReader.parseXML: Attribute: " + att.getLabel() + " Value: " + sValue);
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */ 
/* 319 */                 addExtraAttributes(asset, recordElement);
/*     */ 
/* 322 */                 keys = this.m_hmFieldMappings.keySet();
/*     */ 
/* 324 */                 if (keys != null)
/*     */                 {
/* 326 */                   keyIterator = keys.iterator();
/*     */ 
/* 328 */                   if (keyIterator != null)
/*     */                   {
/* 330 */                     GlobalApplication.getInstance().getLogger().debug("XMLBeanReader.parseXML: About to set the asset fields");
/*     */ 
/* 332 */                     while (keyIterator.hasNext())
/*     */                     {
/* 334 */                       String sKey = (String)keyIterator.next();
/* 335 */                       String sValue = getValue(sKey, recordElement);
/*     */ 
/* 337 */                       GlobalApplication.getInstance().getLogger().debug("XMLBeanReader.parseXML: Fields: Key: " + sKey + " Value: " + sValue);
/* 338 */                       if (sValue != null)
/*     */                       {
/* 341 */                         String sFieldName = (String)this.m_hmFieldMappings.get(sKey);
/* 342 */                         GlobalApplication.getInstance().getLogger().debug("XMLBeanReader.parseXML: Fieldname: " + sFieldName);
/*     */                         try
/*     */                         {
/* 347 */                           String[] aPair = sFieldName.split(":");
/* 348 */                           sFieldName = "set" + aPair[0];
/* 349 */                           String sType = aPair[1];
/*     */ 
/* 352 */                           Class typeClass = getClassForType(sType);
/* 353 */                           Class[] classes = { typeClass };
/* 354 */                           Method setMethod = asset.getClass().getMethod(sFieldName, classes);
/*     */ 
/* 357 */                           Object[] objArgs = getArguments(sValue, typeClass);
/* 358 */                           setMethod.invoke(asset, objArgs);
/* 359 */                           GlobalApplication.getInstance().getLogger().debug("XMLBeanReader.parseXML: Set value for: " + sFieldName);
/*     */                         }
/*     */                         catch (Exception e)
/*     */                         {
/* 363 */                           GlobalApplication.getInstance().getLogger().error("XMLBeanReader.parseXML : Unable to add value : " + sFieldName + " ", e);
/*     */                         }
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */ 
/* 370 */                 prepareForCallback(asset, recordElement);
/*     */ 
/* 372 */                 GregorianCalendar now = new GregorianCalendar();
/* 373 */                 asset.setDateAdded(now.getTime());
/* 374 */                 asset.setDateLastModified(now.getTime());
/* 375 */                 asset.setImportApprovalDirective("TRUE");
/*     */ 
/* 378 */                 if ((this.m_vecStaticAttributeValues != null) && (this.m_vecStaticAttributeValues.size() > 0))
/*     */                 {
/* 380 */                   for (int i = 0; i < this.m_vecStaticAttributeValues.size(); i++)
/*     */                   {
/* 382 */                     asset.addAttributeValue((AttributeValue)this.m_vecStaticAttributeValues.elementAt(i));
/*     */                   }
/*     */ 
/*     */                 }
/*     */ 
/* 387 */                 this.m_vecAssets.add(asset);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (JDOMException e)
/*     */     {
/* 396 */       throw new Bn2Exception("XMLBeanReader.parseXML : JDOM Exception whilst trying to parse XML : " + e, e);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 400 */       throw new Bn2Exception("XMLBeanReader.parseXML : Exception whilst trying to parse XML : " + e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void buildMappings()
/*     */     throws IOException, JDOMException
/*     */   {
/* 418 */     Map hmAttributes = SynchUtil.buildAvailableAttributeMap(this.m_attManager);
/*     */ 
/* 420 */     this.m_hmAttributeMappings = new HashMap();
/* 421 */     this.m_hmFieldMappings = new HashMap();
/*     */ 
/* 424 */     SAXBuilder parser = new SAXBuilder();
/* 425 */     Document doc = parser.build(GlobalSettings.getApplicationPath() + "/WEB-INF/classes/mappings.xml");
/* 426 */     Element rootElement = doc.getRootElement();
/*     */ 
/* 428 */     this.m_sRootElementName = rootElement.getChildText("root_element");
/* 429 */     this.m_sRecordsElementName = rootElement.getChildText("records_element");
/* 430 */     this.m_sRecordElementName = rootElement.getChildText("record_element");
/* 431 */     this.m_sFileElementName = rootElement.getChildText("file_element");
/* 432 */     this.m_sAssetBankIdElementName = rootElement.getChildText("assetbankid_element");
/*     */ 
/* 435 */     Element categories = rootElement.getChild("category_elements");
/* 436 */     Element accessLevels = rootElement.getChild("accesslevel_elements");
/*     */ 
/* 438 */     if (categories != null)
/*     */     {
/* 440 */       this.m_sCategoryPrefix = categories.getAttributeValue("prefix");
/*     */     }
/* 442 */     if (accessLevels != null)
/*     */     {
/* 444 */       this.m_sAccessLevelPrefix = accessLevels.getAttributeValue("prefix");
/*     */     }
/*     */ 
/* 447 */     this.m_vecCategoryElements = getCategoryElements(categories);
/* 448 */     this.m_vecAccessLevelElements = getCategoryElements(accessLevels);
/*     */     try
/*     */     {
/* 453 */       this.m_lDefaultCategoryId = Long.parseLong(rootElement.getChildText("default_category_id"));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 462 */       this.m_lDefaultAccessLevelId = Long.parseLong(rootElement.getChildText("default_accesslevel_id"));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */ 
/* 470 */     Element fieldsElement = rootElement.getChild("fields");
/* 471 */     Element attributesElement = rootElement.getChild("attributes");
/* 472 */     Element[] elements = { fieldsElement, attributesElement };
/* 473 */     List elementList = null;
/* 474 */     Iterator iterator = null;
/*     */ 
/* 476 */     for (int i = 0; i < elements.length; i++)
/*     */     {
/* 478 */       elementList = elements[i].getChildren();
/*     */ 
/* 480 */       if (elementList == null)
/*     */         continue;
/* 482 */       iterator = elementList.listIterator();
/*     */ 
/* 484 */       if (iterator == null) {
/*     */         continue;
/*     */       }
/* 487 */       while (iterator.hasNext())
/*     */       {
/* 489 */         Element mappingElement = (Element)iterator.next();
/*     */ 
/* 492 */         if ((mappingElement != null) && (mappingElement.getParentElement() != null))
/*     */         {
/* 495 */           if (mappingElement.getName().equals("static_value"))
/*     */           {
/* 497 */             if (mappingElement.getParentElement().getName().equals("attributes"))
/*     */             {
/* 499 */               Long lAttId = null;
/*     */               try
/*     */               {
/* 502 */                 lAttId = Long.valueOf(mappingElement.getAttributeValue("id"));
/*     */               }
/*     */               catch (NumberFormatException nfe)
/*     */               {
/*     */               }
/*     */ 
/* 510 */               if ((lAttId != null) && (hmAttributes.containsKey(lAttId)))
/*     */               {
/* 512 */                 Vector vecAttributeValues = SynchUtil.getAttributeValue(this.m_attValueManager, this.m_taxManager, (Attribute)hmAttributes.get(lAttId), mappingElement.getAttributeValue("value"), false);
/* 513 */                 this.m_vecStaticAttributeValues.addAll(vecAttributeValues);
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 523 */             String sAdlibField = mappingElement.getChildText("xml");
/* 524 */             String sABField = mappingElement.getChildText("assetbank");
/*     */ 
/* 527 */             if (mappingElement.getChild("xml").getAttributeValue("topLevelElementIndex") != null)
/*     */             {
/* 529 */               sAdlibField = sAdlibField + "," + mappingElement.getChild("xml").getAttributeValue("topLevelElementIndex");
/*     */             }
/*     */ 
/* 532 */             if (StringUtil.stringIsPopulated(mappingElement.getAttributeValue("defaultValue")))
/*     */             {
/* 535 */               if (this.m_hmDefaultValues == null)
/*     */               {
/* 537 */                 this.m_hmDefaultValues = new HashMap();
/*     */               }
/* 539 */               this.m_hmDefaultValues.put(sAdlibField, mappingElement.getAttributeValue("defaultValue"));
/*     */             }
/*     */ 
/* 542 */             if (mappingElement.getParentElement().getName().equals("attributes"))
/*     */             {
/* 544 */               Long lAttId = null;
/*     */               try
/*     */               {
/* 547 */                 lAttId = Long.valueOf(sABField);
/*     */               }
/*     */               catch (NumberFormatException nfe)
/*     */               {
/*     */               }
/*     */ 
/* 555 */               if ((lAttId != null) && (hmAttributes.containsKey(lAttId)))
/*     */               {
/* 557 */                 this.m_hmAttributeMappings.put(sAdlibField, hmAttributes.get(lAttId));
/*     */               }
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 563 */               String sType = mappingElement.getChild("assetbank").getAttributeValue("type");
/* 564 */               this.m_hmFieldMappings.put(sAdlibField, sABField + ":" + sType);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Vector getCategoryElements(Element a_catElement)
/*     */   {
/* 589 */     Vector vecCategoryElements = null;
/*     */ 
/* 591 */     if (a_catElement != null)
/*     */     {
/* 593 */       List elementList = null;
/* 594 */       ListIterator iterator = null;
/*     */ 
/* 596 */       elementList = a_catElement.getChildren();
/*     */ 
/* 598 */       if (elementList != null)
/*     */       {
/* 600 */         iterator = elementList.listIterator();
/*     */ 
/* 602 */         if (iterator != null)
/*     */         {
/* 604 */           while (iterator.hasNext())
/*     */           {
/* 606 */             Element categoryElement = (Element)iterator.next();
/*     */ 
/* 608 */             if (vecCategoryElements == null)
/*     */             {
/* 610 */               vecCategoryElements = new Vector();
/*     */             }
/* 612 */             vecCategoryElements.add(categoryElement.getText());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 618 */     return vecCategoryElements;
/*     */   }
/*     */ 
/*     */   protected String[] getCategoriesFromElements(Element a_recordElement, String a_sPrefix, Vector a_vecCategoryElements)
/*     */   {
/* 639 */     String sFlatCategoryTree = "";
/*     */ 
/* 641 */     if (StringUtil.stringIsPopulated(a_sPrefix))
/*     */     {
/* 643 */       sFlatCategoryTree = a_sPrefix + ":";
/*     */     }
/*     */ 
/* 646 */     if (a_vecCategoryElements != null)
/*     */     {
/* 648 */       for (int i = 0; i < a_vecCategoryElements.size(); i++)
/*     */       {
/* 650 */         String sElement = (String)a_vecCategoryElements.elementAt(i);
/* 651 */         String[] aElements = sElement.split(":");
/* 652 */         Element tempElement = a_recordElement;
/*     */ 
/* 654 */         for (int x = 0; x < aElements.length; x++)
/*     */         {
/* 656 */           if (tempElement == null)
/*     */           {
/*     */             break;
/*     */           }
/*     */ 
/* 661 */           tempElement = tempElement.getChild(aElements[x]);
/*     */         }
/*     */ 
/* 664 */         if ((tempElement == null) || (tempElement == a_recordElement))
/*     */           continue;
/* 666 */         String sCategory = tempElement.getText();
/*     */ 
/* 669 */         if (!StringUtil.stringIsPopulated(sCategory))
/*     */           continue;
/* 671 */         sFlatCategoryTree = sFlatCategoryTree + sCategory + ":";
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 676 */     return sFlatCategoryTree.split(":");
/*     */   }
/*     */ 
/*     */   protected String getValue(String a_sKey, Element a_recordElement)
/*     */   {
/* 697 */     String[] aKeyAndIndex = a_sKey.split(",");
/* 698 */     int iIndex = 1;
/*     */ 
/* 700 */     if (aKeyAndIndex.length == 2)
/*     */     {
/*     */       try
/*     */       {
/* 704 */         iIndex = Integer.parseInt(aKeyAndIndex[1]);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 712 */     String[] aNestedKeys = aKeyAndIndex[0].split(":");
/* 713 */     Element lastElement = a_recordElement;
/*     */ 
/* 715 */     List elementList = lastElement.getChildren(aNestedKeys[0]);
/* 716 */     Iterator iterator = elementList.iterator();
/*     */ 
/* 718 */     if (iterator != null)
/*     */     {
/* 720 */       while ((iterator.hasNext()) && (iIndex > 0))
/*     */       {
/* 722 */         lastElement = (Element)iterator.next();
/* 723 */         iIndex--;
/*     */       }
/*     */     }
/*     */ 
/* 727 */     if (aNestedKeys.length > 1)
/*     */     {
/* 729 */       for (int i = 1; i < aNestedKeys.length; i++)
/*     */       {
/* 731 */         if (lastElement == null)
/*     */           continue;
/* 733 */         lastElement = lastElement.getChild(aNestedKeys[i]);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 738 */     if (lastElement == null)
/*     */     {
/* 740 */       return null;
/*     */     }
/* 742 */     return lastElement.getText();
/*     */   }
/*     */ 
/*     */   protected Class getClassForType(String a_sType)
/*     */   {
/* 753 */     if (a_sType.equals("int"))
/*     */     {
/* 755 */       return Integer.TYPE;
/*     */     }
/* 757 */     if (a_sType.equals("long"))
/*     */     {
/* 759 */       return Long.TYPE;
/*     */     }
/* 761 */     if (a_sType.equals("double"))
/*     */     {
/* 763 */       return Double.TYPE;
/*     */     }
/* 765 */     if (a_sType.equals("float"))
/*     */     {
/* 767 */       return Float.TYPE;
/*     */     }
/* 769 */     return String.class;
/*     */   }
/*     */ 
/*     */   protected Object[] getArguments(String a_sValue, Class a_class)
/*     */   {
/* 781 */     Object[] args = new Object[1];
/*     */ 
/* 783 */     if (a_class == Integer.TYPE)
/*     */     {
/* 785 */       args[0] = Integer.valueOf(a_sValue);
/*     */     }
/* 787 */     else if (a_class == Long.TYPE)
/*     */     {
/* 789 */       args[0] = Long.valueOf(a_sValue);
/*     */     }
/* 791 */     else if (a_class == Float.TYPE)
/*     */     {
/* 793 */       args[0] = Float.valueOf(a_sValue);
/*     */     }
/* 795 */     else if (a_class == Double.TYPE)
/*     */     {
/* 797 */       args[0] = Double.valueOf(a_sValue);
/*     */     }
/*     */     else
/*     */     {
/* 801 */       args[0] = a_sValue;
/*     */     }
/*     */ 
/* 804 */     return args;
/*     */   }
/*     */ 
/*     */   protected String getFileName(Element a_recordElement)
/*     */   {
/* 812 */     return a_recordElement.getChildText(this.m_sFileElementName);
/*     */   }
/*     */ 
/*     */   protected String getAssetBankId(Element a_recordElement)
/*     */   {
/* 820 */     return a_recordElement.getChildText(this.m_sAssetBankIdElementName);
/*     */   }
/*     */ 
/*     */   protected void addExtraAttributes(XMLImportAsset a_asset, Element a_recordElement)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void prepareForCallback(XMLImportAsset a_asset, Element a_recordElement)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void initialiseComponents()
/*     */   {
/* 838 */     String ksMethodName = "initialiseComponents";
/*     */ 
/* 840 */     if (this.m_attManager == null)
/*     */     {
/*     */       try
/*     */       {
/* 844 */         this.m_attManager = ((AttributeManager)GlobalApplication.getInstance().getComponentManager().lookup("AttributeManager"));
/*     */ 
/* 847 */         this.m_attValueManager = ((AttributeValueManager)GlobalApplication.getInstance().getComponentManager().lookup("AttributeValueManager"));
/*     */ 
/* 850 */         this.m_taxManager = ((TaxonomyManager)GlobalApplication.getInstance().getComponentManager().lookup("TaxonomyManager"));
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/* 855 */         throw new RuntimeException("XMLBeanReader.initialiseComponents : Couldn't find component ", ce);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.XMLBeanReader
 * JD-Core Version:    0.6.0
 */