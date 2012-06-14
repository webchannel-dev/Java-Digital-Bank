/*     */ package com.bright.framework.image.xmp;
/*     */ 
import com.bright.assetbank.application.util.ABImageMagickOptionList;
/*     */ import com.bright.framework.image.exception.ImageException;
import com.bright.framework.image.util.ImageMagick;
import com.bright.framework.util.FileUtil;
import java.io.File;
/*     */ import java.io.StringReader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathConstants;
/*     */ import javax.xml.xpath.XPathExpression;
/*     */ import javax.xml.xpath.XPathExpressionException;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class XMPExtractor
/*     */ {
/*  57 */   private static XMPNamespaceContext c_kXMPNamespaceContext = new XMPNamespaceContext();
/*  58 */   private static final String[] k_saXMLDocNames = { "x:xapmeta", "x:xmpmeta" };
/*     */ 
/*  60 */   private XPathExpression[] m_xPaths = null;
/*  61 */   private String[] m_asXPaths = null;
/*     */ 
/*     */   public void setXPaths(String[] a_asXPaths)
/*     */     throws ImageException
/*     */   {
/*  70 */     if ((a_asXPaths == null) || (a_asXPaths.length == 0))
/*     */     {
/*  72 */       this.m_asXPaths = null;
/*  73 */       this.m_xPaths = null;
/*  74 */       return;
/*     */     }
/*     */ 
/*  77 */     this.m_asXPaths = a_asXPaths;
/*  78 */     this.m_xPaths = new XPathExpression[a_asXPaths.length];
/*     */ 
/*  81 */     for (int i = 0; i < a_asXPaths.length; i++)
/*     */     {
/*  84 */       this.m_xPaths[i] = buildXPath(new String[] { a_asXPaths[i] });
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  90 */     return this.m_asXPaths == null;
/*     */   }
/*     */ 
/*     */   public Map extract(String a_sFilePath)
/*     */     throws ImageException
/*     */   {
/* 112 */     HashMap map = new HashMap();
/*     */ 
/* 115 */     if (isEmpty())
/*     */     {
/* 117 */       throw new ImageException("XMPExtractor.extract: the xpath expression is null. Did you forget to call setXPaths?");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 123 */       StringBuffer sbXml = extractXMPProfile(a_sFilePath);
/*     */ 
/* 125 */       if (sbXml == null)
/*     */       {
/* 128 */         return map;
/*     */       }
/*     */ 
/* 132 */       String sXML = sbXml.toString();
/*     */ 
/* 135 */       int iDocStart = -1;
/* 136 */       int iDocEnd = -1;
/*     */ 
/* 139 */       int i = 0;
/* 140 */       while ((iDocStart < 0) && (i < k_saXMLDocNames.length))
/*     */       {
/* 144 */         iDocStart = sXML.indexOf("<" + k_saXMLDocNames[i]);
/*     */ 
/* 146 */         if (iDocStart >= 0)
/*     */         {
/* 148 */           String sEndTag = "</" + k_saXMLDocNames[i] + ">";
/* 149 */           iDocEnd = sXML.indexOf(sEndTag, iDocStart) + sEndTag.length();
/*     */         }
/* 141 */         i++;
/*     */       }
/*     */ 
/* 154 */       if ((iDocEnd < 0) || (iDocEnd < 0))
/*     */       {
/* 156 */         return map;
/*     */       }
/*     */ 
/* 160 */       sXML = sXML.substring(iDocStart, iDocEnd);
/*     */ 
/* 163 */       InputSource is = new InputSource(new StringReader(sXML));
/*     */ 
/* 165 */       DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
/* 166 */       domFactory.setNamespaceAware(true);
/* 167 */       DocumentBuilder builder = domFactory.newDocumentBuilder();
/* 168 */       Document doc = builder.parse(is);
/*     */ 
/* 174 */       for (int iXPathIndex = 0; iXPathIndex < this.m_xPaths.length; iXPathIndex++)
/*     */       {
/* 177 */         NodeList nodes = (NodeList)this.m_xPaths[iXPathIndex].evaluate(doc, XPathConstants.NODESET);
/* 178 */         Node node = null;
/*     */ 
/* 181 */         for (i = 0; i < nodes.getLength(); i++)
/*     */         {
/* 184 */           node = nodes.item(i);
/*     */ 
/* 187 */           String sKey = this.m_asXPaths[iXPathIndex];
/*     */ 
/* 190 */           String[] asValue = (String[])(String[])map.get(sKey);
/*     */ 
/* 192 */           if (asValue == null)
/*     */           {
/* 195 */             asValue = new String[1];
/*     */           }
/*     */           else
/*     */           {
/* 200 */             String[] asTemp = asValue;
/* 201 */             asValue = new String[asValue.length + 1];
/* 202 */             System.arraycopy(asTemp, 0, asValue, 0, asTemp.length);
/*     */           }
/*     */ 
/* 206 */           asValue[(asValue.length - 1)] = node.getTextContent();
/*     */ 
/* 209 */           map.put(sKey, asValue);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 216 */       throw new ImageException("XMPExtractor.getXMP: " + e.getMessage());
/*     */     }
/*     */ 
/* 219 */     return map; } 
/*     */   // ERROR //



public static StringBuffer extractXMPProfile(String a_sFilePath)
   throws ImageException
 {
   StringBuffer sbXMP = new StringBuffer();
   File fTemp = null;
   try
   {
     ABImageMagickOptionList options = new ABImageMagickOptionList();

     options.addInputFilename(a_sFilePath);

     fTemp = File.createTempFile("image", "xmp");

     options.addOutputFilename("xmp:" + fTemp.getAbsolutePath());
     try
     {
       ImageMagick.convert(options);

       if (fTemp.exists())
       {
         sbXMP = FileUtil.readIntoStringBuffer(fTemp.getAbsolutePath(), "UTF8");
       }

     }
     catch (ImageException ie)
     {
     }

   }
   catch (Exception e)
   {
   }
   finally
   {
     if ((fTemp != null) && (fTemp.exists()))
     {
       fTemp.delete();
       FileUtil.logFileDeletion(fTemp);
     }
   }

   return sbXMP;
 }


 
/*        public static StringBuffer extractXMPProfile(String a_sFilePath) throws ImageException { // Byte code:
/*     */     //   0: new 48	java/lang/StringBuffer
/*     */     //   3: dup
/*     */     //   4: invokespecial 49	java/lang/StringBuffer:<init>	()V
/*     */     //   7: astore_1
/*     */     //   8: aconst_null
/*     */     //   9: astore_2
/*     */     //   10: new 50	com/bright/assetbank/application/util/ABImageMagickOptionList
/*     */     //   13: dup
/*     */     //   14: invokespecial 51	com/bright/assetbank/application/util/ABImageMagickOptionList:<init>	()V
/*     */     //   17: astore_3
/*     */     //   18: aload_3
/*     */     //   19: aload_0
/*     */     //   20: invokevirtual 52	com/bright/assetbank/application/util/ABImageMagickOptionList:addInputFilename	(Ljava/lang/String;)V
/*     */     //   23: ldc 53
/*     */     //   25: ldc 54
/*     */     //   27: invokestatic 55	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
/*     */     //   30: astore_2
/*     */     //   31: aload_3
/*     */     //   32: new 16	java/lang/StringBuilder
/*     */     //   35: dup
/*     */     //   36: invokespecial 17	java/lang/StringBuilder:<init>	()V
/*     */     //   39: ldc 56
/*     */     //   41: invokevirtual 19	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   44: aload_2
/*     */     //   45: invokevirtual 57	java/io/File:getAbsolutePath	()Ljava/lang/String;
/*     */     //   48: invokevirtual 19	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   51: invokevirtual 20	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   54: invokevirtual 58	com/bright/assetbank/application/util/ABImageMagickOptionList:addOutputFilename	(Ljava/lang/String;)V
/*     */     //   57: aload_3
/*     */     //   58: invokestatic 59	com/bright/framework/image/util/ImageMagick:convert	(Lcom/bright/framework/image/util/ImageMagickOptionList;)V
/*     */     //   61: aload_2
/*     */     //   62: invokevirtual 60	java/io/File:exists	()Z
/*     */     //   65: ifeq +13 -> 78
/*     */     //   68: aload_2
/*     */     //   69: invokevirtual 57	java/io/File:getAbsolutePath	()Ljava/lang/String;
/*     */     //   72: ldc 61
/*     */     //   74: invokestatic 62	com/bright/framework/util/FileUtil:readIntoStringBuffer	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/StringBuffer;
/*     */     //   77: astore_1
/*     */     //   78: goto +5 -> 83
/*     */     //   81: astore 4
/*     */     //   83: jsr +45 -> 128
/*     */     //   86: goto +66 -> 152
/*     */     //   89: astore_3
/*     */     //   90: new 10	com/bright/framework/image/exception/ImageException
/*     */     //   93: dup
/*     */     //   94: new 16	java/lang/StringBuilder
/*     */     //   97: dup
/*     */     //   98: invokespecial 17	java/lang/StringBuilder:<init>	()V
/*     */     //   101: ldc 63
/*     */     //   103: invokevirtual 19	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   106: aload_3
/*     */     //   107: invokevirtual 47	java/lang/Exception:getMessage	()Ljava/lang/String;
/*     */     //   110: invokevirtual 19	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   113: invokevirtual 20	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   116: invokespecial 12	com/bright/framework/image/exception/ImageException:<init>	(Ljava/lang/String;)V
/*     */     //   119: athrow
/*     */     //   120: astore 5
/*     */     //   122: jsr +6 -> 128
/*     */     //   125: aload 5
/*     */     //   127: athrow
/*     */     //   128: astore 6
/*     */     //   130: aload_2
/*     */     //   131: ifnull +19 -> 150
/*     */     //   134: aload_2
/*     */     //   135: invokevirtual 60	java/io/File:exists	()Z
/*     */     //   138: ifeq +12 -> 150
/*     */     //   141: aload_2
/*     */     //   142: invokevirtual 64	java/io/File:delete	()Z
/*     */     //   145: pop
/*     */     //   146: aload_2
/*     */     //   147: invokestatic 65	com/bright/framework/util/FileUtil:logFileDeletion	(Ljava/io/File;)V
/*     */     //   150: ret 6
/*     */     //   152: aload_1
/*     */     //   153: areturn
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   57	78	81	com/bright/framework/image/exception/ImageException
/*     */     //   10	83	89	java/lang/Exception
/*     */     //   10	86	120	finally
/*     */     //   89	125	120	finally } 

/* 303 */   private static XPathExpression buildXPath(String[] a_saXPaths) throws ImageException { XPathExpression exp = null;
/*     */ 
/* 306 */     String sExp = null;
/* 307 */     for (int i = 0; i < a_saXPaths.length; i++)
/*     */     {
/* 310 */       if (sExp == null)
/*     */       {
/* 312 */         sExp = "";
/*     */       }
/*     */       else
/*     */       {
/* 316 */         sExp = sExp + "|";
/*     */       }
/*     */ 
/* 320 */       sExp = sExp + a_saXPaths[i];
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 326 */       XPathFactory factory = XPathFactory.newInstance();
/* 327 */       XPath xpath = factory.newXPath();
/*     */ 
/* 329 */       xpath.setNamespaceContext(c_kXMPNamespaceContext);
/*     */ 
/* 331 */       exp = xpath.compile(sExp);
/*     */     }
/*     */     catch (XPathExpressionException e)
/*     */     {
/* 335 */       String sMessage = e.getMessage();
/*     */ 
/* 337 */       if ((sMessage == null) || ((sMessage.length() == 0) && (e.getCause() != null)))
/*     */       {
/* 339 */         sMessage = e.getCause().getMessage();
/*     */       }
/*     */ 
/* 342 */       throw new ImageException("XMPExtractor.buildXPath:" + sMessage);
/*     */     }
/*     */ 
/* 345 */     return exp;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.xmp.XMPExtractor
 * JD-Core Version:    0.6.0
 */