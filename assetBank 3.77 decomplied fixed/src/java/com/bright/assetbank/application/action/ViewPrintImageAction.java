/*     */ package com.bright.assetbank.application.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.bean.Asset;
/*     */ import com.bright.assetbank.application.form.PrintImageForm;
/*     */ import com.bright.assetbank.application.service.IAssetManager;
/*     */ import com.bright.assetbank.entity.service.AssetEntityManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.language.util.LanguageUtils;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ViewPrintImageAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "ViewPrintImageAction";
/*  49 */   private IAssetManager m_assetManager = null;
/*  50 */   protected AssetEntityManager m_assetEntityManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  75 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     PrintImageForm form = (PrintImageForm)a_form;
/*     */     try
/*     */     {
/*  81 */       long lAssetId = getLongParameter(a_request, "id");
/*     */ 
/*  83 */       form.getAsset().setId(lAssetId);
/*     */ 
/*  85 */       if (lAssetId <= 0L)
/*     */       {
/*  87 */         throw new Bn2Exception("ViewPrintImageAction : no asset id passed");
/*     */       }
/*     */ 
/*  90 */       Asset asset = this.m_assetManager.getAsset(a_dbTransaction, lAssetId, userProfile.getVisibleAttributeIds(), false, false);
/*     */ 
/*  92 */       Vector attIds = userProfile.getVisibleAttributeIds();
/*     */ 
/*  95 */       form.setAttributeList(this.m_assetManager.getAssetAttributes(a_dbTransaction, attIds));
/*  96 */       LanguageUtils.setLanguageOnAll(form.getAttributeList(), userProfile.getCurrentLanguage());
/*     */     }
/*     */     catch (Bn2Exception bn2)
/*     */     {
/* 101 */       this.m_logger.error("ViewPrintImageAction exception: " + bn2.getMessage());
/* 102 */       throw bn2;
/*     */     }
/*     */ 
/* 105 */     return a_mapping.findForward("Success");
/*     */   }
/*     */ 
/*     */   public IAssetManager getAssetManager()
/*     */   {
/* 111 */     return this.m_assetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetManager(IAssetManager a_sAssetManager)
/*     */   {
/* 117 */     this.m_assetManager = a_sAssetManager;
/*     */   }
/*     */ 
/*     */   public void setAssetEntityManager(AssetEntityManager entityManager)
/*     */   {
/* 122 */     this.m_assetEntityManager = entityManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewPrintImageAction
 * JD-Core Version:    0.6.0
 */