/*     */ package com.bright.framework.simplelist.taglib;
/*     */ 
/*     */ import com.bn2web.common.constant.CommonConstants;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.framework.common.taglib.BrightWriteTag;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.language.bean.Language;
/*     */ import com.bright.framework.language.constant.LanguageConstants;
/*     */ import com.bright.framework.simplelist.bean.List;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.constant.ListConstants;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.simplelist.util.JSPVarReplaceParser;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.RequestUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.taglib.TagUtils;
/*     */ 
/*     */ public class CMSWriteTag extends BrightWriteTag
/*     */   implements ListConstants, CommonConstants, FrameworkConstants
/*     */ {
/*  58 */   protected String m_sListItemIdentifier = null;
/*  59 */   protected boolean m_bReplaceVariables = false;
/*  60 */   protected boolean m_bRemoveLinebreaks = false;
/*     */ 
/*  62 */   private ListManager m_listManager = null;
/*     */ 
/*     */   protected ListManager getListManager() throws ComponentException
/*     */   {
/*  66 */     if (this.m_listManager == null)
/*     */     {
/*  68 */       this.m_listManager = ((ListManager)GlobalApplication.getInstance().getComponentManager().lookup("ListManager"));
/*     */     }
/*     */ 
/*  71 */     return this.m_listManager;
/*     */   }
/*     */ 
/*     */   public int doStartTag()
/*     */     throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  91 */       ListItem listItem = getItem();
/*  92 */       Language language = getLanguage();
/*  93 */       List list = getListManager().getList(listItem.getListIdentifier(), language.getId());
/*  94 */       boolean bEditMode = false;
/*  95 */       String sEditClose = null;
/*     */ 
/*  99 */       HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
/* 100 */       UserProfile userprofile = UserProfile.getUserProfile(request.getSession());
/*     */ 
/* 102 */       if ((userprofile != null) && (list != null) && (listItem != null) && (userprofile.getCMSEditMode()) && (!list.getNoHTMLMarkup()))
/*     */       {
/* 106 */         String sBounceUrl = RequestUtil.getOriginalRequestUrl(request, true);
/* 107 */         bEditMode = true;
/*     */ 
/* 110 */         TagUtils.getInstance().write(this.pageContext, "<span class='editSpan'>");
/*     */ 
/* 113 */         Language lang = userprofile.getCurrentLanguage();
/* 114 */         if (lang == null)
/*     */         {
/* 116 */           lang = LanguageConstants.k_defaultLanguage;
/*     */         }
/* 118 */         sEditClose = "<a href='viewEditListItem?id=" + listItem.getIdentifier() + "&" + "languageId" + "=" + lang.getId() + "&" + "url" + "=" + sBounceUrl + "' class='editLink'><img src='../" + "images/standard/icon/cmsedit.gif" + "' alt='edit' class='editImg'/></a></span>";
/*     */       }
/*     */ 
/* 121 */       String sFormattedValue = getValue(listItem, list);
/*     */ 
/* 124 */       if (this.filter)
/*     */       {
/* 126 */         TagUtils.getInstance().write(this.pageContext, TagUtils.getInstance().filter(sFormattedValue));
/*     */       }
/*     */       else
/*     */       {
/* 130 */         TagUtils.getInstance().write(this.pageContext, sFormattedValue);
/*     */       }
/*     */ 
/* 134 */       if (bEditMode)
/*     */       {
/* 136 */         TagUtils.getInstance().write(this.pageContext, sEditClose);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 141 */       GlobalApplication.getInstance().getLogger().error("CMSWriteTag.doStartTag : Unable to prepare list item for writing, for identifier : " + getIdentifier() + " " + e.getMessage());
/* 142 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 146 */     return 0;
/*     */   }
/*     */ 
/*     */   protected String getValue(ListItem a_listItem, List list)
/*     */     throws Exception
/*     */   {
/* 161 */     String sFormattedValue = "";
/*     */ 
/* 163 */     if (a_listItem == null)
/*     */     {
/* 165 */       GlobalApplication.getInstance().getLogger().error("CMSWriteTag.doEndTag : there is no list item with identifier: " + getIdentifier());
/*     */     }
/* 170 */     else if (a_listItem.getBody() != null)
/*     */     {
/* 172 */       sFormattedValue = formatValue(a_listItem.getBody());
/*     */ 
/* 175 */       if ((this.m_bReplaceVariables) && (sFormattedValue != null))
/*     */       {
/* 178 */         JSPVarReplaceParser replaceParser = new JSPVarReplaceParser(this.pageContext);
/*     */ 
/* 181 */         sFormattedValue = replaceParser.parse(sFormattedValue);
/*     */       }
/*     */ 
/* 185 */       if ((this.m_bRemoveLinebreaks) && (sFormattedValue != null))
/*     */       {
/* 187 */         sFormattedValue = sFormattedValue.replaceAll("\n", "");
/* 188 */         sFormattedValue = sFormattedValue.replaceAll("\r", "");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 193 */     return sFormattedValue;
/*     */   }
/*     */ 
/*     */   protected Language getLanguage()
/*     */   {
/* 199 */     Language language = (Language)((HttpServletRequest)this.pageContext.getRequest()).getSession().getAttribute("currentLanguage");
/* 200 */     if (language == null)
/*     */     {
/* 202 */       language = LanguageConstants.k_defaultLanguage;
/*     */     }
/* 204 */     return language;
/*     */   }
/*     */ 
/*     */   protected ListItem getItem()
/*     */   {
/* 209 */     ListItem item = null;
/*     */     try
/*     */     {
/* 214 */       Language language = getLanguage();
/* 215 */       item = getListManager().getListItem(null, getIdentifier(), language);
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 219 */       GlobalApplication.getInstance().getLogger().error("CMSWriteTag.getItem : Unable to prepare list item for writing, for identifier : " + getIdentifier() + " " + e.getMessage());
/* 220 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 223 */     return item;
/*     */   }
/*     */ 
/*     */   public String getIdentifier()
/*     */   {
/* 228 */     return this.m_sListItemIdentifier;
/*     */   }
/*     */ 
/*     */   public void setIdentifier(String a_sListItemIdentifier) {
/* 232 */     this.m_sListItemIdentifier = a_sListItemIdentifier;
/*     */   }
/*     */ 
/*     */   public boolean getReplaceVariables() {
/* 236 */     return this.m_bReplaceVariables;
/*     */   }
/*     */ 
/*     */   public void setReplaceVariables(boolean a_bReplaceVariables) {
/* 240 */     this.m_bReplaceVariables = a_bReplaceVariables;
/*     */   }
/*     */ 
/*     */   public boolean getRemoveLinebreaks()
/*     */   {
/* 246 */     return this.m_bRemoveLinebreaks;
/*     */   }
/*     */ 
/*     */   public void setRemoveLinebreaks(boolean a_sRemoveLinebreaks)
/*     */   {
/* 252 */     this.m_bRemoveLinebreaks = a_sRemoveLinebreaks;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.framework.simplelist.taglib.CMSWriteTag
 * JD-Core Version:    0.6.0
 */