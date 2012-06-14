/*    */ package com.bright.assetbank.repurposing.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.constant.AssetBankSettings;
/*    */ import com.bright.assetbank.repurposing.bean.RepurposedAsset;
/*    */ import com.bright.assetbank.repurposing.form.RepurposingForm;
/*    */ import com.bright.assetbank.repurposing.service.AssetRepurposingManager;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewRepurposedVersionAction extends BTransactionAction
/*    */   implements AssetBankConstants, FrameworkConstants
/*    */ {
/*    */   private AssetRepurposingManager m_assetRepurposingManager;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 52 */     RepurposingForm form = (RepurposingForm)a_form;
/*    */ 
/* 54 */     long lId = getLongParameter(a_request, "id");
/* 55 */     long lAssetId = getLongParameter(a_request, "assetId");
/*    */ 
/* 57 */     String sUrlBase = AssetBankSettings.getRepurposedFileBaseUrl(a_request);
/* 58 */     int iHeight = getIntParameter(a_request, "height");
/* 59 */     int iWidth = getIntParameter(a_request, "width");
/*    */ 
/* 61 */     RepurposedAsset version = this.m_assetRepurposingManager.getRepurposedAsset(a_dbTransaction, lId, sUrlBase, iHeight, iWidth);
/*    */ 
/* 63 */     if (version != null)
/*    */     {
/* 65 */       form.setRepurposedVersion(version);
/* 66 */       form.setBaseUrl(sUrlBase);
/* 67 */       form.setAssetId(lAssetId);
/*    */ 
/* 69 */       return a_mapping.findForward("Success");
/*    */     }
/*    */ 
/* 72 */     return a_mapping.findForward("Failure");
/*    */   }
/*    */ 
/*    */   public void setAssetRepurposingManager(AssetRepurposingManager assetRepurposingManager)
/*    */   {
/* 77 */     this.m_assetRepurposingManager = assetRepurposingManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.repurposing.action.ViewRepurposedVersionAction
 * JD-Core Version:    0.6.0
 */