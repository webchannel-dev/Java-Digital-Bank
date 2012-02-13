/*    */ package com.bright.assetbank.synchronise.util.impl;
/*    */ 
/*    */ import com.bright.assetbank.synchronise.constant.impl.AdlibSynchronisationSettings;
/*    */ import com.bright.assetbank.synchronise.util.SynchUtil;
/*    */ import java.util.List;
/*    */ import java.util.ListIterator;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ public class AdlibSynchUtil extends SynchUtil
/*    */ {
/*    */   public static String getAdlibReproductionValue(Element a_recordElement, String a_sElementName, AdlibSynchronisationSettings a_settings)
/*    */   {
/* 49 */     String[] aFormat = a_settings.getAdlibReproductionFormats();
/* 50 */     String[] aResolutions = a_settings.getAdlibReproductionResolutions();
/* 51 */     String sValue = null;
/*    */ 
/* 53 */     for (int i = 0; i < aFormat.length; i++)
/*    */     {
/* 56 */       List listRecordElements = a_recordElement.getChildren(a_settings.getXMLElementNameReproduction());
/*    */ 
/* 58 */       if (listRecordElements == null)
/*    */         continue;
/* 60 */       ListIterator reproIterator = listRecordElements.listIterator();
/*    */ 
/* 62 */       if (reproIterator == null)
/*    */         continue;
/* 64 */       while (reproIterator.hasNext())
/*    */       {
/* 66 */         Element reproElement = (Element)reproIterator.next();
/*    */ 
/* 68 */         if (reproElement != null)
/*    */         {
/* 70 */           String sFormatType = reproElement.getChildText(a_settings.getXMLElementNameReproductionFormat());
/* 71 */           if ((sFormatType != null) && (sFormatType.equals(aFormat[i])))
/*    */           {
/* 73 */             String sReproType = reproElement.getChildText(a_settings.getXMLElementNameReproductionType());
/* 74 */             if ((sReproType != null) && (sReproType.equals(aResolutions[i])))
/*    */             {
/* 76 */               sValue = reproElement.getChildText(a_sElementName);
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/* 82 */       if (sValue != null)
/*    */       {
/* 84 */         return sValue;
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 90 */     return sValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.util.impl.AdlibSynchUtil
 * JD-Core Version:    0.6.0
 */