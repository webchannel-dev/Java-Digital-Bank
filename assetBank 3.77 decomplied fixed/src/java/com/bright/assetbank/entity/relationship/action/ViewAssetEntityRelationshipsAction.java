/*    */ package com.bright.assetbank.entity.relationship.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.entity.action.AssetEntityAction;
/*    */ import com.bright.assetbank.entity.bean.AssetEntity;
/*    */ import com.bright.assetbank.entity.form.AssetEntityForm;
/*    */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*    */ import com.bright.assetbank.language.service.LanguageManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewAssetEntityRelationshipsAction extends AssetEntityAction
/*    */ {
/* 46 */   private LanguageManager m_languageManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 55 */     AssetEntityForm form = (AssetEntityForm)a_form;
/*    */ 
/* 57 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 60 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 62 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 65 */     long lId = getLongParameter(a_request, "id");
/*    */ 
/* 68 */     Vector vecEntities = getAssetEntityManager().getAllEntities(a_dbTransaction);
/* 69 */     form.setEntities(vecEntities);
/*    */ 
/* 71 */     if (!form.getHasErrors())
/*    */     {
/* 73 */       AssetEntity entity = getAssetEntityManager().getEntity(a_dbTransaction, lId);
/* 74 */       form.setEntity(entity);
/*    */ 
/* 76 */       form.setSelectedPeerEntities(entity.getPeerRelationshipIds());
/* 77 */       form.setSelectedChildEntities(entity.getChildRelationshipIds());
/*    */ 
/* 80 */       if (form.getEntity().getTranslations().size() == 0)
/*    */       {
/* 82 */         this.m_languageManager.createEmptyTranslations(a_dbTransaction, form.getEntity());
/*    */       }
/*    */     }
/*    */ 
/* 86 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setLanguageManager(LanguageManager languageManager)
/*    */   {
/* 91 */     this.m_languageManager = languageManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.entity.relationship.action.ViewAssetEntityRelationshipsAction
 * JD-Core Version:    0.6.0
 */