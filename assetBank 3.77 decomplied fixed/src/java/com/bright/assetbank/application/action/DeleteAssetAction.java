/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.action.Bn2Action;
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetLogManager;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*     */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.database.service.DBTransactionManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Date;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class DeleteAssetAction extends Bn2Action
/*     */   implements FrameworkConstants
/*     */ {
/*  62 */   private IAssetManager m_assetManager = null;
/*  63 */   private DBTransactionManager m_transactionManager = null;
/*  64 */   private MultiLanguageSearchManager m_searchManager = null;
/*  65 */   private AssetLogManager m_assetLogManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response)
/*     */     throws Bn2Exception
/*     */   {
/*  85 */     ActionForward afForward = null;
/*  86 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  89 */     if (!userProfile.getIsLoggedIn())
/*     */     {
/*  91 */       this.m_logger.debug("This user does not have permission to view the admin page");
/*  92 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  96 */     long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  98 */     if (lAssetId > 0L)
/*     */     {
/* 101 */       DBTransaction dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */       try
/*     */       {
/* 105 */         Asset asset = this.m_assetManager.getAsset(dbTransaction, lAssetId, null, true, false);
/*     */         ActionForward localActionForward1;
/* 108 */         if ((!userProfile.getIsAdmin()) && (!this.m_assetManager.userCanDeleteAsset(userProfile, asset)))
/*     */         {
/* 110 */           this.m_logger.debug("This user does not have permission to delete asset id=" + lAssetId);
/* 111 */           localActionForward1 = a_mapping.findForward("NoPermission"); //jsr 213;
/*     */         }
/*     */ 
/* 115 */         if ((asset.getCurrentVersionId() > 0L) && (!AssetBankSettings.getCanEditPreviousAssetVersions()))
/*     */         {
/* 117 */           this.m_logger.debug("SaveAssetACtion.execute() : Cannot edit previous asset versions");
/* 118 */           localActionForward1 = a_mapping.findForward("SystemFailure"); //jsr 172;
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 124 */           dbTransaction.commit();
/* 125 */           dbTransaction = this.m_transactionManager.getNewTransaction();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 129 */           throw new Bn2Exception(sqle.getMessage(), sqle);
/*     */         }
/*     */ 
/* 133 */         if (AssetBankSettings.getAuditLogEnabled())
/*     */         {
/* 135 */           this.m_assetLogManager.saveLog(asset.getId(), asset.getFileName(), dbTransaction, userProfile.getUser().getId(), new Date(), 3L, userProfile.getSessionId(), asset.getVersionNumber());
/*     */         }
/*     */ 
/* 139 */         this.m_assetManager.deleteAsset(dbTransaction, lAssetId);
/*     */ 
/* 144 */         if (userProfile.getAssetBox().containsAsset(lAssetId))
/*     */         {
/* 146 */           userProfile.getAssetBox().removeAsset(lAssetId);
/*     */         }
/*     */       }
/*     */       catch (Bn2Exception bn2e)
/*     */       {
/*     */         try
/*     */         {
/* 153 */           dbTransaction.rollback();
/*     */         }
/*     */         catch (SQLException se)
/*     */         {
/*     */         }
/*     */ 
/* 160 */         this.m_logger.error("Exception in DeleteAssetAction:", bn2e);
/* 161 */         throw bn2e;
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 168 */           dbTransaction.commit();
/*     */         }
/*     */         catch (SQLException sqle)
/*     */         {
/* 172 */           this.m_logger.error("Exception commiting transaction in AddAssetAction:", sqle);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 177 */     int iUnsubmitted = getIntParameter(a_request, "unsubmitted");
/* 178 */     long lUnsubmittedAdmin = getLongParameter(a_request, "unsubmittedAdmin");
/*     */ 
/* 180 */     if (iUnsubmitted > 0)
/*     */     {
/* 182 */       return createRedirectingForward("", a_mapping, "Unsubmitted");
/*     */     }
/* 184 */     if (lUnsubmittedAdmin > 0L)
/*     */     {
/* 186 */       return createRedirectingForward("selectedUserId=" + lUnsubmittedAdmin, a_mapping, "UnsubmittedAdmin");
/*     */     }
/*     */ 
/* 190 */     afForward = a_mapping.findForward("Success");
/*     */ 
/* 192 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setDBTransactionManager(DBTransactionManager a_transactionManager)
/*     */   {
/* 207 */     this.m_transactionManager = a_transactionManager;
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 213 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 219 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public MultiLanguageSearchManager getSearchManager()
/*     */   {
/* 225 */     return this.m_searchManager;
/*     */   }
/*     */ 
/*     */   public void setSearchManager(MultiLanguageSearchManager a_sSearchManager)
/*     */   {
/* 231 */     this.m_searchManager = a_sSearchManager;
/*     */   }
/*     */ 
/*     */   public void setAssetLogManager(AssetLogManager a_assetLogManager)
/*     */   {
/* 236 */     this.m_assetLogManager = a_assetLogManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DeleteAssetAction
 * JD-Core Version:    0.6.0
 */