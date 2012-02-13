/*    */ package com.bright.assetbank.customfield.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.customfield.form.CustomFieldForm;
/*    */ import com.bright.assetbank.orgunit.bean.OrgUnitSearchCriteria;
/*    */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewCustomFieldAction extends com.bright.framework.customfield.action.ViewCustomFieldAction
/*    */ {
/* 45 */   private OrgUnitManager m_orgUnitManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_transaction)
/*    */     throws Bn2Exception
/*    */   {
/* 71 */     UserProfile userProfile = UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 74 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 76 */       this.m_logger.error("This user does not have permission to view the admin pages");
/* 77 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 81 */     CustomFieldForm form = (CustomFieldForm)a_form;
/* 82 */     OrgUnitSearchCriteria search = new OrgUnitSearchCriteria();
/* 83 */     Vector vecOrgUnits = this.m_orgUnitManager.getOrgUnitList(a_transaction, search, false);
/* 84 */     form.setOrgUnits(vecOrgUnits);
/*    */ 
/* 86 */     return super.execute(a_mapping, a_form, a_request, a_response, a_transaction);
/*    */   }
/*    */ 
/*    */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager)
/*    */   {
/* 91 */     this.m_orgUnitManager = a_orgUnitManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.customfield.action.ViewCustomFieldAction
 * JD-Core Version:    0.6.0
 */