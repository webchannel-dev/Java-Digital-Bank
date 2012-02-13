/*    */ package com.bright.assetbank.entity.relationship.form;
/*    */ 
/*    */ import com.bn2web.common.form.Bn2Form;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.framework.common.bean.StringObjectBean;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class RelationshipDescriptionForm extends Bn2Form
/*    */ {
/* 32 */   private String m_sRelationshipDescriptionLabel = null;
/* 33 */   private Asset m_sourceAsset = null;
/* 34 */   private ArrayList<StringObjectBean> m_alChildAssetDescriptions = new ArrayList();
/* 35 */   private ArrayList<StringObjectBean> m_alPeerAssetDescriptions = new ArrayList();
/*    */ 
/*    */   public void setRelationshipDescriptionLabel(String a_sRelationshipDescriptionLabel)
/*    */   {
/* 39 */     this.m_sRelationshipDescriptionLabel = a_sRelationshipDescriptionLabel;
/*    */   }
/*    */ 
/*    */   public String getRelationshipDescriptionLabel()
/*    */   {
/* 44 */     return this.m_sRelationshipDescriptionLabel;
/*    */   }
/*    */ 
/*    */   public Asset getSourceAsset()
/*    */   {
/* 49 */     return this.m_sourceAsset;
/*    */   }
/*    */ 
/*    */   public void setSourceAsset(Asset a_sourceAsset)
/*    */   {
/* 54 */     this.m_sourceAsset = a_sourceAsset;
/*    */   }
/*    */ 
/*    */   public void setChildAssetDescriptions(ArrayList<StringObjectBean> a_alChildAssetDescriptions)
/*    */   {
/* 59 */     this.m_alChildAssetDescriptions = a_alChildAssetDescriptions;
/*    */   }
/*    */ 
/*    */   public ArrayList<StringObjectBean> getChildAssetDescriptions()
/*    */   {
/* 64 */     return this.m_alChildAssetDescriptions;
/*    */   }
/*    */ 
/*    */   public void setPeerAssetDescriptions(ArrayList<StringObjectBean> a_alPeerAssetDescriptions)
/*    */   {
/* 69 */     this.m_alPeerAssetDescriptions = a_alPeerAssetDescriptions;
/*    */   }
/*    */ 
/*    */   public ArrayList<StringObjectBean> getPeerAssetDescriptions()
/*    */   {
/* 74 */     return this.m_alPeerAssetDescriptions;
/*    */   }
/*    */ 
/*    */   public int getCount()
/*    */   {
/* 79 */     int iCount = 0;
/* 80 */     if (this.m_alChildAssetDescriptions != null)
/*    */     {
/* 82 */       iCount += this.m_alChildAssetDescriptions.size();
/*    */     }
/*    */ 
/* 85 */     if (this.m_alPeerAssetDescriptions != null)
/*    */     {
/* 87 */       iCount += this.m_alPeerAssetDescriptions.size();
/*    */     }
/*    */ 
/* 90 */     return iCount;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.form.RelationshipDescriptionForm
 * JD-Core Version:    0.6.0
 */