/*    */ package com.bright.assetbank.simplelist.taglib;
/*    */ 
/*    */ import com.bn2web.common.constant.CommonConstants;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.assetbank.user.bean.Brand;
/*    */ import com.bright.assetbank.user.service.ABUserManager;
/*    */ import com.bright.framework.language.bean.Language;
/*    */ import com.bright.framework.simplelist.bean.ListItem;
/*    */ import javax.servlet.jsp.PageContext;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class CMSWriteTag extends com.bright.framework.simplelist.taglib.CMSWriteTag
/*    */   implements AssetBankConstants, CommonConstants
/*    */ {
/*    */   public ListItem getItem()
/*    */   {
/* 46 */     ListItem item = null;
/*    */     try
/*    */     {
/* 51 */       if (AssetBankSettings.getUseBrands())
/*    */       {
/* 53 */         ABUserProfile userProfile = (ABUserProfile)this.pageContext.getAttribute("userprofile", 3);
/*    */ 
/* 55 */         if (userProfile != null)
/*    */         {
/* 57 */           String sContentListIdentifier = userProfile.getBrand().getContentListIdentifier();
/* 58 */           ABUserManager m_userManager = (ABUserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager");
/* 59 */           Language language = getLanguage();
/* 60 */           item = m_userManager.getBrandedListItem(null, getIdentifier(), sContentListIdentifier, language);
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 66 */       GlobalApplication.getInstance().getLogger().error("CMSWriteTag.getItem : Unable to prepare list item for writing, for identifier : " + getIdentifier() + " " + e.getMessage());
/* 67 */       e.printStackTrace();
/*    */     }
/*    */ 
/* 71 */     if (item == null)
/*    */     {
/* 73 */       item = super.getItem();
/*    */     }
/*    */ 
/* 76 */     return item;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.simplelist.taglib.CMSWriteTag
 * JD-Core Version:    0.6.0
 */