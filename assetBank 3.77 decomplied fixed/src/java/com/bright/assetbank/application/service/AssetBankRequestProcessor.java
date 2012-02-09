/*     */ package com.bright.assetbank.application.service;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bn2web.common.service.Bn2RequestProcessor;
/*     */ import com.bn2web.common.service.GlobalApplication;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.customfield.service.CustomFieldManager;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Brand;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.assetbank.user.util.UserUtil;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.avalon.framework.component.ComponentException;
/*     */ import org.apache.avalon.framework.component.ComponentManager;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.Action;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class AssetBankRequestProcessor extends Bn2RequestProcessor
/*     */   implements AssetBankConstants
/*     */ {
/*  71 */   private ABUserManager m_userManager = null;
/*  72 */   private AssetEntityManager m_assetEntityManager = null;
/*     */ 
/*     */   protected ActionForward processActionPerform(HttpServletRequest request, HttpServletResponse response, Action action, ActionForm formInstance, ActionMapping mapping)
/*     */     throws IOException, ServletException
/*     */   {
/* 104 */     ActionForward forward = super.processActionPerform(request, response, action, formInstance, mapping);
/*     */ 
/* 109 */     if (forward == null)
/*     */     {
/* 111 */       throw new RuntimeException("ActionForward is null in processActionPerform.AssetBankRequestProcessor. Action class is: " + action.getClass().getName());
/*     */     }
/*     */ 
/* 114 */     return forward;
/*     */   }
/*     */ 
/*     */   protected ActionForward preProcessActionPerform(HttpServletRequest request, HttpServletResponse response, Action action, ActionForm formInstance, ActionMapping mapping)
/*     */     throws IOException, ServletException
/*     */   {
/* 145 */     ActionForward forward = getForwardFromFilter(request, mapping);
/* 146 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(request);
/*     */ 
/* 149 */     if ((AssetBankSettings.getUseBrands()) && (!userProfile.getIsLoggedIn()))
/*     */     {
/* 152 */       String sBrandParam = AssetBankSettings.getBrandParameter();
/* 153 */       String sBrand = request.getParameter(sBrandParam);
/*     */ 
/* 156 */       String sOrigBrand = StringUtil.getEmptyStringIfNull(userProfile.getBrand().getName());
/* 157 */       if ((StringUtil.stringIsPopulated(sBrand)) && (sBrand.compareToIgnoreCase(sOrigBrand) != 0))
/*     */       {
/*     */         try
/*     */         {
/* 161 */           Brand brand = getUserManager().getBrandByCode(sBrand);
/* 162 */           userProfile.setBrand(brand);
/*     */         }
/*     */         catch (Bn2Exception bn2e)
/*     */         {
/* 166 */           GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst trying to load brand for user: " + bn2e.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 171 */     ServletContext context = request.getSession().getServletContext();
/*     */     try
/*     */     {
/* 176 */       if ((AssetBankSettings.getAssetEntitiesEnabled()) && (context.getAttribute("quickSearchEntities") == null))
/*     */       {
/* 178 */         context.setAttribute("quickSearchEntities", getAssetEntityManager().getAllQuickSearchableEntities(null));
/*     */       }
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 183 */       GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst trying to set asset entities for quick search: " + bn2e.getMessage(), bn2e);
/*     */     }
/*     */ 
/* 187 */     if (context.getAttribute("userApprovalEnabled") == null)
/*     */     {
/*     */       try
/*     */       {
/* 191 */         CustomFieldManager customFieldManager = (CustomFieldManager)GlobalApplication.getInstance().getComponentManager().lookup("CustomFieldManager");
/* 192 */         boolean bUserApproval = UserUtil.isUserApprovalEnabled(customFieldManager);
/* 193 */         context.setAttribute("userApprovalEnabled", Boolean.valueOf(bUserApproval));
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/* 197 */         GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: ComponentException whilst getting user manager component : " + ce);
/*     */       }
/*     */       catch (Bn2Exception bn2e)
/*     */       {
/* 201 */         GlobalApplication.getInstance().getLogger().error("preProcessActionPerform: Bn2Exception whilst getting user approval status : " + bn2e.getMessage());
/*     */       }
/*     */     }
/*     */ 
/* 205 */     return forward;
/*     */   }
/*     */ 
/*     */   protected static ActionForward createRedirectingForward(String a_sQueryString, ActionMapping a_mapping, String a_sKey)
/*     */   {
/* 212 */     ActionForward originalForward = a_mapping.findForward(a_sKey);
/* 213 */     String sPath = originalForward.getPath();
/* 214 */     if (a_sQueryString.length() > 0)
/*     */     {
/* 216 */       sPath = sPath + "?" + a_sQueryString;
/*     */     }
/*     */ 
/* 220 */     ActionForward forward = new ActionForward(sPath);
/* 221 */     forward.setRedirect(true);
/* 222 */     return forward;
/*     */   }
/*     */ 
/*     */   public ABUserManager getUserManager()
/*     */   {
/* 230 */     if (this.m_userManager == null)
/*     */     {
/*     */       try
/*     */       {
/* 234 */         this.m_userManager = ((ABUserManager)GlobalApplication.getInstance().getComponentManager().lookup("UserManager"));
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/* 238 */         GlobalApplication.getInstance().getLogger().error("ComponentException whilst getting user manager component : " + ce);
/*     */       }
/*     */     }
/*     */ 
/* 242 */     return this.m_userManager;
/*     */   }
/*     */ 
/*     */   private ActionForward getForwardFromFilter(HttpServletRequest a_request, ActionMapping a_mapping)
/*     */   {
/* 248 */     String sKey = (String)a_request.getAttribute("forwardKey");
/* 249 */     ActionForward forward = null;
/* 250 */     if (StringUtil.stringIsPopulated(sKey))
/*     */     {
/* 252 */       if ((a_request.getAttribute("redirecting") != null) && (((Boolean)a_request.getAttribute("redirecting")).booleanValue()))
/*     */       {
/* 255 */         String sQueryString = (String)a_request.getAttribute("queryString");
/* 256 */         forward = createRedirectingForward(sQueryString, a_mapping, sKey);
/*     */       }
/*     */       else
/*     */       {
/* 260 */         forward = a_mapping.findForward(sKey);
/*     */       }
/*     */     }
/* 263 */     a_request.removeAttribute("forwardKey");
/* 264 */     a_request.removeAttribute("redirecting");
/* 265 */     a_request.removeAttribute("queryString");
/* 266 */     return forward;
/*     */   }
/*     */ 
/*     */   public AssetEntityManager getAssetEntityManager()
/*     */   {
/* 272 */     if (this.m_assetEntityManager == null)
/*     */     {
/*     */       try
/*     */       {
/* 276 */         this.m_assetEntityManager = ((AssetEntityManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetEntityManager"));
/*     */       }
/*     */       catch (ComponentException ce)
/*     */       {
/* 280 */         GlobalApplication.getInstance().getLogger().error("ComponentException whilst getting asset entity manager component : " + ce);
/*     */       }
/*     */     }
/*     */ 
/* 284 */     return this.m_assetEntityManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.service.AssetBankRequestProcessor
 * JD-Core Version:    0.6.0
 */