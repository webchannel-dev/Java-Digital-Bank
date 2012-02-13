/*     */ package com.clickandbuy.TransactionManager;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.apache.axis.AxisFault;
/*     */ import org.apache.axis.NoEndPointException;
/*     */ import org.apache.axis.client.Call;
/*     */ import org.apache.axis.client.Stub;
/*     */ import org.apache.axis.constants.Style;
/*     */ import org.apache.axis.constants.Use;
/*     */ import org.apache.axis.description.FaultDesc;
/*     */ import org.apache.axis.description.OperationDesc;
/*     */ import org.apache.axis.description.ParameterDesc;
/*     */ import org.apache.axis.encoding.DeserializerFactory;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializerFactory;
/*     */ import org.apache.axis.encoding.ser.BeanSerializerFactory;
/*     */ import org.apache.axis.encoding.ser.EnumDeserializerFactory;
/*     */ import org.apache.axis.encoding.ser.EnumSerializerFactory;
/*     */ import org.apache.axis.soap.SOAPConstants;
/*     */ import org.apache.axis.types.UnsignedInt;
/*     */ import org.apache.axis.utils.JavaUtils;
/*     */ 
/*     */ public class TransactionManagerECommerceBindingStub extends Stub
/*     */   implements TransactionManagerECommerce
/*     */ {
/*  11 */   private Vector cachedSerClasses = new Vector();
/*  12 */   private Vector cachedSerQNames = new Vector();
/*  13 */   private Vector cachedSerFactories = new Vector();
/*  14 */   private Vector cachedDeserFactories = new Vector();
/*     */ 
/*  19 */   static OperationDesc[] _operations = new OperationDesc[4];
/*     */ 
/*     */   private static void _initOperationDesc1()
/*     */   {
/*  26 */     OperationDesc oper = new OperationDesc();
/*  27 */     oper.setName("rcCreateReservation");
/*  28 */     ParameterDesc param = new ParameterDesc(new QName("", "sRequest"),(byte) 1, new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.SingleRequest"), TransactionManagerPaymentSingleRequest.class, false, false);
/*  29 */     oper.addParameter(param);
/*  30 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.ECommerce.Reservation"));
/*  31 */     oper.setReturnClass(TransactionManagerECommerceReservation.class);
/*  32 */     oper.setReturnQName(new QName("", "_return"));
/*  33 */     oper.setStyle(Style.RPC);
/*  34 */     oper.setUse(Use.LITERAL);
/*  35 */     oper.addFault(new FaultDesc(new QName("TransactionManager.ECommerce", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/*  41 */     _operations[0] = oper;
/*     */ 
/*  43 */     oper = new OperationDesc();
/*  44 */     oper.setName("rcCaptureReservation");
/*  45 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  46 */     oper.addParameter(param);
/*  47 */     param = new ParameterDesc(new QName("", "tmPassword"),(byte) 1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  48 */     oper.addParameter(param);
/*  49 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  50 */     oper.addParameter(param);
/*  51 */     param = new ParameterDesc(new QName("", "BDRID"),(byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  52 */     oper.addParameter(param);
/*  53 */     param = new ParameterDesc(new QName("", "amount"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), UnsignedInt.class, false, false);
/*  54 */     oper.addParameter(param);
/*  55 */     param = new ParameterDesc(new QName("", "currency"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  56 */     oper.addParameter(param);
/*  57 */     param = new ParameterDesc(new QName("", "infoText"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  58 */     oper.addParameter(param);
/*  59 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/*  60 */     oper.setReturnClass(Boolean.TYPE);
/*  61 */     oper.setReturnQName(new QName("", "_return"));
/*  62 */     oper.setStyle(Style.RPC);
/*  63 */     oper.setUse(Use.LITERAL);
/*  64 */     oper.addFault(new FaultDesc(new QName("TransactionManager.ECommerce", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/*  70 */     _operations[1] = oper;
/*     */ 
/*  72 */     oper = new OperationDesc();
/*  73 */     oper.setName("rcCancelReservation");
/*  74 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  75 */     oper.addParameter(param);
/*  76 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  77 */     oper.addParameter(param);
/*  78 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  79 */     oper.addParameter(param);
/*  80 */     param = new ParameterDesc(new QName("", "BDRID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  81 */     oper.addParameter(param);
/*  82 */     param = new ParameterDesc(new QName("", "infoText"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  83 */     oper.addParameter(param);
/*  84 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/*  85 */     oper.setReturnClass(Boolean.TYPE);
/*  86 */     oper.setReturnQName(new QName("", "_return"));
/*  87 */     oper.setStyle(Style.RPC);
/*  88 */     oper.setUse(Use.LITERAL);
/*  89 */     oper.addFault(new FaultDesc(new QName("TransactionManager.ECommerce", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/*  95 */     _operations[2] = oper;
/*     */ 
/*  97 */     oper = new OperationDesc();
/*  98 */     oper.setName("rcStatusReservation");
/*  99 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/* 100 */     oper.addParameter(param);
/* 101 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/* 102 */     oper.addParameter(param);
/* 103 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/* 104 */     oper.addParameter(param);
/* 105 */     param = new ParameterDesc(new QName("", "BDRID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/* 106 */     oper.addParameter(param);
/* 107 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.ECommerce.ReservationInfo"));
/* 108 */     oper.setReturnClass(TransactionManagerECommerceReservationInfo.class);
/* 109 */     oper.setReturnQName(new QName("", "_return"));
/* 110 */     oper.setStyle(Style.RPC);
/* 111 */     oper.setUse(Use.LITERAL);
/* 112 */     oper.addFault(new FaultDesc(new QName("TransactionManager.ECommerce", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/* 118 */     _operations[3] = oper;
/*     */   }
/*     */ 
/*     */   public TransactionManagerECommerceBindingStub() throws AxisFault
/*     */   {
/* 123 */     this(null);
/*     */   }
/*     */ 
/*     */   public TransactionManagerECommerceBindingStub(URL endpointURL, javax.xml.rpc.Service service) throws AxisFault {
/* 127 */     this(service);
/* 128 */     this.cachedEndpoint = endpointURL;
/*     */   }
/*     */ 
/*     */   public TransactionManagerECommerceBindingStub(javax.xml.rpc.Service service) throws AxisFault {
/* 132 */     if (service == null)
/* 133 */       this.service = new org.apache.axis.client.Service();
/*     */     else {
/* 135 */       this.service = service;
/*     */     }
/* 137 */     ((org.apache.axis.client.Service)this.service).setTypeMappingVersion("1.2");
/*     */ 
/* 140 */     Class beansf = BeanSerializerFactory.class;
/* 141 */     Class beandf = BeanDeserializerFactory.class;
/* 142 */     Class enumsf = EnumSerializerFactory.class;
/* 143 */     Class enumdf = EnumDeserializerFactory.class;
/*     */ 
/* 145 */     QName qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectQuery");
/* 146 */     this.cachedSerQNames.add(qName);
/* 147 */     Class cls = ClickAndBuyEasyCollectEasyCollectQuery.class;
/* 148 */     this.cachedSerClasses.add(cls);
/* 149 */     this.cachedSerFactories.add(beansf);
/* 150 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 152 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult540");
/* 153 */     this.cachedSerQNames.add(qName);
/* 154 */     cls = ClickAndBuyEasyCollectEasyCollectResult540.class;
/* 155 */     this.cachedSerClasses.add(cls);
/* 156 */     this.cachedSerFactories.add(beansf);
/* 157 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 159 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.ECommerce.PaymentMethodSafety");
/* 160 */     this.cachedSerQNames.add(qName);
/* 161 */     cls = ClickAndBuyECommercePaymentMethodSafety.class;
/* 162 */     this.cachedSerClasses.add(cls);
/* 163 */     this.cachedSerFactories.add(enumsf);
/* 164 */     this.cachedDeserFactories.add(enumdf);
/*     */ 
/* 166 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelQuery");
/* 167 */     this.cachedSerQNames.add(qName);
/* 168 */     cls = ClickAndBuyTransactionCancelQuery.class;
/* 169 */     this.cachedSerClasses.add(cls);
/* 170 */     this.cachedSerFactories.add(beansf);
/* 171 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 173 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelResult");
/* 174 */     this.cachedSerQNames.add(qName);
/* 175 */     cls = ClickAndBuyTransactionCancelResult.class;
/* 176 */     this.cachedSerClasses.add(cls);
/* 177 */     this.cachedSerFactories.add(beansf);
/* 178 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 180 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.ECommerce.Reservation");
/* 181 */     this.cachedSerQNames.add(qName);
/* 182 */     cls = TransactionManagerECommerceReservation.class;
/* 183 */     this.cachedSerClasses.add(cls);
/* 184 */     this.cachedSerFactories.add(beansf);
/* 185 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 187 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.ECommerce.ReservationInfo");
/* 188 */     this.cachedSerQNames.add(qName);
/* 189 */     cls = TransactionManagerECommerceReservationInfo.class;
/* 190 */     this.cachedSerClasses.add(cls);
/* 191 */     this.cachedSerFactories.add(beansf);
/* 192 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 194 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.ECommerce.ReservationStatus");
/* 195 */     this.cachedSerQNames.add(qName);
/* 196 */     cls = TransactionManagerECommerceReservationStatus.class;
/* 197 */     this.cachedSerClasses.add(cls);
/* 198 */     this.cachedSerFactories.add(enumsf);
/* 199 */     this.cachedDeserFactories.add(enumdf);
/*     */ 
/* 201 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException");
/* 202 */     this.cachedSerQNames.add(qName);
/* 203 */     cls = TransactionManagerPaymentPaymentException.class;
/* 204 */     this.cachedSerClasses.add(cls);
/* 205 */     this.cachedSerFactories.add(beansf);
/* 206 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 208 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentRequest");
/* 209 */     this.cachedSerQNames.add(qName);
/* 210 */     cls = TransactionManagerPaymentPaymentRequest.class;
/* 211 */     this.cachedSerClasses.add(cls);
/* 212 */     this.cachedSerFactories.add(beansf);
/* 213 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 215 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentResponse540");
/* 216 */     this.cachedSerQNames.add(qName);
/* 217 */     cls = TransactionManagerPaymentPaymentResponse540.class;
/* 218 */     this.cachedSerClasses.add(cls);
/* 219 */     this.cachedSerFactories.add(beansf);
/* 220 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 222 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.SingleRequest");
/* 223 */     this.cachedSerQNames.add(qName);
/* 224 */     cls = TransactionManagerPaymentSingleRequest.class;
/* 225 */     this.cachedSerClasses.add(cls);
/* 226 */     this.cachedSerFactories.add(beansf);
/* 227 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 229 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.TransactionType");
/* 230 */     this.cachedSerQNames.add(qName);
/* 231 */     cls = TransactionManagerPaymentTransactionType.class;
/* 232 */     this.cachedSerClasses.add(cls);
/* 233 */     this.cachedSerFactories.add(enumsf);
/* 234 */     this.cachedDeserFactories.add(enumdf);
/*     */   }
/*     */ 
/*     */   protected Call createCall() throws RemoteException
/*     */   {
/*     */     try {
/* 240 */       Call _call = super._createCall();
/* 241 */       if (this.maintainSessionSet) {
/* 242 */         _call.setMaintainSession(this.maintainSession);
/*     */       }
/* 244 */       if (this.cachedUsername != null) {
/* 245 */         _call.setUsername(this.cachedUsername);
/*     */       }
/* 247 */       if (this.cachedPassword != null) {
/* 248 */         _call.setPassword(this.cachedPassword);
/*     */       }
/* 250 */       if (this.cachedEndpoint != null) {
/* 251 */         _call.setTargetEndpointAddress(this.cachedEndpoint);
/*     */       }
/* 253 */       if (this.cachedTimeout != null) {
/* 254 */         _call.setTimeout(this.cachedTimeout);
/*     */       }
/* 256 */       if (this.cachedPortName != null) {
/* 257 */         _call.setPortName(this.cachedPortName);
/*     */       }
/* 259 */       Enumeration keys = this.cachedProperties.keys();
/* 260 */       while (keys.hasMoreElements()) {
/* 261 */         String key = (String)keys.nextElement();
/* 262 */         _call.setProperty(key, this.cachedProperties.get(key));
/*     */       }
/*     */ 
/* 269 */       synchronized (this) {
/* 270 */         if (firstCall())
/*     */         {
/* 272 */           _call.setEncodingStyle(null);
/* 273 */           for (int i = 0; i < this.cachedSerFactories.size(); i++) {
/* 274 */             Class cls = (Class)this.cachedSerClasses.get(i);
/* 275 */             QName qName = (QName)this.cachedSerQNames.get(i);
/*     */ 
/* 277 */             Object x = this.cachedSerFactories.get(i);
/* 278 */             if ((x instanceof Class)) {
/* 279 */               Class sf = (Class)this.cachedSerFactories.get(i);
/*     */ 
/* 281 */               Class df = (Class)this.cachedDeserFactories.get(i);
/*     */ 
/* 283 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*     */             }
/* 285 */             else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory)) {
/* 286 */               org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
/*     */ 
/* 288 */               DeserializerFactory df = (DeserializerFactory)this.cachedDeserFactories.get(i);
/*     */ 
/* 290 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 295 */       return _call;
/*     */     } catch (Throwable _t) {
/*     */     
/* 298 */     throw new AxisFault("Failure trying to get the Call object", _t);}
/*     */   }
/*     */ 
/*     */   public TransactionManagerECommerceReservation rcCreateReservation(TransactionManagerPaymentSingleRequest sRequest) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 303 */     if (this.cachedEndpoint == null) {
/* 304 */       throw new NoEndPointException();
/*     */     }
/* 306 */     Call _call = createCall();
/* 307 */     _call.setOperation(_operations[0]);
/* 308 */     _call.setUseSOAPAction(true);
/* 309 */     _call.setSOAPActionURI("TransactionManager.ECommerce#rcCreateReservation");
/* 310 */     _call.setEncodingStyle(null);
/* 311 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 312 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 313 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 314 */     _call.setOperationName(new QName("TransactionManager.ECommerce", "rcCreateReservation"));
/*     */ 
/* 316 */     setRequestHeaders(_call);
/* 317 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { sRequest });
/*     */ 
/* 320 */       if ((_resp instanceof RemoteException)) {
/* 321 */         throw ((RemoteException)_resp);
/*     */       }
/*     */ 
/* 324 */       extractAttachments(_call);
/*     */       try {
/* 326 */         return (TransactionManagerECommerceReservation)_resp;
/*     */       } catch (Exception _exception) {
/* 328 */         return (TransactionManagerECommerceReservation)JavaUtils.convert(_resp, TransactionManagerECommerceReservation.class);
/*     */       }
/*     */     } catch (AxisFault axisFaultException)
/*     */     {
/* 332 */       if (axisFaultException.detail != null) {
/* 333 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 334 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 336 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 337 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
              throw new AxisFault();
/* 340 */     //throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public boolean rcCaptureReservation(long sellerID, String tmPassword, long slaveMerchantID, long BDRID, UnsignedInt amount, String currency, String infoText) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 345 */     if (this.cachedEndpoint == null) {
/* 346 */       throw new NoEndPointException();
/*     */     }
/* 348 */     Call _call = createCall();
/* 349 */     _call.setOperation(_operations[1]);
/* 350 */     _call.setUseSOAPAction(true);
/* 351 */     _call.setSOAPActionURI("TransactionManager.ECommerce#rcCaptureReservation");
/* 352 */     _call.setEncodingStyle(null);
/* 353 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 354 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 355 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 356 */     _call.setOperationName(new QName("TransactionManager.ECommerce", "rcCaptureReservation"));
/*     */ 
/* 358 */     setRequestHeaders(_call);
/* 359 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(BDRID), amount, currency, infoText });
/*     */ 
/* 362 */       if ((_resp instanceof RemoteException)) {
/* 363 */         throw ((RemoteException)_resp);
/*     */       }
/*     */ 
/* 366 */       extractAttachments(_call);
/*     */       try {
/* 368 */         return ((Boolean)_resp).booleanValue();
/*     */       } catch (Exception _exception) {
/* 370 */         return ((Boolean)JavaUtils.convert(_resp, Boolean.TYPE)).booleanValue();
/*     */       }
/*     */     } catch (AxisFault axisFaultException)
/*     */     {
/* 374 */       if (axisFaultException.detail != null) {
/* 375 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 376 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 378 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 379 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
                throw new AxisFault();
/* 382 */     //throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public boolean rcCancelReservation(long sellerID, String tmPassword, long slaveMerchantID, long BDRID, String infoText) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 387 */     if (this.cachedEndpoint == null) {
/* 388 */       throw new NoEndPointException();
/*     */     }
/* 390 */     Call _call = createCall();
/* 391 */     _call.setOperation(_operations[2]);
/* 392 */     _call.setUseSOAPAction(true);
/* 393 */     _call.setSOAPActionURI("TransactionManager.ECommerce#rcCancelReservation");
/* 394 */     _call.setEncodingStyle(null);
/* 395 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 396 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 397 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 398 */     _call.setOperationName(new QName("TransactionManager.ECommerce", "rcCancelReservation"));
/*     */ 
/* 400 */     setRequestHeaders(_call);
/* 401 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(BDRID), infoText });
/*     */ 
/* 404 */       if ((_resp instanceof RemoteException)) {
/* 405 */         throw ((RemoteException)_resp);
/*     */       }
/*     */ 
/* 408 */       extractAttachments(_call);
/*     */       try {
/* 410 */         return ((Boolean)_resp).booleanValue();
/*     */       } catch (Exception _exception) {
/* 412 */         return ((Boolean)JavaUtils.convert(_resp, Boolean.TYPE)).booleanValue();
/*     */       }
/*     */     } catch (AxisFault axisFaultException)
/*     */     {
/* 416 */       if (axisFaultException.detail != null) {
/* 417 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 418 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 420 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 421 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
                //return false;
/*     */     }
/* 424 */    //throw axisFaultException;
                throw new AxisFault();
/*     */   }
/*     */ 
/*     */   public TransactionManagerECommerceReservationInfo rcStatusReservation(long sellerID, String tmPassword, long slaveMerchantID, long BDRID) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 429 */     if (this.cachedEndpoint == null) {
/* 430 */       throw new NoEndPointException();
/*     */     }
/* 432 */     Call _call = createCall();
/* 433 */     _call.setOperation(_operations[3]);
/* 434 */     _call.setUseSOAPAction(true);
/* 435 */     _call.setSOAPActionURI("TransactionManager.ECommerce#rcStatusReservation");
/* 436 */     _call.setEncodingStyle(null);
/* 437 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 438 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 439 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 440 */     _call.setOperationName(new QName("TransactionManager.ECommerce", "rcStatusReservation"));
/*     */ 
/* 442 */     setRequestHeaders(_call);
/* 443 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(BDRID) });
/*     */ 
/* 446 */       if ((_resp instanceof RemoteException)) {
/* 447 */         throw ((RemoteException)_resp);
/*     */       }
/*     */ 
/* 450 */       extractAttachments(_call);
/*     */       try {
/* 452 */         return (TransactionManagerECommerceReservationInfo)_resp;
/*     */       } catch (Exception _exception) {
/* 454 */         return (TransactionManagerECommerceReservationInfo)JavaUtils.convert(_resp, TransactionManagerECommerceReservationInfo.class);
/*     */       }
/*     */     } catch (AxisFault axisFaultException)
/*     */     {
/* 458 */       if (axisFaultException.detail != null) {
/* 459 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 460 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 462 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 463 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 466 */     //throw axisFaultException;
            throw new AxisFault();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  20 */     _initOperationDesc1();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerECommerceBindingStub
 * JD-Core Version:    0.6.0
 */