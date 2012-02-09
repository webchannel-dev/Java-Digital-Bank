/*      */ package com.bright.assetbank.attribute.bean;
/*      */ 
/*      */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*      */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*      */ import com.bright.assetbank.attribute.constant.ACAttributeConstants;
/*      */ import com.bright.framework.language.bean.Language;
/*      */ import com.bright.framework.language.bean.TranslatableWithLanguage;
/*      */ import com.bright.framework.language.bean.Translation;
/*      */ import com.bright.framework.language.constant.LanguageConstants;
/*      */ import com.bright.framework.language.util.LanguageUtils;
/*      */ import com.bright.framework.util.StringUtil;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ public class Attribute extends BaseAttribute
/*      */   implements TranslatableWithLanguage, AssetBankConstants, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -5900097801156067554L;
             private long m_lId = 0L;
/*   80 */   private long m_lTypeId = 0L;
/*   81 */   private int m_iSequence = 0;
/*   82 */   private boolean m_bKeywordSearchable = false;
/*   83 */   private boolean m_bMandatory = false;
/*   84 */   private boolean m_bMandatoryBulkUpload = false;
/*   85 */   private boolean m_bSearchField = false;
/*   86 */   private boolean m_bSearchBuilderField = false;
/*   87 */   private boolean m_bIsVisible = false;
/*   88 */   private boolean m_bIsRelevant = true;
/*   89 */   private boolean m_bStatic = false;
/*   90 */   private String m_sFieldName = null;
/*   91 */   private boolean m_bReadOnly = false;
/*   92 */   private boolean m_bIsHtml = false;
/*   93 */   private boolean m_bRequiredForCompleteness = false;
/*   94 */   private boolean m_bRequiresTranslation = false;
/*   95 */   private boolean m_bExtendableList = false;
/*      */ 
/*   98 */   private AttributeValue m_vValue = null;
/*      */ 
/*  101 */   private Vector<AttributeValue> m_vListOptionValues = null;
/*      */ 
/*  103 */   private int m_iMaxDisplayLength = -1;
/*  104 */   private long m_lTreeId = -1L;
/*  105 */   private boolean m_bHighlight = false;
/*  106 */   private boolean m_bIsNameAttribute = false;
/*  107 */   private boolean m_bIsDescriptionAttribute = false;
/*  108 */   private String m_sDefaultKeywordFilter = null;
/*  109 */   private String m_sOnChangeScript = null;
/*      */ 
/*  111 */   private String m_sBaseUrl = null;
/*      */ 
/*  113 */   private boolean m_bHasSearchTokens = false;
/*  114 */   private String m_sTokenDelimiterRegex = null;
/*      */ 
/*  116 */   private int m_iHeight = 0;
/*  117 */   private int m_iWidth = 0;
/*  118 */   private int m_iMaxSize = 0;
/*  119 */   private boolean m_bIsFiltered = false;
/*      */ 
/*  122 */   private long m_lSeed = 1L;
/*  123 */   private int m_iIncrement = 1;
/*  124 */   private String m_sPrefix = null;
/*  125 */   private int m_iPadWidth = 0;
/*      */ 
/*  127 */   private boolean m_bDataFromChildren = false;
/*      */ 
/*  129 */   private boolean m_bShowOnChild = false;
/*  130 */   private boolean m_bIncludeInSearchForChild = false;
/*      */ 
/*  132 */   private boolean m_bHideForSort = false;
/*      */ 
/*  135 */   private Vector m_vTranslations = null;
/*      */ 
/*  137 */   private boolean m_bSelected = false;
/*      */ 
/*  139 */   private boolean m_bAutoComplete = false;
/*      */ 
/*  142 */   private String m_sPluginClass = null;
/*  143 */   private String m_sAttributeIdsForPluginParams = null;
/*      */ 
/*  145 */   private boolean m_bShowOnDownload = false;
/*      */   private String m_sValueColumnName;
/*      */   private int m_iMaxDecimalPlaces;
/*      */   private int m_iMinDecimalPlaces;
/*  152 */   private boolean m_bHidden = false;
/*      */   
/*  730 */   private static final Pattern c_kRangePattern = Pattern.compile("\\[[^]]*\\]");
/*      */ 
/*      */   public Attribute()
/*      */   {
/*      */   }
/*      */ 
/*      */   public Attribute(Attribute a_toCopy)
/*      */   {
/*  160 */     this.m_lId = a_toCopy.m_lId;
/*  161 */     this.m_lTypeId = a_toCopy.m_lTypeId;
/*  162 */     this.m_sLabel = a_toCopy.m_sLabel;
/*  163 */     this.m_iSequence = a_toCopy.m_iSequence;
/*  164 */     this.m_bKeywordSearchable = a_toCopy.m_bKeywordSearchable;
/*  165 */     this.m_bMandatory = a_toCopy.m_bMandatory;
/*  166 */     this.m_bMandatoryBulkUpload = a_toCopy.m_bMandatoryBulkUpload;
/*  167 */     this.m_bSearchField = a_toCopy.m_bSearchField;
/*  168 */     this.m_bSearchBuilderField = a_toCopy.m_bSearchBuilderField;
/*  169 */     this.m_bIsVisible = a_toCopy.m_bIsVisible;
/*  170 */     this.m_bIsRelevant = a_toCopy.m_bIsRelevant;
/*  171 */     this.m_sDefaultValue = a_toCopy.m_sDefaultValue;
/*  172 */     this.m_sValueIfNotVisible = a_toCopy.m_sValueIfNotVisible;
/*  173 */     this.m_bStatic = a_toCopy.m_bStatic;
/*  174 */     this.m_sFieldName = a_toCopy.m_sFieldName;
/*  175 */     this.m_bReadOnly = a_toCopy.m_bReadOnly;
/*  176 */     this.m_vListOptionValues = a_toCopy.m_vListOptionValues;
/*  177 */     this.m_iMaxDisplayLength = a_toCopy.m_iMaxDisplayLength;
/*  178 */     this.m_lTreeId = a_toCopy.m_lTreeId;
/*  179 */     this.m_bHighlight = a_toCopy.m_bHighlight;
/*  180 */     this.m_sHelpText = a_toCopy.m_sHelpText;
/*  181 */     this.m_bIsNameAttribute = a_toCopy.m_bIsNameAttribute;
/*  182 */     this.m_bIsDescriptionAttribute = a_toCopy.m_bIsDescriptionAttribute;
/*  183 */     this.m_bIsHtml = a_toCopy.m_bIsHtml;
/*  184 */     this.m_sBaseUrl = a_toCopy.m_sBaseUrl;
/*  185 */     this.m_iHeight = a_toCopy.m_iHeight;
/*  186 */     this.m_iWidth = a_toCopy.m_iWidth;
/*  187 */     this.m_iMaxSize = a_toCopy.m_iMaxSize;
/*  188 */     this.m_bIsFiltered = a_toCopy.m_bIsFiltered;
/*  189 */     this.m_iIncrement = a_toCopy.m_iIncrement;
/*  190 */     this.m_sInputMask = a_toCopy.m_sInputMask;
/*  191 */     this.m_bShowOnChild = a_toCopy.m_bShowOnChild;
/*  192 */     this.m_bIncludeInSearchForChild = a_toCopy.m_bIncludeInSearchForChild;
/*  193 */     this.m_sDefaultKeywordFilter = a_toCopy.m_sDefaultKeywordFilter;
/*  194 */     this.m_sOnChangeScript = a_toCopy.m_sOnChangeScript;
/*  195 */     this.m_bRequiredForCompleteness = a_toCopy.m_bRequiredForCompleteness;
/*  196 */     this.m_bRequiresTranslation = a_toCopy.m_bRequiresTranslation;
/*  197 */     this.m_sDisplayName = a_toCopy.m_sDisplayName;
/*  198 */     this.m_sAltText = a_toCopy.m_sAltText;
/*  199 */     this.m_bHasSearchTokens = a_toCopy.m_bHasSearchTokens;
/*  200 */     this.m_sTokenDelimiterRegex = a_toCopy.m_sTokenDelimiterRegex;
/*  201 */     this.m_bExtendableList = a_toCopy.m_bExtendableList;
/*  202 */     this.m_bDataFromChildren = a_toCopy.m_bDataFromChildren;
/*  203 */     this.m_bHideForSort = a_toCopy.m_bHideForSort;
/*  204 */     this.m_sPrefix = a_toCopy.m_sPrefix;
/*  205 */     this.m_iPadWidth = a_toCopy.m_iPadWidth;
/*  206 */     this.m_bSelected = a_toCopy.m_bSelected;
/*  207 */     this.m_bAutoComplete = a_toCopy.m_bAutoComplete;
/*  208 */     this.m_sPluginClass = a_toCopy.m_sPluginClass;
/*  209 */     this.m_sAttributeIdsForPluginParams = a_toCopy.m_sAttributeIdsForPluginParams;
/*  210 */     this.m_sValueColumnName = a_toCopy.m_sValueColumnName;
/*  211 */     this.m_bShowOnDownload = a_toCopy.m_bShowOnDownload;
/*  212 */     this.m_iMaxDecimalPlaces = a_toCopy.m_iMaxDecimalPlaces;
/*  213 */     this.m_iMinDecimalPlaces = a_toCopy.m_iMinDecimalPlaces;
/*  214 */     this.m_bHidden = a_toCopy.m_bHidden;
/*      */ 
/*  217 */     setTranslations(a_toCopy.getTranslations());
/*      */ 
/*  220 */     if (a_toCopy.m_vValue != null)
/*      */     {
/*  222 */       this.m_vValue = new AttributeValue(a_toCopy.m_vValue);
/*  223 */       this.m_vValue.setAttribute(this);
/*      */     }
/*      */ 
/*  226 */     if (a_toCopy.m_vListOptionValues != null)
/*      */     {
/*  228 */       Vector vecValues = new Vector(a_toCopy.m_vListOptionValues.size());
/*  229 */       for (int i = 0; i < a_toCopy.m_vListOptionValues.size(); i++)
/*      */       {
/*  231 */         AttributeValue val = (AttributeValue)a_toCopy.m_vListOptionValues.elementAt(i);
/*  232 */         vecValues.add(new AttributeValue(val));
/*      */       }
/*  234 */       setListOptionValues(vecValues);
/*      */     }
/*      */   }
/*      */ 
            public long getId(){
                return this.m_lId;
            }
            public void setId(long id){
                this.m_lId = id;
            }

