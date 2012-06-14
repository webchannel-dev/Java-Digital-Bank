/*     */ package com.bright.assetbank.attribute.action;
/*     */ 
/*     */ import com.bn2web.common.exception.Bn2Exception;
/*     */ import com.bright.assetbank.attribute.bean.Attribute;
/*     */ import com.bright.assetbank.attribute.service.AttributeManager;
/*     */ import com.bright.assetbank.user.bean.ABUserProfile;
/*     */ import com.bright.framework.common.action.BTransactionAction;
/*     */ import com.bright.framework.database.bean.DBTransaction;
/*     */ import com.bright.framework.user.bean.UserProfile;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class MoveAttributeAction extends BTransactionAction
/*     */ {
/*     */   private static final String c_ksClassName = "MoveAttributeAction";
/*  49 */   private AttributeManager m_attributeManager = null;
/*     */ 
/*     */   public ActionForward execute(ActionMapping a_mapping, ActionForm a_form, HttpServletRequest a_request, HttpServletResponse a_response, DBTransaction a_dbTransaction)
/*     */     throws Bn2Exception
/*     */   {
/*  73 */     ActionForward afForward = null;
/*  74 */     ABUserProfile userProfile = (ABUserProfile)UserProfile.getUserProfile(a_request.getSession());
/*     */ 
/*  77 */     if (!userProfile.getIsAdmin())
/*     */     {
/*  79 */       this.m_logger.error("MoveAttributeActionThis user does not have permission to view the admin pages");
/*  80 */       return a_mapping.findForward("NoPermission");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  86 */       int iIndex = getIntParameter(a_request, "index");
/*     */ 
/*  88 */       if (iIndex < 0)
/*     */       {
/*  90 */         throw new Bn2Exception("MoveAttributeAction - missing parameter called index");
/*     */       }
/*     */ 
/*  94 */       boolean bMoveUp = new Boolean(a_request.getParameter("up")).booleanValue();
/*     */ 
/*  97 */       Vector vecAttributePositions = this.m_attributeManager.getVisibleAttributePositionList();
/*     */ 
/* 100 */       if (iIndex >= vecAttributePositions.size())
/*     */       {
/* 102 */         throw new Bn2Exception("MoveAttributeAction - the index specified is too large");
/*     */       }
/*     */ 
/* 106 */       Attribute attToMove = (Attribute)vecAttributePositions.get(iIndex);
/* 107 */       Attribute attToSwap = null;
/*     */ 
/* 110 */       if (((!bMoveUp) && (iIndex < vecAttributePositions.size() - 1)) || ((bMoveUp) && (iIndex > 0)))
/*     */       {
/* 114 */         int iAdjuster = 0;
/*     */ 
/* 116 */         if (bMoveUp)
/*     */         {
/* 118 */           iAdjuster = -1;
/*     */         }
/*     */         else
/*     */         {
/* 122 */           iAdjuster = 1;
/*     */         }
/*     */ 
/* 126 */         attToSwap = (Attribute)vecAttributePositions.get(iIndex + iAdjuster);
/*     */ 
/* 129 */         attToMove.setSequence(attToMove.getSequence() + iAdjuster);
/* 130 */         attToSwap.setSequence(attToSwap.getSequence() - iAdjuster);
/*     */ 
/* 133 */         this.m_attributeManager.updateAttributePosition(a_dbTransaction, attToMove);
/* 134 */         this.m_attributeManager.updateAttributePosition(a_dbTransaction, attToSwap);
/*     */       }
/*     */ 
/* 137 */       afForward = a_mapping.findForward("Success");
/*     */     }
/*     */     catch (Bn2Exception bn2e)
/*     */     {
/* 141 */       this.m_logger.error("MoveAttributeAction" + bn2e.getMessage());
/* 142 */       throw bn2e;
/*     */     }
/*     */ 
/* 145 */     return afForward;
/*     */   }
/*     */ 
/*     */   public void setAttributeManager(AttributeManager a_attributeManager)
/*     */   {
/* 151 */     this.m_attributeManager = a_attributeManager;
/*     */   }
/*     */ }

/* Location:           C:\Users\mamatha\Desktop\com.zip
 * Qualified Name:     com.bright.assetbank.attribute.action.MoveAttributeAction
 * JD-Core Version:    0.6.0
 */