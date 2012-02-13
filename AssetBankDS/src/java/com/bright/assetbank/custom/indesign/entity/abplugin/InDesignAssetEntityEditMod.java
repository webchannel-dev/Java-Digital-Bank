/*     */ package com.bright.assetbank.custom.indesign.entity.abplugin;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.custom.indesign.entity.bean.InDesignAssetEntity;
/*     */ import com.bright.assetbank.entity.bean.AssetEntity;
/*     */ import com.bright.assetbank.plugin.bean.ABExtensibleBean;
/*     */ import com.bright.assetbank.plugin.iface.ABEditMod;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.struts.Bn2ExtensibleForm;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ @Component
/*     */ public class InDesignAssetEntityEditMod
/*     */   implements ABEditMod
/*     */ {
/*     */   private static final String c_ksFormKey_InDesignAsset_EntityTypeId = "indesign_inDesignAssetEntityTypeId";
/*     */ 
/*     */   @Resource
/*     */   private InDesignAssetEntityService m_service;
/*     */ 
/*     */   public void populateForm(DBTransaction a_transaction, Object a_oCoreObject, Serializable a_extensionData, HttpServletRequest a_request, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/*  59 */     InDesignAssetEntity inDesignAssetEntity = (InDesignAssetEntity)a_extensionData;
/*  60 */     int typeId = getService().typeIdFromInDesignAssetEntity(inDesignAssetEntity);
/*  61 */     a_form.setExt("indesign_inDesignAssetEntityTypeId", Integer.valueOf(typeId));
/*     */   }
/*     */ 
/*     */   public void populateViewEditRequest(DBTransaction a_transaction, HttpServletRequest a_request, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     List types = getService().getInDesignAssetEntityTypes(a_transaction);
/*  71 */     a_request.setAttribute("indesign_assetEntityTypes", types);
/*     */   }
/*     */ 
/*     */   public String getInclude(String a_sPosition)
/*     */   {
/*  78 */     if (a_sPosition.equals("start"))
/*     */     {
/*  80 */       return "/jsp/plugin/indesign/asset_entity_form_start.jsp";
/*     */     }
/*     */ 
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */   public void validate(DBTransaction a_transaction, ABUserProfile a_userProfile, Object a_oCoreObject, Bn2ExtensibleForm a_form)
/*     */     throws Bn2Exception
/*     */   {
/*  91 */     AssetEntity assetEntity = (AssetEntity)a_oCoreObject;
/*  92 */     boolean adding = assetEntity.getId() <= 0L;
/*  93 */     if (!adding)
/*     */     {
/*  95 */       int existingTypeId = getService().getTypeId(a_transaction, assetEntity.getId());
/*  96 */       int newTypeId = typeIdFromForm(a_form);
/*     */ 
/*  98 */       if ((newTypeId != existingTypeId) && (getService().assetsOfEntityExist(a_transaction, assetEntity.getId())))
/*     */       {
/* 101 */         a_form.addError("The InDesign type of this asset type cannot be changed because assets of this asset type exist");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public InDesignAssetEntity extractDataFromForm(DBTransaction a_transaction, ABExtensibleBean a_coreObject, Bn2ExtensibleForm a_form)
/*     */   {
/* 108 */     InDesignAssetEntity inDAE = new InDesignAssetEntity();
/* 109 */     int typeId = typeIdFromForm(a_form);
/* 110 */     inDAE.setInDesignAssetEntityTypeId(typeId);
/* 111 */     return inDAE;
/*     */   }
/*     */ 
/*     */   private int typeIdFromForm(Bn2ExtensibleForm a_form)
/*     */   {
/* 116 */     return a_form.getExtInt("indesign_inDesignAssetEntityTypeId");
/*     */   }
/*     */ 
/*     */   protected InDesignAssetEntityService getService()
/*     */   {
/* 121 */     return this.m_service;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.indesign.entity.abplugin.InDesignAssetEntityEditMod
 * JD-Core Version:    0.6.0
 */