/*    */ package com.bright.assetbank.plugin.taglib;
/*    */ 
/*    */ import com.bright.framework.util.TEIUtil;
/*    */ import javax.servlet.jsp.tagext.TagData;
/*    */ import javax.servlet.jsp.tagext.TagExtraInfo;
/*    */ import javax.servlet.jsp.tagext.VariableInfo;
/*    */ 
/*    */ public class HaveViewExtensionIncludesTEI extends TagExtraInfo
/*    */ {
/*    */   public VariableInfo[] getVariableInfo(TagData a_tagData)
/*    */   {
/* 30 */     return TEIUtil.createVariableInfoForAttribute(a_tagData, "id", "java.lang.Boolean", 1);
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.plugin.taglib.HaveViewExtensionIncludesTEI
 * JD-Core Version:    0.6.0
 */