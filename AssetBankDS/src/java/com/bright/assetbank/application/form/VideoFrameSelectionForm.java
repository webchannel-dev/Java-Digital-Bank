/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class VideoFrameSelectionForm extends Bn2Form
/*    */ {
/* 30 */   private Vector m_vecFrames = null;
/* 31 */   private boolean m_bLast = false;
/*    */ 
/*    */   public void setFrames(Vector a_vecFrames)
/*    */   {
/* 35 */     this.m_vecFrames = a_vecFrames;
/*    */   }
/*    */ 
/*    */   public Vector getFrames()
/*    */   {
/* 40 */     return this.m_vecFrames;
/*    */   }
/*    */ 
/*    */   public void setLast(boolean a_bLast)
/*    */   {
/* 45 */     this.m_bLast = a_bLast;
/*    */   }
/*    */ 
/*    */   public boolean getLast()
/*    */   {
/* 50 */     return this.m_bLast;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.VideoFrameSelectionForm
 * JD-Core Version:    0.6.0
 */