/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public abstract class AsposeSlidesConverter
/*    */ {
/*    */   public AsposeSlidesConverter()
/*    */   {
/* 37 */     FileInputStream slidesLicenseStream = null;
/* 38 */     FileInputStream metafilesLicenseStream = null;
/*    */     try
/*    */     {
/* 42 */       String sLicensePath = AssetBankSettings.getApplicationPath() + File.separator + AssetBankSettings.getThirdPartyLicenseDirectory();
/* 43 */       slidesLicenseStream = new FileInputStream(sLicensePath + File.separator + "Aspose.Slides.lic");
/* 44 */       metafilesLicenseStream = new FileInputStream(sLicensePath + File.separator + "Aspose.Metafiles.lic");
/*    */ 
/* 47 */       com.aspose.slides.License slidesLicense = new com.aspose.slides.License();
/* 48 */       com.aspose.metafiles.License metafilesLicense = new com.aspose.metafiles.License();
/*    */ 
/* 51 */       slidesLicense.setLicense(slidesLicenseStream);
/* 52 */       metafilesLicense.setLicense(metafilesLicenseStream);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 56 */       String sMessage = getClass().getSimpleName() + " : Error loading licenses: ";
/* 57 */       GlobalApplication.getInstance().getLogger().error(sMessage, e);
/*    */     }
/*    */     finally
/*    */     {
/* 63 */       if (slidesLicenseStream != null)
/*    */       {
/*    */         try
/*    */         {
/* 67 */           slidesLicenseStream.close();
/*    */         } catch (IOException e) {
/*    */         }
/*    */       }
/* 71 */       if (metafilesLicenseStream != null)
/*    */       {
/*    */         try
/*    */         {
/* 75 */           metafilesLicenseStream.close();
/*    */         }
/*    */         catch (IOException e)
/*    */         {
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.AsposeSlidesConverter
 * JD-Core Version:    0.6.0
 */