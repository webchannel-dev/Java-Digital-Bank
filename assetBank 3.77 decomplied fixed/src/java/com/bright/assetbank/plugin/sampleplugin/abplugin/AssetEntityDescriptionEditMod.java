/*    */ package com.bright.assetbank.plugin.sampleplugin.abplugin;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.plugin.bean.ABExtensibleBean;
/*    */ import com.bright.assetbank.plugin.iface.ABEditMod;
/*    */ import com.bright.assetbank.plugin.sampleplugin.bean.AssetEntityDescription;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.struts.Bn2ExtensibleForm;
/*    */ import java.io.Serializable;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class AssetEntityDescriptionEditMod
/*    */   implements ABEditMod
/*    */ {
/*    */   private static final String c_ksFormKeyPrefix = "sampleplugin_";
/*    */   private static final String c_ksDescriptionFormKey = "sampleplugin_description";
/*    */ 
/*    */   public void populateForm(DBTransaction a_transaction, Object a_oCoreObject, Serializable a_extensionData, HttpServletRequest a_request, Bn2ExtensibleForm a_form)
/*    */     throws Bn2Exception
/*    */   {
/* 47 */     AssetEntityDescription aed = (AssetEntityDescription)a_extensionData;
/* 48 */     String description = aed == null ? "" : aed.getDescription();
/* 49 */     a_form.setExt("sampleplugin_description", description);
/*    */   }
/*    */ 
/*    */   public void populateViewEditRequest(DBTransaction a_transaction, HttpServletRequest a_request, Bn2ExtensibleForm a_form)
/*    */     throws Bn2Exception
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getInclude(String a_sPosition)
/*    */   {
/* 62 */     if (a_sPosition.equals("start"))
/*    */     {
/* 64 */       return "/jsp/plugin/sampleplugin/asset_entity_form_start.jsp";
/*    */     }
/*    */ 
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   public void validate(DBTransaction a_transaction, ABUserProfile a_userProfile, Object a_oCoreObject, Bn2ExtensibleForm a_form)
/*    */   {
/*    */   }
/*    */ 
/*    */   public AssetEntityDescription extractDataFromForm(DBTransaction a_transaction, ABExtensibleBean a_coreObject, Bn2ExtensibleForm a_form)
/*    */   {
/* 81 */     AssetEntityDescription aed = new AssetEntityDescription();
/* 82 */     aed.setDescription((String)a_form.getExt("sampleplugin_description"));
/* 83 */     return aed;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.sampleplugin.abplugin.AssetEntityDescriptionEditMod
 * JD-Core Version:    0.6.0
 */