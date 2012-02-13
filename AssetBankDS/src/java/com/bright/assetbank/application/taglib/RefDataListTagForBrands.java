/*    */ package com.bright.assetbank.application.taglib;
/*    */ 
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.bean.Brand;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import javax.servlet.jsp.JspException;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ 
/*    */ public class RefDataListTagForBrands extends RefDataListTag
/*    */ {
/*    */   public int doEndTag()
/*    */     throws JspException
/*    */   {
/* 40 */     if ((AssetBankSettings.getUseBrands()) && (this.m_sComponentName.equalsIgnoreCase("ListManager")) && (this.m_sMethodName.equalsIgnoreCase("getListItem")))
/*    */     {
/* 42 */       ABUserProfile userProfile = (ABUserProfile)this.pageContext.getAttribute("userprofile", 3);
/*    */ 
/* 44 */       if (userProfile != null)
/*    */       {
/* 46 */         String sContentListIdentifier = userProfile.getBrand().getContentListIdentifier();
/*    */ 
/* 48 */         if (StringUtil.stringIsPopulated(sContentListIdentifier))
/*    */         {
/* 50 */           this.m_sComponentName = "UserManager";
/* 51 */           this.m_sMethodName = "getBrandedListItem";
/* 52 */           this.m_sArgument2Value = sContentListIdentifier;
/*    */ 
/* 55 */           this.m_sTransactionManagerName = "DBTransactionManager";
/*    */         }
/*    */         else
/*    */         {
/* 60 */           this.m_sArgument2Value = "";
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 65 */     return super.doEndTag();
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.taglib.RefDataListTagForBrands
 * JD-Core Version:    0.6.0
 */