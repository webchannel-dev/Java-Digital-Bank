/*    */ package com.bright.assetbank.custom.indesign.asset.abplugin;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.custom.indesign.asset.bean.InDesignAssetWithRelated;
/*    */ import com.bright.assetbank.plugin.iface.ABViewMod;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.user.bean.User;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.annotation.Resource;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ @Component
/*    */ public class InDesignAssetViewMod
/*    */   implements ABViewMod
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private InDesignAssetService m_service;
/*    */ 
/*    */   public String getInclude(String a_sPosition)
/*    */   {
/* 37 */     if (a_sPosition.equals("dataStart"))
/*    */     {
/* 39 */       return "/jsp/plugin/indesign/view_asset_start.jsp";
/*    */     }
/*    */ 
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */   public String getReplacementInclude(String a_sPosition)
/*    */   {
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   public void populateViewRequest(DBTransaction a_transaction, HttpServletRequest a_request, Object a_coreObject, Object a_extensionData)
/*    */     throws Bn2Exception
/*    */   {
/* 57 */     InDesignAssetWithRelated inDAsset = (InDesignAssetWithRelated)a_extensionData;
/*    */ 
/* 59 */     if (inDAsset != null)
/*    */     {
/* 61 */       a_request.setAttribute("indesign_inDAsset", inDAsset);
/*    */ 
/* 65 */       if (inDAsset.isDocument())
/*    */       {
/* 67 */         if (inDAsset.getTemplateAssetId() > 0L)
/*    */         {
/* 69 */           UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/* 70 */           String sLanguageCode = userProfile.getUser().getLanguage().getCode();
/*    */ 
/* 72 */           String templateName = getService().getTemplateAssetName(inDAsset.getTemplateAssetId(), sLanguageCode);
/*    */ 
/* 74 */           a_request.setAttribute("indesign_templateName", templateName);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private InDesignAssetService getService()
/*    */   {
/* 84 */     return this.m_service;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.asset.abplugin.InDesignAssetViewMod
 * JD-Core Version:    0.6.0
 */