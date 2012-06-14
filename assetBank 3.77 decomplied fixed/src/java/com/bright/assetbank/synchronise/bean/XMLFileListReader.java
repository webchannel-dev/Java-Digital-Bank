/*     */ package com.bright.assetbank.synchronise.bean;
/*     */ 
/*     */ import com.bn2web.common.constant.GlobalSettings;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.synchronise.constant.ExternalDataSynchronisationSettings;
/*     */ import com.bright.assetbank.synchronise.constant.SynchronisationConstants;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Vector;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ 
/*     */ public abstract class XMLFileListReader extends FileListReader
/*     */   implements SynchronisationConstants
/*     */ {
/*     */   private static final String c_ksClassName = "XMLFileListReader";
/*  54 */   private String m_sXMLUrl = null;
/*  55 */   private String[] m_aFiles = null;
/*  56 */   private String[] m_aFilesToUpdate = null;
/*  57 */   protected ExternalDataSynchronisationSettings m_settings = null;
/*  58 */   protected AssetManager m_assetManager = null;
/*     */ 
/*     */   public XMLFileListReader(String a_sXMLUrl, ExternalDataSynchronisationSettings a_settings)
/*     */   {
/*  62 */     this.m_sXMLUrl = a_sXMLUrl;
/*  63 */     this.m_settings = a_settings;
/*  64 */     initialiseComponents();
/*     */   }
/*     */ 
/*     */   public String[] getFiles()
/*     */     throws Bn2Exception
/*     */   {
/*  84 */     GlobalApplication.getInstance().getLogger().debug("XMLFileListReader.getFiles: About to get files from XML");
/*     */ 
/*  88 */     if (this.m_aFiles == null)
/*     */     {
/*  90 */       parseFiles();
/*     */     }
/*     */ 
/*  93 */     return this.m_aFiles;
/*     */   }
/*     */ 
/*     */   public String[] getFilesToUpdate()
/*     */     throws Bn2Exception
/*     */   {
/* 111 */     if (this.m_aFilesToUpdate == null)
/*     */     {
/* 113 */       parseFiles();
/*     */     }
/*     */ 
/* 116 */     return this.m_aFilesToUpdate;
/*     */   }
/*     */ 
/*     */   public String getDebugXML()
/*     */   {
/* 127 */     String sXMLFile = "";
/*     */     try
/*     */     {
/* 130 */       URL xmlFile = new URL(this.m_sXMLUrl);
/* 131 */       BufferedReader in = new BufferedReader(new InputStreamReader(xmlFile.openStream()));
/*     */       String sInputLine;
/* 134 */       while ((sInputLine = in.readLine()) != null)
/*     */       {
/* 136 */         sXMLFile = sXMLFile + sInputLine;
/*     */       }
/*     */ 
/* 139 */       in.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 143 */       GlobalApplication.getInstance().getLogger().error("XMLFileListReader.getDebugXML: Unable to get debug xml");
/*     */     }
/*     */ 
/* 146 */     return sXMLFile;
/*     */   }
/*     */ 
/*     */   private void parseFiles()
/*     */     throws Bn2Exception
/*     */   {
/* 162 */     String ksMethodName = "parseFiles";
/* 163 */     Vector vecFiles = new Vector();
/* 164 */     Vector vecFilesToUpdate = new Vector();
/*     */ 
/* 166 */     GlobalApplication.getInstance().getLogger().debug("XMLFileListReader.parseFiles: About to parse XML");
/*     */     try
/*     */     {
/* 170 */       SAXBuilder parser = new SAXBuilder();
/* 171 */       Document doc = parser.build(GlobalSettings.getApplicationPath() + "/WEB-INF/classes/mappings.xml");
/*     */ 
/* 173 */       GlobalApplication.getInstance().getLogger().debug("XMLFileListReader.parseFiles: Got mapping xml file");
/*     */ 
/* 175 */       Element rootElement = doc.getRootElement();
/*     */ 
/* 177 */       String sRecordsElementName = rootElement.getChildText("records_element");
/*     */ 
/* 180 */       InputStream is = new URL(this.m_sXMLUrl).openStream();
/* 181 */       Reader reader = new InputStreamReader(is, this.m_settings.getFeedEncoding());
/*     */ 
/* 183 */       parser = new SAXBuilder();
/* 184 */       doc = parser.build(reader);
/* 185 */       GlobalApplication.getInstance().getLogger().debug("XMLFileListReader.parseFiles: Got xml file");
/*     */ 
/* 187 */       rootElement = doc.getRootElement();
/* 188 */       Element recordsElement = rootElement.getChild(sRecordsElementName);
/*     */ 
/* 190 */       if (recordsElement != null)
/*     */       {
/* 192 */         List listRecords = recordsElement.getChildren();
/* 193 */         ListIterator recordIterator = listRecords.listIterator();
/*     */ 
/* 196 */         while (recordIterator.hasNext())
/*     */         {
/* 199 */           Element recordElement = (Element)recordIterator.next();
/* 200 */           String sFile = getFileName(recordElement);
/* 201 */           GlobalApplication.getInstance().getLogger().debug("XMLFileListReader.parseFiles: About to add file " + sFile);
/* 202 */           long lId = existingAssetId(recordElement);
/*     */ 
/* 204 */           if (lId <= 0L)
/*     */           {
/* 206 */             if (sFile != null)
/*     */             {
/* 208 */               vecFiles.add(sFile);
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 214 */             vecFilesToUpdate.add(sFile + ":" + lId);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (JDOMException e)
/*     */     {
/* 221 */       throw new Bn2Exception("XMLFileListReader.parseFiles : JDOM Exception whilst parsing XML : " + e, e);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 225 */       throw new Bn2Exception("XMLFileListReader.parseFiles : Exception whilst trying to parse XML : " + e, e);
/*     */     }
/*     */ 
/* 229 */     this.m_aFiles = new String[vecFiles.size()];
/*     */ 
/* 231 */     for (int i = 0; i < vecFiles.size(); i++)
/*     */     {
/* 233 */       this.m_aFiles[i] = ((String)vecFiles.elementAt(i));
/*     */     }
/*     */ 
/* 236 */     this.m_aFilesToUpdate = new String[vecFilesToUpdate.size()];
/*     */ 
/* 238 */     for (int i = 0; i < vecFilesToUpdate.size(); i++)
/*     */     {
/* 240 */       this.m_aFilesToUpdate[i] = ((String)vecFilesToUpdate.elementAt(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected AssetManager getAssetManager()
/*     */   {
/* 247 */     if (this.m_assetManager == null)
/*     */     {
/* 249 */       initialiseComponents();
/*     */     }
/* 251 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   protected void initialiseComponents()
/*     */   {
/* 256 */     String ksMethodName = "initialiseComponents";
/*     */ 
/* 258 */     if (this.m_assetManager == null)
/*     */     {
/*     */       try
/*     */       {
/* 262 */         this.m_assetManager = ((AssetManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetManager"));
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/* 267 */         throw new RuntimeException("XMLFileListReader.initialiseComponents : Couldn't find component ", ce);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract long existingAssetId(Element paramElement)
/*     */     throws Bn2Exception;
/*     */ 
/*     */   protected abstract String getFileName(Element paramElement);
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.bean.XMLFileListReader
 * JD-Core Version:    0.6.0
 */