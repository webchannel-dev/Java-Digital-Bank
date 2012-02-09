/*    */ package com.bright.framework.image.util;
/*    */ 
/*    */ import com.bright.framework.util.MathUtil;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Point;
/*    */ import java.awt.Rectangle;
/*    */ 
/*    */ public class GeometryUtil
/*    */ {
/*    */   public static Rectangle fitAndCentre(Dimension a_aspectRatio, Dimension a_boundingBox)
/*    */   {
/* 42 */     Dimension fitted = fit(a_aspectRatio, a_boundingBox);
/*    */ 
/* 45 */     int x = MathUtil.roundDoubleToInt((a_boundingBox.getWidth() - fitted.width) / 2.0D);
/*    */ 
/* 47 */     int y = MathUtil.roundDoubleToInt((a_boundingBox.getHeight() - fitted.height) / 2.0D);
/*    */ 
/* 49 */     Point origin = new Point(x, y);
/*    */ 
/* 51 */     return new Rectangle(origin, fitted);
/*    */   }
/*    */ 
/*    */   public static Dimension fit(Dimension a_aspectRatio, Dimension a_boundingBox)
/*    */   {
/* 61 */     double scale = Math.min(a_boundingBox.getWidth() / a_aspectRatio.getWidth(), a_boundingBox.getHeight() / a_aspectRatio.getHeight());
/*    */ 
/* 66 */     int width = MathUtil.roundDoubleToInt(scale * a_aspectRatio.getWidth());
/* 67 */     int height = MathUtil.roundDoubleToInt(scale * a_aspectRatio.getHeight());
/* 68 */     return new Dimension(width, height);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.image.util.GeometryUtil
 * JD-Core Version:    0.6.0
 */