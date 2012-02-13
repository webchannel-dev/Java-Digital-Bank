/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.aspose.slides.Presentation;
/*    */ import com.aspose.slides.Slide;
/*    */ import com.bright.assetbank.converter.exception.AssetConversionException;
/*    */ import com.bright.framework.util.FileUtil;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Point;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ public class PptToJpgConverter extends AsposeSlidesConverter
/*    */   implements AssetConverter
/*    */ {
/*    */   public String convert(String a_sBaseSourcePath, String a_sBaseDestinationPath, String a_sRelativeSourcePath, String a_sRelativeDestinationPath)
/*    */     throws AssetConversionException
/*    */   {
/*    */     try
/*    */     {
/* 51 */       Presentation pres = new Presentation(new FileInputStream(new File(a_sBaseSourcePath + "/" + a_sRelativeSourcePath)));
/*    */ 
/* 54 */       Slide slide = pres.getSlideByPosition(1);
/*    */ 
/* 57 */       BufferedImage image = slide.getThumbnail(new Dimension((int)pres.getSlideSize().getX(), (int)pres.getSlideSize().getY()));
/*    */ 
/* 60 */       a_sRelativeDestinationPath = FileUtil.getFilepathWithoutSuffix(a_sRelativeDestinationPath);
/* 61 */       a_sRelativeDestinationPath = a_sRelativeDestinationPath + ".jpg";
/*    */ 
/* 64 */       ImageIO.write(image, "jpeg", new File(a_sBaseDestinationPath + "/" + a_sRelativeDestinationPath));
/*    */     }
/*    */     catch (Throwable e)
/*    */     {
/* 68 */       throw new AssetConversionException(getClass().getSimpleName() + ": " + e.getMessage(), e);
/*    */     }
/* 70 */     return a_sRelativeDestinationPath;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.PptToJpgConverter
 * JD-Core Version:    0.6.0
 */