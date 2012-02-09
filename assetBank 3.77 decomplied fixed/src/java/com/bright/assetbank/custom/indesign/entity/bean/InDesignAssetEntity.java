/*    */ package com.bright.assetbank.custom.indesign.entity.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class InDesignAssetEntity
/*    */   implements Serializable
/*    */ {
/*    */   private int m_inDesignAssetEntityTypeId;
/*    */ 
/*    */   public boolean isDocument()
/*    */   {
/* 30 */     return this.m_inDesignAssetEntityTypeId == 1;
/*    */   }
/*    */ 
/*    */   public boolean isSymbol()
/*    */   {
/* 35 */     return this.m_inDesignAssetEntityTypeId == 2;
/*    */   }
/*    */ 
/*    */   public boolean isImage()
/*    */   {
/* 40 */     return this.m_inDesignAssetEntityTypeId == 3;
/*    */   }
/*    */ 
/*    */   public int getInDesignAssetEntityTypeId()
/*    */   {
/* 51 */     return this.m_inDesignAssetEntityTypeId;
/*    */   }
/*    */ 
/*    */   public void setInDesignAssetEntityTypeId(int a_inDesignAssetEntityTypeId)
/*    */   {
/* 56 */     this.m_inDesignAssetEntityTypeId = a_inDesignAssetEntityTypeId;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity
 * JD-Core Version:    0.6.0
 */