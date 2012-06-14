/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
import com.bright.assetbank.application.service.AssetManager;
/*    */ import com.bright.assetbank.application.service.IAssetManager;
/*    */ import com.bright.assetbank.search.service.MultiLanguageSearchManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import java.sql.SQLException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class MoveAssetRelationshipAction extends BTransactionAction
/*    */   implements AssetBankConstants
/*    */ {
/* 45 */   private IAssetManager m_assetManager = null;
/* 46 */   private MultiLanguageSearchManager m_searchManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 56 */     long lParentId = getLongParameter(a_request, "id");
/* 57 */     long lChildId = getLongParameter(a_request, "childId");
/*    */ 
/* 59 */     boolean bReindex =((AssetManager)this.m_assetManager).demoteChildInRelationshipSequence(a_dbTransaction, lParentId, lChildId);
/*    */ 
/* 61 */     if (bReindex)
/*    */     {
/*    */       try
/*    */       {
/* 65 */         a_dbTransaction.commit();
/* 66 */         a_dbTransaction = getNewTransaction();
/*    */       }
/*    */       catch (SQLException e)
/*    */       {
/* 70 */         this.m_logger.error("MoveAssetRelationshipAction : SQLException whilst committing transaction : " + e, e);
/*    */       }
/* 72 */       this.m_searchManager.indexDocument(this.m_assetManager.getAsset(a_dbTransaction, lParentId, null, true, true), true);
/*    */     }
/*    */ 
/* 75 */     return createRedirectingForward("id=" + lParentId + "#related", a_mapping, "Success");
/*    */   }
/*    */ 
/*    */   public void setAssetManager(IAssetManager a_sAssetManager)
/*    */   {
/* 81 */     this.m_assetManager = a_sAssetManager;
/*    */   }
/*    */ 
/*    */   public void setSearchManager(MultiLanguageSearchManager searchManager)
/*    */   {
/* 86 */     this.m_searchManager = searchManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.MoveAssetRelationshipAction
 * JD-Core Version:    0.6.0
 */