/*     */ package com.bright.assetbank.publishing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.publishing.bean.PublishingAction;
/*     */ import com.bright.assetbank.publishing.form.PublishingForm;
/*     */ import com.bright.assetbank.publishing.service.PublishingManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.category.service.CategoryManager;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewAddPublishingAction extends BTransactionAction
/*     */ {
/*  29 */   private CategoryManager m_categoryManager = null;
/*  30 */   private PublishingManager m_publishingManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  38 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  39 */     PublishingForm form = (PublishingForm)a_form;
/*     */ 
/*  42 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  44 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  45 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  49 */     if (a_request.getMethod().equals("POST"))
/*     */     {
/*  52 */       validateMandatoryFields(form, a_request);
/*     */ 
/*  55 */       if ((form.getPath() != null) && (form.getPath().length() > 0)) {
/*  56 */         String baseName = "temp.dat";
/*  57 */         String fileName = form.getPath() + File.separatorChar + baseName;
/*     */         try {
/*  59 */           BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
/*  60 */           out.write("aString");
/*  61 */           out.close();
/*     */         } catch (IOException e) {
/*  63 */           form.addError("Could not create temporary file '" + baseName + "' in publish directory '" + form.getPath() + "'. <strong>Error: " + e.getMessage() + "</strong>");
/*     */         }
/*     */         try {
/*  66 */           File temp = new File(fileName);
/*  67 */           temp.delete();
/*     */         } catch (SecurityException e) {
/*  69 */           form.addError("Could not delete temporary file '" + baseName + "' in publish directory '" + form.getPath() + "'. <strong>Error: " + e.getMessage() + "</strong>");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  74 */       if (!form.getHasErrors()) {
/*  75 */         PublishingAction newPublishingAction = form.toPublishingAction();
/*  76 */         this.m_publishingManager.savePublishAction(a_dbTransaction, newPublishingAction);
/*  77 */         return createRedirectingForward("/action/viewPublishing");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  82 */       Long id = Long.valueOf(getLongParameter(a_request, "publishingActionId"));
/*  83 */       if (id.longValue() > 0L)
/*     */       {
/*  85 */         PublishingAction bean = this.m_publishingManager.loadPublishAction(a_dbTransaction, id.longValue());
/*  86 */         form.fromPublishingAction(bean);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  91 */     form.setAccessLevelsList(this.m_categoryManager.getAccessLevels(a_dbTransaction));
/*  92 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public void setPublishingManager(PublishingManager a_publishingManager)
/*     */   {
/*  98 */     this.m_publishingManager = a_publishingManager;
/*     */   }
/*     */ 
/*     */   public void setCategoryManager(CategoryManager a_categoryManager)
/*     */   {
/* 103 */     this.m_categoryManager = a_categoryManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.publishing.action.ViewAddPublishingAction
 * JD-Core Version:    0.6.0
 */