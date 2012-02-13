/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.synchronise.bean.ExportResult;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.form.UserForm;
/*     */ import com.bright.assetbank.user.service.UserExportManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ExportAction extends BTransactionAction
/*     */   implements AssetBankConstants
/*     */ {
/*  49 */   private UserExportManager m_exportManager = null;
/*  50 */   private ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  70 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  71 */     UserForm form = (UserForm)a_form;
/*     */ 
/*  73 */     if ((!userProfile.getIsAdmin()) && (!userProfile.checkForRolePermission()))
/*     */     {
/*  75 */       this.m_logger.error("ExportAction.execute : User does not have admin permission : " + userProfile);
/*  76 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  80 */     Vector vecLastUserSearch = userProfile.getLastUserSearch();
/*  81 */     Vector vecIds = null;
/*  82 */     if (vecLastUserSearch != null)
/*     */     {
/*  84 */       vecIds = new Vector();
/*  85 */       for (int i = 0; i < vecLastUserSearch.size(); i++)
/*     */       {
/*  87 */         vecIds.add(new Long(((ABUser)(ABUser)vecLastUserSearch.elementAt(i)).getId()));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  92 */     if ((vecIds == null) || (vecIds.size() == 0))
/*     */     {
/*  94 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "noUsersSelectedForExport", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  97 */     ExportResult result = this.m_exportManager.exportUsers(a_dbTransaction, vecIds);
/*  98 */     a_request.getSession().setAttribute("ExportResult", result);
/*     */ 
/* 100 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setExportManager(UserExportManager a_sExportManager)
/*     */   {
/* 105 */     this.m_exportManager = a_sExportManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager a_listManager)
/*     */   {
/* 110 */     this.m_listManager = a_listManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ExportAction
 * JD-Core Version:    0.6.0
 */