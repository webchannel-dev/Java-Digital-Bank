/*    */ package com.bright.assetbank.publishing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.publishing.form.PublishingForm;
/*    */ import com.bright.assetbank.publishing.service.PublishingManager;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.category.bean.Category;
/*    */ import com.bright.framework.category.service.CategoryManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewPublishingAction extends BTransactionAction
/*    */ {
/* 28 */   private CategoryManager m_categoryManager = null;
/* 29 */   private PublishingManager m_publishingManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 37 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 38 */     PublishingForm form = (PublishingForm)a_form;
/*    */ 
/* 41 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 43 */       this.m_logger.debug("This user does not have permission to view the admin page");
/* 44 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 47 */     List publishActions = this.m_publishingManager.loadPublishActions(a_dbTransaction);
/* 48 */     form.setPublishingActions(publishActions);
/*    */ 
/* 50 */     List <Category>accesLevelsList = this.m_categoryManager.getAccessLevels(a_dbTransaction);
/* 51 */     Map accessLevelsMap = new HashMap();
/* 52 */     for (Category category : accesLevelsList) {
/* 53 */       accessLevelsMap.put(Long.valueOf(category.getId()), category);
/*    */     }
/* 55 */     a_request.setAttribute("accessLevelsMap", accessLevelsMap);
/* 56 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setPublishingManager(PublishingManager a_publishingManager)
/*    */   {
/* 62 */     this.m_publishingManager = a_publishingManager;
/*    */   }
/*    */ 
/*    */   public void setCategoryManager(CategoryManager a_categoryManager)
/*    */   {
/* 67 */     this.m_categoryManager = a_categoryManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.publishing.action.ViewPublishingAction
 * JD-Core Version:    0.6.0
 */