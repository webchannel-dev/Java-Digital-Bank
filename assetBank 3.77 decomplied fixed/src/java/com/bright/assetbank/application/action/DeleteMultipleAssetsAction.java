/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.exception.AssetNotFoundException;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
/*    */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class DeleteMultipleAssetsAction extends BTransactionAction
/*    */   implements FrameworkConstants
/*    */ {
/* 42 */   private IAssetManager m_assetManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 51 */     ActionForward afForward = null;
/* 52 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 55 */     if (!userProfile.getIsAdmin())
/*    */     {
/* 57 */       this.m_logger.debug("This user does not have permission to view the admin page");
/* 58 */       return a_mapping.findForward("NoPermission");
/*    */     }
/*    */ 
/* 62 */     Enumeration e = a_request.getParameterNames();
/* 63 */     List assetIds = new ArrayList();
/* 64 */     while (e.hasMoreElements())
/*    */     {
/* 66 */       String sName = (String)e.nextElement();
/* 67 */       if (sName.startsWith("delete_id"))
/*    */       {
/* 69 */         long lAssetId = getLongParameter(a_request, sName);
/* 70 */         assetIds.add(Long.valueOf(lAssetId));
/*    */       }
/*    */     }
/*    */ 
/* 74 */     for (Iterator i$ = assetIds.iterator(); i$.hasNext(); ) { long lAssetId = ((Long)i$.next()).longValue();
/*    */       try
/*    */       {
/* 78 */         this.m_assetManager.deleteAsset(a_dbTransaction, lAssetId);
/* 79 */         if (userProfile.getAssetBox().containsAsset(lAssetId))
/*    */         {
/* 81 */           userProfile.getAssetBox().removeAsset(lAssetId);
/*    */         }
/*    */ 
/*    */       }
/*    */       catch (AssetNotFoundException ex)
/*    */       {
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 91 */     afForward = a_mapping.findForward("Success");
/*    */ 
/* 93 */     return afForward;
/*    */   }
/*    */ 
/*    */   public void setAssetManager(IAssetManager a_sAssetManager)
/*    */   {
/* 98 */     this.m_assetManager = a_sAssetManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.DeleteMultipleAssetsAction
 * JD-Core Version:    0.6.0
 */