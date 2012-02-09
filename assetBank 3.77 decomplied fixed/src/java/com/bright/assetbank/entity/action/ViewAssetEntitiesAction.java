/*    */ package com.bright.assetbank.entity.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.entity.form.AssetEntityForm;
/*    */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAssetEntitiesAction extends AssetEntityAction
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     AssetEntityForm form = (AssetEntityForm)a_form;
/* 55 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 58 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 60 */       this.m_logger.error("ViewAssetEntitiesAction.execute : User must be an administrator.");
/* 61 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 65 */     form.setEntities(getAssetEntityManager().getAllEntities(a_dbTransaction));
/*    */ 
/* 67 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.action.ViewAssetEntitiesAction
 * JD-Core Version:    0.6.0
 */