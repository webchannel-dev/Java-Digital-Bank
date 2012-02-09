/*     */ package com.bright.assetbank.opengis.constant;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.common.constant.Settings;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class OpenGisApiSettings extends Settings
/*     */ {
/*  36 */   private static OpenGisApiSettings m_instance = new OpenGisApiSettings("OpenGisApiSettings");
/*     */ 
/*  38 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */ 
/*  40 */   private Map<String, String> m_mappedPropertyNameToAttributeId = new HashMap(50);
/*     */ 
/*  43 */   private Map<String, String> m_mappedAttributeIdToPropertyName = new HashMap(50);
/*     */ 
/*     */   private OpenGisApiSettings(String a_sSettingsFilename)
/*     */   {
/*  47 */     super(a_sSettingsFilename);
/*     */ 
/*  49 */     String[] asMappedAtts = getStringSetting("csw-record-property-mappings").split(" *, *");
/*  50 */     for (int i = 0; i < asMappedAtts.length; i++)
/*     */     {
/*  52 */       String[] asNameAndId = asMappedAtts[i].split(" *= *");
/*     */ 
/*  54 */       if (asNameAndId.length == 2)
/*     */       {
/*  56 */         this.m_mappedPropertyNameToAttributeId.put(asNameAndId[0], asNameAndId[1]);
/*  57 */         this.m_mappedAttributeIdToPropertyName.put(asNameAndId[1], asNameAndId[0]);
/*     */       }
/*     */       else
/*     */       {
/*  61 */         this.m_logger.warn(getClass().getSimpleName() + " : Could not parse '" + asMappedAtts[i] + "' as a property name and attribute id pair.");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServerName()
/*     */   {
/*  70 */     return m_instance.getStringSetting("csw-server-name");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceProviderName()
/*     */   {
/*  75 */     return m_instance.getStringSetting("csw-service-provider-name");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceContactName()
/*     */   {
/*  80 */     return m_instance.getStringSetting("csw-service-contact-name");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceContactPosition()
/*     */   {
/*  85 */     return m_instance.getStringSetting("csw-service-contact-position");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceContactRoleCodeSpace()
/*     */   {
/*  90 */     return m_instance.getStringSetting("csw-service-contact-role-code-space");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceContactRoleCodeValue()
/*     */   {
/*  95 */     return m_instance.getStringSetting("csw-service-contact-role-code-value");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceContactInstuctions()
/*     */   {
/* 100 */     return m_instance.getStringSetting("csw-service-contact-instructions");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceContactHoursOfService()
/*     */   {
/* 105 */     return m_instance.getStringSetting("csw-service-contact-hours-of-service");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceContactTelephone()
/*     */   {
/* 110 */     return m_instance.getStringSetting("csw-service-contact-telephone");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceContactFax()
/*     */   {
/* 115 */     return m_instance.getStringSetting("csw-service-contact-fax");
/*     */   }
/*     */ 
/*     */   public static String getOpenGisServiceContactEmail()
/*     */   {
/* 120 */     return m_instance.getStringSetting("csw-service-contact-email");
/*     */   }
/*     */ 
/*     */   public static boolean getIncludeUnsubmittedAssets()
/*     */   {
/* 125 */     return m_instance.getBooleanSetting("csw-include-unsubmitted");
/*     */   }
/*     */ 
/*     */   public static boolean getIncludeIncompleteAssets()
/*     */   {
/* 130 */     return m_instance.getBooleanSetting("csw-include-incomplete");
/*     */   }
/*     */ 
/*     */   public static String getAttributeForPropertyName(String a_sPropertyName)
/*     */   {
/* 135 */     return (String)m_instance.m_mappedPropertyNameToAttributeId.get(a_sPropertyName);
/*     */   }
/*     */ 
/*     */   public static String getPropertyNameForAttribtueId(String a_sAttId)
/*     */   {
/* 140 */     return (String)m_instance.m_mappedAttributeIdToPropertyName.get(a_sAttId);
/*     */   }
/*     */ 
/*     */   public static long getPermissionGroupId()
/*     */   {
/* 145 */     return m_instance.getLongSetting("csw-permission-group-id");
/*     */   }
/*     */ 
/*     */   public static String getKeywordAttributeValueSeparator()
/*     */   {
/* 150 */     return m_instance.getStringSetting("csw-keyword-attribute-value-separator").replace('_', ' ');
/*     */   }
/*     */ 
/*     */   public static String getKeywordAttributeHierarchySeparator()
/*     */   {
/* 155 */     return m_instance.getStringSetting("csw-keyword-attribute-hierarchy-separator").replace('_', ' ');
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.constant.OpenGisApiSettings
 * JD-Core Version:    0.6.0
 */