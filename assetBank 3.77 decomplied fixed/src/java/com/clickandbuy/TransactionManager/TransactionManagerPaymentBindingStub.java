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
/*     */ import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
/*     */ import org.apache.axis.encoding.ser.ArraySerializerFactory;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializerFactory;
/*     */ import org.apache.axis.encoding.ser.BeanSerializerFactory;
/*     */ import org.apache.axis.encoding.ser.EnumDeserializerFactory;
/*     */ import org.apache.axis.encoding.ser.EnumSerializerFactory;
/*     */ import org.apache.axis.soap.SOAPConstants;
/*     */ import org.apache.axis.utils.JavaUtils;
/*     */ 
/*     */ public class TransactionManagerPaymentBindingStub extends Stub
/*     */   implements TransactionManagerPayment
/*     */ {
/*  11 */   private Vector cachedSerClasses = new Vector();
/*  12 */   private Vector cachedSerQNames = new Vector();
/*  13 */   private Vector cachedSerFactories = new Vector();
/*  14 */   private Vector cachedDeserFactories = new Vector();
/*     */ 
/*  19 */   static OperationDesc[] _operations = new OperationDesc[8];
/*     */ 
/*     */   private static void _initOperationDesc1()
/*     */   {
/*  26 */     OperationDesc oper = new OperationDesc();
/*  27 */     oper.setName("getEasyCollectSingle");
/*  28 */     ParameterDesc param = new ParameterDesc(new QName("", "sRequest"),(byte) 1, new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.SingleRequest"), TransactionManagerPaymentSingleRequest.class, false, false);
/*  29 */     oper.addParameter(param);
/*  30 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentResponse"));
/*  31 */     oper.setReturnClass(TransactionManagerPaymentPaymentResponse.class);
/*  32 */     oper.setReturnQName(new QName("", "_return"));
/*  33 */     oper.setStyle(Style.RPC);
/*  34 */     oper.setUse(Use.LITERAL);
/*  35 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Payment", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/*  41 */     _operations[0] = oper;
/*     */ 
/*  43 */     oper = new OperationDesc();
/*  44 */     oper.setName("getEasyCollectSingle540");
/*  45 */     param = new ParameterDesc(new QName("", "sRequest"), (byte)1, new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.SingleRequest"), TransactionManagerPaymentSingleRequest.class, false, false);
/*  46 */     oper.addParameter(param);
/*  47 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentResponse540"));
/*  48 */     oper.setReturnClass(TransactionManagerPaymentPaymentResponse540.class);
/*  49 */     oper.setReturnQName(new QName("", "_return"));
/*  50 */     oper.setStyle(Style.RPC);
/*  51 */     oper.setUse(Use.LITERAL);
/*  52 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Payment", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/*  58 */     _operations[1] = oper;
/*     */ 
/*  60 */     oper = new OperationDesc();
/*  61 */     oper.setName("commitEasyCollectBDR");
/*  62 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  63 */     oper.addParameter(param);
/*  64 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  65 */     oper.addParameter(param);
/*  66 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  67 */     oper.addParameter(param);
/*  68 */     param = new ParameterDesc(new QName("", "BDRID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  69 */     oper.addParameter(param);
/*  70 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/*  71 */     oper.setReturnClass(Boolean.TYPE);
/*  72 */     oper.setReturnQName(new QName("", "_return"));
/*  73 */     oper.setStyle(Style.RPC);
/*  74 */     oper.setUse(Use.LITERAL);
/*  75 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Payment", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/*  81 */     _operations[2] = oper;
/*     */ 
/*  83 */     oper = new OperationDesc();
/*  84 */     oper.setName("partialEasyCollectBegin");
/*  85 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  86 */     oper.addParameter(param);
/*  87 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  88 */     oper.addParameter(param);
/*  89 */     param = new ParameterDesc(new QName("", "extJobID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  90 */     oper.addParameter(param);
/*  91 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/*  92 */     oper.setReturnClass(Long.TYPE);
/*  93 */     oper.setReturnQName(new QName("", "_return"));
/*  94 */     oper.setStyle(Style.RPC);
/*  95 */     oper.setUse(Use.LITERAL);
/*  96 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Payment", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/* 102 */     _operations[3] = oper;
/*     */ 
/* 104 */     oper = new OperationDesc();
/* 105 */     oper.setName("partialEasyCollectAddSingleItem");
/* 106 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/* 107 */     oper.addParameter(param);
/* 108 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/* 109 */     oper.addParameter(param);
/* 110 */     param = new ParameterDesc(new QName("", "mJobID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/* 111 */     oper.addParameter(param);
/* 112 */     param = new ParameterDesc(new QName("", "item"), (byte)1, new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentRequest"), TransactionManagerPaymentPaymentRequest.class, false, false);
/* 113 */     oper.addParameter(param);
/* 114 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 115 */     oper.setReturnClass(Long.TYPE);
/* 116 */     oper.setReturnQName(new QName("", "_return"));
/* 117 */     oper.setStyle(Style.RPC);
/* 118 */     oper.setUse(Use.LITERAL);
/* 119 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Payment", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/* 125 */     _operations[4] = oper;
/*     */ 
/* 127 */     oper = new OperationDesc();
/* 128 */     oper.setName("partialEasyCollectAddItems");
/* 129 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/* 130 */     oper.addParameter(param);
/* 131 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/* 132 */     oper.addParameter(param);
/* 133 */     param = new ParameterDesc(new QName("", "mJobID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/* 134 */     oper.addParameter(param);
/* 135 */     param = new ParameterDesc(new QName("", "mItems"), (byte)1, new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentReqItems"), com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentRequest.class, false, false);
/* 136 */     param.setItemQName(new QName("", "item"));
/* 137 */     oper.addParameter(param);
/* 138 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 139 */     oper.setReturnClass(Long.TYPE);
/* 140 */     oper.setReturnQName(new QName("", "_return"));
/* 141 */     oper.setStyle(Style.RPC);
/* 142 */     oper.setUse(Use.LITERAL);
/* 143 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Payment", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/* 149 */     _operations[5] = oper;
/*     */ 
/* 151 */     oper = new OperationDesc();
/* 152 */     oper.setName("partialEasyCollectFinish");
/* 153 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/* 154 */     oper.addParameter(param);
/* 155 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/* 156 */     oper.addParameter(param);
/* 157 */     param = new ParameterDesc(new QName("", "mJobID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/* 158 */     oper.addParameter(param);
/* 159 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 160 */     oper.setReturnClass(Long.TYPE);
/* 161 */     oper.setReturnQName(new QName("", "_return"));
/* 162 */     oper.setStyle(Style.RPC);
/* 163 */     oper.setUse(Use.LITERAL);
/* 164 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Payment", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/* 170 */     _operations[6] = oper;
/*     */ 
/* 172 */     oper = new OperationDesc();
/* 173 */     oper.setName("placeEasyCollectMulti");
/* 174 */     param = new ParameterDesc(new QName("", "mRequest"), (byte)1, new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.MultiRequest"), TransactionManagerPaymentMultiRequest.class, false, false);
/* 175 */     oper.addParameter(param);
/* 176 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
/* 177 */     oper.setReturnClass(Long.TYPE);
/* 178 */     oper.setReturnQName(new QName("", "_return"));
/* 179 */     oper.setStyle(Style.RPC);
/* 180 */     oper.setUse(Use.LITERAL);
/* 181 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Payment", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/* 187 */     _operations[7] = oper;
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentBindingStub() throws AxisFault
/*     */   {
/* 192 */     this(null);
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentBindingStub(URL endpointURL, javax.xml.rpc.Service service) throws AxisFault {
/* 196 */     this(service);
/* 197 */     this.cachedEndpoint = endpointURL;
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentBindingStub(javax.xml.rpc.Service service) throws AxisFault {
/* 201 */     if (service == null)
/* 202 */       this.service = new org.apache.axis.client.Service();
/*     */     else {
/* 204 */       this.service = service;
/*     */     }
/* 206 */     ((org.apache.axis.client.Service)this.service).setTypeMappingVersion("1.2");
/*     */ 
/* 210 */     Class beansf = BeanSerializerFactory.class;
/* 211 */     Class beandf = BeanDeserializerFactory.class;
/* 212 */     Class enumsf = EnumSerializerFactory.class;
/* 213 */     Class enumdf = EnumDeserializerFactory.class;
/*     */ 
/* 215 */     QName qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectQuery");
/* 216 */     this.cachedSerQNames.add(qName);
/* 217 */     Class cls = ClickAndBuyEasyCollectEasyCollectQuery.class;
/* 218 */     this.cachedSerClasses.add(cls);
/* 219 */     this.cachedSerFactories.add(beansf);
/* 220 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 222 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult");
/* 223 */     this.cachedSerQNames.add(qName);
/* 224 */     cls = ClickAndBuyEasyCollectEasyCollectResult.class;
/* 225 */     this.cachedSerClasses.add(cls);
/* 226 */     this.cachedSerFactories.add(beansf);
/* 227 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 229 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult540");
/* 230 */     this.cachedSerQNames.add(qName);
/* 231 */     cls = ClickAndBuyEasyCollectEasyCollectResult540.class;
/* 232 */     this.cachedSerClasses.add(cls);
/* 233 */     this.cachedSerFactories.add(beansf);
/* 234 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 236 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.ECommerce.PaymentMethodSafety");
/* 237 */     this.cachedSerQNames.add(qName);
/* 238 */     cls = ClickAndBuyECommercePaymentMethodSafety.class;
/* 239 */     this.cachedSerClasses.add(cls);
/* 240 */     this.cachedSerFactories.add(enumsf);
/* 241 */     this.cachedDeserFactories.add(enumdf);
/*     */ 
/* 243 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelQuery");
/* 244 */     this.cachedSerQNames.add(qName);
/* 245 */     cls = ClickAndBuyTransactionCancelQuery.class;
/* 246 */     this.cachedSerClasses.add(cls);
/* 247 */     this.cachedSerFactories.add(beansf);
/* 248 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 250 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelResult");
/* 251 */     this.cachedSerQNames.add(qName);
/* 252 */     cls = ClickAndBuyTransactionCancelResult.class;
/* 253 */     this.cachedSerClasses.add(cls);
/* 254 */     this.cachedSerFactories.add(beansf);
/* 255 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 257 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.MultiRequest");
/* 258 */     this.cachedSerQNames.add(qName);
/* 259 */     cls = TransactionManagerPaymentMultiRequest.class;
/* 260 */     this.cachedSerClasses.add(cls);
/* 261 */     this.cachedSerFactories.add(beansf);
/* 262 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 264 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException");
/* 265 */     this.cachedSerQNames.add(qName);
/* 266 */     cls = TransactionManagerPaymentPaymentException.class;
/* 267 */     this.cachedSerClasses.add(cls);
/* 268 */     this.cachedSerFactories.add(beansf);
/* 269 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 271 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentReqItems");
/* 272 */     this.cachedSerQNames.add(qName);
/* 273 */     cls = com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentRequest.class;
/* 274 */     this.cachedSerClasses.add(cls);
/* 275 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentRequest");
/* 276 */     QName qName2 = new QName("", "item");
/* 277 */     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
/* 278 */     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
/*     */ 
/* 280 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentRequest");
/* 281 */     this.cachedSerQNames.add(qName);
/* 282 */     cls = TransactionManagerPaymentPaymentRequest.class;
/* 283 */     this.cachedSerClasses.add(cls);
/* 284 */     this.cachedSerFactories.add(beansf);
/* 285 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 287 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentResponse");
/* 288 */     this.cachedSerQNames.add(qName);
/* 289 */     cls = TransactionManagerPaymentPaymentResponse.class;
/* 290 */     this.cachedSerClasses.add(cls);
/* 291 */     this.cachedSerFactories.add(beansf);
/* 292 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 294 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentResponse540");
/* 295 */     this.cachedSerQNames.add(qName);
/* 296 */     cls = TransactionManagerPaymentPaymentResponse540.class;
/* 297 */     this.cachedSerClasses.add(cls);
/* 298 */     this.cachedSerFactories.add(beansf);
/* 299 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 301 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.SingleRequest");
/* 302 */     this.cachedSerQNames.add(qName);
/* 303 */     cls = TransactionManagerPaymentSingleRequest.class;
/* 304 */     this.cachedSerClasses.add(cls);
/* 305 */     this.cachedSerFactories.add(beansf);
/* 306 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 308 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.TransactionType");
/* 309 */     this.cachedSerQNames.add(qName);
/* 310 */     cls = TransactionManagerPaymentTransactionType.class;
/* 311 */     this.cachedSerClasses.add(cls);
/* 312 */     this.cachedSerFactories.add(enumsf);
/* 313 */     this.cachedDeserFactories.add(enumdf);
/*     */   }
/*     */ 
/*     */   protected Call createCall() throws RemoteException
/*     */   {
/*     */     try {
/* 319 */       Call _call = super._createCall();
/* 320 */       if (this.maintainSessionSet) {
/* 321 */         _call.setMaintainSession(this.maintainSession);
/*     */       }
/* 323 */       if (this.cachedUsername != null) {
/* 324 */         _call.setUsername(this.cachedUsername);
/*     */       }
/* 326 */       if (this.cachedPassword != null) {
/* 327 */         _call.setPassword(this.cachedPassword);
/*     */       }
/* 329 */       if (this.cachedEndpoint != null) {
/* 330 */         _call.setTargetEndpointAddress(this.cachedEndpoint);
/*     */       }
/* 332 */       if (this.cachedTimeout != null) {
/* 333 */         _call.setTimeout(this.cachedTimeout);
/*     */       }
/* 335 */       if (this.cachedPortName != null) {
/* 336 */         _call.setPortName(this.cachedPortName);
/*     */       }
/* 338 */       Enumeration keys = this.cachedProperties.keys();
/* 339 */       while (keys.hasMoreElements()) {
/* 340 */         String key = (String)keys.nextElement();
/* 341 */         _call.setProperty(key, this.cachedProperties.get(key));
/*     */       }
/*     */ 
/* 348 */       synchronized (this) {
/* 349 */         if (firstCall())
/*     */         {
/* 351 */           _call.setEncodingStyle(null);
/* 352 */           for (int i = 0; i < this.cachedSerFactories.size(); i++) {
/* 353 */             Class cls = (Class)this.cachedSerClasses.get(i);
/* 354 */             QName qName = (QName)this.cachedSerQNames.get(i);
/*     */ 
/* 356 */             Object x = this.cachedSerFactories.get(i);
/* 357 */             if ((x instanceof Class)) {
/* 358 */               Class sf = (Class)this.cachedSerFactories.get(i);
/*     */ 
/* 360 */               Class df = (Class)this.cachedDeserFactories.get(i);
/*     */ 
/* 362 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*     */             }
/* 364 */             else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory)) {
/* 365 */               org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
/*     */ 
/* 367 */               DeserializerFactory df = (DeserializerFactory)this.cachedDeserFactories.get(i);
/*     */ 
/* 369 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 374 */       return _call;
/*     */     } catch (Throwable _t) {
/*     */     
/* 377 */     throw new AxisFault("Failure trying to get the Call object", _t);}
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentPaymentResponse getEasyCollectSingle(TransactionManagerPaymentSingleRequest sRequest) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 382 */     if (this.cachedEndpoint == null) {
/* 383 */       throw new NoEndPointException();
/*     */     }
/* 385 */     Call _call = createCall();
/* 386 */     _call.setOperation(_operations[0]);
/* 387 */     _call.setUseSOAPAction(true);
/* 388 */     _call.setSOAPActionURI("TransactionManager.Payment#getEasyCollectSingle");
/* 389 */     _call.setEncodingStyle(null);
/* 390 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 391 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 392 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 393 */     _call.setOperationName(new QName("TransactionManager.Payment", "getEasyCollectSingle"));
/*     */ 
/* 395 */     setRequestHeaders(_call);
/* 396 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { sRequest });
/*     */ 
/* 399 */       if ((_resp instanceof RemoteException)) {
/* 400 */         throw ((RemoteException)_resp);
/*     */       }
/* 402 */       extractAttachments(_call);
/*     */       try {
/* 404 */         return (TransactionManagerPaymentPaymentResponse)_resp;
/*     */       } catch (Exception _exception) {
/* 406 */         return (TransactionManagerPaymentPaymentResponse)JavaUtils.convert(_resp, TransactionManagerPaymentPaymentResponse.class);
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 409 */       if (axisFaultException.detail != null) {
/* 410 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 411 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 413 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 414 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 417 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public TransactionManagerPaymentPaymentResponse540 getEasyCollectSingle540(TransactionManagerPaymentSingleRequest sRequest) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 422 */     if (this.cachedEndpoint == null) {
/* 423 */       throw new NoEndPointException();
/*     */     }
/* 425 */     Call _call = createCall();
/* 426 */     _call.setOperation(_operations[1]);
/* 427 */     _call.setUseSOAPAction(true);
/* 428 */     _call.setSOAPActionURI("TransactionManager.Payment#getEasyCollectSingle540");
/* 429 */     _call.setEncodingStyle(null);
/* 430 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 431 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 432 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 433 */     _call.setOperationName(new QName("TransactionManager.Payment", "getEasyCollectSingle540"));
/*     */ 
/* 435 */     setRequestHeaders(_call);
/* 436 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { sRequest });
/*     */ 
/* 439 */       if ((_resp instanceof RemoteException)) {
/* 440 */         throw ((RemoteException)_resp);
/*     */       }
/* 442 */       extractAttachments(_call);
/*     */       try {
/* 444 */         return (TransactionManagerPaymentPaymentResponse540)_resp;
/*     */       } catch (Exception _exception) {
/* 446 */         return (TransactionManagerPaymentPaymentResponse540)JavaUtils.convert(_resp, TransactionManagerPaymentPaymentResponse540.class);
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 449 */       if (axisFaultException.detail != null) {
/* 450 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 451 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 453 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 454 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 457 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public boolean commitEasyCollectBDR(long sellerID, String tmPassword, long slaveMerchantID, long BDRID) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 462 */     if (this.cachedEndpoint == null) {
/* 463 */       throw new NoEndPointException();
/*     */     }
/* 465 */     Call _call = createCall();
/* 466 */     _call.setOperation(_operations[2]);
/* 467 */     _call.setUseSOAPAction(true);
/* 468 */     _call.setSOAPActionURI("TransactionManager.Payment#commitEasyCollectBDR");
/* 469 */     _call.setEncodingStyle(null);
/* 470 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 471 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 472 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 473 */     _call.setOperationName(new QName("TransactionManager.Payment", "commitEasyCollectBDR"));
/*     */ 
/* 475 */     setRequestHeaders(_call);
/* 476 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(BDRID) });
/*     */ 
/* 479 */       if ((_resp instanceof RemoteException)) {
/* 480 */         throw ((RemoteException)_resp);
/*     */       }
/* 482 */       extractAttachments(_call);
/*     */       try {
/* 484 */         return ((Boolean)_resp).booleanValue();
/*     */       } catch (Exception _exception) {
/* 486 */         return ((Boolean)JavaUtils.convert(_resp, Boolean.TYPE)).booleanValue();
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 489 */       if (axisFaultException.detail != null) {
/* 490 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 491 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 493 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 494 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 497 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public long partialEasyCollectBegin(long sellerID, String tmPassword, long extJobID) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 502 */     if (this.cachedEndpoint == null) {
/* 503 */       throw new NoEndPointException();
/*     */     }
/* 505 */     Call _call = createCall();
/* 506 */     _call.setOperation(_operations[3]);
/* 507 */     _call.setUseSOAPAction(true);
/* 508 */     _call.setSOAPActionURI("TransactionManager.Payment#partialEasyCollectBegin");
/* 509 */     _call.setEncodingStyle(null);
/* 510 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 511 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 512 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 513 */     _call.setOperationName(new QName("TransactionManager.Payment", "partialEasyCollectBegin"));
/*     */ 
/* 515 */     setRequestHeaders(_call);
/* 516 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(extJobID) });
/*     */ 
/* 519 */       if ((_resp instanceof RemoteException)) {
/* 520 */         throw ((RemoteException)_resp);
/*     */       }
/* 522 */       extractAttachments(_call);
/*     */       try {
/* 524 */         return ((Long)_resp).longValue();
/*     */       } catch (Exception _exception) {
/* 526 */         return ((Long)JavaUtils.convert(_resp, Long.TYPE)).longValue();
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 529 */       if (axisFaultException.detail != null) {
/* 530 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 531 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 533 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 534 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 537 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public long partialEasyCollectAddSingleItem(long sellerID, String tmPassword, long mJobID, TransactionManagerPaymentPaymentRequest item) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 542 */     if (this.cachedEndpoint == null) {
/* 543 */       throw new NoEndPointException();
/*     */     }
/* 545 */     Call _call = createCall();
/* 546 */     _call.setOperation(_operations[4]);
/* 547 */     _call.setUseSOAPAction(true);
/* 548 */     _call.setSOAPActionURI("TransactionManager.Payment#partialEasyCollectAddSingleItem");
/* 549 */     _call.setEncodingStyle(null);
/* 550 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 551 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 552 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 553 */     _call.setOperationName(new QName("TransactionManager.Payment", "partialEasyCollectAddSingleItem"));
/*     */ 
/* 555 */     setRequestHeaders(_call);
/* 556 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(mJobID), item });
/*     */ 
/* 559 */       if ((_resp instanceof RemoteException)) {
/* 560 */         throw ((RemoteException)_resp);
/*     */       }
/* 562 */       extractAttachments(_call);
/*     */       try {
/* 564 */         return ((Long)_resp).longValue();
/*     */       } catch (Exception _exception) {
/* 566 */         return ((Long)JavaUtils.convert(_resp, Long.TYPE)).longValue();
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 569 */       if (axisFaultException.detail != null) {
/* 570 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 571 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 573 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 574 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 577 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public long partialEasyCollectAddItems(long sellerID, String tmPassword, long mJobID, TransactionManagerPaymentPaymentRequest[] mItems) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 582 */     if (this.cachedEndpoint == null) {
/* 583 */       throw new NoEndPointException();
/*     */     }
/* 585 */     Call _call = createCall();
/* 586 */     _call.setOperation(_operations[5]);
/* 587 */     _call.setUseSOAPAction(true);
/* 588 */     _call.setSOAPActionURI("TransactionManager.Payment#partialEasyCollectAddItems");
/* 589 */     _call.setEncodingStyle(null);
/* 590 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 591 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 592 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 593 */     _call.setOperationName(new QName("TransactionManager.Payment", "partialEasyCollectAddItems"));
/*     */ 
/* 595 */     setRequestHeaders(_call);
/* 596 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(mJobID), mItems });
/*     */ 
/* 599 */       if ((_resp instanceof RemoteException)) {
/* 600 */         throw ((RemoteException)_resp);
/*     */       }
/* 602 */       extractAttachments(_call);
/*     */       try {
/* 604 */         return ((Long)_resp).longValue();
/*     */       } catch (Exception _exception) {
/* 606 */         return ((Long)JavaUtils.convert(_resp, Long.TYPE)).longValue();
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 609 */       if (axisFaultException.detail != null) {
/* 610 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 611 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 613 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 614 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 617 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public long partialEasyCollectFinish(long sellerID, String tmPassword, long mJobID) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 622 */     if (this.cachedEndpoint == null) {
/* 623 */       throw new NoEndPointException();
/*     */     }
/* 625 */     Call _call = createCall();
/* 626 */     _call.setOperation(_operations[6]);
/* 627 */     _call.setUseSOAPAction(true);
/* 628 */     _call.setSOAPActionURI("TransactionManager.Payment#partialEasyCollectFinish");
/* 629 */     _call.setEncodingStyle(null);
/* 630 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 631 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 632 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 633 */     _call.setOperationName(new QName("TransactionManager.Payment", "partialEasyCollectFinish"));
/*     */ 
/* 635 */     setRequestHeaders(_call);
/* 636 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(mJobID) });
/*     */ 
/* 639 */       if ((_resp instanceof RemoteException)) {
/* 640 */         throw ((RemoteException)_resp);
/*     */       }
/* 642 */       extractAttachments(_call);
/*     */       try {
/* 644 */         return ((Long)_resp).longValue();
/*     */       } catch (Exception _exception) {
/* 646 */         return ((Long)JavaUtils.convert(_resp, Long.TYPE)).longValue();
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 649 */       if (axisFaultException.detail != null) {
/* 650 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 651 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 653 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 654 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 657 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public long placeEasyCollectMulti(TransactionManagerPaymentMultiRequest mRequest) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 662 */     if (this.cachedEndpoint == null) {
/* 663 */       throw new NoEndPointException();
/*     */     }
/* 665 */     Call _call = createCall();
/* 666 */     _call.setOperation(_operations[7]);
/* 667 */     _call.setUseSOAPAction(true);
/* 668 */     _call.setSOAPActionURI("TransactionManager.Payment#placeEasyCollectMulti");
/* 669 */     _call.setEncodingStyle(null);
/* 670 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 671 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 672 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 673 */     _call.setOperationName(new QName("TransactionManager.Payment", "placeEasyCollectMulti"));
/*     */ 
/* 675 */     setRequestHeaders(_call);
/* 676 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { mRequest });
/*     */ 
/* 679 */       if ((_resp instanceof RemoteException)) {
/* 680 */         throw ((RemoteException)_resp);
/*     */       }
/* 682 */       extractAttachments(_call);
/*     */       try {
/* 684 */         return ((Long)_resp).longValue();
/*     */       } catch (Exception _exception) {
/* 686 */         return ((Long)JavaUtils.convert(_resp, Long.TYPE)).longValue();
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 689 */       if (axisFaultException.detail != null) {
/* 690 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 691 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 693 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 694 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 697 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  20 */     _initOperationDesc1();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerPaymentBindingStub
 * JD-Core Version:    0.6.0
 */