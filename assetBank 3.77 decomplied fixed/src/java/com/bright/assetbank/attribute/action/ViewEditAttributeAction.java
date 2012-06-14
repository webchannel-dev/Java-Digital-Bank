/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.form.AttributeForm;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.attribute.service.AttributeValueManager;
/*     */ import com.bright.assetbank.language.service.LanguageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewEditAttributeAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "ViewEditAttributeAction";
/*  60 */   private AttributeManager m_attributeManager = null;
/*  61 */   private AttributeValueManager m_attributeValueManager = null;
/*  62 */   private LanguageManager m_languageManager = null;
/*  63 */   private ABUserManager m_userManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  90 */     ActionForward afForward = null;
/*  91 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  94 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  96 */       this.m_logger.error("ViewEditAttributeActionThis user does not have permission to view the admin pages");
/*  97 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/* 100 */     AttributeForm form = (AttributeForm)a_form;
/*     */     try
/*     */     {
/* 105 */       long lAttributeId = getLongParameter(a_request, "attributeId");
/*     */ 
/* 107 */       if (lAttributeId > 0L)
/*     */       {
/* 109 */         if (lAttributeId <= 0L)
/*     */         {
/* 111 */           lAttributeId = form.getAttribute().getId();
/*     */         }
/*     */ 
/* 114 */         Attribute attribute = this.m_attributeManager.getAttribute(a_dbTransaction, lAttributeId);
/* 115 */         form.setAttribute(attribute);
/*     */ 
/* 118 */         if ((attribute.getTypeId() == 4L) || (attribute.getTypeId() == 5L))
/*     */         {
/* 122 */           form.setListAttributeValues(this.m_attributeValueManager.getListAttributeValues(a_dbTransaction, lAttributeId));
/*     */         }
/*     */ 
/* 126 */         if (attribute.getIsAutoincrement())
/*     */         {
/* 128 */           attribute.setPadWidth(attribute.getWidth());
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 134 */         if (AssetBankSettings.getSupportMultiLanguage())
/*     */         {
/* 137 */           this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getAttribute());
/*     */         }
/*     */ 
/* 140 */         if (!form.getHasErrors())
/*     */         {
/* 143 */           form.getAttribute().setKeywordSearchable(true);
/* 144 */           form.getAttribute().setHeight(4);
/* 145 */           form.getAttribute().setMaxSize(4000);
/*     */         }
/*     */       }
/*     */ 
/* 149 */       List vGroups = new Vector();
/* 150 */       form.setVisibleToGroups(vGroups);
/* 151 */       vGroups.add(getUserManager().getPublicGroup(a_dbTransaction));
/* 152 */       vGroups.add(getUserManager().getGroup(1L));
/*     */ 
/* 154 */       if (form.getAttribute().getTypeId() == 10L)
/*     */       {
/* 156 */         if (form.getAttribute().getId() <= 0L)
/*     */         {
/* 158 */           form.getAttribute().setHighlight(true);
/*     */         }
/* 160 */         afForward = a_mapping.findForward("AddGroupHeading");
/*     */       }
/*     */       else
/*     */       {
/* 164 */         afForward = a_mapping.findForward("Success");
/*     */       }
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 169 */       this.m_logger.error("ViewEditAttributeAction" + bn2e.getMessage());
/* 170 */       throw bn2e;
/*     */     }
/*     */ 
/* 173 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 178 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public void setAttributeValueManager(AttributeValueManager a_attributeValueManager)
/*     */   {
/* 183 */     this.m_attributeValueManager = a_attributeValueManager;
/*     */   }
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/* 189 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 195 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setLanguageManager(LanguageManager a_languageManager)
/*     */   {
/* 200 */     this.m_languageManager = a_languageManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.ViewEditAttributeAction
 * JD-Core Version:    0.6.0
 */