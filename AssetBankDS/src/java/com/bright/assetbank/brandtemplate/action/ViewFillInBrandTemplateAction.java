/*    */ package com.bright.assetbank.brandtemplate.action;
/*    */ 
/*    */ import com.bn2web.common.action.Bn2Action;
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
/*    */ import com.bright.assetbank.brandtemplate.bean.TemplateVariables;
/*    */ import com.bright.assetbank.brandtemplate.service.BrandTemplateManager;
/*    */ import com.bright.assetbank.user.bean.ABUser;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewFillInBrandTemplateAction extends Bn2Action
/*    */ {
/*    */   private IAssetManager m_assetManager;
/* 47 */   private BrandTemplateManager m_brandTemplateManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*    */     throws Bn2Exception
/*    */   {
/* 56 */     long lAssetId = getIntParameter(a_request, "assetId");
/*    */ 
/* 58 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 60 */     Asset asset = this.m_assetManager.getAsset(null, lAssetId, userProfile.getVisibleAttributeIds(), false, false);
/*    */ 
/* 63 */     if (!this.m_assetManager.userCanDownloadAsset(userProfile, asset))
/*    */     {
/* 65 */       this.m_logger.debug("This user does not have permission to view asset id=" + lAssetId);
/* 66 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 70 */     TemplateVariables templateVariables = new TemplateVariables((ABUser)userProfile.getUser());
/* 71 */     List templateFields = this.m_brandTemplateManager.findFieldsInAssetAndReplacePlaceholders(asset, templateVariables);
/*    */ 
/* 73 */     a_request.setAttribute("asset", asset);
/* 74 */     a_request.setAttribute("templateFields", templateFields);
/*    */ 
/* 76 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public IAssetManager getAssetManager()
/*    */   {
/* 81 */     return this.m_assetManager;
/*    */   }
/*    */ 
/*    */   public void setAssetManager(IAssetManager a_assetManager)
/*    */   {
/* 86 */     this.m_assetManager = a_assetManager;
/*    */   }
/*    */ 
/*    */   public BrandTemplateManager getBrandTemplateManager()
/*    */   {
/* 91 */     return this.m_brandTemplateManager;
/*    */   }
/*    */ 
/*    */   public void setBrandTemplateManager(BrandTemplateManager a_brandTemplateManager)
/*    */   {
/* 96 */     this.m_brandTemplateManager = a_brandTemplateManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.brandtemplate.action.ViewFillInBrandTemplateAction
 * JD-Core Version:    0.6.0
 */