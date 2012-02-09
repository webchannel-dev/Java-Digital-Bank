/*    */ package com.bright.assetbank.repurposing.util;
/*    */ 
/*    */ import com.bright.assetbank.application.bean.ImageConversionInfo;
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class RepurposingUtil
/*    */   implements AttributeConstants
/*    */ {
/*    */   public static ImageConversionInfo getSlideshowConversion(int a_iWidth, int a_iHeight, float a_fJpgQuality, boolean a_bAspectRatio)
/*    */   {
/* 43 */     ImageConversionInfo conversionInfo = new ImageConversionInfo();
/* 44 */     conversionInfo.setJpegQuality(a_fJpgQuality);
/* 45 */     conversionInfo.setScaleUp(true);
/* 46 */     conversionInfo.setMaxHeight(a_iHeight);
/* 47 */     conversionInfo.setMaxWidth(a_iWidth);
/* 48 */     conversionInfo.setMaintainAspectRatio(a_bAspectRatio);
/*    */ 
/* 50 */     return conversionInfo;
/*    */   }
/*    */ 
/*    */   public static Vector<Attribute> filterAttributes(Vector<Attribute> a_vecAtts, ABUserProfile a_userProfile, Vector<Long> a_vecIds)
/*    */   {
/* 64 */     Vector vecVisibleAttributeIds = a_userProfile.getVisibleAttributeIds();
/* 65 */     Vector vecNewAttributes = new Vector();
/*    */ 
/* 67 */     for (Attribute a : a_vecAtts)
/*    */     {
/* 69 */       String sFieldName = "";
/* 70 */       if (a.getFieldName() != null)
/*    */       {
/* 72 */         sFieldName = a.getFieldName();
/*    */       }
/*    */ 
/* 75 */       if ((!a.getIsDataLookupButton()) && (!sFieldName.equals("audit")) && (!sFieldName.equals("version")) && (!sFieldName.equals("rating")) && (!sFieldName.equals("agreements")) && (!sFieldName.equals("usage")) && (!sFieldName.equals("embeddedData")))
/*    */       {
/* 85 */         if (((vecVisibleAttributeIds != null) && (vecVisibleAttributeIds.contains(new Long(a.getId())))) || (a_userProfile.getIsAdmin()))
/*    */         {
/* 88 */           if ((a_vecIds != null) && (a_vecIds.contains(new Long(a.getId()))))
/*    */           {
/* 90 */             a.setSelected(true);
/*    */           }
/* 92 */           vecNewAttributes.add(a);
/*    */         }
/*    */       }
/*    */     }
/* 96 */     return vecNewAttributes;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.util.RepurposingUtil
 * JD-Core Version:    0.6.0
 */