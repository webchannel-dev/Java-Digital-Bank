/*     */ package com.bright.assetbank.webservice.synchronise.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.synchronise.service.SynchronisationServiceManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.webservice.security.service.DigestAuthenticationManager;
/*     */ import com.bright.assetbank.webservice.security.shared.DigestData;
/*     */ import com.bright.assetbank.webservice.security.shared.DigestDataSoapHeader;
/*     */ import com.bright.assetbank.webservice.synchronise.bean.SynchInfo;
/*     */ import com.bright.assetbank.webservice.synchronise.util.ErrorUtils;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.storage.constant.StoredFileType;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.FileDataSource;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.axis.AxisEngine;
/*     */ import org.apache.axis.AxisFault;
/*     */ import org.apache.axis.Message;
/*     */ import org.apache.axis.MessageContext;
/*     */ import org.apache.axis.attachments.AttachmentPart;
/*     */ import org.apache.axis.client.Call;
/*     */ import org.apache.axis.client.Service;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializerFactory;
/*     */ import org.apache.axis.encoding.ser.BeanSerializerFactory;
/*     */ import org.apache.axis.message.SOAPEnvelope;
/*     */ import org.apache.axis.message.SOAPHeaderElement;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class SynchronisationService
/*     */ {
/*     */   private static final String c_ksClassName = "SynchronisationService";
/*     */   private static final String COMPONENT_NAME_SYNC_MANAGER = "SynchronisationServiceManager";
/*  73 */   private static final Log log = GlobalApplication.getInstance().getLogger();
/*     */ 
/*  75 */   private SynchronisationServiceManager m_synchronisationServiceManager = null;
/*     */ 
/*     */   public void setSynchronisationServiceManager(SynchronisationServiceManager a_synchronisationServiceManager) {
/*  78 */     this.m_synchronisationServiceManager = a_synchronisationServiceManager;
/*     */   }
/*     */ 
/*     */   public SynchronisationService()
/*     */   {
/*     */     try
/*     */     {
/*  86 */       setSynchronisationServiceManager((SynchronisationServiceManager)GlobalApplication.getInstance().getComponentManager().lookup("SynchronisationServiceManager"));
/*     */     }
/*     */     catch (ComponentException ce)
/*     */     {
/*  91 */       log.error("Could not get instance of SynchronisationServiceManager " + ce);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Boolean synchroniseAssets(SynchInfo a_synchInfo)
/*     */   {
/* 110 */     String ksMethodName = "synchroniseAssets";
/*     */     try
/*     */     {
/* 114 */       if (!authenticateRequest())
/*     */       {
/* 116 */         Message message = AxisEngine.getCurrentMessageContext().getResponseMessage();
/* 117 */         message.getSOAPBody().addFault(message.getSOAPEnvelope().createName("InvalidSecurityToken"), "Authentication required");
/* 118 */         return new Boolean(false);
/*     */       }
/*     */     }
/*     */     catch (AxisFault af)
/*     */     {
/* 123 */       System.err.println(af);
/* 124 */       return new Boolean(false);
/*     */     }
/*     */     catch (SOAPException se)
/*     */     {
/* 128 */       System.err.println(se);
/* 129 */       return new Boolean(false);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 135 */       saveMessageAttachments();
/* 136 */       ABUser user = this.m_synchronisationServiceManager.getUserForImport();
/*     */ 
/* 139 */       String sPath = this.m_synchronisationServiceManager.getFileStoreManager().getUniqueFilepath("synchroniseAssetsTemp", StoredFileType.IMPORT);
/* 140 */       sPath = this.m_synchronisationServiceManager.getFileStoreManager().getFullBasePath(sPath);
/*     */ 
/* 142 */       this.m_synchronisationServiceManager.bulkUploadImportedZips(sPath, a_synchInfo.getSyncId(), user, 0L);
/* 143 */       this.m_synchronisationServiceManager.updateAssetsFromMetaFile(sPath, a_synchInfo.getSyncId(), user);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 147 */       GlobalApplication.getInstance().getLogger().error("SynchronisationService.synchroniseAssets : Error synchronising assets", e);
/* 148 */       Message message = AxisEngine.getCurrentMessageContext().getResponseMessage();
/*     */       try
/*     */       {
/* 151 */         message.getSOAPBody().addFault(message.getSOAPEnvelope().createName("synchroniseAssets"), e.getMessage());
/*     */       }
/*     */       catch (AxisFault af)
/*     */       {
/* 155 */         log.error("SynchronisationService.synchroniseAssets - " + af);
/*     */       }
/*     */       catch (SOAPException se)
/*     */       {
/* 159 */         log.error("SynchronisationService.synchroniseAssets - " + se);
/*     */       }
/*     */ 
/* 162 */       return new Boolean(false);
/*     */     }
/*     */ 
/* 166 */     return new Boolean(true);
/*     */   }
/*     */ 
/*     */   public void saveMessageAttachments()
/*     */     throws Bn2Exception
/*     */   {
/* 185 */     String ksMethodName = "saveMessageAttachments";
/*     */ 
/* 187 */     Message message = AxisEngine.getCurrentMessageContext().getRequestMessage();
/*     */ 
/* 189 */     for (Iterator itAtt = message.getAttachments(); itAtt.hasNext(); )
/*     */     {
/* 191 */       AttachmentPart part = (AttachmentPart)itAtt.next();
/* 192 */       if (part != null)
/*     */       {
/* 194 */         DataHandler dh = null;
/*     */         try
/*     */         {
/* 197 */           dh = part.getDataHandler();
/*     */           try
/*     */           {
/* 200 */             String sFilePath = this.m_synchronisationServiceManager.getFileStoreManager().getUniqueFilepath(part.getContentId(), StoredFileType.IMPORT);
/* 201 */             String sLoc = this.m_synchronisationServiceManager.getFileStoreManager().getAbsolutePath(sFilePath);
/*     */ 
/* 203 */             FileOutputStream fos = new FileOutputStream(sLoc);
/*     */ 
/* 205 */             dh.writeTo(fos);
/* 206 */             fos.close();
/*     */           }
/*     */           catch (IOException ioe)
/*     */           {
/* 210 */             log.error("SynchronisationService.saveMessageAttachments - " + ioe);
/* 211 */             throw new Bn2Exception("SynchronisationService.saveMessageAttachments", ioe);
/*     */           }
/*     */         }
/*     */         catch (SOAPException se)
/*     */         {
/* 216 */           log.error("SynchronisationService.saveMessageAttachments - " + se);
/* 217 */           throw new Bn2Exception("SynchronisationService.saveMessageAttachments", se);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean publishExportedAssets(String a_sExportId, Vector a_files)
/*     */     throws Bn2Exception
/*     */   {
/* 244 */     String ksMethodName = "publishExportedAssets";
/*     */ 
/* 246 */     SynchInfo synchInfo = new SynchInfo();
/* 247 */     synchInfo.setSyncId(a_sExportId);
/* 248 */     Boolean result = new Boolean(false);
/*     */     try
/*     */     {
/* 253 */       Service service = new Service();
/* 254 */       Call call = (Call)service.createCall();
/* 255 */       QName qn = new QName("ab", "SynchInfo");
/* 256 */       call.registerTypeMapping(SynchInfo.class, qn, new BeanSerializerFactory(SynchInfo.class, qn), new BeanDeserializerFactory(SynchInfo.class, qn));
/* 257 */       String sUrl = AssetBankSettings.getPublishToUrl() + "/services/" + "SynchronisationService";
/* 258 */       call.setTargetEndpointAddress(new URL(sUrl));
/* 259 */       call.setOperationName(new QName(AssetBankSettings.getPublishToUrl(), "synchroniseAssets"));
/*     */ 
/* 262 */       Hashtable htHeaders = new Hashtable();
/* 263 */       htHeaders.put("chunked", "false");
/* 264 */       call.setProperty("HTTP-Request-Headers", htHeaders);
/*     */ 
/* 269 */       addSynchFilesToCall(a_files, call);
/*     */       try
/*     */       {
/* 273 */         result = (Boolean)call.invoke(new Object[] { synchInfo });
/*     */       }
/*     */       catch (AxisFault af)
/*     */       {
/*     */         try
/*     */         {
/* 279 */           SOAPFault fault = call.getResponseMessage().getSOAPBody().getFault();
/* 280 */           String sFaultCode = fault.getFaultCode();
/*     */ 
/* 284 */           if (sFaultCode.equals("InvalidSecurityToken"))
/*     */           {
/* 286 */             Message response = call.getResponseMessage();
/* 287 */             DigestData digestData = respondToChallenge(response);
/*     */ 
/* 291 */             call.addHeader(new DigestDataSoapHeader(digestData).getHeader());
/*     */             try
/*     */             {
/* 295 */               addSynchFilesToCall(a_files, call);
/* 296 */               result = (Boolean)call.invoke(new Object[] { synchInfo });
/*     */             }
/*     */             catch (AxisFault af2)
/*     */             {
/* 301 */               log.error("SynchronisationService.publishExportedAssets", af2);
/* 302 */               throw new Bn2Exception("SynchronisationService.publishExportedAssets", af2);
/*     */             }
/*     */ 
/* 306 */             if (call.getResponseMessage().getSOAPBody().hasFault())
/*     */             {
/* 308 */               String sError = "Unexpected fault in authentication response : SynchronisationService.publishExportedAssets - " + call.getResponseMessage().getSOAPBody().getFault().getFaultString();
/* 309 */               log.debug(sError);
/* 310 */               throw new Bn2Exception(sError);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 315 */             String sError = "Unexpected fault in authentication response : SynchronisationService.publishExportedAssets - " + call.getResponseMessage().getSOAPBody().getFault().getFaultString();
/* 316 */             log.debug(sError);
/* 317 */             throw new Bn2Exception(sError);
/*     */           }
/*     */         }
/*     */         catch (SOAPException se)
/*     */         {
/* 322 */           log.error("SynchronisationService.publishExportedAssets - " + se);
/* 323 */           throw new Bn2Exception("SynchronisationService.publishExportedAssets", se);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ServiceException se)
/*     */     {
/* 329 */       log.error("SynchronisationService.publishExportedAssets - " + se);
/* 330 */       throw new Bn2Exception("SynchronisationService.publishExportedAssets", se);
/*     */     }
/*     */     catch (MalformedURLException murle)
/*     */     {
/* 334 */       log.error("SynchronisationService.publishExportedAssets - " + murle);
/* 335 */       throw new Bn2Exception("SynchronisationService.publishExportedAssets", murle);
/*     */     }
/*     */     catch (RemoteException re)
/*     */     {
/* 339 */       log.error("SynchronisationService.publishExportedAssets - " + re);
/* 340 */       throw new Bn2Exception("SynchronisationService.publishExportedAssets", re);
/*     */     }
/*     */ 
/* 343 */     return result.booleanValue();
/*     */   }
/*     */ 
/*     */   private void addSynchFilesToCall(Vector a_files, Call a_call)
/*     */   {
/* 362 */     for (Iterator it = a_files.iterator(); it.hasNext(); )
/*     */     {
/* 364 */       File f = (File)it.next();
/* 365 */       DataHandler dh = new DataHandler(new FileDataSource(f));
/* 366 */       AttachmentPart part = new AttachmentPart();
/* 367 */       part.setDataHandler(dh);
/* 368 */       part.setContentId(f.getName());
/* 369 */       a_call.addAttachmentPart(part);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean authenticateRequest()
/*     */     throws AxisFault
/*     */   {
/* 386 */     Message request = AxisEngine.getCurrentMessageContext().getRequestMessage();
/*     */ 
/* 388 */     SOAPHeaderElement header = request.getSOAPEnvelope().getHeaderByName("http://test", "AuthenticationHeader");
/*     */ 
/* 391 */     DigestDataSoapHeader digestHeader = new DigestDataSoapHeader(header);
/*     */ 
/* 396 */     DigestAuthenticationManager digestManager = DigestAuthenticationManager.getInstance();
/*     */ 
/* 399 */     if (!digestManager.authenticateDigest(digestHeader))
/*     */     {
/* 402 */       Message response = AxisEngine.getCurrentMessageContext().getResponseMessage();
/* 403 */       DigestDataSoapHeader challenge = createChallengeHeader(digestManager.isStale(digestHeader));
/* 404 */       response.getSOAPEnvelope().addHeader(challenge.getHeader());
/* 405 */       return false;
/*     */     }
/*     */ 
/* 408 */     return true;
/*     */   }
/*     */ 
/*     */   private DigestDataSoapHeader createChallengeHeader(boolean stale)
/*     */   {
/* 426 */     DigestDataSoapHeader digestHeader = new DigestDataSoapHeader();
/* 427 */     digestHeader.setRealm("myRealm");
/* 428 */     digestHeader.setNonce(DigestAuthenticationManager.getInstance().createNewNonce());
/* 429 */     digestHeader.setOpaque("myOpaque");
/* 430 */     digestHeader.setStale(stale);
/* 431 */     digestHeader.setAlgorithm("MD5");
/* 432 */     digestHeader.setQualityOfProtection("auth");
/* 433 */     digestHeader.setRequestCounter(new Long(0L));
/* 434 */     return digestHeader;
/*     */   }
/*     */ 
/*     */   private DigestData respondToChallenge(Message response)
/*     */   {
/* 452 */     DigestDataSoapHeader challenge = null;
/*     */     try
/*     */     {
/* 455 */       SOAPHeaderElement header = response.getSOAPEnvelope().getHeaderByName("http://test", "AuthenticationHeader");
/*     */ 
/* 459 */       challenge = new DigestDataSoapHeader(header);
/* 460 */       challenge.setUsername("AbApp");
/* 461 */       challenge.setCnonce("mycnonce");
/* 462 */       challenge.setUri("image-bank/services/SynchronisationService");
/* 463 */       challenge.storeNewDigestHash("9repH3Pa");
/* 464 */       return challenge.getDigestData();
/*     */     }
/*     */     catch (AxisFault af) {
/*     */     
/* 468 */     throw ErrorUtils.getRuntimeException(this, af, "Problem getting SOAP header : AuthenticationHeader");
/*     */   }}
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.webservice.synchronise.service.SynchronisationService
 * JD-Core Version:    0.6.0
 */