/*      */   public String getLabel()
/*      */   {
/*  240 */     if (!this.m_language.equals(LanguageConstants.k_defaultLanguage))
/*      */     {
/*  242 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  243 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getLabel())))
/*      */       {
/*  245 */         return translation.getLabel();
/*      */       }
/*      */     }
/*  248 */     return this.m_sLabel;
/*      */   }
/*      */ 
/*      */   public String getDisplayName()
/*      */   {
/*  253 */     if (!this.m_language.equals(LanguageConstants.k_defaultLanguage))
/*      */     {
/*  255 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  256 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getDisplayName())))
/*      */       {
/*  258 */         return translation.getDisplayName();
/*      */       }
/*      */     }
/*  261 */     return this.m_sDisplayName;
/*      */   }
/*      */ 
/*      */   public String getAltText()
/*      */   {
/*  266 */     if (!this.m_language.equals(LanguageConstants.k_defaultLanguage))
/*      */     {
/*  268 */       Translation translation = (Translation)LanguageUtils.getTranslation(this.m_language, this.m_vTranslations);
/*  269 */       if ((translation != null) && (StringUtils.isNotEmpty(translation.getAltText())))
/*      */       {
/*  271 */         return translation.getAltText();
/*      */       }
/*      */     }
/*  274 */     return this.m_sAltText;
/*      */   }
/*      */ 
/*      */   public String getButtonScript()
/*      */   {
/*  285 */     String sScript = this.m_sOnChangeScript.replace("lAttributeId", Long.toString(this.m_lId));
/*  286 */     return sScript;
/*      */   }
/*      */ 
/*      */   public boolean isAutoCompleteAllowed()
/*      */   {
/*  291 */     return ACAttributeConstants.k_allowedAttributeTypeIds.contains(Long.valueOf(this.m_lTypeId));
/*      */   }
/*      */ 
/*      */   public void setIsNameAttribute(boolean a_bIsNameAttribute)
/*      */   {
/*  296 */     this.m_bIsNameAttribute = a_bIsNameAttribute;
/*      */   }
/*      */ 
/*      */   public boolean getIsNameAttribute()
/*      */   {
/*  301 */     return this.m_bIsNameAttribute;
/*      */   }
/*      */ 
/*      */   public void setSequence(int a_iSequence)
/*      */   {
/*  306 */     this.m_iSequence = a_iSequence;
/*      */   }
/*      */ 
/*      */   public int getSequence()
/*      */   {
/*  311 */     return this.m_iSequence;
/*      */   }
/*      */ 
/*      */   public void setTypeId(long a_lTypeId)
/*      */   {
/*  316 */     this.m_lTypeId = a_lTypeId;
/*      */   }
/*      */ 
/*      */   public long getTypeId()
/*      */   {
/*  321 */     return this.m_lTypeId;
/*      */   }
/*      */ 
/*      */   public boolean getIsTextfield()
/*      */   {
/*  326 */     return (getTypeId() == 1L) || (getTypeId() == 14L);
/*      */   }
/*      */ 
/*      */   public boolean getIsTextarea()
/*      */   {
/*  332 */     return (getTypeId() == 2L) || (getTypeId() == 15L);
/*      */   }
/*      */ 
/*      */   public boolean getIsTextfieldLong()
/*      */   {
/*  338 */     return getTypeId() == 1L;
/*      */   }
/*      */ 
/*      */   public boolean getIsTextareaLong()
/*      */   {
/*  343 */     return getTypeId() == 2L;
/*      */   }
/*      */ 
/*      */   public boolean getIsTextfieldShort()
/*      */   {
/*  348 */     return getTypeId() == 14L;
/*      */   }
/*      */ 
/*      */   public boolean getIsTextareaShort()
/*      */   {
/*  353 */     return getTypeId() == 15L;
/*      */   }
/*      */ 
/*      */   public boolean getIsDatepicker()
/*      */   {
/*  358 */     return getTypeId() == 3L;
/*      */   }
/*      */ 
/*      */   public boolean getIsDateTime()
/*      */   {
/*  363 */     return getTypeId() == 8L;
/*      */   }
/*      */ 
/*      */   public boolean getIsList()
/*      */   {
/*  372 */     return (getIsDropdownList()) || (getIsCheckList()) || (getIsOptionList());
/*      */   }
/*      */ 
/*      */   public boolean getIsDropdownList()
/*      */   {
/*  379 */     return getTypeId() == 4L;
/*      */   }
/*      */ 
/*      */   public boolean getIsCheckList()
/*      */   {
/*  384 */     return getTypeId() == 5L;
/*      */   }
/*      */ 
/*      */   public boolean getIsOptionList()
/*      */   {
/*  389 */     return getTypeId() == 6L;
/*      */   }
/*      */ 
/*      */   public boolean getIsKeywordPicker()
/*      */   {
/*  394 */     return getTypeId() == 7L;
/*      */   }
/*      */ 
/*      */   public boolean getIsExternalDictionary()
/*      */   {
/*  399 */     return getTypeId() == 12L;
/*      */   }
/*      */ 
/*      */   public boolean getIsAutoincrement()
/*      */   {
/*  404 */     return getTypeId() == 11L;
/*      */   }
/*      */ 
/*      */   public boolean getIsDataLookupButton()
/*      */   {
/*  409 */     return getTypeId() == 13L;
/*      */   }
/*      */ 
/*      */   public boolean getIsFlag()
/*      */   {
/*  414 */     return getTypeId() == 99L;
/*      */   }
/*      */ 
/*      */   public boolean getIsHyperlink()
/*      */   {
/*  419 */     return getTypeId() == 9L;
/*      */   }
/*      */ 
/*      */   public boolean getIsGroupHeading()
/*      */   {
/*  424 */     return getTypeId() == 10L;
/*      */   }
/*      */ 
/*      */   public boolean getIsNumeric()
/*      */   {
/*  429 */     return getTypeId() == 16L;
/*      */   }
/*      */ 
/*      */   public boolean getIsTranslatable()
/*      */   {
/*  434 */     return (getIsTextarea()) || (getIsTextfield());
/*      */   }
/*      */ 
/*      */   public Vector<AttributeValue> getListOptionValues()
/*      */   {
/*  440 */     if (this.m_vListOptionValues == null)
/*      */     {
/*  442 */       this.m_vListOptionValues = new Vector();
/*      */     }
/*      */ 
/*  445 */     return this.m_vListOptionValues;
/*      */   }
/*      */ 
/*      */   public void setListOptionValues(Vector<AttributeValue> a_listOptionValues)
/*      */   {
/*  451 */     this.m_vListOptionValues = a_listOptionValues;
/*      */   }
/*      */ 
/*      */   public void addListOptionValue(AttributeValue a_attVal)
/*      */   {
/*  456 */     if (this.m_vListOptionValues == null)
/*      */     {
/*  458 */       this.m_vListOptionValues = new Vector();
/*      */     }
/*      */ 
/*  461 */     this.m_vListOptionValues.add(a_attVal);
/*      */   }
/*      */ 
/*      */   public void clearListOptionValues()
/*      */   {
/*  469 */     if (this.m_vListOptionValues != null)
/*      */     {
/*  471 */       this.m_vListOptionValues.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getNumberOfOptions()
/*      */   {
/*  477 */     return getListOptionValues().size();
/*      */   }
/*      */ 
/*      */   public boolean getHasListOptionValues()
/*      */   {
/*  486 */     return (getTypeId() == 5L) || (getTypeId() == 6L);
/*      */   }
/*      */ 
/*      */   public boolean getKeywordSearchable()
/*      */   {
/*  493 */     return this.m_bKeywordSearchable;
/*      */   }
/*      */ 
/*      */   public void setKeywordSearchable(boolean a_bKeywordSearchable)
/*      */   {
/*  499 */     this.m_bKeywordSearchable = a_bKeywordSearchable;
/*      */   }
/*      */ 
/*      */   public AttributeValue getValue()
/*      */   {
/*  505 */     if (this.m_vValue == null)
/*      */     {
/*  507 */       this.m_vValue = new AttributeValue();
/*      */     }
/*      */ 
/*  510 */     return this.m_vValue;
/*      */   }
/*      */ 
/*      */   public void setValue(AttributeValue a_sValue)
/*      */   {
/*  516 */     this.m_vValue = a_sValue;
/*      */   }
/*      */ 
/*      */   public boolean isMandatory()
/*      */   {
/*  522 */     return this.m_bMandatory;
/*      */   }
/*      */ 
/*      */   public void setMandatory(boolean a_bMandatory)
/*      */   {
/*  528 */     this.m_bMandatory = a_bMandatory;
/*      */   }
/*      */ 
/*      */   public boolean isMandatoryBulkUpload()
/*      */   {
/*  533 */     return this.m_bMandatoryBulkUpload;
/*      */   }
/*      */ 
/*      */   public void setMandatoryBulkUpload(boolean a_bMandatoryBulkUpload)
/*      */   {
/*  538 */     this.m_bMandatoryBulkUpload = a_bMandatoryBulkUpload;
/*      */   }
/*      */ 
/*      */   public boolean isSearchField()
/*      */   {
/*  544 */     return this.m_bSearchField;
/*      */   }
/*      */ 
/*      */   public void setSearchField(boolean a_sSearchField)
/*      */   {
/*  550 */     this.m_bSearchField = a_sSearchField;
/*      */   }
/*      */ 
/*      */   public boolean getIsVisible()
/*      */   {
/*  556 */     return this.m_bIsVisible;
/*      */   }
/*      */ 
/*      */   public void setIsVisible(boolean a_bIsVisible)
/*      */   {
/*  561 */     this.m_bIsVisible = a_bIsVisible;
/*      */   }
/*      */ 
/*      */   public boolean getIsRelevant()
/*      */   {
/*  567 */     return this.m_bIsRelevant;
/*      */   }
/*      */ 
/*      */   public void setIsRelevant(boolean a_sIsRelevant)
/*      */   {
/*  572 */     this.m_bIsRelevant = a_sIsRelevant;
/*      */   }
/*      */ 
/*      */   public boolean getStatic()
/*      */   {
/*  577 */     return this.m_bStatic;
/*      */   }
/*      */ 
/*      */   public void setStatic(boolean a_sStatic) {
/*  581 */     this.m_bStatic = a_sStatic;
/*      */   }
/*      */ 
/*      */   public String getFieldName() {
/*  585 */     return this.m_sFieldName;
/*      */   }
/*      */ 
/*      */   public void setFieldName(String a_sStaticFieldName) {
/*  589 */     this.m_sFieldName = a_sStaticFieldName;
/*      */   }
/*      */ 
/*      */   public boolean getReadOnly()
/*      */   {
/*  594 */     return this.m_bReadOnly;
/*      */   }
/*      */ 
/*      */   public void setReadOnly(boolean a_sReadOnly) {
/*  598 */     this.m_bReadOnly = a_sReadOnly;
/*      */   }
/*      */ 
/*      */   public boolean getIsHtml()
/*      */   {
/*  603 */     return this.m_bIsHtml;
/*      */   }
/*      */ 
/*      */   public void setIsHtml(boolean a_bIsHtml) {
/*  607 */     this.m_bIsHtml = a_bIsHtml;
/*      */   }
/*      */ 
/*      */   public void setMaxDisplayLength(int a_iMaxDisplayLength)
/*      */   {
/*  612 */     this.m_iMaxDisplayLength = a_iMaxDisplayLength;
/*      */   }
/*      */ 
/*      */   public int getMaxDisplayLength()
/*      */   {
/*  617 */     return this.m_iMaxDisplayLength;
/*      */   }
/*      */ 
/*      */   public String getMaxDisplayLengthString()
/*      */   {
/*  622 */     return String.valueOf(getMaxDisplayLength());
/*      */   }
/*      */ 
/*      */   public void setTreeId(long a_lTreeId)
/*      */   {
/*  627 */     this.m_lTreeId = a_lTreeId;
/*      */   }
/*      */ 
/*      */   public long getTreeId()
/*      */   {
/*  632 */     return this.m_lTreeId;
/*      */   }
/*      */ 
/*      */   public boolean getHighlight() {
/*  636 */     return this.m_bHighlight;
/*      */   }
/*      */ 
/*      */   public void setHighlight(boolean a_bHighlight)
/*      */   {
/*  641 */     this.m_bHighlight = a_bHighlight;
/*      */   }
/*      */ 
/*      */   public void setDefaultKeywordFilter(String a_sDefaultKeywordFilter)
/*      */   {
/*  646 */     this.m_sDefaultKeywordFilter = a_sDefaultKeywordFilter;
/*      */   }
/*      */ 
/*      */   public String getDefaultKeywordFilter()
/*      */   {
/*  651 */     return this.m_sDefaultKeywordFilter;
/*      */   }
/*      */ 
/*      */   public String getOnChangeScript()
/*      */   {
/*  656 */     return this.m_sOnChangeScript;
/*      */   }
/*      */ 
/*      */   public void setOnChangeScript(String onChangeScript)
/*      */   {
/*  661 */     this.m_sOnChangeScript = onChangeScript;
/*      */   }
/*      */ 
/*      */   public String getBaseUrl()
/*      */   {
/*  666 */     return this.m_sBaseUrl;
/*      */   }
/*      */ 
/*      */   public void setBaseUrl(String a_sBaseUrl)
/*      */   {
/*  671 */     this.m_sBaseUrl = a_sBaseUrl;
/*      */   }
/*      */ 
/*      */   public boolean isRequiredForCompleteness()
/*      */   {
/*  676 */     return this.m_bRequiredForCompleteness;
/*      */   }
/*      */ 
/*      */   public void setRequiredForCompleteness(boolean a_bRequiredForCompleteness)
/*      */   {
/*  681 */     this.m_bRequiredForCompleteness = a_bRequiredForCompleteness;
/*      */   }
/*      */ 
/*      */   public boolean getHasSearchTokens()
/*      */   {
/*  686 */     return this.m_bHasSearchTokens;
/*      */   }
/*      */ 
/*      */   public void setHasSearchTokens(boolean a_bHasSearchTokens)
/*      */   {
/*  691 */     this.m_bHasSearchTokens = a_bHasSearchTokens;
/*      */   }
/*      */ 
/*      */   public String getTokenDelimiterRegex()
/*      */   {
/*  701 */     if (getIsKeywordPicker())
/*      */     {
/*  703 */       return Pattern.quote(AssetBankSettings.getKeywordDelimiter());
/*      */     }
/*      */ 
/*  707 */     return this.m_sTokenDelimiterRegex;
/*      */   }
/*      */ 
/*      */   public boolean getDelimiterIsSpace()
/*      */   {
/*  713 */     List tokenDelimiters = getTokenDelimiters();
/*  714 */     boolean bDelimiterIsSpace = (tokenDelimiters == null) || (tokenDelimiters.isEmpty()) || (((String)tokenDelimiters.get(0)).equals(" "));
/*      */ 
/*  717 */     return bDelimiterIsSpace;
/*      */   }
/*      */ 
/*      */   public String getDefaultTokenDelimiter()
/*      */   {
/*  727 */     return (String)getTokenDelimiters().get(0);
/*      */   }
/*      */ 
/*      */   public List<String> getTokenDelimiters()
/*      */   {
/*  745 */     if (getIsKeywordPicker())
/*      */     {
/*  747 */       return Collections.singletonList(AssetBankSettings.getKeywordDelimiter());
/*      */     }
/*      */ 
/*  750 */     String sTokenDelimiterRegex = getTokenDelimiterRegex();
/*  751 */     if (StringUtils.isEmpty(sTokenDelimiterRegex))
/*      */     {
/*  753 */       return null;
/*      */     }
/*      */ 
/*  757 */     if ((sTokenDelimiterRegex.length() == 1) && (!sTokenDelimiterRegex.equals(".")))
/*      */     {
/*  760 */       return Collections.singletonList(sTokenDelimiterRegex);
/*      */     }
/*      */ 
/*  763 */     if (sTokenDelimiterRegex.equals("\\."))
/*      */     {
/*  765 */       return Collections.singletonList(".");
/*      */     }
/*      */ 
/*  768 */     if (c_kRangePattern.matcher(sTokenDelimiterRegex).matches())
/*      */     {
/*  770 */       List separators = new ArrayList();
/*      */ 
/*  772 */       for (int i = 1; i < sTokenDelimiterRegex.length() - 1; i++)
/*      */       {
/*  774 */         separators.add(String.valueOf(sTokenDelimiterRegex.charAt(i)));
/*      */       }
/*  776 */       return separators;
/*      */     }
/*      */ 
/*  780 */     return null;
/*      */   }
/*      */ 
/*      */   public Collection<String> getTokenDelimitersJS()
/*      */   {
/*  789 */     Collection<String> delimiters = getTokenDelimiters();
/*  790 */     if (delimiters == null)
/*      */     {
/*  792 */       return null;
/*      */     }
/*      */ 
/*  795 */     Collection escapedDelimiters = new ArrayList(delimiters.size());
/*  796 */     for (String delimiter : delimiters)
/*      */     {
/*  798 */       escapedDelimiters.add(StringUtil.escapeJS(delimiter));
/*      */     }
/*  800 */     return escapedDelimiters;
/*      */   }
/*      */ 
/*      */   public void setTokenDelimiterRegex(String tokenDelimiterRegex)
/*      */   {
/*  805 */     this.m_sTokenDelimiterRegex = tokenDelimiterRegex;
/*      */   }
/*      */ 
/*      */   public boolean getRequiresTranslation()
/*      */   {
/*  810 */     return this.m_bRequiresTranslation;
/*      */   }
/*      */ 
/*      */   public void setRequiresTranslation(boolean a_bRequiresTranslation)
/*      */   {
/*  815 */     this.m_bRequiresTranslation = a_bRequiresTranslation;
/*      */   }
/*      */ 
/*      */   public Vector getTranslations()
/*      */   {
/*  824 */     if (this.m_vTranslations == null)
/*      */     {
/*  826 */       this.m_vTranslations = new Vector();
/*      */     }
/*  828 */     return this.m_vTranslations;
/*      */   }
/*      */ 
/*      */   public void setTranslations(Vector translations)
/*      */   {
/*  833 */     this.m_vTranslations = translations;
/*      */   }
/*      */ 
/*      */   public Translation createTranslation(Language a_language)
/*      */   {
/*  838 */     return new Translation(a_language);
/*      */   }
/*      */ 
/*      */   public void setLanguage(Language a_language)
/*      */   {
/*  843 */     this.m_language = a_language;
/*      */ 
/*  846 */     if ((this.m_vValue != null) && (!this.m_vValue.getLanguage().equals(a_language)))
/*      */     {
/*  848 */       this.m_vValue.setLanguage(a_language);
/*      */     }
/*      */ 
/*  851 */     LanguageUtils.setLanguageOnAll(getListOptionValues(), a_language);
/*      */   }
/*      */ 
/*      */   public boolean getIsDescriptionAttribute()
/*      */   {
/*  873 */     return this.m_bIsDescriptionAttribute;
/*      */   }
/*      */ 
/*      */   public void setIsDescriptionAttribute(boolean a_bIsDescriptionAttribute)
/*      */   {
/*  878 */     this.m_bIsDescriptionAttribute = a_bIsDescriptionAttribute;
/*      */   }
/*      */ 
/*      */   public boolean isFiltered()
/*      */   {
/*  883 */     return this.m_bIsFiltered;
/*      */   }
/*      */ 
/*      */   public void setFiltered(boolean isFiltered)
/*      */   {
/*  888 */     this.m_bIsFiltered = isFiltered;
/*      */   }
/*      */ 
/*      */   public int getHeight()
/*      */   {
/*  893 */     return this.m_iHeight;
/*      */   }
/*      */ 
/*      */   public void setHeight(int height)
/*      */   {
/*  898 */     this.m_iHeight = height;
/*      */   }
/*      */ 
/*      */   public int getMaxSize()
/*      */   {
/*  903 */     return this.m_iMaxSize;
/*      */   }
/*      */ 
/*      */   public void setMaxSize(int maxSize)
/*      */   {
/*  908 */     this.m_iMaxSize = maxSize;
/*      */   }
/*      */ 
/*      */   public int getWidth()
/*      */   {
/*  913 */     return this.m_iWidth;
/*      */   }
/*      */ 
/*      */   public void setWidth(int width)
/*      */   {
/*  918 */     this.m_iWidth = width;
/*      */   }
/*      */ 
/*      */   public boolean getIsExtendableList()
/*      */   {
/*  923 */     return this.m_bExtendableList;
/*      */   }
/*      */ 
/*      */   public void setIsExtendableList(boolean a_bExtendableList)
/*      */   {
/*  928 */     this.m_bExtendableList = a_bExtendableList;
/*      */   }
/*      */ 
/*      */   public void setSeed(long a_lSeed)
/*      */   {
/*  933 */     this.m_lSeed = a_lSeed;
/*      */   }
/*      */ 
/*      */   public long getSeed()
/*      */   {
/*  938 */     return this.m_lSeed;
/*      */   }
/*      */ 
/*      */   public void setIncrement(int a_iIncrement)
/*      */   {
/*  943 */     this.m_iIncrement = a_iIncrement;
/*      */   }
/*      */ 
/*      */   public int getIncrement()
/*      */   {
/*  948 */     return this.m_iIncrement;
/*      */   }
/*      */ 
/*      */   public boolean getDataFromChildren()
/*      */   {
/*  953 */     return this.m_bDataFromChildren;
/*      */   }
/*      */ 
/*      */   public void setDataFromChildren(boolean getDataFromParent)
/*      */   {
/*  958 */     this.m_bDataFromChildren = getDataFromParent;
/*      */   }
/*      */ 
/*      */   public boolean isSearchBuilderField()
/*      */   {
/*  963 */     return this.m_bSearchBuilderField;
/*      */   }
/*      */ 
/*      */   public void setSearchBuilderField(boolean searchBuilderField)
/*      */   {
/*  968 */     this.m_bSearchBuilderField = searchBuilderField;
/*      */   }
/*      */ 
/*      */   public boolean getIncludeInSearchForChild()
/*      */   {
/*  973 */     return this.m_bIncludeInSearchForChild;
/*      */   }
/*      */ 
/*      */   public void setIncludeInSearchForChild(boolean a_sIncludeInSearchForChild)
/*      */   {
/*  978 */     this.m_bIncludeInSearchForChild = a_sIncludeInSearchForChild;
/*      */   }
/*      */ 
/*      */   public boolean getShowOnChild()
/*      */   {
/*  983 */     return this.m_bShowOnChild;
/*      */   }
/*      */ 
/*      */   public void setShowOnChild(boolean a_sShowOnChild)
/*      */   {
/*  988 */     this.m_bShowOnChild = a_sShowOnChild;
/*      */   }
/*      */ 
/*      */   public boolean getHideForSort()
/*      */   {
/*  993 */     return this.m_bHideForSort;
/*      */   }
/*      */ 
/*      */   public void setHideForSort(boolean a_bHideForSort)
/*      */   {
/*  998 */     this.m_bHideForSort = a_bHideForSort;
/*      */   }
/*      */ 
/*      */   public String getPrefix()
/*      */   {
/* 1003 */     return this.m_sPrefix;
/*      */   }
/*      */ 
/*      */   public void setPrefix(String a_sPrefix)
/*      */   {
/* 1008 */     this.m_sPrefix = a_sPrefix;
/*      */   }
/*      */ 
/*      */   public int getPadWidth()
/*      */   {
/* 1013 */     return this.m_iPadWidth;
/*      */   }
/*      */ 
/*      */   public void setPadWidth(int a_sPadWidth)
/*      */   {
/* 1018 */     this.m_iPadWidth = a_sPadWidth;
/*      */   }
/*      */ 
/*      */   public boolean getSelected()
/*      */   {
/* 1023 */     return this.m_bSelected;
/*      */   }
/*      */ 
/*      */   public void setSelected(boolean a_bSelected)
/*      */   {
/* 1028 */     this.m_bSelected = a_bSelected;
/*      */   }
/*      */ 
/*      */   public boolean isAutoComplete()
/*      */   {
/* 1033 */     return this.m_bAutoComplete;
/*      */   }
/*      */ 
/*      */   public void setAutoComplete(boolean a_bAutoComplete)
/*      */   {
/* 1038 */     this.m_bAutoComplete = a_bAutoComplete;
/*      */   }
/*      */ 
/*      */   public void setPluginClass(String a_sPluginClass)
/*      */   {
/* 1043 */     this.m_sPluginClass = a_sPluginClass;
/*      */   }
/*      */ 
/*      */   public String getPluginClass()
/*      */   {
/* 1048 */     return this.m_sPluginClass;
/*      */   }
/*      */ 
/*      */   public void setAttributeIdsForPluginParams(String a_sAttributeIdsForPluginParams)
/*      */   {
/* 1053 */     this.m_sAttributeIdsForPluginParams = a_sAttributeIdsForPluginParams;
/*      */   }
/*      */ 
/*      */   public String getAttributeIdsForPluginParams()
/*      */   {
/* 1058 */     return this.m_sAttributeIdsForPluginParams;
/*      */   }
/*      */ 
/*      */   public String getValueColumnName()
/*      */   {
/* 1063 */     return this.m_sValueColumnName;
/*      */   }
/*      */ 
/*      */   public void setValueColumnName(String a_valueColumnName)
/*      */   {
/* 1068 */     this.m_sValueColumnName = a_valueColumnName;
/*      */   }
/*      */ 
/*      */   public boolean getShowOnDownload()
/*      */   {
/* 1073 */     return this.m_bShowOnDownload;
/*      */   }
/*      */ 
/*      */   public void setShowOnDownload(boolean a_bShowOnDownload)
/*      */   {
/* 1078 */     this.m_bShowOnDownload = a_bShowOnDownload;
/*      */   }
/*      */ 
/*      */   public int getMaxDecimalPlaces()
/*      */   {
/* 1083 */     return this.m_iMaxDecimalPlaces;
/*      */   }
/*      */ 
/*      */   public void setMaxDecimalPlaces(int a_iMaxDecimalPlaces)
/*      */   {
/* 1088 */     this.m_iMaxDecimalPlaces = a_iMaxDecimalPlaces;
/*      */   }
/*      */ 
/*      */   public int getMinDecimalPlaces()
/*      */   {
/* 1093 */     return this.m_iMinDecimalPlaces;
/*      */   }
/*      */ 
/*      */   public void setMinDecimalPlaces(int a_iMinDecimalPlaces)
/*      */   {
/* 1098 */     this.m_iMinDecimalPlaces = a_iMinDecimalPlaces;
/*      */   }
/*      */ 
/*      */   public void setHidden(boolean a_bHidden)
/*      */   {
/* 1103 */     this.m_bHidden = a_bHidden;
/*      */   }
/*      */ 
/*      */   public boolean isHidden()
/*      */   {
/* 1108 */     return this.m_bHidden;
/*      */   }
/*      */ 
/*      */   public class Translation extends BaseAttribute
/*      */     implements com.bright.framework.language.bean.Translation
/*      */   {
/*      */     public Translation(Language a_language)
/*      */     {
/*  862 */       this.m_language = a_language;
/*      */     }
/*      */ 
/*      */     public long getAttributeId()
/*      */     {
/*  867 */       return Attribute.this.getId();
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.bean.Attribute
 * JD-Core Version:    0.6.0
 */