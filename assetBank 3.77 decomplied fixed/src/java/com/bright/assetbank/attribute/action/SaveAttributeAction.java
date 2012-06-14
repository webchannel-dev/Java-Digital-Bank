/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.exception.PluginInstantiationException;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.bean.AttributeType;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.attribute.form.AttributeForm;
/*     */ import com.bright.assetbank.attribute.plugin.DataLookupPluginFactory;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.GroupAttributeVisibility;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.exception.RowSizeTooLargeException;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SaveAttributeAction extends BTransactionAction
/*     */   implements AttributeConstants
/*     */ {
/*     */   private static final String c_ksClassName = "SaveAttributeAction";
/*  63 */   private AttributeManager m_attributeManager = null;
/*  64 */   private ABUserManager m_userManager = null;
/*  65 */   private CategoryManager m_categoryManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  82 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  85 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  87 */       this.m_logger.error("SaveAttributeActionThis user does not have permission to view the admin pages");
/*  88 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  91 */     AttributeForm form = (AttributeForm)a_form;
/*  92 */     boolean bNew = false;
/*     */     try
/*     */     {
/*  97 */       validateMandatoryFields(form, a_request);
/*     */ 
/* 100 */       long attributeTypeId = form.getAttribute().getTypeId();
/* 101 */       if (form.getAttribute().getIsTextarea())
/*     */       {
/* 103 */         if ((StringUtil.stringIsPopulated(form.getMaxDisplayLengthString())) && (!StringUtil.stringIsInteger(form.getMaxDisplayLengthString())))
/*     */         {
/* 105 */           form.addError("You need to either enter a numeric max display length or leave the field blank");
/*     */         }
/* 107 */         else if (StringUtil.stringIsPopulated(form.getMaxDisplayLengthString()))
/*     */         {
/* 110 */           form.getAttribute().setMaxDisplayLength(Integer.parseInt(form.getMaxDisplayLengthString()));
/*     */         }
/*     */ 
/* 113 */         if (form.getAttribute().getWidth() < 0)
/*     */         {
/* 115 */           form.addError("Please enter a numeric value for the 'Columns' field.");
/*     */         }
/*     */ 
/* 118 */         if (form.getAttribute().getHeight() <= 0)
/*     */         {
/* 120 */           form.addError("Please enter a number greater than 0 for the 'Rows' field.");
/*     */         }
/*     */ 
/* 123 */         if (form.getAttribute().getMaxSize() <= 0)
/*     */         {
/* 125 */           form.addError("Please enter a number greater than 0 for the 'Max characters' field");
/*     */         }
/*     */ 
/* 128 */         if ((attributeTypeId == 15L) && (form.getAttribute().getMaxSize() > AssetBankSettings.getMaxVarcharAttributeLength()))
/*     */         {
/* 131 */           form.addError("'Max characters' cannot be more than " + AssetBankSettings.getMaxVarcharAttributeLength() + " for normal text areas.  If you need a larger text area then you can use a 'Text area (long)'.");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 137 */       if (form.getAttribute().getIsAutoincrement())
/*     */       {
/* 139 */         if ((form.getAttribute().getSeed() <= 0L) || (form.getAttribute().getIncrement() <= 0))
/*     */         {
/* 141 */           form.addError("Please enter a valid seed and increment value for this Autoincrement field");
/*     */         }
/*     */ 
/* 144 */         if (form.getAttribute().getPadWidth() < 0)
/*     */         {
/* 146 */           form.addError("Please enter a numeric value for the 'Width for padding' field.");
/*     */         }
/*     */         else
/*     */         {
/* 151 */           form.getAttribute().setWidth(form.getAttribute().getPadWidth());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 157 */       if (form.getAttribute().getIsDataLookupButton())
/*     */       {
/* 160 */         if (!StringUtil.stringIsPopulated(form.getAttribute().getPluginClass()))
/*     */         {
/* 162 */           form.addError("Please enter a plugin class for this external data lookup button.");
/*     */         }
/*     */         else
/*     */         {
/*     */           try
/*     */           {
/* 169 */             DataLookupPluginFactory.getPluginInstance(form.getAttribute().getPluginClass());
/*     */           }
/*     */           catch (PluginInstantiationException e)
/*     */           {
/* 173 */             this.m_logger.error("SaveAttributeAction.execute: Error instantiating attribute plugin class: " + form.getAttribute().getPluginClass());
/* 174 */             form.addError("The entered plugin class doesn't seem to be valid.");
/*     */           }
/*     */         }
/*     */ 
/* 178 */         if (StringUtil.stringIsPopulated(form.getAttribute().getAttributeIdsForPluginParams()))
/*     */         {
/* 181 */           String[] aIds = form.getAttribute().getAttributeIdsForPluginParams().split(",");
/* 182 */           for (String sId : aIds)
/*     */           {
/*     */             try
/*     */             {
/* 186 */               Long.parseLong(sId);
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 190 */               form.addError("One of your attributes ids isn't a valid number.");
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 197 */       if (StringUtils.isNotEmpty(form.getAttribute().getTokenDelimiterRegex()))
/*     */       {
/* 199 */         String regExp = form.getAttribute().getTokenDelimiterRegex();
/*     */ 
/* 201 */         if (regExp.matches("[\\.\\^\\$]+"))
/*     */         {
/* 203 */           form.addError("Please check the Keyword Delimiter as it appears to contain a regular expression that could not be used as a delimiter (see help for details)");
/*     */         }
/*     */         else
/*     */         {
/*     */           try
/*     */           {
/* 209 */             Pattern.compile(regExp);
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 213 */             form.addError("Please check the Keyword Delimiter as it appears to contain an invalid regular expression (see help for details)");
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 219 */       if ((form.getAttribute().getIsHtml()) && (form.getAttribute().isAutoComplete()))
/*     */       {
/* 221 */         form.addError("Sorry, auto complete cannot be enabled for HTML attributes");
/*     */       }
/*     */ 
/* 224 */       if ((form.getAttribute().getIsNumeric()) && (form.getAttribute().getMinDecimalPlaces() > form.getAttribute().getMaxDecimalPlaces()))
/*     */       {
/* 227 */         form.addError("The minimum number of decimal places cannot be more than the maximum.");
/*     */       }
/*     */ 
/* 230 */       if (form.getHasErrors())
/*     */       {
/* 232 */         return a_mapping.findForward("Failure");
/*     */       }
/*     */ 
/* 235 */       if (form.getAttribute().getId() <= 0L)
/*     */       {
/* 239 */         Vector vecAttributePositions = this.m_attributeManager.getAttributePositionList();
/* 240 */         Attribute att = (Attribute)vecAttributePositions.lastElement();
/* 241 */         int iNewSequence = att.getSequence() + 1;
/* 242 */         form.getAttribute().setSequence(iNewSequence);
/* 243 */         bNew = true;
/*     */       }
/*     */ 
/* 246 */       long lId = 0L;
/*     */       try
/*     */       {
/* 249 */         lId = this.m_attributeManager.saveAttribute(a_dbTransaction, form.getAttribute());
/*     */       }
/*     */       catch (RowSizeTooLargeException e)
/*     */       {
/* 253 */         if ((attributeTypeId == 14L) || (attributeTypeId == 15L))
/*     */         {
/* 256 */           String currentTypeName = this.m_attributeManager.getAttributeTypeById(attributeTypeId).getName();
/* 257 */           long tryThisTypeId = attributeTypeId == 14L ? 1L : 2L;
/*     */ 
/* 260 */           String tryThisTypeName = this.m_attributeManager.getAttributeTypeById(tryThisTypeId).getName();
/* 261 */           form.addError("Due to database row size limitations no more " + currentTypeName + " attributes can be added.  " + "Please try adding a " + tryThisTypeName + " attribute instead.");
/*     */ 
/* 263 */           return a_mapping.findForward("Failure");
/*     */         }
/*     */ 
/* 267 */         throw e;
/*     */       }
/*     */ 
/* 272 */       form.getAttribute().setId(lId);
/*     */ 
/* 274 */       if (form.getVisibleToGroupId() > 0L)
/*     */       {
/* 276 */         GroupAttributeVisibility attVisibility = new GroupAttributeVisibility();
/* 277 */         attVisibility.setAttributeId(lId);
/* 278 */         attVisibility.setGroupId(form.getVisibleToGroupId());
/* 279 */         getUserManager().addGroupAttributeVisibility(a_dbTransaction, attVisibility);
/*     */       }
/*     */ 
/* 283 */       if ((bNew) && (form.getAttribute().getIsKeywordPicker()))
/*     */       {
/* 286 */         String sDescription = "Attribute_" + lId;
/* 287 */         long lCategoryTreeId = this.m_categoryManager.addCategoryTree(a_dbTransaction, sDescription, true, true);
/*     */ 
/* 290 */         form.getAttribute().setTreeId(lCategoryTreeId);
/* 291 */         this.m_attributeManager.saveAttribute(a_dbTransaction, form.getAttribute());
/*     */       }
/*     */ 
/* 294 */       return a_mapping.findForward("Success");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 298 */       this.m_logger.error("SaveAttributeAction" + bn2e.getMessage());
/* 299 */     throw bn2e;}
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 306 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/* 312 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   public void setUserManager(ABUserManager a_userManager)
/*     */   {
/* 318 */     this.m_userManager = a_userManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 323 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.SaveAttributeAction
 * JD-Core Version:    0.6.0
 */