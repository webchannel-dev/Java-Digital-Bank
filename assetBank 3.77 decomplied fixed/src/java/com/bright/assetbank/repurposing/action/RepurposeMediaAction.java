/*     */ package com.bright.assetbank.repurposing.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.form.DownloadForm;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.repurposing.bean.RepurposedAsset;
/*     */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.constant.FrameworkConstants;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.service.FileStoreManager;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.FileUtil;
/*     */ import java.io.File;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class RepurposeMediaAction extends BTransactionAction
/*     */   implements AssetBankConstants, FrameworkConstants
/*     */ {
/*     */   private FileStoreManager m_fileStoreManager;
/*     */   private AssetRepurposingManager m_assetRepurposingManager;
/*     */   private AssetManager m_assetManager;
/* 103 */   protected ListManager m_listManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  62 */     DownloadForm form = (DownloadForm)a_form;
/*  63 */     String sFileId = (String)a_request.getAttribute("downloadFile");
/*  64 */     String sAbsFilePath = this.m_fileStoreManager.getAbsolutePath(sFileId);
/*  65 */     ABUserProfile profile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  67 */     if ((!profile.getIsLoggedIn()) && (!profile.getUserCanRepurposeAssets()))
/*     */     {
/*  69 */       this.m_logger.debug("This user does not have permission to repurpose images");
/*  70 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  73 */     long lId = getLongParameter(a_request, "id");
/*     */ 
/*  75 */     if (lId <= 0L)
/*     */     {
/*  77 */       lId = form.getAsset().getId();
/*     */     }
/*  79 */     Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lId, null, false, false);
/*  80 */     RepurposedAsset version = this.m_assetRepurposingManager.addRepurposedAsset(a_dbTransaction, asset, profile.getUser().getId(), sFileId);
/*     */ 
/*  82 */     if (Boolean.TRUE.equals(a_request.getAttribute("deleteFileAfterUse")))
/*     */     {
/*  84 */       File tempFile = new File(sAbsFilePath);
/*  85 */       tempFile.delete();
/*  86 */       FileUtil.logFileDeletion(tempFile);
/*     */     }
/*     */ 
/*  89 */     String sQueryString = "id=" + version.getId() + "&" + "assetId" + "=" + lId + "&" + "new" + "=" + Boolean.toString(true);
/*     */ 
/*  91 */     if (a_request.getParameter("parentId") != null)
/*     */     {
/*  93 */       sQueryString = sQueryString + "&parentId=" + a_request.getParameter("parentId");
/*     */     }
/*     */ 
/*  96 */     return createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */   }
/*     */ 
/*     */   public void setFileStoreManager(FileStoreManager a_fileStoreManager)
/*     */   {
/* 101 */     this.m_fileStoreManager = a_fileStoreManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 106 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setAssetRepurposingManager(AssetRepurposingManager assetRepurposingManager)
/*     */   {
/* 111 */     this.m_assetRepurposingManager = assetRepurposingManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_assetManager)
/*     */   {
/* 116 */     this.m_assetManager = a_assetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.RepurposeMediaAction
 * JD-Core Version:    0.6.0
 */