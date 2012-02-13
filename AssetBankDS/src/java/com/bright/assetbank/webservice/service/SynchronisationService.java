/*     */ package com.bright.assetbank.webservice.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.synchronise.service.SynchronisationServiceManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.webservice.bean.SynchInfo;
/*     */ import com.bright.assetbank.webservice.service.security.DigestAuthenticationManager;
/*     */ import com.bright.assetbank.webservice.shared.security.DigestData;
/*     */ import com.bright.assetbank.webservice.shared.security.DigestDataSoapHeader;
/*     */ import com.bright.assetbank.webservice.util.ErrorUtils;
/*     */ import com.bright.framework.constant.FrameworkSettings;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
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
/*  75 */   private static final Log log = GlobalApplication.getInstance().getLogger();
/*     */ 
/*  77 */   private SynchronisationServiceManager m_synchronisationServiceManager = null;
/*     */ 
/*     */   public void setSynchronisationServiceManager(SynchronisationServiceManager a_synchronisationServiceManager) {
/*  80 */     this.m_synchronisationServiceManager = a_synchronisationServiceManager;
/*     */   }
/*     */ 
/*     */   public SynchronisationService()
/*     */   {
/*     */     try
/*     */     {
/*  88 */       setSynchronisationServiceManager((SynchronisationServiceManager)GlobalApplication.getInstance().getComponentManager().lookup("SynchronisationServiceManager"));
/*     */     }
/*     */     catch (ComponentException ce)
/*     */     {
/*  93 */       log.error("Could not get instance of SynchronisationServiceManager " + ce);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Boolean synchroniseAssets(SynchInfo a_synchInfo)
/*     */   {
/* 112 */     String ksMethodName = "addAssetsFromMetaFile";
/*     */     try
/*     */     {
/* 116 */       if (!authenticateRequest())
/*     */       {
/* 118 */         Message message = AxisEngine.getCurrentMessageContext().getResponseMessage();
/* 119 */         message.getSOAPBody().addFault(message.getSOAPEnvelope().createName("InvalidSecurityToken"), "Authentication required");
/* 120 */         return new Boolean(false);
/*     */       }
/*     */     }
/*     */     catch (AxisFault af)
/*     */     {
/* 125 */       System.err.println(af);
/* 126 */       return new Boolean(false);
/*     */     }
/*     */     catch (SOAPException se)
/*     */     {
/* 130 */       System.err.println(se);
/* 131 */       return new Boolean(false);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 137 */       saveMessageAttachments();
/* 138 */       ABUser user = this.m_synchronisationServiceManager.getUserForImport();
/* 139 */       this.m_synchronisationServiceManager.bulkUploadImportedZips(a_synchInfo.getSyncId(), user, 0L);
/* 140 */       this.m_synchronisationServiceManager.updateAssetsFromMetaFile(a_synchInfo.getSyncId(), user);
/*     */     }
/*     */     catch (Bn2Exception e)
/*     */     {
/* 144 */       Message message = AxisEngine.getCurrentMessageContext().getResponseMessage();
/*     */       try
/*     */       {
/* 147 */         message.getSOAPBody().addFault(message.getSOAPEnvelope().createName("addAssetsFromMetaFile"), e.getMessage());
/*     */       }
/*     */       catch (AxisFault af)
/*     */       {
/* 151 */         log.error("SynchronisationService.addAssetsFromMetaFile - " + af);
/*     */       }
/*     */       catch (SOAPException se)
/*     */       {
/* 155 */         log.error("SynchronisationService.addAssetsFromMetaFile - " + se);
/*     */       }
/*     */ 
/* 158 */       return new Boolean(false);
/*     */     }
/*     */ 
/* 162 */     return new Boolean(true);
/*     */   }
/*     */ 
/*     */   public void saveMessageAttachments()
/*     */     throws Bn2Exception
/*     */   {
/* 181 */     String ksMethodName = "saveMessageAttachments";
/*     */ 
/* 183 */     Message message = AxisEngine.getCurrentMessageContext().getRequestMessage();
/*     */ 
/* 185 */     for (Iterator itAtt = message.getAttachments(); itAtt.hasNext(); )
/*     */     {
/* 187 */       AttachmentPart part = (AttachmentPart)itAtt.next();
/* 188 */       if (part == null)
/*     */         continue;
/* 190 */       DataHandler dh = null;
/*     */       try
/*     */       {
/* 193 */         dh = part.getDataHandler();
/*     */         try
/*     */         {
/* 196 */           String sLoc = FrameworkSettings.getFilestoreRootFullPath() + "/" + 
/* 197 */             this.m_synchronisationServiceManager.getFileStoreManager().getUniqueFilename(part.getContentId(), FileStoreManager.k_iStoredFileType_IMPORT);
/* 198 */           FileOutputStream fos = new FileOutputStream(sLoc);
/*     */ 
/* 200 */           dh.writeTo(fos);
/* 201 */           fos.close();
/*     */         }
/*     */         catch (IOException ioe)
/*     */         {
/* 205 */           log.error("SynchronisationService.saveMessageAttachments - " + ioe);
/* 206 */           throw new Bn2Exception("SynchronisationService.saveMessageAttachments", ioe);
/*     */         }
/*     */       }
/*     */       catch (SOAPException se)
/*     */       {
/* 211 */         log.error("SynchronisationService.saveMessageAttachments - " + se);
/* 212 */         throw new Bn2Exception("SynchronisationService.saveMessageAttachments", se);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean publishExportedAssets(String a_sExportId, Vector a_files)
/*     */     throws Bn2Exception
/*     */   {
/* 239 */     String ksMethodName = "publishExportedAssets";
/*     */ 
/* 241 */     SynchInfo synchInfo = new SynchInfo();
/* 242 */     synchInfo.setSyncId(a_sExportId);
/* 243 */     Boolean result = new Boolean(false);
/*     */     try
/*     */     {
/* 248 */       Service service = new Service();
/* 249 */       Call call = (Call)service.createCall();
/* 250 */       QName qn = new QName("ab", "SynchInfo");
/* 251 */       call.registerTypeMapping(SynchInfo.class, qn, new BeanSerializerFactory(SynchInfo.class, qn), new BeanDeserializerFactory(SynchInfo.class, qn));
/* 252 */       String sUrl = AssetBankSettings.getPublishToUrl() + "/services/" + "SynchronisationService";
/* 253 */       call.setTargetEndpointAddress(new URL(sUrl));
/* 254 */       call.setOperationName(new QName(AssetBankSettings.getPublishToUrl(), "synchroniseAssets"));
/*     */ 
/* 257 */       Hashtable htHeaders = new Hashtable();
/* 258 */       htHeaders.put("chunked", "false");
/* 259 */       call.setProperty("HTTP-Request-Headers", htHeaders);
/*     */ 
/* 262 */       if (StringUtil.stringIsPopulated(FrameworkSettings.getProxyHost()))
/*     */       {
/* 264 */         Properties props = new Properties(System.getProperties());
/* 265 */         props.put("http.proxySet", "true");
/* 266 */         props.put("http.proxyHost", FrameworkSettings.getProxyHost());
/* 267 */         props.put("http.proxyPort", String.valueOf(FrameworkSettings.getProxyPort()));
/*     */ 
/* 269 */         if (StringUtil.stringIsPopulated(FrameworkSettings.getNTLMDomain()))
/*     */         {
/* 271 */           props.put("http.proxyUser", FrameworkSettings.getNTLMDomain() + "\\" + FrameworkSettings.getProxyUsername());
/*     */         }
/*     */         else
/*     */         {
/* 275 */           props.put("http.proxyUser", FrameworkSettings.getProxyUsername());
/*     */         }
/*     */ 
/* 278 */         props.put("http.proxyPassword", FrameworkSettings.getProxyPassword());
/*     */ 
/* 280 */         Properties newprops = new Properties(props);
/* 281 */         System.setProperties(newprops);
/*     */       }
/*     */ 
/* 284 */       addSynchFilesToCall(a_files, call);
/*     */       try
/*     */       {
/* 288 */         result = (Boolean)call.invoke(new Object[] { synchInfo });
/*     */       }
/*     */       catch (AxisFault af)
/*     */       {
/*     */         try
/*     */         {
/* 294 */           SOAPFault fault = call.getResponseMessage().getSOAPBody().getFault();
/* 295 */           String sFaultCode = fault.getFaultCode();
/*     */ 
/* 299 */           if (sFaultCode.equals("InvalidSecurityToken"))
/*     */           {
/* 301 */             Message response = call.getResponseMessage();
/* 302 */             DigestData digestData = respondToChallenge(response);
/*     */ 
/* 306 */             call.addHeader(new DigestDataSoapHeader(digestData).getHeader());
/*     */             try
/*     */             {
/* 310 */               addSynchFilesToCall(a_files, call);
/* 311 */               result = (Boolean)call.invoke(new Object[] { synchInfo });
/*     */             }
/*     */             catch (AxisFault af2)
/*     */             {
/* 316 */               log.error("SynchronisationService.publishExportedAssets - " + af2);
/* 317 */               throw new Bn2Exception("SynchronisationService.publishExportedAssets", af2);
/*     */             }
/*     */ 
/* 321 */             if (call.getResponseMessage().getSOAPBody().hasFault())
/*     */             {
/* 323 */               String sError = "Unexpected fault in authentication response : SynchronisationService.publishExportedAssets - " + call.getResponseMessage().getSOAPBody().getFault().getFaultString();
/* 324 */               log.debug(sError);
/* 325 */               throw new Bn2Exception(sError);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 330 */             String sError = "Unexpected fault in authentication response : SynchronisationService.publishExportedAssets - " + call.getResponseMessage().getSOAPBody().getFault().getFaultString();
/* 331 */             log.debug(sError);
/* 332 */             throw new Bn2Exception(sError);
/*     */           }
/*     */         }
/*     */         catch (SOAPException se)
/*     */         {
/* 337 */           log.error("SynchronisationService.publishExportedAssets - " + se);
/* 338 */           throw new Bn2Exception("SynchronisationService.publishExportedAssets", se);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ServiceException se)
/*     */     {
/* 344 */       log.error("SynchronisationService.publishExportedAssets - " + se);
/* 345 */       throw new Bn2Exception("SynchronisationService.publishExportedAssets", se);
/*     */     }
/*     */     catch (MalformedURLException murle)
/*     */     {
/* 349 */       log.error("SynchronisationService.publishExportedAssets - " + murle);
/* 350 */       throw new Bn2Exception("SynchronisationService.publishExportedAssets", murle);
/*     */     }
/*     */     catch (RemoteException re)
/*     */     {
/* 354 */       log.error("SynchronisationService.publishExportedAssets - " + re);
/* 355 */       throw new Bn2Exception("SynchronisationService.publishExportedAssets", re);
/*     */     }
/*     */ 
/* 358 */     return result.booleanValue();
/*     */   }
/*     */ 
/*     */   private void addSynchFilesToCall(Vector a_files, Call a_call)
/*     */   {
/* 377 */     for (Iterator it = a_files.iterator(); it.hasNext(); )
/*     */     {
/* 379 */       File f = (File)it.next();
/* 380 */       DataHandler dh = new DataHandler(new FileDataSource(f));
/* 381 */       AttachmentPart part = new AttachmentPart();
/* 382 */       part.setDataHandler(dh);
/* 383 */       part.setContentId(f.getName());
/* 384 */       a_call.addAttachmentPart(part);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean authenticateRequest()
/*     */     throws AxisFault
/*     */   {
/* 401 */     Message request = AxisEngine.getCurrentMessageContext().getRequestMessage();
/*     */ 
/* 403 */     SOAPHeaderElement header = 
/* 404 */       request.getSOAPEnvelope().getHeaderByName("http://test", "AuthenticationHeader");
/*     */ 
/* 406 */     DigestDataSoapHeader digestHeader = new DigestDataSoapHeader(header);
/*     */ 
/* 411 */     DigestAuthenticationManager digestManager = DigestAuthenticationManager.getInstance();
/*     */ 
/* 414 */     if (!digestManager.authenticateDigest(digestHeader))
/*     */     {
/* 417 */       Message response = AxisEngine.getCurrentMessageContext().getResponseMessage();
/* 418 */       DigestDataSoapHeader challenge = createChallengeHeader(digestManager.isStale(digestHeader));
/* 419 */       response.getSOAPEnvelope().addHeader(challenge.getHeader());
/* 420 */       return false;
/*     */     }
/*     */ 
/* 423 */     return true;
/*     */   }
/*     */ 
/*     */   private DigestDataSoapHeader createChallengeHeader(boolean stale)
/*     */   {
/* 441 */     DigestDataSoapHeader digestHeader = new DigestDataSoapHeader();
/* 442 */     digestHeader.setRealm("myRealm");
/* 443 */     digestHeader.setNonce(DigestAuthenticationManager.getInstance().createNewNonce());
/* 444 */     digestHeader.setOpaque("myOpaque");
/* 445 */     digestHeader.setStale(stale);
/* 446 */     digestHeader.setAlgorithm("MD5");
/* 447 */     digestHeader.setQualityOfProtection("auth");
/* 448 */     digestHeader.setRequestCounter(new Long(0L));
/* 449 */     return digestHeader;
/*     */   }
/*     */ 
/*     */   private DigestData respondToChallenge(Message response)
/*     */   {
/* 467 */     DigestDataSoapHeader challenge = null;
/*     */     try
/*     */     {
/* 470 */       SOAPHeaderElement header = 
/* 471 */         response.getSOAPEnvelope().getHeaderByName(
/* 472 */         "http://test", "AuthenticationHeader");
/*     */ 
/* 474 */       challenge = new DigestDataSoapHeader(header);
/* 475 */       challenge.setUsername("AbApp");
/* 476 */       challenge.setCnonce("mycnonce");
/* 477 */       challenge.setUri("image-bank/services/SynchronisationService");
/* 478 */       challenge.storeNewDigestHash("9repH3Pa");
/* 479 */       return challenge.getDigestData();
/*     */     }
/*     */     catch (AxisFault af) {
/*     */     
/* 483 */     throw ErrorUtils.getRuntimeException(this, af, "Problem getting SOAP header : AuthenticationHeader");
/*     */  } }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.webservice.service.SynchronisationService
 * JD-Core Version:    0.6.0
 */