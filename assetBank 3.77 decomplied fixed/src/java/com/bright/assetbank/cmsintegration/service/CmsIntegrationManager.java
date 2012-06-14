/*     */ package com.bright.assetbank.cmsintegration.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2Manager;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeValue;
/*     */ import com.bright.assetbank.cmsintegration.bean.CmsInfo;
/*     */ import com.bright.assetbank.cmsintegration.bean.CmsUploadResult;
/*     */ import com.bright.assetbank.cmsintegration.util.FileTransferHelper;
/*     */ import com.bright.assetbank.cmsintegration.util.FileTransferHelperFactory;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.jdom.CDATA;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.output.Format;
/*     */ import org.jdom.output.XMLOutputter;
/*     */ 
/*     */ public class CmsIntegrationManager extends Bn2Manager
/*     */ {
/*     */   private static final String k_sClassName = "CmsIntegrationManager";
/*  54 */   private int m_iSubFolderCount = 0;
/*  55 */   private Object m_oSubFolderLock = new Object();
/*  56 */   private HashMap<Long, String> m_cmsAttributes = new HashMap();
/*     */ 
/*     */   public void startup()
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     generateCmsAttributes();
/*     */   }
/*     */ 
/*     */   public CmsUploadResult sendFileToCms(CmsInfo a_cmsInfo, String a_sSource, String a_sFilename, boolean a_bDeleteDownloadedFile)
/*     */     throws Bn2Exception
/*     */   {
/*  87 */     CmsUploadResult result = null;
/*     */ 
/*  89 */     if (a_cmsInfo == null)
/*     */     {
/*  91 */       throw new Bn2Exception("CmsIntegrationManager.sendFileToCms: a_cmsInfo is null");
/*     */     }
/*     */ 
/*  95 */     String sFilename = FileUtil.getSafeFilename(a_sFilename, true);
/*     */ 
/*  97 */     int iRepositoryNumber = a_cmsInfo.getRepositoryNumber();
/*     */ 
/* 100 */     String sDirectory = AssetBankSettings.getCmsRepositoryPath(iRepositoryNumber);
/*     */ 
/* 102 */     String sSubFolder = null;
/*     */ 
/* 105 */     if (a_cmsInfo.getSubRepository() != null)
/*     */     {
/* 107 */       sSubFolder = a_cmsInfo.getSubRepository();
/*     */     }
/* 109 */     else if (AssetBankSettings.getCmsAutoSubfolderCount(iRepositoryNumber) > 0)
/*     */     {
/* 112 */       sSubFolder = getAutoSubfolderName(AssetBankSettings.getCmsAutoSubfolderCount(iRepositoryNumber));
/*     */     }
/*     */ 
/* 115 */     result = new CmsUploadResult();
/*     */ 
/* 118 */     if (sSubFolder != null)
/*     */     {
/* 120 */       sDirectory = sDirectory + "/" + sSubFolder;
/* 121 */       result.setSubfolder(sSubFolder);
/*     */     }
/*     */ 
/* 124 */     FileTransferHelper ftHelper = FileTransferHelperFactory.createHelper(AssetBankSettings.getCmsFileTransferMethod());
/* 125 */     String sCmsFilename = null;
/*     */     try
/*     */     {
/* 129 */       if (ftHelper.requiresConnection())
/*     */       {
/* 131 */         ftHelper.connect(AssetBankSettings.getCmsRemoteHost(), AssetBankSettings.getCmsRemotePort(), AssetBankSettings.getCmsRemoteUsername(), AssetBankSettings.getCmsRemotePassword());
/*     */       }
/*     */ 
/* 134 */       sCmsFilename = ftHelper.copyFile(a_sSource, sDirectory, sFilename, AssetBankSettings.getCmsMakeDestinationFilenamesUnique());
/*     */ 
/* 137 */       result.setRemoteDirectory(sDirectory);
/* 138 */       result.setRemoteFilename(sCmsFilename);
/*     */ 
/* 140 */       if (a_bDeleteDownloadedFile)
/*     */       {
/* 142 */         File file = new File(a_sSource);
/* 143 */         file.delete();
/* 144 */         FileUtil.logFileDeletion(file);
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 149 */       this.m_logger.error("CmsIntegrationManager.sendFileToCms() : IOException whilst transfering file to CMS using " + ftHelper.getClass().getSimpleName() + " : " + e.getLocalizedMessage(), e);
/* 150 */       throw new Bn2Exception("CmsIntegrationManager.sendFileToCms() : IOException whilst transfering file to CMS : " + e.getLocalizedMessage(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 154 */       if (ftHelper.requiresConnection())
/*     */       {
/* 156 */         ftHelper.disconnect();
/*     */       }
/*     */     }
/*     */ 
/* 160 */     return result;
/*     */   }
/*     */ 
/*     */   public String getReturnUrl(int iRepositoryNumber, String sSubFolder, String sCmsFilename)
/*     */   {
/* 172 */     String sReturnUrl = AssetBankSettings.getCmsReturnUrl(iRepositoryNumber);
/*     */ 
/* 175 */     String sReturnUrlSuffix = AssetBankSettings.getCmsReturnUrlSuffix(iRepositoryNumber);
/*     */ 
/* 177 */     if ((StringUtils.isNotEmpty(sReturnUrl)) && (StringUtils.isNotEmpty(sReturnUrlSuffix)))
/*     */     {
/* 180 */       sReturnUrl = sReturnUrl + sReturnUrlSuffix;
/*     */ 
/* 183 */       if (sSubFolder != null)
/*     */       {
/* 185 */         sReturnUrl = sReturnUrl + "/" + sSubFolder;
/*     */       }
/*     */ 
/* 188 */       sReturnUrl = sReturnUrl + "/" + sCmsFilename;
/*     */     }
/* 190 */     return sReturnUrl;
/*     */   }
/*     */ 
/*     */   private String getAutoSubfolderName(int a_iMaxNumber)
/*     */   {
/* 206 */     String sFolderName = "";
/*     */ 
/* 208 */     synchronized (this.m_oSubFolderLock)
/*     */     {
/* 210 */       sFolderName = sFolderName + this.m_iSubFolderCount;
/* 211 */       this.m_iSubFolderCount += 1;
/*     */     }
/*     */ 
/* 214 */     return sFolderName;
/*     */   }
/*     */ 
/*     */   private void generateCmsAttributes()
/*     */   {
/* 223 */     String sCmsAttributes = AssetBankSettings.getCmsAttributes();
/*     */ 
/* 225 */     if (StringUtil.stringIsPopulated(sCmsAttributes))
/*     */     {
/* 228 */       String[] saCmsAttributes = sCmsAttributes.split(",");
/*     */ 
/* 230 */       for (int x = 0; x < saCmsAttributes.length; x++)
/*     */       {
/* 232 */         String sCmsAttribute = saCmsAttributes[x];
/*     */ 
/* 234 */         String[] saCmsAttribute = sCmsAttribute.split(":");
/*     */ 
/* 236 */         this.m_cmsAttributes.put(Long.valueOf(saCmsAttribute[0]), saCmsAttribute[1]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public HashMap getCmsAttributes()
/*     */   {
/* 248 */     return this.m_cmsAttributes;
/*     */   }
/*     */ 
/*     */   public String generateAttributeXml(Vector a_vecAttributeValues, String sFilename, int iWidth, int iHeight)
/*     */   {
/* 257 */     Element root = new Element("xml");
/*     */ 
/* 260 */     Element url = new Element("url");
/* 261 */     url.addContent(new CDATA(sFilename));
/* 262 */     root.addContent(url);
/*     */ 
/* 265 */     Element width = new Element("width");
/* 266 */     width.addContent(new CDATA(String.valueOf(iWidth)));
/* 267 */     root.addContent(width);
/*     */ 
/* 270 */     Element height = new Element("height");
/* 271 */     height.addContent(new CDATA(String.valueOf(iHeight)));
/* 272 */     root.addContent(height);
/*     */ 
/* 276 */     Iterator itAttributeValues = a_vecAttributeValues.iterator();
/*     */ 
/* 278 */     while (itAttributeValues.hasNext())
/*     */     {
/* 280 */       AttributeValue attributeValue = (AttributeValue)itAttributeValues.next();
/*     */ 
/* 283 */       if (this.m_cmsAttributes.containsKey(Long.valueOf(attributeValue.getAttribute().getId())))
/*     */       {
/* 286 */         Element attribute = new Element((String)this.m_cmsAttributes.get(Long.valueOf(attributeValue.getAttribute().getId())));
/* 287 */         attribute.addContent(new CDATA(attributeValue.getValue()));
/* 288 */         root.addContent(attribute);
/*     */       }
/*     */     }
/*     */ 
/* 292 */     Document doc = new Document(root);
/* 293 */     XMLOutputter xmlOut = new XMLOutputter(Format.getRawFormat().setOmitDeclaration(true));
/* 294 */     String sXml = xmlOut.outputString(doc);
/*     */ 
/* 296 */     return sXml;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.cmsintegration.service.CmsIntegrationManager
 * JD-Core Version:    0.6.0
 */