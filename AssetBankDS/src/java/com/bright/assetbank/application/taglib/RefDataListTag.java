/*    */ package com.bright.assetbank.application.taglib;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ 
/*    */ public class RefDataListTag extends com.bright.framework.common.taglib.RefDataListTag
/*    */ {
/*    */   public int doEndTag()
/*    */     throws JspException
/*    */   {
/* 44 */     if (AssetBankSettings.isApplicationUpdateInProgress())
/*    */     {
/* 46 */       this.pageContext.setAttribute(this.m_sId, "", 1);
/* 47 */       return 6;
/*    */     }
/*    */ 
/* 51 */     return super.doEndTag();
/*    */   }
/*    */ 
/*    */   protected Class getUserProfileClass()
/*    */   {
/* 56 */     return ABUserProfile.class;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.taglib.RefDataListTag
 * JD-Core Version:    0.6.0
 */