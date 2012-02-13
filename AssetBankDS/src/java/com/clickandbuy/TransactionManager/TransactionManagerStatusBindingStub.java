/*      */ package com.clickandbuy.TransactionManager;
/*      */ 
/*      */ import java.net.URL;
/*      */ import java.rmi.RemoteException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.xml.namespace.QName;
/*      */ import org.apache.axis.AxisFault;
/*      */ import org.apache.axis.NoEndPointException;
/*      */ import org.apache.axis.client.Call;
/*      */ import org.apache.axis.client.Stub;
/*      */ import org.apache.axis.constants.Style;
/*      */ import org.apache.axis.constants.Use;
/*      */ import org.apache.axis.description.FaultDesc;
/*      */ import org.apache.axis.description.OperationDesc;
/*      */ import org.apache.axis.description.ParameterDesc;
/*      */ import org.apache.axis.encoding.DeserializerFactory;
/*      */ import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
/*      */ import org.apache.axis.encoding.ser.ArraySerializerFactory;
/*      */ import org.apache.axis.encoding.ser.BeanDeserializerFactory;
/*      */ import org.apache.axis.encoding.ser.BeanSerializerFactory;
/*      */ import org.apache.axis.encoding.ser.EnumDeserializerFactory;
/*      */ import org.apache.axis.encoding.ser.EnumSerializerFactory;
/*      */ import org.apache.axis.soap.SOAPConstants;
/*      */ import org.apache.axis.utils.JavaUtils;
/*      */ 
/*      */ public class TransactionManagerStatusBindingStub extends Stub
/*      */   implements TransactionManagerStatus
/*      */ {
/*   11 */   private Vector cachedSerClasses = new Vector();
/*   12 */   private Vector cachedSerQNames = new Vector();
/*   13 */   private Vector cachedSerFactories = new Vector();
/*   14 */   private Vector cachedDeserFactories = new Vector();
/*      */ 
/*   19 */   static OperationDesc[] _operations = new OperationDesc[14];
/*      */ 
/*      */   private static void _initOperationDesc1()
/*      */   {
/*   27 */     OperationDesc oper = new OperationDesc();
/*   28 */     oper.setName("getInterfaceStatus");
/*   29 */     ParameterDesc param = new ParameterDesc(new QName("", "sellerID"),(byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*   30 */     oper.addParameter(param);
/*   31 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*   32 */     oper.addParameter(param);
/*   33 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/*   34 */     oper.setReturnClass(Boolean.TYPE);
/*   35 */     oper.setReturnQName(new QName("", "_return"));
/*   36 */     oper.setStyle(Style.RPC);
/*   37 */     oper.setUse(Use.LITERAL);
/*   38 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*   44 */     _operations[0] = oper;
/*      */ 
/*   46 */     oper = new OperationDesc();
/*   47 */     oper.setName("getBDRStatusByBDRID");
/*   48 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*   49 */     oper.addParameter(param);
/*   50 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*   51 */     oper.addParameter(param);
/*   52 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*   53 */     oper.addParameter(param);
/*   54 */     param = new ParameterDesc(new QName("", "BDRID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*   55 */     oper.addParameter(param);
/*   56 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRStatus"));
/*   57 */     oper.setReturnClass(ClickAndBuyTransactionBDRStatus.class);
/*   58 */     oper.setReturnQName(new QName("", "_return"));
/*   59 */     oper.setStyle(Style.RPC);
/*   60 */     oper.setUse(Use.LITERAL);
/*   61 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*   67 */     _operations[1] = oper;
/*      */ 
/*   69 */     oper = new OperationDesc();
/*   70 */     oper.setName("getBDRStatusByExternalBDRID");
/*   71 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*   72 */     oper.addParameter(param);
/*   73 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*   74 */     oper.addParameter(param);
/*   75 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*   76 */     oper.addParameter(param);
/*   77 */     param = new ParameterDesc(new QName("", "externalBDRID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*   78 */     oper.addParameter(param);
/*   79 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRStatus"));
/*   80 */     oper.setReturnClass(ClickAndBuyTransactionBDRStatus.class);
/*   81 */     oper.setReturnQName(new QName("", "_return"));
/*   82 */     oper.setStyle(Style.RPC);
/*   83 */     oper.setUse(Use.LITERAL);
/*   84 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*   90 */     _operations[2] = oper;
/*      */ 
/*   92 */     oper = new OperationDesc();
/*   93 */     oper.setName("getCustomerTransactionStatus");
/*   94 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*   95 */     oper.addParameter(param);
/*   96 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*   97 */     oper.addParameter(param);
/*   98 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*   99 */     oper.addParameter(param);
/*  100 */     param = new ParameterDesc(new QName("", "crn"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  101 */     oper.addParameter(param);
/*  102 */     param = new ParameterDesc(new QName("", "startDateTime"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  103 */     oper.addParameter(param);
/*  104 */     param = new ParameterDesc(new QName("", "endDateTime"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  105 */     oper.addParameter(param);
/*  106 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRStatusList"));
/*  107 */     oper.setReturnClass(com.clickandbuy.TransactionManager.ClickAndBuyTransactionBDRStatus.class);
/*  108 */     oper.setReturnQName(new QName("", "_return"));
/*  109 */     param = oper.getReturnParamDesc();
/*  110 */     param.setItemQName(new QName("", "item"));
/*  111 */     oper.setStyle(Style.RPC);
/*  112 */     oper.setUse(Use.LITERAL);
/*  113 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  119 */     _operations[3] = oper;
/*      */ 
/*  121 */     oper = new OperationDesc();
/*  122 */     oper.setName("getCustomerStatusByCRN");
/*  123 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  124 */     oper.addParameter(param);
/*  125 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  126 */     oper.addParameter(param);
/*  127 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  128 */     oper.addParameter(param);
/*  129 */     param = new ParameterDesc(new QName("", "crn"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  130 */     oper.addParameter(param);
/*  131 */     param = new ParameterDesc(new QName("", "easyCollectID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  132 */     oper.addParameter(param);
/*  133 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CustomerStatus"));
/*  134 */     oper.setReturnClass(ClickAndBuyTransactionCustomerStatus.class);
/*  135 */     oper.setReturnQName(new QName("", "_return"));
/*  136 */     oper.setStyle(Style.RPC);
/*  137 */     oper.setUse(Use.LITERAL);
/*  138 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  144 */     _operations[4] = oper;
/*      */ 
/*  146 */     oper = new OperationDesc();
/*  147 */     oper.setName("getCustomerStatusByMSISDN");
/*  148 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  149 */     oper.addParameter(param);
/*  150 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  151 */     oper.addParameter(param);
/*  152 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  153 */     oper.addParameter(param);
/*  154 */     param = new ParameterDesc(new QName("", "MSISDN"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  155 */     oper.addParameter(param);
/*  156 */     param = new ParameterDesc(new QName("", "easyCollectID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  157 */     oper.addParameter(param);
/*  158 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CustomerStatus"));
/*  159 */     oper.setReturnClass(ClickAndBuyTransactionCustomerStatus.class);
/*  160 */     oper.setReturnQName(new QName("", "_return"));
/*  161 */     oper.setStyle(Style.RPC);
/*  162 */     oper.setUse(Use.LITERAL);
/*  163 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  169 */     _operations[5] = oper;
/*      */ 
/*  171 */     oper = new OperationDesc();
/*  172 */     oper.setName("getTransactionStatusByJobID");
/*  173 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  174 */     oper.addParameter(param);
/*  175 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  176 */     oper.addParameter(param);
/*  177 */     param = new ParameterDesc(new QName("", "jobID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  178 */     oper.addParameter(param);
/*  179 */     param = new ParameterDesc(new QName("", "actionID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  180 */     oper.addParameter(param);
/*  181 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.TransStatusResponse"));
/*  182 */     oper.setReturnClass(TransactionManagerStatusTransStatusResponse.class);
/*  183 */     oper.setReturnQName(new QName("", "_return"));
/*  184 */     oper.setStyle(Style.RPC);
/*  185 */     oper.setUse(Use.LITERAL);
/*  186 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  192 */     _operations[6] = oper;
/*      */ 
/*  194 */     oper = new OperationDesc();
/*  195 */     oper.setName("getTransactionStatusByExternalBDRID");
/*  196 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  197 */     oper.addParameter(param);
/*  198 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  199 */     oper.addParameter(param);
/*  200 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  201 */     oper.addParameter(param);
/*  202 */     param = new ParameterDesc(new QName("", "externalBDRID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  203 */     oper.addParameter(param);
/*  204 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.TransStatusResponse"));
/*  205 */     oper.setReturnClass(TransactionManagerStatusTransStatusResponse.class);
/*  206 */     oper.setReturnQName(new QName("", "_return"));
/*  207 */     oper.setStyle(Style.RPC);
/*  208 */     oper.setUse(Use.LITERAL);
/*  209 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  215 */     _operations[7] = oper;
/*      */ 
/*  217 */     oper = new OperationDesc();
/*  218 */     oper.setName("getJobStatusByJobID");
/*  219 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  220 */     oper.addParameter(param);
/*  221 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  222 */     oper.addParameter(param);
/*  223 */     param = new ParameterDesc(new QName("", "jobID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  224 */     oper.addParameter(param);
/*  225 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.JobStatusResponse"));
/*  226 */     oper.setReturnClass(TransactionManagerStatusJobStatusResponse.class);
/*  227 */     oper.setReturnQName(new QName("", "_return"));
/*  228 */     oper.setStyle(Style.RPC);
/*  229 */     oper.setUse(Use.LITERAL);
/*  230 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  236 */     _operations[8] = oper;
/*      */ 
/*  238 */     oper = new OperationDesc();
/*  239 */     oper.setName("getJobStatusByExtJobID");
/*  240 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  241 */     oper.addParameter(param);
/*  242 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  243 */     oper.addParameter(param);
/*  244 */     param = new ParameterDesc(new QName("", "extJobID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  245 */     oper.addParameter(param);
/*  246 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.JobStatusResponse"));
/*  247 */     oper.setReturnClass(TransactionManagerStatusJobStatusResponse.class);
/*  248 */     oper.setReturnQName(new QName("", "_return"));
/*  249 */     oper.setStyle(Style.RPC);
/*  250 */     oper.setUse(Use.LITERAL);
/*  251 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  257 */     _operations[9] = oper;
/*      */   }
/*      */ 
/*      */   private static void _initOperationDesc2()
/*      */   {
/*  264 */     OperationDesc oper = new OperationDesc();
/*  265 */     oper.setName("getJobItemsStatusByJobID");
/*  266 */     ParameterDesc param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  267 */     oper.addParameter(param);
/*  268 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  269 */     oper.addParameter(param);
/*  270 */     param = new ParameterDesc(new QName("", "jobID"),(byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  271 */     oper.addParameter(param);
/*  272 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.JobDetailResponse"));
/*  273 */     oper.setReturnClass(TransactionManagerStatusJobDetailResponse.class);
/*  274 */     oper.setReturnQName(new QName("", "_return"));
/*  275 */     oper.setStyle(Style.RPC);
/*  276 */     oper.setUse(Use.LITERAL);
/*  277 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  283 */     _operations[10] = oper;
/*      */ 
/*  285 */     oper = new OperationDesc();
/*  286 */     oper.setName("getJobItemsStatusByExtJobID");
/*  287 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  288 */     oper.addParameter(param);
/*  289 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  290 */     oper.addParameter(param);
/*  291 */     param = new ParameterDesc(new QName("", "extJobID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  292 */     oper.addParameter(param);
/*  293 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.JobDetailResponse"));
/*  294 */     oper.setReturnClass(TransactionManagerStatusJobDetailResponse.class);
/*  295 */     oper.setReturnQName(new QName("", "_return"));
/*  296 */     oper.setStyle(Style.RPC);
/*  297 */     oper.setUse(Use.LITERAL);
/*  298 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  304 */     _operations[11] = oper;
/*      */ 
/*  306 */     oper = new OperationDesc();
/*  307 */     oper.setName("isBDRIDCommitted");
/*  308 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  309 */     oper.addParameter(param);
/*  310 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  311 */     oper.addParameter(param);
/*  312 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  313 */     oper.addParameter(param);
/*  314 */     param = new ParameterDesc(new QName("", "BDRID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  315 */     oper.addParameter(param);
/*  316 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRProcessingState"));
/*  317 */     oper.setReturnClass(ClickAndBuyTransactionBDRProcessingState.class);
/*  318 */     oper.setReturnQName(new QName("", "_return"));
/*  319 */     oper.setStyle(Style.RPC);
/*  320 */     oper.setUse(Use.LITERAL);
/*  321 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  327 */     _operations[12] = oper;
/*      */ 
/*  329 */     oper = new OperationDesc();
/*  330 */     oper.setName("isExternalBDRIDCommitted");
/*  331 */     param = new ParameterDesc(new QName("", "sellerID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  332 */     oper.addParameter(param);
/*  333 */     param = new ParameterDesc(new QName("", "tmPassword"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  334 */     oper.addParameter(param);
/*  335 */     param = new ParameterDesc(new QName("", "slaveMerchantID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
/*  336 */     oper.addParameter(param);
/*  337 */     param = new ParameterDesc(new QName("", "externalBDRID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  338 */     oper.addParameter(param);
/*  339 */     oper.setReturnType(new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRProcessingState"));
/*  340 */     oper.setReturnClass(ClickAndBuyTransactionBDRProcessingState.class);
/*  341 */     oper.setReturnQName(new QName("", "_return"));
/*  342 */     oper.setStyle(Style.RPC);
/*  343 */     oper.setUse(Use.LITERAL);
/*  344 */     oper.addFault(new FaultDesc(new QName("TransactionManager.Status", "exception"), "com.clickandbuy.TransactionManager.TransactionManagerStatusStatusException", new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException"), true));
/*      */ 
/*  350 */     _operations[13] = oper;
/*      */   }
/*      */ 
/*      */   public TransactionManagerStatusBindingStub() throws AxisFault
/*      */   {
/*  355 */     this(null);
/*      */   }
/*      */ 
/*      */   public TransactionManagerStatusBindingStub(URL endpointURL, javax.xml.rpc.Service service) throws AxisFault {
/*  359 */     this(service);
/*  360 */     this.cachedEndpoint = endpointURL;
/*      */   }
/*      */ 
/*      */   public TransactionManagerStatusBindingStub(javax.xml.rpc.Service service) throws AxisFault {
/*  364 */     if (service == null)
/*  365 */       this.service = new org.apache.axis.client.Service();
/*      */     else {
/*  367 */       this.service = service;
/*      */     }
/*  369 */     ((org.apache.axis.client.Service)this.service).setTypeMappingVersion("1.2");
/*      */ 
/*  373 */     Class beansf = BeanSerializerFactory.class;
/*  374 */     Class beandf = BeanDeserializerFactory.class;
/*  375 */     Class enumsf = EnumSerializerFactory.class;
/*  376 */     Class enumdf = EnumDeserializerFactory.class;
/*      */ 
/*  378 */     QName qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.EasyCollect.EasyCollectResult");
/*  379 */     this.cachedSerQNames.add(qName);
/*  380 */     Class cls = ClickAndBuyEasyCollectEasyCollectResult.class;
/*  381 */     this.cachedSerClasses.add(cls);
/*  382 */     this.cachedSerFactories.add(beansf);
/*  383 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  385 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Property");
/*  386 */     this.cachedSerQNames.add(qName);
/*  387 */     cls = ClickAndBuyProperty.class;
/*  388 */     this.cachedSerClasses.add(cls);
/*  389 */     this.cachedSerFactories.add(beansf);
/*  390 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  392 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.PropertyList");
/*  393 */     this.cachedSerQNames.add(qName);
/*  394 */     cls = com.clickandbuy.TransactionManager.ClickAndBuyProperty.class;
/*  395 */     this.cachedSerClasses.add(cls);
/*  396 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Property");
/*  397 */     QName qName2 = new QName("", "item");
/*  398 */     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
/*  399 */     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
/*      */ 
/*  401 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRProcessingState");
/*  402 */     this.cachedSerQNames.add(qName);
/*  403 */     cls = ClickAndBuyTransactionBDRProcessingState.class;
/*  404 */     this.cachedSerClasses.add(cls);
/*  405 */     this.cachedSerFactories.add(beansf);
/*  406 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  408 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRStatus");
/*  409 */     this.cachedSerQNames.add(qName);
/*  410 */     cls = ClickAndBuyTransactionBDRStatus.class;
/*  411 */     this.cachedSerClasses.add(cls);
/*  412 */     this.cachedSerFactories.add(beansf);
/*  413 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  415 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRStatusList");
/*  416 */     this.cachedSerQNames.add(qName);
/*  417 */     cls = com.clickandbuy.TransactionManager.ClickAndBuyTransactionBDRStatus.class;
/*  418 */     this.cachedSerClasses.add(cls);
/*  419 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.BDRStatus");
/*  420 */     qName2 = new QName("", "item");
/*  421 */     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
/*  422 */     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
/*      */ 
/*  424 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CancelResult");
/*  425 */     this.cachedSerQNames.add(qName);
/*  426 */     cls = ClickAndBuyTransactionCancelResult.class;
/*  427 */     this.cachedSerClasses.add(cls);
/*  428 */     this.cachedSerFactories.add(beansf);
/*  429 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  431 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CustomerStatus");
/*  432 */     this.cachedSerQNames.add(qName);
/*  433 */     cls = ClickAndBuyTransactionCustomerStatus.class;
/*  434 */     this.cachedSerClasses.add(cls);
/*  435 */     this.cachedSerFactories.add(beansf);
/*  436 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  438 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "ClickAndBuy.Transaction.CustomerStatusType");
/*  439 */     this.cachedSerQNames.add(qName);
/*  440 */     cls = ClickAndBuyTransactionCustomerStatusType.class;
/*  441 */     this.cachedSerClasses.add(cls);
/*  442 */     this.cachedSerFactories.add(enumsf);
/*  443 */     this.cachedDeserFactories.add(enumdf);
/*      */ 
/*  445 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.PaymentResponse");
/*  446 */     this.cachedSerQNames.add(qName);
/*  447 */     cls = TransactionManagerPaymentPaymentResponse.class;
/*  448 */     this.cachedSerClasses.add(cls);
/*  449 */     this.cachedSerFactories.add(beansf);
/*  450 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  452 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Payment.TransactionType");
/*  453 */     this.cachedSerQNames.add(qName);
/*  454 */     cls = TransactionManagerPaymentTransactionType.class;
/*  455 */     this.cachedSerClasses.add(cls);
/*  456 */     this.cachedSerFactories.add(enumsf);
/*  457 */     this.cachedDeserFactories.add(enumdf);
/*      */ 
/*  459 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.ItemStatus");
/*  460 */     this.cachedSerQNames.add(qName);
/*  461 */     cls = TransactionManagerStatusItemStatus.class;
/*  462 */     this.cachedSerClasses.add(cls);
/*  463 */     this.cachedSerFactories.add(enumsf);
/*  464 */     this.cachedDeserFactories.add(enumdf);
/*      */ 
/*  466 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.JobDetailResponse");
/*  467 */     this.cachedSerQNames.add(qName);
/*  468 */     cls = TransactionManagerStatusJobDetailResponse.class;
/*  469 */     this.cachedSerClasses.add(cls);
/*  470 */     this.cachedSerFactories.add(beansf);
/*  471 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  473 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.JobStatusResponse");
/*  474 */     this.cachedSerQNames.add(qName);
/*  475 */     cls = TransactionManagerStatusJobStatusResponse.class;
/*  476 */     this.cachedSerClasses.add(cls);
/*  477 */     this.cachedSerFactories.add(beansf);
/*  478 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  480 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.StatusException");
/*  481 */     this.cachedSerQNames.add(qName);
/*  482 */     cls = TransactionManagerStatusStatusException.class;
/*  483 */     this.cachedSerClasses.add(cls);
/*  484 */     this.cachedSerFactories.add(beansf);
/*  485 */     this.cachedDeserFactories.add(beandf);
/*      */ 
/*  487 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.TransStatItems");
/*  488 */     this.cachedSerQNames.add(qName);
/*  489 */     cls = com.clickandbuy.TransactionManager.TransactionManagerStatusTransStatusResponse.class;
/*  490 */     this.cachedSerClasses.add(cls);
/*  491 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.TransStatusResponse");
/*  492 */     qName2 = new QName("", "item");
/*  493 */     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
/*  494 */     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
/*      */ 
/*  496 */     qName = new QName("https://clickandbuy.com/TransactionManager/", "TransactionManager.Status.TransStatusResponse");
/*  497 */     this.cachedSerQNames.add(qName);
/*  498 */     cls = TransactionManagerStatusTransStatusResponse.class;
/*  499 */     this.cachedSerClasses.add(cls);
/*  500 */     this.cachedSerFactories.add(beansf);
/*  501 */     this.cachedDeserFactories.add(beandf);
/*      */   }
/*      */ 
/*      */   protected Call createCall() throws RemoteException
/*      */   {
/*      */     try {
/*  507 */       Call _call = super._createCall();
/*  508 */       if (this.maintainSessionSet) {
/*  509 */         _call.setMaintainSession(this.maintainSession);
/*      */       }
/*  511 */       if (this.cachedUsername != null) {
/*  512 */         _call.setUsername(this.cachedUsername);
/*      */       }
/*  514 */       if (this.cachedPassword != null) {
/*  515 */         _call.setPassword(this.cachedPassword);
/*      */       }
/*  517 */       if (this.cachedEndpoint != null) {
/*  518 */         _call.setTargetEndpointAddress(this.cachedEndpoint);
/*      */       }
/*  520 */       if (this.cachedTimeout != null) {
/*  521 */         _call.setTimeout(this.cachedTimeout);
/*      */       }
/*  523 */       if (this.cachedPortName != null) {
/*  524 */         _call.setPortName(this.cachedPortName);
/*      */       }
/*  526 */       Enumeration keys = this.cachedProperties.keys();
/*  527 */       while (keys.hasMoreElements()) {
/*  528 */         String key = (String)keys.nextElement();
/*  529 */         _call.setProperty(key, this.cachedProperties.get(key));
/*      */       }
/*      */ 
/*  536 */       synchronized (this) {
/*  537 */         if (firstCall())
/*      */         {
/*  539 */           _call.setEncodingStyle(null);
/*  540 */           for (int i = 0; i < this.cachedSerFactories.size(); i++) {
/*  541 */             Class cls = (Class)this.cachedSerClasses.get(i);
/*  542 */             QName qName = (QName)this.cachedSerQNames.get(i);
/*      */ 
/*  544 */             Object x = this.cachedSerFactories.get(i);
/*  545 */             if ((x instanceof Class)) {
/*  546 */               Class sf = (Class)this.cachedSerFactories.get(i);
/*      */ 
/*  548 */               Class df = (Class)this.cachedDeserFactories.get(i);
/*      */ 
/*  550 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*      */             }
/*  552 */             else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory)) {
/*  553 */               org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
/*      */ 
/*  555 */               DeserializerFactory df = (DeserializerFactory)this.cachedDeserFactories.get(i);
/*      */ 
/*  557 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  562 */       return _call;
/*      */     } catch (Throwable _t) {
/*      */     
/*  565 */     throw new AxisFault("Failure trying to get the Call object", _t);}
/*      */   }
/*      */ 
/*      */   public boolean getInterfaceStatus(long sellerID, String tmPassword) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  570 */     if (this.cachedEndpoint == null) {
/*  571 */       throw new NoEndPointException();
/*      */     }
/*  573 */     Call _call = createCall();
/*  574 */     _call.setOperation(_operations[0]);
/*  575 */     _call.setUseSOAPAction(true);
/*  576 */     _call.setSOAPActionURI("TransactionManager.Status#getInterfaceStatus");
/*  577 */     _call.setEncodingStyle(null);
/*  578 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  579 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  580 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  581 */     _call.setOperationName(new QName("TransactionManager.Status", "getInterfaceStatus"));
/*      */ 
/*  583 */     setRequestHeaders(_call);
/*  584 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword });
/*      */ 
/*  587 */       if ((_resp instanceof RemoteException)) {
/*  588 */         throw ((RemoteException)_resp);
/*      */       }
/*  590 */       extractAttachments(_call);
/*      */       try {
/*  592 */         return ((Boolean)_resp).booleanValue();
/*      */       } catch (Exception _exception) {
/*  594 */         return ((Boolean)JavaUtils.convert(_resp, Boolean.TYPE)).booleanValue();
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  597 */       if (axisFaultException.detail != null) {
/*  598 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  599 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  601 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  602 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  605 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public ClickAndBuyTransactionBDRStatus getBDRStatusByBDRID(long sellerID, String tmPassword, long slaveMerchantID, long BDRID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  610 */     if (this.cachedEndpoint == null) {
/*  611 */       throw new NoEndPointException();
/*      */     }
/*  613 */     Call _call = createCall();
/*  614 */     _call.setOperation(_operations[1]);
/*  615 */     _call.setUseSOAPAction(true);
/*  616 */     _call.setSOAPActionURI("TransactionManager.Status#getBDRStatusByBDRID");
/*  617 */     _call.setEncodingStyle(null);
/*  618 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  619 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  620 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  621 */     _call.setOperationName(new QName("TransactionManager.Status", "getBDRStatusByBDRID"));
/*      */ 
/*  623 */     setRequestHeaders(_call);
/*  624 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(BDRID) });
/*      */ 
/*  627 */       if ((_resp instanceof RemoteException)) {
/*  628 */         throw ((RemoteException)_resp);
/*      */       }
/*  630 */       extractAttachments(_call);
/*      */       try {
/*  632 */         return (ClickAndBuyTransactionBDRStatus)_resp;
/*      */       } catch (Exception _exception) {
/*  634 */         return (ClickAndBuyTransactionBDRStatus)JavaUtils.convert(_resp, ClickAndBuyTransactionBDRStatus.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  637 */       if (axisFaultException.detail != null) {
/*  638 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  639 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  641 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  642 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  645 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public ClickAndBuyTransactionBDRStatus getBDRStatusByExternalBDRID(long sellerID, String tmPassword, long slaveMerchantID, String externalBDRID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  650 */     if (this.cachedEndpoint == null) {
/*  651 */       throw new NoEndPointException();
/*      */     }
/*  653 */     Call _call = createCall();
/*  654 */     _call.setOperation(_operations[2]);
/*  655 */     _call.setUseSOAPAction(true);
/*  656 */     _call.setSOAPActionURI("TransactionManager.Status#getBDRStatusByExternalBDRID");
/*  657 */     _call.setEncodingStyle(null);
/*  658 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  659 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  660 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  661 */     _call.setOperationName(new QName("TransactionManager.Status", "getBDRStatusByExternalBDRID"));
/*      */ 
/*  663 */     setRequestHeaders(_call);
/*  664 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), externalBDRID });
/*      */ 
/*  667 */       if ((_resp instanceof RemoteException)) {
/*  668 */         throw ((RemoteException)_resp);
/*      */       }
/*  670 */       extractAttachments(_call);
/*      */       try {
/*  672 */         return (ClickAndBuyTransactionBDRStatus)_resp;
/*      */       } catch (Exception _exception) {
/*  674 */         return (ClickAndBuyTransactionBDRStatus)JavaUtils.convert(_resp, ClickAndBuyTransactionBDRStatus.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  677 */       if (axisFaultException.detail != null) {
/*  678 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  679 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  681 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  682 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  685 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public ClickAndBuyTransactionBDRStatus[] getCustomerTransactionStatus(long sellerID, String tmPassword, long slaveMerchantID, long crn, String startDateTime, String endDateTime) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  690 */     if (this.cachedEndpoint == null) {
/*  691 */       throw new NoEndPointException();
/*      */     }
/*  693 */     Call _call = createCall();
/*  694 */     _call.setOperation(_operations[3]);
/*  695 */     _call.setUseSOAPAction(true);
/*  696 */     _call.setSOAPActionURI("TransactionManager.Status#getCustomerTransactionStatus");
/*  697 */     _call.setEncodingStyle(null);
/*  698 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  699 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  700 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  701 */     _call.setOperationName(new QName("TransactionManager.Status", "getCustomerTransactionStatus"));
/*      */ 
/*  703 */     setRequestHeaders(_call);
/*  704 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(crn), startDateTime, endDateTime });
/*      */ 
/*  707 */       if ((_resp instanceof RemoteException)) {
/*  708 */         throw ((RemoteException)_resp);
/*      */       }
/*  710 */       extractAttachments(_call);
/*      */       try {
/*  712 */         return (ClickAndBuyTransactionBDRStatus[])(ClickAndBuyTransactionBDRStatus[])_resp;
/*      */       } catch (Exception _exception) {
/*  714 */         return (ClickAndBuyTransactionBDRStatus[])(ClickAndBuyTransactionBDRStatus[])JavaUtils.convert(_resp, com.clickandbuy.TransactionManager.ClickAndBuyTransactionBDRStatus.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  717 */       if (axisFaultException.detail != null) {
/*  718 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  719 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  721 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  722 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  725 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public ClickAndBuyTransactionCustomerStatus getCustomerStatusByCRN(long sellerID, String tmPassword, long slaveMerchantID, long crn, long easyCollectID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  730 */     if (this.cachedEndpoint == null) {
/*  731 */       throw new NoEndPointException();
/*      */     }
/*  733 */     Call _call = createCall();
/*  734 */     _call.setOperation(_operations[4]);
/*  735 */     _call.setUseSOAPAction(true);
/*  736 */     _call.setSOAPActionURI("TransactionManager.Status#getCustomerStatusByCRN");
/*  737 */     _call.setEncodingStyle(null);
/*  738 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  739 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  740 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  741 */     _call.setOperationName(new QName("TransactionManager.Status", "getCustomerStatusByCRN"));
/*      */ 
/*  743 */     setRequestHeaders(_call);
/*  744 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(crn), new Long(easyCollectID) });
/*      */ 
/*  747 */       if ((_resp instanceof RemoteException)) {
/*  748 */         throw ((RemoteException)_resp);
/*      */       }
/*  750 */       extractAttachments(_call);
/*      */       try {
/*  752 */         return (ClickAndBuyTransactionCustomerStatus)_resp;
/*      */       } catch (Exception _exception) {
/*  754 */         return (ClickAndBuyTransactionCustomerStatus)JavaUtils.convert(_resp, ClickAndBuyTransactionCustomerStatus.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  757 */       if (axisFaultException.detail != null) {
/*  758 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  759 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  761 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  762 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  765 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public ClickAndBuyTransactionCustomerStatus getCustomerStatusByMSISDN(long sellerID, String tmPassword, long slaveMerchantID, String MSISDN, long easyCollectID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  770 */     if (this.cachedEndpoint == null) {
/*  771 */       throw new NoEndPointException();
/*      */     }
/*  773 */     Call _call = createCall();
/*  774 */     _call.setOperation(_operations[5]);
/*  775 */     _call.setUseSOAPAction(true);
/*  776 */     _call.setSOAPActionURI("TransactionManager.Status#getCustomerStatusByMSISDN");
/*  777 */     _call.setEncodingStyle(null);
/*  778 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  779 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  780 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  781 */     _call.setOperationName(new QName("TransactionManager.Status", "getCustomerStatusByMSISDN"));
/*      */ 
/*  783 */     setRequestHeaders(_call);
/*  784 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), MSISDN, new Long(easyCollectID) });
/*      */ 
/*  787 */       if ((_resp instanceof RemoteException)) {
/*  788 */         throw ((RemoteException)_resp);
/*      */       }
/*  790 */       extractAttachments(_call);
/*      */       try {
/*  792 */         return (ClickAndBuyTransactionCustomerStatus)_resp;
/*      */       } catch (Exception _exception) {
/*  794 */         return (ClickAndBuyTransactionCustomerStatus)JavaUtils.convert(_resp, ClickAndBuyTransactionCustomerStatus.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  797 */       if (axisFaultException.detail != null) {
/*  798 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  799 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  801 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  802 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  805 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public TransactionManagerStatusTransStatusResponse getTransactionStatusByJobID(long sellerID, String tmPassword, long jobID, long actionID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  810 */     if (this.cachedEndpoint == null) {
/*  811 */       throw new NoEndPointException();
/*      */     }
/*  813 */     Call _call = createCall();
/*  814 */     _call.setOperation(_operations[6]);
/*  815 */     _call.setUseSOAPAction(true);
/*  816 */     _call.setSOAPActionURI("TransactionManager.Status#getTransactionStatusByJobID");
/*  817 */     _call.setEncodingStyle(null);
/*  818 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  819 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  820 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  821 */     _call.setOperationName(new QName("TransactionManager.Status", "getTransactionStatusByJobID"));
/*      */ 
/*  823 */     setRequestHeaders(_call);
/*  824 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(jobID), new Long(actionID) });
/*      */ 
/*  827 */       if ((_resp instanceof RemoteException)) {
/*  828 */         throw ((RemoteException)_resp);
/*      */       }
/*  830 */       extractAttachments(_call);
/*      */       try {
/*  832 */         return (TransactionManagerStatusTransStatusResponse)_resp;
/*      */       } catch (Exception _exception) {
/*  834 */         return (TransactionManagerStatusTransStatusResponse)JavaUtils.convert(_resp, TransactionManagerStatusTransStatusResponse.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  837 */       if (axisFaultException.detail != null) {
/*  838 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  839 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  841 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  842 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  845 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public TransactionManagerStatusTransStatusResponse getTransactionStatusByExternalBDRID(long sellerID, String tmPassword, long slaveMerchantID, String externalBDRID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  850 */     if (this.cachedEndpoint == null) {
/*  851 */       throw new NoEndPointException();
/*      */     }
/*  853 */     Call _call = createCall();
/*  854 */     _call.setOperation(_operations[7]);
/*  855 */     _call.setUseSOAPAction(true);
/*  856 */     _call.setSOAPActionURI("TransactionManager.Status#getTransactionStatusByExternalBDRID");
/*  857 */     _call.setEncodingStyle(null);
/*  858 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  859 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  860 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  861 */     _call.setOperationName(new QName("TransactionManager.Status", "getTransactionStatusByExternalBDRID"));
/*      */ 
/*  863 */     setRequestHeaders(_call);
/*  864 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), externalBDRID });
/*      */ 
/*  867 */       if ((_resp instanceof RemoteException)) {
/*  868 */         throw ((RemoteException)_resp);
/*      */       }
/*  870 */       extractAttachments(_call);
/*      */       try {
/*  872 */         return (TransactionManagerStatusTransStatusResponse)_resp;
/*      */       } catch (Exception _exception) {
/*  874 */         return (TransactionManagerStatusTransStatusResponse)JavaUtils.convert(_resp, TransactionManagerStatusTransStatusResponse.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  877 */       if (axisFaultException.detail != null) {
/*  878 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  879 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  881 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  882 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  885 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public TransactionManagerStatusJobStatusResponse getJobStatusByJobID(long sellerID, String tmPassword, long jobID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  890 */     if (this.cachedEndpoint == null) {
/*  891 */       throw new NoEndPointException();
/*      */     }
/*  893 */     Call _call = createCall();
/*  894 */     _call.setOperation(_operations[8]);
/*  895 */     _call.setUseSOAPAction(true);
/*  896 */     _call.setSOAPActionURI("TransactionManager.Status#getJobStatusByJobID");
/*  897 */     _call.setEncodingStyle(null);
/*  898 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  899 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  900 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  901 */     _call.setOperationName(new QName("TransactionManager.Status", "getJobStatusByJobID"));
/*      */ 
/*  903 */     setRequestHeaders(_call);
/*  904 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(jobID) });
/*      */ 
/*  907 */       if ((_resp instanceof RemoteException)) {
/*  908 */         throw ((RemoteException)_resp);
/*      */       }
/*  910 */       extractAttachments(_call);
/*      */       try {
/*  912 */         return (TransactionManagerStatusJobStatusResponse)_resp;
/*      */       } catch (Exception _exception) {
/*  914 */         return (TransactionManagerStatusJobStatusResponse)JavaUtils.convert(_resp, TransactionManagerStatusJobStatusResponse.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  917 */       if (axisFaultException.detail != null) {
/*  918 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  919 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  921 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  922 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  925 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public TransactionManagerStatusJobStatusResponse getJobStatusByExtJobID(long sellerID, String tmPassword, long extJobID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  930 */     if (this.cachedEndpoint == null) {
/*  931 */       throw new NoEndPointException();
/*      */     }
/*  933 */     Call _call = createCall();
/*  934 */     _call.setOperation(_operations[9]);
/*  935 */     _call.setUseSOAPAction(true);
/*  936 */     _call.setSOAPActionURI("TransactionManager.Status#getJobStatusByExtJobID");
/*  937 */     _call.setEncodingStyle(null);
/*  938 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  939 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  940 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  941 */     _call.setOperationName(new QName("TransactionManager.Status", "getJobStatusByExtJobID"));
/*      */ 
/*  943 */     setRequestHeaders(_call);
/*  944 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(extJobID) });
/*      */ 
/*  947 */       if ((_resp instanceof RemoteException)) {
/*  948 */         throw ((RemoteException)_resp);
/*      */       }
/*  950 */       extractAttachments(_call);
/*      */       try {
/*  952 */         return (TransactionManagerStatusJobStatusResponse)_resp;
/*      */       } catch (Exception _exception) {
/*  954 */         return (TransactionManagerStatusJobStatusResponse)JavaUtils.convert(_resp, TransactionManagerStatusJobStatusResponse.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  957 */       if (axisFaultException.detail != null) {
/*  958 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  959 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/*  961 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/*  962 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/*  965 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public TransactionManagerStatusJobDetailResponse getJobItemsStatusByJobID(long sellerID, String tmPassword, long jobID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/*  970 */     if (this.cachedEndpoint == null) {
/*  971 */       throw new NoEndPointException();
/*      */     }
/*  973 */     Call _call = createCall();
/*  974 */     _call.setOperation(_operations[10]);
/*  975 */     _call.setUseSOAPAction(true);
/*  976 */     _call.setSOAPActionURI("TransactionManager.Status#getJobItemsStatusByJobID");
/*  977 */     _call.setEncodingStyle(null);
/*  978 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/*  979 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/*  980 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/*  981 */     _call.setOperationName(new QName("TransactionManager.Status", "getJobItemsStatusByJobID"));
/*      */ 
/*  983 */     setRequestHeaders(_call);
/*  984 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(jobID) });
/*      */ 
/*  987 */       if ((_resp instanceof RemoteException)) {
/*  988 */         throw ((RemoteException)_resp);
/*      */       }
/*  990 */       extractAttachments(_call);
/*      */       try {
/*  992 */         return (TransactionManagerStatusJobDetailResponse)_resp;
/*      */       } catch (Exception _exception) {
/*  994 */         return (TransactionManagerStatusJobDetailResponse)JavaUtils.convert(_resp, TransactionManagerStatusJobDetailResponse.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/*  997 */       if (axisFaultException.detail != null) {
/*  998 */         if ((axisFaultException.detail instanceof RemoteException)) {
/*  999 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/* 1001 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/* 1002 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/* 1005 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public TransactionManagerStatusJobDetailResponse getJobItemsStatusByExtJobID(long sellerID, String tmPassword, long extJobID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/* 1010 */     if (this.cachedEndpoint == null) {
/* 1011 */       throw new NoEndPointException();
/*      */     }
/* 1013 */     Call _call = createCall();
/* 1014 */     _call.setOperation(_operations[11]);
/* 1015 */     _call.setUseSOAPAction(true);
/* 1016 */     _call.setSOAPActionURI("TransactionManager.Status#getJobItemsStatusByExtJobID");
/* 1017 */     _call.setEncodingStyle(null);
/* 1018 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 1019 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 1020 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 1021 */     _call.setOperationName(new QName("TransactionManager.Status", "getJobItemsStatusByExtJobID"));
/*      */ 
/* 1023 */     setRequestHeaders(_call);
/* 1024 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(extJobID) });
/*      */ 
/* 1027 */       if ((_resp instanceof RemoteException)) {
/* 1028 */         throw ((RemoteException)_resp);
/*      */       }
/* 1030 */       extractAttachments(_call);
/*      */       try {
/* 1032 */         return (TransactionManagerStatusJobDetailResponse)_resp;
/*      */       } catch (Exception _exception) {
/* 1034 */         return (TransactionManagerStatusJobDetailResponse)JavaUtils.convert(_resp, TransactionManagerStatusJobDetailResponse.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/* 1037 */       if (axisFaultException.detail != null) {
/* 1038 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 1039 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/* 1041 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/* 1042 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/* 1045 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public ClickAndBuyTransactionBDRProcessingState isBDRIDCommitted(long sellerID, String tmPassword, long slaveMerchantID, long BDRID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/* 1050 */     if (this.cachedEndpoint == null) {
/* 1051 */       throw new NoEndPointException();
/*      */     }
/* 1053 */     Call _call = createCall();
/* 1054 */     _call.setOperation(_operations[12]);
/* 1055 */     _call.setUseSOAPAction(true);
/* 1056 */     _call.setSOAPActionURI("TransactionManager.Status#isBDRIDCommitted");
/* 1057 */     _call.setEncodingStyle(null);
/* 1058 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 1059 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 1060 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 1061 */     _call.setOperationName(new QName("TransactionManager.Status", "isBDRIDCommitted"));
/*      */ 
/* 1063 */     setRequestHeaders(_call);
/* 1064 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), new Long(BDRID) });
/*      */ 
/* 1067 */       if ((_resp instanceof RemoteException)) {
/* 1068 */         throw ((RemoteException)_resp);
/*      */       }
/* 1070 */       extractAttachments(_call);
/*      */       try {
/* 1072 */         return (ClickAndBuyTransactionBDRProcessingState)_resp;
/*      */       } catch (Exception _exception) {
/* 1074 */         return (ClickAndBuyTransactionBDRProcessingState)JavaUtils.convert(_resp, ClickAndBuyTransactionBDRProcessingState.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/* 1077 */       if (axisFaultException.detail != null) {
/* 1078 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 1079 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/* 1081 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/* 1082 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/* 1085 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   public ClickAndBuyTransactionBDRProcessingState isExternalBDRIDCommitted(long sellerID, String tmPassword, long slaveMerchantID, String externalBDRID) throws RemoteException, TransactionManagerStatusStatusException
/*      */   {
/* 1090 */     if (this.cachedEndpoint == null) {
/* 1091 */       throw new NoEndPointException();
/*      */     }
/* 1093 */     Call _call = createCall();
/* 1094 */     _call.setOperation(_operations[13]);
/* 1095 */     _call.setUseSOAPAction(true);
/* 1096 */     _call.setSOAPActionURI("TransactionManager.Status#isExternalBDRIDCommitted");
/* 1097 */     _call.setEncodingStyle(null);
/* 1098 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 1099 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 1100 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 1101 */     _call.setOperationName(new QName("TransactionManager.Status", "isExternalBDRIDCommitted"));
/*      */ 
/* 1103 */     setRequestHeaders(_call);
/* 1104 */     setAttachments(_call);
/*      */     try { Object _resp = _call.invoke(new Object[] { new Long(sellerID), tmPassword, new Long(slaveMerchantID), externalBDRID });
/*      */ 
/* 1107 */       if ((_resp instanceof RemoteException)) {
/* 1108 */         throw ((RemoteException)_resp);
/*      */       }
/* 1110 */       extractAttachments(_call);
/*      */       try {
/* 1112 */         return (ClickAndBuyTransactionBDRProcessingState)_resp;
/*      */       } catch (Exception _exception) {
/* 1114 */         return (ClickAndBuyTransactionBDRProcessingState)JavaUtils.convert(_resp, ClickAndBuyTransactionBDRProcessingState.class);
/*      */       }
/*      */     } catch (AxisFault axisFaultException) {
/* 1117 */       if (axisFaultException.detail != null) {
/* 1118 */         if ((axisFaultException.detail instanceof RemoteException)) {
/* 1119 */           throw ((RemoteException)axisFaultException.detail);
/*      */         }
/* 1121 */         if ((axisFaultException.detail instanceof TransactionManagerStatusStatusException))
/* 1122 */           throw ((TransactionManagerStatusStatusException)axisFaultException.detail);
/*      */       }
/*      */     }
/* 1125 */     throw axisFaultException;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*   20 */     _initOperationDesc1();
/*   21 */     _initOperationDesc2();
/*      */   }
/*      */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.clickandbuy.TransactionManager.TransactionManagerStatusBindingStub
 * JD-Core Version:    0.6.0
 */