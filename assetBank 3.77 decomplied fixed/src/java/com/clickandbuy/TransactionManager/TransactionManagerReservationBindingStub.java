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
/*     */ import org.apache.axis.soap.SOAPConstants;
/*     */ import org.apache.axis.types.UnsignedInt;
/*     */ import org.apache.axis.utils.JavaUtils;
/*     */ 
/*     */ public class TransactionManagerReservationBindingStub extends Stub
/*     */   implements TransactionManagerReservation
/*     */ {
/*  11 */   private Vector cachedSerClasses = new Vector();
/*  12 */   private Vector cachedSerQNames = new Vector();
/*  13 */   private Vector cachedSerFactories = new Vector();
/*  14 */   private Vector cachedDeserFactories = new Vector();
/*     */ 
/*  19 */   static OperationDesc[] _operations = new OperationDesc[3];
/*     */ 
/*     */   private static void _initOperationDesc1()
/*     */   {
/*  26 */     OperationDesc oper = new OperationDesc();
/*  27 */     oper.setName("createReservation");
/*  28 */     ParameterDesc param = new ParameterDesc(new QName("", "sellerID"),(byte) 1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  29 */     oper.addParameter(param);
/*  30 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  31 */     oper.addParameter(param);
/*  32 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  33 */     oper.addParameter(param);
/*  34 */     param = new ParameterDesc(new QName("", "easyCollectID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  35 */     oper.addParameter(param);
/*  36 */     param = new ParameterDesc(new QName("", "crn"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  37 */     oper.addParameter(param);
/*  38 */     param = new ParameterDesc(new QName("", "amount"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt"), UnsignedInt.class, false, false);
/*  39 */     oper.addParameter(param);
/*  40 */     param = new ParameterDesc(new QName("", "currency"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  41 */     oper.addParameter(param);
/*  42 */     param = new ParameterDesc(new QName("", "offerDescription"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  43 */     oper.addParameter(param);
/*  44 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.ReservationResult"));
/*  45 */     oper.setReturnClass(ClickAndBuyEasyCollectReservationResult.class);
/*  46 */     oper.setReturnQName(new QName("", "_return"));
/*  47 */     oper.setStyle(Style.RPC);
/*  48 */     oper.setUse(Use.LITERAL);
/*  49 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Reservation", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/*  55 */     _operations[0] = oper;
/*     */ 
/*  57 */     oper = new OperationDesc();
/*  58 */     oper.setName("capture");
/*  59 */     param = new ParameterDesc(new QName("", "sellerID"), 1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  60 */     oper.addParameter(param);
/*  61 */     param = new ParameterDesc(new QName("", "tmPassword"), 1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  62 */     oper.addParameter(param);
/*  63 */     param = new ParameterDesc(new QName("", "reservationID"), 1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  64 */     oper.addParameter(param);
/*  65 */     param = new ParameterDesc(new QName("", "request"), 1, new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectQuery"), ClickAndBuyEasyCollectEasyCollectQuery.class, false, false);
/*  66 */     oper.addParameter(param);
/*  67 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult"));
/*  68 */     oper.setReturnClass(ClickAndBuyEasyCollectEasyCollectResult.class);
/*  69 */     oper.setReturnQName(new QName("", "_return"));
/*  70 */     oper.setStyle(Style.RPC);
/*  71 */     oper.setUse(Use.LITERAL);
/*  72 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Reservation", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/*  78 */     _operations[1] = oper;
/*     */ 
/*  80 */     oper = new OperationDesc();
/*  81 */     oper.setName("cancelReservation");
/*  82 */     param = new ParameterDesc(new QName("", "sellerID"), 1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  83 */     oper.addParameter(param);
/*  84 */     param = new ParameterDesc(new QName("", "tmPassword"), 1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  85 */     oper.addParameter(param);
/*  86 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), 1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  87 */     oper.addParameter(param);
/*  88 */     param = new ParameterDesc(new QName("", "reservationID"), 1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  89 */     oper.addParameter(param);
/*  90 */     param = new ParameterDesc(new QName("", "crn"), 1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  91 */     oper.addParameter(param);
/*  92 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/*  93 */     oper.setReturnClass(Boolean.TYPE);
/*  94 */     oper.setReturnQName(new QName("", "_return"));
/*  95 */     oper.setStyle(Style.RPC);
/*  96 */     oper.setUse(Use.LITERAL);
/*  97 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Reservation", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerPaymentPaymentException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException"), true));
/*     */ 
/* 103 */     _operations[2] = oper;
/*     */   }
/*     */ 
/*     */   public TransactionManagerReservationBindingStub() throws AxisFault
/*     */   {
/* 108 */     this(null);
/*     */   }
/*     */ 
/*     */   public TransactionManagerReservationBindingStub(URL endpointURL, javax.xml.rpc.Service service) throws AxisFault {
/* 112 */     this(service);
/* 113 */     this.cachedEndpoint = endpointURL;
/*     */   }
/*     */ 
/*     */   public TransactionManagerReservationBindingStub(javax.xml.rpc.Service service) throws AxisFault {
/* 117 */     if (service == null)
/* 118 */       this.service = new org.apache.axis.client.Service();
/*     */     else {
/* 120 */       this.service = service;
/*     */     }
/* 122 */     ((org.apache.axis.client.Service)this.service).setTypeMappingVersion("1.2");
/*     */ 
/* 125 */     Class beansf = BeanSerializerFactory.class;
/* 126 */     Class beandf = BeanDeserializerFactory.class;
/* 127 */     QName qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectQuery");
/* 128 */     this.cachedSerQNames.add(qName);
/* 129 */     Class cls = ClickAndBuyEasyCollectEasyCollectQuery.class;
/* 130 */     this.cachedSerClasses.add(cls);
/* 131 */     this.cachedSerFactories.add(beansf);
/* 132 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 134 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult");
/* 135 */     this.cachedSerQNames.add(qName);
/* 136 */     cls = ClickAndBuyEasyCollectEasyCollectResult.class;
/* 137 */     this.cachedSerClasses.add(cls);
/* 138 */     this.cachedSerFactories.add(beansf);
/* 139 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 141 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.ReservationResult");
/* 142 */     this.cachedSerQNames.add(qName);
/* 143 */     cls = ClickAndBuyEasyCollectReservationResult.class;
/* 144 */     this.cachedSerClasses.add(cls);
/* 145 */     this.cachedSerFactories.add(beansf);
/* 146 */     this.cachedDeserFactories.add(beandf);
/*     */ 
/* 148 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentException");
/* 149 */     this.cachedSerQNames.add(qName);
/* 150 */     cls = TransactionManagerPaymentPaymentException.class;
/* 151 */     this.cachedSerClasses.add(cls);
/* 152 */     this.cachedSerFactories.add(beansf);
/* 153 */     this.cachedDeserFactories.add(beandf);
/*     */   }
/*     */ 
/*     */   protected Call createCall() throws RemoteException
/*     */   {
/*     */     try {
/* 159 */       Call _call = super._createCall();
/* 160 */       if (this.maintainSessionSet) {
/* 161 */         _call.setMaintainSession(this.maintainSession);
/*     */       }
/* 163 */       if (this.cachedUsername != null) {
/* 164 */         _call.setUsername(this.cachedUsername);
/*     */       }
/* 166 */       if (this.cachedPassword != null) {
/* 167 */         _call.setPassword(this.cachedPassword);
/*     */       }
/* 169 */       if (this.cachedEndpoint != null) {
/* 170 */         _call.setTargetEndpointAddress(this.cachedEndpoint);
/*     */       }
/* 172 */       if (this.cachedTimeout != null) {
/* 173 */         _call.setTimeout(this.cachedTimeout);
/*     */       }
/* 175 */       if (this.cachedPortName != null) {
/* 176 */         _call.setPortName(this.cachedPortName);
/*     */       }
/* 178 */       Enumeration keys = this.cachedProperties.keys();
/* 179 */       while (keys.hasMoreElements()) {
/* 180 */         String key = (String)keys.nextElement();
/* 181 */         _call.setProperty(key, this.cachedProperties.get(key));
/*     */       }
/*     */ 
/* 188 */       synchronized (this) {
/* 189 */         if (firstCall())
/*     */         {
/* 191 */           _call.setEncodingStyle(null);
/* 192 */           for (int i = 0; i < this.cachedSerFactories.size(); i++) {
/* 193 */             Class cls = (Class)this.cachedSerClasses.get(i);
/* 194 */             QName qName = (QName)this.cachedSerQNames.get(i);
/*     */ 
/* 196 */             Object x = this.cachedSerFactories.get(i);
/* 197 */             if ((x instanceof Class)) {
/* 198 */               Class sf = (Class)this.cachedSerFactories.get(i);
/*     */ 
/* 200 */               Class df = (Class)this.cachedDeserFactories.get(i);
/*     */ 
/* 202 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*     */             }
/* 204 */             else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory)) {
/* 205 */               org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
/*     */ 
/* 207 */               DeserializerFactory df = (DeserializerFactory)this.cachedDeserFactories.get(i);
/*     */ 
/* 209 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 214 */       return _call;
/*     */     } catch (Throwable _t) {
/*     */     }
/* 217 */     throw new AxisFault("Failure trying to get the Call object", _t);
/*     */   }
/*     */ 
/*     */   public ClickAndBuyEasyCollectReservationResult createReservation(long sellerID, String tmPassword, long slaveMerchantID, long easyCollectID, long crn, UnsignedInt amount, String currency, String offerDescription) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 222 */     if (this.cachedEndpoint == null) {
/* 223 */       throw new NoEndPointException();
/*     */     }
/* 225 */     Call _call = createCall();
/* 226 */     _call.setOperation(_operations[0]);
/* 227 */     _call.setUseSOAPAction(true);
/* 228 */     _call.setSOAPActionURI("TransactionManager.Reservation#createReservation");
/* 229 */     _call.setEncodingStyle(null);
/* 230 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 231 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 232 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 233 */     _call.setOperationName(new QName("TransactionManager.Reservation", "createReservation"));
/*     */ 
/* 235 */     setRequestHeaders(_call);
/* 236 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(easyCollectID), new Long(crn), amount, currency, offerDescription });
/*     */ 
/* 239 */       if ((_resp instanceof RemoteException)) {
/* 240 */         throw ((RemoteException)_resp);
/*     */       }
/* 242 */       extractAttachments(_call);
/*     */       try {
/* 244 */         return (ClickAndBuyEasyCollectReservationResult)_resp;
/*     */       } catch (Exception _exception) {
/* 246 */         return (ClickAndBuyEasyCollectReservationResult)JavaUtils.convert(_resp, ClickAndBuyEasyCollectReservationResult.class);
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 249 */       if (axisFaultException.detail != null) {
/* 250 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 251 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 253 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 254 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 257 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public ClickAndBuyEasyCollectEasyCollectResult capture(long sellerID, String tmPassword, long reservationID, ClickAndBuyEasyCollectEasyCollectQuery request) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 262 */     if (this.cachedEndpoint == null) {
/* 263 */       throw new NoEndPointException();
/*     */     }
/* 265 */     Call _call = createCall();
/* 266 */     _call.setOperation(_operations[1]);
/* 267 */     _call.setUseSOAPAction(true);
/* 268 */     _call.setSOAPActionURI("TransactionManager.Reservation#capture");
/* 269 */     _call.setEncodingStyle(null);
/* 270 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 271 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 272 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 273 */     _call.setOperationName(new QName("TransactionManager.Reservation", "capture"));
/*     */ 
/* 275 */     setRequestHeaders(_call);
/* 276 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(reservationID), request });
/*     */ 
/* 279 */       if ((_resp instanceof RemoteException)) {
/* 280 */         throw ((RemoteException)_resp);
/*     */       }
/* 282 */       extractAttachments(_call);
/*     */       try {
/* 284 */         return (ClickAndBuyEasyCollectEasyCollectResult)_resp;
/*     */       } catch (Exception _exception) {
/* 286 */         return (ClickAndBuyEasyCollectEasyCollectResult)JavaUtils.convert(_resp, ClickAndBuyEasyCollectEasyCollectResult.class);
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 289 */       if (axisFaultException.detail != null) {
/* 290 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 291 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 293 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 294 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 297 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   public boolean cancelReservation(long sellerID, String tmPassword, long slaveMerchantID, long reservationID, long crn) throws RemoteException, TransactionManagerPaymentPaymentException
/*     */   {
/* 302 */     if (this.cachedEndpoint == null) {
/* 303 */       throw new NoEndPointException();
/*     */     }
/* 305 */     Call _call = createCall();
/* 306 */     _call.setOperation(_operations[2]);
/* 307 */     _call.setUseSOAPAction(true);
/* 308 */     _call.setSOAPActionURI("TransactionManager.Reservation#cancelReservation");
/* 309 */     _call.setEncodingStyle(null);
/* 310 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 311 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 312 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 313 */     _call.setOperationName(new QName("TransactionManager.Reservation", "cancelReservation"));
/*     */ 
/* 315 */     setRequestHeaders(_call);
/* 316 */     setAttachments(_call);
/*     */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(reservationID), new Long(crn) });
/*     */ 
/* 319 */       if ((_resp instanceof RemoteException)) {
/* 320 */         throw ((RemoteException)_resp);
/*     */       }
/* 322 */       extractAttachments(_call);
/*     */       try {
/* 324 */         return ((Boolean)_resp).booleanValue();
/*     */       } catch (Exception _exception) {
/* 326 */         return ((Boolean)JavaUtils.convert(_resp, Boolean.TYPE)).booleanValue();
/*     */       }
/*     */     } catch (AxisFault axisFaultException) {
/* 329 */       if (axisFaultException.detail != null) {
/* 330 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 331 */           throw ((RemoteException)axisFaultException.detail);
/*     */         }
/* 333 */         if ((axisFaultException.detail instanceof TransactionManagerPaymentPaymentException))
/* 334 */           throw ((TransactionManagerPaymentPaymentException)axisFaultException.detail);
/*     */       }
/*     */     }
/* 337 */     throw axisFaultException;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  20 */     _initOperationDesc1();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerReservationBindingStub
 * JD-Core Version:    0.6.0
 */