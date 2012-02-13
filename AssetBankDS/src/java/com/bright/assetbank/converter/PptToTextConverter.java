/*     */ package com.bright.assetbank.converter;
/*     */ 
/*     */ import com.aspose.slides.GroupShape;
/*     */ import com.aspose.slides.PptException;
/*     */ import com.aspose.slides.Presentation;
/*     */ import com.aspose.slides.Rectangle;
/*     */ import com.aspose.slides.Shape;
/*     */ import com.aspose.slides.Shapes;
/*     */ import com.aspose.slides.Slide;
/*     */ import com.aspose.slides.Slides;
/*     */ import com.aspose.slides.TextFrame;
/*     */ import com.aspose.slides.TextHolder;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class PptToTextConverter extends AsposeSlidesConverter
/*     */   implements AssetToTextConverter
/*     */ {
/*     */   public String getText(String a_sFullPath)
/*     */     throws Bn2Exception
/*     */   {
/*  41 */     StringBuilder sbText = new StringBuilder();
/*     */     try
/*     */     {
/*  45 */       Presentation pres = new Presentation(new FileInputStream(new File(a_sFullPath)));
/*     */ 
/*  47 */       for (int i = 1; i <= pres.getSlides().size(); i++)
/*     */       {
/*  49 */         Slide sld = pres.getSlideByPosition(i);
/*     */ 
/*  51 */         for (int j = 0; j < sld.getShapes().size(); j++)
/*     */         {
/*  53 */           Shape shp = sld.getShapes().get(j);
/*     */ 
/*  55 */           if ((shp.getPlaceholder() != null) && (shp.isTextHolder() == true))
/*     */           {
/*  57 */             TextHolder thld = (TextHolder)shp.getPlaceholder();
/*  58 */             sbText.append(thld.getText()).append(' ');
/*     */           }
/*  60 */           else if ((shp instanceof Rectangle))
/*     */           {
/*  62 */             Rectangle rect = (Rectangle)shp;
/*  63 */             if (rect.getTextFrame() != null)
/*     */             {
/*  65 */               sbText.append(rect.getTextFrame().getText()).append(' ');
/*     */             }
/*     */           } else {
/*  68 */             if (!(shp instanceof GroupShape))
/*     */               continue;
/*  70 */             GroupShape gshp = (GroupShape)shp;
/*     */ 
/*  72 */             for (int k = 0; k < gshp.getShapes().size(); k++)
/*     */             {
/*  74 */               Shape shp1 = gshp.getShapes().get(k);
/*  75 */               if ((shp1.getPlaceholder() != null) && (shp1.isTextHolder() == true))
/*     */               {
/*  77 */                 TextHolder thld = (TextHolder)shp1.getPlaceholder();
/*  78 */                 sbText.append(thld.getText()).append(' ');
/*     */               } else {
/*  80 */                 if (!(shp1 instanceof Rectangle))
/*     */                   continue;
/*  82 */                 Rectangle rect = (Rectangle)shp1;
/*  83 */                 if (rect.getTextFrame() == null)
/*     */                   continue;
/*  85 */                 sbText.append(rect.getTextFrame().getText()).append(' ');
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (PptException e)
/*     */     {
/*  95 */       throw new Bn2Exception(getClass().getSimpleName() + ".getText() : Exception thrown while processing ppt : " + e.getLocalizedMessage(), e);
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*  99 */       throw new Bn2Exception(getClass().getSimpleName() + ".getText() : File not found : " + e.getLocalizedMessage(), e);
/*     */     }
/* 101 */     GlobalApplication.getInstance().getLogger().debug(getClass().getSimpleName() + ": Text from PPT file: " + sbText.toString());
/*     */ 
/* 103 */     return sbText.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.PptToTextConverter
 * JD-Core Version:    0.6.0
 */