/*     */ package com.bright.assetbank.synchronise.bean.impl;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.entity.relationship.service.AssetRelationshipManager;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.synchronise.bean.ExternalDataSynchronisationTask;
/*     */ import com.bright.assetbank.synchronise.constant.SynchronisationConstants;
/*     */ import com.bright.assetbank.synchronise.constant.impl.AdlibSynchronisationSettings;
/*     */ import com.bright.assetbank.synchronise.service.AssetImportManager;
/*     */ import com.bright.assetbank.synchronise.service.SimpleFileImportManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.service.RefDataManager;
/*     */ import com.bright.framework.util.ClientHttpRequest;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class AdlibSynchronisationTask extends ExternalDataSynchronisationTask
/*     */   implements SynchronisationConstants
/*     */ {
/*     */   private static final String c_ksClassName = "AdlibSynchronisationTask";
/*  68 */   private SimpleFileImportManager m_sfiManager = null;
/*  69 */   private AssetImportManager m_importManager = null;
/*  70 */   private ABUserManager m_userManager = null;
/*  71 */   private AssetRelationshipManager m_assetRelationshipManager = null;
/*  72 */   private RefDataManager m_refDataManager = null;
/*  73 */   protected MultiLanguageSearchManager m_searchManager = null;
/*     */ 
/*     */   public void run()
/*     */   {
/*  78 */     String ksMethodName = "run";
/*  79 */     GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: About to run Adlib Synchronisation");
/*     */ 
/*  81 */     initialiseComponents();
/*     */ 
/*  84 */     String sUrl = ((AdlibSynchronisationSettings)this.m_settings).getAdlibXMLUrl();
/*     */ 
/*  87 */     String sDate = null;
/*     */     try
/*     */     {
/*  91 */       sDate = this.m_refDataManager.getSystemSetting("LastSynchDate");
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/*  95 */       GlobalApplication.getInstance().getLogger().error("AdlibSynchronisationTask.run: Unable to get last synch date setting: " + e.getMessage());
/*     */     }
/*     */ 
/*  99 */     if (!StringUtil.stringIsPopulated(sDate))
/*     */     {
/* 101 */       sDate = getUrlDateString(-1);
/*     */     }
/*     */ 
/* 104 */     sUrl = sUrl + sDate;
/*     */     try
/*     */     {
/* 109 */       sDate = getUrlDateString(0);
/* 110 */       this.m_refDataManager.setSystemSetting(null, "LastSynchDate", sDate);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 114 */       GlobalApplication.getInstance().getLogger().error("AdlibSynchronisationTask.run: Unable to set last synch date setting: " + e.getMessage());
/*     */     }
/*     */ 
/* 117 */     GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Adlib url: " + sUrl);
/*     */ 
/* 120 */     AdlibXMLFileListReader reader = new AdlibXMLFileListReader(sUrl, getSettings());
/*     */     try
/*     */     {
/* 126 */       HashMap hmAssetIds = this.m_sfiManager.importNewFiles(reader);
/*     */ 
/* 129 */       this.m_sfiManager.updateExistingFiles(reader);
/*     */ 
/* 132 */       AdlibXMLBeanReader beanReader = new AdlibXMLBeanReader(sUrl, hmAssetIds, getSettings(), true);
/* 133 */       ABUser user = this.m_userManager.getApplicationUser();
/*     */ 
/* 136 */       Vector vecUpdatedAssets = this.m_importManager.importAssetData(null, user.getId(), beanReader, "", false, false, 0L, false, true);
/* 137 */       if ((vecUpdatedAssets != null) && (vecUpdatedAssets.size() > 0))
/*     */       {
/* 139 */         GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Imported " + vecUpdatedAssets.size() + " assets");
/*     */ 
/* 141 */         if (((AdlibSynchronisationSettings)this.m_settings).getCallbackToAdlib())
/*     */         {
/* 143 */           int iCount = 0;
/*     */ 
/* 146 */           for (int i = 0; i < vecUpdatedAssets.size(); i++)
/*     */           {
/* 148 */             String sPostXML = "<" + beanReader.getRootElementName() + ">" + "<" + beanReader.getRecordsElementName() + ">";
/*     */ 
/* 151 */             Asset asset = (Asset)vecUpdatedAssets.elementAt(i);
/*     */             try
/*     */             {
/* 156 */               String sCallbackXML = beanReader.getCallbackXMLForId(asset.getId(), null);
/* 157 */               sPostXML = sPostXML + sCallbackXML + "</" + beanReader.getRecordsElementName() + ">" + "</" + beanReader.getRootElementName() + ">";
/* 158 */               doXMLPost(sPostXML);
/* 159 */               iCount++;
/* 160 */               GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Did callback with xml: " + sPostXML);
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 164 */               GlobalApplication.getInstance().getLogger().error("AdlibSynchronisationTask.run: Error unable to perform Adlib callback with asset : " + asset.getId(), e);
/*     */             }
/*     */           }
/*     */ 
/* 168 */           GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Did callback for " + iCount + " assets");
/*     */         }
/*     */       }
/*     */ 
/* 172 */       if (((AdlibSynchronisationSettings)this.m_settings).getSynchroniseRelatedAssets())
/*     */       {
/* 174 */         GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Synchronising related assets");
/*     */ 
/* 177 */         int iRelLimit = ((AdlibSynchronisationSettings)this.m_settings).getSynchroniseRelatedAssetsLimit();
/*     */ 
/* 180 */         HashMap hmRelationshipMap = this.m_assetRelationshipManager.getAssetRelationshipMap(null);
/* 181 */         HashMap hmPrirefMap = beanReader.getPrirefHash();
/*     */ 
/* 184 */         Set keys = hmPrirefMap.keySet();
/* 185 */         Iterator itKeys = keys.iterator();
/* 186 */         String sPostXML = "";
/* 187 */         String sStartXML = "<" + beanReader.getRootElementName() + ">" + "<" + beanReader.getRecordsElementName() + ">";
/* 188 */         String sEndXML = "</" + beanReader.getRecordsElementName() + ">" + "</" + beanReader.getRootElementName() + ">";
/* 189 */         int iCount = 0;
/*     */ 
/* 191 */         while (itKeys.hasNext())
/*     */         {
/* 193 */           Long longAssetBankId = (Long)itKeys.next();
/* 194 */           GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Adding related assets switch to xml for asset : " + longAssetBankId.longValue());
/*     */ 
/* 196 */           if (hmRelationshipMap.get(longAssetBankId) != null)
/*     */           {
/* 198 */             boolean bHasRelatedAssets = ((Boolean)hmRelationshipMap.get(longAssetBankId)).booleanValue();
/* 199 */             String sPriref = (String)hmPrirefMap.get(longAssetBankId);
/*     */ 
/* 201 */             GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Asset " + longAssetBankId.longValue() + " has a priref of " + sPriref);
/*     */ 
/* 203 */             if (StringUtil.stringIsPopulated(sPriref))
/*     */             {
/* 205 */               sPostXML = sPostXML + "<" + beanReader.getRecordElementName() + ">";
/* 206 */               sPostXML = sPostXML + "<" + ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNamePriref() + ">" + sPriref + "</" + ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNamePriref() + ">";
/* 207 */               sPostXML = sPostXML + "<" + ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNameAssetBankRelationships() + ">";
/* 208 */               if (bHasRelatedAssets)
/*     */               {
/* 210 */                 GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Asset " + longAssetBankId.longValue() + " has related assets");
/* 211 */                 sPostXML = sPostXML + "x";
/*     */               }
/* 213 */               sPostXML = sPostXML + "</" + ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNameAssetBankRelationships() + ">";
/* 214 */               sPostXML = sPostXML + "</" + beanReader.getRecordElementName() + ">";
/* 215 */               iCount++;
/*     */               try
/*     */               {
/* 219 */                 sPostXML = sStartXML + sPostXML + sEndXML;
/* 220 */                 doXMLPost(sPostXML);
/* 221 */                 GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Posted xml: " + sPostXML);
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 225 */                 GlobalApplication.getInstance().getLogger().error("AdlibSynchronisationTask.run: Unable to post the following related asset xml: " + sPostXML, e);
/*     */               }
/*     */             }
/*     */ 
/* 229 */             sPostXML = "";
/*     */ 
/* 231 */             if ((iRelLimit > 0) && (iCount >= iRelLimit))
/*     */             {
/*     */               break;
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 238 */             GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Asset with id : " + longAssetBankId.longValue() + " has no related asset switch in the map");
/*     */           }
/*     */         }
/*     */ 
/* 242 */         GlobalApplication.getInstance().getLogger().debug("AdlibSynchronisationTask.run: Updated related assets field for " + iCount + " adlib assets");
/*     */ 
/* 245 */         this.m_searchManager.queueRebuildIndex(true, -1L);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 251 */       GlobalApplication.getInstance().getLogger().error("AdlibSynchronisationTask.run: Error performing Adlib synchronisation", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doXMLPost(String a_sXML)
/*     */     throws IOException
/*     */   {
/* 258 */     URL callbackUrl = new URL(((AdlibSynchronisationSettings)this.m_settings).getAdlibCallback());
/* 259 */     ClientHttpRequest request = new ClientHttpRequest(callbackUrl);
/* 260 */     request.setParameter("DATABASE", ((AdlibSynchronisationSettings)this.m_settings).getAdlibDatabaseName());
/* 261 */     request.setParameter("DATA", a_sXML);
/* 262 */     request.setParameter("isutf8", "1");
/* 263 */     request.post();
/*     */   }
/*     */ 
/*     */   private String getUrlDateString(int a_iOffset)
/*     */   {
/* 270 */     String sDate = "";
/* 271 */     GregorianCalendar now = new GregorianCalendar();
/* 272 */     now.add(6, a_iOffset);
/* 273 */     int iYear = now.get(1);
/* 274 */     int iMonth = now.get(2) + 1;
/* 275 */     int iDay = now.get(5);
/* 276 */     sDate = sDate + iYear + "-";
/*     */ 
/* 278 */     if (iMonth <= 9)
/*     */     {
/* 280 */       sDate = sDate + "0";
/*     */     }
/* 282 */     sDate = sDate + iMonth + "-";
/*     */ 
/* 284 */     if (iDay <= 9)
/*     */     {
/* 286 */       sDate = sDate + "0";
/*     */     }
/* 288 */     sDate = sDate + iDay;
/*     */ 
/* 290 */     return sDate;
/*     */   }
/*     */ 
/*     */   private void initialiseComponents()
/*     */   {
/* 296 */     String ksMethodName = "initialiseComponents";
/*     */ 
/* 298 */     if ((this.m_sfiManager == null) || (this.m_importManager == null) || (this.m_userManager == null))
/*     */     {
/*     */       try
/*     */       {
/* 302 */         this.m_sfiManager = ((SimpleFileImportManager)GlobalApplication.getInstance().getComponentManager().lookup("SimpleFileImportManager"));
/*     */ 
/* 305 */         this.m_importManager = ((AssetImportManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetImportManager"));
/*     */ 
/* 308 */         this.m_userManager = ((ABUserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager"));
/*     */ 
/* 311 */         this.m_assetRelationshipManager = ((AssetRelationshipManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetRelationshipManager"));
/*     */ 
/* 314 */         this.m_refDataManager = ((RefDataManager)GlobalApplication.getInstance().getComponentManager().lookup("RefDataManager"));
/*     */ 
/* 317 */         this.m_searchManager = ((MultiLanguageSearchManager)GlobalApplication.getInstance().getComponentManager().lookup("SearchManager"));
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/* 322 */         throw new RuntimeException("AdlibSynchronisationTask.initialiseComponents : Couldn't find component ", ce);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.impl.AdlibSynchronisationTask
 * JD-Core Version:    0.6.0
 */