/*    */ package com.bright.assetbank.priceband.bean;
/*    */ 
/*    */ import com.bright.assetbank.assetbox.bean.AssetPrice;
/*    */ import com.bright.framework.common.bean.TranslatableStringDataBean;
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class PriceBandComparator
/*    */   implements Comparator, Serializable
/*    */ {
/*    */   public int compare(Object a_o1, Object a_o2)
/*    */   {
/* 35 */     int iResult = 0;
/*    */ 
/* 37 */     if (!(a_o1 instanceof AssetPrice))
/* 38 */       throw new ClassCastException();
/* 39 */     if (!(a_o2 instanceof AssetPrice)) {
/* 40 */       throw new ClassCastException();
/*    */     }
/* 42 */     PriceBand pb1 = ((AssetPrice)a_o1).getPriceBand();
/* 43 */     PriceBand pb2 = ((AssetPrice)a_o2).getPriceBand();
/*    */ 
/* 45 */     iResult = compare1(pb1, pb2);
/*    */ 
/* 47 */     if (iResult == 0)
/*    */     {
/* 49 */       iResult = compare2(pb1, pb2);
/*    */     }
/*    */ 
/* 52 */     return iResult;
/*    */   }
/*    */ 
/*    */   private int compare1(PriceBand a_pb1, PriceBand a_pb2)
/*    */   {
/* 64 */     int iResult = 0;
/*    */ 
/* 66 */     if (a_pb1.getPriceBandType().getId() > a_pb2.getPriceBandType().getId())
/*    */     {
/* 69 */       iResult = -1;
/*    */     }
/* 71 */     if (a_pb1.getPriceBandType().getId() < a_pb2.getPriceBandType().getId())
/*    */     {
/* 74 */       iResult = 1;
/*    */     }
/*    */ 
/* 77 */     return iResult;
/*    */   }
/*    */ 
/*    */   private int compare2(PriceBand a_pb1, PriceBand a_pb2)
/*    */   {
/* 88 */     int iResult = a_pb1.getName().compareTo(a_pb2.getName());
/*    */ 
/* 90 */     return iResult;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.priceband.bean.PriceBandComparator
 * JD-Core Version:    0.6.0
 */