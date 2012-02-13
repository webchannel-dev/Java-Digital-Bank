/*    */ package com.bright.assetbank.custom.bgs.bean;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PlacenameCriteria
/*    */ {
/* 34 */   private Collection<Long> m_placenameIds = new ArrayList();
/*    */   private String m_topoRel;
/*    */ 
/*    */   public PlacenameCriteria()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PlacenameCriteria(Collection<Long> a_placenameIds, String a_topoRel)
/*    */   {
/* 43 */     this.m_placenameIds = a_placenameIds;
/* 44 */     this.m_topoRel = a_topoRel;
/*    */   }
/*    */ 
/*    */   public boolean validate(List<String> a_errors, String a_sNiceName)
/*    */   {
/* 53 */     boolean bValid = true;
/*    */ 
/* 55 */     if ((!getTopoRel().equals("EQUAL")) && (!getTopoRel().equals("INSIDE")))
/*    */     {
/* 58 */       if (a_errors != null)
/*    */       {
/* 60 */         String sMessage = "Invalid Placename criteria: " + a_sNiceName + " must be " + "EQUAL" + " or " + "INSIDE" + ".";
/*    */ 
/* 62 */         a_errors.add(sMessage);
/*    */       }
/*    */ 
/* 65 */       bValid = false;
/*    */     }
/*    */ 
/* 68 */     return bValid;
/*    */   }
/*    */ 
/*    */   public boolean validate()
/*    */   {
/* 73 */     return validate(null, null);
/*    */   }
/*    */ 
/*    */   public Collection<Long> getPlacenameIds()
/*    */   {
/* 78 */     return this.m_placenameIds;
/*    */   }
/*    */ 
/*    */   public void setPlacenameIds(Collection<Long> a_placenameIds)
/*    */   {
/* 83 */     this.m_placenameIds = a_placenameIds;
/*    */   }
/*    */ 
/*    */   public String getTopoRel()
/*    */   {
/* 88 */     return this.m_topoRel;
/*    */   }
/*    */ 
/*    */   public void setTopoRel(String a_topoRel)
/*    */   {
/* 93 */     this.m_topoRel = a_topoRel;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.custom.bgs.bean.PlacenameCriteria
 * JD-Core Version:    0.6.0
 */