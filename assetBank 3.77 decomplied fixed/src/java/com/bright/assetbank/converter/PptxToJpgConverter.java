/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.aspose.slides.pptx.PresentationEx;
/*    */ import com.aspose.slides.pptx.SlideEx;
/*    */ import com.aspose.slides.pptx.SlidesEx;
/*    */ import com.bright.assetbank.converter.exception.AssetConversionException;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ public class PptxToJpgConverter extends AsposeSlidesConverter
/*    */   implements AssetConverter
/*    */ {
/*    */   public String convert(String a_sBaseSourcePath, String a_sBaseDestinationPath, String a_sRelativeSourcePath, String a_sRelativeDestinationPath)
/*    */     throws AssetConversionException
/*    */   {
/*    */     try
/*    */     {
/* 49 */       PresentationEx pres = new PresentationEx(new FileInputStream(new File(a_sBaseSourcePath + "/" + a_sRelativeSourcePath)));
/*    */ 
/* 52 */       SlideEx slide = pres.getSlides().get(0);
/*    */ 
/* 55 */       BufferedImage image = slide.getThumbnail(1.0F, 1.0F);
/*    */ 
/* 58 */       a_sRelativeDestinationPath = FileUtil.getFilepathWithoutSuffix(a_sRelativeDestinationPath);
/* 59 */       a_sRelativeDestinationPath = a_sRelativeDestinationPath + ".png";
/*    */ 
/* 63 */       ImageIO.write(image, "png", new File(a_sBaseDestinationPath + "/" + a_sRelativeDestinationPath));
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 67 */       throw new AssetConversionException(getClass().getSimpleName() + ": " + e.getMessage(), e);
/*    */     }
/* 69 */     return a_sRelativeDestinationPath;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.PptxToJpgConverter
 * JD-Core Version:    0.6.0
 */