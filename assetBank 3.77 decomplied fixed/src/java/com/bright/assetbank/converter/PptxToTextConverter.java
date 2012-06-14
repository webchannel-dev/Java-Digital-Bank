/*    */ package com.bright.assetbank.converter;
/*    */ 
/*    */ import com.aspose.slides.pptx.AutoShapeEx;
/*    */ import com.aspose.slides.pptx.ParagraphEx;
/*    */ import com.aspose.slides.pptx.ParagraphsEx;
/*    */ import com.aspose.slides.pptx.PortionEx;
/*    */ import com.aspose.slides.pptx.PortionsEx;
/*    */ import com.aspose.slides.pptx.PresentationEx;
/*    */ import com.aspose.slides.pptx.ShapeEx;
/*    */ import com.aspose.slides.pptx.ShapesEx;
/*    */ import com.aspose.slides.pptx.SlideEx;
/*    */ import com.aspose.slides.pptx.SlidesEx;
/*    */ import com.aspose.slides.pptx.TextFrameEx;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class PptxToTextConverter extends AsposeSlidesConverter
/*    */   implements AssetToTextConverter
/*    */ {
/*    */   public String getText(String a_sFullPath)
/*    */     throws Bn2Exception
/*    */   {
/* 40 */     StringBuilder sbText = new StringBuilder();
/*    */     try
/*    */     {
/* 44 */       PresentationEx presentation = new PresentationEx(new FileInputStream(new File(a_sFullPath)));
/*    */ 
/* 46 */       for (int index = 0; index < presentation.getSlides().size(); index++)
/*    */       {
/* 48 */         SlideEx slideEx = presentation.getSlides().get(index);
/* 49 */         ShapesEx shps = slideEx.getShapes();
/*    */ 
/* 51 */         for (int sh = 0; sh < shps.size(); sh++)
/*    */         {
/* 53 */           ShapeEx shape = shps.get(sh);
/*    */ 
/* 55 */           if (!(shape instanceof AutoShapeEx))
/*    */             continue;
/* 57 */           AutoShapeEx aShp = (AutoShapeEx)shape;
/*    */ 
/* 59 */           if (aShp.getTextFrame() == null)
/*    */             continue;
/* 61 */           TextFrameEx tf = aShp.getTextFrame();
/*    */ 
/* 63 */           for (int pg = 0; pg < tf.getParagraphs().size(); pg++)
/*    */           {
/* 65 */             ParagraphEx Paragraph = tf.getParagraphs().get(pg);
/*    */ 
/* 67 */             for (int pt = 0; pt < Paragraph.getPortions().size(); pt++)
/*    */             {
/* 69 */               sbText.append(Paragraph.getPortions().get(pt).getText());
/*    */             }
/*    */           }
/* 72 */           sbText.append(" ");
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 80 */       throw new Bn2Exception(getClass().getSimpleName() + ".getText() : Exception thrown while processing pptx : " + e.getLocalizedMessage(), e);
/*    */     }
/*    */ 
/* 83 */     GlobalApplication.getInstance().getLogger().debug(getClass().getSimpleName() + ": Text from PPTX file: " + sbText.toString());
/*    */ 
/* 85 */     return sbText.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.converter.PptxToTextConverter
 * JD-Core Version:    0.6.0
 */