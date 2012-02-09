/*     */ package com.bright.assetbank.user.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.orgunit.service.OrgUnitManager;
/*     */ import com.bright.assetbank.user.bean.ABUser;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.assetbank.user.bean.Group;
/*     */ import com.bright.assetbank.user.service.ABUserManager;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.user.form.ListUsersForm;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewRecentlyRegisteredAction extends UserAction
/*     */ {
/*  47 */   private OrgUnitManager m_orgUnitManager = null;
/*     */ 
/*  49 */   public void setOrgUnitManager(OrgUnitManager a_orgUnitManager) { this.m_orgUnitManager = a_orgUnitManager;
/*     */   }
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  76 */     ActionForward afForward = null;
/*  77 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  78 */     ListUsersForm usersForm = (ListUsersForm)a_form;
/*     */ 
/*  81 */     if ((!userProfile.getIsAdmin()) && (!userProfile.getIsOrgUnitAdmin()))
/*     */     {
/*  83 */       this.m_logger.error("ViewRecentlyRegisteredAction.execute : User does not have admin permission : " + userProfile);
/*  84 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  88 */     Vector vecUsers = getUserManager().getRecentlyRegisteredUsers(a_dbTransaction);
/*     */ 
/*  90 */     if (userProfile.getIsAdmin())
/*     */     {
/*  92 */       usersForm.setUsers(vecUsers);
/*     */     }
/*     */     else
/*     */     {
/*  97 */       ABUser adminUser = (ABUser)userProfile.getUser();
/*  98 */       Vector vecGroups = this.m_orgUnitManager.getOrgUnitAdminUserManagedGroups(a_dbTransaction, adminUser.getId());
/*  99 */       Vector vecFiltered = new Vector();
/*     */ 
/* 101 */       if (vecUsers != null)
/*     */       {
/* 103 */         Iterator itUsers = vecUsers.iterator();
/* 104 */         while (itUsers.hasNext())
/*     */         {
/* 106 */           ABUser regUser = (ABUser)itUsers.next();
/* 107 */           boolean bIsManaged = false;
/* 108 */           Iterator itGroups = vecGroups.iterator();
/* 109 */           while (itGroups.hasNext())
/*     */           {
/* 111 */             Group group = (Group)itGroups.next();
/* 112 */             if (regUser.getIsInGroup(new Long(group.getId()).intValue()))
/*     */             {
/* 114 */               bIsManaged = true;
/* 115 */               break;
/*     */             }
/*     */           }
/*     */ 
/* 119 */           if (bIsManaged)
/*     */           {
/* 121 */             vecFiltered.add(regUser);
/*     */           }
/*     */         }
/*     */       }
/* 125 */       usersForm.setUsers(vecFiltered);
/*     */     }
/*     */ 
/* 128 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 130 */     return afForward;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.user.action.ViewRecentlyRegisteredAction
 * JD-Core Version:    0.6.0
 */