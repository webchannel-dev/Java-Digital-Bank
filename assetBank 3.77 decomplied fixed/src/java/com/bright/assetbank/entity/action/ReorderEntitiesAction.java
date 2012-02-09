/*    */ package com.bright.assetbank.entity.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.entity.bean.AssetEntity;
/*    */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Collections;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ReorderEntitiesAction extends AssetEntityAction
/*    */   implements AssetBankConstants
/*    */ {
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 53 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 56 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*    */     {
/* 58 */       this.m_logger.error("DeleteAssetEntityAction.execute : User must be an administrator.");
/* 59 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 62 */     boolean bUp = Boolean.parseBoolean(a_request.getParameter("up"));
/* 63 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 66 */     Vector entities = getAssetEntityManager().getAllEntities(a_dbTransaction);
/*    */ 
/* 69 */     int increment = bUp ? -1 : 1;
/* 70 */     for (int i = 0; i < entities.size(); i++)
/*    */     {
/* 72 */       AssetEntity entity = (AssetEntity)entities.get(i);
/* 73 */       if ((entity.getId() != lId) || (i + increment < 0) || (i + increment >= entities.size()))
/*    */         continue;
/* 75 */       Collections.swap(entities, i, i + increment);
/* 76 */       break;
/*    */     }
/*    */ 
/* 81 */     getAssetEntityManager().reorderEntities(a_dbTransaction, entities);
/*    */ 
/* 84 */     a_request.getSession().getServletContext().setAttribute("quickSearchEntities", getAssetEntityManager().getAllQuickSearchableEntities(a_dbTransaction));
/*    */ 
/* 86 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.action.ReorderEntitiesAction
 * JD-Core Version:    0.6.0
 */