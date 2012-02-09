/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*     */ import com.bright.assetbank.application.service.FileAssetManagerImpl;
/*     */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class SetNameAttributeAction extends BTransactionAction
/*     */   implements AttributeConstants, AssetBankConstants
/*     */ {
/*     */   private static final String c_ksClassName = "SetNameAttributeAction";
/*  49 */   private FileAssetManagerImpl m_fileAssetManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  77 */     ActionForward afForward = null;
/*  78 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  81 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  83 */       this.m_logger.error("SetNameAttributeActionThis user does not have permission to view the admin pages");
/*  84 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*  88 */     Enumeration e = a_request.getParameterNames();
/*  89 */     Vector vecAttributeIds = new Vector();
/*     */ 
/*  91 */     while (e.hasMoreElements())
/*     */     {
/*  93 */       String sName = (String)e.nextElement();
/*     */ 
/*  95 */       if (sName.startsWith("nameAtt"))
/*     */       {
/*  97 */         long lVal = getLongParameter(a_request, sName);
/*  98 */         vecAttributeIds.add(new Long(lVal));
/*     */       }
/*     */     }
/* 101 */     this.m_fileAssetManager.setAssetNamingAttributes(a_dbTransaction, vecAttributeIds);
/* 102 */     long lGroupId = getLongParameter(a_request, "daGroupId");
/* 103 */     String sQueryString = "daGroupId=" + lGroupId;
/*     */ 
/* 105 */     afForward = createRedirectingForward(sQueryString, a_mapping, "Success");
/*     */ 
/* 108 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setFileAssetManager(FileAssetManagerImpl a_fileAssetManager)
/*     */   {
/* 113 */     this.m_fileAssetManager = a_fileAssetManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.SetNameAttributeAction
 * JD-Core Version:    0.6.0
 */