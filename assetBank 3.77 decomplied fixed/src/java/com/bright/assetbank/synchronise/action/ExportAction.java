/*     */ package com.bright.assetbank.synchronise.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*     */ import com.bright.assetbank.application.service.AssetManager;
/*     */ import com.bright.assetbank.synchronise.bean.ExportInfo;
/*     */ import com.bright.assetbank.synchronise.form.ExportForm;
/*     */ import com.bright.assetbank.synchronise.service.AssetExportManager;
/*     */ import com.bright.assetbank.usage.service.UsageManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.simplelist.bean.ListItem;
/*     */ import com.bright.framework.simplelist.service.ListManager;
/*     */ import com.bright.framework.user.bean.User;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import com.bright.framework.util.StringUtil;
/*     */ import java.util.Iterator;
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
/*  59 */   private AssetExportManager m_exportManager = null;
/*     */ 
/*  61 */   protected ListManager m_listManager = null;
/*  62 */   protected UsageManager m_usageManager = null;
/*  63 */   protected AssetManager m_assetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  80 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*  81 */     ExportForm form = (ExportForm)a_form;
/*     */ 
/*  83 */     if ((!userProfile.getIsAdmin()) && ((!AssetBankSettings.getOrgUnitAdminsCanExport()) || (!userProfile.getIsOrgUnitAdmin())))
/*     */     {
/*  85 */       this.m_logger.error("ExportFromSearchAction.execute : User does not have admin permission : " + userProfile);
/*  86 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  90 */     Vector vIds = (Vector)a_request.getSession().getAttribute("ExportAssetIds");
/*     */ 
/*  93 */     if ((vIds == null) || (vIds.size() == 0))
/*     */     {
/*  95 */       form.addError(this.m_listManager.getListItem(a_dbTransaction, "noAssetsSelectedForExport", userProfile.getCurrentLanguage()).getBody());
/*     */     }
/*     */ 
/*  99 */     validateMandatoryFields(form, a_request);
/*     */ 
/* 101 */     if (form.getHasErrors())
/*     */     {
/* 103 */       return a_mapping.findForward("Failure");
/*     */     }
/*     */ 
/* 109 */     if ((!userProfile.getIsAdmin()) && (form.isExportingAssetFiles()))
/*     */     {
/* 111 */       Vector checkIds = (Vector)vIds.clone();
/*     */ 
/* 113 */       Iterator itCheckIds = checkIds.iterator();
/*     */ 
/* 115 */       while (itCheckIds.hasNext())
/*     */       {
/* 117 */         long lId = ((Long)itCheckIds.next()).longValue();
/*     */ 
/* 119 */         Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lId);
/*     */ 
/* 122 */         if (!this.m_assetManager.userCanDownloadOriginal(userProfile, asset))
/*     */         {
/* 124 */           vIds.remove(new Long(lId));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 131 */     boolean[] exportFiles = new boolean[vIds.size()];
/* 132 */     for (int i = 0; i < vIds.size(); i++)
/*     */     {
/* 134 */       exportFiles[i] = form.isExportingAssetFiles();
/*     */     }
/*     */ 
/* 138 */     String sFilename = form.getName();
/* 139 */     if (StringUtil.stringIsPopulated(form.getAppendToFilename()))
/*     */     {
/* 141 */       sFilename = sFilename + "-" + form.getAppendToFilename();
/*     */     }
/*     */ 
/* 144 */     ExportInfo info = new ExportInfo();
/* 145 */     info.setAssetIds(vIds);
/* 146 */     info.setName(sFilename);
/* 147 */     info.setDescription(form.getDescription());
/* 148 */     info.setExportFiles(exportFiles);
/* 149 */     info.setDataOnly(!form.isExportingAssetFiles());
/* 150 */     info.setExportFilesInZips(form.getExportFilesInZips());
/* 151 */     info.setAllLanguages(form.getExportAllLanguages());
/* 152 */     info.setUser(userProfile.getUser());
/* 153 */     getExportManager().queueImport(info);
/*     */ 
/* 156 */     for (int i = 0; i < vIds.size(); i++)
/*     */     {
/* 158 */      // if (exportFiles[i] == 0)
                 if (exportFiles[i] == false)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 163 */       Long lgId = (Long)vIds.get(i);
/* 164 */       long lId = lgId.longValue();
/*     */ 
/* 166 */       this.m_usageManager.logAssetUseAsynchronously(lId, userProfile.getUser().getId(), 0L, "", 4L, userProfile.getSessionId(), null);
/*     */     }
/*     */ 
/* 176 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public AssetExportManager getExportManager()
/*     */   {
/* 181 */     return this.m_exportManager;
/*     */   }
/*     */ 
/*     */   public void setExportManager(AssetExportManager a_sExportManager)
/*     */   {
/* 186 */     this.m_exportManager = a_sExportManager;
/*     */   }
/*     */ 
/*     */   public void setListManager(ListManager listManager)
/*     */   {
/* 192 */     this.m_listManager = listManager;
/*     */   }
/*     */ 
/*     */   public void setUsageManager(UsageManager a_usageManager)
/*     */   {
/* 197 */     this.m_usageManager = a_usageManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(AssetManager a_sAssetManager)
/*     */   {
/* 202 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.synchronise.action.ExportAction
 * JD-Core Version:    0.6.0
 */