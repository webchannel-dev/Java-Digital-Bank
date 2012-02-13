/*    */ package com.bright.assetbank.application.action;
/*    */ 
/*    */ import com.bn2web.common.exception.Bn2Exception;
/*    */ import com.bright.assetbank.application.constant.AssetBankConstants;
/*    */ import com.bright.assetbank.application.form.SlideshowForm;
/*    */ import com.bright.assetbank.attribute.constant.AttributeConstants;
/*    */ import com.bright.assetbank.attribute.service.AttributeManager;
/*    */ import com.bright.assetbank.attribute.util.AttributeUtil;
/*    */ import com.bright.assetbank.repurposing.util.RepurposingUtil;
/*    */ import com.bright.assetbank.user.bean.ABUserProfile;
/*    */ import com.bright.framework.common.action.BTransactionAction;
/*    */ import com.bright.framework.constant.FrameworkConstants;
/*    */ import com.bright.framework.database.bean.DBTransaction;
/*    */ import com.bright.framework.user.bean.UserProfile;
/*    */ import com.bright.framework.util.StringUtil;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class ViewSlideshowAction extends BTransactionAction
/*    */   implements AttributeConstants, AssetBankConstants, FrameworkConstants
/*    */ {
/* 50 */   private AttributeManager m_attributeManager = null;
/*    */ 
/*    */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*    */     throws Bn2Exception
/*    */   {
/* 60 */     SlideshowForm ssForm = (SlideshowForm)a_form;
/*    */ 
/* 63 */     if (!StringUtil.stringIsPopulated(ssForm.getDisplayTime()))
/*    */     {
/* 65 */       ssForm.setDisplayTime("5");
/*    */     }
/*    */     else
/*    */     {
/*    */       try
/*    */       {
/* 71 */         Integer.parseInt(ssForm.getDisplayTime());
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 75 */         ssForm.setDisplayTime("5");
/*    */       }
/*    */     }
/*    */ 
/* 79 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*    */ 
/* 82 */     Vector vecIds = AttributeUtil.getControlPanelSelectedAttributes(a_request);
/* 83 */     ssForm.setAttributeIds(vecIds);
/*    */ 
/* 85 */     Vector vecAttributes = this.m_attributeManager.getVisibleAttributes(a_dbTransaction);
/*    */ 
/* 88 */     vecAttributes = RepurposingUtil.filterAttributes(vecAttributes, userProfile, vecIds);
/* 89 */     ssForm.setAttributeList(vecAttributes);
/*    */ 
/* 91 */     return a_mapping.findForward("Success");
/*    */   }
/*    */ 
/*    */   public void setAttributeManager(AttributeManager a_attributeManager)
/*    */   {
/* 96 */     this.m_attributeManager = a_attributeManager;
/*    */   }
/*    */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.application.action.ViewSlideshowAction
 * JD-Core Version:    0.6.0
 */