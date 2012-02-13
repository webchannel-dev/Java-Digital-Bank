/*     */ package com.bright.assetbank.opengis.service;
/*     */ 
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.opengis.exception.OpenGisServiceException;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class OpenGisCswManagerLoader extends Bn2Manager
/*     */   implements OpenGisCswManager
/*     */ {
/*  36 */   private Log m_logger = GlobalApplication.getInstance().getLogger();
/*     */   private AttributeManager m_attributeManager;
/*     */   private AssetManager m_assetManager;
/*     */   private MultiLanguageSearchManager m_searchManager;
/*     */   private ABUserManager m_userManager;
/*  43 */   private OpenGisCswManager m_delegate = null;
/*     */ 
/*     */   public Element describeRecord(Element a_request, boolean aBUseDefaultNamespace) throws OpenGisServiceException
/*     */   {
/*  47 */     return getDelegate().describeRecord(a_request, aBUseDefaultNamespace);
/*     */   }
/*     */ 
/*     */   public Element getCapabilities(Element a_request, boolean aBUseDefaultNamespace) throws OpenGisServiceException
/*     */   {
/*  52 */     return getDelegate().getCapabilities(a_request, aBUseDefaultNamespace);
/*     */   }
/*     */ 
/*     */   public Element getRecordById(Element a_request, boolean aBUseDefaultNamespace) throws OpenGisServiceException
/*     */   {
/*  57 */     return getDelegate().getRecordById(a_request, aBUseDefaultNamespace);
/*     */   }
/*     */ 
/*     */   public Element getRecords(Element a_request, boolean aBUseDefaultNamespace) throws OpenGisServiceException
/*     */   {
/*  62 */     return getDelegate().getRecords(a_request, aBUseDefaultNamespace);
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/*  67 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/*  72 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_searchManager)
/*     */   {
/*  77 */     this.m_searchManager = a_searchManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/*  82 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   private synchronized OpenGisCswManager getDelegate()
/*     */   {
/*  87 */     if (this.m_delegate == null)
/*     */     {
/*     */       try
/*     */       {
/*  91 */         this.m_delegate = ((OpenGisCswManager)getClass().getClassLoader().loadClass(getClass().getPackage().getName() + ".OpenGisCswManagerImpl").newInstance());
/*     */ 
/*  94 */         this.m_delegate.setAssetManager(this.m_assetManager);
/*  95 */         this.m_delegate.setAttributeManager(this.m_attributeManager);
/*  96 */         this.m_delegate.setSearchManager(this.m_searchManager);
/*  97 */         this.m_delegate.setUserManager(this.m_userManager);
/*     */       }
/*     */       catch (InstantiationException e)
/*     */       {
/* 101 */         this.m_logger.error("Exception dynamically loading OpenGisCswManagerImpl: " + e.getMessage(), e);
/*     */       }
/*     */       catch (IllegalAccessException e)
/*     */       {
/* 105 */         this.m_logger.error("Exception dynamically loading OpenGisCswManagerImpl: " + e.getMessage(), e);
/*     */       }
/*     */       catch (ClassNotFoundException e)
/*     */       {
/* 109 */         this.m_logger.error("Exception dynamically loading OpenGisCswManagerImpl: " + e.getMessage(), e);
/*     */       }
/*     */     }
/* 112 */     return this.m_delegate;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.opengis.service.OpenGisCswManagerLoader
 * JD-Core Version:    0.6.0
 */