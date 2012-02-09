/*     */ package com.bright.assetbank.custom.lfb.plugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.attribute.plugin.BeanLookupPlugin;
/*     */ import com.bright.assetbank.custom.lfb.constant.LFBSettings;
/*     */ import java.net.URL;
/*     */ import org.apache.axis.types.NonNegativeInteger;
/*     */ import org.apache.commons.logging.Log;
/*     */ import uk.gov.london_fire.www.Ims.DimsIncidentDataWebService.DimsIncidentDataWebServiceLocator;
/*     */ import uk.gov.london_fire.www.Ims.DimsIncidentDataWebService.DimsIncidentDataWebServiceSoapStub;
/*     */ import uk.gov.london_fire.www.schemas.IIMS.DIMS.IncidentData.IncidentDataRequestStructure;
/*     */ import uk.gov.london_fire.www.schemas.IIMS.DIMS.IncidentData.IncidentDataResponseStructure;
/*     */ import uk.gov.london_fire.www.schemas.IIMS.DIMS.IncidentData.IncidentStructure;
/*     */ 
/*     */ public class IncidentLookupPlugin extends BeanLookupPlugin
/*     */ {
/*  47 */   private DimsIncidentDataWebServiceSoapStub m_service = null;
/*     */   public static final String c_ksClassName = "IncidentLookupPlugin";
/*     */ 
/*     */   public IncidentLookupPlugin()
/*     */     throws Bn2Exception
/*     */   {
/*  57 */     initialise();
/*     */   }
/*     */ 
/*     */   public void initialise() throws Bn2Exception
/*     */   {
/*  62 */     String ksMethodName = "initialise";
/*     */     try
/*     */     {
/*  67 */       String sUrl = LFBSettings.getIncidentLookupWebserviceUrl();
/*  68 */       GlobalApplication.getInstance().getLogger().debug("IncidentLookupPlugin.initialise: Got IMS webservice URL: " + sUrl);
/*     */ 
/*  70 */       this.m_service = new DimsIncidentDataWebServiceSoapStub(new URL(sUrl), new DimsIncidentDataWebServiceLocator());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  74 */       String sMessage = "IncidentLookupPlugin: Error starting incident lookup plugin: " + e.getMessage();
/*  75 */       GlobalApplication.getInstance().getLogger().error(sMessage);
/*  76 */       throw new Bn2Exception(sMessage, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Object getBean(String a_sRequestParams)
/*     */     throws Exception
/*     */   {
/*  94 */     String ksMethodName = "getBean";
/*  95 */     IncidentStructure incident = null;
/*     */ 
/*  98 */     GlobalApplication.getInstance().getLogger().debug("IncidentLookupPlugin.getBean: Request parameters: " + a_sRequestParams);
/*  99 */     NonNegativeInteger nniIncidentNo = new NonNegativeInteger(a_sRequestParams);
/*     */ 
/* 101 */     GlobalApplication.getInstance().getLogger().debug("IncidentLookupPlugin.getBean: Incident no created: " + nniIncidentNo.intValue());
/*     */ 
/* 105 */     IncidentDataRequestStructure incidentRequest = new IncidentDataRequestStructure(nniIncidentNo);
/* 106 */     IncidentDataResponseStructure incidentResponse = this.m_service.incidentSearch(incidentRequest);
/*     */ 
/* 108 */     if (incidentResponse != null)
/*     */     {
/* 110 */       incident = incidentResponse.getIncident();
/*     */     }
/*     */ 
/* 113 */     return incident;
/*     */   }
/*     */ 
/*     */   protected String getAttributeMappings()
/*     */   {
/* 120 */     return LFBSettings.getIncidentLookupAttributeMappings();
/*     */   }
/*     */ 
/*     */   protected String getAttributeMappingsDelimiter()
/*     */   {
/* 126 */     return ",";
/*     */   }
/*     */ 
/*     */   protected String getAttributeMappingDelimiter()
/*     */   {
/* 132 */     return "=>";
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.lfb.plugin.IncidentLookupPlugin
 * JD-Core Version:    0.6.0
 */