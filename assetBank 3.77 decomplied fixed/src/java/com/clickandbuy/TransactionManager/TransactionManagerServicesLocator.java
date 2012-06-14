/*     */ package com.clickandbuy.TransactionManager;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.rmi.Remote;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ import org.apache.axis.AxisFault;
/*     */ import org.apache.axis.EngineConfiguration;
/*     */ import org.apache.axis.client.Service;
/*     */ import org.apache.axis.client.Stub;
/*     */ 
/*     */ public class TransactionManagerServicesLocator extends Service
/*     */   implements TransactionManagerServices
/*     */ {
/*  29 */   private String TransactionManagerStatusServicesPort_address = "https://services.uk.clickandbuy.com/TMI/1.4/";
/*     */ 
/*  36 */   private String TransactionManagerStatusServicesPortWSDDServiceName = "TransactionManagerStatusServicesPort";
/*     */ 
/*  74 */   private String TransactionManagerReservationServicesPort_address = "https://services.uk.clickandbuy.com/TMI/1.4/";
/*     */ 
/*  81 */   private String TransactionManagerReservationServicesPortWSDDServiceName = "TransactionManagerReservationServicesPort";
/*     */ 
/* 119 */   private String TransactionManagerPaymentServicesPort_address = "https://services.uk.clickandbuy.com/TMI/1.4/";
/*     */ 
/* 126 */   private String TransactionManagerPaymentServicesPortWSDDServiceName = "TransactionManagerPaymentServicesPort";
/*     */ 
/* 164 */   private String TransactionManagerECommerceServicesPort_address = "https://services.uk.clickandbuy.com/TMI/1.4/";
/*     */ 
/* 171 */   private String TransactionManagerECommerceServicesPortWSDDServiceName = "TransactionManagerECommerceServicesPort";
/*     */ 
/* 274 */   private HashSet ports = null;
/*     */ 
/*     */   public TransactionManagerServicesLocator()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TransactionManagerServicesLocator(EngineConfiguration config)
/*     */   {
/*  21 */     super(config);
/*     */   }
/*     */ 
/*     */   public TransactionManagerServicesLocator(String wsdlLoc, QName sName) throws ServiceException {
/*  25 */     super(wsdlLoc, sName);
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerStatusServicesPortAddress()
/*     */   {
/*  32 */     return this.TransactionManagerStatusServicesPort_address;
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerStatusServicesPortWSDDServiceName()
/*     */   {
/*  39 */     return this.TransactionManagerStatusServicesPortWSDDServiceName;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerStatusServicesPortWSDDServiceName(String name) {
/*  43 */     this.TransactionManagerStatusServicesPortWSDDServiceName = name;
/*     */   }
/*     */   public TransactionManagerStatus getTransactionManagerStatusServicesPort() throws ServiceException {
/*     */     URL endpoint;
/*     */     try {
/*  49 */       endpoint = new URL(this.TransactionManagerStatusServicesPort_address);
/*     */     }
/*     */     catch (MalformedURLException e) {
/*  52 */       throw new ServiceException(e);
/*     */     }
/*  54 */     return getTransactionManagerStatusServicesPort(endpoint);
/*     */   }
/*     */ 
/*     */   public TransactionManagerStatus getTransactionManagerStatusServicesPort(URL portAddress) throws ServiceException {
/*     */     try {
/*  59 */       TransactionManagerStatusBindingStub _stub = new TransactionManagerStatusBindingStub(portAddress, this);
/*  60 */       _stub.setPortName(getTransactionManagerStatusServicesPortWSDDServiceName());
/*  61 */       return _stub;
/*     */     } catch (AxisFault e) {
/*     */     }
/*  64 */     return null;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerStatusServicesPortEndpointAddress(String address)
/*     */   {
/*  69 */     this.TransactionManagerStatusServicesPort_address = address;
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerReservationServicesPortAddress()
/*     */   {
/*  77 */     return this.TransactionManagerReservationServicesPort_address;
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerReservationServicesPortWSDDServiceName()
/*     */   {
/*  84 */     return this.TransactionManagerReservationServicesPortWSDDServiceName;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerReservationServicesPortWSDDServiceName(String name) {
/*  88 */     this.TransactionManagerReservationServicesPortWSDDServiceName = name;
/*     */   }
/*     */   public TransactionManagerReservation getTransactionManagerReservationServicesPort() throws ServiceException {
/*     */     URL endpoint;
/*     */     try {
/*  94 */       endpoint = new URL(this.TransactionManagerReservationServicesPort_address);
/*     */     }
/*     */     catch (MalformedURLException e) {
/*  97 */       throw new ServiceException(e);
/*     */     }
/*  99 */     return getTransactionManagerReservationServicesPort(endpoint);
/*     */   }
/*     */ 
/*     */   public TransactionManagerReservation getTransactionManagerReservationServicesPort(URL portAddress) throws ServiceException {
/*     */     try {
/* 104 */       TransactionManagerReservationBindingStub _stub = new TransactionManagerReservationBindingStub(portAddress, this);
/* 105 */       _stub.setPortName(getTransactionManagerReservationServicesPortWSDDServiceName());
/* 106 */       return _stub;
/*     */     } catch (AxisFault e) {
/*     */     }
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerReservationServicesPortEndpointAddress(String address)
/*     */   {
/* 114 */     this.TransactionManagerReservationServicesPort_address = address;
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerPaymentServicesPortAddress()
/*     */   {
/* 122 */     return this.TransactionManagerPaymentServicesPort_address;
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerPaymentServicesPortWSDDServiceName()
/*     */   {
/* 129 */     return this.TransactionManagerPaymentServicesPortWSDDServiceName;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerPaymentServicesPortWSDDServiceName(String name) {
/* 133 */     this.TransactionManagerPaymentServicesPortWSDDServiceName = name;
/*     */   }
/*     */   public TransactionManagerPayment getTransactionManagerPaymentServicesPort() throws ServiceException {
/*     */     URL endpoint;
/*     */     try {
/* 139 */       endpoint = new URL(this.TransactionManagerPaymentServicesPort_address);
/*     */     }
/*     */     catch (MalformedURLException e) {
/* 142 */       throw new ServiceException(e);
/*     */     }
/* 144 */     return getTransactionManagerPaymentServicesPort(endpoint);
/*     */   }
/*     */ 
/*     */   public TransactionManagerPayment getTransactionManagerPaymentServicesPort(URL portAddress) throws ServiceException {
/*     */     try {
/* 149 */       TransactionManagerPaymentBindingStub _stub = new TransactionManagerPaymentBindingStub(portAddress, this);
/* 150 */       _stub.setPortName(getTransactionManagerPaymentServicesPortWSDDServiceName());
/* 151 */       return _stub;
/*     */     } catch (AxisFault e) {
/*     */     }
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerPaymentServicesPortEndpointAddress(String address)
/*     */   {
/* 159 */     this.TransactionManagerPaymentServicesPort_address = address;
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerECommerceServicesPortAddress()
/*     */   {
/* 167 */     return this.TransactionManagerECommerceServicesPort_address;
/*     */   }
/*     */ 
/*     */   public String getTransactionManagerECommerceServicesPortWSDDServiceName()
/*     */   {
/* 174 */     return this.TransactionManagerECommerceServicesPortWSDDServiceName;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerECommerceServicesPortWSDDServiceName(String name) {
/* 178 */     this.TransactionManagerECommerceServicesPortWSDDServiceName = name;
/*     */   }
/*     */   public TransactionManagerECommerce getTransactionManagerECommerceServicesPort() throws ServiceException {
/*     */     URL endpoint;
/*     */     try {
/* 184 */       endpoint = new URL(this.TransactionManagerECommerceServicesPort_address);
/*     */     }
/*     */     catch (MalformedURLException e) {
/* 187 */       throw new ServiceException(e);
/*     */     }
/* 189 */     return getTransactionManagerECommerceServicesPort(endpoint);
/*     */   }
/*     */ 
/*     */   public TransactionManagerECommerce getTransactionManagerECommerceServicesPort(URL portAddress) throws ServiceException {
/*     */     try {
/* 194 */       TransactionManagerECommerceBindingStub _stub = new TransactionManagerECommerceBindingStub(portAddress, this);
/* 195 */       _stub.setPortName(getTransactionManagerECommerceServicesPortWSDDServiceName());
/* 196 */       return _stub;
/*     */     } catch (AxisFault e) {
/*     */     }
/* 199 */     return null;
/*     */   }
/*     */ 
/*     */   public void setTransactionManagerECommerceServicesPortEndpointAddress(String address)
/*     */   {
/* 204 */     this.TransactionManagerECommerceServicesPort_address = address;
/*     */   }
/*     */ 
/*     */   public Remote getPort(Class serviceEndpointInterface)
/*     */     throws ServiceException
/*     */   {
/*     */     try
/*     */     {
/* 214 */       if (TransactionManagerStatus.class.isAssignableFrom(serviceEndpointInterface)) {
/* 215 */         TransactionManagerStatusBindingStub _stub = new TransactionManagerStatusBindingStub(new URL(this.TransactionManagerStatusServicesPort_address), this);
/* 216 */         _stub.setPortName(getTransactionManagerStatusServicesPortWSDDServiceName());
/* 217 */         return _stub;
/*     */       }
/* 219 */       if (TransactionManagerReservation.class.isAssignableFrom(serviceEndpointInterface)) {
/* 220 */         TransactionManagerReservationBindingStub _stub = new TransactionManagerReservationBindingStub(new URL(this.TransactionManagerReservationServicesPort_address), this);
/* 221 */         _stub.setPortName(getTransactionManagerReservationServicesPortWSDDServiceName());
/* 222 */         return _stub;
/*     */       }
/* 224 */       if (TransactionManagerPayment.class.isAssignableFrom(serviceEndpointInterface)) {
/* 225 */         TransactionManagerPaymentBindingStub _stub = new TransactionManagerPaymentBindingStub(new URL(this.TransactionManagerPaymentServicesPort_address), this);
/* 226 */         _stub.setPortName(getTransactionManagerPaymentServicesPortWSDDServiceName());
/* 227 */         return _stub;
/*     */       }
/* 229 */       if (TransactionManagerECommerce.class.isAssignableFrom(serviceEndpointInterface)) {
/* 230 */         TransactionManagerECommerceBindingStub _stub = new TransactionManagerECommerceBindingStub(new URL(this.TransactionManagerECommerceServicesPort_address), this);
/* 231 */         _stub.setPortName(getTransactionManagerECommerceServicesPortWSDDServiceName());
/* 232 */         return _stub;
/*     */       }
/*     */     }
/*     */     catch (Throwable t) {
/* 236 */       throw new ServiceException(t);
/*     */     }
/* 238 */     throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
/*     */   }
/*     */ 
/*     */   public Remote getPort(QName portName, Class serviceEndpointInterface)
/*     */     throws ServiceException
/*     */   {
/* 247 */     if (portName == null) {
/* 248 */       return getPort(serviceEndpointInterface);
/*     */     }
/* 250 */     String inputPortName = portName.getLocalPart();
/* 251 */     if ("TransactionManagerStatusServicesPort".equals(inputPortName)) {
/* 252 */       return getTransactionManagerStatusServicesPort();
/*     */     }
/* 254 */     if ("TransactionManagerReservationServicesPort".equals(inputPortName)) {
/* 255 */       return getTransactionManagerReservationServicesPort();
/*     */     }
/* 257 */     if ("TransactionManagerPaymentServicesPort".equals(inputPortName)) {
/* 258 */       return getTransactionManagerPaymentServicesPort();
/*     */     }
/* 260 */     if ("TransactionManagerECommerceServicesPort".equals(inputPortName)) {
/* 261 */       return getTransactionManagerECommerceServicesPort();
/*     */     }
/*     */ 
/* 264 */     Remote _stub = getPort(serviceEndpointInterface);
/* 265 */     ((Stub)_stub).setPortName(portName);
/* 266 */     return _stub;
/*     */   }
/*     */ 
/*     */   public QName getServiceName()
/*     */   {
/* 271 */     return new QName("https://clickandbuy.com/TransactionManager/", "TransactionManagerServices");
/*     */   }
/*     */ 
/*     */   public Iterator getPorts()
/*     */   {
/* 277 */     if (this.ports == null) {
/* 278 */       this.ports = new HashSet();
/* 279 */       this.ports.add(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManagerStatusServicesPort"));
/* 280 */       this.ports.add(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManagerReservationServicesPort"));
/* 281 */       this.ports.add(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManagerPaymentServicesPort"));
/* 282 */       this.ports.add(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManagerECommerceServicesPort"));
/*     */     }
/* 284 */     return this.ports.iterator();
/*     */   }
/*     */ 
/*     */   public void setEndpointAddress(String portName, String address)
/*     */     throws ServiceException
/*     */   {
/* 292 */     if ("TransactionManagerStatusServicesPort".equals(portName)) {
/* 293 */       setTransactionManagerStatusServicesPortEndpointAddress(address);
/*     */     }
/* 296 */     else if ("TransactionManagerReservationServicesPort".equals(portName)) {
/* 297 */       setTransactionManagerReservationServicesPortEndpointAddress(address);
/*     */     }
/* 300 */     else if ("TransactionManagerPaymentServicesPort".equals(portName)) {
/* 301 */       setTransactionManagerPaymentServicesPortEndpointAddress(address);
/*     */     }
/* 304 */     else if ("TransactionManagerECommerceServicesPort".equals(portName)) {
/* 305 */       setTransactionManagerECommerceServicesPortEndpointAddress(address);
/*     */     }
/*     */     else
/*     */     {
/* 309 */       throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setEndpointAddress(QName portName, String address)
/*     */     throws ServiceException
/*     */   {
/* 317 */     setEndpointAddress(portName.getLocalPart(), address);
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerServicesLocator
 * JD-Core Version:    0.6.0
 */