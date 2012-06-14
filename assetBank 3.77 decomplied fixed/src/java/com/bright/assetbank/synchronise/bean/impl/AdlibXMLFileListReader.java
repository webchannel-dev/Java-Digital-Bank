/*    */ package com.bright.assetbank.synchronise.bean.impl;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.service.AssetManager;
/*    */ import com.bright.assetbank.synchronise.bean.XMLFileListReader;
/*    */ import com.bright.assetbank.synchronise.constant.ExternalDataSynchronisationSettings;
/*    */ import com.bright.assetbank.synchronise.constant.impl.AdlibSynchronisationSettings;
/*    */ import com.bright.assetbank.synchronise.util.impl.AdlibSynchUtil;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ public class AdlibXMLFileListReader extends XMLFileListReader
/*    */ {
/*    */   public AdlibXMLFileListReader(String a_sXMLUrl, ExternalDataSynchronisationSettings a_settings)
/*    */   {
/* 40 */     super(a_sXMLUrl, a_settings);
/*    */   }
/*    */ 
/*    */   protected long existingAssetId(Element a_recordElement) throws Bn2Exception
/*    */   {
/* 45 */     String sId = a_recordElement.getChildText(((AdlibSynchronisationSettings)this.m_settings).getXMLElementNameAssetBankId());
/* 46 */     long lId = -1L;
/*    */     try
/*    */     {
/* 50 */       lId = Long.parseLong(sId);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */     }
/*    */ 
/* 57 */     if (lId > 0L)
/*    */     {
/* 59 */       boolean bExists = getAssetManager().doesAssetExist(null, lId);
/*    */ 
/* 61 */       if (bExists)
/*    */       {
/* 63 */         return lId;
/*    */       }
/*    */     }
/*    */ 
/* 67 */     return 0L;
/*    */   }
/*    */ 
/*    */   protected String getFileName(Element a_recordElement)
/*    */   {
/* 72 */     String sFile = AdlibSynchUtil.getAdlibReproductionValue(a_recordElement, ((AdlibSynchronisationSettings)this.m_settings).getXMLElementNameReproductionUrl(), (AdlibSynchronisationSettings)this.m_settings);
/* 73 */     return sFile;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.impl.AdlibXMLFileListReader
 * JD-Core Version:    0.6.0
 */