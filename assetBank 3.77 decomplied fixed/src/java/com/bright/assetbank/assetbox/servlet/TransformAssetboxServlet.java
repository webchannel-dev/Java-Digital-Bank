/*    */ package com.bright.assetbank.assetbox.servlet;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bn2web.common.service.GlobalApplication;
/*    */ import com.bright.assetbank.application.bean.Asset;
/*    */ import com.bright.assetbank.application.servlet.TransformAssetsServlet;
/*    */ import com.bright.assetbank.assetbox.bean.AssetBox;
/*    */ import com.bright.assetbank.assetbox.service.AssetBoxManager;
/*    */ import com.bright.assetbank.assetbox.util.AssetBoxUtil;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.database.service.DBTransactionManager;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.avalon.framework.component.ComponentManager;
/*    */ import org.apache.commons.logging.Log;
/*    */ 
/*    */ public class TransformAssetboxServlet extends TransformAssetsServlet
/*    */ {
/*    */   private static final long serialVersionUID = 7893923982636118264L;
/*    */   private static final String c_ksClassName = "TransformAssetboxServlet";
/*    */ 
/*    */   protected Vector<Asset> getAssets(HttpServletRequest a_request)
/*    */     throws Bn2Exception
/*    */   {
/* 54 */     DBTransaction transaction = null;
/* 55 */     Vector vecAssets = null;
/*    */     try
/*    */     {
/* 60 */       DBTransactionManager transManager = (DBTransactionManager)GlobalApplication.getInstance().getComponentManager().lookup("DBTransactionManager");
/* 61 */       AssetBoxManager assetBoxManager = (AssetBoxManager)GlobalApplication.getInstance().getComponentManager().lookup("AssetBoxManager");
/*    */ 
/* 64 */       transaction = transManager.getNewTransaction();
/* 65 */       ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/* 66 */       assetBoxManager.refreshAssetBoxInProfileOrFail(transaction, userProfile, userProfile.getAssetBox().getId());
/*    */ 
/* 68 */       String sGetAll = a_request.getParameter("fullList");
/*    */ 
/* 70 */       vecAssets = AssetBoxUtil.getAssetsFromAssetBox(userProfile, sGetAll.equals("true"), -1L);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 74 */       if (transaction != null)
/*    */       {
/*    */         try
/*    */         {
/* 78 */           transaction.rollback(); } catch (Exception ex) {
/*    */         }
/*    */       }
/* 81 */       GlobalApplication.getInstance().getLogger().error("TransformAssetboxServlet.getAssets: ", e);
/*    */     }
/*    */     finally
/*    */     {
/* 85 */       if (transaction != null)
/*    */       {
/*    */         try
/*    */         {
/* 89 */           transaction.commit(); } catch (Exception ex) {
/*    */         }
/*    */       }
/*    */     }
/* 93 */     return vecAssets;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.assetbox.servlet.TransformAssetboxServlet
 * JD-Core Version:    0.6.0
 */