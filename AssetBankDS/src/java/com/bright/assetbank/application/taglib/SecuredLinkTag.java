/*     */ package com.bright.assetbank.application.taglib;
/*     */ 
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.constant.UserConstants;
/*     */ import com.bright.assetbank.user.service.RoleManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyTagSupport;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ 
/*     */ public class SecuredLinkTag extends BodyTagSupport
/*     */   implements AssetBankConstants, UserConstants
/*     */ {
/*  43 */   private String m_sLinkId = null;
/*  44 */   private String m_sHref = null;
/*  45 */   private String m_sAlt = "";
/*  46 */   private String m_sLinkText = null;
/*  47 */   private String m_sPrefix = "";
/*  48 */   private String m_sSuffix = "";
/*     */ 
/*     */   public void setLinkId(String a_sLinkId)
/*     */   {
/*  52 */     this.m_sLinkId = a_sLinkId;
/*     */   }
/*     */ 
/*     */   public void setHref(String a_sHref)
/*     */   {
/*  57 */     this.m_sHref = a_sHref;
/*     */   }
/*     */ 
/*     */   public void setAlt(String a_sAlt)
/*     */   {
/*  62 */     this.m_sAlt = a_sAlt;
/*     */   }
/*     */ 
/*     */   public void setLinkText(String a_sLinkText)
/*     */   {
/*  67 */     this.m_sLinkText = a_sLinkText;
/*     */   }
/*     */ 
/*     */   public void setPrefix(String a_sPrefix)
/*     */   {
/*  72 */     this.m_sPrefix = a_sPrefix;
/*     */   }
/*     */ 
/*     */   public void setSuffix(String a_sSuffix)
/*     */   {
/*  77 */     this.m_sSuffix = a_sSuffix;
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*  93 */     return 2;
/*     */   }
/*     */ 
/*     */   public int doAfterBody()
/*     */     throws JspException
/*     */   {
/* 110 */     return 0;
/*     */   }
/*     */ 
/*     */   public int doEndTag()
/*     */     throws JspException
/*     */   {
/* 128 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(this.pageContext.getSession());
/*     */     try
/*     */     {
/* 132 */       int iUserRoleStatus = -1;
/* 133 */       if (AssetBankSettings.getUseGroupRoles())
/*     */       {
/* 136 */         RoleManager roleManager = (RoleManager)GlobalApplication.getInstance().getComponentManager().lookup("RoleManager");
/* 137 */         iUserRoleStatus = roleManager.getUserRoleStatusForLink(userProfile, this.m_sLinkId);
/*     */       }
/*     */ 
/* 141 */       String link = this.m_sPrefix + "<a href='" + this.m_sHref + "' alt='" + this.m_sAlt + "'>" + this.m_sLinkText + "</a>" + this.m_sSuffix;
/* 142 */       if ((userProfile.getIsAdmin()) || (iUserRoleStatus == 1))
/*     */       {
/* 144 */         this.pageContext.getOut().write(link);
/*     */       }
/*     */     }
/*     */     catch (ComponentException e)
/*     */     {
/* 149 */       throw new JspException("Component Exception occured whilst getting RoleManager for SecuredLinkTag tag: " + e.getMessage());
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 153 */       throw new JspException("IO Exception occured whilst writing secured link: " + e.getMessage());
/*     */     }
/*     */ 
/* 157 */     return 6;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.taglib.SecuredLinkTag
 * JD-Core Version:    0.6.0
 */