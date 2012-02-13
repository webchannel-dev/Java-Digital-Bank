/*    */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*    */ 
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignAssetEditExistingMod extends InDesignAssetEditMod
/*    */ {
/*    */   public String getInclude(String a_sPosition)
/*    */   {
/* 27 */     if (a_sPosition.equals("start"))
/*    */     {
/* 29 */       return "/jsp/plugin/indesign/edit_existing_asset_form_start.jsp";
/*    */     }
/*    */ 
/* 33 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetEditExistingMod
 * JD-Core Version:    0.6.0
 */