/*    */ package com.bright.assetbank.application.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.attribute.bean.Attribute;
/*    */ import java.util.Vector;
/*    */ 
/*    */ public class SlideshowForm extends Bn2Form
/*    */ {
/*    */   private static final long serialVersionUID = -4638633823536172743L;
/* 33 */   private Vector<Attribute> m_vecAttributeList = null;
/* 34 */   private Vector<Long> m_vecAttributeIds = null;
/* 35 */   private String m_sDisplayTime = "5";
/* 36 */   private boolean m_bShowAttributeLabels = false;
/*    */ 
/*    */   public Vector<Attribute> getAttributeList()
/*    */   {
/* 40 */     return this.m_vecAttributeList;
/*    */   }
/*    */ 
/*    */   public void setAttributeList(Vector<Attribute> a_vecAttributeList)
/*    */   {
/* 45 */     this.m_vecAttributeList = a_vecAttributeList;
/*    */   }
/*    */ 
/*    */   public void setAttributeIds(Vector<Long> a_vecAttributeIds)
/*    */   {
/* 50 */     this.m_vecAttributeIds = a_vecAttributeIds;
/*    */   }
/*    */ 
/*    */   public Vector<Long> getAttributeIds()
/*    */   {
/* 55 */     return this.m_vecAttributeIds;
/*    */   }
/*    */ 
/*    */   public void setDisplayTime(String a_sDisplayTime)
/*    */   {
/* 60 */     this.m_sDisplayTime = a_sDisplayTime;
/*    */   }
/*    */ 
/*    */   public String getDisplayTime()
/*    */   {
/* 65 */     return this.m_sDisplayTime;
/*    */   }
/*    */ 
/*    */   public void setShowAttributeLabels(boolean a_bShowAttributeLabels)
/*    */   {
/* 70 */     this.m_bShowAttributeLabels = a_bShowAttributeLabels;
/*    */   }
/*    */ 
/*    */   public boolean getShowAttributeLabels()
/*    */   {
/* 75 */     return this.m_bShowAttributeLabels;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.form.SlideshowForm
 * JD-Core Version:    0.6.0
 */