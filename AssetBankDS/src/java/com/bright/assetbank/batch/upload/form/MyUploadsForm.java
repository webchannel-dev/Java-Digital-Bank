/*    */ package com.bright.assetbank.batch.upload.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.framework.common.bean.BrightDate;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MyUploadsForm extends Bn2Form
/*    */ {
/*    */   private List m_userUploadsForDayList;
/*    */   private int m_iNumIncompleteAssets;
/*    */   private long m_lNumUnsubmittedAssets;
/*    */   private BrightDate m_dtAssetsShownFrom;
/*    */ 
/*    */   public List getUserUploadsForDayList()
/*    */   {
/* 21 */     return this.m_userUploadsForDayList;
/*    */   }
/*    */ 
/*    */   public void setUserUploadsForDayList(List a_userUploadsForDayList) {
/* 25 */     this.m_userUploadsForDayList = a_userUploadsForDayList;
/*    */   }
/*    */ 
/*    */   public int getNumIncompleteAssets()
/*    */   {
/* 30 */     return this.m_iNumIncompleteAssets;
/*    */   }
/*    */ 
/*    */   public void setNumIncompleteAssets(int a_iNumIncompleteAssets)
/*    */   {
/* 35 */     this.m_iNumIncompleteAssets = a_iNumIncompleteAssets;
/*    */   }
/*    */ 
/*    */   public long getNumUnsubmittedAssets()
/*    */   {
/* 40 */     return this.m_lNumUnsubmittedAssets;
/*    */   }
/*    */ 
/*    */   public void setNumUnsubmittedAssets(long a_lNumUnsubmittedAssets) {
/* 44 */     this.m_lNumUnsubmittedAssets = a_lNumUnsubmittedAssets;
/*    */   }
/*    */ 
/*    */   public BrightDate getAssetsShownFromDate()
/*    */   {
/* 49 */     return this.m_dtAssetsShownFrom;
/*    */   }
/*    */ 
/*    */   public void setAssetsShownFromDate(BrightDate assetsShownFrom)
/*    */   {
/* 54 */     this.m_dtAssetsShownFrom = assetsShownFrom;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.batch.upload.form.MyUploadsForm
 * JD-Core Version:    0.6.0
 */